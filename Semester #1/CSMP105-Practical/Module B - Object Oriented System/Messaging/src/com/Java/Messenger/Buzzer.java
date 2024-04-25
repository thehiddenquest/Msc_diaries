package com.Java.Messenger;

import java.awt.Toolkit;

public class Buzzer extends Notification{

	@Override
	public String notify(String sender, String receiver, String... args) {
//		System.out.println();
		String Text = "*Beep*";

		playCustomBeep(1000, 500);
		return Text;
	}
    public static void playCustomBeep(int frequency, int duration) {
        try {
            Toolkit toolkit = Toolkit.getDefaultToolkit();
            toolkit.beep(); // Trigger the default beep first
            Thread.sleep(200); // Add a small delay before the custom beep

            // Use java.awt.Toolkit's beep method to simulate a beep with the specified frequency and duration
            for (int i = 0; i < duration / 200; i++) {
                toolkit.beep();
                Thread.sleep(frequency);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

	@Override
	public Boolean verify(String senderID, String senderPassword) {
		// TODO Auto-generated method stub
		return null;
	}

}
