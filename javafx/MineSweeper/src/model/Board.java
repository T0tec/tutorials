package model;

import java.util.Random;

import view.Minesweeper;

public class Board {

  private Tile[][] board;
  private int mineCount;
  private int chosenMineCount;
  private Minesweeper view;

  public Board(int x, int y, int mineCount, Minesweeper view) {
    board = new Tile[x][y];
    this.mineCount = mineCount;
    chosenMineCount = mineCount;
    this.view = view;
    initializeBoard();

  }

  private void initializeBoard() {
    for (int i = 0; i < board.length; i++) {
      for (int j = 0; j < board[i].length; j++) {
        board[i][j] = new Tile(Status.EMPTY);
      }
    }
    Random r = new Random();
    for (int i = 1; i <= mineCount; i++) {
      int row = r.nextInt(Minesweeper.ROWS);
      int column = r.nextInt(Minesweeper.COLUMNS);
      while (board[row][column].getStatus() == Status.BOMB) {
        row = r.nextInt(Minesweeper.ROWS);
        column = r.nextInt(Minesweeper.COLUMNS);
      }
      board[row][column].setStatus(Status.BOMB);
      if (column < Minesweeper.COLUMNS - 1) {
        board[row][column + 1].incNeighbourCount();
      }
      if (row < Minesweeper.ROWS - 1) {
        board[row + 1][column].incNeighbourCount();
      }
      if (column > 0) {
        board[row][column - 1].incNeighbourCount();
      }
      if (row > 0) {
        board[row - 1][column].incNeighbourCount();
      }
      if (row < Minesweeper.ROWS - 1 && column < Minesweeper.COLUMNS - 1) {
        board[row + 1][column + 1].incNeighbourCount();
      }
      if (row < Minesweeper.ROWS - 1 && column > 0) {
        board[row + 1][column - 1].incNeighbourCount();
      }
      if (row > 0 && column < Minesweeper.COLUMNS - 1) {
        board[row - 1][column + 1].incNeighbourCount();
      }
      if (row > 0 && column > 0) {
        board[row - 1][column - 1].incNeighbourCount();
      }
    }
  }

  public Status getTileStatus(int row, int column) {
    return board[row][column].getStatus();
  }

  public void setTileStatus(int row, int column, Status status) {
    board[row][column].setStatus(status);
  }

  public boolean isTileMarked(int row, int column) {
    return board[row][column].isMarked();
  }

  public void setMark(int row, int column) {
    board[row][column].setMarked(true);
    chosenMineCount--;
    if (board[row][column].getStatus() == Status.BOMB) {
      mineCount--;
    }
  }

  public void clearMark(int row, int column) {
    board[row][column].setMarked(false);
    chosenMineCount++;
    if (board[row][column].getStatus() == Status.BOMB) {
      mineCount++;
    }
  }

  public int getNeighbourCount(int row, int column) {
    return board[row][column].getNeighbourCount();
  }

  public boolean isPuzzleComplete() {
    return mineCount == 0 && chosenMineCount == 0;
  }

  public void handleRightClick(int row, int column) {
    if (!isTileMarked(row, column)) {
      setMark(row, column);
    } else {
      clearMark(row, column);
    }
    view.updateButtons();
    if (isPuzzleComplete()) {
      gameOver();
    }

  }

  public void handleLeftClick(int row, int column) {
    if (getTileStatus(row, column) == Status.EMPTY) {
      if (getNeighbourCount(row, column) == 0) {
        removeEmptyNeighbourCells(row, column);
      } else {
        setTileStatus(row, column, Status.CLICKED);
      }
      view.updateButtons();
    } else if (getTileStatus(row, column) == Status.BOMB) {
      setTileStatus(row, column, Status.EXPLODED);
      gameOver();
    }
  }

  private void removeEmptyNeighbourCells(int row, int column) {
    if (row < Minesweeper.ROWS && column < Minesweeper.COLUMNS
        && getNeighbourCount(row, column) == 0 && getTileStatus(row, column) != Status.CLICKED) {
      if (!isTileMarked(row, column)) {
        setTileStatus(row, column, Status.CLICKED);
      }
      for (int i = row - 1; i <= row + 1; i++) {
        for (int j = column - 1; j <= column + 1; j++) {
          if ((i != row || j != column) && (i >= 0 && i < Minesweeper.ROWS) && (j >= 0 && j
                                                                                          < Minesweeper.COLUMNS)
              && !isTileMarked(row, column)) {
            removeEmptyNeighbourCells(i, j);
          }
        }
      }
    }
    if (!isTileMarked(row, column)) {
      setTileStatus(row, column, Status.CLICKED);
    }
  }

  private void gameOver() {
    for (int i = 0; i < board.length; i++) {
      for (int j = 0; j < board[i].length; j++) {
        if (board[i][j].getStatus() != Status.BOMB && board[i][j].getStatus() != Status.EXPLODED) {
          board[i][j].setStatus(Status.CLICKED);
        }
      }
    }
    view.updateButtons();
    view.gameOver();
  }
}
