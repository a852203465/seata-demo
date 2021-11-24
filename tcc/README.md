# Spring Cloud 整合Seata 实现 TCC事务模式

## 使用方式
### 1. 创建数据库
 - tcc-account
 - tcc-order
 - tcc-storage

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
    修改OrderServiceImpl中create方法，最后一行代码改为如下：
```java
    throw new RuntimeException("调用2阶段提交的rollback方法");
    //return true;
```

## 总结
    分布式事务的TCC模式和AT模式的本质区别是一个是2阶段提交，一个是交易补偿。
    seata框架对AT模式的支持是非常方便的，但是对TCC模式的支持，最大的就是自动触发commit和prepare方法，
    真正的实现还是需要开发人员自己做。














