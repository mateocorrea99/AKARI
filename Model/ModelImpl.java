package Akari.model;

import java.util.ArrayList;

public class ModelImpl implements Model {
  private Puzzle puzzle;
  private PuzzleLibrary puzzleLibrary;
  private int index;
  private ArrayList<ModelObserver> observers;
  private boolean[][] lamps;

  public ModelImpl(PuzzleLibrary library) {
    if (library == null) {
      throw new IllegalArgumentException();
    }
    this.puzzleLibrary = library;
    this.puzzle = library.getPuzzle(0);
    this.index = 0;
    this.observers = new ArrayList<>();
    boolean[][] lamps = new boolean[puzzle.getHeight()][puzzle.getWidth()];
    for (int i = 0; i < puzzle.getHeight(); i++) {
      for (int j = 0; j < puzzle.getWidth(); j++) {
        lamps[i][j] = false;
      }
    }
    this.lamps = lamps;
  }

  @Override
  public void addLamp(int r, int c) {
    if (puzzle.getCellType(r, c) != CellType.CORRIDOR) {
      throw new IllegalArgumentException();
    }
    lamps[r][c] = true;
    update();
  }

  @Override
  public void removeLamp(int r, int c) {
    if (puzzle.getCellType(r, c) != CellType.CORRIDOR) {
      throw new IllegalArgumentException();
    }
    lamps[r][c] = false;
    update();
  }

  @Override
  public boolean isLit(int r, int c) {
    if (puzzle.getCellType(r, c) != CellType.CORRIDOR) {
      throw new IllegalArgumentException();
    }
    // CODE
    if (puzzle.getCellType(r, c) == CellType.CORRIDOR) {
      if (isLamp(r, c)) {
        return true;
      }

      int current_r_up = r - 1;
      int current_r_down = r + 1;
      int current_c_right = c + 1;
      int current_c_left = c - 1;

      // Direction up
      while (current_r_up >= 0) {
        if (puzzle.getCellType(current_r_up, c) != CellType.CORRIDOR) {
          break;
        }

        if (isLamp(current_r_up, c)) {
          return true;
        }

        current_r_up--;
      }

      // Direction down
      while (current_r_down < puzzle.getHeight()) {
        if (puzzle.getCellType(current_r_down, c) != CellType.CORRIDOR) {
          break;
        }

        if (isLamp(current_r_down, c)) {
          return true;
        }

        current_r_down++;
      }

      // Direction right
      while (current_c_right < puzzle.getWidth()) {
        if (puzzle.getCellType(r, current_c_right) != CellType.CORRIDOR) {
          break;
        }

        if (isLamp(r, current_c_right)) {
          return true;
        }
        current_c_right++;
      }

      // Direction Left
      while (current_c_left >= 0) {
        if (puzzle.getCellType(r, current_c_left) != CellType.CORRIDOR) {
          break;
        }

        if (isLamp(r, current_c_left)) {
          return true;
        }
        current_c_left--;
      }
    }
    return false;
  }

  @Override
  public boolean isLamp(int r, int c) {
    if (puzzle.getCellType(r, c) != CellType.CORRIDOR) {
      throw new IllegalArgumentException();
    }
    return lamps[r][c];
  }

