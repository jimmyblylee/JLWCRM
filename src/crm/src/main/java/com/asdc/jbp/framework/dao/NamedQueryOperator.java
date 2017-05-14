/**
 * Project Name : jbp-framework <br>
 * File Name : NamedQueryOperator.java <br>
 * Package Name : com.asdc.jbp.framework.dao <br>
 * Create Time : Apr 30, 2016 <br>
 * Create by : xiangyu_li@asdc.com.cn <br>
 * Copyright © 2006, 2016, ASDC DAI. All rights reserved.
 */
package com.asdc.jbp.framework.dao;

import java.util.List;

import javax.persistence.LockTimeoutException;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceException;
import javax.persistence.PessimisticLockException;
import javax.persistence.QueryTimeoutException;
import javax.persistence.TransactionRequiredException;

import com.asdc.jbp.framework.exception.ServiceException;

/**
 * ClassName : NamedQueryOperator <br>
 * Description : Named query operator <br>
 * Create Time : Apr 30, 2016 <br>
 * Create by : xiangyu_li@asdc.com.cn <br>
 *
 */
public interface NamedQueryOperator {

    /**
     * Description : query list by given named query <br>
     * Create Time: Apr 30, 2016 <br>
     * Create by : xiangyu_li@asdc.com.cn <br>
     *
     * @param queryName
     * @param params
     *            the ordered parameter, these parameters will be put into the query one by one in number order.
     * @return a list of the results
     *
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
    public List<?> queryByNamedQuery(String queryName, Object... params);

    /**
     * Description : query list by given named query <br>
     * Create Time: Apr 30, 2016 <br>
     * Create by : xiangyu_li@asdc.com.cn <br>
     *
     * @param queryName
     * @param params
     *            {@link Parameter} list, and this will be set as the parameters into the query
     * @return a list of the results
     *
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
    public List<?> queryByNamedQuery(String queryName, List<Parameter> params);

    /**
     * Description : query list with given named query by page <br>
     * Create Time: Apr 30, 2016 <br>
     * Create by : xiangyu_li@asdc.com.cn <br>
     *
     * @param queryName
     * @param start
     * @param limit if limit < 0 then will set it with {@link Integer#MAX_VALUE}
     * @param params
     *            the ordered parameter, these parameters will be put into the query one by one in number order.
     * @return a list of the results
     *
     * @throws ServiceException start < 0
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
    public List<?> queryByNamedQuery(String queryName, Integer start, Integer limit, Object... params) throws ServiceException;

    /**
     * Description : query list with given named query by page <br>
     * Create Time: Apr 30, 2016 <br>
     * Create by : xiangyu_li@asdc.com.cn <br>
     *
     * @param queryName
     * @param start
     * @param limit if limit < 0 then will set it with {@link Integer#MAX_VALUE}
     * @param params
     *            {@link Parameter} list, and this will be set as the parameters into the query
     * @return a list of the results
     *
     * @throws ServiceException start < 0
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
    public List<?> queryByNamedQuery(String queryName, Integer start, Integer limit, List<Parameter> params) throws ServiceException;

    /**
     * Description : get count by a counting sql defined by named query <br>
     * Create Time: Apr 30, 2016 <br>
     * Create by : xiangyu_li@asdc.com.cn <br>
     *
     * @param queryName
     * @param params
     *            the ordered parameter, these parameters will be put into the query one by one in number order.
     * @return the result
     *
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
    public Integer getCountByNamedQuery(String queryName, Object... params);

    /**
     * Description : get count by a counting sql defined by named query <br>
     * Create Time: Apr 30, 2016 <br>
     * Create by : xiangyu_li@asdc.com.cn <br>
     *
     * @param queryName
     * @param params
     *            {@link Parameter} list, and this will be set as the parameters into the query
     * @return the result
     *
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
    public Integer getCountByNamedQuery(String queryName, List<Parameter> params);

    /**
     * Description : get sigle result by given named sql <br>
     * Create Time: Apr 30, 2016 <br>
     * Create by : xiangyu_li@asdc.com.cn <br>
     *
     * @param queryName
     * @param params
     *            the ordered parameter, these parameters will be put into the query one by one in number order.
     * @return the result
     *
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
    public Object getSingleResultByNamedQuery(String queryName, Object... params);

    /**
     * Description : get sigle result by given named sql <br>
     * Create Time: Apr 30, 2016 <br>
     * Create by : xiangyu_li@asdc.com.cn <br>
     *
     * @param queryName
     * @param params
     *            {@link Parameter} list, and this will be set as the parameters into the query
     * @return the result
     *
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
    public Object getSingleResultByNamedQuery(String queryName, List<Parameter> params);

    /**
     * Description : Execute an update or delete statement. by named query <br>
     * Create Time: Apr 30, 2016 <br>
     * Create by : xiangyu_li@asdc.com.cn <br>
     *
     * @param queryName
     * @param params
     *            the ordered parameter, these parameters will be put into the query one by one in number order.
     * @return the number of entities updated or deleted
     *
     * @throws IllegalStateException
     *             if called for a Java Persistence query language SELECT statement or for a criteria query
     * @throws TransactionRequiredException
     *             if there is no transaction
     * @throws QueryTimeoutException
     *             if the statement execution exceeds the query timeout value set and only the statement is rolled back
     * @throws PersistenceException
     *             if the query execution exceeds the query timeout value set and the transaction is rolled back
     */
    public Integer executeByNamedQuery(String queryName, Object... params);

