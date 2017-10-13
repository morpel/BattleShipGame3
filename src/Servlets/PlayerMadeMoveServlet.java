package Servlets;

import Logic.Cell;
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

@WebServlet(name = "PlayerMadeMoveServlet")
public class PlayerMadeMoveServlet extends HttpServlet{

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ServerEngine serverEngine = ServletUtils.getServerEngine(getServletContext());
        String userNameFromSession = SessionUtils.getUsername(req);
        String gameName = (String)req.getAttribute(Constants.GAME_NAME);
        int row = (int)req.getAttribute(Constants.ROW);
        int col = (int)req.getAttribute(Constants.COLUMN);
        Cell.BoardObjects hitResult = serverEngine.checkPlayerMove(gameName, row, col);
        Gson gson = new Gson();
        String hitResJson = gson.toJson(hitResult);
        req.setAttribute(Constants.HIT_RESULT, hitResJson);
        if (serverEngine.isGameEnded(gameName)){
            req.setAttribute(Constants.IS_GAME_ENDED, true);
        }else{
            req.setAttribute(Constants.IS_GAME_ENDED, false);
        }
    }
}
