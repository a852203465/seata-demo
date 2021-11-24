package cn.darkjrong.tccorder.service.impl;

import cn.darkjrong.tccorder.entity.Order;
import cn.darkjrong.tccorder.service.OrderApi;
import io.seata.rm.tcc.api.BusinessActionContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Service
public class OrderSaveImpl implements OrderApi {

    private Map<String, Statement> statementMap = new ConcurrentHashMap<>(100);
    private Map<String, Connection> connectionMap = new ConcurrentHashMap<>(100);

    @Resource
    private DataSource hikariDataSource;

    @Override
    public boolean saveOrder(BusinessActionContext actionContext, Order order) {
        try{
            Connection connection = hikariDataSource.getConnection();
            connection.setAutoCommit(false);
            String sql = "INSERT INTO `orders` (`id`,`user_id`,`product_id`,`count`,`pay_amount`,`status`) VALUES(NULL, ?, ?, ?, ?,0)";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setLong(1, order.getUserId());
            stmt.setLong(2, order.getProductId());
            stmt.setInt(3, order.getCount());
            stmt.setBigDecimal(4, order.getPayAmount());
            stmt.executeUpdate();
            statementMap.put(actionContext.getXid(), stmt);
            connectionMap.put(actionContext.getXid(), connection);
        } catch (SQLException e) {
            log.error("保存订单失败:", e);
            return false;
        }
        return true;
    }

    /**
     * 提交事务
     * @param actionContext save xid
     * @return
     */
    @Override
    public boolean commit(BusinessActionContext actionContext){
        log.info("保存订单, commit, xid:{}", actionContext.getXid());
        PreparedStatement statement = (PreparedStatement) statementMap.get(actionContext.getXid());
        Connection connection = connectionMap.get(actionContext.getXid());
        try {
            connection.commit();
        } catch (SQLException e) {
            log.error("保存订单失败:", e);
            return false;
        }finally {
            try {
                statementMap.remove(actionContext.getXid());
                connectionMap.remove(actionContext.getXid());
                if (null != statement){
                    statement.close();
                }
                if (null != connection){
                    connection.close();
                }
            } catch (SQLException e) {
                log.error("保存订单提交事务后关闭连接池失败:", e);
            }
        }
        return true;
    }

    /**
     * 回滚事务
     * @param actionContext save xid
     * @return
     */
    @Override
    public boolean rollback(BusinessActionContext actionContext){
        log.info("保存订单金额, rollback, xid:{}", actionContext.getXid());
        PreparedStatement statement = (PreparedStatement) statementMap.get(actionContext.getXid());
        Connection connection = connectionMap.get(actionContext.getXid());
        try {
            connection.rollback();
        } catch (SQLException e) {
            return false;
        }finally {
            try {
                if (null != statement){
                    statement.close();
                }
                if (null != connection){
                    connection.close();
                }
                statementMap.remove(actionContext.getXid());
                connectionMap.remove(actionContext.getXid());
            } catch (SQLException e) {
                log.error("保存订单回滚事务后关闭连接池失败:", e);
            }
        }
        return true;
    }
}
