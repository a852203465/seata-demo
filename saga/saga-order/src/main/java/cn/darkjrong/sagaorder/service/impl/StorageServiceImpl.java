package cn.darkjrong.sagaorder.service.impl;

import cn.darkjrong.sagaorder.feign.StorageApi;
import cn.darkjrong.sagaorder.service.StorageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class StorageServiceImpl implements StorageService {

    @Autowired
    private StorageApi storageApi;

    @Override
    public boolean decrease(String businessKey, Long productId, Integer count) {
        return storageApi.decrease(businessKey, productId, count);
    }

    @Override
    public boolean compensateDecrease(String businessKey, Long productId, Integer count) {
        return storageApi.compensateDecrease(businessKey, productId, count);
    }
}
