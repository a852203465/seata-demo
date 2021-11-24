package cn.darkjrong.tccorder.repository;

import cn.darkjrong.tccorder.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    @Transactional
    @Modifying
    @Query(value = "UPDATE orders SET pay_amount = pay_amount - ?2,status = 1\n" +
            "    where user_id = ?1 and status = ?3", nativeQuery = true)
    void updateOrder(Long userId, BigDecimal payAmount, Integer status);





}
