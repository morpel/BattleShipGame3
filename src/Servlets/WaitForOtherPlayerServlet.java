package Servlets;

import ServerLogic.ServerEngine;
import com.google.gson.Gson;
import utils.ServletUtils;
import utils.SessionUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "WaitForOtherPlayerServlet")
public class WaitForOtherPlayerServlet extends HttpServlet{
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ServerEngine serverEngine = ServletUtils.getServerEngine(getServletContext());
        String gameName = SessionUtils.getGameName(req);

        String res;
        PrintWriter out = resp.getWriter();
        Boolean isGameContain2Players = serverEngine.isGameFull(gameName);
        if(isGameContain2Players) {
            serverEngine.startCurrentPlayerTime(gameName);
            res = "true";
        } else{
            res = "false";
        }

        out.print(res);
        out.flush();
    }
}