    /**
     * Description : Execute an update or delete statement. by named query <br>
     * Create Time: Apr 30, 2016 <br>
     * Create by : xiangyu_li@asdc.com.cn <br>
     *
     * @param queryName
     * @param params
     *            {@link Parameter} list, and this will be set as the parameters into the query
     * @return the number of entities updated or deleted
     *
     * @throws IllegalStateException
     *             if called for a Java Persistence query language SELECT statement or for a criteria query
     * @throws TransactionRequiredException
     *             if there is no transaction
     * @throws QueryTimeoutException
     *             if the statement execution exceeds the query timeout value set and only the statement is rolled back
     * @throws PersistenceException
     *             if the query execution exceeds the query timeout value set and the transaction is rolled back
     */
    public Integer executeByNamedQuery(String queryName, List<Parameter> params);

    /** 
     * Description： 查询数据总条数
     * @param queryName hsqlName
     * @return int 数据总量
     * @author name：liujie <br>email: jie_liu1@asdc.com.cn
     **/
    int getAllCount(String queryName);

    
    
    /**
     * 
     * Description : HQL动态sql查询 <br>
     * Create Time: 2017年3月15日 <br>
     * Create by : huayang_xu@asdc.com.cn <br>
     *
     * @param queryName 命名查询名称
     * @param params 解析参数
     * @return
     */
    public List<?> queryByNamedQueryDynamic(String queryName, List<Parameter> params);

    /**
     * 
     * Description : HQL动态sql查询 <br>
     * Create Time: 2017年3月17日 <br>
     * Create by : huayang_xu@asdc.com.cn <br>
     *
     * @param queryName 命名查询名称
     * @param params 解析参数
     * @return
     */
	public List<?> queryByNamedQueryDynamic(String queryName, Object... params);

	/**
	 * 
	 * Description : 原生sql动态查询 <br>
	 * Create Time: 2017年3月17日 <br>
	 * Create by : huayang_xu@asdc.com.cn <br>
	 *
	 * @param queryName 命名查询名称
	 * @param params 解析参数
	 * @return 返回泛型为Map
	 */
	public List<?> queryByNamedQueryDynamicNative(String queryName, List<Parameter> params);
	
	/**
	 * 
	 * Description : 原生sql动态查询 <br>
	 * Create Time: 2017年3月17日 <br>
	 * Create by : huayang_xu@asdc.com.cn <br>
	 *
	 * @param queryName 命名查询名称
	 * @param params 解析参数
	 * @return 返回泛型为Map
	 */
	public List<?> queryByNamedQueryDynamicNative(String queryName, Object... params);
	
	/**
	 * 
	 * Description : HQL动态sql分页查询 <br>
	 * Create Time: 2017年3月17日 <br>
	 * Create by : huayang_xu@asdc.com.cn <br>
	 *
	 * @param queryName 命名查询名称
	 * @param start 开始位置
	 * @param limit 限制条数
	 * @param params 解析参数
	 * @return
	 */
	public List<?> queryByNamedQueryDynamic(String queryName, Integer start, Integer limit, List<Parameter> params)throws ServiceException;

	/**
	 * 
	 * Description : HQL动态sql分页查询 <br>
	 * Create Time: 2017年3月17日 <br>
	 * Create by : huayang_xu@asdc.com.cn <br>
	 *
	 * @param queryName 命名查询名称
	 * @param start 开始位置
	 * @param limit 限制条数
	 * @param params 解析参数
	 * @return
	 */
	public List<?> queryByNamedQueryDynamic(String queryName, Integer start, Integer limit, Object... params)throws ServiceException;

