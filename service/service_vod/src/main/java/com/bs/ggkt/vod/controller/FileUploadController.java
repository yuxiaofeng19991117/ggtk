package com.bs.ggkt.vod.controller;

import com.bs.ggkt.result.Result;
import com.bs.ggkt.vod.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/admin/vod/file")
@CrossOrigin
public class FileUploadController {

    @Autowired
    private FileService fileService;

    /**
     * 将文件上传到腾讯云并且返回url
     * @param file          上传的文件
     * @return              返回的url
     */
    @PostMapping("upload")
    public Result uploadFile(MultipartFile file) {

        String url = fileService.upload(file);

        return Result.ok(url).message("上传文件成功");
    }

}
