/*
 * Project Name : jbp-features-sys <br>
 * File Name : UserMgtServiceImpl.java <br>
 * Package Name : com.asdc.jbp.sys.service.impl <br>
 * Create Time : Apr 30, 2016 <br>
 * Create by : xiangyu_li@asdc.com.cn <br>
 * Copyright © 2006, 2016, ASDC DAI. All rights reserved.
 */
package com.asdc.jbp.sys.service.impl;

import com.asdc.jbp.framework.dao.BlobImpl;
import com.asdc.jbp.framework.dao.JpaOrmOperator;
import com.asdc.jbp.framework.dao.Parameter;
import com.asdc.jbp.framework.exception.ErrLevel;
import com.asdc.jbp.framework.exception.ServiceException;
import com.asdc.jbp.framework.message.Messages;
import com.asdc.jbp.framework.utils.BeanUtils;
import com.asdc.jbp.framework.utils.StringUtils;
import com.asdc.jbp.sys.entity.SysDept;
import com.asdc.jbp.sys.entity.SysUser;
import com.asdc.jbp.sys.entity.SysUserPhoto;
import com.asdc.jbp.sys.entity.SysUserPwd;
import com.asdc.jbp.sys.entity.stuff.UserType;
import com.asdc.jbp.sys.service.AuthorityMgtService;
import com.asdc.jbp.sys.service.UserMgtService;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import java.io.UnsupportedEncodingException;
import java.sql.Blob;
import java.util.List;

/**
 * ClassName : UserMgtServiceImpl <br>
 * Description :实现  {@link UserMgtService} <br>
 * Create Time : Apr 30, 2016 <br>
 * Create by : xiangyu_li@asdc.com.cn <br>
 */
@SuppressWarnings("unchecked")
@Service
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Transactional(readOnly = true)
public class UserMgtServiceImpl implements UserMgtService {

    @Resource(name = "SysCommonDao")
    private JpaOrmOperator sysCommonDao;
    @Resource
    private AuthorityMgtService authService;

    /*
     * (non-Javadoc)
     *
     * @see com.asdc.jbp.sys.service.UserMgtService#createSysUser(com.asdc.jbp.sys.entity.SysUser)
     */
    @Override
    @Transactional
    public SysUser createSysUser(SysUser user) throws ServiceException {
        // validate
        if (user == null) {
            throw new ServiceException("ERR_SYS_001", Messages.getMsg("sys", "ERR_SYS_MSG_USER_NULL"));
        }
        if (user.getAccount() == null) {
            throw new ServiceException("ERR_SYS_003", ErrLevel.WARN, Messages.getMsg("sys", "ERR_SYS_MSG_USER_ACCOUNT_NULL"));
        }
        try {
            sysCommonDao.getSingleResultByNamedQuery("sys.hql.getUserIdByAccount", Parameter.toList("account", user.getAccount()));
            throw new ServiceException("ERR_SYS_003", ErrLevel.WARN, Messages.getMsg("sys", "ERR_SYS_MSG_USER_ACCOUNT_NO_UNIQUE_1"), user.getAccount());
        } catch (NoResultException e) {
            // ignore
        } catch (NonUniqueResultException e) {
            throw new ServiceException("ERR_SYS_003", ErrLevel.WARN, Messages.getMsg("sys", "ERR_SYS_MSG_USER_ACCOUNT_NO_UNIQUE_2"), user.getAccount());
        }
        // fill default values
        if (user.getIsEnabled() == null) {
            user.setIsEnabled(true);
        }
        // FIXME 这里使用了旧的技术，需要改正
        System.err.println("这里使用了错误的实现，需要修改");
//        if (user.getTypeCode() == null) {
//            user.setTypeCode("NORMAL");
//        }
        // save
        sysCommonDao.persist(user);
        return user;
    }

