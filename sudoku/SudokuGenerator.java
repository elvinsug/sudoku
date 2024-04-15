import java.util.*;

public class SudokuGenerator {
    private static final int SIZE = 9;
    private static final int EMPTY = 0;

    public static void main(String[] args) {
        int[][] board = generateSudoku();
        printBoard(board);
    }

    private static int[][] generateSudoku() {
        int[][] board = new int[SIZE][SIZE];
        Random random = new Random();
        solveSudoku(board, random);
        return board;
    }

    private static boolean solveSudoku(int[][] board, Random random) {
        List<Integer> numbers = new ArrayList<>();
        for (int num = 1; num <= SIZE; num++) {
            numbers.add(num);
        }
        Collections.shuffle(numbers, random);
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                if (!fillCell(board, row, col, numbers)) {
                    return false;
                }
            }
        }
        return true;
    }

    private static boolean fillCell(int[][] board, int row, int col, List<Integer> numbers) {
        if (row == SIZE) {
            return true; // Board filled successfully
        }
        if (col == SIZE) {
            return fillCell(board, row + 1, 0, numbers); // Move to the next row
        }
        if (board[row][col] != EMPTY) {
            return fillCell(board, row, col + 1, numbers); // Cell already filled
        }
        for (int num : numbers) {
            if (isValid(board, row, col, num)) {
                board[row][col] = num;
                if (fillCell(board, row, col + 1, numbers)) {
                    return true; // Move to the next cell
                }
                board[row][col] = EMPTY; // Backtrack
            }
        }
        return false; // No valid number found for the cell
    }

    private static boolean isValid(int[][] board, int row, int col, int num) {
        return !usedInRow(board, row, num) &&
               !usedInCol(board, col, num) &&
               !usedInBox(board, row - row % 3, col - col % 3, num);
    }

    private static boolean usedInRow(int[][] board, int row, int num) {
        for (int col = 0; col < SIZE; col++) {
            if (board[row][col] == num) {
                return true;
            }
        }
        return false;
    }

    private static boolean usedInCol(int[][] board, int col, int num) {
        for (int row = 0; row < SIZE; row++) {
            if (board[row][col] == num) {
                return true;
            }
        }
        return false;
    }

    private static boolean usedInBox(int[][] board, int boxStartRow, int boxStartCol, int num) {
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                if (board[row + boxStartRow][col + boxStartCol] == num) {
                    return true;
                }
            }
        }
        return false;
    }

    private static void printBoard(int[][] board) {
        for (int row = 0; row < SIZE; row++) {
            System.out.println(Arrays.toString(board[row]));
        }
    }
}
