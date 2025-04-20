package Model.transfer_object;

public class MarksTO {
	
	private String paperCode;
	private String paperTitle;
	private String coll;
	private String cate;
	private String number;
	private String paperType;
	private String fullMarks;
	private String marksObt;
	private String stream;
	private String sem;
	private String year;
	private String subject;
	private String TeachersName;
	public MarksTO() {
	
	}
	public MarksTO(String paperCode, String paperTitle, String coll, String cate, String number, String paperType,
			String fullMarks, String marksObt, String stream, String sem, String year, String subject) {
		this.paperCode = paperCode;
		this.paperTitle = paperTitle;
		this.coll = coll;
		this.cate = cate;
		this.number = number;
		this.paperType = paperType;
		this.fullMarks = fullMarks;
		this.marksObt = marksObt;
		this.stream = stream;
		this.sem = sem;
		this.year = year;
		this.subject = subject;
	}

	public String getPaperCode() {
        return paperCode;
    }

    public void setPaperCode(String paperCode) {
        this.paperCode = paperCode;
    }

    public String getPaperTitle() {
        return paperTitle;
    }

    public void setPaperTitle(String paperTitle) {
        this.paperTitle = paperTitle;
    }

    public String getColl() {
        return coll;
    }

    public void setColl(String coll) {
        this.coll = coll;
    }

    public String getCate() {
        return cate;
    }

    public void setCate(String cate) {
        this.cate = cate;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getPaperType() {
        return paperType;
    }

    public void setPaperType(String paperType) {
        this.paperType = paperType;
    }

    public String getFullMarks() {
        return fullMarks;
    }

    public void setFullMarks(String fullMarks) {
        this.fullMarks = fullMarks;
    }

    public String getMarksObt() {
        return marksObt;
    }

    public void setMarksObt(String marksObt) {
        this.marksObt = marksObt;
    }

    public String getStream() {
        return stream;
    }

    public void setStream(String stream) {
        this.stream = stream;
    }

    public String getSem() {
        return sem;
    }

    public void setSem(String sem) {
        this.sem = sem;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }
	public void setTeachersName(String string) {
		this.TeachersName = string;
	}
	public String getTeachersName()
	{
		return TeachersName;
	}

}
