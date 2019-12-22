package com.seata.order.controller;

import com.seata.order.po.Order;
import com.seata.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

/**
 * @author cuizhiquan
 * @Description
 * @date 2019/11/1 21:08
 * @Copyright (c) 2017, DaChen All Rights Reserved.
 */
@RestController
@RequestMapping(value = "/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    /**
     * 创建订单
     * @param order
     * @return
     */
    @GetMapping("create")
    public String create(Order order){
        order = new Order();
        order.setUserId(1L);
        order.setProductId(1L);
        order.setCount(1);
        order.setMoney(new BigDecimal(100));
        order.setStatus(0);
        orderService.create(order);
        return "Create order success";
    }

    /**
     * 修改订单状态
     * @param userId
     * @param money
     * @param status
     * @return
     */
    @RequestMapping("update")
    String update(@RequestParam("userId") Long userId, @RequestParam("money") BigDecimal money, @RequestParam("status") Integer status){
        orderService.update(userId,money,status);
        return "订单状态修改成功";
    }
}
