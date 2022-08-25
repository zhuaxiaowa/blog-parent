package com.xuexilema.blog.controller;

import com.xuexilema.blog.utils.QiniuyunUtils;
import com.xuexilema.blog.vo.Result;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@RestController
@RequestMapping("/upload")
public class UploadController {

    @Autowired
    private QiniuyunUtils qiniuyunUtils;

    public Result upload(@RequestParam("image") MultipartFile file) {
        String fileName = UUID.randomUUID().toString() + "." + StringUtils.substringAfterLast(file.getOriginalFilename(), ".");
        boolean upload = qiniuyunUtils.upload(file, fileName);
        if (upload) {
            return Result.success(qiniuyunUtils.url + fileName);
        } else {
            return Result.error(20001, "上传失败");
        }
    }
}
