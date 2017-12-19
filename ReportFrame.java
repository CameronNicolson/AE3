import java.awt.*;
import javax.swing.*;

/**
 * Class to define window in which attendance report is reported.
 */
public class ReportFrame extends JFrame {

		/** report of report window */
		private JTextArea report;
		private FitnessProgram fitnessProgram;

        ReportFrame(FitnessProgram fitProg) {
        	this.fitnessProgram = fitProg;
	        this.setTitle("Attendance Report");
		    this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			this.report = new JTextArea();
			this.report.setFont(new Font("Monospaced", Font.PLAIN, 14));
			this.getContentPane().add(report, BorderLayout.CENTER);
			this.setSize(700, 300);
			this.setLocationRelativeTo(null);
			this.setVisible(true);
			this.produceReport();
        }

        public void produceReport() {
        	String[] tHead = {"Id", "Class", "Tutor", "Attendances", "Average Attendance"};
        	FitnessClass[] programArr = this.fitnessProgram.orderByDescending();
        	String table = this.buildTable(tHead, programArr);
        	this.report.append(table);
        }

        public String buildTableHeader(Object[] headings, int[] headingSize) {
        	String formatString = "";
        	int pos = 0;
        	for (Object heading : headings) {
        		int columnSize = (headingSize[pos] / 2) + headingSize[pos];
           		formatString += "%-"+columnSize+"s";
           		pos++;
        	}
        	String header = String.format(formatString, headings); 
        	int tableWidth = header.length();
			String lines = new String(new char[tableWidth]).replace("\0", "=");
        	header += "\n" + lines + "\n";
        	return header;
        }        

        public String buildTableBody(Object[] data, int[] wordSize) {
           	String formatString = "";
           	int pos = 0;
           	for(Object obj : data) {
           		if(pos == 5) {
           		 formatString += "%n";
           		 pos = 0;
           		}
           		int columnSize = (wordSize[pos] / 2) + wordSize[pos];
           		formatString += "%-"+columnSize+"s";
           		pos++;
           	}
        	String body = String.format(formatString, data); 
        	return body;
        }

        public String buildTable(String[] th, FitnessClass[] td) {
        	String table = this.generateReportData(th, td);
        	return table;
        }

        public String printAttendanceArray(int[] arr) {
          String results = "";
	      for (int i = 0; i < arr.length; i++) {
	         if (i > 0) results += " ";
	         results += arr[i];
	      }
	      return results;
	   }

		public static int[] getLongestStrings(int[] currentLongest, String[] words) {
		      int[] stringNumericSizes = currentLongest;
		      for (int i= 0;i < words.length;i++) {
		          if (words[i].length() > stringNumericSizes[i]) {
		              stringNumericSizes[i] = words[i].length();
		          }
		      }
		      return stringNumericSizes;
		 }

		 public String buildSummary(int width) {
		 	String message = "Overall average: ";
		 	double columnSize = (2.0 / 3.0 * width) / 2;
		 	System.err.println("columnSize " + columnSize + "width "+ width);
		 	String summary = String.format("%n%n%"+columnSize+"s", message);
		 	summary += String.format("%.2f", this.fitnessProgram.getOverallAttendance());
		 	return summary;
		 }

        public String generateReportData(String[] columnNames, FitnessClass[] fitClassArr) {
        	int count = 0;
        	int noOfRows = this.fitnessProgram.getTotFitClasses();
        	int noOfColumns = 5;
        	int[] longestStrings = {0,0,0,0,0};
        	int totNoEnteries = noOfRows * noOfColumns;
        	String[] allEntries = new String[totNoEnteries];
        	for (FitnessClass fitClass : fitClassArr) {
        			if(fitClass == null) continue;
        			for(int i=0;i<noOfColumns;i++) {
        				String[] currentRowColumns = {fitClass.getId(), fitClass.getClassname(), fitClass.getTutor(), 
        				this.printAttendanceArray(fitClass.getAttendance()),""+fitClass.averageAttendance()};
        				allEntries[count] = currentRowColumns[i];
        				longestStrings = this.getLongestStrings(longestStrings, currentRowColumns);
        				count++;
        			}
        	}
        	String header = this.buildTableHeader(columnNames, longestStrings);
        	String body = this.buildTableBody(allEntries, longestStrings);
        	String summary = this.buildSummary(header.length());
        	return header + body + summary;
        }
}
