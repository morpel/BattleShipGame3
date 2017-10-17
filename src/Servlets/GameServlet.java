package Servlets;

import ServerLogic.Game;
import ServerLogic.ServerEngine;
import com.google.gson.Gson;
import utils.Constants;
import utils.ServletUtils;
import utils.SessionUtils;
import utils.Url;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

//Should run on startup and every 2 seconds
@WebServlet(name = "GameServlet")
public class GameServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ServerEngine serverEngine = ServletUtils.getServerEngine(getServletContext());
        String userName = SessionUtils.getUsername(req);
        PrintWriter out = resp.getWriter();
        Gson gson = new Gson();
        resp.setContentType("text/html;charset=UTF-8");
        Url GoToIndex = new Url(Constants.INDEX_URL);
        String GoToIndexJson = gson.toJson(GoToIndex);

        if (serverEngine.isPlayerLoggedIn(userName)) {
            List<SingleGameDetails> gamesDetails = getGameDetails(serverEngine.getGames());
            List<String> loggedInUsers = serverEngine.getUsersList();
            GamesAndUsersList gamesAndUsers = new GamesAndUsersList(gamesDetails, loggedInUsers);
            String gamesAndUsersJson = gson.toJson(gamesAndUsers);
            out.print(gamesAndUsersJson);
            out.flush();
        } else {
            //Player is not logged in
            resp.setStatus(400);
            out.print(GoToIndexJson);
            out.flush();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }

    private List<SingleGameDetails> getGameDetails(Map<String, Game> games) {
        List<SingleGameDetails> m_Details;
        m_Details = new ArrayList<>();

        for (Game game : games.values()) {
            SingleGameDetails sgd = new SingleGameDetails(game);
            m_Details.add(sgd);
        }

        return m_Details;
    }

    private class SingleGameDetails {
        String gameName;
        String creator;
        int boardSize;
        String gameStyle;
        int otherPlayerInGame;
        Boolean isGameFullyOccupied;

        public SingleGameDetails(Game game) {
            gameName = game.getName();
            creator = game.getCreator().getName();
            boardSize = game.getLogic().getBoardSize();
            gameStyle = ""; //TODO!!!!!!!!!!!!!
            otherPlayerInGame = game.getCurrentlyPlaying().size();
            isGameFullyOccupied = game.getIsFull();
        }
    }

    private class GamesAndUsersList {
        private List<SingleGameDetails> games;
        private List<String> users;

        public GamesAndUsersList(List<SingleGameDetails> gamesDetails, List<String> loggedInUsers) {
            games = gamesDetails;
            users = loggedInUsers;
        }
    }
}
