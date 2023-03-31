package priv.xl.toolkit.img;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;
import com.sun.org.slf4j.internal.Logger;
import com.sun.org.slf4j.internal.LoggerFactory;
import org.apache.commons.lang3.exception.ExceptionUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * 图片工具类
 *
 * @author lei.xu
 * @since 2023/3/29 2:04 下午
 */
public class ImageUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(ImageUtil.class);

    /**
     * 读取图片
     *
     * ImageIO读取图片时, 默认只能读取横向图像, 纵向图像会旋转90/270度, 该方法主要是为了修正旋转角度而提供. 需要
     * 读取横向图像时, 建议直接使用ImageIO.class提供的read()方法读取, 性能更好
     *
     * @param imgFile 图片文件
     * @return 缓冲图像
     * @throws IOException IO异常
     */
    public static BufferedImage read(File imgFile) throws IOException {
        // 读取图片
        BufferedImage bufferedImage = ImageIO.read(imgFile);

        // 获取图片旋转角度, 检查是否需要修正
        int rotateAngle = getImgRotateAngle(imgFile);
        if (90 != rotateAngle && 270 != rotateAngle) {
            return bufferedImage;
        }

        // 宽高互换
        int correctWidth = bufferedImage.getHeight();
        int correctHeight = bufferedImage.getWidth();

        // 中心点位置
        double centerWidth = correctWidth / 2.0;
        double centerHeight = correctHeight / 2.0;

        BufferedImage correctImgBuffer = new BufferedImage(correctWidth, correctHeight, BufferedImage.TYPE_INT_RGB);

        // 在画布中旋转, 修正图片角度
        Graphics2D graphics = correctImgBuffer.createGraphics();
        graphics.rotate(Math.toRadians(rotateAngle), centerWidth, centerHeight);
        graphics.drawImage(bufferedImage, (correctWidth - bufferedImage.getWidth()) / 2, (correctHeight - bufferedImage.getHeight()) / 2, null);
        graphics.rotate(Math.toRadians(-rotateAngle), centerWidth, centerHeight);
        graphics.dispose();

        return correctImgBuffer;
    }

    public static BufferedImage read(String path) throws IOException {
        return read(new File(path));
    }

    /**
     * 给图片添加文字水印
     *
     * 当前方法使用原生写法, 性能不高, 如没有文字在图像位置的偏移需求, 建议使用Hutool ImgUtil.class提供的pressText()方法,
     * 当前方法主要为了实现文字水印依照图片大小缩放的功能而提供, 图像较小时, 当前方法的水印会相应的缩小
     *
     * @param imgFile       原图文件
     * @param watermarkText 水印文字
     */
    public static void watermarking(File imgFile, String watermarkText) {
        if (!imgFile.exists()) {
            throw new RuntimeException("图片不存在, 无法加水印");
        }
        try {
            // 获取原图的后缀
            String imgFileExtension = imgFile.getName().substring(imgFile.getName().indexOf(".") + 1);

            // 读取原图像到缓存
            BufferedImage originImgBuffer = read(imgFile);

            int width = originImgBuffer.getWidth();
            int height = originImgBuffer.getHeight();

            // 水印层缓冲图像
            BufferedImage watermarkImgBuffer = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            Graphics2D watermarkGraphics = watermarkImgBuffer.createGraphics();
            watermarkGraphics.drawImage(originImgBuffer, 0, 0, width, height, null);
            watermarkGraphics.setColor(Color.WHITE);

            double fontLength = width * 0.2;
            int fontSize = (int) (fontLength / watermarkText.length() * 1.5);
            watermarkGraphics.setFont(new Font("宋体", Font.BOLD, fontSize));

            int x = width - (watermarkText.length() + 1) * fontSize;
            int y = height - fontSize * 2;
            watermarkGraphics.drawString(watermarkText, x, y);

            // 覆盖原图
            ImageIO.write(watermarkImgBuffer, imgFileExtension, imgFile);
        } catch (Exception e) {
            // 发生异常要求不影响原图的处理
            LOGGER.error("图片加水印失败, 异常堆栈: \n{}", ExceptionUtils.getStackTrace(e));
        }
    }

    public static void watermarking(String imgFilePath, String watermarkText) {
        watermarking(new File(imgFilePath), watermarkText);
    }

    /**
     * 给图片添加图片水印
     *
     * 输出的结果图片与与原图分开, 或直接返回加水印的缓冲图像时, 建议使用Hutool ImgUtil.class提供的pressImage()方法加水印,
     * 该方法的性能更好, 当前方法主要为了解决加水印后的图像需要覆盖原图的问题
     *
     * @param imgFile               原图文件
     * @param watermarkingImgBuffer 水印图片缓冲图像
     */
    public static void imgWatermarking(File imgFile, BufferedImage watermarkingImgBuffer) {
        if (!imgFile.exists()) {
            throw new RuntimeException("图片不存在, 无法加水印");
        }
        try {
            // 获取原图的后缀
            String imgFileExtension = imgFile.getName().substring(imgFile.getName().indexOf(".") + 1);

            // 读取原图像到缓存, 保存原图长宽
            BufferedImage originImgBuffer = read(imgFile);

            // 计算水印图像在原图中的偏移量(右下角)
            // 图像最终偏移量为右(下)侧边框, 为了能展示出完整的水印图像, 需要偏移时减去图像宽度, 最后的(* 1.5), 是为了能够让
            // 图像距离右(下)侧边框有一定距离, 而不是紧贴右(下)侧边框
            int x = (int) (originImgBuffer.getWidth() - watermarkingImgBuffer.getWidth() * 1.5);
            int y = (int) (originImgBuffer.getHeight() - watermarkingImgBuffer.getHeight() * 1.5);

            // 声明原图大小的画布, 增加水印图像(按设定的偏移量)
            Graphics2D graphics = originImgBuffer.createGraphics();
            graphics.drawImage(watermarkingImgBuffer, x, y, null);
            graphics.dispose();

            // 覆盖原图
            ImageIO.write(originImgBuffer, imgFileExtension, imgFile);
        } catch (Exception e) {
            // 发生异常要求不影响原图的处理
            LOGGER.error("图片加水印失败, 异常堆栈: \n{}", ExceptionUtils.getStackTrace(e));
        }
    }

    /**
     * 获取图片的旋转角度
     *
     * @param imgFile 图片文件
     * @return 图片的旋转角度
     */
    private static int getImgRotateAngle(File imgFile) {
        // 获取图片的旋转角度
        try {
            Metadata metadata = ImageMetadataReader.readMetadata(imgFile);
            for (Directory directory : metadata.getDirectories()) {
                for (Tag tag : directory.getTags()) {
                    if ("Orientation".equalsIgnoreCase(tag.getTagName())) {
                        String orientation = tag.getDescription();
                        if (orientation.contains("90")) {
                            return 90;
                        } else if (orientation.contains("180")) {
                            return 180;
                        } else if (orientation.contains("270")) {
                            return 270;
                        }
                    }
                }
            }
        } catch (ImageProcessingException | IOException e) {
            LOGGER.error("获取图片旋转角度失败, 异常堆栈: \n{}", ExceptionUtils.getStackTrace(e));
        }
        return 0;
    }

}
