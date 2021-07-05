package com.techelevator;

import com.techelevator.tenmo.dao.AccountsDAO;
import com.techelevator.tenmo.dao.JdbcAccountsDAO;
import com.techelevator.tenmo.model.Accounts;
import org.springframework.jdbc.core.JdbcTemplate;

public class testJdbcAccountsDAO {

    private JdbcTemplate jdbcTemplate;
    JdbcAccountsDAO testAcct = new JdbcAccountsDAO(jdbcTemplate);

    public testJdbcAccountsDAO() {
      Double returnVal = testAcct.getAcctBal(1001);
      testAcct.deductMoneySent(111.11,1002);
      testAcct.sendMoney(111.11,1003 );

    }


}
