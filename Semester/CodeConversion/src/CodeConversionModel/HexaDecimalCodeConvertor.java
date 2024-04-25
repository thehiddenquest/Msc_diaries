package CodeConversionModel;

public class HexaDecimalCodeConvertor extends Observer{
	private String hexCode;
	@Override
	public void update() {
		int decimal = (int) subject.getState();
		hexCode =Integer.toHexString(decimal);
	}
	public String getHexCode()
	{
		return hexCode;
	}

}
