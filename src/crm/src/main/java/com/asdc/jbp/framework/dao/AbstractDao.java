/**
 * Project Name : jbp-framework <br>
 * File Name : AbstractDao.java <br>
 * Package Name : com.asdc.jbp.framework.dao <br>
 * Create Time : Apr 30, 2016 <br>
 * Create by : xiangyu_li@asdc.com.cn <br>
 * Copyright © 2006, 2016, ASDC DAI. All rights reserved.
 */
package com.asdc.jbp.framework.dao;

import java.io.StringWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.hibernate.MappingException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.engine.spi.NamedQueryDefinition;
import org.hibernate.engine.spi.NamedSQLQueryDefinition;
import org.hibernate.internal.SessionFactoryImpl;
import org.hibernate.transform.Transformers;

import com.asdc.jbp.framework.exception.ServiceException;
import com.asdc.jbp.framework.message.Messages;

import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;

/**
 * ClassName : AbstractDao <br>
 * Description : AbstractDao provides ormapping operation support <br>
 * Create Time : Apr 30, 2016 <br>
 * Create by : xiangyu_li@asdc.com.cn <br>
 *
 */
public abstract class AbstractDao implements JpaOrmOperator {

    protected abstract EntityManager getEntityManager();

    private EntityManager em() {
        return getEntityManager();
    }

    private Query createNamedQuery(String queryName, Object... params) {
        Query query = em().createNamedQuery(queryName);
        if (params != null && params.length > 0) {
            int pos = 0;
            for (Object param : params) {
                query.setParameter(pos++, param);
            }
        }
        return query;
    }

    private Query createNamedQuery(String queryName, List<Parameter> params) {
        Query query = em().createNamedQuery(queryName);
        if (params != null && params.size() > 0) {
            for (Parameter param : params) {
                query.setParameter(param.getName(), param.getValue());
            }
        }
        return query;
    }
    
    
    private Query createNamedQueryDynamic(String queryName, Object... params) {
        Map<String,Object> paramsMap=new HashMap<String,Object>();
        paramsMap.put("params", params);
        return getNamedSql(queryName, paramsMap);
    }

    private Query createNamedQueryDynamic(String queryName, List<Parameter> params) {
    	Map<String,Object> paramsMap=new HashMap<String,Object>();
    	 if (params != null && params.size() > 0) {
             for (Parameter param : params) {
            	 paramsMap.put(param.getName(), param.getValue());
             }
         }    	     	 
    	return getNamedSql(queryName, paramsMap);
    }
    
    @Override
    public List<?> queryByNamedQueryDynamic(String queryName, List<Parameter> params){
    	return createNamedQueryDynamic(queryName, params).getResultList();
    }
    
    @Override
    public List<?> queryByNamedQueryDynamic(String queryName, Object... params){
    	return createNamedQueryDynamic(queryName, params).getResultList();
    }
    
    @Override
    public List<?> queryByNamedQueryDynamicNative(String queryName, List<Parameter> params){
    	Query nativeQuery =createNamedQueryDynamic(queryName, params);
    	nativeQuery.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
    	return nativeQuery.getResultList();
    }
    
    @Override
    public List<?> queryByNamedQueryDynamicNative(String queryName ,Object... params){
    	Query nativeQuery =createNamedQueryDynamic(queryName, params);
    	nativeQuery.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
    	return nativeQuery.getResultList();
    }
    
    @Override
    public List<?> queryByNamedQueryDynamicNative(String queryName, List<Parameter> params,Class<?> c){
    	Query nativeQuery =createNamedQueryDynamic(queryName, params);
    	nativeQuery.unwrap(SQLQuery.class).setResultTransformer(Transformers.aliasToBean(c));
    	return nativeQuery.getResultList();
    }
    @Override
    public List<?> queryByNamedQueryDynamicNative(String queryName, Class<?> c, Object... params){
    	Query nativeQuery =createNamedQueryDynamic(queryName, params);
    	nativeQuery.unwrap(SQLQuery.class).setResultTransformer(Transformers.aliasToBean(c));
    	return nativeQuery.getResultList();
    }
    
