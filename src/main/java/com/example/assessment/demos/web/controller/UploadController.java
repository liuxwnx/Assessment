package com.example.assessment.demos.web.controller;


import com.example.assessment.demos.web.context.BaseContext;
import com.example.assessment.demos.web.properties.AliOssProperties;
import com.example.assessment.demos.web.utils.AliOssUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/file")
@Slf4j
public class UploadController {

    @Autowired
    private AliOssProperties properties;

    @PostMapping("/upload")
    public String upload(MultipartFile file) throws IOException {
        log.info("file接收的文件名: {}", file.getOriginalFilename());

        AliOssUtil aliOssUtil = new AliOssUtil(properties.getEndpoint(), properties.getAccessKeyId(), properties.getAccessKeySecret(), properties.getBucketName());
        //把addOrderDTO.getFile()转成byte[]
        return aliOssUtil.upload(file.getBytes(), file.getOriginalFilename());
    }
}
