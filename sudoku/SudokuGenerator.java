import java.util.stream.*;

public class SudokuGenerator {
    public static int[][] generatedSudoku = new int[9][9];
    public static boolean[][] generatedIsGiven = new boolean[9][9];
    private static int BOARD_SIZE = 9;
    private static int SUBSECTION_SIZE = 3;
    private static int NO_VALUE = 0;
    private static int BOARD_START_INDEX = 0;
    private static int MIN_VALUE = 1;
    private static int MAX_VALUE = 9;
    public static int numFalse;

    public SudokuGenerator() {
        generatedSudoku = new int[9][9];
        generatedIsGiven = new boolean[9][9];
        System.out.printf("GenerateAgain%n");
        int[][] temp = new int[3][3];
        int[] check = new int[10]; // true = 1, false = 0;
        for (int i = 0; i < 10; i++)
            check[i] = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                int rand_int = (int) (Math.random() * 9 + 1);
                while (check[rand_int] == 1) {
                    rand_int = (int) (Math.random() * 9 + 1);
                }
                check[rand_int] = 1;
                temp[i][j] = rand_int;
            }
        }
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                generatedSudoku[i][j] = temp[i][j];
            }
        }
        for (int i = 0; i < 10; i++)
            check[i] = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                int rand_int = (int) (Math.random() * 9 + 1);
                while (check[rand_int] == 1) {
                    rand_int = (int) (Math.random() * 9 + 1);
                }
                check[rand_int] = 1;
                temp[i][j] = rand_int;
            }
        }
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                generatedSudoku[3 + i][3 + j] = temp[i][j];
            }
        }
        for (int i = 0; i < 10; i++)
            check[i] = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                int rand_int = (int) (Math.random() * 9 + 1);
                while (check[rand_int] == 1) {
                    rand_int = (int) (Math.random() * 9 + 1);
                }
                check[rand_int] = 1;
                temp[i][j] = rand_int;
            }
        }
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                generatedSudoku[6 + i][6 + j] = temp[i][j];
            }
        }
        solve(generatedSudoku);

        String difficultyLevel = StartingPanel.difficultyLevel;
        if (difficultyLevel.equals("Easy")) {
            numFalse = 18;
            GameBoardPanel.cellLeft = 18;
        } else if (difficultyLevel.equals("Medium")) {
            numFalse = 36;
            GameBoardPanel.cellLeft = 36;
        } else {
            numFalse = 54;
            GameBoardPanel.cellLeft = 54;
        }
        System.out.printf("difficultyLevel = %s numFalse = %d\n", difficultyLevel, numFalse);
        boolean[] checkIs = new boolean[81];
        // easy 0 18 False 63 True
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                generatedIsGiven[i][j] = true;
            }
        }
        for (int i = 0; i < 81; i++)
            checkIs[i] = false;
        GameBoardPanel.cellLeft = numFalse;
        for (int i = 1; i <= numFalse; i++) {
            int rand_int = (int) (Math.random() * 81);
            while (checkIs[rand_int] == true) {
                rand_int = (int) (Math.random() * 81);
            }
            checkIs[rand_int] = true;
            // System.out.printf("rand_int=%d%n", rand_int);
            generatedIsGiven[rand_int / 9][rand_int % 9] = false;
        }
    }

    private boolean solve(int[][] board) {
        for (int row = BOARD_START_INDEX; row < BOARD_SIZE; row++) {
            for (int column = BOARD_START_INDEX; column < BOARD_SIZE; column++) {
                if (board[row][column] == NO_VALUE) {
                    for (int k = MIN_VALUE; k <= MAX_VALUE; k++) {
                        board[row][column] = k;
                        if (isValid(board, row, column) && solve(board)) {
                            return true;
                        }
                        board[row][column] = NO_VALUE;
                    }
                    return false;
                }
            }
        }
        return true;
    }

    private boolean isValid(int[][] board, int row, int column) {
        return (rowConstraint(board, row)
                && columnConstraint(board, column)
                && subsectionConstraint(board, row, column));
    }

    private boolean rowConstraint(int[][] board, int row) {
        boolean[] constraint = new boolean[BOARD_SIZE];
        return IntStream.range(BOARD_START_INDEX, BOARD_SIZE)
                .allMatch(column -> checkConstraint(board, row, constraint, column));
    }

    private boolean columnConstraint(int[][] board, int column) {
        boolean[] constraint = new boolean[BOARD_SIZE];
        return IntStream.range(BOARD_START_INDEX, BOARD_SIZE)
                .allMatch(row -> checkConstraint(board, row, constraint, column));
    }

    private boolean subsectionConstraint(int[][] board, int row, int column) {
        boolean[] constraint = new boolean[BOARD_SIZE];
        int subsectionRowStart = (row / SUBSECTION_SIZE) * SUBSECTION_SIZE;
        int subsectionRowEnd = subsectionRowStart + SUBSECTION_SIZE;

        int subsectionColumnStart = (column / SUBSECTION_SIZE) * SUBSECTION_SIZE;
        int subsectionColumnEnd = subsectionColumnStart + SUBSECTION_SIZE;

        for (int r = subsectionRowStart; r < subsectionRowEnd; r++) {
            for (int c = subsectionColumnStart; c < subsectionColumnEnd; c++) {
                if (!checkConstraint(board, r, constraint, c))
                    return false;
            }
        }
        return true;
    }

    boolean checkConstraint(
            int[][] board,
            int row,
            boolean[] constraint,
            int column) {
        if (board[row][column] != NO_VALUE) {
            if (!constraint[board[row][column] - 1]) {
                constraint[board[row][column] - 1] = true;
            } else {
                return false;
            }
        }
        return true;
    }

}
