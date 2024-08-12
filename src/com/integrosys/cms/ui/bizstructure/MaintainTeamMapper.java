/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:
 */
package com.integrosys.cms.ui.bizstructure;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.MapperException;
import com.integrosys.component.bizstructure.app.bus.ITeamMember;
import com.integrosys.component.bizstructure.app.bus.ITeamMembership;
import com.integrosys.component.bizstructure.app.bus.ITeamType;
import com.integrosys.component.bizstructure.app.bus.ITeamTypeMembership;
import com.integrosys.component.bizstructure.app.bus.OBTeam;
import com.integrosys.component.bizstructure.app.bus.OBTeamMember;
import com.integrosys.component.bizstructure.app.bus.OBTeamMembership;
import com.integrosys.component.bizstructure.app.bus.OBTeamSearchCriteria;
import com.integrosys.component.bizstructure.app.bus.TeamSearchCriteria;
import com.integrosys.component.bizstructure.app.trx.ITeamTrxValue;
import com.integrosys.component.user.app.bus.ICommonUser;

/**
 * Mapper class used to map form values to objects and vice versa
 * @author $Author: dli $<br>
 * @version $Revision: 1.6 $
 * @since $Date: 2005/08/16 12:33:34 $ Tag: $Name: $
 */
public class MaintainTeamMapper extends AbstractCommonMapper {
	/**
	 * Default Construtor
	 */
	public MaintainTeamMapper() {
	}

	public String[][] getParameterDescriptor() {
		return (new String[][] { { ICommonEventConstant.EVENT, "java.lang.String", REQUEST_SCOPE },
				{ "teamTypeId", "java.lang.String", REQUEST_SCOPE },
				{ "TeamTrxValue", "com.integrosys.cms.app.bizstructure.trx.OBCMSTeamTrxValue", SERVICE_SCOPE },
				{ "TeamTypeList", "java.util.Arrays$ArrayList", SERVICE_SCOPE },
				{ "memshipTypeId", "java.lang.String", SERVICE_SCOPE },
				{ "mapTeam", "com.integrosys.component.bizstructure.app.bus.OBTeam", SERVICE_SCOPE },
				{ "addUserList", "java.util.ArrayList", SERVICE_SCOPE },
				{ "memTypeId", "java.lang.String", REQUEST_SCOPE }, });
	}

