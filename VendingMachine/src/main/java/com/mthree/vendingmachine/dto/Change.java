/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mthree.vendingmachine.dto;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author salon
 */
public class Change {

    public static BigDecimal changeDueInPennies (BigDecimal itemCost, BigDecimal money) {
        BigDecimal changeDueInPennies = (money.subtract(itemCost)).multiply(new BigDecimal("100"));
        System.out.println("Change due: $" + (changeDueInPennies.divide(new BigDecimal("100"),2,RoundingMode.HALF_UP).toString()));
        return changeDueInPennies;
    }
    
    public static Map<BigDecimal,Integer> changeDuePerCoin (BigDecimal itemCost, BigDecimal money) {
        
        /*Coin[] coinEnumArray = Coin.values();
        ArrayList <String> coinStringList = new ArrayList<>();
        for (Coin coin : coinEnumArray) {
            coinStringList.add(coin.toString());
        }

          ArrayList<BigDecimal> coins = new ArrayList<>(); 
          for (String coin:coinStringList) {
              coins.add(new BigDecimal(coin));
          }*/
       
        
        BigDecimal changeDueInPennies = changeDueInPennies(itemCost, money);
        //Calculates the number of quarters, dimes, nickels and pennies due 
        //back to the user.
        Integer noOfCoin;
        BigDecimal zero = new BigDecimal("0");
        //Map <coin, noOfCoin>
        Map <BigDecimal, Integer> amountPerCoin = new HashMap<>();
        ArrayList<BigDecimal> coins = new ArrayList<>(); 
        Coin q= Coin.QUARTER;
        coins.add(q.getValue());
        Coin d = Coin.DIME;
        coins.add(d.getValue());
        Coin n= Coin.NICKEL;
        coins.add(n.getValue());
        Coin p = Coin.PENNY;
        coins.add(p.getValue());
         
        //for every coin in the array:
        for ( BigDecimal coin:coins) {
            //if the change is greater than or equal to the coin amount
            if (changeDueInPennies.compareTo(coin) >= 0) {
                //If the coin amounts does not exactly divide by the change amount
                if (!changeDueInPennies.remainder(coin).equals(zero)) {
                    //the number of coins of coin[i] required will be the floor division of change amount/coin
                    noOfCoin = changeDueInPennies.divide(coin,0,RoundingMode.DOWN).intValue();
                    //add the type of coin and amount of coin to the map
                    amountPerCoin.put(coin, noOfCoin);
                    //the change amount is updated to be the remaining amount
                    changeDueInPennies = changeDueInPennies.remainder(coin);
                    //if the change amount is less than or equal to 0, stop the loop
                    if (changeDueInPennies.compareTo(zero)<0) {  
                        break;
                    }
                    //if the change divided by the coin is an exact number/integer
                } else if (changeDueInPennies.remainder(coin).equals(zero)) {  //could change to just else
                    noOfCoin = changeDueInPennies.divide(coin,0,RoundingMode.DOWN).intValue();
                    amountPerCoin.put(coin, noOfCoin);
                    changeDueInPennies=changeDueInPennies.remainder(coin);
                    //if the change amount if less than or equal to 0, stop the loop
                    if ((changeDueInPennies.compareTo(zero))<=0) {
                        break;
                      
                    }
                }
                
            } 
        }//end of for loop
        return amountPerCoin;
    }
    
}
