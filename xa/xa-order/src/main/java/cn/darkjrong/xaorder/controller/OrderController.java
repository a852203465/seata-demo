package cn.darkjrong.xaorder.controller;

import cn.darkjrong.xaorder.entity.Order;
import cn.darkjrong.xaorder.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@Slf4j
@RestController
@RequestMapping(value = "order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    /**
     * 创建订单
     * @param order
     * @return
     */
    @PostMapping("create")
    public String create(@RequestBody Order order){
        orderService.create(order);
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
        orderService.update(userId,payAmount,status);
        return "订单状态修改成功";
    }
}