	/**
	 * 
	 * Description : 原生sql分页查询 <br>
	 * Create Time: 2017年3月17日 <br>
	 * Create by : huayang_xu@asdc.com.cn <br>
	 *
	 * @param queryName 命名查询名称
	 * @param start 开始位置
	 * @param limit 查询上限
	 * @param params  查询参数
	 * @return 返回泛型为Map
	 * @throws ServiceException
	 */
	public List<?> queryByNamedQueryDynamicNative(String queryName, Integer start, Integer limit, Object... params) throws ServiceException;
	
	/**
	 * 
	 * Description : 原生sql分页查询 <br>
	 * Create Time: 2017年3月17日 <br>
	 * Create by : huayang_xu@asdc.com.cn <br>
	 *
	 * @param queryName 命名查询名称
	 * @param start 开始位置
	 * @param limit 查询上限
	 * @param params 查询参数
	 * @return 返回泛型为Map
	 * @throws ServiceException
	 */
	public List<?> queryByNamedQueryDynamicNative(String queryName, Integer start, Integer limit, List<Parameter> params) throws ServiceException;

	/**
	 * 
	 * Description : 原生sql查询 <br>
	 * Create Time: 2017年3月27日 <br>
	 * Create by : huayang_xu@asdc.com.cn <br>
	 *
	 * @param queryName 命名查询名称
	 * @param params 查询参数
	 * @param c 返回实体类型
	 * @return The list in the specified object
	 */
	public List<?> queryByNamedQueryDynamicNative(String queryName, List<Parameter> params, Class<?> c);

	/**
	 * 
	 * Description : 原生sql查询 <br>
	 * Create Time: 2017年3月27日 <br>
	 * Create by : huayang_xu@asdc.com.cn <br>
	 *
	 * @param queryName 命名查询名称
	 * @param c 返回实体类型
	 * @param params params 查询参数 默认封装为一个key为params的map，freemarker中调用此key的值即可
	 * @return The list in the specified object
	 */
	public List<?> queryByNamedQueryDynamicNative(String queryName, Class<?> c, Object... params);

	/**
	 * 
	 * Description : 原生sql分页查询 <br>
	 * Create Time: 2017年3月27日 <br>
	 * Create by : huayang_xu@asdc.com.cn <br>
	 *
	 * @param queryName 命名查询名称
	 * @param start 开始位置
	 * @param limit 查询上限
	 * @param c 泛型对象
	 * @param params 查询参数 默认封装为一个key为params的map，freemarker中调用此key的值即可
	 * @return
	 * @throws ServiceException
	 */
	public List<?> queryByNamedQueryDynamicNative(String queryName, Integer start, Integer limit, Class<?> c, Object... params) throws ServiceException;
	
	/**
	 * 
	 * Description : 原生sql分页查询 <br>
	 * Create Time: 2017年3月27日 <br>
	 * Create by : huayang_xu@asdc.com.cn <br>
	 *
	 * @param queryName 命名查询名称
	 * @param start 开始位置
	 * @param limit 查询上限
	 * @param params  查询参数
	 * @param c 泛型对象
	 * @return
	 * @throws ServiceException
	 */
	public List<?> queryByNamedQueryDynamicNative(String queryName, Integer start, Integer limit, List<Parameter> params, Class<?> c) throws ServiceException;

	/**
	 * 
	 * Description : get count by a counting sql defined by named queryO <br>
	 * Create Time: 2017年3月27日 <br>
	 * Create by : huayang_xu@asdc.com.cn <br>
	 *
	 * @param queryName 命名查询名称
	 * @param params 查询参数 默认封装为一个key为params的map，freemarker中调用此key的值即可
	 * @return
	 */
	public Integer getCountByNamedQueryDynamic(String queryName, Object... params);

	/**
	 * 
	 * Description : get count by a counting sql defined by named query <br>
	 * Create Time: 2017年3月27日 <br>
	 * Create by : huayang_xu@asdc.com.cn <br>
	 *
	 * @param queryName 命名查询名称
	 * @param params 查询参数
	 * @return
	 */
	public Integer getCountByNamedQueryDynamic(String queryName, List<Parameter> params);
	
