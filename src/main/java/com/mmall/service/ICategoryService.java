package com.mmall.service;

import com.mmall.common.ServerResponse;
import com.mmall.pojo.Category;

import java.util.List;
import java.util.Set;

/**
 * 项目名：   mmall
 * 包名：     com.mmall.service
 * 文件名：   ICategoryService
 * 创建时间： 2017/11/19 20:20 * @author   Longxi XU
 * 描述：     TODO
 */
public interface ICategoryService {

    ServerResponse<String> addCategory(String categoryName, Integer parentId);

    ServerResponse<String> setCategoryName(Integer categoryId, String categoryName);

    ServerResponse<List<Category>> getChildrenParallelCategory(Integer categoryId);

    ServerResponse<List<Integer>> selectCategoryAndChildrenById(Integer categoryId);
}
