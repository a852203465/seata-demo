package cn.darkjrong.sagaorder.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Component
@FeignClient(value = "storage-service")
public interface StorageApi {

    /**
     * 扣减库存
     * @param businessKey businessKey
     * @param productId productId
     * @param count count
     */
    @GetMapping(value = "/storage/decrease")
    boolean decrease(@RequestParam("businessKey") String businessKey, @RequestParam("productId") Long productId, @RequestParam("count") Integer count);

    /**
     * 补偿扣减库存
     * @param businessKey businessKey
     * @param productId productId
     * @param count count
     */
    @GetMapping(value = "/storage/compensateDecrease")
    boolean compensateDecrease(@RequestParam("businessKey") String businessKey, @RequestParam("productId") Long productId, @RequestParam("count") Integer count);
}
