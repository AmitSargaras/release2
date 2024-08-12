/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/cccertificate/trx/ReadCCCertificateIDOperation.java,v 1.2 2003/08/07 01:38:22 hltan Exp $
 */
package com.integrosys.cms.app.cccertificate.trx;

//ofa
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.cccertificate.bus.ICCCertificate;
import com.integrosys.cms.app.cccertificate.bus.SBCCCertificateBusManager;
import com.integrosys.cms.app.cccertificate.bus.SBCCCertificateBusManagerHome;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;
import com.integrosys.cms.app.transaction.CMSTrxOperation;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.ITrxReadOperation;

/**
 * This operation is responsible for reading a cc certificate trx based on a
 * certificate ID
 * 
 * @author $Author: hltan $
 * @version $Revision: 1.2 $
 * @since $Date: 2003/08/07 01:38:22 $ Tag: $Name: $
 */
public class ReadCCCertificateIDOperation extends CMSTrxOperation implements ITrxReadOperation {
	/**
	 * Default Constructor
	 */
	public ReadCCCertificateIDOperation() {
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
			OBCCCertificateTrxValue newValue = new OBCCCertificateTrxValue(trxValue);

			String stagingRef = trxValue.getStagingReferenceID();
			String actualRef = trxValue.getReferenceID();

			DefaultLogger.debug(this, "Actual Reference: " + actualRef + " , Staging Reference: " + stagingRef);

			if (null != actualRef) {
				long actualPK = Long.parseLong(actualRef);
				SBCCCertificateBusManager mgr = getSBCCCertificateBusManager();
				newValue.setCCCertificate(mgr.getCCCertificate(actualPK));
			}
			if (null != stagingRef) {
				long stagingPK = Long.parseLong(stagingRef);
				SBCCCertificateBusManager mgr = getSBStagingCertificateBusManager();
				ICCCertificate stage = mgr.getCCCertificate(stagingPK);
				newValue.setStagingCCCertificate(stage);
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
	 * @return SBCCCertificateBusManager - the home interface for the staging
	 *         certificate session bean
	 */
	private SBCCCertificateBusManager getSBStagingCertificateBusManager() throws TransactionException {
		SBCCCertificateBusManager remote = (SBCCCertificateBusManager) BeanController.getEJB(
				ICMSJNDIConstant.SB_STAGING_CCCERTIFICATE_BUS_JNDI, SBCCCertificateBusManagerHome.class.getName());
		if (remote != null) {
			return remote;
		}
		throw new TransactionException("SBStagingCertificateBusManager is null!");
	}

	/**
	 * Get the home interface for the Document Item Session Bean of the actual
	 * customer data
	 * @return SBCCCertificateBusManager - the home interface for the
	 *         certificate session bean
	 */
	private SBCCCertificateBusManager getSBCCCertificateBusManager() throws TransactionException {
		SBCCCertificateBusManager remote = (SBCCCertificateBusManager) BeanController.getEJB(
				ICMSJNDIConstant.SB_CCCERTIFICATE_BUS_JNDI, SBCCCertificateBusManagerHome.class.getName());
		if (remote != null) {
			return remote;
		}
		throw new TransactionException("SBCCCertificateBusManager is null!");
	}
}