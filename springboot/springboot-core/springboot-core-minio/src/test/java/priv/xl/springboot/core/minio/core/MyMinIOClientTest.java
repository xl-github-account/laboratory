package priv.xl.springboot.core.minio.core;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import priv.xl.springboot.core.minio.enums.BucketTypeEnum;

@SpringBootTest
class MyMinIOClientTest {

    @Autowired
    MyMinIOClient myMinIOClient;

    @Test
    public void test() {
        long s = System.currentTimeMillis();
        // 获取图片的预签名URL
        /*for (int i = 0; i < 100; i++) {
            String presignedUrl = this.myMinIOClient.getImgPreviewPresignedUrl(
                    "/20230316/f22f6145f9844f3c87e9d9f9d9ed5312-1678944442887.jpeg", BucketTypeEnum.WORK_ORDER_IMG, 30, TimeUnit.MINUTES
            );
            System.out.println(presignedUrl);
        }*/
        // 获取桶下存储的所有文件地址
//        List<String> imgPathArr = this.myMinIOClient.getAllFile(BucketTypeEnum.WORK_ORDER_IMG);
//        imgPathArr.forEach(System.out::println);

        this.myMinIOClient.batchDeleteFiles(BucketTypeEnum.WORK_ORDER_IMG, "deploy-system.yml");
        System.out.println("耗时: " + (System.currentTimeMillis() - s));
    }

}