    @Override
    public List<?> queryByNamedQueryDynamic(String queryName, Integer start, Integer limit, List<Parameter> params) throws ServiceException {
    	if (start < 0) {
            throw new ServiceException("ERR_DAO_001", Messages.getMsg("ERR_DAO_MSG_START_LESS_THAN_ZERO"));
        }
    	return createNamedQueryDynamic(queryName, params).setFirstResult(start).setMaxResults(limit > 0 ? limit : Integer.MAX_VALUE).getResultList();
    }
    
    @Override
    public List<?> queryByNamedQueryDynamic(String queryName, Integer start, Integer limit, Object... params) throws ServiceException {
    	if (start < 0) {
            throw new ServiceException("ERR_DAO_001", Messages.getMsg("ERR_DAO_MSG_START_LESS_THAN_ZERO"));
        }
    	return createNamedQueryDynamic(queryName, params).setFirstResult(start).setMaxResults(limit > 0 ? limit : Integer.MAX_VALUE).getResultList();
    }
    
    @Override
    public List<?> queryByNamedQueryDynamicNative(String queryName, Integer start, Integer limit, Object... params) throws ServiceException {
    	if (start < 0) {
            throw new ServiceException("ERR_DAO_001", Messages.getMsg("ERR_DAO_MSG_START_LESS_THAN_ZERO"));
        }
    	Query nativeQuery =createNamedQueryDynamic(queryName, params);
    	nativeQuery.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
    	return nativeQuery.setFirstResult(start).setMaxResults(limit > 0 ? limit : Integer.MAX_VALUE).getResultList();    	
    }
    
    @Override
    public List<?> queryByNamedQueryDynamicNative(String queryName, Integer start, Integer limit, List<Parameter> params) throws ServiceException {
    	if (start < 0) {
            throw new ServiceException("ERR_DAO_001", Messages.getMsg("ERR_DAO_MSG_START_LESS_THAN_ZERO"));
        }
    	Query nativeQuery =createNamedQueryDynamic(queryName, params);
    	nativeQuery.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
    	return nativeQuery.setFirstResult(start).setMaxResults(limit > 0 ? limit : Integer.MAX_VALUE).getResultList();
    }
    
    @Override
    public List<?> queryByNamedQueryDynamicNative(String queryName, Integer start, Integer limit, Class<?>c, Object... params) throws ServiceException {
    	if (start < 0) {
            throw new ServiceException("ERR_DAO_001", Messages.getMsg("ERR_DAO_MSG_START_LESS_THAN_ZERO"));
        }
    	Query nativeQuery =createNamedQueryDynamic(queryName, params);
    	nativeQuery.unwrap(SQLQuery.class).setResultTransformer(Transformers.aliasToBean(c));
    	return nativeQuery.setFirstResult(start).setMaxResults(limit > 0 ? limit : Integer.MAX_VALUE).getResultList();
    }
    
    @Override
    public List<?> queryByNamedQueryDynamicNative(String queryName, Integer start, Integer limit, List<Parameter> params, Class<?>c) throws ServiceException {
    	if (start < 0) {
            throw new ServiceException("ERR_DAO_001", Messages.getMsg("ERR_DAO_MSG_START_LESS_THAN_ZERO"));
        }
    	Query nativeQuery =createNamedQueryDynamic(queryName, params);
    	nativeQuery.unwrap(SQLQuery.class).setResultTransformer(Transformers.aliasToBean(c));
    	return nativeQuery.setFirstResult(start).setMaxResults(limit > 0 ? limit : Integer.MAX_VALUE).getResultList();
    }
       
    @Override
    public List<?> queryByNamedQuery(String queryName, Object... params) {
        return createNamedQuery(queryName, params).getResultList();
    }

    @Override
    public List<?> queryByNamedQuery(String queryName, List<Parameter> params) {
        return createNamedQuery(queryName, params).getResultList();
    }

    @Override
    public List<?> queryByNamedQuery(String queryName, Integer start, Integer limit, Object... params) throws ServiceException {
        if (start < 0) {
            throw new ServiceException("ERR_DAO_001", Messages.getMsg("ERR_DAO_MSG_START_LESS_THAN_ZERO"));
        }
        return createNamedQuery(queryName, params).setFirstResult(start).setMaxResults(limit > 0 ? limit : Integer.MAX_VALUE).getResultList();
    }

