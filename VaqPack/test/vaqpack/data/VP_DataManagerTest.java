/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vaqpack.data;

import java.util.concurrent.CountDownLatch;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import vaqpack.VP_GUIController;

/**
 *
 * @author erik
 */
public class VP_DataManagerTest {
    
    public VP_DataManagerTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of checkDBLocation method, of class VP_DataManager.
     * @throws java.lang.Exception
     */
    @Test
    public void testCheckDBLocation() throws Exception {
        System.out.println("checkDBLocation");
        VP_DataManager instance = null;
        instance.checkDBLocation();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of retrieveDBLocation method, of class VP_DataManager.
     */
    @Test
    public void testRetrieveDBLocation() throws Exception {
        System.out.println("retrieveDBLocation");
        VP_DataManager instance = null;
        instance.retrieveDBLocation();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of storeDBLocation method, of class VP_DataManager.
     */
    @Test
    public void testStoreDBLocation() throws Exception {
        System.out.println("storeDBLocation");
        String[] loc = null;
        VP_DataManager instance = null;
        instance.storeDBLocation(loc);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getCurrentLocation method, of class VP_DataManager.
     */
    @Test
    public void testGetCurrentLocation() {
        System.out.println("getCurrentLocation");
        VP_DataManager instance = null;
        String[] expResult = null;
        String[] result = instance.getCurrentLocation();
        assertArrayEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of retrieveAdmin method, of class VP_DataManager.
     */
    @Test
    public void testRetrieveAdmin() throws Exception {
        System.out.println("retrieveAdmin");
        VP_DataManager instance = null;
        instance.retrieveAdmin();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of storeAdminCred method, of class VP_DataManager.
     */
    @Test
    public void testStoreAdminCred() throws Exception {
        System.out.println("storeAdminCred");
        String[] cred = null;
        VP_DataManager instance = null;
        instance.storeAdminCred(cred);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of checkDBSchemaExists method, of class VP_DataManager.
     */
    @Test
    public void testCheckDBSchemaExists() throws Exception {
        System.out.println("checkDBSchemaExists");
        VP_DataManager instance = null;
        instance.checkDBSchemaExists();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of checkDBTable method, of class VP_DataManager.
     */
    @Test
    public void testCheckDBTable() throws Exception {
        System.out.println("checkDBTable");
        int tableNumb = 0;
        VP_DataManager instance = null;
        instance.checkDBTable(tableNumb);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of contructUserAccess method, of class VP_DataManager.
     */
    @Test
    public void testContructUserAccess() throws Exception {
        System.out.println("contructUserAccess");
        VP_DataManager instance = null;
        instance.contructUserAccess();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of searchForVPAdmin method, of class VP_DataManager.
     */
    @Test
    public void testSearchForVPAdmin() throws Exception {
        System.out.println("searchForVPAdmin");
        VP_DataManager instance = null;
        boolean expResult = false;
        boolean result = instance.searchForVPAdmin();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of createVPAdmin method, of class VP_DataManager.
     */
    @Test
    public void testCreateVPAdmin() throws Exception {
        System.out.println("createVPAdmin");
        String[] cred = null;
        VP_DataManager instance = null;
        boolean expResult = false;
        boolean result = instance.createVPAdmin(cred);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of userLogin method, of class VP_DataManager.
     */
    @Test
    public void testUserLogin() throws Exception {
        System.out.println("userLogin");
        String[] cred = null;
        VP_DataManager instance = null;
        int expResult = 0;
        int result = instance.userLogin(cred);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of verifyRegAccess method, of class VP_DataManager.
     */
    @Test
    public void testVerifyRegAccess() throws Exception {
        System.out.println("verifyRegAccess");
        String[] cred = null;
        VP_DataManager instance = null;
        boolean expResult = false;
        boolean result = instance.verifyRegAccess(cred);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of resendAccess method, of class VP_DataManager.
     */
    @Test
    public void testResendAccess() throws Exception {
        System.out.println("resendAccess");
        String[] cred = null;
        VP_DataManager instance = null;
        instance.resendAccess(cred);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of findUserForReset method, of class VP_DataManager.
     */
    @Test
    public void testFindUserForReset() throws Exception {
        System.out.println("findUserForReset");
        String email = "";
        VP_DataManager instance = null;
        int expResult = 0;
        int result = instance.findUserForReset(email);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of checkCurrentPassword method, of class VP_DataManager.
     */
    @Test
    public void testCheckCurrentPassword() throws Exception {
        System.out.println("checkCurrentPassword");
        String password = "";
        VP_DataManager instance = null;
        boolean expResult = false;
        boolean result = instance.checkCurrentPassword(password);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of changePass method, of class VP_DataManager.
     */
    @Test
    public void testChangePass() throws Exception {
        System.out.println("changePass");
        String password = "";
        VP_DataManager instance = null;
        instance.changePass(password);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of resetPass method, of class VP_DataManager.
     */
    @Test
    public void testResetPass() throws Exception {
        System.out.println("resetPass");
        String[] cred = null;
        VP_DataManager instance = null;
        int expResult = 0;
        int result = instance.resetPass(cred);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of regUser method, of class VP_DataManager.
     */
    @Test
    public void testRegUser() throws Exception {
        System.out.println("regUser");
        String[] cred = null;
        VP_DataManager instance = null;
        int expResult = 0;
        int result = instance.regUser(cred);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of checkEmail method, of class VP_DataManager.
     */
    @Test
    public void testCheckEmail() {
        System.out.println("checkEmail");
        String email = "";
        VP_DataManager instance = null;
        boolean expResult = false;
        boolean result = instance.checkEmail(email);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of saveUserData method, of class VP_DataManager.
     */
    @Test
    public void testSaveUserData() throws Exception {
        System.out.println("saveUserData");
        VP_DataManager instance = null;
        instance.saveUserData();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of saveBCardData method, of class VP_DataManager.
     */
    @Test
    public void testSaveBCardData() throws Exception {
        System.out.println("saveBCardData");
        VP_DataManager instance = null;
        instance.saveBCardData();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of saveCovLetData method, of class VP_DataManager.
     */
    @Test
    public void testSaveCovLetData() throws Exception {
        System.out.println("saveCovLetData");
        VP_DataManager instance = null;
        instance.saveCovLetData();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of saveResume method, of class VP_DataManager.
     */
    @Test
    public void testSaveResume() throws Exception {
        System.out.println("saveResume");
        int section = 0;
        VP_DataManager instance = null;
        instance.saveResume(section);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of addContact method, of class VP_DataManager.
     */
    @Test
    public void testAddContact() throws Exception {
        System.out.println("addContact");
        String email = "";
        String name = "";
        VP_DataManager instance = null;
        instance.addContact(email, name);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of deleteContact method, of class VP_DataManager.
     */
    @Test
    public void testDeleteContact() throws Exception {
        System.out.println("deleteContact");
        String email = "";
        String name = "";
        VP_DataManager instance = null;
        instance.deleteContact(email, name);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of sendAttachments method, of class VP_DataManager.
     */
    @Test
    public void testSendAttachments() throws Exception {
        System.out.println("sendAttachments");
        String email = "";
        boolean sendResHTML = false;
        boolean sendResPDF = false;
        boolean sendBCHTML = false;
        boolean sendBCPDF = false;
        boolean sendCLHTML = false;
        boolean sendCLPDF = false;
        VP_DataManager instance = null;
        instance.sendAttachments(email, sendResHTML, sendResPDF, sendBCHTML, sendBCPDF, sendCLHTML, sendCLPDF);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of printDocument method, of class VP_DataManager.
     */
    @Test
    public void testPrintDocument() throws Exception {
        System.out.println("printDocument");
        int type = 0;
        VP_DataManager instance = null;
        instance.printDocument(type);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of loadCovLet method, of class VP_DataManager.
     */
    @Test
    public void testLoadCovLet() throws Exception {
        System.out.println("loadCovLet");
        int clID = 0;
        VP_DataManager instance = null;
        instance.loadCovLet(clID);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getController method, of class VP_DataManager.
     */
    @Test
    public void testGetController() {
        System.out.println("getController");
        VP_DataManager instance = null;
        VP_GUIController expResult = null;
        VP_GUIController result = instance.getController();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getDbBusy method, of class VP_DataManager.
     */
    @Test
    public void testGetDbBusy() {
        System.out.println("getDbBusy");
        VP_DataManager instance = null;
        CountDownLatch expResult = null;
        CountDownLatch result = instance.getDbBusy();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setDbBusy method, of class VP_DataManager.
     */
    @Test
    public void testSetDbBusy() {
        System.out.println("setDbBusy");
        CountDownLatch dbBusy = null;
        VP_DataManager instance = null;
        instance.setDbBusy(dbBusy);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
