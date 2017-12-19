import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import java.io.*;

/**
 * Defines a GUI that displays details of a FitnessProgram object
 * and contains buttons enabling access to the required functionality.
 */
public class SportsCentreGUI extends JFrame implements ActionListener {
	
	/** GUI JButtons */
	private JButton closeButton, attendanceButton;
	private JButton addButton, deleteButton;

	/** GUI JTextFields */
	private JTextField idIn, classIn, tutorIn;

	/** Display of class timetable */
	private JTextArea display;

	/** Display of attendance information */
	private ReportFrame report;

	/** Names of input text files */
	private final String classesInFile = "ClassesIn.txt";
	private final String classesOutFile = "ClassesOut.txt";
	private final String attendancesFile = "AttendancesIn.txt";

	private FitnessProgram fitnessProgram = new FitnessProgram();
	
	/**
	 * Constructor for AssEx3GUI class
	 */
	public SportsCentreGUI() {
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setTitle("Boyd-Orr Sports Centre");
		setSize(700, 300);
		display = new JTextArea();
		display.setFont(new Font("Monospaced", Font.PLAIN, 14));
		add(display, BorderLayout.CENTER);
		layoutTop();
		layoutBottom();
		this.initLadiesDay();
		this.initAttendances();
		System.err.println("===== Here is sorted attendances =====");
		FitnessClass [] sort = this.fitnessProgram.orderByDescending();
		for(FitnessClass fitClass : sort) {
			System.err.println(fitClass.averageAttendance());
		}
		System.err.println("Overall = " +this.fitnessProgram.getOverallAttendance());
	}

	public void displayError(String msg, String titlebar) {
		JOptionPane.showMessageDialog(this, msg, titlebar, JOptionPane.ERROR_MESSAGE);
	}

	/**
	 * Creates the FitnessProgram list ordered by start time
	 * using data from the file ClassesIn.txt
	 */
	public void initLadiesDay() {
		Scanner preparedFile = this.readInputFile(this.classesInFile);
		this.addFitnessClasses(preparedFile);
		this.updateDisplay(); 
	}


	public void addFitnessClasses(Scanner file) {
		try {
		    while (file.hasNext()) {
			    String fitClassId = file.next();
			    String fitClassName = file.next();
			    String fitClassTutor = file.next();
			    int fitClassStartTime = file.nextInt();
		    	boolean complete = this.fitnessProgram.insertFitnessClass(new FitnessClass(fitClassId, fitClassName, fitClassTutor, fitClassStartTime));
		    }
		} catch(Throwable t) {
			String errorString = String.format("Problem reading from file %s. The program will now close.", t.getMessage());
			JOptionPane.showMessageDialog(this, errorString, "File Not Found", JOptionPane.ERROR_MESSAGE);
			System.exit(0);
		}
	}

	public void addAttendanceFigures(Scanner file) {
		try {
		    while (file.hasNext()) {
			    String fitClassId = file.next();
			    int readFigures[] = {file.nextInt(), file.nextInt(), file.nextInt(), file.nextInt(), file.nextInt()};
				FitnessClass contains = this.fitnessProgram.findById(fitClassId);
				if(contains == null) return;
				contains.setAttendance(readFigures);
				System.err.println(contains.getAttendance());
		    }
		} catch(Throwable t) {
			String errorString = String.format("Problem reading from file %s. The program will now close.", t.getMessage());
			JOptionPane.showMessageDialog(this, errorString, "File Not Found", JOptionPane.ERROR_MESSAGE);
			System.exit(0);
		}
	}

	public Scanner readInputFile(String filePath) {
		Scanner scannerIn = null;
		try { 
			scannerIn = new Scanner(new File(filePath));
		} catch(FileNotFoundException e) {
			String errorString = String.format("Unable to open %s. The program will now close.", filePath);
			JOptionPane.showMessageDialog(this, errorString, "File Not Found", JOptionPane.ERROR_MESSAGE);
			System.exit(0);
		}
		return scannerIn;
	}

	/**
	 * Initialises the attendances using data
	 * from the file AttendancesIn.txt
	 */
	public void initAttendances() {
	    Scanner preparedFile = this.readInputFile(attendancesFile);
	    this.addAttendanceFigures(preparedFile);
	    FitnessClass[] list = this.fitnessProgram.getProgramArr();
	    FitnessClass test = list[0];
	    System.err.println("Average attendance is " + test.averageAttendance());
	}

	/**
	 * Instantiates timetable display and adds it to GUI
	 */
	public void updateDisplay() {
		String timetable = this.buildTimetable();
		this.display.setText(null);
		this.display.append(timetable);
	}

