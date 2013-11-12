/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.ili.base.webbeans.organization;

import java.util.ArrayList;
import java.util.List;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import net.ili.base.entities.organization.Organization;
import net.ili.base.services.commons.Const;
import net.ili.base.services.organization.OrganizationService;
import net.ili.base.webbeans.SuperWebBean;
import net.ili.base.webbeans.commons.Msg;
import net.ili.base.webbeans.commons.PrimeFacesTreeBuilder;
import org.omnifaces.util.Messages;
import org.primefaces.model.TreeNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Weir
 */
@Named
@ViewScoped
public class OrganizationBean extends SuperWebBean {

    private static final long serialVersionUID = -459438028933624729L;
    private static final Logger log = LoggerFactory.getLogger(OrganizationBean.class);
    private final String VIEW_ROOT = "/views/organization/organization/";
    private final String listURL = VIEW_ROOT + "list";
    private final String addURL = VIEW_ROOT + "add";
    @Inject
    OrganizationService organService;
    private Organization organ;
    private Organization selectedParent;
    private List<Organization> organList;
    private TreeNode organTree;
    private TreeNode selectedNode;

    public OrganizationBean() {
    }

    public String preparelist() {
        list();
        return this.listURL;
    }

    public String prepareAdd() {
        organ = new Organization();
        selectedParent = new Organization();
        list();
        return this.addURL;
    }

    public String save() {
        try {
            organService.saveAndFlush(organ);
            this.organTree = null;
            Msg.info("操作成功。");
        } catch (Exception e) {
            log.error(e.getMessage());
            Msg.error("操作失败！");
            return null;
        }
        return this.preparelist();
    }

    public void remove() {
        try {
            if (selectedNode != null && selectedNode.getData() instanceof Organization) {
                organ = (Organization) selectedNode.getData();
                organService.remove(organ);
                organTree = null;
                Messages.addGlobalInfo("操作成功。", new Object[]{});
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            Messages.addGlobalInfo("操作失败。", new Object[]{});
        }
    }

    public void list() {
        organList = new ArrayList<>();
        organList = organService.getAll();
    }

    public Organization getOrgan() {
        return organ;
    }

    public void setOrgan(Organization organ) {
        this.organ = organ;
    }

    public Organization getSelectedParent() {
        return selectedParent;
    }

    public void setSelectedParent(Organization selectedParent) {
        if (null != this.organ) {
            this.organ.setParent(selectedParent);
        }
        this.selectedParent = selectedParent;
    }

    public List<Organization> getOrganList() {
        return organList;
    }

    public void setOrganList(List<Organization> organList) {
        this.organList = organList;
    }

    public TreeNode getOrganTree() {
        if (organTree == null) {
            this.list();
            organTree = PrimeFacesTreeBuilder.buildTree(organList, new Organization());
        }
        return organTree;
    }

    public TreeNode getSelectedNode() {
        return selectedNode;
    }

    public void setSelectedNode(TreeNode selectedNode) {
        this.selectedNode = selectedNode;
    }

    private void buiderTree() {
        List<Organization> list = organService.getAll();
        for (Organization org : list) {
        }
    }
}
