package Servlets;

import ServerLogic.ServerEngine;
import com.google.gson.Gson;
import utils.ServletUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name ="CheckForXmlErrorsServlet")
public class CheckForXmlErrorsServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ServerEngine serverEngine = ServletUtils.getServerEngine(getServletContext());
        PrintWriter printer = resp.getWriter();

        if(serverEngine.getIsXMLFileInQueue()){
            Gson gson = new Gson();
            serverEngine.setIsXMLFileInQueue(false);
            String ErrJson = gson.toJson(serverEngine.getXmlCheckReporter());
            serverEngine.setXMLValidityMsg(null);
            printer.print(ErrJson);
            printer.flush();
        }
        else{
            printer.print("null");
            printer.flush();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
