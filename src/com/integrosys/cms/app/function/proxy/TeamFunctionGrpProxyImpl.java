package com.integrosys.cms.app.function.proxy;

import java.util.List;

import org.apache.commons.lang.Validate;

import com.integrosys.base.businfra.transaction.ITrxController;
import com.integrosys.base.businfra.transaction.ITrxControllerFactory;
import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.function.bus.ITeamFunctionGrp;
import com.integrosys.cms.app.function.bus.ITeamFunctionGrpBusManager;
import com.integrosys.cms.app.function.bus.TeamFunctionGrpException;
import com.integrosys.cms.app.function.trx.ITeamFunctionGrpTrxValue;
import com.integrosys.cms.app.function.trx.OBTeamFunctionGrpTrxValue;
import com.integrosys.cms.app.transaction.ICMSTrxResult;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.ITrxContext;
import com.integrosys.cms.app.transaction.OBCMSTrxParameter;
import com.integrosys.component.bizstructure.app.bus.ITeam;

public class TeamFunctionGrpProxyImpl implements ITeamFunctionGrpProxy {

	private ITeamFunctionGrpBusManager teamFunctionGrpBusManager;

	private ITeamFunctionGrpBusManager stagingTeamFunctionGrpBusManager;

	private ITrxControllerFactory TrxControllerFactory;

	public ITeamFunctionGrpBusManager getTeamFunctionGrpBusManager() {
		return teamFunctionGrpBusManager;
	}

	public void setTeamFunctionGrpBusManager(ITeamFunctionGrpBusManager teamFunctionGrpBusManager) {
		this.teamFunctionGrpBusManager = teamFunctionGrpBusManager;
	}

	public ITeamFunctionGrpBusManager getStagingTeamFunctionGrpBusManager() {
		return stagingTeamFunctionGrpBusManager;
	}

	public void setStagingTeamFunctionGrpBusManager(ITeamFunctionGrpBusManager stagingTeamFunctionGrpBusManager) {
		this.stagingTeamFunctionGrpBusManager = stagingTeamFunctionGrpBusManager;
	}

	public ITrxControllerFactory getTrxControllerFactory() {
		return TrxControllerFactory;
	}

	public void setTrxControllerFactory(ITrxControllerFactory trxControllerFactory) {
		TrxControllerFactory = trxControllerFactory;
	}

	public List getTeamFunctionGrpByTeamType() throws TeamFunctionGrpException {
		long teamTypeKey = 1;
		return getTeamFunctionGrpBusManager().getTeamFunctionGrpByTeamType(teamTypeKey);
	}

	public List getTeamFunctionGrpByTeamId(long teamKey) throws TeamFunctionGrpException {
		return getTeamFunctionGrpBusManager().getTeamFunctionGrpByTeamId(teamKey);
	}

	public List getStageTeamFunctionGrpByGroupId(long groupKey) throws TeamFunctionGrpException {
		return getStagingTeamFunctionGrpBusManager().getTeamFunctionGrpByGroupId(groupKey);
	}

	public List getActualTeamFunctionGrpByGroupId(long groupKey) throws TeamFunctionGrpException {
		return getTeamFunctionGrpBusManager().getTeamFunctionGrpByGroupId(groupKey);
	}

