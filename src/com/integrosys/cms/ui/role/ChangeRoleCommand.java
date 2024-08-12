package com.integrosys.cms.ui.role;

import java.util.HashMap;

import com.integrosys.base.techinfra.exception.EntityNotFoundException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.bizstructure.proxy.CMSTeamProxy;
import com.integrosys.cms.app.customer.bus.ICMSCustomer;
import com.integrosys.cms.app.limit.bus.ILimitProfile;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;
import com.integrosys.cms.ui.login.CMSGlobalSessionConstant;
import com.integrosys.cms.ui.login.CMSLoginErrorCodes;
import com.integrosys.component.bizstructure.app.bus.BizStructureException;
import com.integrosys.component.bizstructure.app.bus.ITeam;
import com.integrosys.component.bizstructure.app.bus.ITeamMembership;
import com.integrosys.component.login.app.LoginErrorCodes;
import com.integrosys.component.login.ui.LoginProcessException;
import com.integrosys.component.user.app.bus.OBCommonUser;

public class ChangeRoleCommand extends AbstractCommand implements ICommonEventConstant {

	/**
	 * Default Constructor
	 */
	public ChangeRoleCommand() {

	}

	/**
	 * Defines an two dimensional array with the parameter list to be passed to
	 * the doExecute method by a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ "event", "java.lang.String", REQUEST_SCOPE },
				{ IGlobalConstant.USER, "com.integrosys.component.user.app.bus.ICommonUser", GLOBAL_SCOPE },
				{ IGlobalConstant.USER_TEAM, "com.integrosys.component.bizstructure.app.bus.ITeam", GLOBAL_SCOPE },
				{ CMSGlobalSessionConstant.TEAM_TYPE_MEMBERSHIP_TYPE_NAME, "java.lang.String", GLOBAL_SCOPE }, // cr33
				{ CMSGlobalSessionConstant.TEAM_TYPE_MEMBERSHIP_ID, "java.lang.String", GLOBAL_SCOPE }, // cr33
				{ IGlobalConstant.GLOBAL_LIMITPROFILE_OBJ, "com.integrosys.cms.app.limit.bus.ILimitProfile",
						GLOBAL_SCOPE },
				{ IGlobalConstant.GLOBAL_CUSTOMER_OBJ, "com.integrosys.cms.app.customer.bus.ICMSCustomer", GLOBAL_SCOPE },
				{ "locale", "java.util.Locale", REQUEST_SCOPE } });
	}

	/**
	 * Defines an two dimensional array with the result list to be expected as a
	 * result from the doExecute method using a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getResultDescriptor() {
		return (new String[][] {
				{ IGlobalConstant.USER, "com.integrosys.component.user.app.bus.ICommonUser", GLOBAL_SCOPE },
				{ IGlobalConstant.USER_TEAM, "com.integrosys.component.bizstructure.app.bus.ITeam", GLOBAL_SCOPE },
				{ CMSGlobalSessionConstant.TEAM_TYPE_MEMBERSHIP_TYPE_NAME, "java.lang.String", GLOBAL_SCOPE }, // cr33
				{ CMSGlobalSessionConstant.TEAM_TYPE_MEMBERSHIP_ID, "java.lang.String", GLOBAL_SCOPE }, // cr33
				{ IGlobalConstant.GLOBAL_LIMITPROFILE_OBJ, "com.integrosys.cms.app.limit.bus.ILimitProfile",
						GLOBAL_SCOPE },
				{ IGlobalConstant.GLOBAL_CUSTOMER_OBJ, "com.integrosys.cms.app.customer.bus.ICMSCustomer", GLOBAL_SCOPE },
				{ "todoGlobal","com.integrosys.cms.app.transaction.CMSTrxSearchCriteria", GLOBAL_SCOPE },		
		});
	}

	/**
	 * This method does the Business operations with the HashMap and put the
	 * results back into the HashMap.Here preparation for company borrower is
	 * done.
	 * 
	 * @param map is of type HashMap
	 * @throws CommandProcessingException on errors
	 * @throws CommandValidationException on errors
	 * @return HashMap with the Result
	 */
	// CR-33
	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
		HashMap result = new HashMap();
		HashMap returnMap = new HashMap();

