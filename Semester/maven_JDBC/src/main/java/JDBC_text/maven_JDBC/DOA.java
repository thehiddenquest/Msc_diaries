package JDBC_text.maven_JDBC;

import java.util.ArrayList;

public class DOA {
	
	private ArrayList<TOA> toaList = null;
	sqlQuery Query = null;
	public DOA() {
		this.Query = new sqlQuery();
	}
	public void InsertData(String name,String gender) {
		Query.setDataBase(name, gender);
	}
	private ArrayList<TOA> GetData() {
		toaList = Query.getData();
		return toaList;
	}
	public void printdata() {
		GetData();
		if (toaList == null)
			System.out.println("No data found");
		else {
			System.out.println("|ID|  |Name|  |gender|");
			for (TOA toa : toaList) {
				String recieve = toa.getter();
				System.out.println(recieve);
			}
		}
	}
	public void closeConnection()
	{
		Query.closeConnection();
	}


}
