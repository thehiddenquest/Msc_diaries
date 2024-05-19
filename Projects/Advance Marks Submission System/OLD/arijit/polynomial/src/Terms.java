

public class Terms {
	protected double coeff;
	protected int power;
	
	public Terms(double c, int p) {
		coeff = c;
		power = p;
	}
	public double getCoeff() {
        return coeff;
    }

    public int getPower() {
        return power;
    }
}
