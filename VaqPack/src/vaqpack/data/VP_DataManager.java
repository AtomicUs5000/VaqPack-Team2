/**
 * VP_DataManager.java - Manages user data nodes (XML). FILE ID 3000
 */
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

/**
 * Stores the data nodes (XML) as built or loaded by the user.
 *
 * @author William Dewald (Project Manager, Team-02)
 * @author Fernando Bazan
 * @author Erik Lopez
 * @author Raul Saavedra
 * @author Nathanael Carr
 * @version 1.0
 * @since 1.0
 */
public class VP_DataManager {

    private final VP_GUIController controller;
    private final VP_DatabaseManager dbManager;
    private final VP_FileManager fileM;
    private CountDownLatch dbBusy;

    /**
     * Constructor. Creates a Database Manager, a File Manager, a data-to-HTML
     * convertor, and an HTML-to-PDF convertor.
     * 
     * @param controller Stored to access handler events
     * @since 1.0
     */
    public VP_DataManager(VP_GUIController controller) {
        //-------- Initialization Start ----------\\
        this.controller = controller;
        dbManager = new VP_DatabaseManager(this);
        fileM = new VP_FileManager(this);
        dbBusy = new CountDownLatch(0);
        //-------- Initialization End ------------\\
    }

    /**
     * Calls checkDBRunning() of the Database Manager.
     * 
     * @throws SQLException 
     * @since 1.0
     */
    public void checkDBLocation() throws SQLException {
        dbManager.checkDBRunning();
    }

    /**
     * Gets the database server URL and port from the properties file that is 
     * accessible by the File Manager and sends the values to Database Manager.
     * 
     * @throws FileNotFoundException
     * @throws IOException 
     * @since 1.0
     */
    public void retrieveDBLocation() throws FileNotFoundException, IOException {
        //-------- Initialization Start ----------\\
        String[] loc = fileM.retrieveUrlPort();
        //-------- Initialization End ------------\\

        dbManager.setUrl(loc[0]);
        dbManager.setPort(loc[1]);
    }

    /**
     * Sends the database URL and port to the Database Manager and to the File 
     * Manager for permanent storage in the properties file.
     * 
     * @param loc String array storing the URL and port provided by the user.
     * @throws IOException 
     * @since 1.0
     */
    public void storeDBLocation(String[] loc) throws IOException {
        dbManager.setUrl(loc[0]);
        dbManager.setPort(loc[1]);
        fileM.storeUrlPort(loc);
    }

    /**
     * Gets the current database URL and port from the Database Manager.
     * 
     * @return String array of the database URL and port.
     * @since 1.0
     */
    public String[] getCurrentLocation() {
        return new String[]{dbManager.getUrl(), dbManager.getPort()};
    }

    /**
     * Gets the database administrator credentials from the encrypted 
     * properties file that is accessible by the File Manager. Sends the values 
     * to the Database Manager to be used by all queries.
     * 
     * @throws IOException
     * @throws FileNotFoundException
     * @throws NoSuchAlgorithmException
     * @throws NoSuchPaddingException
     * @throws InvalidKeyException
     * @throws InvalidAlgorithmParameterException
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException 
     * @since 1.0
     */
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

    /**
     * Sends the database administrator credentials to the the File Manager for
     * encryption and storing in the properties file. Sends the values to the 
     * Database Manager to be used by all queries.
     * 
     * @param cred String array storing the database administrator credentials.
     * @throws IOException
     * @throws FileNotFoundException
     * @throws NoSuchAlgorithmException
     * @throws NoSuchPaddingException
     * @throws InvalidKeyException
     * @throws InvalidAlgorithmParameterException
     * @throws UnsupportedEncodingException
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException 
     * @since 1.0
     */
    public void storeAdminCred(String[] cred) throws IOException,
            FileNotFoundException, NoSuchAlgorithmException,
            NoSuchPaddingException, InvalidKeyException,
            InvalidAlgorithmParameterException, UnsupportedEncodingException,
            IllegalBlockSizeException, BadPaddingException {
        dbManager.setAdminUserName(cred[0]);
        dbManager.setAdminPassword(cred[1]);
        fileM.storeAdminUserPass(cred);
    }

    /**
     * Calls checkSchema() of the Database Manager.
     * 
     * @throws SQLException 
     * @since 1.0
     */
    public void checkDBSchemaExists() throws SQLException {
        dbManager.checkSchema();
    }

    /**
     * Calls a specific function of Database Manger to check a table.
     * 
     * @param tableNumb Determines which database table to check.
     * @throws SQLException 
     * @since 1.0
     */
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

