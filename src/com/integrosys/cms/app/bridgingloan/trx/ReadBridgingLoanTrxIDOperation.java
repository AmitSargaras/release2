package com.integrosys.cms.app.bridgingloan.trx;

import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.cms.app.bridgingloan.bus.IBridgingLoan;
import com.integrosys.cms.app.bridgingloan.bus.SBBridgingLoanBusManager;
import com.integrosys.cms.app.bridgingloan.bus.SBBridgingLoanBusManagerHome;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;
import com.integrosys.cms.app.transaction.CMSTrxOperation;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.ITrxReadOperation;

/**
 * Describe this class. Purpose: Description:
 * 
 * @author Cynthia<br>
 * @version R1.1
 * @since Mar 21, 2007 Tag: $Name$
 */
public class ReadBridgingLoanTrxIDOperation extends CMSTrxOperation implements ITrxReadOperation {

	/**
	 * Get the operation name of the current operation
	 * @return String - the operation name of the current operation
	 */
	public String getOperationName() {
		return ICMSConstant.ACTION_READ_BRIDGING_LOAN_ID;
	}

	/**
	 * This method is used to read a transaction object
	 * @param anITrxValue - the ITrxValue object containing the parameters
	 *        required for retrieving a record, such as the transaction ID.
	 * @return ITrxValue - containing the requested data.
	 * @throws com.integrosys.base.businfra.transaction.TransactionException if
	 *         any other errors occur.
	 */
	public ITrxValue getTransaction(ITrxValue anITrxValue) throws TransactionException {
		try {
			ICMSTrxValue trxValue = (ICMSTrxValue) anITrxValue;
			trxValue = getTrxManager().getTransaction(trxValue.getTransactionID());

			OBBridgingLoanTrxValue newValue = new OBBridgingLoanTrxValue(trxValue);
			String stagingRef = trxValue.getStagingReferenceID();
			String actualRef = trxValue.getReferenceID();

			if (stagingRef != null) {
				Long stagingID = new Long(stagingRef);
				IBridgingLoan staging = getSBStagingBridgingLoanBusManager().getBridgingLoanByID(stagingID);
				newValue.setStagingBridgingLoan(staging);
			}
			if (actualRef != null) {
				Long actualID = new Long(actualRef);
				IBridgingLoan actual = getSBBridgingLoanBusManager().getBridgingLoanByID(actualID);
				newValue.setBridgingLoan(actual);
			}
			return newValue;
		}
		catch (Exception ex) {
			throw new TrxOperationException(ex.toString());
		}
	}

	/**
	 * Get the home interface for the Document Item Session Bean of the staging
	 * policy cap data
	 * @return SBBridgingLoanBusManager - the home interface for the staging
	 *         policy cap session bean
	 */
	private SBBridgingLoanBusManager getSBStagingBridgingLoanBusManager() throws TransactionException {
		SBBridgingLoanBusManager remote = (SBBridgingLoanBusManager) BeanController.getEJB(
				ICMSJNDIConstant.SB_STAGING_BRIDGING_LOAN_BUS_JNDI, SBBridgingLoanBusManagerHome.class.getName());
		if (remote != null) {
			return remote;
		}
		throw new TransactionException("SBStagingBridgingLoanBusManager is null!");
	}

	/**
	 * Get the home interface for the Document Item Session Bean of the actual
	 * policy cap data
	 * @return SBBridgingLoanBusManager - the home interface for the policy cap
	 *         session bean
	 */
	private SBBridgingLoanBusManager getSBBridgingLoanBusManager() throws TransactionException {
		SBBridgingLoanBusManager remote = (SBBridgingLoanBusManager) BeanController.getEJB(
				ICMSJNDIConstant.SB_BRIDGING_LOAN_BUS_JNDI, SBBridgingLoanBusManagerHome.class.getName());
		if (remote != null) {
			return remote;
		}
		throw new TransactionException("SBBridgingLoanBusManager is null!");
	}
}