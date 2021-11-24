package cn.darkjrong.tccorder.entity;


import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * 订单
 *
 * @author Rong.Jia
 * @date 2021/11/23
 */
@Data
@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    private Long productId;

    private Integer count;

    private BigDecimal payAmount;

    /**订单状态：0：创建中；1：已完结*/
    private Integer status;

}
