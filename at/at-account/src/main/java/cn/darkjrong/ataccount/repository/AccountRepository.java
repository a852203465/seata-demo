package cn.darkjrong.ataccount.repository;

import cn.darkjrong.ataccount.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    /**
     * 扣减账户余额
     * @param userId 用户id
     * @param payAmount 金额
     */
    @Transactional
    @Modifying
    @Query(value = "UPDATE account SET balance = balance - ?2,used = used + ?2 where user_id = ?1", nativeQuery = true)
    void decrease(Long userId, BigDecimal payAmount);








}
