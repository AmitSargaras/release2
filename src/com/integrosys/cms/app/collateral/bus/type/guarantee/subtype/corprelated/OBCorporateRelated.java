/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/bus/type/guarantee/subtype/corprelated/OBCorporateRelated.java,v 1.2 2003/06/25 07:59:58 lyng Exp $
 */
package com.integrosys.cms.app.collateral.bus.type.guarantee.subtype.corprelated;

import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.collateral.bus.OBCollateralSubType;
import com.integrosys.cms.app.collateral.bus.type.guarantee.subtype.OBCorpCommon;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * This class represents guarantee of type Corporated - Related.
 * 
 * @author $Author: lyng $<br>
 * @version $Revision: 1.2 $
 * @since $Date: 2003/06/25 07:59:58 $ Tag: $Name: $
 */
public class OBCorporateRelated extends OBCorpCommon implements ICorporateRelated {
	/**
	 * Default Constructor.
	 */
	public OBCorporateRelated() {
		super();
		super.setCollateralSubType(new OBCollateralSubType(ICMSConstant.COLTYPE_GUARANTEE_CORP_RELATED));
	}

	/**
	 * Construct the object from its interface.
	 * 
	 * @param obj is of type ICorporateRelated
	 */
	public OBCorporateRelated(ICorporateRelated obj) {
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
		else if (!(obj instanceof OBCorporateRelated)) {
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