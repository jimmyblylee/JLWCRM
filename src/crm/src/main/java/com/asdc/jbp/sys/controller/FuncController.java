/*
 * Project Name : jbp-features-sys <br>
 * File Name : FuncController.java <br>
 * Package Name : com.asdc.jbp.sys.controller <br>
 * Create Time : May 1, 2016 <br>
 * Create by : xiangyu_li@asdc.com.cn <br>
 * Copyright © 2006, 2016, ASDC DAI. All rights reserved.
 */
package com.asdc.jbp.sys.controller;

import java.beans.IntrospectionException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.asdc.jbp.dict.entity.SysDict;
import com.asdc.jbp.dict.service.DictService;
import com.asdc.jbp.framework.action.helper.ControllerHelper;
import com.asdc.jbp.framework.dao.Parameter;
import com.asdc.jbp.framework.dto.WorkDTO;
import com.asdc.jbp.framework.exception.ServiceException;
import com.asdc.jbp.framework.utils.ProxyStripper;
import com.asdc.jbp.sys.entity.SysFunc;
import com.asdc.jbp.sys.service.AuthorityMgtService;

/**
 * ClassName : FuncController <br>
 * Description : 功能管理控制器 <br>
 * Create Time : May 1, 2016 <br>
 * Create by : shuai_zhou@asdc.com.cn <br>
 *
 */
