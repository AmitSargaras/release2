/*
 * Created on Apr 2, 2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.integrosys.cms.app.manualinput.security.trx;

import com.integrosys.cms.app.collateral.trx.ICollateralTrxValue;
import com.integrosys.cms.app.collateral.trx.OBCollateralTrxValue;
import com.integrosys.cms.app.transaction.CMSTrxOperation;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.ITrxRouteOperation;

/**
 * @author Administrator
 * 
 *         TODO To change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Style - Code Templates
 */
public abstract class AbstractMISecOperation extends CMSTrxOperation implements ITrxRouteOperation {

	public ICollateralTrxValue createTransaction(ICollateralTrxValue value) throws Exception {
		ICMSTrxValue tempValue = super.createTransaction(value);
		OBCollateralTrxValue newValue = new OBCollateralTrxValue(tempValue);
		newValue.setCollateral(value.getCollateral());
		newValue.setStagingCollateral(value.getStagingCollateral());
		return newValue;
	}

	public ICollateralTrxValue updateTransaction(ICollateralTrxValue value) throws Exception {
		ICMSTrxValue tempValue = super.updateTransaction(value);
		OBCollateralTrxValue newValue = new OBCollateralTrxValue(tempValue);
		newValue.setCollateral(value.getCollateral());
		newValue.setStagingCollateral(value.getStagingCollateral());
		return newValue;
	}
}
