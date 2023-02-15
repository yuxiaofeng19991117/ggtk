package com.bs.ggkt.vod.service.impl;

import com.bs.ggkt.exception.GgktException;
import com.bs.ggkt.vod.service.VodService;
import com.qcloud.vod.VodUploadClient;
import com.qcloud.vod.model.VodUploadRequest;
import com.qcloud.vod.model.VodUploadResponse;
import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.profile.ClientProfile;
import com.tencentcloudapi.common.profile.HttpProfile;
import com.tencentcloudapi.vod.v20180717.VodClient;
import com.tencentcloudapi.vod.v20180717.models.DeleteMediaRequest;
import com.tencentcloudapi.vod.v20180717.models.DeleteMediaResponse;
import org.springframework.stereotype.Service;

import java.io.InputStream;

import static com.bs.ggkt.vod.utils.ConstantPropertiesUtil.ACCESS_KEY_ID;
import static com.bs.ggkt.vod.utils.ConstantPropertiesUtil.ACCESS_KEY_SECRET;

@Service
public class VodServiceImpl implements VodService {

    @Override
    public String uploadVideo(InputStream fileInputStream) {
        VodUploadClient client = new VodUploadClient(ACCESS_KEY_ID, ACCESS_KEY_SECRET);
        VodUploadRequest request = new VodUploadRequest();

        //设置视频文件在本地路径
        request.setMediaFilePath("");

        request.setProcedure("LongVideoPreset");

        try {
            VodUploadResponse response = client.upload("ap-beijing", request);
            return response.getFileId();
        } catch (Exception e) {
            throw new GgktException(20001, "上传视频失败");
        }
    }

    @Override
    public void removeVideo(String fileId) {

        try {
            Credential credential = new Credential(ACCESS_KEY_ID, ACCESS_KEY_SECRET);

            HttpProfile httpProfile = new HttpProfile();
            httpProfile.setEndpoint("vod.tencentcloudapi.com");

            ClientProfile clientProfile = new ClientProfile();
            clientProfile.setHttpProfile(httpProfile);

            VodClient client = new VodClient(credential, "", clientProfile);

            DeleteMediaRequest req = new DeleteMediaRequest();
            req.setFileId(fileId);

            DeleteMediaResponse resp = client.DeleteMedia(req);

            System.out.println(DeleteMediaResponse.toJsonString(resp));
        } catch (Exception e) {
            e.printStackTrace();
            throw new GgktException(20001, "删除视频失败");
        }

    }
}
