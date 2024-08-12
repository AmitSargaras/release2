/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/documentlocation/trx/ReadCCDocumentLocationOperation.java,v 1.1 2004/02/17 02:12:37 hltan Exp $
 */
package com.integrosys.cms.app.documentlocation.trx;

//ofa
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;
import com.integrosys.cms.app.documentlocation.bus.ICCDocumentLocation;
import com.integrosys.cms.app.documentlocation.bus.SBDocumentLocationBusManager;
import com.integrosys.cms.app.documentlocation.bus.SBDocumentLocationBusManagerHome;
import com.integrosys.cms.app.transaction.CMSTrxOperation;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.ITrxReadOperation;

/**
 * This operation is responsible for the creation of a CC document location
 * transaction
 * 
 * @author $Author: hltan $
 * @version $Revision: 1.1 $
 * @since $Date: 2004/02/17 02:12:37 $ Tag: $Name: $
 */
public class ReadCCDocumentLocationOperation extends CMSTrxOperation implements ITrxReadOperation {
	/**
	 * Default Constructor
	 */
	public ReadCCDocumentLocationOperation() {
		super();
	}

	/**
	 * Get the operation name of the current operation
	 * @return String - the operation name of the current operation
	 */
	public String getOperationName() {
		return ICMSConstant.ACTION_READ_CC_DOC_LOC;
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

			OBCCDocumentLocationTrxValue newValue = new OBCCDocumentLocationTrxValue(trxValue);

			String stagingRef = trxValue.getStagingReferenceID();
			String actualRef = trxValue.getReferenceID();

			DefaultLogger.debug(this, "Actual Reference: " + actualRef + " , Staging Reference: " + stagingRef);

			if (stagingRef != null) {
				long stagingPK = (new Long(stagingRef)).longValue();
				ICCDocumentLocation stagingCCDocumentLocation = getSBStagingDocumentLocationBusManager()
						.getCCDocumentLocation(stagingPK);
				newValue.setStagingCCDocumentLocation(stagingCCDocumentLocation);
			}

			if (actualRef != null) {
				long actualPK = (new Long(actualRef)).longValue();
				ICCDocumentLocation actualCCDocumentLocation = getSBDocumentLocationBusManager().getCCDocumentLocation(
						actualPK);
				newValue.setCCDocumentLocation(actualCCDocumentLocation);
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
	 * @return SBDocumentLocationBusManager - the home interface for the staging
	 *         document location session bean
	 */
	private SBDocumentLocationBusManager getSBStagingDocumentLocationBusManager() throws TransactionException {
		SBDocumentLocationBusManager remote = (SBDocumentLocationBusManager) BeanController.getEJB(
				ICMSJNDIConstant.SB_STAGING_DOC_LOCATION_BUS_JNDI, SBDocumentLocationBusManagerHome.class.getName());
		if (remote != null) {
			return remote;
		}
		throw new TransactionException("SBStagingDocumentLocationBusManager is null!");
	}

	/**
	 * Get the home interface for the Document Item Session Bean of the actual
	 * customer data
	 * @return SBDocumentLocationBusManager - the home interface for the
	 *         document location session bean
	 */
	private SBDocumentLocationBusManager getSBDocumentLocationBusManager() throws TransactionException {
		SBDocumentLocationBusManager remote = (SBDocumentLocationBusManager) BeanController.getEJB(
				ICMSJNDIConstant.SB_DOC_LOCATION_BUS_JNDI, SBDocumentLocationBusManagerHome.class.getName());
		if (remote != null) {
			return remote;
		}
		throw new TransactionException("SBDocumentLocationBusManager is null!");
	}
}