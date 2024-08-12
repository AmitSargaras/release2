/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/diaryitem/ReadDiaryItemCommand.java,v 1.5 2005/05/10 10:22:09 wltan Exp $
 */
package com.integrosys.cms.ui.diaryitem;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

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

/**
 * This command class retrieves a diary item for display/editing
 * 
 * @author $Author: wltan $<br>
 * @version $Revision: 1.5 $
 * @since $Date: 2005/05/10 10:22:09 $ Tag: $Name: $
 */

public class ReadDiaryItemCommand extends DiaryItemsCommand {
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
				{ "itemDueDate", "java.lang.String", REQUEST_SCOPE },
				
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
				{ "itemDueDate", "java.lang.String", REQUEST_SCOPE },
				{ "activityList", "java.util.List", REQUEST_SCOPE },
				{ "activityList", "java.util.List", SERVICE_SCOPE },
				{ "facilityBoardCategoryList", "java.util.List", REQUEST_SCOPE },
				{ "facilityBoardCategoryList", "java.util.List", SERVICE_SCOPE },
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
				listOfRecrods=getODSchedule(diaryNumber);
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
			String overDueCount = (String) map.get("overDueCount");
			String strItemID = (String) map.get("itemId");
			String itemDueDate = (String) map.get("itemDueDate");
			if (isNull(strItemID)) {
				throw new CommandProcessingException("Error obtaining ID from request");
			}
			
			String action = (String)map.get("action");
			String nextTargetDate = (String)map.get("nextTargetDate");
						
			long itemID = Long.parseLong(strItemID);
			IDiaryItemProxyManager proxyMgr = getDiaryItemProxyManager();
			IDiaryItem item = proxyMgr.getDiaryItem(itemID);
			if(null != item.getDueDate()) {
			itemDueDate = new SimpleDateFormat("dd/MMM/yyyy").format(item.getDueDate());
			}
			if(action != null) {
			item.setAction(action);
			}
			
			IGeneralParamDao generalParamDao =(IGeneralParamDao)BeanHouse.get("generalParamDao");
			IGeneralParamGroup generalParamGroup  =generalParamDao.getGeneralParamGroupByGroupType("actualGeneralParamGroup", "GENERAL_PARAM");
			IGeneralParamEntry[]generalParamEntries= generalParamGroup.getFeedEntries();
			Date dateApplication=new Date();
			for(int i=0;i<generalParamEntries.length;i++){
				if(generalParamEntries[i].getParamCode().equals("APPLICATION_DATE")){
					dateApplication=new Date(generalParamEntries[i].getParamValue());
				}
			}
			
			if("Y".equals(item.getDropLineOD())) {
				List monthAndClosingAmount =  null;
				if(!"Y".equals(overDueCount)) {
				Date DueDate = new SimpleDateFormat("yyyy-MM-dd").parse(itemDueDate);
				 monthAndClosingAmount = diaryItemJdbc.getMonthAndClosingAmountForOD(new SimpleDateFormat("dd/MM/yyyy").format(DueDate),item.getDiaryNumber());
				}else {
					Date DueDate = new SimpleDateFormat("yyyy-MM-dd").parse(itemDueDate);
					 monthAndClosingAmount = diaryItemJdbc.getMonthAndClosingAmountForOD(new SimpleDateFormat("dd/MM/yyyy").format(DueDate),item.getDiaryNumber());
				}
					for (int i = 0; i < monthAndClosingAmount.size(); i++) {
					String[] str = (String[]) monthAndClosingAmount.get(i);
					item.setMonth(str[0]);
					item.setClosingAmount(str[1]);
				}
				//Start Release Amount validation
				if("prepare_update".equals(event)) {
					String partyId=item.getCustomerReference();
					String facilityCat=item.getFacilityBoardCategory();
					String lineNo=item.getFacilityLineNo();
					String serialNo=item.getFacilitySerialNo();
					String releaseAmount=getAmounts(partyId,facilityCat,lineNo,serialNo);
					String closingAmount=item.getClosingAmount();
					result.put("releaseAmount", releaseAmount);
					result.put("closingAmount", closingAmount);
				}
				//End Release Amount validation
			}
			if(!("Y".equals(item.getDropLineOD()) || nextTargetDate == null)) {
				item.setNextTargetDate(DateUtil.convertDate(nextTargetDate));
				}
			
