package com.peanut.fs.common.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * @description:
 * @author:Peanutfs
 * @date:created 2019/9/6
 */
@Slf4j
public class FileUploadUtil {

    public static String uploadFile(MultipartFile multipartFile, String savePath, String visitPath){
        log.info("[FileUploadUtil.uploadFile]上传文件开始savePath:{}, visitPath:{}", savePath, visitPath);
        File file = new File(savePath);
        if (!file.exists() || !file.isDirectory()){
            file.mkdirs();
        }
        String filename = multipartFile.getOriginalFilename();
        // 把文件的名称设置唯一值，uuid
        String uuid = UUID.randomUUID().toString().replace("-", "");
        filename = uuid + "_" + filename;
        try {
            multipartFile.transferTo(new File(savePath, filename));
        } catch (IOException e) {
            log.error("[FileUploadUtil.uploadFile]文件上传异常e:{}", e);
            return null;
        }
        return visitPath + filename;
    }
}
