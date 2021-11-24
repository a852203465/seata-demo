package cn.darkjrong.tccorder.service;

import cn.darkjrong.tccorder.entity.Order;

import java.math.BigDecimal;

public interface OrderService {

    /**
     * 创建订单
     * @param order
     * @return
     */
    boolean create(Order order);

    /**
     * 修改订单状态
     * @param userId
     * @param money
     * @param status
     */
    void update(Long userId,BigDecimal money,Integer status);
}
