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

@WebServlet(name = "DeleteGameServlet")
public class DeleteGameServlet extends HttpServlet{

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ServerEngine serverEngine = ServletUtils.getServerEngine(getServletContext());
        String userNameFromSession = SessionUtils.getUsername(req);
        String gameName = (String)req.getAttribute(Constants.GAME_NAME);
        if (serverEngine.isUserCreatedTheGame(gameName,userNameFromSession)){
            if (serverEngine.isGameWithoutPlayers(gameName)){
                serverEngine.deleteGame(gameName);
                req.setAttribute(Constants.IS_DELETE_GAME_SUCCESS, true);
            } else{
                req.setAttribute(Constants.IS_DELETE_GAME_SUCCESS, false);
                req.setAttribute(Constants.DELETE_GAME_ERROR, "You can't delete game with signed in users");
            }
        }
        else{
            req.setAttribute(Constants.IS_DELETE_GAME_SUCCESS, false);
            req.setAttribute(Constants.DELETE_GAME_ERROR, "You cant delete a game you didn't create");
        }
    }
}
