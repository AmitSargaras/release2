/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/trx/OBCollateralTrxResult.java,v 1.2 2003/06/13 10:02:37 lyng Exp $: /home/cms/cvsroot/cms/src/com/integrosys/cms/app/collateral/trx/OBCollateralTrxResult.java,v 1.1 2002/09/19 02:26:52 kllee Exp $
 */
package com.integrosys.cms.app.collateral.trx;

import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.transaction.OBCMSTrxResult;

/**
 * This class defines transaction results for use with collateral.
 * 
 * @author $Author: lyng $<br>
 * @version $Revision: 1.2 $
 * @since $Date: 2003/06/13 10:02:37 $ Tag: $Name: $
 */
public class OBCollateralTrxResult extends OBCMSTrxResult implements ICollateralTrxResult {
	/**
	 * Default Constructor
	 */
	public OBCollateralTrxResult() {
		super();
	}

	/**
	 * Construct the object from its interface
	 * 
	 * @param obj is of type ICollateralTrxResult
	 */
	public OBCollateralTrxResult(ICollateralTrxResult obj) {
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