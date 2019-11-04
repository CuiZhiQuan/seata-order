package com.seata.order.service.impl;

import com.seata.order.feign.AccountApi;
import com.seata.order.feign.StorageApi;
import com.seata.order.mapper.OrderMapper;
import com.seata.order.po.Order;
import com.seata.order.service.OrderService;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * @author cuizhiquan
 * @Description
 * @date 2019/11/1 21:11
 * @Copyright (c) 2017, DaChen All Rights Reserved.
 */
@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private StorageApi storageApi;
    @Autowired
    private AccountApi accountApi;

    @Override
    @GlobalTransactional(name = "th-create-order",rollbackFor = Exception.class)
    public void create(Order order) {
        log.info("------->交易开始");
        //本地方法
        orderMapper.create(order);

        //远程方法 扣减库存
        storageApi.decrease(order.getProductId(),order.getCount());

        //远程方法 扣减账户余额

        log.info("------->扣减账户开始order中");
        accountApi.decrease(order.getUserId(),order.getMoney());
        log.info("------->扣减账户结束order中");

        log.info("------->交易结束");
    }

    @Override
    public void update(Long userId, BigDecimal money, Integer status) {
        log.info("修改订单状态，入参为：userId={},money={},status={}",userId,money,status);
        orderMapper.update(userId,money,status);
    }
}
