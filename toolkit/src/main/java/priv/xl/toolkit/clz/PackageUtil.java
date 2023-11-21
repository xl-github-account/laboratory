package priv.xl.toolkit.clz;

import com.sun.org.slf4j.internal.Logger;
import com.sun.org.slf4j.internal.LoggerFactory;

import java.io.File;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * 包工具类
 *
 * @author lei.xu
 * 2022/8/18 1:54 下午
 */
public class PackageUtil {

    private static final Logger log = LoggerFactory.getLogger(PackageUtil.class);

    /**
     * 获取指定包路径下的全部class
     *
     * @param recursive 是否查找子package
     * @param packages  包路径
     * @return 找到class以后存放的集合
     */
    public static List<Class<?>> findClassInPackage(boolean recursive, String... packages) {
        if (packages == null || packages.length == 0) {
            return new ArrayList<>();
        }

        List<Class<?>> clazzs = new ArrayList<>();
        for (String pkg : packages) {
            // 包名对应的路径名称
            String packageDirName = pkg.replace('.', '/');

            Enumeration<URL> dirs;
            try {
                dirs = Thread.currentThread().getContextClassLoader().getResources(packageDirName);
                while (dirs.hasMoreElements()) {
                    URL url = dirs.nextElement();

                    String protocol = url.getProtocol();

                    if ("file".equals(protocol)) {
                        String filePath = URLDecoder.decode(url.getFile(), "UTF-8");
                        findClassInPackageByFile(pkg, filePath, recursive, clazzs);
                    } else if ("jar".equals(protocol)) {
                        JarFile jar = ((JarURLConnection) url.openConnection()).getJarFile();
                        findClassesByJar(pkg, jar, clazzs);
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return clazzs;
    }

    /**
     * 在package对应的路径下找到所有的class
     *
     * @param pkgName   package名称
     * @param filePath  package对应的路径
     * @param recursive 是否查找子package
     * @param clazzs    找到class以后存放的集合
     */
    public static void findClassInPackageByFile(String pkgName, String filePath, final boolean recursive, List<Class<?>> clazzs) {
        File dir = new File(filePath);
        if (!dir.exists() || !dir.isDirectory()) {
            return;
        }
        // 在给定的目录下找到所有的文件，并且进行条件 过滤
        File[] dirFiles = dir.listFiles(file -> {
                    // 接受dir目录
                    boolean acceptDir = recursive && file.isDirectory();
                    // 接受class文件
                    boolean acceptClass = file.getName().endsWith("class");
                    return acceptDir || acceptClass;
                }
        );

        if (dirFiles != null) {
            Arrays.stream(dirFiles).forEach(file -> {
                if (file.isDirectory()) {
                    findClassInPackageByFile(pkgName + "." + file.getName(), file.getAbsolutePath(), recursive, clazzs);
                } else {
                    String className = file.getName().substring(0, file.getName().length() - 6);
                    try {
                        clazzs.add(Thread.currentThread().getContextClassLoader().loadClass(pkgName + "." + className));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    /**
     * 扫描包路径下的所有class文件
     *
     * @param pkgName 包名
     * @param jar     jar文件
     * @param classes 保存包路径下class的集合
     */
    private static void findClassesByJar(String pkgName, JarFile jar, List<Class<?>> classes) {
        String pkgDir = pkgName.replace(".", "/");


        Enumeration<JarEntry> entry = jar.entries();

        JarEntry jarEntry;
        String name, className;
        Class<?> claze;
        while (entry.hasMoreElements()) {
            jarEntry = entry.nextElement();

            name = jarEntry.getName();
            if (name.charAt(0) == '/') {
                name = name.substring(1);
            }


            if (jarEntry.isDirectory() || !name.startsWith(pkgDir) || !name.endsWith(".class")) {
                // 非指定包路径， 非class文件
                continue;
            }


            // 去掉后面的".class", 将路径转为package格式
            className = name.substring(0, name.length() - 6);
            claze = loadClass(className.replace("/", "."));
            if (claze != null) {
                classes.add(claze);
            }
        }
    }

    private static Class<?> loadClass(String fullClzName) {
        try {
            return Thread.currentThread().getContextClassLoader().loadClass(fullClzName);
        } catch (ClassNotFoundException e) {
            log.error("load class error! clz: {}, e:{}", fullClzName, e);
        }
        return null;
    }

}
