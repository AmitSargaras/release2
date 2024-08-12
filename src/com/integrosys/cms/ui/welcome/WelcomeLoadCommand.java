package com.integrosys.cms.ui.welcome;

import java.rmi.RemoteException;
import java.util.HashMap;

import com.integrosys.base.businfra.search.SearchDAOException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.diary.bus.DiaryItemException;
import com.integrosys.cms.app.diary.proxy.IDiaryItemProxyManager;
import com.integrosys.cms.app.notification.bus.CMSNotificationException;
import com.integrosys.cms.app.notification.proxy.CMSNotificationFactory;
import com.integrosys.cms.app.transaction.CMSTrxSearchCriteria;
import com.integrosys.cms.app.transaction.SBCMSTrxManager;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;
import com.integrosys.component.bizstructure.app.bus.ITeam;
import com.integrosys.component.bizstructure.app.bus.ITeamMember;
import com.integrosys.component.bizstructure.app.bus.ITeamMembership;
import com.integrosys.component.user.app.bus.OBCommonUser;

public class WelcomeLoadCommand extends AbstractCommand implements ICommonEventConstant {

	private SBCMSTrxManager workflowManager;

	private IDiaryItemProxyManager diaryItemProxyManager;

	public SBCMSTrxManager getWorkflowManager() {
		return workflowManager;
	}

	public IDiaryItemProxyManager getDiaryItemProxyManager() {
		return diaryItemProxyManager;
	}

	public void setWorkflowManager(SBCMSTrxManager workflowManager) {
		this.workflowManager = workflowManager;
	}

