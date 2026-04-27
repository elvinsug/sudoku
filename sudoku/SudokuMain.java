import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.sound.sampled.*;
import javax.swing.*;

/**
 * The main Sudoku program
 */
public class SudokuMain extends JFrame {

  class GapPanel extends JPanel {

    public GapPanel() {
      setOpaque(false);
      setPreferredSize(new Dimension(12, 1));
    }
  }

  class BackgroundPanel extends JPanel {

    private boolean isBg1 = true;
    private BufferedImage backgroundImage;

    public BackgroundPanel() {
      super(new BorderLayout()); // Use BorderLayout as the layout manager
      loadBackgroundImage("sudoku/bg1.jpg");
    }

    private void loadBackgroundImage(String imagePath) {
      try {
        backgroundImage = ImageIO.read(new File(imagePath)); // Load the background image
      } catch (IOException e) {
        e.printStackTrace();
      }
    }

    public void toggleBackground() {
      isBg1 = !isBg1; // Toggle the state
      if (isBg1) {
        loadBackgroundImage("sudoku/bg1.jpg");
        btnEasy.setBackground(COLOR_BG1_DEFAULT);
        btnMedium.setBackground(COLOR_BG1_DEFAULT);
        btnHard.setBackground(COLOR_BG1_DEFAULT);
      } else {
        loadBackgroundImage("sudoku/bg2.jpg");
        btnEasy.setBackground(COLOR_BG2_DEFAULT);
        btnMedium.setBackground(COLOR_BG2_DEFAULT);
        btnHard.setBackground(COLOR_BG2_DEFAULT);
      }
      repaint(); // Repaint the panel to update the background
    }

    public boolean isBg1() {
      return isBg1;
    }

    @Override
    protected void paintComponent(Graphics g) {
      super.paintComponent(g);
      if (backgroundImage != null) {
        g.drawImage(
            backgroundImage,
            0,
            0,
            this.getWidth(),
            this.getHeight(),
            this);
      }
    }
  }

  private static final long serialVersionUID = 1L; // to prevent serial warning

  // private variables
  public static GameBoardPanel board = new GameBoardPanel();

  JLabel newGameLabel = new JLabel("New Game");
  JButton btnEasy = new JButton("Easy");
  JButton btnMedium = new JButton("Medium");
  JButton btnHard = new JButton("Hard");

  JButton btn1 = new JButton("1");
  JButton btn2 = new JButton("2");
  JButton btn3 = new JButton("3");
  JButton btn4 = new JButton("4");
  JButton btn5 = new JButton("5");
  JButton btn6 = new JButton("6");
  JButton btn7 = new JButton("7");
  JButton btn8 = new JButton("8");
  JButton btn9 = new JButton("9");

  public int highlightNow = -1;

  public static header help = new header();
  public static Container cp;
  StartingPanel start = new StartingPanel();

  // public static WelcomeScreen help2 = new WelcomeScreen();
  JPanel btnNewGameContainer = new JPanel();
  JPanel leftButtonGroup = new JPanel();
  JPanel topPanel = new JPanel();
  JPanel leftPanel = new JPanel();
  JPanel bottomPanel = new JPanel();
  JPanel centerPanel = new JPanel();
  JPanel rightPanel = new JPanel();

  private static final Color COLOR_BG1_DEFAULT = new Color(101, 163, 96);
  private static final Color COLOR_BG1_HOVER = new Color(136, 184, 132);
  private static final Color COLOR_BG2_DEFAULT = new Color(28, 46, 74);
  private static final Color COLOR_BG2_HOVER = new Color(35, 57, 93);

