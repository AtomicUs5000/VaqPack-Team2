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

import com.lowagie.text.DocumentException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.xml.bind.DatatypeConverter;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

public class VP_DataManager {

    private final VP_GUIController controller;
    private final VP_DatabaseManager dbManager;
    private final VP_FileManager fileM;

    /*------------------------------------------------------------------------*
     * VP_DataManager()
     * - Constructor.
     * - Parameter VP_GUIController stored to access events
     * - Parameter VP_Loader stored to manipulate its progress
     * - Creates a Database Manager, a File Manager, a data-to-html convertor,
     *   and a html-to-pdf convertor.
     *------------------------------------------------------------------------*/
    protected VP_DataManager(VP_GUIController controller) {
        //-------- Initialization Start ----------\\
        this.controller = controller;
        dbManager = new VP_DatabaseManager(controller);
        fileM = new VP_FileManager();
        //-------- Initialization End ------------\\
    }

    /*------------------------------------------------------------------------*
     * checkDBLocation()
     * - Calls checkDBRunning() of the Database Manager.
     * - No parameters.
     * - No return.
     *------------------------------------------------------------------------*/
    protected void checkDBLocation() throws SQLException {
        dbManager.checkDBRunning();
    }

    /*------------------------------------------------------------------------*
     * retrieveDBLocation()
     * - Gets the database server url and port from the properties file that is
     *   accessible by the File Manager and sends the values to Database Manager.
     * - No parameters.
     * - No return.
     *------------------------------------------------------------------------*/
    protected void retrieveDBLocation() throws FileNotFoundException, IOException {
        //-------- Initialization Start ----------\\
        String[] loc = fileM.retrieveUrlPort();
        //-------- Initialization End ------------\\

        dbManager.setUrl(loc[0]);
        dbManager.setPort(loc[1]);
    }

    /*------------------------------------------------------------------------*
     * storeDBLocation()
     * - Sends the database url and port to the Database Manager and to the
     *   File Manager for permanent storage in the properties file.
     * - Paramter loc is a string array of the url and port provided by the user.
     * - No return.
     *------------------------------------------------------------------------*/
    protected void storeDBLocation(String[] loc) throws IOException {
        dbManager.setUrl(loc[0]);
        dbManager.setPort(loc[1]);
        fileM.storeUrlPort(loc);
    }

    /*------------------------------------------------------------------------*
     * getCurrentLocation()
     * - Gets the current database url and port from the Database Manager.
     * - No parameters.
     * - Return a string array of the database url and port.
     *------------------------------------------------------------------------*/
    protected String[] getCurrentLocation() {
        return new String[]{dbManager.getUrl(), dbManager.getPort()};
    }

    /*------------------------------------------------------------------------*
     * retrieveAdmin()
     * - Gets the database admin credentials from the encrypted properties file
     *   that is accessible by the File Mangager and sends the values to the 
     *   Database Manager to be used by all queries.
     * - No parameters.
     * - No return.
     *------------------------------------------------------------------------*/
    protected void retrieveAdmin() throws IOException, FileNotFoundException,
            NoSuchAlgorithmException, NoSuchPaddingException,
            InvalidKeyException, InvalidAlgorithmParameterException,
            IllegalBlockSizeException, BadPaddingException {
        //-------- Initialization Start ----------\\
        String[] cred = fileM.retrieveAdminEncrypted(dbManager.getUrl(), dbManager.getPort());
        //-------- Initialization End ------------\\

        dbManager.setAdminUserName(cred[0]);
        dbManager.setAdminPassword(cred[1]);
    }

    /*------------------------------------------------------------------------*
     * storeAdminCred()
     * - Sends the database admin credentials to the the File Mangager for
     *   encryption and storing in the properties file, and sends the values to 
     *   the Database Manager to be used by all queries.
     * - Parameters cred is a string array of database admin credentials.
     * - No return.
     *------------------------------------------------------------------------*/
    protected void storeAdminCred(String[] cred) throws IOException,
            FileNotFoundException, NoSuchAlgorithmException,
            NoSuchPaddingException, InvalidKeyException,
            InvalidAlgorithmParameterException, UnsupportedEncodingException,
            IllegalBlockSizeException, BadPaddingException {
        dbManager.setAdminUserName(cred[0]);
        dbManager.setAdminPassword(cred[1]);
        fileM.storeAdminUserPass(cred);
    }

    /*------------------------------------------------------------------------*
     * checkDBSchemaExists()
     * - Calls checkSchema() of the Database Manager.
     * - No parameters.
     * - No return.
     *------------------------------------------------------------------------*/
    protected void checkDBSchemaExists() throws SQLException {
        dbManager.checkSchema();
    }

