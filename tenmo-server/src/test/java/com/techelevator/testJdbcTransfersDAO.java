package com.techelevator;

import com.techelevator.tenmo.dao.JdbcTransfersDAO;
import com.techelevator.tenmo.model.XferData;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class testJdbcTransfersDAO {

    private JdbcTemplate jdbcTemplate;
    JdbcTransfersDAO testXfer = new JdbcTransfersDAO(jdbcTemplate);

    public testJdbcTransfersDAO() {
      List<XferData> returnList = testXfer.getAllTransfers(1004);
      Integer returnInt = testXfer.updateTransferAdded(2005, 2005, 11.11);


    }


}