			ICommonUser user = (ICommonUser) map.get(IGlobalConstant.USER);
			List segementCodeAndSegmentName = diaryItemJdbc.getRegionAndSegment(item.getCustomerReference());
			
			if (segementCodeAndSegmentName != null) {
				for (int i = 0; i < segementCodeAndSegmentName.size(); i++) {
					String[] codeLst = (String[]) segementCodeAndSegmentName.get(i);
					item.setCustomerSegment(codeLst[0]);
					item.setCustomerSegmentName(codeLst[1]);
				}
				}
			
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
			item.setCloseBy(user.getLoginID()+" "+dateFormat.format(DateUtil.getDate()));
			item.setCloseDate(dateFormat.format(dateApplication));
			String genericCount = (String) map.get("genericCount");
			String odCount = (String) map.get("odCount");
			String totalCount = (String) map.get("totalCount");
			String segment = (String) map.get("segment");
			String segmentName = (String) map.get("segmentName");
			String searchLeID = (String) map.get("searchLeID");
			String searchCustomerName = (String) map.get("searchCustomerName"); 
			
			//String totalCountForCurrentTotalPages = (String) map.get("totalCountForCurrentTotalPages");
			SearchResult searchResult = (SearchResult) map.get("DiaryItemList");
			result.put("closeBy",user.getLoginID()+" "+dateFormat.format(DateUtil.getDate()));
			result.put("closedate", dateFormat.format(DateUtil.getDate()));
			result.put("actionList", getActionList(diaryItemJdbc));
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
			result.put("itemDueDate", itemDueDate);
			result.put("DiaryItemList", searchResult);
			result.put("activityList", getActivityList(diaryItemJdbc));
			result.put("facilityBoardCategoryList", getFacilityBoardCategory(diaryItemJdbc));
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
	private List getFacilityBoardCategory(IDiaryItemJdbc diaryItemJdbc) {
		List lbValList = new ArrayList();
		try {
				//MISecurityUIHelper helper = new MISecurityUIHelper();
				List facilityBoardCategory = diaryItemJdbc.getFacilityBoardCategory();
				
					for (int i = 0; i < facilityBoardCategory.size(); i++) {
						String [] str = (String[]) facilityBoardCategory.get(i);
						String id = str[0];
						String value = str[1];
						LabelValueBean lvBean = new LabelValueBean(value, id);
						lbValList.add(lvBean);
					}
		}
		catch (Exception ex) {
		}
		return CommonUtil.sortDropdown(lbValList);
	}
	
	private List getActivityList(IDiaryItemJdbc diaryItemJdbc) {
		List lbValList = new ArrayList();
		try {
				//MISecurityUIHelper helper = new MISecurityUIHelper();
				List activityList = diaryItemJdbc.getActivityList();
				
					for (int i = 0; i < activityList.size(); i++) {
						String [] str = (String[]) activityList.get(i);
						String id = str[0];
						String value = str[1];
						LabelValueBean lvBean = new LabelValueBean(value, id);
						lbValList.add(lvBean);
					}
		}
		catch (Exception ex) {
		}
		return CommonUtil.sortDropdown(lbValList);
	}
	
	private List getActionList(IDiaryItemJdbc diaryItemJdbc) {
		List lbValList = new ArrayList();
		try {
				//MISecurityUIHelper helper = new MISecurityUIHelper();
				List activityList = diaryItemJdbc.getActionList();
				
					for (int i = 0; i < activityList.size(); i++) {
						String [] str = (String[]) activityList.get(i);
						String id = str[0];
						String value = str[1];
						LabelValueBean lvBean = new LabelValueBean(value, id);
						lbValList.add(lvBean);
					}
		}
		catch (Exception ex) {
		}
		return CommonUtil.sortDropdown(lbValList);
	}
	
	
	private static boolean isNull(String s) {
		if ((s == null) || (s.trim().length() == 0)) {
			return true;
		}
		return false;
	}
	
