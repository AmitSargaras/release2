/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/limit/trx/ReadCoBorrowerLimitIDOperation.java,v 1.1 2003/08/01 06:44:26 kllee Exp $
 */
package com.integrosys.cms.app.limit.trx;

//ofa
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;
import com.integrosys.cms.app.limit.bus.ICoBorrowerLimit;
import com.integrosys.cms.app.limit.bus.SBLimitManager;
import com.integrosys.cms.app.limit.bus.SBLimitManagerHome;
import com.integrosys.cms.app.transaction.CMSTrxOperation;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.ITrxReadOperation;

/**
 * This operation is responsible for the creation of a limit doc transaction
 * 
 * @author $Author: kllee $
 * @version $Revision: 1.1 $
 * @since $Date: 2003/08/01 06:44:26 $ Tag: $Name: $
 */
public class ReadCoBorrowerLimitIDOperation extends CMSTrxOperation implements ITrxReadOperation {
	/**
	 * Default Constructor
	 */
	public ReadCoBorrowerLimitIDOperation() {
		super();
	}

	/**
	 * Get the operation name of the current operation
	 * @return String - the operation name of the current operation
	 */
	public String getOperationName() {
		return ICMSConstant.ACTION_READ_CO_LIMIT_ID;
	}

	/**
	 * This method is used to read a transaction object
	 * 
	 * @param val is the ITrxValue object containing the parameters required for
	 *        retrieving a record, such as the transaction ID.
	 * @return ITrxValue containing the requested data.
	 * @throws TransactionException if any other errors occur.
	 */
	public ITrxValue getTransaction(ITrxValue val) throws TransactionException {
		try {
			ICMSTrxValue trxValue = super.getCMSTrxValue(val);
			trxValue = (ICMSTrxValue) getTrxManager().getTrxByRefIDAndTrxType(trxValue.getReferenceID(),
					trxValue.getTransactionType());
			OBCoBorrowerLimitTrxValue newValue = new OBCoBorrowerLimitTrxValue(trxValue);

			String stagingRef = trxValue.getStagingReferenceID();
			String actualRef = trxValue.getReferenceID();

			DefaultLogger.debug(this, "Actual Reference: " + actualRef + " , Staging Reference: " + stagingRef);

			if (null != actualRef) {
				SBLimitManager mgr = getSBLimitManager();
				newValue.setLimit(mgr.getCoBorrowerLimit(Long.parseLong(actualRef)));
			}
			if (null != stagingRef) {
				SBLimitManager mgr = getSBLimitManagerStaging();

				ICoBorrowerLimit stage = mgr.getCoBorrowerLimit(Long.parseLong(stagingRef));

				newValue.setStagingLimit(stage);
			}

			return newValue;
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new TrxOperationException(e);
		}
	}

	/**
	 * Get the SBLimitManager remote interface
	 * 
	 * @return SBLimitManager
	 */
	private SBLimitManager getSBLimitManager() throws TransactionException {
		SBLimitManager home = (SBLimitManager) BeanController.getEJB(ICMSJNDIConstant.SB_LIMIT_MGR_JNDI,
				SBLimitManagerHome.class.getName());
		if (null != home) {
			return home;
		}
		else {
			throw new TransactionException("SBLimitManager is null!");
		}
	}

	/**
	 * Get the SBLimitManager remote interface
	 * 
	 * @return SBLimitManager
	 */
	private SBLimitManager getSBLimitManagerStaging() throws TransactionException {
		SBLimitManager home = (SBLimitManager) BeanController.getEJB(ICMSJNDIConstant.SB_LIMIT_MGR_JNDI_STAGING,
				SBLimitManagerHome.class.getName());
		if (null != home) {
			return home;
		}
		else {
			throw new TransactionException("SBLimitManager is null!");
		}
	}
}