    /**
     * Calls buildAccessLevels() of the Database Manager.
     * 
     * @throws SQLException 
     * @since 1.0
     */
    public void contructUserAccess() throws SQLException {
        dbManager.buildAccessLevels();
    }

    /**
     * Calls findAdminUser() of the Database Manager.
     * 
     * @return Boolean value indicating whether or not a VaqPack administrator 
     * user has been created and exists in the database user table. This is
     * necessary in case the user cancels or the program exists in the middle
     * of first-run initialization.
     * @throws SQLException 
     * @since 1.0
     */
    public boolean searchForVPAdmin() throws SQLException {
        return dbManager.findAdminUser();
    }

    /**
     * Calls createVaqPackAdmin() of the Database Manager.
     * 
     * @param cred String array of the database administrator user
     * credentials and the VaqPack administrator user credentials.
     * @return Boolean value indicating if the database administrator user is
     * valid, allowing the creation of this VP administrator user account.
     * @throws SQLException
     * @throws NoSuchAlgorithmException
     * @throws UnsupportedEncodingException 
     * @since 1.0
     */
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

    /**
     * Calls hashPassword() and then sends it to the Database Manager with 
     * attemptUserLogin(). If all is well, a new VP_User is created.
     * 
     * @param cred String array of user credentials.
     * @return An integer that is returned from attemptUserLogin().
     * @throws SQLException
     * @throws NoSuchAlgorithmException
     * @throws UnsupportedEncodingException 
     * @since 1.0
     */
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

    /**
     * Calls hashPassword() and then sends it to the Database Manager
     * with verifyUserAccessCode().
     * 
     * @param cred String array of user credentials.
     * @return Boolean value that is returned from verifyUserAccessCode().
     * @throws SQLException
     * @throws NoSuchAlgorithmException
     * @throws UnsupportedEncodingException 
     * @since 1.0
     */
    public boolean verifyRegAccess(String[] cred) throws SQLException,
            NoSuchAlgorithmException, UnsupportedEncodingException {
        //-------- Initialization Start ----------\\
        cred[1] = hashPassword(cred[1]);
        boolean success = dbManager.verifyUserAccessCode(cred);
        //-------- Initialization End ------------\\

        return success;
    }

    /**
     * Calls hashPassword() and then sends it to the Database Manager with 
     * resendUserAccessCode(). If the entered user email exists in the database, 
     * an email is sent containing a new registration access code generated by 
     * generatAccessCode().
     * 
     * @param cred String array of user credentials.
     * @throws SQLException
     * @throws NoSuchAlgorithmException
     * @throws UnsupportedEncodingException 
     * @since 1.0
     */
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

    /**
     * Calls generatAccessCode() and then sends it to the Database Manager along 
     * with the user email through findUserOrRegUserForReset(). If the entered 
     * user email exists in the database, and if the user has not reset their 
     * password in over 24 hours, an email is sent containing the code.
     * 
     * @param email String of the user email address.
     * @return The integer returned by findUserOrRegUserForReset().
     * @throws SQLException 
     * @since 1.0
     */
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
    
    /**
     * Calls checkPassword() and them sends it to the Database Manager. 
     * Checks if user's password exists in the database.
     * 
     * @param password String of user password.
     * @return Boolean from checkPassword() indicating if password string was 
     * found in the database.
     * @throws SQLException
     * @throws NoSuchAlgorithmException
     * @throws UnsupportedEncodingException 
     * @since 1.0
     */
    public boolean checkCurrentPassword(String password) throws SQLException, 
            NoSuchAlgorithmException, UnsupportedEncodingException {
        return(dbManager.checkPassword(hashPassword(password)));
    }
    
    /**
     * Calls changePassword(). Sends user an email notifying them of password
     * change.
     * 
     * @param password String of user password.
     * @throws SQLException
     * @throws NoSuchAlgorithmException
     * @throws UnsupportedEncodingException 
     * @since 1.0
     */
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

    /**
     * Calls hashPassword() and then sends it to the Database Manager with 
     * resetPassword().
     * 
     * @param cred String array of user credentials.
     * @return An integer that is returned from resetPassword().
     * @throws SQLException
     * @throws NoSuchAlgorithmException
     * @throws UnsupportedEncodingException 
     * @since 1.0
     */
    public int resetPass(String[] cred) throws SQLException,
            NoSuchAlgorithmException, UnsupportedEncodingException {
        cred[1] = hashPassword(cred[1]);
        return dbManager.resetPassword(cred);
    }

