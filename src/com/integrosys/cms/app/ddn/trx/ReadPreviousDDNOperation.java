/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/ddn/trx/ReadPreviousDDNOperation.java,v 1.3 2005/08/25 08:46:10 hshii Exp $
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
 * This operation is to read previous active DDN.
 * 
 * @author $Author: hshii $
 * @version $Revision: 1.3 $
 * @since $Date: 2005/08/25 08:46:10 $ Tag: $Name: $
 */
public class ReadPreviousDDNOperation extends CMSTrxOperation implements ITrxReadOperation {
	/**
	 * Default Constructor
	 */
	public ReadPreviousDDNOperation() {
		super();
	}

	/**
	 * Get the operation name of the current operation.
	 * 
	 * @return operation name ICMSConstant.ACTION_READ_PREVIOUS_DDN
	 */
	public String getOperationName() {
		return ICMSConstant.ACTION_READ_PREVIOUS_DDN;
	}

	/**
	 * This method is used to read a transaction object.
	 * 
	 * @param trxVal transaction value containing the parameters required for
	 *        retrieving a record, such as the trx id
	 * @return transaction value containing the requested data
	 * @throws TransactionException if any other errors occur
	 */
	public ITrxValue getTransaction(ITrxValue trxVal) throws TransactionException {
		try {
			ICMSTrxValue trxValue = super.getCMSTrxValue(trxVal);
			IDDNTrxValue prevActiveTrx = null;
			if ((trxVal.getTransactionID() != null) && (trxVal.getTransactionID().length() > 0)) {
				trxValue = (ICMSTrxValue) getTrxManager().getTransaction(trxVal.getTransactionID());
				prevActiveTrx = new DDNTrxDAO().getPrevApprovedHistoryTrxValue(trxValue.getTransactionID(),
						ICMSConstant.ACTION_CHECKER_APPROVE_GENERATE_DDN);
			}
			else {
				trxValue = (ICMSTrxValue) getTrxManager().getTrxByRefIDAndTrxType(trxValue.getReferenceID(),
						trxValue.getTransactionType());
				prevActiveTrx = new DDNTrxDAO().getDDNApprovedHistoryTrxValue(trxValue.getTransactionID(),
						ICMSConstant.ACTION_CHECKER_APPROVE_GENERATE_DDN);
			}

			OBDDNTrxValue newValue = new OBDDNTrxValue(trxValue);

			if ((prevActiveTrx != null)
					&& (newValue.getStatus().equals(ICMSConstant.STATE_PENDING_UPDATE)
							|| newValue.getStatus().equals(ICMSConstant.STATE_REJECTED)
							|| newValue.getOpDesc()
									.equals(ICMSConstant.ACTION_MAKER_CLOSE_REJECTED_UPDATE_GENERATE_DDN) || ((newValue
							.getTransactionSubType() != null) && newValue.getTransactionSubType().equals(
							ICMSConstant.ACTION_MAKER_UPDATE_DDN_SUBTYPE)))) {
				newValue.setRemarks(prevActiveTrx.getRemarks());
				newValue.setUserInfo(prevActiveTrx.getUserInfo());
			}

			String actualRef = newValue.getReferenceID();
			String stagingRef = newValue.getStagingReferenceID();

			DefaultLogger.info(this, "Actual Reference: " + actualRef + " , Staging Reference: " + stagingRef);

			if (stagingRef != null) {
				IDDN stagingDDN = getSBStagingDDNBusManager().getDDN(Long.parseLong(stagingRef));
				newValue.setStagingDDN(stagingDDN);
			}

			if (actualRef != null) {
				IDDN actualDDN = getSBDDNBusManager().getDDN(Long.parseLong(actualRef));
				newValue.setDDN(actualDDN);
			}
			return newValue;
		}
		catch (Exception ex) {
			throw new TrxOperationException(ex.toString());
		}
	}

	/**
	 * Get the staging home interface for DDN.
	 * 
	 * @return SBDDNBusManager the home interface for the staging ddn session
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
	 * Get the actual home interface for DDN.
	 * 
	 * @return SBDDNBusManager the home interface for the ddn session bean
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