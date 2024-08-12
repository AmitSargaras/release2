/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/bus/type/document/subtype/fxnetting/OBFXNetting.java,v 1.3 2003/07/21 05:33:26 lyng Exp $
 */
package com.integrosys.cms.app.collateral.bus.type.document.subtype.fxnetting;

import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.collateral.bus.OBCollateralSubType;
import com.integrosys.cms.app.collateral.bus.type.document.OBDocumentCollateral;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * This class represents an FX/Derivative Document of type Netting Agreement.
 * 
 * @author $Author: lyng $<br>
 * @version $Revision: 1.3 $
 * @since $Date: 2003/07/21 05:33:26 $ Tag: $Name: $
 */
public class OBFXNetting extends OBDocumentCollateral implements IFXNetting {
	/**
	 * Default Constructor.
	 */
	public OBFXNetting() {
		super();
		super.setCollateralSubType(new OBCollateralSubType(ICMSConstant.COLTYPE_DOC_FX_NETTING));
	}

	/**
	 * Construct the object from its interface.
	 * 
	 * @param obj is of type IFXNetting
	 */
	public OBFXNetting(IFXNetting obj) {
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
		else if (!(obj instanceof OBFXNetting)) {
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