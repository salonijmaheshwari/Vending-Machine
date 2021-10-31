/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mthree.vendingmachine.service;


import com.mthree.vendingmachine.dao.VendingMachinePersistenceException;
import com.mthree.vendingmachine.dto.Item;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author salon
 */
public class VendingMachineServiceLayerImplTest {
   
    private final VendingMachineServiceLayer testService;
    
    public VendingMachineServiceLayerImplTest(){
    /*VendingMachineDao dao = new VendingMachineDaoStubImpl();  
    VendingMachineAuditDao auditDao = new VendingMachineAuditDaoStubImpl();
    testService = new VendingMachineServiceLayerImpl(dao,auditDao);*/
    ApplicationContext ctx = 
        new ClassPathXmlApplicationContext("applicationContext.xml");
    testService = 
        ctx.getBean("serviceLayer", VendingMachineServiceLayer.class);
    
    }
    
    @BeforeAll
    public static void setUpClass() {
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() {
    }
    
    @AfterEach
    public void tearDown() {
    }

    @Test
    public void testSomeMethod() {
        
    }
    @Test
    public void testCheckIfEnoughMoney() throws VendingMachinePersistenceException {
        //ARRANGE
        Item twixClone = new Item("Twix",new BigDecimal("1.70"));
        twixClone.setInventory(9);
        
        BigDecimal enoughMoney = new BigDecimal("2.00");
        BigDecimal notEnoughMoney = new BigDecimal("1.59");
        
        //ACT - enough money
        try {
            testService.checkIfEnoughMoney(twixClone, enoughMoney) ;
        } catch (VendingMachineInsufficientFundsException e){
            fail("There is sufficient funds, exception should not have been thrown");
        }
        //ACT - not enough money
        try {
            testService.checkIfEnoughMoney(twixClone, notEnoughMoney);
            fail("There insufficient funds, exception should have been thrown");
        } catch (VendingMachineInsufficientFundsException e){
        }
    }
    
    @Test
    public void testGetItemsInStockWithCosts() {
        
    }
    
    @Test
    public void testGetChangePerCoin() throws VendingMachinePersistenceException{
        //ARRANGE
        Item twixClone = new Item("Twix",new BigDecimal("1.60"));
        twixClone.setInventory(9);
        
        BigDecimal money = new BigDecimal("2.50");
        
        //Change should be $0.90: 25c: 3, 10c: 1, 5c:1
        Map<BigDecimal, Integer> expectedChangePerCoin = new HashMap<>();
        expectedChangePerCoin.put(new BigDecimal("0.25"),3);
        expectedChangePerCoin.put(new BigDecimal("0.10"),1);
        expectedChangePerCoin.put(new BigDecimal("0.05"),1);
        
        //ACT
        Map<BigDecimal, Integer> changePerCoin = testService.getChangePerCoin(twixClone, money);
        
        //ASSERT
        assertEquals(changePerCoin.size(), 3, "There should be three coins");
        
        //assertTrue(changePerCoin.entrySet().equals(expectedChangePerCoin.entrySet()), "The change per coin map should be equal to the expected.");
        //need to find out how to compare these as this doesnt work
    }
    
    @Test
    public void testGetItem() throws VendingMachineInsufficientFundsException, VendingMachinePersistenceException, VendingMachineNoItemInventoryException {
        //ARRANGE
        Item snickersClone = new Item("Snickers",new BigDecimal("2.10"));
        snickersClone.setInventory(0);
        BigDecimal money = new BigDecimal("3.00");
        
        Item kitkatClone = new Item("Kitkat",new BigDecimal("1.50"));
        kitkatClone.setInventory(16);
        
        Item itemWanted = null;
        //ACT
        try {
            itemWanted = testService.getItem("Snickers", money);
            fail("The item wanted is out of stock.");
        }catch (VendingMachineNoItemInventoryException e) {
        }
        try {
            itemWanted = testService.getItem("Kitkat", money);
        }catch (VendingMachineNoItemInventoryException e) {
            if (testService.getItemInventory("Kitkat")>0){
            fail("The item wanted is in stock.");
        } 

        //ASSERT
        assertNotNull(itemWanted, "change should not be null");
        assertEquals(itemWanted, kitkatClone,"The item retrieved should be snickers");
    }
    }
    
    @Test
    public void testRemoveOneItemFromInventory() throws VendingMachinePersistenceException,VendingMachineNoItemInventoryException {
        //ARRANGE
        String name="Snikers";
        
        //There are no snickers left
        try{
            //ACT
            testService.removeOneItemFromInventory(name);
            //ASSERT
            fail("There are no snickers left, exception should be thrown");
        } catch (VendingMachineNoItemInventoryException e) {  
        }
        
        String kitkat = "Kitkat";
        try{
            //ACT
            testService.removeOneItemFromInventory(kitkat);
        } catch (VendingMachineNoItemInventoryException e) {
            if (testService.getItemInventory(kitkat)>0){
                fail("Kitkats are in stock, exception should not be thrown");
            }
        } 
    }
}
