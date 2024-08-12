package com.integrosys.cms.batch.sibs.parameter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.commons.lang.StringUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.List;
import java.util.Date;
import java.math.BigDecimal;

import com.integrosys.base.techinfra.util.DateUtil;

/**
 * Created by IntelliJ IDEA.
 * User: Vincent
 * Date: Jun 3, 2009
 * Time: 6:14:30 PM
 * To change this template use File | Settings | File Templates.
 */
public class SingleParameterLoaderImpl extends AbstractSingleParameterLoader {

    private static final String EXTERNAL_INQUIRY_QUERY_MAP = "externalInquiryQueryMap";
    private static final String CMS_INQUIRY_QUERY_MAP = "cmsInquiryQueryMap";
    private static final String CMS_INSERT_QUERY_MAP = "cmsInsertQueryMap";
    private static final String CMS_UPDATE_QUERY_MAP = "cmsUpdateQueryMap";
    private static final String CMS_UPDATE_QUERY_FOR_DELETION_MAP = "cmsUpdateQueryForDeletionMap";
    private static final String KEYS_VALUE_LIST = "keysValueList";
    private static final String KEYS_VALUE_LIST_FOR_UPDATE = "keysValueListforUpdate";
    private static final String KEYS_VALUE_LIST_FOR_CREATE = "keysValueListforCreate";
    private static final String DEPENDENCY_PARAM_FLAG = "dependencyParamFlag";

    private Map remoteEntityNameSingleParameterLoaderMap;

    private String externalInquiryQuery;

    private String cmsInquiryQuery;

    private String cmsInsertQuery;

    private String cmsUpdateQuery;

    private String cmsUpdateQueryForDeletion;

    private List keysValueList;

    private List keysValueListforUpdate;

    private List keysValueListforCreate;

    private boolean dependencyParamFlag;

    // **************** Getter and Setter ******************
    public Map getRemoteEntityNameSingleParameterLoaderMap() {
        return remoteEntityNameSingleParameterLoaderMap;
    }

    public void setRemoteEntityNameSingleParameterLoaderMap(Map remoteEntityNameSingleParameterLoaderMap) {
        this.remoteEntityNameSingleParameterLoaderMap = remoteEntityNameSingleParameterLoaderMap;
    }

    public String getExternalInquiryQuery() {
        return externalInquiryQuery;
    }

    public void setExternalInquiryQuery(String externalInquiryQuery) {
        this.externalInquiryQuery = externalInquiryQuery;
    }

    public String getCmsInquiryQuery() {
        return cmsInquiryQuery;
    }

    public void setCmsInquiryQuery(String cmsInquiryQuery) {
        this.cmsInquiryQuery = cmsInquiryQuery;
    }

    public String getCmsInsertQuery() {
        return cmsInsertQuery;
    }

    public void setCmsInsertQuery(String cmsInsertQuery) {
        this.cmsInsertQuery = cmsInsertQuery;
    }

    public String getCmsUpdateQuery() {
        return cmsUpdateQuery;
    }

    public void setCmsUpdateQuery(String cmsUpdateQuery) {
        this.cmsUpdateQuery = cmsUpdateQuery;
    }

    public String getCmsUpdateQueryForDeletion() {
        return cmsUpdateQueryForDeletion;
    }

    public void setCmsUpdateQueryForDeletion(String cmsUpdateQueryForDeletion) {
        this.cmsUpdateQueryForDeletion = cmsUpdateQueryForDeletion;
    }

    public List getKeysValueList() {
        return keysValueList;
    }

    public void setKeysValueList(List keysValueList) {
        this.keysValueList = keysValueList;
    }

    public List getKeysValueListforUpdate() {
        return keysValueListforUpdate;
    }

    public void setKeysValueListforUpdate(List keysValueListforUpdate) {
        this.keysValueListforUpdate = keysValueListforUpdate;
    }

    public List getKeysValueListforCreate() {
        return keysValueListforCreate;
    }

    public void setKeysValueListforCreate(List keysValueListforCreate) {
        this.keysValueListforCreate = keysValueListforCreate;
    }

    public Object[] getKeysValueBetweenDifferentSources(ResultSet externalResultSet) throws SQLException {
        Object[] keysValueObject = null;
        if (externalResultSet != null && this.keysValueList != null && !this.keysValueList.isEmpty()) {
            keysValueObject = setColumnMetaInfo(externalResultSet, this.keysValueList);
        }

        return keysValueObject ;
    }

    public Object[] getInsertValuesFromExternal(ResultSet externalResultSet) throws SQLException {
        Object[] keysValueObject = null;
        if (externalResultSet != null && this.keysValueListforCreate != null && !this.keysValueListforCreate.isEmpty()) {
            keysValueObject = setColumnMetaInfo(externalResultSet, this.keysValueListforCreate);
        }

        return keysValueObject ;
    }

