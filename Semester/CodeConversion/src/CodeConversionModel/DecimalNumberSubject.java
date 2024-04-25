package CodeConversionModel;

public class DecimalNumberSubject extends Subject{
	private int decimal;
	@Override
	public Object getState() {
		// TODO Auto-generated method stub
		return decimal;
	}
	public void setState(Object obj) {
		// TODO Auto-generated method stub
		decimal = (int) obj;
	}
	

}
