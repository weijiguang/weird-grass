/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.ili.grass.webbeans.authority;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import net.ili.grass.entities.IdEntity;
import net.ili.grass.entities.authority.Modules;
import net.ili.grass.entities.authority.Resource;
import net.ili.grass.entities.authority.Role;
import net.ili.grass.services.authority.ModulesService;
import net.ili.grass.services.authority.ResourceService;
import net.ili.grass.services.authority.RoleService;
import net.ili.grass.services.commons.Const;
import net.ili.grass.webbeans.SuperWebBean;
import org.omnifaces.util.Messages;
import org.primefaces.model.CheckboxTreeNode;
import org.primefaces.model.TreeNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Weir
 */
@Named
@ViewScoped
public class GrantPermissionBean extends SuperWebBean {

    private static final long serialVersionUID = -6699582142347403542L;
    private static final Logger log = LoggerFactory.getLogger(GrantPermissionBean.class);
    private static final String VIEW_ROOT = "/views/authority/role/";
    @Inject
    private RoleService roleService;
    @Inject
    private ModulesService modulesService;
    @Inject
    private ResourceService resourceService;
    private Role role;
    private Role selectedRole;
    private List<Resource> resourcesList;
    private List<Resource> selectedRoleResources;
    private TreeNode resourceTree;
    private TreeNode[] selectedNodes;

    /**
     *
     */
    public GrantPermissionBean() {

    }

    /**
     *
     */
    @PostConstruct
    public void init() {
        List<Modules> moduleses = modulesService.getAll();
        resourcesList = resourceService.getAll();
        resourceTree = new CheckboxTreeNode("Root", null);
        createMergeSingleLayerTree(resourceTree, new String[]{"", "getModules"}, moduleses, resourcesList);
    }

    /**
     *
     * @return
     */
    public String prepareGrantPermission() {
        if (selectedRole != null) {
            role = roleService.getOne(selectedRole.getId());
            selectedRoleResources = role.getResources();
            if (selectedRoleResources == null || selectedRoleResources.isEmpty()) {
                selectedNodes = null;
            }
            settingSelectedNode(resourceTree, selectedRoleResources);
        }
        return VIEW_ROOT + "grant_permission";
    }

    /**
     *
     */
    public void savePermissionSetting() {
        if (selectedRole != null) {
            if (selectedRoleResources == null) {
                selectedRoleResources = new ArrayList<>();
            }
            selectedRoleResources.clear();
            if (selectedNodes != null && selectedNodes.length > 0) {
                for (TreeNode node : selectedNodes) {
                    if (node.getData() instanceof Resource) {
                        selectedRoleResources.add((Resource) node.getData());
                    }
                }
            }
            role = roleService.getOne(selectedRole.getId());
            role.setResources(selectedRoleResources);
            try {
                roleService.saveAndFlush(role);
                Messages.addGlobalInfo("操作成功", new Object[]{});
            } catch (Exception e) {
                log.error(e.getMessage());
                Messages.addGlobalError("操作失败", new Object[]{});
            }
        }
    }

    /**
     *
     * @return
     */
    public Role getSelectedRole() {
        return selectedRole;
    }

    /**
     *
     * @param selectedRole
     */
    public void setSelectedRole(Role selectedRole) {
        this.selectedRole = selectedRole;
    }

    /**
     *
     * @return
     */
    public TreeNode getResourceTree() {
        return resourceTree;
    }

    /**
     *
     * @param resourceTree
     */
    public void setResourceTree(TreeNode resourceTree) {
        this.resourceTree = resourceTree;
    }

    /**
     *
     * @return
     */
    public TreeNode[] getSelectedNodes() {
        return selectedNodes;
    }

    /**
     *
     * @param selectedNodes
     */
    public void setSelectedNodes(TreeNode[] selectedNodes) {
        this.selectedNodes = selectedNodes;
    }

