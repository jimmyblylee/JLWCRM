/**
 * Project Name : jbp-features-sys <br>
 * File Name : TokenHelper.java <br>
 * Package Name : com.asdc.jbp.sys.service.impl <br>
 * Create Time : May 1, 2016 <br>
 * Create by : xiangyu_li@asdc.com.cn <br>
 * Copyright © 2006, 2016, ASDC DAI. All rights reserved.
 */
package com.asdc.jbp.sys.service.impl;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.asdc.jbp.dict.entity.SysDict;
import com.asdc.jbp.framework.utils.BeanUtils;
import com.asdc.jbp.sys.entity.SysDept;
import com.asdc.jbp.sys.entity.SysFunc;
import com.asdc.jbp.sys.entity.SysUser;
import com.asdc.jbp.sys.token.Dept;
import com.asdc.jbp.sys.token.Dict;
import com.asdc.jbp.sys.token.Func;
import com.asdc.jbp.sys.token.FuncTree;
import com.asdc.jbp.sys.token.User;

/**
 * ClassName : TokenHelper <br>
 * Description : Token helper, provide converters from entity to vo <br>
 * Create Time : May 1, 2016 <br>
 * Create by : xiangyu_li@asdc.com.cn <br>
 *
 */
public abstract class TokenHelper {

    /**
     * Description : convert {@link SysDict} into {@link Dict} <br>
     * Create Time: May 1, 2016 <br>
     * Create by : xiangyu_li@asdc.com.cn <br>
     *
     * @param entity
     * @return vo of {@link Dict} or null if the entity is null
     */
    public static Dict convert(SysDict entity) {
        if (entity == null) {
            return null;
        }
        Dict dict = new Dict();
        dict.setId(entity.getId());
        dict.setNature(entity.getNature());
        dict.setCode(entity.getCode());
        dict.setValue(entity.getValue());
        return dict;
    }

    /**
     * Description : convert {@link SysDept} into {@link Dept} <br>
     * Create Time: May 1, 2016 <br>
     * Create by : xiangyu_li@asdc.com.cn <br>
     *
     * @param entity
     * @return vo of {@link Dept} or null if the entity is null
     */
    public static Dept convert(SysDept entity) {
        if (entity == null) {
            return null;
        }
        Dept dept = new Dept();
        dept.setId(entity.getId());
        dept.setName(entity.getName());
        return dept;
    }

    /**
     * Description : convert {@link SysUser} into {@link User} <br>
     * Create Time: May 1, 2016 <br>
     * Create by : xiangyu_li@asdc.com.cn <br>
     *
     * @param entity
     * @return vo of {@link User} or null if the entity is null
     */
    public static User convert(SysUser entity) {
        if (entity == null) {
            return null;
        }
        User user = new User();
        user.setId(entity.getId());
        user.setAccount(entity.getAccount());
        user.setDept(convert(entity.getDept()));
        user.setEmail(entity.getEmail());
        user.setName(entity.getName());
        user.setType(convert(entity.getType()));
        return user;
    }

    public static FuncTree convert(List<SysFunc> sources) {
        List<FuncTree> roots = new ArrayList<FuncTree>();
        for (SysFunc entity : sources) {
            if (entity.getIsRoot()) {
                FuncTree func = new FuncTree();
                copyFuncInfo(entity, func);
                roots.add(func);
                if (func.getHasChildren()) {
                    fetchChildren(func, sources);
                }
            }
        }
        FuncTree tree = new FuncTree();
        tree.setId(-1);
        tree.setName("Root");
        tree.setCode("root");
        tree.setChildren(roots);
        tree.setHasChildren(roots.size() > 0);
        return tree;
    }

    private static void fetchChildren(FuncTree func, List<SysFunc> sources) {
        func.setChildren(new LinkedList<FuncTree>());
        for (SysFunc entity : sources) {
            if (entity.getParent() != null && entity.getParent().getId() == func.getId()) {
                FuncTree child = new FuncTree();
                copyFuncInfo(entity, child);
                func.getChildren().add(child);
                if (child.getHasChildren()) {
                    fetchChildren(child, sources);
                }
            }
        }
    }

    private static void copyFuncInfo(SysFunc entity, Func func) {
        BeanUtils.copyProperties(entity, func, "parent", "typeCode", "type", "desc", "isBaseFunc", "isEnabled", "children");
        func.setType(convert(entity.getType()));
    }
}
