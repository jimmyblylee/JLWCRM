/*
 * Project Name : jbp-plugin-dict-service <br>
 * File Name : DictServiceImpl.java <br>
 * Package Name : com.asdc.jbp.dict.service <br>
 * Create Time : Apr 14, 2016 <br>
 * Create by : xiangyu_li@asdc.com.cn <br>
 * Copyright © 2006, 2016, ASDC DAI. All rights reserved.
 */
package com.asdc.jbp.dict.service.impl;


import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.asdc.jbp.dict.dao.SysDictDao;
import com.asdc.jbp.dict.entity.SysDict;
import com.asdc.jbp.dict.service.DictService;
import com.asdc.jbp.framework.exception.ServiceException;
import com.asdc.jbp.framework.message.Messages;
import com.asdc.jbp.framework.utils.BeanUtils;
import com.asdc.jbp.framework.utils.PinyinUtils;

/**
 * ClassName : DictServiceImpl <br>
 * Description : service implementation of {@link DictService} <br>
 * Create Time : Apr 14, 2016 <br>
 * Create by : xiangyu_li@asdc.com.cn <br>
 *
 * @see DictService
 */

@Service
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Transactional(readOnly = true)
public class DictServiceImpl implements DictService {

    @Resource
    private SysDictDao dao;

    /*
     * (non-Javadoc)
     *
     * @see com.asdc.jbp.dict.service.DictSercie#getDictByNatureAndCode(java.lang.String, java.lang.String)
     */
    @Override
    public SysDict getDictByNatureAndCode(String nature, String code) throws ServiceException {
        if (nature == null || code == null) {
            throw new ServiceException("ERR_DICT_001", Messages.getMsg("dict", "ERR_DICT_MSG_NATURE_CODE_NULL"));
        }
        return dao.getSysDictByNatureAndCode(nature, code);
    }

    public SysDict getSysDictById(Integer id) throws ServiceException {

        SysDict dict = dao.getSysDictById(id);

        if (dict == null) {
            throw new ServiceException("ERR_DICT_001", Messages.getMsg("dict", "ERR_DICT_MSG_NO_RESULT_WITH_ID"), id);
        }
        return dict;
    }

    /*
     * (non-Javadoc)
     *
     * @see com.asdc.jbp.dict.service.DictSercie#queryDictsByNature(java.lang.String, int, int)
     */
    @Override
    public List<SysDict> queryDictsByNature(String nature) throws ServiceException {
        if (nature == null) {
            throw new ServiceException("ERR_DICT_002", Messages.getMsg("dict", "ERR_DICT_MSG_NATURE_NULL"));
        }
        List<SysDict> dictList = dao.queryDictsByNature(nature);

        List<SysDict> childrenDictList = new ArrayList<>();

        for (SysDict dict : dictList) {

            if (dict.getParent().getId() != 1) {

                childrenDictList.add(dict);
            }

        }
        return childrenDictList;
    }

    /*
     * (non-Javadoc)
     *
     * @see com.asdc.jbp.dict.service.DictSercie#getDictCountByNature(java.lang.String)
     */
    @Override
    public int getDictCountByNature(String nature) throws ServiceException {
        if (nature == null) {
            throw new ServiceException("ERR_DICT_002", Messages.getMsg("dict", "ERR_DICT_MSG_NATURE_NULL"));
        }
        return dao.getDictsCountByNature(nature);
    }

    /*
     * (non-Javadoc)
     *
     * @see com.asdc.jbp.dict.service.DictSercie#queryAllDicts(int, int)
     */
    @Override
    public List<SysDict> queryAllDicts(int start, int limit) throws ServiceException {
        if (start < 0) {
            throw new ServiceException("ERR_DICT_003", Messages.getMsg("dict", "ERR_DICT_003"));
        }
        return dao.queryAll(limit < 0 ? 0 : start, limit < 0 ? Integer.MAX_VALUE : limit);
    }

    /* (non-Javadoc)
     * @see com.asdc.jbp.dict.service.DictService#queryDictCountByLikeQuery(int, int, java.lang.String)
     */
    @Override
    public List<SysDict> queryDictCountByLikeQuery(int start, int limit, SysDict dict) throws ServiceException {

        if (dict.getCode() != null) {
            dict.setCode("%" + dict.getCode() + "%");
        } else {
            dict.setCode("%%");
        }
        List<SysDict> dictList = dao.querySysDeptByManyfields(start, limit, dict);

        List<SysDict> listdict;
        for (SysDict aDictList : dictList) {
            listdict = getParentById(aDictList.getId());
            if (listdict.size() > 0) {
                aDictList.setChilds("childs");
            }
        }
        return dictList;
    }

