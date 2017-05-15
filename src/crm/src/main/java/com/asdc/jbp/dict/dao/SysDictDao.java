/**
 * Project Name : jbp-plugin-dict-service <br>
 * File Name : SysDictDao.java <br>
 * Package Name : com.asdc.jbp.dict.dao <br>
 * Create Time : Apr 14, 2016 <br>
 * Create by : xiangyu_li@asdc.com.cn <br>
 * Copyright © 2006, 2016, ASDC DAI. All rights reserved.
 */
package com.asdc.jbp.dict.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.LockTimeoutException;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.PessimisticLockException;
import javax.persistence.Query;
import javax.persistence.QueryTimeoutException;
import javax.persistence.TransactionRequiredException;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.asdc.jbp.dict.entity.SysDict;

/**
 * ClassName : SysDictDao <br>
 * Description : dao of SysDict <br>
 * Create Time : Apr 14, 2016 <br>
 * Create by : xiangyu_li@asdc.com.cn <br>
 *
 */
@Transactional(readOnly = false)
@Repository
public  class SysDictDao {

    @PersistenceContext(unitName = "crm_mgmt")
    private EntityManager em;

    @SuppressWarnings("unchecked")
    public List<SysDict> queryAll(int start, int limit) {
        return em.createNamedQuery("dict.hql.queryAllSysDict").setFirstResult(start).setMaxResults(limit).getResultList();
    }

    public int getAllCount() {
        return Integer.parseInt(em.createNamedQuery("dict.hql.getAllSysDictCount").getSingleResult().toString());
    }
    @Transactional(readOnly = false)
    public void persist(Object dict) {
        em.persist(dict);

    }

    /**
     * Description : get the SysDict entity with nature and code <br>
     * Create Time: Apr 28, 2016 <br>
     * Create by : xiangyu_li@asdc.com.cn <br>
     *
     * @param nature
     * @param code
     * @return the entity of SysDict with given nature and code
     * @throws NoResultException
     *             if there is no result
     * @throws NonUniqueResultException
     *             if more than one result
     * @throws IllegalStateException
     *             if called for a Java Persistence query language UPDATE or DELETE statement
     * @throws QueryTimeoutException
     *             if the query execution exceeds the query timeout value set and only the statement is rolled back
     * @throws TransactionRequiredException
     *             if a lock mode has been set and there is no transaction
     * @throws PessimisticLockException
     *             if pessimistic locking fails and the transaction is rolled back
     * @throws LockTimeoutException
     *             if pessimistic locking fails and only the statement is rolled back
     * @throws PersistenceException
     *             if the query execution exceeds the query timeout value set and the transaction is rolled back
     */
	public SysDict getSysDictByNatureAndCode(String nature, String code) {
		SysDict dict = null;
		try {
			dict = (SysDict) em.createNamedQuery("dict.hql.querySysDictByNatureAndCode").setParameter("nature", nature).setParameter("code", code)
					.getSingleResult();
		} catch (NoResultException | NonUniqueResultException e) {
			// TODO: handle exception
			return null;
		}
		return dict;

	}

    public SysDict getSysDictById(Integer id) {
        return em.find(SysDict.class, id);
    }

    @SuppressWarnings("unchecked")
    public List<SysDict> queryDictsByNature(String nature) {
        return em.createNamedQuery("dict.hql.querySysDictByNature").setParameter("nature", nature).getResultList();
    }

    public int getDictsCountByNature(String nature) {
    	return Integer.parseInt(em.createNamedQuery("dict.hql.getSysDictCountByNature").setParameter("nature", nature).getSingleResult().toString());
    }

    public int removeDictByNature(String nature) {
        return em.createNamedQuery("dict.hql.setDisableByNature").setParameter("nature", nature).executeUpdate();
    }

    /**
     *
     * Description：通过字典id删除
     * @param id 字典id
     * @return void
     * @author name：wangyishuai
     *
     */
    public void deleteDictById(int id) {
          em.createNamedQuery("sys.hql.removeDictById").setParameter("id", id).executeUpdate();
     }
    /**
     *
     * Description：通过id恢复数据
     * @param id 字典id
     * @return void
     * @author name：wangyishuai
     *
     */
    public void reciveDictById(int id) {
        em.createNamedQuery("sys.hql.recoverDictById").setParameter("id", id).executeUpdate();
   }

    /**
     *
     * Description：通过父级id查询出所含子级
     * @param id 父级id
     * @return List<SysDict>
     * @author name：wangyishuai
     *
     */
    @SuppressWarnings("unchecked")
    public List<SysDict> getParentById(Integer id) {
        return em.createNamedQuery("sys.hql.getParentById").setParameter("id", id).getResultList();
    }

