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
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "DeleteGameServlet")
public class DeleteGameServlet extends HttpServlet{

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ServerEngine serverEngine = ServletUtils.getServerEngine(getServletContext());
        String gameName = req.getParameter(Constants.GAME_NAME);
        PrintWriter out = resp.getWriter();
        String msg;
        if (serverEngine.isGameWithoutPlayers(gameName)){
            serverEngine.deleteGame(gameName);
            msg = "Game Deleted Successfully";
        } else{
            msg = "This Game Is Busy, You Can't Delete It";
        }
        out.print(msg);
        out.flush();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
