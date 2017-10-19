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
import java.awt.*;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "PlayerMadeMoveServlet")
public class PlayerMadeMoveServlet extends HttpServlet{

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ServerEngine serverEngine = ServletUtils.getServerEngine(getServletContext());
        String gameName = SessionUtils.getGameName(req);
        String point = req.getParameter(Constants.POINT);
        Point attackingPoint = serverEngine.getPointFromString(point);
        Cell.BoardObjects hitResult = serverEngine.checkPlayerMove(gameName,attackingPoint);
        Gson gson = new Gson();
        PrintWriter out = resp.getWriter();
        MoveRes moveRes = new MoveRes(hitResult,serverEngine.isGameEnded(gameName), attackingPoint, hitResult.sign());
        String moveResJson = gson.toJson(moveRes);
        serverEngine.updateOtherPlayer(gameName,SessionUtils.getUsername(req),moveRes);
        out.print(moveResJson);
        out.flush();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }

    public class MoveRes{
        private Cell.BoardObjects hitResult;
        private Point hitPoint;
        private Boolean isGameEnded;
        private String sign;

        public MoveRes(Cell.BoardObjects i_HitResult, Boolean i_IsGameEnded, Point i_HitPoint, String i_Sign) {
            hitResult = i_HitResult;
            isGameEnded = i_IsGameEnded;
            hitPoint = i_HitPoint;
            sign = i_Sign;
        }
    }
}
