package cn.darkjrong.sagaorder.service.impl;

import cn.darkjrong.sagaorder.feign.AccountApi;
import cn.darkjrong.sagaorder.service.AccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Slf4j
@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountApi accountApi;

    @Override
    public boolean decrease(String businessKey, Long userId, BigDecimal money) {
        return accountApi.decrease(businessKey, userId, money);
    }

    @Override
    public boolean compensateDecrease(String businessKey, Long userId, BigDecimal money) {
        return accountApi.compensateDecrease(businessKey, userId, money);
    }
}
