/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/sccertificate/trx/CheckerApproveUpdatePSCCOperation.java,v 1.4 2006/09/26 10:33:21 jychong Exp $
 */
package com.integrosys.cms.app.sccertificate.trx;

//ofa
import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;
import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.common.util.CommonUtil;
import com.integrosys.cms.app.sccertificate.bus.IPartialSCCertificate;
import com.integrosys.cms.app.sccertificate.bus.SCCertificateException;

/**
 * This operation allows a checker to approve a partial sc certificate update
 * 
 * @author $Author: jychong $
 * @version $Revision: 1.4 $
 * @since $Date: 2006/09/26 10:33:21 $ Tag: $Name: $
 */
public class CheckerApproveUpdatePSCCOperation extends AbstractPSCCTrxOperation {
	/**
	 * Default Constructor
	 */
	public CheckerApproveUpdatePSCCOperation() {
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
	 * Process the transaction 1. Update the actual data 2. Update the
	 * transaction record
	 * @param anITrxValue of ITrxValue type
	 * @return ITrxResult - the transaction result
	 * @throws TrxOperationException if encounters any error during the
	 *         processing of the transaction
	 */
	public ITrxResult performProcess(ITrxValue anITrxValue) throws TrxOperationException {
		IPartialSCCertificateTrxValue trxValue = getPartialSCCertificateTrxValue(anITrxValue);
		trxValue = updateActualPartialSCCertificate(trxValue);
		trxValue = updatePartialSCCertificateTransaction(trxValue);
		return super.prepareResult(trxValue);
	}

	/**
	 * Update the actual document item
	 * @param anITrxValue of ITrxValue type
	 * @return IPartialSCCertificateTrxValue - the document item trx value
	 * @throws TrxOperationException on errors
	 */
	private IPartialSCCertificateTrxValue updateActualPartialSCCertificate(
			IPartialSCCertificateTrxValue anIPartialSCCertificateTrxValue) throws TrxOperationException {
		try {
			IPartialSCCertificate staging = anIPartialSCCertificateTrxValue.getStagingPartialSCCertificate();
			IPartialSCCertificate actual = anIPartialSCCertificateTrxValue.getPartialSCCertificate();
			IPartialSCCertificate updActual = (IPartialSCCertificate) CommonUtil.deepClone(staging);

			updActual = mergePartialSCCertificate(actual, updActual);
			updActual.setDateGenerated(DateUtil.getDate());
			if (staging.getCreditOfficerName() != null) {
				updActual.setCreditOfficerDt(DateUtil.getDate());
			}
			else {
				updActual.setCreditOfficerDt(null);
			}
			if (staging.getSeniorOfficerName() != null) {
				updActual.setSeniorOfficerDt(DateUtil.getDate());
			}
			else {
				updActual.setSeniorOfficerDt(null);
			}

			IPartialSCCertificate actualSCCert = getSBSCCertificateBusManager().updatePartialSCCertificate(updActual);
			anIPartialSCCertificateTrxValue.setPartialSCCertificate(actualSCCert);
			anIPartialSCCertificateTrxValue.setReferenceID(String.valueOf(actualSCCert.getSCCertID()));
			return anIPartialSCCertificateTrxValue;
		}
		catch (ConcurrentUpdateException ex) {
			throw new TrxOperationException(ex);
		}
		catch (SCCertificateException ex) {
			throw new TrxOperationException(ex);
		}
		catch (Exception ex) {
			throw new TrxOperationException("Exception in updateActualSCCertificate(): " + ex.toString());
		}
	}

}