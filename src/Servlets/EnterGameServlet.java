package Servlets;

import ServerLogic.ServerEngine;
import com.google.gson.Gson;
import utils.Constants;
import utils.ServletUtils;
import utils.SessionUtils;
import utils.Url;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "EnterGameServlet")
public class EnterGameServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }

    //should be called when the request holds the selected game name
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ServerEngine serverEngine = ServletUtils.getServerEngine(getServletContext());
        String userName = SessionUtils.getUsername(req);
        String gameName = req.getParameter(Constants.GAME_NAME);
        Gson responseJson = new Gson();
        PrintWriter out = resp.getWriter();
        Boolean isPlayerEnteredGame = serverEngine.assignUserToGame(userName,gameName);
        if (isPlayerEnteredGame){
            req.getSession(true).setAttribute(Constants.GAME_NAME, gameName);
            Url GoToGame = new Url(Constants.GAME_URL);
            String GoToGameJson = responseJson.toJson(GoToGame);
            out.print(GoToGameJson);
            out.flush();
        } else{
            resp.setStatus(400);
            out.print("This game is not available any more");
            out.flush();
        }
    }
}
