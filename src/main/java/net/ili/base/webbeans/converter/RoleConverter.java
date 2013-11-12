/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.ili.base.webbeans.converter;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import net.ili.base.entities.authority.Role;
import net.ili.base.services.authority.RoleService;

/**
 * The @FacesConverter isn't an eligible injection target. Replace it by
 *
 * @ManagedBean or @Named. As you'd like to use CDI as as well, use @Named.
 *
 * @Named
 * @ApplicationScoped // I assume that your converter doesn't have any state.
 * public class MyConverter implements Converter { // ... } You only need to
 * change the way it's been referenced in the views. You cannot rely on forClass
 * anymore. You'd need to explicitly specify it as #{myConverter}.
 *
 * //<h:inputSomething ... converter="#{myConverter}" />
 * or
 *
 * //<h:inputSomething ...>
 * // <f:converter binding="#{myConverter}" />
 * //</h:inputSomething>
 * If you really need to keep the @FacesConverter in favor of forClass, then
 * you'd need to grab the EJB manually by JNDI. A concrete example is shown in
 * this blog article. I can however not tell that for CDI beans.
 *
 * The JSF guys have confirmed this embarrassing oversight and they will make
 * the @FacesConverter an eligible injection target in upcoming JSF 2.2, see
 * also JSF spec issue 763.
 * @author Weir
 */
//@FacesConverter("organizationConverter")
@Named
@ViewScoped
public class RoleConverter implements Converter {

    @Inject
    private RoleService roleService;

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String submittedValue) {
        if (submittedValue.trim().equals("")) {
            return null;
        } else {
            try {
                long id = Long.parseLong(submittedValue);
                return roleService.getOne(id);
            } catch (NumberFormatException exception) {
                throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Conversion Error", "Not a valid  Department"));
            }
        }
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object value) {
        if (value == null || "".equals(value)) {
            return "";
        } else {
            return String.valueOf(((Role) value).getId());
        }
    }
}
