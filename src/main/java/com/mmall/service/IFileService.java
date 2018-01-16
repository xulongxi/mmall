package com.mmall.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * 项目名：   mmall
 * 包名：     com.mmall.service
 * 文件名：   IFileService
 * 创建时间： 2017/11/21 1:47 * @author   Longxi XU
 * 描述：     TODO
 */
public interface IFileService {
    String upload(MultipartFile file, String path);
}
