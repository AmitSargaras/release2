/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/custodian/trx/CustodianReadController.java,v 1.3 2003/07/28 02:09:12 hltan Exp $
 */
package com.integrosys.cms.app.custodian.trx;

import com.integrosys.base.businfra.transaction.AbstractTrxController;
import com.integrosys.base.businfra.transaction.ITrxOperation;
import com.integrosys.base.businfra.transaction.ITrxOperationFactory;
import com.integrosys.base.businfra.transaction.ITrxParameter;
import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxControllerException;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.transaction.CMSReadTrxManager;

/**
 * Put Description here
 * @author $Author: hltan $
 * @version $Revision: 1.3 $
 * @since $Date: 2003/07/28 02:09:12 $ Tag: $Name: $
 */

public class CustodianReadController extends AbstractTrxController implements ITrxOperationFactory {
	public CustodianReadController() {
	}

	public String getInstanceName() {
		return ICMSConstant.INSTANCE_CUSTODIAN;
	} // is this correct?

	public ITrxResult operate(ITrxValue value, ITrxParameter param) throws TrxParameterException,
			TrxControllerException, TransactionException {
		if (null == value) {
			throw new TrxParameterException("ITrxValue is null!");
		}
		if (null == param) {
			throw new TrxParameterException("ITrxParameter is null!");
		}
		value = setInstanceName(value);
		DefaultLogger.debug(this, "Instance Name: " + value.getInstanceName());
		ITrxOperation op = getOperation(value, param);
		DefaultLogger.debug(this, "From state " + value.getFromState());
		CMSReadTrxManager mgr = new CMSReadTrxManager();

		ITrxResult result = null;
		try {
			result = mgr.operateTransaction(op, value);
			return result;
		}
		catch (TransactionException te) {
			throw te;
		}
		catch (Exception re) {
			throw new TrxControllerException("Caught Unknown Exception: " + re.toString());
		}
	}

	public ITrxOperation getOperation(ITrxValue value, ITrxParameter param) throws TrxParameterException {

		if (param.getAction() != null) {
			if (param.getAction().equals(ICMSConstant.ACTION_READ_CUSTODIAN_DOC)) {
				return new ReadCustodianDocTrxOperation();
			}
			if (param.getAction().equals(ICMSConstant.ACTION_READ_CUSTODIAN_ID_DOC)) {
				return new ReadCustodianDocIDTrxOperation();
			}
		}
		throw new TrxParameterException("No Action specified");
	}

}
