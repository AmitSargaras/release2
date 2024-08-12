/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:
 */

package com.integrosys.cms.ui.userrole;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import com.integrosys.base.techinfra.exception.EntityNotFoundException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.component.bizstructure.app.bus.ITeamType;
import com.integrosys.component.bizstructure.app.bus.ITeamTypeMembership;

/**
 * Command class to get the list of documents based on the document type set on
 * the search criteria
 * @author $Author: czhou $<br>
 * @version $Revision: 1.3 $
 * @since $Date: 2006/01/19 09:41:16 $ Tag: $Name: $
 */
public class ListTeamTypesCmd extends AbstractUserRoleCommand implements ICommonEventConstant {

	/**
	 * Default Constructor
	 */
	public ListTeamTypesCmd() {
	}

	/**
	 * Defines an two dimensional array with the parameter list to be passed to
	 * the doExecute method by a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getParameterDescriptor() {
		return (new String[][] {});
	}

	/**
	 * Defines an two dimensional array with the result list to be expected as a
	 * result from the doExecute method using a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getResultDescriptor() {
		return (new String[][] { { "memberTypeId","java.lang.String", REQUEST_SCOPE },
				{ "TeamTypeList", "java.util.ArrayList", SERVICE_SCOPE },
				{ "moduleList", "java.util.ArrayList", REQUEST_SCOPE } });
	}

	/**
	 * This method does the Business operations with the HashMap and put the
	 * results back into the HashMap.Here creation for Company Borrower is done.
	 * 
	 * @param map is of type HashMap
	 * @return HashMap with the Result
	 */
	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
		HashMap returnMap = new HashMap();
		HashMap resultMap = new HashMap();
		try {
			ITeamType[] teamType = getCmsUserRoleProxy().getNodeTeamTypes();
			Arrays.sort(teamType, new Comparator() {

				public int compare(Object thisObject, Object thatObject) {
					int thisPeerOrder = (thisObject == null) ? Integer.MAX_VALUE : ((ITeamType) thisObject)
							.getPeerOrder();
					int thatPeerOrder = (thatObject == null) ? Integer.MAX_VALUE : ((ITeamType) thatObject)
							.getPeerOrder();

					return (thisPeerOrder == thatPeerOrder) ? 0 : ((thisPeerOrder > thatPeerOrder) ? 1 : -1);
				}
			});
			List teamTypeMembershipLst = new ArrayList();
			for(int i =0;i<teamType.length;i++){
				ITeamTypeMembership[] teamTypeMembershipArr = teamType[i].getTeamTypeMemberships();
				for (int j = 0; j < teamTypeMembershipArr.length; j++) {
					teamTypeMembershipLst.add(teamTypeMembershipArr[j]);
				}
			}
			List moduleList = getCmsUserRoleProxy().getUserModuleList(Long.toString(teamType[0].getTeamTypeMemberships()[0].getMembershipID()));
			List nodeList = Arrays.asList(teamType);
			resultMap.put("memberTypeId", Long.toString(teamType[0].getTeamTypeMemberships()[0].getMembershipID()));
			resultMap.put("moduleList", moduleList);
			resultMap.put("TeamTypeList", teamTypeMembershipLst);
		}
		catch (EntityNotFoundException e) {
			CommandProcessingException cpe = new CommandProcessingException("failed to retreive all team types");
			cpe.initCause(e);
			throw cpe;
		}

		DefaultLogger.debug(this, "Going out of doExecute()");
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		return returnMap;
	}
}
