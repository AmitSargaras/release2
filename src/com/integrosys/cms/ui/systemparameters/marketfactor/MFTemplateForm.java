/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.ui.systemparameters.marketfactor;

import java.io.Serializable;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;

import com.integrosys.cms.ui.common.TrxContextForm;

/**
 * @author Administrator
 * 
 *         TODO To change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Style - Code Templates
 */
public class MFTemplateForm extends TrxContextForm implements Serializable {

	private String mFTemplateID = "";

	private String mFTemplateName = "";

	private String mFTemplateStatus = "";

	private String secType;

	private String[] secSubType;

	private String[] unselectSecSubType = new String[0];

	private String[] deletedItemList;

	private List mFItemList;

	private String mFTemplateNameClass = "fieldname";

	private String mFTemplateStatusClass = "fieldname";

	private String secTypeClass = "fieldname";

	private String secSubTypeClass = "fieldname";

	private String isCreate;

	/**
	 * Description : get method for form to get the template Id
	 * 
	 * @return mFTemplateID
	 */
	public String getMFTemplateID() {
		return this.mFTemplateID;
	}

	/**
	 * Description : set the template Id
	 * 
	 * @param mFTemplateID is the template Id
	 */
	public void setMFTemplateID(String mFTemplateID) {
		this.mFTemplateID = mFTemplateID;
	}

	/**
	 * Description : get method for form to get the template name
	 * 
	 * @return mFTemplateName
	 */
	public String getMFTemplateName() {
		return this.mFTemplateName;
	}

	/**
	 * Description : set the template name
	 * 
	 * @param mFTemplateName is the template name
	 */
	public void setMFTemplateName(String mFTemplateName) {
		this.mFTemplateName = mFTemplateName;
	}

	/**
	 * Description : get method for form to get the template status
	 * 
	 * @return mFTemplateStatus
	 */
	public String getMFTemplateStatus() {
		return this.mFTemplateStatus;
	}

	/**
	 * Description : set the template status
	 * 
	 * @param mFTemplateStatus is the template status
	 */
	public void setMFTemplateStatus(String mFTemplateStatus) {
		this.mFTemplateStatus = mFTemplateStatus;
	}

	/**
	 * Description : get method for form to get the security type code
	 * 
	 * @return secType
	 */
	public String getSecType() {
		return this.secType;
	}

	/**
	 * Description : set the security type code
	 * 
	 * @param secType is the security type code
	 */
	public void setSecType(String secType) {
		this.secType = secType;
	}

	/**
	 * Description : get method for form to get the list of security subtype
	 * code
	 * 
	 * @return secSubType
	 */
	public String[] getSecSubType() {
		return this.secSubType;
	}

	/**
	 * Description : set the list of security subtype code
	 * 
	 * @param secSubType is the list of security subtype code
	 */
	public void setSecSubType(String[] secSubType) {
		this.secSubType = secSubType;
	}

	/**
	 * Description : set the list of security subtype code in unselect box
	 * 
	 * @param unselectSecSubType is the list of security subtype code in
	 *        unselect box
	 */
	public void setUnselectSecSubType(String[] unselectSecSubType) {
		this.unselectSecSubType = unselectSecSubType;
	}

	/**
	 * Description : get method for form to get the list of security subtype
	 * code in unselect box
	 * 
	 * @return unselectSecSubType
	 */
	public String[] getUnselectSecSubType() {
		return this.unselectSecSubType;
	}

	/**
	 * Description : set the list of item deleted
	 * 
	 * @param deletedItemList is the list of item deleted
	 */
	public void setDeletedItemList(String[] deletedItemList) {
		this.deletedItemList = deletedItemList;
	}

	/**
	 * Description : get method for form to get the list of item deleted
	 * 
	 * @return deletedItemList
	 */
	public String[] getDeletedItemList() {
		return this.deletedItemList;
	}

	/**
	 * Description : set the list of item added
	 * 
	 * @param mFItemList is the list of item added
	 */
	public void setMFItemList(List mFItemList) {
		this.mFItemList = mFItemList;
	}

	/**
	 * Description : get method for form to get the list of item added
	 * 
	 * @return mFItemList
	 */
	public List getMFItemList() {
		return this.mFItemList;
	}

	public String getMFTemplateNameClass() {
		return this.mFTemplateNameClass;
	}

	public void setMFTemplateNameClass(String mFTemplateNameClass) {
		this.mFTemplateNameClass = mFTemplateNameClass;
	}

	public String getMFTemplateStatusClass() {
		return this.mFTemplateStatusClass;
	}

	public void setMFTemplateStatusClass(String mFTemplateStatusClass) {
		this.mFTemplateStatusClass = mFTemplateStatusClass;
	}

	public String getSecTypeClass() {
		return this.secTypeClass;
	}

	public void setSecTypeClass(String secTypeClass) {
		this.secTypeClass = secTypeClass;
	}

	public String getSecSubTypeClass() {
		return this.secSubTypeClass;
	}

	public void setSecSubTypeClass(String secSubTypeClass) {
		this.secSubTypeClass = secSubTypeClass;
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.integrosys.base.uiinfra.common.CommonForm#getMapper()
	 */
	public String[][] getMapper() {
		// TODO Auto-generated method stub
		String[][] input = {
				{ "MFTemplateForm", "com.integrosys.cms.ui.systemparameters.marketfactor.MFTemplateMapper" },
				{ "theOBTrxContext", "com.integrosys.cms.ui.common.TrxContextMapper" } };
		return input;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.apache.struts.action.ActionForm#reset(org.apache.struts.action.
	 * ActionMapping, javax.servlet.http.HttpServletRequest)
	 */
	public void reset(ActionMapping arg0, HttpServletRequest arg1) {
		// TODO Auto-generated method stub

	}
}
