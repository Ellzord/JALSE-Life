package life.entities;

import jalse.entities.Entity;
import jalse.entities.annotations.GetAttribute;
import jalse.entities.annotations.SetAttribute;

import java.awt.*;
import java.util.Set;

public interface Cell extends Entity {

  @GetAttribute
  Color getColor();

  @GetAttribute
  Point getPosition();

  @GetAttribute
  Integer getRow();

  @GetAttribute
  Integer getCol();

  @GetAttribute
  Class<? extends Cell> getNextGenStatus();

  @GetAttribute
  Set<Cell> getNeighbors();

  @SetAttribute
  void setColor(Color color);

  @SetAttribute
  void setPosition(Point position);

  @SetAttribute
  void setRow(Integer row);

  @SetAttribute
  void setCol(Integer col);

  @SetAttribute
  void setNextGenStatus(Class<? extends Cell> nextGenStatus);

  @SetAttribute
  void setNeighbors(Set<Cell> neighbors);

  interface DeadCell extends Cell {}
  interface LiveCell extends Cell {}
}
