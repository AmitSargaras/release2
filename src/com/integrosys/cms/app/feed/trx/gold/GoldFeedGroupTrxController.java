package com.integrosys.cms.app.feed.trx.gold;

import java.util.Map;

import com.integrosys.base.businfra.transaction.ITrxOperation;
import com.integrosys.base.businfra.transaction.ITrxParameter;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.transaction.CMSTrxController;

public class GoldFeedGroupTrxController extends CMSTrxController {

	private Map nameTrxOperationMap;

	/**
	 * @return the nameTrxOperationMap
	 */
	public Map getNameTrxOperationMap() {
		return nameTrxOperationMap;
	}

	/**
	 * @param nameTrxOperationMap the nameTrxOperationMap to set
	 */
	public void setNameTrxOperationMap(Map nameTrxOperationMap) {
		this.nameTrxOperationMap = nameTrxOperationMap;
	}

	public GoldFeedGroupTrxController() {
		super();
	}

	public ITrxOperation getOperation(ITrxValue value, ITrxParameter param) throws TrxParameterException {
		ITrxOperation op = factoryOperation(value, param);
		DefaultLogger.debug(this, "Returning Operation: " + op);
		return op;
	}

	public String getInstanceName() {
		return ICMSConstant.INSTANCE_GOLD_FEED_GROUP;
	}

	private ITrxOperation factoryOperation(ITrxValue value, ITrxParameter param) throws TrxParameterException {

		String action = param.getAction();
		if (null == action) {
			throw new TrxParameterException("Action is null in ITrxParameter!");
		}

		DefaultLogger.debug(this, "Action: " + action);

		if (param.getAction().equals(ICMSConstant.ACTION_MAKER_UPDATE_GOLD_FEED_GROUP)) {
			return (ITrxOperation) getNameTrxOperationMap().get("MakerUpdateOperation");
		}

		if (param.getAction().equals(ICMSConstant.ACTION_MAKER_CLOSE_REJECTED_GOLD_FEED_GROUP)) {
			return (ITrxOperation) getNameTrxOperationMap().get("MakerCloseRejectedOperation");
		}

		if (param.getAction().equals(ICMSConstant.ACTION_CHECKER_APPROVE_GOLD_FEED_GROUP)) {
			return (ITrxOperation) getNameTrxOperationMap().get("CheckerApproveUpdateOperation");
		}

		if (param.getAction().equals(ICMSConstant.ACTION_CHECKER_REJECT_GOLD_FEED_GROUP)) {
			return (ITrxOperation) getNameTrxOperationMap().get("CheckerRejectOperation");
		}

		if (param.getAction().equals(ICMSConstant.ACTION_MAKER_SUBMIT_GOLD_FEED_GROUP)) {
			return (ITrxOperation) getNameTrxOperationMap().get("MakerSubmitOperation");
		}

		if (param.getAction().equals(ICMSConstant.ACTION_MAKER_UPDATE_REJECTED_GOLD_FEED_GROUP)) {
			return (ITrxOperation) getNameTrxOperationMap().get("MakerUpdateRejectedOperation");
		}

		if (param.getAction().equals(ICMSConstant.ACTION_MAKER_SUBMIT_REJECTED_GOLD_FEED_GROUP)) {
			return (ITrxOperation) getNameTrxOperationMap().get("MakerSubmitRejectedOperation");
		}

		if (param.getAction().equals(ICMSConstant.ACTION_MAKER_CLOSE_DRAFT_GOLD_FEED_GROUP)) {
			return (ITrxOperation) getNameTrxOperationMap().get("MakerCloseDraftOperation");
		}

		throw new TrxParameterException("No operations found");
	}
}
