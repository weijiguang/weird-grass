/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.ili.grass.util;

import java.io.IOException;
import java.util.Map;
import java.util.Set;
import javax.faces.application.ConfigurableNavigationHandler;
import javax.faces.application.NavigationCase;
import javax.faces.application.NavigationHandler;
import javax.faces.context.FacesContext;
import org.omnifaces.util.Faces;
import org.slf4j.LoggerFactory;

/**
 * 自定义JSF导航管理器，使用redirect方式导航
 *
 * @author Weir
 */
//Register custom ConfigurableNavigationHandler as follows in faces-config.xml:
//
//<application>
//    <navigation-handler>com.example.RedirectNavigationHandler</navigation-handler>
//</application>  
public class RedirectNavigationHandler extends ConfigurableNavigationHandler {

    private static final org.slf4j.Logger log = LoggerFactory.getLogger(RedirectNavigationHandler.class);
    @SuppressWarnings("FieldMayBeFinal")
    private NavigationHandler navigationHandler;

    public RedirectNavigationHandler(NavigationHandler navigationHandler) {
        this.navigationHandler = navigationHandler;
    }

    @Override
    public NavigationCase getNavigationCase(FacesContext context, String fromAction, String outcome) {
        if (navigationHandler instanceof ConfigurableNavigationHandler) {
            return ((ConfigurableNavigationHandler) navigationHandler).getNavigationCase(context, fromAction, outcome);
        } else {
            return null;
        }
    }

    @Override
    public Map<String, Set<NavigationCase>> getNavigationCases() {
        if (navigationHandler instanceof ConfigurableNavigationHandler) {
            return ((ConfigurableNavigationHandler) navigationHandler).getNavigationCases();
        } else {
            return null;
        }
    }

    @Override
    public void handleNavigation(FacesContext context, String fromAction, String outcome) {
        NavigationCase navigationCase = getNavigationCase(context, fromAction, outcome);
        if (navigationCase != null && navigationCase.isRedirect() == false) {
            try {
                Faces.redirect(navigationCase.getToViewId(context), "");
            } catch (IOException ex) {
                log.error("redirect Exception." + ex.getMessage());
            }
        }
        navigationHandler.handleNavigation(context, fromAction, outcome);
    }
}