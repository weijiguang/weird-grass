/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.ili.grass.webbeans.organization;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import net.ili.grass.entities.organization.Department;
import net.ili.grass.entities.organization.Organization;
import net.ili.grass.services.commons.Const;
import net.ili.grass.services.organization.DepartmentService;
import net.ili.grass.webbeans.SuperWebBean;
import net.ili.grass.webbeans.commons.PrimeFacesTreeBuilder;
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
public class DepartmentBean extends SuperWebBean {

    private static final Logger log = LoggerFactory.getLogger(DepartmentBean.class);
    private final String VIEW_ROOT = "/views/organization/department/";
    private final String listURL = VIEW_ROOT + "list";
    private final String addURL = VIEW_ROOT + "add";
    @Inject
    private DepartmentService deptService;
    private Department dept;
    private List<Department> deptList;
    private Organization selectedOrgan;
    private List<Organization> organList;
    private Department selectedPerantDept;
    private TreeNode departmentTree;
    private TreeNode selectedNode;

    /**
     *
     */
    public DepartmentBean() {

    }

    @PostConstruct
    public void init() {
        organList = this.deptService.getAllOrganization();
        this.deptList = deptService.getAll();
    }

    /**
     *
     * @return
     */
    public String prepareAdd() {
        this.dept = new Department();
        return this.addURL;
    }

    /**
     *
     * @return
     */
    public String prepareList() {
        this.list();
        return this.listURL;
    }

    /**
     *
     */
    public void list() {
        this.deptList = new ArrayList<>();
        this.deptList = deptService.getAll();
    }

    /**
     *
     */
    public void saveAndFlush() {
        if (this.dept != null) {
            if (dept.getOrgan() == null) {
                Messages.addGlobalWarn("操作失败。机构不可为空。", new Object[]{});
                return;
            }
            try {
                this.deptService.saveAndFlush(dept);
                this.reset();
                Messages.addGlobalInfo("操作成功。", "");
                this.prepareAdd();
            } catch (Exception e) {
                Messages.addGlobalError("操作失败！", "");
                log.error(e.getMessage());
            }
        }
    }

    /**
     *
     */
    public void remove() {
        if (this.dept != null) {
            try {
                this.deptService.remove(dept);
                this.reset();
                Messages.addGlobalInfo("操作成功。", "");
            } catch (Exception e) {
                Messages.addGlobalError("操作失败！", "");
                log.error(e.getMessage());
            }
        }
    }

    public void deleteNode() {
        if (selectedNode != null && selectedNode.getData() != null && selectedNode.getData() instanceof Department) {
            dept = (Department) selectedNode.getData();
            remove();
        }
    }

    private void reset() {
        this.dept = null;
        this.deptList = null;
        this.organList = null;
        this.departmentTree = null;
    }

    /**
     *
     * @return
     */
    public Department getDept() {
        return dept;
    }

    /**
     *
     * @param dept
     */
    public void setDept(Department dept) {
        this.dept = dept;
    }

    /**
     *
     * @return
     */
    public List<Department> getDeptList() {
        if (deptList == null || deptList.isEmpty()) {
            list();
        }
        return deptList;
    }

    /**
     *
     * @param deptList
     */
    public void setDeptList(List<Department> DepartmentList) {
        this.deptList = DepartmentList;
    }

    public List<Organization> getOrganList() {
        if (organList == null || organList.isEmpty()) {
            organList = this.deptService.getAllOrganization();
        }
        return organList;
    }

    public void setOrganList(List<Organization> organList) {
        this.organList = organList;
    }

    /**
     *
     * @return
     */
    public Organization getSelectedOrgan() {
        return selectedOrgan;
    }

    /**
     *
     * @param selectedOrgan
     */
    public void setSelectedOrgan(Organization selectedOrgan) {
        this.selectedOrgan = selectedOrgan;
        if (this.dept != null) {
            this.dept.setOrgan(selectedOrgan);
        }
    }

    /**
     *
     * @return
     */
    public Department getSelectedPerantDept() {
        return selectedPerantDept == null ? new Department() : selectedPerantDept;
    }

    /**
     *
     * @param selectedPerantDept
     */
    public void setSelectedPerantDept(Department selectedPerantDept) {
        this.selectedPerantDept = selectedPerantDept;
        if (this.dept != null) {
            this.dept.setParent(selectedPerantDept);
        }
    }

    /**
     *
     * @return
     */
    public TreeNode getDepartmentTree() {
        organList = deptService.getAllOrganization();
        list();
        departmentTree = PrimeFacesTreeBuilder.buildMergeTree(new String[]{"", "getOrgan"}, organList, deptList);
        return departmentTree;
    }

    /**
     *
     * @return
     */
    public TreeNode getSelectedNode() {
        return selectedNode;
    }

    /**
     *
     * @param selectedNode
     */
    public void setSelectedNode(TreeNode selectedNode) {
        this.selectedNode = selectedNode;
    }
}
