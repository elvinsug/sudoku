# Sudoku

A desktop **9×9 Sudoku** game built with **Java Swing**. It combines a short onboarding flow, procedurally generated boards, a stopwatch, hints, and a polished layout with optional backgrounds and sound hooks—aimed at being pleasant to play and straightforward to read if you are new to Java GUI code.

---

## What you need

| Requirement | Notes |
|---------------|--------|
| **Java Development Kit (JDK)** | Version **8 or newer** is enough. You need `javac` and `java` on your `PATH`. |

No build tool (Maven/Gradle) is required: the project is a small set of `.java` files plus assets.

---

## Run the game (quick start)

**Always run commands from the repository root** (the folder that contains `README.md` and the `sudoku/` directory). The program loads images and audio with paths such as `sudoku/bg.jpg`, so the **current working directory** must be that root folder.

### 1. Compile

```bash
cd /path/to/Sudoku
javac sudoku/*.java
```

This produces `.class` files next to the sources (inside `sudoku/`). Compiled bytecode is ignored by Git via `.gitignore` (`*.class`).

### 2. Launch

```bash
java -cp sudoku SudokuMain
```

You should see the **starting screen** first; after you begin, the main board and controls appear.

---

## How to play (for first-time users)

1. **Welcome screen** — Enter a display name, pick **Easy**, **Medium**, or **Hard**, then press **New Game**. The title updates to greet you; the start button stays disabled until both name and difficulty are set.
2. **Main screen** — Click a cell and type digits **1–9**. Correct entries lock the cell and turn it green; wrong entries are highlighted in red and count toward mistakes (shown in the header).
3. **Difficulty** — Roughly speaking, more empty cells mean a harder puzzle: the generator removes **18** (Easy), **36** (Medium), or **54** (Hard) clues after building a valid full grid.
4. **Hint** — Fills one empty or wrong cell with the correct value (when available). Hints and “cells left” are shown in the header. Solving the grid stops the timer and shows a congratulations dialog.
5. **Number strip (left)** — Buttons **1–9** highlight where that digit already appears in **given** or **correct** cells (a lightweight “pencil” aid). Click the same button again to clear highlights.
6. **Background** — The **🎑 / 🌞** control toggles between two in-game themes; the welcome screen uses its own artwork (`bg.jpg`).

---

## Project layout (what each part does)

| Path | Role |
|------|------|
| `sudoku/SudokuMain.java` | **Entry point.** Builds the `JFrame`, wires the welcome panel → main layout, difficulty buttons, number highlights, background toggle, and optional music (`playMusic()` exists but is commented out in `main`). |
| `sudoku/StartingPanel.java` | **Welcome UI:** name field, difficulty radios, **New Game**; stores static `playerName` and `difficultyLevel` for the rest of the app. |
| `sudoku/GameBoardPanel.java` | **The grid:** 9×9 `Cell` widgets, `Puzzle` instance, `newGame()` reset, keyboard input, win detection, ties into `header` and `StopwatchGUI`. |
| `sudoku/Cell.java` | **One square:** extends `JTextField`, draws thick lines for 3×3 blocks, colors by `CellStatus` (given, to guess, correct, wrong). |
| `sudoku/CellStatus.java` | Enum describing each cell’s logical state. |
| `sudoku/Puzzle.java` | Copies the generator’s static `numbers` and `isGiven` arrays into its own static fields used across the UI. |
| `sudoku/SudokuGenerator.java` | **Board construction:** seeds the three 3×3 boxes on the diagonal, **solves** to a complete grid, then clears `numFalse` cells at random for the chosen difficulty. |
| `sudoku/SudokuConstants.java` | Shared constants (`GRID_SIZE`, `SUBGRID_SIZE`). |
| `sudoku/header.java` | **Top bar:** stopwatch, mistake / cells-left / hints-used labels, **Hint** button logic. |
| `sudoku/StopwatchGUI.java` | Simple **HH:MM:SS** label driven by a `javax.swing.Timer`; reset when a new game starts. |
| `sudoku/bg.jpg`, `bg1.jpg`, `bg2.jpg` | Background images for the welcome screen and the two in-game themes. |
| `sudoku/music.wav` | Optional looped WAV (used only if you uncomment `playMusic()` in `SudokuMain.main`). |

Naming like `header` (lowercase class name) matches the existing codebase; in Java, types are usually `PascalCase`, but changing that would ripple through many files.

---

## Architecture in plain language

- **Model-ish data** lives in static arrays on `Puzzle` and `SudokuGenerator` (numbers + which cells are “givens”).  
- **Views** are Swing components: `Cell`, panels, labels, buttons.  
- **Control flow** is mostly anonymous listeners in `SudokuMain`, `GameBoardPanel`, and `header`.

If you are learning Swing, follow the thread rule already used here: the UI is constructed on the **Event Dispatch Thread** via `SwingUtilities.invokeLater` in `main`—that avoids subtle bugs from updating widgets off the GUI thread.

---

## Fonts and assets

- The UI requests **Poppins** in several places. If Poppins is not installed, the JVM substitutes a default font; the game still runs.
- Keep **paths** as they are (`sudoku/...`) unless you also change the code or run from a different working directory.

---

## Troubleshooting

| Symptom | Likely cause |
|---------|----------------|
| `FileNotFoundException` or missing backgrounds | Run `java` from the **repo root**, not from inside `sudoku/`. |
| `NoClassDefFoundError` / wrong class | Use `java -cp sudoku SudokuMain` after `javac sudoku/*.java`. |
| No music | `playMusic()` is intentionally commented out in `SudokuMain.main`; uncomment to experiment. |

---

## Extending the project (ideas)

- Persist best times per player name (simple file or embedded DB).  
- Replace random hint cell with a constraint-based hint (e.g. single candidate).  
- Promote `playerName` / difficulty into a small domain class instead of static fields on `StartingPanel`.  
- Add a `package` declaration and a proper `src/` tree if you outgrow the flat layout.

---

## License

If you publish this repository, add a `LICENSE` file that matches how you want others to use the code and assets (especially images and audio).
