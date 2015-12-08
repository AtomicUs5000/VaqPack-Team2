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
 * FILE ID 3000
 *-----------------------------------------------------------------------------*/
package vaqpack.data;

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
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.xml.bind.DatatypeConverter;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import vaqpack.VP_GUIController;
import vaqpack.peripherals.VP_Mail;

public class VP_DataManager {

    private final VP_GUIController controller;
    private final VP_DatabaseManager dbManager;
    private final VP_FileManager fileM;
    private CountDownLatch dbBusy;

    /*------------------------------------------------------------------------*
     * VP_DataManager()
     * - Constructor.
     * - Parameter VP_GUIController stored to access events
     * - Parameter VP_Loader stored to manipulate its progress
     * - Creates a Database Manager, a File Manager, a data-to-html convertor,
     *   and a html-to-pdf convertor.
     *------------------------------------------------------------------------*/
    public VP_DataManager(VP_GUIController controller) {
        //-------- Initialization Start ----------\\
        this.controller = controller;
        dbManager = new VP_DatabaseManager(this);
        fileM = new VP_FileManager(this);
        dbBusy = new CountDownLatch(0);
        //-------- Initialization End ------------\\
    }

    /*------------------------------------------------------------------------*
     * checkDBLocation()
     * - Calls checkDBRunning() of the Database Manager.
     * - No parameters.
     * - No return.
     *------------------------------------------------------------------------*/
    public void checkDBLocation() throws SQLException {
        dbManager.checkDBRunning();
    }

