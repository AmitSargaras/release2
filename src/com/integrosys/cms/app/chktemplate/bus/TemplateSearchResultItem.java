/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/checklist/bus/TemplateSearchResultItem.java,v 1.3 2003/08/22 11:13:25 sathish Exp $
 */
package com.integrosys.cms.app.chktemplate.bus;

//java
import java.io.Serializable;

import com.integrosys.base.techinfra.util.AccessorUtil;

/**
 * This class implements the ICheckList
 * 
 * @author $Author: sathish $<br>
 * @version $Revision: 1.3 $
 * @since $Date: 2003/08/22 11:13:25 $ Tag: $Name: $
 */
public class TemplateSearchResultItem implements Serializable {
	private String trxID = null;

	private String trxFromState = null;

	private String trxStatus = null;

	private long templateID = com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;

	private String law = null;

	private String legalConstitution = null;

	private String collateralSubType = null;

	/**
	 * Get the trx ID
	 * @return String - the trx ID
	 */
	public String getTrxID() {
		return this.trxID;
	}

	/**
	 * Get the trx from state
	 * @return String - the trx from state
	 */
	public String getTrxFromState() {
		return this.trxFromState;
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
	 * Get the law
	 * @return String - the law
	 */
	public String getLaw() {
		return this.law;
	}

	/**
	 * Get the legal constitution
	 * @return String - the legal constitution
	 */
	public String getLegalConstitution() {
		return this.legalConstitution;
	}

	/**
	 * Get the collateral sub type
	 * @return String - the collateral sub type
	 */
	public String getCollateralSubType() {
		return this.collateralSubType;
	}

	/**
	 * Set the trx ID.
	 * @param aTrxID - String
	 */
	public void setTrxID(String aTrxID) {
		this.trxID = aTrxID;
	}

	/**
	 * Set the trx from state
	 * @param aTrxFromState - String
	 */
	public void setTrxFromState(String aTrxFromState) {
		this.trxFromState = aTrxFromState;
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
	 * Set the law
	 * @param aLaw - String
	 */
	public void setLaw(String aLaw) {
		this.law = aLaw;
	}

	/**
	 * Set the legal constitution
	 * @param aLegalConstitution - String
	 */
	public void setLegalConstitution(String aLegalConstitution) {
		this.legalConstitution = aLegalConstitution;
	}

	/**
	 * Set the collateral sub type
	 * @param aCollateralSubType - String
	 */
	public void setCollateralSubType(String aCollateralSubType) {
		this.collateralSubType = aCollateralSubType;
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