    @Override
    public List<SysDict> queryDictChildrenByParentId(int start, int limit, int id) throws ServiceException {
        if (limit == 0) {
            limit = 5;
        }
        return dao.queryChildrenDictByDictId(limit < 0 ? 0 : start, limit < 0 ? Integer.MAX_VALUE : limit, id);
    }

    /* (non-Javadoc)
     * @see com.asdc.jbp.dict.service.DictService#querySysDictByManyfieldsCount(java.lang.String)
     */
    @Override
    public Integer querySysDictByManyfieldsCount(SysDict dict) throws ServiceException {
        if (dict.getCode() != null) {
            dict.setCode("%" + dict.getCode() + "%");
        } else {
            dict.setCode("%%");
        }
        return dao.getCountByNamedQuery(dict);
    }

    /* (non-Javadoc)
     * @see com.asdc.jbp.dict.service.DictService#getParentById(int)
     */
    @Override
    public List<SysDict> getParentById(int id) throws ServiceException {
        return dao.getParentById(id);
    }

    /*
     * (non-Javadoc)
     *
     * @see com.asdc.jbp.dict.service.DictSercie#getAllDictCount()
     */
    @Override
    public int getAllDictCount() {
        return dao.getAllCount();
    }

    /*
     * (non-Javadoc)
     *
     * @see com.asdc.jbp.dict.service.DictSercie#createDictionary(com.asdc.jbp.dict.entity.SysDict)
     */
    @Override
    @Transactional
    public SysDict createDictionary(SysDict dict) throws ServiceException {
        if (dict == null || dict.getNature() == null || dict.getCode() == null) {
            throw new ServiceException("ERR_DICT_004", Messages.getMsg("dict", "ERR_DICT_MSG_CREATE_NULL"));
        }
        SysDict dataInDB = dao.getSysDictByNatureAndCode(dict.getNature(), dict.getCode());
        if (dataInDB != null) {
            throw new ServiceException("ERR_DICT_004", Messages.getMsg("dict", "ERR_DICT_MSG_DUPLICTED_NATURE_CODE"), dict.getNature(), dict.getCode());
        }
        dict.setPinyin(PinyinUtils.inverse(dict.getValue()));
        dict.setPinyinShort(PinyinUtils.inverseShort(dict.getValue()));
        dict.setIsEnabled(true);
        dao.persist(dict);
        return dict;
    }

