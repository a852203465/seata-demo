package cn.darkjrong.sagaorder.service.impl;

import cn.darkjrong.sagaorder.entity.Order;
import cn.darkjrong.sagaorder.service.OrderService;
import cn.hutool.extra.spring.SpringUtil;
import io.seata.saga.engine.StateMachineEngine;
import io.seata.saga.statelang.domain.ExecutionStatus;
import io.seata.saga.statelang.domain.StateMachineInstance;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class OrderServiceImpl implements OrderService {

    /**
     * 创建订单
     * @param order
     * @return
     * 测试结果：
     * 1.添加本地事务：仅仅扣减库存
     * 2.不添加本地事务：创建订单，扣减库存
     */
    @Override
    public boolean create(Order order) {
        log.info("------->交易开始");

        StateMachineEngine stateMachineEngine = SpringUtil.getBean(StateMachineEngine.class);

        Map<String, Object> startParams = new HashMap<>(3);
        String businessKey = String.valueOf(System.currentTimeMillis());
        startParams.put("businessKey", businessKey);
        startParams.put("order", order);
        startParams.put("mockReduceAccountFail", "true");
        startParams.put("userId", order.getUserId());
        startParams.put("money", order.getPayAmount());
        startParams.put("productId", order.getProductId());
        startParams.put("count", order.getCount());

        //sync test
        StateMachineInstance inst = stateMachineEngine.startWithBusinessKey("buyGoodsOnline", null, businessKey, startParams);

        Assert.isTrue(ExecutionStatus.SU.equals(inst.getStatus()), "saga transaction execute failed. XID: " + inst.getId());
        System.out.println("saga transaction commit succeed. XID: " + inst.getId());

        inst = stateMachineEngine.getStateMachineConfig().getStateLogStore().getStateMachineInstanceByBusinessKey(businessKey, null);
        Assert.isTrue(ExecutionStatus.SU.equals(inst.getStatus()), "saga transaction execute failed. XID: " + inst.getId());

        return true;
    }

    /**
     * 修改订单状态
     */
    @Override
    public void update(Long userId,BigDecimal payAmount,Integer status) {
        log.info("修改订单状态，入参为：userId={},payAmount={},status={}",userId,payAmount,status);
        //orderDao.update(userId,payAmount,status);
    }
}
