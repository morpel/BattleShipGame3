package ServerLogic;

import Logic.Cell;
import Logic.InvalidXMLInputsException;
import Logic.NotXMLFileException;
import Logic.Player;
import Servlets.PlayerMadeMoveServlet;

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
                    theCurrentGame.getLogic().initGameComponents();
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

    public Boolean assignUserToGame(String userName, String gameName) {
        User user = getUser(userName);
        Game game = m_Games.get(gameName);
        if(game == null || game.getIsFull()){
            return false;
        } else {
            user.setCurrentGame(game);
            game.addPlayer(user);
            return true;
        }
    }

    public Game getGame(String gameName) {
        return m_Games.get(gameName);
    }

    public Cell.BoardObjects checkPlayerMove(String gameName, Point attackedPoint) {
        Game game = m_Games.get(gameName);
        game.getLogic().getCurrentPlayer().getStats().stopClock();
        Cell.BoardObjects hitResult = game.getLogic().checkMove(attackedPoint);
        if (!hitResult.equals(Cell.BoardObjects.ship)) {
            game.getLogic().switchPlayers();
        }
        game.getLogic().getCurrentPlayer().getStats().startClock();

        return hitResult;
    }

    public boolean isGameEnded(String gameName) {
        return (getGame(gameName).getLogic().checkIfGameFinished());
    }

    public boolean checkMineLocation(String gameName, Point minePlace, String userName) {
        Game game = getGame(gameName);
        return game.getLogic().getPlayerByName(userName).checkMineLocation(minePlace);
    }

    public void addMineToPlayer(String gameName, Point minePlace) {
        Game game = getGame(gameName);
        game.getLogic().getCurrentPlayer().addMine(minePlace);
        game.getLogic().switchPlayers();
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

    public String[][] getUserShipsBoard(String userName) {
        User user = getUser(userName);
        return (user.getShipsBoard());
    }

    public String[][] getUserAttackingBoard(String userName) {
        User user = getUser(userName);
        return (user.getAttackingBoard());
    }

    public Player.Stats getUserStats(String userName) {
        User user = getUser(userName);
        return user.getCurrentGameStats();
    }

    public int getOpponentScore(String userName){
        return getOpponent(userName).getStats().getScore();
    }

    public Player getOpponent(String userName) {
        User user = getUser(userName);
        Player me = user.getCurrentGame().getLogic().getPlayerByName(userName);
        Player opponent;
        if(user.getCurrentGame().getLogic().getPlayers()[0].equals(me)){
            opponent = user.getCurrentGame().getLogic().getPlayers()[1];
        }
        else{
            opponent = user.getCurrentGame().getLogic().getPlayers()[0];
        }

        return opponent;
    }

    public void initGame(String gameName) {
        Game game = getGame(gameName);
        game.getLogic().initGameComponents();
    }

    public void detachUserFromGame(String userName, String gameName) {
        User user = getUser(userName);
        user.setCurrentGame(null);
        Game game = getGame(gameName);
        game.removePlayer(user);
    }

    public int getBoardSize(String gameName) {
        return getGame(gameName).getLogic().getBoardSize();
    }

    public Point getPointFromString(String str) {
        if(str!=null) {
            str = str.substring(1);
            String[] values = str.split(",");
            int row = Integer.valueOf(values[0]);
            int col = Integer.valueOf(values[1]);

            return new Point(row, col);
        } else{
            return null;
        }
    }

    public boolean isUserCurrentPlayer(String userName, String gameName) {
        Game game = getGame(gameName);
        return game.getLogic().getCurrentPlayer().getName().equals(userName);
    }

    public void updateOtherPlayer(String gameName, String username, PlayerMadeMoveServlet.MoveRes move) {
        User otherUser = getUser(getOpponent(username).getName());
        otherUser.newHitMade(move);

    }

    public boolean isNewMoveMade(String userName) {
        User user = getUser(userName);
        return user.getIsNewMoveMade();
    }

    public PlayerMadeMoveServlet.MoveRes getNewMoveForUser(String userName) {
        User user = getUser(userName);
        return user.getNewMove();
    }

    public void setIsNewMoveMadeForUser(String userName, boolean value) {
        User user = getUser(userName);
        user.setIsNewMoveMade(value);
    }

    public Boolean isGameFull(String gameName) {
        Game game = getGame(gameName);
        if (game != null) {
            return game.getIsFull();
        }
        return true;
    }

    public boolean isUserPlaysGame(String username, String gameName) {
        User user = getUser(username);
        return user.getCurrentGame()!=null;
    }

    public boolean isGoodPlaceForMineToUser(String userName, Point minePoint) {
        User user = getUser(userName);
        return user.getCurrentGame().getLogic().getPlayerByName(userName).checkMineLocation(minePoint);
    }

    public int getMinesLeftForUser(String userName) {
        User user = getUser(userName);
        return user.getCurrentGame().getLogic().getPlayerByName(userName).getMinesLeft();
    }

    private static class XMLCheckReporter{
        protected Boolean isXMLFileInQueue = false;
        protected String XMLValidityMsg;
    }
}
