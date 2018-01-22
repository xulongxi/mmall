package com.mmall.service.impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.mmall.common.ServerResponse;
import com.mmall.dao.CategoryMapper;
import com.mmall.pojo.Category;
import com.mmall.service.ICategoryService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;

/**
 * 项目名：   mmall
 * 包名：     com.mmall.service
 * 文件名：   ICategoryServiceImpl
 * 创建时间： 2017/11/19 20:21 * @author   Longxi XU
 * 描述：     TODO
 */
@Slf4j
@Service("iCategoryService")
public class CategoryServiceImpl implements ICategoryService {


//    private Logger logger = LoggerFactory.getLogger(CategoryServiceImpl.class);
    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public ServerResponse<String> addCategory(String categoryName, Integer parentId) {
        if (parentId == null || StringUtils.isBlank(categoryName)) {
            return ServerResponse.createByErrorMessage("添加品类参数错误");
        }
        Category category = new Category();
        category.setName(categoryName);
        category.setParentId(parentId);
        //这个分类是可用的
        category.setStatus(true);
        int rowCount = categoryMapper.insert(category);
        if (rowCount > 0) {
            return ServerResponse.createBySuccess("添加目录成功");
        }
        return ServerResponse.createByErrorMessage("添加目录失败");
    }

    @Override
    public ServerResponse<String> setCategoryName(Integer categoryId, String categoryName) {
        if (categoryId == null || StringUtils.isBlank(categoryName)) {
            return ServerResponse.createByErrorMessage("更新品类参数错误");
        }
        Category category = new Category();
        category.setName(categoryName);
        category.setId(categoryId);
        int rowCount = categoryMapper.updateByPrimaryKeySelective(category);
        if (rowCount > 0) {
            return ServerResponse.createBySuccess("更新目录成功");
        }
        return ServerResponse.createByErrorMessage("更新目录失败");
    }

    @Override
    public ServerResponse<List<Category>> getChildrenParallelCategory(Integer categoryId) {
        List<Category> categories = categoryMapper.selectChildrenParallelCategory(categoryId);
        if (CollectionUtils.isEmpty(categories)) {
            log.info("未找到当前分类的子分类");
        }
        return ServerResponse.createBySuccess(categories);
    }

    /**
     * 递归查询子节点的ID
     *
     * @param categoryId
     * @return
     */
    @Override
    public ServerResponse<List<Integer>> selectCategoryAndChildrenById(Integer categoryId) {
        Set<Category> categorySet = Sets.newHashSet();
        findChildrenCategory(categorySet, categoryId);
        List<Integer> categoryIdList = Lists.newArrayList();
        for (Category category : categorySet) {
            categoryIdList.add(category.getId());
        }
        return ServerResponse.createBySuccess(categoryIdList);
    }


    public Set<Category> findChildrenCategory(Set<Category> categorySet, Integer categoryId) {
        Category category = categoryMapper.selectByPrimaryKey(categoryId);
        if (category != null) {
            categorySet.add(category);
        }
        //查找子节点，递归算法要有退出条件;
        // 在MyBatis的Mapper里，如果查不到数据，会直接不返回，而不是返回null，所以不用判空
        List<Category> categoryList = categoryMapper.selectChildrenParallelCategory(categoryId);
        for (Category categoryItem : categoryList) {
            findChildrenCategory(categorySet, categoryItem.getId());
        }
        return categorySet;
    }


}
