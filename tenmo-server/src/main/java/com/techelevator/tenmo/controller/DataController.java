package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.JdbcAccountsDAO;
import com.techelevator.tenmo.dao.JdbcTransfersDAO;
import com.techelevator.tenmo.dao.UserDao;
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


    public DataController(JdbcAccountsDAO jdbcAccountsDAO, JdbcTransfersDAO jdbcTransfersDAO)
     {
        this.jdbcAccountsDAO = jdbcAccountsDAO;
        this.jdbcTransfersDAO = jdbcTransfersDAO;
    }

    @RequestMapping(value = "/getbalance/{id}", method = RequestMethod.GET)
    public Double getBal(@PathVariable int id) {
        return jdbcAccountsDAO.getAcctBal(id);
    }


    @RequestMapping(value = "/getalltransfers/{id}", method = RequestMethod.GET)
    public List<XferData> getAllTransfers(@PathVariable int id) {
        int breakpoint = 1;
        return jdbcTransfersDAO.getAllTransfers(id)     ;
    }
}


