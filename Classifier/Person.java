
public class Person {
	private String classname;
	private String[] attributes = new String[16];
	
	
	public Person(String classname, String[] attributes) {
		this.classname = classname;
		this.attributes = attributes;
	}

	public String getClassname() {
		return classname;
	}
	public void setClassname(String classname) {
		this.classname = classname;
	}
	public String[] getAttributes() {
		return attributes;
	}
	public void setAttributes(String[] attributes) {
		this.attributes = attributes;
	}
	
	public void print() {
		System.out.print(this.getClassname()+ ",");
		for (int i = 0; i < 16; i++) {
			System.out.print(this.getAttributes()[i] + ",");
		}
		System.out.println();
	}
}
