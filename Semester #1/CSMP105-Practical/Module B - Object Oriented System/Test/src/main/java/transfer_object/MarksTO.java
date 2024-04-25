package transfer_object;

public class MarksTO implements BaseTO{
	
	private String pt,fm,mo,co,ca,nu,pc;
	
	public MarksTO(String pt, String fm, String mo, String co, String ca, String nu, String pc) {
		this.pt = pt;
		this.fm = fm;
		this.mo = mo;
		this.co = co;
		this.ca = ca;
		this.nu = nu;
		this.pc = pc;
	}

	public String getPt() {
		return pt;
	}

	public void setPt(String pt) {
		this.pt = pt;
	}

	public String getFm() {
		return fm;
	}

	public void setFm(String fm) {
		this.fm = fm;
	}

	public String getMo() {
		return mo;
	}

	public void setMo(String mo) {
		this.mo = mo;
	}

	public String getCo() {
		return co;
	}

	public void setCo(String co) {
		this.co = co;
	}

	public String getCa() {
		return ca;
	}

	public void setCa(String ca) {
		this.ca = ca;
	}

	public String getNu() {
		return nu;
	}

	public void setNu(String nu) {
		this.nu = nu;
	}

	public String getPc() {
		return pc;
	}

	public void setPc(String pc) {
		this.pc = pc;
	}
	
}