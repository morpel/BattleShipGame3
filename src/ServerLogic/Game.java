package ServerLogic;

import Logic.GameLogic;

import java.util.ArrayList;
import java.util.List;

public class Game {
    private GameLogic gameLogic;
    private User creator;
    private String gameName;
    private List<User> currentlyPlaying;
    private boolean isXMLLoaded = false;
    private boolean isFull = false;

    public Game(String i_GameName, User i_Creator) {
        gameLogic = new GameLogic();
        creator = i_Creator;
        gameName = i_GameName;
        currentlyPlaying = new ArrayList<>();
    }

    public boolean getIsFull(){
        return isFull;
    }

    public GameLogic getLogic() {
        return gameLogic;
    }

    public String getName() {
        return gameName;
    }

    public User getCreator() {
        return creator;
    }

    public List<User> getCurrentlyPlaying() {
        return currentlyPlaying;
    }

    public void addPlayer(User user) {
        getLogic().getPlayers()[currentlyPlaying.size()].setName(user.getName());
        currentlyPlaying.add(user);
        isFull = currentlyPlaying.size() == 2;
    }

    public void setIsXMLLoaded(boolean i_IsXMLLoaded) {
        isXMLLoaded = i_IsXMLLoaded;
    }

    public void removePlayer(User user) {
        currentlyPlaying.remove(user);
        isFull = currentlyPlaying.size() == 2;
    }
}
