package cn.darkjrong.sagastorage.service.impl;

import cn.darkjrong.sagastorage.repository.StorageRepository;
import cn.darkjrong.sagastorage.service.StorageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Slf4j
@Service
public class StorageServiceImpl implements StorageService {

    @Resource
    private StorageRepository storageRepository;

    @Override
    public boolean decrease(Long productId, Integer count) {
        log.info("扣减库存, commit, productId:{}, count:{}", productId, count);
        storageRepository.decrease(productId, count);
        //throw new RuntimeException();
        return true;
    }

    @Override
    public boolean compensateDecrease(Long productId, Integer count) {
        log.info("补偿扣减库存, compensate, productId:{}, count:{}", productId, count);
        storageRepository.compensateDecrease(productId, count);
        return true;
    }
}
