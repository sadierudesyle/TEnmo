package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;
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

    public List<Transfer> transfers = new ArrayList<>();


    public List<Transfer> getAllTransfers(int userId) {
        getTransfersFrom(userId);
        getTransfersTo(userId);
        return transfers;
    }

    public void getTransfersFrom(int userId) {
        sql = "SELECT transfer_id, transfer_type_id, u.username, amount FROM transfers AS t " +
                "JOIN accounts a ON a.account_id = t.account_from " +
                "JOIN users u ON u.user_id = a.user_id " +
                "WHERE transfer_type_id = 1 AND transfer_status_id = 2 AND u.user_id = ?";
        SqlRowSet returnVal = jdbcTemplate.queryForRowSet(sql, userId);
        while(returnVal.next()) {
            Transfer transfer = mapRowToTransfer(returnVal, 1);
            transfers.add(transfer);
        }
    }


    public void getTransfersTo(int userId) {
        sql = "SELECT transfer_id, transfer_type_id, u.username, amount FROM transfers AS t " +
                "JOIN accounts a ON a.account_id = t.account_to " +
                "JOIN users u ON u.user_id = a.user_id " +
                "WHERE transfer_type_id = 2 AND transfer_status_id = 2 AND u.user_id = ?";
        SqlRowSet returnVal = jdbcTemplate.queryForRowSet(sql, userId);
        while(returnVal.next()) {
            Transfer transfer = mapRowToTransfer(returnVal, 2);
            transfers.add(transfer);
        }
    }

    private Transfer mapRowToTransfer(SqlRowSet results, int direction) {
        Transfer transfer = new Transfer();
        transfer.setTransferId(results.getInt("transfer_id"));
        transfer.setTransferTypeId(direction);
        transfer.setUsername(results.getString("username"));
        transfer.setAmount(results.getDouble("amount"));
        return transfer;
    }


}
