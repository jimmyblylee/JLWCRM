/**
 * Project Name : jbp-plugin-dict-service <br>
 * File Name : DictService.java <br>
 * Package Name : com.asdc.jbp.dict.service <br>
 * Create Time : Apr 14, 2016 <br>
 * Create by : xiangyu_li@asdc.com.cn <br>
 * Copyright © 2006, 2016, ASDC DAI. All rights reserved.
 */
package com.asdc.jbp.dict.service;

import java.util.List;

import com.asdc.jbp.dict.entity.SysDict;
import com.asdc.jbp.framework.exception.ServiceException;

/**
 * ClassName : DictService <br>
 * Description : Operation service of SysDict <br>
 * Create Time : Apr 14, 2016 <br>
 * Create by : xiangyu_li@asdc.com.cn <br>
 *
 */
public interface DictService {

    /**
     * Description : get the SysDict by given nature string and code string <br>
     * Create Time: Apr 14, 2016 <br>
     * Create by : xiangyu_li@asdc.com.cn <br>
     *
     * @param nature
     * @param code
     * @return the entity of SysDict with given nature and code
     * @throws ServiceException
     *             nature or code is null
     */
    public SysDict getDictByNatureAndCode(String nature, String code) throws ServiceException;

    /**
     * Description : get SysDict entity by given id <br>
     * Create Time: Apr 28, 2016 <br>
     * Create by : xiangyu_li@asdc.com.cn <br>
     *
     * @param dictId
     * @return the entity of SysDict with given id
     * @throws ServiceException
     *             can not find it
     */
    public SysDict getSysDictById(Integer dictId) throws ServiceException;

    /**
     * Description : query SysDict by given nature string <br>
     * Create Time: Apr 14, 2016 <br>
     * Create by : xiangyu_li@asdc.com.cn <br>
     *
     * @param nature
     * @param start
     * @param limit
     *            -1 for skipping page limit, and return all the SysDict
     * @return the list of SysDict with given nature
     * @throws ServiceException
     *             nature is null or start is less than 0
     */
    public List<SysDict> queryDictsByNature(String nature) throws ServiceException;

    /**
     * Description : get the count of SysDict by given nature string <br>
     * Create Time: Apr 14, 2016 <br>
     * Create by : xiangyu_li@asdc.com.cn <br>
     *
     * @param nature
     * @return the count of SysDict with given nature
     * @throws ServiceException
     *             nature is null
     */
    public int getDictCountByNature(String nature) throws ServiceException;

    /**
     * Description : query all the SysDict <br>
     * Create Time: Apr 14, 2016 <br>
     * Create by : xiangyu_li@asdc.com.cn <br>
     *
     * @param start
     * @param limit
     *            -1 for skipping page limit, and return all the SysDict
     * @return the list of SysDict for all data, and limited by page start and page limit
     * @throws ServiceException
     *             start is less than 0
     */
    public List<SysDict> queryAllDicts(int start, int limit) throws ServiceException;

    /**
     * Description : get the count of all SysDict <br>
     * Create Time: Apr 14, 2016 <br>
     * Create by : xiangyu_li@asdc.com.cn <br>
     *
     * @return the totle count of all SysDict
     */
    public int getAllDictCount();

    /**
     * Description : create SysDict by given dict <br>
     * Create Time: Apr 14, 2016 <br>
     * Create by : xiangyu_li@asdc.com.cn <br>
     *
     * @param dict
     * @return the entity created with filling infos: id, pinyin, enabled(true as default)
     * @throws ServiceException
     *             dict is null, or validation failed
     */
    public SysDict createDictionary(SysDict dict) throws ServiceException;

    /**
     * Description : create SysDict by given list<br>
     * Create Time: Apr 14, 2016 <br>
     * Create by : xiangyu_li@asdc.com.cn <br>
     *
     * @param dictList
     * @throws ServiceException
     *             dictList is null or there are some entity can not pass the validation
     */
    public void createDictionary(List<SysDict> dictList) throws ServiceException;

