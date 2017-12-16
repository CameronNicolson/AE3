import java.awt.*;
import javax.swing.*;

/**
 * Class to define window in which attendance report is reported.
 */
public class ReportFrame extends JFrame {

		/** report of report window */
		private JTextArea report;

        ReportFrame() {
	        this.setTitle("Attendance Report");
		    this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			this.report = new JTextArea();
			this.report.setFont(new Font("Monospaced", Font.PLAIN, 14));
			this.getContentPane().add(report, BorderLayout.CENTER);
			this.setSize(700, 300);
			this.setLocationRelativeTo(null);
			this.setVisible(true);
        }
}
