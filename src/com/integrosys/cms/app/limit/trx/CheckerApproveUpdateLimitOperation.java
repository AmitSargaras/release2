/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/limit/trx/CheckerApproveUpdateLimitOperation.java,v 1.10 2006/10/27 11:04:46 czhou Exp $
 */
package com.integrosys.cms.app.limit.trx;

//ofa
import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.limit.bus.ICoBorrowerLimit;
import com.integrosys.cms.app.limit.bus.ILimit;
import com.integrosys.cms.app.limit.bus.ILimitProfile;
import com.integrosys.cms.app.limit.proxy.ILimitProxy;

/**
 * This operation allows checker to approve an update on limit
 * 
 * @author $Author: czhou $
 * @version $Revision: 1.10 $
 * @since $Date: 2006/10/27 11:04:46 $ Tag: $Name: $
 */
public class CheckerApproveUpdateLimitOperation extends AbstractLimitTrxOperation {
	/**
	 * Defaulc Constructor
	 */
	public CheckerApproveUpdateLimitOperation() {
		super();
	}

	/**
	 * Get the operation name of the current operation
	 * 
	 * @return String - the operation name of the current operation
	 */
	public String getOperationName() {
		return ICMSConstant.ACTION_CHECKER_APPROVE_UPDATE_LIMIT;
	}

	/***
	 * To set Limit Activated Ind
	 * 
	 */
	public ITrxValue preProcess(ITrxValue value) throws TrxOperationException {
		ILimitTrxValue aLimitTrxValue = super.getLimitTrxValue(value);
		ILimitTrxValue[] trxValues = aLimitTrxValue.getLimitTrxValues();
		if (trxValues != null) {
			for (int i = 0; i < trxValues.length; i++) {
				ILimit aStagingLimit = trxValues[i].getStagingLimit();
				ILimit aActualLimit = trxValues[i].getLimit();

				if (aStagingLimit.getActivatedLimitAmount() != null) {
					if (aStagingLimit.getActivatedLimitAmount().compareTo(aActualLimit.getActivatedLimitAmount()) != 0) {
						if (aStagingLimit.getActivatedLimitAmount().getAmount() > 0) {
							aStagingLimit.setLimitActivatedInd(true);
						}
						else {
							aStagingLimit.setLimitActivatedInd(false);
						}
					}
				}
				else {
					aStagingLimit.setLimitActivatedInd(false);
				}

			}
		}

		return super.preProcess(aLimitTrxValue);
	}

	/**
	 * The following tasks are performed:
	 * 
	 * 1. Update Acutal Record from Staging 2. Update Transaction Record
	 * 
	 * @param value is of type ITrxValue
	 * @return ITrxResult
	 * @throws TrxOperationException on error
	 */
	public ITrxResult performProcess(ITrxValue value) throws TrxOperationException {
		try {
			ILimitTrxValue trxValue = super.getLimitTrxValue(value);
			trxValue = super.updateActualLimitFromStaging(trxValue);
			trxValue = super.updateTransaction(trxValue);
			return super.prepareResult(trxValue);
		}
		catch (TrxOperationException e) {
			throw e;
		}
		catch (Exception e) {
			throw new TrxOperationException("Caught Exception!", e);
		}
	}

