/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/transaction/OBCMSTrxResult.java,v 1.3 2003/08/25 00:55:55 sathish Exp $
 */
package com.integrosys.cms.app.transaction;

import com.integrosys.base.businfra.transaction.OBTrxResult;
import com.integrosys.base.techinfra.util.AccessorUtil;

/**
 * This class represents the transaction result object for CMS.
 * 
 * @author Alfred Lee
 */
public class OBCMSTrxResult extends OBTrxResult implements ICMSTrxResult {
	/**
	 * Default Constructor
	 */
	public OBCMSTrxResult() {
		super();
	}

	/**
	 * Constructr the OB from its interface
	 * 
	 * @param in is the ICMSTrxResult object
	 */
	public OBCMSTrxResult(ICMSTrxResult in) {
		this();
		AccessorUtil.copyValue(in, this);
	}

	private String lastUserInfo;

	public String getLastUserInfo() {
		return lastUserInfo;
	}

	public void setLastUserInfo(String lastUserInfo) {
		this.lastUserInfo = lastUserInfo;
	}

	public String getLastRemarks() {
		return lastRemarks;
	}

	public void setLastRemarks(String lastRemarks) {
		this.lastRemarks = lastRemarks;
	}

	private String lastRemarks;

	/**
	 * Prints a String representation of this object
	 * 
	 * @return String
	 */
	public String toString() {
		return AccessorUtil.printMethodValue(this);
	}
}
