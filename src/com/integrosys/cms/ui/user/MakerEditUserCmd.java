/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:
 */

package com.integrosys.cms.ui.user;

import java.rmi.RemoteException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import org.apache.struts.action.ActionMessage;

import com.integrosys.base.businfra.search.SearchDAOException;
import com.integrosys.base.techinfra.dbsupport.DBConnectionException;
import com.integrosys.base.techinfra.dbsupport.DBUtil;
import com.integrosys.base.techinfra.dbsupport.NoSQLStatementException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.transaction.OBCMSTrxResult;
import com.integrosys.cms.app.transaction.OBTrxContext;
import com.integrosys.cms.app.user.proxy.CMSUserProxy;
import com.integrosys.cms.app.user.trx.OBUserTrxValue;
import com.integrosys.component.bizstructure.app.bus.OBTeamTypeMembership;
import com.integrosys.component.common.transaction.ICompTrxResult;
import com.integrosys.component.common.transaction.OBCompTrxResult;
import com.integrosys.component.notification.bus.OBNotificationRecipient;
import com.integrosys.component.user.app.bus.OBCommonUser;
import com.integrosys.component.user.app.bus.UserException;

/**
 * Command class to add the new user by admin maker on the corresponding
 * event...
 * @author $Author: ravi $<br>
 * @version $Revision: 1.4 $
 * @since $Date: 2003/08/28 04:35:25 $ Tag: $Name: $
 */
public class MakerEditUserCmd extends AbstractCommand implements ICommonEventConstant {
	/**
	 * Default Constructor
	 */
	public MakerEditUserCmd() {
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
				{ "CommonUserTrxValue", "com.integrosys.cms.app.user.trx.OBUserTrxValue", SERVICE_SCOPE },
				{ "teamTypeMembershipId", "java.lang.String", REQUEST_SCOPE },
				{ "theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE },
				{ "formObj", "com.integrosys.cms.ui.user.MaintainUserForm", "FORM_SCOPE" },
				{ "isUnlock", "java.lang.String", REQUEST_SCOPE }
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
		return (new String[][] { { "request.ITrxResult", "com.integrosys.component.common.transaction.ICompTrxResult",
				REQUEST_SCOPE },
			{ "isUnlock", "java.lang.String", REQUEST_SCOPE }
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
		OBUserTrxValue userTrxVal = (OBUserTrxValue) map.get("CommonUserTrxValue");
		String isUnlock=(String)map.get("isUnlock");
		System.out.println("<<<<<<<<<<<<<<<<<<<IS UNLOCKED>>>>>>>>>>>"+isUnlock);
		resultMap.put("isUnlock", isUnlock);
		/*Added by Abhijit R for HDFC -  start*/
		/*To Do: Need to set this also in mapper instead of doing here*/
		String tteamTypeMembershipId = (String) map.get("teamTypeMembershipId");
		
		if(tteamTypeMembershipId==null||tteamTypeMembershipId.trim().equals("")){
			exceptionMap.put("teamTypeMembershipIdError", new ActionMessage("error.string.mandatory"));
			ICompTrxResult trxResult = null;
			resultMap.put("request.ITrxResult", trxResult);
			returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
			returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
			return returnMap;
			
		}else{
			long teamTypeMembershipId = Long.parseLong((String) map.get("teamTypeMembershipId"));
		OBTeamTypeMembership teamTypeMembership = new OBTeamTypeMembership();
		teamTypeMembership.setMembershipID(teamTypeMembershipId);
		user.setTeamTypeMembership(teamTypeMembership);
		/*Added by Abhijit R for HDFC - End*/
		try {
			CMSUserProxy proxy = new CMSUserProxy();
			ICompTrxResult trxResult = proxy.makerUpdateUser(trxContext, userTrxVal, user);
			resultMap.put("request.ITrxResult", trxResult);
			
			OBCompTrxResult ob=(OBCompTrxResult)trxResult;
			OBUserTrxValue obuser=((OBUserTrxValue)ob.getTrxValue());
			System.out.println(">>>>>>>USER ID<<<<<<<"+obuser.getStagingUser().getUserID());
			if("Y".equals(isUnlock)) {
				System.out.println(">>>>>>>Y.equals(isUnlock)<<<<<<<"+obuser.getStagingUser().getUserID());
				updateUnlock(obuser.getStagingUser().getUserID());
			}
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
	
	private void updateUnlock(long userId) {
		String query="UPDATE STAGE_USER SET IS_UNLOCK = 'Y' WHERE USER_ID="+userId+"";
		System.out.println(">>>>>>>updateUnlock<<<<<<<"+query);
		StringBuffer strBuffer = new StringBuffer(query.trim());
		
		DBUtil dbUtil = null;
		try {
			dbUtil = new DBUtil();
			dbUtil.setSQL(strBuffer.toString());
			ResultSet rs = dbUtil.executeQuery();
			dbUtil.commit();
			System.out.println(">>>>>>>ResultSet<<<<<<<"+rs.toString());
		}
		catch (DBConnectionException dbe) {
			throw new SearchDAOException(dbe);
		}
		catch (NoSQLStatementException ne) {
			throw new SearchDAOException(ne);
		}
		catch (SQLException se) {
			throw new SearchDAOException(se);
		}
		finally {
			try {
				dbUtil.close();
			}
			catch (SQLException e) {
				throw new SearchDAOException(e);
			}
		}
	}
}
