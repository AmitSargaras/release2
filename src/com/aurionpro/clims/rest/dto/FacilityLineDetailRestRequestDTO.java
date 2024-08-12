/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:$
 */
package com.aurionpro.clims.rest.dto;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import org.apache.struts.action.ActionErrors;


/**
 * Describe this class. Purpose: To set get and set method for the value needed
 * by AA Detail Description: Have set and get method to store the screen value
 * and get the value from other command class
 * 
 * @author $Author: Jerlin$<br>
 * @version $Revision:$
 * @since $Date: $ Tag: $Name$
 */


public class FacilityLineDetailRestRequestDTO  {
	
	private String newLine;
	private String systemId;
    private String liabBranch;
    private String mainLineCode;
    private String isCurrencyRestriction;
    private String serialNo;
    private String releasedAmount;
    private String utilizedAmount;
    private String releasedDate;
    private String sendToFile;
    private String lmtStartDate;
    private String lmtExpiryDate;
    private String intradayLimitExpDate;
    private String dayLightLimit;
    private String isAvailable;
    private String freeze;
    private String revolvingLine;
    private String scmFlag;
    private String lastAvailDate;
    private String uploadDate;
    private String segment1;
    private String ruleId;
    private String capitalMarketExposure;
    private String pslFlag;
    private String pslValue;
    private String realEstateExposure;
    private String uncondiCanclCommit;
    private String intrestRate;
    private String intrestRateType;
    private String floatingRateType;
    private String margin;
    private String marginAddSub;
    private String realEstate;
    private String commRealEstate;
    private String dayLightLmtAvail;
    private String dayLightLmtAppr;
    private String limiIndays;
    private String closedFlag;
    private String vendorDtls; 
    private String limitCustomerRestrict;
    private String branchAllowed;
    private String productAllowed;
    private String currencyAllowed;
    private String security1;
    private String security2;
    private String security3;
    private String security4;
    private String security5;
    private String security6;
    private String internalRemarks;
    
    private String camId;
    
    private String idlEffectiveFromDate;
	private String idlExpiryDate;
	private String idlAmount;
    

	private List<UdfRestRequestDTO> udfList;
    private List<UdfRestRequestDTO> udf2List;

    private ActionErrors errors;
    private String event;
	
	private List<CoBorrowerDetailsRestRequestDTO> lineCoborrowerList;


	public String getIdlEffectiveFromDate() {
		return idlEffectiveFromDate;
	}


	public void setIdlEffectiveFromDate(String idlEffectiveFromDate) {
		this.idlEffectiveFromDate = idlEffectiveFromDate;
	}


	public String getIdlExpiryDate() {
		return idlExpiryDate;
	}


	public void setIdlExpiryDate(String idlExpiryDate) {
		this.idlExpiryDate = idlExpiryDate;
	}


	public String getIdlAmount() {
		return idlAmount;
	}


	public void setIdlAmount(String idlAmount) {
		this.idlAmount = idlAmount;
	}


	public String getNewLine() {
		return newLine;
	}


	public void setNewLine(String newLine) {
		this.newLine = newLine;
	}


	public String getSystemId() {
		return systemId;
	}


    public String getCamId() {
		return camId;
	}


	public void setCamId(String camId) {
		this.camId = camId;
	}


	public void setSystemId(String systemId) {
		this.systemId = systemId;
	}


	public String getLiabBranch() {
		return liabBranch;
	}


	public void setLiabBranch(String liabBranch) {
		this.liabBranch = liabBranch;
	}


	public String getMainLineCode() {
		return mainLineCode;
	}


	public void setMainLineCode(String mainLineCode) {
		this.mainLineCode = mainLineCode;
	}


	public String getIsCurrencyRestriction() {
		return isCurrencyRestriction;
	}


	public void setIsCurrencyRestriction(String isCurrencyRestriction) {
		this.isCurrencyRestriction = isCurrencyRestriction;
	}


	public String getReleasedAmount() {
		return releasedAmount;
	}


	public void setReleasedAmount(String releasedAmount) {
		this.releasedAmount = releasedAmount;
	}


	public String getUtilizedAmount() {
		return utilizedAmount;
	}


