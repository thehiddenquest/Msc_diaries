package myproject;

public abstract class Notification_service {
	
	protected abstract Notification createNotification();
	
	public static void notify(String sender, String reciver, String message, String notificationType) {
		getNotificationService(notificationType).createNotification().notify(sender, reciver, message);
	}
	
	public static Notification_service getNotificationService(String notificationType) {
		if(notificationType.equalsIgnoreCase("Message")) {
			return new Message_notification_service();
		}
		else if(notificationType.equalsIgnoreCase("Mail")) {
			return new Mail_notification_service();
		}
		else
			return new Buzzer_notification_service();
	}
}
