package utils;

import ServerLogic.ServerEngine;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

public class ServletUtils {

    private static final String SERVER_ENGINE_ATTRIBUTE_NAME = "ServerEngine";
    private static final String CHAT_MANAGER_ATTRIBUTE_NAME = "chatManager";

    public static ServerEngine getServerEngine(ServletContext servletContext) {
        if (servletContext.getAttribute(SERVER_ENGINE_ATTRIBUTE_NAME) == null) {
            servletContext.setAttribute(SERVER_ENGINE_ATTRIBUTE_NAME, ServerEngine.getInstance());
        }

        return (ServerEngine) servletContext.getAttribute(SERVER_ENGINE_ATTRIBUTE_NAME);
    }
//
//    public static ChatManager getChatManager(ServletContext servletContext) {
//	if (servletContext.getAttribute(CHAT_MANAGER_ATTRIBUTE_NAME) == null) {
//	    servletContext.setAttribute(CHAT_MANAGER_ATTRIBUTE_NAME, new ChatManager());
//	}
//	return (ChatManager) servletContext.getAttribute(CHAT_MANAGER_ATTRIBUTE_NAME);
//    }
//
//    public static int getIntParameter(HttpServletRequest request, String name) {
//	String value = request.getParameter(name);
//	if (value != null) {
//	    try {
//		return Integer.parseInt(value);
//	    } catch (NumberFormatException numberFormatException) {
//	    }
//	}
//	return INT_PARAMETER_ERROR;
//    }
}
