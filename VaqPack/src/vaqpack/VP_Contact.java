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
 *-----------------------------------------------------------------------------*/
package vaqpack;

public class VP_Contact {

    private String email;

    /*------------------------------------------------------------------------*
     * VP_Contact()
     * - Constructor.
     * - Parameter email sets the contact's email.
     *------------------------------------------------------------------------*/
    protected VP_Contact(String email) {
        this.email = email;
    }

    /*------------------------------------------------------------------------*
     * Setters and Getters
     *------------------------------------------------------------------------*/
    protected String getEmail() {
        return email;
    }

    protected void setEmail(String email) {
        this.email = email;
    }

}
