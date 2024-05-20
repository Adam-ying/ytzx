package com.yingtao.ytzx.manager.controller;

import com.yingtao.ytzx.manager.service.FileUploadService;
import com.yingtao.ytzx.model.vo.common.Result;
import com.yingtao.ytzx.model.vo.common.ResultCodeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author Adam
 * @create 2024-04-16 19:09
 */
@RestController
@RequestMapping("/admin/system")
public class FileUploadController {

    @Autowired
    private FileUploadService fileUploadService;

    @PostMapping("/fileUpload")
    public Result fileUpload(@RequestParam(value = "file") MultipartFile multipartFile){
        String fileUrl = fileUploadService.fileUpload(multipartFile);
        return Result.build(fileUrl, ResultCodeEnum.SUCCESS);
    }
}
