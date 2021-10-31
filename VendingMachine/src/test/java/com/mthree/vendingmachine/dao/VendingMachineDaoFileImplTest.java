/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mthree.vendingmachine.dao;

import com.mthree.vendingmachine.dto.Item;
import java.io.FileWriter;
import java.math.BigDecimal;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author salon
 */
public class VendingMachineDaoFileImplTest {
    VendingMachineDao testDao = new VendingMachineDaoFileImpl("VendingMachineTestFile.txt");
    
    public VendingMachineDaoFileImplTest() {
    }
    @BeforeAll
    public static void setUpClass() {
    }
    @AfterAll
    public static void tearDownClass() {
    }
    @BeforeEach
    public void setUp(){
    }
    
    @AfterEach
    public void tearDown() {
    }
    
    @Test
    public void testGetItem() throws VendingMachinePersistenceException {
        //ARRANGE
        Item snickersClone = new Item("Snickers",new BigDecimal("1.75"));
        snickersClone.setInventory(0);
        
        
        //ACT
        Item retrievedItem = testDao.getItem("Snickers");
        
        //ASSERT
        assertNotNull(retrievedItem, "Item should not be null");
        assertEquals(retrievedItem, snickersClone,"The item retrieved should be snickers");
        
    }
    @Test
    public void testRemoveOneItemFromInventory() throws VendingMachinePersistenceException {
        //ARRANGE
        String itemName = "Kitkat";
        
        //ACT
        //get the inventory before we remove one
        int inventoryBefore = testDao.getItemInventory(itemName);
        
        //remove one item
        testDao.removeOneItemFromInventory(itemName);
        
        //get the inventory after we have removed one
        int inventoryAfter = testDao.getItemInventory(itemName);
        
        assertTrue(inventoryAfter<inventoryBefore,"The inventory after should be less than before");
        assertEquals(inventoryAfter,inventoryBefore-1,"The inventory after should be one less than it was"
                + "before");
        
    }
    @Test
    public void testGetItemInventory() throws VendingMachinePersistenceException {
        //ARRANGE 
        String itemName = "Snickers";
        
        //ACT
        int retrievedInventory = testDao.getItemInventory(itemName);

        //ASSERT 
        assertEquals(retrievedInventory,0,"There are 0 items of snickers left.");  
    }
    
    @Test
    public void testGetMapOfItemNamesInStockWithCosts() throws VendingMachinePersistenceException {
        
        //ACT
        Map<String,BigDecimal> itemsInStock = testDao.getMapOfItemNamesInStockWithCosts();
        
        //ASSERT
        //there are 0 snickers left, so it should not be included.
        assertFalse(itemsInStock.containsKey("Snickers"));
        //There are 7 items in total, only snickers  is out of stock, so there should be 6 items
        assertEquals(itemsInStock.size(),6,"The menu list should contain 6 items.");
        assertTrue(itemsInStock.containsKey("Kitkat") &&
                itemsInStock.containsKey("Coffee Crisp") &&
                itemsInStock.containsKey("Twix") &&
                itemsInStock.containsKey("Caramilk") &&
                itemsInStock.containsKey("Bounty") &&
                itemsInStock.containsKey("Dairy Milk"));
    }
    
} 