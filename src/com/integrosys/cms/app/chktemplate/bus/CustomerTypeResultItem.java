/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/checklist/bus/CustomerTypeResultItem.java,v 1.11 2003/08/22 11:13:25 sathish Exp $
 */
package com.integrosys.cms.app.chktemplate.bus;

//java
import java.io.Serializable;

import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * This class implements the ICheckList
 * 
 * @author $Author: sathish $<br>
 * @version $Revision: 1.11 $
 * @since $Date: 2003/08/22 11:13:25 $ Tag: $Name: $
 */
public class CustomerTypeResultItem implements Serializable {
	private String custTypeCode = null;

	private String custTypeDesc = null;

	private long templateID = com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;

	private String trxID = null;

	private String trxStatus = null;

	CustomerTypeResultItem(String aCustTypeCode, String aCustTypeDesc) {
		setCustTypeCode(aCustTypeCode);
		setCustTypeDesc(aCustTypeDesc);
	}

	public String getCustTypeCode() {
		return this.custTypeCode;
	}

	public String getCustTypeDesc() {
		return this.custTypeDesc;
	}

	public long getTemplateID() {
		return this.templateID;
	}

	public String getTrxID() {
		return this.trxID;
	}

	public String getTrxStatus() {
		return this.trxStatus;
	}

	public void setCustTypeCode(String aCustTypeCode) {
		this.custTypeCode = aCustTypeCode;
	}

	public void setCustTypeDesc(String aCustTypeDesc) {
		this.custTypeDesc = aCustTypeDesc;
	}

	public void setTemplateID(long aTemplateID) {
		this.templateID = aTemplateID;
	}

	public void setTrxID(String aTrxID) {
		this.trxID = aTrxID;
	}

	public void setTrxStatus(String aTrxStatus) {
		this.trxStatus = aTrxStatus;
	}

	/**
	 * If there exist a trx ID, it means that the template is being created.
	 * @return boolean - true if the template is created for the customer type
	 *         and false otherwise
	 */
	public boolean isCompleted() {
		if (ICMSConstant.STATE_ACTIVE.equals(getTrxStatus())) {
			return true;
		}
		else {
			if (getTemplateID() != com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE) {
				return true;
			}
			return false;
		}
	}

	/**
	 * Check if the trx is a new one or not
	 * @return boolean - true if it is new and false otherwise
	 */
	public boolean isNew() {
		if ((getTrxID() == null) || (getTrxID().trim().length() == 0)) {
			return true;
		}
		return false;
	}

	/**
	 * Check if edit is allowed. If a template is pending update then no one can
	 * update that
	 * @return boolean - true if the status of the current template is not
	 *         pending update
	 */
	public boolean isEditAllowed() {
		if ((ICMSConstant.STATE_PENDING_UPDATE.equals(getTrxStatus()))
				|| (ICMSConstant.STATE_REJECTED.equals(getTrxStatus()))) {
			return false;
		}
		return true;
	}

	/**
	 * Prints a String representation of this object
	 * 
	 * @return String
	 */
	public String toString() {
		return AccessorUtil.printMethodValue(this);
	}
}
