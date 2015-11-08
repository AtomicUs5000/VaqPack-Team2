/*-----------------------------------------------------------------------------*
 * VP_DataManager.java
 * - Stores the data nodes (XML) as built or loaded by the user
 * Authors:
 * - Team-02
 * -- William Dewald (Project Manager)
 * -- Fernando Bazan
 * -- Nathanael Carr
 * -- Erik Lopez
 * -- Raul Saavedra
 * FILE ID 1200
 *-----------------------------------------------------------------------------*/
package vaqpack;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class VP_DataManager {
    private final VP_GUIController controller;
    private final VP_DatabaseManager dbManager;
    private final VP_DataToHtml data2html;
    private final VP_HtmlToPdf   html2pdf;
    private final VP_FileManager fileM;
    private final VP_Loader loader;
    
    /*------------------------------------------------------------------------*
     * VP_DataManager()
     * - Constructor.
     * - parameter VP_GUIController stored to access events
     * - parameter VP_Loader atored to manipulate its progress
     *------------------------------------------------------------------------*/
    protected VP_DataManager(VP_GUIController controller, VP_Loader loader) {
        this.controller = controller;
        this.loader = loader;
        dbManager = new VP_DatabaseManager();
        data2html = new VP_DataToHtml();
        html2pdf = new VP_HtmlToPdf();
        fileM = new VP_FileManager();
    }
    
    
    protected void retrieveDBLocation() throws FileNotFoundException, IOException {
        String[] loc = fileM.retrieveUrlPort();
        dbManager.setUrl(loc[0]);
        dbManager.setPort(loc[1]);
    }
    
    protected void storeDBLocation(String[] loc) throws IOException {
        dbManager.setUrl(loc[0]);
        dbManager.setPort(loc[1]);
        fileM.storeUrlPort(loc);
    }
    
    protected void storeAdminCred(String[] cred) throws IOException,
            FileNotFoundException, NoSuchAlgorithmException,
            NoSuchPaddingException, InvalidKeyException,
            InvalidAlgorithmParameterException, UnsupportedEncodingException,
            IllegalBlockSizeException, BadPaddingException {
        dbManager.setAdminUserName(cred[0]);
        dbManager.setAdminPassword(cred[1]);
        fileM.storeAdminUserPass(cred);
    }
    
    protected String[] getCurrentLocation() {
        return new String[]{dbManager.getUrl(), dbManager.getPort()};
    }
    
    protected void checkDBLocation() throws SQLException {
        dbManager.checkDBRunning();
    }

    protected void retrieveAdmin() throws IOException, FileNotFoundException,
            NoSuchAlgorithmException, NoSuchPaddingException,
            InvalidKeyException, InvalidAlgorithmParameterException,
            IllegalBlockSizeException, BadPaddingException {
            String[] cred = fileM.retrieveAdminEncrypted(dbManager.getUrl(), dbManager.getPort());
        dbManager.setAdminUserName(cred[0]);
        dbManager.setAdminPassword(cred[1]);
    }
    
    protected void checkDBSchemaExists() throws SQLException {
        dbManager.checkSchema();
    }
    
    protected void checkDBTable(int tableNumb) throws SQLException {
        switch (tableNumb) {
            case 0:  dbManager.checkUserTable();                break;
            case 1:  dbManager.checkAcessLevelTable();          break;
            case 2:  dbManager.checkBusinessCardTable();        break;
            case 3:  dbManager.checkContactTable();             break;
            case 4:  dbManager.checkCoverLetterTable();         break;
            case 5:  dbManager.checkResumeTable();              break;
            case 6:  dbManager.checkUserDataTable();            break;
            case 7:  dbManager.checkCustomThemeTable();         break;
            case 8:  dbManager.checkDefaultThemeTable();        break;
            case 9:  dbManager.checkBCHasCustomThemeTable();    break;
            case 10: dbManager.checkBCHasDefaultThemeTable();   break;
            case 11: dbManager.checkBusinessCardPDFTable();     break;
            case 12: dbManager.checkCLHasCustomThemeTable();    break;
            case 13: dbManager.checkCLHasDefaultThemeTable();   break;
            case 14: dbManager.checkCoverLetterPDFTable();      break;
            case 15: dbManager.checkResHasCustomThemeTable();   break;
            case 16: dbManager.checkResHasDefaultThemeTable();  break;
            case 17: dbManager.checkResPDFTable();              break;
            case 18: dbManager.checkResHTMLTable();             break;
        }
    }
    
    protected void contructUserAccess() throws SQLException {
        dbManager.buildAccessLevels();
    }
    
    protected boolean searchForVPAdmin() throws SQLException {
        return dbManager.findAdminUser();
    }
    
    protected boolean createVPAdmin(String[] cred) throws SQLException,
            NoSuchAlgorithmException, UnsupportedEncodingException {
        return dbManager.createVaqPackAdmin(cred);
    }
    
    /*------------------------------------------------------------------------*
     * Setters and Getters
     *------------------------------------------------------------------------*/
}
