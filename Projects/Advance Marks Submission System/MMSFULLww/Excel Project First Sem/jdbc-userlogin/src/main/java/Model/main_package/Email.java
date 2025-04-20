package Model.main_package;

import java.util.Properties;
import java.util.Random;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.security.sasl.AuthenticationException;

import com.itextpdf.io.IOException;

public class Email {
    public int randomNumber;
    public static String Sender;
    public static String Reciver;
    public static String Mail;

    public Email(String Reciver) {
        Email.Reciver = Reciver;
    }

    // public static void main(String[] args)
    public String sendMail() {
        String to = Reciver;
        String from = "***********@gmail.com";
        String host = "smtp.gmail.com";
        Sender = from;

        // Setup mail server
        Properties properties = System.getProperties();
        properties.put("mail.smtp.ssl.protocols", "TLSv1.2");
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.auth", "true");

        // Get the Session object.// and pass username and password
        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
            protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
                return new javax.mail.PasswordAuthentication("XXXX.@gmail.com", "xxxx xxxx xxxx xxxx");
            }
        });
        // session.setDebug(true);
        try {
            // Create a default MimeMessage object.
            generateRandomNumber();
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            message.setSubject("YOUR ONE TIME PASSWORD IS HERE");
            message.setText(String.valueOf(randomNumber));
            Transport.send(message);
            return String.valueOf(randomNumber);
        } catch (MessagingException mex) {
            mex.printStackTrace();
            return null;
        }
    }

    // To sent PDF Attachment
    public void sendAttachment(String senderUserName, String senderEmailID, String attachmentPath) {
        String to = Reciver;
        String from = "***********@gmail.com";
        String host = "smtp.gmail.com";

        Sender = from;

        // Setup mail server
        Properties properties = System.getProperties();
        properties.put("mail.smtp.ssl.protocols", "TLSv1.2");
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.auth", "true");

        // Get the Session object.// and pass username and password
        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
            protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
                return new javax.mail.PasswordAuthentication("***********@gmail.com", "**** **** **** ****");
            }
        });
        try {
            // Create a default MimeMessage object.
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            message.setSubject("Marksheet Report from " + senderUserName);

            // Create the message body part
            MimeBodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setText(
                    "The marksheet is currently being forwarded by " + senderUserName + " ( " + senderEmailID + " )");

            // Create the attachment body part
            MimeBodyPart attachmentPart = new MimeBodyPart();
            try {
                attachmentPart.attachFile(attachmentPath);
            } catch (java.io.IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            // Create Multipart object to add multiple parts to the email
            MimeMultipart multipart = new MimeMultipart();
            multipart.addBodyPart(messageBodyPart);
            multipart.addBodyPart(attachmentPart);

            // Set the multipart object as the content of the message
            message.setContent(multipart);

            // Send the message
            Transport.send(message);
        } catch (MessagingException mex) {
            mex.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void generateRandomNumber() {
        Random random = new Random();
        randomNumber = random.nextInt(900000) + 100000;
    }

}