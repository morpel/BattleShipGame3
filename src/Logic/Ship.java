package Logic;

import java.awt.*;

public abstract class Ship extends BoardComponent {
    private int score;
    protected String direction;
    protected int length;
    protected int onBoardParts;
    private int partsHit;

    public Ship(String i_Direction, Point pos, int i_Length, int i_Score) {
        length = i_Length;
        score = i_Score;
        direction = i_Direction;
        partsHit = 0;
        setPosition(pos);
    }


    public int getOnBoardParts() {
        return onBoardParts;
    }

    public int getScore() {
        return score;
    }

    public void setPartsHit(int partsHit) {
        this.partsHit = partsHit;
    }

    public abstract void setPosition(Point position) ;

    public void setScore(int score) {
        this.score = score;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public boolean checkIfInMySpace(Point attackingPoint) {
        for (Cell compCell : getCells()) {
            if (compCell.getWhere().equals(attackingPoint) && compCell.isGoodHit() != true) {
                compCell.setGoodHit(true);
                partsHit++;
                if (partsHit == onBoardParts) {
                    setDestroyed(true);
                }
                return true;
            }
        }
        return false;
    }

    public void action(Player current, Player attacked, Point hitLocation) {
        int hitScore = 0;
        if (getDestroyed())
            hitScore = score;
        current.addNewMove(hitLocation, true, hitScore);
        attacked.addNewHitInMyBoard(hitLocation, true);
    }

}
