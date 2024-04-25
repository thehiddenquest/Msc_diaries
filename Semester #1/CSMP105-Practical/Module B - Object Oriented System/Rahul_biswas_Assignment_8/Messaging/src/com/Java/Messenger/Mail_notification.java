package com.Java.Messenger;

public class Mail_notification extends Notification{
	public Email em;
	@Override
	public String notify(String sender, String receiver, String... args) {
        String subject = args[0];
        String message = args[1];
		em = new Email(sender,receiver,subject,message);
		String Text = "sending mail to "+ receiver + " from "+ sender + " Mail: "+ message;
		return Text;
	}
}
