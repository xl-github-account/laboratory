package priv.xl.springboot.core.minio.core;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import io.minio.*;
import io.minio.errors.*;
import io.minio.http.Method;
import io.minio.messages.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;
import priv.xl.springboot.core.minio.exception.MinIoOperationException;
import priv.xl.springboot.core.minio.utils.TagsExtend;
import priv.xl.springboot.core.minio.enums.BucketTypeEnum;

import java.io.*;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * MinIO操作业务类
 *
 * @author lei.xu
 * 2022/10/24 3:06 下午
 */
@Slf4j
@AllArgsConstructor
public class MyMinIOClient {

    private final MinioClient minioClient;

    /**
     * 获取桶列表
     *
     * @return 桶信息列表
     */
    public List<Bucket> listBuckets() {
        try {
            return this.minioClient.listBuckets();
        } catch (Exception e) {
            this.exceptionPrintLog(e);
            throw new MinIoOperationException("获取桶列表发生错误");
        }
    }

    /**
     * 检查桶是否存在
     *
     * @param bucketName 桶名
     * @return bool
     */
    public boolean bucketIsExists(String bucketName) {
        try {
            BucketExistsArgs bucketExistsArgs = BucketExistsArgs.builder().bucket(bucketName).build();
            return this.minioClient.bucketExists(bucketExistsArgs);
        } catch (Exception e) {
            this.exceptionPrintLog(e);
            throw new MinIoOperationException("尝试检查桶状态发生错误，桶名称[" + bucketName + "]");
        }
    }

    /**
     * 创建桶
     *
     * @param bucketName 桶名
     */
    public void makeBucket(String bucketName) {
        try {
            MakeBucketArgs makeBucketArgs = MakeBucketArgs.builder().bucket(bucketName).build();
            this.minioClient.makeBucket(makeBucketArgs);
        } catch (Exception e) {
            this.exceptionPrintLog(e);
            throw new MinIoOperationException("尝试创建桶发生错误，桶名称[" + bucketName + "]");
        }
    }

    /**
     * 删除桶
     *
     * @param bucketName 桶名
     */
    public void removeBucket(String bucketName) {
        try {
            RemoveBucketArgs removeBucketArgs = RemoveBucketArgs.builder().bucket(bucketName).build();
            this.minioClient.removeBucket(removeBucketArgs);
        } catch (Exception e) {
            this.exceptionPrintLog(e);
            throw new MinIoOperationException("尝试删除桶失败，桶名称[" + bucketName + "]");
        }
    }

    /**
     * 获取桶的标签列表
     *
     * @param bucketName 桶名
     * @return 标签列表对象: Map<String, String>
     */
    public Tags listBucketTags(String bucketName) {
        try {
            GetBucketTagsArgs getBucketTagsArgs = GetBucketTagsArgs.builder().bucket(bucketName).build();
            return this.minioClient.getBucketTags(getBucketTagsArgs);
        } catch (Exception e) {
            this.exceptionPrintLog(e);
            throw new MinIoOperationException("尝试获取桶标签列表发生错误，桶名称[" + bucketName + "]");
        }
    }

    /**
     * 添加桶标签
     *
     * @param bucketName 桶名
     * @param tags       桶标签扩展工具
     */
    public void setBucketTags(String bucketName, TagsExtend tags) {
        try {
            SetBucketTagsArgs setBucketTagsArgs = SetBucketTagsArgs.builder().bucket(bucketName).tags(tags.getTags()).build();
            this.minioClient.setBucketTags(setBucketTagsArgs);
        } catch (Exception e) {
            this.exceptionPrintLog(e);
            throw new MinIoOperationException("尝试为桶添加标签发生错误，桶名称[" + bucketName + "]");
        }
    }

    /**
     * 删除桶标签列表
     *
     * @param bucketName 桶名
     */
    public void removeBucketTags(String bucketName) {
        try {
            DeleteBucketTagsArgs deleteBucketTagsArgs = DeleteBucketTagsArgs.builder().bucket(bucketName).build();
            this.minioClient.deleteBucketTags(deleteBucketTagsArgs);
        } catch (Exception e) {
            this.exceptionPrintLog(e);
            throw new MinIoOperationException("删除桶标签列表错误，桶名称[" + bucketName + "]");
        }
    }

    /**
     * 上传文件
     *
     * @param file       文件对象
     * @param bucketType 桶名称
     * @return 文件存储路径
     */
    public String uploadFile(File file, BucketTypeEnum bucketType) {
        // 检查桶是否存在
        if (!this.bucketIsExists(bucketType.getName())) {
            this.makeBucket(bucketType.getName());
        }

        // 生成文件上传路径
        String absolutePath = this.generatorUploadPath(file.getName());

        try {
            PutObjectArgs putObjectArgs = PutObjectArgs.builder()
                    .bucket(bucketType.getName())
                    .object(absolutePath)
                    .stream(new FileInputStream(file), file.length(), -1)
                    .build();
            this.minioClient.putObject(putObjectArgs).versionId();
        } catch (Exception e) {
            this.exceptionPrintLog(e);
            throw new MinIoOperationException("上传文件到MinIO发生错误，桶名称[" + bucketType.getName() + "]");
        }
        return absolutePath;
    }