	public void setUtilizedAmount(String utilizedAmount) {
		this.utilizedAmount = utilizedAmount;
	}


	public String getReleasedDate() {
		return releasedDate;
	}


	public void setReleasedDate(String releasedDate) {
		this.releasedDate = releasedDate;
	}


	public String getSendToFile() {
		return sendToFile;
	}


	public void setSendToFile(String sendToFile) {
		this.sendToFile = sendToFile;
	}


	public String getLmtStartDate() {
		return lmtStartDate;
	}


	public void setLmtStartDate(String lmtStartDate) {
		this.lmtStartDate = lmtStartDate;
	}


	public String getLmtExpiryDate() {
		return lmtExpiryDate;
	}


	public void setLmtExpiryDate(String lmtExpiryDate) {
		this.lmtExpiryDate = lmtExpiryDate;
	}


	public String getIntradayLimitExpDate() {
		return intradayLimitExpDate;
	}


	public void setIntradayLimitExpDate(String intradayLimitExpDate) {
		this.intradayLimitExpDate = intradayLimitExpDate;
	}


	public String getDayLightLimit() {
		return dayLightLimit;
	}


	public void setDayLightLimit(String dayLightLimit) {
		this.dayLightLimit = dayLightLimit;
	}


	public String getIsAvailable() {
		return isAvailable;
	}


	public void setIsAvailable(String isAvailable) {
		this.isAvailable = isAvailable;
	}


	public String getFreeze() {
		return freeze;
	}


	public void setFreeze(String freeze) {
		this.freeze = freeze;
	}


	public String getRevolvingLine() {
		return revolvingLine;
	}


	public void setRevolvingLine(String revolvingLine) {
		this.revolvingLine = revolvingLine;
	}


	public String getScmFlag() {
		return scmFlag;
	}


	public void setScmFlag(String scmFlag) {
		this.scmFlag = scmFlag;
	}


	public String getLastAvailDate() {
		return lastAvailDate;
	}


	public void setLastAvailDate(String lastAvailDate) {
		this.lastAvailDate = lastAvailDate;
	}


	public String getUploadDate() {
		return uploadDate;
	}


	public void setUploadDate(String uploadDate) {
		this.uploadDate = uploadDate;
	}


	public String getSegment1() {
		return segment1;
	}


	public void setSegment1(String segment1) {
		this.segment1 = segment1;
	}


	public String getRuleId() {
		return ruleId;
	}


	public void setRuleId(String ruleId) {
		this.ruleId = ruleId;
	}


	public String getCapitalMarketExposure() {
		return capitalMarketExposure;
	}


	public void setCapitalMarketExposure(String capitalMarketExposure) {
		this.capitalMarketExposure = capitalMarketExposure;
	}


	public String getPslFlag() {
		return pslFlag;
	}


	public void setPslFlag(String pslFlag) {
		this.pslFlag = pslFlag;
	}


	public String getRealEstateExposure() {
		return realEstateExposure;
	}


	public void setRealEstateExposure(String realEstateExposure) {
		this.realEstateExposure = realEstateExposure;
	}


	public String getUncondiCanclCommit() {
		return uncondiCanclCommit;
	}


	public void setUncondiCanclCommit(String uncondiCanclCommit) {
		this.uncondiCanclCommit = uncondiCanclCommit;
	}


	public String getIntrestRate() {
		return intrestRate;
	}


	public void setIntrestRate(String intrestRate) {
		this.intrestRate = intrestRate;
	}


	public String getIntrestRateType() {
		return intrestRateType;
	}


	public void setIntrestRateType(String intrestRateType) {
		this.intrestRateType = intrestRateType;
	}


	public String getFloatingRateType() {
		return floatingRateType;
	}


	public void setFloatingRateType(String floatingRateType) {
		this.floatingRateType = floatingRateType;
	}


	public String getMargin() {
		return margin;
	}


	public void setMargin(String margin) {
		this.margin = margin;
	}


	public String getRealEstate() {
		return realEstate;
	}


	public void setRealEstate(String realEstate) {
		this.realEstate = realEstate;
	}


	public String getCommRealEstate() {
		return commRealEstate;
	}


