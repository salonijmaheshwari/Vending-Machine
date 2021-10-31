/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mthree.vendingmachine.ui;

import com.mthree.vendingmachine.dto.Coin;
import com.mthree.vendingmachine.dto.Item;
import java.math.BigDecimal;
import java.util.Map;

/**
 *
 * @author salon
 */
public class VendingMachineView {

    private final UserIO io;

    public VendingMachineView(UserIO io) {
        this.io = io;
    }
    
    public int printMenuAndGetSelection() {
        io.print("Main Menu");
        io.print("1. Add Money");
        io.print("2. Buy an Item");
        io.print("3. Get Change");
        io.print("4. Exit");
        return io.readInt("Please select from the above choices.", 1, 4);
    }

    public String Exit() {
        return io.readString("Please write Exit to Quit");
    }

    public BigDecimal getMoney() {
        return io.readBigDecimal("Please input the amount money in dollars.");
    }

    public void displayMenuBanner() {
        io.print("=== Items Menu ===");
    }

    public void displayMenu(Map<String, BigDecimal> itemsInStockWithCosts) {
        itemsInStockWithCosts.entrySet().forEach(entry -> {
            io.print(entry.getKey() + ": $" + entry.getValue());
        });
    }

    public String getItemSelection() {
        return io.readString("Please select an item from the menu.");
    }

    public void displayEnjoyBanner(String name) {
        io.print("Here is your change.");
        io.print("Enjoy your " + name + "!");
    }

    public void displayInsufficientFundsMsg(BigDecimal money) {
        io.print("Insufficent funds, you only have input $" + money);
    }

    public void displayItemOutOfStockMsg(String name) {
        io.print("Error, " + name + " is out of stock.");
    }

    public void displayChangeDuePerCoin(Map<BigDecimal, Integer> changeDuePerCoin) {
          Coin c;
            
        changeDuePerCoin.entrySet().forEach(entry -> {
            io.print(entry.getKey() + "c : " + entry.getValue());  
        });
            
    }
   
        
   
    public void displayExitBanner() {
        io.print("Good Bye!!!");
    }

    public void displayUnknownCommandBanner() {
        io.print("Unknown Command!!!");
    }

    public void displayErrorMessage(String errorMsg) {
        io.print("=== Error ===");
        io.print(errorMsg);
    }

    public void displayPleaseTryAgainMsg() {
        io.print("Please Enter Valid Input.");
    }

}