    /**
     * 重载: 文件流上传文件
     *
     * @param file       文件流
     * @param bucketType 桶名称
     * @return 存储路径
     */
    public String uploadFile(MultipartFile file, BucketTypeEnum bucketType) {
        // 检查桶是否存在
        if (!this.bucketIsExists(bucketType.getName())) {
            this.makeBucket(bucketType.getName());
        }

        // 生成文件上传路径
        String absolutePath = this.generatorUploadPath(file.getOriginalFilename());

        try {
            PutObjectArgs putObjectArgs = PutObjectArgs.builder()
                    .bucket(bucketType.getName())
                    .object(absolutePath)
                    .stream(file.getInputStream(), file.getSize(), -1)
                    .build();
            this.minioClient.putObject(putObjectArgs).versionId();
        } catch (Exception e) {
            this.exceptionPrintLog(e);
            throw new MinIoOperationException("上传文件到MinIO发生错误，桶名称[" + bucketType.getName() + "]");
        }
        return absolutePath;
    }

    /**
     * 获取文件流
     *
     * @param storagePath 文件存储路径
     * @param bucketType  桶名称
     */
    public InputStream getFileInputStream(String storagePath, BucketTypeEnum bucketType) {
        try {
            // 检查桶是否存在
            if (!this.bucketIsExists(bucketType.getName())) {
                throw new MinIoOperationException("文件桶[" + bucketType.getName() + "]不存在!");
            }

            GetObjectArgs getObjectArgs = GetObjectArgs.builder()
                    .bucket(bucketType.getName())
                    .object(storagePath).build();
            return this.minioClient.getObject(getObjectArgs);
        } catch (Exception e) {
            if (e instanceof ErrorResponseException) {
                ErrorResponseException resEx = (ErrorResponseException) e;
                if (404 == resEx.response().code()) {
                    throw new MinIoOperationException("文件不存在，桶名称[" + bucketType.getName() + "]，存储路径[" + storagePath + "]");
                }
            }
            this.exceptionPrintLog(e);
            throw new MinIoOperationException("MinIO下载文件发生错误，桶名称[" + bucketType.getName() + "]");
        }
    }

    /**
     * 获取文件的预签名URL
     *
     * @param storagePath  文件存储路径
     * @param bucketType   桶名称
     * @param method       请求方式
     * @param duration     URL有效期
     * @param durationUnit URL有效期单位
     * @param mediaType    请求类型
     * @return 预签名的URL
     */
    public String getFilePresignedUrl(String storagePath, BucketTypeEnum bucketType, Method method, int duration, TimeUnit durationUnit, String mediaType) {
        try {
            // 检查桶是否存在
            if (!this.bucketIsExists(bucketType.getName())) {
                throw new MinIoOperationException("文件桶[" + bucketType.getName() + "]不存在!");
            }

            GetPresignedObjectUrlArgs getPresignedObjectUrlArgs = GetPresignedObjectUrlArgs.builder()
                    .method(method)
                    .bucket(bucketType.getName())
                    .object(storagePath)
//                    .expiry(duration, durationUnit)
                    .extraQueryParams(ImmutableMap.of("response-content-type", mediaType))
                    .build();
            return this.minioClient.getPresignedObjectUrl(getPresignedObjectUrlArgs);
        } catch (Exception e) {
            this.exceptionPrintLog(e);
            throw new MinIoOperationException("MinIO获取签名URL失败, 桶名称[" + bucketType.getName() + "]");
        }
    }

    /**
     * 获取图片预览预签名URL
     *
     * @param storagePath  文件存储路径
     * @param bucketType   桶名称
     * @param duration     URL有效期
     * @param durationUnit URL有效期单位
     * @param mediaType    文件格式, 默认JPEG
     * @return 签名的文件URL
     */
    public String getImgPreviewPresignedUrl(String storagePath, BucketTypeEnum bucketType, int duration, TimeUnit durationUnit, String mediaType) {
        return this.getFilePresignedUrl(storagePath, bucketType, Method.GET, duration, durationUnit, mediaType);
    }

    public String getImgPreviewPresignedUrl(String storagePath, BucketTypeEnum bucketType, int duration, TimeUnit durationUnit) {
        return this.getFilePresignedUrl(storagePath, bucketType, Method.GET, duration, durationUnit, MediaType.IMAGE_JPEG_VALUE);
    }

    /**
     * 获取文件下载预签名URL
     *
     * @param storagePath  文件存储路径
     * @param bucketType   桶名称
     * @param duration     URL有效期
     * @param durationUnit URL有效期单位
     * @return 签名的文件URL
     */
    public String getFileDownloadPresignedUrl(String storagePath, BucketTypeEnum bucketType, int duration, TimeUnit durationUnit) {
        return this.getFilePresignedUrl(storagePath, bucketType, Method.GET, duration, durationUnit, MediaType.APPLICATION_OCTET_STREAM_VALUE);
    }

