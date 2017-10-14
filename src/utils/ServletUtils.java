package utils;

import ServerLogic.ServerEngine;

import javax.servlet.ServletContext;

public class ServletUtils {

    private static final String SERVER_ENGIN = "ServerEngine";

    public static ServerEngine getServerEngine(ServletContext servletContext) {
        if (servletContext.getAttribute(SERVER_ENGIN) == null) {
            servletContext.setAttribute(SERVER_ENGIN, ServerEngine.getInstance());
        }

        return (ServerEngine) servletContext.getAttribute(SERVER_ENGIN);
    }
}
