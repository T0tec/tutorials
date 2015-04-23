package model;

import java.util.Collection;
import java.util.HashSet;

import config.Config;

/**
 * Encapsulate the game rules and the current game state. Allow clients to subscribe to model
 * changes. Notify subscribers when changes to the model occur.
 */
public class Model {

  // Keep track of whether a move animation is currently playing.
  // When a move animation is busy then no new moves are allowed.
  private boolean isGameOver = false;
  private boolean moveInProgress = false;

  private int[][] board = new int[Config.ROWS][Config.COLUMNS];

  /**
   * @param progress the new move-in-progress state.
   */
  public void setMoveInProgress(boolean progress) {
    moveInProgress = progress;
  }

  /**
   * @return whether a move animation is currently in progress.
   */
  public boolean getMoveInProgress() {
    return moveInProgress;
  }

  // The set of event handlers for this model.
  private final Collection<ModelEventListener> subscribers = new HashSet<>();

  // Add a new subscriber.
  public void subscribe(ModelEventListener listener) {
    subscribers.add(listener);
  }

  // Unsubscribe an event listener.
  public void unsubscribe(ModelEventListener listener) {
    subscribers.remove(listener);
  }

  // Notify all subscribers about an event.
  private void notifySubscribers(ModelEvent event) {
    for (ModelEventListener listener : subscribers) {
      listener.eventOccurred(event);
    }
  }

  /**
   * Check if the game has ended. Warn subscribers of this fact.
   */
  public void checkGameOver() {
    // TODO (step 3): implement this correctly.
    // NEED TO CHECK 4 IN ROW (horizontal, vertical and diagonal)
    // + how to know the color of the Player??
    if (isGameOver) {
      notifySubscribers(new ModelEvent(ModelEvent.ModelEventType.GameOver));
    }
  }

  /**
   * Check if a new disk can be inserted in the current column.
   *
   * @return true if and only if a move in this column is allowed.
   */
  public boolean playableMove(int column) {
    // No new moves are allowed when an animation is busy.
    if (getMoveInProgress()) {
      return false;
    }

    if (isGameOver) {
      return false;
    }

    return true;
  }

  /**
   * Compute the final destination row of a candidate move.
   *
   * @return the row.
   */
  public int moveDestination(int column) {
    int row = 0;
    while (row < Config.ROWS && board[row][column] == 0) {
      row++;
    }
    row--;
    return row;
  }

  /**
   * Commit the insertion of a new disk in a given column.
   */
  public void playMove(int column) {
    // Verify the following preconditions:
    // assert (isGameOver() == false);
    // assert (playableMove(column) == true);
    checkGameOver();

    if (!isGameOver) {
      if (playableMove(column)) {
        int row = moveDestination(column);
        // Change the model to reflect the new move.
        for (int j = 0; j < board[0].length; j++) {
          if (board[row][column] == 0) {
            board[row][column] = 1;
          }
        }
      }
    }


  }

  // TODO (step 3): Also check for termination conditions.

  // TODO (step 3): Notify subscribers about important model changes.
}
