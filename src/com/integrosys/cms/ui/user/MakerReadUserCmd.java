/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:
 */

package com.integrosys.cms.ui.user;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.struts.util.LabelValueBean;

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
import com.integrosys.cms.app.bizstructure.proxy.CMSTeamProxy;
import com.integrosys.cms.app.bizstructure.proxy.ICMSTeamProxy;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.geography.city.bus.ICity;
import com.integrosys.cms.app.geography.city.proxy.ICityProxyManager;
import com.integrosys.cms.app.geography.country.bus.ICountry;
import com.integrosys.cms.app.geography.region.bus.IRegion;
import com.integrosys.cms.app.geography.state.bus.IState;
import com.integrosys.cms.app.systemBankBranch.bus.ISystemBankBranch;
import com.integrosys.cms.app.systemBankBranch.proxy.ISystemBankBranchProxyManager;
import com.integrosys.cms.app.user.bus.StdUserDAO;
import com.integrosys.cms.app.user.proxy.CMSUserProxy;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;
import com.integrosys.cms.ui.manualinput.CommonUtil;
import com.integrosys.component.bizstructure.app.bus.ITeamType;
import com.integrosys.component.bizstructure.app.bus.ITeamTypeMembership;
import com.integrosys.component.user.app.bus.ICommonUser;
import com.integrosys.component.user.app.bus.OBCommonUser;
import com.integrosys.component.user.app.trx.ICommonUserTrxValue;

/**
 * Command class to add the new user by admin maker on the corresponding
 * event...
 * @author $Author: ravi $<br>
 * @version $Revision: 1.2 $
 * @since $Date: 2003/08/10 10:27:03 $ Tag: $Name: $
 */
public class MakerReadUserCmd extends AbstractCommand implements ICommonEventConstant {
	private ICityProxyManager cityProxy;

	public ICityProxyManager getCityProxy() {
		return cityProxy;
	}

	public void setCityProxy(ICityProxyManager cityProxy) {
		this.cityProxy = cityProxy;
	}
	
	private ISystemBankBranchProxyManager systemBankBranchProxy;

	
	
	
	public ISystemBankBranchProxyManager getSystemBankBranchProxy() {
		return systemBankBranchProxy;
	}

	public void setSystemBankBranchProxy(
			ISystemBankBranchProxyManager systemBankBranchProxy) {
		this.systemBankBranchProxy = systemBankBranchProxy;
	}

	/**
	 * Default Constructor
	 */
	public MakerReadUserCmd() {
	}

	/**
	 * Defines an two dimensional array with the parameter list to be passed to
	 * the doExecute method by a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getParameterDescriptor() {
		return (new String[][] { { "UserId", "java.lang.String", REQUEST_SCOPE },
				{IGlobalConstant.GLOBAL_LOS_USER, "com.integrosys.component.user.app.bus.ICommonUser",GLOBAL_SCOPE},
				{ "event", "java.lang.String", REQUEST_SCOPE } });
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
				{ "CommonUser", "com.integrosys.component.user.app.bus.OBCommonUser",
					SERVICE_SCOPE },
					{ "CommonUser", "com.integrosys.component.user.app.bus.OBCommonUser",
						FORM_SCOPE },	
						{ "branchObj", "com.integrosys.cms.app.systemBankBranch.bus.OBSystemBankBranch",SERVICE_SCOPE },
						{ "isUnlock", "java.lang.String", REQUEST_SCOPE }
//				{ "stateList", "java.util.List", SERVICE_SCOPE },
//				{ "countryList", "java.util.List", SERVICE_SCOPE },
//				{ "regionList", "java.util.List", SERVICE_SCOPE },
//				{ "cityList", "java.util.List", SERVICE_SCOPE },
//				{ "branchList", "java.util.List", SERVICE_SCOPE },
//				{"TeamTypeMembershipLst","java.util.List",SERVICE_SCOPE},
		
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
		String userId = (String) map.get("UserId");
		String event = (String) map.get("event");
		try {
			ArrayList teamTypeMembershipLst = new ArrayList();
			CMSUserProxy proxy = new CMSUserProxy();
			// ICommonUserTrxValue userTrxVal = proxy.getUserByPK(userId);
			ICommonUserTrxValue userTrxVal = proxy.getUserByPK(userId.trim());
			ICommonUser user=userTrxVal.getUser();
			OBCommonUser globalUser = (OBCommonUser) map.get(IGlobalConstant.GLOBAL_LOS_USER);
			if(globalUser.getLoginID().equalsIgnoreCase(user.getLoginID())){
				resultMap.put("wipInvalid", "wipInvalid");
			}else{
			if (!"maker_view_user".equals(event) && !"rejected_read".equals(event)
					&& !userTrxVal.getStatus().equals(ICMSConstant.STATE_ACTIVE)) {
				resultMap.put("wip", "wip");
			}
			else {
				
				resultMap.put("CommonUserTrxValue", userTrxVal);
				resultMap.put("CommonUser", user);
			}
			String branchCode= user.getEjbBranchCode();
			if(branchCode!=null){
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
			}
			ICMSTeamProxy proxyTeam = new CMSTeamProxy();
			ITeamType[] teamTypearr = proxyTeam.getNodeTeamTypes();
			for(int i =0;i<teamTypearr.length;i++){
				ITeamTypeMembership[] teamTypeMembershipArr = teamTypearr[i].getTeamTypeMemberships();
				for (int j = 0; j < teamTypeMembershipArr.length; j++) {
					teamTypeMembershipLst.add(teamTypeMembershipArr[j]);
				}
			}
			
		}
			
			String isUnlock=getIsUnlock(userId);
			resultMap.put("isUnlock", isUnlock);
		}
		catch (EntityNotFoundException e) {
			DefaultLogger.debug(this, "got exception in doExecute" + e);
			e.printStackTrace();
			throw (new CommandProcessingException(e.getMessage()));
		}
		catch (Exception e) {
			DefaultLogger.debug(this, "got exception in doExecute" + e);
			e.printStackTrace();
			throw (new CommandProcessingException(e.getMessage()));
		}
		// By abhijit r for testing dormant user batch job
		//executeInternal();
		DefaultLogger.debug(this, "Going out of doExecute()");
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		return returnMap;
	}
	
	private static void executeInternal() {
//		System.out.println("IN execute");
		int updatedDormantUser = 0;
		List loginIdList= new ArrayList();
		List  list = (new StdUserDAO()).searchDormantNewUser();
		if(list!=null){
		String[] login_id= new String[list.size()];
		for(int i=0;i<list.size();i++){
			String login=(String)list.get(i);
			loginIdList.add(login);
		}
//		System.out.println("####################!!!!!!!!!!!!!!!!!!!!!!!!!######################");
		if ((null != loginIdList) && (loginIdList.size() != 0)) {
			for (int j = 0; j < loginIdList.size(); j++) {
				try {
					(new StdUserDAO()).updateDormantUser(String.valueOf(loginIdList.get(j)),"CMS_USER");
					(new StdUserDAO()).updateDormantUser(String.valueOf(loginIdList.get(j)),"STAGE_USER");
					updatedDormantUser++;
				}
				catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		}
		
//		System.out.println("####################!!!!!!!!!!!!!!!!!!!!!!!!!######################");

	}
	
	private String getIsUnlock(String userId) {
		String query="SELECT IS_UNLOCK,STATUS FROM CMS_USER WHERE USER_ID="+userId+"";
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
