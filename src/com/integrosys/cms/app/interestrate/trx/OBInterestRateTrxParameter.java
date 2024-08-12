/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.app.interestrate.trx;

import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.transaction.OBCMSTrxParameter;

/**
 * This class defines transaction parameters for use with interestrate.
 * 
 * @author $Author: pctan $<br>
 * @version $Revision: $
 * @since $Date: $ Tag: $Name: $
 */
public class OBInterestRateTrxParameter extends OBCMSTrxParameter implements IInterestRateTrxParameter {
	/**
	 * Default Constructor
	 */
	public OBInterestRateTrxParameter() {
		super();
	}

	/**
	 * Construct the object from its interface
	 * 
	 * @param obj is of type IInterestRateTrxParameter
	 */
	public OBInterestRateTrxParameter(IInterestRateTrxParameter obj) {
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