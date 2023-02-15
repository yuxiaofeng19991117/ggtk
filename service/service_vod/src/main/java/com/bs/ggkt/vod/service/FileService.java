package com.bs.ggkt.vod.service;

import org.springframework.web.multipart.MultipartFile;

public interface FileService {

    /**
     * 将文件上传到腾讯云并且返回url
     * @param file          上传的文件
     * @return              返回的url
     */
    String upload(MultipartFile file);

}
