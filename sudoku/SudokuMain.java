import java.awt.*;
import javax.swing.*;

/**
 * The main Sudoku program
 */
public class SudokuMain extends JFrame {

  private static final long serialVersionUID = 1L; // to prevent serial warning

  // private variables
  GameBoardPanel board = new GameBoardPanel();
  JButton btnNewGame = new JButton("New Game");
  header help = new header();
  // Constructor
  public SudokuMain() {
    Container cp = getContentPane();
    cp.setLayout(new BorderLayout());
    cp.add(board, BorderLayout.CENTER);
    cp.add(help,BorderLayout.NORTH);
    // Add a button to the south to re-start the game via board.newGame()
    // ......

    // Initialize the game board to start the game
    board.newGame();

    pack(); // Pack the UI components, instead of using setSize()
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // to handle window-closing
    setTitle("Sudoku");
    setVisible(true);
  }

  /** The entry main() entry method */
  public static void main(String[] args) {
    //  the constructor of "SudokuMain"
    // Run the GUI construction in the Event-Dispatching thread for thread safety
    SwingUtilities.invokeLater(
      new Runnable() {
        @Override
        public void run() {
          new SudokuMain(); // Let the constructor do the job
        }
      }
    );
    // .........
  }
}
