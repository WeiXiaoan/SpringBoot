package java;

import com.nebula.Main;
import com.nebula.cloud.CloudConfig;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.model.PutObjectResult;
import com.qcloud.cos.region.Region;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;

/**
 * Created by SmallAnn on 2018/7/10
 */

@RunWith(SpringRunner.class)    //该注解表示测试环境
@SpringBootTest(classes = Main.class)     //该注解表示启动整个Spring-Boot程序
public class ConfigTest {

    @Autowired
    private CloudConfig config;

    @Test
    public void configTest(){
        System.out.println(config.getSecretId());
        System.out.println(config.getSecretKey());
    }

    @Test
    public void cloudTest() {
        // 1 初始化用户身份信息(secretId, secretKey)
        COSCredentials cred = new BasicCOSCredentials(config.getSecretId(), config.getSecretKey());
        // 2 设置bucket的区域, COS地域的简称请参照 https://cloud.tencent.com/document/product/436/6224
        ClientConfig clientConfig = new ClientConfig(new Region(config.getRegionName()));
        // 3 生成cos客户端
        COSClient cosClient = new COSClient(cred, clientConfig);

        // bucket的命名规则为{name}-{appid} ，此处填写的存储桶名称必须为此格式
        String bucketName = config.getBucketName();

        uploadTest(cosClient, bucketName);
    }

    private void uploadTest(COSClient cosClient, String bucketName) {
        // 简单文件上传, 最大支持 5 GB, 适用于小文件上传, 建议 20M以下的文件使用该接口
        // 大文件上传请参照 API 文档高级 API 上传
        File localFile = new File("src/main/resources/config/cloud.properties");
        // 指定要上传到 COS 上对象键
        // 对象键（Key）是对象在存储桶中的唯一标识。例如，在对象的访问域名 `bucket1-1250000000.cos.ap-guangzhou.myqcloud.com/doc1/pic1.jpg` 中，对象键为 doc1/pic1.jpg, 详情参考 [对象键](https://cloud.tencent.com/document/product/436/13324)
        String key = "upload_single_demo.properties";
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, key, localFile);
        PutObjectResult putObjectResult = cosClient.putObject(putObjectRequest);
    }
}