    private void createMergeSingleLayerTree(TreeNode parentNode, String[] relatrionMethodNames, List<? extends IdEntity>... treeLists) {
        for (int i = 0; i < treeLists.length; i++) {
            mergeSingleLayerTree(parentNode, treeLists[i], relatrionMethodNames[i] == null ? null : relatrionMethodNames[i].trim());
        }
    }

    private void mergeSingleLayerTree(TreeNode parentTreeNode, List<? extends IdEntity> treeList, String relatrionMethodName) {
        //循环获取当前节点
        for (IdEntity currentNodeEntity : treeList) {
            parentTreeNode = relatrionMethodName != null && relatrionMethodName.length() > 0
                    ? lookupParentTreeNode(parentTreeNode, currentNodeEntity, relatrionMethodName) : parentTreeNode;
            TreeNode currentTreeNode = new CheckboxTreeNode(currentNodeEntity, parentTreeNode);
        }
    }

    //迭代遍历树
    private synchronized TreeNode lookupParentTreeNode(TreeNode parentNode, IdEntity nodeEntity, String relatrionMethodName) {
        Stack<TreeNode> treeNodeStack = new Stack<>();

        while (parentNode.getParent() != null) {//获得根目录
            parentNode = parentNode.getParent();
        }
        // 1.先添加第1层的子节点到迭代堆栈里
        if (!parentNode.getChildren().isEmpty()) {
            for (TreeNode node : parentNode.getChildren()) {
                treeNodeStack.add(node);
            }
        }

        TreeNode tempNode;
        while (!treeNodeStack.empty()) {
            tempNode = treeNodeStack.pop();//弹出一个节点

            if (nodeEntity != null && nodeEntity.getId() != null && tempNode.getData() != null
                    && relatrionMethodName != null && relatrionMethodName.trim().length() > 0) {
                try {
                    Class nodeEntityOwnerClass = nodeEntity.getClass();//获取具体类
                    Method method = nodeEntityOwnerClass.getMethod(relatrionMethodName);//获取获取具体类的关联方法
                    Object objNodeEntity = method.invoke(nodeEntityOwnerClass.cast(nodeEntity));//调用关联方法（无参数）
                    if (tempNode.getData().equals(objNodeEntity)) {//主从关联类型一致及主从对象相等
                        return tempNode;
                    }
                } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
                    log.error(ex.getMessage());
                }
            }

            if (!tempNode.isLeaf()) {//如果不是叶节点，则将其子节点压入堆栈
                for (TreeNode node : tempNode.getChildren()) {
                    treeNodeStack.push(node);
                }
            }
        }
        return parentNode;
    }

    //迭代遍历树
    private synchronized TreeNode[] settingSelectedNode(TreeNode parentNode, List<? extends IdEntity> nodeEntities) {
        Stack<TreeNode> treeNodeStack = new Stack<>();
        TreeNode[] selectTreeNode = null;
        if (nodeEntities != null && !nodeEntities.isEmpty()) {
            selectTreeNode = new TreeNode[nodeEntities.size()];
        }

        while (parentNode.getParent() != null) {//获得根目录
            parentNode = parentNode.getParent();
        }
        // 1.先添加第1层的子节点到迭代堆栈里
        if (!parentNode.getChildren().isEmpty()) {
            for (TreeNode node : parentNode.getChildren()) {
                treeNodeStack.add(node);
            }
        }
        int i = 0;
        TreeNode tempNode;
        while (!treeNodeStack.empty()) {
            tempNode = treeNodeStack.pop();//弹出一个节点
            tempNode.setSelected(false);
            if (nodeEntities != null && !nodeEntities.isEmpty() && nodeEntities.contains(tempNode.getData())) {
                tempNode.setSelected(true);
                selectTreeNode[i] = tempNode;
                i++;
            }
            if (!tempNode.isLeaf()) {//如果不是叶节点，则将其子节点压入堆栈
                for (TreeNode node : tempNode.getChildren()) {
                    treeNodeStack.push(node);
                }
            }
        }
        return selectTreeNode;
    }
}
