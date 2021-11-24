# Spring Cloud 整合Seata 实现 AT事务模式

## 使用方式
### 1. 创建数据库
- at-account
- at-order
- at-storage

### 2. 初始化SQL
- 对三个数据库分别运行 sqls目录中的三个SQL文件

### 3. 模拟测试
    开始进行测试，有2个场景，正常commit和场景和异常rollback场景。
#### 3.1 模拟commit事务
    服务启动后，我们用postman发一个post请求，请求url:http://localhost:9001/order/create,请求参数：
```json
{
  "userId":1,
  "productId":1,
  "count":1,
  "money":1,
  "payAmount":50
}
```    

#### 3.2 模拟rollback事务
    AccountServiceImpl类里面注释掉的代码放开就可以模拟超时事务回滚。
    把下面的代码模拟超时异常这段放开，用debug模式进行，继续发送前面一样的请求，可以看到at-order库中的undo_log
```java
public void decrease(Long userId, BigDecimal payAmount) {
        log.info("------->扣减账户开始account中");
        //模拟超时异常，全局事务回滚
//        try {
//            Thread.sleep(30*1000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        accountRepository.decrease(userId,payAmount);
        log.info("------->扣减账户结束account中");

        //修改订单状态，此调用会导致调用成环
        log.info("修改订单状态开始");
        String mes = orderApi.update(userId, payAmount.multiply(new BigDecimal("0.09")),0);
        log.info("修改订单状态结束：{}",mes);
}
```

## 总结
    本质都是基于undo_log的交易补偿，这也是AT模式的特点。
    但是本文的环境比上节复杂很多，这并不是seata本身造成的，
    而是微服务的拆分带来的系统架构的复杂性。
















