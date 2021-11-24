package cn.darkjrong.atstorage.entity;

import lombok.Data;

import javax.persistence.*;

/**
 * 库存
 *
 * @author Rong.Jia
 * @date 2021/11/24
 */
@Table(name = "storage")
@Entity
@Data
public class Storage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 产品id
     */
    private Long productId;

    /**
     * 总库存
     */
    private Integer total;

    /**
     * 已用库存
     */
    private Integer used;

    /**
     * 剩余库存
     */
    private Integer residue;
}
