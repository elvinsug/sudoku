import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class header extends JPanel {

  public static int hintUsed = 0;

  public static JLabel labelMistake = new JLabel(
    "Mistakes: " + GameBoardPanel.mistake
  );
  public static JLabel labelCellLeft = new JLabel(
    "Cells Left: " + GameBoardPanel.cellLeft
  );
  public static JLabel labelHintUsed = new JLabel("Hints Used: " + hintUsed);
  public StopwatchGUI stopwatch = new StopwatchGUI();
  public static JButton hintButton = new JButton("Hint"); // Add text to the button

  // Custom JPanel for gap in header
  class GapPanel extends JPanel {

    public GapPanel() {
      setOpaque(false);
      setPreferredSize(new Dimension(24,1));
    }
  }

  public header() {
    hintButton.setFont(new Font("Poppins", Font.PLAIN, 20));
    hintButton.setForeground(Color.WHITE);
    hintButton.setMargin(new Insets(4, 24, 4, 24));
    hintButton.setOpaque(true);
    hintButton.setContentAreaFilled(true);
    hintButton.setFocusPainted(false);
    hintButton.setBorderPainted(false);
    hintButton.setBackground(new Color(252,166,4)); // Set the default color

    labelCellLeft.setFont(new Font("Poppins", Font.PLAIN, 20));
    labelMistake.setFont(new Font("Poppins", Font.PLAIN, 20));
    labelHintUsed.setFont(new Font("Poppins", Font.PLAIN, 20));

    setLayout(new FlowLayout());
    add(stopwatch);
    add(new GapPanel());
    add(labelMistake);
    add(labelCellLeft);
    add(labelHintUsed);
    add(new GapPanel());
    add(hintButton);
    setOpaque(false);

    hintButton.addActionListener(
      new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
          int rand_int = (int) (Math.random() * 81);
          while (
            Puzzle.isGiven[rand_int / 9][rand_int % 9] == true ||
            (
              GameBoardPanel.cells[rand_int / 9][rand_int % 9].status !=
              CellStatus.WRONG_GUESS &&
              GameBoardPanel.cells[rand_int / 9][rand_int % 9].status !=
              CellStatus.TO_GUESS
            )
          ) {
            rand_int = (int) (Math.random() * 81);
          }
          hintUsed++;
          labelHintUsed.setText("Hints used: " + hintUsed);
          GameBoardPanel.cells[rand_int / 9][rand_int % 9].status =
            CellStatus.CORRECT_GUESS;
          GameBoardPanel.cellLeft--;
          labelCellLeft.setText("Cells Left: " + GameBoardPanel.cellLeft);
          GameBoardPanel.cells[rand_int / 9][rand_int % 9].number =
            Puzzle.numbers[rand_int / 9][rand_int % 9];
          GameBoardPanel.cells[rand_int / 9][rand_int % 9].paint();
          if (GameBoardPanel.cellLeft == 0) {
            hintButton.setEnabled(false);
            StopwatchGUI.timer.stop();
            JOptionPane.showMessageDialog(
              null,
              "Congratulations!",
              "Puzzle Solved",
              JOptionPane.PLAIN_MESSAGE
            );
          }
        }
      }
    );
  }
}
