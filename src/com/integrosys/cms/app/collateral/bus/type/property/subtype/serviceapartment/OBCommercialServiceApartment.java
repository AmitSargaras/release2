/**
 *
 * Copyright Integro Technologies Pte Ltd
 * $Header$
 *
 * OBCommercialServiceApartment
 *
 * Created on 6:25:06 PM
 *
 * Purpose:
 * Description:
 *
 * @author $Author$<br>
 * @version $Revision$
 * @since $Date$
 * Tag: $Name$
 */
package com.integrosys.cms.app.collateral.bus.type.property.subtype.serviceapartment;

import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.collateral.bus.OBCollateralSubType;
import com.integrosys.cms.app.collateral.bus.type.property.OBPropertyCollateral;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * Created by IntelliJ IDEA. User: Eric Date: Feb 27, 2007 Time: 6:25:06 PM
 */
public class OBCommercialServiceApartment extends OBPropertyCollateral implements ICommercialServiceApartment {

	/**
	 * Default Constructor.
	 */
	public OBCommercialServiceApartment() {
		super();
		super.setCollateralSubType(new OBCollateralSubType(ICMSConstant.COLTYPE_PROP_SPEC_SERVICE_APT));
	}

	/**
	 * Construct the object from its interface.
	 * 
	 * @param obj is of type ICommercialGeneral
	 */
	public OBCommercialServiceApartment(ICommercialServiceApartment obj) {
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
		else if (!(obj instanceof OBCommercialServiceApartment)) {
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
