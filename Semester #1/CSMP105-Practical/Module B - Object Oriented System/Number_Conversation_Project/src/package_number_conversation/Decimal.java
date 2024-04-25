package package_number_conversation;

public class Decimal extends Subject{

	private int integer;
	
	@Override
	public Object getState() {
		// TODO Auto-generated method stub
		return integer;
	}

	@Override
	public void setState(Object obj) {
		// TODO Auto-generated method stub
		integer = (int) obj;
	}
	

}