    /*
     * (non-Javadoc)
     *
     * @see com.asdc.jbp.sys.service.UserMgtService#updateSysUser(com.asdc.jbp.sys.entity.SysUser)
     */
    @Override
    @Transactional
    public SysUser updateSysUser(SysUser user) throws ServiceException {
        // validate
        if (user == null) {
            throw new ServiceException("ERR_SYS_002", Messages.getMsg("sys", "ERR_SYS_MSG_USER_NULL"));
        }
        if (user.getAccount() == null) {
            throw new ServiceException("ERR_SYS_003", ErrLevel.WARN, Messages.getMsg("sys", "ERR_SYS_MSG_USER_ACCOUNT_NULL"));
        }
        Integer userId = Integer.MIN_VALUE;
        try {
            userId = ((Number) sysCommonDao.getSingleResultByNamedQuery("sys.hql.getUserIdByAccount", Parameter.toList("account", user.getAccount()))).intValue();
        } catch (NoResultException e) {
            // ignore
        } catch (NonUniqueResultException e) {
            throw new ServiceException("ERR_SYS_003", ErrLevel.WARN, Messages.getMsg("sys", "ERR_SYS_MSG_USER_ACCOUNT_NO_UNIQUE_2"), user.getAccount());
        }
        if (!userId.equals(Integer.MIN_VALUE) && !userId.equals(user.getId())) {
            throw new ServiceException("ERR_SYS_002", ErrLevel.WARN, Messages.getMsg("sys", "ERR_SYS_MSG_USER_ACCOUNT_NO_UNIQUE_1"), user.getAccount());
        }
        SysUser userInDB = getSysUserById(user.getId());

        // fill default values
        BeanUtils.copyProperties(user, userInDB, "type", "isEnabled", "lastTime");
        // FIXME 这里使用了旧的技术，需要改正
        System.err.println("这里使用了错误的实现，需要修改");
//        if (user.getTypeCode() != null) {
//            userInDB.setTypeCode(user.getTypeCode());
//        }
        return userInDB;
    }

    /*
     * (non-Javadoc)
     *
     * @see com.asdc.jbp.sys.service.UserMgtService#updateUserPhoto(java.lang.Integer, java.lang.String)
     */
    @Override
    @Transactional
    public void updateUserPhoto(Integer userId, String base64PhotoStr) throws ServiceException {
        Blob base64PhotoBlob = null;
        SysUserPhoto userInDB = getSysUserById(SysUserPhoto.class, userId);
        if (base64PhotoStr != null) {
            base64PhotoBlob = new BlobImpl(base64PhotoStr.getBytes());
        }
        userInDB.setPhoto(base64PhotoBlob);
    }

    /*
     * (non-Javadoc)
     *
     * @see com.asdc.jbp.sys.service.UserMgtService#getUserPhoto(java.lang.Integer)
     */
    @Override
    public String getUserPhoto(Integer userId) throws ServiceException, UnsupportedEncodingException {
        Blob base64PhotoBlob = getSysUserById(SysUserPhoto.class, userId).getPhoto();
        if (base64PhotoBlob == null) {
            return "";
        }
        byte[] photoByte = BlobImpl.blobToBytes(base64PhotoBlob);
        assert photoByte != null;
        return new String(photoByte, "UTF-8");
    }

