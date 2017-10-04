package Logic;

import java.awt.*;
import java.util.ArrayList;

public class Mine extends BoardComponent {

    public Mine(Point location) {
        setPosition(location);
    }

    public void setPosition(Point position) {
        getCells().add(new Cell(position, "M"));
    }

    public int getScore() {
        return 0;
    }

    public boolean checkIfInMySpace(Point attackingPoint) {
        return attackingPoint.equals(getCells().get(0).getWhere());
    }

    public void action(Player current, Player attacked, Point hitLocation) {
        BoardComponent hitComp = current.checkIfHitMe(hitLocation);
        if (hitComp != null) {
            if (!attacked.checkIfCellWasAttackedBefore(hitLocation)){
                if (hitComp.getCompKind().equals(Cell.BoardObjects.mine)){
                    current.addNewHitInMyBoard(hitLocation, false);
                    attacked.addNewMove(hitLocation, false, 0);
                    current.addNewMove(hitLocation,false,0);
                } else {
                    hitComp.action(attacked, current, hitLocation);
                    current.addNewHitInMyBoard(hitLocation, true);
                    attacked.addNewMove(hitLocation, true, 0);
                }
            }
        } else {
            current.addNewHitInMyBoard(hitLocation, false);
            current.addNewMove(hitLocation,false,0);
            attacked.addNewMove(hitLocation, false, 0);
        }
    }
}
