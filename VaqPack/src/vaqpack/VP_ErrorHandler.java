/*-----------------------------------------------------------------------------*
 * VP_ErrorHandler.java
 * - Reports the information related to an error by its code
 * Authors:
 * - Team-02
 * -- William Dewald (Project Manager)
 * -- Fernando Bazan
 * -- Nathanael Carr
 * -- Erik Lopez
 * -- Raul Saavedra
 * FILE ID 9900
 *-----------------------------------------------------------------------------*/
package vaqpack;

public class VP_ErrorHandler {

    private String header, // the header text of the error message box
            content;            // the content of the error message box
    private boolean critical;   // true = program should not continue

    /*------------------------------------------------------------------------*
     * VP_ErrorHandler()
     * - Constructor.
     * - parameter errorCode used to determine the properties
     *   Error codes should be grouped by each class's File ID
     * - parameter exceptionString used for additional information
     *------------------------------------------------------------------------*/
    protected VP_ErrorHandler(int errorCode, String exceptionString) {
        switch (errorCode) {
            // ID 1100 Originates from VP_GUIController
            case 1101:
                header = "A thread was unexpectedly interrupted during the request "
                        + "for MySQL location details.\n"
                        + "VaqPack will attempt to continue.\n If the program is "
                        + "unstable, restart it.";
                content = "Error " + errorCode + ": " + exceptionString;
                critical = false;
                break;
            case 1102:
                header = "A thread was unexpectedly interrupted during the request "
                        + "for MySQL admin account details.\n"
                        + "VaqPack will attempt to continue.\n If the program is "
                        + "unstable, restart it.";
                content = "Error " + errorCode + ": " + exceptionString;
                critical = false;
                break;
            case 1103:
                header = "A thread was unexpectedly interrupted during verification "
                        + "of MySQL location details.\n"
                        + "VaqPack will attempt to continue.\n If the program is "
                        + "unstable, restart it.";
                content = "Error " + errorCode + ": " + exceptionString;
                critical = false;
                break;
            case 1104:
                header = "A thread was unexpectedly interrupted during verification "
                        + "of MySQL admin account details.\n"
                        + "VaqPack will attempt to continue.\n If the program is "
                        + "unstable, restart it.";
                content = "Error " + errorCode + ": " + exceptionString;
                critical = false;
                break;
            case 1105:
                header = "A thread was unexpectedly interrupted during verification "
                        + "of the VaqPack admin user account details.\n"
                        + "VaqPack will attempt to continue.\n If the program is "
                        + "unstable, restart it.";
                content = "Error " + errorCode + ": " + exceptionString;
                critical = false;
                break;
            case 1106:
                header = "A thread was unexpectedly interrupted during the "
                        + " creation of the VaqPack admin user account.\n"
                        + "VaqPack will attempt to continue.\n If the program is "
                        + "unstable, restart it.";
                content = "Error " + errorCode + ": " + exceptionString;
                critical = false;
                break;
            case 1107:
                header = "A thread was unexpectedly interrupted during the final "
                        + "loading stages.\n"
                        + "VaqPack will attempt to continue.";
                content = "Error " + errorCode + ": " + exceptionString;
                critical = false;
                break;

            // ID 1200 Originates from VP_DataManger
            case 1201:
                header = "VaqPack's hashing algorithm has failed while attempting "
                        + "to store the VaqPack admin user credentials.";
                content = "Error " + errorCode + ": " + exceptionString;
                critical = true;
                break;
            case 1202:
                header = "VaqPack's hashing algorithm has failed while attempting "
                        + "to log in a user.\n"
                        + "VaqPack will attempt to continue.\n"
                        + "Please try logging in again.";
                content = "Error " + errorCode + ": " + exceptionString;
                critical = false;
                break;
            case 1203:
                header = "VaqPack's hashing algorithm has failed while attempting "
                        + "to complete the registration of a user.\n"
                        + "VaqPack will attempt to continue.\n"
                        + "Please try confirming registration again.";
                content = "Error " + errorCode + ": " + exceptionString;
                critical = false;
                break;
            case 1204:
                header = "VaqPack's hashing algorithm has failed while attempting "
                        + "to reset the password of a user.\n"
                        + "VaqPack will attempt to continue.\n"
                        + "Please try resetting the password again.";
                content = "Error " + errorCode + ": " + exceptionString;
                critical = false;
                break;
            case 1205:
                header = "VaqPack's hashing algorithm has failed while attempting "
                        + "to resend the registration code for a user.\n"
                        + "VaqPack will attempt to continue.\n"
                        + "Please try requesting the code again.";
                content = "Error " + errorCode + ": " + exceptionString;
                critical = false;
                break;
            case 1206:
                header = "VaqPack's hashing algorithm has failed while attempting "
                        + "to register a new user.\n"
                        + "VaqPack will attempt to continue.\n"
                        + "Please try submitting your registration again.";
                content = "Error " + errorCode + ": " + exceptionString;
                critical = false;
                break;

            // ID 1300 Originates from VP_FileManager
            case 1301:
                header = "VaqPack is not able to store the configuration file "
                        + "that is necessary to connect to the MySQL database.";
                content = "Error " + errorCode + ": " + exceptionString;
                critical = true;
                break;
            case 1302:
                header = "VaqPack is not able to store the credentials file "
                        + "that is necessary to connect to the MySQL database.";
                content = "Error " + errorCode + ": " + exceptionString;
                critical = true;
                break;
            case 1303:
                header = "An unexpected error occurred while encrypting or decrypting "
                        + "credentials.";
                content = "Error " + errorCode + ": " + exceptionString;
                critical = true;
                break;
            case 1304:
                header = "An unexpected error occurred while encrypting "
                        + "VaqPack admin credentials.";
                content = "Error " + errorCode + ": " + exceptionString;
                critical = true;
                break;
            case 1305:
                header = "An unexpected error occurred while searching for "
                        + "VaqPack admin credentials.";
                content = "Error " + errorCode + ": " + exceptionString;
                critical = true;
                break;
            case 1306:
                header = "An unexpected error occurred while storing "
                        + "VaqPack admin credentials.";
                content = "Error " + errorCode + ": " + exceptionString;
                critical = true;
                break;

            // ID 1400 Originates from VP_DatabaseManager
            case 1401:
                header = "VaqPack has encountered a critical MySQL error \n"
                        + "while checking for connectivity.";
                content = "Error " + errorCode + ": " + exceptionString;
                critical = true;
                break;
            case 1402:
                header = "VaqPack has encountered a critical MySQL error \n"
                        + "while checking admin credentials.";
                content = "Error " + errorCode + ": " + exceptionString;
                critical = true;
                break;
            case 1403:
                header = "VaqPack has encountered a critical MySQL error \n"
                        + "while checking database tables.";
                content = "Error " + errorCode + ": " + exceptionString;
                critical = true;
                break;
            case 1404:
                header = "VaqPack has encountered a critical MySQL error \n"
                        + "while verifying user access levels.";
                content = "Error " + errorCode + ": " + exceptionString;
                critical = true;
                break;
            case 1405:
                header = "VaqPack has encountered a critical MySQL error \n"
                        + "while verifying VaqPack admin user existence.";
                content = "Error " + errorCode + ": " + exceptionString;
                critical = true;
                break;
            case 1406:
                header = "VaqPack has encountered a critical MySQL error \n"
                        + "while storing VaqPack admin user details.";
                content = "Error " + errorCode + ": " + exceptionString;
                critical = true;
                break;
            case 1407:
                header = "VaqPack has encountered a MySQL error \n"
                        + "while logging in a user.\n"
                        + "VaqPack will attempt to continue.\n"
                        + "Please login again.";
                content = "Error " + errorCode + ": " + exceptionString;
                critical = false;
                break;
            case 1408:
                header = "VaqPack has encountered a MySQL error \n"
                        + "while completing the registration of a user.\n"
                        + "VaqPack will attempt to continue.\n"
                        + "Please try confirming your registration again.";
                content = "Error " + errorCode + ": " + exceptionString;
                critical = false;
                break;
            case 1409:
                header = "VaqPack has encountered a MySQL error \n"
                        + "while resetting a user password.\n"
                        + "VaqPack will attempt to continue.\n"
                        + "Please try resetting your password again.";
                content = "Error " + errorCode + ": " + exceptionString;
                critical = false;
                break;
            case 1410:
                header = "VaqPack has encountered a MySQL error \n"
                        + "while resetting and resending a user registration code.\n"
                        + "VaqPack will attempt to continue.\n"
                        + "Please try resrnding a new code again.";
                content = "Error " + errorCode + ": " + exceptionString;
                critical = false;
                break;
            case 1411:
                header = "VaqPack has encountered a MySQL error \n"
                        + "while initiating the password reset sequence.\n"
                        + "VaqPack will attempt to continue.\n"
                        + "Please try again.";
                content = "Error " + errorCode + ": " + exceptionString;
                critical = false;
                break;
            case 1412:
                header = "VaqPack has encountered a MySQL error \n"
                        + "while registering a new user.\n"
                        + "VaqPack will attempt to continue.\n"
                        + "Please try again.";
                content = "Error " + errorCode + ": " + exceptionString;
                critical = false;
                break;
            case 1413:
                header = "VaqPack has encountered a MySQL error \n"
                        + "while storing user data.\n"
                        + "VaqPack will attempt to continue.\n"
                        + "Please try again.";
                content = "Error " + errorCode + ": " + exceptionString;
                critical = false;
                break;
            case 1414:
                header = "VaqPack has encountered a MySQL error \n"
                        + "while storing business card data.\n"
                        + "VaqPack will attempt to continue.\n"
                        + "Please try again.";
                content = "Error " + errorCode + ": " + exceptionString;
                critical = false;
                break;
            case 1415:
                header = "VaqPack has encountered a MySQL error \n"
                        + "while storing cover letter data.\n"
                        + "VaqPack will attempt to continue.\n"
                        + "Please try again.";
                content = "Error " + errorCode + ": " + exceptionString;
                critical = false;
                break;
            case 1416:
                header = "VaqPack has encountered a MySQL error \n"
                        + "while loading cover letter data.\n"
                        + "VaqPack will attempt to continue.\n"
                        + "Please try again.";
                content = "Error " + errorCode + ": " + exceptionString;
                critical = false;
                break;
            case 1417:
                header = "VaqPack has encountered a MySQL error \n"
                        + "while storing resume objective.\n"
                        + "VaqPack will attempt to continue.\n"
                        + "Please try again.";
                content = "Error " + errorCode + ": " + exceptionString;
                critical = false;
                break;
            case 1418:
                header = "VaqPack has encountered a MySQL error \n"
                        + "while storing resume education fields.\n"
                        + "VaqPack will attempt to continue.\n"
                        + "Please try again.";
                content = "Error " + errorCode + ": " + exceptionString;
                critical = false;
                break;
            // ID 1500 Originates from VP_Loader
            // ID 1600 Originates from VP_Mail
            case 1601:
                header = "VaqPack could not send the email due to \n"
                        + "malformed parameters passed to the VP_Mail object.";
                content = "Error " + errorCode + ": " + exceptionString;
                critical = false;
                break;

            // ID 1700 Originates from VP_Header
            // ID 1800 Originates from VP_Tree
            // ID 1900 Originates from VP_Center
            // ID 2000 Originates from VP_Footer
            // ID 2100 Originates from VP_BusinessCard
            // ID 2200 Originates from VP_CoverLetter
            // ID 2300 Originates from VP_Resume
            // ID 2400 Originates from VP_User
            // ID 2500 Originates from VP_Theme
            // ID 2600 Originates from VP_Contact
            // ID 2700 Originates from VP_Textfield
            // ID 2800 Originates from VP_PasswordField
            // ID 2900 Originates from VP_DataToHtml
            case 2901:
                header = "VaqPack could not create the pdf document for the "
                        + "business card.";
                content = "Error " + errorCode + ": " + exceptionString;
                critical = true;
                break;
            case 2902:
                header = "VaqPack could not create the pdf document for the "
                        + "cover letter.";
                content = "Error " + errorCode + ": " + exceptionString;
                critical = true;
                break;
            // ID 3000 Originates from VP_HtmlToPdf
            // ID 3100 Originates from VP_SoundManager
            // General Error, perhaps invalid code in constructor
            default:
                header = "An unknown error has occured.";
                content = "Error " + errorCode + ": " + exceptionString;
                critical = true;
                break;
        }
        content = "\n" + content;
        if (critical) {
            content += "\nVaqPack WILL NOW EXIT.";
        }
    }

    /*------------------------------------------------------------------------*
     * clearError()
     * - Clears the values of all properties. This is unnecessary, but helps to
     *   avoid potential problems that can occur if an error code is not
     *   assigned properly in loadError().
     * - No parameters.
     * - No return.
     *------------------------------------------------------------------------*/
    protected void clearError() {
        header = "";
        content = "";
        critical = false;
    }

    /*------------------------------------------------------------------------*
     * Setters and Getters
     *------------------------------------------------------------------------*/
    protected String getHeader() {
        return header;
    }

    protected String getContent() {
        return content;
    }

    protected boolean isCritical() {
        return critical;
    }
}
