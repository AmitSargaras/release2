package com.integrosys.cms.app.function.proxy;

import java.util.List;

import com.integrosys.cms.app.function.bus.ITeamFunctionGrp;
import com.integrosys.cms.app.function.bus.TeamFunctionGrpException;
import com.integrosys.cms.app.function.trx.ITeamFunctionGrpTrxValue;
import com.integrosys.cms.app.transaction.ITrxContext;
import com.integrosys.component.bizstructure.app.bus.ITeam;

public interface ITeamFunctionGrpProxy {

	public List getTeamFunctionGrpByTeamType() throws TeamFunctionGrpException;

	public List getTeamFunctionGrpByTeamId(long teamKey) throws TeamFunctionGrpException;

	public List getStageTeamFunctionGrpByGroupId(long groupKey) throws TeamFunctionGrpException;

	public List getActualTeamFunctionGrpByGroupId(long groupKey) throws TeamFunctionGrpException;

	public ITeamFunctionGrpTrxValue checkerApproveTeamFunctionGrp(ITrxContext anITrxContext,
			ITeamFunctionGrpTrxValue aTrxValue) throws TeamFunctionGrpException;

	public ITeamFunctionGrpTrxValue checkerRejectTeamFunctionGrp(ITrxContext anITrxContext,
			ITeamFunctionGrpTrxValue aTrxValue) throws TeamFunctionGrpException;

	public List getActiveTeamFunctionGrp() throws TeamFunctionGrpException;

	public ITeamFunctionGrpTrxValue getTeamFunctionGrpByTeamId(ITrxContext anITrxContext, long teamId)
			throws TeamFunctionGrpException;

	public ITeamFunctionGrpTrxValue getTeamFunctionGrpByTrxId(ITrxContext anITrxContext, String trxId)
			throws TeamFunctionGrpException;

	// public ITeamFunctionGrpTrxValue getTeamFunctionGrpByTrxID(long trxID)
	// throws TeamFunctionGrpException;

	public ITeamFunctionGrpTrxValue makerCloseDraftTeamFunctionGrp(ITrxContext anITrxContext,
			ITeamFunctionGrpTrxValue aTrxValue) throws TeamFunctionGrpException;

	public ITeamFunctionGrpTrxValue makerCloseRejectedTeamFunctionGrp(ITrxContext anITrxContext,
			ITeamFunctionGrpTrxValue aTrxValue) throws TeamFunctionGrpException;

	public ITeamFunctionGrpTrxValue makerSubmitTeamFunctionGrpCreateTrans(ITrxContext anITrxContext,
			ITeamFunctionGrpTrxValue aTeamFunctionGrpTrxValue, List listTeamFunctionGrp, ITeam team)
			throws TeamFunctionGrpException;

	public ITeamFunctionGrpTrxValue makerSubmitTeamFunctionGrpUpdateTrans(ITrxContext anITrxContext,
			ITeamFunctionGrpTrxValue aTeamFunctionGrpTrxValue, List listTeamFunctionGrp, ITeam team)
			throws TeamFunctionGrpException;

	public ITeamFunctionGrpTrxValue makerSubmitRejectedTeamFunctionGrp(ITrxContext anITrxContext,
			ITeamFunctionGrpTrxValue aTrxValue, ITeam team) throws TeamFunctionGrpException;

	public ITeamFunctionGrpTrxValue makerUpdateTeamFunctionGrp(ITrxContext anITrxContext,
			ITeamFunctionGrpTrxValue aFeedGroupTrxValue, ITeamFunctionGrp aFeedGroup, ITeam team)
			throws TeamFunctionGrpException;

	public ITeamFunctionGrpTrxValue makerUpdateRejectedTeamFunctionGrp(ITrxContext anITrxContext,
			ITeamFunctionGrpTrxValue aTrxValue, ITeam team) throws TeamFunctionGrpException;

	public ITeamFunctionGrpTrxValue systemCloseTeamFunctionGroupTransaction(ITrxContext trxContext,
			ITeamFunctionGrpTrxValue teamFunctionGroupTrxValue) throws TeamFunctionGrpException;

}
