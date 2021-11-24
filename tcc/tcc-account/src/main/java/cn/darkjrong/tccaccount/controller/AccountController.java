package cn.darkjrong.tccaccount.controller;

import cn.darkjrong.tccaccount.service.AccountService;
import io.seata.rm.tcc.api.BusinessActionContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.math.BigDecimal;

@Slf4j
@RestController
@RequestMapping("account")
public class AccountController {

    @Resource
    private AccountService accountService;

    /**
     * 扣减账户余额
     * actionContext save xid
     * @param userId 用户id
     * @param money 金额
     * @return
     */
    @RequestMapping("decrease")
    public boolean prepare(@RequestBody BusinessActionContext actionContext, @RequestParam("userId") Long userId, @RequestParam("money") BigDecimal money){
        return accountService.decrease(actionContext.getXid(), userId,money);
    }

    @RequestMapping("commit")
    public boolean commit(@RequestBody BusinessActionContext actionContext){
        try {
            return accountService.commit(actionContext.getXid());
        }catch (IllegalStateException e){
            log.error("commit error:", e);
            return true;
        }
    }

    @RequestMapping("rollback")
    public boolean rollback(@RequestBody BusinessActionContext actionContext){
        try {
            return accountService.rollback(actionContext.getXid());
        }catch (IllegalStateException e){
            log.error("rollback error:", e);
            return true;
        }
    }
}
