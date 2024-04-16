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
        solveSudoku(board);
        return board;
    }

    private static boolean solveSudoku(int[][] board) {
        List<Integer> numbers = new ArrayList<>();
        for (int num = 1; num <= SIZE; num++) {
            numbers.add(num);
        }
        Collections.shuffle(numbers);
        return solveSudokuHelper(board, 0, 0, numbers);
    }

    private static boolean solveSudokuHelper(int[][] board, int row, int col, List<Integer> numbers) {
        if (row == SIZE) {
            return true; // Sudoku solved
        }

        int nextRow = col == SIZE - 1 ? row + 1 : row;
        int nextCol = col == SIZE - 1 ? 0 : col + 1;

        for (int num : numbers) {
            if (isValid(board, row, col, num)) {
                board[row][col] = num;
                if (solveSudokuHelper(board, nextRow, nextCol, numbers)) {
                    return true;
                }
                board[row][col] = EMPTY; // Backtrack
            }
        }

        return false;
    }

    private static boolean isValid(int[][] board, int row, int col, int num) {
        for (int i = 0; i < SIZE; i++) {
            if (board[row][i] == num || board[i][col] == num || board[row - row % 3 + i / 3][col - col % 3 + i % 3] == num) {
                return false;
            }
        }
        return true;
    }

    private static void printBoard(int[][] board) {
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                System.out.print(board[row][col] + " ");
            }
            System.out.println();
        }
    }
}
