/*-----------------------------------------------------------------------------*
 * VP_Mail.java
 * - Email preparation and sending
 * Authors:
 * - Team-02
 * -- William Dewald (Project Manager)
 * -- Fernando Bazan
 * -- Nathanael Carr
 * -- Erik Lopez
 * -- Raul Saavedra
 * FILE ID 2000
 *-----------------------------------------------------------------------------*/
package vaqpack;

public class VP_Mail {

    private String address,
            subject,
            body;
    // don't know the data type for an attachment yet

    /*------------------------------------------------------------------------*
     * VP_Mail()
     * - Constructor.
     *------------------------------------------------------------------------*/
    protected VP_Mail(String address, String subject, String body) {
        this.address = address;
        this.subject = subject;
        this.body = body;
    }

    /*------------------------------------------------------------------------*
     * Setters and Getters
     *------------------------------------------------------------------------*/
    protected String getAddress() {
        return address;
    }

    protected void setAddress(String address) {
        this.address = address;
    }

    protected String getSubject() {
        return subject;
    }

    protected void setSubject(String subject) {
        this.subject = subject;
    }

    protected String getBody() {
        return body;
    }

    protected void setBody(String body) {
        this.body = body;
    }
}
