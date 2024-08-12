/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:
 */

package com.integrosys.cms.ui.user;

import java.rmi.RemoteException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.integrosys.base.businfra.search.SearchDAOException;
import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.techinfra.dbsupport.DBConnectionException;
import com.integrosys.base.techinfra.dbsupport.DBUtil;
import com.integrosys.base.techinfra.dbsupport.NoSQLStatementException;
import com.integrosys.base.techinfra.exception.EntityNotFoundException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.systemBankBranch.bus.ISystemBankBranch;
import com.integrosys.cms.app.systemBankBranch.proxy.ISystemBankBranchProxyManager;
import com.integrosys.cms.app.user.proxy.CMSUserProxy;
import com.integrosys.component.notification.bus.OBNotificationRecipient;
import com.integrosys.component.user.app.bus.ICommonUser;
import com.integrosys.component.user.app.trx.ICommonUserTrxValue;

/**
 * Command class to add the new user by admin maker on the corresponding
 * event...
 * @author $Author: ravi $<br>
 * @version $Revision: 1.2 $
 * @since $Date: 2003/08/10 10:27:03 $ Tag: $Name: $
 */
public class CheckerReadUserCmd extends AbstractCommand implements ICommonEventConstant {
	/**
	 * Default Constructor
	 */
	public CheckerReadUserCmd() {
	}

	/**
	 * Defines an two dimensional array with the parameter list to be passed to
	 * the doExecute method by a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getParameterDescriptor() {
		return (new String[][] { { "TrxId", "java.lang.String", REQUEST_SCOPE }, 
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
		return (new String[][] { { "CommonUserTrxValue", "com.integrosys.cms.app.user.trx.OBUserTrxValue",
				SERVICE_SCOPE },
				{ "branchObj", "com.integrosys.cms.app.systemBankBranch.bus.OBSystemBankBranch",SERVICE_SCOPE },
				{ "CommonUser", "com.integrosys.component.user.app.bus.OBCommonUser",
					SERVICE_SCOPE },
					{ "CommonUser", "com.integrosys.component.user.app.bus.OBCommonUser",
						FORM_SCOPE },	
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
		String trxId = (String) map.get("TrxId");
		try {
			CMSUserProxy proxy = new CMSUserProxy();
			ICommonUserTrxValue userTrxVal = proxy.getUser(Long.parseLong(trxId.trim()));
			ICommonUser user=userTrxVal.getStagingUser();
			
			String isUnlock=getIsUnlock(user);
			resultMap.put("isUnlock", isUnlock);
			
			String branchCode= user.getEjbBranchCode();
        	ISystemBankBranchProxyManager systemBankBranchProxyManager=(ISystemBankBranchProxyManager) BeanHouse.get("systemBankBranchProxy");
        	 SearchResult searchResult = systemBankBranchProxyManager.getAllActualBranch();
        	 List branchList= new ArrayList();
			 List idList = (List) searchResult.getResultList();
			for (int i = 0; i < idList.size(); i++) {
				ISystemBankBranch bankBranch = (ISystemBankBranch) idList.get(i);
				if(branchCode.equals(bankBranch.getSystemBankBranchCode())){
					
					resultMap.put("branchObj", bankBranch);
					break;
				}
			}
        	
			resultMap.put("CommonUserTrxValue", userTrxVal);
			resultMap.put("CommonUser", user);
		}
		catch (RemoteException e) {
			DefaultLogger.debug(this, "got exception in doExecute" + e);
			DefaultLogger.error(this, e);
			throw (new CommandProcessingException(e.getMessage()));
		}
		catch (EntityNotFoundException e) {
			DefaultLogger.debug(this, "got exception in doExecute" + e);
			DefaultLogger.error(this, e);
			throw (new CommandProcessingException(e.getMessage()));
		}
		catch (Exception e) {
			DefaultLogger.debug(this, "got exception in doExecute" + e);
			DefaultLogger.error(this, e);
			throw (new CommandProcessingException(e.getMessage()));
		}
		DefaultLogger.debug(this, "Going out of doExecute()");
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		return returnMap;
	}
	
	private String getIsUnlock(ICommonUser user) {
		String query="SELECT IS_UNLOCK, STATUS FROM STAGE_USER WHERE USER_ID="+user.getUserID()+"";
		DBUtil dbUtil = null;
		try {
			dbUtil = new DBUtil();
			dbUtil.setSQL(query.toString());
			ResultSet rs = dbUtil.executeQuery();
			while (rs.next()) {
				String isUnlock=rs.getString("IS_UNLOCK");
				String status=rs.getString("STATUS");
				if("Y".equals(isUnlock) && "A".equals(status))
					return "Y";
			}
			return "";
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
