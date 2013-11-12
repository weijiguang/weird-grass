/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.ili.base.webbeans.authority;

import java.util.List;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import net.ili.base.entities.authority.Modules;
import net.ili.base.services.authority.ModulesService;
import net.ili.base.services.commons.Const;
import net.ili.base.webbeans.SuperWebBean;
import org.omnifaces.util.Messages;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Weir
 */
@Named
@ViewScoped
public class ModulesBean extends SuperWebBean {

    private static final long serialVersionUID = -332568561298829759L;
    private static final Logger log = LoggerFactory.getLogger(ModulesBean.class);
    private final String VIEW_ROOT = "/views/authority/modules/";
    @Inject
    private ModulesService modulesService;
    private Modules modules;
    private List<Modules> moduleList;

    public String prepareAdd() {
        this.modules = new Modules();
        return this.VIEW_ROOT + "add";
    }

    public String prepareList() {
        this.list();
        return this.VIEW_ROOT + "list";
    }

    public void list() {
        this.moduleList = this.modulesService.getAll();
    }

    public void save() {
        try {
            this.modulesService.saveAndFlush(modules);
            Messages.addGlobalInfo("操作成功", new Object[]{});
        } catch (Exception e) {
            log.error(e.getMessage());
            Messages.addGlobalError("操作失败", new Object[]{});
        }
    }

    public void remove() {
        if (modules != null && !modules.itIsNew()) {
            try {
                this.modulesService.remove(modules);
                Messages.addGlobalInfo("操作成功", new Object[]{});
            } catch (Exception e) {
                log.error(e.getMessage());
                Messages.addGlobalError("操作失败", new Object[]{});
            }
        }
    }

    public Modules getModules() {
        return modules;
    }

    public void setModules(Modules modules) {
        this.modules = modules;
    }

    public List<Modules> getModuleList() {
        return moduleList;
    }

    public void setModuleList(List<Modules> moduleList) {
        this.moduleList = moduleList;
    }
}
