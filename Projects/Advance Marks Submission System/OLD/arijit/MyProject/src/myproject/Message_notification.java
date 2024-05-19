package myproject;

public class Message_notification extends Notification{

	@Override
	public void notify(String sender, String reciver, String message) {
		System.out.println("sending SMS to "+ reciver + " from "+ sender + " Message: "+ message);
	}

}
