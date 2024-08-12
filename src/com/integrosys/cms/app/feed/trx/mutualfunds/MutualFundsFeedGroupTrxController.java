/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/feed/trx/bond/BondFeedGroupTrxController.java,v 1.4 2005/01/12 02:49:03 hshii Exp $
 */
package com.integrosys.cms.app.feed.trx.mutualfunds;

import java.util.Map;

import com.integrosys.base.businfra.transaction.ITrxOperation;
import com.integrosys.base.businfra.transaction.ITrxParameter;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.transaction.CMSTrxController;

/**
 * This controller is used to control document item related operations.
 * 
 * @author $Author: Dattatray Thorat $
 * @version $Revision: 1.4 $
 * @since $Date: 2011/05/10 10:45:20 $ Tag: $Name%
 */
public class MutualFundsFeedGroupTrxController extends CMSTrxController {

	/**
	 * Default Constructor
	 */

	private Map nameTrxOperationMap;

	public Map getNameTrxOperationMap() {
		return nameTrxOperationMap;
	}

	public void setNameTrxOperationMap(Map nameTrxOperationMap) {
		this.nameTrxOperationMap = nameTrxOperationMap;
	}

	public MutualFundsFeedGroupTrxController() {
		super();
	}

	/**
	 * Return the instance name associated to this ITrxController. The instance
	 * name refers to the instance of the state transition table. Not
	 * implemented.
	 * 
	 * @return String
	 */
	public String getInstanceName() {
		return ICMSConstant.INSTANCE_MUTUAL_FUNDS_FEED_GROUP;
	}

	/**
	 * Returns an ITrxOperation object
	 * @param value - ITrxValue
	 * @param param - ITrxParameter
	 * @return ITrxOperation - the trx operation
	 * @throws TrxParameterException on error
	 */
	public ITrxOperation getOperation(ITrxValue value, ITrxParameter param) throws TrxParameterException {
		ITrxOperation op = factoryOperation(value, param);
		DefaultLogger.debug(this, "Returning Operation: " + op);
		return op;
	}

	/**
	 * Helper method to factory the operations
	 * @param value - ITrxValue
	 * @param param - ITrxParameter
	 * @return ITrxOperation - the trx operation
	 * @throws TrxParameterException on error
	 */
	private ITrxOperation factoryOperation(ITrxValue value, ITrxParameter param) throws TrxParameterException {

		String action = param.getAction();
		if (null == action) {
			throw new TrxParameterException("Action is null in ITrxParameter!");
		}

		DefaultLogger.debug(this, "Action: " + action);

		if (param.getAction().equals(ICMSConstant.ACTION_MAKER_UPDATE_MUTUAL_FUNDS_FEED_GROUP)) {
			return (ITrxOperation) getNameTrxOperationMap().get("MakerUpdateOperation");
		}

		if (param.getAction().equals(ICMSConstant.ACTION_MAKER_CLOSE_REJECTED_MUTUAL_FUNDS_FEED_GROUP)) {
			return (ITrxOperation) getNameTrxOperationMap().get("MakerCloseRejectedOperation");
		}

		if (param.getAction().equals(ICMSConstant.ACTION_CHECKER_APPROVE_MUTUAL_FUNDS_FEED_GROUP)) {
			return (ITrxOperation) getNameTrxOperationMap().get("CheckerApproveUpdateOperation");
		}

		if (param.getAction().equals(ICMSConstant.ACTION_CHECKER_REJECT_MUTUAL_FUNDS_FEED_GROUP)) {
			return (ITrxOperation) getNameTrxOperationMap().get("CheckerRejectOperation");
		}

		if (param.getAction().equals(ICMSConstant.ACTION_MAKER_SUBMIT_MUTUAL_FUNDS_FEED_GROUP)) {
			return (ITrxOperation) getNameTrxOperationMap().get("MakerSubmitOperation");
		}

		if (param.getAction().equals(ICMSConstant.ACTION_MAKER_UPDATE_REJECTED_MUTUAL_FUNDS_FEED_GROUP)) {
			return (ITrxOperation) getNameTrxOperationMap().get("MakerUpdateRejectedOperation");
		}

		if (param.getAction().equals(ICMSConstant.ACTION_MAKER_SUBMIT_REJECTED_MUTUAL_FUNDS_FEED_GROUP)) {
			return (ITrxOperation) getNameTrxOperationMap().get("MakerSubmitRejectedOperation");
		}

		if (param.getAction().equals(ICMSConstant.ACTION_MAKER_CLOSE_DRAFT_MUTUAL_FUNDS_FEED_GROUP)) {
			return (ITrxOperation) getNameTrxOperationMap().get("MakerCloseDraftOperation");
		}

		throw new TrxParameterException("No operations found");
	}
}