/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.ili.grass.webbeans.commons;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Stack;
import net.ili.grass.entities.IdEntity;
import net.ili.grass.entities.TreeNodeEntity;
import net.ili.grass.webbeans.SuperWebBean;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Weir
 */
public class PrimeFacesTreeBuilder extends SuperWebBean {

    private static final org.slf4j.Logger log = LoggerFactory.getLogger(PrimeFacesTreeBuilder.class);

    /**
     *
     * @param treeList
     * @param currentTreeNodeEntity
     * @return
     */
    public static TreeNode buildTree(final List<? extends TreeNodeEntity> treeList, final TreeNodeEntity currentTreeNodeEntity) {
        TreeNode root = new DefaultTreeNode("root", null);
        createTree(treeList, root, currentTreeNodeEntity);
        return root;
    }

    /**
     *
     * @param relatrionMethodNames 主从关系的方法
     * @param treeLists 要合并的树。合并的树必须是主从关系的树且转入参数的顺序必须是主前从后。
     * @return 返回合并后的树
     */
    public static TreeNode buildMergeTree(String[] relatrionMethodNames, final List<? extends TreeNodeEntity>... treeLists) {
        TreeNode root = new DefaultTreeNode("root", null);
        createMergeTree(root, relatrionMethodNames, treeLists);
        return root;
    }

    private static void createTree(List<? extends TreeNodeEntity> treeList, TreeNode parentTreeNode, TreeNodeEntity currentTreeNodeEntity) {
        for (TreeNodeEntity currentNodeEntity : treeList) {
            if (currentNodeEntity.getParent() == null && (currentTreeNodeEntity == null || currentTreeNodeEntity.getId() == null)) {
                TreeNode currentTreeNode = new DefaultTreeNode(currentNodeEntity, parentTreeNode);
//                System.out.println(currentNodeEntity.toString());
                createTree(treeList, currentTreeNode, currentNodeEntity);//将当前成员作为上级成员递归。
            } else if (currentNodeEntity.getParent() != null && currentNodeEntity.getParent().equals(currentTreeNodeEntity)) {//获取当前节点成员
//                System.out.println("******** id *********:" + currentNodeEntity.getId() + " " + currentNodeEntity.toString());
                TreeNode currentTreeNode = new DefaultTreeNode(currentNodeEntity, parentTreeNode);
                createTree(treeList, currentTreeNode, currentNodeEntity);//将当前成员作为上级成员递归。
            }
        }
    }

    private static void createMergeTree(TreeNode parentNode, String[] relatrionMethodNames, List<? extends TreeNodeEntity>... treeLists) {
        for (int i = 0; i < treeLists.length; i++) {
            mergeTree(parentNode, null, treeLists[i], relatrionMethodNames[i] == null ? null : relatrionMethodNames[i].trim());
        }
    }

    private static void mergeTree(TreeNode parentTreeNode, TreeNodeEntity perantNodeEntity, List<? extends TreeNodeEntity> treeList, String relatrionMethodName) {
        //循环获取当前节点
        for (TreeNodeEntity currentNodeEntity : treeList) {
            //如果当前节点的父节点为空，则当前节点为父节点。
            if ((perantNodeEntity == null || perantNodeEntity.getId() == null) && currentNodeEntity.getParent() == null) {
                parentTreeNode = relatrionMethodName != null && relatrionMethodName.length() > 0
                        ? searchParentTreeNode(parentTreeNode, currentNodeEntity, relatrionMethodName) : parentTreeNode;
                TreeNode currentTreeNode = new DefaultTreeNode(currentNodeEntity, parentTreeNode);
//                TreeNode currentTreeNode = new DefaultTreeNode(currentNodeEntity, parentTreeNode);
                mergeTree(currentTreeNode, currentNodeEntity, treeList, relatrionMethodName);//将当前成员作为上级成员递归。
            } else if (currentNodeEntity.getParent() != null && currentNodeEntity.getParent().equals(perantNodeEntity)) {//获取当前节点成员
//                parentTreeNode = relatrionMethodName != null && relatrionMethodName.length() > 0
//                        ? searchParentTreeNode(parentTreeNode, currentNodeEntity, relatrionMethodName) : parentTreeNode;
                TreeNode currentTreeNode = new DefaultTreeNode(currentNodeEntity, parentTreeNode);
//                TreeNode currentTreeNode = new DefaultTreeNode(currentNodeEntity, parentTreeNode);
                mergeTree(currentTreeNode, currentNodeEntity, treeList, relatrionMethodName);//将当前成员作为上级成员递归。
            }
        }
    }

    //每个递归方法都有自己的返回值，只有第一层的递归方法才会把自己的返回值输出给调用他的程序。
    private static synchronized TreeNode searchParentTreeNode_Back(TreeNode parentNode, TreeNodeEntity nodeEntity, String relatrionMethodName) {
        for (TreeNode node : parentNode.getChildren()) {
            if (node.getChildCount() > 0) {
                searchParentTreeNode_Back(node, nodeEntity, relatrionMethodName);
            }
            if (nodeEntity != null && nodeEntity.getId() != null && node.getData() != null
                    && relatrionMethodName != null && relatrionMethodName.trim().length() > 0) {
                try {
                    Class nodeEntityOwnerClass = nodeEntity.getClass();//获取具体类
                    Method method = nodeEntityOwnerClass.getMethod(relatrionMethodName);//获取获取具体类的关联方法
                    Object objNodeEntity = method.invoke(nodeEntityOwnerClass.cast(nodeEntity));//调用关联方法（无参数）
                    if (node.getData().equals(objNodeEntity)) {//主从关联类型一致及主从对象相等
                        return node;
                    }
                } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
                    log.error(ex.getMessage());
                }
            }
        }
        return parentNode;
    }
    //迭代遍历树

    private static synchronized TreeNode searchParentTreeNode(TreeNode parentNode, TreeNodeEntity nodeEntity, String relatrionMethodName) {
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

    /**
     *
     * @param relatrionMethodNames 主从关系的方法
     * @param treeLists 要合并的树。合并的树必须是主从关系的树且转入参数的顺序必须是主前从后。
     * @return 返回合并后的树
     */
    public static TreeNode buildMergeSingleLayerTree(String[] relatrionMethodNames, final List<? extends IdEntity>... treeLists) {
        TreeNode root = new DefaultTreeNode("root", null);
        createMergeSingleLayerTree(root, relatrionMethodNames, treeLists);
        return root;
    }

    private static void createMergeSingleLayerTree(TreeNode parentNode, String[] relatrionMethodNames, List<? extends IdEntity>... treeLists) {
        for (int i = 0; i < treeLists.length; i++) {
            mergeSingleLayerTree(parentNode, treeLists[i], relatrionMethodNames[i] == null ? null : relatrionMethodNames[i].trim());
        }
    }

    private static void mergeSingleLayerTree(TreeNode parentTreeNode, List<? extends IdEntity> treeList, String relatrionMethodName) {
        //循环获取当前节点
        for (IdEntity currentNodeEntity : treeList) {
            parentTreeNode = relatrionMethodName != null && relatrionMethodName.length() > 0
                    ? lookupParentTreeNode(parentTreeNode, currentNodeEntity, relatrionMethodName) : parentTreeNode;
            TreeNode currentTreeNode = new DefaultTreeNode(currentNodeEntity, parentTreeNode);
        }
    }

    //迭代遍历树
    private static synchronized TreeNode lookupParentTreeNode(TreeNode parentNode, IdEntity nodeEntity, String relatrionMethodName) {
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
}
