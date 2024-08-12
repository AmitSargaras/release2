package com.integrosys.cms.batch.sibs.parameter.obj;

import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.batch.sibs.parameter.IParameterProperty;
import com.integrosys.base.techinfra.util.DateUtil;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: Cynthia
 * Date: Oct 2, 2008
 * Time: 3:51:13 PM
 * To change this template use File | Settings | File Templates.
 */
public class OBHostProductPackage implements IHostProductPackage {

    private static final String[] MATCHING_PROPERTIES = new String[] { "packageCode" };
    private static final String[] IGNORED_PROPERTIES = new String[] { "packageCode", "status", "lastUpdatedDate" };

    private String packageCode;
    private String description;
    private Date expiryDate;
    private String expiryDateStr;
    private Date effectiveDate;
    private String effectiveDateStr;
    private BigDecimal fundAllocated;
    private String status;
    private Date lastUpdatedDate;


    public String getPackageCode() {
        return packageCode;
    }

    public void setPackageCode(String packageCode) {
        this.packageCode = packageCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getExpiryDate() {
        if (expiryDate == null) {
            expiryDate = parseJulianDate(getExpiryDateStr());
        }
        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getExpiryDateStr() {
        return expiryDateStr;
    }

    public void setExpiryDateStr(String expiryDateStr) {
        if (expiryDateStr != null) {
            expiryDateStr = expiryDateStr.trim();
        }
        this.expiryDateStr = expiryDateStr;
    }

    public Date getEffectiveDate() {
        if (effectiveDate == null) {
            effectiveDate = parseJulianDate(getEffectiveDateStr());
        }
        return effectiveDate;
    }

    public void setEffectiveDate(Date effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    public String getEffectiveDateStr() {
        return effectiveDateStr;
    }

    public void setEffectiveDateStr(String effectiveDateStr) {
        if (effectiveDateStr != null) {
            effectiveDateStr = effectiveDateStr.trim();
        }
        this.effectiveDateStr = effectiveDateStr;
    }

    public BigDecimal getFundAllocated() {
        return fundAllocated;
    }

    public void setFundAllocated(BigDecimal fundAllocated) {
        this.fundAllocated = fundAllocated;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getLastUpdatedDate() {
        return lastUpdatedDate;
    }

    public void setLastUpdatedDate(Date lastUpdatedDate) {
        this.lastUpdatedDate = lastUpdatedDate;
    }


    /****** Methods from ISynchronizer ******/
    public String[] getMatchingProperties() {
        return MATCHING_PROPERTIES;
    }

    public String[] getIgnoreProperties() {
        return IGNORED_PROPERTIES;
    }

    public void updatePropertiesForCreateUpdate(IParameterProperty paramProperty) {
        setStatus(ICMSConstant.STATE_ACTIVE);
        setLastUpdatedDate(new Date());

        trimStringAttributes();
    }

    public void updatePropertiesForDelete(IParameterProperty paramProperty) {
        setStatus(ICMSConstant.STATE_DELETED);
        setLastUpdatedDate(new Date());

        trimStringAttributes();
    }


    private void trimStringAttributes() {
        if(getPackageCode() != null){
            setPackageCode(getPackageCode().trim());
        }
        if(getDescription() != null){
            setDescription(getDescription().trim());
        }
    }

    private Date parseJulianDate (String dateStr) {
        Date parsedDate = null;
        if (dateStr != null && dateStr.length() == 7 && !("9999999").equals(dateStr)) {
            parsedDate = DateUtil.parseDate("yyyyDDD",dateStr);
        }

        return parsedDate;
    }

}