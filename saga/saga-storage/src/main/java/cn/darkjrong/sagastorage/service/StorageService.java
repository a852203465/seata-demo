package cn.darkjrong.sagastorage.service;

public interface StorageService {

    /**
     * 扣减库存
     * @param productId 产品id
     * @param count 数量
     */
    boolean decrease(Long productId, Integer count);

    /**
     * 回滚扣减库存
     * @param productId 产品id
     * @param count 数量
     */
    boolean compensateDecrease(Long productId, Integer count);
}
