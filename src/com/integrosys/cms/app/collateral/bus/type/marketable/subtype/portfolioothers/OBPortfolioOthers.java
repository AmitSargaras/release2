/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/bus/type/marketable/subtype/portfolioothers/OBPortfolioOthers.java,v 1.4 2003/08/19 10:58:34 lyng Exp $
 */
package com.integrosys.cms.app.collateral.bus.type.marketable.subtype.portfolioothers;

import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.collateral.bus.OBCollateralSubType;
import com.integrosys.cms.app.collateral.bus.type.marketable.subtype.OBPortfolioCommon;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * This class represents Marketable of type Portfolio of Securities Controlled
 * via Custodian.
 * 
 * @author $Author: lyng $<br>
 * @version $Revision: 1.4 $
 * @since $Date: 2003/08/19 10:58:34 $ Tag: $Name: $
 */
public class OBPortfolioOthers extends OBPortfolioCommon implements IPortfolioOthers {
	/**
	 * Default Constructor.
	 */
	public OBPortfolioOthers() {
		super();
		super.setCollateralSubType(new OBCollateralSubType(ICMSConstant.COLTYPE_MAR_PORTFOLIO_OTHERS));
	}

	/**
	 * Construct the object from its interface.
	 * 
	 * @param obj is of type IPortfolioOthers
	 */
	public OBPortfolioOthers(IPortfolioOthers obj) {
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
		else if (!(obj instanceof OBPortfolioOthers)) {
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