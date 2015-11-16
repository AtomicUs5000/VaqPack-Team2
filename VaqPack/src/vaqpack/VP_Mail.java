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

import com.sun.mail.smtp.SMTPTransport;
import java.util.Properties;
import java.util.Date;
import java.security.Security;
import javafx.application.Platform;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class VP_Mail extends Thread{
    private final VP_GUIController controller;
    private final String
            port = "465",
            host = "smtp.gamil.com",
            userName = "vaqpackt2",
            password = "!vpMaiL3340?",
            recipient,
            subject,
            message;
    private final String[] ccEmails;

    /*------------------------------------------------------------------------*
     * VP_Mail()
     * - Constructor.
     * - Parameter string recipient is the email address to receieve the message.
     * - Parameter string array ccEmails is the list of cc emails.
     * - Paramater string subject is the subject or title of the email.
     * - Parameter string message is the email's message body.
     *------------------------------------------------------------------------*/
    protected VP_Mail(VP_GUIController controller, String recipient, String[] ccEmails, String subject, String message){
        this.controller = controller;
        this.recipient = recipient;
        this.ccEmails = ccEmails;
        this.subject = subject;
        this.message = message;
    }
    
    @Override
    public void run() {
        try {
            int ccLength = ccEmails.length;
            Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
            String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";
            Properties props = System.getProperties();
            props.setProperty("mail.smtps.host", host);
            props.setProperty("mail.smtp.socketFactory.class", SSL_FACTORY);
            props.setProperty("mail.smtp.socketFactory.fallback", "false");
            props.setProperty("mail.smtp.port", port);
            props.setProperty("mail.smtp.socketFactory.port", port);
            props.setProperty("mail.smtps.auth", "true");
            // property "mail.smtps.quitwait" as string "false" means QUIT command is sent right away
            // property "mail.smtps.quitwait" as string "true" means wait for response to QUIT command.
            props.put("mail.smtps.quitwait", "false");
            Session session = Session.getInstance(props, null);
            MimeMessage msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress(userName + "@gmail.com"));
            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipient, false));
            for (int i = 0; i < ccLength; i++)
                msg.addRecipients(Message.RecipientType.CC, InternetAddress.parse(ccEmails[i], false));
            msg.setSubject(subject);
            msg.setText(message, "utf-8");
            msg.setSentDate(new Date());
            SMTPTransport t = (SMTPTransport)session.getTransport("smtps");
            t.connect("smtp.gmail.com", userName, password);
            t.sendMessage(msg, msg.getAllRecipients());
            t.close();
        } catch (MessagingException ex) {
            Platform.runLater(() -> controller.errorAlert(2001, ex.getMessage()));
        }
    }

    /*------------------------------------------------------------------------*
     * Setters and Getters
     *------------------------------------------------------------------------*/
}
