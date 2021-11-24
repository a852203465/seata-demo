package cn.darkjrong.tccorder.service.impl;

import cn.darkjrong.tccorder.entity.Order;
import cn.darkjrong.tccorder.feign.AccountApi;
import cn.darkjrong.tccorder.feign.StorageApi;
import cn.darkjrong.tccorder.repository.OrderRepository;
import cn.darkjrong.tccorder.service.OrderApi;
import cn.darkjrong.tccorder.service.OrderService;
import io.seata.core.context.RootContext;
import io.seata.rm.tcc.api.BusinessActionContext;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;

@Slf4j
@Service
public class OrderServiceImpl implements OrderService {

    @Resource
    private OrderRepository orderRepository;
    @Resource
    private StorageApi storageApi;
    @Resource
    private OrderApi orderSave;
    @Resource
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
    @GlobalTransactional
    public boolean create(Order order) {
        String xid = RootContext.getXID();
        log.info("------->交易开始");
        BusinessActionContext actionContext = new BusinessActionContext();
        actionContext.setXid(xid);
        boolean result = orderSave.saveOrder(actionContext, order);
        if(!result){
            throw new RuntimeException("保存订单失败");
        }
        //远程方法 扣减库存
        log.info("------->扣减库存开始storage中");
        result = storageApi.decrease(actionContext, order.getProductId(), order.getCount());
        if(!result){
            throw new RuntimeException("扣减库存失败");
        }
        log.info("------->扣减库存结束storage中");
        //远程方法 扣减账户余额
        log.info("------->扣减账户开始account中");
        result = accountApi.prepare(actionContext, order.getUserId(),order.getPayAmount());
        log.info("------->扣减账户结束account中" + result);
        log.info("------->交易结束");
//        throw new RuntimeException("调用2阶段提交的rollback方法");
        return true;
    }

    /**
     * 修改订单状态
     */
    @Override
    public void update(Long userId,BigDecimal payAmount,Integer status) {
        log.info("修改订单状态，入参为：userId={},payAmount={},status={}",userId,payAmount,status);
        orderRepository.updateOrder(userId,payAmount,status);
    }
}
