/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/sccertificate/trx/ReadSCCertificateIDOperation.java,v 1.1 2003/08/08 12:45:37 hltan Exp $
 */
package com.integrosys.cms.app.sccertificate.trx;

//ofa
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;
import com.integrosys.cms.app.sccertificate.bus.ISCCertificate;
import com.integrosys.cms.app.sccertificate.bus.SBSCCertificateBusManager;
import com.integrosys.cms.app.sccertificate.bus.SBSCCertificateBusManagerHome;
import com.integrosys.cms.app.transaction.CMSTrxOperation;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.ITrxReadOperation;

/**
 * This operation is responsible for reading a sc certificate trx based on a
 * certificate ID
 * 
 * @author $Author: hltan $
 * @version $Revision: 1.1 $
 * @since $Date: 2003/08/08 12:45:37 $ Tag: $Name: $
 */
public class ReadSCCertificateIDOperation extends CMSTrxOperation implements ITrxReadOperation {
	/**
	 * Default Constructor
	 */
	public ReadSCCertificateIDOperation() {
		super();
	}

	/**
	 * Get the operation name of the current operation
	 * @return String - the operation name of the current operation
	 */
	public String getOperationName() {
		return ICMSConstant.ACTION_READ_SCC_ID;
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
			OBSCCertificateTrxValue newValue = new OBSCCertificateTrxValue(trxValue);

			String stagingRef = trxValue.getStagingReferenceID();
			String actualRef = trxValue.getReferenceID();

			DefaultLogger.debug(this, "Actual Reference: " + actualRef + " , Staging Reference: " + stagingRef);

			if (null != actualRef) {
				long actualPK = Long.parseLong(actualRef);
				SBSCCertificateBusManager mgr = getSBSCCertificateBusManager();
				newValue.setSCCertificate(mgr.getSCCertificate(actualPK));
			}
			if (null != stagingRef) {
				long stagingPK = Long.parseLong(stagingRef);
				SBSCCertificateBusManager mgr = getSBStagingCertificateBusManager();
				ISCCertificate stage = mgr.getSCCertificate(stagingPK);
				newValue.setStagingSCCertificate(stage);
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
	 * @return SBSCCertificateBusManager - the home interface for the staging
	 *         certificate session bean
	 */
	private SBSCCertificateBusManager getSBStagingCertificateBusManager() throws TransactionException {
		SBSCCertificateBusManager remote = (SBSCCertificateBusManager) BeanController.getEJB(
				ICMSJNDIConstant.SB_STAGING_SCCERTIFICATE_BUS_JNDI, SBSCCertificateBusManagerHome.class.getName());
		if (remote != null) {
			return remote;
		}
		throw new TransactionException("SBStagingCertificateBusManager is null!");
	}

	/**
	 * Get the home interface for the Document Item Session Bean of the actual
	 * customer data
	 * @return SBSCCertificateBusManager - the home interface for the
	 *         certificate session bean
	 */
	private SBSCCertificateBusManager getSBSCCertificateBusManager() throws TransactionException {
		SBSCCertificateBusManager remote = (SBSCCertificateBusManager) BeanController.getEJB(
				ICMSJNDIConstant.SB_SCCERTIFICATE_BUS_JNDI, SBSCCertificateBusManagerHome.class.getName());
		if (remote != null) {
			return remote;
		}
		throw new TransactionException("SBSCCertificateBusManager is null!");
	}
}