package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Accounts;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.XferData;
import com.techelevator.tenmo.model.XferDetail;
import org.springframework.data.relational.core.sql.In;
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
    private AccountsDAO accountsDAO;

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


    public Integer updateTransferAdded(Integer account_from, Integer account_to, Double amount) {
        sql = "INSERT INTO transfers (transfer_type_id, transfer_status_id, account_from, account_to, amount) " +
                "VALUES (2, 2, (SELECT account_id FROM accounts WHERE user_id = ?), (SELECT account_id FROM accounts WHERE user_id = ?), ?); ";

        Integer accountUpdated = jdbcTemplate.update(sql, account_from, account_to, amount);
        return accountUpdated;
    }


private XferData mapRowToTransfer(SqlRowSet results) {
        XferData transfer = new XferData();
        transfer.setTransferId(results.getInt("transfer_id"));
        transfer.setDirection(results.getString("from_to"));
        transfer.setUsername(results.getString("username"));
        transfer.setAmount(results.getDouble("amount"));
        return transfer;
    }


}