    /**
     * Description : udpate SysDict by given dict <br>
     * Create Time: Apr 14, 2016 <br>
     * Create by : xiangyu_li@asdc.com.cn <br>
     *
     * @param dict
     * @return the updated SysDict with new pinyin generated by given value of SysDict
     * @throws ServiceException
     *             dict is null or entity validation failed
     */
    public SysDict updateDictionary(SysDict dict) throws ServiceException;

    /**
     * Description : remove SysDict by given id <br>
     * Create Time: Apr 14, 2016 <br>
     * Create by : xiangyu_li@asdc.com.cn <br>
     *
     * @param dictId
     * @return 
     * @throws ServiceException
     *             given id is null or could not find SysDict by given id
     */
    public void removeDictionariesById(Integer dictId) throws ServiceException;

    /**
     * Description : remove SysDict by given nature <br>
     * Create Time: Apr 14, 2016 <br>
     * Create by : xiangyu_li@asdc.com.cn <br>
     *
     * @param nature
     * @return 
     * @throws ServiceException
     *             nature is null
     */
    public int removeDictionaryByNature(String nature) throws ServiceException;

	/**
	 * 
	 * Description：通过父级字典id获取其子级
	 * @param dictId 父级id
	 * @throws ServiceException
	 * @return List<SysDict>
	 * @author name：wangyishuai
	 *
	 */
	List<SysDict> getParentById(int dictId) throws ServiceException;

	/**
	 * 
	 * Description：根据父类dictId得到其子类的列表
	 * @param start 从第几条开始
	 * @param limit 取几条数据
	 * @param dictId 父级id
	 * @throws ServiceException
	 * @return List<SysDict>
	 * @author name：wangyishuai
	 *
	 */
	List<SysDict> queryDictChildrenByParentId(int start, int limit, int dictId)
			throws ServiceException;

	/**
	 * 
	 * Description：模糊查询获取数据总个数
	 * @param dict 查询条件
	 * @throws ServiceException
	 * @return Integer
	 * @author name：wangyishuai
	 *
	 */
	Integer querySysDictByManyfieldsCount(SysDict dict)
			throws ServiceException;

	/**
	 * 
	 * Description：字典模糊查询获取其 数据
	 * @param start 从第几条开始
	 * @param limit 取几条数据
	 * @param dict 查询条件
	 * @throws ServiceException
	 * @return List<SysDict>
	 * @author name：wangyishuai
	 *
	 */
	public List<SysDict> queryDictCountByLikeQuery(int start,
			int limit, SysDict dict) throws ServiceException;

	/** 
	 * Description：恢复字典数据
	 * @param dictid 字典id
	 * @return void
	 * @author name：wangyishuai
	 **/
	public void reciveDictionariesById(int dictid);

	/** 
	 * Description：批量删除
	 * @param dictIds 字典id集
	 * @return void
	 * @author name：wangyishuai
	 * @throws ServiceException 
	 **/
	public void removeSysDicts(List<Integer> dictIds) throws ServiceException;

	/** 
	 * Description：批量恢复
	 * @param dictIds
	 * @return void
	 * @author name：wangyishuai
	 * @throws ServiceException 
	 **/
	public void recoverDicts(List<Integer> dictIds) throws ServiceException;

    /**
     * 
     * Description：查询上级字典为null的菜单（为了和机构、字典统一。这里采用null来查找。和菜单一样为空的只有一个）
     * @return
     * @throws ServiceException
     * @return SysDict
     * @author name：yangxuan <br>email: xuan_yang@asdc.com.cn
     *
     */
	public List<SysDict> queryParentDictIsNull() throws ServiceException;
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
	public int checkcodeAndNature(String nature, String code)throws ServiceException ;
    /**
     * 
     * Description：根据类型和编码得到对应的下拉框信息
     * @return
     * @throws ServiceException
     * @return List<SysDict>
     * @author name：yangxuan <br>email: xuan_yang@asdc.com.cn
     *
     */
    public List<SysDict> getSelectDictInfo(String nature, String code) throws ServiceException ;

	public List<SysDict> queryDictCountByOrderBy(int start, int limit, SysDict queryCondition)  throws ServiceException;

	public List<SysDict> queryChildrenByParentCode(String code);

	public SysDict queryCodeByValue(String code, String value);

	public String queryValueByCode(String code);
}