    public Object[] getUpdateValuesFromExternal(ResultSet externalResultSet) throws SQLException {
        Object[] keysValueObject = null;
        if (externalResultSet != null && this.keysValueListforUpdate != null && !this.keysValueListforUpdate.isEmpty()) {
            keysValueObject = setColumnMetaInfo(externalResultSet, this.keysValueListforUpdate);
        }

        return keysValueObject ;
    }

    public boolean getDependencyParamFlag() {
        return dependencyParamFlag;
    }

    public void setDependencyParamFlag(boolean dependencyParamFlag) {
        this.dependencyParamFlag = dependencyParamFlag;
    }

    protected void setValue (Map loaderMap) {
        if (loaderMap != null) {
            if (loaderMap.get(EXTERNAL_INQUIRY_QUERY_MAP) != null) {
                setExternalInquiryQuery((String)loaderMap.get(EXTERNAL_INQUIRY_QUERY_MAP));
            }
            if (loaderMap.get(CMS_INQUIRY_QUERY_MAP) != null) {
                setCmsInquiryQuery((String)loaderMap.get(CMS_INQUIRY_QUERY_MAP));
            }
            if (loaderMap.get(CMS_INSERT_QUERY_MAP) != null) {
                setCmsInsertQuery((String)loaderMap.get(CMS_INSERT_QUERY_MAP));
            }
            if (loaderMap.get(CMS_UPDATE_QUERY_MAP) != null) {
                setCmsUpdateQuery((String)loaderMap.get(CMS_UPDATE_QUERY_MAP));
            }
            if (loaderMap.get(CMS_UPDATE_QUERY_FOR_DELETION_MAP) != null) {
                setCmsUpdateQueryForDeletion((String)loaderMap.get(CMS_UPDATE_QUERY_FOR_DELETION_MAP));
            }
            if (loaderMap.get(KEYS_VALUE_LIST) != null) {
                setKeysValueList((List)loaderMap.get(KEYS_VALUE_LIST));
            }
            if (loaderMap.get(KEYS_VALUE_LIST_FOR_UPDATE) != null) {
                setKeysValueListforUpdate((List)loaderMap.get(KEYS_VALUE_LIST_FOR_UPDATE));
            }
            if (loaderMap.get(KEYS_VALUE_LIST_FOR_CREATE) != null) {
                setKeysValueListforCreate((List)loaderMap.get(KEYS_VALUE_LIST_FOR_CREATE));
            }
            if (loaderMap.get(DEPENDENCY_PARAM_FLAG) != null) {
                setDependencyParamFlag((new Boolean((String)loaderMap.get(DEPENDENCY_PARAM_FLAG))).booleanValue());
            } else {
                setDependencyParamFlag(false); 
            }
        }
    }

    protected Object[] setColumnMetaInfo(ResultSet externalResultSet, List tempKeysValueList) throws SQLException {
        Object[] keysValueObject = new Object[tempKeysValueList.size()];
        Object tempObj = null;
        for (int i = 0; i < tempKeysValueList.size(); i++) {
            String currentTempStr = (String) tempKeysValueList.get(i);

            String[] tempArray = StringUtils.split(currentTempStr, "=");
//            logger.debug("tempArray 0 : " + tempArray[0]);
//            logger.debug("tempArray 1 : " + tempArray[1]);
//            logger.debug("String.class.toString() : " + String.valueOf(String.class));
//            logger.debug("String.class.getName() : " + String.class.getName());
//            logger.debug("BigDecimal.class.getName() : " + BigDecimal.class.getName());
//            logger.debug("Integer.class.getName() : " + Integer.class.getName());
//            logger.debug("Date.class.getName() : " + Date.class.getName());

            tempObj = new Object();
            if (StringUtils.equals((String)tempArray[1], String.class.getName())) {
                tempObj = StringUtils.trim(externalResultSet.getString(tempArray[0]));
            }
            else if (StringUtils.equals((String)tempArray[1], BigDecimal.class.getName())) {
                tempObj = new BigDecimal(StringUtils.trim(externalResultSet.getString(tempArray[0])));
            }
            else if (StringUtils.equals((String)tempArray[1], Integer.class.getName())) {
                tempObj = new Integer(externalResultSet.getInt(tempArray[0]));
            }
            else if (StringUtils.equals((String)tempArray[1], Date.class.getName())) {
                tempObj = parseJulianDate(StringUtils.trim(externalResultSet.getString(tempArray[0])));
            }
            else {
                continue;
            }
            
            keysValueObject[i] = tempObj;
        }
        return keysValueObject;
    }

    private Date parseJulianDate (String dateStr) {
        Date parsedDate = null;
        if (dateStr != null && dateStr.length() == 7 && !("9999999").equals(dateStr)) {
            parsedDate = DateUtil.parseDate("yyyyDDD",dateStr);
        }

        return parsedDate;
    }
}
