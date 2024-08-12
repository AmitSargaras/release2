/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collaborationtask/trx/ReadCollateralTaskOperation.java,v 1.2 2005/10/14 08:14:59 hshii Exp $
 */
package com.integrosys.cms.app.collaborationtask.trx;

//ofa
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.collaborationtask.bus.ICollateralTask;
import com.integrosys.cms.app.collaborationtask.bus.SBCollaborationTaskBusManager;
import com.integrosys.cms.app.collaborationtask.bus.SBCollaborationTaskBusManagerHome;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;
import com.integrosys.cms.app.transaction.CMSTransactionDAO;
import com.integrosys.cms.app.transaction.CMSTransactionDAOFactory;
import com.integrosys.cms.app.transaction.CMSTrxOperation;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.ITrxReadOperation;
import com.integrosys.cms.app.transaction.OBCMSTrxHistoryLog;

/**
 * This operation is responsible for the creation of a collateral task
 * transaction
 * 
 * @author $Author: hshii $
 * @version $Revision: 1.2 $
 * @since $Date: 2005/10/14 08:14:59 $ Tag: $Name: $
 */
public class ReadCollateralTaskOperation extends CMSTrxOperation implements ITrxReadOperation {
	/**
	 * Default Constructor
	 */
	public ReadCollateralTaskOperation() {
		super();
	}

	/**
	 * Get the operation name of the current operation
	 * @return String - the operation name of the current operation
	 */
	public String getOperationName() {
		return ICMSConstant.ACTION_READ_COLLATERAL_TASK;
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

			OBCollateralTaskTrxValue newValue = new OBCollateralTaskTrxValue(trxValue);

			String stagingRef = trxValue.getStagingReferenceID();
			String actualRef = trxValue.getReferenceID();

			DefaultLogger.debug(this, "Actual Reference: " + actualRef + " , Staging Reference: " + stagingRef);

			if (stagingRef != null) {
				long stagingPK = (new Long(stagingRef)).longValue();
				ICollateralTask stagingCollateralTask = getSBStagingCollaborationTaskBusManager().getCollateralTask(
						stagingPK);
				newValue.setStagingCollateralTask(stagingCollateralTask);
			}

			if (actualRef != null) {
				long actualPK = (new Long(actualRef)).longValue();
				ICollateralTask actualCollateralTask = getSBCollaborationTaskBusManager().getCollateralTask(actualPK);
				newValue.setCollateralTask(actualCollateralTask);
			}

			OBCMSTrxHistoryLog[] logs = (OBCMSTrxHistoryLog[]) ((CMSTransactionDAO) CMSTransactionDAOFactory.getDAO())
					.getTransactionLogs(anITrxValue.getTransactionID(), ICMSConstant.COL_TASK_MAX_HISTORY).toArray(
							new OBCMSTrxHistoryLog[0]);
			newValue.setTransactionHistory(logs);

			return newValue;
		}
		catch (Exception ex) {
			throw new TrxOperationException(ex.toString());
		}
	}

	/**
	 * Get the home interface for the Document Item Session Bean of the staging
	 * customer data
	 * @return SBCollaborationTaskBusManager - the home interface for the
	 *         staging collaboration task session bean
	 */
	private SBCollaborationTaskBusManager getSBStagingCollaborationTaskBusManager() throws TransactionException {
		SBCollaborationTaskBusManager remote = (SBCollaborationTaskBusManager) BeanController.getEJB(
				ICMSJNDIConstant.SB_STAGING_COLLABORATION_BUS_JNDI, SBCollaborationTaskBusManagerHome.class.getName());
		if (remote != null) {
			return remote;
		}
		throw new TransactionException("SBStagingCollaborationTaskBusManager is null!");
	}

	/**
	 * Get the home interface for the Document Item Session Bean of the actual
	 * customer data
	 * @return SBCollaborationTaskBusManager - the home interface for the
	 *         Collaboration Task session bean
	 */
	private SBCollaborationTaskBusManager getSBCollaborationTaskBusManager() throws TransactionException {
		SBCollaborationTaskBusManager remote = (SBCollaborationTaskBusManager) BeanController.getEJB(
				ICMSJNDIConstant.SB_COLLABORATION_BUS_JNDI, SBCollaborationTaskBusManagerHome.class.getName());
		if (remote != null) {
			return remote;
		}
		throw new TransactionException("SBCollaborationTaskBusManager is null!");
	}
}