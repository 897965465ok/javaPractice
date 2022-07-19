package com.jiang.mall.controller;

import com.github.pagehelper.PageInfo;
import com.jiang.mall.common.ApiRestResponse;
import com.jiang.mall.model.request.CreateOrderReq;
import com.jiang.mall.model.vo.OrderVO;
import com.jiang.mall.service.OrderService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 *  订单类
 */
@RestController
public class OrderController {
    @Autowired
    OrderService orderService;
    @PostMapping("order/create")
    @ApiOperation("创建订单")
    public ApiRestResponse create(@RequestBody CreateOrderReq createOrderReq){
         String  orderNumber = orderService.create(createOrderReq);
         return ApiRestResponse.success(orderNumber) ;
    }

    @GetMapping("order/detail")
    @ApiOperation("前台订单详情")
    public  ApiRestResponse detail ( @RequestParam String orderNo){
        OrderVO orderVO = orderService.detail(orderNo);

        return ApiRestResponse.success(orderVO) ;
    }

    @GetMapping("order/list")
    @ApiOperation("前台订单列表")
    public  ApiRestResponse list (@RequestParam Integer pageNum, @RequestParam Integer pageSize){
        PageInfo pageInfo = orderService.listForCustomer(pageNum,pageSize);
        return ApiRestResponse.success(pageInfo) ;
    }


    @PostMapping("order/cancel")
    @ApiOperation("前台订单取消")
    public  ApiRestResponse cancel (@RequestParam String orderNo ){
        orderService.cancel(orderNo);
        return ApiRestResponse.success() ;

    }

    @GetMapping ("order/qrcode")
    @ApiOperation("生成支付二维码")
    public  ApiRestResponse qrcode (@RequestParam String orderNo ){
      String pngAddress =   orderService.qrcode(orderNo);
        return ApiRestResponse.success(pngAddress) ;

    }


    @GetMapping("pay")
    @ApiOperation("支付接口")
    public ApiRestResponse pay(@RequestParam String orderNo) {
        orderService.pay(orderNo);
        return ApiRestResponse.success();
    }

}
