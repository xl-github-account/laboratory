package priv.xl.springboot.core.minio.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 桶类型枚举
 *
 * @author lei.xu
 * 2022/11/10 5:40 下午
 */
@Getter
@AllArgsConstructor
public enum BucketTypeEnum {

    /**
     * 点位图存储桶
     */
    POINT_CONFIG("bucket-image-point-config"),

    /**
     * 海康摄像头历史视频
     */
    HIK_CAMERA("bucket-hik-camera"),

    /**
     * 工单处理图片上传存储桶
     */
    WORK_ORDER_IMG("bucket-work-order-img");

    private final String name;

}
