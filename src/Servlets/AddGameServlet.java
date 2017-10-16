package Servlets;

import ServerLogic.ServerEngine;
import com.google.gson.Gson;
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
import java.nio.file.Path;
import java.util.List;

@WebServlet(name = "AddGameServlet")
@MultipartConfig
public class AddGameServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        final String gameName = req.getParameter("gameName");
        final Part filePart = req.getPart("file");
        final String fileName = getFileName(filePart);
        String XMLPath = null;
        OutputStream out = null;
        InputStream fileContent = null;
        ServerEngine serverEngine = ServletUtils.getServerEngine(getServletContext());
        try {
            String prefix = getServletContext().getRealPath("/");
            String filePath = prefix.concat("Lobby\\" + fileName);
            File file = new File(filePath);
            XMLPath = file.getPath();
            out = new FileOutputStream(file);
            fileContent = filePart.getInputStream();

            int read;
            final byte[] bytes = new byte[1024];

            while ((read = fileContent.read(bytes)) != -1) {
                out.write(bytes, 0, read);
            }
        } catch (FileNotFoundException e) {
            System.out.println("file not found");
        } finally {
            if (out != null) {
                out.close();
            }
            if (fileContent != null) {
                fileContent.close();
            }
        }

        serverEngine.setIsXMLFileInQueue(true);
        String userNameFromSession = SessionUtils.getUsername(req);
        String enteredGameName = req.getParameter(Constants.GAME_NAME);
        if (serverEngine.isGameNameExist(enteredGameName)) {
            String errorMessage = "Game name " + enteredGameName + " already exists. Please enter a different game name.";
            serverEngine.setXMLValidityMsg(errorMessage);
        } else {
            serverEngine.addNewGame(enteredGameName, userNameFromSession);
            String gameInputsErr = serverEngine.checkXML(XMLPath, enteredGameName);
            serverEngine.setXMLValidityMsg(gameInputsErr);
            if (gameInputsErr != null) {
                serverEngine.removeGame(enteredGameName);
            }
        }
        resp.sendRedirect("Lobby/lobby.html");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }

    private String getFileName(final Part part) {
        final String partHeader = part.getHeader("content-disposition");
        for (String content : part.getHeader("content-disposition").split(";")) {
            if (content.trim().startsWith("filename")) {
                return content.substring(
                        content.indexOf('=') + 1).trim().replace("\"", "");
            }
        }
        return null;
    }
}
