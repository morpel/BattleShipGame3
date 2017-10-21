package Servlets;

import ServerLogic.ServerEngine;
import ServerLogic.User;
import com.google.gson.Gson;
import utils.ServletUtils;
import utils.SessionUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "GetStatsServlet")
public class GetStatsServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ServerEngine serverEngine = ServletUtils.getServerEngine(getServletContext());
        String userName = SessionUtils.getUsername(req);
        String gameName = SessionUtils.getGameName(req);
        PrintWriter out = resp.getWriter();
        if(serverEngine.isGameFull(gameName)) {
            GameStats gameStats = new GameStats(serverEngine, userName, gameName);
            Gson gson = new Gson();
            String gameStatsJson = gson.toJson(gameStats);
            out.print(gameStatsJson);
            out.flush();
        } else{
            out.print("null");
            out.flush();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }

    private class GameStats{
        private String myName;
        private int myScore;
        private int opponentScore;
        private String gameType;
        private int myHits;
        private int myMisses;
        private int minesLeft;
        private String avgMoveTime;
        private int shipsLeftToSink;
        private Boolean isGameOver;
        private String winner;

        public GameStats(ServerEngine serverEngine, String userName, String gameName) {
            User user = serverEngine.getUser(userName);
            myName = user.getName();
            myScore = user.getCurrentGameStats().getScore();
            myHits = user.getCurrentGameStats().getNumHits();
            myMisses = user.getCurrentGameStats().getNumMiss();
            minesLeft = user.getCurrentGame().getLogic().getPlayerByName(userName).getMinesLeft();
            opponentScore = serverEngine.getOpponentScore(userName);
            gameType = user.getCurrentGame().getLogic().getGameType();
            avgMoveTime = user.getCurrentGameStats().getAvgMovesTimeAsString();
            shipsLeftToSink = serverEngine.getOpponent(userName).getLShipsCount() + serverEngine.getOpponent(userName).getRegularShipsCount();
            isGameOver = serverEngine.isGameEnded(gameName);
            winner = serverEngine.getWinner(gameName);
        }
    }
}
