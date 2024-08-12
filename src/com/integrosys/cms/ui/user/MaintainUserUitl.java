/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:
 */

package com.integrosys.cms.ui.user;

import java.util.StringTokenizer;

import com.integrosys.base.techinfra.propertyfile.PropertyManager;
import com.integrosys.cms.ui.bizstructure.MaintainTeamUtil;
import com.integrosys.component.bizstructure.app.bus.ITeam;
import com.integrosys.cms.app.user.bus.StdUserSearchCriteria;
import com.integrosys.component.user.app.bus.CommonUserSearchCriteria;

import java.util.ArrayList;

/**
 * Command class to get the list of documents based on the document type set on
 * the search criteria
 * @author $Author: ravi $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2003/07/30 07:49:56 $ Tag: $Name: $
 */
public class MaintainUserUitl {
	public static CommonUserSearchCriteria createEmptySearchCriteria() {
		CommonUserSearchCriteria sc = new CommonUserSearchCriteria();
		sc.setStartIndex(0);
		sc.setNItems(10);
		sc.setFirstSort(MaintainUserAction.FIRST_SORT);
		sc.setSecondSort(MaintainUserAction.SECOND_SORT);
		StdUserSearchCriteria obsc = new StdUserSearchCriteria();
		sc.setCriteria(obsc);
		return sc;
	}

	public static CommonUserSearchCriteria createEmptySearchCriteria(String userID, ITeam userTeam) {
		CommonUserSearchCriteria criteria = createEmptySearchCriteria();
		if (userTeam != null) {
			setTeamCriteria(criteria, userID, userTeam);
		}
		return criteria;
	}

	public static void setTeamCriteria(CommonUserSearchCriteria criteria, String userID, ITeam userTeam) {
		if (!MaintainTeamUtil.isSuperUser(userID)) {
			StdUserSearchCriteria ob = (StdUserSearchCriteria)criteria.getCriteria();
			ob.setTeamID(userTeam.getTeamID());
			ob.setMaintainTeam(userTeam);
			
			StringTokenizer strToken = new StringTokenizer(PropertyManager.getValue("integrosys.local.users.access"), ",");
			if (strToken.countTokens() > 0) {		
				ArrayList typeList = new ArrayList();
				while (strToken.hasMoreTokens()) {
					typeList.add(new Integer(strToken.nextToken()));
				}
				ob.setTeamTypeList(typeList);
			}
			
			criteria.setCriteria(ob);
		}
	}

}