	/**
	 * This method is used to map the Form values into Corresponding OB Values
	 * and returns the same.
	 * 
	 * @param cForm is of type CommonForm
	 * @return Object
	 */
	public Object mapFormToOB(CommonForm cForm, HashMap map) throws MapperException {
		DefaultLogger.debug(this, "Inside Map Form to OB ");
		String event = (String) map.get(ICommonEventConstant.EVENT);
		MaintainTeamForm aForm = (MaintainTeamForm) cForm;
		if (event.equals("maker_list_team") || event.equals("redirect_list_team")) {
			TeamSearchCriteria sc = new TeamSearchCriteria();
			sc.setStartIndex(aForm.getStartIndex());
			sc.setNItems(10);
			OBTeamSearchCriteria obsc = new OBTeamSearchCriteria();
			long teamTypeId = Long.parseLong((String) map.get("teamTypeId"));
			obsc.setTeamTypeID(teamTypeId);
			sc.setCriteria(obsc);
			return sc;
		}
		else if (event.equals("maker_edit_team") || event.equals("maker_edit_reject_edit")
				|| event.equals("search_team_user_for_edit") || event.equals("search_team_user_for_edit_reject")
				|| event.equals("remove_team_users_for_edit") || event.equals("remove_team_user_for_edit_reject")
				|| event.equals("add_ctry_edit_team") || event.equals("add_ctry_edit_team_reject")
				|| event.equals("del_ctry_edit_team") || event.equals("del_ctry_edit_team_reject")) {
			OBTeam obTeam = (OBTeam) map.get("mapTeam");
			obTeam.setAbbreviation(aForm.getTeamName());
			obTeam.setDescription(aForm.getTeamDesc());
			obTeam.setCountryCodes(aForm.getCtryCode());
			obTeam.setOrganisationCodes(aForm.getOrgCode());
			//Application Type
			obTeam.setSegmentCodes(aForm.getSgmtCode());
			//Bank Business Unit 
			obTeam.setCMSSegmentCodes(aForm.getSgmtCodeCMS());
			obTeam.setOrgGroupCode(aForm.getOrgGroupCode());
			if ((event.equals("remove_team_users_for_edit") || event.equals("remove_team_user_for_edit_reject"))
					&& ((aForm.getRmUser() != null) && (aForm.getRmUser().length > 0))) {
				String membershipTypeId = (String) map.get("memTypeId");
				removeUsers(obTeam, aForm.getRmUser(), membershipTypeId);
			}
			return obTeam;
		}
		else if (event.equals("maker_add_team") || event.equals("maker_edit_reject_add")
				|| event.equals("search_team_user_for_add") || event.equals("search_team_user_for_add_reject")
				|| event.equals("remove_team_users_for_add") || event.equals("remove_team_user_for_add_reject")
				|| event.equals("add_ctry_add_team") || event.equals("add_ctry_add_team_reject")
				|| event.equals("del_ctry_add_team") || event.equals("del_ctry_add_team_reject")) {

			OBTeam team = (OBTeam) map.get("mapTeam");
			if (team == null) {
				team = new OBTeam();
				if (event.equals("maker_edit_reject_add")) {
					ITeamTrxValue iTeamTrxVal = (ITeamTrxValue) map.get("TeamTrxValue");
					AccessorUtil.copyValue(iTeamTrxVal.getStagingTeam(), team);
				}
				ITeamType tt = getTeamTypeFromList(map);
				team.setTeamType(tt);
				ITeamTypeMembership[] ttm = tt.getTeamTypeMemberships();
				ITeamMembership[] tm = new ITeamMembership[ttm.length];
				for (int i = 0; i < ttm.length; i++) {
					tm[i] = new OBTeamMembership();
					((OBTeamMembership) tm[i]).setTeamTypeMembership(ttm[i]);
				}
				team.setTeamMemberships(tm);
			}

			// set form fields on the team...
			team.setAbbreviation(aForm.getTeamName());
			team.setDescription(aForm.getTeamDesc());
			team.setCountryCodes(aForm.getCtryCode());
			team.setOrganisationCodes(aForm.getOrgCode());
			team.setSegmentCodes(aForm.getSgmtCode());
			team.setCMSSegmentCodes(aForm.getSgmtCodeCMS());
			team.setOrgGroupCode(aForm.getOrgGroupCode());

			// set updated members on the team...
			// todo add remove list filter code here....
			if ((event.equals("remove_team_users_for_add") || event.equals("remove_team_user_for_add_reject"))
					&& ((aForm.getRmUser() != null) && (aForm.getRmUser().length > 0))) {
				String membershipTypeId = (String) map.get("memTypeId");
				removeUsers(team, aForm.getRmUser(), membershipTypeId);
			}
			// DefaultLogger.debug(this, " >>bizstructure in mapper<< " + team);
			return team;
		}
		else if (event.equals("add_team_users_for_add") || event.equals("add_team_users_for_add_reject")
				|| event.equals("add_team_users_for_edit") || event.equals("add_team_users_for_edit_reject")) {
			OBTeam team = (OBTeam) map.get("mapTeam");// todo - get from
														// session..
			List memList = (List) map.get("addUserList");// todo - user list is
															// supposed to be in
															// memory by this
															// time
			long memshipTypeId = Long.parseLong((String) map.get("memshipTypeId"));// todo
																					// Get
																					// the
																					// memship
																					// id
																					// set
																					// while
																					// clicking
																					// on
																					// add
																					// button
																					// .
																					// .
																					// either
																					// from
																					// session
																					// or
																					// request
																					// .
																					// .
			DefaultLogger.debug(this, " >>Team 111 in mapper<< " + memshipTypeId + memList.size());
			ITeamType tt = team.getTeamType();
			ITeamTypeMembership[] ttm = tt.getTeamTypeMemberships();
			ITeamMembership[] itm = team.getTeamMemberships();
			ITeamMembership[] tm = new ITeamMembership[itm.length];
			for (int i = 0; i < tm.length; i++) {
				tm[i] = new OBTeamMembership();
				AccessorUtil.copyValue(itm[i], tm[i]);
			}
			if (BizStructureHelper.allowMakerCheckerSameUser()) {
				for (int i = 0; i < tm.length; i++) {// remove duplicate recs...
					if (tm[i].getTeamTypeMembership().getMembershipType().getMembershipTypeID() == memshipTypeId) {
						ITeamMember[] tms = tm[i].getTeamMembers();
						if (tms != null) {
							for (int j = 0; j < tms.length; j++) {
								for (int k = 0; k < memList.size(); k++) {
									if (((ICommonUser) memList.get(k)).getUserID() == (tms[j].getTeamMemberUser()
											.getUserID())) {
										memList.remove(k);
									}
								}
							}
						}
					}
				}
			}
			else {
				for (int i = 0; i < tm.length; i++) {// remove duplicate recs...
					ITeamMember[] tms = tm[i].getTeamMembers();
					if (tms != null) {
						for (int j = 0; j < tms.length; j++) {
							for (int k = 0; k < memList.size(); k++) {
								if (((ICommonUser) memList.get(k)).getUserID() == (tms[j].getTeamMemberUser()
										.getUserID())) {
									memList.remove(k);
								}
							}
						}
					}
				}
			}
			ITeamMember[] otm = new ITeamMember[memList.size()];
			for (int i = 0; i < memList.size(); i++) {
				otm[i] = new OBTeamMember();
				((OBTeamMember) otm[i]).setTeamMemberUser((ICommonUser) memList.get(i));
			}
			for (int i = 0; i < ttm.length; i++) {
				if (tm[i].getTeamTypeMembership().getMembershipType().getMembershipTypeID() == memshipTypeId) {
					ITeamMember[] oldTM = tm[i].getTeamMembers();
					if (oldTM != null) {
						ITeamMember[] newTM = new ITeamMember[oldTM.length + otm.length];
						for (int j = 0; j < oldTM.length; j++) {
							newTM[j] = oldTM[j];
						}
						for (int j = oldTM.length, k = 0; j < newTM.length; j++, k++) {
							newTM[j] = otm[k];
						}
						((OBTeamMembership) tm[i]).setTeamMembers(newTM);
					}
					else {
						((OBTeamMembership) tm[i]).setTeamMembers(otm);
					}
					break;
				}
			}
			team.setTeamMemberships(tm);
			return team;
		}
		return null;
	}

