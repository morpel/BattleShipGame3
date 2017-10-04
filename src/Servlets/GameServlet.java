package Servlets;

import ServerLogic.ServerEngine;
import ServerLogic.User;
import com.google.gson.Gson;
import utils.ServletUtils;
import utils.SessionUtils;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//Should run on startup
public class GameServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User registerd;
        String json;
        Gson gson = new Gson();
        resp.setContentType("application/json");

        ServerEngine serverEngine = ServletUtils.getServerEngine(getServletContext());
        String userName = SessionUtils.getUsername(req);
        if (userName == null){
            resp.sendRedirect("index.html");
        } else{
            resp.sendRedirect("Lobby/lobby.html");
        }

    }
}
