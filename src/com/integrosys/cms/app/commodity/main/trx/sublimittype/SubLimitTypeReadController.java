/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/commodity/main/trx/sublimittype/SubLimitTypeReadController.java,v 1.1 2005/10/06 05:08:56 hmbao Exp $
 */
package com.integrosys.cms.app.commodity.main.trx.sublimittype;

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
 * Describe this class. Purpose: Description:
 * 
 * @author BaoHongMan
 * @version R1.4
 * @since 2005-9-15
 * @Tag com.integrosys.cms.app.commodity.main.trx.sublimittype.
 *      SubLimitTypeReadController.java
 */
public class SubLimitTypeReadController extends AbstractTrxController implements ITrxOperationFactory {

	/*
	 * @seecom.integrosys.base.businfra.transaction.AbstractTrxController#
	 * getInstanceName()
	 */
	public String getInstanceName() {
		return ICMSConstant.INSTANCE_COMMODITY_MAIN_SUBLIMITTYPE;
	}

	/*
	 * @see
	 * com.integrosys.base.businfra.transaction.AbstractTrxController#operate
	 * (com.integrosys.base.businfra.transaction.ITrxValue,
	 * com.integrosys.base.businfra.transaction.ITrxParameter)
	 */
	public ITrxResult operate(ITrxValue itrxvalue, ITrxParameter itrxparameter) throws TrxParameterException,
			TrxControllerException, TransactionException {
		DefaultLogger.debug(this, " - operate() - Begin.");
		if (itrxvalue == null) {
			throw new TrxParameterException("ITrxValue is null!");
		}
		if (itrxparameter == null) {
			throw new TrxParameterException("ITrxParameter is null!");
		}
		itrxvalue = setInstanceName(itrxvalue);
		DefaultLogger.debug(this, "Instance Name: " + itrxvalue.getInstanceName());
		ITrxOperation op = getOperation(itrxvalue, itrxparameter);
		DefaultLogger.debug(this, "From state " + itrxvalue.getFromState());
		CMSReadTrxManager mgr = new CMSReadTrxManager();
		ITrxResult result = null;
		try {
			result = mgr.operateTransaction(op, itrxvalue);
			DefaultLogger.debug(this, " - operate() - End.");
			return result;
		}
		catch (TransactionException te) {
			throw te;
		}
		catch (Exception re) {
			re.printStackTrace();
			throw new TrxControllerException("Caught Unknown Exception: " + re.toString());
		}
	}

	/*
	 * @see
	 * com.integrosys.base.businfra.transaction.ITrxOperationFactory#getOperation
	 * (com.integrosys.base.businfra.transaction.ITrxValue,
	 * com.integrosys.base.businfra.transaction.ITrxParameter)
	 */
	public ITrxOperation getOperation(ITrxValue itrxvalue, ITrxParameter param) throws TrxParameterException {
		if (null == param) {
			throw new TrxParameterException("ITrxParameter is null!");
		}
		String action = param.getAction();
		if (action == null) {
			throw new TrxParameterException("Action para is null!");
		}
		DefaultLogger.debug(this, "Action : " + action);
		if (ICMSConstant.ACTION_READ_COMMODITY_MAIN_ID.equals(action)) {
			return new ReadSLTByIdOperation();
		}
		if (ICMSConstant.ACTION_READ_COMMODITY_MAIN_TRXID.equals(action)) {
			return new ReadSLTByTrxIdOperation();
		}
		if (ICMSConstant.ACTION_READ_COMMODITY_MAIN_SLT_GROUP.equals(action)) {
			return new ReadSLTByGroupIdOperation();
		}
		throw new TrxParameterException("Unknow Action: " + action + ".");
	}
}
