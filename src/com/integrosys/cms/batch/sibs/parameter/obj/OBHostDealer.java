package com.integrosys.cms.batch.sibs.parameter.obj;

import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.batch.sibs.parameter.IParameterProperty;
import com.integrosys.base.techinfra.util.DateUtil;

import java.util.Date;
import java.math.BigDecimal;

/**
 * Created by IntelliJ IDEA.
 * User: Cynthia
 * Date: Oct 2, 2008
 * Time: 4:24:31 PM
 * To change this template use File | Settings | File Templates.
 */
public class OBHostDealer implements IHostDealer {

    private static final String[] MATCHING_PROPERTIES = new String[] { "dealerNumber" };
    private static final String[] IGNORED_PROPERTIES = new String[] { "dealerNumber", "status", "lastUpdatedDate" };

    private String dealerNumber;
    private String dealerName;
    private String dealerShortName;
    private String formattedShortName;
    private String cif;
    private String addressLine1;
    private String addressLine2;
    private String addressLine3;
    private String phone1;
    private String phone2;
    private BigDecimal limitAmount;
    private BigDecimal reserveAmount;
    private BigDecimal amountUtilised;
    private String dealerPlan;
    private String dealerGroup;
    private String officerCode;
    private Date expiryDate1;
    private Date expiryDate2;
    private String expiryDateStr;
    private String revolvingIndicator;
    private BigDecimal accrualPercent;
    private String dealerStatus;
    private Date dateRecruited1;
    private Date dateRecruited2;
    private String dateRecruitedStr;
    private int minIntDay;
    private int intSubsidyDays;
    private String productGroup;
    private String branchServed;
    private BigDecimal grossDevValue;
    private BigDecimal grossDevCost;
    private BigDecimal securityValue;
    private BigDecimal underLimitFbr;
    private BigDecimal underLimitFbt;
    private String recondIndicator;
    private String usedIndicator;
    private String newIndicator;
    private String newNational;
    private String dealerStatus2;
    private Date dealerStatusDate1;
    private Date dealerStatusDate2;
    private String dealerStatusDateStr;
    private String rescheduleIndicator;
	private BigDecimal deliquencyRate;
	private String fisMemberIndicator;
	private String fisDealerCode;
	private String remarks;
//	private String type;
    private String status;
    private Date lastUpdatedDate;


    public String getDealerNumber() {
        return dealerNumber;
    }

    public void setDealerNumber(String dealerNumber) {
        this.dealerNumber = dealerNumber;
    }

    public String getDealerName() {
        return dealerName;
    }

    public void setDealerName(String dealerName) {
        this.dealerName = dealerName;
    }

    public String getDealerShortName() {
        return dealerShortName;
    }

    public void setDealerShortName(String dealerShortName) {
        this.dealerShortName = dealerShortName;
    }

    public String getFormattedShortName() {
        return formattedShortName;
    }

    public void setFormattedShortName(String formattedShortName) {
        this.formattedShortName = formattedShortName;
    }

    public String getCif() {
        return cif;
    }

    public void setCif(String cif) {
        this.cif = cif;
    }

