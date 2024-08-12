package com.integrosys.cms.ui.discrepency;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import com.integrosys.base.businfra.search.SearchDAOException;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.discrepency.bus.IDiscrepancyJdbc;
import com.integrosys.cms.app.discrepency.bus.IDiscrepency;
import com.integrosys.cms.app.discrepency.bus.IDiscrepencyDAO;
import com.integrosys.cms.app.discrepency.bus.OBDiscrepency;
import com.integrosys.cms.app.discrepency.proxy.IDiscrepencyProxyManager;
import com.integrosys.cms.app.discrepency.trx.IDiscrepencyTrxValue;
import com.integrosys.cms.app.discrepency.trx.OBDiscrepencyTrxValue;
import com.integrosys.cms.app.transaction.CMSTrxSearchCriteria;
import com.integrosys.cms.app.transaction.OBTrxContext;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;
import com.integrosys.cms.ui.login.CMSGlobalSessionConstant;
import com.integrosys.component.bizstructure.app.bus.ITeam;
import com.integrosys.component.bizstructure.app.bus.ITeamMembership;
import com.integrosys.component.user.app.bus.ICommonUser;

public class MakerEditResubmitDiscrepencyCommmand extends AbstractCommand {
	
	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ "customerID", "java.lang.String", REQUEST_SCOPE },
				{ "discrepencyObj", "com.integrosys.cms.app.discrepency.bus.IDiscrepency",FORM_SCOPE },
				{"searchstatus","java.lang.String",REQUEST_SCOPE},
				{"discType","java.lang.String",REQUEST_SCOPE},
				{ "searchResult", "java.util.HashMap", REQUEST_SCOPE },
				{ "session.searchResult", "java.util.HashMap", SERVICE_SCOPE },
				{ "startIndex", "java.lang.String", REQUEST_SCOPE },
				{ IGlobalConstant.USER, "com.integrosys.component.user.app.bus.ICommonUser", GLOBAL_SCOPE },
				{ IGlobalConstant.USER_TEAM, "com.integrosys.component.bizstructure.app.bus.ITeam", GLOBAL_SCOPE },
				{ "locale", "java.util.Locale", REQUEST_SCOPE },
				{ IGlobalConstant.GLOBAL_CMSTRXSEARCH_START_INDEX, "java.lang.String", GLOBAL_SCOPE },
				{ IGlobalConstant.GLOBAL_CMSTRXSEARCHCRITERIA_OBJ,
						"com.integrosys.cms.app.transaction.CMSTrxSearchCriteria", GLOBAL_SCOPE },
						{ "searchList2", "java.util.ArrayList", SERVICE_SCOPE },
				{ CMSGlobalSessionConstant.TEAM_TYPE_MEMBERSHIP_ID, "java.lang.String", GLOBAL_SCOPE },
				{ IGlobalConstant.GLOBAL_CUSTOMER_OBJ, "com.integrosys.cms.app.customer.bus.ICMSCustomer", GLOBAL_SCOPE },
				{ "event", "java.lang.String", REQUEST_SCOPE }
		});
	}
	
	public String[][] getResultDescriptor() {
		return (new String[][] {
				{ "searchResult", "java.util.HashMap", REQUEST_SCOPE },
				{ "customerID", "java.lang.String", REQUEST_SCOPE },
				{ "event", "java.lang.String", REQUEST_SCOPE },
				{"searchstatus","java.lang.String",REQUEST_SCOPE},
				{"discType","java.lang.String",REQUEST_SCOPE},
				{ "startIndex", "java.lang.String", REQUEST_SCOPE },
				{ "session.searchResult", "java.util.HashMap", SERVICE_SCOPE },
				{ "session.search", "java.util.HashMap", SERVICE_SCOPE },
				{ "searchList2", "java.util.ArrayList", SERVICE_SCOPE },
				//{ "startIndex", "java.lang.String", REQUEST_SCOPE },
				{ IGlobalConstant.GLOBAL_CMSTRXSEARCHCRITERIA_OBJ,
						"com.integrosys.cms.app.transaction.CMSTrxSearchCriteria", GLOBAL_SCOPE },
				{ IGlobalConstant.GLOBAL_CMSTRXSEARCH_START_INDEX, "java.lang.String", GLOBAL_SCOPE },
 });
	}
	
	public HashMap doExecute(HashMap map) throws CommandProcessingException,
	CommandValidationException {
		HashMap returnMap = new HashMap();
		HashMap resultMap = new HashMap();
		HashMap exceptionMap = new HashMap();
		
		try {
			ArrayList searchList1=new ArrayList();
			IDiscrepency discrepency = (IDiscrepency) map.get("discrepencyObj");
			String discrepencyID=String.valueOf(discrepency.getId());
			String searchstatus=(String)map.get("searchstatus");
			String discType=(String)map.get("discType");
			String startIdx = (String) map.get("startIndex");
			String event = (String) map.get("event");
			HashMap searchResult=(HashMap)map.get("session.searchResult");
			ArrayList searchList=(ArrayList)searchResult.get("list");
			ArrayList replaceList=new ArrayList();
			for(int i=0;i<searchList.size();i++){
				OBDiscrepency objDisc=(OBDiscrepency)searchList.get(i);
				String objID=String.valueOf(objDisc.getId());
				
				if(objID.equals(discrepencyID)){
					objDisc.setAcceptedDate(discrepency.getAcceptedDate());
					objDisc.setApprovedBy(discrepency.getApprovedBy());
					objDisc.setCounter(discrepency.getCounter());
					objDisc.setCreationDate(discrepency.getCreationDate());
					objDisc.setCreditApprover(discrepency.getCreditApprover());
					objDisc.setCustomerId(discrepency.getCustomerId());
					objDisc.setDeferDate(discrepency.getDeferDate());
					//objDisc.setTotalDeferedDays(discrepency.getTotalDeferedDays());
					//objDisc.setOriginalDeferedDays(discrepency.getOriginalDeferedDays());
					objDisc.setDeferedCounter(discrepency.getDeferedCounter());
					objDisc.setDiscrepency(discrepency.getDiscrepency());
					objDisc.setDiscrepencyRemark(discrepency.getDiscrepencyRemark());
					objDisc.setDiscrepencyType(discrepency.getDiscrepencyType());
					objDisc.setDocRemarks(discrepency.getDocRemarks());
					objDisc.setFacilityList(discrepency.getFacilityList());
					objDisc.setId(discrepency.getId());
					objDisc.setNextDueDate(discrepency.getNextDueDate());
					objDisc.setOriginalTargetDate(discrepency.getOriginalTargetDate());
					objDisc.setRecDate(discrepency.getRecDate());
					objDisc.setStatus(discrepency.getStatus());
					objDisc.setTransactionStatus(discrepency.getStatus()); //replace status by trxstatus for trxstatus in db
					objDisc.setCritical(discrepency.getCritical());
					//replace.setVersionTime(0);
					objDisc.setWaiveDate(discrepency.getWaiveDate());
					Date creationDate=discrepency.getCreationDate();
					Date originalDate=discrepency.getOriginalTargetDate();
					Date nextDate=discrepency.getNextDueDate();
					Date deferDate = discrepency.getDeferDate();
					if(nextDate!=null){
					String originalDays=calculateDays(nextDate,originalDate);
					discrepency.setOriginalDeferedDays(originalDays);
					}
					if(nextDate!=null){
						String totalDays=calculateDays(nextDate,creationDate);
						discrepency.setTotalDeferedDays(totalDays);
						}
					
				}
			}
			String discStatus=discrepency.getStatus();
			/*if(discStatus.equals("PENDING_CLOSE")||discStatus.equals("PENDING_DEFER")
					||discStatus.equals("PENDING_WAIVE")){
				Date creationDate=discrepency.getCreationDate();
				Date originalDate=discrepency.getOriginalTargetDate();
				Date nextDate=discrepency.getNextDueDate();
				if(nextDate!=null){				
						String totalDays=calculateDays(nextDate,creationDate);
						discrepency.setTotalDeferedDays(totalDays);				
						String originalDays=calculateDays(nextDate,originalDate);
						discrepency.setOriginalDeferedDays(originalDays);
					
				
				}
			}*/
			
			replaceList=searchList;
			searchResult.remove("list");
			searchResult.put("list", replaceList);
			HashMap searchResult1 =sortedMap(searchResult);
			searchList1=(ArrayList)searchResult1.get("list");
			searchList1=search(searchList1,searchstatus,discType);
			searchResult1.remove("list");
			searchResult1.put("list", searchList1);
			OBTrxContext ctx = (OBTrxContext) map.get("theOBTrxContext");
			IDiscrepencyTrxValue trxValueIn = (OBDiscrepencyTrxValue) map.get("IDiscrepencyTrxValue");
			IDiscrepencyDAO discrepencyDAO=(IDiscrepencyDAO)BeanHouse.get("discrepencyDAO");
		//	discrepencyDAO.updateStageDiscrepency(discrepency);

			String sCustomerID = (String) map.get("customerID");
			long customerID= Long.parseLong(sCustomerID);
			String teamTypeMemID = (String) map.get(CMSGlobalSessionConstant.TEAM_TYPE_MEMBERSHIP_ID);
			
			String globalStartIndex = (String) map.get(IGlobalConstant.GLOBAL_CMSTRXSEARCH_START_INDEX);
			CMSTrxSearchCriteria globalSearch = (CMSTrxSearchCriteria) map
					.get(IGlobalConstant.GLOBAL_CMSTRXSEARCHCRITERIA_OBJ);

			ICommonUser user = (ICommonUser) map.get(IGlobalConstant.USER);
			ITeam team = (ITeam) map.get(IGlobalConstant.USER_TEAM);

			CMSTrxSearchCriteria criteria = new CMSTrxSearchCriteria();
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
			
			/*HashMap searchResult = this.getSearchResult(criteria, customerID);
			
			HashMap searchResult1 =sortedMap(searchResult);
			ArrayList resultList = (ArrayList)searchResult1.get("list");
			
				ArrayList searchList=search(resultList,searchstatus,discType);
				searchResult1.remove("list");
				searchResult1.put("list", searchList);
			*/
			resultMap.put("searchResult", searchResult1);
			resultMap.put("session.searchResult", searchResult1);
			resultMap.put("session.search", searchResult1);
			resultMap.put(IGlobalConstant.USER_TEAM, team);
			resultMap.put(IGlobalConstant.USER, user);
			resultMap.put("event", event);
			resultMap.put("customerID", sCustomerID);
			resultMap.put("searchstatus", searchstatus);
			resultMap.put("discType", discType);
			resultMap.put("searchList2", searchResult1.get("list"));
			resultMap.put("startIndex", startIdx);
			
		
		}
		catch (Exception e) {
			CommandProcessingException cpe = new CommandProcessingException(
					"Internal Error While Processing ");
			cpe.initCause(e);
			throw cpe;
		}
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		return returnMap;
	}
	
	protected HashMap getSearchResult(CMSTrxSearchCriteria criteria, long customerId) throws TrxOperationException,
	SearchDAOException, RemoteException {

		IDiscrepancyJdbc discrepencyJdbc= (IDiscrepancyJdbc)BeanHouse.get("discrepencyJdbc");
		return discrepencyJdbc.searchBulkTransactions(criteria,customerId);
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
				IDiscrepencyProxyManager discrepencyProxyManager=(IDiscrepencyProxyManager) BeanHouse.get("discrepencyProxy");
				IDiscrepencyTrxValue trxValue=discrepencyProxyManager.getDiscrepencyByTrxID(aTrxID);
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
public String calculateDays(Date nextDate,Date calculateDate){
		
		
		Calendar calendar1 = Calendar.getInstance();
        Calendar calendar2 = Calendar.getInstance();
        calendar1.set(calculateDate.getYear(),calculateDate.getMonth(),calculateDate.getDate());
		calendar2.set(nextDate.getYear(), nextDate.getMonth(), nextDate.getDate());
		  long milliseconds1 = calendar1.getTimeInMillis();
		  long milliseconds2 = calendar2.getTimeInMillis();
		  long diff =  milliseconds2-milliseconds1;
		  long diffSeconds = diff / 1000;
		  long diffMinutes = diff / (60 * 1000);
		  long diffHours = diff / (60 * 60 * 1000);
		  long diffDays = diff / (24 * 60 * 60 * 1000);
		  String days=String.valueOf(diffDays);
		  return days;
        
	}

}
