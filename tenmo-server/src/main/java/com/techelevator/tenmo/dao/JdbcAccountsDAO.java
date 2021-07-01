package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Accounts;
import com.techelevator.tenmo.model.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Component                            //added
public class JdbcAccountsDAO implements AccountsDAO {
    String sql;
    private JdbcTemplate jdbcTemplate;

    public JdbcAccountsDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }



    @Override
    public Double getAcctBal(int userId) {
        sql = "SELECT balance FROM accounts WHERE user_id = ?";
        Double returnVal = jdbcTemplate.queryForObject(sql, Double.class, userId);
        if (returnVal != null){
            return returnVal;}
        else {return -1.0;}
    }


}
