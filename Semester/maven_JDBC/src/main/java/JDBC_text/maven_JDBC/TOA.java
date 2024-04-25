package JDBC_text.maven_JDBC;

public class TOA {
	private int id;
	private String name;
	private String gender;

	public TOA(int id, String name, String gender) {
		this.id = id;
		this.name = name;
		this.gender = gender;
	}

	public String getter() {
		String send = id + "    " + name + "    " + gender;
		return send;
	}

}
