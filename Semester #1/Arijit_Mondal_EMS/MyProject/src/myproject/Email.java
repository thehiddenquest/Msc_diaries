package myproject;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.security.sasl.AuthenticationException;

public class Email {

	public static String Sender;
	public static String Reciver;
	public static String Mail;
	public Email(String Reciver, String Mail) {
		Email.Reciver = Reciver;
		Email.Mail = Mail;
	}
	//public static void main(String[] args)
	public void sendMail() {
        String to = Reciver;
        String from = "arijitps2112@gmail.com";
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
                return new javax.mail.PasswordAuthentication("arijitps2112@gmail.com", "lnla vxaf gesw gifl");
            }
        });
        //session.setDebug(true);
        try {
            // Create a default MimeMessage object.
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            message.setSubject("This is the Subject Line!");
            message.setText(Mail);
            System.out.println("sending...");
            Transport.send(message);
            System.out.println("Sent message successfully....");
        } catch (MessagingException mex) {
            mex.printStackTrace();
        }
    }

}