    /*------------------------------------------------------------------------*
     * checkDBTable()
     * - Calls a specific function of Database Manger to check a table.
     * - Parameters tableNumb determines which database table to check.
     * - No return.
     *------------------------------------------------------------------------*/
    protected void checkDBTable(int tableNumb) throws SQLException {
        switch (tableNumb) {
            case 0:
                dbManager.checkUserTable();
                break;
            case 1:
                dbManager.checkAcessLevelTable();
                break;
            case 2:
                dbManager.checkRegisteringUserTable();
                break;
            case 3:
                dbManager.checkResetCodeTable();
                break;
            case 4:
                dbManager.checkBusinessCardTable();
                break;
            case 5:
                dbManager.checkContactTable();
                break;
            case 6:
                dbManager.checkCoverLetterTable();
                break;
            case 7:
                dbManager.checkResumeTable();
                break;
            case 8:
                dbManager.checkUserDataTable();
                break;
            case 9:
                dbManager.checkCustomThemeTable();
                break;
            case 10:
                dbManager.checkBusinessCardPDFTable();
                break;
            case 11:
                dbManager.checkCoverLetterPDFTable();
                break;
            case 12:
                dbManager.checkResPDFTable();
                break;
            case 13:
                dbManager.checkResHTMLTable();
                break;
        }
    }

    /*------------------------------------------------------------------------*
     * contructUserAccess()
     * - Calls buildAccessLevels() of the Database Manager.
     * - No parameters.
     * - No return.
     *------------------------------------------------------------------------*/
    protected void contructUserAccess() throws SQLException {
        dbManager.buildAccessLevels();
    }

    /*------------------------------------------------------------------------*
     * searchForVPAdmin()
     * - Calls findAdminUser() of the Database Manager.
     * - No parameters.
     * - Returns a boolean value indicating whether or not a VaqPack admin user
     *   has been created and exists in the database user table. This is
     *   necessary in case the user cancels or the program exists in the middle
     *   of first-run initialization.
     *------------------------------------------------------------------------*/
    protected boolean searchForVPAdmin() throws SQLException {
        return dbManager.findAdminUser();
    }

    /*------------------------------------------------------------------------*
     * createVPAdmin()
     * - Calls createVaqPackAdmin() of the Database Manager.
     * - Parameters cred is a string array of the database admin user
     *   credentials and the VaqPack admin user credentials.
     * - Returns a boolean value indicating if the database admin user is
     *   valid, allowing the creation of this VP admin user account.
     *------------------------------------------------------------------------*/
    protected boolean createVPAdmin(String[] cred) throws SQLException,
            NoSuchAlgorithmException, UnsupportedEncodingException {
        //-------- Initialization Start ----------\\
        boolean adminChecked;
        String[] ccMail = {};
        String code = generatAccessCode();
        adminChecked = dbManager.createVaqPackAdmin(cred, code, hashPassword(cred[3]));
        String msg = "A VaqPack administrator account has been created associated with this email address.\n"
                + "Please log into VaqPack and enter the following code:\n\n"
                + code + "\n\n"
                + "The code will expire in 1 hour. If you do not enter the code within this timeframe, "
                + "the system administrator will have to set up your account again.\n"
                + "The code only needs to be entered once to activate your account.\n\n"
                + "This is an automated message from the VaqPack software. Please do not reply.";
        VP_Mail regEmail = new VP_Mail(controller, cred[2], ccMail, "VaqPack Registration", msg, "", null);
        //-------- Initialization End ------------\\

        regEmail.setDaemon(true);
        regEmail.start();
        return adminChecked;
    }

    /*------------------------------------------------------------------------*
     * userLogin()
     * - Calls hashPassword() and then sends it to the Database Manager
     *   with attemptUserLogin(). If all is well, a new VP_User is created.
     * - Parameter cred is a string array of user credentials.
     * - Returns an integer that is returned from attemptUserLogin().
     *------------------------------------------------------------------------*/
    protected int userLogin(String[] cred) throws SQLException,
            NoSuchAlgorithmException, UnsupportedEncodingException {
        //-------- Initialization Start ----------\\
        int loginStatus;
        //-------- Initialization End ------------\\
        cred[1] = hashPassword(cred[1]);
        loginStatus = dbManager.attemptUserLogin(cred, controller.getCurrentUser());
        if (loginStatus >= 0) {
            controller.newUserSet();
        }
        return loginStatus;
    }

