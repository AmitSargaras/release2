/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:
 */

package com.integrosys.cms.ui.user;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.ejb.CreateException;

import org.apache.struts.action.ActionMessage;

import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.base.techinfra.exception.EntityNotFoundException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.systemBankBranch.trx.ISystemBankBranchTrxValue;
import com.integrosys.cms.app.transaction.OBTrxContext;
import com.integrosys.cms.app.user.bus.StdUserDAO;
import com.integrosys.cms.app.user.proxy.CMSUserProxy;
import com.integrosys.cms.app.user.trx.OBUserTrxValue;
import com.integrosys.component.bizstructure.app.bus.OBTeamTypeMembership;
import com.integrosys.component.bizstructure.app.constants.BizstructureJNDIConstant;
import com.integrosys.component.common.transaction.ICompTrxResult;
import com.integrosys.component.user.app.bus.EBCommonUserHome;
import com.integrosys.component.user.app.bus.EBCommonUserSegmentHome;
import com.integrosys.component.user.app.bus.ICommonUser;
import com.integrosys.component.user.app.bus.OBCommonUser;
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
public class BroadCastMessageCmd extends AbstractCommand implements ICommonEventConstant {
	/**
	 * Default Constructor
	 */
	public static String CREATE_ACTIVE_USER = "integrosys.los.smeuser.create.active.user";

	public BroadCastMessageCmd() {
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
				{ "teamTypeMembershipId", "java.lang.String", REQUEST_SCOPE },
				
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
		return (new String[][] {
				{ "request.ITrxResult", "com.integrosys.component.common.transaction.ICompTrxResult", REQUEST_SCOPE },
				{ "Error_EmpId", "java.lang.String", REQUEST_SCOPE },
				//{ "UserList", "java.util.List", REQUEST_SCOPE },
				//{ "UserCount", "java.lang.String", REQUEST_SCOPE },
				{ "resultString", "java.lang.String", REQUEST_SCOPE },
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
		HashMap exceptionMap = new HashMap();
		OBCommonUser user = (OBCommonUser) map.get("CommonUser");
		
		OBTrxContext trxContext = (OBTrxContext) map.get("theOBTrxContext");
		String event = (String) map.get("event");
		String status = (String) map.get("status");
		String tteamTypeMembershipId =(String) map.get("teamTypeMembershipId");
		
		CMSUserProxy proxy = new CMSUserProxy();
		ICommonUser commonUser=null;
		StdUserDAO  stdUserDAO = new StdUserDAO();
		List  list = new ArrayList();
		StringBuffer resultString = new StringBuffer ();
		resultString = (StringBuffer)stdUserDAO.BroadCastMessageDetail();
		/*stdUserDAO.killLoginUserDetail();
		
		list = (List)stdUserDAO.getLoginUserDetail();
		
		long count = stdUserDAO.getLoginUserCount();
		resultMap.put("UserList", list);
		resultMap.put("UserCount", String.valueOf(count));*/
		resultMap.put("resultString", resultString);
		DefaultLogger.debug(this, " -------- Create Successfull -----------");
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		return returnMap;	
	
	}	
}
