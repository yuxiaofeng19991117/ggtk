package com.bs.ggkt.vod.service.impl;

import com.bs.ggkt.vod.service.FileService;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.http.HttpProtocol;
import com.qcloud.cos.model.*;
import com.qcloud.cos.region.Region;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.UUID;

import static com.bs.ggkt.vod.utils.ConstantPropertiesUtil.*;

@Service
public class FileServiceImpl implements FileService {

    /**
     * 将文件上传到腾讯云并且返回url
     * @param file          上传的文件
     * @return              返回的url
     */
    @Override
    public String upload(MultipartFile file) {
        // 1 初始化用户身份信息（secretId, secretKey）。
        // SECRETID和SECRETKEY请登录访问管理控制台 https://console.cloud.tencent.com/cam/capi 进行查看和管理
        String secretId = ACCESS_KEY_ID;
        String secretKey = ACCESS_KEY_SECRET;
        COSCredentials cred = new BasicCOSCredentials(secretId, secretKey);
        // 2 设置 bucket 的地域, COS 地域的简称请参照 https://cloud.tencent.com/document/product/436/6224
        // clientConfig 中包含了设置 region, https(默认 http), 超时, 代理等 set 方法, 使用可参见源码或者常见问题 Java SDK 部分。
        Region region = new Region(END_POINT);
        ClientConfig clientConfig = new ClientConfig(region);
        // 这里建议设置使用 https 协议
        // 从 5.6.54 版本开始，默认使用了 https
        clientConfig.setHttpProtocol(HttpProtocol.https);
        // 3 生成 cos 客户端。
        COSClient cosClient = new COSClient(cred, clientConfig);

        // 存储桶的命名格式为 BucketName-APPID，此处填写的存储桶名称必须为此格式
        String bucketName = BUCKET_NAME;
        // 对象键(Key)是对象在存储桶中的唯一标识。
        //在文件名称前面添加uuid值
        String key = UUID.randomUUID().toString().replaceAll("-", "") + file.getOriginalFilename();

        //对上传文件分组, 根据当前日期
        String dateTime = new DateTime().toString("yyyy/MM/dd");
        key = dateTime + "/" + key;

        try {
            //获取上传文件的输入流
            InputStream stream = file.getInputStream();

            ObjectMetadata objectMetadata = new ObjectMetadata();
            // 上传的流如果能够获取准确的流长度，则推荐一定填写 content-length
            // 如果确实没办法获取到，则下面这行可以省略，但同时高级接口也没办法使用分块上传了

            PutObjectRequest putObjectRequest = new PutObjectRequest(
                    bucketName,
                    key,
                    stream,
                    objectMetadata);

            // 设置存储类型（如有需要，不需要请忽略此行代码）, 默认是标准(Standard), 低频(standard_ia)
            // 更多存储类型请参见 https://cloud.tencent.com/document/product/436/33417
            putObjectRequest.setStorageClass(StorageClass.Standard_IA);

            // 高级接口会返回一个异步结果Upload
            // 可同步地调用 waitForUploadResult 方法等待上传完成，成功返回UploadResult, 失败抛出异常
            PutObjectResult Res = cosClient.putObject(putObjectRequest);

            //返回文件上传路径
            return "https://" + bucketName + "." + "cos" + "." + END_POINT + ".myqcloud.com" + "/" + key;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

}
