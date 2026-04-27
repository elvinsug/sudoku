import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class StartingPanel extends JPanel {

  public static String playerName;
  public static String difficultyLevel = "dummy";
  public static boolean isStarted = false;

  private JTextField usernameField;
  private JButton startButton;
  private JRadioButton easy, medium, hard;
  private BufferedImage backgroundImage;
  private ButtonGroup difficultyGroup;
  private JLabel titleLabel;

  // Custom JPanel with transparent background
  class TransparentPanel extends JPanel {

    public TransparentPanel() {
      setOpaque(false);
    }
  }

  public StartingPanel() {
    try {
      backgroundImage = ImageIO.read(new File("sudoku/bg.jpg")); // Load the background image
    } catch (IOException e) {
      e.printStackTrace();
    }

    setLayout(new GridLayout(3, 3));
    setPreferredSize(new Dimension(1440, 900));

    JPanel startingContainer = new JPanel();
    startingContainer.setLayout(
        new BoxLayout(startingContainer, BoxLayout.Y_AXIS));
    startingContainer.setOpaque(false);

    titleLabel = new JLabel("Sudoku!");
    titleLabel.setFont(new Font("Poppins", Font.BOLD, 48));
    titleLabel.setAlignmentX(JComponent.CENTER_ALIGNMENT);

    usernameField = new JTextField();
    Dimension fieldDimension = new Dimension(500, 48);
    usernameField.setPreferredSize(fieldDimension); // Set preferred size
    usernameField.setMinimumSize(fieldDimension); // Set minimum size
    usernameField.setMaximumSize(fieldDimension); // Set maximum size
    usernameField.setFont(new Font("Poppins", Font.PLAIN, 20));

    // Add listeners
    usernameField
        .getDocument()
        .addDocumentListener(
            new DocumentListener() {
              public void changedUpdate(DocumentEvent e) {
                checkInput();
              }

              public void removeUpdate(DocumentEvent e) {
                checkInput();
              }

              public void insertUpdate(DocumentEvent e) {
                checkInput();
              }
            });

    // Document listener for username field to enable the start button conditionally
    usernameField
        .getDocument()
        .addDocumentListener(
            new DocumentListener() {
              public void changedUpdate(DocumentEvent e) {
                updateStartButtonState();
              }

              public void removeUpdate(DocumentEvent e) {
                updateStartButtonState();
              }

              public void insertUpdate(DocumentEvent e) {
                updateStartButtonState();
              }
            });

    // Initialize the radio buttons
    easy = new JRadioButton("Easy");
    medium = new JRadioButton("Medium");
    hard = new JRadioButton("Hard");

    difficultyLevel = "";
    JPanel diffcultyPanel = new JPanel();
    diffcultyPanel.setLayout(new GridLayout(1, 3));
    diffcultyPanel.setAlignmentX(JComponent.CENTER_ALIGNMENT);
    // Set the same font to all radio buttons
    Font radioButtonFont = new Font("Poppins", Font.PLAIN, 20);
    diffcultyPanel.setOpaque(false);

    easy.setFont(radioButtonFont);
    medium.setFont(radioButtonFont);
    hard.setFont(radioButtonFont);

    easy.setActionCommand("Easy");
    medium.setActionCommand("Medium");
    hard.setActionCommand("Hard");

    easy.setContentAreaFilled(false);
    easy.setOpaque(false);
    easy.setBackground(new Color(0, 0, 0, 0)); // Fully transparent

    medium.setContentAreaFilled(false);
    medium.setOpaque(false);
    medium.setBackground(new Color(0, 0, 0, 0)); // Fully transparent

    hard.setContentAreaFilled(false);
    hard.setOpaque(false);
    hard.setBackground(new Color(0, 0, 0, 0)); // Fully transparent

    JPanel easyContainer = new JPanel();
    easyContainer.setLayout(new BoxLayout(easyContainer, BoxLayout.Y_AXIS));
    easyContainer.add(easy);
    easy.setAlignmentX(JComponent.CENTER_ALIGNMENT);
    easyContainer.setOpaque(false);

    JPanel mediumContainer = new JPanel();
    mediumContainer.setLayout(new BoxLayout(mediumContainer, BoxLayout.Y_AXIS));
    mediumContainer.add(medium);
    medium.setAlignmentX(JComponent.CENTER_ALIGNMENT);
    mediumContainer.setOpaque(false);

    JPanel hardContainer = new JPanel();
    hardContainer.setLayout(new BoxLayout(hardContainer, BoxLayout.Y_AXIS));
    hardContainer.add(hard);
    hard.setAlignmentX(JComponent.CENTER_ALIGNMENT);
    hardContainer.setOpaque(false);

    diffcultyPanel.add(easyContainer);
    diffcultyPanel.add(mediumContainer);
    diffcultyPanel.add(hardContainer);

    // Action listeners for radio buttons
    ActionListener difficultyListener = e -> checkInput();
    easy.addActionListener(difficultyListener);
    medium.addActionListener(difficultyListener);
    hard.addActionListener(difficultyListener);

    // Create a button group and add the radio buttons to it
    difficultyGroup = new ButtonGroup();
    difficultyGroup.add(easy);
    difficultyGroup.add(medium);
    difficultyGroup.add(hard);

    // New game button
    startButton = new JButton("New Game");
    startButton.setFont(new Font("Poppins", Font.PLAIN, 20));
    startButton.setForeground(Color.WHITE);
    startButton.setMargin(new Insets(8, 28, 8, 28));
    startButton.setOpaque(true);
    startButton.setContentAreaFilled(true);
    startButton.setFocusPainted(false);
    startButton.setBorderPainted(false);
    startButton.setBackground(new Color(101, 163, 96)); // Set the default color
    startButton.setAlignmentX(JComponent.CENTER_ALIGNMENT);

    // Add a mouse listener to change the button color on hover
    startButton.addMouseListener(
        new MouseAdapter() {
          @Override
          public void mouseEntered(MouseEvent e) {
            if (startButton.isEnabled()) {
              startButton.setBackground(new Color(136, 184, 132)); // Change to hover color
            }
          }

          @Override
          public void mouseExited(MouseEvent e) {
            if (startButton.isEnabled()) {
              startButton.setBackground(new Color(101, 163, 96)); // Revert to default color
            }
          }
        });

    // Add an action listener to the button
    startButton.addActionListener(
        new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {
            printGameDetails();
          }
        });

    startingContainer.add(Box.createRigidArea(new Dimension(0, 24))); // gap
    startingContainer.add(titleLabel);
    startingContainer.add(Box.createRigidArea(new Dimension(0, 12))); // gap
    startingContainer.add(usernameField);
    startingContainer.add(Box.createRigidArea(new Dimension(0, 12))); // gap
    startingContainer.add(diffcultyPanel);
    startingContainer.add(startButton);
    startingContainer.add(Box.createRigidArea(new Dimension(0, 24))); // gap

    // adding the StartingContainer in the middle row and column
    for (int row = 0; row < 3; row++) {
      for (int col = 0; col < 3; col++) {
        if (col == 1 && row == 1) {
          add(startingContainer);
        } else {
          add(new TransparentPanel());
        }
      }
    }

    // Disable start button initially
    startButton.setEnabled(false);
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    if (backgroundImage != null) {
      g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
    }
  }

  // Method to enable or disable start button based on user input
  private void updateStartButtonState() {
    boolean isNameEntered = !usernameField.getText().trim().isEmpty();
    boolean isDifficultySelected = !difficultyLevel.isEmpty();
    startButton.setEnabled(isNameEntered && isDifficultySelected);
  }

  private void printGameDetails() {
    System.out.println("Player Name: " + playerName);
    System.out.println("Difficulty Level: " + difficultyLevel);
  }

  private void checkInput() {
    playerName = usernameField.getText().trim();
    difficultyLevel = (difficultyGroup.getSelection() != null)
        ? difficultyGroup.getSelection().getActionCommand()
        : "";
    startButton.setEnabled(!playerName.isEmpty() && !difficultyLevel.isEmpty());
    if (!playerName.isEmpty()) {
      titleLabel.setText("Let's Play, " + playerName + "!");
    } else {
      titleLabel.setText("Sudoku!");
    }
  }

  public JButton getStartButton() {
    return startButton;
  }

  // running the panel
  public static void main(String[] args) {
    SwingUtilities.invokeLater(
        new Runnable() {
          public void run() {
            JFrame frame = new JFrame("Sudoku Game");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(1440, 900);
            frame.setLocationRelativeTo(null); // Center the frame on the screen

            StartingPanel welcomeScreen = new StartingPanel();
            frame.add(welcomeScreen, BorderLayout.CENTER); // Add to center to fill the frame

            frame.setVisible(true);
          }
        });
  }
}