  // Constructor
  public SudokuMain() {
    BackgroundPanel mainPanel = new BackgroundPanel();
    setContentPane(mainPanel);

    start.getStartButton().addActionListener(new startButtonListener());

    cp = getContentPane();
    cp.setLayout(new BorderLayout());
    cp.add(start, BorderLayout.CENTER);

    // Custom JPanel with transparent background
    class TransparentPanel extends JPanel {

      public TransparentPanel() {
        setOpaque(false);
      }
    }

    topPanel = new JPanel();
    topPanel.setOpaque(false);

    topPanel.setLayout(new GridLayout(3, 1));
    for (int row = 0; row < 3; row++) {
      for (int col = 0; col < 1; col++) {
        if (row == 1 && col == 0) {
          topPanel.add(help);
        } else {
          topPanel.add(new TransparentPanel());
        }
      }
    }

    btnNewGameContainer.setLayout(new FlowLayout());
    btnNewGameContainer.add(newGameLabel);
    btnNewGameContainer.add(new GapPanel());
    btnNewGameContainer.add(btnEasy);
    btnNewGameContainer.add(new GapPanel());
    btnNewGameContainer.add(btnMedium);
    btnNewGameContainer.add(new GapPanel());
    btnNewGameContainer.add(btnHard);
    btnNewGameContainer.setOpaque(false);

    bottomPanel = new JPanel();
    bottomPanel.setLayout(new GridLayout(3, 1));
    bottomPanel.setOpaque(false);
    for (int row = 0; row < 3; row++) {
      for (int col = 0; col < 1; col++) {
        if (row == 1 && col == 0) {
          bottomPanel.add(btnNewGameContainer);
        } else {
          bottomPanel.add(new TransparentPanel());
        }
      }
    }

    leftButtonGroup.setLayout(new BoxLayout(leftButtonGroup, BoxLayout.Y_AXIS));
    leftButtonGroup.add(btn1);
    leftButtonGroup.add(btn2);
    leftButtonGroup.add(btn3);
    leftButtonGroup.add(btn4);
    leftButtonGroup.add(btn5);
    leftButtonGroup.add(btn6);
    leftButtonGroup.add(btn7);
    leftButtonGroup.add(btn8);
    leftButtonGroup.add(btn9);
    leftButtonGroup.setOpaque(false);

    btn1.setMargin(new Insets(17, 18, 17, 19));
    btn2.setMargin(new Insets(17, 17, 17, 17));
    btn3.setMargin(new Insets(17, 17, 17, 17));
    btn4.setMargin(new Insets(17, 17, 17, 17));
    btn5.setMargin(new Insets(17, 17, 17, 17));
    btn6.setMargin(new Insets(17, 17, 17, 17));
    btn7.setMargin(new Insets(17, 17, 17, 17));
    btn8.setMargin(new Insets(17, 17, 17, 17));
    btn9.setMargin(new Insets(17, 17, 17, 17));

    btn1.setFont(new Font("Poppins", Font.PLAIN, 16));
    btn2.setFont(new Font("Poppins", Font.PLAIN, 16));
    btn3.setFont(new Font("Poppins", Font.PLAIN, 16));
    btn4.setFont(new Font("Poppins", Font.PLAIN, 16));
    btn5.setFont(new Font("Poppins", Font.PLAIN, 16));
    btn6.setFont(new Font("Poppins", Font.PLAIN, 16));
    btn7.setFont(new Font("Poppins", Font.PLAIN, 16));
    btn8.setFont(new Font("Poppins", Font.PLAIN, 16));
    btn9.setFont(new Font("Poppins", Font.PLAIN, 16));

    leftPanel = new JPanel();
    leftPanel.setLayout(new GridLayout(1, 3));
    leftPanel.setPreferredSize(new Dimension(320, 1000));
    leftPanel.setOpaque(false);

    for (int col = 0; col < 3; col++) {
      if (col == 1) {
        leftPanel.add(leftButtonGroup);
      } else {
        leftPanel.add(new TransparentPanel());
      }
    }

    btn1.addActionListener(
        new ActionListener() {
          public void actionPerformed(ActionEvent e) {
            if (highlightNow != 1) {
              for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                  if (Puzzle.numbers[i][j] == 1 &&
                      (GameBoardPanel.cells[i][j].status == CellStatus.GIVEN ||
                          GameBoardPanel.cells[i][j].status == CellStatus.CORRECT_GUESS)) {
                    GameBoardPanel.cells[i][j].paintHelp(i, j);
                  } else {
                    GameBoardPanel.cells[i][j].paintDefault(i, j);
                  }
                }
              }
              highlightNow = 1;
            } else {
              for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                  GameBoardPanel.cells[i][j].paintDefault(i, j);
                }
              }
              highlightNow = -1;
            }
          }
        });
    btn2.addActionListener(
        new ActionListener() {
          public void actionPerformed(ActionEvent e) {
            if (highlightNow != 2) {
              for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                  if (Puzzle.numbers[i][j] == 2 &&
                      (GameBoardPanel.cells[i][j].status == CellStatus.GIVEN ||
                          GameBoardPanel.cells[i][j].status == CellStatus.CORRECT_GUESS)) {
                    GameBoardPanel.cells[i][j].paintHelp(i, j);
                  } else {
                    GameBoardPanel.cells[i][j].paintDefault(i, j);
                  }
                }
              }
              highlightNow = 2;
            } else {
              for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                  GameBoardPanel.cells[i][j].paintDefault(i, j);
                }
              }
              highlightNow = -1;
            }
          }
        });
    btn3.addActionListener(
        new ActionListener() {
          public void actionPerformed(ActionEvent e) {
            if (highlightNow != 3) {
              for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                  if (Puzzle.numbers[i][j] == 3 &&
                      (GameBoardPanel.cells[i][j].status == CellStatus.GIVEN ||
                          GameBoardPanel.cells[i][j].status == CellStatus.CORRECT_GUESS)) {
                    GameBoardPanel.cells[i][j].paintHelp(i, j);
                  } else {
                    GameBoardPanel.cells[i][j].paintDefault(i, j);
                  }
                }
              }
              highlightNow = 3;
            } else {
              for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                  GameBoardPanel.cells[i][j].paintDefault(i, j);
                }
              }
              highlightNow = -1;
            }
          }
        });
    btn4.addActionListener(
        new ActionListener() {
          public void actionPerformed(ActionEvent e) {
            if (highlightNow != 4) {
              for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                  if (Puzzle.numbers[i][j] == 4 &&
                      (GameBoardPanel.cells[i][j].status == CellStatus.GIVEN ||
                          GameBoardPanel.cells[i][j].status == CellStatus.CORRECT_GUESS)) {
                    GameBoardPanel.cells[i][j].paintHelp(i, j);
                  } else {
                    GameBoardPanel.cells[i][j].paintDefault(i, j);
                  }
                }
              }
              highlightNow = 4;
            } else {
              for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                  GameBoardPanel.cells[i][j].paintDefault(i, j);
                }
              }
              highlightNow = -1;
            }
          }
        });
    btn5.addActionListener(
        new ActionListener() {
          public void actionPerformed(ActionEvent e) {
            if (highlightNow != 5) {
              for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                  if (Puzzle.numbers[i][j] == 5 &&
                      (GameBoardPanel.cells[i][j].status == CellStatus.GIVEN ||
                          GameBoardPanel.cells[i][j].status == CellStatus.CORRECT_GUESS)) {
                    GameBoardPanel.cells[i][j].paintHelp(i, j);
                  } else {
                    GameBoardPanel.cells[i][j].paintDefault(i, j);
                  }
                }
              }
              highlightNow = 5;
            } else {
              for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                  GameBoardPanel.cells[i][j].paintDefault(i, j);
                }
              }
              highlightNow = -1;
            }
          }
        });
    btn6.addActionListener(
        new ActionListener() {
          public void actionPerformed(ActionEvent e) {
            if (highlightNow != 6) {
              for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                  if (Puzzle.numbers[i][j] == 6 &&
                      (GameBoardPanel.cells[i][j].status == CellStatus.GIVEN ||
                          GameBoardPanel.cells[i][j].status == CellStatus.CORRECT_GUESS)) {
                    GameBoardPanel.cells[i][j].paintHelp(i, j);
                  } else {
                    GameBoardPanel.cells[i][j].paintDefault(i, j);
                  }
                }
              }
              highlightNow = 6;
            } else {
              for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                  GameBoardPanel.cells[i][j].paintDefault(i, j);
                }
              }
              highlightNow = -1;
            }
          }
        });
    btn7.addActionListener(
        new ActionListener() {
          public void actionPerformed(ActionEvent e) {
            if (highlightNow != 7) {
              for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                  if (Puzzle.numbers[i][j] == 7 &&
                      (GameBoardPanel.cells[i][j].status == CellStatus.GIVEN ||
                          GameBoardPanel.cells[i][j].status == CellStatus.CORRECT_GUESS)) {
                    GameBoardPanel.cells[i][j].paintHelp(i, j);
                  } else {
                    GameBoardPanel.cells[i][j].paintDefault(i, j);
                  }
                }
              }
              highlightNow = 7;
            } else {
              for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                  GameBoardPanel.cells[i][j].paintDefault(i, j);
                }
              }
              highlightNow = -1;
            }
          }
        });
    btn8.addActionListener(
        new ActionListener() {
          public void actionPerformed(ActionEvent e) {
            if (highlightNow != 8) {
              for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                  if (Puzzle.numbers[i][j] == 8 &&
                      (GameBoardPanel.cells[i][j].status == CellStatus.GIVEN ||
                          GameBoardPanel.cells[i][j].status == CellStatus.CORRECT_GUESS)) {
                    GameBoardPanel.cells[i][j].paintHelp(i, j);
                  } else {
                    GameBoardPanel.cells[i][j].paintDefault(i, j);
                  }
                }
              }
              highlightNow = 8;
            } else {
              for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                  GameBoardPanel.cells[i][j].paintDefault(i, j);
                }
              }
              highlightNow = -1;
            }
          }
        });
    btn9.addActionListener(
        new ActionListener() {
          public void actionPerformed(ActionEvent e) {
            if (highlightNow != 9) {
              for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                  if (Puzzle.numbers[i][j] == 9 &&
                      (GameBoardPanel.cells[i][j].status == CellStatus.GIVEN ||
                          GameBoardPanel.cells[i][j].status == CellStatus.CORRECT_GUESS)) {
                    GameBoardPanel.cells[i][j].paintHelp(i, j);
                  } else {
                    GameBoardPanel.cells[i][j].paintDefault(i, j);
                  }
                }
              }
              highlightNow = 9;
            } else {
              for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                  GameBoardPanel.cells[i][j].paintDefault(i, j);
                }
              }
              highlightNow = -1;
            }
          }
        });

    // New game button
    newGameLabel.setFont(new Font("Popping", Font.PLAIN, 20));

    btnEasy.setFont(new Font("Poppins", Font.PLAIN, 20));
    btnEasy.setForeground(Color.WHITE);
    btnEasy.setMargin(new Insets(8, 28, 8, 28));
    btnEasy.setOpaque(true);
    btnEasy.setContentAreaFilled(true);
    btnEasy.setFocusPainted(false);
    btnEasy.setBorderPainted(false);
    btnEasy.setBackground(new Color(101, 163, 96)); // Set the default color
    btnEasy.setPreferredSize(new Dimension(180, 40));

    btnMedium.setFont(new Font("Poppins", Font.PLAIN, 20));
    btnMedium.setForeground(Color.WHITE);
    btnMedium.setMargin(new Insets(8, 28, 8, 28));
    btnMedium.setOpaque(true);
    btnMedium.setContentAreaFilled(true);
    btnMedium.setFocusPainted(false);
    btnMedium.setBorderPainted(false);
    btnMedium.setBackground(new Color(101, 163, 96));
    btnMedium.setPreferredSize(new Dimension(180, 40));

    btnHard.setFont(new Font("Poppins", Font.PLAIN, 20));
    btnHard.setForeground(Color.WHITE);
    btnHard.setMargin(new Insets(8, 28, 8, 28));
    btnHard.setOpaque(true);
    btnHard.setContentAreaFilled(true);
    btnHard.setFocusPainted(false);
    btnHard.setBorderPainted(false);
    btnHard.setBackground(new Color(101, 163, 96));
    btnHard.setPreferredSize(new Dimension(180, 40));
    // Add a mouse listener to change the button color on hover
    btnEasy.addMouseListener(
        new MouseAdapter() {
          @Override
          public void mouseEntered(MouseEvent e) {
            if (mainPanel.isBg1()) {
              btnEasy.setBackground(COLOR_BG1_HOVER);
            } else {
              btnEasy.setBackground(COLOR_BG2_HOVER);
            }
          }

          @Override
          public void mouseExited(MouseEvent e) {
            if (mainPanel.isBg1()) {
              btnEasy.setBackground(COLOR_BG1_DEFAULT);
            } else {
              btnEasy.setBackground(COLOR_BG2_DEFAULT);
            }
          }
        });
    btnMedium.addMouseListener(
        new MouseAdapter() {
          @Override
          public void mouseEntered(MouseEvent e) {
            if (mainPanel.isBg1()) {
              btnMedium.setBackground(COLOR_BG1_HOVER);
            } else {
              btnMedium.setBackground(COLOR_BG2_HOVER);
            }
          }

          @Override
          public void mouseExited(MouseEvent e) {
            if (mainPanel.isBg1()) {
              btnMedium.setBackground(COLOR_BG1_DEFAULT);
            } else {
              btnMedium.setBackground(COLOR_BG2_DEFAULT);
            }
          }
        });
    btnHard.addMouseListener(
        new MouseAdapter() {
          @Override
          public void mouseEntered(MouseEvent e) {
            if (mainPanel.isBg1()) {
              btnHard.setBackground(COLOR_BG1_HOVER);
            } else {
              btnHard.setBackground(COLOR_BG2_HOVER);
            }
          }

          @Override
          public void mouseExited(MouseEvent e) {
            if (mainPanel.isBg1()) {
              btnHard.setBackground(COLOR_BG1_DEFAULT);
            } else {
              btnHard.setBackground(COLOR_BG2_DEFAULT);
            }
          }
        });

    btnEasy.addActionListener(
        new ActionListener() {
          public void actionPerformed(ActionEvent e) {
            StartingPanel.difficultyLevel = "Easy";
            header.hintButton.setEnabled(true);
            board.newGame();
          }
        });
    btnMedium.addActionListener(
        new ActionListener() {
          public void actionPerformed(ActionEvent e) {
            StartingPanel.difficultyLevel = "Medium";
            header.hintButton.setEnabled(true);
            board.newGame();
          }
        });
    btnHard.addActionListener(
        new ActionListener() {
          public void actionPerformed(ActionEvent e) {
            StartingPanel.difficultyLevel = "Hard";
            header.hintButton.setEnabled(true);
            board.newGame();
          }
        });

    rightPanel.setOpaque(false);
    rightPanel.setPreferredSize(new Dimension(320, 100));
    rightPanel.setLayout(new GridLayout(1, 3));

    JButton changeBGButton = new JButton("🎑");
    changeBGButton.setFont(new Font("Poppins", Font.PLAIN, 42));
    changeBGButton.setMargin(new Insets(10, 12, 8, 10));
    changeBGButton.addActionListener(
        new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {
            mainPanel.toggleBackground(); // Call toggleBackground on the main panel
            if (mainPanel.isBg1()) {
              changeBGButton.setText("🎑"); // Set button icon for bg1
            } else {
              changeBGButton.setText("🌞"); // Set button icon for bg2
            }
          }
        });

    JPanel changeBGButtonContainer = new JPanel();
    changeBGButtonContainer.setLayout(
        new BoxLayout(changeBGButtonContainer, BoxLayout.X_AXIS));
    changeBGButtonContainer.setOpaque(false);
    changeBGButtonContainer.add(changeBGButton);
    changeBGButton.setAlignmentY(Component.CENTER_ALIGNMENT);
    changeBGButton.setAlignmentX(Component.CENTER_ALIGNMENT);

    rightPanel.add(new TransparentPanel());
    rightPanel.add(changeBGButtonContainer);
    rightPanel.add(new TransparentPanel());

    centerPanel = new JPanel();
    centerPanel.setLayout(new BorderLayout());
    centerPanel.add(board, BorderLayout.CENTER);
    centerPanel.setOpaque(false);

    pack(); // Pack the UI components, instead of using setSize()
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // to handle window-closing
    setTitle("Sudoku");
    super.setVisible(true);
  }

  public static void playMusic() {
    try {
      File file = new File("sudoku/music.wav");
      if (!file.exists()) {
        System.err.println("Error: music.wav file not found.");
        return;
      }
      AudioInputStream audiostream = AudioSystem.getAudioInputStream(file);
      Clip clip = AudioSystem.getClip();
      clip.open(audiostream);

      clip.start();
      clip.loop(10);
    } catch (
        UnsupportedAudioFileException | IOException | LineUnavailableException e) {
      // Handle the exceptions here, such as logging or displaying an error message
      e.printStackTrace(); // Example: printing stack trace
    }
  }

  private class startButtonListener implements ActionListener {

    public void actionPerformed(ActionEvent evt) {
      cp.remove(start);
      cp.add(centerPanel, BorderLayout.CENTER);
      cp.add(topPanel, BorderLayout.NORTH);
      cp.add(bottomPanel, BorderLayout.SOUTH);
      cp.add(leftPanel, BorderLayout.WEST);
      cp.add(rightPanel, BorderLayout.EAST);
      board.newGame();
    }
  }

  /** The entry main() entry method */
  public static void main(String[] args) {
    // the constructor of "SudokuMain"
    // Run the GUI construction in the Event-Dispatching thread for thread safety
    // playMusic();
    SwingUtilities.invokeLater(
        new Runnable() {
          @Override
          public void run() {
            new SudokuMain(); // Let the constructor do the job
          }
        });
  }
}
