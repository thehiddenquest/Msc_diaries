package com.Java.Messenger;

import java.util.regex.Pattern;



public class Mail_notification extends Notification {
	public Email em =new Email();
	private String passedNotification = "";

	private Boolean emailValidate(String emailID) {
		if (emailID == null || emailID.isEmpty()) {
			passedNotification = "Empty email ID";
			return false;
		}
		final String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\." + "[a-zA-Z0-9_+&*-]+)*@"
				+ "(?:[a-zA-Z0-9-])+(\\.[a-zA-Z]{2,7})+(\\.[a-zA-Z]{2,8})?$";
		Pattern pattern = Pattern.compile(emailRegex, Pattern.CASE_INSENSITIVE);
		if (!pattern.matcher(emailID).matches()) {
			passedNotification = "Invalid email id: " + emailID;
			return false;
		}
		return true;
	}

	private Boolean emailEmptyMesseage(String message) {
		if (message == null || message.isEmpty()) {
			passedNotification = "Please Enter some message!!";
			return false;
		}
		return true;
	}

	private Boolean emailVerifier(String emailID, String message) {
		if (!emailValidate(emailID) || !emailEmptyMesseage(message)) {
			return false;
		}
		return true;
	}

	@Override
	public String notify(String sender, String receiver, String... args) {
		String subject = args[0];
		String message = args[1];
		String password = args[2];
		if (!emailVerifier(receiver, message)) {
			return passedNotification;
		}
		if (em.sendEmail(sender, receiver, subject, message,password)) {
			passedNotification = "Email is successfully send to" + receiver;
		}
		else
		{
			passedNotification= "Email not sent to " + receiver;
		}
		return passedNotification;
	}

	@Override
	public Boolean verify(String senderID, String senderPassword) {
		return em.emailVerifier(senderID, senderPassword);
	}
}
