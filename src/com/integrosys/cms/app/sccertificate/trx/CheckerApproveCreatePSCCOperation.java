/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/sccertificate/trx/CheckerApproveCreatePSCCOperation.java,v 1.3 2004/10/06 09:16:06 visveswari Exp $
 */
package com.integrosys.cms.app.sccertificate.trx;

//java
import java.rmi.RemoteException;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.sccertificate.bus.IPartialSCCertificate;
import com.integrosys.cms.app.sccertificate.bus.SCCertificateException;

/**
 * This operation allows a checker to approve a sc certificate create
 * 
 * @author $Author: visveswari $
 * @version $Revision: 1.3 $
 * @since $Date: 2004/10/06 09:16:06 $ Tag: $Name: $
 */
public class CheckerApproveCreatePSCCOperation extends AbstractPSCCTrxOperation {
	/**
	 * Default Constructor
	 */
	public CheckerApproveCreatePSCCOperation() {
		super();
	}

	/**
	 * Get the operation name of the current operation
	 * 
	 * @return String - the operation name of the current operation
	 */
	public String getOperationName() {
		return ICMSConstant.ACTION_CHECKER_APPROVE_GENERATE_PSCC;
	}

	/**
	 * Process the transaction 1. Create the actual data 2. Update the
	 * transaction record
	 * @param anITrxValue of ITrxValue type
	 * @return ITrxResult - the transaction result
	 * @throws TrxOperationException if encounters any error during the
	 *         processing of the transaction
	 */
	public ITrxResult performProcess(ITrxValue anITrxValue) throws TrxOperationException {
		IPartialSCCertificateTrxValue trxValue = getPartialSCCertificateTrxValue(anITrxValue);
		trxValue = createActualPartialSCCertificate(trxValue);
		trxValue = updatePartialSCCertificateTransaction(trxValue);
		return super.prepareResult(trxValue);
	}

	/**
	 * Create the actual document item
	 * @param anITrxValue of ITrxValue type
	 * @return IPartialSCCertificateTrxValue - the document item trx value
	 * @throws TrxOperationException on errors
	 */
	private IPartialSCCertificateTrxValue createActualPartialSCCertificate(
			IPartialSCCertificateTrxValue anIPartialSCCertificateTrxValue) throws TrxOperationException {
		try {
			IPartialSCCertificate scCert = anIPartialSCCertificateTrxValue.getStagingPartialSCCertificate();
			scCert.setDateGenerated(DateUtil.getDate());
			if ((scCert.getCreditOfficerName() != null) && (scCert.getCreditOfficerName().trim().length() > 0)) {
				scCert.setCreditOfficerDt(DateUtil.getDate());
			}
			if ((scCert.getSeniorOfficerName() != null) && (scCert.getSeniorOfficerName().trim().length() > 0)) {
				scCert.setSeniorOfficerDt(DateUtil.getDate());
			}
			IPartialSCCertificate actualSCCert = getSBSCCertificateBusManager().createPartialSCCertificate(scCert);
			anIPartialSCCertificateTrxValue.setPartialSCCertificate(actualSCCert);
			anIPartialSCCertificateTrxValue.setReferenceID(String.valueOf(actualSCCert.getSCCertID()));
			return anIPartialSCCertificateTrxValue;
		}
		catch (SCCertificateException ex) {
			throw new TrxOperationException(ex);
		}
		catch (RemoteException ex) {
			throw new TrxOperationException("Exception in createActualPartialSCCertificate(): " + ex.toString());
		}
	}
}