	private List<ODSchedule> getODSchedule(String diaryNumber) throws SearchDAOException, DBConnectionException, SQLException{
		
		List<ODSchedule> listOfRecrods = new ArrayList<ODSchedule>();
		DefaultLogger.debug(this,"In getODSchedule");
		
		String sql="SELECT DIARY_NUMBER," + 
				"  TO_CHAR( LOAN_AMOUNT, '999,999,999,999.99') AS LOAN_AMOUNT, " + 
				"  TO_CHAR( INEREST_RATE, '999,999,999,999.99') AS INEREST_RATE, " + 
				"  TO_BE_REDUCED_ON, " + 
				"  TO_CHAR( MONTH, '999,999,999,999') AS MONTH , " + 
				"  TO_CHAR( OP_BAL, '999,999,999,999.99') AS OP_BAL, " + 
				"  TO_CHAR( EMI, '999,999,999,999.99') AS EMI, " + 
				"  TO_CHAR( INTEREST, '999,999,999,999.99') AS INTEREST, " + 
				"  TO_CHAR( PRINCIPAL, '999,999,999,999.99') AS PRINCIPAL, " + 
				"  TO_CHAR( CAL_BAL, '999,999,999,999.99') AS CAL_BAL, " + 
				"  IS_CLOSED " + 
				"FROM CMS_DIARY_SCHEDULE_DATA " + 
				"WHERE DIARY_NUMBER='"+diaryNumber+"' AND MONTH IS NOT NULL " + 
				"ORDER BY(TO_NUMBER(MONTH))";
		
		DefaultLogger.debug(this,"In getODSchedule "+sql);
		DBUtil dbutil=null;
		ResultSet rs=null;
		try{
			dbutil=new DBUtil();
			dbutil.setSQL(sql);
			rs=dbutil.executeQuery();
			while(rs.next()){
				ODSchedule row=new ODSchedule();
				row.setDiaryNumber(rs.getString("DIARY_NUMBER"));
				row.setLoanAmount(rs.getString("LOAN_AMOUNT"));
				row.setInterestRate(rs.getString("INEREST_RATE"));
				row.setToBeReducedOn(rs.getString("TO_BE_REDUCED_ON"));
				row.setMonth(rs.getString("MONTH"));
				row.setOpBal(rs.getString("OP_BAL"));
				row.setEmi(rs.getString("EMI"));
				row.setInterest(rs.getString("INTEREST"));
				row.setPrincipal(rs.getString("PRINCIPAL"));
				row.setCalBal(rs.getString("CAL_BAL"));
				row.setIsClosed(rs.getString("IS_CLOSED"));
				listOfRecrods.add(row);
				DefaultLogger.debug(this,"In getODSchedule listOfRecrods.size()"+listOfRecrods.size());
			}
		}
		catch(Exception e){
			DefaultLogger.debug(this,e.getMessage() );
			e.printStackTrace();
		}
		finally{
			try {
				dbutil.close();
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
		DefaultLogger.debug(this,"In getODSchedule : listOfRecrods.size()= "+listOfRecrods.size());
		return listOfRecrods;
	}
	
	private String getAmounts(String partyId, String facilityCat, String lineNo, String serialNo) throws SearchDAOException, DBConnectionException, SQLException{
		
		String sql="SELECT RELEASED_AMOUNT " + 
				"FROM sci_lsp_sys_xref " + 
				"WHERE LINE_NO            = '"+lineNo+"' " + 
				"AND SERIAL_NO            ='"+serialNo+"' " + 
				"AND cms_lsp_sys_xref_id IN " + 
				"  (SELECT cms_lsp_sys_xref_id " + 
				"  FROM SCI_LSP_lmts_xref_map " + 
				"  WHERE cms_lsp_appr_lmts_id IN " + 
				"    (SELECT cms_lsp_appr_lmts_id " + 
				"    FROM sci_lsp_appr_lmts " + 
				"    WHERE cms_limit_profile_id = " + 
				"      (SELECT cms_lsp_lmt_profile_id " + 
				"      FROM SCI_LSP_LMT_PROFILE " + 
				"      WHERE llp_le_id='"+partyId+"' " + 
				"      ) " + 
				"    AND FACILITY_CAT     = '"+facilityCat+"' " + 
				"    AND LINE_NO          ='"+lineNo+"' " + 
				"    AND CMS_LIMIT_STATUS ='ACTIVE' " + 
				"    ) " + 
				"  )";
		DefaultLogger.debug(this,"In getAmounts "+sql);
		DBUtil dbutil=null;
		ResultSet rs=null;
		try{
			dbutil=new DBUtil();
			dbutil.setSQL(sql);
			rs=dbutil.executeQuery();
			while(rs.next()){
				return rs.getString("RELEASED_AMOUNT");
			}
		}
		catch(Exception e){
			DefaultLogger.debug(this,e.getMessage() );
			e.printStackTrace();
		}
		finally{
			try {
				dbutil.close();
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
		return "";
	}

}
