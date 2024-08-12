/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/sccertificate/trx/CheckerApproveCreateSCCOperation.java,v 1.12 2006/10/27 11:04:46 czhou Exp $
 */
package com.integrosys.cms.app.sccertificate.trx;

//java
import java.rmi.RemoteException;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.cms.app.cccertificate.bus.CCCertificateException;
import com.integrosys.cms.app.cccertificate.proxy.CCCertificateProxyManagerFactory;
import com.integrosys.cms.app.cccertificate.proxy.ICCCertificateProxyManager;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.ddn.bus.DDNException;
import com.integrosys.cms.app.ddn.proxy.DDNProxyManagerFactory;
import com.integrosys.cms.app.ddn.proxy.IDDNProxyManager;
import com.integrosys.cms.app.limit.bus.ILimitProfile;
import com.integrosys.cms.app.limit.bus.ITATEntry;
import com.integrosys.cms.app.limit.bus.LimitException;
import com.integrosys.cms.app.limit.proxy.ILimitProxy;
import com.integrosys.cms.app.limit.proxy.LimitProxyFactory;
import com.integrosys.cms.app.limit.trx.ILimitProfileTrxValue;
import com.integrosys.cms.app.sccertificate.bus.ISCCertificate;
import com.integrosys.cms.app.sccertificate.bus.SCCertificateException;
import com.integrosys.cms.app.transaction.ICMSTrxResult;

/**
 * This operation allows a checker to approve a sc certificate create
 * 
 * @author $Author: czhou $
 * @version $Revision: 1.12 $
 * @since $Date: 2006/10/27 11:04:46 $ Tag: $Name: $
 */
