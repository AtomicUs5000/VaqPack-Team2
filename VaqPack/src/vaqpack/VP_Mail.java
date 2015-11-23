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
 * FILE ID 1600
 *-----------------------------------------------------------------------------*/
package vaqpack;

import com.sun.mail.smtp.SMTPTransport;
import java.io.File;
import java.util.Properties;
import java.util.Date;
import java.security.Security;
import javafx.application.Platform;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class VP_Mail extends Thread {

    private final VP_GUIController controller;
    private final String port,
            host,
            userName,
            password,
            recipient,
            subject,
            message,
            filename;
    private final String[] ccEmails;
    private final File attachment;

    /*------------------------------------------------------------------------*
     * VP_Mail()
     * - Constructor.
     * - Parameter string recipient is the email address to receieve the message.
     * - Parameter string array ccEmails is the list of cc emails.
     * - Paramater string subject is the subject or title of the email.
     * - Parameter string message is the email's message body.
     *------------------------------------------------------------------------*/
    protected VP_Mail(VP_GUIController controller, String recipient, String[] ccEmails, String subject, String message, String filename, File attachment) {
        //-------- Initialization Start ----------\\
        this.controller = controller;
        this.recipient = recipient;
        this.ccEmails = ccEmails;
        this.subject = subject;
        this.message = message;
        this.attachment = attachment;
        port = "465";
        host = "smtp.gamil.com";
        userName = "vaqpackt2";
        password = "!vpMaiL3340?";
        this.filename = filename;
        //-------- Initialization End ------------\\
    }

    @Override
    public void run() {
        //-------- Initialization Start ----------\\
        int ccLength = 0;
        Properties props = System.getProperties();
        String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";
        Session session;
        MimeMessage msg;
        MimeMultipart attach;
        BodyPart msgbody;
        SMTPTransport transPort;
        DataSource source;
        //-------- Initialization End ------------\\

        if (ccEmails != null) {
            ccLength = ccEmails.length;
        }
        try {
            // need to figure out how to work in attachments
            Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
            props.setProperty("mail.smtps.host", host);
            props.setProperty("mail.smtp.socketFactory.class", SSL_FACTORY);
            props.setProperty("mail.smtp.socketFactory.fallback", "false");
            props.setProperty("mail.smtp.port", port);
            props.setProperty("mail.smtp.socketFactory.port", port);
            props.setProperty("mail.smtps.auth", "true");
            // property "mail.smtps.quitwait" as string "false" means QUIT command is sent right away
            // property "mail.smtps.quitwait" as string "true" means wait for response to QUIT command.
            // this setting of this property does not matter recently according to google mail docs.
            props.put("mail.smtps.quitwait", "false");
            session = Session.getInstance(props, null);
            msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress(userName + "@gmail.com"));
            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipient, false));
            for (int i = 0; i < ccLength; i++) {
                msg.addRecipients(Message.RecipientType.CC, InternetAddress.parse(ccEmails[i], false));
            }
            msg.setSubject(subject);
            msgbody = new MimeBodyPart();
            msgbody.setText(message);
            msg.setSentDate(new Date());
            attach = new MimeMultipart();
            attach.addBodyPart(msgbody);
            msgbody = new MimeBodyPart();
            
            // attachment
            if (!filename.equals("") && attachment != null) {
                source = new FileDataSource(attachment);
                msgbody.setDataHandler(new DataHandler(source));
                msgbody.setFileName(filename);
                attach.addBodyPart(msgbody);
                msg.setContent(attach);
            }
            
            transPort = (SMTPTransport) session.getTransport("smtps");
            transPort.connect("smtp.gmail.com", userName, password);
            transPort.sendMessage(msg, msg.getAllRecipients());
            transPort.close();
        } catch (MessagingException ex) {
            Platform.runLater(() -> controller.errorAlert(1601, ex.getMessage()));
        }
    }

    /*##########################################################################
     * SUBCLASSES
     *########################################################################*/
    /*##########################################################################
     * SETTERS AND GETTERS
     *########################################################################*/
}
