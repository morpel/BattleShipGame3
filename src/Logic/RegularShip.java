package Logic;

import java.awt.*;

public class RegularShip extends Ship {

    public RegularShip(String directionStr, Point pos, int length, int score) {
        super(directionStr,pos,length,score);
        setCompKind(Cell.BoardObjects.ship);
        onBoardParts = length;
    }

    public void setPosition(Point position) {
        if (direction.compareTo("ROW") == 0) {
            for (int i = 0; i < length; i++) {
                getCells().add(new Cell(new Point(position.x + i, position.y), "#"));
            }
        } else {
            for (int i = 0; i < length; i++) {
                getCells().add(new Cell(new Point(position.x, position.y + i), "#"));
            }
        }
    }
}
