package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.XferData;
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

    public List<XferData> transfers = new ArrayList<XferData>();
//AR    public List<Transfer> transfers = new ArrayList<>();

    public List<XferData> getAllTransfers(int userId) {
//        public List<Transfer> getAllTransfers(int userId) {
        getTransfersFrom(userId);
        getTransfersTo(userId);
        return transfers;
    }

    public void getTransfersFrom(int userId) {
      sql = "SELECT transfer_id, amount, 'From' as From_To, username FROM transfers AS t  "+
                "JOIN accounts a on account_from = a.account_id  "+
                "JOIN users u ON a.user_id = u.user_id  "+
                "WHERE t.account_to IN (select account_id from accounts a where a.user_id = ?) ";


//AR        sql = "SELECT transfer_id, transfer_type_id, u.username, amount FROM transfers AS t " +
//AR                "JOIN accounts a ON a.account_id = t.account_from " +
//AR                "JOIN users u ON u.user_id = a.user_id " +
//AR                "WHERE transfer_type_id = 1 AND transfer_status_id = 2 AND u.user_id = ?";
        SqlRowSet returnVal = jdbcTemplate.queryForRowSet(sql, userId);
        while(returnVal.next()) {
            XferData transfer = mapRowToTransfer(returnVal);
            transfers.add(transfer);
        }
//        while(returnVal.next()) {
//            Transfer transfer = mapRowToTransfer(returnVal, 1);
//            transfers.add(transfer);
//        }
    }


    public void getTransfersTo(int userId) {
        sql = "SELECT transfer_id, amount, 'To' as From_To, username FROM transfers AS t "+
        "JOIN accounts a on account_to = a.account_id "+
        "JOIN users u ON a.user_id = u.user_id "+
        "WHERE t.account_from IN (select account_id from accounts a where a.user_id = ?)" ;

//AR        sql = "SELECT transfer_id, transfer_type_id, u.username, amount FROM transfers AS t " +
//AR                "JOIN accounts a ON a.account_id = t.account_to " +
//AR                "JOIN users u ON u.user_id = a.user_id " +
//AR                "WHERE transfer_type_id = 2 AND transfer_status_id = 2 AND u.user_id = ?";
        SqlRowSet returnVal = jdbcTemplate.queryForRowSet(sql, userId);
        while(returnVal.next()) {
            XferData transfer = mapRowToTransfer(returnVal);
            transfers.add(transfer);
        }
//        while(returnVal.next()) {
//            Transfer transfer = mapRowToTransfer(returnVal, 2);
//            transfers.add(transfer);
        }

//AR    private Transfer mapRowToTransfer(SqlRowSet results, int direction) {
//AR    Transfer transfer = new Transfer();
//AR    transfer.setTransferId(results.getInt("transfer_id"));
//AR    transfer.setTransferTypeId(direction);
//AR    transfer.setUsername(results.getString("username"));
//AR    transfer.setAmount(results.getDouble("amount"));
private XferData mapRowToTransfer(SqlRowSet results) {
        XferData transfer = new XferData();
        transfer.setTransferId(results.getInt("transfer_id"));
        transfer.setDirection(results.getString("from_to"));
        transfer.setUsername(results.getString("username"));
        transfer.setAmount(results.getDouble("amount"));
        return transfer;
    }


}
