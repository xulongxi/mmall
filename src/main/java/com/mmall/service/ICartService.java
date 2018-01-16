package com.mmall.service;

import com.mmall.common.ServerResponse;
import com.mmall.vo.CartVo;

import javax.servlet.http.HttpSession;

/**
 * 项目名：   mmall
 * 包名：     com.mmall.service
 * 文件名：   ICartService
 * 创建时间： 2017/11/26 8:39
 *
 * @author Longxi XU
 * 描述：     TODO
 */
public interface ICartService {
    ServerResponse add(Integer userId, Integer count, Integer productId);

    ServerResponse update(Integer userId, Integer count, Integer productId);

    ServerResponse<CartVo> deleteProduct(Integer userId, String productIds);

    ServerResponse<CartVo> list(Integer userId);

    ServerResponse<CartVo> selectOrUnSelect(Integer userId, Integer productId, Integer checked);

    ServerResponse<Integer> getCartProductCount(Integer userId);
}
