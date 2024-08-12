/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/limit/trx/ReadCoBorrowerLimitOperation.java,v 1.2 2006/09/12 06:53:08 hshii Exp $
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
import com.integrosys.cms.app.limit.proxy.ILimitProxy;
import com.integrosys.cms.app.limit.proxy.LimitProxyFactory;
import com.integrosys.cms.app.transaction.CMSTrxOperation;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.ITrxReadOperation;

/**
 * This operation is responsible for the creation of a limit doc transaction
 * 
 * @author $Author: hshii $
 * @version $Revision: 1.2 $
 * @since $Date: 2006/09/12 06:53:08 $ Tag: $Name: $
 */
public class ReadCoBorrowerLimitOperation extends CMSTrxOperation implements ITrxReadOperation {
	/**
	 * Default Constructor
	 */
	public ReadCoBorrowerLimitOperation() {
		super();
	}

	/**
	 * Get the operation name of the current operation
	 * @return String - the operation name of the current operation
	 */
	public String getOperationName() {
		return ICMSConstant.ACTION_READ_CO_LIMIT;
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
			ICMSTrxValue trxValue = (ICMSTrxValue) getTrxManager().getTransaction(val.getTransactionID());
			OBCoBorrowerLimitTrxValue newValue = new OBCoBorrowerLimitTrxValue(trxValue);

			String stagingRef = trxValue.getStagingReferenceID();
			String actualRef = trxValue.getReferenceID();

			DefaultLogger.debug(this, "Actual Reference: " + actualRef + " , Staging Reference: " + stagingRef);

			if (null != actualRef) {
				ILimitProxy proxy = LimitProxyFactory.getProxy();
				newValue.setLimit(proxy.getCoBorrowerLimit(Long.parseLong(actualRef)));
				// SBLimitManager mgr = getSBLimitManager();
				// newValue.setLimit(mgr.getCoBorrowerLimit(Long.parseLong(
				// actualRef)));
			}
			if (null != stagingRef) {
				SBLimitManager mgr = getSBLimitManagerStaging();

				ICoBorrowerLimit stage = mgr.getCoBorrowerLimit(Long.parseLong(stagingRef));

				ICoBorrowerLimit actual = newValue.getLimit();
				if (actual != null) {
					stage.setCollateralAllocations(actual.getCollateralAllocations());
				}

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