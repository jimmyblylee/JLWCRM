/**
 * Project Name : jbp-features-sys <br>
 * File Name : AuthorityServiceImpl.java <br>
 * Package Name : com.asdc.jbp.sys.service <br>
 * Create Time : May 1, 2016 <br>
 * Create by : xiangyu_li@asdc.com.cn <br>
 * Copyright © 2006, 2016, ASDC DAI. All rights reserved.
 */
package com.asdc.jbp.sys.service.impl;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import javax.annotation.Resource;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.asdc.jbp.framework.dao.JpaOrmOperator;
import com.asdc.jbp.framework.dao.Parameter;
import com.asdc.jbp.framework.exception.ErrLevel;
import com.asdc.jbp.framework.exception.ServiceException;
import com.asdc.jbp.framework.message.Messages;
import com.asdc.jbp.framework.utils.BeanUtils;
import com.asdc.jbp.framework.utils.CollectionUtils;
import com.asdc.jbp.framework.utils.StringUtils;
import com.asdc.jbp.sys.entity.RelSysGroupRole;
import com.asdc.jbp.sys.entity.RelSysRoleFunc;
import com.asdc.jbp.sys.entity.RelSysUserGroup;
import com.asdc.jbp.sys.entity.RelSysUserRole;
import com.asdc.jbp.sys.entity.SysFunc;
import com.asdc.jbp.sys.entity.SysGlobalVariable;
import com.asdc.jbp.sys.entity.SysGroup;
import com.asdc.jbp.sys.entity.SysRole;
import com.asdc.jbp.sys.entity.SysUser;
import com.asdc.jbp.sys.entity.stuff.FuncType;
import com.asdc.jbp.sys.service.AuthenticationService;
import com.asdc.jbp.sys.service.AuthorityMgtService;
import com.asdc.jbp.sys.service.AuthorizationService;
import com.asdc.jbp.sys.token.Token;

/**
 * ClassName : AuthorityServiceImpl <br>
 * Description : 实现 of {@link AuthorityMgtService}, {@link AuthenticationService} and {@link AuthorizationService} <br>
 * Create Time : May 1, 2016 <br>
 * Create by : xiangyu_li@asdc.com.cn <br>
 *
 */
@Service
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Transactional(readOnly = true)
public class AuthorityServiceImpl implements AuthorityMgtService, AuthenticationService {

	@Resource(name = "SysCommonDao")
	private JpaOrmOperator dao;

