/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mthree.vendingmachine.service;

import com.mthree.vendingmachine.dao.VendingMachinePersistenceException;
import com.mthree.vendingmachine.dto.Item;
import java.math.BigDecimal;
import java.util.Map;

/**
 *
 * @author salon
 */
public interface VendingMachineServiceLayer {
                
    void checkIfEnoughMoney(Item item, BigDecimal inputMoney)throws 
            VendingMachineInsufficientFundsException,VendingMachinePersistenceException;
    
    void removeOneItemFromInventory(String name) throws 
            VendingMachineNoItemInventoryException, 
            VendingMachinePersistenceException;
    
    Map<String, BigDecimal>  getItemsInStockWithCosts () throws 
            VendingMachinePersistenceException;

    Item getItem(String name, BigDecimal inputMoney) throws 
            VendingMachineInsufficientFundsException, 
            VendingMachineNoItemInventoryException, 
            VendingMachinePersistenceException;
    
    Map<BigDecimal, Integer> getChangePerCoin(Item item, BigDecimal money) throws 
            VendingMachinePersistenceException;
    
    int getItemInventory(String name) throws 
            VendingMachinePersistenceException,
            VendingMachineNoItemInventoryException;
    
}
