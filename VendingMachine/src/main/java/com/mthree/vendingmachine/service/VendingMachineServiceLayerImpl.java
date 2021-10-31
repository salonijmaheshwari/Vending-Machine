/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mthree.vendingmachine.service;

import com.mthree.vendingmachine.dao.VendingMachineAuditDao;
import com.mthree.vendingmachine.dao.VendingMachineDao;
import com.mthree.vendingmachine.dao.VendingMachinePersistenceException;
import com.mthree.vendingmachine.dto.Change;
import com.mthree.vendingmachine.dto.Item;
import java.math.BigDecimal;
import java.util.Map;

/**
 *
 * @author salon
 */
public class VendingMachineServiceLayerImpl implements VendingMachineServiceLayer{
   
    private final VendingMachineAuditDao auditDao;
    private final VendingMachineDao dao;

    public VendingMachineServiceLayerImpl(VendingMachineDao dao,VendingMachineAuditDao auditDao) {
        this.auditDao = auditDao;
        this.dao = dao;
    }

    @Override
    public void checkIfEnoughMoney(Item item, BigDecimal inputMoney) throws VendingMachineInsufficientFundsException,VendingMachinePersistenceException {
        //Checks if the user has input enough money to buy selected item
        //If the cost of the item is greater than the amount of money put in
        if (item.getCost().compareTo(inputMoney)==1) {
            auditDao.writeAuditEntry("Error happened due to insufficient money.");
            throw new VendingMachineInsufficientFundsException (
            "ERROR: insufficient funds, you have only input "+ inputMoney);  
        }
         
    }
    
    @Override
    public Map<String, BigDecimal>  getItemsInStockWithCosts () throws VendingMachinePersistenceException{
        //Map of key=name, value=cost
         Map<String, BigDecimal> itemsInStockWithCosts = dao.getMapOfItemNamesInStockWithCosts();
         return itemsInStockWithCosts;
    }
    
    @Override
    public Map<BigDecimal, Integer> getChangePerCoin(Item item, BigDecimal money) throws VendingMachinePersistenceException{
        BigDecimal itemCost; 
        if(item == null)
        {
            itemCost=new BigDecimal("0");
        }else{
            itemCost=item.getCost();
        }
        Map<BigDecimal, Integer> changeDuePerCoin = Change.changeDuePerCoin(itemCost, money);
        auditDao.writeAuditEntry(changeDuePerCoin.toString() +"change returned to User." );
        return changeDuePerCoin;
    }
    
    @Override
    public Item getItem(String name, BigDecimal inputMoney) throws VendingMachineInsufficientFundsException, VendingMachineNoItemInventoryException, VendingMachinePersistenceException {
        Item wantedItem = dao.getItem(name);   //the inputs are case sensitive.
        
        //If the wanted item returns null, the item does not exist in the items map
        if (wantedItem == null) {
            throw new VendingMachineNoItemInventoryException (
                "ERROR: there is no " + name + " in the vending machine.");
        }
        
        //Check if the user has input enough money
        checkIfEnoughMoney(wantedItem,inputMoney);
        
        //If they have, check that the item is in stock and if so, remove one item from the inventory
        this.removeOneItemFromInventory(name);
        return wantedItem;
//        //Give user their change
//        return getChangePerCoin(wantedItem,inputMoney);
    }
    
    
    @Override
    public void removeOneItemFromInventory (String name) throws VendingMachineNoItemInventoryException, VendingMachinePersistenceException {
        //Remove one item from the inventory only when there are items to be removed, i.e. inventory>0.
        if (dao.getItemInventory(name)>0) {
            dao.removeOneItemFromInventory(name);
            //if an items removed, write to the audit log
            auditDao.writeAuditEntry(" One " + name + " removed");
        } else {
             auditDao.writeAuditEntry(" There isn't any " + name + " left in the inventory");
            //If there are no items left to remove, throw an exception
            throw new VendingMachineNoItemInventoryException (
            "ERROR: " + name + " is out of stock.");
           
        }
        
    }

    @Override
    public int getItemInventory(String name) throws VendingMachinePersistenceException, VendingMachineNoItemInventoryException{
        if(dao.getItemInventory(name)==0 ){
              throw new VendingMachineNoItemInventoryException (
            "ERROR: " + name + " is out of stock.");
        }
         else
            return dao.getItemInventory(name);
    }
}
