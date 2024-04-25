package com.Java.Messenger;

public abstract class Notification {
	 public abstract String notify(String sender, String receiver, String... args);
	 public abstract Boolean verify(String senderID,String senderPassword);
}
