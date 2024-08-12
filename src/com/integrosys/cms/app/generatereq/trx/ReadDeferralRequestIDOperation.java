/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/generatereq/trx/ReadDeferralRequestIDOperation.java,v 1.1 2003/09/11 05:49:17 hltan Exp $
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
 * This operation is responsible for reading a cc certificate trx based on a
 * certificate ID
 * 
 * @author $Author: hltan $
 * @version $Revision: 1.1 $
 * @since $Date: 2003/09/11 05:49:17 $ Tag: $Name: $
 */
public class ReadDeferralRequestIDOperation extends CMSTrxOperation implements ITrxReadOperation {
	/**
	 * Default Constructor
	 */
	public ReadDeferralRequestIDOperation() {
		super();
	}

	/**
	 * Get the operation name of the current operation
	 * @return String - the operation name of the current operation
	 */
	public String getOperationName() {
		return ICMSConstant.ACTION_READ_CCC_ID;
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
			OBDeferralRequestTrxValue newValue = new OBDeferralRequestTrxValue(trxValue);

			String stagingRef = trxValue.getStagingReferenceID();
			String actualRef = trxValue.getReferenceID();

			DefaultLogger.debug(this, "Actual Reference: " + actualRef + " , Staging Reference: " + stagingRef);

			if (null != actualRef) {
				long actualPK = Long.parseLong(actualRef);
				SBGenerateRequestBusManager mgr = getSBGenerateRequestBusManager();
				newValue.setDeferralRequest(mgr.getDeferralRequest(actualPK));
			}
			if (null != stagingRef) {
				long stagingPK = Long.parseLong(stagingRef);
				SBGenerateRequestBusManager mgr = getSBStagingGenerateRequestBusManager();
				IDeferralRequest stage = mgr.getDeferralRequest(stagingPK);
				newValue.setStagingDeferralRequest(stage);
			}
			return newValue;
		}
		catch (Exception ex) {
			throw new TrxOperationException(ex);
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