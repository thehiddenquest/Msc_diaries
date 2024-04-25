package com.Java.Messenger;

public class Message_notification extends Notification{

	@Override
	public String notify(String sender, String receiver, String... args) {
//		System.out.println();
		String message = args[0];
		String Text = "sending SMS to "+ receiver + " from "+ sender + " Message: "+ message;
		return Text;
	}

	@Override
	public Boolean verify(String senderID, String senderPassword) {
		// TODO Auto-generated method stub
		return true;
	}

}
