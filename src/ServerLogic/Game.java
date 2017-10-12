package ServerLogic;

import Logic.GameLogic;

public class Game {
    private GameLogic gameLogic;
    private User creator;
    private String gameName;
    private User[] currentlyPlaying;
    private boolean isXMLLoaded = false;

    public Game(String i_GameName, User i_Creator) {
        gameLogic = new GameLogic();
        creator = i_Creator;
        gameName = i_GameName;
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

    public User[] getCurrentlyPlaying() {
        return currentlyPlaying;
    }
}
