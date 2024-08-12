/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/bus/type/insurance/subtype/creditderivative/OBCreditDerivative.java,v 1.4 2004/01/16 08:23:36 lyng Exp $
 */
package com.integrosys.cms.app.collateral.bus.type.insurance.subtype.creditderivative;

import java.util.Date;

import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.collateral.bus.OBCollateralSubType;
import com.integrosys.cms.app.collateral.bus.type.insurance.OBInsuranceCollateral;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * This class represents Insurance of type Credit Derivatives.
 * 
 * @author $Author: lyng $<br>
 * @version $Revision: 1.4 $
 * @since $Date: 2004/01/16 08:23:36 $ Tag: $Name: $
 */
public class OBCreditDerivative extends OBInsuranceCollateral implements ICreditDerivative {
	private Date iSDADate;

	private Date treasuryDocDate;

	private String isBankRiskConfirmation;

	private String description;
	
	private String arrInsurer;

	public String getArrInsurer() {
		return arrInsurer;
	}

	public void setArrInsurer(String arrInsurer) {
		this.arrInsurer = arrInsurer;
	}

	/**
	 * Default Constructor.
	 */
	public OBCreditDerivative() {
		super();
		super.setCollateralSubType(new OBCollateralSubType(ICMSConstant.COLTYPE_INS_CR_DERIVATIVE));
	}

	/**
	 * Construct the object from its interface.
	 * 
	 * @param obj is of type ICreditDerivative
	 */
	public OBCreditDerivative(ICreditDerivative obj) {
		this();
		AccessorUtil.copyValue(obj, this);
	}

	/**
	 * Get date of ISDA Master Agreement.
	 * 
	 * @return Date
	 */
	public Date getISDADate() {
		return iSDADate;
	}

	/**
	 * Set date of ISDA Master Agreement.
	 * 
	 * @param iSDADate of type Date
	 */
	public void setISDADate(Date iSDADate) {
		this.iSDADate = iSDADate;
	}

	/**
	 * Get date of treasury documentation.
	 * 
	 * @return Date
	 */
	public Date getTreasuryDocDate() {
		return treasuryDocDate;
	}

	/**
	 * Set date of treasury documentation.
	 * 
	 * @param treasuryDocDate of type Date
	 */
	public void setTreasuryDocDate(Date treasuryDocDate) {
		this.treasuryDocDate = treasuryDocDate;
	}

	/**
	 * Get bank risk approval confirmation.
	 * 
	 * @return String
	 */
	public String getIsBankRiskConfirmation() {
		return isBankRiskConfirmation;
	}

	/**
	 * Set bank risk approval confirmation.
	 * 
	 * @param isBankRiskConfirmation of type String
	 */
	public void setIsBankRiskConfirmation(String isBankRiskConfirmation) {
		this.isBankRiskConfirmation = isBankRiskConfirmation;
	}

	/**
	 * Get credit derivatives instrument description.
	 * 
	 * @return String
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Set credit derivatives instrument description.
	 * 
	 * @param description of type String
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Return a String representation of this object.
	 * 
	 * @return String
	 */
	public String toString() {
		return AccessorUtil.printMethodValue(this);
	}

	/**
	 * Test for equality.
	 * 
	 * @param obj is of type Object
	 * @return boolean
	 */
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		else if (!(obj instanceof OBCreditDerivative)) {
			return false;
		}
		else {
			if (obj.hashCode() == this.hashCode()) {
				return true;
			}
			else {
				return false;
			}
		}
	}
}