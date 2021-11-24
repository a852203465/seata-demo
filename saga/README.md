# Spring Cloud 整合Seata 实现 SAGA事务模式

## 使用方式
### 1. 创建数据库
- saga-account
- saga-order
- saga-storage

### 2. 初始化SQL
- 对三个数据库分别运行 sqls目录中的三个SQL文件

### 3. 模拟测试
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
```java
public void decrease(Long userId, BigDecimal payAmount) {
        LOGGER.info("------->扣减账户开始account中");
        //模拟超时异常，全局事务回滚
    /*try {
        Thread.sleep(30*1000);
    } catch (InterruptedException e) {
        e.printStackTrace();
    }*/
        accountDao.decrease(userId,payAmount);
        LOGGER.info("------->扣减账户结束account中");
}
```

## 总结
    seata中xa模式的机制跟TCC相类似，都是2阶段提交，而代码实现跟AT模式很像，都是通过数据源代理来实现的。
    跟TCC模式相比，开发人员可以不用关注分支事务提交和回滚的代码编写，seata框架已经帮我们做了。

















