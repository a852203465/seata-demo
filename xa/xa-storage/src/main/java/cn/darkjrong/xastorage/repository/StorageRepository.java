package cn.darkjrong.xastorage.repository;

import cn.darkjrong.xastorage.entity.Storage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface StorageRepository extends JpaRepository<Storage, Long> {

    /**
     * 扣减库存
     * @param productId 产品id
     * @param count 数量
     */
    @Transactional
    @Modifying
    @Query(value = "UPDATE storage SET used = used + ?2,residue = residue - ?2 WHERE product_id = ?1", nativeQuery = true)
    void decrease(Long productId, Integer count);



}
