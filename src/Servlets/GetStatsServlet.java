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

        GameStats gameStats = new GameStats(serverEngine,userName);
        Gson gson = new Gson();
        String gameStatsJson = gson.toJson(gameStats);
        PrintWriter out = resp.getWriter();
        out.print(gameStatsJson);
        out.flush();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }

    private class GameStats{
        private int myScore;
        private int opponentScore;
        private String gameType;
        private int myHits;
        private int myMisses;
        private int minesLeft;
        private String avgMoveTime;
        private int shipsLeftToSink;

        public GameStats(ServerEngine serverEngine, String userName) {
            User user = serverEngine.getUser(userName);
            myScore = user.getCurrentGameStats().getScore();
            opponentScore = serverEngine.getOpponentScore(userName);
            gameType = ""; //TODO:!!!!!!!!!!!!!!!!!!!!!!!!!
            myHits = user.getCurrentGameStats().getNumHits();
            myHits = user.getCurrentGameStats().getNumMiss();
            minesLeft = user.getCurrentGame().getLogic().getPlayerByName(userName).getMinesLeft();
            avgMoveTime = user.getCurrentGameStats().getAvgMovesTimeAsString();
            shipsLeftToSink = serverEngine.getOpponent(userName).getLShipsCount() + serverEngine.getOpponent(userName).getRegularShipsCount();
        }
    }
}