    /**
     *
     * Description：通过父级id查询出所含子级
     * @param start 第几条开始
     * @param limit 取多少条
     * @param id 父级id
     * @return List<SysDict>
     * @author name：wangyishuai
     *
     */
    @SuppressWarnings("unchecked")
    public List<SysDict> queryChildrenDictByDictId(int start, int limit,Integer id) {
        return em.createNamedQuery("sys.hql.getParentById").setFirstResult(start).setMaxResults(limit).setParameter("id", id).getResultList();
    }

	/**
	 *
	 * Description：字典模糊查询
	 * @param start 第几条开始
	 * @param limit 取多少条
	 * @param dict  模糊查询条件
	 * @return List<SysDict>
	 * @author name：wangyishuai
	 *
	 */
	@SuppressWarnings("unchecked")
	public List<SysDict> querySysDeptByManyfields(int start, int limit,SysDict dict) {
		// TODO Auto-generated method stub

		//return em.createNamedQuery("dict.hql.querySysDeptByManyfields").setParameter("id", dict.getId()).setParameter("nature", dict.getCode()).setParameter("code", dict.getCode()).setParameter("value", dict.getCode()).setParameter("isEnabled", dict.getIsEnabled()).setFirstResult(start).setMaxResults(limit).getResultList();
		Query query  =em.createNamedQuery("dict.hql.querySysDeptByParentIdManyfields").setParameter("id", dict.getId()).setParameter("nature", dict.getCode()).setParameter("code", dict.getCode()).setParameter("value", dict.getCode()).setParameter("isEnabled", dict.getIsEnabled());

		return query.setFirstResult(start).setMaxResults(limit).getResultList();
	}

	/**
	 *
	 * Description：字典模糊查询总个数
	 * @param dict 模糊查询条件
	 * @return int
	 * @author name：wangyishuai
	 *
	 */
	public int getCountByNamedQuery(SysDict dict) {
		// TODO Auto-generated method stub
		return Integer.parseInt(em.createNamedQuery("sys.hql.getCountSysDeptByParentIdManyfields").setParameter("id", dict.getId()).setParameter("nature", dict.getCode()).setParameter("code", dict.getCode()).setParameter("value", dict.getCode()).setParameter("isEnabled", dict.getIsEnabled()).getSingleResult().toString());
	}
	/**
	 *
	 * Description：查询上级字典为null的字典对象。这里单就是字典管理
	 * @return
	 * @return SysDict
	 * @author name：yangxuan <br>email: xuan_yang@asdc.com.cn
	 *
	 */
	@SuppressWarnings("unchecked")
	public List<SysDict> queryParentDictIsNull(){

		 return (List<SysDict>) em.createNamedQuery("sys.hql.queryParentDictIsNull").getResultList();
	}
    /**
     *
     * Description：判断是否存在指定的字典编码和字典类型
     * @param nature
     * @param code
     * @return
     * @return int
     * @author name：yangxuan <br>email: xuan_yang@asdc.com.cn
     *
     */
	public int checkcodeAndNature(String nature, String code){
		// TODO Auto-generated method stub
		return Integer.parseInt(em.createNamedQuery("dict.hql.querySysDictByNatureAndCodeIsExist")
				.setParameter("nature",nature).setParameter("code",code).getSingleResult().toString());
	}

	public SysDict queryDictById(SysDict dict) {
		@SuppressWarnings("unchecked")
		List<SysDict> dictList  = em.createNamedQuery("dict.hql.queryDictById").setParameter("id",dict.getId()).setParameter("isEnabled",dict.getIsEnabled()).setParameter("nature", dict.getCode()).setParameter("code", dict.getCode()).setParameter("value", dict.getCode()).getResultList();
		if(dictList != null && dictList.size() != 0){
			return dictList.get(0);
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public List<SysDict> querySysDeptByOrderBy(int start, int limit,SysDict dict) {
		// TODO Auto-generated method stub
		Query query  =em.createNativeQuery("dict.hql.querySysDeptByOrder").setParameter("id", dict.getId()).setParameter("nature", dict.getCode()).setParameter("code", dict.getCode()).setParameter("value", dict.getCode()).setParameter("isEnabled", dict.getIsEnabled()).setParameter("order","dict.value");
		return query.setFirstResult(start).setMaxResults(limit).getResultList();
	}

	@SuppressWarnings("unchecked")
    public List<SysDict> queryChildrenByParentCode(String code) {
	    // TODO Auto-generated method stub
	    return em.createNamedQuery("sys.hql.queryChildrenByParentCode").setParameter("code", code).getResultList();
    }

	public SysDict queryCodeByValue(String code, String value) {
	    // TODO Auto-generated method stub
	    return (SysDict) em.createNamedQuery("sys.hql.queryCodeByValue").setParameter("code", code).setParameter("value", value).getSingleResult();
    }

	public String queryValueByCode(String code) {
	    // TODO Auto-generated method stub
		SysDict dict = (SysDict) em.createNamedQuery("sys.hql.queryValueByCode").setParameter("code", code).getSingleResult();
		return dict.getValue();
    }

}
