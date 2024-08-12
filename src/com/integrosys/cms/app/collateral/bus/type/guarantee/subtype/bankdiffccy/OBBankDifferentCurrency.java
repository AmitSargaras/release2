/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/bus/type/guarantee/subtype/bankdiffccy/OBBankDifferentCurrency.java,v 1.2 2003/06/25 07:59:45 lyng Exp $
 */
package com.integrosys.cms.app.collateral.bus.type.guarantee.subtype.bankdiffccy;

import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.collateral.bus.OBCollateralSubType;
import com.integrosys.cms.app.collateral.bus.type.guarantee.subtype.OBBankCommon;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * This class represents guarantee of type Bank - Different Currency.
 * 
 * @author $Author: lyng $<br>
 * @version $Revision: 1.2 $
 * @since $Date: 2003/06/25 07:59:45 $ Tag: $Name: $
 */
public class OBBankDifferentCurrency extends OBBankCommon implements IBankDifferentCurrency {
	/**
	 * Default Constructor.
	 */
	public OBBankDifferentCurrency() {
		super();
		super.setCollateralSubType(new OBCollateralSubType(ICMSConstant.COLTYPE_GUARANTEE_BANK_DIFFCCY));
	}

	/**
	 * Construct the object from its interface.
	 * 
	 * @param obj is of type IBankDifferentCurrency
	 */
	public OBBankDifferentCurrency(IBankDifferentCurrency obj) {
		this();
		AccessorUtil.copyValue(obj, this);
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
		else if (!(obj instanceof OBBankDifferentCurrency)) {
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