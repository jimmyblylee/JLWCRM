/**
 * Project Name : jbp-framework <br>
 * File Name : WorkDTO.java <br>
 * Package Name : com.asdc.jbp.framework.dto <br>
 * Create Time : Apr 12, 2016 <br>
 * Create by : xiangyu_li@asdc.com.cn <br>
 * Copyright © 2006, 2016, ASDC DAI. All rights reserved.
 */
package com.asdc.jbp.framework.dto;

import java.io.Serializable;
import java.util.AbstractMap;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.asdc.jbp.framework.exception.ErrLevel;
import com.asdc.jbp.framework.exception.ServiceException;
import com.asdc.jbp.framework.ui.AppConstant;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonParser.Feature;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * ClassName : WorkDTO <br>
 * Description : work DTO in the current thread <br>
 * Create Time : Apr 12, 2016 <br>
 * Create by : xiangyu_li@asdc.com.cn <br>
 *
 */
public class WorkDTO extends AbstractMap<String, Object> implements AppConstant, Serializable {

    private static final long serialVersionUID = -6067768530318967219L;

    protected Map<String, Object> map;

    /**
     * Create a new instance of WorkDTO.
     * 
     * @param dto
     * @throws ServiceException
     */
    public WorkDTO(Map<String, Object> dto) throws ServiceException {
        this.map = dto;
    }

    /**
     * @see java.util.AbstractMap#entrySet()
     */
    @Override
    public Set<java.util.Map.Entry<String, Object>> entrySet() {
        return map.entrySet();
    }

    /**
     * Returns the value to which the specified key is mapped, or {@code null} if this map contains no mapping for the key.
     * 
     * <p>
     * More formally, if this map contains a mapping from a key {@code k} to a value {@code v} such that {@code (key==null ? k==null : key.equals(k))} , then
     * this method returns {@code v}; otherwise it returns {@code null}. (There can be at most one such mapping.)
     * 
     * <p>
     * A return value of {@code null} does not <i>necessarily</i> indicate that the map contains no mapping for the key; it's also possible that the map
     * explicitly maps the key to {@code null}. The {@link #containsKey containsKey} operation may be used to distinguish these two cases.
     * 
     * @see #put(Object, Object)
     */
    @SuppressWarnings("unchecked")
    public <T> T get(String key) {
        return (T) map.get(key);
    }

    /**
     * Associates the specified value with the specified key in this map. If the map previously contained a mapping for the key, the old value is replaced.
     * 
     * @param key
     *            key with which the specified value is to be associated
     * @param value
     *            value to be associated with the specified key
     * @return the previous value associated with <tt>key</tt>, or <tt>null</tt> if there was no mapping for <tt>key</tt>. (A <tt>null</tt> return can also
     *         indicate that the map previously associated <tt>null</tt> with <tt>key</tt>.)
     */
    public Object put(String key, Object value) {
        return map.put(key, value);
    }

    // *****************system *******************//
    /**
     * Description : get controller name <br>
     * Create Time: Apr 12, 2016 <br>
     * Create by : xiangyu_li@asdc.com.cn <br>
     *
     * @return controller name
     */
    public String getController() {
        return get(CNS_SERVER.CONTROLLER.toString());
    }
    
    /**
     * Description : remove the controller name parameter from dto <br>
     * Create Time: Apr 25, 2016 <br>
     * Create by : xiangyu_li@asdc.com.cn <br>
     *
     */
    public void removeController() {
        remove(CNS_SERVER.CONTROLLER.toString());
    }

    /**
     * Description : get method name <br>
     * Create Time: Apr 12, 2016 <br>
     * Create by : xiangyu_li@asdc.com.cn <br>
     *
     * @return method name
     */
    public String getMethod() {
        return get(CNS_SERVER.METHOD.toString());
    }
    
