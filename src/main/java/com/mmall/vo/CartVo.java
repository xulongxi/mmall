package com.mmall.vo;

import java.math.BigDecimal;
import java.util.List;

/**
 * 项目名：   mmall
 * 包名：     com.mmall.vo
 * 文件名：   CartVo
 * 创建时间： 2017/11/26 9:24
 *
 * @author Longxi XU
 * 描述：     这是包含了多个被添加到购物车里的总购物车
 */
public class CartVo {
    //被加到购物车里的商品列表
    private List<CartProductVo> cartProductVoList;
    //把每个放到购物车里的价格都加起来的总价，而不是针对某一个商品
    private BigDecimal cartTotalPrice;
    //是否全勾选
    private boolean allChecked;
    private String imageHost;

    public List<CartProductVo> getCartProductVoList() {
        return cartProductVoList;
    }

    public void setCartProductVoList(List<CartProductVo> cartProductVoList) {
        this.cartProductVoList = cartProductVoList;
    }

    public BigDecimal getCartTotalPrice() {
        return cartTotalPrice;
    }

    public void setCartTotalPrice(BigDecimal cartTotalPrice) {
        this.cartTotalPrice = cartTotalPrice;
    }

    public boolean isAllChecked() {
        return allChecked;
    }

    public void setAllChecked(boolean allChecked) {
        this.allChecked = allChecked;
    }

    public String getImageHost() {
        return imageHost;
    }

    public void setImageHost(String imageHost) {
        this.imageHost = imageHost;
    }
}
