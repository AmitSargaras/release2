package com.integrosys.cms.app.bridgingloan.trx;

import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.bridgingloan.bus.IBridgingLoan;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.OBCMSTrxValue;

/**
 * Describe this class. Purpose: Description:
 * 
 * @author Cynthia<br>
 * @version R1.1
 * @since Mar 21, 2007 Tag: $Name$
 */
public class OBBridgingLoanTrxValue extends OBCMSTrxValue implements IBridgingLoanTrxValue {

	private IBridgingLoan bridgingLoan = null;

	private IBridgingLoan stageBridgingLoan = null;

	/**
	 * Default Constructor
	 */
	public OBBridgingLoanTrxValue() {
	}

	/**
	 * Construct the object based on the bridging loan object
	 * @param obj - IBridgingLoan
	 */
	public OBBridgingLoanTrxValue(IBridgingLoan obj) {
		AccessorUtil.copyValue(obj, this);
	}

	/**
	 * Construct the object based on its parent info
	 * @param anICMSTrxValue - ICMSTrxValue
	 */
	public OBBridgingLoanTrxValue(ICMSTrxValue anICMSTrxValue) {
		AccessorUtil.copyValue(anICMSTrxValue, this);
	}

	public IBridgingLoan getBridgingLoan() {
		return bridgingLoan;
	}

	public void setBridgingLoan(IBridgingLoan bridgingLoan) {
		this.bridgingLoan = bridgingLoan;
	}

	public IBridgingLoan getStagingBridgingLoan() {
		return stageBridgingLoan;
	}

	public void setStagingBridgingLoan(IBridgingLoan stageBridgingLoan) {
		this.stageBridgingLoan = stageBridgingLoan;
	}
}