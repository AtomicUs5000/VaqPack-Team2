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

    private String header, // the header text of the error message box
            content;            // the content of the error message box
    private boolean critical;   // true = program should not continue

    /*------------------------------------------------------------------------*
     * VP_ErrorHandler()
     * - Constructor.
     * - parameter errorCode used to determine the properties
     *   Error codes should be grouped by class:
     *      11xx from VP_GUIEvents
     *      12xx from VP_DatabaseManager
     *      13xx from VP_DataManager
     *      14xx from VP_DataToHTML
     *      15xx from VP_HTMLToPDF
     *      16xx from VP_FileManager
     *      17xx from VP_TemplateManager
     *      18xx from VP_NetworkManager
     * - parameter exceptionString used for additional information
     *------------------------------------------------------------------------*/
    protected VP_ErrorHandler(int errorCode, String exceptionString) {
        switch (errorCode) {
            case 1100:
                header = "This is just a test error";
                content = "Error " + errorCode + ": " + exceptionString;
                critical = false;
                break;
            case 1101:
                header = "This is just a test critical error";
                content = "CRITICAL ERROR " + errorCode + ": " + exceptionString;
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
