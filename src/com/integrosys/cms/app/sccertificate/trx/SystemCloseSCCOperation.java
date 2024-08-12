/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/sccertificate/trx/SystemCloseSCCOperation.java,v 1.7 2005/12/13 08:42:02 wltan Exp $
 */
package com.integrosys.cms.app.sccertificate.trx;

//ofa
import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.limit.bus.ILimitProfile;
import com.integrosys.cms.app.limit.bus.LimitException;
import com.integrosys.cms.app.limit.proxy.ILimitProxy;
import com.integrosys.cms.app.limit.proxy.LimitProxyFactory;
import com.integrosys.cms.app.limit.trx.ILimitProfileTrxValue;
import com.integrosys.cms.app.sccertificate.bus.ISCCertificate;

/**
 * This operation allows system to close SCC trx
 * 
 * @author $Author: wltan $
 * @version $Revision: 1.7 $
 * @since $Date: 2005/12/13 08:42:02 $ Tag: $Name: $
 */
public class SystemCloseSCCOperation extends AbstractSCCTrxOperation {
	/**
	 * Default Constructor
	 */
	public SystemCloseSCCOperation() {
		super();
	}

	/**
	 * Get the operation name of the current operation
	 * 
	 * @return String - the operation name of the current operation
	 */
	public String getOperationName() {
		return ICMSConstant.ACTION_SYSTEM_CLOSE_SCC;
	}

	/**
	 * Process the transaction 1. Update the transaction record
	 * @param anITrxValue of ITrxValue type
	 * @return ITrxResult - the transaction result
	 * @throws TrxOperationException if encounters any error during the
	 *         processing of the transaction
	 */
	public ITrxResult performProcess(ITrxValue anITrxValue) throws TrxOperationException {
		ISCCertificateTrxValue trxValue = getSCCertificateTrxValue(anITrxValue);
		trxValue = updateSCCertificateTransaction(trxValue);
		return super.prepareResult(trxValue);
	}

	/**
	 * Post Processing after the trx operation 1. To reset the
	 * limitprofile/subprofile scc_issued and bca completion flag
	 * @param result - ITrxResult
	 * @return ITrxResult - the transaction result
	 * @throws TrxOperationException if encounters any error during the
	 *         processing of the transaction
	 */
	public ITrxResult postProcess(ITrxResult result) throws TrxOperationException {
		ISCCertificateTrxValue ccTrxValue = (ISCCertificateTrxValue) result.getTrxValue();
		ISCCertificate stage = ccTrxValue.getStagingSCCertificate();
		resetLimitProfile(stage.getLimitProfileID());
		// systemUnlockCheckList(actual);
		return result;
	}

	private void resetLimitProfile(long aLimitProfileID) throws TrxOperationException {
		try {
			ILimitProxy proxy = LimitProxyFactory.getProxy();
			ILimitProfileTrxValue trxValue = proxy.getTrxLimitProfile(aLimitProfileID);
			ILimitProfile profile = trxValue.getLimitProfile();
			ILimitProfile stageProfile = trxValue.getStagingLimitProfile();
			profile.setSCCCompleteInd(false);
			profile.setBCACompleteInd(false);
			stageProfile.setSCCCompleteInd(false);
			stageProfile.setBCACompleteInd(false);
			trxValue.setLimitProfile(profile);
			trxValue.setStagingLimitProfile(stageProfile);
			proxy.systemUpdateLimitProfile(trxValue);
		}
		catch (LimitException ex) {
			throw new TrxOperationException("Exception in postProcess", ex);
		}
	}

}