package Logic;

import java.awt.Point;
import java.util.ArrayList;

public abstract class BoardComponent {
    private ArrayList<Cell> cells = new ArrayList<>();
    private Cell.BoardObjects compKind;
    private boolean destroyed = false;

    public Cell.BoardObjects getCompKind() {
        return compKind;
    }

    public boolean getDestroyed(){return destroyed;}

    public ArrayList<Cell> getCells() {
        return cells;
    }

    public void setDestroyed(boolean isDestroyed) {
        this.destroyed = isDestroyed;
    }

    protected void setCompKind(Cell.BoardObjects kind){
        compKind = kind;
    }

    public abstract void action(Player current, Player attacked, Point hitLocation);

    public abstract void setPosition(Point position);

    public abstract boolean checkIfInMySpace(Point attackingPoint);
}