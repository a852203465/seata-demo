package cn.darkjrong.xaaccount.service.impl;

import cn.darkjrong.xaaccount.repository.AccountRepository;
import cn.darkjrong.xaaccount.service.AccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Slf4j
@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountRepository accountRepository;

    /**
     * 扣减账户余额
     *
     * @param userId    用户id
     * @param payAmount 金额
     */
    @Override
    public void decrease(Long userId, BigDecimal payAmount) {
        log.info("------->扣减账户开始account中");

        //模拟超时异常，全局事务回滚
        /*try {
            Thread.sleep(30*1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/

        accountRepository.decrease(userId, payAmount);
        log.info("------->扣减账户结束account中");
    }
}
