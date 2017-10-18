package ServerLogic;

import Logic.Player;

public class User implements Comparable{
    private String name;
    private Game myCurrrentGame;

    public User(String i_Name) {
        name = i_Name;
    }

    @Override
    public int compareTo(Object o) {
        return name.compareTo(((User) o).name);
    }

    public String getName() {
        return name;
    }

    public void setCurrentGame(Game game){
        myCurrrentGame = game;
    }

    public Game getCurrentGame() {
        return myCurrrentGame;
    }

    public String[][] getShipsBoard() {
        return myCurrrentGame.getLogic().getPlayerByName(name).getMyShipBoard().getBoard();
    }

    public String[][] getAttackingBoard() {
        return myCurrrentGame.getLogic().getPlayerByName(name).getMyAttackingBoard().getBoard();
    }

    public Player.Stats getCurrentGameStats() {
        return myCurrrentGame.getLogic().getPlayerByName(name).getStats();
    }
}
