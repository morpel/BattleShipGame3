package Servlets;

import ServerLogic.Game;
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
import java.util.Map;

//Should run on startup and every 2 seconds
@WebServlet(name = "GameServlet")
public class GameServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ServerEngine serverEngine = ServletUtils.getServerEngine(getServletContext());
        String userName = SessionUtils.getUsername(req);
        resp.setContentType("text/html;charset=UTF-8");
        if (serverEngine.isPlayerLoggedIn(userName)){
            Map<String, Game> games = serverEngine.getGames();
            Gson gson = new Gson();
            String json = gson.toJson(games);
            req.setAttribute(Constants.GAMES_MAP, json);
            getServletContext().getRequestDispatcher("/Lobby/lobby.html").forward(req, resp);
        } else{
            //Player is not logged in
            resp.sendRedirect("index.html");
        }
    }
}
