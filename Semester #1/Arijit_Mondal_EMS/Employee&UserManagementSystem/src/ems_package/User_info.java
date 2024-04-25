package ems_package;

import java.io.Serializable;
import java.util.HashMap;

import javax.swing.AbstractAction;

@SuppressWarnings("serial")
public abstract class User_info extends BaseEntity implements Serializable{
	String userid,password,emailid;
	public abstract void addUser(User_info u);
	public abstract void UserAuthenticate(User_info u);
	public abstract void ResetPass(User_info user);
}
