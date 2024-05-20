package com.yingtao.ytzx.manager.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author Adam
 * @create 2024-04-16 19:10
 */
public interface FileUploadService {
    String fileUpload(MultipartFile multipartFile);
}
