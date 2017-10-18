package Servlets;

import Logic.Cell;
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

@WebServlet(name = "PlayerMadeMoveServlet")
public class PlayerMadeMoveServlet extends HttpServlet{

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ServerEngine serverEngine = ServletUtils.getServerEngine(getServletContext());
        String gameName = req.getParameter(Constants.GAME_NAME);
        int row = Integer.valueOf(req.getParameter(Constants.ROW));
        int col = Integer.valueOf(req.getParameter(Constants.COLUMN));

        Cell.BoardObjects hitResult = serverEngine.checkPlayerMove(gameName, row, col);
        Gson gson = new Gson();
        PrintWriter out = resp.getWriter();
        MoveRes moveRes = new MoveRes(hitResult,serverEngine.isGameEnded(gameName));
        String moveResJson = gson.toJson(moveRes);
        out.print(moveResJson);
        out.flush();
    }

    private class MoveRes{
        private Cell.BoardObjects hitResult;
        private Boolean isGameEnded;

        public MoveRes(Cell.BoardObjects i_HitResult, Boolean i_IsGameEnded) {
            hitResult = i_HitResult;
            isGameEnded = i_IsGameEnded;
        }
    }
}
