package com.Java.Messenger;

import java.util.Properties;
import javax.mail.AuthenticationFailedException;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Email {
    public String to;
    public String receivedSubject;
    public String receivedMessage;
    public String senderID;
    public Properties properties;


    public Boolean sendEmail(String from, String to, String receivedSubject, String receivedMessage, String senderPassword) {
        this.to = to;
        this.receivedSubject = receivedSubject;
        this.receivedMessage = receivedMessage;
        String host = "smtp.gmail.com";
        // Setup mail server
        properties = System.getProperties();
        properties.put("mail.smtp.ssl.protocols", "TLSv1.2");
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.auth", "true");

        // Get the Session object.// and pass username and password
        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
            protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
            	return new javax.mail.PasswordAuthentication(from, senderPassword);
            }
        });

        try {
            // Create a default MimeMessage object.
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            message.setSubject(receivedSubject);
            message.setText(receivedMessage);
            Transport.send(message);
            return true;
        } 
        catch (MessagingException mex) {
            mex.printStackTrace();
            return false;
        }
      
    }

    public Boolean emailVerifier(String senderID, String senderPassword) {
        this.senderID = senderID;
        String host = "smtp.gmail.com";
        // Setup mail server
        properties = System.getProperties();
        properties.put("mail.smtp.ssl.protocols", "TLSv1.2");
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.auth", "true");
        
       Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
            public javax.mail.PasswordAuthentication getPasswordAuthentication() {
                return new javax.mail.PasswordAuthentication(senderID, senderPassword);
            }
        });

        Transport transport = null;
        try {
            transport = session.getTransport("smtp"); // Assuming SMTP protocol
            transport.connect(); // Attempt connection to trigger authentication
            return true;
        } catch (AuthenticationFailedException e) {
            return false;
        } catch (MessagingException e) {
            return false;
        } finally {
            if (transport != null) {
                try {
                    transport.close(); // Close the connection gracefully
                } catch (MessagingException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}