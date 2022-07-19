package com.jiang.mall.service;

import com.github.pagehelper.PageInfo;
import com.jiang.mall.model.request.CreateOrderReq;
import com.jiang.mall.model.vo.CartVO;
import com.jiang.mall.model.vo.OrderVO;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 订单Service
 */
@RestController
public interface OrderService {

    String create(CreateOrderReq createOrderReq);

    OrderVO detail(String orderNo);

    PageInfo listForCustomer(Integer pageNum, Integer pageSize);

    void cancel(String orderNo);

    String qrcode(String orderNo);

    PageInfo listForAdmin(Integer pageNum, Integer pageSize);

    void  pay(String orderNo);

    void deliver(String orderNo);

    void finish(String orderNo);
}
