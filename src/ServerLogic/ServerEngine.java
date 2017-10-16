package ServerLogic;

import Logic.Cell;
import Logic.InvalidXMLInputsException;
import Logic.NotXMLFileException;

import javax.xml.bind.JAXBException;
import javax.xml.bind.UnmarshalException;
import java.awt.*;
import java.io.FileNotFoundException;
import java.nio.file.*;
import java.util.*;
import java.util.List;

public final class ServerEngine {
    private static ServerEngine ServerEngineInstance = null;
    private static Map<String, Game> m_Games;
    private static Set<User> m_Users;
    private static XMLCheckReporter xmlCheckReporter;

    public static ServerEngine getInstance() {
        if (ServerEngineInstance == null) {
            ServerEngineInstance = new ServerEngine();
            m_Games = new HashMap<>();
            m_Users = new HashSet<>();
            xmlCheckReporter = new XMLCheckReporter();
        }
        return ServerEngineInstance;
    }

    public static XMLCheckReporter getXmlCheckReporter() {
        return xmlCheckReporter;
    }

    public String getXMLValidityMsg() {
        return xmlCheckReporter.XMLValidityMsg == null ? null : xmlCheckReporter.XMLValidityMsg;
    }

    public void setXMLValidityMsg(String i_XMLValidityMsg) {
        if (i_XMLValidityMsg == null){
            xmlCheckReporter.XMLValidityMsg = null;
        } else{
            xmlCheckReporter.XMLValidityMsg = new String(i_XMLValidityMsg);
        }
    }


    public Boolean getIsXMLFileInQueue() {
        return xmlCheckReporter.isXMLFileInQueue;
    }

    public void setIsXMLFileInQueue(Boolean i_IsXMLFileInQueue) {
        xmlCheckReporter.isXMLFileInQueue = i_IsXMLFileInQueue;
    }

    public boolean isPlayerLoggedIn(String enteredName) {
        User user = getUser(enteredName);
        if (user == null){
            return false;
        }

        return true;
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

    public Cell.BoardObjects checkPlayerMove(String gameName, int row, int col) {
        //User user = getUser(userName);
        Game game = m_Games.get(gameName);
        game.getLogic().getCurrentPlayer().getStats().stopClock();
        Point attackedPoint = new Point(col,row);
        Cell.BoardObjects hitResult = game.getLogic().checkMove(attackedPoint);
        if (!hitResult.equals(Cell.BoardObjects.ship)) {
            game.getLogic().switchPlayers();
        }

        return hitResult;
    }

    public boolean isGameEnded(String gameName) {
        return (getGame(gameName).getLogic().checkIfGameFinished());
    }

    public boolean checkMineLocation(String gameName, Point minePlace) {
        Game game = getGame(gameName);
        return game.getLogic().getCurrentPlayer().checkMineLocation(minePlace);
    }

    public void addMineToPlayer(String gameName, Point minePlace) {
        Game game = getGame(gameName);
        game.getLogic().getCurrentPlayer().addMine(minePlace);
    }

    public boolean isUserCreatedTheGame(String gameName, String userName) {
        Game game = getGame(gameName);
        User user = getUser(userName);
        return game.getCreator().equals(user);
    }

    public boolean isGameWithoutPlayers(String gameName) {
        Game game = getGame(gameName);
        return (game.getCurrentlyPlaying().size() == 0);
    }

    public void deleteGame(String gameName) {
        m_Games.remove(gameName);
    }

    public void removeGame(String i_GameName) {
        m_Games.remove(i_GameName);
    }

    private static class XMLCheckReporter{
        protected static Boolean isXMLFileInQueue = false;
        protected static String XMLValidityMsg;
    }
}
