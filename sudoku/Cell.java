import java.awt.Color;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.JTextField;
import javax.swing.border.Border;

/**
 * The Cell class model the cells of the Sudoku puzzle, by customizing (subclass)
 * the javax.swing.JTextField to include row/column, puzzle number and status.
 */
public class Cell extends JTextField {

  private static final long serialVersionUID = 1L; // to prevent serial warning

  // Define named constants for JTextField's colors and fonts
  //  to be chosen based on CellStatus
  public static final Color BG_GIVEN = Color.WHITE;
  public static final Color FG_GIVEN = Color.BLACK;
  public static final Color FG_NOT_GIVEN = Color.GRAY;
  public static final Color BG_TO_GUESS = new Color(255, 255, 153);
  public static final Color BG_CORRECT_GUESS = new Color(153, 255, 153);
  public static final Color BG_WRONG_GUESS = new Color(255, 153, 153);
  public static final Font FONT_NUMBERS = new Font("Arial", Font.BOLD, 24);

  // Define properties (package-visible)
  /** The row and column number [0-8] of this cell */
  int row, col;
  /** The puzzle number [1-9] for this cell */
  int number;
  /** The status of this cell defined in enum CellStatus */
  CellStatus status;

  /** Constructor */
  /** Constructor */
  public Cell(int row, int col) {
    super(); // JTextField
    this.row = row;
    this.col = col;
    // Inherited from JTextField: Beautify all the cells once for all
    super.setHorizontalAlignment(JTextField.CENTER);
    super.setFont(FONT_NUMBERS);
    

    // Set the background color of all cells to white (hex code #ffffff)
    super.setBackground(Color.WHITE);

    // Base border thickness for all cells
    int top = 1;
    int left = 1;
    int bottom = 1;
    int right = 1;

    // Add thicker border for the outer edges and the 3x3 region borders
    if (row == 0) { // Top edge
      top += 2;
    }
    if (col == 0) { // Left edge
      left += 2;
    }
    if (row == SudokuConstants.GRID_SIZE - 1) { // Bottom edge
      bottom += 2;
    }
    if (col == SudokuConstants.GRID_SIZE - 1) { // Right edge
      right += 2;
    }

    // Add thicker border to separate the 3x3 regions
    if (row % 3 == 0 && row != 0) { // Top edge of a 3x3 block
      top += 1;
    }
    if (col % 3 == 0 && col != 0) { // Left edge of a 3x3 block
      left += 1;
    }
    if ((row + 1) % 3 == 0 && row != SudokuConstants.GRID_SIZE - 1) { // Bottom edge of a 3x3 block
      bottom += 1;
    }
    if ((col + 1) % 3 == 0 && col != SudokuConstants.GRID_SIZE - 1) { // Right edge of a 3x3 block
      right += 1;
    }

    // Create the border with the calculated thicknesses
    Border border = BorderFactory.createMatteBorder(
      top,
      left,
      bottom,
      right,
      Color.BLACK
    );

    // Set the background color of all cells to white (hex code #ffffff)
    super.setBackground(Color.WHITE);

    super.setBorder(border);
  }

  /** Reset this cell for a new game, given the puzzle number and isGiven */
  public void newGame(int number, boolean isGiven) {
    this.number = number;
    status = isGiven ? CellStatus.GIVEN : CellStatus.TO_GUESS;
    paint(); // paint itself
  }

  /** This Cell (JTextField) paints itself based on its status */
  public void paint() {
    if (status == CellStatus.GIVEN) {
      // Inherited from JTextField: Set display properties
      super.setText(number + "");
      super.setEditable(false);
      super.setBackground(BG_GIVEN);
      super.setForeground(FG_GIVEN);
    } else if (status == CellStatus.TO_GUESS) {
      // Inherited from JTextField: Set display properties
      super.setText("");
      super.setEditable(true);
      super.setBackground(Color.WHITE); // Change the background color to white (hex code #ffffff)
      super.setForeground(FG_NOT_GIVEN);
    } else if (status == CellStatus.CORRECT_GUESS) { // from TO_GUESS
      super.setBackground(BG_CORRECT_GUESS);
    } else if (status == CellStatus.WRONG_GUESS) { // from TO_GUESS
      super.setBackground(BG_WRONG_GUESS);
    }
  }

  public void setStatus(CellStatus wrongGuess) {}

  public Object getRow() {
    return null;
  }

  public Object getCol() {
    return null;
  }
}
