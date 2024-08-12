package com.integrosys.cms.ui.diaryitem;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import com.integrosys.base.businfra.search.SearchDAOException;
import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.diary.bus.DiaryItemException;
import com.integrosys.cms.app.diary.bus.DiaryItemSearchCriteria;
import com.integrosys.cms.app.diary.bus.IDiaryDao;
import com.integrosys.cms.app.diary.bus.IDiaryItem;
import com.integrosys.cms.app.diary.proxy.IDiaryItemProxyManager;
import com.integrosys.cms.app.generalparam.bus.IGeneralParamDao;
import com.integrosys.cms.app.generalparam.bus.IGeneralParamEntry;
import com.integrosys.cms.app.generalparam.bus.IGeneralParamGroup;
import com.integrosys.cms.ui.collateral.CategoryCodeConstant;
import com.integrosys.cms.ui.common.CommonCodeList;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;
import com.integrosys.component.bizstructure.app.bus.ITeam;

public class TodaysDiaryItemCommand extends AbstractListDiaryItemsCommand {
	
	private IDiaryItemProxyManager diaryItemProxyManager;
	
	public IDiaryItemProxyManager getDiaryItemProxyManager() {
		return diaryItemProxyManager;
	}

	public void setDiaryItemProxyManager(IDiaryItemProxyManager diaryItemProxyManager) {
		this.diaryItemProxyManager = diaryItemProxyManager;
	}
	
	private int totalPageForPagination;

	private int recordsPerPageForPagination;

	public void setTotalPageForPagination(int totalPageForPagination) {
		this.totalPageForPagination = totalPageForPagination;
	}

	public void setRecordsPerPageForPagination(int recordsPerPageForPagination) {
		this.recordsPerPageForPagination = recordsPerPageForPagination;
	}

