package Servlets;

import ServerLogic.ServerEngine;
import utils.Constants;
import utils.ServletUtils;
import utils.SessionUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "AddGameServlet")
public class AddGameServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ServerEngine serverEngine = ServletUtils.getServerEngine(getServletContext());
        String userNameFromSession = SessionUtils.getUsername(req);
        String enteredGameName = (String)req.getAttribute(Constants.GAME_NAME);
        if(serverEngine.isGameNameExist(enteredGameName)){
            String errorMessage = "Game name " + enteredGameName + " already exists. Please enter a different game name.";
            req.setAttribute(Constants.GAME_NAME_ERROR_MSG, errorMessage);
            getServletContext().getRequestDispatcher("/Lobby/lobby.html").forward(req, resp);
        } else{
            serverEngine.addNewGame(enteredGameName,userNameFromSession);
            String XMLPath = (String)req.getAttribute(Constants.XML_PATH);
            String gameInputsErr = serverEngine.checkXML(XMLPath, enteredGameName);
            if (gameInputsErr == null){//XML is valid
                String message = "Game \"" + enteredGameName + "\" has loaded successfully";
                req.setAttribute(Constants.XML_LOAD_SUCCESS, message);
                getServletContext().getRequestDispatcher("/Lobby/lobby.html").forward(req, resp);
            }
            else{
                req.setAttribute(Constants.XML_LOAD_ERROR, gameInputsErr);
                getServletContext().getRequestDispatcher("/Lobby/lobby.html").forward(req, resp);
            }
        }
    }
}
