package life.entities;

import java.awt.Color;
import java.awt.Point;
import java.util.Set;

import jalse.entities.Entity;
import jalse.entities.annotations.GetAttribute;
import jalse.entities.annotations.SetAttribute;

public interface Cell extends Entity {

    interface DeadCell extends Cell {}

    interface LiveCell extends Cell {}

    @GetAttribute
    int getCol();

    @GetAttribute
    Color getColour();

    @GetAttribute
    Set<Cell> getNeighbors();

    @GetAttribute
    Class<? extends Cell> getNextGenStatus();

    @GetAttribute
    Point getPosition();

    @GetAttribute
    int getRow();

    @SetAttribute
    void setCol(int col);

    @SetAttribute
    void setColour(Color colour);

    @SetAttribute
    void setNeighbors(Set<Cell> neighbors);

    @SetAttribute
    void setNextGenStatus(Class<? extends Cell> nextGenStatus);

    @SetAttribute
    void setPosition(Point position);

    @SetAttribute
    void setRow(int row);
}
