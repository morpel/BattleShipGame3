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
import java.awt.*;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "PlayerSatMineServlet")
public class PlayerSatMineServlet extends HttpServlet{

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ServerEngine serverEngine = ServletUtils.getServerEngine(getServletContext());
        String gameName = SessionUtils.getGameName(req);
        String userName = SessionUtils.getUsername(req);
        String pointStr = req.getParameter(Constants.POINT);
        //should be done only if the player has mines left to put
        Point minePlace = serverEngine.getPointFromString(pointStr);
        PrintWriter out = resp.getWriter();
        String msg;
        serverEngine.addMineToPlayer(gameName,minePlace);
        msg = "Mine Was Sat Successfully";


        out.print(msg);
        out.flush();

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
