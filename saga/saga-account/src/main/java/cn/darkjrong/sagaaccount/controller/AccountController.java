package cn.darkjrong.sagaaccount.controller;

import cn.darkjrong.sagaaccount.service.AccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@Slf4j
@RestController
@RequestMapping("account")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @RequestMapping("decrease")
    public boolean decrease(@RequestParam("businessKey") String businessKey, @RequestParam("userId") Long userId, @RequestParam("money") BigDecimal money){
        log.info("businessKey:{}", businessKey);
        return accountService.decrease(userId,money);
    }

    @RequestMapping("compensateDecrease")
    public boolean compensateDecrease(@RequestParam("businessKey") String businessKey, @RequestParam("userId") Long userId, @RequestParam("money") BigDecimal money){
        log.info("businessKey:{}", businessKey);
        return accountService.compensateDecrease(userId, money);
    }
}
