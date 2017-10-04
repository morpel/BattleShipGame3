package Logic;

import java.awt.*;

public class LShip extends Ship {
    public LShip(String directionStr, Point pos, int length, int score) {
        super(directionStr,pos,length,score);
        setCompKind(Cell.BoardObjects.ship);
        onBoardParts = length * 2 - 1;
    }

    public void setPosition(Point position) {
        getCells().add(new Cell(new Point(position.x, position.y), "#" ));
        if (direction.indexOf("DOWN") >= 0) {
            for (int i = 1; i < length; i++) {
                getCells().add(new Cell(new Point(position.x, position.y - i), "#"));
            }
        } else {
            for (int i = 1; i < length; i++) {
                getCells().add(new Cell(new Point(position.x, position.y + i), "#"));
            }
        }
        if (direction.indexOf("LEFT") >= 0){
            for (int i = 1; i < length; i++) {
                getCells().add(new Cell(new Point(position.x - i, position.y), "#"));
            }
        } else {
            for (int i = 1; i < length; i++) {
                getCells().add(new Cell(new Point(position.x + i, position.y), "#"));
            }
        }
    }
}
