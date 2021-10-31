/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mthree.vendingmachine.dao;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;

/**
 *
 * @author salon
 */
public class VendingMachineAuditDaoFileImpl implements VendingMachineAuditDao {
    
    private final String AUDIT_FILE;
    //Default constructor
    public VendingMachineAuditDaoFileImpl() {
        this.AUDIT_FILE = "audit.txt";
    }
    //Contructor for testing
    public VendingMachineAuditDaoFileImpl(String auditTestFile) {
        this.AUDIT_FILE = auditTestFile;
    }
    

    @Override
    public void writeAuditEntry(String entry) throws VendingMachinePersistenceException {
         PrintWriter out;
         try {
             out = new PrintWriter(new FileWriter(AUDIT_FILE, true));
         } catch (IOException e) {
             throw new VendingMachinePersistenceException("Could not persist audit information", e);
         }
         LocalDateTime timestamp = LocalDateTime.now();
         out.println(timestamp.toString() + " : " +entry);
         out.flush();
    }
    
}