	/**
	 * This method is used to map data from OB to the form and to return the
	 * form.
	 * 
	 * @param cForm is of type CommonForm
	 * @param obj is of type Object
	 * @return Object
	 */
	public CommonForm mapOBToForm(CommonForm cForm, Object obj, HashMap map) throws MapperException {
		DefaultLogger.debug(this, "inside mapOb to form ");
		MaintainTeamForm aForm = (MaintainTeamForm) cForm;
		DefaultLogger.debug(this, "Going out of mapOb to form ");
		return aForm;
	}

	private ITeamType getTeamTypeFromList(HashMap map) {
		List teamTypeList = (List) map.get("TeamTypeList");
		String teamTypeId = (String) map.get("teamTypeId");
		ITeamType teamType = null;
		for (int i = 0; i < teamTypeList.size(); i++) {
			teamType = (ITeamType) teamTypeList.get(i);
			if (Long.parseLong(teamTypeId) == teamType.getTeamTypeID()) {
				return teamType;
			}
		}
		return teamType;
	}

	private void removeUsers(OBTeam team, String[] users, String membershipTypeId) {
		List userList = Arrays.asList(users);
		ITeamMembership[] tmi = team.getTeamMemberships();
		OBTeamMembership[] tm = new OBTeamMembership[tmi.length];
		for (int i = 0; i < tm.length; i++) {
			tm[i] = new OBTeamMembership();
			AccessorUtil.copyValue(tmi[i], tm[i]);
		}
		for (int i = 0; i < tm.length; i++) {
			if (tm[i].getTeamTypeMembership().getMembershipType().getMembershipTypeID() == Long
					.parseLong(membershipTypeId)) {
				ArrayList remainingUsers = new ArrayList();
				ITeamMember[] imems = tm[i].getTeamMembers();
				if ((imems != null) && (imems.length > 0)) {
					for (int j = 0; j < imems.length; j++) {
						if (!userList.contains(String.valueOf(imems[j].getTeamMemberUser().getUserID()))) {
							remainingUsers.add(imems[j]);
						}
					}
				}
				OBTeamMember[] remainingMembers = (OBTeamMember[]) remainingUsers
						.toArray(new OBTeamMember[remainingUsers.size()]);
				tm[i].setTeamMembers(remainingMembers);
			}
		}
		team.setTeamMemberships(tm);
	}
}
