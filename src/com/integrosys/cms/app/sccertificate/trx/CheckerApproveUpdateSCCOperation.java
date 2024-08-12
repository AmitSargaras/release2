/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/sccertificate/trx/CheckerApproveUpdateSCCOperation.java,v 1.5 2006/09/26 10:33:21 jychong Exp $
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
import com.integrosys.cms.app.sccertificate.bus.ISCCertificate;
import com.integrosys.cms.app.sccertificate.bus.SCCertificateException;

/**
 * This operation allows a checker to approve a sc certificate update
 * 
 * @author $Author: jychong $
 * @version $Revision: 1.5 $
 * @since $Date: 2006/09/26 10:33:21 $ Tag: $Name: $
 */
public class CheckerApproveUpdateSCCOperation extends AbstractSCCTrxOperation {
	/**
	 * Default Constructor
	 */
	public CheckerApproveUpdateSCCOperation() {
		super();
	}

	/**
	 * Get the operation name of the current operation
	 * 
	 * @return String - the operation name of the current operation
	 */
	public String getOperationName() {
		return ICMSConstant.ACTION_CHECKER_APPROVE_GENERATE_SCC;
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
		ISCCertificateTrxValue trxValue = getSCCertificateTrxValue(anITrxValue);
		trxValue = updateActualSCCertificate(trxValue);
		trxValue = updateSCCertificateTransaction(trxValue);
		return super.prepareResult(trxValue);
	}

	/**
	 * Post Processing after the trx operation 1. To lock security checklist
	 * items
	 * @param anITrxValue - ITrxValue
	 * @return ITrxResult - the transaction result
	 * @throws TrxOperationException if encounters any error during the
	 *         processing of the transaction
	 */
	public ITrxResult postProcess(ITrxResult result) throws TrxOperationException {
		ISCCertificateTrxValue ccTrxValue = (ISCCertificateTrxValue) result.getTrxValue();
		ISCCertificate actual = ccTrxValue.getSCCertificate();
		systemLockCheckList(actual);
		return result;
	}

	/**
	 * Update the actual document item
	 * @param anITrxValue of ITrxValue type
	 * @return ISCCertificateTrxValue - the document item trx value
	 * @throws TrxOperationException on errors
	 */
	private ISCCertificateTrxValue updateActualSCCertificate(ISCCertificateTrxValue anISCCertificateTrxValue)
			throws TrxOperationException {
		try {
			ISCCertificate staging = anISCCertificateTrxValue.getStagingSCCertificate();
			ISCCertificate actual = anISCCertificateTrxValue.getSCCertificate();
			ISCCertificate updActual = (ISCCertificate) CommonUtil.deepClone(staging);

			updActual = mergeSCCertificate(actual, updActual);
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

			ISCCertificate actualSCCert = getSBSCCertificateBusManager().updateSCCertificate(updActual);
			anISCCertificateTrxValue.setSCCertificate(actualSCCert);
			anISCCertificateTrxValue.setReferenceID(String.valueOf(actualSCCert.getSCCertID()));
			return anISCCertificateTrxValue;
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