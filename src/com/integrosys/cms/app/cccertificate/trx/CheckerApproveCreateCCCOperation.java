/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/cccertificate/trx/CheckerApproveCreateCCCOperation.java,v 1.20 2005/08/16 06:36:49 kchua Exp $
 */
package com.integrosys.cms.app.cccertificate.trx;

//java
import java.rmi.RemoteException;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.cms.app.cccertificate.bus.CCCertificateException;
import com.integrosys.cms.app.cccertificate.bus.ICCCertificate;
import com.integrosys.cms.app.cccertificate.proxy.CCCertificateProxyManagerFactory;
import com.integrosys.cms.app.cccertificate.proxy.ICCCertificateProxyManager;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;
import com.integrosys.cms.app.customer.bus.CustomerException;
import com.integrosys.cms.app.customer.bus.ICMSCustomer;
import com.integrosys.cms.app.customer.bus.SBCustomerManager;
import com.integrosys.cms.app.customer.bus.SBCustomerManagerHome;
import com.integrosys.cms.app.limit.bus.ILimit;
import com.integrosys.cms.app.limit.bus.ILimitProfile;
import com.integrosys.cms.app.limit.bus.ITATEntry;
import com.integrosys.cms.app.limit.bus.LimitException;
import com.integrosys.cms.app.limit.proxy.ILimitProxy;
import com.integrosys.cms.app.limit.proxy.LimitProxyFactory;
import com.integrosys.cms.app.limit.trx.ILimitProfileTrxValue;
import com.integrosys.cms.app.sccertificate.proxy.ISCCertificateProxyManager;
import com.integrosys.cms.app.sccertificate.proxy.SCCertificateProxyManagerFactory;
import com.integrosys.cms.app.transaction.ICMSTrxResult;

/**
 * This operation allows a checker to approve a cc certificate create
 * 
 * @author $Author: kchua $
 * @version $Revision: 1.20 $
 * @since $Date: 2005/08/16 06:36:49 $ Tag: $Name: $
 */
