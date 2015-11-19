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
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class VP_DataManager {

    private final VP_GUIController controller;
    private final VP_DatabaseManager dbManager;
    private final VP_DataToHtml data2html;
    private final VP_HtmlToPdf html2pdf;
    private final VP_FileManager fileM;

    /*------------------------------------------------------------------------*
     * VP_DataManager()
     * - Constructor.
     * - Parameter VP_GUIController stored to access events
     * - Parameter VP_Loader atored to manipulate its progress
     * - Creates a Database Manager, a File Manager, a data-to-html convertor,
     *   and a html-to-pdf convertor.
     *------------------------------------------------------------------------*/
    protected VP_DataManager(VP_GUIController controller) {
        this.controller = controller;
        dbManager = new VP_DatabaseManager();
        data2html = new VP_DataToHtml();
        html2pdf = new VP_HtmlToPdf();
        fileM = new VP_FileManager();
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
     *   accessible by the File Mangager and sends the values to Database Manager.
     * - No parameters.
     * - No return.
     *------------------------------------------------------------------------*/
    protected void retrieveDBLocation() throws FileNotFoundException, IOException {
        String[] loc = fileM.retrieveUrlPort();
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
        String[] cred = fileM.retrieveAdminEncrypted(dbManager.getUrl(), dbManager.getPort());
        dbManager.setAdminUserName(cred[0]);
        dbManager.setAdminPassword(cred[1]);
    }

    /*------------------------------------------------------------------------*
     * storeAdminCred()
     * - Sends the database admin credentials to the the File Mangager for
     *   encryption and storing in the properties file, and sends the values to 
     *   th eDatabase Manager to be used by all queries.
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
                dbManager.checkDefaultThemeTable();
                break;
            case 11:
                dbManager.checkBCHasCustomThemeTable();
                break;
            case 12:
                dbManager.checkBCHasDefaultThemeTable();
                break;
            case 13:
                dbManager.checkBusinessCardPDFTable();
                break;
            case 14:
                dbManager.checkCLHasCustomThemeTable();
                break;
            case 15:
                dbManager.checkCLHasDefaultThemeTable();
                break;
            case 16:
                dbManager.checkCoverLetterPDFTable();
                break;
            case 17:
                dbManager.checkResHasCustomThemeTable();
                break;
            case 18:
                dbManager.checkResHasDefaultThemeTable();
                break;
            case 19:
                dbManager.checkResPDFTable();
                break;
            case 20:
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
     *   credentials and the VaqPack admin user crfedentials.
     * - Returns a boolean value indicating if that the database admin user is
     *   valid, allowing the creation of this VP admin user account.
     *------------------------------------------------------------------------*/
    protected boolean createVPAdmin(String[] cred) throws SQLException,
            NoSuchAlgorithmException, UnsupportedEncodingException {
        String[] ccMail = {};
        String code = generatAccessCode();
        boolean adminChecked = dbManager.createVaqPackAdmin(cred, code);
        String msg = "A VaqPack administrator account has been created associated with this email address.\n"
                + "Please log into VaqPack and enter the following code:\n\n"
                + code + "\n\n"
                + "The code will expire in 1 hour. If you do not enter the code within this timeframe, "
                + "the system administrator will have to set up your account again.\n"
                + "The code only needs to be entered once to activate your account.\n\n"
                + "This is an automated message from the VaqPack software. Please do not reply.";
        VP_Mail regEmail = new VP_Mail(controller, cred[2], ccMail, "VaqPack Registration", msg);
        regEmail.setDaemon(true);
        regEmail.start();
        return adminChecked;
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
        if (email.length() >= 3) {
            String regex
                    = "^[\\w!#$%&’*+/=?`{|}~^-]+(?:\\.[\\w!#$%&’*+/=?`{|}~^-]+)*"
                    + "@(?:[A-Z0-9-]+\\.)+[A-Z]{2,63}$";
            Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(email);
            return matcher.matches();
        } else {
            return false;
        }
    }

    protected int userLogin(String[] cred) throws SQLException,
            NoSuchAlgorithmException, UnsupportedEncodingException {
        return dbManager.attemptUserLogin(cred);
    }

    protected boolean verifyRegAccess(String[] cred) throws SQLException,
            NoSuchAlgorithmException, UnsupportedEncodingException {
        return dbManager.verifyUserAccessCode(cred);
    }

    protected void resendAccess(String[] cred) throws SQLException,
            NoSuchAlgorithmException, UnsupportedEncodingException {
        String msg,
                code = generatAccessCode();
        String[] ccMail = {};
        VP_Mail regEmail;
        if (dbManager.resendUserAccessCode(cred, code)) {
            msg = "Please log into VaqPack and enter the following code:\n\n"
                    + code + "\n\n"
                    + "The code will expire in 1 hour. If you do not enter the code within this timeframe, "
                    + "you will have to register your account again.\n"
                    + "The code only needs to be entered once to activate your account.\n\n"
                    + "This is an automated message from the VaqPack software. Please do not reply.";
            regEmail = new VP_Mail(controller, cred[0], ccMail, "VaqPack Registration", msg);
            regEmail.setDaemon(true);
            regEmail.start();
        }
    }

    protected int findUser(String email) throws SQLException {
        String msg,
                code = generatAccessCode();
        String[] ccMail = {};
        VP_Mail resetEmail;
        int userStatus = dbManager.findUserOrRegUser(email, code);
        if (userStatus == 2) {
            msg = "Please enter the following code to reset your password:\n\n"
                    + code + "\n\n"
                    + "The code will expire in 1 hour.\n\n"
                    + "This is an automated message from the VaqPack software. Please do not reply.";
            resetEmail = new VP_Mail(controller, email, ccMail, "VaqPack Password Reset", msg);
            resetEmail.setDaemon(true);
            resetEmail.start();
        }
        return userStatus;
    }

    protected int resetPass(String[] cred) throws SQLException,
            NoSuchAlgorithmException, UnsupportedEncodingException {
        return dbManager.resetPassword(cred);
    }

    /*------------------------------------------------------------------------*
     * generatAccessCode()
     * - Generates a 16-character string used an an access code for verifying
     *   that a user has enetered a valid email address.
     * - Returns a random string of 16 characters.
     *------------------------------------------------------------------------*/
    private String generatAccessCode() {
        String code = "";
        Random rand = new Random();
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
     * Setters and Getters
     *------------------------------------------------------------------------*/
}
