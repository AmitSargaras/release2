/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:
 */

package com.integrosys.cms.ui.userrole;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.techinfra.exception.EntityNotFoundException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.bizstructure.proxy.CMSTeamProxy;
import com.integrosys.cms.app.bizstructure.proxy.ICMSTeamProxy;
import com.integrosys.cms.app.userrole.proxy.CMSUserRoleProxy;
import com.integrosys.cms.app.userrole.proxy.ICMSUserRoleProxy;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;
import com.integrosys.component.bizstructure.app.bus.BizStructureException;
import com.integrosys.component.bizstructure.app.bus.ITeam;
import com.integrosys.component.bizstructure.app.bus.ITeamType;
import com.integrosys.component.bizstructure.app.bus.ITeamTypeMembership;
import com.integrosys.component.bizstructure.app.bus.TeamSearchCriteria;
import com.integrosys.component.user.app.bus.ICommonUser;

/**
 * Command class to get the list of documents based on the document type set on
 * the search criteria
 * @author $Author: dli $<br>
 * @version $Revision: 1.2 $
 * @since $Date: 2005/08/16 12:33:34 $ Tag: $Name: $
 */
public class MakerListUserRoleCmd extends AbstractCommand implements ICommonEventConstant {

	private ICMSUserRoleProxy cmsUserRoleProxy;

	public void setCmsUserRoleProxy(ICMSUserRoleProxy cmsUserRoleProxy) {
		this.cmsUserRoleProxy = cmsUserRoleProxy;
	}

	public ICMSUserRoleProxy getCmsUserRoleProxy() {
		return cmsUserRoleProxy;
	}

	/**
	 * Default Constructor
	 */
	public MakerListUserRoleCmd() {
	}

	/**
	 * Defines an two dimensional array with the parameter list to be passed to
	 * the doExecute method by a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getParameterDescriptor() {
		return (new String[][] { { "memberTypeId","java.lang.String", REQUEST_SCOPE },
			{ IGlobalConstant.USER, "com.integrosys.component.user.app.bus.ICommonUser", GLOBAL_SCOPE },
			{ IGlobalConstant.USER_TEAM, "com.integrosys.component.bizstructure.app.bus.ITeam", GLOBAL_SCOPE }		
		});
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
				{ "TeamTypeList", "java.util.ArrayList", REQUEST_SCOPE },
				{ "moduleList", "java.util.ArrayList", REQUEST_SCOPE }
		});
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
			ICMSUserRoleProxy proxy = new CMSUserRoleProxy();
			
			String memberTypeId = (String)map.get("memberTypeId");
			
			List moduleList = proxy.getUserModuleList(memberTypeId);
			
			List teamTypeMembershipLst = new ArrayList();
			try {
				
				ICMSTeamProxy teamProxy = new CMSTeamProxy();
				ITeamType[] teamTypearr = teamProxy.getNodeTeamTypes();
				for(int i =0;i<teamTypearr.length;i++){
					ITeamTypeMembership[] teamTypeMembershipArr = teamTypearr[i].getTeamTypeMemberships();
					for (int j = 0; j < teamTypeMembershipArr.length; j++) {
						teamTypeMembershipLst.add(teamTypeMembershipArr[j]);
					}
				}
				
			} catch (EntityNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			resultMap.put("memberTypeId", memberTypeId);
			resultMap.put("moduleList", moduleList);
			resultMap.put("TeamTypeList", teamTypeMembershipLst);
		}
		catch (Exception e) {
			CommandProcessingException cpe = new CommandProcessingException("failed to search team using criteria");
			cpe.initCause(e);
			throw cpe;
		}

		DefaultLogger.debug(this, "Going out of doExecute()");
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		return returnMap;
	}
}
