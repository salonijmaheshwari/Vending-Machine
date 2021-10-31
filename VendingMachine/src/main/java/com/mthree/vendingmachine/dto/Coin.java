/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mthree.vendingmachine.dto;

import java.math.BigDecimal;

/**
 *
 * @author salon
 */
public enum Coin {
    
    QUARTER(new BigDecimal("25")), DIME(new BigDecimal("10")), NICKEL(new BigDecimal("5")), PENNY(new BigDecimal("1"));

    private final BigDecimal value;

    private Coin(BigDecimal value) {
        this.value = value;
    }

    public BigDecimal getValue() {
        return value;
    }

   /*
    @Override
    public String toString(){
        switch (this) {
            
            case QUARTER:
                return "25";
            case DIME:
                return "10";
            case NICKEL:
                return "5";
            case PENNY:
                return "1";
        }
        return null;
    }
*/
}
