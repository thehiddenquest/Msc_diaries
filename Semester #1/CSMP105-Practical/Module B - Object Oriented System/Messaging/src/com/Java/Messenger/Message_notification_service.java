package com.Java.Messenger;

public class Message_notification_service extends Notification_service {

	@Override
	protected Notification createNotification() {
		
		return new Message_notification();
	}

}
