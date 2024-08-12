/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/transaction/OBCMSTrxParameter.java,v 1.2 2003/06/12 11:58:56 hltan Exp $
 */
package com.integrosys.cms.app.transaction;

import com.integrosys.base.businfra.transaction.OBTrxParameter;
import com.integrosys.base.techinfra.util.AccessorUtil;

/**
 * This class represents the transaction parameter object for CMS.
 * 
 * @author Alfred Lee
 */
public class OBCMSTrxParameter extends OBTrxParameter implements ICMSTrxParameter {
	/**
	 * Default Constructor
	 */
	public OBCMSTrxParameter() {
		super();
	}

	/**
	 * Constructr the OB from its interface
	 * 
	 * @param in is the ICMSTrxValue object
	 */
	public OBCMSTrxParameter(ICMSTrxParameter in) {
		this();
		AccessorUtil.copyValue(in, this);
	}

	/**
	 * Prints a String representation of this object
	 * 
	 * @return String
	 */
	public String toString() {
		return AccessorUtil.printMethodValue(this);
	}
}
