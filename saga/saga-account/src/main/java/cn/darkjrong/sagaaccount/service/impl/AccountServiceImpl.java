package cn.darkjrong.sagaaccount.service.impl;

import cn.darkjrong.sagaaccount.repository.AccountRepository;
import cn.darkjrong.sagaaccount.service.AccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Slf4j
@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public boolean decrease(Long userId, BigDecimal payAmount) {
        log.info("------->尝试扣减账户开始account, userId:{}, payAmount:{}", userId, payAmount);
        accountRepository.decrease(userId, payAmount);
        log.info("------->尝试扣减账户结束account");

        return true;

    }

    @Override
    public boolean compensateDecrease(Long userId, BigDecimal payAmount){
        log.info("------->尝试补偿扣减账户开始account, userId:{}, payAmount:{}", userId, payAmount);
        accountRepository.decrease(userId, payAmount.negate());
        log.info("------->尝试补偿扣减账户结束account");
        return true;
    }

}
