/*
 * Created on 2007-2-22
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.integrosys.cms.ui.manualinput.limit;

import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;

import com.integrosys.base.uiinfra.common.CommonForm;

/**
 * @author Administrator
 * 
 *         TODO To change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Style - Code Templates
 */
public class LmtSecMappingForm extends CommonForm implements Serializable {
	private String fromEvent;

	private String limitProfileID;

	private String customerID;

	private String isCreate;

	private String indexID;

	private String securityId;

	private String sourceId;

	private String securitySubtype;

	private String[] selectedSec;
	
	private String secBookingCountry;
	
	private String secCurrency;
	
	private String secBookingOrg;
	
	private String collateralCode;

	public String getCollateralCode() {
		return collateralCode;
	}

	public void setCollateralCode(String collateralCode) {
		this.collateralCode = collateralCode;
	}

	public String getSecBookingOrg() {
		return secBookingOrg;
	}

	public void setSecBookingOrg(String secBookingOrg) {
		this.secBookingOrg = secBookingOrg;
	}

	public String getSecCurrency() {
		return secCurrency;
	}

	public void setSecCurrency(String secCurrency) {
		this.secCurrency = secCurrency;
	}

	public String getSecBookingCountry() {
		return secBookingCountry;
	}

	public void setSecBookingCountry(String secBookingCountry) {
		this.secBookingCountry = secBookingCountry;
	}

	public LmtSecMappingForm() {
		selectedSec = new String[0];
	}

	/**
	 * @return Returns the limitProfileId.
	 */
	public String getLimitProfileID() {
		return limitProfileID;
	}

	/**
	 * @param limitProfileId The limitProfileId to set.
	 */
	public void setLimitProfileID(String limitProfileID) {
		this.limitProfileID = limitProfileID;
	}

	/**
	 * @return Returns the customerID.
	 */
	public String getCustomerID() {
		return customerID;
	}

	/**
	 * @param customerID The customerID to set.
	 */
	public void setCustomerID(String customerID) {
		this.customerID = customerID;
	}

	/**
	 * @return Returns the sourceId.
	 */
	public String getSourceId() {
		return sourceId;
	}

	/**
	 * @param sourceId The sourceId to set.
	 */
	public void setSourceId(String sourceId) {
		this.sourceId = sourceId;
	}

	/**
	 * @return Returns the fromEvent.
	 */
	public String getFromEvent() {
		return fromEvent;
	}

	/**
	 * @param fromEvent The fromEvent to set.
	 */
	public void setFromEvent(String fromEvent) {
		this.fromEvent = fromEvent;
	}

	/**
	 * @return Returns the indexID.
	 */
	public String getIndexID() {
		return indexID;
	}

	/**
	 * @param indexID The indexID to set.
	 */
	public void setIndexID(String indexID) {
		this.indexID = indexID;
	}

	/**
	 * @return Returns the isCreate.
	 */
	public String getIsCreate() {
		return isCreate;
	}

	/**
	 * @param isCreate The isCreate to set.
	 */
	public void setIsCreate(String isCreate) {
		this.isCreate = isCreate;
	}

	/**
	 * @return Returns the securityId.
	 */
	public String getSecurityId() {
		return securityId;
	}

	/**
	 * @param securityId The securityId to set.
	 */
	public void setSecurityId(String securityId) {
		this.securityId = securityId;
	}

	/**
	 * @return Returns the securitySubtype.
	 */
	public String getSecuritySubtype() {
		return securitySubtype;
	}

	/**
	 * @param securitySubtype The securitySubtype to set.
	 */
	public void setSecuritySubtype(String securitySubtype) {
		this.securitySubtype = securitySubtype;
	}

	/**
	 * @return Returns the selectedSec.
	 */
	public String[] getSelectedSec() {
		return selectedSec;
	}

	/**
	 * @param selectedSec The selectedSec to set.
	 */
	public void setSelectedSec(String[] selectedSec) {
		this.selectedSec = selectedSec;
	}

	public void reset(ActionMapping arg0, HttpServletRequest arg1) {
		selectedSec = new String[0];
	}

	public String[][] getMapper() {
		// TODO Auto-generated method stub
		String[][] input = { { "lmtSecForm", "com.integrosys.cms.ui.manualinput.limit.LmtSecMapper" }, };
		return input;
	}
}