    public String getAddressLine1() {
        return addressLine1;
    }

    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
    }

    public String getAddressLine2() {
        return addressLine2;
    }

    public void setAddressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
    }

    public String getAddressLine3() {
        return addressLine3;
    }

    public void setAddressLine3(String addressLine3) {
        this.addressLine3 = addressLine3;
    }

    public String getPhone1() {
        return phone1;
    }

    public void setPhone1(String phone1) {
        this.phone1 = phone1;
    }

    public String getPhone2() {
        return phone2;
    }

    public void setPhone2(String phone2) {
        this.phone2 = phone2;
    }

    public BigDecimal getLimitAmount() {
        return limitAmount;
    }

    public void setLimitAmount(BigDecimal limitAmount) {
        this.limitAmount = limitAmount;
    }

    public BigDecimal getReserveAmount() {
        return reserveAmount;
    }

    public void setReserveAmount(BigDecimal reserveAmount) {
        this.reserveAmount = reserveAmount;
    }

    public BigDecimal getAmountUtilised() {
        return amountUtilised;
    }

    public void setAmountUtilised(BigDecimal amountUtilised) {
        this.amountUtilised = amountUtilised;
    }

    public String getDealerPlan() {
        return dealerPlan;
    }

    public void setDealerPlan(String dealerPlan) {
        this.dealerPlan = dealerPlan;
    }

    public String getDealerGroup() {
        return dealerGroup;
    }

    public void setDealerGroup(String dealerGroup) {
        this.dealerGroup = dealerGroup;
    }

    public String getOfficerCode() {
        return officerCode;
    }

    public void setOfficerCode(String officerCode) {
        this.officerCode = officerCode;
    }

    public Date getExpiryDate1() {
        if (expiryDate1 == null) {
            expiryDate1 = parseJulianDate(getExpiryDateStr());
        }
        return expiryDate1;
    }

    public void setExpiryDate1(Date expiryDate1) {
        this.expiryDate1 = expiryDate1;
    }

    public Date getExpiryDate2() {
        if (expiryDate2 == null) {
            expiryDate2 = parseJulianDate(getExpiryDateStr());
        }
        return expiryDate2;
    }

    public void setExpiryDate2(Date expiryDate2) {
        this.expiryDate2 = expiryDate2;
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

    public String getRevolvingIndicator() {
        return revolvingIndicator;
    }

    public void setRevolvingIndicator(String revolvingIndicator) {
        this.revolvingIndicator = revolvingIndicator;
    }

    public BigDecimal getAccrualPercent() {
        return accrualPercent;
    }

    public void setAccrualPercent(BigDecimal accrualPercent) {
        this.accrualPercent = accrualPercent;
    }

    public String getDealerStatus() {
        return dealerStatus;
    }

    public void setDealerStatus(String dealerStatus) {
        this.dealerStatus = dealerStatus;
    }

    public Date getDateRecruited1() {
        if (dateRecruited1 == null) {
            dateRecruited1 = parseJulianDate(getDateRecruitedStr());
        }
        return dateRecruited1;
    }

    public void setDateRecruited1(Date dateRecruited1) {
        this.dateRecruited1 = dateRecruited1;
    }

    public Date getDateRecruited2() {
        if (dateRecruited2 == null) {
            dateRecruited2 = parseJulianDate(getDateRecruitedStr());
        }
        return dateRecruited2;
    }

    public void setDateRecruited2(Date dateRecruited2) {
        this.dateRecruited2 = dateRecruited2;
    }

    public String getDateRecruitedStr() {
        return dateRecruitedStr;
    }

    public void setDateRecruitedStr(String dateRecruitedStr) {
        if (dateRecruitedStr != null) {
            dateRecruitedStr = dateRecruitedStr.trim();
        }
        this.dateRecruitedStr = dateRecruitedStr;
    }

    public int getMinIntDay() {
        return minIntDay;
    }

    public void setMinIntDay(int minIntDay) {
        this.minIntDay = minIntDay;
    }

    public int getIntSubsidyDays() {
        return intSubsidyDays;
    }

    public void setIntSubsidyDays(int intSubsidyDays) {
        this.intSubsidyDays = intSubsidyDays;
    }

    public String getProductGroup() {
        return productGroup;
    }

    public void setProductGroup(String productGroup) {
        this.productGroup = productGroup;
    }

    public String getBranchServed() {
        return branchServed;
    }

    public void setBranchServed(String branchServed) {
        this.branchServed = branchServed;
    }

    public BigDecimal getGrossDevValue() {
        return grossDevValue;
    }

    public void setGrossDevValue(BigDecimal grossDevValue) {
        this.grossDevValue = grossDevValue;
    }

    public BigDecimal getGrossDevCost() {
        return grossDevCost;
    }

    public void setGrossDevCost(BigDecimal grossDevCost) {
        this.grossDevCost = grossDevCost;
    }

    public BigDecimal getSecurityValue() {
        return securityValue;
    }

    public void setSecurityValue(BigDecimal securityValue) {
        this.securityValue = securityValue;
    }

    public BigDecimal getUnderLimitFbr() {
        return underLimitFbr;
    }

    public void setUnderLimitFbr(BigDecimal underLimitFbr) {
        this.underLimitFbr = underLimitFbr;
    }

    public BigDecimal getUnderLimitFbt() {
        return underLimitFbt;
    }

    public void setUnderLimitFbt(BigDecimal underLimitFbt) {
        this.underLimitFbt = underLimitFbt;
    }

    public String getRecondIndicator() {
        return recondIndicator;
    }

    public void setRecondIndicator(String recondIndicator) {
        this.recondIndicator = recondIndicator;
    }

    public String getUsedIndicator() {
        return usedIndicator;
    }

    public void setUsedIndicator(String usedIndicator) {
        this.usedIndicator = usedIndicator;
    }

    public String getNewIndicator() {
        return newIndicator;
    }

    public void setNewIndicator(String newIndicator) {
        this.newIndicator = newIndicator;
    }

    public String getNewNational() {
        return newNational;
    }

    public void setNewNational(String newNational) {
        this.newNational = newNational;
    }

    public String getDealerStatus2() {
        return dealerStatus2;
    }

    public void setDealerStatus2(String dealerStatus2) {
        this.dealerStatus2 = dealerStatus2;
    }

    public Date getDealerStatusDate1() {
        if (dealerStatusDate1 == null) {
            dealerStatusDate1 = parseJulianDate(getDealerStatusDateStr());
        }
        return dealerStatusDate1;
    }

    public void setDealerStatusDate1(Date dealerStatusDate1) {
        this.dealerStatusDate1 = dealerStatusDate1;
    }

    public Date getDealerStatusDate2() {
        if (dealerStatusDate2 == null) {
            dealerStatusDate2 = parseJulianDate(getDealerStatusDateStr());
        }
        return dealerStatusDate2;
    }

    public void setDealerStatusDate2(Date dealerStatusDate2) {
        this.dealerStatusDate2 = dealerStatusDate2;
    }

    public String getDealerStatusDateStr() {
        return dealerStatusDateStr;
    }

    public void setDealerStatusDateStr(String dealerStatusDateStr) {
        if (dealerStatusDateStr != null) {
            dealerStatusDateStr = dealerStatusDateStr.trim();
        }
        this.dealerStatusDateStr = dealerStatusDateStr;
    }

    public String getRescheduleIndicator() {
        return rescheduleIndicator;
    }

    public void setRescheduleIndicator(String rescheduleIndicator) {
        this.rescheduleIndicator = rescheduleIndicator;
    }

    public BigDecimal getDeliquencyRate() {
        return deliquencyRate;
    }

    public void setDeliquencyRate(BigDecimal deliquencyRate) {
        this.deliquencyRate = deliquencyRate;
    }

    public String getFisMemberIndicator() {
        return fisMemberIndicator;
    }

    public void setFisMemberIndicator(String fisMemberIndicator) {
        this.fisMemberIndicator = fisMemberIndicator;
    }

    public String getFisDealerCode() {
        return fisDealerCode;
    }

    public void setFisDealerCode(String fisDealerCode) {
        this.fisDealerCode = fisDealerCode;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

//    public String getType() {
//        return type;
//    }
//
//    public void setType(String type) {
//        this.type = type;
//    }

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
        if(getDealerNumber() != null){
            setDealerNumber(getDealerNumber().trim());
        }
        if(getDealerName() != null){
            setDealerName(getDealerName().trim());
        }
        if(getDealerShortName() != null){
            setDealerShortName(getDealerShortName().trim());
        }
        if(getFormattedShortName() != null){
            setFormattedShortName(getFormattedShortName().trim());
        }
        if(getCif() != null){
            setCif(getCif().trim());
        }
        if(getAddressLine1() != null){
            setAddressLine1(getAddressLine1().trim());
        }
        if(getAddressLine2() != null){
            setAddressLine2(getAddressLine2().trim());
        }
        if(getAddressLine3() != null){
            setAddressLine3(getAddressLine3().trim());
        }
        if(getPhone1() != null){
            setPhone1(getPhone1().trim());
        }
        if(getPhone2() != null){
            setPhone2(getPhone2().trim());
        }
        if(getDealerPlan() != null){
            setDealerPlan(getDealerPlan().trim());
        }
        if(getDealerGroup() != null){
            setDealerGroup(getDealerGroup().trim());
        }
        if(getOfficerCode() != null){
            setOfficerCode(getOfficerCode().trim());
        }
        if(getRevolvingIndicator() != null){
            setRevolvingIndicator(getRevolvingIndicator().trim());
        }
        if(getDealerStatus() != null){
            setDealerStatus(getDealerStatus().trim());
        }
        if(getProductGroup() != null){
            setProductGroup(getProductGroup().trim());
        }
        if(getBranchServed() != null){
            setBranchServed(getBranchServed().trim());
        }
        if(getRecondIndicator() != null){
            setRecondIndicator(getRecondIndicator().trim());
        }
        if(getUsedIndicator() != null){
            setUsedIndicator(getUsedIndicator().trim());
        }
        if(getNewIndicator() != null){
            setNewIndicator(getNewIndicator().trim());
        }
        if(getNewNational() != null){
            setNewNational(getNewNational().trim());
        }
        if(getDealerStatus2() != null){
            setDealerStatus2(getDealerStatus2().trim());
        }
        if(getRescheduleIndicator() != null){
            setRescheduleIndicator(getRescheduleIndicator().trim());
        }
        if(getFisMemberIndicator() != null){
            setFisMemberIndicator(getFisMemberIndicator().trim());
        }
        if(getFisDealerCode() != null){
            setFisDealerCode(getFisDealerCode().trim());
        }
         if(getRemarks() != null){
            setRemarks(getRemarks().trim());
        }
//        if(getType() != null){
//            setType(getType().trim());
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