    @Override
    public List<?> queryByNamedQuery(String queryName, Integer start, Integer limit, List<Parameter> params) {
        return createNamedQuery(queryName, params).setFirstResult(start).setMaxResults(limit > 0 ? limit : Integer.MAX_VALUE).getResultList();
    }

    @Override
    public Integer getCountByNamedQuery(String queryName, Object... params) {
        return ((Number) getSingleResultByNamedQuery(queryName, params)).intValue();
    }

    @Override
    public Integer getCountByNamedQuery(String queryName, List<Parameter> params) {
        return ((Number) getSingleResultByNamedQuery(queryName, params)).intValue();
    }

    @Override
    public Object getSingleResultByNamedQuery(String queryName, Object... params) {
        return createNamedQuery(queryName, params).getSingleResult();
    }

    @Override
    public Object getSingleResultByNamedQuery(String queryName, List<Parameter> params) {
        return createNamedQuery(queryName, params).getSingleResult();
    }

    @Override
    public Integer executeByNamedQuery(String queryName, Object... params) {
        return createNamedQuery(queryName, params).executeUpdate();
    }

    @Override
    public Integer executeByNamedQuery(String queryName, List<Parameter> params) {
        return createNamedQuery(queryName, params).executeUpdate();
    }
    
    @Override
    public int getAllCount(String queryName) {
        return Integer.parseInt(createNamedQuery(queryName).getSingleResult().toString());
    }

    @Override
    public void persist(Object entity) {
        em().persist(entity);
    }

    @Override
    public <T> T merge(T entity) {
        return em().merge(entity);
    }

    @Override
    public void remove(Object entity) {
        em().remove(entity);
    }

    @Override
    public <T> T find(Class<T> entityClass, Object primaryKey) {
        return em().find(entityClass, primaryKey);
    }

    @Override
    public boolean contains(Object entity) {
        return em().contains(entity);
    }

    @Override
    public void detach(Object entity) {
        em().detach(entity);
    }

    @Override
    public void flush() {
        em().flush();
    }

    @Override
    public void clear() {
        em().clear();
    }
    
    
    private Query createNativeQuery(String queryName) {
        Query query = em().createNativeQuery(queryName);
        return query;
    }
    /**
     * 通过原生sql查询得到List集合并进行分页
     * @param queryName
     * @return
     */
    public List<?> queryListByNativeQuery(String queryName,int start,int limit){
    	return createNativeQuery(queryName).setFirstResult(start).setMaxResults(limit > 0 ? limit : Integer.MAX_VALUE).getResultList();
    }
    /**
     * 通过原生sql查询得到List集合
     * @param queryName
     * @return
     */
    public List<?> queryListByNativeQuery(String queryName){
    	return createNativeQuery(queryName).getResultList();
    }
    /**
     * 通过原生sql查询得到对象
     * @param queryName
     * @return
     */
    public Object getSingleResultByNativeQuery(String queryName) {
        return createNativeQuery(queryName).getSingleResult();
    };
    /**
     * 通过原生sql查询得到条数
     * @param queryName
     * @return
     */
    public Integer getCountByNativeQuery(String queryName){
        return  ((Number)createNativeQuery(queryName).getSingleResult()).intValue();
    }
    
    /**
     * 
     * Description : 获取解析语句 <br>
     * Create Time: 2017年3月16日 <br>
     * Create by : huayang_xu@asdc.com.cn <br>
     *
     * @param queryName 命名查询名称
     * @param params	解析参数
     * @return
     */
    private Query getNamedSql(String queryName,Map<String,?> params){
    	String querySql="";
    	Session session = (Session) em().getDelegate();
		SessionFactoryImpl sessionFactory = (SessionFactoryImpl) session.getSessionFactory();
		NamedQueryDefinition nqd=sessionFactory.getNamedQuery(queryName);
		Query query;
		if ( nqd != null ) {
			querySql=processTemplate(queryName,nqd.getQueryString(),params);
			query = em().createQuery(querySql);
		}
		else {
			NamedSQLQueryDefinition nsqlqd = sessionFactory.getNamedSQLQuery( queryName );
			if ( nsqlqd==null ) {
				throw new MappingException( "Named query not known: " + queryName );
			}
			querySql=processTemplate(queryName,nsqlqd.getQueryString(),params);
			query = em().createNativeQuery(querySql);
		}

		return query;
    }
    
