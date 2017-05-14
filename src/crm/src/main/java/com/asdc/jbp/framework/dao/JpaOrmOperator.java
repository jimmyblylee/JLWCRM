/**
 * Project Name : jbp-framework <br>
 * File Name : JpaOrmOperator.java <br>
 * Package Name : com.asdc.jbp.framework.dao <br>
 * Create Time : Apr 30, 2016 <br>
 * Create by : xiangyu_li@asdc.com.cn <br>
 * Copyright © 2006, 2016, ASDC DAI. All rights reserved.
 */
package com.asdc.jbp.framework.dao;

import java.util.List;

import javax.persistence.EntityExistsException;
import javax.persistence.PersistenceException;
import javax.persistence.TransactionRequiredException;

/**
 * ClassName : JpaOrmOperator <br>
 * Description : JPA OR Mapping entities operator <br>
 * Create Time : Apr 30, 2016 <br>
 * Create by : xiangyu_li@asdc.com.cn <br>
 *
 */
public interface JpaOrmOperator extends NamedQueryOperator {

    /**
     * Description : Make an instance managed and persistent. <br>
     * Create Time: Apr 30, 2016 <br>
     * Create by : xiangyu_li@asdc.com.cn <br>
     *
     * @param entity
     *            entity instance
     * @throws EntityExistsException
     *             if the entity already exists. (If the entity already exists, the <code>EntityExistsException</code> may be thrown when the persist operation
     *             is invoked, or the <code>EntityExistsException</code> or another <code>PersistenceException</code> may be thrown at flush or commit time.)
     * @throws IllegalArgumentException
     *             if the instance is not an entity
     * @throws TransactionRequiredException
     *             if invoked on a container-managed entity manager of type <code>PersistenceContextType.TRANSACTION</code> and there is no transaction
     */
    public void persist(Object entity);

    /**
     * Description : Merge the state of the given entity into the current persistence context. <br>
     * Create Time: Apr 30, 2016 <br>
     * Create by : xiangyu_li@asdc.com.cn <br>
     *
     * @param entity
     *            entity instance
     * @return the managed instance that the state was merged to
     * @throws IllegalArgumentException
     *             if instance is not an entity or is a removed entity
     * @throws TransactionRequiredException
     *             if invoked on a container-managed entity manager of type <code>PersistenceContextType.TRANSACTION</code> and there is no transaction
     */
    public <T> T merge(T entity);

    /**
     * Description : Remove the entity instance. <br>
     * Create Time: Apr 30, 2016 <br>
     * Create by : xiangyu_li@asdc.com.cn <br>
     *
     * @param entity
     *            entity instance
     * @throws IllegalArgumentException
     *             if the instance is not an entity or is a detached entity
     * @throws TransactionRequiredException
     *             if invoked on a container-managed entity manager of type <code>PersistenceContextType.TRANSACTION</code> and there is no transaction
     */
    public void remove(Object entity);

    /**
     * Description : Find by primary key. Search for an entity of the specified class and primary key. If the entity instance is contained in the persistence
     * context, it is returned from there. <br>
     * Create Time: Apr 30, 2016 <br>
     * Create by : xiangyu_li@asdc.com.cn <br>
     *
     * @param entityClass
     *            entity class
     * @param primaryKey
     *            primary key
     * @return the found entity instance or null if the entity does not exist
     */
    public <T> T find(Class<T> entityClass, Object primaryKey);

    /**
     * Description : Check if the instance is a managed entity instance belonging to the current persistence context. <br>
     * Create Time: Apr 30, 2016 <br>
     * Create by : xiangyu_li@asdc.com.cn <br>
     *
     * @param entity
     *            entity instance
     * @return boolean indicating if entity is in persistence context
     * @throws IllegalArgumentException
     *             if not an entity
     */
    public boolean contains(Object entity);

    /**
     * Description : Remove the given entity from the persistence context, causing a managed entity to become detached. Unflushed changes made to the entity if
     * any (including removal of the entity), will not be synchronized to the database. Entities which previously referenced the detached entity will continue
     * to reference it. <br>
     * Create Time: Apr 30, 2016 <br>
     * Create by : xiangyu_li@asdc.com.cn <br>
     *
     * @param entity
     *            entity instance
     * @throws IllegalArgumentException
     *             if the instance is not an entity
     * @since Java Persistence 2.0
     */
    public void detach(Object entity);

    /**
     * Description : Synchronize the persistence context to the underlying database. <br>
     * Create Time: Apr 30, 2016 <br>
     * Create by : xiangyu_li@asdc.com.cn <br>
     *
     * @throws TransactionRequiredException
     *             if there is no transaction
     * @throws PersistenceException
     *             if the flush fails
     */
    public void flush();

    /**
     * Description : Clear the persistence context, causing all managed entities to become detached. Changes made to entities that have not been flushed to the
     * database will not be persisted. <br>
     * Create Time: Apr 30, 2016 <br>
     * Create by : xiangyu_li@asdc.com.cn <br>
     */
    public void clear();

	public List<?> queryListByNativeQuery(String string);

	public Integer getCountByNativeQuery(String string);

	public List<?> queryListByNativeQuery(String string, int start,int limit);
}
