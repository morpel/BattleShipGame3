package Servlets;

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
import java.io.PrintWriter;

@WebServlet(name = "BoardsInfoServlet")
public class BoardsInfoServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ServerEngine serverEngine = ServletUtils.getServerEngine(getServletContext());
        String userName = SessionUtils.getUsername(req);
        String gameName = SessionUtils.getGameName(req);

        String[][] shipsBoard = serverEngine.getUserShipsBoard(userName);
        String[][] attackingBoard = serverEngine.getUserAttackingBoard(userName);

        UserBoards userBoards = new UserBoards(attackingBoard,shipsBoard,serverEngine.getBoardSize(gameName));

        Gson gson = new Gson();
        String boardsJson = gson.toJson(userBoards);
        PrintWriter out = resp.getWriter();
        out.print(boardsJson);
        out.flush();

    }

    private class UserBoards {
        String[][] attacking;
        String[][] ships;
        int boardSize;

        public UserBoards(String[][] attacking, String[][] ships, int boardSize) {
            this.attacking = attacking;
            this.ships = ships;
            this.boardSize = boardSize;
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}