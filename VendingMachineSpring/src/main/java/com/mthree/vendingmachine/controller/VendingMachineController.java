/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mthree.vendingmachine.controller;

import com.mthree.vendingmachine.dao.VendingMachinePersistenceException;
import com.mthree.vendingmachine.dto.Item;
import com.mthree.vendingmachine.service.VendingMachineInsufficientFundsException;
import com.mthree.vendingmachine.service.VendingMachineNoItemInventoryException;
import com.mthree.vendingmachine.service.VendingMachineServiceLayer;
import com.mthree.vendingmachine.ui.VendingMachineView;
import java.math.BigDecimal;
import java.util.Map;

/**
 *
 * @author salon
 */
public class VendingMachineController {

    private final VendingMachineView view;
    private final VendingMachineServiceLayer service;

    public VendingMachineController(VendingMachineView view, VendingMachineServiceLayer service) {
        this.view = view;
        this.service = service;
    }

    public void run() {

        boolean keepGoing = true;
        int menuSelection;
        String itemSelection=null;
        BigDecimal inputMoney = new BigDecimal("0");

        while (keepGoing) {
            try {
                menuSelection = getMenuSelection();

                switch (menuSelection) {
                    case 1:
                        inputMoney = inputMoney.add(getMoney());
                        break;
                    case 2:
                        getMenu();
                        itemSelection = getItemSelection();

                        getItem(itemSelection, inputMoney);
                        inputMoney = new BigDecimal("0");
                        break;
                    case 3:
                        getChange(inputMoney);
                        inputMoney = new BigDecimal("0");
                        break;
                    case 4:
                        keepGoing = false;
                        break;
                    default:
                        unknownCommand();
                }

               
            } catch (VendingMachineInsufficientFundsException | VendingMachineNoItemInventoryException | VendingMachinePersistenceException e) {
                view.displayErrorMessage(e.getMessage());
            }
        }
        exitMessage();
    }

    private int getMenuSelection() {
        return view.printMenuAndGetSelection();
    }

    private void getMenu() throws VendingMachinePersistenceException {
        view.displayMenuBanner();
        Map<String, BigDecimal> itemsInStockWithCosts = service.getItemsInStockWithCosts();
        view.displayMenu(itemsInStockWithCosts);
    }

    private BigDecimal getMoney() {
        return view.getMoney();
    }

    private String getItemSelection() {
        return view.getItemSelection();
    }

    private void exitMessage() {
        view.displayExitBanner();
    }

    private void unknownCommand() {
        view.displayUnknownCommandBanner();
    }

    private void getItem(String name, BigDecimal money) throws VendingMachineInsufficientFundsException, VendingMachineNoItemInventoryException, VendingMachinePersistenceException {
        Item wantedItem = service.getItem(name, money);
        Map<BigDecimal, Integer> changeDuePerCoin = service.getChangePerCoin(wantedItem, money);
        view.displayChangeDuePerCoin(changeDuePerCoin);
        view.displayEnjoyBanner(name);
    }
    
    private void getChange(BigDecimal money) throws VendingMachineInsufficientFundsException, VendingMachineNoItemInventoryException, VendingMachinePersistenceException{
        Map<BigDecimal, Integer> changeDuePerCoin = service.getChangePerCoin(null, money);
        view.displayChangeDuePerCoin(changeDuePerCoin);
        
    }
    
    
   
}
