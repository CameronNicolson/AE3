/** Defines an object representing a single fitness class
 */
public class FitnessClass implements Comparable<FitnessClass> {
	private final int weeks;
	private String id;
	private String classname;
	private String tutor; 
	private int startTime;
	private int [] attendance;
	
	FitnessClass(String id, String classname, String tutor, int startTime) {
		this.id = id;
		this.classname = classname;
		this.tutor = tutor;
		this.startTime = startTime;
		this.weeks = 0;
		this.attendance = null;
	}
	
	FitnessClass(){
		 this.weeks = 0;
		 this.classname = "";
		 this.tutor = "";
		 this.startTime = 0;
		 this.id = "";
		 this.attendance = null;
	}
	
	private String generateClassID() {
		StringBuilder id = new StringBuilder();
		char classIntial = this.classname.charAt(0);
		char tutorIntial = this.tutor.charAt(0);
		id.append(classIntial);
		id.append(tutorIntial);
		id.append(this.startTime);
		return id.toString(); 
	}
	
	private int findFreeSlot() {
		return 0;
	}

    public int compareTo(FitnessClass other) {
	  return 0; // replace with your code
    }

    public String getId() {
    	return this.id;
    }

    public String getClassname() {
    	return this.classname;
    }

    public String getTutor() {
    	return this.tutor;
    }

    public int getStartTime() {
    	return this.startTime;
    }

    public int[] getAttendance() {
    	return this.attendance;
    }

    public void setAttendance(int[] arr) {
    	this.attendance = arr;
    }
}
