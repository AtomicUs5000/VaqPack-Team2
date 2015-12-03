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
 * FILE ID 4000
 *-----------------------------------------------------------------------------*/
package vaqpack.peripherals;

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
    public VP_ErrorHandler(int errorCode, String exceptionString) {
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

            // ID 3000 Originates from VP_DataManger
            case 3001:
                header = "VaqPack's hashing algorithm has failed while attempting "
                        + "to store the VaqPack admin user credentials.";
                content = "Error " + errorCode + ": " + exceptionString;
                critical = true;
                break;
            case 3002:
                header = "VaqPack's hashing algorithm has failed while attempting "
                        + "to log in a user.\n"
                        + "VaqPack will attempt to continue.\n"
                        + "Please try logging in again.";
                content = "Error " + errorCode + ": " + exceptionString;
                critical = false;
                break;
            case 3003:
                header = "VaqPack's hashing algorithm has failed while attempting "
                        + "to complete the registration of a user.\n"
                        + "VaqPack will attempt to continue.\n"
                        + "Please try confirming registration again.";
                content = "Error " + errorCode + ": " + exceptionString;
                critical = false;
                break;
            case 3004:
                header = "VaqPack's hashing algorithm has failed while attempting "
                        + "to reset the password of a user.\n"
                        + "VaqPack will attempt to continue.\n"
                        + "Please try resetting the password again.";
                content = "Error " + errorCode + ": " + exceptionString;
                critical = false;
                break;
            case 3005:
                header = "VaqPack's hashing algorithm has failed while attempting "
                        + "to resend the registration code for a user.\n"
                        + "VaqPack will attempt to continue.\n"
                        + "Please try requesting the code again.";
                content = "Error " + errorCode + ": " + exceptionString;
                critical = false;
                break;
            case 3006:
                header = "VaqPack's hashing algorithm has failed while attempting "
                        + "to register a new user.\n"
                        + "VaqPack will attempt to continue.\n"
                        + "Please try submitting your registration again.";
                content = "Error " + errorCode + ": " + exceptionString;
                critical = false;
                break;
            
            // ID 3100 Originates from VP_DatabaseManager
            case 3101:
                header = "VaqPack has encountered a critical MySQL error \n"
                        + "while checking for connectivity.";
                content = "Error " + errorCode + ": " + exceptionString;
                critical = true;
                break;
            case 3102:
                header = "VaqPack has encountered a critical MySQL error \n"
                        + "while checking admin credentials.";
                content = "Error " + errorCode + ": " + exceptionString;
                critical = true;
                break;
            case 3103:
                header = "VaqPack has encountered a critical MySQL error \n"
                        + "while checking database tables.";
                content = "Error " + errorCode + ": " + exceptionString;
                critical = true;
                break;
            case 3104:
                header = "VaqPack has encountered a critical MySQL error \n"
                        + "while verifying user access levels.";
                content = "Error " + errorCode + ": " + exceptionString;
                critical = true;
                break;
            case 3105:
                header = "VaqPack has encountered a critical MySQL error \n"
                        + "while verifying VaqPack admin user existence.";
                content = "Error " + errorCode + ": " + exceptionString;
                critical = true;
                break;
            case 3106:
                header = "VaqPack has encountered a critical MySQL error \n"
                        + "while storing VaqPack admin user details.";
                content = "Error " + errorCode + ": " + exceptionString;
                critical = true;
                break;
            case 3107:
                header = "VaqPack has encountered a MySQL error \n"
                        + "while logging in a user.\n"
                        + "VaqPack will attempt to continue.\n"
                        + "Please login again.";
                content = "Error " + errorCode + ": " + exceptionString;
                critical = false;
                break;
            case 3108:
                header = "VaqPack has encountered a MySQL error \n"
                        + "while completing the registration of a user.\n"
                        + "VaqPack will attempt to continue.\n"
                        + "Please try confirming your registration again.";
                content = "Error " + errorCode + ": " + exceptionString;
                critical = false;
                break;
            case 3109:
                header = "VaqPack has encountered a MySQL error \n"
                        + "while resetting a user password.\n"
                        + "VaqPack will attempt to continue.\n"
                        + "Please try resetting your password again.";
                content = "Error " + errorCode + ": " + exceptionString;
                critical = false;
                break;
            case 3110:
                header = "VaqPack has encountered a MySQL error \n"
                        + "while resetting and resending a user registration code.\n"
                        + "VaqPack will attempt to continue.\n"
                        + "Please try resrnding a new code again.";
                content = "Error " + errorCode + ": " + exceptionString;
                critical = false;
                break;
            case 3111:
                header = "VaqPack has encountered a MySQL error \n"
                        + "while initiating the password reset sequence.\n"
                        + "VaqPack will attempt to continue.\n"
                        + "Please try again.";
                content = "Error " + errorCode + ": " + exceptionString;
                critical = false;
                break;
            case 3112:
                header = "VaqPack has encountered a MySQL error \n"
                        + "while registering a new user.\n"
                        + "VaqPack will attempt to continue.\n"
                        + "Please try again.";
                content = "Error " + errorCode + ": " + exceptionString;
                critical = false;
                break;
            case 3113:
                header = "VaqPack has encountered a MySQL error \n"
                        + "while storing user data.\n"
                        + "VaqPack will attempt to continue.\n"
                        + "Please try again.";
                content = "Error " + errorCode + ": " + exceptionString;
                critical = false;
                break;
            case 3114:
                header = "VaqPack has encountered a MySQL error \n"
                        + "while storing business card data.\n"
                        + "VaqPack will attempt to continue.\n"
                        + "Please try again.";
                content = "Error " + errorCode + ": " + exceptionString;
                critical = false;
                break;
            case 3115:
                header = "VaqPack has encountered a MySQL error \n"
                        + "while storing cover letter data.\n"
                        + "VaqPack will attempt to continue.\n"
                        + "Please try again.";
                content = "Error " + errorCode + ": " + exceptionString;
                critical = false;
                break;
            case 3116:
                header = "VaqPack has encountered a MySQL error \n"
                        + "while loading cover letter data.\n"
                        + "VaqPack will attempt to continue.\n"
                        + "Please try again.";
                content = "Error " + errorCode + ": " + exceptionString;
                critical = false;
                break;
            case 3117:
                header = "VaqPack has encountered a MySQL error \n"
                        + "while storing resume objective.\n"
                        + "VaqPack will attempt to continue.\n"
                        + "Please try again.";
                content = "Error " + errorCode + ": " + exceptionString;
                critical = false;
                break;
            case 3118:
                header = "VaqPack has encountered a MySQL error \n"
                        + "while storing resume education fields.\n"
                        + "VaqPack will attempt to continue.\n"
                        + "Please try again.";
                content = "Error " + errorCode + ": " + exceptionString;
                critical = false;
                break;
            case 3119:
                header = "VaqPack has encountered a MySQL error \n"
                        + "while storing resume experience fields.\n"
                        + "VaqPack will attempt to continue.\n"
                        + "Please try again.";
                content = "Error " + errorCode + ": " + exceptionString;
                critical = false;
                break;
            case 3120:
                header = "VaqPack has encountered a MySQL error \n"
                        + "while storing resume references.\n"
                        + "VaqPack will attempt to continue.\n"
                        + "Please try again.";
                content = "Error " + errorCode + ": " + exceptionString;
                critical = false;
                break;
            case 3121:
                header = "VaqPack has encountered a MySQL error \n"
                        + "while storing resume achievements.\n"
                        + "VaqPack will attempt to continue.\n"
                        + "Please try again.";
                content = "Error " + errorCode + ": " + exceptionString;
                critical = false;
                break;
            case 3122:
                header = "VaqPack has encountered a MySQL error \n"
                        + "while storing the resume community section.\n"
                        + "VaqPack will attempt to continue.\n"
                        + "Please try again.";
                content = "Error " + errorCode + ": " + exceptionString;
                critical = false;
                break;
            case 3123:
                header = "VaqPack has encountered a MySQL error \n"
                        + "while storing the resume qualifications.\n"
                        + "VaqPack will attempt to continue.\n"
                        + "Please try again.";
                content = "Error " + errorCode + ": " + exceptionString;
                critical = false;
                break;
            case 3124:
                header = "VaqPack has encountered a MySQL error \n"
                        + "while storing the resume highlights.\n"
                        + "VaqPack will attempt to continue.\n"
                        + "Please try again.";
                content = "Error " + errorCode + ": " + exceptionString;
                critical = false;
                break;
            case 3125:
                header = "VaqPack has encountered a MySQL error \n"
                        + "while storing the resume languages.\n"
                        + "VaqPack will attempt to continue.\n"
                        + "Please try again.";
                content = "Error " + errorCode + ": " + exceptionString;
                critical = false;
                break;
            case 3126:
                header = "VaqPack has encountered a MySQL error \n"
                        + "while storing the resume software section.\n"
                        + "VaqPack will attempt to continue.\n"
                        + "Please try again.";
                content = "Error " + errorCode + ": " + exceptionString;
                critical = false;
                break;

            // ID 3200 Originates from VP_FileManager
            case 3201:
                header = "VaqPack is not able to store the configuration file "
                        + "that is necessary to connect to the MySQL database.";
                content = "Error " + errorCode + ": " + exceptionString;
                critical = true;
                break;
            case 3202:
                header = "VaqPack is not able to store the credentials file "
                        + "that is necessary to connect to the MySQL database.";
                content = "Error " + errorCode + ": " + exceptionString;
                critical = true;
                break;
            case 3203:
                header = "An unexpected error occurred while encrypting or decrypting "
                        + "credentials.";
                content = "Error " + errorCode + ": " + exceptionString;
                critical = true;
                break;
            case 3204:
                header = "An unexpected error occurred while encrypting "
                        + "VaqPack admin credentials.";
                content = "Error " + errorCode + ": " + exceptionString;
                critical = true;
                break;
            case 3205:
                header = "An unexpected error occurred while searching for "
                        + "VaqPack admin credentials.";
                content = "Error " + errorCode + ": " + exceptionString;
                critical = true;
                break;
            case 3206:
                header = "An unexpected error occurred while storing "
                        + "VaqPack admin credentials.";
                content = "Error " + errorCode + ": " + exceptionString;
                critical = true;
                break;
                
            // ID 3300 Originates from VP_DataToHtml
            case 3301:
                header = "VaqPack could not create the PDF document for the "
                        + "business card.";
                content = "Error " + errorCode + ": " + exceptionString;
                critical = true;
                break;
            case 3302:
                header = "VaqPack could not create the PDF document for the "
                        + "cover letter.";
                content = "Error " + errorCode + ": " + exceptionString;
                critical = true;
                break;
            case 3303:
                header = "VaqPack could not create the HTML and PDF documents for the "
                        + "resume.";
                content = "Error " + errorCode + ": " + exceptionString;
                critical = true;
                break;
            
            // ID 4200 Originates from VP_Mail
            case 4201:
                header = "VaqPack could not send the email due to \n"
                        + "malformed parameters passed to the VP_Mail object.";
                content = "Error " + errorCode + ": " + exceptionString;
                critical = false;
                break;

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
    public String getHeader() {
        return header;
    }

    public String getContent() {
        return content;
    }

    public boolean isCritical() {
        return critical;
    }
}
