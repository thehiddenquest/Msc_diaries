package package_number_conversation;

public class Octadecimal extends Observer{

	private String octadecimal;
	@Override
	public void update() {

		int decimal = (int) subject.getState();
		octadecimal = Integer.toOctalString(decimal);
	}
	public String getOcta() {
		return octadecimal;
	}
	
}
