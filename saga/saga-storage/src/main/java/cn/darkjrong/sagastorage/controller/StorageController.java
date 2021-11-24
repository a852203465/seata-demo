package cn.darkjrong.sagastorage.controller;

import cn.darkjrong.sagastorage.service.StorageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("storage")
public class StorageController {

    @Autowired
    private StorageService storageService;

    /**
     * 扣减库存
     * @param businessKey businessKey
     * @param productId 产品id
     * @param count 数量
     * @return 扣减库存结果
     */
    @RequestMapping("decrease")
    public boolean decrease(@RequestParam("businessKey") String businessKey, @RequestParam("productId") Long productId, @RequestParam("count") Integer count){
        log.info("businessKey:{}", businessKey);
        return storageService.decrease(productId, count);
    }

    /**
     * 补偿扣减库存
     * @param businessKey businessKey
     * @param productId 产品id
     * @param count 数量
     * @return 补偿结果
     */
    @RequestMapping("compensateDecrease")
    public boolean compensateDecrease(@RequestParam("businessKey") String businessKey, @RequestParam("productId") Long productId, @RequestParam("count") Integer count){
        log.info("businessKey:{}", businessKey);
        return storageService.compensateDecrease(productId, count);
    }
}
