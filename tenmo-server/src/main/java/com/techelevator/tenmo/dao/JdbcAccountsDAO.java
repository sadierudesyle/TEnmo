package com.techelevator.tenmo.dao;

import org.springframework.data.relational.core.sql.In;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

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

    @Override
    public Integer sendMoney(double amount, Integer userId) {
        sql = "UPDATE accounts SET balance = balance + ?" +
                "WHERE user_id = ?";

        int rowsUpdt = jdbcTemplate.update(sql, amount, userId);
        return rowsUpdt;
    }


    @Override
    public Integer deductMoneySent(double amount, Integer userId) {
        sql = "UPDATE accounts SET balance = balance - ? " +
                "WHERE user_id = ?";
        int rUpdt =  jdbcTemplate.update(sql, amount, userId);
        return rUpdt;}


}
