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

@WebServlet(name = "CheckForNewMovesServlet")
public class CheckForNewMovesServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ServerEngine serverEngine = ServletUtils.getServerEngine(getServletContext());
        String userName = SessionUtils.getUsername(req);
        PrintWriter out = resp.getWriter();
        if(serverEngine.isNewMoveMade(userName)){
            PlayerMadeMoveServlet.MoveRes moveRes = serverEngine.getNewMoveForUser(userName);
            serverEngine.setIsNewMoveMadeForUser(userName,false);
            Gson gson = new Gson();
            String moveResJson = gson.toJson(moveRes);
            out = resp.getWriter();
            out.print(moveResJson);
            out.flush();
        } else{
            String n = null;
            out.print(n);
            out.flush();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
