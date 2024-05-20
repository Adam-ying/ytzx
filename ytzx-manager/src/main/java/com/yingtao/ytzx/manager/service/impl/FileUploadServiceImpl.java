package com.yingtao.ytzx.manager.service.impl;

import cn.hutool.core.date.DateUtil;
import com.yingtao.ytzx.manager.properties.MinioProperties;
import com.yingtao.ytzx.manager.service.FileUploadService;
import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.UUID;

/**
 * @author Adam
 * @create 2024-04-16 19:11
 */
@Service
public class FileUploadServiceImpl implements FileUploadService {

    @Autowired
    private MinioProperties minioProperties;

    @Override
    public String fileUpload(MultipartFile multipartFile) {
        try{
            //创建minio客户端对象
            MinioClient minioClient = MinioClient.builder().endpoint(minioProperties.getEndpointUrl()).
                    credentials(minioProperties.getAccessKey(), minioProperties.getSecretKey()).
                    build();
            //判断桶是否存在
            boolean bucketExists = minioClient.bucketExists(BucketExistsArgs.
                    builder().bucket(minioProperties.getBucketName()).build());
            //不存在 创建新桶
            if(!bucketExists){
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(minioProperties.getBucketName()).build());
            }else{
                //存在 打印信息
                System.out.println("Bucket 'ytzx-bucket' already exists.");
            }

            //创建文件名称
            String time = DateUtil.format(new Date(), "yyyyMMdd");
            String uuid = UUID.randomUUID().toString().replace("-", "");
            String filename = time + "/" + uuid + multipartFile.getOriginalFilename();
            System.out.println("filename = " + filename);

            //将文件存入到minio对象中
            PutObjectArgs putObjectArgs = PutObjectArgs.builder().bucket(minioProperties.getBucketName())
                    .stream(multipartFile.getInputStream(), multipartFile.getSize(), -1)
                    .object(filename).build();
            minioClient.putObject(putObjectArgs);

            return minioProperties.getEndpointUrl() + "/" + minioProperties.getBucketName() + "/" + filename;

        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}
