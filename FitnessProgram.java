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
	private int totFitClasses; 
    
    FitnessProgram() {
        this.programArr = new FitnessClass[7];
    }

    public boolean insertFitnessClass(FitnessClass fitClass) {
        int x = fitClass.getStartTime() - 9;
        if(this.programArr[x] != null ) {
          return false;
        } 
        this.programArr[x] = fitClass;
        totFitClasses++;
        return true;      
    }

    public boolean removeFitnessClass(FitnessClass fitClass) {
        int x = fitClass.getStartTime() - 9;
        if(this.programArr[x] == null) return false;
        this.programArr[x] = null;
        totFitClasses--;
        return true;
    }

    public FitnessClass findById(String query) {
        FitnessClass match = null;
        for(FitnessClass fitClass : programArr ) {
            if(fitClass == null) continue;
            if(fitClass.getId().equals(query)) {
                System.err.println("yes it equals id = " + fitClass.getClassname());
                match = fitClass;
                break;
            }
        }
        return match;
    }

    public FitnessClass findByStartTime(int t) {
        FitnessClass match = null;
        for(FitnessClass fitClass : programArr ) {
            if(fitClass == null) continue;
            if(fitClass.getStartTime() == t) {
                System.err.println("yes it equals start time = " + fitClass.getClassname());
                match = fitClass;
                break;
            }
        }
        return match;
    }

   public FitnessClass[] orderByDescending () {
        FitnessClass[] sortList = new FitnessClass[this.totFitClasses];
        int count = 0;
        for(FitnessClass fitClass : this.programArr) {
            if(fitClass == null) continue;
            sortList[count] = fitClass;
            count++;
        }
        Arrays.sort(sortList);
        return sortList;
    }

    public double getOverallAttendance() {
        FitnessClass[] order = this.orderByDescending();
        int count = 0;
        double sum = 0;
        double average = 0;
        for(FitnessClass fitClass : order) {
            sum += fitClass.averageAttendance();
            count++;
        }
        average = sum / count;
        return average;
    }

    public int getTotFitClasses() {
        return this.totFitClasses;
    }

    public int getMaxNoClasses() {
        return this.maxNoClasses;
    }

    public FitnessClass[] getProgramArr() {
        return this.programArr;
    }

}