    /**
     * Description : remove the method name parameter from dto <br>
     * Create Time: Apr 25, 2016 <br>
     * Create by : xiangyu_li@asdc.com.cn <br>
     *
     */
    public void removeMethod() {
        remove(CNS_SERVER.METHOD.toString());
    }

    // ***************** file *******************//
    /**
     * Description : set file result <br>
     * Create Time: Apr 12, 2016 <br>
     * Create by : xiangyu_li@asdc.com.cn <br>
     *
     * @param fileId
     * @param fileName
     * @param url
     */
    public void setFileResultSuccess(String fileId, String fileName, String url) {
        HashMap<String, String> fileInfo = new HashMap<String, String>();
        fileInfo.put("fileId", fileId);
        fileInfo.put("fileName", fileName);
        fileInfo.put("url", url);
        put(CNS_FILE.SUCCESS.toString(), true);
        put(CNS_FILE.CNS_FILE_RESULT.toString(), fileInfo);
    }

    /**
     * Description : upload file key <br>
     * Create Time: Apr 12, 2016 <br>
     * Create by : xiangyu_li@asdc.com.cn <br>
     *
     * @return file key
     */
    public String getFileKey() {
        return get(CNS_FILE.CNS_FILE_KEY.toString());
    }

    // ***************** list request *******************//
    /**
     * Description : set result list in response <br>
     * Create Time: Apr 12, 2016 <br>
     * Create by : xiangyu_li@asdc.com.cn <br>
     *
     * @param result
     */
    public void setResult(Object result) {
        put(CNS_LIST_REQUEST.RESULT.toString(), result);
    }

    /**
     * Description : set result totle size in response <br>
     * Create Time: Apr 12, 2016 <br>
     * Create by : xiangyu_li@asdc.com.cn <br>
     *
     * @param totle
     */
    public void setTotle(int totle) {
        put(CNS_LIST_REQUEST.TOTAL.toString(), totle);
    }

    /**
     * Description : get list request start <br>
     * Create Time: Apr 12, 2016 <br>
     * Create by : xiangyu_li@asdc.com.cn <br>
     *
     * @return start num of list request
     */
    public int getStart() {
        return getInteger(CNS_LIST_REQUEST.START.toString());
    }

    /**
     * Description : get list request limit <br>
     * Create Time: Apr 12, 2016 <br>
     * Create by : xiangyu_li@asdc.com.cn <br>
     *
     * @return limit num of list request
     */
    public int getLimit() {
        return getInteger(CNS_LIST_REQUEST.LIMIT.toString());
    }

    /**
     * Description : convert the json in workDTO to {@code Map<String, Object} <br>
     * Create Time: Apr 12, 2016 <br>
     * Create by : xiangyu_li@asdc.com.cn <br>
     *
     * @param key
     * @return map result
     */
    @SuppressWarnings("unchecked")
    public Map<String, Object> convertJsonToMapByKey(String key) {
        if (!containsKey(key)) {
            return null;
        }
        try {
            return getTemplateObjectMapper().readValue(this.<String> get(key), new TypeReference<HashMap<String, Object>>() {
            });
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.EMPTY_MAP;
        }
    }

