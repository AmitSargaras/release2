/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/generatereq/trx/ReadDeferralRequestOperation.java,v 1.1 2003/09/11 05:49:17 hltan Exp $
 */
package com.integrosys.cms.app.generatereq.trx;

//ofa
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;
import com.integrosys.cms.app.generatereq.bus.IDeferralRequest;
import com.integrosys.cms.app.generatereq.bus.SBGenerateRequestBusManager;
import com.integrosys.cms.app.generatereq.bus.SBGenerateRequestBusManagerHome;
import com.integrosys.cms.app.transaction.CMSTrxOperation;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.ITrxReadOperation;

/**
 * This operation is responsible for the creation of a cc certificate doc
 * transaction
 * 
 * @author $Author: hltan $
 * @version $Revision: 1.1 $
 * @since $Date: 2003/09/11 05:49:17 $ Tag: $Name: $
 */
public class ReadDeferralRequestOperation extends CMSTrxOperation implements ITrxReadOperation {
	/**
	 * Default Constructor
	 */
	public ReadDeferralRequestOperation() {
		super();
	}

	/**
	 * Get the operation name of the current operation
	 * @return String - the operation name of the current operation
	 */
	public String getOperationName() {
		return ICMSConstant.ACTION_READ_DEFERRAL_REQ;
	}

	/**
	 * This method is used to read a transaction object
	 * @param anITrxValue - the ITrxValue object containing the parameters
	 *        required for retrieving a record, such as the transaction ID.
	 * @return ITrxValue - containing the requested data.
	 * @throws TransactionException if any other errors occur.
	 */
	public ITrxValue getTransaction(ITrxValue anITrxValue) throws TransactionException {
		try {
			ICMSTrxValue trxValue = (ICMSTrxValue) getTrxManager().getTransaction(anITrxValue.getTransactionID());

			OBDeferralRequestTrxValue newValue = new OBDeferralRequestTrxValue(trxValue);

			String stagingRef = trxValue.getStagingReferenceID();
			String actualRef = trxValue.getReferenceID();

			DefaultLogger.debug(this, "Actual Reference: " + actualRef + " , Staging Reference: " + stagingRef);

			if (stagingRef != null) {
				long stagingPK = (new Long(stagingRef)).longValue();
				IDeferralRequest stagingRequest = getSBStagingGenerateRequestBusManager().getDeferralRequest(stagingPK);
				newValue.setStagingDeferralRequest(stagingRequest);
			}

			if (actualRef != null) {
				long actualPK = (new Long(actualRef)).longValue();
				IDeferralRequest actualRequest = getSBGenerateRequestBusManager().getDeferralRequest(actualPK);
				newValue.setDeferralRequest(actualRequest);
			}
			return newValue;
		}
		catch (Exception ex) {
			throw new TrxOperationException(ex.toString());
		}
	}

	/**
	 * To get the remote handler for the staging generate request session bean
	 * @return SBGenerateRequestBusManager - the remote handler for the staging
	 *         generate request session bean
	 */
	protected SBGenerateRequestBusManager getSBStagingGenerateRequestBusManager() {
		SBGenerateRequestBusManager remote = (SBGenerateRequestBusManager) BeanController.getEJB(
				ICMSJNDIConstant.SB_STAGING_GENERATE_REQUEST_BUS_JNDI, SBGenerateRequestBusManagerHome.class.getName());
		return remote;
	}

	/**
	 * To get the remote handler for the certificate session bean
	 * @return SBGenerateRequestBusManager - the remote handler for the
	 *         certificate session bean
	 */
	protected SBGenerateRequestBusManager getSBGenerateRequestBusManager() {
		SBGenerateRequestBusManager remote = (SBGenerateRequestBusManager) BeanController.getEJB(
				ICMSJNDIConstant.SB_GENERATE_REQUEST_BUS_JNDI, SBGenerateRequestBusManagerHome.class.getName());
		return remote;
	}
}