public class CheckerApproveCreateCCCOperation extends AbstractCCCTrxOperation {
	/**
	 * Default Constructor
	 */
	public CheckerApproveCreateCCCOperation() {
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
	 * Process the transaction 1. Create the actual data 2. Update the
	 * transaction record
	 * @param anITrxValue - ITrxValue
	 * @return ITrxResult - the transaction result
	 * @throws TrxOperationException if encounters any error during the
	 *         processing of the transaction
	 */
	public ITrxResult performProcess(ITrxValue anITrxValue) throws TrxOperationException {
		ICCCertificateTrxValue trxValue = getCCCertificateTrxValue(anITrxValue);
		trxValue = createActualCCCertificate(trxValue);
		trxValue = updateCCCertificateTransaction(trxValue);
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
			ICCCertificateTrxValue ccTrxValue = (ICCCertificateTrxValue) result.getTrxValue();
			ILimitProxy proxy = LimitProxyFactory.getProxy();
			ICCCertificate actual = ccTrxValue.getCCCertificate();
			systemLockCheckList(actual);
			long limitProfileID = actual.getLimitProfileID();
			if (limitProfileID == com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE) {
				if ((actual.getSubProfileID() != com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE)
						&& (actual.getCCCertCategory().equals(ICMSConstant.CHECKLIST_NON_BORROWER))) {
					updateCustomerStatus(actual.getSubProfileID(), ICMSConstant.CCC_COMPLETED);
				}
				return result;
			}
			ILimitProfileTrxValue trxValue = proxy.getTrxLimitProfile(limitProfileID);
			ILimitProfile profile = trxValue.getLimitProfile();
			boolean cccGen = completedCCCGeneration(profile);
			// CCC timestamped only for main borrower
			if (isMainBorrower(ccTrxValue)) {
				// trxValue = proxy.getTrxLimitProfile(limitProfileID);
				ICMSTrxResult resultTAT = proxy.cccGenerated(trxValue);
				DefaultLogger.debug(this, "CCC Generated !!!");
				// if (completedCCCGeneration(profile))
				if (cccGen) {
					if (isCleanType(trxValue.getLimitProfile())) {
						DefaultLogger.debug(this, "CLEAN TYPE BFL !!!");
						resultTAT = proxy.completeProcessBCA((ILimitProfileTrxValue) resultTAT.getTrxValue());
						return result;
					}
					if (isSCCGenerated(trxValue.getLimitProfile())) {
						DefaultLogger.debug(this, "SCC GENERATED !!!");
						resultTAT = proxy.completeProcessBCA((ILimitProfileTrxValue) resultTAT.getTrxValue());
					}
				}
				trxValue = (ILimitProfileTrxValue) resultTAT.getTrxValue();
			}

			// check if BCA is complete, and update the CCC status if all is
			// complete
			// ICCCertificateProxyManager cccProxy =
			// CCCertificateProxyManagerFactory.getCCCertificateProxyManager();
			// boolean cccGen = completedCCCGeneration(profile);
			if (false == cccGen) { // not completed yet
				DefaultLogger.debug(this, "Not all CCC generated.");
				return result;
			}
			else {
				DefaultLogger.debug(this, "All CCC generated.");
				trxValue = prepareCCCFlag(trxValue);
			}
			boolean limitCompleted = proxy.areAllLimitsActivated(profile);
			if (false == limitCompleted) { // not completed yet
				DefaultLogger.debug(this, "Not all limits activated.");
				if (true == cccGen) {
					systemUpdateLimitProfile(trxValue, proxy);
				}
				return result;
			}
			else {
				DefaultLogger.debug(this, "All limits activated.");
			}
			ISCCertificateProxyManager sccProxy = SCCertificateProxyManagerFactory.getSCCertificateProxyManager();
			boolean sccGen = sccProxy.isSCCFullyGenerated(profile);
			if (false == sccGen) {
				DefaultLogger.debug(this, "Not all SCC generated.");
				if (true == cccGen) {
					systemUpdateLimitProfile(trxValue, proxy);
				}
				return result; // not completed yet
			}
			else {
				DefaultLogger.debug(this, "All SCC generated.");
				// help to set the SCC flag otherwise it'll never be set if the
				// SCC is clean type
				trxValue = prepareSCCFlag(trxValue);
			}

			// bca is complete
			trxValue = prepareCompleteBCA(trxValue);
			systemUpdateLimitProfile(trxValue, proxy);
			return result;
		}
		catch (LimitException ex) {
			throw new TrxOperationException("Exception in postProcess", ex);
		}
		catch (TrxOperationException e) {
			throw e;
		}
		catch (Exception e) {
			throw new TrxOperationException("Caught Unknown Exception in postProcess!", e);
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
	private ILimitProfileTrxValue prepareCCCFlag(ILimitProfileTrxValue trxValue) {
		ILimitProfile profile = trxValue.getLimitProfile();
		ILimitProfile stageProfile = trxValue.getStagingLimitProfile();

		profile.setCCCCompleteInd(true);
		stageProfile.setCCCCompleteInd(true);

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

	private boolean isMainBorrower(ICCCertificateTrxValue anICCCertificateTrxValue) {
		ICCCertificate actualCCC = anICCCertificateTrxValue.getCCCertificate();
		if (ICMSConstant.CHECKLIST_MAIN_BORROWER.equals(actualCCC.getCCCertCategory())) {
			return true;
		}
		return false;
	}

	private boolean isCleanType(ILimitProfile anILimitProfile) {
		ILimit[] limitList = anILimitProfile.getNonDeletedLimits();
		if ((limitList == null) || (limitList.length == 0)) {
			return true;
		}

		for (int ii = 0; ii < limitList.length; ii++) {
			if ((limitList[ii].getNonDeletedCollateralAllocations() != null)
					&& (limitList[ii].getNonDeletedCollateralAllocations().length > 0)) {
				return false;
			}
		}
		return true;
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

	private boolean isSCCGenerated(ILimitProfile anILimitProfile) {
		ITATEntry[] tatList = anILimitProfile.getTATEntries();

		if ((tatList == null) || (tatList.length == 0)) {
			return false;
		}

		for (int ii = 0; ii < tatList.length; ii++) {
			if (tatList[ii].getTATServiceCode().equals(ICMSConstant.TAT_CODE_GEN_SCC)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * To update the customer status using the customer ID
	 * @param aCustomerID of long type
	 * @param aCCCStatus of String type
	 * @throw TrxOperationException on error
	 */
	private void updateCustomerStatus(long aCustomerID, String aCCCStatus) throws TrxOperationException {
		try {
			ICMSCustomer customer = getCustomerManager().getCustomer(aCustomerID);
			customer.setCCCStatus(aCCCStatus);
			getCustomerManager().updateCustomer(customer);
		}
		catch (CustomerException ex) {
			throw new TrxOperationException(ex);
		}
		catch (RemoteException ex) {
			throw new TrxOperationException("Exception in updateCustomerStatus: " + ex.toString());
		}
	}

	private SBCustomerManager getCustomerManager() {
		SBCustomerManager remote = (SBCustomerManager) BeanController.getEJB(ICMSJNDIConstant.SB_CUSTOMER_MGR_JNDI,
				SBCustomerManagerHome.class.getName());
		return remote;

	}

	/**
	 * Create the actual document item
	 * @param anITrxValue - ITrxValue
	 * @return ICCCertificateTrxValue - the document item trx value
	 * @throws TrxOperationException on errors
	 */
	private ICCCertificateTrxValue createActualCCCertificate(ICCCertificateTrxValue anICCCertificateTrxValue)
			throws TrxOperationException {
		try {
			ICCCertificate ccCert = anICCCertificateTrxValue.getStagingCCCertificate();
			ccCert.setDateGenerated(DateUtil.getDate());
			if ((ccCert.getCreditOfficerName() != null) && (ccCert.getCreditOfficerName().trim().length() > 0)) {
				ccCert.setCreditOfficerDt(DateUtil.getDate());
			}
			if ((ccCert.getSeniorOfficerName() != null) && (ccCert.getSeniorOfficerName().trim().length() > 0)) {
				ccCert.setSeniorOfficerDt(DateUtil.getDate());
			}
			ICCCertificate actualCCCert = getSBCCCertificateBusManager().createCCCertificate(ccCert);
			anICCCertificateTrxValue.setCCCertificate(actualCCCert);
			anICCCertificateTrxValue.setReferenceID(String.valueOf(actualCCCert.getCCCertID()));
			return anICCCertificateTrxValue;
		}
		catch (CCCertificateException cex) {
			throw new TrxOperationException(cex);
		}
		catch (RemoteException ex) {
			throw new TrxOperationException("Exception in createActualCCCertificate(): " + ex.toString());
		}
	}
}
