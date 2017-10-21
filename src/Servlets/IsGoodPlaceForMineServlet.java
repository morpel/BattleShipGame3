package Servlets;

import ServerLogic.ServerEngine;
import sun.security.jgss.HttpCaller;
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

@WebServlet(name = "IsGoodPlaceForMineServlet")
public class IsGoodPlaceForMineServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ServerEngine serverEngine = ServletUtils.getServerEngine(getServletContext());
        String userName = SessionUtils.getUsername(req);
        String row = req.getParameter(Constants.ROW);
        String col = req.getParameter(Constants.COLUMN);
        Point minePoint = new Point(Integer.valueOf(col),Integer.valueOf(row));
        PrintWriter out = resp.getWriter();
        String res;
        if(serverEngine.isGoodPlaceForMineToUser(userName,minePoint)){
            res = "yes";
        } else{
            res = "no";
        }

        out.print(res);
        out.flush();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
