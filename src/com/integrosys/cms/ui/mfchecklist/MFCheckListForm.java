/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.ui.mfchecklist;

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
public class MFCheckListForm extends TrxContextForm implements Serializable {

	private String mFTemplateID = "";

	private String mFTemplateName = "";

	private String lastUpdateDate;

	private String[] valuerAssignFactorList;

	private List mFItemList;

	private String colCollateralID;

	public String getColCollateralID() {
		return this.colCollateralID;
	}

	public void setColCollateralID(String colCollateralID) {
		this.colCollateralID = colCollateralID;
	}

	public String getMFTemplateID() {
		return this.mFTemplateID;
	}

	public void setMFTemplateID(String mFTemplateID) {
		this.mFTemplateID = mFTemplateID;
	}

	public String getMFTemplateName() {
		return this.mFTemplateName;
	}

	public void setMFTemplateName(String mFTemplateName) {
		this.mFTemplateName = mFTemplateName;
	}

	public String getLastUpdateDate() {
		return lastUpdateDate;
	}

	public void setLastUpdateDate(String lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	public String[] getValuerAssignFactorList() {
		return this.valuerAssignFactorList;
	}

	public void setValuerAssignFactorList(String[] value) {
		this.valuerAssignFactorList = value;
	}

	public void setMFItemList(List mFItemList) {
		this.mFItemList = mFItemList;
	}

	public List getMFItemList() {
		return this.mFItemList;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.integrosys.base.uiinfra.common.CommonForm#getMapper()
	 */
	public String[][] getMapper() {
		// TODO Auto-generated method stub
		String[][] input = { { "MFCheckListForm", "com.integrosys.cms.ui.mfchecklist.MFCheckListMapper" },
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