	/**
	 * Defines an two dimensional array with the result list to be expected as a
	 * result from the doExecute method using a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	
	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ "DiaryItemSearchCriteria", "com.integrosys.cms.app.diary.bus.DiaryItemSearchCriteria", FORM_SCOPE },
				
				{ "frompage", "java.lang.String", REQUEST_SCOPE },
				{ "startIndex", "java.lang.String", REQUEST_SCOPE },
				{ "customerIndex", "java.lang.String", REQUEST_SCOPE },
				{ "countryFilter", "java.lang.String", REQUEST_SCOPE },
				{ "allowedCountry", "java.lang.String", REQUEST_SCOPE },
				{ "session.frompage", "java.lang.String", SERVICE_SCOPE },
				{ "session.startIndex", "java.lang.String", SERVICE_SCOPE },
				{ "session.customerIndex", "java.lang.String", SERVICE_SCOPE },
				{ "session.countryFilter", "java.lang.String", SERVICE_SCOPE },
				{ IGlobalConstant.USER_TEAM, "com.integrosys.component.bizstructure.app.bus.ITeam", GLOBAL_SCOPE },
				// CR-120 Search Diary Item
				{ "searchLeID", "java.lang.String", REQUEST_SCOPE },
				{ "searchCustomerName", "java.lang.String", REQUEST_SCOPE },
				{ "totalCountForCurrentTotalPages", "java.lang.Integer", SERVICE_SCOPE } });
	}
	
	public String[][] getResultDescriptor() {
		return (new String[][] { { "diaryItemList", "com.integrosys.base.businfra.search.SearchResult", SERVICE_SCOPE },
			{ "segmentWiseTodayList", "java.lang.String", SERVICE_SCOPE },
			{ "totalGenericDiaryItemcount","java.lang.Integer", REQUEST_SCOPE },
			{ "totalDroplineODDiaryItemcount","java.lang.Integer", REQUEST_SCOPE },
			{ "recordsPerPage", "java.lang.Integer", REQUEST_SCOPE },
			{ "frompage", "java.lang.String", REQUEST_SCOPE },
			{ "startIndex", "java.lang.String", REQUEST_SCOPE },
			{ "customerIndex", "java.lang.String", REQUEST_SCOPE },
			{ "countryFilter", "java.lang.String", REQUEST_SCOPE },
			{ "allowedCountry", "java.lang.String", REQUEST_SCOPE },
			{ "session.frompage", "java.lang.String", SERVICE_SCOPE },
			{ "session.startIndex", "java.lang.String", SERVICE_SCOPE },
			{ "session.customerIndex", "java.lang.String", SERVICE_SCOPE },
			{ "session.countryFilter", "java.lang.String", SERVICE_SCOPE },
			{ IGlobalConstant.USER_TEAM, "com.integrosys.component.bizstructure.app.bus.ITeam", GLOBAL_SCOPE },
			// CR-120 Search Diary Item
			{ "searchLeID", "java.lang.String", REQUEST_SCOPE },
			{ "searchCustomerName", "java.lang.String", REQUEST_SCOPE },
			
			{ "genericTodayCount", "java.lang.Integer", REQUEST_SCOPE },
			{ "droplineTodayCount", "java.lang.Integer", REQUEST_SCOPE },
			{ "overdueTodayCount", "java.lang.Integer", REQUEST_SCOPE },
			
			{ "totalCountForCurrentTotalPages", "java.lang.Integer", SERVICE_SCOPE } 
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
	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
		HashMap result = new HashMap();
		HashMap exceptionMap = new HashMap();
		HashMap temp = new HashMap();

		IDiaryItemProxyManager proxyMgr =getDiaryItemProxyManager();
		//proxyMgr.getDiaryItemsDueFor(criteria);

		String fromPage = (String) map.get("frompage");
		String startIndex = (String) map.get("startIndex");
		String customerIndex = (String) map.get("customerIndex");

		String countryFilter = (String) map.get("countryFilter");
		final String allowedCountry = (String) map.get("allowedCountry");
		
		
		IDiaryDao diaryDao =(IDiaryDao)BeanHouse.get("diaryDao");
		SearchResult diaryItemList = new SearchResult();
		diaryItemList = diaryDao.getAllDiaryItemList(IDiaryDao.ACTUAL_DIARY_ITEM_NAME);
		
		int genericTodayCount = 0;
		int droplineTodayCount = 0;
		int overdueTodayCount = 0;
		
		IGeneralParamDao generalParamDao =(IGeneralParamDao)BeanHouse.get("generalParamDao");
		IGeneralParamGroup generalParamGroup  =generalParamDao.getGeneralParamGroupByGroupType("actualGeneralParamGroup", "GENERAL_PARAM");
		IGeneralParamEntry[]generalParamEntries= generalParamGroup.getFeedEntries();
		Date dateApplication=new Date();
		long ladGenIndicator=0l;
		for(int i=0;i<generalParamEntries.length;i++){
			if(generalParamEntries[i].getParamCode().equals("APPLICATION_DATE")){
				dateApplication=new Date(generalParamEntries[i].getParamValue());
			}
		}
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
		
		ITeam team = (ITeam) map.get(IGlobalConstant.USER_TEAM);
		Long teamID = team.getTeamID();
		
		try {
			genericTodayCount  = diaryDao.getGenericTodayCount(dateFormat.format(dateApplication).toString(),teamID);
			droplineTodayCount = diaryDao.getDroplineTodayCount(dateFormat.format(dateApplication).toString(),teamID);
			overdueTodayCount  = diaryDao.getOverdueDroplineTodayCount(dateFormat.format(dateApplication).toString(),teamID)+diaryDao.getOverdueGenericTodayCount(dateFormat.format(dateApplication).toString(),teamID);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
		
		CommonCodeList  commonCodeSegmentList = CommonCodeList.getInstance(CategoryCodeConstant.HDFC_SEGMENT);
		
		Iterator itr = commonCodeSegmentList.getCommonCodeLabels().iterator();
		String[] segmentList = new String[commonCodeSegmentList.getCommonCodeLabels().size()];
		int counter=0;
	    while (itr.hasNext()) {
	    		segmentList[counter] = (String)itr.next();
	    		counter=counter+1;
	    }
	    String [][] segmentWiseTodayList = new String[commonCodeSegmentList.getCommonCodeLabels().size()][6];
		
		if (commonCodeSegmentList != null && commonCodeSegmentList.getCommonCodeLabels() != null
		        && commonCodeSegmentList.getCommonCodeLabels().size() > 0) {
		     
		     for(int i=0;i< commonCodeSegmentList.getCommonCodeLabels().size();i++){
		    		 segmentWiseTodayList[i][0] = Integer.toString(i+1);
		    		 segmentWiseTodayList[i][1] = segmentList[i];
		    		 
		    		 int segmentGenericCount = 0;
		    		 int segmentDroplineODCount = 0;
		    		 int segmentOverdueCount = 0;
					try {
						
						segmentGenericCount = diaryDao.getSegmentGenericTodayCount(segmentList[i], dateFormat.format(dateApplication).toString(),teamID);
						segmentDroplineODCount = diaryDao.getSegmentDroplineTodayCount(segmentList[i], dateFormat.format(dateApplication).toString(),teamID);
						segmentOverdueCount = diaryDao.getOverdueSegmentDroplineTodayCount(segmentList[i], dateFormat.format(dateApplication).toString(),teamID) + diaryDao.getOverdueSegmentGenericTodayCount(segmentList[i], dateFormat.format(dateApplication).toString(),teamID);
					} catch (Exception e) {
						e.printStackTrace();
					}
		    		 
		    		 if(segmentGenericCount == 0){
		    			 segmentWiseTodayList[i][2] = "-";
		    		 }else{
		    			 segmentWiseTodayList[i][2] = Integer.toString(segmentGenericCount);
		    		 }
		    		 
		    		 if(segmentDroplineODCount == 0){
		    			 segmentWiseTodayList[i][3] = "-";
		    		 }else{
		    			 segmentWiseTodayList[i][3] = Integer.toString(segmentDroplineODCount);
		    		 }
		    		  
		    		 if(segmentOverdueCount == 0){
		    			 segmentWiseTodayList[i][4] = "-";
		    		 }else{
		    			 segmentWiseTodayList[i][4] = Integer.toString(segmentOverdueCount);
		    		 }
		    		 
		    		 if(segmentGenericCount+segmentDroplineODCount+segmentOverdueCount > 0){
		    			 segmentWiseTodayList[i][5] = Integer.toString(segmentGenericCount+segmentDroplineODCount+segmentOverdueCount);
		    		 }else{
		    			 segmentWiseTodayList[i][5] = "-";
		    		 }
		    		 
		    		 
		     }
		}
		
		if (diaryItemList != null) {
			result.put("totalCountForCurrentTotalPages", new Integer(diaryItemList.getNItems()));
		}
		
		result.put("genericTodayCount", genericTodayCount);
		result.put("droplineTodayCount", droplineTodayCount);
		result.put("overdueTodayCount", overdueTodayCount);
		
		result.put("frompage", fromPage);
		result.put("session.frompage", fromPage);
		result.put("session.startIndex", startIndex);
		result.put("session.customerIndex", customerIndex);
		result.put("session.countryFilter", countryFilter);
		result.put("recordsPerPage", new Integer(DiaryItemListMapper.getDefaultPaginationCount()));
		
		result.put("segmentWiseTodayList", segmentWiseTodayList);
		result.put("diaryItemList", diaryItemList);
		//result.put("totalGenericDiaryItemcount", totalGenericDiaryItemcount);
		//result.put("totalDroplineODDiaryItemcount", totalDroplineODDiaryItemcount);
		
		temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		return temp;
	}

	@Override
	protected SearchResult performSearch(HashMap map) throws DiaryItemException, SearchDAOException {
		return null;
	}

	@Override
	protected SearchResult getGenericListWithSegment(HashMap map,String segmentname) throws DiaryItemException, SearchDAOException {
		return null;
	}

	@Override
	protected SearchResult getGenericListWithoutSegment(HashMap map) throws DiaryItemException, SearchDAOException {
		return null;
	}

	@Override
	protected SearchResult getDropLineListWithSegment(HashMap map,String segmentname) throws DiaryItemException, SearchDAOException {
		return null;
	}

	@Override
	protected SearchResult getDropLineListWithoutSegment(HashMap map) throws DiaryItemException, SearchDAOException {
		return null;
	}

	@Override
	protected SearchResult getTotalListWithSegment(HashMap map,String segmentname) throws DiaryItemException, SearchDAOException {
		return null;
	}

	@Override
	protected SearchResult getTodayGenericListWithSegment(HashMap map, String segmentName)
			throws DiaryItemException, SearchDAOException {
		return null;
	}

	@Override
	protected SearchResult getTodayGenericListWithoutSegment(HashMap map)
			throws DiaryItemException, SearchDAOException {
		return null;
	}

	@Override
	protected SearchResult getTodayDropLineListWithSegment(HashMap map, String segmentName)
			throws DiaryItemException, SearchDAOException {
		return null;
	}

	@Override
	protected SearchResult getTodayDropLineListWithoutSegment(HashMap map)
			throws DiaryItemException, SearchDAOException {
		return null;
	}

	@Override
	protected SearchResult getTodayTotalListWithSegment(HashMap map, String segmentName)
			throws DiaryItemException, SearchDAOException {
		return null;
	}

	@Override
	protected SearchResult getTodayTotalListWithoutSegment(HashMap map) throws DiaryItemException, SearchDAOException {
		return null;
	}

	@Override
	protected SearchResult getTodayOverDueListWithSegment(HashMap map, String segmentName)
			throws DiaryItemException, SearchDAOException {
		return null;
	}

	@Override
	protected SearchResult getTodayOverDueListWithoutSegment(HashMap map) throws DiaryItemException, SearchDAOException {
		return null;
	}
	
}
