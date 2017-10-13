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

@WebServlet(name = "PlayerSatMineServlet")
public class PlayerSatMineServlet extends HttpServlet{

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ServerEngine serverEngine = ServletUtils.getServerEngine(getServletContext());
        String gameName = (String)req.getAttribute(Constants.GAME_NAME);
        int row = (int)req.getAttribute(Constants.ROW);
        int col = (int)req.getAttribute(Constants.COLUMN);
        //should be done only if the player has mines left to put
        Point minePlace = new Point(col + 1, row + 1);
        if (serverEngine.checkMineLocation(gameName,minePlace)){
            serverEngine.addMineToPlayer(gameName,minePlace);
            req.setAttribute(Constants.IS_MINE_SET_SUCCESSFULLY, true);
        } else {
            req.setAttribute(Constants.IS_MINE_SET_SUCCESSFULLY, false);
        }

    }
}
