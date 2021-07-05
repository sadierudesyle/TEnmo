package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.XferData;
import com.techelevator.tenmo.model.XferDetail;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component                            //added
public class JdbcXferDetailDAO implements XferDetailDAO {
    String sql;
    private JdbcTemplate jdbcTemplate;
    private XferDetail values;

    public JdbcXferDetailDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public XferDetail getDetail(int xfernum)
 {
     XferDetail returnVal = new XferDetail();
      sql = "SELECT transfer_id, transfer_type_desc, transfer_status_desc, " +
              "q.username as recipient, amount, u.username as sender  FROM transfers AS t "+
             "JOIN accounts b on account_to = b.account_id "+
             "JOIN users q ON b.user_id = q.user_id "+
             "JOIN accounts a on account_from = a.account_id "+
             "JOIN users u ON a.user_id = u.user_id "+
             "JOIN transfer_statuses ts ON ts.transfer_status_id = t.transfer_status_id "+
             "JOIN transfer_types tt ON tt.transfer_type_id = t.transfer_type_id "+
             "WHERE t.transfer_id = ?";


      SqlRowSet results = jdbcTemplate.queryForRowSet(sql, xfernum);
//     SqlRowSet returnVal = jdbcTemplate.queryForRowSet(sql, xfernum);
        if (results.next()) {
            returnVal= mapRowToTransfer(results);
        }
        // fix this to be informative
        return returnVal;
    }

private XferDetail mapRowToTransfer(SqlRowSet results) {
        XferDetail values = new XferDetail();
    values.setTransferId(results.getInt("transfer_id"));
    values.setAmount(results.getDouble("amount"));
    values.setStatus(results.getString("transfer_status_desc"));
    values.setType(results.getString("transfer_type_desc"));
    values.setUserFrom(results.getString("sender"));
    values.setUserTo(results.getString("recipient"));
    return values;
    }
}