    /**
     * 删除文件
     *
     * @param storagePath 存储路径
     * @param bucketType  桶名称
     */
    public void deleteFile(String storagePath, BucketTypeEnum bucketType) {
        try {
            // 检查桶是否存在
            if (!this.bucketIsExists(bucketType.getName())) {
                return;
            }

            RemoveObjectArgs removeObjectArgs = RemoveObjectArgs.builder()
                    .bucket(bucketType.getName())
                    .object(storagePath).build();
            this.minioClient.removeObject(removeObjectArgs);
        } catch (Exception e) {
            this.exceptionPrintLog(e);
            throw new MinIoOperationException("MinIO删除文件发生错误，桶名称[" + bucketType.getName() + "]");
        }
    }

    /**
     * 批量删除文件
     *
     * @param bucketType 桶名称
     * @param storages   存储路径
     */
    public void batchDeleteFiles(BucketTypeEnum bucketType, String... storages) {
        try {
            // 检查桶是否存在
            if (ArrayUtils.isEmpty(storages) || !this.bucketIsExists(bucketType.getName())) {
                return;
            }

            RemoveObjectsArgs removeObjectArgs = RemoveObjectsArgs.builder()
                    .bucket(bucketType.getName())
                    .objects(
                            Arrays.stream(storages).map(DeleteObject::new).collect(Collectors.toList())
                    ).build();
            Iterable<Result<DeleteError>> results = this.minioClient.removeObjects(removeObjectArgs);
            for (Result<DeleteError> result : results) {
                DeleteError error = result.get();
                log.error("MinIO批量删除文件发生错误, 桶名称{}, 文件路径: {}, 异常code: {}, 异常信息: {}",
                        bucketType.getName(), error.objectName(), error.code(), error.message()
                );
            }
        } catch (Exception e) {
            this.exceptionPrintLog(e);
            throw new MinIoOperationException("MinIO批量删除文件发生错误，桶名称[" + bucketType.getName() + "]");
        }
    }

    /**
     * 获取文件元数据
     *
     * @param bucketType  桶类型
     * @param storagePath 存储路径
     * @return 元数据响应对象
     */
    public StatObjectResponse getFileMeta(BucketTypeEnum bucketType, String storagePath) {
        if (!this.bucketIsExists(bucketType.getName())) {
            return null;
        }

        try {
            StatObjectArgs statObjectArgs = StatObjectArgs.builder()
                    .bucket(bucketType.getName())
                    .object(storagePath)
                    .build();
            return this.minioClient.statObject(statObjectArgs);
        } catch (Exception e) {
            String msg = e.getMessage();
            if ("Object does not exist".equalsIgnoreCase(msg)) {
                return null;
            }
            this.exceptionPrintLog(e);
            throw new MinIoOperationException("MinIO获取文件元数据发生错误，桶名称[" + bucketType.getName() + "]，文件路径[" + storagePath + "]");
        }
    }

    /**
     * 获取桶存储的所有文件路径
     *
     * @param bucketType 桶名称
     * @return 文件存储路径列表
     */
    public List<String> getAllFile(BucketTypeEnum bucketType) {
        if (!this.bucketIsExists(bucketType.getName())) {
            return Lists.newArrayList();
        }

        try {
            ListObjectsArgs listObjectsArgs = ListObjectsArgs.builder()
                    .bucket(bucketType.getName())
                    .recursive(true)
                    .build();
            Iterable<Result<Item>> resultIterable = this.minioClient.listObjects(listObjectsArgs);

            List<String> filePathArr = Lists.newArrayList();
            for (Result<Item> result : resultIterable) {
                filePathArr.add(result.get().objectName());
            }
            return filePathArr;
        } catch (Exception e) {
            this.exceptionPrintLog(e);
            throw new MinIoOperationException("MinIO获取桶存储的所有文件发生错误，桶名称[" + bucketType.getName() + "]");
        }
    }

    /**
     * 生成文件上传路径
     * 该方法线程安全
     *
     * @param originFilename 源文件名
     * @return 存储路径
     */
    private String generatorUploadPath(String originFilename) {
        String nowDate = LocalDate.now().toString().replaceAll("-", "");
        // 文件名规则: uuid-时间戳
        String uuid = UUID.randomUUID().toString().replaceAll("-", "") + "-" + System.currentTimeMillis();
        String absolutePath = "/" + nowDate + "/" + uuid;

        if (originFilename != null) {
            int idx = originFilename.lastIndexOf(".");
            if (idx != -1) {
                absolutePath += originFilename.substring(idx);
            }
        }
        return absolutePath;
    }

    private void exceptionPrintLog(Exception ex) {
        StringWriter stringWriter = new StringWriter();
        ex.printStackTrace(new PrintWriter(stringWriter));
        log.error("MinIO操作发生异常, 堆栈信息: \n{}", stringWriter);
    }

}
