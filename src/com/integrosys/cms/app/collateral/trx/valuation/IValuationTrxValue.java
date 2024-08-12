/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/trx/IValuationTrxValue.java,v 1.1 2003/07/11 10:12:53 lyng Exp $
 */
package com.integrosys.cms.app.collateral.trx.valuation;

import com.integrosys.cms.app.collateral.bus.IValuation;
import com.integrosys.cms.app.transaction.ICMSTrxValue;

/**
 * This interface defines transaction data for use with CMS. It also contains
 * valuation entity.
 * 
 * @author $Author: lyng $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2003/07/11 10:12:53 $ Tag: $Name: $
 */
public interface IValuationTrxValue extends ICMSTrxValue {
	/**
	 * Get actual valuation contained in this transaction.
	 * 
	 * @return object of valuation type
	 */
	public IValuation getValuation();

	/**
	 * Set actual valuation to this transaction.
	 * 
	 * @param valuation of type IValuation
	 */
	public void setValuation(IValuation valuation);

	/**
	 * Get staging valuation contained in this transaction.
	 * 
	 * @return valuation object
	 */
	public IValuation getStagingValuation();

	/**
	 * Set staging valuation to this transaction.
	 * 
	 * @param stagingValuation of type IValuation
	 */
	public void setStagingValuation(IValuation stagingValuation);
}
