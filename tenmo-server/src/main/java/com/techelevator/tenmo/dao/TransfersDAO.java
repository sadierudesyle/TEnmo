package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.XferData;

import java.util.List;

public interface TransfersDAO {
    public void getTransfersFrom(int userId, List<XferData> transfers);
    public void getTransfersTo(int userId,List<XferData> transfers);
    public Integer updateTransferAdded(Integer account_from, Integer account_to, Double amount);
}
