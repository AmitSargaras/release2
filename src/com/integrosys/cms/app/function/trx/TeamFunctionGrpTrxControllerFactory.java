package com.integrosys.cms.app.function.trx;

import com.integrosys.base.businfra.transaction.ITrxController;
import com.integrosys.base.businfra.transaction.ITrxControllerFactory;
import com.integrosys.base.businfra.transaction.ITrxParameter;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.cms.app.common.constant.ICMSConstant;

public class TeamFunctionGrpTrxControllerFactory implements ITrxControllerFactory {
	private ITrxController teamFunctionGrpReadController;

	private ITrxController teamFunctionGrpTrxController;

	public ITrxController getTeamFunctionGrpReadController() {
		return teamFunctionGrpReadController;
	}

	public void setTeamFunctionGrpReadController(ITrxController teamFunctionGrpReadController) {
		this.teamFunctionGrpReadController = teamFunctionGrpReadController;
	}

	public ITrxController getTeamFunctionGrpTrxController() {
		return teamFunctionGrpTrxController;
	}

	public void setTeamFunctionGrpTrxController(ITrxController teamFunctionGrpTrxController) {
		this.teamFunctionGrpTrxController = teamFunctionGrpTrxController;
	}

	public TeamFunctionGrpTrxControllerFactory() {
		super();
	}

	public ITrxController getController(ITrxValue value, ITrxParameter param) throws TrxParameterException {
		if (param.getAction().equals(ICMSConstant.ACTION_READ_TEAM_FUNCTION_GRP)) {
			return getTeamFunctionGrpReadController();
		}

		if (param.getAction().equals(ICMSConstant.ACTION_MAKER_CLOSE_REJECTED_TEAM_FUNCTION_GRP)) {
			return getTeamFunctionGrpTrxController();
		}

		if (param.getAction().equals(ICMSConstant.ACTION_CHECKER_APPROVE_TEAM_FUNCTION_GRP)) {
			return getTeamFunctionGrpTrxController();
		}

		if (param.getAction().equals(ICMSConstant.ACTION_CHECKER_REJECT_TEAM_FUNCTION_GRP)) {
			return getTeamFunctionGrpTrxController();
		}

		if (param.getAction().equals(ICMSConstant.ACTION_MAKER_SUBMIT_TEAM_FUNCTION_GRP_CREATE)) {
			return getTeamFunctionGrpTrxController();
		}

		if (param.getAction().equals(ICMSConstant.ACTION_MAKER_UPDATE_REJECTED_TEAM_FUNCTION_GRP)) {
			return getTeamFunctionGrpTrxController();
		}

		if (param.getAction().equals(ICMSConstant.ACTION_MAKER_SUBMIT_TEAM_FUNCTION_GRP_UPDATE)) {
			return getTeamFunctionGrpTrxController();
		}

		if (param.getAction().equals(ICMSConstant.ACTION_MAKER_SUBMIT_REJECTED_TEAM_FUNCTION_GRP)) {
			return getTeamFunctionGrpTrxController();
		}

		if (param.getAction().equals(ICMSConstant.ACTION_SYSTEM_CLOSE_TEAM_FUNCTION_GRP)) {
			return getTeamFunctionGrpTrxController();
		}

		throw new TrxParameterException("Action " + param.getAction() + " is not recognised!!!");
	}
}