	public ITeamFunctionGrpTrxValue checkerApproveTeamFunctionGrp(ITrxContext anITrxContext,
			ITeamFunctionGrpTrxValue aTrxValue) throws TeamFunctionGrpException {
		Validate.notNull(aTrxValue, "'aTrxValue' must not be null");

		aTrxValue = formulateTrxValue(anITrxContext, aTrxValue);

		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_CHECKER_APPROVE_TEAM_FUNCTION_GRP);
		return operate(aTrxValue, param, null);
	}

	public ITeamFunctionGrpTrxValue checkerRejectTeamFunctionGrp(ITrxContext anITrxContext,
			ITeamFunctionGrpTrxValue aTrxValue) throws TeamFunctionGrpException {
		Validate.notNull(aTrxValue, "'aTrxValue' must not be null");

		aTrxValue = formulateTrxValue(anITrxContext, aTrxValue);

		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_CHECKER_REJECT_TEAM_FUNCTION_GRP);
		return operate(aTrxValue, param, null);
	}

	public List getActiveTeamFunctionGrp() throws TeamFunctionGrpException {
		return getTeamFunctionGrpBusManager().getActiveTeamFunctionGrp(1);
	}

	public ITeamFunctionGrpTrxValue getTeamFunctionGrpByTeamId(ITrxContext anITrxContext, long teamId)
			throws TeamFunctionGrpException {

		ITeamFunctionGrpTrxValue trxValue = new OBTeamFunctionGrpTrxValue();
		ITeamFunctionGrp teamFunctionGrp = null;
		List listTeamFunctionGrpStaging = getStagingTeamFunctionGrpBusManager().getTeamFunctionGrpByTeamId(teamId);
		List listTeamFunctionGrpActual = getTeamFunctionGrpBusManager().getTeamFunctionGrpByTeamId(teamId);
		if (listTeamFunctionGrpActual != null) {
			trxValue.setTeamFunctionGrps(listTeamFunctionGrpActual);
		}
		if (listTeamFunctionGrpStaging == null) {
			return null;
		}
		if (listTeamFunctionGrpStaging != null) {
			teamFunctionGrp = (ITeamFunctionGrp) listTeamFunctionGrpStaging.get(0);
			trxValue.setStagingReferenceID(String.valueOf(teamFunctionGrp.getGroupId()));
		}
		// teamFunctionGrp.

		// aTeamFunctionGrpTrxValue = formulateTrxValue(anITrxContext,
		// aTeamFunctionGrpTrxValue, listTeamFunctionGrp);
		trxValue = formulateTrxValue(anITrxContext, trxValue);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_READ_TEAM_FUNCTION_GRP);
		return operate(trxValue, param, null);
	}

	public ITeamFunctionGrpTrxValue getTeamFunctionGrpByTrxId(ITrxContext anITrxContext, String trxId)
			throws TeamFunctionGrpException {

		ITeamFunctionGrpTrxValue trxValue = new OBTeamFunctionGrpTrxValue();
		trxValue.setTransactionID(trxId);
		trxValue = formulateTrxValue(anITrxContext, trxValue);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_READ_TEAM_FUNCTION_GRP);
		return operate(trxValue, param, null);
	}

	public ITeamFunctionGrpTrxValue makerCloseDraftTeamFunctionGrp(ITrxContext anITrxContext,
			ITeamFunctionGrpTrxValue aTrxValue) throws TeamFunctionGrpException {
		Validate.notNull(aTrxValue, "'aTrxValue' must not be null");

		aTrxValue = formulateTrxValue(anITrxContext, aTrxValue);

		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_CLOSE_DRAFT_TEAM_FUNCTION_GRP);
		return operate(aTrxValue, param, null);
	}

	public ITeamFunctionGrpTrxValue makerCloseRejectedTeamFunctionGrp(ITrxContext anITrxContext,
			ITeamFunctionGrpTrxValue aTrxValue) throws TeamFunctionGrpException {
		Validate.notNull(aTrxValue, "'aTrxValue' must not be null");

		aTrxValue = formulateTrxValue(anITrxContext, aTrxValue);

		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_CLOSE_REJECTED_TEAM_FUNCTION_GRP);
		return operate(aTrxValue, param, null);
	}

	public ITeamFunctionGrpTrxValue makerSubmitTeamFunctionGrpCreateTrans(ITrxContext anITrxContext,
			ITeamFunctionGrpTrxValue aTeamFunctionGrpTrxValue, List listTeamFunctionGrp, ITeam team)
			throws TeamFunctionGrpException {
		Validate.notNull(listTeamFunctionGrp, "'listTeamFunctionGrp' must not be null");
		Validate.notNull(aTeamFunctionGrpTrxValue, "'aTeamFunctionGrpTrxValue' must not be null");
		aTeamFunctionGrpTrxValue = formulateTrxValue(anITrxContext, aTeamFunctionGrpTrxValue, listTeamFunctionGrp);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_SUBMIT_TEAM_FUNCTION_GRP_CREATE);

		return operate(aTeamFunctionGrpTrxValue, param, team);
	}

	public ITeamFunctionGrpTrxValue makerSubmitTeamFunctionGrpUpdateTrans(ITrxContext anITrxContext,
			ITeamFunctionGrpTrxValue aTeamFunctionGrpTrxValue, List listTeamFunctionGrp, ITeam team)
			throws TeamFunctionGrpException {
		Validate.notNull(listTeamFunctionGrp, "'listTeamFunctionGrp' must not be null");
		Validate.notNull(aTeamFunctionGrpTrxValue, "'aTeamFunctionGrpTrxValue' must not be null");
		aTeamFunctionGrpTrxValue = formulateTrxValue(anITrxContext, aTeamFunctionGrpTrxValue, listTeamFunctionGrp);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_SUBMIT_TEAM_FUNCTION_GRP_UPDATE);

		return operate(aTeamFunctionGrpTrxValue, param, team);
	}

	public ITeamFunctionGrpTrxValue makerSubmitRejectedTeamFunctionGrp(ITrxContext anITrxContext,
			ITeamFunctionGrpTrxValue aTrxValue, ITeam team) throws TeamFunctionGrpException {
		Validate.notNull(aTrxValue, "'aTrxValue' must not be null");

		aTrxValue = formulateTrxValue(anITrxContext, aTrxValue);

		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_SUBMIT_REJECTED_TEAM_FUNCTION_GRP);
		return operate(aTrxValue, param, team);
	}

	public ITeamFunctionGrpTrxValue makerUpdateTeamFunctionGrp(ITrxContext anITrxContext,
			ITeamFunctionGrpTrxValue aFeedGroupTrxValue, ITeamFunctionGrp aFeedGroup, ITeam team)
			throws TeamFunctionGrpException {
		Validate.notNull(aFeedGroup, "'aFeedGroup' must not be null");
		Validate.notNull(aFeedGroupTrxValue, "'aFeedGroupTrxValue' must not be null");
		// aFeedGroupTrxValue = formulateTrxValue(anITrxContext,
		// aFeedGroupTrxValue, aFeedGroup);

		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_UPDATE_TEAM_FUNCTION_GRP);
		return operate(aFeedGroupTrxValue, param, team);
	}

	public ITeamFunctionGrpTrxValue makerUpdateRejectedTeamFunctionGrp(ITrxContext anITrxContext,
			ITeamFunctionGrpTrxValue aTrxValue, ITeam team) throws TeamFunctionGrpException {
		Validate.notNull(aTrxValue, "'aTrxValue' must not be null");

		aTrxValue = formulateTrxValue(anITrxContext, aTrxValue);

		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_UPDATE_REJECTED_TEAM_FUNCTION_GRP);
		return operate(aTrxValue, param, team);
	}

	public ITeamFunctionGrpTrxValue systemCloseTeamFunctionGroupTransaction(ITrxContext trxContext,
			ITeamFunctionGrpTrxValue teamFunctionGroupTrxValue) throws TeamFunctionGrpException {
		Validate.notNull(teamFunctionGroupTrxValue, "'teamFunctionGroupTrxValue' must not be null");

		teamFunctionGroupTrxValue = formulateTrxValue(trxContext, teamFunctionGroupTrxValue);

		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_SYSTEM_CLOSE_TEAM_FUNCTION_GRP);
		return operate(teamFunctionGroupTrxValue, param, null);

	}

	/**
	 * Formulate the checklist trx object
	 * @param anITrxContext - ITrxContext
	 * @param aTrxValue -
	 * @return ICheckListTrxValue - the checklist trx interface formulated
	 */
	protected ITeamFunctionGrpTrxValue formulateTrxValue(ITrxContext anITrxContext, ITeamFunctionGrpTrxValue aTrxValue) {
		aTrxValue.setTrxContext(anITrxContext);
		aTrxValue.setTransactionType(ICMSConstant.INSTANCE_TEAM_FUNCTION_GRP);

		return aTrxValue;
	}

	protected ITeamFunctionGrpTrxValue operate(ITeamFunctionGrpTrxValue aTrxValue,
			OBCMSTrxParameter anOBCMSTrxParameter, ITeam team) throws TeamFunctionGrpException {

		ICMSTrxResult result = operateForResult(aTrxValue, anOBCMSTrxParameter, team);
		return (ITeamFunctionGrpTrxValue) result.getTrxValue();
	}

	/**
	 * @param anICMSTrxValue
	 * @param anOBCMSTrxParameter - OBCMSTrxParameter
	 * @return ICMSTrxResult - the trx result interface
	 */
	protected ICMSTrxResult operateForResult(ICMSTrxValue anICMSTrxValue, OBCMSTrxParameter anOBCMSTrxParameter,
			ITeam team) throws TeamFunctionGrpException {
		try {
			ITrxController controller = getTrxControllerFactory().getController(anICMSTrxValue, anOBCMSTrxParameter);
			if (controller == null) {
				throw new TeamFunctionGrpException("ITrxController is null!!!");
			}

			if (team != null) {
				anICMSTrxValue.setCustomerName(team.getAbbreviation());
			}
			ITrxResult result = controller.operate(anICMSTrxValue, anOBCMSTrxParameter);
			return (ICMSTrxResult) result;
		}
		catch (TransactionException e) {
			throw new TeamFunctionGrpException("failed to operate team function workflow, trx value [" + anICMSTrxValue
					+ "]", e);
		}
	}

	protected ITeamFunctionGrpTrxValue formulateTrxValue(ITrxContext anITrxContext, ICMSTrxValue anICMSTrxValue,
			List listTeamFunctionGrp) {

		ITeamFunctionGrpTrxValue teamFunctionGrpTrxValue = null;
		if (anICMSTrxValue != null) {
			teamFunctionGrpTrxValue = new OBTeamFunctionGrpTrxValue(anICMSTrxValue);
		}
		else {
			teamFunctionGrpTrxValue = new OBTeamFunctionGrpTrxValue();
		}
		teamFunctionGrpTrxValue = formulateTrxValue(anITrxContext, (ITeamFunctionGrpTrxValue) teamFunctionGrpTrxValue);

		teamFunctionGrpTrxValue.setStagingTeamFunctionGrps(listTeamFunctionGrp);

		return teamFunctionGrpTrxValue;
	}

}
