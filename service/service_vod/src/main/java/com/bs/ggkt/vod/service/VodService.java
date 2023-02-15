package com.bs.ggkt.vod.service;

import java.io.InputStream;

public interface VodService {

    /**
     * 上传视频
     * @return          视频id
     */
    String uploadVideo(InputStream fileInputStream);

    /**
     * 删除视频
     * @param fileId     视频id
     */
    void removeVideo(String fileId);
}