    /*------------------------------------------------------------------------*
     * verifyRegAccess()
     * - Calls hashPassword() and then sends it to the Database Manager
     *   with verifyUserAccessCode().
     * - Parameter cred is a string array of user credentials.
     * - Returns a boolean value that is returned from verifyUserAccessCode().
     *------------------------------------------------------------------------*/
    protected boolean verifyRegAccess(String[] cred) throws SQLException,
            NoSuchAlgorithmException, UnsupportedEncodingException {
        //-------- Initialization Start ----------\\
        cred[1] = hashPassword(cred[1]);
        boolean success = dbManager.verifyUserAccessCode(cred);
        //-------- Initialization End ------------\\
        
        return success;
    }

    /*------------------------------------------------------------------------*
     * resendAccess()
     * - Calls hashPassword() and then sends it to the Database Manager
     *   with resendUserAccessCode().
     * - If the entered user email exists in the database, an email is sent
     *   containing a new registration access code generated by 
     *   generatAccessCode().
     * - Parameter cred is a string array of user credentials.
     * - No return..
     *------------------------------------------------------------------------*/
    protected void resendAccess(String[] cred) throws SQLException,
            NoSuchAlgorithmException, UnsupportedEncodingException {
        //-------- Initialization Start ----------\\
        String msg,
                code = generatAccessCode();
        String[] ccMail = {};
        VP_Mail regEmail;
        //-------- Initialization End ------------\\

        cred[1] = hashPassword(cred[1]);
        if (dbManager.resendUserAccessCode(cred, code)) {
            msg = "Please log into VaqPack and enter the following code:\n\n"
                    + code + "\n\n"
                    + "The code will expire in 1 hour. If you do not enter the code within this timeframe, "
                    + "you will have to register your account again.\n"
                    + "The code only needs to be entered once to activate your account.\n\n"
                    + "This is an automated message from the VaqPack software. Please do not reply.";
            regEmail = new VP_Mail(controller, cred[0], ccMail, "VaqPack Registration", msg, "", null);
            regEmail.setDaemon(true);
            regEmail.start();
        }
    }

    /*------------------------------------------------------------------------*
     * findUserForReset()
     * - Calls generatAccessCode() and then sends it to the Database Manager
     *   along with the user email through findUserOrRegUserForReset().
     * - If the enetered user email exists in the database, and if the user
     *   has not reset their password in over 24 hours, an email is sent
     *   containing the code.
     * - Parameter email is a string email of the user.
     * - Returns the integer returned by findUserOrRegUserForReset().
     *------------------------------------------------------------------------*/
    protected int findUserForReset(String email) throws SQLException {
        //-------- Initialization Start ----------\\
        String msg,
                code = generatAccessCode();
        String[] ccMail = {};
        VP_Mail resetEmail;
        int userStatus = dbManager.findUserOrRegUserForReset(email, code);
        //-------- Initialization End ------------\\

        if (userStatus == 2) {
            msg = "Please enter the following code to reset your password:\n\n"
                    + code + "\n\n"
                    + "The code will expire in 1 hour.\n\n"
                    + "This is an automated message from the VaqPack software. Please do not reply.";
            resetEmail = new VP_Mail(controller, email, ccMail, "VaqPack Password Reset", msg, "", null);
            resetEmail.setDaemon(true);
            resetEmail.start();
        }
        return userStatus;
    }

    /*------------------------------------------------------------------------*
     * resetPass()
     * - Calls hashPassword() and then sends it to the Database Manager
     *   with resetPassword().
     * - Parameter cred is a string array of user credentials.
     * - Returns an integer that is returned from resetPassword().
     *------------------------------------------------------------------------*/
    protected int resetPass(String[] cred) throws SQLException,
            NoSuchAlgorithmException, UnsupportedEncodingException {
        cred[1] = hashPassword(cred[1]);
        return dbManager.resetPassword(cred);
    }

    /*------------------------------------------------------------------------*
     * regUser()
     * - Calls hashPassword() and generateCode() then sends these values to the 
     *   Database Manager using registerUser() along with the user credentials.
     *   If the user is new, an email is sent containing the access code.
     * - Parameter cred is a string array of user credentials.
     * - Returns an integer value that is returned from registerUser().
     *------------------------------------------------------------------------*/
    protected int regUser(String[] cred) throws SQLException,
            NoSuchAlgorithmException, UnsupportedEncodingException {
        //-------- Initialization Start ----------\\
        int registerStatus;
        String msg,
                code = generatAccessCode();
        String[] ccMail = {};
        VP_Mail registerEmail;
        //-------- Initialization End ------------\\

        cred[1] = hashPassword(cred[1]);
        registerStatus = dbManager.registerUser(cred, code);
        if (registerStatus == 2) {
            msg = "Please log into VaqPack and enter the following code:\n\n"
                    + code + "\n\n"
                    + "The code will expire in 1 hour. If you do not enter the code within this timeframe, "
                    + "you will have to register your account again.\n"
                    + "The code only needs to be entered once to activate your account.\n\n"
                    + "This is an automated message from the VaqPack software. Please do not reply.";
            registerEmail = new VP_Mail(controller, cred[0], ccMail, "VaqPack Registration", msg, "", null);
            registerEmail.setDaemon(true);
            registerEmail.start();
        }
        return registerStatus;
    }

