/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:
 */

package com.integrosys.cms.ui.user;

import java.rmi.RemoteException;
import java.util.HashMap;

import javax.ejb.CreateException;

import org.apache.struts.action.ActionMessage;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.base.techinfra.exception.EntityNotFoundException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.systemBankBranch.trx.ISystemBankBranchTrxValue;
import com.integrosys.cms.app.transaction.OBTrxContext;
import com.integrosys.cms.app.user.bus.StdUserSearchCriteria;
import com.integrosys.cms.app.user.proxy.CMSStdUserProxyFactory;
import com.integrosys.cms.app.user.proxy.CMSUserProxy;
import com.integrosys.cms.app.user.trx.OBUserTrxValue;
import com.integrosys.component.bizstructure.app.bus.OBTeamTypeMembership;
import com.integrosys.component.bizstructure.app.constants.BizstructureJNDIConstant;
import com.integrosys.component.common.transaction.ICompTrxResult;
import com.integrosys.component.user.app.bus.CommonUserSearchCriteria;
import com.integrosys.component.user.app.bus.EBCommonUserHome;
import com.integrosys.component.user.app.bus.EBCommonUserSegmentHome;
import com.integrosys.component.user.app.bus.ICommonUser;
import com.integrosys.component.user.app.bus.OBCommonUser;
import com.integrosys.component.user.app.bus.OBCommonUserSearchCriteria;
import com.integrosys.component.user.app.bus.OBCommonUserSegment;
import com.integrosys.component.user.app.bus.UserException;
import com.integrosys.component.user.app.constant.JNDIConstant;
import com.integrosys.component.user.app.constant.UserConstant;

/**
 * Command class to add the new user by admin maker on the corresponding
 * event...
 * @author $Author: pooja $<br>
 * @version $Revision: 1.6 $
 * @since $Date: 2004/07/12 07:52:39 $ Tag: $Name: $
 */
public class MakerAddUserCmd extends AbstractCommand implements ICommonEventConstant {
	/**
	 * Default Constructor
	 */
	public static String CREATE_ACTIVE_USER = "integrosys.los.smeuser.create.active.user";

	public MakerAddUserCmd() {
	}

	/**
	 * Defines an two dimensional array with the parameter list to be passed to
	 * the doExecute method by a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getParameterDescriptor() {
		return (new String[][] { { "CommonUser", "com.integrosys.cms.app.user.bus.OBCMSUser", FORM_SCOPE },
				{ "theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE },
				{ "CommonUserTrxValue", "com.integrosys.cms.app.user.trx.OBUserTrxValue", SERVICE_SCOPE },
				{ "event", "java.lang.String", REQUEST_SCOPE }, { "empId", "java.lang.String", REQUEST_SCOPE },
				{ "status", "java.lang.String", REQUEST_SCOPE }, { "teamTypeMembership", "java.lang.String", REQUEST_SCOPE },
				{ "teamTypeMembershipId", "java.lang.String", REQUEST_SCOPE }, });
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
				{ "request.ITrxResult", "com.integrosys.component.common.transaction.ICompTrxResult", REQUEST_SCOPE },
				{ "Error_EmpId", "java.lang.String", REQUEST_SCOPE }, });
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
		HashMap exceptionMap = new HashMap();
		OBCommonUser user = (OBCommonUser) map.get("CommonUser");
		
		OBTrxContext trxContext = (OBTrxContext) map.get("theOBTrxContext");
		String event = (String) map.get("event");
		String status = (String) map.get("status");
		String tteamTypeMembershipId =(String) map.get("teamTypeMembershipId");
		boolean existingUser=true;
		CMSUserProxy proxy = new CMSUserProxy();
		ICommonUser commonUser=null;
		/*Added by archana for HDFC -  start*/
		/*To Do: Need to set this also in mapper instead of doing here*/
		if(user.getLoginID()!=null && !(user.getLoginID().trim().equals(""))){
			try {
				CommonUserSearchCriteria criteria= new CommonUserSearchCriteria();
				StdUserSearchCriteria  criteriaOB= new StdUserSearchCriteria();
				criteriaOB.setEmployeeId(user.getEmployeeID().trim());
				criteriaOB.setLoginId(user.getLoginID().trim());
				criteria.setCriteria(criteriaOB);
				criteria.setNItems(10);
				SearchResult searchResult= CMSStdUserProxyFactory.getUserProxy().searchUsers(criteria);
				
				if(searchResult !=null){
					if(searchResult.getNItems()==0){
						existingUser=false;
					}
				}else{
					existingUser=false;
				}
				 //commonUser= proxy.getUser(user.getLoginID());
			} catch (EntityNotFoundException e) {
				DefaultLogger.debug(this, "Duplicate user not found");
			} catch (RemoteException e) {
				DefaultLogger.debug(this, "Duplicate user not found");
			}
		}
		
		if(existingUser){
			exceptionMap.put("loginIDDuplicateError", new ActionMessage("error.string.duplicateLoginId"));
			ICompTrxResult trxResult = null;
			resultMap.put("request.ITrxResult", trxResult);
			returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
			returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
			return returnMap;
			
		}else if(tteamTypeMembershipId==null||tteamTypeMembershipId.trim().equals("")){
			exceptionMap.put("teamTypeMembershipIdError", new ActionMessage("error.string.mandatory"));
			ICompTrxResult trxResult = null;
			resultMap.put("request.ITrxResult", trxResult);
			returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
			returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
			return returnMap;
			
		}else{
			long teamTypeMembershipId = Long.parseLong(tteamTypeMembershipId);
		OBTeamTypeMembership teamTypeMembership = new OBTeamTypeMembership();
		teamTypeMembership.setMembershipID(teamTypeMembershipId);
		user.setTeamTypeMembership(teamTypeMembership);
		/*Added by archana for HDFC - End*/
		if ("maker_add_user".equals(event)) {
			user.setStatus(UserConstant.STATUS_NEW);
		}
		if ((status != null) && !status.equals("")) {
			user.setStatus(status);
		}

		// Validation of Employee ID not needed for Maybank as they are not
		// providing the employee ID list
		// String empId = (String)map.get("empId");
		OBUserTrxValue trxVal = new OBUserTrxValue();
		if (event.equals("maker_edit_reject_add")) {
			trxVal = (OBUserTrxValue) map.get("CommonUserTrxValue");
		}
		//not required this code for HDFC bank
		/*trxVal.setOriginatingCountry((trxContext.getTeam().getCountryCodes())[0]);
		
		if (trxContext.getTeam().getOrganisationCodes() != null && 
				trxContext.getTeam().getOrganisationCodes().length > 0) {
			trxVal.setOriginatingOrganisation((trxContext.getTeam().getOrganisationCodes())[0]);
		}*/
		// DefaultLogger.debug(this,
		// "...............ctry and org  = "+(trxContext
		// .getTeam().getCountryCodes
		// ())[0]+" "+(trxContext.getTeam().getOrganisationCodes())[0]);
		try {
		
			ICompTrxResult trxResult = proxy.makerCreateUser(trxContext, trxVal, user);
						resultMap.put("request.ITrxResult", trxResult);

		}
		catch (RemoteException e) {
			DefaultLogger.debug(this, "got exception in doExecute" + e);
			e.printStackTrace();
			throw (new CommandProcessingException(e.getMessage()));
		}
		catch (UserException e) {
			DefaultLogger.debug(this, "got exception in doExecute" + e);
			e.printStackTrace();
			throw (new CommandProcessingException(e.getMessage()));
		} 
		DefaultLogger.debug(this, "Going out of doExecute()");
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		return returnMap;
	}
	}
		
}
