package Servlets;

import ServerLogic.ServerEngine;
import utils.Constants;
import utils.ServletUtils;

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
        String gameName = (String)req.getAttribute(Constants.GAME_NAME);
        int row = Integer.valueOf(req.getParameter(Constants.ROW));
        int col = Integer.valueOf(req.getParameter(Constants.COLUMN));
        //should be done only if the player has mines left to put
        Point minePlace = new Point(col + 1, row + 1);//IS THIS RIGHT??
        PrintWriter out = resp.getWriter();
        String msg;
        if (serverEngine.checkMineLocation(gameName,minePlace)){
            serverEngine.addMineToPlayer(gameName,minePlace);
            msg = "Mine Was Sat Successfully";
        } else {
            msg = "You Can't Place A Mine In This Location";
        }

        out.print(msg);
        out.flush();

    }
}
