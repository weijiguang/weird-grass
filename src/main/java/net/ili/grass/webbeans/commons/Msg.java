/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.ili.grass.webbeans.commons;

import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

/**
 *
 * @author Weir
 */
public final class Msg {

    public static void error(Exception ex, String defaultMsg) {
        String msg = ex.getLocalizedMessage();
        if (msg != null && msg.length() > 0) {
            error(msg);
        } else {
            error(defaultMsg);
        }
    }

    public static void error(List<String> messages) {
        for (String message : messages) {
            error(message);
        }
    }

    public static void error(String msg) {
        FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, msg);
        FacesContext.getCurrentInstance().addMessage(null, facesMsg);
    }

    public static void info(String msg) {
        FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_INFO, msg, msg);
        FacesContext.getCurrentInstance().addMessage(null, facesMsg);
    }
}
