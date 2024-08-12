package com.integrosys.cms.ui.discrepency;

import java.rmi.RemoteException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import com.integrosys.base.businfra.search.SearchDAOException;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.techinfra.dbsupport.DBConnectionException;
import com.integrosys.base.techinfra.dbsupport.DBUtil;
import com.integrosys.base.techinfra.dbsupport.NoSQLStatementException;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.customer.bus.ICMSCustomer;
import com.integrosys.cms.app.discrepency.bus.IDiscrepancyJdbc;
import com.integrosys.cms.app.discrepency.bus.NoSuchDiscrepencyException;
import com.integrosys.cms.app.discrepency.bus.OBDiscrepency;
import com.integrosys.cms.app.discrepency.proxy.IDiscrepencyProxyManager;
import com.integrosys.cms.app.discrepency.trx.IDiscrepencyTrxValue;
import com.integrosys.cms.app.transaction.CMSTrxSearchCriteria;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;
import com.integrosys.cms.ui.login.CMSGlobalSessionConstant;
import com.integrosys.component.bizstructure.app.bus.ITeam;
import com.integrosys.component.bizstructure.app.bus.ITeamMembership;
import com.integrosys.component.user.app.bus.ICommonUser;

/**
 * @author $Author: Sandeep Shinde
 * @version 2.0
 * @since $Date: 14/02/2011 02:37:00 $ Tag: $Name: $
 */

