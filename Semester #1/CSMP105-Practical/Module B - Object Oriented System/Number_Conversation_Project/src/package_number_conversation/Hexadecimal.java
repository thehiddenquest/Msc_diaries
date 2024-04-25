package package_number_conversation;

public class Hexadecimal extends Observer{

	private String hexadecimal;
	@Override
	public void update() {

		int decimal = (int) subject.getState();
		hexadecimal = Integer.toHexString(decimal);
	}
	public String getHexa() {
		return hexadecimal;
	}
	
}
