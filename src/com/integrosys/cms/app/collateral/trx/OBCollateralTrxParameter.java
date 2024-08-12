/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/trx/OBCollateralTrxParameter.java,v 1.1 2003/08/13 04:37:16 lyng Exp $
 */
package com.integrosys.cms.app.collateral.trx;

import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.transaction.OBCMSTrxParameter;

/**
 * This class defines transaction parameters for use with collateral.
 * 
 * @author $Author: lyng $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2003/08/13 04:37:16 $: 2002/09/19 02:26:52 $ Tag: $Name: $
 */
public class OBCollateralTrxParameter extends OBCMSTrxParameter implements ICollateralTrxParameter {
	/**
	 * Default Constructor
	 */
	public OBCollateralTrxParameter() {
		super();
	}

	/**
	 * Construct the object from its interface
	 * 
	 * @param obj is of type ICollateralTrxParameter
	 */
	public OBCollateralTrxParameter(ICollateralTrxParameter obj) {
		this();
		AccessorUtil.copyValue(obj, this);
	}

	/**
	 * Return a String representation of this object
	 * 
	 * @return String
	 */
	public String toString() {
		return AccessorUtil.printMethodValue(this);
	}
}