/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/commodity/main/trx/profile/CheckerApproveCreateProfileOperation.java,v 1.4 2005/04/27 08:17:05 wltan Exp $
 */
package com.integrosys.cms.app.commodity.main.trx.profile;

//ofa
import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.commodity.main.CommodityException;
import com.integrosys.cms.app.commodity.main.bus.profile.IProfile;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * This operation allows a checker to approve a checklist create
 * 
 * @author $Author: wltan $
 * @version $Revision: 1.4 $
 * @since $Date: 2005/04/27 08:17:05 $ Tag: $Name: $
 */
public class CheckerApproveCreateProfileOperation extends AbstractProfileTrxOperation {
	/**
	 * Default Constructor
	 */
	public CheckerApproveCreateProfileOperation() {
		super();
	}

	/**
	 * Get the operation name of the current operation
	 * 
	 * @return String - the operation name of the current operation
	 */
	public String getOperationName() {
		return ICMSConstant.ACTION_CHECKER_CREATE_APPROVE_COMMODITY_MAIN;
	}

	/**
	 * Process the transaction 1. Create the actual data 2. Update the staging
	 * data with the actual data 3. Update the transaction record
	 * @param anITrxValue - ITrxValue
	 * @return ITrxResult - the transaction result
	 * @throws com.integrosys.base.businfra.transaction.TrxOperationException if
	 *         encounters any error during the processing of the transaction
	 */
	public ITrxResult performProcess(ITrxValue anITrxValue) throws TrxOperationException {
		IProfileTrxValue trxValue = getProfileTrxValue(anITrxValue);
		trxValue = createActualProfile(trxValue);
		trxValue = updateProfileTransaction(trxValue);
		DefaultLogger.error(this, "status : " + trxValue.getStatus());
		return super.prepareResult(trxValue);
	}

	/**
	 * Create the actual document item
	 * @param anIProfileTrxValue - ITrxValue
	 * @return IProfileTrxValue - the document item trx value
	 * @throws com.integrosys.base.businfra.transaction.TrxOperationException on
	 *         errors
	 */
	private IProfileTrxValue createActualProfile(IProfileTrxValue anIProfileTrxValue) throws TrxOperationException {
		try {
			DefaultLogger.debug(this, "$$$Debug: x1  staging.getRefID="
					+ anIProfileTrxValue.getStagingProfile()[0].getGroupID());
			IProfile[] staging = anIProfileTrxValue.getStagingProfile();

			IProfile[] actualProfile = (IProfile[]) getBusManager().createInfo(staging);

			DefaultLogger.debug(this, "$$$Debug: x2  staging.getRefID=" + staging[0].getProfileID()
					+ " , actual RefID=" + actualProfile[0].getProfileID());

			anIProfileTrxValue.setProfile(actualProfile);
			anIProfileTrxValue.setReferenceID(String.valueOf(actualProfile[0].getProfileID()));

			DefaultLogger.debug(this, "$$$Debug: x3  staging.getRefID=" + staging[0].getProfileID()
					+ " , actual RefID=" + actualProfile[0].getProfileID());

			anIProfileTrxValue.setStagingReferenceID(String.valueOf(staging[0].getProfileID()));

			DefaultLogger.debug(this, "$$$Debug: x4  staging.getRefID=" + staging[0].getProfileID()
					+ " , actual RefID=" + actualProfile[0].getProfileID());

			return anIProfileTrxValue;

		}
		catch (CommodityException cex) {
			throw new TrxOperationException(cex);
		}
	}
}