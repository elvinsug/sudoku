import java.awt.*; // Using AWT container / component
import java.awt.event.*; // Using AWT event / listener interfaces
// An AWT program inherits from the top‐level container Frame
public class AWTCounter extends Frame {
    private Label lblCount; // Declare a Label component
    private TextField tfCount; // Declare a TextField component
    private Button btnCount; // Declare a Button component
    private int count = 0; // Counter's value
    // Constructor to setup GUI components and event handlers
    public AWTCounter () {
        setLayout(new FlowLayout());
        // "super" Frame (Container) sets its layout to FlowLayout
        // to arrange the components from left‐to‐right,
        // and flow to next row from top‐to‐bottom.
        lblCount = new Label("Counter"); // construct the Label
        add(lblCount); // "super" Frame adds Label
        tfCount = new TextField(count + "", 10); // construct TextField
        tfCount.setEditable(false); // set to read‐only
        add(tfCount); // "super" Frame adds TextField
        btnCount = new Button("Count"); // construct the Button
        add(btnCount); // "super" Frame adds Button
        BtnCountListener listener = new BtnCountListener();
        btnCount.addActionListener(listener);
        // "btnCount" is the source that fires an ActionEvent when
        // clicked. The source adds an instance of BtnCountListener
        // as an ActionEvent listener, which provides an ActionEvent
        // handler called actionPerformed().
        // Clicking "Count" button calls back actionPerformed().
        setTitle("AWT Counter"); // "super" Frame sets title
        setSize(300, 100); // "super" Frame sets initial size
        setVisible(true); // "super" Frame shows
    }
    // The entry main() method
    public static void main(String[] args) {
        // Invoke the constructor to setup the GUI
        AWTCounter app = new AWTCounter();
    }
    // Define an inner class to handle the "Count" button‐click
    private class BtnCountListener implements ActionListener {
        // ActionEvent handler ‐ Called back upon button‐click.
        @Override
        public void actionPerformed(ActionEvent evt) {
            ++count; // Increase the counter value
            // Display the counter value on the TextField tfCount
            tfCount.setText(count + ""); // Convert int to String
        }
    }
}