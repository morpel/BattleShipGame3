package Servlets;

import ServerLogic.Game;
import ServerLogic.ServerEngine;
import com.google.gson.Gson;
import utils.Constants;
import utils.ServletUtils;
import utils.SessionUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

//Should run on startup and every 2 seconds
@WebServlet(name = "GameServlet")
public class GameServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ServerEngine serverEngine = ServletUtils.getServerEngine(getServletContext());
        String userName = SessionUtils.getUsername(req);
        resp.setContentType("text/html;charset=UTF-8");
        if (serverEngine.isPlayerLoggedIn(userName)){
            //generate all games details
            List<SingleGameDetails> gamesDetails = getGameDetails(serverEngine.getGames());
            Gson gson = new Gson();
            String GamesJson = gson.toJson(gamesDetails);
            req.setAttribute(Constants.GAMES_DETAILS, GamesJson);

            //generate all players list
            List<String> loggedInUsers = serverEngine.getUsersList();
            String usersJson = gson.toJson(loggedInUsers);
            req.setAttribute(Constants.USERS_LIST, usersJson);
            getServletContext().getRequestDispatcher("/Lobby/lobby.html").forward(req, resp);
            //if above line doesnt work try do the same with req.getRe....
        } else {
            //Player is not logged in
            resp.sendRedirect("index.html");
        }
    }

    private List<SingleGameDetails> getGameDetails(Map<String,Game> games){
        List<SingleGameDetails> m_Details;
        m_Details = new ArrayList<>();

        for (Game game : games.values()){
            SingleGameDetails sgd = new SingleGameDetails(game);
            m_Details.add(sgd);
        }

        return m_Details;
    }

    private class SingleGameDetails{
        String gameName;
        String creator;
        int boardSize;
        String gameStyle;
        int isOtherPlayerInGame;
        Boolean isGameFullyOccupied;

        public SingleGameDetails(Game game) {
            gameName = game.getName();
            creator = game.getCreator().getName();
            boardSize = game.getLogic().getBoardSize();
            gameStyle = null; //TODO!!!!!!!!!!!!!
            isOtherPlayerInGame = game.getCurrentlyPlaying().size();
            isGameFullyOccupied = game.getIsFull();
        }
    }
}
