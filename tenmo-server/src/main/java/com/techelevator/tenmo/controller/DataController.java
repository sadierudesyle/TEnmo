package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.*;
import com.techelevator.tenmo.model.*;
import com.techelevator.tenmo.security.jwt.TokenProvider;
import org.springframework.boot.autoconfigure.quartz.QuartzProperties;
import org.springframework.data.relational.core.sql.In;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;

/**
 * Controller to authenticate users.
 */
@RestController
public class DataController {

    private UserDao userDao;
    private JdbcAccountsDAO jdbcAccountsDAO;
    private JdbcTransfersDAO jdbcTransfersDAO;
    private JdbcXferDetailDAO jdbcXferDetailDAO;
    private JdbcUserDao jdbcUserDao;


    public DataController(JdbcAccountsDAO jdbcAccountsDAO, JdbcTransfersDAO jdbcTransfersDAO,
                          JdbcXferDetailDAO jdbcXferDetailDAO, JdbcUserDao jdbcUserDao)
     {
        this.jdbcAccountsDAO = jdbcAccountsDAO;
        this.jdbcTransfersDAO = jdbcTransfersDAO;
        this.jdbcXferDetailDAO = jdbcXferDetailDAO;
        this.jdbcUserDao = jdbcUserDao;
    }

    @RequestMapping(value = "/getbalance/{id}", method = RequestMethod.GET)
    public Double getBal(@PathVariable int id) {
        return jdbcAccountsDAO.getAcctBal(id);
    }


    @RequestMapping(value = "/getalltransfers/{id}", method = RequestMethod.GET)
    public List<XferData> getAllTransfers(@PathVariable int id) {
        return jdbcTransfersDAO.getAllTransfers(id);
    }

    @RequestMapping(value = "/transferdetail/{transferid}", method = RequestMethod.GET)
    public XferDetail getTransferDetails(@PathVariable int transferid)   {
        return jdbcXferDetailDAO.getDetail(transferid);
    }

    @RequestMapping(value = "/users/{id}", method = RequestMethod.GET)
    public List<User> findForTransfer(@PathVariable int id) {
        return jdbcUserDao.findForTransfer(id);
    }
}


