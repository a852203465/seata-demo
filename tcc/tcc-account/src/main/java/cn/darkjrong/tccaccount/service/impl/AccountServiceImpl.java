package cn.darkjrong.tccaccount.service.impl;

import cn.darkjrong.tccaccount.service.AccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Service
public class AccountServiceImpl implements AccountService {

    private Map<String, Statement> statementMap = new ConcurrentHashMap<>(100);
    private Map<String, Connection> connectionMap = new ConcurrentHashMap<>(100);

    @Autowired
    private DataSource dataSource;

    @Override
    public boolean decrease(String xid, Long userId, BigDecimal payAmount) {
        log.info("commit, xid:{}", xid);
        log.info("------->尝试扣减账户开始account");

        //模拟超时异常，全局事务回滚
        /*try {
            Thread.sleep(30*1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/

        try {
            //尝试扣减账户金额,事务不提交
            Connection connection = dataSource.getConnection();
            connection.setAutoCommit(false);
            String sql = "UPDATE account SET balance = balance - ?,used = used + ? where user_id = ?";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setBigDecimal(1, payAmount);
            stmt.setBigDecimal(2, payAmount);
            stmt.setLong(3, userId);
            stmt.executeUpdate();
            statementMap.put(xid, stmt);
            connectionMap.put(xid, connection);
        } catch (Exception e) {
            log.error("decrease parepare failure:", e);
            return false;
        }

        log.info("------->尝试扣减账户结束account");

        return true;

    }

    @Override
    public boolean commit(String xid){
        log.info("扣减账户金额, commit, xid:{}", xid);
        PreparedStatement statement = (PreparedStatement) statementMap.get(xid);
        Connection connection = connectionMap.get(xid);
        try {
            if (null != connection){
                connection.commit();
            }
        } catch (SQLException e) {
            log.error("扣减账户失败:", e);
            return false;
        }finally {
            try {
                statementMap.remove(xid);
                connectionMap.remove(xid);
                if (null != statement){
                    statement.close();
                }
                if (null != connection){
                    connection.close();
                }
            } catch (SQLException e) {
                log.error("扣减账户提交事务后关闭连接池失败:", e);
            }
        }
        return true;
    }

    @Override
    public boolean rollback(String xid){
        log.info("扣减账户金额, rollback, xid:{}", xid);
        PreparedStatement statement = (PreparedStatement) statementMap.get(xid);
        Connection connection = connectionMap.get(xid);
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
                statementMap.remove(xid);
                connectionMap.remove(xid);
            } catch (SQLException e) {
                log.error("扣减账户回滚事务后关闭连接池失败:", e);
            }
        }
        return true;
    }
}
