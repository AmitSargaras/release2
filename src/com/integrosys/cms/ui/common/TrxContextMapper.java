/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:
 */

package com.integrosys.cms.ui.common;

import java.util.HashMap;
import java.util.List;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.exception.MapperException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.customer.bus.ICMSCustomer;
import com.integrosys.cms.app.limit.bus.ILimitProfile;
import com.integrosys.cms.app.transaction.OBTrxContext;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;
import com.integrosys.cms.ui.login.CMSGlobalSessionConstant;
import com.integrosys.component.bizstructure.app.bus.ITeam;
import com.integrosys.component.bizstructure.app.bus.ITeamMembership;
import com.integrosys.component.user.app.bus.ICommonUser;

/**
 * This Mapper class maps the TrxContextForm to the OBTrxContext object..
 * @author $Author: sathish $<br>
 * @version $Revision: 1.7 $
 * @since $Date: 2003/08/07 12:50:12 $ Tag: $Name: $
 */
public class TrxContextMapper extends AbstractCommonMapper {
	/**
	 * Default Construtor
	 */
	public TrxContextMapper() {
	}

	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ IGlobalConstant.USER, "com.integrosys.component.user.app.bus.ICommonUser", GLOBAL_SCOPE },
				{ IGlobalConstant.USER_TEAM, "com.integrosys.component.bizstructure.app.bus.ITeam", GLOBAL_SCOPE },
				{ IGlobalConstant.GLOBAL_LIMITPROFILE_OBJ, "com.integrosys.cms.app.limit.bus.ILimitProfile",
						GLOBAL_SCOPE },
				{ IGlobalConstant.GLOBAL_CUSTOMER_OBJ, "com.integrosys.cms.app.customer.bus.ICMSCustomer", GLOBAL_SCOPE },
				{ CMSGlobalSessionConstant.TEAM_TYPE_MEMBERSHIP_ID, "java.lang.String", GLOBAL_SCOPE },
				{ CMSGlobalSessionConstant.TEAM_FUNTION, "java.util.List", GLOBAL_SCOPE } });
	}

	/**
	 * This method is used to map the Form values into Corresponding OB Values
	 * and returns the same.
	 * 
	 * @param cForm is of type CommonForm
	 * @return Object
	 */
	public Object mapFormToOB(CommonForm cForm, HashMap map) throws MapperException {
		DefaultLogger.debug(this, "Inside mapForm to OB ");
		TrxContextForm aForm = (TrxContextForm) cForm;
		ICommonUser user = (ICommonUser) map.get(IGlobalConstant.USER);
		ITeam team = (ITeam) map.get(IGlobalConstant.USER_TEAM);
		ICMSCustomer customer = (ICMSCustomer) map.get(IGlobalConstant.GLOBAL_CUSTOMER_OBJ);
		ILimitProfile profile = (ILimitProfile) map.get(IGlobalConstant.GLOBAL_LIMITPROFILE_OBJ);
		List teamFunctionGroupList = (List) map.get(CMSGlobalSessionConstant.TEAM_FUNTION);

		String teamMemIDStr = (String) map.get(CMSGlobalSessionConstant.TEAM_TYPE_MEMBERSHIP_ID);
		long teamMembershipID = ICMSConstant.LONG_INVALID_VALUE;
		if ((teamMemIDStr != null) && (teamMemIDStr.trim().length() > 0)) {
			teamMembershipID = Long.parseLong(teamMemIDStr);
		}

		OBTrxContext context = new OBTrxContext(user, team);
		context.setCustomer(customer);
		context.setLimitProfile(profile);
		context.setTeamMembershipID(getTeamMembershipIDFromTeam(teamMembershipID, team));
		context.setFunctionGroupList(teamFunctionGroupList);

		context.setRemarks(aForm.getRemarks());
		return context;
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
		return null;// this method is not supposed to be used..
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
}