	/**
	 * This method may contain additional work to be done after a transaction
	 * operation has been completed.
	 * 
	 * This method will identify if it's time to make BCA Completed = true
	 * 
	 * @param result is the ITrxResult object after transaction operation
	 * @return ITrxResult object
	 * @throws TrxOperationException on errors
	 */
	public ITrxResult postProcess(ITrxResult result) throws TrxOperationException {
		/*
		 * try { ITrxValue value = result.getTrxValue(); ILimitTrxValue trxValue
		 * = super.getLimitTrxValue(value); ILimit limit = trxValue.getLimit();
		 * long limitProfileID = limit.getLimitProfileID();
		 * 
		 * ILimitProxy proxy = LimitProxyFactory.getProxy();
		 * ILimitProfileTrxValue profileTrx =
		 * proxy.getTrxLimitProfile(limitProfileID); ILimitProfile profile =
		 * profileTrx.getLimitProfile();
		 * 
		 * boolean limitCompleted = areAllLimitsActivated(profile); if(false ==
		 * limitCompleted) { //not completed yet DefaultLogger.debug(this,
		 * "Not all limits activated."); return result; } else {
		 * DefaultLogger.debug(this, "All limits activated."); }
		 * 
		 * //-- R1.5 CR146 - Removed CCC Generation, therefore should not check
		 * for CCC anymore // ICCCertificateProxyManager cccProxy =
		 * CCCertificateProxyManagerFactory.getCCCertificateProxyManager(); //
		 * boolean cccGen = cccProxy.isAllCCCGenerated(profile); // if(false ==
		 * cccGen) { //not completed yet // DefaultLogger.debug(this,
		 * "Not all CCC generated."); // return result; // } // else { //
		 * DefaultLogger.debug(this, "All CCC generated."); // profileTrx =
		 * prepareCCCFlag(profileTrx); // } ISCCertificateProxyManager sccProxy
		 * = SCCertificateProxyManagerFactory.getSCCertificateProxyManager();
		 * boolean sccGen = sccProxy.isSCCFullyGenerated(profile); if(false ==
		 * sccGen) { DefaultLogger.debug(this, "Not all SCC generated."); return
		 * result; //not completed yet } else { DefaultLogger.debug(this,
		 * "All SCC generated."); profileTrx = prepareSCCFlag(profileTrx); }
		 * 
		 * //R1.5 UAT CMS-3551 -- starts int bflStatus =
		 * proxy.getBFLStatus(profile); if(!(bflStatus ==
		 * ICMSConstant.BFL_NOT_REQUIRED || bflStatus ==
		 * ICMSConstant.BFL_COMPLETED)) { DefaultLogger.debug(this,
		 * "BFL NOT COMPLETED."); return result; }
		 * 
		 * //BCA is complete completeBCA(profileTrx, proxy);
		 */
		return result;
		/*
		 * } catch(TrxOperationException e) { throw e; } catch(Exception e) {
		 * throw new TrxOperationException("Caught Exception!", e); }
		 */
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
	 * Method to make the limit profile complete
	 */
	private void completeBCA(ILimitProfileTrxValue trxValue, ILimitProxy proxy) throws TrxOperationException {
		try {
			ILimitProfile profile = trxValue.getLimitProfile();
			ILimitProfile stageProfile = trxValue.getStagingLimitProfile();

			profile.setBCACompleteInd(true);
			stageProfile.setBCACompleteInd(true);

			trxValue.setLimitProfile(profile);
			trxValue.setStagingLimitProfile(stageProfile);

			proxy.systemUpdateLimitProfile(trxValue);
		}
		catch (Exception e) {
			DefaultLogger.error(this, "Caught Exception while completing BCA!", e);
			throw new TrxOperationException("Caught Exception while completing BCA!", e);
		}
	}

	/**
	 * Identify if all limits in a limit profile (including co-borrower limits)
	 * have been activated. The method returns true if ALL limits are activated.
	 * 
	 * @deprecated Use <code>getActivatedLimitsStatus</code> instead
	 * @param value is the ILimitProfile to be evaluated
	 * @return boolean
	 */
	private boolean areAllLimitsActivated(ILimitProfile value) {
		// the method assumes true by default.
		boolean activated = true;

		ILimit[] limitList = value.getLimits();
		if ((null != limitList) && (limitList.length != 0)) {
			for (int i = 0; i < limitList.length; i++) {
				ILimit limit = limitList[i];
				if (ICMSConstant.STATE_DELETED.equals(limit.getLimitStatus())) {
					continue; // don't include this
				}

				if (limit.getLimitActivatedInd() == false) {
					activated = false;
					return activated;
				}
				/*
				 * Amount amt = limit.getActivatedLimitAmount(); if(null == amt
				 * || amt.getAmount() == 0) { activated = false; return
				 * activated; }
				 */
				else {
					ICoBorrowerLimit[] coLimitList = limit.getCoBorrowerLimits();
					if ((null != coLimitList) && (coLimitList.length != 0)) {
						for (int j = 0; j < coLimitList.length; j++) {
							ICoBorrowerLimit coLimit = coLimitList[j];
							if (ICMSConstant.STATE_DELETED.equals(coLimit.getStatus())) {
								continue; // don't include this
							}
							/*
							 * Amount coAmt = coLimit.getActivatedLimitAmount();
							 * if(null == coAmt || coAmt.getAmount() == 0) {
							 * activated = false; return activated; }
							 */
							if (coLimit.getLimitActivatedInd() == false) {
								activated = false;
								return activated;
							}
						}
					}
				}
			}
		}
		return activated;
	}
}