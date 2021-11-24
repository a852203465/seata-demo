package cn.darkjrong.sagaorder.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;

@Component
@FeignClient(value = "account-service")
public interface AccountApi {

    /**
     * 扣减账户余额
     * @param businessKey
     * @param userId 用户id
     * @param money 金额
     */
    @RequestMapping("/account/decrease")
    boolean decrease(@RequestParam("businessKey") String businessKey, @RequestParam("userId") Long userId, @RequestParam("money") BigDecimal money);

    /**
     * 交易补偿，把扣减的账户金额加回去.
     *
     * @param businessKey
     * @param userId userId
     * @param money money
     * @return the boolean
     */
    @RequestMapping("/account/compensateDecrease")
    boolean compensateDecrease(@RequestParam("businessKey") String businessKey, @RequestParam("userId") Long userId, @RequestParam("money") BigDecimal money);

}
