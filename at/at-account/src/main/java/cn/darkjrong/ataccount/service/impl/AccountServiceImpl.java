package cn.darkjrong.ataccount.service.impl;

import cn.darkjrong.ataccount.feign.OrderApi;
import cn.darkjrong.ataccount.repository.AccountRepository;
import cn.darkjrong.ataccount.service.AccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Slf4j
@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private OrderApi orderApi;

    /**
     * 扣减账户余额
     * @param userId 用户id
     * @param payAmount 金额
     */
    @Override
    public void decrease(Long userId, BigDecimal payAmount) {
        log.info("------->扣减账户开始account中");
        //模拟超时异常，全局事务回滚
//        try {
//            Thread.sleep(30*1000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        accountRepository.decrease(userId,payAmount);
        log.info("------->扣减账户结束account中");

        //修改订单状态，此调用会导致调用成环
        log.info("修改订单状态开始");
        String mes = orderApi.update(userId, payAmount.multiply(new BigDecimal("0.09")),0);
        log.info("修改订单状态结束：{}",mes);
    }
}
