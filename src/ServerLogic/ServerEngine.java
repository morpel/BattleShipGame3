package ServerLogic;

import Logic.GameLogic;

import java.util.Map;
import java.util.TreeMap;

public final class ServerEngine {
    private static ServerEngine ServerEngineInstance = null;
    private static GameLogic[] m_GameLogics;
    private static Map<User, Integer> m_Users;



    public static ServerEngine getInstance() {
        if(ServerEngineInstance == null) {
            ServerEngineInstance = new ServerEngine();
            m_GameLogics = new GameLogic[0];
            m_Users = new TreeMap<>();
        }
        return ServerEngineInstance;
    }

    public boolean isPlayerLoggedIn(String enteredName) {
        User tmpUser = new User(enteredName);
        return m_Users.containsKey(tmpUser);
    }
}