    /*------------------------------------------------------------------------*
     * checkEmail()
     * - Compares the string parameter against a REGEX pattern to
     *   determinine if an email address is well-formed.
     * - Parameter email is a string of a potential email address.
     * - Returns a boolean value indicating if the string parameter is a
     *   well-formed address.
     *------------------------------------------------------------------------*/
    protected boolean checkEmail(String email) {
        //-------- Initialization Start ----------\\
        String regex;
        Pattern pattern;
        Matcher matcher;
        //-------- Initialization End ------------\\

        if (email.length() >= 3) {
            regex
                    = "^[\\w!#$%&’*+/=?`{|}~^-]+(?:\\.[\\w!#$%&’*+/=?`{|}~^-]+)*"
                    + "@(?:[A-Z0-9-]+\\.)+[A-Z]{2,63}$";
            pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
            matcher = pattern.matcher(email);
            return matcher.matches();
        } else {
            return false;
        }
    }
    
    /*------------------------------------------------------------------------*
     * saveUserData()
     * - Calls storeUserData() in the database manager passing user data.
     * - No parameters.
     * - No return.
     *------------------------------------------------------------------------*/
    protected void saveUserData() throws SQLException {
        dbManager.storeUserData(controller.getCurrentUser());
    }
    
    /*------------------------------------------------------------------------*
     * saveBCardData()
     * - Calls generateBCardPDF() in the file manager passing user data.
     *   Then calls storeBCardData in the database manager to store the pdf.
     * - No parameters.
     * - No return.
     *------------------------------------------------------------------------*/
    protected void saveBCardData() throws SQLException, TransformerException,
            ParserConfigurationException, IOException, FileNotFoundException, 
            DocumentException {
        File bcpdf = fileM.generateBCardPDF(controller.getCurrentUser());
        dbManager.storeBCardData(controller.getCurrentUser(), bcpdf);
        if (bcpdf != null && bcpdf.exists()) {
            bcpdf.delete();
        }
    }
    
    /*------------------------------------------------------------------------*
     * saveCovLetData()
     * - Calls generateCovLetPDF() in the file manager passing user data.
     *   Then calls storeCovLetData in the database manager to store the pdf.
     * - No parameters.
     * - No return.
     *------------------------------------------------------------------------*/
    protected void saveCovLetData() throws SQLException, TransformerException,
            ParserConfigurationException, IOException, FileNotFoundException, 
            DocumentException {
        File clpdf = fileM.generateCovLetPDF(controller.getCurrentUser());
        dbManager.storeCovLetData(controller.getCurrentUser(), clpdf);
        if (clpdf != null && clpdf.exists()) {
            clpdf.delete();
        }
    }
    
    /*------------------------------------------------------------------------*
     * loadCovLetData()
     * - 
     *------------------------------------------------------------------------*/
    protected void loadCovLet(int clID) throws SQLException {
        dbManager.loadCovLetData(controller.getCurrentUser(), clID);
    }

    /*------------------------------------------------------------------------*
     * generatAccessCode()
     * - Generates a 16-character string used an an access code for verifying
     *   that a user has enetered a valid email address.
     * - Returns a random string of 16 characters.
     *------------------------------------------------------------------------*/
    private String generatAccessCode() {
        //-------- Initialization Start ----------\\
        String code = "";
        Random rand = new Random();
        //-------- Initialization End ------------\\

        for (int i = 0; i < 16; i++) {
            int thisChar = 48 + rand.nextInt(74);
            if (thisChar > 90 && thisChar < 97) {
                i--;
            } else {
                code += ((char) (thisChar));
            }
        }
        return code;
    }

    /*------------------------------------------------------------------------*
     * hashPassword()
     * - Hashes a password.
     * - Parameter pass is the string to be hashed.
     * - Returns a string of the hashed password.
     *------------------------------------------------------------------------*/
    private String hashPassword(String pass) throws NoSuchAlgorithmException,
            UnsupportedEncodingException {
        //-------- Initialization Start ----------\\
        MessageDigest mDig = MessageDigest.getInstance("SHA-256");
        byte[] passHash = mDig.digest(pass.getBytes("UTF-8"));
        //-------- Initialization End ------------\\

        return DatatypeConverter.printHexBinary(passHash);
    }

    /*##########################################################################
     * SUBCLASSES
     *########################################################################*/
    /*##########################################################################
     * SETTERS AND GETTERS
     *########################################################################*/
}
