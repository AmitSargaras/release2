/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/bus/type/guarantee/subtype/banksameccy/OBBankSameCurrency.java,v 1.2 2003/06/25 07:59:52 lyng Exp $
 */
package com.integrosys.cms.app.collateral.bus.type.guarantee.subtype.banksameccy;

import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.collateral.bus.OBCollateralSubType;
import com.integrosys.cms.app.collateral.bus.type.guarantee.subtype.OBBankCommon;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * This class represents guarantee of type Bank - Same Currency.
 * 
 * @author $Author: lyng $<br>
 * @version $Revision: 1.2 $
 * @since $Date: 2003/06/25 07:59:52 $ Tag: $Name: $
 */
public class OBBankSameCurrency extends OBBankCommon implements IBankSameCurrency {
	/**
	 * Default Constructor.
	 */
	public OBBankSameCurrency() {
		super();
		super.setCollateralSubType(new OBCollateralSubType(ICMSConstant.COLTYPE_GUARANTEE_BANK_SAMECCY));
	}

	/**
	 * Construct the object from its interface.
	 * 
	 * @param obj is of type IBankSameCurrency
	 */
	public OBBankSameCurrency(IBankSameCurrency obj) {
		this();
		AccessorUtil.copyValue(obj, this);
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
		else if (!(obj instanceof OBBankSameCurrency)) {
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