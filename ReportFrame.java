import java.awt.*;
import javax.swing.*;

/**
 * Class to define window in which attendance report is displayed.
 */
public class ReportFrame extends JFrame {

		/** Display of class timetable */
		private JTextArea display;

        ReportFrame() {
	        this.setTitle("Attendance Reportffff");
		    this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			this.display = new JTextArea();
			this.display.setFont(new Font("Monospaced", Font.PLAIN, 14));
			this.getContentPane().add(display, BorderLayout.CENTER);
			this.setSize(700, 300);
			this.setLocationRelativeTo(null);
			this.setVisible(true);
        }
}
