package myproject;

public class Buzzer extends Notification{

	@Override
	public void notify(String sender, String reciver, String message) {
		System.out.println("*Beep*");
	}

}
