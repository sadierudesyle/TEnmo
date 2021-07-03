package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.XferData;
import com.techelevator.tenmo.model.XferDetail;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import javax.sql.RowSet;
import java.util.ArrayList;
import java.util.List;

@Component                            //added
public class JdbcTransfersDAO implements TransfersDAO {
    String sql;
    private JdbcTemplate jdbcTemplate;

    public JdbcTransfersDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }




    public List<XferData> getAllTransfers(int userId) {
        List<XferData> transfers = new ArrayList<XferData>();
        getTransfersFrom(userId, transfers);
        getTransfersTo(userId, transfers);
        return transfers;
    }

    public void getTransfersFrom(int userId, List<XferData> transfers) {
      sql = "SELECT transfer_id, amount, 'From' as From_To, username FROM transfers AS t  "+
                "JOIN accounts a on account_from = a.account_id  "+
                "JOIN users u ON a.user_id = u.user_id  "+
                "WHERE t.account_to IN (select account_id from accounts a where a.user_id = ?) ";

        SqlRowSet returnVal = jdbcTemplate.queryForRowSet(sql, userId);
        while(returnVal.next()) {
            XferData transfer = mapRowToTransfer(returnVal);
            transfers.add(transfer);
        }
    }


    public void getTransfersTo(int userId,List<XferData> transfers) {
        sql = "SELECT transfer_id, amount, 'To' as From_To, username FROM transfers AS t "+
        "JOIN accounts a on account_to = a.account_id "+
        "JOIN users u ON a.user_id = u.user_id "+
        "WHERE t.account_from IN (select account_id from accounts a where a.user_id = ?)" ;

        SqlRowSet returnV = jdbcTemplate.queryForRowSet(sql, userId);
        while(returnV.next()) {
            XferData transfer = mapRowToTransfer(returnV);
            transfers.add(transfer);
        }
        }


    public int sendMoney(double amount, Integer account) {
        sql = "UPDATE accounts SET balance = balance + ?" +
              "WHERE account_id = ?";

      int rowsUpdt = jdbcTemplate.update(sql, amount, account);
      return rowsUpdt;
        }

    public int deductMoneySent(double amount, Integer userId) {
        sql = "UPDATE accounts SET balance = balance + ? " +
                 "WHERE user_id = ?";
      int rUpdt =  jdbcTemplate.update(sql, amount, userId);
        return rUpdt;}



private XferData mapRowToTransfer(SqlRowSet results) {
        XferData transfer = new XferData();
        transfer.setTransferId(results.getInt("transfer_id"));
        transfer.setDirection(results.getString("from_to"));
        transfer.setUsername(results.getString("username"));
        transfer.setAmount(results.getDouble("amount"));
        return transfer;
    }


}
