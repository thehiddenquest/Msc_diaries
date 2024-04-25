package CodeConversionModel;

public class OctalCodeConvertor extends Observer{
	private String OctalCode;
	@Override
	public void update() {
		int decimal = (int) subject.getState();
		OctalCode =Integer.toOctalString(decimal);
	}
	public String getOctCode()
	{
		return OctalCode;
	}

}
