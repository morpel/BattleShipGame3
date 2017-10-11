package ServerLogic;

import Logic.InvalidXMLInputsException;
import Logic.NotXMLFileException;
import javafx.application.Platform;

import javax.xml.bind.JAXBException;
import javax.xml.bind.UnmarshalException;
import java.io.FileNotFoundException;
import java.nio.file.FileSystemNotFoundException;
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
        String res = null;
        Game theCurrentGame = m_Games.get(gameName);
/*
        try {
            if (gameLogic.initGameFromXML()) {
                if (gameLogic.checkGameInputs()) {
                    res = true;
                    VImage.setVisible(true);
                    StartGameButton.setDisable(false);
                } else {
                    errLabel.setText("The inputs in the XML file are invalid, Load another XML file!");
                }
            } else {
                errLabel.setText("Game initialization failed!");
            }
        } catch (NotXMLFileException ex) {
            Platform.runLater(() -> {
                errLabel.setText(ex.getMessage());
            });
        } catch (FileSystemNotFoundException e) {
            Platform.runLater(() -> {
                errLabel.setText("File Not Found. Try Again!\n");
            });
        } catch (FileNotFoundException ex) {
            Platform.runLater(() -> {
                errLabel.setText("File Not Found. Try Again!\n");
            });
        } catch (InvalidXMLInputsException ex) {
            Platform.runLater(() -> {
                errLabel.setText(ex.getMessage());
            });
        } catch (UnmarshalException ex) {
            Platform.runLater(() -> {
                errLabel.setText("The XML file doesn't contain the requested data");
            });
        } catch (JAXBException ex) {
            Platform.runLater(() -> {
                errLabel.setText("Path parsing didn't work");
            });
        } catch (Exception ex) {
            Platform.runLater(() -> {
                errLabel.setText("Something went wrong :(");
            });
        }
*/

        return res;
    }

    public void addNewGame(String i_GameName, String i_UserName) {
        User creator = getUser(i_UserName);
        Game newGame = new Game(i_GameName, creator);
        m_Games.put(i_GameName,newGame);
    }
}
