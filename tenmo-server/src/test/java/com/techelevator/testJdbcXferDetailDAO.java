package com.techelevator;

import com.techelevator.tenmo.dao.JdbcTransfersDAO;
import com.techelevator.tenmo.dao.JdbcXferDetailDAO;
import com.techelevator.tenmo.model.XferData;
import com.techelevator.tenmo.model.XferDetail;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.aop.framework.AopInfrastructureBean;
import org.springframework.data.relational.core.sql.In;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;

import java.util.List;
import java.util.Scanner;

public class testJdbcXferDetailDAO extends TEnmoDAOTests {

    private JdbcTemplate jdbcTemplate;
    JdbcXferDetailDAO testXfer = new JdbcXferDetailDAO(jdbcTemplate);
    public static SingleConnectionDataSource dataSource;
    private JdbcXferDetailDAO sut;


//    @Before
//    public void setup () {
////        JdbcXferDetailDAO testXfer = new JdbcXferDetailDAO(jdbcTemplate);
//    }
@Test
    public void testJdbcXferDetailDAO() {
        XferDetail returnDetail = testXfer.getDetail(3001) ;
    Assert.assertNotNull(returnDetail);
        Double returnVal1 = returnDetail.getAmount();
        String returnVal2 =  returnDetail.getStatus();
        Integer returnVal3 = returnDetail.getTransferId();
        String returnVal4 =  returnDetail.getType();
        String returnVal5 =  returnDetail.getUserFrom();
        String returnVal6 = returnDetail.getUserTo();

        Double eVal1 = 75.00;
        String eVal2 =  "2";
        Integer eVal3 = 3001;
        String eVal4 =  "2";
        String eVal5 =  "2001";
        String eVal6 = "2002";



        Assert.assertEquals(returnVal1, eVal1);
        Assert.assertEquals(returnVal3, eVal3);
        Assert.assertEquals(returnVal2, eVal2);
        Assert.assertEquals(returnVal4, eVal4);
        Assert.assertEquals(returnVal5, eVal5);
        Assert.assertEquals(returnVal6, eVal6);
//
//        Double value1 = 33.33;
//        String value2 = "2";
//        Integer value3 = 3021;
//        String value4 = "2";
//        String value5 = "2006";
//        String value6 = "2002";
//
//        Assert.assertEquals(returnVal1, value1);
//        Assert.assertEquals(returnVal3, value3);
//        Assert.assertEquals(returnVal2, value2);
//        Assert.assertEquals(returnVal4, value4);
//        Assert.assertEquals(returnVal5, value5);
//        Assert.assertEquals(returnVal6, value6);


}
}
