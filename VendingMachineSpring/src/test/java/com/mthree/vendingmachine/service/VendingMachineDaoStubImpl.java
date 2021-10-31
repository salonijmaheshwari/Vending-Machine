/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mthree.vendingmachine.service;

import com.mthree.vendingmachine.dao.VendingMachineDao;
import com.mthree.vendingmachine.dao.VendingMachinePersistenceException;
import com.mthree.vendingmachine.dto.Item;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 *
 * @author salon
 */
public class VendingMachineDaoStubImpl implements VendingMachineDao {
    
    public Item item1,item2;
    Map <String, Item> items = new HashMap<>();
    
    public VendingMachineDaoStubImpl(){
       item1= new Item("Kitkat",new BigDecimal("1.4"));
       item1.setInventory(10);
       items.put(item1.getName(), item1);
       item2= new Item("Snikers",new BigDecimal("1.4"));
       item2.setInventory(0);
       items.put(item2.getName(), item2);
    }
    
    public VendingMachineDaoStubImpl(Item testItem){
        this.item1 = testItem;
    }
            
    @Override
    public void removeOneItemFromInventory(String name) throws VendingMachinePersistenceException {
        int prevInventory = items.get(name).getInventory();
        items.get(name).setInventory(prevInventory-1);
    }

    @Override
    public List<Item> getAllItems() throws VendingMachinePersistenceException {
        return new ArrayList(items.values());
    }

    @Override
    public int getItemInventory(String name) throws VendingMachinePersistenceException {
        return items.get(name).getInventory();
    }

    @Override
    public Item getItem(String name) throws VendingMachinePersistenceException {
       return items.get(name);
    }

    @Override
    public Map<String, BigDecimal> getMapOfItemNamesInStockWithCosts() throws VendingMachinePersistenceException {
        Map<String, BigDecimal> itemsInStockWithCosts = items.entrySet()
                .stream()
                .filter(map -> map.getValue().getInventory() > 0)
                .collect(Collectors.toMap(map -> map.getKey(), map -> map.getValue().getCost()));
        
        return itemsInStockWithCosts;
    }
}
