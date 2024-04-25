package main_package;

import java.util.Properties;
import java.util.Random;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.security.sasl.AuthenticationException;

public class Email {
	public int randomNumber;
	public static String Sender;
	public static String Reciver;
	public static String Mail;
	public Email(String Reciver) {
		Email.Reciver = Reciver;
	}
	//public static void main(String[] args)
	public String sendMail() {
        String to = Reciver;
        String from = "codehub5565@gmail.com";
        String host = "smtp.gmail.com";
        
        Sender = from;

        // Setup mail server
        Properties properties = System.getProperties();
        properties.put("mail.smtp.ssl.protocols","TLSv1.2");
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.auth", "true");

        // Get the Session object.// and pass username and password
        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
            protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
                return new javax.mail.PasswordAuthentication("codehub5565@gmail.com", "yfcp wgiu bjqr rmcs");
            }
        });
        //session.setDebug(true);
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
	
	 public void generateRandomNumber() {
	        Random random = new Random();
	        randomNumber = random.nextInt(900000) + 100000;
	    }

}