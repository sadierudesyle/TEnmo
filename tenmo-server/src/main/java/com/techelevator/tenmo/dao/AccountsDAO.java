package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Accounts;
import org.springframework.data.relational.core.sql.In;

public interface AccountsDAO {
    Double getAcctBal(int userID);
    Integer sendMoney(double amount, Integer account);
    Integer deductMoneySent(double amount, Integer userId);

}
