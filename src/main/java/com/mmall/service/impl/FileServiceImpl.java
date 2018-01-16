package com.mmall.service.impl;

import com.google.common.collect.Lists;
import com.mmall.service.IFileService;
import com.mmall.util.FTPUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * 项目名：   mmall
 * 包名：     com.mmall.service
 * 文件名：   FileServiceImpl
 * 创建时间： 2017/11/21 1:47 * @author   Longxi XU
 * 描述：     TODO
 */
@Service("iFileService")
public class FileServiceImpl implements IFileService {

    private static Logger logger = LoggerFactory.getLogger(FileServiceImpl.class);

    /**
     * 返回上传后的修改后的文件名
     *
     * @param file
     * @param path
     * @return
     */
    @Override
    public String upload(MultipartFile file, String path) {
        String fileName = file.getOriginalFilename();
        //扩展名
        String fileExtensionName = fileName.substring(fileName.lastIndexOf('.') + 1);
        //防止两个不同用户上传同名文件，需要对文件重命名
        String uploadFileName = UUID.randomUUID().toString() + "." + fileExtensionName;
        logger.info("开始上传文件，上传文件的原的文件名：{}，上传的路径：{}，新文件名：{}", fileName, path, uploadFileName);
        File fileDir = new File(path);
        if (!fileDir.exists()) {
            fileDir.setWritable(true);
            fileDir.mkdirs();
        }
        File targetFile = new File(path, uploadFileName);
        try {
            file.transferTo(targetFile);
            //文件已经上传成功
            //todo 把targetFile上传到我们的FTP服务器
            FTPUtil.uploadFile(Lists.newArrayList(targetFile));
            //todo 然后删除upload下面的文件
            targetFile.delete();
        } catch (IOException e) {
            logger.error("上传文件异常", e);
            return null;
        }
        return targetFile.getName();
    }
}
