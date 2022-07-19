package com.jiang.mall.controller;

import com.jiang.mall.common.ApiRestResponse;
import com.jiang.mall.exception.MallExceptionEunm;
import com.jiang.mall.filter.UserFilter;
import com.jiang.mall.model.vo.CartVO;
import com.jiang.mall.service.CartService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 购物车Controller
 */
@RestController       //  @ResponseBody + @Controller = @RestController
@RequestMapping("/cart")
public class CartController {
    @Autowired
    CartService cartService;
    @GetMapping("/list")
    @ApiOperation("购物车列表")
    public ApiRestResponse list() {
        // 内部获取用户ID，防止横向越权
        List<CartVO> cartVOList = cartService.list(UserFilter.currentUser.getId());
        return ApiRestResponse.success(cartVOList);
    }



    @PostMapping("/add")
    @ApiOperation("购物车添加商品")
    public ApiRestResponse add(@RequestParam Integer productId, @RequestParam Integer count) {
     List<CartVO> cartVOList = cartService.add(UserFilter.currentUser.getId(), productId, count);
        return ApiRestResponse.success(cartVOList);
    }

    @PostMapping("/update")
    @ApiOperation("更新购物车")
    public ApiRestResponse update(@RequestParam Integer productId, @RequestParam Integer count) {
        List<CartVO> cartVOList = cartService.update(UserFilter.currentUser.getId(), productId, count);
        return ApiRestResponse.success(cartVOList);
    }

    @PostMapping("/delete")
    @ApiOperation("删除购物车中的商品")
    public ApiRestResponse delete(@RequestParam Integer productId) {
        // 不能传入UserId , cartID, 否则可以删除别人的购物车
        List<CartVO> cartVOList = cartService.delete(UserFilter.currentUser.getId(), productId);

        return ApiRestResponse.success(cartVOList);
    }

    @PostMapping("/select")
    @ApiOperation("单选反选")
    public ApiRestResponse select(@RequestParam Integer productId,@RequestParam Integer selected) {
        // 不能传入UserId , cartID, 否则可以删除别人的购物车
        List<CartVO> cartVOList = cartService.selectOrNot(UserFilter.currentUser.getId(), productId,selected);
        return ApiRestResponse.success(cartVOList);
    }


    @PostMapping("/selectAll")
    @ApiOperation("全选/反选")
    public ApiRestResponse selectAll(@RequestParam Integer productId,@RequestParam Integer selected) {
        // 不能传入UserId , cartID, 否则可以删除别人的购物车
        List<CartVO> cartVOList = cartService.selectAllOrNot(UserFilter.currentUser.getId(), productId,selected);
        return ApiRestResponse.success(cartVOList);
    }

}
