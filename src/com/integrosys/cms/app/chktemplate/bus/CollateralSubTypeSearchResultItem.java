/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/checklist/bus/CollateralSubTypeSearchResultItem.java,v 1.4 2003/08/22 11:13:25 sathish Exp $
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
 * @version $Revision: 1.4 $
 * @since $Date: 2003/08/22 11:13:25 $ Tag: $Name: $
 */
public class CollateralSubTypeSearchResultItem implements Serializable {
	private String collateralSubTypeCode = null;

	private String collateralSubTypeDesc = null;

	private long templateID = com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;

	private String trxID = null;

	private String trxStatus = null;

	/**
	 * Default Constructor
	 */
	public CollateralSubTypeSearchResultItem() {
	}

	/**
	 * Constructor
	 * @param aCollateralSubTypeCode - String
	 * @param aCollateralSubTypeDesc - String
	 */
	public CollateralSubTypeSearchResultItem(String aCollateralSubTypeCode, String aCollateralSubTypeDesc) {
		setCollateralSubTypeCode(aCollateralSubTypeCode);
		setCollateralSubTypeDesc(aCollateralSubTypeDesc);
	}

	/**
	 * Get the collateral subtype code
	 * @return String - the collateral subtype code
	 */
	public String getCollateralSubTypeCode() {
		return this.collateralSubTypeCode;
	}

	/**
	 * Get the collateral subtype description
	 * @return String - the collateral subtype description
	 */
	public String getCollateralSubTypeDesc() {
		return this.collateralSubTypeDesc;
	}

	/**
	 * Get the template ID
	 * @return long - the template ID
	 */
	public long getTemplateID() {
		return this.templateID;
	}

	/**
	 * Get the transaction ID
	 * @return String - the transaction ID
	 */
	public String getTrxID() {
		return this.trxID;
	}

	/**
	 * Get the transaction status
	 * @return String - the transaction status
	 */
	public String getTrxStatus() {
		return this.trxStatus;
	}

	/**
	 * Set the Collateral subtype code
	 * @param aCollateralSubTypeCode - String
	 */
	public void setCollateralSubTypeCode(String aCollateralSubTypeCode) {
		this.collateralSubTypeCode = aCollateralSubTypeCode;
	}

	/**
	 * Set the Collateral subtype description
	 * @param aCollateralSubTypeDesc - String
	 */
	public void setCollateralSubTypeDesc(String aCollateralSubTypeDesc) {
		this.collateralSubTypeDesc = aCollateralSubTypeDesc;
	}

	/**
	 * Set the template ID
	 * @param aTemplateID - long
	 */
	public void setTemplateID(long aTemplateID) {
		this.templateID = aTemplateID;
	}

	/**
	 * Set the transaction ID
	 * @param aTrxID - String
	 */
	public void setTrxID(String aTrxID) {
		this.trxID = aTrxID;
	}

	/**
	 * Set the transaction status
	 * @param aTrxStatus - String
	 */
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