    /**
     * Description : convert the json in workDTO to given type <br>
     * Create Time: Apr 12, 2016 <br>
     * Create by : xiangyu_li@asdc.com.cn <br>
     *
     * @param key
     * @param type
     * @return converted object
     */
    public <T> T convertJsonToBeanByKey(String key, Class<T> type) {
        if (!containsKey(key)) {
            return null;
        }
        try {
            return getTemplateObjectMapper().readValue(this.<String> get(key), type);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Description : convert the json in workDTO to list construct with given type <br>
     * Create Time: Apr 12, 2016 <br>
     * Create by : xiangyu_li@asdc.com.cn <br>
     *
     * @param key
     * @param type
     * @return converted list of object
     */
    public <T> List<T> converJsonToBeanListByKey(String key, Class<T> type) {
        if (!containsKey(key)) {
            return null;
        }
        ObjectMapper mapper = getTemplateObjectMapper();
        try {
            return mapper.readValue(this.<String> get(key), mapper.getTypeFactory().constructCollectionType(List.class, type));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private ObjectMapper getTemplateObjectMapper() {
        ObjectMapper mapper = new ObjectMapper().setVisibility(PropertyAccessor.FIELD, Visibility.ANY);
        mapper.configure(Feature.ALLOW_NON_NUMERIC_NUMBERS, true);
        mapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);
        return mapper;
    }

    // ***************** common *******************//
    /**
     * Description : get integer by key, this will translate the string into integer <br>
     * Create Time: Apr 12, 2016 <br>
     * Create by : xiangyu_li@asdc.com.cn <br>
     *
     * @param key
     * @return integer, null for is not a integer or bad number formated string
     */
    public Integer getInteger(String key) {
        if (containsKey(key) && get(key) != null) {
            Object obj = get(key);
            if (obj instanceof Integer) {
                return (Integer) obj;
            } else {
                try {
                    return Integer.parseInt(this.<Object> get(key).toString());
                } catch (NumberFormatException e) {
                    return null;
                }
            }
        } else {
            return null;
        }
    }

    /**
     * getLong: get Long by key, this will translate the string to Long <br>
     * Create Time: Oct 5, 2015 <br>
     * Create by: xiangyu_li@asdc.com.cn
     * 
     * @param key
     * @return Long, null for is not a integer or bad number formated string
     */
    public Long getLong(String key) {
        if (containsKey(key) && get(key) != null) {
            Object obj = get(key);
            if (obj instanceof Long) {
                return (Long) obj;
            } else {
                try {
                    return Long.valueOf(this.<Object> get(key).toString());
                } catch (NumberFormatException e) {
                    return null;
                }
            }
        } else {
            return null;
        }
    }

    /**
     * Description : set failed error code <br>
     * Create Time: Apr 12, 2016 <br>
     * Create by : xiangyu_li@asdc.com.cn <br>
     *
     * @param code
     * @param msg
     */
    public void setError(String code, String msg) {
        this.put(CNS_REQUEST.SUCCESS.toString(), false);
        this.put(CNS_REQUEST.ERR_CODE.toString(), code);
        this.put(CNS_REQUEST.ERR_LEVEL.toString(), CNS_REQUEST.CNS_ERROR);
        this.put(CNS_REQUEST.ERR_MESSAGE.toString(), msg);
    }

    /**
     * Description : set error or warn by given exception <br>
     * Create Time: Apr 15, 2016 <br>
     * Create by : xiangyu_li@asdc.com.cn <br>
     *
     * @param exception
     * @return the given excpeiton
     */
    public ServiceException setIssue(ServiceException exception) {
        if (ErrLevel.ERR.equals(exception.getErrLevel())) {
            this.setError(exception.getErrCode(), exception.getMessage());
        } else {
            this.setWarn(exception.getErrCode(), exception.getMessage());
        }
        return exception;
    }

    /**
     * Description : set warning error code <br>
     * Create Time: Apr 12, 2016 <br>
     * Create by : xiangyu_li@asdc.com.cn <br>
     *
     * @param code
     * @param msg
     */
    public void setWarn(String code, String msg) {
        this.put(CNS_REQUEST.SUCCESS.toString(), false);
        this.put(CNS_REQUEST.ERR_CODE.toString(), code);
        this.put(CNS_REQUEST.ERR_LEVEL.toString(), CNS_REQUEST.CNS_WARNING);
        this.put(CNS_REQUEST.ERR_MESSAGE.toString(), msg);
    }

    /**
     * Description : set flag that dispatcher won't control the response stream <br>
     * Create Time: Apr 12, 2016 <br>
     * Create by : xiangyu_li@asdc.com.cn <br>
     */
    public void letMeControlTheResponseStream() {
        this.put(CNS_REQUEST.LET_ME_CTRL_THE_STREAM.toString(), true);
    }
}
