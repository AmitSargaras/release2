/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/cccertificate/trx/SystemCloseCCCOperation.java,v 1.6 2005/12/13 08:38:20 wltan Exp $
 */
package com.integrosys.cms.app.cccertificate.trx;

import java.rmi.RemoteException;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.cms.app.cccertificate.bus.ICCCertificate;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;
import com.integrosys.cms.app.customer.bus.CustomerException;
import com.integrosys.cms.app.customer.bus.ICMSCustomer;
import com.integrosys.cms.app.customer.bus.SBCustomerManager;
import com.integrosys.cms.app.customer.bus.SBCustomerManagerHome;
import com.integrosys.cms.app.limit.bus.ILimitProfile;
import com.integrosys.cms.app.limit.bus.LimitException;
import com.integrosys.cms.app.limit.proxy.ILimitProxy;
import com.integrosys.cms.app.limit.proxy.LimitProxyFactory;
import com.integrosys.cms.app.limit.trx.ILimitProfileTrxValue;

/**
 * This operation allows system to close the CCC trx
 * 
 * @author $Author: wltan $
 * @version $Revision: 1.6 $
 * @since $Date: 2005/12/13 08:38:20 $ Tag: $Name: $
 */
public class SystemCloseCCCOperation extends AbstractCCCTrxOperation {
	/**
	 * Defaulc Constructor
	 */
	public SystemCloseCCCOperation() {
		super();
	}

	/**
	 * Get the operation name of the current operation
	 * 
	 * @return String - the operation name of the current operation
	 */
	public String getOperationName() {
		return ICMSConstant.ACTION_SYSTEM_CLOSE_CCC;
	}

	/**
	 * Process the transaction 1. Update the transaction record
	 * @param anITrxValue - ITrxValue
	 * @return ITrxResult - the transaction result
	 * @throws TrxOperationException if encounters any error during the
	 *         processing of the transaction
	 */
	public ITrxResult performProcess(ITrxValue anITrxValue) throws TrxOperationException {
		ICCCertificateTrxValue trxValue = getCCCertificateTrxValue(anITrxValue);
		trxValue = updateCCCertificateTransaction(trxValue);
		return super.prepareResult(trxValue);
	}

	/**
	 * Post Processing after the trx operation 1. To reset the
	 * limitprofile/subprofile ccc_issued and bca completion flag
	 * @param result - ITrxResult
	 * @return ITrxResult - the transaction result
	 * @throws TrxOperationException if encounters any error during the
	 *         processing of the transaction
	 */
	public ITrxResult postProcess(ITrxResult result) throws TrxOperationException {
		ICCCertificateTrxValue ccTrxValue = (ICCCertificateTrxValue) result.getTrxValue();
		ICCCertificate stage = ccTrxValue.getStagingCCCertificate();
		if (ICMSConstant.CHECKLIST_NON_BORROWER.equals(stage.getCCCertCategory())) {
			resetSubProfile(stage.getSubProfileID());
		}
		else {
			resetLimitProfile(stage.getLimitProfileID());
		}
		// systemUnlockCheckList(actual);
		return result;
	}

	private void resetLimitProfile(long aLimitProfileID) throws TrxOperationException {
		try {
			ILimitProxy proxy = LimitProxyFactory.getProxy();
			ILimitProfileTrxValue trxValue = proxy.getTrxLimitProfile(aLimitProfileID);
			ILimitProfile profile = trxValue.getLimitProfile();
			ILimitProfile stageProfile = trxValue.getStagingLimitProfile();
			profile.setCCCCompleteInd(false);
			profile.setBCACompleteInd(false);
			stageProfile.setCCCCompleteInd(false);
			stageProfile.setBCACompleteInd(false);
			trxValue.setLimitProfile(profile);
			trxValue.setStagingLimitProfile(stageProfile);
			proxy.systemUpdateLimitProfile(trxValue);
		}
		catch (LimitException ex) {
			throw new TrxOperationException("Exception in postProcess", ex);
		}
	}

	private void resetSubProfile(long aCustomerID) throws TrxOperationException {
		try {
			ICMSCustomer customer = getCustomerManager().getCustomer(aCustomerID);
			customer.setCCCStatus(ICMSConstant.CCC_STARTED);
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

}