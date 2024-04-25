package com.Java.Messenger;



public abstract class Notification_service {
	
	protected abstract Notification createNotification();
	
	public static String notify(String sender, String reciver,String notificationType, String... args) {
		return getNotificationService(notificationType).createNotification().notify(sender, reciver, args);
	}
	public static Boolean verify(String notificationType,String senderID,String senderPassword)
	{
		return getNotificationService(notificationType).createNotification().verify(senderID,senderPassword);
	}
	public static Notification_service getNotificationService(String notificationType) {
		if(notificationType.equalsIgnoreCase("TEXT")) {
			return new Message_notification_service();
		}
		else if(notificationType.equalsIgnoreCase("EMail")) {
			return new Mail_notification_service();
		}
		else
			return new Buzzer_notification_service();
	}
}
