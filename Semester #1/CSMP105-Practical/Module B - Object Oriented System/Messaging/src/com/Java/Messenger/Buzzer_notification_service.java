package com.Java.Messenger;

public class Buzzer_notification_service extends Notification_service {

	@Override
	protected Notification createNotification() {
		
		return new Buzzer();
	}

}