	public void setCommRealEstate(String commRealEstate) {
		this.commRealEstate = commRealEstate;
	}


	public String getDayLightLmtAvail() {
		return dayLightLmtAvail;
	}


	public void setDayLightLmtAvail(String dayLightLmtAvail) {
		this.dayLightLmtAvail = dayLightLmtAvail;
	}


	public String getDayLightLmtAppr() {
		return dayLightLmtAppr;
	}


	public void setDayLightLmtAppr(String dayLightLmtAppr) {
		this.dayLightLmtAppr = dayLightLmtAppr;
	}


	public String getLimiIndays() {
		return limiIndays;
	}


	public void setLimiIndays(String limiIndays) {
		this.limiIndays = limiIndays;
	}


	public String getClosedFlag() {
		return closedFlag;
	}


	public void setClosedFlag(String closedFlag) {
		this.closedFlag = closedFlag;
	}


	public String getLimitCustomerRestrict() {
		return limitCustomerRestrict;
	}


	public void setLimitCustomerRestrict(String limitCustomerRestrict) {
		this.limitCustomerRestrict = limitCustomerRestrict;
	}


	public String getBranchAllowed() {
		return branchAllowed;
	}


	public void setBranchAllowed(String branchAllowed) {
		this.branchAllowed = branchAllowed;
	}


	public String getProductAllowed() {
		return productAllowed;
	}


	public void setProductAllowed(String productAllowed) {
		this.productAllowed = productAllowed;
	}


	public String getCurrencyAllowed() {
		return currencyAllowed;
	}


	public void setCurrencyAllowed(String currencyAllowed) {
		this.currencyAllowed = currencyAllowed;
	}


	public String getSecurity1() {
		return security1;
	}


	public void setSecurity1(String security1) {
		this.security1 = security1;
	}


	public String getSecurity2() {
		return security2;
	}


	public void setSecurity2(String security2) {
		this.security2 = security2;
	}


	public String getSecurity3() {
		return security3;
	}


	public void setSecurity3(String security3) {
		this.security3 = security3;
	}


	public String getSecurity4() {
		return security4;
	}


	public void setSecurity4(String security4) {
		this.security4 = security4;
	}


	public String getSecurity5() {
		return security5;
	}


	public void setSecurity5(String security5) {
		this.security5 = security5;
	}


	public String getSecurity6() {
		return security6;
	}


	public void setSecurity6(String security6) {
		this.security6 = security6;
	}


	public String getInternalRemarks() {
		return internalRemarks;
	}


	public void setInternalRemarks(String internalRemarks) {
		this.internalRemarks = internalRemarks;
	}


	

	public String getSerialNo() {
		return serialNo;
	}


	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}


	public String getPslValue() {
		return pslValue;
	}


	public void setPslValue(String pslValue) {
		this.pslValue = pslValue;
	}


	public String getVendorDtls() {
		return vendorDtls;
	}


	public void setVendorDtls(String vendorDtls) {
		this.vendorDtls = vendorDtls;
	}


	public String getMarginAddSub() {
		return marginAddSub;
	}


	public void setMarginAddSub(String marginAddSub) {
		this.marginAddSub = marginAddSub;
	}


	public List<UdfRestRequestDTO> getUdfList() {
		return udfList;
	}


	public void setUdfList(List<UdfRestRequestDTO> udfList) {
		this.udfList = udfList;
	}


	public List<UdfRestRequestDTO> getUdf2List() {
		return udf2List;
	}


	public void setUdf2List(List<UdfRestRequestDTO> udf2List) {
		this.udf2List = udf2List;
	}


	public ActionErrors getErrors() {
		return errors;
	}


	public void setErrors(ActionErrors errors) {
		this.errors = errors;
	}


	public String getEvent() {
		return event;
	}


	public void setEvent(String event) {
		this.event = event;
	}


	public List<CoBorrowerDetailsRestRequestDTO> getLineCoborrowerList() {
		return lineCoborrowerList;
	}


	public void setLineCoborrowerList(List<CoBorrowerDetailsRestRequestDTO> lineCoborrowerList) {
		this.lineCoborrowerList = lineCoborrowerList;
	}


	
	
}