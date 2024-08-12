/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/ddn/trx/ReadDDNOperation.java,v 1.3 2005/08/20 10:25:39 hshii Exp $
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
 * This operation is responsible for the creation of a ddn transaction
 * 
 * @author $Author: hshii $
 * @version $Revision: 1.3 $
 * @since $Date: 2005/08/20 10:25:39 $ Tag: $Name: $
 */
public class ReadDDNOperation extends CMSTrxOperation implements ITrxReadOperation {
	/**
	 * Default Constructor
	 */
	public ReadDDNOperation() {
		super();
	}

	/**
	 * Get the operation name of the current operation
	 * @return String - the operation name of the current operation
	 */
	public String getOperationName() {
		return ICMSConstant.ACTION_READ_DDN;
	}

	/**
	 * This method is used to read a transaction object
	 * @param anITrxValue the ITrxValue object containing the parameters
	 *        required for retrieving a record, such as the transaction ID.
	 * @return ITrxValue - containing the requested data.
	 * @throws TransactionException if any other errors occur.
	 */
	public ITrxValue getTransaction(ITrxValue anITrxValue) throws TransactionException {
		try {
			ICMSTrxValue trxValue = (ICMSTrxValue) getTrxManager().getTransaction(anITrxValue.getTransactionID());

			OBDDNTrxValue newValue = new OBDDNTrxValue(trxValue);
			if (((IDDNTrxValue) anITrxValue).getIsLatestActive()) {
				IDDNTrxValue latestActiveTrx = new DDNTrxDAO().getLatestActiveInfo(trxValue.getTransactionID(),
						ICMSConstant.ACTION_CHECKER_APPROVE_GENERATE_DDN);
				newValue.setRemarks(latestActiveTrx.getRemarks());
				newValue.setUserInfo(latestActiveTrx.getUserInfo());
			}

			String stagingRef = trxValue.getStagingReferenceID();
			String actualRef = trxValue.getReferenceID();

			DefaultLogger.debug(this, "Actual Reference: " + actualRef + " , Staging Reference: " + stagingRef);

			if (stagingRef != null) {
				long stagingPK = (new Long(stagingRef)).longValue();
				IDDN stagingDDN = getSBStagingDDNBusManager().getDDN(stagingPK);
				newValue.setStagingDDN(stagingDDN);
			}

			if (actualRef != null) {
				long actualPK = (new Long(actualRef)).longValue();
				IDDN actualDDN = getSBDDNBusManager().getDDN(actualPK);
				newValue.setDDN(actualDDN);
			}
			return newValue;
		}
		catch (Exception ex) {
			throw new TrxOperationException(ex.toString());
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