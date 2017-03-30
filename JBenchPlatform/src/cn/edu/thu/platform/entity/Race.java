package cn.edu.thu.platform.entity;

public class Race {
	private String line1;
	private String line2;
	private String variable;
	private String packageClass;
	private String detail;

	public Race(String line1, String line2, String variable,
			String packageClass, String detail) {
		super();
		this.line1 = line1;
		this.line2 = line2;
		this.variable = variable;
		this.packageClass = packageClass;
		this.detail = detail;
	}

	public void Update(){
		if(Integer.parseInt(this.line1)>Integer.parseInt(this.line2)) {
			String temp = this.line1;
			this.line1 = this.line2;
			this.line2 = temp;
		}
	}
	public Race(String line1, String line2) {
		this.line1 = line1;
		this.line2 = line2;
	}

	public void setLine1(String line1) {
		this.line1 = line1;
	}

	public void setLine2(String line2) {
		this.line2 = line2;
	}

	public String getLine1() {
		return line1;
	}

	public String getLine2() {
		return line2;
	}

	public String getVariable() {
		return variable;
	}

	public void setVariable(String variable) {
		this.variable = variable;
	}

	public String getPackageClass() {
		return packageClass;
	}

	public void setPackageClass(String packageClass) {
		this.packageClass = packageClass;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((line1 == null) ? 0 : line1.hashCode());
		result = prime * result + ((line2 == null) ? 0 : line2.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Race other = (Race) obj;
		if (line1 == null) {
			if (other.line1 != null)
				return false;
		} else if (!line1.equals(other.line1))
			return false;
		if (line2 == null) {
			if (other.line2 != null)
				return false;
		} else if (!line2.equals(other.line2))
			return false;
		return true;
	}
}
