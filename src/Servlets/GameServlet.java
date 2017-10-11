package Servlets;

import ServerLogic.ServerEngine;
import utils.ServletUtils;
import utils.SessionUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//Should run on startup and every 2 seconds
@WebServlet(name = "GameServlet")
public class GameServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ServerEngine serverEngine = ServletUtils.getServerEngine(getServletContext());
        String userName = SessionUtils.getUsername(req);
        resp.setContentType("text/html;charset=UTF-8");
        if (!serverEngine.isPlayerLoggedIn(userName)){
            //Player is not logged in
            resp.sendRedirect("index.html");
        }
    }
}
