package CodeConversionModel;

public class BinaryCodeConvertor extends Observer{
	private String binCode;
	@Override
	public void update() {
		int decimal = (int) subject.getState();
		binCode =Integer.toBinaryString(decimal);
	}
	public String getBinCode()
	{
		return binCode;
	}

}
