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
 * FILE ID 4200
 *-----------------------------------------------------------------------------*/
package vaqpack.peripherals;

import com.sun.mail.smtp.SMTPTransport;
import java.io.File;
import java.util.Properties;
import java.util.Date;
import java.security.Security;
import java.util.ArrayList;
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
import vaqpack.VP_GUIController;

public class VP_Mail extends Thread {

    private final VP_GUIController controller;
    private final String port,
            host,
            userName,
            password,
            recipient,
            subject,
            message;
    private final String[] ccEmails;
    private final ArrayList<File> attachments;
    private final ArrayList<String> filenames;

    /*------------------------------------------------------------------------*
     * VP_Mail()
     * - Constructor.
     * - Parameter string recipient is the email address to receieve the message.
     * - Parameter string array ccEmails is the list of cc emails.
     * - Paramater string subject is the subject or title of the email.
     * - Parameter string message is the email's message body.
     *------------------------------------------------------------------------*/
    public VP_Mail(VP_GUIController controller, String recipient, String[] ccEmails, String subject, String message,
            ArrayList<String> filenames, ArrayList<File> attachments) {
        //-------- Initialization Start ----------\\
        this.controller = controller;
        this.recipient = recipient;
        this.ccEmails = ccEmails;
        this.subject = subject;
        this.message = message;
        this.attachments = attachments;
        port = "465";
        host = "smtp.gamil.com";
        userName = "vaqpackt2";
        password = "!vpMaiL3340?";
        this.filenames = filenames;
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
        SMTPTransport transPort;
        MimeMultipart attach;
        DataSource source;
        BodyPart msgbody;
        //-------- Initialization End ------------\\

        if (ccEmails != null) {
            ccLength = ccEmails.length;
        }
        try {
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
            msg.setSentDate(new Date());

            // attachment
            if (filenames != null && attachments !=null && !filenames.get(0).equals("") && attachments.get(0) != null) {
                msgbody = new MimeBodyPart();
                msgbody.setText(message);
                attach = new MimeMultipart("mixed");
                attach.addBodyPart(msgbody);
                for (int i = 0; i < attachments.size(); i++){
                    msgbody = new MimeBodyPart();
                    source = new FileDataSource(attachments.get(i));
                    msgbody.setDataHandler(new DataHandler(source));
                    msgbody.setFileName(filenames.get(i));
                    attach.addBodyPart(msgbody);
                }
                msg.setContent(attach);
            } else {
                msg.setText(message, "utf-8");
            }
            transPort = (SMTPTransport) session.getTransport("smtps");
            transPort.connect("smtp.gmail.com", userName, password);
            transPort.sendMessage(msg, msg.getAllRecipients());
            transPort.close();
            if (filenames != null && attachments !=null && !filenames.get(0).equals("") && attachments.get(0) != null) {
                for (int i = 0; i < attachments.size(); i++) {
                    if (attachments.get(i) != null && attachments.get(i).exists()) {
                        attachments.get(i).delete();
                    }
                }
            }  
        } catch (MessagingException ex) {
            Platform.runLater(() -> controller.errorAlert(4201, ex.getMessage()));
        }
    }

    /*##########################################################################
     * SUBCLASSES
     *########################################################################*/
    /*##########################################################################
     * SETTERS AND GETTERS
     *########################################################################*/
}
