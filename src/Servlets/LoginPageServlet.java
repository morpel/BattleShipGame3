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

//should run when login page is loaded
@WebServlet(name = "LoginPageServlet")
public class LoginPageServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String usernameFromSession = SessionUtils.getUsername(request);
        ServerEngine serverEngine = ServletUtils.getServerEngine(getServletContext());

        if (usernameFromSession == null) {
            //user is not logged in yet
            String usernameFromParameter = request.getParameter(Constants.USERNAME);
            if (usernameFromParameter == null) {
                //no username in session and no username in parameter -
                //redirect back to the index page
                //this return an HTTP code back to the browser telling it to load
                response.sendRedirect("signUp.html");
            } else {
                //normalize the username value
                usernameFromParameter = usernameFromParameter.trim();
                if (serverEngine.isPlayerLoggedIn(usernameFromParameter)) {
                    String errorMessage = "Username " + usernameFromParameter + " already exists. Please enter a different username.";
                    // username already exists, forward the request back to index.jsp
                    // with a parameter that indicates that an error should be displayed
                    // the request dispatcher obtained from the servlet context is one that MUST get an absolute path (starting with'/')
                    // and is relative to the web app root
                    // see this link for more details:
                    // http://timjansen.github.io/jarfiller/guide/servlet25/requestdispatcher.xhtml
                    request.setAttribute(Constants.USER_NAME_ERROR, errorMessage);
                    getServletContext().getRequestDispatcher("/index.html").forward(request, response);
                } else {
                    //add the new user to the users list
                    serverEngine.addUser(usernameFromParameter);
                    //set the username in a session so it will be available on each request
                    //the true parameter means that if a session object does not exists yet
                    //create a new one
                    request.getSession(true).setAttribute(Constants.USERNAME, usernameFromParameter);

                    //redirect the request to the chat room - in order to actually change the URL
                    System.out.println("On login, request URI is: " + request.getRequestURI());
                    response.sendRedirect("/Lobby/lobby.html");
                }
            }
        } else {
            //user is already logged in
            response.sendRedirect("/Lobby/lobby.html");
        }
    }
}