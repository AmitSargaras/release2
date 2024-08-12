//Source file: C:\\los_rose\\src\\com\\integrosys\\los\\app\\team\\TeamProxy.java

package com.integrosys.cms.app.userrole.proxy;

import java.rmi.RemoteException;
import java.util.List;

import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.base.techinfra.exception.EntityNotFoundException;
import com.integrosys.cms.app.bizstructure.trx.OBCMSTeamTrxValue;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.ITrxContext;
import com.integrosys.cms.app.transaction.TrxOperationHelper;
import com.integrosys.cms.app.user.bus.SBUserTeamManager;
import com.integrosys.cms.app.userrole.bus.IUserRoleBus;
import com.integrosys.cms.app.userrole.bus.UserRoleBusImpl;
import com.integrosys.component.bizstructure.app.bus.BizStructureException;
import com.integrosys.component.bizstructure.app.bus.ITeam;
import com.integrosys.component.bizstructure.app.bus.ITeamMembership;
import com.integrosys.component.bizstructure.app.bus.TeamException;
import com.integrosys.component.bizstructure.app.proxy.BizStructureProxyImpl;
import com.integrosys.component.bizstructure.app.trx.ITeamTrxValue;
import com.integrosys.component.common.transaction.ICompTrxResult;

public class CMSUserRoleProxy extends BizStructureProxyImpl implements ICMSUserRoleProxy {

	private static final long serialVersionUID = -2641709401340241974L;

	/**
     */
	public CMSUserRoleProxy() {
		super();
	}

	public ICompTrxResult makerCreateTeam(ITeamTrxValue trxValue, ITeam team) throws TeamException {
		throw new TeamException("Use the method with ITrxContext as parameter. An ITrxContext object is required!");
	}

	public ICompTrxResult checkerApproveCreateTeam(ITeamTrxValue trxValue) throws TeamException {
		throw new TeamException("Use the method with ITrxContext as parameter. An ITrxContext object is required!");
	}

	public ICompTrxResult checkerRejectCreateTeam(ITeamTrxValue trxValue) throws TeamException {
		throw new TeamException("Use the method with ITrxContext as parameter. An ITrxContext object is required!");
	}

	public ICompTrxResult makerCancelCreateTeam(ITeamTrxValue trxValue) throws TeamException {
		throw new TeamException("Use the method with ITrxContext as parameter. An ITrxContext object is required!");
	}

	public ICompTrxResult makerUpdateTeam(ITeamTrxValue trx, ITeam team) throws TeamException {
		throw new TeamException("Use the method with ITrxContext as parameter. An ITrxContext object is required!");
	}

	public ICompTrxResult checkerApproveUpdateTeam(ITeamTrxValue trxValue) throws TeamException {
		throw new TeamException("Use the method with ITrxContext as parameter. An ITrxContext object is required!");
	}

	public ICompTrxResult checkerRejectUpdateTeam(ITeamTrxValue trxValue) throws TeamException {
		throw new TeamException("Use the method with ITrxContext as parameter. An ITrxContext object is required!");
	}

	public ICompTrxResult makerCancelUpdateTeam(ITeamTrxValue trxValue) throws TeamException {
		throw new TeamException("Use the method with ITrxContext as parameter. An ITrxContext object is required!");
	}

	public ICompTrxResult makerDeleteTeam(ITeamTrxValue trx) throws TeamException {
		throw new TeamException("Use the method with ITrxContext as parameter. An ITrxContext object is required!");
	}

	public ICompTrxResult checkerApproveDeleteTeam(ITeamTrxValue trxValue) throws TeamException {
		throw new TeamException("Use the method with ITrxContext as parameter. An ITrxContext object is required!");
	}

	public ICompTrxResult checkerRejectDeleteTeam(ITeamTrxValue trxValue) throws TeamException {
		throw new TeamException("Use the method with ITrxContext as parameter. An ITrxContext object is required!");
	}

	public ICompTrxResult makerCancelDeleteTeam(ITeamTrxValue trxValue) throws TeamException {
		throw new TeamException("Use the method with ITrxContext as parameter. An ITrxContext object is required!");
	}

	// ******* Methods for maker/checker create and maintenance of team
	// **********
	public ICompTrxResult makerCreateTeam(ITrxContext trxContext, ITeamTrxValue trxValue, ITeam team)
			throws TeamException {
		trxValue = (ITeamTrxValue) mapTrxValue(trxContext, trxValue, team);

		return super.makerCreateTeam(trxValue, team);
	}

	public ICompTrxResult checkerApproveCreateTeam(ITrxContext trxContext, ITeamTrxValue trxValue) throws TeamException {
		trxValue = (ITeamTrxValue) mapTrxValue(trxContext, trxValue, null);

		return super.checkerApproveCreateTeam(trxValue);
	}

	public ICompTrxResult checkerRejectCreateTeam(ITrxContext trxContext, ITeamTrxValue trxValue) throws TeamException {
		trxValue = (ITeamTrxValue) mapTrxValue(trxContext, trxValue, null);

		return super.checkerRejectCreateTeam(trxValue);
	}

