package cn.darkjrong.atorder.repository;

import cn.darkjrong.atorder.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    /**
     * 修改订单金额
     * @param userId
     * @param payAmount
     */
    @Transactional
    @Modifying
    @Query(value = "UPDATE orders SET pay_amount = pay_amount - ?2,status = 1\n" +
            "    where user_id = ?1 and status = ?3", nativeQuery = true)
    void update(Long userId, BigDecimal payAmount, Integer status);

}
