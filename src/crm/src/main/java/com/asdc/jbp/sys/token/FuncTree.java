/**
 * Project Name : jbp-plugins-token <br>
 * File Name : FuncTree.java <br>
 * Package Name : com.asdc.jbp.sys.token <br>
 * Create Time : Apr 30, 2016 <br>
 * Create by : xiangyu_li@asdc.com.cn <br>
 * Copyright © 2006, 2016, ASDC DAI. All rights reserved.
 */
package com.asdc.jbp.sys.token;

import java.util.LinkedList;
import java.util.List;

/**
 * ClassName : FuncTree <br>
 * Description : Func tree <br>
 * Create Time : Apr 30, 2016 <br>
 * Create by : xiangyu_li@asdc.com.cn <br>
 *
 */
public class FuncTree extends Func {

    private static final long serialVersionUID = 5506814251868859211L;

    private FuncTree parent;
    private List<FuncTree> children;
    private Boolean hasChildren;
    private Boolean isRoot;
    
    /**
     * Description : make a clone of Func by FuncTree <br>
     * Create Time: Apr 30, 2016 <br>
     * Create by : xiangyu_li@asdc.com.cn <br>
     *
     * @param node
     * @return the clone of one FuncTree
     */
    public static Func cloneFunc(FuncTree node) {
        Func func = new Func();
        func.setId(node.getId());
        func.setCode(node.getCode());
        func.setName(node.getName());
        func.setOrder(node.getOrder());
        func.setType(node.getType());
        func.setUrl(node.getUrl());
        func.setIcon(node.getIcon());
        func.setIsVisible(node.getIsVisible());
        return func;
    }
    
    /**
     * Description : get all data in this and his sub func tree nodes <br>
     * Create Time: Apr 30, 2016 <br>
     * Create by : xiangyu_li@asdc.com.cn <br>
     *
     * @return the list of Func
     */
    public List<Func> getAllCloneSubFuncs() {
        List<Func> list = new LinkedList<>();
        list.add(cloneFunc(this));
        if (hasChildren) {
            for (FuncTree child : children) {
                list.addAll(child.getAllCloneSubFuncs());
            }
        }
        return list;
    }

    /**
     * @return the parent
     */
    public FuncTree getParent() {
        return parent;
    }

    /**
     * @param parent
     *            the parent to set
     */
    public void setParent(FuncTree parent) {
        this.parent = parent;
    }

    /**
     * @return the children
     */
    public List<FuncTree> getChildren() {
        return children;
    }

    /**
     * @param children
     *            the children to set
     */
    public void setChildren(List<FuncTree> children) {
        this.children = children;
    }

    /**
     * @return the hasChildren
     */
    public Boolean getHasChildren() {
        return hasChildren;
    }

    /**
     * @param hasChildren
     *            the hasChildren to set
     */
    public void setHasChildren(Boolean hasChildren) {
        this.hasChildren = hasChildren;
    }

    /**
     * @return the isRoot
     */
    public Boolean getIsRoot() {
        return isRoot;
    }

    /**
     * @param isRoot
     *            the isRoot to set
     */
    public void setIsRoot(Boolean isRoot) {
        this.isRoot = isRoot;
    }
    
    @Override
    public String toString() {
        return getCode() != null ? getCode() : "";
    }
}
