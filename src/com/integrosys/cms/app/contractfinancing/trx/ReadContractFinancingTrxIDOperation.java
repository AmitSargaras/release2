package com.integrosys.cms.app.contractfinancing.trx;

import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;
import com.integrosys.cms.app.contractfinancing.bus.IContractFinancing;
import com.integrosys.cms.app.contractfinancing.bus.SBContractFinancingBusManager;
import com.integrosys.cms.app.contractfinancing.bus.SBContractFinancingBusManagerHome;
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
public class ReadContractFinancingTrxIDOperation extends CMSTrxOperation implements ITrxReadOperation {

	/**
	 * Get the operation name of the current operation
	 * @return String - the operation name of the current operation
	 */
	public String getOperationName() {
		return ICMSConstant.ACTION_READ_CONTRACT_FINANCING_ID;
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
			DefaultLogger.debug(this, ">>>>>>>>>>>>>> In ReadContractFinancingOperation.getTransaction");
			ICMSTrxValue trxValue = (ICMSTrxValue) anITrxValue;
			trxValue = getTrxManager().getTransaction(trxValue.getTransactionID());

			DefaultLogger.debug(this, ">>>>>>>>>>>>>>> Trx Value reference id = " + trxValue.getReferenceID() + "\n"
					+ trxValue);
			OBContractFinancingTrxValue newValue = new OBContractFinancingTrxValue(trxValue);
			String stagingRef = trxValue.getStagingReferenceID();
			String actualRef = trxValue.getReferenceID();

			DefaultLogger.debug(this, ">>>>>>>>>>> Actual Reference: " + actualRef + " , Staging Reference: "
					+ stagingRef);
			DefaultLogger.debug(this, ">>>>>>>>>>> getFromState: " + trxValue.getFromState() + " , getStatus: "
					+ trxValue.getStatus());

			if (stagingRef != null) {
				Long stagingID = new Long(stagingRef);
				IContractFinancing staging = getSBStagingContractFinancingBusManager().getContractFinancingByID(
						stagingID);
				newValue.setStagingContractFinancing(staging);
			}

			if (actualRef != null) {
				Long actualID = new Long(actualRef);
				IContractFinancing actual = getSBContractFinancingBusManager().getContractFinancingByID(actualID);
				newValue.setContractFinancing(actual);
			}
			return newValue;
		}
		catch (Exception ex) {
			throw new TrxOperationException(ex.toString());
		}

	}

	/**
	 * Get the home interface for the Document Item Session Bean of the staging
	 * contract financing data
	 * @return SBContractFinancingBusManager - the home interface for the
	 *         staging contract financing session bean
	 */
	private SBContractFinancingBusManager getSBStagingContractFinancingBusManager() throws TransactionException {
		SBContractFinancingBusManager remote = (SBContractFinancingBusManager) BeanController.getEJB(
				ICMSJNDIConstant.SB_STAGING_CONTRACT_FINANCING_BUS_JNDI, SBContractFinancingBusManagerHome.class
						.getName());
		if (remote != null) {
			return remote;
		}
		throw new TransactionException("SBStagingContractFinancingBusManager is null!");
	}

	/**
	 * Get the home interface for the Document Item Session Bean of the actual
	 * contract financing data
	 * @return SBContractFinancingBusManager - the home interface for the
	 *         contract financing session bean
	 */
	private SBContractFinancingBusManager getSBContractFinancingBusManager() throws TransactionException {
		SBContractFinancingBusManager remote = (SBContractFinancingBusManager) BeanController.getEJB(
				ICMSJNDIConstant.SB_CONTRACT_FINANCING_BUS_JNDI, SBContractFinancingBusManagerHome.class.getName());
		if (remote != null) {
			return remote;
		}
		throw new TransactionException("SBContractFinancingBusManager is null!");
	}
}
