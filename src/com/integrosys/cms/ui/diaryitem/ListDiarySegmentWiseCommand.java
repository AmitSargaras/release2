package com.integrosys.cms.ui.diaryitem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import com.integrosys.base.businfra.search.SearchDAOException;
import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.diary.bus.DiaryItemException;
import com.integrosys.cms.app.diary.bus.DiaryItemSearchCriteria;
import com.integrosys.cms.app.diary.bus.IDiaryDao;
import com.integrosys.cms.app.diary.bus.IDiaryItem;
import com.integrosys.cms.app.diary.proxy.IDiaryItemProxyManager;
import com.integrosys.cms.app.generalparam.bus.IGeneralParamDao;
import com.integrosys.cms.ui.collateral.CategoryCodeConstant;
import com.integrosys.cms.ui.common.CommonCodeList;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;
import com.integrosys.component.bizstructure.app.bus.ITeam;

public class ListDiarySegmentWiseCommand extends AbstractListDiaryItemsCommand{
	
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
			{ "segmentWiseList", "java.lang.String", SERVICE_SCOPE },
			{ "totalGenericDiaryItemcount","java.lang.Integer", REQUEST_SCOPE },
			{ "totalDroplineODDiaryItemcount","java.lang.Integer", REQUEST_SCOPE },
			{ "teamID","java.lang.Long", REQUEST_SCOPE },
			
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
		
		ITeam team = (ITeam) map.get(IGlobalConstant.USER_TEAM);
		
		Long teamID = team.getTeamID();
		
		IDiaryDao diaryDao =(IDiaryDao)BeanHouse.get("diaryDao");
		SearchResult diaryItemList = new SearchResult();
		diaryItemList = diaryDao.getAllDiaryItemList(IDiaryDao.ACTUAL_DIARY_ITEM_NAME);
		
		int totalDiaryItemcount = diaryItemList.getNItems();
		
		List totalDiaryItemList = new ArrayList();
		List diaryItemListDropLine = new ArrayList();
		List totalDiaryItemListGeneral = new ArrayList();
		
		if(null != diaryItemList){
			totalDiaryItemList = new ArrayList(diaryItemList.getResultList());
		}
		for(int i=0 ; i < totalDiaryItemList.size(); i++){
				IDiaryItem diaryItem =(IDiaryItem) totalDiaryItemList.get(i);
				if("Y".equals(diaryItem.getDropLineOD())){
					diaryItemListDropLine.add(diaryItem);
				}else
					totalDiaryItemListGeneral.add(diaryItem);
		}
		
		int totalGenericDiaryItemcount = totalDiaryItemListGeneral.size();
		int totalDroplineODDiaryItemcount = diaryItemListDropLine.size();
		
		CommonCodeList  commonCodeSegmentList = CommonCodeList.getInstance(CategoryCodeConstant.HDFC_SEGMENT);
		
		Iterator itr = commonCodeSegmentList.getCommonCodeLabels().iterator();
		String[] segmentList = new String[commonCodeSegmentList.getCommonCodeLabels().size()];
		int counter=0;
	    while (itr.hasNext()) {
	    		segmentList[counter] = (String)itr.next();
	    		counter=counter+1;
	    }
	    String [][] segmentWiseList = new String[commonCodeSegmentList.getCommonCodeLabels().size()][5];
		
		if (commonCodeSegmentList != null && commonCodeSegmentList.getCommonCodeLabels() != null
		        && commonCodeSegmentList.getCommonCodeLabels().size() > 0) {
		     
		     for(int i=0;i< commonCodeSegmentList.getCommonCodeLabels().size();i++){
		    		 segmentWiseList[i][0] = Integer.toString(i+1);
		    		 segmentWiseList[i][1] = segmentList[i];
		    		 
		    		 int segmentGenericCount = 0;
		    		 int segmentDroplineODCount = 0;
		    		 
					try {
						
						segmentGenericCount = diaryDao.getSegmentGenericCount(segmentList[i],teamID);
						segmentDroplineODCount = diaryDao.getSegmentDroplineODCount(segmentList[i],teamID);
						
					} catch (Exception e) {
						e.printStackTrace();
					}
		    		 
		    		 if(segmentGenericCount == 0){
		    			 segmentWiseList[i][2] = "-";
		    		 }else{
		    			 segmentWiseList[i][2] = Integer.toString(segmentGenericCount);
		    		 }
		    		 
		    		 if(segmentDroplineODCount == 0){
		    			 segmentWiseList[i][3] = "-";
		    		 }else{
		    			 segmentWiseList[i][3] = Integer.toString(segmentDroplineODCount);
		    		 }
		    		  
		    		 
		    		 if(segmentGenericCount+segmentDroplineODCount > 0){
		    			 segmentWiseList[i][4] = Integer.toString(segmentGenericCount+segmentDroplineODCount);
		    		 }else{
		    			 segmentWiseList[i][4] = "-";
		    		 }
		    		 
		    		 
		     }
		}
		
		if (diaryItemList != null) {
			result.put("totalCountForCurrentTotalPages", new Integer(diaryItemList.getNItems()));
		}
		
		result.put("teamID", teamID);
		result.put("frompage", fromPage);
		result.put("session.frompage", fromPage);
		result.put("session.startIndex", startIndex);
		result.put("session.customerIndex", customerIndex);
		result.put("session.countryFilter", countryFilter);
		result.put("recordsPerPage", new Integer(DiaryItemListMapper.getDefaultPaginationCount()));
		
		result.put("segmentWiseList", segmentWiseList);
		result.put("diaryItemList", diaryItemList);
		result.put("totalGenericDiaryItemcount", totalGenericDiaryItemcount);
		result.put("totalDroplineODDiaryItemcount", totalDroplineODDiaryItemcount);
		
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
	protected SearchResult getTodayGenericListWithSegment(HashMap map,String segmentname) throws DiaryItemException, SearchDAOException {
		return null;
	}

	@Override
	protected SearchResult getTodayGenericListWithoutSegment(HashMap map) throws DiaryItemException, SearchDAOException {
		return null;
	}

	@Override
	protected SearchResult getTodayDropLineListWithSegment(HashMap map,String segmentname) throws DiaryItemException, SearchDAOException {
		return null;
	}

	@Override
	protected SearchResult getTodayDropLineListWithoutSegment(HashMap map) throws DiaryItemException, SearchDAOException {
		return null;
	}

	@Override
	protected SearchResult getTodayTotalListWithSegment(HashMap map,String segmentname) throws DiaryItemException, SearchDAOException {
		return null;
	}
	
	@Override
	protected SearchResult getTodayTotalListWithoutSegment(HashMap map) throws DiaryItemException, SearchDAOException {
		return null;
	}
	
	@Override
	protected SearchResult getTodayOverDueListWithSegment(HashMap map,String segmentname) throws DiaryItemException, SearchDAOException {
		return null;
	}
	
	@Override
	protected SearchResult getTodayOverDueListWithoutSegment(HashMap map) throws DiaryItemException, SearchDAOException {
		return null;
	}
	
	
}
