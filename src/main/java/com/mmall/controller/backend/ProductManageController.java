package com.mmall.controller.backend;

import com.google.common.collect.Maps;
import com.mmall.common.Const;
import com.mmall.common.ResponseCode;
import com.mmall.common.ServerResponse;
import com.mmall.pojo.Product;
import com.mmall.pojo.User;
import com.mmall.service.IFileService;
import com.mmall.service.IProductService;
import com.mmall.service.IUserService;
import com.mmall.util.PropertiesUtil;
import com.sun.deploy.net.HttpResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * 项目名：   mmall
 * 包名：     com.mmall.controller.backend
 * 文件名：   ProductManageController
 * 创建时间： 2017/11/20 14:29 * @author   Longxi XU
 * 描述：     管理商品
 */
@Controller
@RequestMapping("/manage/product")
public class ProductManageController {

    @Autowired
    IUserService iUserService;
    @Autowired
    IProductService iProductService;
    @Autowired
    IFileService iFileService;

    @RequestMapping(value = "save.do")
    @ResponseBody
    public ServerResponse productSave(HttpSession session, Product product) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "用户未登录");
        }
        if (iUserService.checkAdminRole(user).isSuccess()) {
            //填充业务逻辑
            return iProductService.saveOrUpdateProduct(product);
        }
        return ServerResponse.createByErrorMessage("不是管理员");
    }

    @RequestMapping(value = "set_sale_status.do")
    @ResponseBody
    public ServerResponse setSaleStatus(HttpSession session, Integer productId, Integer status) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "用户未登录");
        }
        if (iUserService.checkAdminRole(user).isSuccess()) {
            //填充业务逻辑
            return iProductService.setSaleStatus(productId, status);
        }
        return ServerResponse.createByErrorMessage("不是管理员");
    }

    @RequestMapping(value = "detail.do")
    @ResponseBody
    public ServerResponse detail(HttpSession session, Integer productId) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "用户未登录");
        }
        if (iUserService.checkAdminRole(user).isSuccess()) {
            //填充业务逻辑
            return iProductService.manageProductDetail(productId);
        }
        return ServerResponse.createByErrorMessage("不是管理员");
    }

    @RequestMapping(value = "list.do")
    @ResponseBody
    public ServerResponse getList(HttpSession session,
                                  @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                  @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "用户未登录");
        }
        if (iUserService.checkAdminRole(user).isSuccess()) {
            //填充业务逻辑
            return iProductService.getProductList(pageNum, pageSize);
        }
        return ServerResponse.createByErrorMessage("不是管理员");
    }

    @RequestMapping(value = "search.do")
    @ResponseBody
    public ServerResponse search(HttpSession session, String productName, Integer productId,
                                 @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                 @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "用户未登录");
        }
        if (iUserService.checkAdminRole(user).isSuccess()) {
            //填充业务逻辑
            return  iProductService.searchProductByNameOrId(productName, productId, pageNum, pageSize);
        }
        return ServerResponse.createByErrorMessage("不是管理员");
    }

    @RequestMapping(value = "upload.do")
    @ResponseBody
    public ServerResponse upload(@RequestParam(value = "upload_file", required = false) MultipartFile file, HttpSession session) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "用户未登录");
        }
        if (iUserService.checkAdminRole(user).isSuccess()) {
            //填充业务逻辑
            String path = session.getServletContext().getRealPath("upload");
            String targetFimeName = iFileService.upload(file, path);
            String url = PropertiesUtil.getProperty("ftp.server.http.prefix") + targetFimeName;
            Map fileMap = Maps.newHashMap();
            fileMap.put("uri", targetFimeName);
            fileMap.put("url", url);
            return ServerResponse.createBySuccess(fileMap);
        }
        return ServerResponse.createByErrorMessage("不是管理员");
    }

    @RequestMapping(value = "rich_text_img_upload.do")
    @ResponseBody
    public Map richTextImgUpload(@RequestParam(value = "upload_file", required = false) MultipartFile file,
                                 HttpSession session, HttpServletResponse httpServletResponse) {
        Map resultMap = Maps.newHashMap();
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            resultMap.put("success", false);
            resultMap.put("msg", "请登录管理员");
            return resultMap;
        }
        //富文本simditor中对于返回值有自己的要求
//        {
//            "file_path": "http://img.happymmall.com/5fb239f2-0007-40c1-b8e6-0dc11b22779c.jpg",
//                "msg": "上传成功",
//                "success": true
//        }

        if (iUserService.checkAdminRole(user).isSuccess()) {
            //填充业务逻辑
            String path = session.getServletContext().getRealPath("upload");
            String targetFimeName = iFileService.upload(file, path);
            if (StringUtils.isBlank(targetFimeName)) {
                resultMap.put("success", false);
                resultMap.put("msg", "上传失败");
            }
            String url = PropertiesUtil.getProperty("ftp.server.http.prefix") + targetFimeName;
            resultMap.put("success", true);
            resultMap.put("msg", "上传成功");
            resultMap.put("file_path", url);
            httpServletResponse.addHeader("Access-Control-Allow-Headers", "X-File-Name");
            return resultMap;
        }
        resultMap.put("success", false);
        resultMap.put("msg", "上传失败");
        return resultMap;
    }
}
