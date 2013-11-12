/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.ili.base.util;

import java.util.Map;
import java.util.Set;
import javax.faces.application.ConfigurableNavigationHandler;
import javax.faces.application.NavigationCase;
import javax.faces.application.NavigationHandler;
import javax.faces.context.FacesContext;
import org.apache.commons.lang3.StringUtils;

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

    @SuppressWarnings("FieldMayBeFinal")
    private NavigationHandler parent;

    public RedirectNavigationHandler(NavigationHandler parent) {
        this.parent = parent;
    }

    @Override
    public NavigationCase getNavigationCase(FacesContext context, String fromAction, String outcome) {
        if (parent instanceof ConfigurableNavigationHandler) {
            return ((ConfigurableNavigationHandler) parent).getNavigationCase(context, fromAction, outcome);
        } else {
            return null;
        }
    }

    @Override
    public Map<String, Set<NavigationCase>> getNavigationCases() {
        if (parent instanceof ConfigurableNavigationHandler) {
            return ((ConfigurableNavigationHandler) parent).getNavigationCases();
        } else {
            return null;
        }
    }

    @Override
    public void handleNavigation(FacesContext context, String from, String outcome) {
        if (StringUtils.isNotEmpty(outcome) && !outcome.endsWith("?faces-redirect=true")) {
            outcome += "?faces-redirect=true";
        }
        parent.handleNavigation(context, from, outcome);
    }
}
