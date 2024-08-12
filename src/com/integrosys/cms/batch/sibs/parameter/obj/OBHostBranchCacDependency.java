package com.integrosys.cms.batch.sibs.parameter.obj;

import com.integrosys.cms.batch.sibs.parameter.IParameterProperty;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.DateUtil;

import java.util.Date;

import org.apache.commons.lang.StringUtils;

/**
 * Created by IntelliJ IDEA.
 * User: Cynthia
 * Date: Oct 7, 2008
 * Time: 11:10:55 AM
 * To change this template use File | Settings | File Templates.
 */
public class OBHostBranchCacDependency implements IDependency {

    public static final String ISLAMIC_INDICATOR = "I";
    public static final String CONVENTIONAL_INDICATOR = "C";
    public static final String FULL_FLEDGE_ISLAMIC_INDICATOR = "F";

    private static final String[] MATCHING_PROPERTIES = new String[] { "branchNumber" };
    private static final String[] IGNORED_PROPERTIES = new String[] { "hostBranchId", "branchNumber" };

//    private static final String[] MATCHING_PROPERTIES = new String[] { "branchNumber" };
//    private static final String[] IGNORED_PROPERTIES = new String[] { "branchNumber", "status", "lastUpdatedDate" };

    private long hostBranchId = ICMSConstant.LONG_INVALID_VALUE;
    private String branchNumber;
    private String branchName;
//    private String islamicConventionalIndicator;
    private String country;
    private String state;
    private String rcCode;
    private String cacCode;
    private String status;
    private Date lastUpdatedDate;
    private String fisBranchId;
    private String bankBranchId;
    private String sptfBranchId;
    private Date branchOpeningDate;
    private String branchOpeningDateStr;
    private String closedBranchIndicator = ICMSConstant.FALSE_VALUE;
//    private String masbTagging;


    public long getHostBranchId() {
        return hostBranchId;
    }

    public void setHostBranchId(long hostBranchId) {
        this.hostBranchId = hostBranchId;
    }
    
    public String getBranchNumber() {
        return branchNumber;
    }

    public void setBranchNumber(String branchNumber) {
        this.branchNumber = branchNumber;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }


//    public String getIslamicConventionalIndicator() {
//        return islamicConventionalIndicator;
//    }
//
//    public void setIslamicConventionalIndicator(String islamicConventionalIndicator) {
//        this.islamicConventionalIndicator = islamicConventionalIndicator;
//    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getRcCode() {
        return rcCode;
    }

    public void setRcCode(String rcCode) {
        this.rcCode = rcCode;
    }

    public String getCacCode() {
        return cacCode;
    }

    public void setCacCode(String cacCode) {
        this.cacCode = cacCode;
    }

//    public String getStatus() {
//        return status;
//    }
//
//    public void setStatus(String status) {
//        this.status = status;
//    }
//
//    public Date getLastUpdatedDate() {
//        return lastUpdatedDate;
//    }
//
//    public void setLastUpdatedDate(Date lastUpdatedDate) {
//        this.lastUpdatedDate = lastUpdatedDate;
//    }

    public String getFisBranchId() {
        return fisBranchId;
    }

    public void setFisBranchId(String fisBranchId) {
        this.fisBranchId = fisBranchId;
    }

    public String getBankBranchId() {
        return bankBranchId;
    }

    public void setBankBranchId(String bankBranchId) {
        this.bankBranchId = bankBranchId;
    }

    public String getSptfBranchId() {
        return sptfBranchId;
    }

    public void setSptfBranchId(String sptfBranchId) {
        this.sptfBranchId = sptfBranchId;
    }

    public Date getBranchOpeningDate() {
        if (branchOpeningDate == null) {
            branchOpeningDate = parseJulianDate(getBranchOpeningDateStr());
        }
        return branchOpeningDate;
    }

    public void setBranchOpeningDate(Date branchOpeningDate) {
        this.branchOpeningDate = branchOpeningDate;
    }

    public String getBranchOpeningDateStr() {
        return branchOpeningDateStr;
    }

    public void setBranchOpeningDateStr(String branchOpeningDateStr) {
        if (branchOpeningDateStr != null) {
            branchOpeningDateStr = branchOpeningDateStr.trim();
        }
        this.branchOpeningDateStr = branchOpeningDateStr;
    }

    public String getClosedBranchIndicator() {
        return closedBranchIndicator;
    }

    public void setClosedBranchIndicator(String closedBranchIndicator) {
        this.closedBranchIndicator = closedBranchIndicator;
    }

