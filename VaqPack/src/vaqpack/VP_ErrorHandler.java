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
 *-----------------------------------------------------------------------------*/
package vaqpack;

public class VP_ErrorHandler {
    private String
            header,             // the header text of the error message box
            content;            // the content of the error message box
    private boolean
            critical;           // true = program should not continue
    
    /*------------------------------------------------------------------------*
     * VP_ErrorHandler()
     * - Constructor.
     * - parameter errorCode used to determine the properties
     *   Error codes should be grouped by each class's File ID
     * - parameter exceptionString used for additional information
     *------------------------------------------------------------------------*/
    protected VP_ErrorHandler(int errorCode, String exceptionString) {
        switch (errorCode) {
            case 1301:
                header = "VaqPack is not able to read the configiuration file "
                        + "that is necessary to connect to the MySQL database.";
                content = "Error " + errorCode + ": " + exceptionString;
                critical = true;
                break;
            case 1302:
                header = "VaqPack is not able to store the configiuration file "
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
                header = "VaqPack is not able to read the credentials file "
                        + "that is necessary to connect to the MySQL database.\n"
                        + "The file may be corrupt.\n"
                        + "VaqPack will attempt to recover from this error.\n"
                        + "The database administrator must reenter credentials.";
                content = "Error " + errorCode + ": " + exceptionString;
                critical = false;
                break;
            case 1305:
                header = "VaqPack is not able to store the credentials file "
                        + "that is necessary to connect to the MySQL database.";
                content = "Error " + errorCode + ": " + exceptionString;
                critical = true;
                break;
            case 1306:
                header = "An unexpected error occurred while encrypting "
                        + "VaqPack admin credentials.";
                content = "Error " + errorCode + ": " + exceptionString;
                critical = true;
                break;
            case 1307:
                header = "An unexpected error occurred while searching for "
                        + "VaqPack admin credentials.";
                content = "Error " + errorCode + ": " + exceptionString;
                critical = true;
                break;
            case 1308:
                header = "An unexpected error occurred while storing "
                        + "VaqPack admin credentials.";
                content = "Error " + errorCode + ": " + exceptionString;
                critical = true;
                break;
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
            default:
                header = "An unknown error has occured.";
                content = "Error " + errorCode + ": " + exceptionString;
                critical = true;
                break;
        }
        if (critical) {
            content += "\nVaqPack WILL NOW EXIT.";
        }
    }

    /*------------------------------------------------------------------------*
     * clearError()
     * - Clears the values of all properties. This is unnecessary, but helps to
     * avoid potential problems that can occur if an error code is not assigned
     * properly in loadError()
     * - No parameters
     * - No return
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