public class CheckerReadBulkDiscrepencyCommand extends AbstractCommand implements
		ICommonEventConstant {

	private IDiscrepencyProxyManager discrepencyProxy;
	
	public IDiscrepencyProxyManager getDiscrepencyProxy() {
		return discrepencyProxy;
	}

	public void setDiscrepencyProxy(IDiscrepencyProxyManager discrepencyProxy) {
		this.discrepencyProxy = discrepencyProxy;
	}

	public CheckerReadBulkDiscrepencyCommand() {
	}

	/**
	 * Defines an two dimensional array with the parameter list to be passed to
	 * the doExecute method by a HashMap syntax for the array is
	 * (HashMapkey,class name,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ "customerID", "java.lang.String", REQUEST_SCOPE },
				{"searchstatus","java.lang.String",REQUEST_SCOPE},
				{"discType","java.lang.String",REQUEST_SCOPE},
				{ "startIndex", "java.lang.String", REQUEST_SCOPE },
				{ "session.search", "java.util.HashMap", SERVICE_SCOPE },
				{ "loginId", "java.lang.String", REQUEST_SCOPE },
				{ "transactionDate", "java.lang.String", REQUEST_SCOPE },
				{ "searchResult", "java.util.HashMap", REQUEST_SCOPE },
				{ "session.searchResult", "java.util.HashMap", SERVICE_SCOPE },
				{ "checkId", "java.lang.String", REQUEST_SCOPE },
				{ "unCheckId", "java.lang.String", REQUEST_SCOPE },
				{ "selectedArrayMap", "java.util.HashMap", SERVICE_SCOPE },
				{ IGlobalConstant.USER, "com.integrosys.component.user.app.bus.ICommonUser", GLOBAL_SCOPE },
				{ IGlobalConstant.USER_TEAM, "com.integrosys.component.bizstructure.app.bus.ITeam", GLOBAL_SCOPE },
				{ "locale", "java.util.Locale", REQUEST_SCOPE },
				{ IGlobalConstant.GLOBAL_CMSTRXSEARCH_START_INDEX, "java.lang.String", GLOBAL_SCOPE },
				{ IGlobalConstant.GLOBAL_CMSTRXSEARCHCRITERIA_OBJ,
						"com.integrosys.cms.app.transaction.CMSTrxSearchCriteria", GLOBAL_SCOPE },
						{ "searchList2", "java.util.ArrayList", SERVICE_SCOPE },
				{ CMSGlobalSessionConstant.TEAM_TYPE_MEMBERSHIP_ID, "java.lang.String", GLOBAL_SCOPE },
				{ IGlobalConstant.GLOBAL_CUSTOMER_OBJ, "com.integrosys.cms.app.customer.bus.ICMSCustomer", GLOBAL_SCOPE },
				{ "event", "java.lang.String", REQUEST_SCOPE } });
	}

	/**
	 * Defines an two dimensional array with the result list to be expected as a
	 * result from the doExecute method using a HashMap syntax for the array is
	 * (HashMapkey,class name,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getResultDescriptor() {
		return (new String[][] {
				{ "searchResult", "java.util.HashMap", REQUEST_SCOPE },
				{ "customerID", "java.lang.String", REQUEST_SCOPE },
				{ "event", "java.lang.String", REQUEST_SCOPE },
				{ "session.searchResult", "java.util.HashMap", SERVICE_SCOPE },
				{ "loginId", "java.lang.String", REQUEST_SCOPE },
				{ "session.search", "java.util.HashMap", SERVICE_SCOPE },
				{ "selectedArrayMap", "java.util.HashMap", SERVICE_SCOPE },
				{ "transactionDate", "java.lang.String", REQUEST_SCOPE },
				{ "searchList2", "java.util.ArrayList", SERVICE_SCOPE },
				{ "startIndex", "java.lang.String", REQUEST_SCOPE },
				{"searchstatus","java.lang.String",REQUEST_SCOPE},
				{"discType","java.lang.String",REQUEST_SCOPE},
				{ IGlobalConstant.GLOBAL_CMSTRXSEARCHCRITERIA_OBJ,
						"com.integrosys.cms.app.transaction.CMSTrxSearchCriteria", GLOBAL_SCOPE },
				{ IGlobalConstant.GLOBAL_CMSTRXSEARCH_START_INDEX, "java.lang.String", GLOBAL_SCOPE },
 });
	}

	/**
	 * This method does the Business operations with the HashMap and put the
	 * results back into the HashMap.Here creation for Company Borrower is done.
	 * 
	 * @param map
	 *            is of type HashMap
	 * @return HashMap with the Result
	 */
	public HashMap doExecute(HashMap map) throws CommandProcessingException,
			CommandValidationException, NoSuchDiscrepencyException{
		HashMap returnMap = new HashMap();
		HashMap resultMap = new HashMap();
		HashMap discrepencyMap=new HashMap();
		HashMap discrepencyMap2=new HashMap();
		ICMSCustomer custOB = (ICMSCustomer) map.get(IGlobalConstant.GLOBAL_CUSTOMER_OBJ);
		String event = (String) map.get("event");
		String searchstatus=(String)map.get("searchstatus");
		String loginId=(String)map.get("loginId");
		String transactionDate=(String)map.get("transactionDate");
		String discType=(String)map.get("discType");
		String startIdx = (String) map.get("startIndex");
		String checkId =(String) map.get("checkId");
		String unCheckId =(String) map.get("unCheckId");
		HashMap selectedArrayMap = (HashMap) map.get("selectedArrayMap");
		resultMap.put("loginId", loginId);
		resultMap.put("transactionDate", transactionDate);
		if(selectedArrayMap==null){ 
			selectedArrayMap = new HashMap();
		}
		
		if(checkId!=null && !checkId.equals("") ){
			
			String[] selected=checkId.split("-");
			if(selected!=null){
					for(int k=0;k<selected.length;k++){
						if(!selected[k].equals("")){
								selectedArrayMap.put(selected[k], selected[k]);
						}
					}
			}
		}
			
		if(unCheckId!=null && !unCheckId.equals("")){
			String[] unchecked=unCheckId.split("-");
			if(unchecked!=null){
					for(int ak=0;ak<unchecked.length;ak++){
						if(!unchecked[ak].equals("")){
								selectedArrayMap.remove(unchecked[ak]);
						}
					}
				}
		}
		
		resultMap.put("selectedArrayMap", selectedArrayMap);
		
		/*if(event.equals("checker_search_discrepency")||event.equals("maker_process_search_discrepency")
				||event.equals("maker_close_search_discrepency")){
			try {
				discrepencyMap=(HashMap)map.get("session.searchResult");
				discrepencyMap2=(HashMap)map.get("session.searchResult");
				ArrayList sessiondiscrepencyList =new ArrayList();
				HashMap searchResult=new HashMap();
				ArrayList searchList = new ArrayList();
				ArrayList searchList2 = new ArrayList();
				searchList=(ArrayList)map.get("searchList2");
				searchList2=searchList;
				
						if(!searchstatus.equalsIgnoreCase("Please")|| !discType.equalsIgnoreCase("Please")){
						for(int i=0;i<searchList.size();i++){
							OBDiscrepency searchDiscrepency=(OBDiscrepency)searchList.get(i);
							
							if(searchstatus.equalsIgnoreCase("PENDING_DEFER")||searchstatus.equalsIgnoreCase("PENDING_CLOSE")||
									searchstatus.equalsIgnoreCase("PENDING_WAIVE")||searchstatus.equalsIgnoreCase("PENDING_CREATE")
									||searchstatus.equalsIgnoreCase("PENDING_UPDATE")){
								if(!searchstatus.equals("")&& discType.equals("Please")){
									if(searchstatus.equalsIgnoreCase(searchDiscrepency.getStatus())){
										sessiondiscrepencyList.add(searchDiscrepency);
										
									}	
								}
								else if(!discType.equals("")&& searchstatus.equals("Please")){
									if(discType.equalsIgnoreCase(searchDiscrepency.getStatus())){
										sessiondiscrepencyList.add(searchDiscrepency);
									}
									
								}
								else if(!discType.equals("")&&!searchstatus.equals("")){
									if(discType.equalsIgnoreCase(searchDiscrepency.getDiscrepencyType())&& 
											searchstatus.equalsIgnoreCase(searchDiscrepency.getStatus())){
										sessiondiscrepencyList.add(searchDiscrepency);
									}
									
								}
							}
							
							else{
								if(!searchstatus.equals("")&& discType.equals("Please")){
									if(searchstatus.equalsIgnoreCase(searchDiscrepency.getStatus())&&
											searchDiscrepency.getTransactionStatus().equalsIgnoreCase("ACTIVE")){
										sessiondiscrepencyList.add(searchDiscrepency);
										
									}	
								}
								else if(!discType.equals("")&& searchstatus.equals("Please")){
									if(discType.equalsIgnoreCase(searchDiscrepency.getDiscrepencyType())){
										sessiondiscrepencyList.add(searchDiscrepency);
									}
									
								}
								else if(!discType.equals("")&&!searchstatus.equals("")){
									if(discType.equalsIgnoreCase(searchDiscrepency.getDiscrepencyType())&& 
											searchstatus.equalsIgnoreCase(searchDiscrepency.getStatus())&&
											searchDiscrepency.getTransactionStatus().equalsIgnoreCase("ACTIVE")){
										sessiondiscrepencyList.add(searchDiscrepency);
									}
									
								}
							}
					}
						
						discrepencyMap.remove("list");
						discrepencyMap.put("list", sessiondiscrepencyList);
						
						resultMap.put("session.search", discrepencyMap);
						resultMap.put("event", event);
						resultMap.put("searchResult", discrepencyMap);
						resultMap.put("searchList2", searchList2);
						resultMap.put("session.searchResult",map.get("session.searchResult"));
						resultMap.put("customerID", custOB);
						resultMap.put("searchstatus",searchstatus);
						resultMap.put("discType",discType);
						resultMap.put("startIndex",startIdx);
				}
				else{
					HashMap sortedResult =sortedMap(discrepencyMap2);
					
						discrepencyMap.remove("list");
						discrepencyMap.put("list", searchList2);
						resultMap.put("session.search", discrepencyMap);
						resultMap.put("event", event);
						resultMap.put("searchResult", discrepencyMap);
						resultMap.put("searchList2", searchList2);
						resultMap.put("session.searchResult", map.get("session.searchResult"));
						resultMap.put("customerID", custOB);
						resultMap.put("searchstatus", searchstatus);
						resultMap.put("discType", discType);
						resultMap.put("startIndex", startIdx);
					}
				
				
				
			} catch (Exception e) {
				CommandProcessingException cpe = new CommandProcessingException(
						"Internal Error While Processing Search Map");
				cpe.initCause(e);
				throw cpe;
			}
			
		}*/
		if(event.equals("checker_next_list_discrepency")||event.equals("maker_next_list_discrepency")
				||event.equals("maker_close_next_bulk_discrepancy")){
			try{
				resultMap.put("session.searchResult",map.get("session.searchResult") );
				resultMap.put("session.search", map.get("session.search"));
				resultMap.put(IGlobalConstant.USER_TEAM, map.get(IGlobalConstant.USER_TEAM));
				resultMap.put(IGlobalConstant.USER, map.get(IGlobalConstant.USER));
				resultMap.put("event", event);
				resultMap.put("customerID", map.get("customerID"));
				resultMap.put("startIndex", startIdx);
				resultMap.put("searchstatus", searchstatus);
				resultMap.put("discType", discType);
				resultMap.put("searchList2",  map.get("searchList2"));
				
			}
			catch(Exception e){
				e.printStackTrace();
				
			}
		}
		
		else if(event.equals("maker_return_process_list_discrepency")||"maker_close_search_discrepency".equals(event)){
			try{
				HashMap searchResult=(HashMap)map.get("session.searchResult");
				HashMap searchResult1 =sortedMap(searchResult);
				ArrayList resultList = (ArrayList)searchResult1.get("list");
				ArrayList searchList=search(resultList,searchstatus,discType);
				searchResult1.remove("list");
				searchResult1.put("list", searchList);
				resultMap.put("session.searchResult", searchResult1);
				resultMap.put(IGlobalConstant.USER_TEAM, map.get(IGlobalConstant.USER_TEAM));
				resultMap.put(IGlobalConstant.USER, map.get(IGlobalConstant.USER));
				resultMap.put("event", event);
				resultMap.put("customerID", map.get("customerID"));
				resultMap.put("startIndex", startIdx);
				resultMap.put("searchstatus", searchstatus);
				resultMap.put("discType", discType);
				resultMap.put("searchList2",  searchResult.get("list"));
				
			}
			catch(Exception e){
				e.printStackTrace();
				
			}
			
		}
		else if(event.equals("maker_search_session_list_discrepency")||"maker_search_session_list_discrepency_close".equals(event)){
			try{
				startIdx="0";
				HashMap searchResult=(HashMap)map.get("session.searchResult");
				HashMap searchResult1 =sortedMap(searchResult);
				ArrayList resultList = (ArrayList)searchResult1.get("list");
				ArrayList searchList=search(resultList,searchstatus,discType);
				searchResult1.remove("list");
				searchResult1.put("list", searchList);
				resultMap.put("session.search", searchResult1);
				resultMap.put("session.searchResult", searchResult);
				resultMap.put(IGlobalConstant.USER_TEAM, map.get(IGlobalConstant.USER_TEAM));
				resultMap.put(IGlobalConstant.USER, map.get(IGlobalConstant.USER));
				resultMap.put("event", event);
				resultMap.put("customerID", map.get("customerID"));
				resultMap.put("startIndex", startIdx);
				resultMap.put("searchstatus", searchstatus);
				resultMap.put("discType", discType);
				resultMap.put("searchList2",  searchResult.get("list"));
				
			}
			catch(Exception e){
				e.printStackTrace();
				
			}
			
		}
		
		
		else{
		try {
			String sCustomerID = (String) map.get("customerID");
			long customerID= Long.parseLong(sCustomerID);
			String teamTypeMemID = (String) map.get(CMSGlobalSessionConstant.TEAM_TYPE_MEMBERSHIP_ID);
			
			String globalStartIndex = (String) map.get(IGlobalConstant.GLOBAL_CMSTRXSEARCH_START_INDEX);
			CMSTrxSearchCriteria globalSearch = (CMSTrxSearchCriteria) map
					.get(IGlobalConstant.GLOBAL_CMSTRXSEARCHCRITERIA_OBJ);

			ICommonUser user = (ICommonUser) map.get(IGlobalConstant.USER);
			ITeam team = (ITeam) map.get(IGlobalConstant.USER_TEAM);

			CMSTrxSearchCriteria criteria = new CMSTrxSearchCriteria();
			criteria.setLoginID(loginId);
			criteria.setTransactionDate(transactionDate);
			criteria.setTeamID(team.getTeamID());
			criteria.setSearchIndicator(ICMSConstant.TODO_ACTION);
			long teamTypeMembershipID = com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;
			if ((teamTypeMemID != null) && (teamTypeMemID.trim().length() > 0)) {
				teamTypeMembershipID = Long.parseLong(teamTypeMemID);
			}

			for (int i = 0; i < team.getTeamMemberships().length; i++) {
				ITeamMembership membership = team.getTeamMemberships()[i];
				if (membership.getTeamTypeMembership().getMembershipID() == teamTypeMembershipID) {
					criteria.setTeamMembershipID(membership.getTeamMembershipID());
					break;
				}
			}

			criteria.setTeamTypeMembershipID(teamTypeMembershipID);
			// +01/07/2006
			criteria.setUserID(user.getUserID());
			
/*			String teamTypeBranchMakerStr = String.valueOf(ICMSConstant.TEAM_TYPE_BRANCH_MAKER);
			if(teamTypeBranchMakerStr.equals(teamTypeMemID)){
				long teamMembershipIdForCpuMakerUser = getTeamMemberShipIdForBranchUser(ICMSConstant.TEAM_TYPE_SSC_MAKER);
				long teamIdForCpuMakerUser = getTeamIdForBranchUser(ICMSConstant.TEAM_TYPE_SSC_MAKER);
				criteria.setTeamTypeMembershipID(ICMSConstant.TEAM_TYPE_SSC_MAKER);
				criteria.setTeamMembershipID(teamMembershipIdForCpuMakerUser);
				criteria.setTeamID(teamIdForCpuMakerUser);
			}else {
				String makerTeamTypeMemID = getTeamTypeIdBranchUser(loginId);
				if(teamTypeBranchMakerStr.equals(makerTeamTypeMemID)) {
					long teamIdForBranchUser = getTeamIdForBranchUser(ICMSConstant.TEAM_TYPE_BRANCH_CHECKER);
					criteria.setTeamID(teamIdForBranchUser);
					criteria.setTeamTypeMembershipID(ICMSConstant.TEAM_TYPE_BRANCH_CHECKER);
				}
			}*/
			
			HashMap searchResult = this.getSearchResult(criteria, customerID);
			HashMap searchResult1 =sortedMap(searchResult);
			ArrayList resultList = (ArrayList)searchResult1.get("list");
			if(event.equals("checker_search_discrepency")||event.equals("maker_process_search_discrepency")
					||event.equals("maker_close_search_discrepency")||event.equals("checker_list_discrepency")){
				ArrayList searchList=search(resultList,searchstatus,discType);
				searchResult1.remove("list");
				searchResult1.put("list", searchList);
				
			}
			if(event.equals("checker_search_discrepency")||event.equals("maker_process_search_discrepency")
					){
				if(!searchstatus.equalsIgnoreCase("Please")|| !discType.equalsIgnoreCase("Please")){
					startIdx="0";
				}
			}
			
			resultMap.put("searchResult", searchResult1);
			resultMap.put("session.searchResult", searchResult1);
			resultMap.put("session.search", searchResult1);
			resultMap.put(IGlobalConstant.USER_TEAM, team);
			resultMap.put(IGlobalConstant.USER, user);
			resultMap.put("event", event);
			resultMap.put("loginId", loginId);
			resultMap.put("customerID", sCustomerID);
			resultMap.put("searchstatus", searchstatus);
			resultMap.put("discType", discType);
			resultMap.put("searchList2", searchResult1.get("list"));
			resultMap.put("startIndex", startIdx);
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		}
		
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		return returnMap;
	}
	
	
	protected HashMap getSearchResult(CMSTrxSearchCriteria criteria, long customerId) throws TrxOperationException,
	SearchDAOException, RemoteException {

		IDiscrepancyJdbc discrepencyJdbc= (IDiscrepancyJdbc)BeanHouse.get("discrepencyJdbc");
		return discrepencyJdbc.searchBulkTransactions(criteria,customerId);
		}
	
	public HashMap sortedMap(HashMap searchResult){
		ArrayList create=new ArrayList();
		ArrayList update=new ArrayList();
		ArrayList defer=new ArrayList();
		ArrayList waive=new ArrayList();
		ArrayList close=new ArrayList();
		ArrayList result=new ArrayList();
		HashMap returnMap=new HashMap();
		HashMap trxValueMap=new HashMap();
		ArrayList resultList = (ArrayList)searchResult.get("list");
		HashMap infoMap=(HashMap)searchResult.get("map");
		HashMap remarkMap=(HashMap)searchResult.get("remarks_map");
		
		for(int i=0;i<resultList.size();i++){
			OBDiscrepency disObj=new OBDiscrepency();
			disObj=(OBDiscrepency)resultList.get(i);
			String aTrxID =(String)infoMap.get(String.valueOf(disObj.getId()));
			try {
				IDiscrepencyTrxValue trxValue=getDiscrepencyProxy().getDiscrepencyByTrxID(aTrxID);
				trxValueMap.put(aTrxID, trxValue);
			}  catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(disObj.getStatus().equals("PENDING_CREATE")){
				String id =(String)infoMap.get(String.valueOf(disObj.getId()));
				create.add(disObj);
			}
			if(disObj.getStatus().equals("PENDING_UPDATE")){
				String id =(String)infoMap.get(String.valueOf(disObj.getId()));
				update.add(disObj);
			}
			if(disObj.getStatus().equals("PENDING_DEFER")){
				String id =(String)infoMap.get(String.valueOf(disObj.getId()));
				defer.add(disObj);
			}
			if(disObj.getStatus().equals("PENDING_CLOSE")){
				String id =(String)infoMap.get(String.valueOf(disObj.getId()));
				close.add(disObj);
			}
			if(disObj.getStatus().equals("PENDING_WAIVE")){
				String id =(String)infoMap.get(String.valueOf(disObj.getId()));
				waive.add(disObj);
			}
			
		}
		if(create.size()>0){
			result.addAll(create);
		}
		if(update.size()>0){
			result.addAll(update);
		}
		if(defer.size()>0){
			result.addAll(defer);
		}
		if(close.size()>0){
			result.addAll(close);
		}
		if(waive.size()>0){
			result.addAll(waive);
		}
		returnMap.put("trxValueMap", trxValueMap);
		returnMap.put("list", result);
		returnMap.put("map", infoMap);
		returnMap.put("remarks_map", remarkMap);
		return returnMap;
		
	}
	
			public ArrayList search(ArrayList resultList,String searchstatus,String discType){
				if(!searchstatus.equalsIgnoreCase("Please")|| !discType.equalsIgnoreCase("Please")){
				ArrayList sessiondiscrepencyList =new ArrayList();
				for(int i=0;i<resultList.size();i++){
					OBDiscrepency searchDiscrepency=(OBDiscrepency)resultList.get(i);
					
					if(searchstatus.equalsIgnoreCase("PENDING_DEFER")||searchstatus.equalsIgnoreCase("PENDING_CLOSE")||
							searchstatus.equalsIgnoreCase("PENDING_WAIVE")||searchstatus.equalsIgnoreCase("PENDING_CREATE")
							||searchstatus.equalsIgnoreCase("PENDING_UPDATE")){
						if(!searchstatus.equals("")&& discType.equals("Please")){
							if(searchstatus.equalsIgnoreCase(searchDiscrepency.getStatus())){
								sessiondiscrepencyList.add(searchDiscrepency);
								
							}	
						}
						else if(!discType.equals("")&& searchstatus.equals("Please")){
							if(discType.equalsIgnoreCase(searchDiscrepency.getStatus())){
								sessiondiscrepencyList.add(searchDiscrepency);
							}
							
						}
						else if(!discType.equals("")&&!searchstatus.equals("")){
							if(discType.equalsIgnoreCase(searchDiscrepency.getDiscrepencyType())&& 
									searchstatus.equalsIgnoreCase(searchDiscrepency.getStatus())){
								sessiondiscrepencyList.add(searchDiscrepency);
							}
							
						}
					}
					
					else{
						if(!searchstatus.equals("")&& discType.equals("Please")){
							if(searchstatus.equalsIgnoreCase(searchDiscrepency.getStatus())&&
									searchDiscrepency.getTransactionStatus().equalsIgnoreCase("ACTIVE")){
								sessiondiscrepencyList.add(searchDiscrepency);
								
							}	
						}
						else if(!discType.equals("")&& searchstatus.equals("Please")){
							if(discType.equalsIgnoreCase(searchDiscrepency.getDiscrepencyType())){
								sessiondiscrepencyList.add(searchDiscrepency);
							}
							
						}
						else if(!discType.equals("")&&!searchstatus.equals("")){
							if(discType.equalsIgnoreCase(searchDiscrepency.getDiscrepencyType())&& 
									searchstatus.equalsIgnoreCase(searchDiscrepency.getStatus())&&
									searchDiscrepency.getTransactionStatus().equalsIgnoreCase("ACTIVE")){
								sessiondiscrepencyList.add(searchDiscrepency);
							}
							
						}
					}
			}
				return sessiondiscrepencyList;
				}
				return resultList;
				
		}
			
	public long getTeamMemberShipIdForBranchUser(int teamID) throws SearchDAOException{
		DBUtil dbUtil = null;
		long  teamMembershipIdForBranchUser = 0;
		try {
			dbUtil = new DBUtil();
			String sql = "SELECT TEAM_MEMBERSHIP_ID FROM CMS_TEAM_MEMBER WHERE USER_ID = (SELECT USER_ID FROM CMS_USER WHERE TEAM_TYPE_MEMBERSHIP_ID = '"+teamID+"' AND ROWNUM = 1)";
			dbUtil.setSQL(sql);
			System.out.println("getTeamMemberShipIdForBranchUser() =>sql=>"+sql);
			ResultSet rs = dbUtil.executeQuery();
			while (rs.next()) {
				teamMembershipIdForBranchUser=rs.getLong("TEAM_MEMBERSHIP_ID");
				System.out.println("getTeamMemberShipIdForBranchUser() for branch user=>teamMembershipIdForBranchUser=>"+teamMembershipIdForBranchUser);
			}
			rs.close();
			return teamMembershipIdForBranchUser;
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
	
	public long getTeamIdForBranchUser(int teamID) throws SearchDAOException{
		DBUtil dbUtil = null;
		long  teamIdForBranchUser = 0;
		try {
			dbUtil = new DBUtil();
			String sql = "SELECT TEAM_ID FROM CMS_TEAM WHERE TEAM_TYPE_ID = (SELECT DISTINCT TEAM_TYPE_ID FROM CMS_TEAM_TYPE_MEMBERSHIP WHERE TEAM_TYPE_MEMBERSHIP_ID = '"+teamID+"')";
			dbUtil.setSQL(sql);
			System.out.println("getTeamIdForBranchUser() =>sql=>"+sql);
			ResultSet rs = dbUtil.executeQuery();
			while (rs.next()) {
				teamIdForBranchUser=rs.getLong("TEAM_ID");
				System.out.println("getTeamIdForBranchUser() for branch user=>teamIdForBranchUser=>"+teamIdForBranchUser);
			}
			rs.close();
			return teamIdForBranchUser;
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
	
	public String getTeamTypeIdBranchUser(String loginId) throws SearchDAOException{
		DBUtil dbUtil = null;
		String  teamTypeIdForBranchUser = "";
		try {
			dbUtil = new DBUtil();
			String sql = "SELECT DISTINCT TEAM_TYPE_MEMBERSHIP_ID FROM CMS_USER WHERE  LOGIN_ID = '"+loginId+"' ";
			dbUtil.setSQL(sql);
			System.out.println("getTeamTypeIdBranchUser() =>sql=>"+sql);
			ResultSet rs = dbUtil.executeQuery();
			while (rs.next()) {
				teamTypeIdForBranchUser=rs.getString("TEAM_TYPE_MEMBERSHIP_ID");
				System.out.println("getTeamTypeIdBranchUser() for branch user=>teamTypeIdForBranchUser=>"+teamTypeIdForBranchUser);
			}
			rs.close();
			return teamTypeIdForBranchUser;
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
