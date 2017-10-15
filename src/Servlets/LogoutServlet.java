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
            serverEngine.removeUser(usernameFromSession);
            SessionUtils.clearSession(req);

            /*
            when sending redirect, tomcat has a shitty logic how to calculate the URL given, weather its relative or not
            you can read about it here:
            https://tomcat.apache.org/tomcat-5.5-doc/servletapi/javax/servlet/http/HttpServletResponse.html#sendRedirect(java.lang.String)
            the best way (IMO) is to fetch the context path dynamically and build the redirection from it and on
            (from some reason this call works as well; response.sendRedirect("../../../index.html"); not sure why. the request uri is '/pages/chatroom/chat/logout')
             */
            Gson gson = new Gson();
            Url GoToIndex = new Url(Constants.INDEX_URL);
            String GoToIndexJson = gson.toJson(GoToIndex);
            PrintWriter out = resp.getWriter();
            out.print(GoToIndexJson);
            out.flush();
        }


    }
}
