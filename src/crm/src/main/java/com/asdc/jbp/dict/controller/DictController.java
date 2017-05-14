/**
 * Project Name jbp-plugins-dict
 * File Name DictController.java
 * Package Name com.asdc.jbp.dict.controller
 * Create Time 2016年6月5日
 * Create by wangyishuai/yishuai_wang@asdc.com.cn <br>
 * Copyright © 2006, 2016, ASDC DAI. All rights reserved.
 */
package com.asdc.jbp.dict.controller;

import java.beans.IntrospectionException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.asdc.jbp.dict.entity.SysDict;
import com.asdc.jbp.dict.service.DictService;
import com.asdc.jbp.framework.dto.WorkDTO;
import com.asdc.jbp.framework.dto.bind.WorkDTOAware;
import com.asdc.jbp.framework.exception.ServiceException;
import com.asdc.jbp.framework.utils.ProxyStripper;

/** 
 * ClassName: DictController.java <br>
 * Description: <br>
 * Create by: wangyishuai/yishuai_wang@asdc.com.cn <br>
 * Create Time: 2016年6月5日<br>
 */
@Controller("DictController")
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class DictController implements WorkDTOAware {
	private WorkDTO workDTO;
	@Resource
	private DictService service;

	@Override
	public void setWorkDTO(WorkDTO workDTO) {
		this.workDTO = workDTO;
	}
	public void setService(DictService service) {
		this.service = service;
	}
	/**
	 * 
	 * Description：数据字典添加
	 * @throws ServiceException
	 * @return void
	 * @author name：wangyishuai <br>email: yishuai_wang@asdc.com.cn
	 *
	 */
	public void createDict() throws ServiceException {
		SysDict dict = workDTO.convertJsonToBeanByKey("dict", SysDict.class);
		
		if(dict.getIsNature() == null){
			dict.setIsNature(true);
		}
		service.createDictionary(dict);		
	} 
	
	/**
	 * 
	 * Description：模糊查询
	 * @throws ServiceException
	 * @throws IntrospectionException
	 * @return void
	 * @author name：wangyishuai
	 *
	 */
	public void queryFuzzyAllDict() throws ServiceException, IntrospectionException {
		int start = workDTO.getStart();
		int limit = workDTO.getLimit();
		SysDict queryCondition = workDTO.convertJsonToBeanByKey("pageQuery", SysDict.class);
		if(queryCondition.getId() == null){
			queryCondition.setId(1);
		}
		List<SysDict> dictList=  (List<SysDict>) service.queryDictCountByLikeQuery(start, limit,queryCondition);
		int totle = service.querySysDictByManyfieldsCount(queryCondition);
		workDTO.setTotle(totle);
	    workDTO.setResult(ProxyStripper.cleanFromProxies(dictList));
	}
	
	
	/**
	 * 
	 * Description：数据字典修改
	 * @throws ServiceException
	 * @return void
	 * @author name：wangyishuai <br>email: yishuai_wang@asdc.com.cn
	 *
	 */
	public void updateDict() throws ServiceException {
		SysDict dict = workDTO.convertJsonToBeanByKey("dict", SysDict.class);
		service.updateDictionary(dict);
	}
	
	/**
	 *  
	 * Description：数据字典删除
	 * @throws ServiceException
	 * @return void
	 * @author name：wangyishuai <br>email: yishuai_wang@asdc.com.cn
	 *
	 */
	public void deleteDict() throws ServiceException {
		int dictid=workDTO.getInteger("dictId");
		service.removeDictionariesById(dictid);
	}
	/**
	 * 
	 * Description：恢复字典数据
	 * @throws ServiceException
	 * @return void
	 * @author name：wangyishuai
	 *
	 */
	public void reciveDict() throws ServiceException {
		int dictid=workDTO.getInteger("dictId");
		service.reciveDictionariesById(dictid);
	}
	
	
	/**
	 * 
	 * Description：批量删除
	 * @throws ServiceException
	 * @return void
	 * @author name：wangyishuai
	 *
	 */
	@SuppressWarnings("unchecked")
	public void deleteDicts() throws ServiceException {
		Map<String, Object> map = workDTO.convertJsonToMapByKey("pageQuery");
		List<Integer> dictIds =(List<Integer>) map.get("dictIdJson");
		service.removeSysDicts(dictIds);
		workDTO.setResult(true);
	}
	
	/**
	 * 
	 * Description：批量恢复
	 * @throws ServiceException
	 * @return void
	 * @author name：wangyishuai
	 *
	 */
	@SuppressWarnings("unchecked")
	public void recoverDicts() throws ServiceException {
		Map<String, Object> map = workDTO.convertJsonToMapByKey("pageQuery");
		List<Integer> dictIds =(List<Integer>) map.get("dictIdJson");
		service.recoverDicts(dictIds);
		workDTO.setResult(true);
	}
	/**
	 * 
	 * Description：前台第一次得到的树的数据
	 * @throws ServiceException
	 * @return void
	 * @author name：yangxuan <br>email: xuan_yang@asdc.com.cn
	 */
	public void getDictTreeParent() throws ServiceException{
		List<Map<String,Object>>  dictTreeList = new ArrayList<Map<String,Object>>();
		SysDict dict = service.getSysDictById(1);
		String dictId = workDTO.get("dictId");
		
		Map<String,Object>  map = new HashMap<String,Object>();
		map.put("label", dict.getValue());
    	map.put("id", dict.getId());
    	map.put("controller","DictController");
    	map.put("method", "getDictTreeChildren");
    	int hideDictid = 0; 
    	
    	List<SysDict> childrenList = service.getParentById(1);
        if(dictId != null && !dictId.equals("")){
        	hideDictid = Integer.valueOf(dictId);
        	map.put("hideCommon", hideDictid);
        }
		// 得到所有的子类
		if (childrenList != null && childrenList.size() > 0 && dictId != null && !dictId.equals("")){
			map.put("type", "folder");
			map.put("children", getDictTreeById(childrenList,hideDictid));
		}else if(childrenList != null && childrenList.size() > 0 && (dictId == null || "".equals(dictId))){
			map.put("type", "folder");
			map.put("children", getDictTreeById(childrenList));
		}else if(childrenList == null || childrenList.size() <= 0){
			map.put("type", "doc");
		}
		dictTreeList.add(map);
		workDTO.setResult(dictTreeList);
    }
	/**
	 * 
	 * Description：点击树的父节点，请求子节点的数据
	 * @throws ServiceException
	 * @return void
	 * @author name：yangxuan <br>email: xuan_yang@asdc.com.cn
	 *
	 */
	public void getDictTreeChildren() throws ServiceException{
		String dictId = workDTO.get("commonId");
		int dictid = Integer.valueOf(dictId);
		String hideDictId = workDTO.get("commonHideId");
		List<Map<String,Object>>  dictTreeList = new ArrayList<Map<String,Object>>();
		List<SysDict> childrenList = service.getParentById(dictid);
		if(hideDictId !=null && !hideDictId.equals("")){
			 int hideId = Integer.valueOf(hideDictId);
			 dictTreeList = getDictTreeById(childrenList,hideId);
		}else{
			dictTreeList = getDictTreeById(childrenList);
		}
		workDTO.setResult(dictTreeList);
	}
	
	private List<Map<String,Object>> getDictTreeById(List<SysDict> childrenList) throws ServiceException {
		List<Map<String,Object>>  dictTreeList = new ArrayList<Map<String,Object>>();
		// 得到所有的子类
		if (childrenList != null && childrenList.size() > 0) {
			for (int i = 0; i < childrenList.size(); i++) {
				    Map<String,Object>  map = new HashMap<String,Object>();
				    SysDict dict =childrenList.get(i);
				    map.put("label", dict.getValue());
			    	map.put("id", dict.getId());
			    	map.put("controller","DictController");
			    	map.put("method", "getDictTreeChildren");
					if (service.getParentById(dict.getId()).size() > 0) {
						map.put("type", "folder");
						map.put("children", getNullListMap());
					} else {
						map.put("type", "doc");
					}
					dictTreeList.add(map);
			}
		}
		return dictTreeList;
	}
	public List<Map<String,Object>> getNullListMap(){
		List<Map<String,Object>>  listDictTreeNode = new ArrayList<Map<String,Object>>();
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("label", "");
		map.put("type", "");
		listDictTreeNode.add(map);
		return listDictTreeNode;
	}
	/**
	 * 
	 * Description：过滤掉自己不能选择本身的上级字典
	 * @param id
	 * @return
	 * @throws ServiceException
	 * @return String
	 * @author name：yangxuan <br>email: xuan_yang@asdc.com.cn
	 *
	 */
	private List<Map<String,Object>> getDictTreeById(List<SysDict> childrenList, int hideDictId) throws ServiceException {
		List<Map<String,Object>>  dictTreeList = new ArrayList<Map<String,Object>>();
		// 得到所有的子类
		if (childrenList != null && childrenList.size() > 0) {
			for (int i = 0; i < childrenList.size(); i++) {
				Map<String,Object>  map = new HashMap<String,Object>();
			    SysDict dict =childrenList.get(i);
				if(dict.getId() != hideDictId){
				    map.put("label", dict.getValue());
			    	map.put("id", dict.getId());
			    	map.put("hideCommon", hideDictId);
			    	map.put("controller","DictController");
			    	map.put("method", "getDictTreeChildren");
					List<SysDict> dictList = service.getParentById(dict.getId());
					if(dictList.size() > 0){
						if (dictList.size() == 1 && dictList.get(0).getId() == hideDictId) {
							map.put("type", "doc");
						}else{
							map.put("type", "folder");
							map.put("children",getNullListMap());
						}
					}else{
						map.put("type", "doc");
					}
				  dictTreeList.add(map);
				}
			}
		}
		return dictTreeList;
	}
		
	/**
     * 
     * Description：分页查询
     * @param start 第几条开始取
     * @param limit 取几条数据
     * @param queryCondition 查询条件
     * @throws ServiceException
     * @return List<SysDict>
     * @author name：wangyishuai <br>email: yishuai_wang@asdc.com.cn
     *
     */
    public List<SysDict> queryDictByPagingOrQueryCondition(int start, int limit,SysDict queryCondition) throws ServiceException {	
		List<SysDict> queryByNamedQuery = (List<SysDict>) service.queryDictCountByLikeQuery(start, limit,queryCondition);
		return queryByNamedQuery;
	}
    
	public Integer queryDictCountByQueryCondtion(SysDict queryCondition) throws ServiceException {
		int totle = service.querySysDictByManyfieldsCount(queryCondition);
		return totle;
	}
	
	/** 
     * Description：根据父类id得到其父类以及子类的列表
     * @param dictFirstNumber 第几条开始
     * @param limit 取几条数据
     * @param parentId 父级id
     * @throws ServiceException
     * @return List<SysDict>
     * @author name：wangyishuai <br>email: yishuai_wang@asdc.com.cn
     **/
    public List<SysDict> getParentandChildrenListByParentId(int start,int limit,int parentId) throws ServiceException{
    	// 定义一个list集合，用来存放父部门（单个对象）和子部门信息（列表） 
        List<SysDict>  dictList= new ArrayList<SysDict>();        
         //查询得到点击tree节点本身        
        SysDict sd_parent =service.getSysDictById(parentId);        
        //存放本身（对象）到大的list中																			        
        dictList.add(sd_parent);
        dictList.addAll(service.queryDictChildrenByParentId(start,limit-1,parentId));
        return dictList;
    }
    
    /**
     * 
     * Description：根据nature得到其子类的列表
     * @param nature 字典类型
     * @throws ServiceException
     * @return List<SysDict>
     * @author name：wangyishuai
     *
     */
    public void getDictChildrenList(String nature) throws ServiceException{
    	List<SysDict> listsys= service.queryDictsByNature(nature);
    	workDTO.setResult(listsys);   	     
    }
    
    /**
     *  
     * Description：根据nature和code查询
     * @throws ServiceException
     * @return void
     * @author name：wangyishuai <br>email: yishuai_wang@asdc.com.cn
     * @throws IntrospectionException 
     *
     */
    public void getDictByNatureAndCode() throws ServiceException, IntrospectionException {
        String nature = workDTO.convertJsonToBeanByKey("nature", String.class);
        String code = workDTO.convertJsonToBeanByKey("code", String.class);
        SysDict queryByNatureAndCode = service.getDictByNatureAndCode(nature, code);
        workDTO.setResult(ProxyStripper.cleanFromProxies(queryByNatureAndCode));
    }
    /**
     *  
     * Description：根据nature和code查询
     * @throws ServiceException
     * @return void
     * @author name：wangyishuai <br>email: yishuai_wang@asdc.com.cn
     * @throws IntrospectionException 
     *
     */
    public void checkcodeAndNature() throws ServiceException{
    	  SysDict dict = workDTO.convertJsonToBeanByKey("dict", SysDict.class);
    	  String nature = dict.getNature();
          String code = dict.getCode();
          int count = service.checkcodeAndNature(nature, code);
          if(count == 0 ){
        	  workDTO.put("success", false);
          }else{
        	  workDTO.put("error", true);
          }    
    }
   /**
    *  
    * Description：通过id得到子类集合
    * @throws ServiceException
    * @return void
    * @author name：yangxuan <br>email: xuan_yang@asdc.com.cn
    * @throws IntrospectionException 
    *
    */
   public void getChildrenListByDictId() throws ServiceException, IntrospectionException{
	   String dictStrId  = workDTO.get("dictId");
	   int dictId = Integer.valueOf(dictStrId);
	   List<SysDict> childrenList = service.getParentById(dictId);
	   workDTO.setResult(ProxyStripper.cleanFromProxies(childrenList));
   }
   //菜单下拉框是否可用
   public void getSelectDictInfo() throws ServiceException, IntrospectionException{
	  String code = workDTO.get("code");
	  String nature = workDTO.get("nature");
	  List<SysDict> dictList = service.getSelectDictInfo(nature, code);
	  workDTO.setResult(ProxyStripper.cleanFromProxies(dictList));
   }
   
	public void queryFuzzyAllDictOrder() throws ServiceException, IntrospectionException {
		SysDict sd = new SysDict();
		sd.setIsEnabled(true);
		sd.setId(-10020001);
		List<SysDict> dictList=  (List<SysDict>) service.queryDictCountByOrderBy(1, 5,sd);
	    workDTO.setResult(ProxyStripper.cleanFromProxies(dictList));
	}
   
}