@SuppressWarnings("ALL")
@Controller("FuncController")
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class FuncController extends ControllerHelper {

    @Resource
    private AuthorityMgtService authService;

    @Resource
    private DictService service;

    @Override
    public void setWorkDTO(WorkDTO workDTO) {
        this.workDTO = workDTO;
    }

    public void setService(DictService service) {
        this.service = service;
    }

    public void setAuthService(AuthorityMgtService authService) {
        this.authService = authService;
    }

    /**
     * Description：添加菜单
     *
     * @throws ServiceException
     * @return void
     * @author zhoushuai /shuai_zhou@asdc.com.cn
     *
     */
    public void addFunc() throws ServiceException {
        SysFunc func = workDTO.convertJsonToBeanByKey("addFunc", SysFunc.class);
        if (func.getParent().getId() == null) {
            func.getParent().setId(0);
        }
        func.setIsEnabled(true);
        if (func.getParent().getId() == null || "".equals(func.getIcon())) {
            func.setIcon("fa fa-user");
        }
        authService.createFunc(func);
        workDTO.setResult(true);
    }

    public void queryFuncByCode() throws ServiceException, IntrospectionException {
        String code = workDTO.get("code").toString().replace("\"", "");
        SysFunc func = authService.queryFuncByCode(code);
        workDTO.setResult(ProxyStripper.cleanFromProxies(func));
    }

    /**
     *
     * Description：根据ID删除菜单
     *
     * @throws ServiceException
     * @return void
     * @author zhoushuai /shuai_zhou@asdc.com.cn
     *
     */
    @SuppressWarnings("unchecked")
    public void deleteFuncInfo() throws ServiceException {
        Map<String, Object> funcMap = workDTO.convertJsonToMapByKey("funcIdList");
        List<Integer> funcIdList = (List<Integer>) funcMap.get("funcId");
        authService.removeFuncByIds(funcIdList);
        workDTO.setResult(true);
    }

    @SuppressWarnings("unchecked")
    public void recoverFuncInfo() throws ServiceException {
        Map<String, Object> roleMap = workDTO.convertJsonToMapByKey("funcIdList");
        List<Integer> roleIdList = (List<Integer>) roleMap.get("funcId");
        /**
         * 判断此菜单是否存在上级菜单
         */
        for (int i = 0; i < roleIdList.size(); i++) {
            roleIdList.addAll(getFuncIds(roleIdList.get(i)));
        }
        authService.recoverFuncInfo(roleIdList);
        workDTO.setResult(true);
    }

    public List<Integer> getFuncIds(int funcId) throws ServiceException {
        List<Integer> funcIds = new ArrayList<Integer>();
        SysFunc func = (SysFunc) authService.getFuncById(funcId);
        if (func.getParent().getIsEnabled() != null && !func.getParent().getIsEnabled()) {
            getFuncIds(func.getParent().getId());
            funcIds.add(func.getParent().getId());
        }
        return funcIds;
    }

    /**
     *
     * Description：修改菜单
     *
     * @throws ServiceException
     * @return void
     * @author zhoushuai /shuai_zhou@asdc.com.cn
     * @throws IntrospectionException
     *
     */
    public void update() throws ServiceException, IntrospectionException {
        SysFunc func = workDTO.convertJsonToBeanByKey("updateFunc", SysFunc.class);
        SysFunc sysFunc = authService.updateFunc(func);
        workDTO.setResult(ProxyStripper.cleanFromProxies(sysFunc));
    }

    /**
     *
     * Description：根据顶级父级菜单栏
     *
     * @throws ServiceException
     * @throws IntrospectionException
     * @return void
     * @author name：yangxuan <br>
     *         email: xuan_yang@asdc.com.cn
     *
     */
    public void getFuncParent() throws ServiceException, IntrospectionException {
        String updateFuncId = workDTO.get("funcId");
        List<SysFunc> listSysFunc = authService.queryParentByFuncId(new Integer(updateFuncId));
        workDTO.setResult(ProxyStripper.cleanFromProxies(listSysFunc));
    }

    /**
     *
     * Description：通过菜单id得到菜单信息
     *
     * @throws ServiceException
     * @throws IntrospectionException
     * @return void
     * @author name：yangxuan <br>
     *         email: xuan_yang@asdc.com.cn
     *
     */
    public void findFuncInfoById() throws ServiceException, IntrospectionException {
        String updateFuncId = workDTO.get("updateFuncId");
        SysFunc funcById = authService.getFuncById(new Integer(updateFuncId));
        workDTO.setResult(ProxyStripper.cleanFromProxies(funcById));
    }

    /**
     * Description：树状菜单显示
     *
     * @throws ServiceException
     * @author zhoushuai /shuai_zhou@asdc.com.cn
     * @throws ServiceException
     *
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public void queryAllFunc() throws ServiceException {
        List mapList = new ArrayList();
        List<SysFunc> funcList = authService.queryAllSysFuncGroup(); // 获取所有parent=0的数据
        for (SysFunc func : funcList) {
            Map map = new HashMap();
            map.put("id", func.getId());
            map.put("label", func.getName());
            boolean ischecked = false;
            map.put("checked", ischecked);
            if (func.getChildren() != null) {
                map.put("type", "folder");
                map.put("children", getChildren(func.getId()));
            }
            map.put("type", "doc");
            mapList.add(map);
        }
        workDTO.setResult(mapList);
    }

    /**
     * Description：树状子菜单显示
     *
     * @throws ServiceException
     * @author zhoushuai /shuai_zhou@asdc.com.cn
     * @throws ServiceException
     *
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    protected List getChildren(Integer funcId) throws ServiceException {
        List mapList = new ArrayList();
        List<SysFunc> funcList = authService.queryChildren(funcId);

        for (SysFunc func : funcList) {
            Map map = new HashMap();
            map.put("id", func.getId());
            map.put("label", func.getName());
            boolean ischecked = false;
            map.put("checked", ischecked);
            if (func.getChildren() != null) {
                map.put("type", "folder");
                map.put("children", getChildren(func.getId()));
            }
            map.put("type", "doc");
            mapList.add(map);
        }

        return mapList;
    }

    /**
     *
     * Description：查询所有菜单
     *
     * @throws ServiceException
     * @return void
     * @author zhoushuai /shuai_zhou@asdc.com.cn
     *
     */
    @SuppressWarnings({ "unchecked" })
    public void queryFuncInfo() throws ServiceException, IntrospectionException {
        Map<String, Object> pageQueryParams = workDTO.convertJsonToMapByKey("pageQuery");
        String funcStatus = (String) pageQueryParams.get("funcStatus");
        String pageQuery = (String) pageQueryParams.get("queryParams");
        int start = workDTO.getStart();
        int limit = workDTO.getLimit();
        /**
         * 第一次查询查找parentId为0的数据(第一次查询所有的查询条件都是空的)
         */
        Integer parentId = 0;
        if (!"".equals(pageQuery) || !funcStatus.equals("true")) {
            parentId = null;
        }
        Boolean isEnabled = true;
        if (!funcStatus.equals("true")) {
            isEnabled = false;
        }
        log.debug("start:" + start + ",limit:" + limit + ",funcStatus:" + funcStatus + ",pageQuery" + pageQuery
            + ",parentId" + parentId);
        List<SysFunc> queryAllFunc = (List<SysFunc>) authService.queryInfoNoPage("sys.hql.queryAllSysFuncPage",
            Parameter.toList("pageQuery", "%" + pageQuery + "%", "isEnabled", isEnabled, "parentId", parentId));
        // 类型
        List<SysDict> listDictType = service.getSelectDictInfo("FUNC_TYPE", "NATURE");
        List<SysDict> listDictVisible = service.getSelectDictInfo("WHETHER_TYPE", "WHETHER_CODE");

        Set<SysFunc> ss = new HashSet<>();
        for (int i = 0; i < queryAllFunc.size(); i++) {
            List<SysFunc> listfunc = authService.queryChildren(queryAllFunc.get(i).getId());

            if (listfunc.size() != 0) {
                queryAllFunc.get(i).setChildren(ss);
            }
            for (SysDict sysDictType : listDictType) {
                if (queryAllFunc.get(i).getType().getCode().equals(sysDictType.getCode())) {
                    // FIXME 这里使用了旧实现
                    System.err.println("这里使用了错误的实现，需要修改");
//                    queryAllFunc.get(i).setTypeCode(sysDictType.getValue());
                }
            }
            for (SysDict sysDcitVisible : listDictVisible) {
                if (queryAllFunc.get(i).getIsVisible().equals(Boolean.parseBoolean(sysDcitVisible.getCode()))) {
                    queryAllFunc.get(i).setVisible(sysDcitVisible.getValue());
                }
                ;
            }
        }

		/*
		 * 判断是否存在上级 遍历一下，
		 */

        int totle = authService.queryTotle("sys.hql.queryFuncTotle",
            Parameter.toList("pageQuery", "%" + pageQuery + "%", "isEnabled", isEnabled, "parentId", parentId));
        workDTO.setTotle(totle);
        workDTO.setResult(ProxyStripper.cleanFromProxies(queryAllFunc));
    }

    @SuppressWarnings("unused")
    private boolean getIsEnabled(String funcStatus) {
        boolean isEnabled = false;
        if (funcStatus.equals("可用") || funcStatus.equals("")) {
            isEnabled = true;
        }
        return isEnabled;
    }

    /**
     *
     * Description：通过菜单id
     *
     * @throws NumberFormatException
     * @throws ServiceException
     * @return void
     * @author name：yangxuan <br>
     *         email: xuan_yang@asdc.com.cn
     */
    public void spliceTrTd() throws NumberFormatException, ServiceException {
        String funcParentId = workDTO.get("funcParentId");
        String paddingStr = workDTO.get("padding");
        String subStr = paddingStr.replaceAll("[^(0-9)]", "");
        int paddingInt = new Integer(subStr);
        int finalPadding = paddingInt + 20;
        List<SysFunc> listSysFunc = authService.queryParentByFuncId(new Integer(funcParentId));
        // 类型
        List<SysDict> listDictType = service.getSelectDictInfo("FUNC_TYPE", "NATURE");
        List<SysDict> listDictVisible = service.getSelectDictInfo("WHETHER_TYPE", "WHETHER_CODE");
        List<String> list = new ArrayList<String>();
        for (int i = 0; i < listSysFunc.size(); i++) {
            StringBuffer sb = new StringBuffer();
            for (SysDict sysDictType : listDictType) {

                // FIXME 这里使用了旧实现
                System.err.println("这里使用了错误的实现，需要修改");
//                if (listSysFunc.get(i).getTypeCode().equals(sysDictType.getCode())) {
//                    listSysFunc.get(i).setTypeCode(sysDictType.getValue());
//                }
            }
            for (SysDict sysDcitVisible : listDictVisible) {
                if (listSysFunc.get(i).getIsVisible().equals(Boolean.parseBoolean(sysDcitVisible.getCode()))) {
                    listSysFunc.get(i).setVisible(sysDcitVisible.getValue());
                }
                ;
            }
            // 拼接tr
            sb.append(
                "<tr class=\"odd gradeX\" name=\"#spliceflag" + new Integer(funcParentId) + "#\" id=\"funcTrId\">");
            /*
             * 拼接名称 判断这个名字是否包含子级菜单，如果包含，那么就显示加好标签 true:表示存在下级菜单，显示加号
             * false：表示不存在下级菜单，不显示加号
             */
            if (authService.getChildrenCountByParentId(listSysFunc.get(i).getId())) {
                // 拼接id
                sb.append("<td class=\"width-50px\"></td>");
                sb.append("<td class=\"ng-binding  wid180px\" style=\"padding:0;line-height:39px; padding-left:"
                    + finalPadding + "px\" id=\"funcId" + listSysFunc.get(i).getId() + "\">"
                    + "<span class=\"fa fa-angle-right\" ng-click=\"getChildrenFunc(" + listSysFunc.get(i).getId()
                    + ")\" id=\"funcId{{item.id}}\" style=\"padding:0 10px;\"></span>"
                    + "<span class=\"fa fa-angle-down\" style=\"display:none;padding:10px;\" ng-click=\"hideChildrenFunc("
                    + listSysFunc.get(i).getId() + ")\"></span>" + listSysFunc.get(i).getName() + "</td>");
            } else {
                // 拼接id
                sb.append("<td class=\"width-50px\"><input type=\"checkbox\" name=\"funcCheckChild\" value="
                    + listSysFunc.get(i).getId() + "></td>");
                sb.append("<td class=\"ng-binding wid180px\" style=\"padding:0;line-height:39px;padding-left:"
                    + finalPadding + "px\" id=\"funcId" + listSysFunc.get(i).getId() + "\" >"
                    + "<span  id=\"funcId{{item.id}}\" style=\"padding:10px;\"></span>"
                    + listSysFunc.get(i).getName() + "</td>");
            }
            // 拼接路由
            if (listSysFunc.get(i).getCode() != null) {
                sb.append("<td class=\"ng-binding text-center wid140px hidden-xs hidden-sm \">" + listSysFunc.get(i).getCode() + "</td>");
            } else {
                sb.append("<td class=\"ng-binding text-center wid140px hidden-xs hidden-sm \"></td>");
            }
            // 拼接菜单类型
            sb.append("<td class=\"ng-binding text-center wid140px hidden-xs hidden-sm \">" + listSysFunc.get(i).getType().getCode() + "</td>");
            // 拼接是否可见
            sb.append("<td class=\"ng-binding text-center wid90px hidden-xs hidden-sm \">" + listSysFunc.get(i).getVisible() + "</td>");
            // 拼接上级菜单名称
            sb.append("<td class=\"ng-binding text-center wid140px hidden-xs hidden-sm \">" + listSysFunc.get(i).getParent().getName()
                + "</td>");
            // 拼接操作栏位
            sb.append("<td class=\"text-center\" wid330px>");
            // 1.查看
            sb.append(
                "<button type=\"button\" class=\"btn green btn-xs\" id=\"viewFuncInfoButton\" ng-click=\"findFuncInfo("
                    + listSysFunc.get(i).getId() + ",'details')\">"
                    + "<span class=\"fa fa-search-plus\"></span> 查看</button>");
            // 2.修改
            sb.append(
                "<button type=\"button\" class=\"btn btn-primary btn-xs\" id=\"updateFuncInfoButton\" ng-click=\"updateFuncInfoWin("
                    + listSysFunc.get(i).getId() + ",'edit')\">"
                    + "<span class=\"fa fa-edit\"></span> 修改</button>");
            if (authService.getChildrenCountByParentId(listSysFunc.get(i).getId())) {
                // 3.删除
                sb.append(
                    "<button type=\"button\" ng-show=\"deleteFuncButton\" class=\"btn btn-danger btn-xs\" id=\"deleteFuncInfoButton\" ng-disabled =\"true\" >"
                        + "<span class=\"glyphicon glyphicon-trash\"></span> 删除</button>");
            } else {
                // 4.删除
                sb.append(
                    "<button type=\"button\" ng-show=\"deleteFuncButton\" class=\"btn btn-danger btn-xs\" id=\"deleteFuncInfoButton\" ng-click=\"deleteFuncModelWin("
                        + listSysFunc.get(i).getId() + ")\">"
                        + "<span class=\"glyphicon glyphicon-trash\"></span> 删除</button>");
            }
            // 4.恢复
            sb.append(
                "<button type=\"button\" ng-show=\"recoverFuncButton\" class=\"btn btn-danger btn-xs ng-hide\" id=\"recoverFuncInfoButton\" ng-click=\"deleteFuncModelWin("
                    + listSysFunc.get(i).getId() + ")\">"
                    + "<span class=\"glyphicon glyphicon-trash\"></span>恢复</button>");
            // 5.添加下级菜单
            sb.append(
                "<button type=\"button\" ng-show=\"deleteFuncButton\" class=\"btn btn-primary btn-xs\" id=\"addChilderFuncInfoButton\" ng-click=\"addFuncModelWin("
                    + listSysFunc.get(i).getId() + ")\">"
                    + "<span class=\"glyphicon glyphicon-plus\"></span>添加下级菜单</button>");
            sb.append("</td></tr>");
            list.add(sb.toString());
        }
        workDTO.setResult(list);
    }

    /**
     * 需要只是取第一层数据。
     *
     * @throws IntrospectionException
     */
    public void getFuncTreeParent() throws ServiceException, IntrospectionException {
        List<Map<String, Object>> lm = new ArrayList<Map<String, Object>>();
        SysFunc func = authService.getFuncById(0);
        // 如果存在的话，在获取另一个参数
        String funcId = workDTO.get("funcId");
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("label", func.getName());
        map.put("id", func.getId());
        map.put("controller", "FuncController");
        map.put("method", "getFuncTreeChildren");
        int hideFuncid = 0;
        List<SysFunc> childrenList = authService.queryChildren(0);
        if (funcId != null && !funcId.equals("")) {
            hideFuncid = Integer.valueOf(funcId);
            map.put("hideCommon", hideFuncid);
        }
        //得到所有的子类
        if (childrenList != null && childrenList.size() > 0 && funcId != null && !funcId.equals("")) {
            map.put("type", "folder");
            map.put("children", getFuncTreeById(childrenList, hideFuncid));
        } else if (childrenList != null && childrenList.size() > 0 && (funcId == null || "".equals(funcId))) {
            map.put("type", "folder");
            map.put("children", getFuncTreeById(childrenList));
        } else if (childrenList == null || childrenList.size() <= 0) {
            map.put("type", "doc");
        }
        lm.add(map);
        workDTO.setResult(lm);
    }
    public void getFuncTreeChildren() throws ServiceException {
        String funcId = workDTO.get("commonId");
        int funcid = Integer.valueOf(funcId);
        String hideFuncId = workDTO.get("commonHideId");
        List<Map<String, Object>> lm = new ArrayList<Map<String, Object>>();
        List<SysFunc> childrenList = authService.queryChildren(funcid);
        if (hideFuncId != null && !hideFuncId.equals("")) {
            int hideId = Integer.valueOf(hideFuncId);
            lm = getFuncTreeById(childrenList, hideId);
        } else {
            lm = getFuncTreeById(childrenList);
        }
        workDTO.setResult(lm);
    }
    private List<Map<String, Object>> getFuncTreeById(List<SysFunc> childrenList) throws ServiceException {
        List<Map<String, Object>> lm = new ArrayList<Map<String, Object>>();
        // 得到所有的子类
        if (childrenList != null && childrenList.size() > 0) {
            for (int i = 0; i < childrenList.size(); i++) {
                Map<String, Object> map = new HashMap<String, Object>();
                SysFunc func = childrenList.get(i);
                map.put("label", func.getName());
                map.put("id", func.getId());
                map.put("controller", "FuncController");
                map.put("method", "getFuncTreeChildren");
                if (authService.queryChildren(func.getId()).size() > 0) {
                    map.put("type", "folder");
                    map.put("children", getNullListMap());
                } else {
                    map.put("type", "doc");
                }
                lm.add(map);
            }
        }
        return lm;
    }
    public List<Map<String, Object>> getNullListMap() {
        List<Map<String, Object>> listMap = new ArrayList<Map<String, Object>>();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("label", "");
        map.put("type", "");
        listMap.add(map);
        return listMap;
    }
    /**
     * Description： 过滤掉自己不能选择本身的上级字典
     *
     * @param id
     * @return
     * @throws ServiceException
     * @return String
     * @author name：yangxuan <br>
     *         email: xuan_yang@asdc.com.cn
     */
    private List<Map<String,Object>> getFuncTreeById(List<SysFunc> childrenList, int hideFuncId) throws ServiceException {
        List<Map<String,Object>>  lm = new ArrayList<>();
        // 得到所有的子类
        if (childrenList != null && childrenList.size() > 0) {
            for (int i = 0; i < childrenList.size(); i++) {
                Map<String,Object>  map = new HashMap<>();
                SysFunc func =childrenList.get(i);
                if(func.getId() != hideFuncId){
                    map.put("label", func.getName());
                    map.put("id", func.getId());
                    map.put("hideCommon", hideFuncId);
                    map.put("controller", "FuncController");
                    map.put("method", "getFuncTreeChildren");
                    List<SysFunc> funcList = authService.queryChildren(func.getId());
                    if(funcList.size() > 0){
                        if (funcList.size() == 1 && funcList.get(0).getId() == hideFuncId) {
                            map.put("type", "doc");
                        }else{
                            map.put("type", "folder");
                            map.put("children",getNullListMap());
                        }
                    }else{
                        map.put("type", "doc");
                    }
                    lm.add(map);
                }
            }
        }
        return lm;
    }

    /**
     *
     * Description：通过funcParentId 得到所有的所属的下级的funId
     *
     */
    public void getNeedHideFuncIds() throws NumberFormatException, ServiceException {
        String funcParentId = workDTO.get("funcParentId");

        log.debug("font--->funcParendId:" + funcParentId);

        String fontStr = getFuncIdsByRecursion(new Integer(funcParentId));

        String fontSubstring = fontStr.substring(0, fontStr.length() - 1);

        workDTO.setResult(fontSubstring);
    }

    private StringBuffer funcIds = new StringBuffer();

    public String getFuncIdsByRecursion(int funcParentId) throws ServiceException {

        funcIds.append(funcParentId).append(",");

        List<SysFunc> listFunc = authService.queryChildren(funcParentId);
        for (int i = 0; i < listFunc.size(); i++) {
            if (authService.queryChildren(listFunc.get(i).getId()).size() != 0) {
                getFuncIdsByRecursion(listFunc.get(i).getId());
            }
        }
        return funcIds.toString();
    }

}
