package assignment1;

// import java.util.Scanner;

import javax.swing.JOptionPane;


class FactorialTest{
	public static void main(String[] args)
	{
		FactUtil factutil = FactUtil.getInstance();
		/* Scanner sc = new Scanner(System.in); */
		System.out.print("Enter a number: ");
		/* int n = sc.nextInt(); */
		String input = JOptionPane.showInputDialog("Enter a number");
		int n = Integer.parseInt(input);
		int x = factutil.factorial(n);
		JOptionPane.showMessageDialog(null, "Factorial of " + n + " is " + x);
		//System.out.println("Factorial of "+ n + " i +s " x);
	}
}