	public ICompTrxResult makerCancelCreateTeam(ITrxContext trxContext, ITeamTrxValue trxValue) throws TeamException {
		trxValue = (ITeamTrxValue) mapTrxValue(trxContext, trxValue, null);

		return super.makerCancelCreateTeam(trxValue);
	}

	public ICompTrxResult makerUpdateTeam(ITrxContext trxContext, ITeamTrxValue trxValue, ITeam team)
			throws TeamException {
		trxValue = (ITeamTrxValue) mapTrxValue(trxContext, trxValue, team);

		return super.makerUpdateTeam(trxValue, team);
	}

	public ICompTrxResult checkerApproveUpdateTeam(ITrxContext trxContext, ITeamTrxValue trxValue) throws TeamException {
		trxValue = (ITeamTrxValue) mapTrxValue(trxContext, trxValue, null);

		return super.checkerApproveUpdateTeam(trxValue);
	}

	public ICompTrxResult checkerRejectUpdateTeam(ITrxContext trxContext, ITeamTrxValue trxValue) throws TeamException {
		trxValue = (ITeamTrxValue) mapTrxValue(trxContext, trxValue, null);

		return super.checkerRejectUpdateTeam(trxValue);
	}

	public ICompTrxResult makerCancelUpdateTeam(ITrxContext trxContext, ITeamTrxValue trxValue) throws TeamException {
		trxValue = (ITeamTrxValue) mapTrxValue(trxContext, trxValue, null);

		return super.makerCancelUpdateTeam(trxValue);
	}

	public ICompTrxResult makerDeleteTeam(ITrxContext trxContext, ITeamTrxValue trxValue) throws TeamException {
		trxValue = (ITeamTrxValue) mapTrxValue(trxContext, trxValue, null);

		return super.makerDeleteTeam(trxValue);
	}

	public ICompTrxResult checkerApproveDeleteTeam(ITrxContext trxContext, ITeamTrxValue trxValue) throws TeamException {
		trxValue = (ITeamTrxValue) mapTrxValue(trxContext, trxValue, null);

		return super.checkerApproveDeleteTeam(trxValue);
	}

	public ICompTrxResult checkerRejectDeleteTeam(ITrxContext trxContext, ITeamTrxValue trxValue) throws TeamException {
		trxValue = (ITeamTrxValue) mapTrxValue(trxContext, trxValue, null);

		return super.checkerRejectDeleteTeam(trxValue);
	}

	public ICompTrxResult makerCancelDeleteTeam(ITrxContext trxContext, ITeamTrxValue trxValue) throws TeamException {
		trxValue = (ITeamTrxValue) mapTrxValue(trxContext, trxValue, null);

		return super.makerCancelDeleteTeam(trxValue);
	}

	public ITeam[] getTeamsByUserID(long userID) throws EntityNotFoundException, BizStructureException {
		try {
			return getMultiRoleMgr().getTeamsByUserID(userID);
		}
		catch (RemoteException re) {
			throw new EntityNotFoundException("cannot find team given user id [" + userID + "]", re.getCause());
		}
	}

	public ITeamMembership[] getTeamMembershipListByUserID(long userID) throws EntityNotFoundException {
		try {
			return getMultiRoleMgr().getTeamMembershipListByUserID(userID);
		}
		catch (RemoteException re) {
			throw new EntityNotFoundException("cannot find team membership given user id [" + userID + "]", re
					.getCause());
		}
	}

	protected SBUserTeamManager getMultiRoleMgr() {
		SBUserTeamManager mgr = (SBUserTeamManager) BeanController.getEJB("SBUserTeamManagerHome",
				"com.integrosys.cms.app.user.bus.SBUserTeamManagerHome");
		return mgr;
	}

	/**
	 * Prepares map trx context into ICMSTrxValue
	 */
	private ITrxValue mapTrxValue(ITrxContext trxContext, ITrxValue value, ITeam team) throws TeamException {
		try {
			value = TrxOperationHelper.mapTrxContext(trxContext, value);
			ICMSTrxValue trxValue = (ICMSTrxValue) value;
			trxValue.setTrxContext(trxContext);

			OBCMSTeamTrxValue ttrx = (OBCMSTeamTrxValue) value;
			if (team != null) {
				ttrx.setCustomerName(team.getAbbreviation());
			}
			else if (ttrx.getStagingTeam() != null) {
				ttrx.setCustomerName(ttrx.getStagingTeam().getAbbreviation());
			}
			else if (ttrx.getTeam() != null) {
				ttrx.setCustomerName(ttrx.getTeam().getAbbreviation());
			}

			return trxValue;
		}
		catch (TransactionException e) {
			throw new TeamException("failed to map trx context into transaction value", e);
		}
	}
	
	
	public List getUserModuleList(String membershipId){
		IUserRoleBus userRoleBus = new UserRoleBusImpl();
		
		return userRoleBus.getUserModuleList(membershipId);
	}
}
