package ServerLogic;

import Logic.InvalidXMLInputsException;
import Logic.NotXMLFileException;
import jdk.nashorn.internal.parser.TokenStream;

import javax.xml.bind.JAXBException;
import javax.xml.bind.UnmarshalException;
import java.io.FileNotFoundException;
import java.nio.file.*;
import java.util.*;

public final class ServerEngine {
    private static ServerEngine ServerEngineInstance = null;
    private static Map<String, Game> m_Games;
    private static Set<User> m_Users;


    public static ServerEngine getInstance() {
        if (ServerEngineInstance == null) {
            ServerEngineInstance = new ServerEngine();
            m_Games = new HashMap<>();
            m_Users = new HashSet<>();
        }
        return ServerEngineInstance;
    }

    public boolean isPlayerLoggedIn(String enteredName) {
        User tmpUser = new User(enteredName);
        return m_Users.contains(tmpUser);
    }

    public void addUser(String i_Username) {
        User newUser = new User(i_Username);
        m_Users.add(newUser);
    }

    public void removeUser(String usernameToRemove) {
        User userToRemove = getUser(usernameToRemove);
        m_Users.remove(userToRemove);
    }

    //returns null if not exist
    public User getUser(String name){
        for (User user : m_Users) {
            if (user.getName().equals(name)) {
                return user;
            }
        }

        return null;
    }

    public boolean isGameNameExist(String eneteredGameName) {
        return m_Games.containsKey(eneteredGameName);
    }

    public String checkXML(String xmlPath, String gameName) {
        String res;
        Game theCurrentGame = m_Games.get(gameName);
        Path path = Paths.get(xmlPath);
        theCurrentGame.getLogic().setXMLPath(path);

        try {
            if (theCurrentGame.getLogic().initGameFromXML()) {
                if (theCurrentGame.getLogic().checkGameInputs()) {
                    theCurrentGame.setIsXMLLoaded(true);
                    res = null;
                } else {
                    res = "The inputs in the XML file are invalid, Load another XML file!";
                }
            } else {
                res = "Game initialization failed!";
            }
        } catch (NotXMLFileException|InvalidXMLInputsException ex) {
            res = ex.getMessage();
        } catch (FileSystemNotFoundException|FileNotFoundException e) {
            res = "File Not Found. Try Again!\n";
        } catch (UnmarshalException ex) {
           res = "The XML file doesn't contain the requested data";
        } catch (JAXBException ex) {
            res = "Path parsing didn't work";
        } catch (Exception ex) {
            res = "Something went wrong :(";
        }

        return res;
    }

    public void addNewGame(String i_GameName, String i_UserName) {
        User creator = getUser(i_UserName);
        Game newGame = new Game(i_GameName, creator);
        m_Games.put(i_GameName,newGame);
    }

    public Map<String, Game> getGames() {
        return m_Games;
    }

    public Set<User> getUsers() {
        return m_Users;
    }

    public List<String> getUsersList() {
        List<String> res = new ArrayList<>();

        for(User user : m_Users){
            res.add(user.getName());
        }

        return res;
    }

    public void assignUserToGame(String userName, String gameName) {
        User user = getUser(userName);
        Game game = m_Games.get(gameName);
        user.setCurrentGame(game);
        game.addPlayer(user);
    }

    public Game getGame(String gameName) {
        return m_Games.get(gameName);
    }
}
