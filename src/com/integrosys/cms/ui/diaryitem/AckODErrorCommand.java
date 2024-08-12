package com.integrosys.cms.ui.diaryitem;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.struts.util.LabelValueBean;

import com.integrosys.base.businfra.search.SearchDAOException;
import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.techinfra.dbsupport.DBConnectionException;
import com.integrosys.base.techinfra.dbsupport.DBUtil;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.diary.bus.DiaryItemException;
import com.integrosys.cms.app.diary.bus.IDiaryItem;
import com.integrosys.cms.app.diary.bus.IDiaryItemJdbc;
import com.integrosys.cms.app.diary.proxy.IDiaryItemProxyManager;
import com.integrosys.cms.app.generalparam.bus.IGeneralParamDao;
import com.integrosys.cms.app.generalparam.bus.IGeneralParamEntry;
import com.integrosys.cms.app.generalparam.bus.IGeneralParamGroup;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;
import com.integrosys.cms.ui.manualinput.CommonUtil;
import com.integrosys.component.user.app.bus.ICommonUser;

public class AckODErrorCommand extends DiaryItemsCommand{
	public String[][] getParameterDescriptor() {
		return (new String[][] { { "itemId", "java.lang.String", REQUEST_SCOPE },
			 { "action", "java.lang.String", REQUEST_SCOPE },
			 { "nextTargetDate", "java.lang.String", REQUEST_SCOPE },
			{ IGlobalConstant.USER_TEAM, "com.integrosys.component.bizstructure.app.bus.ITeam",
			GLOBAL_SCOPE },
			{IGlobalConstant.USER, "com.integrosys.component.user.app.bus.ICommonUser", GLOBAL_SCOPE},
				{ "frompage", "java.lang.String", REQUEST_SCOPE },
				{ "session.frompage", "java.lang.String", SERVICE_SCOPE },
				{ "session.startIndex", "java.lang.String", SERVICE_SCOPE },
				{ "session.customerIndex", "java.lang.String", SERVICE_SCOPE },
				{ "event", "java.lang.String", REQUEST_SCOPE },
				{ "diaryNumber", "java.lang.String", REQUEST_SCOPE },
				{ "fromEvent", "java.lang.String", REQUEST_SCOPE },
				{ "genericCount", "java.lang.String", SERVICE_SCOPE },
				{ "odCount", "java.lang.String", SERVICE_SCOPE },
				{ "totalCount", "java.lang.String", SERVICE_SCOPE },
				{ "segment", "java.lang.String", SERVICE_SCOPE },
				{ "segmentName", "java.lang.String", SERVICE_SCOPE },
				{ "searchLeID", "java.lang.String", SERVICE_SCOPE },
				{ "overDueCount", "java.lang.String", SERVICE_SCOPE },
				{ "searchCustomerName", "java.lang.String", SERVICE_SCOPE },
				{ "totalCountForCurrentTotalPages", "java.lang.Integer", SERVICE_SCOPE },
				{ "DiaryItemList", "com.integrosys.base.businfra.search.SearchResult", REQUEST_SCOPE },
				{ "recordsPerPage", "java.lang.Integer", REQUEST_SCOPE },
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
		return (new String[][] { { "diaryItemObj", "com.integrosys.cms.app.diary.bus.OBDiaryItem", SERVICE_SCOPE },
				{ "diaryItemObj", "com.integrosys.cms.app.diary.bus.OBDiaryItem", FORM_SCOPE },
				{ "frompage", "java.lang.String", REQUEST_SCOPE }, { "startIndex", "java.lang.String", REQUEST_SCOPE },
				{ "customerIndex", "java.lang.String", REQUEST_SCOPE },
				{ "actionList", "java.util.List", SERVICE_SCOPE },
				{ "actionList", "java.util.List", REQUEST_SCOPE },
				{ "closeBy", "java.lang.String", SERVICE_SCOPE },	
				{ "closeBy", "java.lang.String", REQUEST_SCOPE },
				{ "closedate", "java.lang.String", SERVICE_SCOPE },	
				{ "closedate", "java.lang.String", REQUEST_SCOPE },
				{ "fromEvent", "java.lang.String", REQUEST_SCOPE },
				{ "listOfRecrods", "java.util.List", REQUEST_SCOPE },
				{ "releaseAmount", "java.lang.String", SERVICE_SCOPE },
				{ "closingAmount", "java.lang.String", SERVICE_SCOPE },
				{ "genericCount", "java.lang.String", REQUEST_SCOPE },
				{ "odCount", "java.lang.String", REQUEST_SCOPE },
				{ "totalCount", "java.lang.String", REQUEST_SCOPE },
				{ "segment", "java.lang.String", REQUEST_SCOPE },
				{ "overDueCount", "java.lang.String", REQUEST_SCOPE },
				{ "segmentName", "java.lang.String", REQUEST_SCOPE },
				{ "searchLeID", "java.lang.String", SERVICE_SCOPE },
				{ "searchCustomerName", "java.lang.String", SERVICE_SCOPE },
				{ "totalCountForCurrentTotalPages", "java.lang.Integer", REQUEST_SCOPE },
				{ "DiaryItemList", "com.integrosys.base.businfra.search.SearchResult", REQUEST_SCOPE },
				{ "recordsPerPage", "java.lang.Integer", REQUEST_SCOPE },
		});
	}

	/**
	 * This method does the Business operations with the HashMap and put the
	 * results back into the HashMap.Here reading for Company Borrower is done.
	 * 
	 * @param map is of type HashMap
	 * @throws CommandProcessingException on errors
	 * @throws CommandValidationException on errors
	 * @return HashMap with the Result
	 */
	@SuppressWarnings("unchecked")
	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
		HashMap result = new HashMap();
		HashMap exceptionMap = new HashMap();
		HashMap temp = new HashMap();

		try {
			
			//Start View OD Schedule
			String event = (String) map.get("event");
			result.put("fromEvent", (String) map.get("fromEvent"));
			if("view_odschedule".equals(event)) {
				List listOfRecrods = new ArrayList();
				String diaryNumber = (String) map.get("diaryNumber");
				result.put("listOfRecrods", listOfRecrods);
			}
			//End View OD Schedule
			String fromPage = (String) map.get("frompage");
			String sessFromPage = (String) map.get("session.frompage");
			String sessStartIndex = (String) map.get("session.startIndex");
			String sessCustomerIndex = (String) map.get("session.customerIndex");
			IDiaryItemJdbc diaryItemJdbc=(IDiaryItemJdbc)BeanHouse.get("diaryItemJdbc");
			DefaultLogger.debug(this, "session.fromPage >>>>> " + sessFromPage);
			DefaultLogger.debug(this, "session.startIndex >>>>> " + sessStartIndex);
			DefaultLogger.debug(this, "session.customerIndex >>>>> " + sessCustomerIndex);
			
			String strItemID = (String) map.get("itemId");

			
			
			String action = (String)map.get("action");
			String nextTargetDate = (String)map.get("nextTargetDate");
						
			long itemID = Long.parseLong(strItemID);
			IDiaryItemProxyManager proxyMgr = getDiaryItemProxyManager();
			IDiaryItem item = proxyMgr.getDiaryItem(itemID);
			item.setAction(action);
			
			IGeneralParamDao generalParamDao =(IGeneralParamDao)BeanHouse.get("generalParamDao");
			IGeneralParamGroup generalParamGroup  =generalParamDao.getGeneralParamGroupByGroupType("actualGeneralParamGroup", "GENERAL_PARAM");
			IGeneralParamEntry[]generalParamEntries= generalParamGroup.getFeedEntries();
			Date dateApplication=new Date();
			for(int i=0;i<generalParamEntries.length;i++){
				if(generalParamEntries[i].getParamCode().equals("APPLICATION_DATE")){
					dateApplication=new Date(generalParamEntries[i].getParamValue());
				}
			}
			
			
			
			ICommonUser user = (ICommonUser) map.get(IGlobalConstant.USER);
			
			
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
			
			item.setCloseBy(user.getLoginID()+" "+dateFormat.format(dateApplication));
			item.setCloseDate(dateFormat.format(dateApplication));
			String genericCount = (String) map.get("genericCount");
			String odCount = (String) map.get("odCount");
			String totalCount = (String) map.get("totalCount");
			String segment = (String) map.get("segment");
			String segmentName = (String) map.get("segmentName");
			String searchLeID = (String) map.get("searchLeID");
			String searchCustomerName = (String) map.get("searchCustomerName"); 
			String overDueCount = (String) map.get("overDueCount");
			//String totalCountForCurrentTotalPages = (String) map.get("totalCountForCurrentTotalPages");
			SearchResult searchResult = (SearchResult) map.get("DiaryItemList");
			result.put("closeBy",user.getLoginID()+" "+dateFormat.format(dateApplication));
			result.put("closedate", dateFormat.format(dateApplication));
			result.put("frompage", isNull(fromPage) ? sessFromPage : fromPage);
			result.put("startIndex", sessStartIndex);
			result.put("customerIndex", sessCustomerIndex);
			result.put("diaryItemObj", item);
			result.put("genericCount", genericCount);
			result.put("segment", segment);
			result.put("odCount", odCount);
			result.put("totalCount", totalCount);
			result.put("segmentName", segmentName);
			result.put("searchLeID", searchLeID);
			result.put("searchCustomerName", searchCustomerName);
			result.put("overDueCount", overDueCount);
			
			result.put("DiaryItemList", searchResult);
			result.put("recordsPerPage", new Integer(DiaryItemListMapper.getDefaultPaginationCount()));

			if (searchResult != null) {
				result.put("totalCountForCurrentTotalPages", new Integer(searchResult.getNItems()));
			}
			
		}
		catch (NumberFormatException ex) {
			DefaultLogger.error(this, "NumberFormatException caught in doExecute()", ex);
			exceptionMap.put("application.exception", ex);
			throw new CommandProcessingException(ex.getMessage());

		}
		catch (DiaryItemException ex) {
			DefaultLogger.error(this, "DiaryItemException caught in doExecute()", ex);
			exceptionMap.put("application.exception", ex);
			throw new CommandProcessingException(ex.getMessage());

		}
		catch (Exception ex) {
			DefaultLogger.error(this, "Unknown Exception caught in doExecute()", ex);
			exceptionMap.put("application.exception", ex);
			throw new CommandProcessingException(ex.getMessage());
		}
		temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		return temp;
	}

	/**
	 * helper method to test for null and empty strings
	 * @param s - entered string
	 * @return boolean - true if null
	 */
	private static boolean isNull(String s) {
		if ((s == null) || (s.trim().length() == 0)) {
			return true;
		}
		return false;
	}
	

}
