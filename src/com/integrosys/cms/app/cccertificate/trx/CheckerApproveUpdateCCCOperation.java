/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/cccertificate/trx/CheckerApproveUpdateCCCOperation.java,v 1.4 2004/10/06 09:14:03 visveswari Exp $
 */
package com.integrosys.cms.app.cccertificate.trx;

//ofa
import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;
import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.cms.app.cccertificate.bus.CCCertificateException;
import com.integrosys.cms.app.cccertificate.bus.ICCCertificate;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.common.util.CommonUtil;

/**
 * This operation allows a checker to approve a cc certificate update
 * 
 * @author $Author: visveswari $
 * @version $Revision: 1.4 $
 * @since $Date: 2004/10/06 09:14:03 $ Tag: $Name: $
 */
public class CheckerApproveUpdateCCCOperation extends AbstractCCCTrxOperation {
	/**
	 * Default Constructor
	 */
	public CheckerApproveUpdateCCCOperation() {
		super();
	}

	/**
	 * Get the operation name of the current operation
	 * 
	 * @return String - the operation name of the current operation
	 */
	public String getOperationName() {
		return ICMSConstant.ACTION_CHECKER_APPROVE_GENERATE_CCC;
	}

	/**
	 * Process the transaction 1. Update the actual data 2. Update the
	 * transaction record
	 * @param anITrxValue - ITrxValue
	 * @return ITrxResult - the transaction result
	 * @throws TrxOperationException if encounters any error during the
	 *         processing of the transaction
	 */
	public ITrxResult performProcess(ITrxValue anITrxValue) throws TrxOperationException {
		ICCCertificateTrxValue trxValue = getCCCertificateTrxValue(anITrxValue);
		trxValue = updateActualCCCertificate(trxValue);
		trxValue = updateCCCertificateTransaction(trxValue);
		return super.prepareResult(trxValue);
	}

	/**
	 * Post Processing after the trx operation 1. To lock cc checklist items
	 * @param anITrxValue - ITrxValue
	 * @return ITrxResult - the transaction result
	 * @throws TrxOperationException if encounters any error during the
	 *         processing of the transaction
	 */
	public ITrxResult postProcess(ITrxResult result) throws TrxOperationException {
		ICCCertificateTrxValue ccTrxValue = (ICCCertificateTrxValue) result.getTrxValue();
		ICCCertificate actual = ccTrxValue.getCCCertificate();
		systemLockCheckList(actual);
		return result;
	}

	/**
	 * update the actual document item
	 * @param anITrxValue - ITrxValue
	 * @return ICCCertificateTrxValue - the document item trx value
	 * @throws TrxOperationException on errors
	 */
	private ICCCertificateTrxValue updateActualCCCertificate(ICCCertificateTrxValue anICCCertificateTrxValue)
			throws TrxOperationException {
		try {
			ICCCertificate staging = anICCCertificateTrxValue.getStagingCCCertificate();
			ICCCertificate actual = anICCCertificateTrxValue.getCCCertificate();
			ICCCertificate updActual = (ICCCertificate) CommonUtil.deepClone(staging);
			updActual = mergeCCCertificate(actual, updActual);
			updActual.setDateGenerated(DateUtil.getDate());
			if ((staging.getCreditOfficerName() != null)
					&& !staging.getCreditOfficerName().equals(actual.getCreditOfficerName())) {
				updActual.setCreditOfficerDt(DateUtil.getDate());
			}
			if ((staging.getSeniorOfficerName() != null)
					&& !staging.getSeniorOfficerName().equals(actual.getSeniorOfficerName())) {
				updActual.setSeniorOfficerDt(DateUtil.getDate());
			}
			ICCCertificate actualCCCert = getSBCCCertificateBusManager().updateCCCertificate(updActual);
			anICCCertificateTrxValue.setCCCertificate(actualCCCert);
			anICCCertificateTrxValue.setReferenceID(String.valueOf(actualCCCert.getCCCertID()));
			return anICCCertificateTrxValue;
		}
		catch (CCCertificateException ex) {
			throw new TrxOperationException(ex);
		}
		catch (ConcurrentUpdateException ex) {
			throw new TrxOperationException(ex);
		}
		catch (Exception ex) {
			throw new TrxOperationException("Exception in updateActualCCCertificate(): " + ex.toString());
		}
	}
}