    /*------------------------------------------------------------------------*
     * retrieveDBLocation()
     * - Gets the database server url and port from the properties file that is
     *   accessible by the File Manager and sends the values to Database Manager.
     * - No parameters.
     * - No return.
     *------------------------------------------------------------------------*/
    public void retrieveDBLocation() throws FileNotFoundException, IOException {
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
    public void storeDBLocation(String[] loc) throws IOException {
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
    public String[] getCurrentLocation() {
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
    public void retrieveAdmin() throws IOException, FileNotFoundException,
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
    public void storeAdminCred(String[] cred) throws IOException,
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
    public void checkDBSchemaExists() throws SQLException {
        dbManager.checkSchema();
    }

    /*------------------------------------------------------------------------*
     * checkDBTable()
     * - Calls a specific function of Database Manger to check a table.
     * - Parameters tableNumb determines which database table to check.
     * - No return.
     *------------------------------------------------------------------------*/
    public void checkDBTable(int tableNumb) throws SQLException {
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
                dbManager.checkBusinessCardHTMLTable();
                break;
            case 12:
                dbManager.checkCoverLetterPDFTable();
                break;
            case 13:
                dbManager.checkCoverLetterHTMLTable();
                break;
            case 14:
                dbManager.checkResPDFTable();
                break;
            case 15:
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
    public void contructUserAccess() throws SQLException {
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
    public boolean searchForVPAdmin() throws SQLException {
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
    public boolean createVPAdmin(String[] cred) throws SQLException,
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
        VP_Mail regEmail = new VP_Mail(controller, cred[2], ccMail, "VaqPack Registration", msg, null, null);
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
    public int userLogin(String[] cred) throws SQLException,
            NoSuchAlgorithmException, UnsupportedEncodingException {
        //-------- Initialization Start ----------\\
        int loginStatus;
        //-------- Initialization End ------------\\
        cred[1] = hashPassword(cred[1]);
        loginStatus = dbManager.attemptUserLogin(cred);
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
    public boolean verifyRegAccess(String[] cred) throws SQLException,
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
    public void resendAccess(String[] cred) throws SQLException,
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
            regEmail = new VP_Mail(controller, cred[0], ccMail, "VaqPack Registration", msg, null, null);
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
    public int findUserForReset(String email) throws SQLException {
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
            resetEmail = new VP_Mail(controller, email, ccMail, "VaqPack Password Reset", msg, null, null);
            resetEmail.setDaemon(true);
            resetEmail.start();
        }
        return userStatus;
    }
    
    public boolean checkCurrentPassword(String password) throws SQLException, 
            NoSuchAlgorithmException, UnsupportedEncodingException {
        return(dbManager.checkPassword(hashPassword(password)));
    }
    
    public void changePass(String password) throws SQLException, 
            NoSuchAlgorithmException, UnsupportedEncodingException {
        //-------- Initialization Start ----------\\
        String msg;
        String[] ccMail = {};
        VP_Mail changePassEmail;
        //-------- Initialization End ------------\\
        
        dbManager.changePassword(hashPassword(password));
        msg = "This message is to inform you that your password has recently been changed for the VaqPack account "
                + "associated with this email address.\nIf you have not changed your password, your VaqPack account might have "
                + "been compromised and you should contact the VaqPack administrator.\n\n"
                + "This is an automated message from the VaqPack software. Please do not reply.";
        changePassEmail = new VP_Mail(controller, controller.getCurrentUser().getEmail().getValueSafe(),
                ccMail, "VaqPack Administration", msg, null, null);
        changePassEmail.setDaemon(true);
        changePassEmail.start();
    }

    /*------------------------------------------------------------------------*
     * resetPass()
     * - Calls hashPassword() and then sends it to the Database Manager
     *   with resetPassword().
     * - Parameter cred is a string array of user credentials.
     * - Returns an integer that is returned from resetPassword().
     *------------------------------------------------------------------------*/
    public int resetPass(String[] cred) throws SQLException,
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
    public int regUser(String[] cred) throws SQLException,
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
            registerEmail = new VP_Mail(controller, cred[0], ccMail, "VaqPack Registration", msg, null, null);
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
    public boolean checkEmail(String email) {
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
    public void saveUserData() throws SQLException {
        dbManager.storeUserData();
    }

    /*------------------------------------------------------------------------*
     * saveBCardData()
     * - Calls generateBCardHTMLandPDF() in the file manager.
     * - No parameters.
     * - No return.
     *------------------------------------------------------------------------*/
    public void saveBCardData() throws SQLException, TransformerException,
            ParserConfigurationException, IOException, FileNotFoundException,
            DocumentException {
        File[] bcFiles = fileM.generateBCardHTMLandPDF();
        dbManager.storeBCardHTML(bcFiles[0]);
        dbManager.storeBCardPDF(bcFiles[1]);
        if (bcFiles[0] != null && bcFiles[0].exists()) {
            bcFiles[0].delete();
        }
        if (bcFiles[1] != null && bcFiles[1].exists()) {
            bcFiles[1].delete();
        }
    }

    /*------------------------------------------------------------------------*
     * saveCovLetData()
     * - Calls generateCovLetHTMLandPDF() in the file manager.
     * - No parameters.
     * - No return.
     *------------------------------------------------------------------------*/
    public void saveCovLetData() throws SQLException, TransformerException,
            ParserConfigurationException, IOException, FileNotFoundException,
            DocumentException {
        File[] clFiles = fileM.generateCovLetHTMLandPDF();
        dbManager.storeCovLetPDF(clFiles[1]);
        // html must come after pdf in this case because the cover letter id
        //    is generated in the storeCovLetPDF function.
        dbManager.storeCovLetHTML(clFiles[0]);
        if (clFiles[0] != null && clFiles[0].exists()) {
            clFiles[0].delete();
        }
        if (clFiles[1] != null && clFiles[1].exists()) {
            clFiles[1].delete();
        }
    }

    public void saveResume(int section) throws SQLException, TransformerException,
            ParserConfigurationException, IOException, FileNotFoundException,
            DocumentException {
        dbManager.storeResumeData(section);
        if (controller.getCurrentUser().getResume().hasCompletedResume()) {
            File[] resFiles = new File[2];
            resFiles = fileM.generateResHTMLandPDF();
            dbManager.storeResHTML(resFiles[0]);
            dbManager.storeResPDF(resFiles[1]);
            if (resFiles[0] != null && resFiles[0].exists()) {
                resFiles[0].delete();
            }
            if (resFiles[1] != null && resFiles[1].exists()) {
                resFiles[1].delete();
            }
        }
    }
    
    public void addContact(String email, String name) throws SQLException {
        dbManager.addUserContact(email, name);
    }
    
    public void deleteContact(String email, String name) throws SQLException {
        dbManager.deleteUserContact(email, name);
    }
    
    public void sendAttachments(String email, boolean sendResHTML, boolean sendResPDF,
            boolean sendBCHTML, boolean sendBCPDF, boolean sendCLHTML, boolean sendCLPDF) 
            throws SQLException, IOException {
        //-------- Initialization Start ----------\\
        File temp1 = null,
                temp2 = null,
                temp3 = null,
                temp4 = null,
                temp5 = null,
                temp6 = null;
        ArrayList<File> files = new ArrayList();
        ArrayList<String> filenames = new ArrayList();
        String[] ccMail = {};
        String msg;
        VP_Mail sendAttachEmail;
        //-------- Initialization End ------------\\
        
        if (sendResHTML) {
            temp1 = dbManager.retrieveFile(1);
            if (temp1 != null) {
                files.add(temp1);
                filenames.add(controller.getCurrentUser().getLastName().getValueSafe() + "_resume.html");
            }
        }
        if (sendResPDF) {
            temp2 = dbManager.retrieveFile(2);
            if (temp2 != null) {
                files.add(temp2);
                filenames.add(controller.getCurrentUser().getLastName().getValueSafe() + "_resume.pdf");
            }
        }
        if (sendBCHTML) {
            temp3 = dbManager.retrieveFile(3);
            if (temp3 != null) {
                files.add(temp3);
                filenames.add(controller.getCurrentUser().getLastName().getValueSafe() + "_business_card.html");
            }
        }
        if (sendBCPDF) {
            temp4 = dbManager.retrieveFile(4);
            if (temp4 != null) {
                files.add(temp4);
                filenames.add(controller.getCurrentUser().getLastName().getValueSafe() + "_business_card.pdf");
            }
        }
        if (sendCLHTML) {
            temp5 = dbManager.retrieveFile(5);
            if (temp5 != null) {
                files.add(temp5);
                filenames.add(controller.getCurrentUser().getLastName().getValueSafe() + "_cover_letter.html");
            }
        }
        if (sendCLPDF) {
            temp6 = dbManager.retrieveFile(6);
            if (temp6 != null) {
                files.add(temp6);
                filenames.add(controller.getCurrentUser().getLastName().getValueSafe() + "_cover_letter.pdf");
            }
        }
        if (files.size() > 0) {
            msg = controller.getCurrentUser().getFirstName().getValueSafe() + " " +
                    controller.getCurrentUser().getLastName().getValueSafe() +
                    " is sending you files using VaqPack. If you believe that you have received this email in error, "
                    + "please contact " + controller.getCurrentUser().getFirstName().getValueSafe() + " " +
                    controller.getCurrentUser().getLastName().getValueSafe() + " at " +
                    controller.getCurrentUser().getDocEmail() + ".";
            sendAttachEmail = new VP_Mail(controller, email, ccMail, "VaqPack Mailer", msg, filenames, files);
            sendAttachEmail.setDaemon(true);
            sendAttachEmail.start();
        }
    }

    /*------------------------------------------------------------------------*
     * loadCovLetData()
     * - 
     *------------------------------------------------------------------------*/
    public void loadCovLet(int clID) throws SQLException {
        dbManager.loadCovLetData(clID);
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
    protected VP_GUIController getController() {
        return controller;
    }

    public CountDownLatch getDbBusy() {
        return dbBusy;
    }

    public void setDbBusy(CountDownLatch dbBusy) {
        this.dbBusy = dbBusy;
    }
}
