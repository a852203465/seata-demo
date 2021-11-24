package cn.darkjrong.ataccount.entity;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Date;

/**
 * 账户
 *
 * @author Rong.Jia
 * @date 2021/11/24
 */
@Data
@Entity
@Table(name = "account")
public class Account {

    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 总额度
     */
    private BigDecimal total;

    /**
     * 已用额度
     */
    private BigDecimal used;

    /**
     * 剩余额度
     */
    private BigDecimal balance;
    /**
     * 更新时间
     */
    private Date lastUpdateTime;
}
