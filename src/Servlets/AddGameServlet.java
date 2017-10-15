package Servlets;

import ServerLogic.ServerEngine;
import utils.Constants;
import utils.ServletUtils;
import utils.SessionUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.*;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;

@WebServlet(name = "AddGameServlet")
@MultipartConfig
public class AddGameServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        final String gameName = req.getParameter("gameName");
        final Part filePart = req.getPart("file");
        String XMLPath = null;

        OutputStream out = null;
        InputStream fileContent = null;

        try{
            File file = new File("C:\\Users\\morpel\\IdeaProjects\\BattleShipGame3\\src\\resources\\tmpXml.xml");
            XMLPath = file.getPath();
            out = new FileOutputStream(file);
            fileContent = filePart.getInputStream();

            int read =0;
            final byte[] bytes = new byte[1024];

            while((read = fileContent.read(bytes)) != -1){
                out.write(bytes,0,read);
            }
        } catch (FileNotFoundException e){
            System.out.println("file not found");
        }
        finally {
            if(out!=null){
                out.close();
            }
            if(fileContent!=null){
                fileContent.close();
            }
        }
        ServerEngine serverEngine = ServletUtils.getServerEngine(getServletContext());
        String userNameFromSession = SessionUtils.getUsername(req);
        String enteredGameName = req.getParameter(Constants.GAME_NAME);
        if(serverEngine.isGameNameExist(enteredGameName)){
            String errorMessage = "Game name " + enteredGameName + " already exists. Please enter a different game name.";
            req.setAttribute(Constants.GAME_NAME_ERROR_MSG, errorMessage);
            getServletContext().getRequestDispatcher("/Lobby/lobby.html").forward(req, resp);
        } else{
            serverEngine.addNewGame(enteredGameName,userNameFromSession);
            String gameInputsErr = serverEngine.checkXML(XMLPath, enteredGameName);
            if (gameInputsErr == null){//XML is valid
                String message = "Game \"" + enteredGameName + "\" has loaded successfully";
                req.setAttribute(Constants.XML_LOAD_SUCCESS, message);
                getServletContext().getRequestDispatcher("/Lobby/lobby.html").forward(req, resp);
            }
            else{
                req.setAttribute(Constants.XML_LOAD_ERROR, gameInputsErr);
                getServletContext().getRequestDispatcher("/Lobby/lobby.html").forward(req, resp);
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
