package life.actions;

import jalse.actions.Action;
import jalse.actions.ActionContext;
import jalse.entities.Entity;
import life.CellProperties;
import life.entities.Cell;
import life.entities.Cell.LiveCell;
import life.entities.Cell.DeadCell;
import life.entities.Field;

/**
 * Update Action that advances the field from one generation to the next.
 *
 * The update is performed in two parts:
 * 1. Stage: Calculates the Cell's type based on current state of neighbors.
 * 2. Advance: Change Cell types to the staged type.
 */
public class Update implements Action<Entity> {

  @Override
  public void perform(ActionContext<Entity> context) throws InterruptedException {
    final Field field = context.getActor().asType(Field.class);
    field.streamCells().forEach(this::stageNextGenStatus);
    field.streamCells().forEach(this::advanceToNextGenStatus);
  }

  /** Count number of living neighbors and determine status for next generation. */
  private void stageNextGenStatus(Cell cell) {
    final long livingNeighbors = cell.getNeighbors().stream().filter(c -> c.isMarkedAsType(LiveCell.class)).count();

    if (cell.isMarkedAsType(LiveCell.class) && (livingNeighbors < 2 || livingNeighbors > 3)) {
      cell.setNextGenStatus(DeadCell.class);
    } else if (cell.isMarkedAsType(DeadCell.class) && livingNeighbors == 3) {
      cell.setNextGenStatus(LiveCell.class);
    }
  }

  /** Unmark Cell's current type and mark as new type determined during staging. */
  private void advanceToNextGenStatus(Cell cell) {
    if (cell.getNextGenStatus().isAssignableFrom(LiveCell.class)) {
      cell.unmarkAsType(DeadCell.class);
      cell.markAsType(LiveCell.class);
      cell.setColor(CellProperties.Live.COLOR);
    } else {
      cell.unmarkAsType(LiveCell.class);
      cell.markAsType(DeadCell.class);
      cell.setColor(CellProperties.Dead.COLOR);
    }
  }
}