    /*
     * (non-Javadoc)
     *
     * @see com.asdc.jbp.sys.service.UserMgtService#removeSysUser(java.util.List)
     */
    @Override
    @Transactional
    public void removeSysUser(List<Integer> ids) throws ServiceException {
        if (ids == null) {
            throw new ServiceException("ERR_SYS_003", Messages.getMsg("sys", "ERR_SYS_MSG_USER_ID_NULL"));
        }
        for (Integer id : ids) {
            getSysUserById(id).setIsEnabled(false);
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see com.asdc.jbp.sys.service.UserMgtService#getSysUserById(java.lang.Integer)
     */
    @Override
    public SysUser getSysUserById(Integer id) throws ServiceException {
        return getSysUserById(SysUser.class, id);
    }

    /**
     * Description : 根据用户ID 获取 SysUser, SysUserPhoto, SysUserPhoto <br>
     * Create Time: May 1, 2016 <br>
     * Create by : xiangyu_li@asdc.com.cn <br>
     *
     * @param type   SysUser, SysUserPhoto, SysUserPhoto
     * @param userId SysUser, SysUserPhoto, SysUserPhoto
     * @return persisted SysUser
     * @throws ServiceException <li>ERR_SYS_0X3: ID为空</li>
     *                          <li>ERR_SYS_0X3: 无法根据ID找到该类</li>
     */
    private <T> T getSysUserById(Class<T> type, Integer userId) throws ServiceException {
        if (userId == null) {
            throw new ServiceException("ERR_SYS_003", Messages.getMsg("sys", "ERR_SYS_MSG_USER_ID_NULL"));
        }
        T userInDB = sysCommonDao.find(type, userId);
        if (userInDB == null) {
            throw new ServiceException("ERR_SYS_003", Messages.getMsg("sys", "ERR_SYS_MSG_CAN_NOT_FIND_USER_BY_ID"), userId);
        }
        return userInDB;
    }

    /*
     * (non-Javadoc)
     *
     * @see com.asdc.jbp.sys.service.UserMgtService#getSysUserIdByUserAccount(java.lang.String)
     */
    public Integer getSysUserIdByUserAccount(String account) throws ServiceException {
        if (account == null) {
            throw new ServiceException("ERR_SYS_003", Messages.getMsg("sys", "ERR_SYS_MSG_USER_ACCOUNT_NULL"));
        }
        try {
            return ((Number) sysCommonDao.getSingleResultByNamedQuery("sys.hql.getUserIdByAccount", Parameter.toList("account", account))).intValue();
        } catch (NoResultException e) {
            throw new ServiceException("ERR_SYS_003", ErrLevel.WARN, Messages.getMsg("sys", "ERR_SYS_MSG_CAN_NOT_FIND_USER_BY_ACCOUNT"), account);
        } catch (NonUniqueResultException e) {
            throw new ServiceException("ERR_SYS_003", ErrLevel.WARN, Messages.getMsg("sys", "ERR_SYS_MSG_USER_ACCOUNT_NO_UNIQUE_2"), account);
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see com.asdc.jbp.sys.service.UserMgtService#getSysUserIdByUserEmail(java.lang.String)
     */
    public Integer getSysUserIdByUserEmail(String email) throws ServiceException {
        if (email == null) {
            throw new ServiceException("ERR_SYS_003", Messages.getMsg("sys", "ERR_SYS_MSG_USER_EMAIL_NULL"));
        }
        return ((Number) sysCommonDao.getSingleResultByNamedQuery("sys.hql.getUserIdByEmail", Parameter.toList("email", email))).intValue();
    }

    /*
     * (non-Javadoc)
     *
     * @see com.asdc.jbp.sys.service.UserMgtService#resetUserPassword(java.lang.Integer, java.lang.String)
     */
    @Override
    @Transactional
    public void resetUserPassword(Integer userId, String password) throws ServiceException {
        SysUserPwd userInDB = getSysUserById(SysUserPwd.class, userId);
        userInDB.setPassword(StringUtils.encryptByMD5(password));
    }

    /*
     * (non-Javadoc)
     *
     * @see com.asdc.jbp.sys.service.UserMgtService#resetMyPassword(java.lang.String)
     */
    @Override
    @Transactional
    public void resetMyPassword(String password) throws ServiceException {
        System.err.println("此功能被禁用");
//        Token token;
//        try {
//            token = (Token) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        } catch (Exception e) {
//            throw new ServiceException("ERR_SYS_002", Messages.getMsg("sys", "ERR_SYS_MSG_CAN_NOT_GET_TOKEN"));
//        }
//        if (UserType.ANONYMOUS.toString().equals(token.getUser().getType().getCode())) {
//            throw new ServiceException("ERR_SYS_002", ErrLevel.WARN, Messages.getMsg("sys", "ERR_SYS_MSG_ANONYMOUS_PASSWORD"));
//        }
//        getSysUserById(SysUserPwd.class, token.getUser().getId()).setPassword(StringUtils.encryptByMD5(password));
    }

    /*
     * (non-Javadoc)
     *
     * @see com.asdc.jbp.sys.service.UserMgtService#resetUserPassword(java.lang.Integer)
     */
    @Override
    @Transactional
    public String generateUserPassword(Integer userId) throws ServiceException {
        if (UserType.ANONYMOUS.toString().equals(getSysUserById(userId).getType().getCode())) {
            throw new ServiceException("ERR_SYS_002", ErrLevel.WARN, Messages.getMsg("sys", "ERR_SYS_MSG_ANONYMOUS_PASSWORD"));
        }
        SysUserPwd userInDB = getSysUserById(SysUserPwd.class, userId);
        String password = StringUtils.generatePwd(8);
        userInDB.setPassword(StringUtils.encryptByMD5(password));
        return password;
    }

    /*
     * (non-Javadoc)
     *
     * @see com.asdc.jbp.sys.service.UserMgtService#validateLoginNameIsInDBorNot(java.lang.String)
     */
    @Override
    public boolean validateAccountIsInDBorNot(String account) {
        try {
            getSysUserIdByUserAccount(account);
            return true;
        } catch (ServiceException e) {
            return false;
        }
    }

    /* (non-Javadoc)
     * @see com.asdc.jbp.sys.service.UserMgtService#getSysUsersByDeptAndAccountAndName(com.asdc.jbp.sys.entity.SysUser)
     */
    @Override
    public List<SysUser> getSysUsersByDeptAndAccountAndName(SysUser sysUser, int start, int limit)
        throws ServiceException {
        if (limit == 0) {
            limit = 5;
        }
        String name = sysUser.getName();
        if (name != null) {
            name = "%" + name + "%";
        }
        List<SysUser> sysUsersList;
        sysUsersList = (List<SysUser>) sysCommonDao.queryByNamedQuery("sys.hql.querySysUsersByDeptAndAccountAndName", start, limit, Parameter.toList("deptId", sysUser.getDeptId(), "account", name, "name", name, "isEnabled", sysUser.getIsEnabled()));
        return sysUsersList;
    }

    /* (non-Javadoc)
     * @see com.asdc.jbp.sys.service.UserMgtService#getSysUsersByDeptAndAccountAndNameCount(com.asdc.jbp.sys.entity.SysUser)
     */
    @Override
    @Transactional
    public int getSysUsersByDeptAndAccountAndNameCount(SysUser sysUser) throws ServiceException {
        String name = sysUser.getName();
        if (name != null) {
            name = "%" + name + "%";
        }
        return authService.queryTotle("sys.hql.querySysUsersByDeptAndAccountAndNameCount", Parameter.toList("deptId", sysUser.getDeptId(), "account", name, "name", name, "isEnabled", sysUser.getIsEnabled()));
    }

    /* (non-Javadoc)
     * @see com.asdc.jbp.sys.service.UserMgtService#updateSysUserInfo(com.asdc.jbp.sys.entity.SysUser)
     */
    @Override
    @Transactional
    public SysUser updateSysUserInfo(SysUser sysUser) throws ServiceException {

        updateUserPhoto(sysUser.getId(), sysUser.getSysUserPhoto().getPhotoStr());
        //重置用户密码
        if (sysUser.getSysUserPwd() != null && sysUser.getSysUserPwd().getPassword() != null && !("".equals(sysUser.getSysUserPwd().getPassword()))) {
            resetUserPassword(sysUser.getId(), sysUser.getSysUserPwd().getPassword());
        }
        if (sysUser.getGroupId() != null) {
            //更新用户组信息
            authService.replaceUserToGroups(sysUser.getId(), sysUser.getGroupId());
        }
        //更新用户基本信息
        return updateSysUser(sysUser);
    }

    /* (non-Javadoc)
     * @see com.asdc.jbp.sys.service.UserMgtService#creatSysUserInfo(com.asdc.jbp.sys.entity.SysUser)
     */
    @Override
    public SysUser creatSysUserInfo(SysUser sysUser) throws ServiceException {
        //创建用户信息
        createSysUser(sysUser);
        //修改用户信息
        return updateSysUserInfo(sysUser);
    }

    /* (non-Javadoc)
     * @see com.asdc.jbp.sys.service.UserMgtService#getUserById(java.lang.Integer)
     */
    @Override
    public SysUser getUserById(Integer userId) throws ServiceException {
        return (SysUser) sysCommonDao.getSingleResultByNamedQuery("sys.hql.getUserById", Parameter.toList("id", userId));
    }

    /* (non-Javadoc)
     * @see com.asdc.jbp.sys.service.UserMgtService#repeatSysUser(java.util.List)
     */
    @Override
    public void repeatSysUser(List<Integer> ids) throws ServiceException {
        if (ids == null) {
            throw new ServiceException("ERR_SYS_003", Messages.getMsg("sys", "ERR_SYS_MSG_USER_ID_NULL"));
        }
        SysDept deptUpdate = new SysDept();


        for (Integer id : ids) {
            SysUser user = getSysUserById(id);
            user.setIsEnabled(true);
            SysDept dept = user.getDept();
            if (!dept.getIsEnabled()) {
                deptUpdate.setId(2);//总公司
                user.setDept(deptUpdate);
            }
        }
    }


    public Integer getCountByEmail(String email) throws ServiceException {
        if (email == null) {
            throw new ServiceException("ERR_SYS_003", Messages.getMsg("sys", "ERR_SYS_MSG_USER_EMAIL_NULL"));
        }
        return ((Number) sysCommonDao.getSingleResultByNamedQuery("sys.hql.getCountByEmail", Parameter.toList("email", email))).intValue();
    }

    @Override
    @Transactional
    public SysUser registerSysUser(SysUser sysUser) throws ServiceException {
        //重置用户密码
        if (sysUser.getSysUserPwd() != null && sysUser.getSysUserPwd().getPassword() != null && !("".equals(sysUser.getSysUserPwd().getPassword()))) {
            resetUserPassword(sysUser.getId(), sysUser.getSysUserPwd().getPassword());
        }
        if (sysUser.getGroupId() != null) {
            //更新用户组信息
            authService.replaceUserToGroups(sysUser.getId(), sysUser.getGroupId());
        }
        //更新用户基本信息
        return updateSysUser(sysUser);
    }

    @Override
    public SysUser creatSysUser(SysUser sysUser) throws ServiceException {
        //创建用户信息
        createSysUser(sysUser);
        //修改用户信息
        return registerSysUser(sysUser);
    }

    public SysUser updateSysUserPassword(SysUser sysUser) throws ServiceException {
        //重置用户密码
        if (sysUser.getSysUserPwd() != null && sysUser.getSysUserPwd().getPassword() != null && !("".equals(sysUser.getSysUserPwd().getPassword()))) {
            resetUserPassword(sysUser.getId(), sysUser.getSysUserPwd().getPassword());
        }
        return sysUser;
    }

    @Override
    @Transactional
    public SysUser updateSysUserLastTime(SysUser user) throws ServiceException {

        // validate
        if (user == null) {
            throw new ServiceException("ERR_SYS_002", Messages.getMsg("sys", "ERR_SYS_MSG_USER_NULL"));
        }
        SysUser userInDB = getSysUserById(user.getId());

        // fill default values
        BeanUtils.copyProperties(user, userInDB, "type", "isEnabled");
        // FIXME 这里使用了旧的技术，需要改正
        System.err.println("这里使用了错误的实现，需要修改");
//        if (user.getTypeCode() != null) {
//            userInDB.setTypeCode(user.getTypeCode());
//        }
        return userInDB;
    }

    @Override
    public SysUser getUserByName(String username) throws ServiceException {
        return null;
    }

}