    /**
     * Calls hashPassword() and generateCode() then sends these values to the 
     * Database Manager using registerUser() along with the user credentials.
     * If the user is new, an email is sent containing the access code.
     * 
     * @param cred String array of user credentials.
     * @return An integer value that is returned from registerUser().
     * @throws SQLException
     * @throws NoSuchAlgorithmException
     * @throws UnsupportedEncodingException 
     * @since 1.0
     */
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

    /**
     * Compares the string parameter against a REGEX pattern to determine if an 
     * email address is well-formed.
     * 
     * @param email String of email address to test.
     * @return Boolean value indicating if the string parameter is a well-formed
     * email address.
     * @since 1.0
     */
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

    /**
     * Calls storeUserData() in the database manager passing user data.
     * 
     * @throws SQLException 
     * @since 1.0
     */
    public void saveUserData() throws SQLException {
        dbManager.storeUserData();
    }

    /**
     * Calls generateBCardHTMLandPDF() in the file manager.
     * 
     * @throws SQLException
     * @throws TransformerException
     * @throws ParserConfigurationException
     * @throws IOException
     * @throws FileNotFoundException
     * @throws DocumentException 
     * @since 1.0
     */
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

    /**
     * Calls generateCovLetHTMLandPDF() in the file manager.
     * 
     * @throws SQLException
     * @throws TransformerException
     * @throws ParserConfigurationException
     * @throws IOException
     * @throws FileNotFoundException
     * @throws DocumentException 
     * @since 1.0
     */
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

    /**
     * Calls storeResumeData(). If the user has completed their resume, stores
     * the resume in HTML and PDF formats.
     * 
     * @param section Integer indicating which section of the resume to update
     * 
     * @throws SQLException
     * @throws TransformerException
     * @throws ParserConfigurationException
     * @throws IOException
     * @throws FileNotFoundException
     * @throws DocumentException 
     * @since 1.0
     */
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
    
    /**
     * Calls addUserContact(). Adds a user contact via Database Manager.
     * 
     * @param email String of contact email address.
     * @param name String of contact name.
     * @throws SQLException 
     * @since 1.0
     */
    public void addContact(String email, String name) throws SQLException {
        dbManager.addUserContact(email, name);
    }
    
    /**
     * Calls deleteUserContact(). Deletes a user contact via Database Manager.
     * 
     * @param email String of contact email address.
     * @param name String of contact name.
     * @throws SQLException 
     * @since 1.0
     */
    public void deleteContact(String email, String name) throws SQLException {
        dbManager.deleteUserContact(email, name);
    }
    
    /**
     * Determines which VaqPack documents to send as email attachments.
     * 
     * @param email String of recipient email address.
     * @param sendResHTML Boolean to send Resume in HTML.
     * @param sendResPDF Boolean to send Resume in PDF.
     * @param sendBCHTML Boolean to send Business Card in HTML.
     * @param sendBCPDF Boolean to send Business Card in PDF.
     * @param sendCLHTML Boolean to send Cover Letter in HTML.
     * @param sendCLPDF Boolean to send Cover Letter in PDF.
     * @throws SQLException
     * @throws IOException 
     * @since 1.0
     */
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
    
    /**
     * Calls printFile().
     * 
     * @param type Integer indicating which document and format combination to 
     * print.
     * @throws SQLException
     * @throws IOException 
     * @since 1.0
     */
    public void printDocument(int type) throws SQLException, IOException {
        dbManager.printFile(type, true);
    }

    /**
     * Calls loadCovLetData().
     * 
     * @param clID Integer indicating which cover letter to load.
     * @throws SQLException 
     * @since 1.0
     */
    public void loadCovLet(int clID) throws SQLException {
        dbManager.loadCovLetData(clID);
    }

    /**
     * Generates a 16-character string used an an access code for verifying
     * that a user has entered a valid email address.
     * 
     * @return Random string of 16 characters.
     * @since 1.0
     */
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

    /**
     * Hashes a password.
     * 
     * @param pass String to be hashed.
     * @return Hashed string.
     * @throws NoSuchAlgorithmException
     * @throws UnsupportedEncodingException 
     * @since 1.0
     */
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
    /**
     * Accessor method.
     * 
     * @return The stored VP_GUIController object.
     * @since 1.0
     */
    protected VP_GUIController getController() {
        return controller;
    }

    /**
     * Accessor method.
     * 
     * @return The CountDownLatch object indicating thread status.
     * @since 1.0
     */
    public CountDownLatch getDbBusy() {
        return dbBusy;
    }

    /**
     * Mutator method.
     * 
     * @param dbBusy Set the CountDownLatch object.
     */
    public void setDbBusy(CountDownLatch dbBusy) {
        this.dbBusy = dbBusy;
    }
}