    /*
     * (non-Javadoc)
     *
     * @see com.asdc.jbp.dict.service.DictSercie#createDictionary(java.util.List)
     */
    @Override
    public void createDictionary(List<SysDict> dictList) throws ServiceException {
        // validate
        for (SysDict dict : dictList) {
            if (dict == null || dict.getNature() == null || dict.getCode() == null) {
                throw new ServiceException("ERR_DICT_004", Messages.getMsg("dict", "ERR_DICT_MSG_CREATE_NULL"));
            }
            if (dao.getSysDictByNatureAndCode(dict.getNature(), dict.getCode()) != null) {
                throw new ServiceException("ERR_DICT_004", Messages.getMsg("dict", "ERR_DICT_MSG_DUPLICTED_NATURE_CODE"), dict.getNature(), dict.getCode());
            }
        }
        for (SysDict dict : dictList) {
            dict.setPinyin(PinyinUtils.inverse(dict.getValue()));
            dict.setPinyinShort(PinyinUtils.inverseShort(dict.getValue()));
            dict.setIsEnabled(true);
            dao.persist(dict);
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see com.asdc.jbp.dict.service.DictSercie#updateDictionary(com.asdc.jbp.dict.entity.SysDict)
     */
    @Override
    @Transactional
    public SysDict updateDictionary(SysDict dict) throws ServiceException {
        if (dict == null || dict.getNature() == null || dict.getCode() == null) {
            throw new ServiceException("ERR_DICT_004", Messages.getMsg("dict", "ERR_DICT_MSG_CREATE_NULL"));
        }
        //1.通过dictid
        SysDict dataDB = getSysDictById(dict.getId());
        //这个是
        SysDict dataInDB = getDictByNatureAndCode(dict.getNature(), dict.getCode());
        dict.setPinyin(PinyinUtils.inverse(dict.getValue()));
        dict.setIsEnabled(true);
        dict.setPinyinShort(PinyinUtils.inverseShort(dict.getValue()));
        if (dataInDB != null) {
            //是要报错的
            if (dataDB.equals(dataInDB)) {
                BeanUtils.copyProperties(dict, dataInDB, "id", "isEnabled");
                return dataInDB;
            } else {
                throw new ServiceException("ERR_DICT_004", Messages.getMsg("dict", "ERR_DICT_MSG_DUPLICTED_NATURE_CODE"), dict.getNature(), dict.getCode());
            }
        } else {
            //可以修改
            BeanUtils.copyProperties(dict, dataDB, "id", "isEnabled");
            return dataDB;
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see com.asdc.jbp.dict.service.DictSercie#removeDictionariesById(java.lang.Long)
     */
    @Override
    @Transactional
    public void removeDictionariesById(Integer id) throws ServiceException {
        dao.deleteDictById(id);
    }

    @Override
    @Transactional
    public void removeSysDicts(List<Integer> ids) throws ServiceException {
        if (ids == null) {
            throw new ServiceException("ERR_SYS_003", Messages.getMsg("dict", "ERR_DICT_MSG_ID_NULL"));
        }
        for (Integer id : ids) {
            removeDictionariesById(id);
        }
    }


    /* (non-Javadoc)
     * @see com.asdc.jbp.dict.service.DictService#removeDictionaryByNature(java.lang.String)
     */
    @Override
    @Transactional
    public int removeDictionaryByNature(String nature) throws ServiceException {
        if (nature == null) {
            throw new ServiceException("ERR_DICT_005", Messages.getMsg("dict", "ERR_DICT_MSG_NATURE_NULL"));
        }
        return dao.removeDictByNature(nature);
    }

    /* (non-Javadoc)
     * @see com.asdc.jbp.dict.service.DictService#reciveDictionariesById(int)
     */
    @Override
    @Transactional
    public void reciveDictionariesById(int dictid) {
        dao.reciveDictById(dictid);

    }

    @Transactional
    public void recoverDicts(List<Integer> ids) throws ServiceException {
        if (ids == null) {
            throw new ServiceException("ERR_SYS_003", Messages.getMsg("dict", "ERR_DICT_MSG_ID_NULL"));
        }
        for (Integer id : ids) {
            reciveDictionariesById(id);
        }

    }

    @Override
    public List<SysDict> queryParentDictIsNull() throws ServiceException {
        return dao.queryParentDictIsNull();
    }

    @Override
    public int checkcodeAndNature(String nature, String code) throws ServiceException {
        if (nature == null || code == null) {
            throw new ServiceException("ERR_DICT_005", Messages.getMsg("dict", "ERR_DICT_MSG_NATURE_NULL"));
        }
        return dao.checkcodeAndNature(nature, code);
    }


    public List<SysDict> getSelectDictInfo(String nature, String code) throws ServiceException {
        SysDict sd = dao.getSysDictByNatureAndCode(nature, code);
        return dao.getParentById(sd.getId());
    }

    public List<SysDict> queryDictCountByOrderBy(int start, int limit, SysDict dict) throws ServiceException {

        if (dict.getCode() != null) {
            dict.setCode("%" + dict.getCode() + "%");
        } else {
            dict.setCode("%%");
        }
        List<SysDict> dictList = dao.querySysDeptByOrderBy(start, limit, dict);

        List<SysDict> listdict;
        for (SysDict aDictList : dictList) {
            listdict = getParentById(aDictList.getId());
            if (listdict.size() > 0) {
                aDictList.setChilds("childs");
            }
        }
        return dictList;
    }

    @Override
    public List<SysDict> queryChildrenByParentCode(String code) {
        return dao.queryChildrenByParentCode(code);
    }

    @Override
    public SysDict queryCodeByValue(String code, String value) {
        return dao.queryCodeByValue(code, value);
    }

    @Override
    public String queryValueByCode(String code) {
        return dao.queryValueByCode(code);
    }


}
