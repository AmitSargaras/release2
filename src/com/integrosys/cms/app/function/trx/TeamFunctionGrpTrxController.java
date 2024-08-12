package com.integrosys.cms.app.function.trx;

import java.util.Map;

import com.integrosys.base.businfra.transaction.ITrxOperation;
import com.integrosys.base.businfra.transaction.ITrxParameter;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.transaction.CMSTrxController;

public class TeamFunctionGrpTrxController extends CMSTrxController {

	private static final long serialVersionUID = -1732629413025974209L;

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

	public TeamFunctionGrpTrxController() {
		super();
	}

	public ITrxOperation getOperation(ITrxValue value, ITrxParameter param) throws TrxParameterException {
		ITrxOperation op = factoryOperation(value, param);
		DefaultLogger.debug(this, "Returning Operation: " + op);
		return op;
	}

	public String getInstanceName() {
		return ICMSConstant.INSTANCE_TEAM_FUNCTION_GRP;
	}

	private ITrxOperation factoryOperation(ITrxValue value, ITrxParameter param) throws TrxParameterException {

		String action = param.getAction();
		if (null == action) {
			throw new TrxParameterException("Action is null in ITrxParameter!");
		}

		DefaultLogger.debug(this, "Action: " + action);

		/*
		 * if(param.getAction().equals(ICMSConstant.
		 * ACTION_MAKER_UPDATE_TEAM_FUNCTION_GRP)) { return (ITrxOperation)
		 * getNameTrxOperationMap().get("MakerUpdateOperation"); }
		 * 
		 * if(param.getAction().equals(ICMSConstant.
		 * ACTION_MAKER_CLOSE_DRAFT_TEAM_FUNCTION_GRP)) { return (ITrxOperation)
		 * getNameTrxOperationMap().get("MakerCloseDraftOperation"); }
		 */

		if (param.getAction().equals(ICMSConstant.ACTION_CHECKER_REJECT_TEAM_FUNCTION_GRP)) {
			return (ITrxOperation) getNameTrxOperationMap().get("CheckerRejectOperation");
		}

		if (param.getAction().equals(ICMSConstant.ACTION_CHECKER_APPROVE_TEAM_FUNCTION_GRP)) {
			return (ITrxOperation) getNameTrxOperationMap().get("CheckerApproveUpdateOperation");
		}

		if (param.getAction().equals(ICMSConstant.ACTION_MAKER_SUBMIT_TEAM_FUNCTION_GRP_CREATE)) {
			return (ITrxOperation) getNameTrxOperationMap().get("MakerSubmitOperationCreate");
		}

		if (param.getAction().equals(ICMSConstant.ACTION_MAKER_SUBMIT_TEAM_FUNCTION_GRP_UPDATE)) {
			return (ITrxOperation) getNameTrxOperationMap().get("MakerSubmitOperationUpdate");
		}

		if (param.getAction().equals(ICMSConstant.ACTION_MAKER_UPDATE_REJECTED_TEAM_FUNCTION_GRP)) {
			return (ITrxOperation) getNameTrxOperationMap().get("MakerUpdateRejectedOperation");
		}

		if (param.getAction().equals(ICMSConstant.ACTION_MAKER_SUBMIT_REJECTED_TEAM_FUNCTION_GRP)) {
			return (ITrxOperation) getNameTrxOperationMap().get("MakerSubmitRejectedOperation");
		}

		if (param.getAction().equals(ICMSConstant.ACTION_MAKER_CLOSE_REJECTED_TEAM_FUNCTION_GRP)) {
			return (ITrxOperation) getNameTrxOperationMap().get("MakerCloseRejectedOperation");
		}

		if (param.getAction().equals(ICMSConstant.ACTION_SYSTEM_CLOSE_TEAM_FUNCTION_GRP)) {
			return (ITrxOperation) getNameTrxOperationMap().get("SystemCloseOperation");
		}

		throw new TrxParameterException("No operations found");
	}
}
