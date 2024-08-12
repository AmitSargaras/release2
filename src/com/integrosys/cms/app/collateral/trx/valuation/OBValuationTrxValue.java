/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/trx/OBValuationTrxValue.java,v 1.2 2003/07/14 07:36:50 lyng Exp $
 */
package com.integrosys.cms.app.collateral.trx.valuation;

import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.collateral.bus.IValuation;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.OBCMSTrxValue;

/**
 * This class defines transaction data for use with CMS.
 * 
 * @author $Author: lyng $<br>
 * @version $Revision: 1.2 $
 * @since $Date: 2003/07/14 07:36:50 $ Tag: $Name: $
 */
public class OBValuationTrxValue extends OBCMSTrxValue implements IValuationTrxValue {
	private IValuation valuation;

	private IValuation stagingValuation;

	/**
	 * Default Constructor
	 */
	public OBValuationTrxValue() {
		super();
		setTransactionType(ICMSConstant.INSTANCE_VALUATION);
	}

	/**
	 * Construct an object from its interface
	 * 
	 * @param obj is of type IValuationTrxValue
	 */
	public OBValuationTrxValue(IValuationTrxValue obj) {
		this();
		AccessorUtil.copyValue(obj, this);
	}

	/**
	 * Construct an object from its interface
	 * 
	 * @param obj is of type ICMSTrxValue
	 */
	public OBValuationTrxValue(ICMSTrxValue obj) {
		this();
		AccessorUtil.copyValue(obj, this);
	}

	/**
	 * Get actual valuation contained in this transaction.
	 * 
	 * @return object of valuation type
	 */
	public IValuation getValuation() {
		return valuation;
	}

	/**
	 * Set actual valuation to this transaction.
	 * 
	 * @param valuation of type IValuation
	 */
	public void setValuation(IValuation valuation) {
		this.valuation = valuation;
	}

	/**
	 * Get staging valuation contained in this transaction.
	 * 
	 * @return valuation object
	 */
	public IValuation getStagingValuation() {
		return stagingValuation;
	}

	/**
	 * Set staging valuation to this transaction.
	 * 
	 * @param stagingValuation of type IValuation
	 */
	public void setStagingValuation(IValuation stagingValuation) {
		this.stagingValuation = stagingValuation;
	}

	/**
	 * Return a String representation of the object
	 * 
	 * @return String
	 */
	public String toString() {
		return AccessorUtil.printMethodValue(this);
	}
}