/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/ddn/trx/ReadDDNIDOperation.java,v 1.3 2005/08/20 10:25:39 hshii Exp $
 */
package com.integrosys.cms.app.ddn.trx;

//ofa
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;
import com.integrosys.cms.app.ddn.bus.IDDN;
import com.integrosys.cms.app.ddn.bus.SBDDNBusManager;
import com.integrosys.cms.app.ddn.bus.SBDDNBusManagerHome;
import com.integrosys.cms.app.transaction.CMSTrxOperation;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.ITrxReadOperation;

/**
 * This operation is responsible for reading a ddn trx based on a ddn ID
 * 
 * @author $Author: hshii $
 * @version $Revision: 1.3 $
 * @since $Date: 2005/08/20 10:25:39 $ Tag: $Name: $
 */
public class ReadDDNIDOperation extends CMSTrxOperation implements ITrxReadOperation {
	/**
	 * Default Constructor
	 */
	public ReadDDNIDOperation() {
		super();
	}

	/**
	 * Get the operation name of the current operation
	 * @return String - the operation name of the current operation
	 */
	public String getOperationName() {
		return ICMSConstant.ACTION_READ_DDN_ID;
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
			OBDDNTrxValue newValue = new OBDDNTrxValue(trxValue);
			if (((IDDNTrxValue) val).getIsLatestActive()) {
				IDDNTrxValue latestActiveTrx = new DDNTrxDAO().getLatestActiveInfo(trxValue.getTransactionID(),
						ICMSConstant.ACTION_CHECKER_APPROVE_GENERATE_DDN);
				newValue.setRemarks(latestActiveTrx.getRemarks());
				newValue.setUserInfo(latestActiveTrx.getUserInfo());
			}

			String stagingRef = trxValue.getStagingReferenceID();
			String actualRef = trxValue.getReferenceID();

			DefaultLogger.debug(this, "Actual Reference: " + actualRef + " , Staging Reference: " + stagingRef);

			if (null != actualRef) {
				long actualPK = Long.parseLong(actualRef);
				SBDDNBusManager mgr = getSBDDNBusManager();
				newValue.setDDN(mgr.getDDN(actualPK));
			}
			if (null != stagingRef) {
				long stagingPK = Long.parseLong(stagingRef);
				SBDDNBusManager mgr = getSBStagingDDNBusManager();
				IDDN stage = mgr.getDDN(stagingPK);
				newValue.setStagingDDN(stage);
			}
			return newValue;
		}
		catch (Exception ex) {
			throw new TrxOperationException(ex);
		}
	}

	/**
	 * Get the home interface for the Document Item Session Bean of the staging
	 * customer data
	 * @return SBDDNBusManager - the home interface for the staging ddn session
	 *         bean
	 */
	private SBDDNBusManager getSBStagingDDNBusManager() throws TransactionException {
		SBDDNBusManager remote = (SBDDNBusManager) BeanController.getEJB(ICMSJNDIConstant.SB_STAGING_DDN_BUS_JNDI,
				SBDDNBusManagerHome.class.getName());
		if (remote != null) {
			return remote;
		}
		throw new TransactionException("SBStagingDDNBusManager is null!");
	}

	/**
	 * Get the home interface for the Document Item Session Bean of the actual
	 * customer data
	 * @return SBDDNBusManager - the home interface for the ddn session bean
	 */
	private SBDDNBusManager getSBDDNBusManager() throws TransactionException {
		SBDDNBusManager remote = (SBDDNBusManager) BeanController.getEJB(ICMSJNDIConstant.SB_DDN_BUS_JNDI,
				SBDDNBusManagerHome.class.getName());
		if (remote != null) {
			return remote;
		}
		throw new TransactionException("SBDDNBusManager is null!");
	}
}