		try {
			
			result.put("todoGlobal", null);
			OBCommonUser user = (OBCommonUser) map.get(IGlobalConstant.USER);
			ITeam team = (ITeam) map.get(IGlobalConstant.USER_TEAM);
			String teamMemTypeName = (String) map.get(CMSGlobalSessionConstant.TEAM_TYPE_MEMBERSHIP_TYPE_NAME);
			String teamMemberShipID = (String) map.get(CMSGlobalSessionConstant.TEAM_TYPE_MEMBERSHIP_ID);
			ILimitProfile iLimitProfile = (ILimitProfile) map.get(IGlobalConstant.GLOBAL_LIMITPROFILE_OBJ);
			ICMSCustomer iCMSCustomer = (ICMSCustomer) map.get(IGlobalConstant.GLOBAL_CUSTOMER_OBJ);

			DefaultLogger.debug(this, "---------------- teamMemberShipID" + teamMemberShipID);

			// DefaultLogger.debug(this, "Testing user from session is null - >"
			// + (user == null));
			// DefaultLogger.debug(this, "Testing team from session - >" +
			// team);

			try {
				DefaultLogger.debug(this, "in InitLoginManager retrieving team info ");
				// ITeam team = (ITeam)
				// BizStructureProxyFactory.getProxy().getTeamByUserID
				// (usr.getUserID());
				ITeam[] teams = new CMSTeamProxy().getTeamsByUserID(user.getUserID()); // CR
																						// -
																						// 33
				ITeamMembership[] memberships = (new CMSTeamProxy()).getTeamMembershipListByUserID(user.getUserID());
				ITeamMembership membership = changeTeamTypeMembershipRequested(memberships, teamMemberShipID);
				ITeam teamNew = getSelectedTeam(membership, teams);
				String teamType = teamNew.getTeamType().getBusinessCode();// needs
																			// to
																			// change
																			// this
																			// line
				String memType = membership.getTeamTypeMembership().getMembershipType().getMembershipTypeName();
				long teamMemberShipIDlong = membership.getTeamTypeMembership().getMembershipID();
				String teamMemberShipIDNew = new Long(teamMemberShipIDlong).toString();
				result.put(IGlobalConstant.USER, user);
				result.put(IGlobalConstant.USER_TEAM, teamNew);
				result.put(IGlobalConstant.GLOBAL_LIMITPROFILE_OBJ, null);
				result.put(IGlobalConstant.GLOBAL_CUSTOMER_OBJ, null);
				result.put(CMSGlobalSessionConstant.TEAM_TYPE_MEMBERSHIP_TYPE_NAME, membership.getTeamTypeMembership()
						.getMembershipType().getMembershipTypeName());
				result.put(CMSGlobalSessionConstant.TEAM_TYPE_MEMBERSHIP_ID, teamMemberShipIDNew);
				returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
				DefaultLogger.debug(this, "InitLoginManager has set this team identifier in session: "
						+ teamType.toUpperCase() + memType.toUpperCase());

			}
			catch (EntityNotFoundException enfe) {
				LoginProcessException lpexp = new LoginProcessException("Team Info not found in Database");
				lpexp.setErrorCode(LoginErrorCodes.USER_NOT_ASSIGNED_TEAM);
				throw lpexp;
			}
			catch (BizStructureException rex) {
				LoginProcessException lpexp = new LoginProcessException("BizStructureException while getting Team");
				lpexp.setErrorCode(LoginErrorCodes.GENERAL_LOGIN_ERROR);
				throw lpexp;
			}

			return returnMap;
		}
		catch (Throwable e) {
			e.printStackTrace();
			throw (new CommandProcessingException(e.getMessage()));
		}

	}

	// CR-33
	public ITeamMembership changeTeamTypeMembershipRequested(ITeamMembership[] teamMemberShips, String teamMembershipReq)
			throws LoginProcessException {

		// check if Default and has multiple roles throw excp
		// if role req is not present

		String errorCode;
		LoginProcessException lpexp = new LoginProcessException();
		if ((teamMemberShips == null) || (teamMemberShips.length == 0)) {
			lpexp.setErrorCode(CMSLoginErrorCodes.LOGIN_TEAM_MEMBERSHIP_ERROR);
			throw lpexp;
		}

		// ITeamMembership[] teamMemberships = teams[i].getTeamMemberships();

		if (teamMemberShips.length > 1) {
			// if he has multiple memberships and has not selected a membership
			if ((teamMembershipReq == null) || (teamMembershipReq.trim().length() == 0)) { // default
				lpexp.setErrorCode(CMSLoginErrorCodes.LOGIN_TEAM_NOT_SELECTED_ERROR);
				throw lpexp;
			}
			for (int i = 0; i < teamMemberShips.length; i++) {
				long teamMembershipIDlong = teamMemberShips[i].getTeamTypeMembership().getMembershipID();
				String teamMembershipIDString = new Long(teamMembershipIDlong).toString();
				if (!teamMembershipReq.equals(teamMembershipIDString)) {
					return teamMemberShips[i];
				}
			}
			lpexp.setErrorCode(CMSLoginErrorCodes.LOGIN_TEAM_MEMBERSHIP_ERROR);
			throw lpexp;
		}
		return teamMemberShips[0];
	}

	private ITeam getSelectedTeam(ITeamMembership membership, ITeam[] teams) {
		for (int i = 0; i < teams.length; i++) {
			ITeamMembership[] teamMembership = teams[i].getTeamMemberships();
			for (int j = 0; j < teamMembership.length; j++) {
				if (membership.getTeamMembershipID() == teamMembership[j].getTeamMembershipID()) {
					return teams[i];
				}
			}
		}
		return null;
	}

}
