package com.mmall.controller.backend;

import com.mmall.common.ResponseCode;
import com.mmall.common.ServerResponse;
import com.mmall.pojo.User;
import com.mmall.service.ICategoryService;
import com.mmall.service.IUserService;
import com.mmall.util.CookieUtil;
import com.mmall.util.JsonUtil;
import com.mmall.util.RedisPoolUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * 项目名：   mmall
 * 包名：     com.mmall.controller.backend
 * 文件名：   CategoryManageController
 * 创建时间： 2017/11/19 20:02 * @author   Longxi XU
 * 描述：     管理分类
 */
@Controller
@RequestMapping("/manage/category")
public class CategoryManageController {


    @Autowired
    private IUserService iUserService;

    @Autowired
    private ICategoryService iCategoryService;

    @RequestMapping(value = "add_category.do")
    @ResponseBody
    public ServerResponse addCategory(HttpServletRequest httpServletRequest, String categoryName,
                                      @RequestParam(value = "parentId", defaultValue = "0") Integer parentId) {
        String loginToken = CookieUtil.readLoginToken(httpServletRequest);
        if (StringUtils.isEmpty(loginToken)) {
            return ServerResponse.createByErrorMessage("用户未登录，无法获取当前用户的信息");
        }
        String userJsonStr = RedisPoolUtil.get(loginToken);
        User user = JsonUtil.string2Obj(userJsonStr, User.class);
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "用户未登录，请登录");
        }
        if (iUserService.checkAdminRole(user).isSuccess()) {
            //是管理员
            //实现加分类的逻辑
            return iCategoryService.addCategory(categoryName, parentId);
        }
        return ServerResponse.createByErrorMessage("不是管理员，无权操作");
    }

    @RequestMapping(value = "set_category_name.do")
    @ResponseBody
    public ServerResponse setCategoryName(HttpServletRequest httpServletRequest, String categoryName, Integer categoryId) {
        String loginToken = CookieUtil.readLoginToken(httpServletRequest);
        if (StringUtils.isEmpty(loginToken)) {
            return ServerResponse.createByErrorMessage("用户未登录，无法获取当前用户的信息");
        }
        String userJsonStr = RedisPoolUtil.get(loginToken);
        User user = JsonUtil.string2Obj(userJsonStr, User.class);
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "用户未登录，请登录");
        }
        if (iUserService.checkAdminRole(user).isSuccess()) {
            //是管理员
            //更新分类名称
            return iCategoryService.setCategoryName(categoryId, categoryName);
        }
        return ServerResponse.createByErrorMessage("不是管理员，无权操作");
    }

    @RequestMapping(value = "get_category.do")
    @ResponseBody
    public ServerResponse getChildrenParallelCategory(HttpServletRequest httpServletRequest, @RequestParam(value = "categoryId", defaultValue = "0") Integer categoryId) {
        String loginToken = CookieUtil.readLoginToken(httpServletRequest);
        if (StringUtils.isEmpty(loginToken)) {
            return ServerResponse.createByErrorMessage("用户未登录，无法获取当前用户的信息");
        }
        String userJsonStr = RedisPoolUtil.get(loginToken);
        User user = JsonUtil.string2Obj(userJsonStr, User.class);
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "用户未登录，请登录");
        }
        if (iUserService.checkAdminRole(user).isSuccess()) {
            //是管理员
            //查询子节点，不递归，保持平级
            return iCategoryService.getChildrenParallelCategory(categoryId);
        }
        return ServerResponse.createByErrorMessage("不是管理员，无权操作");
    }

    @RequestMapping(value = "get_deep_category.do")
    @ResponseBody
    public ServerResponse getCategoryAndDeepChildrenCategory(HttpServletRequest httpServletRequest, Integer categoryId) {
        String loginToken = CookieUtil.readLoginToken(httpServletRequest);
        if (StringUtils.isEmpty(loginToken)) {
            return ServerResponse.createByErrorMessage("用户未登录，无法获取当前用户的信息");
        }
        String userJsonStr = RedisPoolUtil.get(loginToken);
        User user = JsonUtil.string2Obj(userJsonStr, User.class);
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "用户未登录，请登录");
        }
        if (iUserService.checkAdminRole(user).isSuccess()) {
            //是管理员
            //查询子节点，递归包括所有子节点
            return iCategoryService.selectCategoryAndChildrenById(categoryId);
        }
        return ServerResponse.createByErrorMessage("不是管理员，无权操作");
    }

}
