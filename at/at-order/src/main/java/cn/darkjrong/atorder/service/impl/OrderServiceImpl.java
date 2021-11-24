package cn.darkjrong.atorder.service.impl;

import cn.darkjrong.atorder.entity.Order;
import cn.darkjrong.atorder.feign.AccountApi;
import cn.darkjrong.atorder.feign.StorageApi;
import cn.darkjrong.atorder.repository.OrderRepository;
import cn.darkjrong.atorder.service.OrderService;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Slf4j
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private StorageApi storageApi;
    @Autowired
    private AccountApi accountApi;

    /**
     * 创建订单
     * @param order
     * @return
     * 测试结果：
     * 1.添加本地事务：仅仅扣减库存
     * 2.不添加本地事务：创建订单，扣减库存
     */
    @Override
    @GlobalTransactional(name = "fsp-create-order",rollbackFor = Exception.class)
    public void create(Order order) {
        log.info("------->交易开始");
        //本地方法
        orderRepository.save(order);

        //远程方法 扣减库存
        storageApi.decrease(order.getProductId(),order.getCount());

        //远程方法 扣减账户余额

        log.info("------->扣减账户开始order中");
        accountApi.decrease(order.getUserId(),order.getPayAmount());
        log.info("------->扣减账户结束order中");

        log.info("------->交易结束");
    }

    /**
     * 修改订单状态
     */
    @Override
    public void update(Long userId,BigDecimal payAmount,Integer status) {
        log.info("修改订单状态，入参为：userId={},payAmount={},status={}",userId,payAmount,status);
        orderRepository.update(userId,payAmount,status);
    }
}
