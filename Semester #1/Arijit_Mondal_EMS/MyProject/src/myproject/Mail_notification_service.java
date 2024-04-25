package myproject;

public class Mail_notification_service extends Notification_service {

	@Override
	protected Notification createNotification() {
		
		return new Mail_notification();
	}

}
