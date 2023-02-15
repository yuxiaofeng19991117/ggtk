package com.bs.ggkt.vod.controller;

import com.bs.ggkt.exception.GgktException;
import com.bs.ggkt.result.Result;
import com.bs.ggkt.vod.service.VodService;
import com.bs.ggkt.vod.utils.Signature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.Random;

import static com.bs.ggkt.vod.utils.ConstantPropertiesUtil.ACCESS_KEY_ID;
import static com.bs.ggkt.vod.utils.ConstantPropertiesUtil.ACCESS_KEY_SECRET;

/**
 * <p>
 * 腾讯云点播
 * </p>
 *
 * @author
 * @since 2022-11-27
 */
@RestController
@RequestMapping("/admin/vod")
@CrossOrigin
public class VodController {

    @Autowired
    private VodService vodService;

    @PostMapping("upload")
    public Result upload(MultipartFile file) throws IOException {

        InputStream fileInputStream = file.getInputStream();
        String fileId = vodService.uploadVideo(fileInputStream);

        return Result.ok(fileId);
    }

    @DeleteMapping("remove/{fileId}")
    public Result remove(@PathVariable String fileId) {

        vodService.removeVideo(fileId);
        return Result.ok(null);

    }

    @PostMapping("sign")
    public Result sign() {

        Signature sign = new Signature();
        // 设置 App 的云 API 密钥
        sign.setSecretId(ACCESS_KEY_ID);
        sign.setSecretKey(ACCESS_KEY_SECRET);
        sign.setCurrentTime(System.currentTimeMillis() / 1000);
        sign.setRandom(new Random().nextInt(java.lang.Integer.MAX_VALUE));
        sign.setSignValidDuration(3600 * 24 * 2); // 签名有效期：2天
        try {
            String signature = sign.getUploadSignature();
            System.out.println("signature : " + signature);
            return Result.ok(signature);
        } catch (Exception e) {
            System.out.print("获取签名失败");
            e.printStackTrace();
            throw new GgktException(20001, "获取签名失败");
        }

    }

}