	/**
	 * 
	 * Description : get sigle result by given named sql <br>
	 * Create Time: 2017年3月27日 <br>
	 * Create by : huayang_xu@asdc.com.cn <br>
	 *
	 * @param queryName 命名查询名称
	 * @param params 查询参数 默认封装为一个key为params的map，freemarker中调用此key的值即可
	 * @return
	 */
	public Object getSingleResultByNamedQueryDynamic(String queryName, Object... params);

	/**
	 * 
	 * Description : get sigle result by given named sql <br>
	 * Create Time: 2017年3月27日 <br>
	 * Create by : huayang_xu@asdc.com.cn <br>
	 *
	 * @param queryName 命名查询名称
	 * @param params 查询参数
	 * @return
	 */
	public Object getSingleResultByNamedQueryDynamic(String queryName, List<Parameter> params);

	/**
	 * 
	 * Description : Execute an update or delete statement. by named query <br>
	 * Create Time: 2017年3月27日 <br>
	 * Create by : huayang_xu@asdc.com.cn <br>
	 *
	 * @param queryName 命名查询名称
	 * @param params 查询参数 默认封装为一个key为params的map，freemarker中调用此key的值即可
	 * @return
	 */
	public Integer executeByNamedQueryDynamic(String queryName, Object... params);

	/**
	 * 
	 * Description :   Execute an update or delete statement. by named query<br>
	 * Create Time: 2017年3月27日 <br>
	 * Create by : huayang_xu@asdc.com.cn <br>
	 *
	 * @param queryName 命名查询名称
	 * @param params 查询参数
	 * @return
	 */
	public Integer executeByNamedQueryDynamic(String queryName, List<Parameter> params);

	/**
	 * 
	 * Description : 返回原生实体查询<br>
	 * Create Time: 2017年3月27日 <br>
	 * Create by : huayang_xu@asdc.com.cn <br>
	 *
	 * @param queryName 命名查询名称
	 * @param c 返回实体类型
	 * @param params 查询参数 默认封装为一个key为params的map，freemarker中调用此key的值即可
	 * @return
	 */
	public  Object getSingleResultByNamedQueryDynamicNative(String queryName, Class<?> c, Object... params);

	/**
	 * 
	 * Description : 返回原生实体查询 <br>
	 * Create Time: 2017年3月27日 <br>
	 * Create by : huayang_xu@asdc.com.cn <br>
	 *
	 * @param queryName 命名查询名称
	 * @param params 查询参数
	 * @param c 返回实体类型
	 * @return 
	 */
	public  Object getSingleResultByNamedQueryDynamicNative(String queryName, List<Parameter> params, Class<?> c);

	/**
	 * 
	 * Description : HQL查询单个实体,不存在返回null,多个返回第一条数据<br>
	 * Create Time: 2017年3月27日 <br>
	 * Create by : huayang_xu@asdc.com.cn <br>
	 *
	 * @param queryName 命名查询名称
	 * @param params 查询参数
	 * @return
	 */
	public Object findResultByNamedQueryDynamic(String queryName, Object... params);

	/**
	 * 
	 * Description : HQL查询单个实体,不存在返回null,多个返回第一条数据 <br>
	 * Create Time: 2017年3月27日 <br>
	 * Create by : huayang_xu@asdc.com.cn <br>
	 *
	 * @param queryName queryName 命名查询名称
	 * @param params 查询参数
	 * @return
	 */
	public Object findResultByNamedQueryDynamic(String queryName, List<Parameter> params);

	/**
	 * 
	 * Description : 原生HQL查询单个实体,不存在返回null,多个返回第一条数据 <br>
	 * Create Time: 2017年3月27日 <br>
	 * Create by : huayang_xu@asdc.com.cn <br>
	 *
	 * @param queryName 命名查询名称
	 * @param c 实体类型
	 * @param params 查询参数
	 * @return
	 */
	public Object findResultByNamedQueryDynamicNative(String queryName, Class<?> c, Object... params);

	/**
	 * 
	 * Description : 原生HQL查询单个实体,不存在返回null,多个返回第一条数据 <br>
	 * Create Time: 2017年3月27日 <br>
	 * Create by : huayang_xu@asdc.com.cn <br>
	 *
	 * @param queryName 命名查询名称
	 * @param params 查询参数
	 * @param c 实体类型
	 * @return
	 */
	public Object findResultByNamedQueryDynamicNative(String queryName, List<Parameter> params, Class<?> c);


	
}
