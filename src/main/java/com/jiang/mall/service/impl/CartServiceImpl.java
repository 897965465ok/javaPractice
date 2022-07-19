package com.jiang.mall.service.impl;

import com.jiang.mall.common.Constant;
import com.jiang.mall.exception.MallException;
import com.jiang.mall.exception.MallExceptionEunm;
import com.jiang.mall.model.dao.CartMapper;
import com.jiang.mall.model.dao.ProductMapper;
import com.jiang.mall.model.pojo.Cart;
import com.jiang.mall.model.pojo.Product;
import com.jiang.mall.model.vo.CartVO;
import com.jiang.mall.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * CartService 购物车Service层
 */
@Service
public class CartServiceImpl implements CartService {
    @Autowired
    ProductMapper productMapper;
    @Autowired
    CartMapper cartMapper;

    @Override
    public  List<CartVO> list(Integer userId){
        List<CartVO> cartVOS = cartMapper.selectList(userId);
        for (int i = 0; i < cartVOS.size(); i++) {
            CartVO cartVO =  cartVOS.get(i);
            cartVO.setTotalPrice(cartVO.getPrice()* cartVO.getQuantity());

        }
        return  cartVOS;

    }
    @Override
    public List<CartVO> add(Integer userId, Integer productId, Integer count) {
        validProduct(productId, count);
        Cart cart = cartMapper.selectCartByUserIdAndProductId(userId, productId);
        if (cart == null) {
            // 商品不存在购物车，需要新增记录
            cart = new Cart();
            cart.setProductId(productId);
            cart.setUserId(userId);
            cart.setQuantity(count);
            cart.setSelected(Constant.Cart.CHECKED);
            cartMapper.insertSelective(cart);
        } else {
            // 商品在购物车，数量增加
            count = cart.getQuantity() + count;
            Cart cartNew = new Cart();
            cartNew.setQuantity(count);
            cartNew.setId(cart.getId());
            cartNew.setProductId(cart.getProductId());
            cartNew.setUserId(cart.getUserId());
            cartNew.setSelected(Constant.Cart.CHECKED);
            cartMapper.updateByPrimaryKeySelective(cartNew);
        }

        return  this.list(userId);

    }

    private void validProduct(Integer productId, Integer count) {

        Product product = productMapper.selectByPrimaryKey(productId);
        // 判断是否存在是否上架
        if (product == null || product.getStatus().equals(Constant.SaleStatus.NOT_SALE)) {
            throw new MallException(MallExceptionEunm.NOT_SALE);
        }
        // 判断库存
        if (count > product.getStock()) {

            throw new MallException(MallExceptionEunm.NOT_ENOUGH);
        }


    }
    @Override
    public List<CartVO> update(Integer userId, Integer productId, Integer count) {

        validProduct(productId, count);

        Cart cart = cartMapper.selectCartByUserIdAndProductId(userId, productId);

        if (cart == null) {
            // 商品不存在购物车，无法更新
            throw  new MallException(MallExceptionEunm.UPDATE_FAILED);
        } else {
            // 商品在购物车，更新数量
            Cart cartNew = new Cart();
            cartNew.setQuantity(count);
            cartNew.setId(cart.getId());
            cartNew.setProductId(cart.getProductId());
            cartNew.setUserId(cart.getUserId());
            cartNew.setSelected(Constant.Cart.CHECKED);
            cartMapper.updateByPrimaryKeySelective(cartNew);
        }
        return  this.list(userId);

    }

    @Override
    public List<CartVO> delete(Integer userId, Integer productId) {

        Cart cart = cartMapper.selectCartByUserIdAndProductId(userId, productId);
        if (cart == null) {
            // 商品不存在购物车，无法删除
            throw  new MallException(MallExceptionEunm.DELETE_FAILED);
        } else {
            // 商品在购物车，更新数量
            cartMapper.deleteByPrimaryKey(cart.getId());
        }
        return  this.list(userId);

    }

    @Override
    public List<CartVO> selectOrNot(Integer userId, Integer productId, Integer selected) {
        Cart cart = cartMapper.selectCartByUserIdAndProductId(userId, productId);
        if (cart == null) {
            // 商品不存在购物车，无法选中
            throw  new MallException(MallExceptionEunm.UPDATE_FAILED);
        } else {
            // 商品在购物车，选中
            cartMapper.selectOrNot(userId,productId,selected);
        }
        return  this.list(userId);
    }

    @Override
    public List<CartVO> selectAllOrNot(Integer userId, Integer productId, Integer selected) {
            cartMapper.selectOrNot(userId,null,selected);
           return  this.list(userId);
    }

}
