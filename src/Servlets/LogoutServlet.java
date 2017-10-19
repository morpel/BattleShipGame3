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

@WebServlet(name = "LogoutPageServlet")
public class LogoutServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String usernameFromSession = SessionUtils.getUsername(req);
        ServerEngine serverEngine = ServletUtils.getServerEngine(getServletContext());

        if (usernameFromSession != null) {
            System.out.println("Clearing session for " + usernameFromSession);
            String gameName = SessionUtils.getGameName(req);
            if (serverEngine.isUserPlaysGame(usernameFromSession,gameName)){
                serverEngine.detachUserFromGame(usernameFromSession,gameName);
            }
            serverEngine.removeUser(usernameFromSession);
            SessionUtils.clearSession(req);
            Gson gson = new Gson();
            Url GoToIndex = new Url(Constants.INDEX_URL);
            String GoToIndexJson = gson.toJson(GoToIndex);
            PrintWriter out = resp.getWriter();
            out.print(GoToIndexJson);
            out.flush();
        }
    }
}
