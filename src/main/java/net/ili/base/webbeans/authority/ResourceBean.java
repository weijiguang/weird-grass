/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.ili.base.webbeans.authority;

import java.util.List;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import net.ili.base.entities.authority.Resource;
import net.ili.base.services.authority.ResourceService;
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
public class ResourceBean extends SuperWebBean {

    private static final Logger log = LoggerFactory.getLogger(ResourceBean.class);
    private static final long serialVersionUID = -767298838803456253L;
    private final String VIEW_ROOT = "/views/authority/resource/";
    @Inject
    ResourceService resourceService;
    private List<Resource> resourceList;
    private Resource resource;
    private Resource selectedResource;

    public String prepareList() {
        this.list();
        return VIEW_ROOT + "list";
    }

    public String prepareAdd() {
        this.resource = new Resource();
        return VIEW_ROOT + "add";
    }

    public String prepareEdit() {
        if (selectedResource != null && !selectedResource.itIsNew()) {
            return VIEW_ROOT + "edit";
        }
        return null;
    }

    public void list() {
        resourceList = resourceService.getAll();
    }

    public void save() {
        try {
            resourceService.saveAndFlush(resource);
            Messages.addGlobalInfo("操作成功。", new Object[]{});
        } catch (Exception e) {
            Messages.addGlobalError("操作失败。", new Object[]{});
            log.error(e.getMessage());
        }
    }

    public void edit() {
         try {
            resourceService.saveAndFlush(selectedResource);
            Messages.addGlobalInfo("操作成功。", new Object[]{});
        } catch (Exception e) {
            Messages.addGlobalError("操作失败。", new Object[]{});
            log.error(e.getMessage());
        }
    }

    public List<Resource> getResourceList() {
        return resourceList;
    }

    public void setResourceList(List<Resource> resourceList) {
        this.resourceList = resourceList;
    }

    public Resource getResource() {
        return resource;
    }

    public void setResource(Resource resource) {
        this.resource = resource;
    }

    public Resource getSelectedResource() {
        return selectedResource;
    }

    public void setSelectedResource(Resource selectedResource) {
        this.selectedResource = selectedResource;
    }
}
