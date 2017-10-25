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

//should run when login page is loaded
@WebServlet(name = "LoginPageServlet")
public class LoginPageServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String usernameFromSession = SessionUtils.getUsername(request);
        ServerEngine serverEngine = ServletUtils.getServerEngine(getServletContext());
        Gson responseJson = new Gson();
        Url GoToLobby = new Url(Constants.LOBBY_URL);
        String GoToLobbyJson = responseJson.toJson(GoToLobby);
        PrintWriter out = response.getWriter();
        System.out.println("From session: " + usernameFromSession);
        if (usernameFromSession == null) {
            //user is not logged in yet
            String usernameFromParameter = (String)request.getAttribute(Constants.USERNAME);
            if (usernameFromParameter == null || usernameFromParameter.equals("null")){
                usernameFromParameter = request.getParameter(Constants.USERNAME);
            }
            System.out.println("From parameter: " + usernameFromParameter);
            if (usernameFromParameter != null) {
                //normalize the username value
                usernameFromParameter = usernameFromParameter.trim();
                if (serverEngine.isPlayerLoggedIn(usernameFromParameter)) {
                    String errorMessage = "Username " + usernameFromParameter + " already exists. Please enter a different username.";
                    response.setStatus(400);
                    out.print(errorMessage);
                    out.flush();
                } else {
                    //add the new user to the users list
                    serverEngine.addUser(usernameFromParameter);
                    //set the username in a session so it will be available on each request
                    //the true parameter means that if a session object does not exists yet
                    //create a new one
                    request.getSession(true).setAttribute(Constants.USERNAME, usernameFromParameter);

                    //redirect the request to the chat room - in order to actually change the URL

                    //redirecting the client , by json (not by server-side)
                    out.print(GoToLobbyJson);
                    out.flush();
                }
            } else{
                String n = null;
                out.print(n);
                out.flush();

            }
        } else {
            //user is already logged in
            out.print(GoToLobbyJson);
            out.flush();
//            response.sendRedirect("/Lobby/lobby.html");
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}