public class CheckerApproveCreateSCCOperation extends AbstractSCCTrxOperation {
	/**
	 * Default Constructor
	 */
	public CheckerApproveCreateSCCOperation() {
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
	 * Process the transaction 1. Create the actual data 2. Update the
	 * transaction record
	 * @param anITrxValue of ITrxValue type
	 * @return ITrxResult - the transaction result
	 * @throws TrxOperationException if encounters any error during the
	 *         processing of the transaction
	 */
	public ITrxResult performProcess(ITrxValue anITrxValue) throws TrxOperationException {
		ISCCertificateTrxValue trxValue = getSCCertificateTrxValue(anITrxValue);
		trxValue = createActualSCCertificate(trxValue);
		trxValue = updateSCCertificateTransaction(trxValue);
		return super.prepareResult(trxValue);
	}

	/**
	 * Post Processing after the trx operation 1. To update the TAT for CCC
	 * generation 2. To complete the BCA if SCC is generated or if it is a clean
	 * type BCA
	 * @param anITrxValue - ITrxValue
	 * @return ITrxResult - the transaction result
	 * @throws TrxOperationException if encounters any error during the
	 *         processing of the transaction
	 */
	public ITrxResult postProcess(ITrxResult result) throws TrxOperationException {
		try {
			ISCCertificateTrxValue ccTrxValue = (ISCCertificateTrxValue) result.getTrxValue();
			systemLockCheckList(ccTrxValue.getSCCertificate());
			long limitProfileID = ccTrxValue.getSCCertificate().getLimitProfileID();
			ILimitProxy proxy = LimitProxyFactory.getProxy();
			ILimitProfileTrxValue trxValue = proxy.getTrxLimitProfile(limitProfileID);
			ILimitProfile profile = trxValue.getLimitProfile();

			// Update the DDN so that it is no longer valid if there exist
			IDDNProxyManager mgr = DDNProxyManagerFactory.getDDNProxyManager();
			mgr.sccIssued(limitProfileID);

			ICMSTrxResult resultTAT = proxy.sccGenerated(trxValue);

			// -- R1.5 CR146 - Removed CCC Generation, therefore should not
			// check for CCC anymore
			// if (completedCCCGeneration(trxValue.getLimitProfile()))
			// {
			// if (isCCCGenerated(trxValue.getLimitProfile()))
			// {
			// DefaultLogger.debug(this, "TimeStamp for BCA Completed !!!");
			// trxValue = proxy.getTrxLimitProfile(limitProfileID);
			// resultTAT =
			// proxy.completeProcessBCA((ILimitProfileTrxValue)resultTAT
			// .getTrxValue());
			// }
			// }
			trxValue = proxy.getTrxLimitProfile(limitProfileID);
			profile = trxValue.getLimitProfile();

			// check if BCA is complete, and update the CCC status if all is
			// complete
			boolean sccGen = true; // since there is only 1 SCC per BCA
			DefaultLogger.debug(this, "All SCC generated.");
			trxValue = prepareSCCFlag(trxValue);

			// -- R1.5 CR146 - Removed CCC Generation, therefore should not
			// check for CCC anymore
			// ICCCertificateProxyManager cccProxy =
			// CCCertificateProxyManagerFactory.getCCCertificateProxyManager();
			// boolean cccGen = cccProxy.isAllCCCGenerated(profile);
			// if(false == cccGen) { //not completed yet
			// DefaultLogger.debug(this, "Not all CCC generated.");
			// if(true == sccGen) {
			// systemUpdateLimitProfile(trxValue, proxy);
			// }
			// return result;
			// }
			// else {
			// DefaultLogger.debug(this, "All CCC generated.");
			// }

			boolean limitCompleted = proxy.areAllLimitsActivated(profile);
			if (false == limitCompleted) { // not completed yet
				DefaultLogger.debug(this, "Not all limits activated.");
				if (true == sccGen) {
					systemUpdateLimitProfile(trxValue, proxy);
				}
				return result;
			}
			else {
				DefaultLogger.debug(this, "All limits activated.");
			}

			// R1.5 UAT CMS-3551 -- starts
			int bflStatus = proxy.getBFLStatus(profile);
			if (!((bflStatus == ICMSConstant.BFL_NOT_REQUIRED) || (bflStatus == ICMSConstant.BFL_COMPLETED))) {
				DefaultLogger.debug(this, "BFL NOT COMPLETED.");
				return result;
			}

			// bca is complete
			trxValue = prepareCompleteBCA(trxValue);

			systemUpdateLimitProfile(trxValue, proxy);
			return result;
		}
		catch (DDNException ex) {
			throw new TrxOperationException("Exception in postProcess", ex);
		}
		catch (ConcurrentUpdateException ex) {
			throw new TrxOperationException("Exception in postProcess", ex);
		}
		catch (LimitException ex) {
			throw new TrxOperationException("Exception in postProcess", ex);
		}
		catch (Exception e) {
			throw new TrxOperationException("Caught Unknown Exception in postProcess!", e);
		}
	}

	/**
	 * Method to persist the limit profile via system update
	 */
	private void systemUpdateLimitProfile(ILimitProfileTrxValue trxValue, ILimitProxy proxy)
			throws TrxOperationException {
		try {
			proxy.systemUpdateLimitProfile(trxValue);
		}
		catch (Exception e) {
			DefaultLogger.error(this, "Caught Exception in systemUpdateLimitProfile!", e);
			throw new TrxOperationException("Caught Exception in systemUpdateLimitProfile!", e);
		}
	}

	/**
	 * Method to set the CCC flag indicator
	 */
	private ILimitProfileTrxValue prepareSCCFlag(ILimitProfileTrxValue trxValue) {
		ILimitProfile profile = trxValue.getLimitProfile();
		ILimitProfile stageProfile = trxValue.getStagingLimitProfile();

		profile.setSCCCompleteInd(true);
		stageProfile.setSCCCompleteInd(true);

		trxValue.setLimitProfile(profile);
		trxValue.setStagingLimitProfile(stageProfile);

		return trxValue;
	}

	/**
	 * Method to set the CCC flag indicator
	 */
	private ILimitProfileTrxValue prepareCompleteBCA(ILimitProfileTrxValue trxValue) {
		ILimitProfile profile = trxValue.getLimitProfile();
		ILimitProfile stageProfile = trxValue.getStagingLimitProfile();

		profile.setBCACompleteInd(true);
		stageProfile.setBCACompleteInd(true);

		trxValue.setLimitProfile(profile);
		trxValue.setStagingLimitProfile(stageProfile);

		return trxValue;
	}

	private boolean completedCCCGeneration(ILimitProfile anILimitProfile) throws TrxOperationException {
		try {
			ICCCertificateProxyManager proxy = CCCertificateProxyManagerFactory.getCCCertificateProxyManager();
			int noOfCCCRequired = proxy.getNoOfCCCRequired(anILimitProfile);
			int noOfCCCGenerated = proxy.getNoOfCCCGenerated(anILimitProfile);
			if (noOfCCCRequired == noOfCCCGenerated) {
				return true;
			}
			return false;
		}
		catch (CCCertificateException ex) {
			throw new TrxOperationException("Exception in completedCCCGeneration", ex);
		}
	}

	private boolean isCCCGenerated(ILimitProfile anILimitProfile) {
		ITATEntry[] tatList = anILimitProfile.getTATEntries();

		if ((tatList == null) || (tatList.length == 0)) {
			return false;
		}

		for (int ii = 0; ii < tatList.length; ii++) {
			if (tatList[ii].getTATServiceCode().equals(ICMSConstant.TAT_CODE_GEN_CCC)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Create the actual document item
	 * @param anITrxValue of ITrxValue type
	 * @return ISCCertificateTrxValue - the document item trx value
	 * @throws TrxOperationException on errors
	 */
	private ISCCertificateTrxValue createActualSCCertificate(ISCCertificateTrxValue anISCCertificateTrxValue)
			throws TrxOperationException {
		try {
			ISCCertificate scCert = anISCCertificateTrxValue.getStagingSCCertificate();
			scCert.setDateGenerated(DateUtil.getDate());
			if ((scCert.getCreditOfficerName() != null) && (scCert.getCreditOfficerName().trim().length() > 0)) {
				scCert.setCreditOfficerDt(DateUtil.getDate());
			}
			if ((scCert.getSeniorOfficerName() != null) && (scCert.getSeniorOfficerName().trim().length() > 0)) {
				scCert.setSeniorOfficerDt(DateUtil.getDate());
			}
			ISCCertificate actualSCCert = getSBSCCertificateBusManager().createSCCertificate(scCert);
			anISCCertificateTrxValue.setSCCertificate(actualSCCert);
			anISCCertificateTrxValue.setReferenceID(String.valueOf(actualSCCert.getSCCertID()));
			return anISCCertificateTrxValue;
		}
		catch (SCCertificateException ex) {
			throw new TrxOperationException(ex);
		}
		catch (RemoteException ex) {
			throw new TrxOperationException("Exception in createActualSCCertificate(): " + ex.toString());
		}
	}
}