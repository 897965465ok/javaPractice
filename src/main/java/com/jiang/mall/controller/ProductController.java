package com.jiang.mall.controller;

import com.github.pagehelper.PageInfo;
import com.jiang.mall.common.ApiRestResponse;
import com.jiang.mall.model.pojo.Product;
import com.jiang.mall.model.request.ProductListReq;
import com.jiang.mall.service.ProductService;
import io.swagger.annotations.ApiOperation;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 *  前端商品描述
 */
@RestController
public class ProductController {
    @Autowired
    ProductService productService;
    @ApiOperation("前端商品描述")
    @GetMapping("product/detail")
    public ApiRestResponse detail(@Param("id") Integer id){
       Product product = productService.detail(id);
       return  ApiRestResponse.success(product);

    }
    @ApiOperation("前端商品详情")
    @GetMapping("product/list")
    public ApiRestResponse list(ProductListReq productListReq){
        PageInfo list = productService.list(productListReq);
        return  ApiRestResponse.success(list);

    }
}
