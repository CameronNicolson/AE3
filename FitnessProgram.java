import java.util.*;

/**
 * Maintains a list of Fitness Class objects
 * The list is initialised in order of start time
 * The methods allow objects to be added and deleted from the list
 * In addition an array can be returned in order of average attendance
 */
public class FitnessProgram {
	private FitnessClass [] programArr;
	final int maxNoClasses = 7;
	private int noClassesAdded; 
    
    FitnessProgram() {
        this.programArr = new FitnessClass[7];

        for(int x = 0; x < maxNoClasses; x++) {
            this.programArr[x] = null;
            System.err.println(this.programArr[x]);
        }

    }

    public void insertFitnessClass(FitnessClass fitClass) {
        int x = fitClass.getStartTime() - 9;
        if(this.programArr[x] == null ) {
             System.out.printf("OK ADDED: %s %s %s pos %s %n", fitClass.getId(), fitClass.getTutor(), fitClass.getClassname(), x);
             this.programArr[x] = fitClass;
             noClassesAdded++;
        }
       
    }
}
