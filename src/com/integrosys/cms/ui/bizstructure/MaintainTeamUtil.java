package com.integrosys.cms.ui.bizstructure;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.StringTokenizer;

import org.apache.commons.lang.StringUtils;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.propertyfile.PropertyManager;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.ui.common.CountryList;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;
import com.integrosys.component.bizstructure.app.bus.ITeam;
import com.integrosys.component.bizstructure.app.bus.ITeamMember;
import com.integrosys.component.bizstructure.app.bus.OBTeamSearchCriteria;

public class MaintainTeamUtil {

	public static ArrayList getCountryNameList(String[] ctyCodeArray) {
		if ((ctyCodeArray == null) || (ctyCodeArray.length == 0)) {
			return new ArrayList();
		}
		ArrayList ctyNameList = new ArrayList();
		Map ctyCodeMap = CountryList.getInstance().getCountryList();
		for (int index = 0; index < ctyCodeArray.length; index++) {
			String label = (String) ctyCodeMap.get(ctyCodeArray[index]);
			if (label != null) {
				ctyNameList.add(label);
			}
			else {
				ctyNameList.add("-");
			}
		}
		return ctyNameList;
	}

	public static boolean isSuperUser(String loginID) {
		if (loginID == null) {
			return false;
		}
		String superUsers = PropertyManager.getValue("integrosys.super_users");
		StringTokenizer tokenizer = new StringTokenizer(superUsers, ",");
		while (tokenizer.hasMoreTokens()) {
			if (loginID.equalsIgnoreCase(tokenizer.nextToken())) {
				return true;
			}
		}
		return false;
	}
	
	public static boolean hasSuperUserConcept() {
		return StringUtils.isNotEmpty(PropertyManager.getValue("integrosys.super_users"));
	}
	
	public static boolean hasSuperUser(ITeam team) {
		if (team == null)
			return false;
		
		if (team.getTeamMemberships() != null) {
			for (int i = 0; i < team.getTeamMemberships().length; i++) {
				ITeamMember[] members = team.getTeamMemberships()[i].getTeamMembers();
				if (members != null){
					for (int j = 0; j < members.length; j++) {
						
						if (isSuperUser(members[j].getTeamMemberUser().getLoginID())) 
							return true;
					}
				}
			}	
		}
		return false;
	}

	public static boolean isTeamEditable(ITeam loginUserTeam, ITeam aUserTeam) {
		if ((loginUserTeam == null) || (aUserTeam == null)) {
			return false;
		}
		boolean isEditable = isParentChildArray(loginUserTeam.getCountryCodes(), aUserTeam.getCountryCodes());
		if (isEditable) {
			isEditable = isParentChildArray(loginUserTeam.getOrganisationCodes(), aUserTeam.getOrganisationCodes());
		}
		if (isEditable) {
			isEditable = isParentChildArray(loginUserTeam.getSegmentCodes(), aUserTeam.getSegmentCodes());
		}
		return isEditable;
	}

	public static boolean isParentChildArray(String[] parentArray, String[] childArray) {
		if (parentArray == null) {
			return false;
		}
		if (childArray == null) {
			return true;
		}
		Arrays.sort(parentArray);
		for (int index = 0; index < childArray.length; index++) {
			if (-1 == Arrays.binarySearch(parentArray, childArray[index])) {
				return false;
			}
		}
		return true;
	}
	
	public static boolean hasTeamTypeAccess(String loginID, String teamTypeID) {
		if (teamTypeID == null) 
			return false;
		
		if (isSuperUser(loginID))
			return true;		
		
		StringTokenizer strToken = new StringTokenizer(PropertyManager.getValue("integrosys.local.users.access"), ",");
		if (strToken.countTokens() == 0)
			return true;
		
		while (strToken.hasMoreTokens()) {
			String type = strToken.nextToken();
			if (teamTypeID.equals(type))
				return true;
		}
		return false;		
	}
}
