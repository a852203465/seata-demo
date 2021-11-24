package cn.darkjrong.ataccount.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;

/**
 * 订单api
 *
 * @author Rong.Jia
 * @date 2021/11/24
 */
@Component
@FeignClient(value = "order-service")
public interface OrderApi {

    /**
     * 修改订单金额
     * @param userId
     * @param payAmount
     * @param status
     * @return 成功信息
     */
    @RequestMapping("/order/update")
    String update(@RequestParam("userId") Long userId, @RequestParam("payAmount") BigDecimal payAmount, @RequestParam("status") Integer status);
}
