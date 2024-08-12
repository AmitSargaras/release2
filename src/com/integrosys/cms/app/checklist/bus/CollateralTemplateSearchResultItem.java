/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/checklist/bus/CollateralTemplateSearchResultItem.java,v 1.2 2003/08/22 11:13:25 sathish Exp $
 */
package com.integrosys.cms.app.checklist.bus;

//java
import java.io.Serializable;

import com.integrosys.base.techinfra.util.AccessorUtil;

/**
 * This class implements the ICheckList
 * 
 * @author $Author: sathish $<br>
 * @version $Revision: 1.2 $
 * @since $Date: 2003/08/22 11:13:25 $ Tag: $Name: $
 */
public class CollateralTemplateSearchResultItem implements Serializable {
	private String trxID = null;

	private String trxStatus = null;

	private long templateID = com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;

	private String collateralType = null;

	private boolean completeInd = false;

	/**
	 * Get the trx ID
	 * @return String - the trx ID
	 */
	public String getTrxID() {
		return this.trxID;
	}

	/**
	 * Get the trx status
	 * @return String - the trx status
	 */
	public String getTrxStatus() {
		return this.trxStatus;
	}

	/**
	 * Get the templateID
	 * @return templateID - long
	 */
	public long getTemplateID() {
		return this.templateID;
	}

	/**
	 * Get the collateral type
	 * @return String - the collateral type
	 */
	public String getCollateralType() {
		return this.collateralType;
	}

	/**
	 * Get the complete indicator
	 * @return boolean - true if it is completed and false otherwise
	 */
	public boolean getCompleteInd() {
		return this.completeInd;
	}

	/**
	 * Set the trx ID.
	 * @param aTrxID - String
	 */
	public void setTrxID(String aTrxID) {
		this.trxID = aTrxID;
	}

	/**
	 * Set the trx status.
	 * @param aTrxStatus - String
	 */
	public void setTrxStatus(String aTrxStatus) {
		this.trxStatus = aTrxStatus;
	}

	/**
	 * Set the template ID
	 * @param aTemplateID - long
	 */
	public void setTemplateID(long aTemplateID) {
		this.templateID = aTemplateID;
	}

	/**
	 * Set the collateral type
	 * @param aCollateralType - String
	 */
	public void setCollateralType(String aCollateralType) {
		this.collateralType = aCollateralType;
	}

	/**
	 * Set the complete indicator
	 * @param aCompleteInd - boolean
	 */
	public void setCompleteInd(boolean aCompleteInd) {
		this.completeInd = aCompleteInd;
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
