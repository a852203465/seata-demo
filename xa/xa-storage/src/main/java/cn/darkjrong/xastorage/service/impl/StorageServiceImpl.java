package cn.darkjrong.xastorage.service.impl;

import cn.darkjrong.xastorage.repository.StorageRepository;
import cn.darkjrong.xastorage.service.StorageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class StorageServiceImpl implements StorageService {

    @Autowired
    private StorageRepository storageRepository;

    /**
     * 扣减库存
     * @param productId 产品id
     * @param count 数量
     */
    @Override
    public void decrease(Long productId, Integer count) {
        log.info("------->扣减库存开始");
        storageRepository.decrease(productId,count);
        log.info("------->扣减库存结束");
    }
}
