package cn.darkjrong.tccorder.controller;

import cn.darkjrong.tccorder.entity.Order;
import cn.darkjrong.tccorder.service.OrderService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.math.BigDecimal;

/**
 * @author jinjunzhu
 */
@RestController
@RequestMapping(value = "order")
public class OrderController {

    @Resource
    private OrderService orderServiceImpl;

    /**
     * 创建订单
     * @param order
     * @return
     */
    @PostMapping("create")
    public String create(@RequestBody Order order){
        orderServiceImpl.create(order);
        return "Create order success";
    }

    /**
     * 修改订单状态
     * @param userId
     * @param payAmount
     * @param status
     * @return
     */
    @RequestMapping("update")
    String update(@RequestParam("userId") Long userId, @RequestParam("payAmount") BigDecimal payAmount, @RequestParam("status") Integer status){
        orderServiceImpl.update(userId,payAmount,status);
        return "订单状态修改成功";
    }
}
