package cn.darkjrong.sagaaccount.service;

import java.math.BigDecimal;

public interface AccountService {

    /**
     * 扣减账户余额
     * @param userId 用户id
     * @param money 金额
     * @return prepare是否成功
     */
    boolean decrease(Long userId, BigDecimal money);

    /**
     * Commit boolean.
     *
     * @param userId 用户id
     * @param money 金额
     * @return the boolean
     */
    boolean compensateDecrease(Long userId, BigDecimal money);
}