  @Override
  public boolean isLampIllegal(int r, int c) {
    if (puzzle.getCellType(r, c) != CellType.CORRIDOR) {
      throw new IllegalArgumentException();
    }

    int current_r_up = r - 1;
    int current_r_down = r + 1;
    int current_c_right = c + 1;
    int current_c_left = c - 1;

    // Direction up
    while (current_r_up >= 0) {
      if (puzzle.getCellType(current_r_up, c) != CellType.CORRIDOR) {
        break;
      }

      if (lamps[current_r_up][c]) {
        return true;
      }
      current_r_up--;
    }

    // Direction down
    while (current_r_down < puzzle.getHeight()) {
      if (puzzle.getCellType(current_r_down, c) != CellType.CORRIDOR) {
        break;
      }

      if (lamps[current_r_down][c]) {
        return true;
      }
      current_r_down++;
    }

    // Direction right
    while (current_c_right < puzzle.getWidth()) {
      if (puzzle.getCellType(r, current_c_right) != CellType.CORRIDOR) {
        break;
      }

      if (lamps[r][current_c_right]) {
        return true;
      }
      current_c_right++;
    }

    // Direction Left
    while (current_c_left >= 0) {
      if (puzzle.getCellType(r, current_c_left) != CellType.CORRIDOR) {
        break;
      }

      if (lamps[r][current_c_left]) {
        return true;
      }
      current_c_left--;
    }

    return false;
  }

  @Override
  public Puzzle getActivePuzzle() {
    return this.puzzle;
  }

  @Override
  public int getActivePuzzleIndex() {
    return this.index;
  }

  @Override
  public void setActivePuzzleIndex(int index) {
    this.index = index;
    this.puzzle = puzzleLibrary.getPuzzle(index);
    this.newPuzzle();
    update();
  }

  @Override
  public int getPuzzleLibrarySize() {
    return puzzleLibrary.size();
  }

  @Override
  public void resetPuzzle() {
    for (int i = 0; i < puzzle.getHeight(); i++) {
      for (int j = 0; j < puzzle.getWidth(); j++) {
        lamps[i][j] = false;
      }
    }
    update();
  }

  @Override
  public boolean isSolved() {
    for (int i = 0; i < puzzle.getHeight(); i++) {
      for (int j = 0; j < puzzle.getWidth(); j++) {

        if (puzzle.getCellType(i, j) == CellType.CLUE) {
          if (!isClueSatisfied(i, j)) {
            return false;
          }
        } else if (puzzle.getCellType(i, j) == CellType.CORRIDOR) {
          if (lamps[i][j]) {
            if (isLampIllegal(i, j)) {
              return false;
            }
          } else {
            if (!isLit(i, j)) {
              return false;
            }
          }
        }
      }
    }
    return true;
  }

  @Override
  public boolean isClueSatisfied(int r, int c) {
    int lamps_around = 0;

    // Up
    if (r - 1 >= 0) {
      if (puzzle.getCellType(r - 1, c) == CellType.CORRIDOR) {
        if (lamps[r - 1][c]) {
          lamps_around++;
        }
      }
    }

    // Down
    if (r + 1 < puzzle.getHeight()) {
      if (puzzle.getCellType(r + 1, c) == CellType.CORRIDOR) {
        if (lamps[r + 1][c]) {
          lamps_around++;
        }
      }
    }

    // Right
    if (c + 1 < puzzle.getWidth()) {
      if (puzzle.getCellType(r, c + 1) == CellType.CORRIDOR) {
        if (lamps[r][c + 1]) {
          lamps_around++;
        }
      }
    }

    // Left
    if (c - 1 >= 0) {
      if (puzzle.getCellType(r, c - 1) == CellType.CORRIDOR) {
        if (lamps[r][c - 1]) {
          lamps_around++;
        }
      }
    }

    return puzzle.getClue(r, c) == lamps_around;
  }

  @Override
  public void addObserver(ModelObserver observer) {
    this.observers.add(observer);
  }

  @Override
  public void removeObserver(ModelObserver observer) {
    this.observers.remove(observer);
  }

  @Override
  public void update() {
    for (ModelObserver observer : observers) {
      observer.update(this);
    }
  }

  @Override
  public void newPuzzle() {
    int height = puzzle.getHeight();
    int width = puzzle.getWidth();
    boolean[][] new_lamps = new boolean[height][width];

    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        new_lamps[i][j] = false;
      }
    }

    this.lamps = new_lamps;
  }

  @Override
  public void clickCell(int r, int c) {
    // CODE
    lamps[r][c] = !lamps[r][c];
    update();
  }
}