    /**
     * 
     * Description : 通过freemarker语法解析sql语句 <br>
     * Create Time: 2017年3月15日 <br>
     * Create by : huayang_xu@asdc.com.cn <br>
     *
     * @param queryName 命名查询名称
     * @param querySql	sql语句
     * @param params	解析参数
     * @return
     */
    private  String processTemplate(String queryName,String querySql,Map<String,?> params){
		StringWriter stringWriter = new StringWriter();
		Configuration configuration = new Configuration();
		StringTemplateLoader stringLoader = new StringTemplateLoader();
		stringLoader.putTemplate(queryName,querySql);
		configuration.setTemplateLoader(stringLoader);
		try {
			configuration.getTemplate(queryName).process(params, stringWriter);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return stringWriter.toString();
	}

    @Override
    public Integer getCountByNamedQueryDynamic(String queryName, Object... params) {
        return ((Number) getSingleResultByNamedQueryDynamic(queryName, params)).intValue();
    }

    @Override
    public Integer getCountByNamedQueryDynamic(String queryName, List<Parameter> params) {
        return ((Number) getSingleResultByNamedQueryDynamic(queryName, params)).intValue();
    }

    @Override
    public Object getSingleResultByNamedQueryDynamic(String queryName, Object... params) {
        return createNamedQueryDynamic(queryName, params).getSingleResult();
    }

    @Override
    public Object getSingleResultByNamedQueryDynamic(String queryName, List<Parameter> params) {
        return createNamedQueryDynamic(queryName, params).getSingleResult();
    }
    @Override
    public Object getSingleResultByNamedQueryDynamicNative(String queryName, Class<?> c, Object... params) {
    	Query nativeQuery =createNamedQueryDynamic(queryName, params);
    	nativeQuery.unwrap(SQLQuery.class).setResultTransformer(Transformers.aliasToBean(c));
        return nativeQuery.getSingleResult();
    }

    @Override
    public Object getSingleResultByNamedQueryDynamicNative(String queryName, List<Parameter> params, Class<?> c) {
    	Query nativeQuery =createNamedQueryDynamic(queryName, params);
    	nativeQuery.unwrap(SQLQuery.class).setResultTransformer(Transformers.aliasToBean(c));
        return nativeQuery.getSingleResult();
    }

    @Override
    public Integer executeByNamedQueryDynamic(String queryName, Object... params) {
        return createNamedQueryDynamic(queryName, params).executeUpdate();
    }

    @Override
    public Integer executeByNamedQueryDynamic(String queryName, List<Parameter> params) {
        return createNamedQueryDynamic(queryName, params).executeUpdate();
    }
    
    @Override
    public Object findResultByNamedQueryDynamic(String queryName, Object... params){
    	List<?> objs=createNamedQueryDynamic(queryName, params).getResultList();
    	if(objs.size()>0){
    		return objs.get(0);
    	}else{
    		return null;
    	}
    	
    }
    
    @Override
    public Object findResultByNamedQueryDynamic(String queryName, List<Parameter> params){
    	List<?> objs=createNamedQueryDynamic(queryName, params).getResultList();
    	if(objs.size()>0){
    		return objs.get(0);
    	}else{
    		return null;
    	}
    }
    
    @Override
    public Object findResultByNamedQueryDynamicNative(String queryName, Class<?> c, Object... params){
    	Query nativeQuery =createNamedQueryDynamic(queryName, params);
    	nativeQuery.unwrap(SQLQuery.class).setResultTransformer(Transformers.aliasToBean(c));
    	List<?> objs=nativeQuery.getResultList();
    	if(objs.size()>0){
    		return objs.get(0);
    	}else{
    		return null;
    	}
    }
    
    @Override
    public Object findResultByNamedQueryDynamicNative(String queryName, List<Parameter> params, Class<?> c){
    	Query nativeQuery =createNamedQueryDynamic(queryName, params);
    	nativeQuery.unwrap(SQLQuery.class).setResultTransformer(Transformers.aliasToBean(c));
    	List<?> objs=nativeQuery.getResultList();
    	if(objs.size()>0){
    		return objs.get(0);
    	}else{
    		return null;
    	}
    }
}
