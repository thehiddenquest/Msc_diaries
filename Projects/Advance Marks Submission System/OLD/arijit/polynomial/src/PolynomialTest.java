import javax.swing.*;

public class PolynomialTest {

	public static void main(String[] args) {
		double[] coef1 = {2,3,0,5};
		double[] coef2 = {0,2,2,4};
		Polynomial p1 = new Polynomial(coef1);
		Polynomial p2 = new Polynomial(coef2);
		
		JOptionPane.showMessageDialog(null,"p1="+p1);
		JOptionPane.showMessageDialog(null,"p2="+p2);
		JOptionPane.showMessageDialog(null,"p1(1)="+p1.evaluate(1));
		JOptionPane.showMessageDialog(null,"p2(2)="+p2.evaluate(2));

		//System.out.println("p1="+p1);
		//System.out.println("p2="+p2);
		//System.out.println("p1(1)="+p1.evaluate(1));
		//System.out.println("p2(2)="+p2.evaluate(2));
		
		Polynomial p3 = Polynomial.sum(p1, p2);
		//System.out.println("p1+p2="+p3);
		JOptionPane.showMessageDialog(null,"p1+p2="+p3);
		
		Polynomial p4 = Polynomial.diff(p1, p2);
		//System.out.println("p1-p2="+p4);
		JOptionPane.showMessageDialog(null,"p1-p2="+p4);
		
		Polynomial p5 = Polynomial.product(p1, p2);
		//System.out.println("p1 x p2="+p5);
		JOptionPane.showMessageDialog(null,"p1 x p2="+p5);
		
		p1.scale(2);
		//System.out.println("p1 after scaled by 2="+p1);
		JOptionPane.showMessageDialog(null,"p1 after scaled by 2="+p1);
	}

}
