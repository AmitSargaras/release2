/*
 * Created on Feb 14, 2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.integrosys.cms.app.manualinput.limit.trx;

import com.integrosys.cms.app.limit.trx.ILimitTrxValue;
import com.integrosys.cms.app.limit.trx.OBLimitTrxValue;
import com.integrosys.cms.app.transaction.CMSTrxOperation;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.ITrxRouteOperation;

/**
 * @author Administrator
 * 
 *         TODO To change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Style - Code Templates
 */
public abstract class AbstractMILmtOperation extends CMSTrxOperation implements ITrxRouteOperation {

	public ILimitTrxValue createTransaction(ILimitTrxValue value) throws Exception {
		ICMSTrxValue tempValue = super.createTransaction(value);
		OBLimitTrxValue newValue = new OBLimitTrxValue(tempValue);
		newValue.setLimit(value.getLimit());
		newValue.setStagingLimit(value.getStagingLimit());
		return newValue;
	}

	public ILimitTrxValue updateTransaction(ILimitTrxValue value) throws Exception {
		ICMSTrxValue tempValue = super.updateTransaction(value);
		OBLimitTrxValue newValue = new OBLimitTrxValue(tempValue);
		newValue.setLimit(value.getLimit());
		newValue.setStagingLimit(value.getStagingLimit());
		return newValue;
	}
}