//    public String getMasbTagging() {
//        return masbTagging;
//    }
//
//    public void setMasbTagging(String masbTagging) {
//        this.masbTagging = masbTagging;
//    }

    /****** Methods from ISynchronizer ******/
    public String[] getMatchingProperties() {
        return MATCHING_PROPERTIES;
    }

    public String[] getIgnoreProperties() {
        return IGNORED_PROPERTIES;
    }

    public void updatePropertiesForCreateUpdate(IParameterProperty paramProperty) {
//        try {
//            int branchNum = Integer.parseInt(branchNumber);
//            if((branchNum >=500 && branchNum <= 999) || (branchNum >= 7000 && branchNum <=7099 )) {
//                setIslamicConventionalIndicator(OBHostBranchCacDependency.ISLAMIC_INDICATOR);
//            }
//        } catch(NumberFormatException e) {
//            DefaultLogger.warn(this, "NumberFormatException encountered when parsing branch number!!");
//        }

//        String masbTaging = masbTagging;
//        if (StringUtils.isNotBlank(masbTaging)){
//            if (StringUtils.equals(masbTaging, OBHostBranchCacDependency.ISLAMIC_INDICATOR)
//                    || StringUtils.equals(masbTaging, OBHostBranchCacDependency.FULL_FLEDGE_ISLAMIC_INDICATOR)) {
//                setIslamicConventionalIndicator(OBHostBranchCacDependency.ISLAMIC_INDICATOR);
//            }
//            else if (StringUtils.equals(masbTaging, OBHostBranchCacDependency.CONVENTIONAL_INDICATOR)) {
//                setIslamicConventionalIndicator(OBHostBranchCacDependency.CONVENTIONAL_INDICATOR);
//            }
//        }

        if (branchOpeningDateStr != null && ("9999999").equals(branchOpeningDateStr)) {
            setClosedBranchIndicator(ICMSConstant.TRUE_VALUE);
        }

//        setStatus(ICMSConstant.STATE_ACTIVE);
//        setLastUpdatedDate(new Date());

        trimStringAttributes();
    }

    public void updatePropertiesForDelete(IParameterProperty paramProperty) {
//        setStatus(ICMSConstant.STATE_DELETED);
//        setLastUpdatedDate(new Date());

        trimStringAttributes();
    }

    private void trimStringAttributes() {
        if(getBranchNumber() != null){
            setBranchNumber(getBranchNumber().trim());
        }
        if(getBranchName() != null){
            setBranchName(getBranchName().trim());
        }
        if(getCountry() != null){
            setCountry(getCountry().trim());
        }
        if(getState() != null){
            setState(getState().trim());
        }
        if(getFisBranchId() != null){
            setFisBranchId(getFisBranchId().trim());
        }
        if(getBankBranchId() != null){
            setBankBranchId(getBankBranchId().trim());
        }
        if(getSptfBranchId() != null){
            setSptfBranchId(getSptfBranchId().trim());
        }
//        if(getMasbTagging() != null){
//            setMasbTagging(getMasbTagging().trim());
//        }
    }

    private Date parseJulianDate (String dateStr) {
        Date parsedDate = null;
        if (dateStr != null && dateStr.length() == 7 && !("9999999").equals(dateStr)) {
            parsedDate = DateUtil.parseDate("yyyyDDD",dateStr);
        }

        return parsedDate;
    }

}
