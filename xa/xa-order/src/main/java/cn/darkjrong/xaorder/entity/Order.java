package cn.darkjrong.xaorder.entity;


import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;

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

    /**
     * 订单状态：0：创建中；1：已完结
     */
    private Integer status;

}
