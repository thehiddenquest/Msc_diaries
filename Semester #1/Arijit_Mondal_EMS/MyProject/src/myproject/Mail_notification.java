package myproject;

public class Mail_notification extends Notification{
	
	@Override
	public void notify(String sender, String reciver, String message) {
		Email email = new Email(reciver, message);
		email.sendMail();
		System.out.println("Mail send to : "+ reciver);
		System.out.println("from :"+ email.Sender); 
		System.out.println(" Mail: "+ message);
		
		
	}
}
