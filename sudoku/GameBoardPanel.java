import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class GameBoardPanel extends JPanel {

  public static int mistake = 0;
  public static int cellLeft = 2;

  private static final long serialVersionUID = 1L; // to prevent serial warning

  // Define named constants for UI sizes
  public static final int CELL_SIZE = 60; // Cell width/height in pixels
  public static final int BOARD_WIDTH = CELL_SIZE * SudokuConstants.GRID_SIZE;
  public static final int BOARD_HEIGHT = CELL_SIZE * SudokuConstants.GRID_SIZE;
  // Board width/height in pixels

  // Define properties
  /** The game board composes of 9x9 Cells (customized JTextFields) */
  public static Cell[][] cells = new Cell[SudokuConstants.GRID_SIZE][SudokuConstants.GRID_SIZE];
  /** It also contains a Puzzle with array numbers and isGiven */
  public Puzzle puzzle = new Puzzle();

  /** Constructor */
  public GameBoardPanel() {
    super.setLayout(
        new GridLayout(SudokuConstants.GRID_SIZE, SudokuConstants.GRID_SIZE)); // JPanel

    setOpaque(false);

    // Allocate the 2D array of Cell, and added into JPanel.
    for (int row = 0; row < SudokuConstants.GRID_SIZE; ++row) {
      for (int col = 0; col < SudokuConstants.GRID_SIZE; ++col) {
        cells[row][col] = new Cell(row, col);
        super.add(cells[row][col]); // JPanel
      }
    }

    puzzle.newPuzzle();

    super.setPreferredSize(new Dimension(BOARD_WIDTH, BOARD_HEIGHT));
    // super.setSize(new Dimension(BOARD_WIDTH, BOARD_HEIGHT));
  }

  public void newGame() {

    for (int row = 0; row < SudokuConstants.GRID_SIZE; ++row) {
      for (int col = 0; col < SudokuConstants.GRID_SIZE; ++col) {
        super.remove(cells[row][col]);
        cells[row][col] = new Cell(row, col);
        super.add(cells[row][col]);
      }
    }
    super.setPreferredSize(new Dimension(BOARD_WIDTH, BOARD_HEIGHT));
    puzzle.newPuzzle();
    mistake = 0;
    cellLeft = SudokuGenerator.numFalse;
    header.hintUsed = 0;
    header.labelMistake.setText("Mistakes: " + mistake);
    header.labelCellLeft.setText("Cells Left: " + cellLeft);
    header.labelHintUsed.setText("Hints Used: " + header.hintUsed);

    StopwatchGUI.seconds = 0;
    StopwatchGUI.timer.start();
    // Generate a new puzzle

    CellInputListener cellInputListener = new CellInputListener();

    // Initialize all the 9x9 cells, based on the puzzle.
    for (int row = 0; row < SudokuConstants.GRID_SIZE; ++row) {
      for (int col = 0; col < SudokuConstants.GRID_SIZE; ++col) {
        cells[row][col].newGame(
            Puzzle.numbers[row][col],
            Puzzle.isGiven[row][col]);
        cells[row][col].paintDefault(row, col);
      }
    }
    for (int row = 0; row < SudokuConstants.GRID_SIZE; ++row) {
      for (int col = 0; col < SudokuConstants.GRID_SIZE; ++col) {
        if (!Puzzle.isGiven[row][col]) {
          cells[row][col].addKeyListener(cellInputListener);
        }
      }
    }
  }

  /**
   * Return true if the puzzle is solved
   * i.e., none of the cell have status of TO_GUESS or WRONG_GUESS
   */
  public boolean isSolved() {
    for (int row = 0; row < SudokuConstants.GRID_SIZE; ++row) {
      for (int col = 0; col < SudokuConstants.GRID_SIZE; ++col) {
        if (cells[row][col].status == CellStatus.TO_GUESS ||
            cells[row][col].status == CellStatus.WRONG_GUESS) {
          return false;
        }
      }
    }
    return true;
  }

  public class CellInputListener implements KeyListener {

    @Override
    public void keyTyped(KeyEvent e) {
      JTextField sourceCell = (JTextField) e.getSource();
      Cell cell = (Cell) sourceCell; // Correct casting from JTextField to Cell

      // Check if the cell's status is not CORRECT_GUESS
      if (cell.status != CellStatus.CORRECT_GUESS) {
        char c = e.getKeyChar(); // Get the character typed

        // Check if the character is between '0' and '9' (including '0')
        if (c >= '1' && c <= '9') {
          // Set the text to this character if it's valid, replacing any existing content
          sourceCell.setText(String.valueOf(c));
          e.consume(); // Prevent further processing to avoid duplication
        } else {
          // Ignore the character if it's not a number between '0' and '9'
          e.consume();
        }
      } else {
        // Consume the key event to prevent the character from being inserted into the
        // cell
        e.consume();
      }
    }

    @Override
    public void keyPressed(KeyEvent e) {
      JTextField sourceTextField = (JTextField) e.getSource();
      String text = sourceTextField.getText();

      if (text.matches("[1-9]")) { // Verify if the content is exactly one digit
        int numberIn = Integer.parseInt(text);
        // System.out.println("You entered " + numberIn); // For debugging

        // Correct casting from JTextField to Cell, assuming the source really is a
        // Cell.
        // This will throw a ClassCastException if the source is not a Cell.
        Cell cell = (Cell) sourceTextField;
        if (numberIn == cell.number) {
          if (cell.status != CellStatus.CORRECT_GUESS) {
            --cellLeft;
          }
          cell.setEditable(false);
          cell.status = CellStatus.CORRECT_GUESS;
        } else {
          cell.status = CellStatus.WRONG_GUESS;
        }
        cell.paint(); // Update the visual representation based on status

        if (isSolved()) {
          StopwatchGUI.timer.stop();
          JOptionPane.showMessageDialog(
              null,
              "Congratulations!",
              "Puzzle Solved",
              JOptionPane.PLAIN_MESSAGE);
        }
      }
    }

    @Override
    public void keyReleased(KeyEvent e) {
      JTextField sourceCell = (JTextField) e.getSource();
      String text = sourceCell.getText().trim();
      if (text.matches("[1-9]")) {
        int numberInput = Integer.parseInt(text);
        System.out.println("You entered " + numberInput);
        Cell cell = (Cell) sourceCell; // Assuming the source is indeed a Cell
        if (numberInput == cell.number) {
          if (cell.status != CellStatus.CORRECT_GUESS) {
            --cellLeft;
          }
          cell.setEditable(false);
          cell.status = CellStatus.CORRECT_GUESS;
        } else {
          ++mistake;
          cell.status = CellStatus.WRONG_GUESS;
        }
        cell.paint(); // Update the visual representation based on status
        header.labelMistake.setText("Mistakes: " + mistake);
        header.labelCellLeft.setText("Cells left: " + cellLeft);
        System.out.printf("mistake=%d cellLeft=%d%n", mistake, cellLeft);
        if (isSolved()) {
          StopwatchGUI.timer.stop();
          JOptionPane.showMessageDialog(
              null,
              "Congratulations!",
              "Puzzle Solved",
              JOptionPane.PLAIN_MESSAGE);
        }
      }
    }
  }
}
