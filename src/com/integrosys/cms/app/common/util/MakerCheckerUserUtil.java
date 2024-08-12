package com.integrosys.cms.app.common.util;

import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Map;

import com.integrosys.base.techinfra.exception.EntityNotFoundException;
import com.integrosys.base.techinfra.propertyfile.PropertyManager;
import com.integrosys.cms.app.bizstructure.proxy.ICMSTeamProxy;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.transaction.OBTrxContext;
import com.integrosys.cms.ui.login.CMSLoginErrorCodes;
import com.integrosys.component.bizstructure.app.bus.BizStructureException;
import com.integrosys.component.bizstructure.app.bus.ITeam;
import com.integrosys.component.bizstructure.app.bus.ITeamMembership;
import com.integrosys.component.login.ui.LoginProcessException;
import com.integrosys.component.user.app.bus.OBCommonUser;
import com.integrosys.component.user.app.proxy.ICommonUserProxy;

public class MakerCheckerUserUtil{
	
	public static final String DMY_MAKER; 
	public static final String DMY_CHECKER; 
	public static final String DMY_MASTER_MAKER; 
	public static final String DMY_MASTER_CHECKER; 
	
	public static final String DUMMY_MAKER = "dummy_maker";
	public static final String DUMMY_CHECKER = "dummy_checker";
	public static final String DUMMY_MASTER_MAKER = "dummy_master_maker";
	public static final String DUMMY_MASTER_CHECKER = "dummy_master_checker";
	
    static {
    	DMY_MAKER = PropertyManager.getValue(DUMMY_MAKER);
    	DMY_CHECKER = PropertyManager.getValue(DUMMY_CHECKER);
    	DMY_MASTER_MAKER = PropertyManager.getValue(DUMMY_MASTER_MAKER);
    	DMY_MASTER_CHECKER = PropertyManager.getValue(DUMMY_MASTER_CHECKER);
    }
	private ICommonUserProxy userProxy;
	private ICMSTeamProxy cmsTeamProxy;
	
	public void setUserProxy(ICommonUserProxy userProxy) {
		this.userProxy = userProxy;
	}
	public ICommonUserProxy getUserProxy() {
		return userProxy;
	}
	public ICMSTeamProxy getCmsTeamProxy() {
		return cmsTeamProxy;
	}
	public void setCmsTeamProxy(ICMSTeamProxy cmsTeamProxy) {
		this.cmsTeamProxy = cmsTeamProxy;
	}
	
	Map<String,OBTrxContext> makerCheckerMap = new HashMap<String, OBTrxContext>();
	
