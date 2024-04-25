package package_number_conversation;

public class Binary extends Observer{

	private String binary = null;
	@Override
	public void update() {

		int decimal = (int) subject.getState();
		System.out.print(decimal);
		binary = Integer.toBinaryString(decimal);
	}
	public String getBinary() {
		return binary;
	}
	
}
