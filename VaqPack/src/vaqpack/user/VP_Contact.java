/*-----------------------------------------------------------------------------*
 * VP_Contact.java
 * - Contact object, each user may have several
 * Authors:
 * - Team-02
 * -- William Dewald (Project Manager)
 * -- Fernando Bazan
 * -- Nathanael Carr
 * -- Erik Lopez
 * -- Raul Saavedra
 * FILE ID 5400
 *-----------------------------------------------------------------------------*/
package vaqpack.user;

public class VP_Contact {

    private String email;

    /*------------------------------------------------------------------------*
     * VP_Contact()
     * - Constructor.
     * - Parameter email sets the contact's email.
     *------------------------------------------------------------------------*/
    protected VP_Contact(String email) {
        //-------- Initialization Start ----------\\
        this.email = email;
        //-------- Initialization End ------------\\
    }

    /*##########################################################################
     * SUBCLASSES
     *########################################################################*/
    /*##########################################################################
     * SETTERS AND GETTERS
     *########################################################################*/
    protected String getEmail() {
        return email;
    }

    protected void setEmail(String email) {
        this.email = email;
    }

}
