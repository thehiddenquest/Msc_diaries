package assignment1;

public class FactUtil{
	
	private static FactUtil instance = null;
	private FactUtil()
	{
		
	}
	public static FactUtil getInstance()
	{
		if(instance == null)
		{
			instance = new FactUtil();
		}
		return instance;
	}
	int factorial(int n) {
		int fact = 1;
		for(int i = n ; i > 0 ; i--)
			fact=fact*i;
		return fact;
	}

}