	/**
	 * Description : 根据ID 获取 SysGroup, SysRole, SysFunc <br>
	 * Create Time: May 1, 2016 <br>
	 * Create by : xiangyu_li@asdc.com.cn <br>
	 *
	 * @param type
	 *            SysGroup, SysRole, SysFunc
	 * @param id
	 * @return SysGroup, SysRole, SysFunc
	 * @throws ServiceException
	 *             <li>ERR_SYS_0X3: ID为空</li>
	 *             <li>ERR_SYS_0X3: 无法根据ID找到该类</li>
	 */
	private <T> T getEntityById(Class<T> type, Integer id) throws ServiceException {
		String errorCode = SysGroup.class.equals(type) ? "ERR_SYS_023" : SysRole.class.equals(type) ? "ERR_SYS_033" : "ERR_SYS_043";
		if (id == null) {
			throw new ServiceException(errorCode, Messages.getMsg("sys", "ERR_SYS_MSG_AUTHORITY_GET_ID_NULL"));
		}
		T userInDB = dao.find(type, id);
		if (userInDB == null) {
			throw new ServiceException(errorCode, Messages.getMsg("sys", "ERR_SYS_MSG_AUTHORITY_GET_NOT_FOUND"), id);
		}
		return userInDB;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.asdc.jbp.sys.service.AuthorizationService#getUserTokenByUserId(java.lang.Integer)
	 */
	@Override
	public Token getUserTokenByUserId(Integer userId) throws ServiceException {
		SysUser user = getSysUserById(userId);
		List<SysFunc> funcs = queryFuncsByUserId(userId);
		Token token = new Token();
		token.setUser(TokenHelper.convert(user));
		token.setDept(token.getUser().getDept());
		token.setFunc(TokenHelper.convert(funcs));
		return token;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.asdc.jbp.sys.service.AuthenticationService#checkAccountAndPwd(java.lang.String, java.lang.String)
	 */
	@Override
	public Integer checkAccountAndPwd(String account, String password) throws ServiceException {
		if (account == null || password == null) {
			throw new ServiceException("ERR_SYS_001", ErrLevel.WARN, Messages.getMsg("sys", "ERR_SYS_MSG_USER_NULL"));
		}
		try {
			return ((Number) dao.getSingleResultByNamedQuery("sys.hql.getUserIdByAccountAndPwd",
			        Parameter.toList("account", account, "password", StringUtils.encryptByMD5(password)))).intValue();
		} catch (Exception e) {
			throw new ServiceException("ERR_SYS_001", ErrLevel.WARN, Messages.getMsg("sys", "ERR_SYS_MSG_USER_NULL"));
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.asdc.jbp.sys.service.AuthorityMgtService#getGroupById(java.lang.Integer)
	 */
	@Override
	public SysGroup getGroupById(Integer groupId) throws ServiceException {
		return getEntityById(SysGroup.class, groupId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.asdc.jbp.sys.service.AuthorityMgtService#createGroup(com.asdc.jbp.sys.entity.SysGroup)
	 */
	@Override
	@Transactional(readOnly = false)
	public SysGroup createGroup(SysGroup group) throws ServiceException {
		// validate
		if (group == null) {
			throw new ServiceException("ERR_SYS_021", Messages.getMsg("sys", "ERR_SYS_MSG_GROUP_CREATE_ENTITY_NULL"));
		}
		if (group.getIsEnabled() == null) {
			group.setIsEnabled(false);
		}
		dao.persist(group);
		return group;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.asdc.jbp.sys.service.AuthorityMgtService#updateGroup(com.asdc.jbp.sys.entity.SysGroup)
	 */
	@Override
	@Transactional(readOnly = false)
	public SysGroup updateGroup(SysGroup group) throws ServiceException {
		// validate
		if (group == null) {
			throw new ServiceException("ERR_SYS_022", Messages.getMsg("sys", "ERR_SYS_MSG_GROUP_CREATE_ENTITY_NULL"));
		}
		SysGroup groupInDB = getEntityById(SysGroup.class, group.getId());

		// fill default values
		BeanUtils.copyProperties(group, groupInDB);
		return groupInDB;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.asdc.jbp.sys.service.AuthorityMgtService#removeGroupById(java.lang.Integer)
	 */
	@Override
	@Transactional(readOnly = false)
	public void removeGroupById(Integer groupId) throws ServiceException {
		getGroupById(groupId).setIsEnabled(false);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.asdc.jbp.sys.service.AuthorityMgtService#removeGroupByIds(java.util.List)
	 */
	@Override
	@Transactional(readOnly = false)
	public void removeGroupByIds(List<Integer> groupIds) throws ServiceException {
		if (groupIds == null) {
			throw new ServiceException("ERR_SYS_022", Messages.getMsg("sys", "ERR_SYS_MSG_DEPT_ID_NULL"));
		}
		for (Integer id : groupIds) {
			removeGroupById(id);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.asdc.jbp.sys.service.AuthorityMgtService#queryRolesByGroupId(java.lang.Integer)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<SysRole> queryRolesByGroupId(Integer groupId) throws ServiceException {
		// just validate the data
		getGroupById(groupId);
		// query it
		return (List<SysRole>) dao.queryByNamedQuery("sys.hql.queryRolesByGroupId", Parameter.toList("groupId", groupId));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.asdc.jbp.sys.service.AuthorityMgtService#replaceRolesToGroup(java.lang.Integer, java.util.List)
	 */
	@Override
	@Transactional(readOnly = false)
	public void replaceRolesToGroup(Integer groupId, List<Integer> roleIds) throws ServiceException {
		// just validate it
		getGroupById(groupId);
		for (Integer roleId : roleIds) {
			getRoleById(roleId);
		}
		// delete the old data
		dao.executeByNamedQuery("sys.hql.removeRelOfGroupRoleByGroupId", Parameter.toList("groupId", groupId));
		// insert the new data
		for (Integer roleId : roleIds) {
			dao.persist(new RelSysGroupRole(groupId, roleId));
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.asdc.jbp.sys.service.AuthorityMgtService#assignRolesToGroup(java.lang.Integer, java.util.List)
	 */
	@Override
	@Transactional(readOnly = false)
	public void assignRolesToGroup(Integer groupId, List<Integer> roleIds) throws ServiceException {
		// just validate it
		getGroupById(groupId);
		for (Integer roleId : roleIds) {
			getRoleById(roleId);
		}

		for (Integer roleId : roleIds) {
			dao.executeByNamedQuery("sys.hql.removeRelOfGroupRoleByGroupIdAndRoleId", Parameter.toList("groupId", groupId, "roleId", roleId));
			dao.persist(new RelSysGroupRole(groupId, roleId));
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.asdc.jbp.sys.service.AuthorityMgtService#removeRolesFromGroup(java.lang.Integer, java.util.List)
	 */
	@Override
	@Transactional(readOnly = false)
	public void removeRolesFromGroup(Integer groupId, List<Integer> roleIds) throws ServiceException {
		// just validate it
		getGroupById(groupId);
		for (Integer roleId : roleIds) {
			getRoleById(roleId);
		}

		for (Integer roleId : roleIds) {
			Object rel;
			try {
				rel = dao.getSingleResultByNamedQuery("sys.hql.getRelOfGroupRoleByGroupIdAndRoleId", Parameter.toList("groupId", groupId, "roleId", roleId));
			} catch (NoResultException e) {
				throw new ServiceException("ERR_SYS_051", Messages.getMsg("sys", "ERR_SYS_MSG_REL_ROLE_GROUP_NULL"));
			} catch (NonUniqueResultException e) {
				throw new ServiceException("ERR_SYS_051", Messages.getMsg("sys", "ERR_SYS_MSG_REL_ROLE_GROUP_NO_UNIQUE"));
			}
			dao.remove(rel);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.asdc.jbp.sys.service.AuthorityMgtService#queryUsersByGroupId(java.lang.Integer)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<SysUser> queryUsersByGroupId(Integer groupId) throws ServiceException {
		// validate it
		getGroupById(groupId);
		return (List<SysUser>) dao.queryByNamedQuery("sys.hql.queryUsersByGroupId", Parameter.toList("groupId", groupId));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.asdc.jbp.sys.service.AuthorityMgtService#replaceUsersToGroup(java.lang.Integer, java.util.List)
	 */
	@Override
	@Transactional(readOnly = false)
	public void replaceUsersToGroup(Integer groupId, List<Integer> userIds) throws ServiceException {
		// just validate it
		getGroupById(groupId);
		for (Integer userId : userIds) {
			getSysUserById(userId);
		}
		// delete the old data
		dao.executeByNamedQuery("sys.hql.removeRelOfUserGroupByGroupId", Parameter.toList("groupId", groupId));
		// insert the new data
		for (Integer userId : userIds) {
			dao.persist(new RelSysUserGroup(userId, groupId));
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.asdc.jbp.sys.service.AuthorityMgtService#assignUsersToGroup(java.lang.Integer, java.util.List)
	 */
	@Override
	@Transactional(readOnly = false)
	public void assignUsersToGroup(Integer groupId, List<Integer> userIds) throws ServiceException {
		// just validate it
		getGroupById(groupId);
		for (Integer userId : userIds) {
			getSysUserById(userId);
		}

		for (Integer userId : userIds) {
			dao.executeByNamedQuery("sys.hql.removeRelOfUserGroupByGroupIdAndUserId", Parameter.toList("groupId", groupId, "userId", userId));
			dao.persist(new RelSysUserGroup(userId, groupId));
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.asdc.jbp.sys.service.AuthorityMgtService#removeUsersFromGroup(java.lang.Integer, java.util.List)
	 */
	@Override
	@Transactional(readOnly = false)
	public void removeUsersFromGroup(Integer groupId, List<Integer> userIds) throws ServiceException {
		// just validate it
		getGroupById(groupId);
		for (Integer userId : userIds) {
			getSysUserById(userId);
		}

		for (Integer userId : userIds) {
			Object rel;
			try {
				rel = dao.getSingleResultByNamedQuery("sys.hql.getRelOfUserGroupByGroupIdAndUserId", Parameter.toList("groupId", groupId, "userId", userId));
			} catch (NoResultException e) {
				throw new ServiceException("ERR_SYS_054", Messages.getMsg("sys", "ERR_SYS_MSG_REL_USER_GROUP_NULL"));
			} catch (NonUniqueResultException e) {
				throw new ServiceException("ERR_SYS_054", Messages.getMsg("sys", "ERR_SYS_MSG_REL_USER_GROUP_NO_UNIQUE"));
			}
			dao.remove(rel);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.asdc.jbp.sys.service.AuthorityMgtService#queryFuncsByGroupId(java.lang.Integer)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<SysFunc> queryFuncsByGroupId(Integer groupId) throws ServiceException {
		// validate it
		getGroupById(groupId);
		return (List<SysFunc>) dao.queryByNamedQuery("sys.hql.queryFuncsByGroupId", Parameter.toList("groupId", groupId));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.asdc.jbp.sys.service.AuthorityMgtService#queryGroupsByRoleId(java.lang.Integer)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<SysGroup> queryGroupsByRoleId(Integer roleId) throws ServiceException {
		// validate it
		getRoleById(roleId);
		return (List<SysGroup>) dao.queryByNamedQuery("sys.hql.queryGroupsByRoleId", Parameter.toList("roleId", roleId));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.asdc.jbp.sys.service.AuthorityMgtService#getRoleById(java.lang.Integer)
	 */
	@Override
	public SysRole getRoleById(Integer roleId) throws ServiceException {
		return getEntityById(SysRole.class, roleId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.asdc.jbp.sys.service.AuthorityMgtService#createRole(com.asdc.jbp.sys.entity.SysRole)
	 */
	@Override
	@Transactional(readOnly = false)
	public SysRole createRole(SysRole role) throws ServiceException {
		// validate
		if (role == null) {
			throw new ServiceException("ERR_SYS_031", Messages.getMsg("sys", "ERR_SYS_MSG_ROLE_CREATE_ENTITY_NULL"));
		}
		if (role.getIsEnabled() == null){
			role.setIsEnabled(false);
		}
		//role.setIsBaseRole(false);
		dao.persist(role);
		return role;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.asdc.jbp.sys.service.AuthorityMgtService#updateRole(com.asdc.jbp.sys.entity.SysRole)
	 */
	@Override
	@Transactional(readOnly = false)
	public SysRole updateRole(SysRole role) throws ServiceException {
		// validate
		if (role == null) {
			throw new ServiceException("ERR_SYS_032", Messages.getMsg("sys", "ERR_SYS_MSG_ROLE_CREATE_ENTITY_NULL"));
		}
		SysRole roleInDB = getEntityById(SysRole.class, role.getId());

		// fill default values
		BeanUtils.copyProperties(role, roleInDB);
		return roleInDB;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.asdc.jbp.sys.service.AuthorityMgtService#removeRoleById(java.lang.Integer)
	 */
	@Override
	@Transactional(readOnly = false)
	public void removeRoleById(Integer roleId) throws ServiceException {
		getRoleById(roleId).setIsEnabled(false);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.asdc.jbp.sys.service.AuthorityMgtService#removeRoleByIds(java.util.List)
	 */
	@Override
	@Transactional(readOnly = false)
	public void removeRoleByIds(List<Integer> roleIds) throws ServiceException {
		if (roleIds == null) {
			throw new ServiceException("ERR_SYS_032", Messages.getMsg("sys", "ERR_SYS_MSG_DEPT_ID_NULL"));
		}
		for (Integer id : roleIds) {
			removeRoleById(id);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.asdc.jbp.sys.service.AuthorityMgtService#queryUsersByRoleId(java.lang.Integer)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<SysUser> queryUsersByRoleId(Integer roleId) throws ServiceException {
		// validate it
		getRoleById(roleId);
		return (List<SysUser>) dao.queryByNamedQuery("sys.hql.queryUsersByRoleId", Parameter.toList("roleId", roleId));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.asdc.jbp.sys.service.AuthorityMgtService#queryFuncsByRoleId(java.lang.Integer)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<SysFunc> queryFuncsByRoleId(Integer roleId) throws ServiceException {
		// validate it
		getRoleById(roleId);
		return (List<SysFunc>) dao.queryByNamedQuery("sys.hql.queryFuncsByRoleId", Parameter.toList("roleId", roleId));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.asdc.jbp.sys.service.AuthorityMgtService#replaceFuncsToRole(java.lang.Integer, java.util.List)
	 */
	@Override
	@Transactional(readOnly = false)
	public void replaceFuncsToRole(Integer roleId, List<Integer> funcIds) throws ServiceException {
		// just validate it
		getRoleById(roleId);
		for (Integer funcId : funcIds) {
			getFuncById(funcId);
		}
		// delete the old data
		dao.executeByNamedQuery("sys.hql.removeRelOfRoleFuncByRoleId", Parameter.toList("roleId", roleId));
		// insert the new data
		for (Integer funcId : funcIds) {
			dao.persist(new RelSysRoleFunc(roleId, funcId));
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.asdc.jbp.sys.service.AuthorityMgtService#assignFuncsToRole(java.lang.Integer, java.util.List)
	 */
	@Override
	@Transactional(readOnly = false)
	public void assignFuncsToRole(Integer roleId, List<Integer> funcIds) throws ServiceException {
		// just validate it
		getRoleById(roleId);
		for (Integer funcId : funcIds) {
			getFuncById(funcId);
		}

		for (Integer funcId : funcIds) {
			dao.executeByNamedQuery("sys.hql.removeRelOfRoleFuncByRoleIdAndFuncId", Parameter.toList("funcId", funcId, "roleId", roleId));
			dao.persist(new RelSysRoleFunc(roleId, funcId));
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.asdc.jbp.sys.service.AuthorityMgtService#removeFuncsFromRole(java.lang.Integer, java.util.List)
	 */
	@Override
	@Transactional(readOnly = false)
	public void removeFuncsFromRole(Integer roleId, List<Integer> funcIds) throws ServiceException {
		// just validate it
		getRoleById(roleId);
		for (Integer funcId : funcIds) {
			getFuncById(funcId);
		}

		for (Integer funcId : funcIds) {
			Object rel;
			try {
				rel = dao.getSingleResultByNamedQuery("sys.hql.getRelOfRoleFuncByRoleIdAndFuncId", Parameter.toList("funcId", funcId, "roleId", roleId));
			} catch (NoResultException e) {
				throw new ServiceException("ERR_SYS_052", Messages.getMsg("sys", "ERR_SYS_MSG_REL_ROLE_FUNC_NULL"));
			} catch (NonUniqueResultException e) {
				throw new ServiceException("ERR_SYS_052", Messages.getMsg("sys", "ERR_SYS_MSG_REL_ROLE_FUNC_NO_UNIQUE"));
			}
			dao.remove(rel);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.asdc.jbp.sys.service.AuthorityMgtService#queryGroupsByUserId(java.lang.Integer)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<SysGroup> queryGroupsByUserId(Integer userId) throws ServiceException {
		// validate it
		getSysUserById(userId);
		return (List<SysGroup>) dao.queryByNamedQuery("sys.hql.queryGroupsByUserId", Parameter.toList("userId", userId));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.asdc.jbp.sys.service.AuthorityMgtService#queryRolesByUserId(java.lang.Integer)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<SysRole> queryRolesByUserId(Integer userId) throws ServiceException {
		// validate it
		getSysUserById(userId);

		Comparator<SysRole> comparator = new Comparator<SysRole>() {
			@Override
			public int compare(SysRole o1, SysRole o2) {
				return o1 != null && o2 != null && o1.getId().equals(o2.getId()) ? 0 : -1;
			}
		};

		List<SysRole> result = new LinkedList<>();
		List<SysRole> directRoles = (List<SysRole>) dao.queryByNamedQuery("sys.hql.queryDirectRolesByUserId", Parameter.toList("userId", userId));
		CollectionUtils.addAllByUnique(result, directRoles, comparator);
		
		for (SysGroup group : queryGroupsByUserId(userId)) {
			CollectionUtils.addAllByUnique(result, queryRolesByGroupId(group.getId()), comparator);
		}
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.asdc.jbp.sys.service.AuthorityMgtService#replaceRolesToUser(java.lang.Integer, java.util.List)
	 */
	@Override
	@Transactional(readOnly = false)
	public void replaceRolesToUser(Integer userId, List<Integer> roleIds) throws ServiceException {
		// just validate it
		getSysUserById(userId);
		for (Integer roleId : roleIds) {
			getRoleById(roleId);
		}
		// delete the old data
		dao.executeByNamedQuery("sys.hql.removeRelOfUserRoleByUserId", Parameter.toList("userId", userId));
		// insert the new data
		for (Integer roleId : roleIds) {
			dao.persist(new RelSysUserRole(userId, roleId));
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.asdc.jbp.sys.service.AuthorityMgtService#assignRolesToUser(java.lang.Integer, java.util.List)
	 */
	@Override
	@Transactional(readOnly = false)
	public void assignRolesToUser(Integer userId, List<Integer> roleIds) throws ServiceException {
		// just validate it
		getSysUserById(userId);
		for (Integer roleId : roleIds) {
			getRoleById(roleId);
		}

		for (Integer roleId : roleIds) {
			dao.executeByNamedQuery("sys.hql.removeRelOfUserRoleByRoleIdAndUserId", Parameter.toList("userId", userId, "roleId", roleId));
			dao.persist(new RelSysUserRole(userId, roleId));
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.asdc.jbp.sys.service.AuthorityMgtService#removeRolesFromUser(java.lang.Integer, java.util.List)
	 */
	@Override
	@Transactional(readOnly = false)
	public void removeRolesFromUser(Integer userId, List<Integer> roleIds) throws ServiceException {
		// just validate it
		getSysUserById(userId);
		for (Integer roleId : roleIds) {
			getRoleById(roleId);
		}

		for (Integer roleId : roleIds) {
			Object rel;
			try {
				rel = dao.getSingleResultByNamedQuery("sys.hql.getRelOfUserRoleByRoleIdAndUserId", Parameter.toList("userId", userId, "roleId", roleId));
			} catch (NoResultException e) {
				throw new ServiceException("ERR_SYS_053", Messages.getMsg("sys", "ERR_SYS_MSG_REL_ROLE_USER_NULL"));
			} catch (NonUniqueResultException e) {
				throw new ServiceException("ERR_SYS_053", Messages.getMsg("sys", "ERR_SYS_MSG_REL_ROLE_USER_NO_UNIQUE"));
			}
			dao.remove(rel);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.asdc.jbp.sys.service.AuthorityMgtService#queryFuncsByUserId(java.lang.Integer)
	 */
	@Override
	public List<SysFunc> queryFuncsByUserId(Integer userId) throws ServiceException {
		// validate it
		getSysUserById(userId);
        //查询角色通过userId
		List<SysFunc> result = new LinkedList<>();
		for (SysRole role : queryRolesByUserId(userId)) {
			CollectionUtils.addAllByUnique(result, queryFuncsByRoleId(role.getId()), new Comparator<SysFunc>() {
				@Override
				public int compare(SysFunc o1, SysFunc o2) {
					return o1 != null && o2 != null && o1.getId().equals(o2.getId()) ? 0 : -1;
				}
			});
		}
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.asdc.jbp.sys.service.AuthorityMgtService#queryGroupsByFuncId(java.lang.Integer)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<SysGroup> queryGroupsByFuncId(Integer funcId) throws ServiceException {
		// validate it
		getFuncById(funcId);
		return (List<SysGroup>) dao.queryByNamedQuery("sys.hql.queryGroupsByFuncId", Parameter.toList("funcId", funcId));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.asdc.jbp.sys.service.AuthorityMgtService#queryRolesByFuncId(java.lang.Integer)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<SysRole> queryRolesByFuncId(Integer funcId) throws ServiceException {
		// validate it
		getFuncById(funcId);
		return (List<SysRole>) dao.queryByNamedQuery("sys.hql.queryRolesByFuncId", Parameter.toList("funcId", funcId));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.asdc.jbp.sys.service.AuthorityMgtService#queryUsersByFuncId(java.lang.Integer)
	 */
	@Override
	public List<SysUser> queryUsersByFuncId(Integer funcId) throws ServiceException {
		// validate it
		getFuncById(funcId);

		Comparator<SysUser> comparator = new Comparator<SysUser>() {
			@Override
			public int compare(SysUser o1, SysUser o2) {
				return o1 != null && o2 != null && o1.getId().equals(o2.getId()) ? 0 : -1;
			}
		};
		List<SysUser> result = new LinkedList<>();
		for (SysRole role : queryRolesByFuncId(funcId)) {
			CollectionUtils.addAllByUnique(result, queryUsersByRoleId(role.getId()), comparator);
		}
		for (SysGroup group : queryGroupsByFuncId(funcId)) {
			CollectionUtils.addAllByUnique(result, queryUsersByGroupId(group.getId()), comparator);
		}
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.asdc.jbp.sys.service.AuthorityMgtService#getFuncById(java.lang.Integer)
	 */
	@Override
	public SysFunc getFuncById(Integer funcId) throws ServiceException {
		SysFunc userInDB = dao.find(SysFunc.class, funcId);
		if (userInDB == null) {
			throw new ServiceException("ERR_SYS_043", Messages.getMsg("sys", "ERR_SYS_MSG_AUTHORITY_GET_NOT_FOUND"), funcId);
		}
		return userInDB;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.asdc.jbp.sys.service.AuthorityMgtService#createFunc(com.asdc.jbp.sys.entity.SysFunc)
	 */
	@Override
	@Transactional(readOnly = false)
	public SysFunc createFunc(SysFunc func) throws ServiceException {
		// validate
		if (func == null) {
			throw new ServiceException("ERR_SYS_041", Messages.getMsg("sys", "ERR_SYS_MSG_FUNC_CREATE_ENTITY_NULL"));
		}
		if (func.getName() == null) {
			throw new ServiceException("ERR_SYS_041", ErrLevel.WARN, Messages.getMsg("sys", "ERR_SYS_MSG_FUNC_CREATE_NAME_NULL"));
		}
		if (func.getTypeCode() == null) {
			throw new ServiceException("ERR_SYS_041", ErrLevel.WARN, Messages.getMsg("sys", "ERR_SYS_MSG_FUNC_CREATE_TYPE_NULL"));
		}
		if (FuncType.LINK.toString().equals(func.getTypeCode()) && func.getUrl() == null) {
			throw new ServiceException("ERR_SYS_041", ErrLevel.WARN, Messages.getMsg("sys", "ERR_SYS_MSG_FUNC_CREATE_LINK_NULL"));
		}
		if (func.getIsVisible() == null) {
			func.setIsVisible(true);
		}
		func.setIsBaseFunc(false);
		if (func.getIsEnabled() == null) {
			func.setIsEnabled(false);
		}
		dao.persist(func);
		return func;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.asdc.jbp.sys.service.AuthorityMgtService#updateFunc(com.asdc.jbp.sys.entity.SysFunc)
	 */
	@Override
	@Transactional(readOnly = false)
	public SysFunc updateFunc(SysFunc func) throws ServiceException {
		// validate
		if (func == null) {
			throw new ServiceException("ERR_SYS_042", Messages.getMsg("sys", "ERR_SYS_MSG_FUNC_CREATE_ENTITY_NULL"));
		}
		if (func.getName() == null) {
			throw new ServiceException("ERR_SYS_042", ErrLevel.WARN, Messages.getMsg("sys", "ERR_SYS_MSG_FUNC_CREATE_NAME_NULL"));
		}
		if (func.getTypeCode() == null) {
			throw new ServiceException("ERR_SYS_042", ErrLevel.WARN, Messages.getMsg("sys", "ERR_SYS_MSG_FUNC_CREATE_TYPE_NULL"));
		}
		if (FuncType.LINK.toString().equals(func.getTypeCode()) && func.getUrl() == null) {
			throw new ServiceException("ERR_SYS_042", ErrLevel.WARN, Messages.getMsg("sys", "ERR_SYS_MSG_FUNC_CREATE_LINK_NULL"));
		}
		SysFunc funcInDB = getEntityById(SysFunc.class, func.getId());
		// fill default values
		SysFunc sf = new SysFunc();
		
		//说明不用进行查询验证
		if(func.getCode() !=null && !func.getCode().isEmpty()){
			if((funcInDB.getCode()!=null&& !funcInDB.getCode().isEmpty()) && (funcInDB.getCode().equals(func.getCode()))){
				BeanUtils.copyProperties(func, funcInDB, "isBaseRole", "children", "hasChildren");
			}else{
            	sf= queryFuncByCode(func.getCode());
    			if(sf.getIsEnabled() == null){
    				BeanUtils.copyProperties(func, funcInDB, "isBaseRole", "children", "hasChildren");
    				if (func.getIsEnabled() == null) {
    					func.setIsEnabled(true);
    				}
    			}
			}
		}else{
			BeanUtils.copyProperties(func, funcInDB, "isBaseRole", "children", "hasChildren");
		}
		return sf;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.asdc.jbp.sys.service.AuthorityMgtService#removeFuncById(java.lang.Integer)
	 */
	@Override
	@Transactional(readOnly = false)
	public void removeFuncById(Integer funcId) throws ServiceException {
		getFuncById(funcId).setIsEnabled(false);
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.asdc.jbp.sys.service.AuthorityMgtService#removeFuncByIds(java.util.List)
	 */
	@Override
	@Transactional(readOnly = false)
	public void removeFuncByIds(List<Integer> funcIds) throws ServiceException {
		if (funcIds == null) {
			throw new ServiceException("ERR_SYS_042", Messages.getMsg("sys", "ERR_SYS_MSG_DEPT_ID_NULL"));
		}
		for (Integer id : funcIds) {
			removeFuncById(id);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.asdc.jbp.sys.service.AuthorityMgtService#replaceUsersToGroups(java.lang.Integer, java.util.List)
	 */
	@Override
	@Transactional(readOnly = false)
	public void replaceUsersToGroups(List<Integer> groupIds, List<Integer> userIds) throws ServiceException {
		for (Integer groupId : groupIds) {
			assignUsersToGroup(groupId, userIds);
		}
	}

	/**
	 * Description : 根据用户ID 获取 SysUser, SysUserPhoto, SysUserPhoto <br>
	 * Create Time: May 1, 2016 <br>
	 * Create by : xiangyu_li@asdc.com.cn <br>
	 *
	 * @param type
	 *            SysUser, SysUserPhoto, SysUserPhoto
	 * @param id
	 *            SysUser, SysUserPhoto, SysUserPhoto
	 * @return persisted SysUser
	 * @throws ServiceException
	 *             <li>ERR_SYS_0X3: ID为空</li>
	 *             <li>ERR_SYS_0X3: 无法根据ID找到该类</li>
	 */
	private <T> T getSysUserById(Class<T> type, Integer userId) throws ServiceException {
		if (userId == null) {
			throw new ServiceException("ERR_SYS_003", Messages.getMsg("sys", "ERR_SYS_MSG_USER_ID_NULL"));
		}
		T userInDB = dao.find(type, userId);
		if (userInDB == null) {
			throw new ServiceException("ERR_SYS_003", Messages.getMsg("sys", "ERR_SYS_MSG_CAN_NOT_FIND_USER_BY_ID"), userId);
		}
		return userInDB;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.asdc.jbp.sys.service.AuthorityMgtService#replaceUserToGroups(java.lang.Integer, java.util.List)
	 */
	@Override
	@Transactional(readOnly = false)
	public void replaceUserToGroups(Integer userId, List<Integer> groupIds) throws ServiceException {
		// just validate it
		getSysUserById(userId);
		for (Integer groupId : groupIds) {
			getGroupById(groupId);
		}
		// delete the old data
		dao.executeByNamedQuery("sys.hql.removeRelOfUserGroupByUserId", Parameter.toList("userId", userId));
		// insert the new data
		for (Integer groupId : groupIds) {
			dao.persist(new RelSysUserGroup(userId, groupId));
		}
	}

	/**
	 * Description：通过userId查询用户
	 * 
	 * @param id
	 * @return
	 * @throws ServiceException
	 * @return SysUser
	 * @author name：yuruixin
	 **/
	public SysUser getSysUserById(Integer id) throws ServiceException {
		return getSysUserById(SysUser.class, id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.asdc.jbp.sys.service.AuthorityMgtService#queryAllRole(int, int)
	 */
	@Override
	public List<?> queryInfoByPage(String queryName, int start, int limit, List<Parameter> params) throws ServiceException {
		if (limit == 0) {
			limit = 5;
		}
		// dao.find(SysRole.class, primaryKey);
		return dao.queryByNamedQuery(queryName,start,limit,params);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.asdc.jbp.sys.service.AuthorityMgtService#queryGroup(String, int, int, String)
	 */
	@Override
	public List<?> queryGroup(String queryName, int start, int limit, String queryCondition) throws ServiceException {
		if (limit == 0) {
			limit = 5;
		}
		if (queryCondition != null && !"".equals(queryCondition)) {
			return dao.queryByNamedQuery("sys.hql.querySysGroupByName", start, limit,
			        Parameter.toList("name", "%" + queryCondition + "%", "desc", "%" + queryCondition + "%"));
		} else {
			return dao.queryByNamedQuery("sys.hql.queryAllSysGroup", start, limit);
		}
	}

	@Override
	public Integer queryTotle(String queryName, List<Parameter> params) throws ServiceException {
		int totle = dao.getCountByNamedQuery(queryName, params);
		return totle;
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = false)
	public List<SysFunc> queryParentByFuncId(Integer funcId) throws ServiceException {
		return (List<SysFunc>) dao.queryByNamedQuery("sys.hql.queryParentByFuncId", Parameter.toList("funcId", funcId));
	}

	@Override
	public int queryGroupCountByQueryCondtion(String queryCondition) throws ServiceException {
		int totle = 0;
		if (queryCondition != null && !"".equals(queryCondition)) {
			totle = queryGroupTotle();
		} else {
			totle = querySysGroupByManyfieldsCount(queryCondition);
		}
		return totle;

	}

	@Override
	public Integer queryGroupTotle() throws ServiceException {
		int totle = dao.getAllCount("sys.hql.queryCountAllSysGroup");
		return totle;
	}

	@Override
	public Integer querySysGroupByManyfieldsCount(String queryCondition) throws ServiceException {
		String keyWord = "%" + queryCondition + "%";
		int totle = dao.getCountByNamedQuery("sys.hql.queryCountSysGroupByName", Parameter.toList("name", keyWord));
		return totle;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.asdc.jbp.sys.service.AuthorityMgtService#replaceRolesToUsers(java.util.List, java.util.List)
	 */
	@Override
	@Transactional(readOnly = false)
	public void replaceRolesToUsers(List<Integer> userIds, List<Integer> roleIds) throws ServiceException {
		for (Integer integer : userIds) {
			replaceRolesToUser(integer, roleIds);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.asdc.jbp.sys.service.AuthorityMgtService#queryRoleByRoleName(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	public List<SysRole> queryRoleByRoleName(String roleName) throws ServiceException {
		if (roleName == null) {
			throw new ServiceException("ERR_SYS_033", Messages.getMsg("sys", "ERR_SYS_MSG_ROLE_CREATE_NAME_NULL"));
		}
		List<SysRole> queryByNamedQuery = (List<SysRole>) dao.queryByNamedQuery("sys.hql.queryRoleInfoByName", Parameter.toList("roleName", roleName));
		return queryByNamedQuery;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.asdc.jbp.sys.service.AuthorityMgtService#queryRelRoleFuncInfoByFuncId(java.lang.Integer)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<RelSysRoleFunc> queryRelRoleFuncInfoByFuncId(Integer funcId) throws ServiceException {
		if (funcId == null) {
			throw new ServiceException("ERR_SYS_043", Messages.getMsg("sys", "ERR_SYS_MSG_AUTHORITY_GET_ID_NULL"));
		}
		List<RelSysRoleFunc> roleFuncList = (List<RelSysRoleFunc>) dao.queryByNamedQuery("sys.hql.queryRoleFuncByFuncId", Parameter.toList("funcId", funcId));
		return roleFuncList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SysFunc> queryAllParentSysFuncGroup() throws ServiceException {
		List<SysFunc> funcList = (List<SysFunc>) dao.queryByNamedQuery("sys.hql.queryAllSysFuncGroup");
		return funcList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SysFunc> queryChildren(Integer funcId) throws ServiceException {
		return (List<SysFunc>) dao.queryByNamedQuery("sys.hql.queryChildrenFuncByFuncId", Parameter.toList("id", funcId));
	}

	@Override
	public int getSysFuncNameCount(SysFunc sysFunc) throws ServiceException {
		String name = sysFunc.getName();
		String code = sysFunc.getCode();
		if (code != null) {
			code = "%" + code + "%";
		}
		if (name != null) {
			name = "%" + name + "%";
		}
		int funcCount = queryTotle("sys.hql.querySysFuncNameCount", Parameter.toList("funcId", sysFunc.getId(), "name", name, "code", code));
		return funcCount;
	}

	@Override
	public int getSysFuncParentCount(SysFunc sysFunc) throws ServiceException {
		boolean isEnabledFunc = sysFunc.getIsEnabled();

		int funcCount = queryTotle("sys.hql.querySysFuncParentCount", Parameter.toList("funcId", sysFunc.getId(), "isEnabledFunc", isEnabledFunc));
		return funcCount;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SysFunc> getSysFuncName(SysFunc sysFunc, int start, int limit) throws ServiceException {
		if (limit == 0) {
			limit = 5;
		}
		String code = sysFunc.getCode();
		String name = sysFunc.getName();
		if (code != null) {
			code = "%" + code + "%";
		}
		if (name != null) {
			name = "%" + name + "%";
		}
		List<SysFunc> sysFuncList;
		sysFuncList = (List<SysFunc>) dao.queryByNamedQuery("sys.hql.querySysFuncName", start, limit,
		        Parameter.toList("funcId", sysFunc.getId(), "name", name, "code", code));
		return sysFuncList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SysFunc> getSysFuncParent(SysFunc sysFunc, int start, int limit) throws ServiceException {
		if (limit == 0) {
			limit = 5;
		}
		boolean isEnabledFunc = sysFunc.getIsEnabled();

		List<SysFunc> sysFuncList;
		sysFuncList = (List<SysFunc>) dao.queryByNamedQuery("sys.hql.querySysFuncParent", start, limit,
		        Parameter.toList("funcId", sysFunc.getId(), "isEnabledFunc", isEnabledFunc));
		return sysFuncList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SysFunc> queryAllSysFuncGroup() {
		return (List<SysFunc>) dao.queryByNamedQuery("sys.hql.queryAllSysFuncGroup");
	}

	/**
	 * Description： 根据角色ID更新用户
	 * 
	 * @param userIds
	 * @param roleIds
	 * @return void
	 * @author name：liujie <br>
	 *         email: jie_liu1@asdc.com.cn
	 * @throws ServiceException
	 **/
	@Override
	public void replaceUsersToRole(List<Integer> userIdList, Integer roleId) throws ServiceException {
		getRoleById(roleId);
		for (Integer userId : userIdList) {
			getSysUserById(userId);
		}
		// 删除旧数据
		dao.executeByNamedQuery("sys.hql.removeRelOfUserRoleByRoleId", Parameter.toList("roleId", roleId));
		// 插入新对应关系
		for (Integer userId : userIdList) {
			dao.persist(new RelSysUserRole(userId, roleId));
		}
	}

	@Override
	public void recoverRoleInfo(List<Integer> roleList) throws ServiceException {
		if (roleList == null) {
			throw new ServiceException("ERR_SYS_032", Messages.getMsg("sys", "ERR_SYS_MSG_DEPT_ID_NULL"));
		}
		for (Integer id : roleList) {
			SysRole entityById = getRoleById(id);
			entityById.setIsEnabled(true);
			updateRole(entityById);
		}

	}

	@Override
	public void recoverFuncInfo(List<Integer> funcList) throws ServiceException {
		if (funcList == null) {
			throw new ServiceException("ERR_SYS_032", Messages.getMsg("sys", "ERR_SYS_MSG_DEPT_ID_NULL"));
		}
		for (Integer id : funcList) {
			SysFunc entityById = getFuncById(id);
			entityById.setIsEnabled(true);
			updateFunc(entityById);
		}
	}

	@Override
	public boolean getChildrenCountByParentId(int funcParentId) throws ServiceException {
		boolean falg = true;
		int totle = dao.getCountByNamedQuery("sys.hql.queryFuncChildrenCountByParentId", Parameter.toList("parentId", funcParentId));
		if (totle == 0) {
			falg = false;
		}
		return falg;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SysFunc> getFuncByParentIsNull() throws ServiceException {
		return (List<SysFunc>) dao.queryByNamedQuery("sys.hql.queryParentFuncIsNull");
	}
	public List<?> queryInfoNoPage(String queryName,List<Parameter> params) throws ServiceException {
		return dao.queryByNamedQuery(queryName,params);
	}
	public SysUser queryUserIdByAccont(String account) throws ServiceException{
		int totle =  dao.getCountByNamedQuery("sys.hql.getCountByAccount", Parameter.toList("account", account));
		SysUser user = new SysUser();
		if(totle == 0){
			//返回true  -->表示可以添加
			user.setIsEnabled(null);
		}else{
			user = (SysUser) dao.getSingleResultByNamedQuery("sys.hql.getUserByAccount", Parameter.toList("account", account));
		}
		return user;
	}
	
	public boolean queryUserByDeptId(int deptid)throws ServiceException{
		int totle =  dao.getCountByNamedQuery("sys.hql.getUserByDeptId", Parameter.toList("deptId", deptid));
		boolean flag = true;
		//没有机构
		if(totle == 0){
			flag = false;
		}
		return flag;
	}

	@Override
	public SysFunc queryFuncByCode(String code) throws ServiceException {
		int totle =  dao.getCountByNamedQuery("sys.hql.querySysFuncByCode", Parameter.toList("code", code));
		SysFunc func = new SysFunc();
		if(totle == 0){
			//返回true  -->表示可以添加
			func.setIsEnabled(null);
		}else{
			func = (SysFunc) dao.getSingleResultByNamedQuery("sys.hql.querySysFuncById", Parameter.toList("code", code));
		}
		return func;
	}
}
