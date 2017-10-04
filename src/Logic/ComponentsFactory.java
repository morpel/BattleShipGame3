package Logic;

import java.awt.*;

public class ComponentsFactory {
    public static BoardComponent createShips(String directionStr, Point pos, String currShipType, BattleShipGame.ShipTypes shipTypesInGame) {
        int index = 0;

        while (currShipType.compareTo(shipTypesInGame.shipType.get(index).id) != 0) {
            index++;
        }
        if (shipTypesInGame.shipType.get(index).category.equals("L_SHAPE"))
            return new LShip(directionStr, pos, shipTypesInGame.shipType.get(index).length, shipTypesInGame.shipType.get(index).score);
        else
            return new RegularShip(directionStr, pos, shipTypesInGame.shipType.get(index).length, shipTypesInGame.shipType.get(index).score);
    }

    public static BoardComponent createMine(Point location) {
        BoardComponent mine = new Mine(location);
        mine.setCompKind(Cell.BoardObjects.mine);
        return mine;
    }
}