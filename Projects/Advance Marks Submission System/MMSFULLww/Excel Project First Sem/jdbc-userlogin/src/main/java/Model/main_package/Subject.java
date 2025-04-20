package Model.main_package;
public class Subject {
	private String name = null;
	private String[] fullMarks = new String[3];

	public Subject() {
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setMarks(String[] fullMarks) {
		this.fullMarks = fullMarks;
	}

	public String[] getMarks() {
		return fullMarks;
	}

}