	public OBTrxContext setContextForMaker() throws NumberFormatException {

		OBCommonUser user = null;
		ITeam[] teams = null;
		ITeamMembership[] memberships;
		OBTrxContext context = null;
		
		try {

			context = makerCheckerMap.get("USER_MAKER");

			if(context==null){
				user = (OBCommonUser)getUserProxy().getUser(DMY_MAKER);
				teams =  getCmsTeamProxy().getTeamsByUserID(user.getUserID());
				context = new OBTrxContext(user, teams[0]);
	
				memberships = getCmsTeamProxy().getTeamMembershipListByUserID(user.getUserID());
				boolean makerCheckerSameUserChk = Boolean.valueOf(
						PropertyManager.getValue(ICMSConstant.MAKER_CHECKER_SAME_USER)).booleanValue();
				ITeamMembership membershipChk = null;
				if (makerCheckerSameUserChk) {
					membershipChk = validateMakerCheckerSelection(memberships, "");
				}
				else {
					membershipChk = validateTeamTypeMembershipRequested(memberships, null);
				}
				String teamMembershipIDChk =  String.valueOf(membershipChk.getTeamTypeMembership().getMembershipID());
				context.setTeamMembershipID(getTeamMembershipIDFromTeam(Long.parseLong(teamMembershipIDChk),  teams[0]));
	
				makerCheckerMap.put("USER_MAKER",context);
			}
			return context;
		} catch (EntityNotFoundException e) {
			e.printStackTrace();
		}
		catch (BizStructureException e) {
			e.printStackTrace();
		}
		catch (RemoteException e) {
			e.printStackTrace();
		}
		return null;
	}
	public OBTrxContext setContextForChecker() throws NumberFormatException {
		
		OBCommonUser user = null;
		ITeam[] teams = null;
		ITeamMembership[] memberships;
		OBTrxContext context = null;
		
		try {
			
			context = makerCheckerMap.get("USER_CHECKER");
			
			if(context==null){
				user = (OBCommonUser) getUserProxy().getUser(DMY_CHECKER);
				teams = getCmsTeamProxy().getTeamsByUserID(user.getUserID());
				context = new OBTrxContext(user, teams[0]);
				
				memberships = getCmsTeamProxy().getTeamMembershipListByUserID(user.getUserID());
				boolean makerCheckerSameUserChk = Boolean.valueOf(
						PropertyManager.getValue(ICMSConstant.MAKER_CHECKER_SAME_USER)).booleanValue();
				ITeamMembership membershipChk = null;
				if (makerCheckerSameUserChk) {
					membershipChk = validateMakerCheckerSelection(memberships, "");
				}
				else {
					membershipChk = validateTeamTypeMembershipRequested(memberships, null);
				}
				String teamMembershipIDChk =  String.valueOf(membershipChk.getTeamTypeMembership().getMembershipID());
				context.setTeamMembershipID(getTeamMembershipIDFromTeam(Long.parseLong(teamMembershipIDChk),  teams[0]));
				makerCheckerMap.put("USER_CHECKER",context);
			}
			
			return context;
		} catch (EntityNotFoundException e) {
			e.printStackTrace();
		}
		catch (BizStructureException e) {
			e.printStackTrace();
		}
		catch (RemoteException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private long getTeamMembershipIDFromTeam(long teamTypeID, ITeam team) {
		if (team == null) {
			return ICMSConstant.LONG_INVALID_VALUE;
		}
		ITeamMembership[] memberships = team.getTeamMemberships();
		if (memberships != null) {
			for (int i = 0; i < memberships.length; i++) {
				if (memberships[i].getTeamTypeMembership().getMembershipID() == teamTypeID) {
					return memberships[i].getTeamMembershipID();
				}
			}
		}
		return ICMSConstant.LONG_INVALID_VALUE;
	}
	
	private ITeamMembership validateMakerCheckerSelection(ITeamMembership[] teamMemberShips, String membershipID)
	throws LoginProcessException {
		LoginProcessException lpexp = new LoginProcessException();
		if ((teamMemberShips == null) || (teamMemberShips.length == 0)) {
			lpexp.setErrorCode(CMSLoginErrorCodes.LOGIN_TEAM_MEMBERSHIP_ERROR);
			throw lpexp;
		}
		if (teamMemberShips.length > 1) {
			// the user has maker checker role
			if ((membershipID == null) || (membershipID.trim().length() == 0)) {
				lpexp.setErrorCode(CMSLoginErrorCodes.LOGIN_TEAM_NOT_SELECTED_ERROR);
				throw lpexp;
			}
			for (int i = 0; i < teamMemberShips.length; i++) {
				long membershipIDlong = teamMemberShips[i].getTeamTypeMembership().getMembershipType()
				.getMembershipTypeID();
				String membershipIDString = new Long(membershipIDlong).toString();
				if (membershipID.equals(membershipIDString)) {
					return teamMemberShips[i];
				}
			}
			lpexp.setErrorCode(CMSLoginErrorCodes.LOGIN_TEAM_MEMBERSHIP_ERROR);
			throw lpexp;
		}
		return teamMemberShips[0];
	}
	
	private ITeamMembership validateTeamTypeMembershipRequested(ITeamMembership[] teamMemberShips,
			String teamMembershipReq) throws LoginProcessException {

		LoginProcessException lpexp = new LoginProcessException();
		if ((teamMemberShips == null) || (teamMemberShips.length == 0)) {
			lpexp.setErrorCode(CMSLoginErrorCodes.LOGIN_TEAM_MEMBERSHIP_ERROR);
			throw lpexp;
		}
		if (teamMemberShips.length > 1) {
			if ((teamMembershipReq == null) || (teamMembershipReq.trim().length() == 0)) { // default
				lpexp.setErrorCode(CMSLoginErrorCodes.LOGIN_TEAM_NOT_SELECTED_ERROR);
				throw lpexp;
			}
			for (int i = 0; i < teamMemberShips.length; i++) {
				long teamMembershipIDlong = teamMemberShips[i].getTeamTypeMembership().getMembershipID();
				String teamMembershipIDString = new Long(teamMembershipIDlong).toString();
				if (teamMembershipReq.equals(teamMembershipIDString)) {
					return teamMemberShips[i];
				}
			}
			lpexp.setErrorCode(CMSLoginErrorCodes.LOGIN_TEAM_MEMBERSHIP_ERROR);
			throw lpexp;
		}
		return teamMemberShips[0];
	}
	
	
	public OBTrxContext setContextForMasterMaker() throws NumberFormatException {

		OBCommonUser user = null;
		ITeam[] teams = null;
		ITeamMembership[] memberships;
		OBTrxContext context = null;
		
		try {

			context = makerCheckerMap.get("USER_MASTER_MAKER");

			if(context==null){
				user = (OBCommonUser)getUserProxy().getUser(DMY_MASTER_MAKER);
				teams =  getCmsTeamProxy().getTeamsByUserID(user.getUserID());
				context = new OBTrxContext(user, teams[0]);
	
				memberships = getCmsTeamProxy().getTeamMembershipListByUserID(user.getUserID());
				boolean makerCheckerSameUserChk = Boolean.valueOf(
						PropertyManager.getValue(ICMSConstant.MAKER_CHECKER_SAME_USER)).booleanValue();
				ITeamMembership membershipChk = null;
				if (makerCheckerSameUserChk) {
					membershipChk = validateMakerCheckerSelection(memberships, "");
				}
				else {
					membershipChk = validateTeamTypeMembershipRequested(memberships, null);
				}
				String teamMembershipIDChk =  String.valueOf(membershipChk.getTeamTypeMembership().getMembershipID());
				context.setTeamMembershipID(getTeamMembershipIDFromTeam(Long.parseLong(teamMembershipIDChk),  teams[0]));
	
				makerCheckerMap.put("USER_MASTER_MAKER",context);
			}
			return context;
		} catch (EntityNotFoundException e) {
			e.printStackTrace();
		}
		catch (BizStructureException e) {
			e.printStackTrace();
		}
		catch (RemoteException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public OBTrxContext setContextForMasterChecker() throws NumberFormatException {
		
		OBCommonUser user = null;
		ITeam[] teams = null;
		ITeamMembership[] memberships;
		OBTrxContext context = null;
		
		try {
			
			context = makerCheckerMap.get("USER_MASTER_CHECKER");
			
			if(context==null){
				user = (OBCommonUser) getUserProxy().getUser(DMY_MASTER_CHECKER);
				teams = getCmsTeamProxy().getTeamsByUserID(user.getUserID());
				context = new OBTrxContext(user, teams[0]);
				
				memberships = getCmsTeamProxy().getTeamMembershipListByUserID(user.getUserID());
				boolean makerCheckerSameUserChk = Boolean.valueOf(
						PropertyManager.getValue(ICMSConstant.MAKER_CHECKER_SAME_USER)).booleanValue();
				ITeamMembership membershipChk = null;
				if (makerCheckerSameUserChk) {
					membershipChk = validateMakerCheckerSelection(memberships, "");
				}
				else {
					membershipChk = validateTeamTypeMembershipRequested(memberships, null);
				}
				String teamMembershipIDChk =  String.valueOf(membershipChk.getTeamTypeMembership().getMembershipID());
				context.setTeamMembershipID(getTeamMembershipIDFromTeam(Long.parseLong(teamMembershipIDChk),  teams[0]));
				makerCheckerMap.put("USER_MASTER_CHECKER",context);
			}
			
			return context;
		} catch (EntityNotFoundException e) {
			e.printStackTrace();
		}
		catch (BizStructureException e) {
			e.printStackTrace();
		}
		catch (RemoteException e) {
			e.printStackTrace();
		}
		return null;
	}
}