	public void setDiaryItemProxyManager(IDiaryItemProxyManager diaryItemProxyManager) {
		this.diaryItemProxyManager = diaryItemProxyManager;
	}

	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ IGlobalConstant.USER, "com.integrosys.component.user.app.bus.ICommonUser", GLOBAL_SCOPE },
				{ IGlobalConstant.USER_TEAM, "com.integrosys.component.bizstructure.app.bus.ITeam", GLOBAL_SCOPE },
				{ IGlobalConstant.TEAM_TYPE_MEMBERSHIP_TYPE_NAME, "java.lang.String", GLOBAL_SCOPE },
				{ "countIndex", "java.lang.String", REQUEST_SCOPE } });
	}

	public String[][] getResultDescriptor() {
		return (new String[][] { { "todoCount", "java.lang.String", REQUEST_SCOPE },
				{ "newBorrowerCount", "java.lang.String", REQUEST_SCOPE },
				{ "newNonBorrowerCount", "java.lang.String", REQUEST_SCOPE },
				{ "notificationCount", "java.lang.String", REQUEST_SCOPE },
				{ "diaryItemDueCount", "java.lang.String", REQUEST_SCOPE } });
	}

	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
		HashMap result = new HashMap();
		HashMap returnMap = new HashMap();
		try {
			OBCommonUser user = (OBCommonUser) map.get(IGlobalConstant.GLOBAL_LOS_USER);
			ITeam team = (ITeam) map.get(IGlobalConstant.USER_TEAM);
			long teamTypeMembershipID = getTeamTypeMembershipID(user, team);
			CMSTrxSearchCriteria criteria = getCMSTrxCriteria(user, team, teamTypeMembershipID);

			String indexStr = (String) map.get("countIndex");
			int countIndex = -1;
			if (indexStr != null) {
				countIndex = Integer.parseInt(indexStr);
			}
			int todoCount = 0;
			int notificationCount = 0;
			int diaryItemDueCount = 0;
			int newBorrowerCount = 0;
			int newNonBorrowerCount = 0;
			switch (countIndex) {
			case 1:
				notificationCount = getNotificationCount(user, team, criteria);
				break;
			case 2:
				diaryItemDueCount = getDiaryItemCount(team);
				break;
			case 3:
				newBorrowerCount = getNewBorrowerCount(team, teamTypeMembershipID);
				break;
			case 4:
				newNonBorrowerCount = getNewNonBorrowerCount(team, teamTypeMembershipID);
				break;
			case 5:
				todoCount = getToDoCount(criteria);
				break;
			default:
				DefaultLogger.debug(this, "Don't count!");
				break;
			}
			result.put("todoCount", String.valueOf(todoCount));
			result.put("newBorrowerCount", String.valueOf(newBorrowerCount));
			result.put("newNonBorrowerCount", String.valueOf(newNonBorrowerCount));
			result.put("notificationCount", String.valueOf(notificationCount));
			result.put("diaryItemDueCount", String.valueOf(diaryItemDueCount));

			returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
			return returnMap;
		}
		catch (Exception ex) {
			throw new CommandProcessingException("failed to load task management, notification or diary count", ex);
		}
	}

	private int getNewBorrowerCount(ITeam team, long teamTypeMembershipID) throws Exception {
		if (!isCoutRequired(teamTypeMembershipID)) {
			return 0;
		}
		CMSTrxSearchCriteria c1 = new CMSTrxSearchCriteria();
		c1.setSearchIndicator(ICMSConstant.TODO_ACTION);
		String[] trxx = { ICMSConstant.INSTANCE_LIMIT_PROFILE };
		c1.setTransactionTypes(trxx);
		c1.setCurrentState(ICMSConstant.STATE_NEW);

		c1.setCurrentState(true);
		c1.setOnlyMembershipRecords(false);
		c1.setTeamTypeMembershipID(teamTypeMembershipID);
		c1.setAllowedCountries(team.getCountryCodes());
		c1.setAllowedOrganisations(team.getOrganisationCodes());
		c1.setAllowedSegments(team.getSegmentCodes());
		c1.setCustomerType(ICMSConstant.CUSTOMER_TYPE_BORROWER);
		return getWorkflowManager().getWorkflowTrxCount(c1);
	}

	private int getNewNonBorrowerCount(ITeam team, long teamTypeMembershipID) throws Exception {
		if (!isCoutRequired(teamTypeMembershipID)) {
			return 0;
		}
		CMSTrxSearchCriteria c1 = new CMSTrxSearchCriteria();
		c1.setSearchIndicator(ICMSConstant.TODO_ACTION);
		String[] trxx = { ICMSConstant.INSTANCE_LIMIT_PROFILE };
		c1.setTransactionTypes(trxx);
		c1.setCurrentState(ICMSConstant.STATE_NEW);

		c1.setCurrentState(true);
		c1.setOnlyMembershipRecords(false);
		c1.setTeamTypeMembershipID(teamTypeMembershipID);
		c1.setAllowedCountries(team.getCountryCodes());
		c1.setAllowedOrganisations(team.getOrganisationCodes());
		c1.setAllowedSegments(team.getSegmentCodes());
		c1.setCustomerType(ICMSConstant.CUSTOMER_TYPE_NONBORROWER);
		return getWorkflowManager().getWorkflowTrxCount(c1);
	}

	private boolean isCoutRequired(long teamTypeMembershipID) {
		if ((teamTypeMembershipID != ICMSConstant.TEAM_TYPE_FAM_USER)
				&& (teamTypeMembershipID != ICMSConstant.TEAM_TYPE_SC_MAKER)
				&& (teamTypeMembershipID != ICMSConstant.TEAM_TYPE_SC_MAKER_WFH)
				&& (teamTypeMembershipID != ICMSConstant.TEAM_TYPE_SC_CHECKER)
				&& (teamTypeMembershipID != ICMSConstant.TEAM_TYPE_SC_CHECKER_WFH)
				&& (teamTypeMembershipID != ICMSConstant.TEAM_TYPE_GAM_USER)
				&& (teamTypeMembershipID != ICMSConstant.TEAM_TYPE_SCO_USER)
				&& (teamTypeMembershipID != ICMSConstant.TEAM_TYPE_MIS_USER)
				&& (teamTypeMembershipID != ICMSConstant.TEAM_TYPE_GROUP_MANAGEMENT_USER)
				&& (teamTypeMembershipID != ICMSConstant.TEAM_TYPE_CPC_CUSTODIAN_MAKER)
				&& (teamTypeMembershipID != ICMSConstant.TEAM_TYPE_CPC_CUSTODIAN_CHECKER)) {
			return true;
		}
		else {
			return false;
		}
	}

	private CMSTrxSearchCriteria getCMSTrxCriteria(OBCommonUser user, ITeam team, long teamTypeMembershipID) {
		CMSTrxSearchCriteria criteria = new CMSTrxSearchCriteria();
		criteria.setStartIndex(0);
		criteria.setNItems(10);
		criteria.setAllowedCountries(team.getCountryCodes());
		criteria.setAllowedOrganisations(team.getOrganisationCodes());
		criteria.setAllowedSegments(team.getSegmentCodes());
		criteria.setSearchIndicator(ICMSConstant.TODO_ACTION);
		criteria.setTeamTypeMembershipID(teamTypeMembershipID);
		criteria.setUserID(user.getUserID());
		return criteria;
	}

	private long getTeamTypeMembershipID(OBCommonUser user, ITeam team) {
		long teamTypeMembershipID = com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;
		for (int i = 0; i < team.getTeamMemberships().length; i++) {
			ITeamMembership iTeamMembership = team.getTeamMemberships()[i];
			for (int j = 0; j < iTeamMembership.getTeamMembers().length; j++) {
				ITeamMember iTeamMember = iTeamMembership.getTeamMembers()[j];
				if (iTeamMember.getTeamMemberUser().getUserID() == user.getUserID()) {
					teamTypeMembershipID = iTeamMembership.getTeamTypeMembership().getMembershipID();
				}
			}

		}
		return teamTypeMembershipID;
	}

	private int getToDoCount(CMSTrxSearchCriteria criteria) throws SearchDAOException, RemoteException {
		int todoCount = 0;

		if ((criteria.getTeamTypeMembershipID() != ICMSConstant.TEAM_TYPE_MIS_USER)
				&& (criteria.getTeamTypeMembershipID() != ICMSConstant.TEAM_TYPE_GROUP_MANAGEMENT_USER)
				&& (criteria.getTeamTypeMembershipID() != ICMSConstant.TEAM_TYPE_CPC_MANAGER_USER)) {
			todoCount = getWorkflowManager().getTransactionCount(criteria);
		}
		return todoCount;
	}

	private int getNotificationCount(OBCommonUser user, ITeam team, CMSTrxSearchCriteria criteria)
			throws CMSNotificationException {
		int notificationCount = 0;
		if ((criteria.getTeamTypeMembershipID() != ICMSConstant.TEAM_TYPE_SYS_ADMIN_MAKER)
				&& (criteria.getTeamTypeMembershipID() != ICMSConstant.TEAM_TYPE_SYS_ADMIN_CHECKER)) {
			notificationCount = CMSNotificationFactory.getCMSNotificationProxy().countNotifications(team.getTeamID(),
					user.getUserID());
		}
		return notificationCount;
	}

	private int getDiaryItemCount(ITeam team) throws DiaryItemException, SearchDAOException {
		int diaryItemDueCount = 0;
		diaryItemDueCount = getDiaryItemProxyManager().getDiaryItemsDueFor(team, team.getCountryCodes());

		return diaryItemDueCount;
	}

}
