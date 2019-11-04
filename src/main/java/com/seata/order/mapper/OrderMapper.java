package com.seata.order.mapper;

import com.seata.order.po.Order;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;

/**
 * @author cuizhiquan
 * @Description
 * @date 2019/11/1 21:12
 * @Copyright (c) 2017, DaChen All Rights Reserved.
 */
@Mapper
public interface OrderMapper {

    /**
     * 创建订单
     * @param order
     * @return
     */
    @Insert({
            "insert into `order` (`id`,`user_id`,`product_id`,`count`,`money`,`status`)",
            "values(NULL, #{userId}, #{productId}, #{count}, #{money},0)"
    })
    void create(Order order);

    /**
     * 修改订单金额
     * @param userId
     * @param money
     * @param status
     */
    void update(@Param("userId") Long userId, @Param("money") BigDecimal money, @Param("status") Integer status);
}
