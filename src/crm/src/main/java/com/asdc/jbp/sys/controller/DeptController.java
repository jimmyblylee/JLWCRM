package com.asdc.jbp.sys.controller;

import java.beans.IntrospectionException;
import java.util.*;

import javax.annotation.Resource;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.asdc.jbp.framework.action.helper.ControllerHelper;
import com.asdc.jbp.framework.exception.ServiceException;
import com.asdc.jbp.framework.utils.ProxyStripper;
import com.asdc.jbp.sys.entity.SysDept;
import com.asdc.jbp.sys.entity.SysUser;
import com.asdc.jbp.sys.service.DeptMgtService;
import com.asdc.jbp.sys.service.UserMgtService;
import com.asdc.jbp.sys.token.Token;

/**
 * ClassName: DeptController.java <br>
 * Description: <br>
 * 机构管理的控制层 Create by: name：yangxuan <br>
 * email: xuan_yang@asdc.com.cn <br>
 * Create Time: 2016年5月31日<br>
 */
@SuppressWarnings({"unused", "Duplicates", "WeakerAccess"})
@Controller("DeptController")
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class DeptController extends ControllerHelper {

    @Resource
    private DeptMgtService deptService;
    @Resource
    private UserMgtService userService;

    /**
     * Description：添加部门信息
     *
     * @author name：yangxuan <br>
     * email: xuan_yang@asdc.com.cn
     */

    public void createSysDept() throws ServiceException {
        SysDept dept = workDTO.convertJsonToBeanByKey("dept", SysDept.class);
        deptService.createSysDept(dept);
    }

    /**
     * Description：修改部门信息
     *
     * @author name：yangxuan <br>
     * email: xuan_yang@asdc.com.cn
     */

    public void updateSysDept() throws ServiceException {
        SysDept dept = workDTO.convertJsonToBeanByKey("dept", SysDept.class);
        deptService.updateSysDept(dept);
    }

    /**
     * Description：通过deptid,单个删除部门信息
     *
     * @author name：yangxuan <br>
     * email: xuan_yang@asdc.com.cn
     */
    public void removeSysDept() throws ServiceException {
        String deptIdStrs = diGuiGetDelId(workDTO.getInteger("deptid"), true);
        String[] array = deptIdStrs.substring(0, deptIdStrs.length() - 1).split(",");
        List<Integer> list = new ArrayList<>();
        for (String anArray : array) {
            list.add(Integer.parseInt(anArray));
        }
        deptService.removeSysDept(list);
    }

    /**
     * Description：通过deptid,多个恢复机构状态
     *
     * @author name：yangxuan <br>
     * email: xuan_yang@asdc.com.cn
     */
    public void recoverDeptState() throws ServiceException {
        String deptIdStrs = diGuiGetDelId(workDTO.getInteger("deptid"), false);
        String[] array = deptIdStrs.substring(0, deptIdStrs.length() - 1).split(",");
        List<Integer> list = new ArrayList<>();
        for (String anArray : array) {
            list.add(Integer.parseInt(anArray));
        }
        deptService.recoverDeptState(list);
    }

    /**
     * Description：模糊查询、分页数据查询接口
     *
     * @author name：yangxuan <br>
     * email: xuan_yang@asdc.com.cn
     */
    public void queryDeptBypaging() throws ServiceException, IntrospectionException {
        int start = workDTO.getStart();
        int limit = workDTO.getLimit();
        SysDept dept = workDTO.convertJsonToBeanByKey("pageQuery", SysDept.class);
        List<SysDept> deptList = deptService.queryDeptBypagingAndQuerying(start, limit, dept);
        int total = deptService.getCountBypagingAndQuerying(dept);
        workDTO.setTotle(total);
        workDTO.setResult(ProxyStripper.cleanFromProxies(deptList));
    }

    /**
     * Description：前台第一次得到的树的数据
     *
     * @author name：yangxuan <br>
     * email: xuan_yang@asdc.com.cn
     */
    public void getDeptTreeParent() throws ServiceException {


        //获取当前用户id
        Token token = sessionDTO.get("token");
        int userId = token.getUser().getId();
        SysUser user = userService.getUserById(userId);
        int deptId1 = user.getDept().getId();
        List<Map<String, Object>> lm = new ArrayList<>();
        //默认机构管理
        SysDept dept = deptService.getSysDeptById(deptId1);
        //如果存在的话，在获取另一个参数
        String deptId = workDTO.get("deptId");
        Map<String, Object> map = new HashMap<>();
        map.put("label", dept.getName());
        map.put("id", dept.getId());
        map.put("controller", "DeptController");
        map.put("method", "getDeptTreeChildren");
        int hideDeptid = 0;
        List<SysDept> childrenList = deptService.queryChildren(deptId1);
        if (deptId != null && !deptId.equals("")) {
            map.put("hideCommon", workDTO.getInteger("deptid"));
        }
        // 得到所有的子类
        if (childrenList != null && childrenList.size() > 0 && deptId != null && !deptId.equals("")) {
            map.put("type", "folder");
            map.put("children", getDeptTreeById(childrenList, hideDeptid));
        } else if (childrenList != null && childrenList.size() > 0 && (deptId == null || "".equals(deptId))) {
            map.put("type", "folder");
            map.put("children", getDeptTreeById(childrenList));
        } else if (childrenList == null || childrenList.size() <= 0) {
            map.put("type", "doc");
        }
        lm.add(map);
        workDTO.setResult(lm);
    }

    /**
     * Description：点击树的父节点，请求子节点的数据
     *
     * @author name：yangxuan <br>
     * email: xuan_yang@asdc.com.cn
     */
    public void getDeptTreeChildren() throws ServiceException {
        String hideDeptId = workDTO.get("commonHideId");
        List<Map<String, Object>> lm;
        List<SysDept> childrenList = deptService.queryChildren(workDTO.getInteger("commonId"));
        if (hideDeptId != null && !hideDeptId.equals("")) {
            lm = getDeptTreeById(childrenList, workDTO.getInteger("commonHideId"));
        } else {
            lm = getDeptTreeById(childrenList);
        }
        workDTO.setResult(lm);
    }

    /**
     * Description：过滤掉自己不能选择本身的上级机构
     *
     * @return String
     * @author name：yangxuan <br>email: xuan_yang@asdc.com.cn
     */
    public List<Map<String, Object>> getDeptTreeById(List<SysDept> childrenList, int hideDeptId) throws ServiceException {
        List<Map<String, Object>> lm = new ArrayList<>();
        // 得到所有的子类
        if (childrenList != null && childrenList.size() > 0) {
            for (SysDept aChildrenList : childrenList) {
                Map<String, Object> map = new HashMap<>();
                if (aChildrenList.getId() != hideDeptId) {
                    map.put("label", aChildrenList.getName());
                    map.put("id", aChildrenList.getId());
                    map.put("hideCommon", hideDeptId);
                    map.put("controller", "DeptController");
                    map.put("method", "getDeptTreeChildren");
                    List<SysDept> deptList = deptService.queryChildren(aChildrenList.getId());
                    if (deptList.size() > 0) {
                        if (deptList.size() == 1 && deptList.get(0).getId() == hideDeptId) {
                            map.put("type", "doc");
                        } else {
                            map.put("type", "folder");
                            map.put("children", getNullListMap());
                        }
                    } else {
                        map.put("type", "doc");
                    }
                    lm.add(map);
                }
            }
        }
        return lm;
    }

    /**
     * Description：根据父类id得到直属子类
     *
     * @return String
     * @author name：yangxuan <br>
     * email: xuan_yang@asdc.com.cn
     */
    public List<Map<String, Object>> getDeptTreeById(List<SysDept> childrenList) throws ServiceException {
        List<Map<String, Object>> lm = new ArrayList<>();
        // 得到所有的子类
        if (childrenList != null && childrenList.size() > 0) {
            for (SysDept aChildrenList : childrenList) {
                Map<String, Object> map = new HashMap<>();
                map.put("label", aChildrenList.getName());
                map.put("id", aChildrenList.getId());
                map.put("controller", "DeptController");
                map.put("method", "getDeptTreeChildren");
                if (deptService.queryChildren(aChildrenList.getId()).size() > 0) {
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

    @SuppressWarnings("Duplicates")
    public List<Map<String, Object>> getNullListMap() {
        List<Map<String, Object>> listMap = new ArrayList<>();
        Map<String, Object> map = new HashMap<>();
        map.put("label", "");
        map.put("type", "");
        listMap.add(map);
        return listMap;
    }

    /**
     * Description：通过deptId,得到所有子类的id数据。
     *
     * @author name：yangxuan <br>
     * email: xuan_yang@asdc.com.cn
     */
    private StringBuffer sb = new StringBuffer();

    public String diGuiGetDelId(int id, Boolean flag) throws ServiceException {
        sb.append(id).append(",");
        List<SysDept> listDept;
        if (flag) {
            listDept = deptService.queryChildren(id);
        } else {
            listDept = deptService.queryChildrenRecover(id);
        }
        if (listDept.size() > 0) {
            for (SysDept aListDept : listDept) {
                diGuiGetDelId(aListDept.getId(), flag);
            }
        }
        return sb.toString();
    }

    public void setDeptService(DeptMgtService deptService) {
        this.deptService = deptService;
    }

    /**
     * Description：上移位置改变排序位置方法
     *
     * @author name：yangxuan <br>email: xuan_yang@asdc.com.cn
     */
    public void moveUpSort() throws ServiceException {
        //获取其上级的排序值
        int sort = workDTO.getInteger("maxSort");

        //1.修改自身排序
        SysDept dept = deptService.getSysDeptById(workDTO.getInteger("deptId"));
        dept.setSort(sort);
        deptService.updateSysDept(dept);
        //2.修改上级排序
        SysDept deptUp = deptService.getSysDeptById(workDTO.getInteger("upDeptid"));
        deptUp.setSort(sort + 1);
        deptService.updateSysDept(deptUp);
    }

    //下移controller
    public void moveDownSort() throws ServiceException {
        int sort = workDTO.getInteger("minSort");
        //1.修改自身排序
        SysDept dept = deptService.getSysDeptById(workDTO.getInteger("deptid"));
        dept.setSort(sort);
        deptService.updateSysDept(dept);
        //2.修改下级排序
        SysDept deptUp = deptService.getSysDeptById(workDTO.getInteger("downDeptid"));
        deptUp.setSort(sort - 1);
        deptService.updateSysDept(deptUp);
    }

    //置顶controlller
    public void moveTopSort() throws ServiceException {
        String tmpIds = workDTO.get("tmpIds");
        //1.修改自身排序
        SysDept dept = deptService.getSysDeptById(workDTO.getInteger("deptid"));
        dept.setSort(workDTO.getInteger("minSort"));
        deptService.updateSysDept(dept);
        //2.修改其他排序
        String[] array = tmpIds.substring(1, tmpIds.length() - 1).split(",");
        for (String anArray : array) {
            SysDept deptOhter = deptService.getSysDeptById(Integer.valueOf(anArray));
            deptOhter.setSort(deptOhter.getSort() + 1);
            deptService.updateSysDept(deptOhter);
        }
    }

    //置底controller
    public void moveLowSort() throws ServiceException {
        String tmpIds = workDTO.get("tmpIds");
        //1.修改自身排序
        SysDept dept = deptService.getSysDeptById(workDTO.getInteger("deptid"));
        dept.setSort(workDTO.getInteger("maxSort"));
        deptService.updateSysDept(dept);
        //2.修改其他排序
        String[] array = tmpIds.substring(1, tmpIds.length() - 1).split(",");
        for (String anArray : array) {
            SysDept deptOhter = deptService.getSysDeptById(Integer.valueOf(anArray));
            deptOhter.setSort(deptOhter.getSort() - 1);
            deptService.updateSysDept(deptOhter);
        }
    }

    public void getDept() throws ServiceException {
        SysDept sys = new SysDept();
        sys.setId(1);
        sys.setIsEnabled(true);
        SysDept sys1 = deptService.queryDeptById(sys);
        workDTO.setResult(sys1);
    }

    //通过deptId来判断是否存在下级机构
    public void getChildDept() throws ServiceException {
        List<SysDept> deptList = deptService.queryChildren(workDTO.getInteger("deptid"));
        if (deptList != null && deptList.size() > 0) {
            workDTO.setResult(true);
        } else {
            workDTO.setResult(false);
        }
    }

    /**
     * Description：查找最上级机构
     *
     * @author name：yangxuan <br>email: xuan_yang@asdc.com.cn
     */
    public void queryParentDeptIsNull() throws IntrospectionException {
        SysDept sysDept = deptService.queryParentDeptIsNull();
        workDTO.setResult(ProxyStripper.cleanFromProxies(sysDept));
    }

    /**
     * Description：通过机构id判断机构是否可用
     *
     * @author name：yangxuan <br>email: xuan_yang@asdc.com.cn
     */
    public void queryDeptByDeptIdNoisEnabled() throws IntrospectionException, ServiceException {
        boolean flag = deptService.queryDeptByDeptIdNoisEnabled(workDTO.getInteger("deptid"));
        workDTO.setResult(flag);
    }


    public void getOneDeptUserTree() throws ServiceException {
        Token token = sessionDTO.get("token");
        int userId = token.getUser().getId();
        SysUser user = userService.getUserById(userId);
        int deptId1 = user.getDept().getId();
        SysDept dept = deptService.getSysDeptById(deptId1);
        List<Map<String, Object>> lm = new ArrayList<>();
        Map<String, Object> map = new HashMap<>();
        map.put("label", dept.getName());
        map.put("id", dept.getId());
        map.put("checkbox_false", Boolean.TRUE);
        map.put("user_dept_falg", "dept");
        List<SysDept> deptList = deptService.queryChildren(dept.getId());
        if (deptList != null && deptList.size() > 0) {
            map.put("type", "folder");
            map.put("children", getDeptUserByDiGui(deptList, dept, userId));
        } else {
            map.put("type", "doc");
        }
        lm.add(map);
        workDTO.setResult(lm);
    }

    public List<Map<String, Object>> getDeptUserByDiGui(List<SysDept> deptList, SysDept dept, int userId) throws ServiceException {
        if (deptList != null && deptList.size() > 0)
            return getDeptTree(deptList, userId);
        List<SysUser> userList = deptService.getUserListByDeptId(dept.getId());
        if (userList != null && userList.size() > 0) {
            return getUserTree(userList, userId);
        }
        //noinspection unchecked
        return Collections.EMPTY_LIST;
    }

    public List<Map<String, Object>> getDeptTree(List<SysDept> childrenList, int userid) throws ServiceException {
        List<Map<String, Object>> lm = new ArrayList<>();
        if (childrenList != null && childrenList.size() > 0) {
            for (SysDept aChildrenList : childrenList) {
                Map<String, Object> map = new HashMap<>();
                map.put("label", aChildrenList.getName());
                map.put("id", aChildrenList.getId());
                map.put("hideCommon", userid);
                map.put("checkbox_false", Boolean.TRUE);
                map.put("type", "folder");
                map.put("user_dept_falg", "dept");
                List<SysDept> deptList = deptService.queryChildren(aChildrenList.getId());
                map.put("children", getDeptUserByDiGui(deptList, aChildrenList, userid));
                lm.add(map);
            }

        }
        return lm;
    }

    private List<Map<String, Object>> getUserTree(List<SysUser> userList, int hideUserid) {
        List<Map<String, Object>> lm = new ArrayList<>();
        if (userList != null && userList.size() > 0) {
            for (SysUser anUserList : userList) {
                Map<String, Object> map = new HashMap<>();
                if (anUserList.getId() != hideUserid) {
                    map.put("label", anUserList.getName());
                    map.put("id", anUserList.getId());
                    map.put("checkbox_false", Boolean.TRUE);
                    map.put("user_dept_falg", "user");
                    lm.add(map);
                }
            }

        }
        return lm;
    }

    public void getTwoDeptUserTree() throws ServiceException {
        int deptid = workDTO.getInteger("commonId");
        int userid = workDTO.getInteger("commonHideId");
        List<SysDept> childrenList = deptService.queryChildren(deptid);
        List<SysUser> userList = deptService.getUserListByDeptId(deptid);
        List<Map<String, Object>> deptLm = new ArrayList<>();
        if (childrenList != null && childrenList.size() > 0)
            deptLm = getDeptTree(childrenList, userid);
        if (userList != null && userList.size() > 0) {
            deptLm.addAll(getUserTree(userList, userid));
        }
        workDTO.setResult(deptLm);
    }


}