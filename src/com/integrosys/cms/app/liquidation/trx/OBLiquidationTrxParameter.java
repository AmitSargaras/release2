/*
 * Copyright Integro Technologies Pte Ltd
 * $Header$
 */
package com.integrosys.cms.app.liquidation.trx;

import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.transaction.OBCMSTrxParameter;

/**
 * This class defines transaction parameters for use with Liquidation.
 * 
 * @author $Author: lini$<br>
 * @version $Revision$
 * @since $Date$ Tag: $Name$
 */
public class OBLiquidationTrxParameter extends OBCMSTrxParameter implements ILiquidationTrxParameter {
	/**
	 * Default Constructor
	 */
	public OBLiquidationTrxParameter() {
		super();
	}

	/**
	 * Construct the object from its interface
	 * 
	 * @param obj is of type ILiquidationTrxParameter
	 */
	public OBLiquidationTrxParameter(ILiquidationTrxParameter obj) {
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