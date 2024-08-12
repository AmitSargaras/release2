/*
 * Created on Apr 3, 2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.integrosys.cms.ui.manualinput.security;

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
public class SecPledgorDtlForm extends CommonForm implements Serializable {
	private String fromEvent;

	private String isCreate;

	private String indexID;

	private String leIdType;

	private String leId;

	private String customerName;

	private String iDType;

	private String iDNo;

	private String[] selectedPlegor;

	private String limitProfileID;

	private String customerID;

	public SecPledgorDtlForm() {
		selectedPlegor = new String[0];
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
	 * @return Returns the customerName.
	 */
	public String getCustomerName() {
		return customerName;
	}

	/**
	 * @param customerName The customerName to set.
	 */
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	/**
	 * @return Returns the iDType.
	 */
	public String getIDType() {
		return iDType;
	}

	/**
	 * @param iDType The iDType to set.
	 */
	public void setIDType(String iDType) {
		this.iDType = iDType;
	}

	/**
	 * @return Returns the iDNo.
	 */
	public String getIDNo() {
		return iDNo;
	}

	/**
	 * @param iDNo The iDNo to set.
	 */
	public void setIDNo(String iDNo) {
		this.iDNo = iDNo;
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
	 * @return Returns the leId.
	 */
	public String getLeId() {
		return leId;
	}

	/**
	 * @param leId The leId to set.
	 */
	public void setLeId(String leId) {
		this.leId = leId;
	}

	/**
	 * @return Returns the leIdType.
	 */
	public String getLeIdType() {
		return leIdType;
	}

	/**
	 * @param leIdType The leIdType to set.
	 */
	public void setLeIdType(String leIdType) {
		this.leIdType = leIdType;
	}

	/**
	 * @return Returns the selectedPlegor.
	 */
	public String[] getSelectedPlegor() {
		return selectedPlegor;
	}

	/**
	 * @param selectedPlegor The selectedPlegor to set.
	 */
	public void setSelectedPlegor(String[] selectedPlegor) {
		this.selectedPlegor = selectedPlegor;
	}

	public void reset(ActionMapping arg0, HttpServletRequest arg1) {
		selectedPlegor = new String[0];
	}

	public String[][] getMapper() {
		// TODO Auto-generated method stub
		String[][] input = { { "secPledgorDtlForm", "com.integrosys.cms.ui.manualinput.security.SecPledgorDtlMapper" }, };
		return input;
	}
}
