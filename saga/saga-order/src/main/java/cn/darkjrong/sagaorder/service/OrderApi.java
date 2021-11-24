package cn.darkjrong.sagaorder.service;

import cn.darkjrong.sagaorder.entity.Order;

public interface OrderApi {

    boolean saveOrder(String businessKey, Order order);

    /**
     * 失败补偿，删除订单
     * @param order order
     * @return
     */
    boolean deleteOrder(String businessKey, Order order);
}
