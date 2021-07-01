package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Accounts;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

@Component                            //added
public class JdbcAccountsDAO implements AccountsDAO {
    String sql;
    private JdbcTemplate jdbctemplate;
 //added
    public JdbcAccountsDAO(JdbcTemplate jdbcTemplate){
        this.jdbctemplate = jdbcTemplate;
    }
 //end of addition
//    public AccountsDAO(DataSource datasource){
//updated to following:
    public JdbcAccountsDAO(DataSource datasource){
        jdbctemplate = new JdbcTemplate(datasource);
    }


    @Override
    public Double getAcctBal(int userId) {
        sql = "SELECT balance FROM accounts WHERE user_id = ?";
        Double returnVal = jdbctemplate.queryForObject(sql, double.class, userId);
        if (returnVal != null){
            return returnVal;}
        else {return -1.0;}
    }


}
