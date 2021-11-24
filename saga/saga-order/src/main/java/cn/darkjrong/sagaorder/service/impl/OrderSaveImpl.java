package cn.darkjrong.sagaorder.service.impl;

import cn.darkjrong.sagaorder.entity.Order;
import cn.darkjrong.sagaorder.repository.OrderRepository;
import cn.darkjrong.sagaorder.service.OrderApi;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class OrderSaveImpl implements OrderApi {

    @Autowired
    private OrderRepository orderRepository;

    @Override
    public boolean saveOrder(String businessKey, Order order) {
        log.info("保存订单, businessKey：{}, order: {}", businessKey, order);
        orderRepository.save(order);
        return true;
    }

    /**
     * 回滚事务,删除订单
     * @param order order
     * @return
     */
    @Override
    public boolean deleteOrder(String businessKey,Order order){
        log.info("删除订单, businessKey：{}, order: {}", businessKey, order);
        orderRepository.delete(order);
        return true;
    }
}