	public String buildTimetable() {
		int availTimes = this.fitnessProgram.getProgramArr().length;
		int beginTime = 9;
		String headings, timetableClasses, timetableTutor;
		headings = timetableClasses = timetableTutor = "";
		for(int i=0; i<availTimes;i++) {
			int timeslot = beginTime + i;
			int endTime = timeslot + 1; 
			String runningTime = String.format("%d - %d", timeslot, endTime);
			headings += String.format("%-10s", runningTime);
			FitnessClass fitClass = this.fitnessProgram.findByStartTime(timeslot);
			if( fitClass == null) {
				timetableClasses += String.format("%-10s", "Available");
				timetableTutor += String.format("%-10s", "");
			} else {
				timetableClasses += String.format("%-10s", fitClass.getClassname());
				timetableTutor += String.format("%-10s", fitClass.getTutor());
			}
		}
		String timetable = String.format("%s%n%s%n%s", headings, timetableClasses, timetableTutor);
		return timetable;
	}

	/**
	 * adds buttons to top of GUI
	 */
	public void layoutTop() {
		JPanel top = new JPanel();
		closeButton = new JButton("Save and Exit");
		closeButton.addActionListener(this);
		top.add(closeButton);
		attendanceButton = new JButton("View Attendances");
		attendanceButton.addActionListener(this);
		top.add(attendanceButton);
		add(top, BorderLayout.NORTH);
	}

	/**
	 * adds labels, text fields and buttons to bottom of GUI
	 */
	public void layoutBottom() {
		// instantiate panel for bottom of display
		JPanel bottom = new JPanel(new GridLayout(3, 3));

		// add upper label, text field and button
		JLabel idLabel = new JLabel("Enter Class Id");
		bottom.add(idLabel);
		idIn = new JTextField();
		bottom.add(idIn);
		JPanel panel1 = new JPanel();
		addButton = new JButton("Add");
		addButton.addActionListener(this);
		panel1.add(addButton);
		bottom.add(panel1);

		// add middle label, text field and button
		JLabel nmeLabel = new JLabel("Enter Class Name");
		bottom.add(nmeLabel);
		classIn = new JTextField();
		bottom.add(classIn);
		JPanel panel2 = new JPanel();
		deleteButton = new JButton("Delete");
		deleteButton.addActionListener(this);
		panel2.add(deleteButton);
		bottom.add(panel2);

		// add lower label text field and button
		JLabel tutLabel = new JLabel("Enter Tutor Name");
		bottom.add(tutLabel);
		tutorIn = new JTextField();
		bottom.add(tutorIn);

		add(bottom, BorderLayout.SOUTH);
	}

	public boolean isFieldEmpty(String str) {
		return !str.trim().equals("");
	}

	public boolean isFormValid() {
		return  this.isFieldEmpty(this.classIn.getText()) && 
				this.isFieldEmpty(this.tutorIn.getText());
	}

	public boolean isListFull() {
		int currentCount = this.fitnessProgram.getTotFitClasses();
		int spacesAvailable = this.fitnessProgram.getMaxNoClasses();
		return currentCount == spacesAvailable;
	}

	/**
	 * Processes adding a class
	 */
	public void processAdding() {
		if(isListFull()) 
			this.displayError("Cannot add class when list is already full", "Add class error");

		if(!this.isFormValid()) 
			this.displayError("Class and Tutor fields must cannot be empty", "Add class error");
			
	}

	/**
	 * Processes deleting a class
	 */
	public void processDeletion() {
	    FitnessClass searchResult = this.fitnessProgram.findById(this.idIn.getText());
	    if(searchResult == null) {
	    	this.displayError("Failed to delete class. No results matched your term.", "No results found");
	    	return;
	    }
	    this.fitnessProgram.removeFitnessClass(searchResult);
	    this.updateDisplay();
	}

	/**
	 * Instantiates a new window and displays the attendance report
	 */
	public void displayReport() {
		report = new ReportFrame(fitnessProgram);
	}

	/**
	 * Writes lines to file representing class name, 
	 * tutor and start time and then exits from the program
	 */
	public void processSaveAndClose() {
	    // your code here
	}

	/**
	 * Process button clicks.
	 * @param ae the ActionEvent
	 */
	public void actionPerformed(ActionEvent ae) {
	    if(ae.getSource() == this.attendanceButton) {
	    	this.displayReport();
	    } else if(ae.getSource() == this.deleteButton) {
	    	this.processDeletion();
	    } else if(ae.getSource() == this.addButton) {
	    	this.processAdding();
	    }
	}
}
