package life.actions;

import jalse.actions.Action;
import jalse.actions.ActionContext;
import jalse.entities.Entity;
import life.CellProperties;
import life.entities.Cell;
import life.entities.Cell.LiveCell;
import life.entities.Cell.DeadCell;
import life.entities.Field;

public class Update implements Action<Entity> {

  @Override
  public void perform(ActionContext<Entity> context) throws InterruptedException {
    final Field field = context.getActor().asType(Field.class);
    field.streamCells().forEach(this::stageNextGenStatus);
    field.streamCells().forEach(this::advanceToNextGenStatus);
  }

  private void stageNextGenStatus(Cell cell) {
    final long livingNeighbors = cell.getNeighbors().stream().filter(c -> c.isMarkedAsType(LiveCell.class)).count();

    if (cell.isMarkedAsType(LiveCell.class)) {
      if (livingNeighbors < 2 || livingNeighbors > 3) {
        cell.setNextGenStatus(DeadCell.class);
      } else {
        cell.setNextGenStatus(LiveCell.class);
      }
    } else {
      if (livingNeighbors == 3) {
        cell.setNextGenStatus(LiveCell.class);
      } else {
        cell.setNextGenStatus(DeadCell.class);
      }
    }
  }

  private void advanceToNextGenStatus(Cell cell) {
    if (cell.getNextGenStatus().isAssignableFrom(LiveCell.class)) {
      cell.markAsType(LiveCell.class);
      cell.unmarkAsType(DeadCell.class);
      cell.setColor(CellProperties.Live.COLOR);
    } else {
      cell.markAsType(DeadCell.class);
      cell.unmarkAsType(LiveCell.class);
      cell.setColor(CellProperties.Dead.COLOR);
    }
  }
}
