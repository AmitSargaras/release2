package com.integrosys.cms.app.userrole.proxy;

import java.util.List;

import com.integrosys.base.techinfra.exception.EntityNotFoundException;
import com.integrosys.cms.app.transaction.ITrxContext;
import com.integrosys.component.bizstructure.app.bus.BizStructureException;
import com.integrosys.component.bizstructure.app.bus.ITeam;
import com.integrosys.component.bizstructure.app.bus.ITeamMembership;
import com.integrosys.component.bizstructure.app.bus.TeamException;
import com.integrosys.component.bizstructure.app.proxy.IBizStructureProxy;
import com.integrosys.component.bizstructure.app.trx.ITeamTrxValue;
import com.integrosys.component.common.transaction.ICompTrxResult;

/**
 * Service bean to interface with CMS Team Infrastructure.
 * 
 * @author Chong Jun Yong
 * 
 */
public interface ICMSUserRoleProxy extends IBizStructureProxy {
	public ICompTrxResult makerCreateTeam(ITrxContext trxContext, ITeamTrxValue trxValue, ITeam team)
			throws TeamException;

	public ICompTrxResult checkerApproveCreateTeam(ITrxContext trxContext, ITeamTrxValue trxValue) throws TeamException;

	public ICompTrxResult checkerRejectCreateTeam(ITrxContext trxContext, ITeamTrxValue trxValue) throws TeamException;

	public ICompTrxResult makerCancelCreateTeam(ITrxContext trxContext, ITeamTrxValue trxValue) throws TeamException;

	public ICompTrxResult makerUpdateTeam(ITrxContext trxContext, ITeamTrxValue trxValue, ITeam team)
			throws TeamException;

	public ICompTrxResult checkerApproveUpdateTeam(ITrxContext trxContext, ITeamTrxValue trxValue) throws TeamException;

	public ICompTrxResult checkerRejectUpdateTeam(ITrxContext trxContext, ITeamTrxValue trxValue) throws TeamException;

	public ICompTrxResult makerCancelUpdateTeam(ITrxContext trxContext, ITeamTrxValue trxValue) throws TeamException;

	public ICompTrxResult makerDeleteTeam(ITrxContext trxContext, ITeamTrxValue trxValue) throws TeamException;

	public ICompTrxResult checkerApproveDeleteTeam(ITrxContext trxContext, ITeamTrxValue trxValue) throws TeamException;

	public ICompTrxResult checkerRejectDeleteTeam(ITrxContext trxContext, ITeamTrxValue trxValue) throws TeamException;

	public ICompTrxResult makerCancelDeleteTeam(ITrxContext trxContext, ITeamTrxValue trxValue) throws TeamException;

	public ITeam[] getTeamsByUserID(long userID) throws EntityNotFoundException, BizStructureException;

	public ITeamMembership[] getTeamMembershipListByUserID(long userID) throws EntityNotFoundException;
	
	public List getUserModuleList(String userID);
}
