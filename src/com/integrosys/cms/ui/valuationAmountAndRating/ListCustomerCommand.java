/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/customer/ListCustomerCommand.java,v 1.17 2005/05/12 12:58:51 jtan Exp $
 */

package com.integrosys.cms.ui.valuationAmountAndRating;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.struts.action.ActionMessage;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.techinfra.dbsupport.DBConnectionException;
import com.integrosys.base.techinfra.dbsupport.DBUtil;
import com.integrosys.base.techinfra.validation.Validator;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ErrorKeyMapper;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.customer.bus.CustomerSearchCriteria;
import com.integrosys.cms.app.customer.bus.OBCustomerSearchResult;
import com.integrosys.cms.app.customer.proxy.CustomerProxyFactory;
import com.integrosys.cms.app.customer.proxy.ICustomerProxy;
import com.integrosys.cms.app.valuationAmountAndRating.bus.OBValuationAmountAndRating;
import com.integrosys.cms.asst.validator.ASSTValidator;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;

/**
 * This class is used to list the customer details based on some search
 * contsraints
 * 
 * @author $Author: jtan $<br>
 * @version $Revision: 1.17 $
 * @since $Date: 2005/05/12 12:58:51 $ Tag: $Name: $
 */
public class ListCustomerCommand extends AbstractCommand {
	/**
	 * Default Constructor
	 */
	public ListCustomerCommand() {

	}

	/**
	 * Defines a two dimensional array with the parameter list to be passed to
	 * the doExecute method by a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ "searchCustomerName", "java.lang.String", REQUEST_SCOPE },
				{ "fromPage", "java.lang.String", REQUEST_SCOPE },
				{ "startIndex", "java.lang.String", REQUEST_SCOPE },
				{ "valuationAmountAndRatingObj","com.integrosys.cms.app.valuationAmountAndRating.bus.OBValuationAmountAndRating",FORM_SCOPE },
				{ IGlobalConstant.GLOBAL_CUSTOMERSEARCHCRITERIA_OBJ,"com.integrosys.cms.app.customer.bus.CustomerSearchCriteria", GLOBAL_SCOPE },
				{ "event", "java.lang.String", REQUEST_SCOPE },
				});
	}

	/**
	 * Defines a two dimensional array with the result list to be expected as a
	 * result from the doExecute method using a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getResultDescriptor() {
		return (new String[][] {
				//{ "customerList", "com.integrosys.base.businfra.search.SearchResult", FORM_SCOPE },
				{ "customerList", "com.integrosys.base.businfra.search.SearchResult", SERVICE_SCOPE },
				{ IGlobalConstant.GLOBAL_CUSTOMERSEARCHCRITERIA_OBJ,"com.integrosys.cms.app.customer.bus.CustomerSearchCriteria", GLOBAL_SCOPE },
				{ "customerSearchCriteria1", "com.integrosys.cms.app.customer.bus.CustomerSearchCriteria",SERVICE_SCOPE }, 
				{"customerNameAndIdList", "java.util.List", REQUEST_SCOPE},
				{"customerNameAndIdList", "java.util.List", SERVICE_SCOPE},
				{ "valuationAmountAndRatingObj","com.integrosys.cms.app.valuationAmountAndRating.bus.OBValuationAmountAndRating", SERVICE_SCOPE },
				{ "event", "java.lang.String", REQUEST_SCOPE },
				});
	}

	/**
	 * This method does the Business operations with the HashMap and put the
	 * results back into the HashMap.Here creation for Company Borrower is done.
	 * 
	 * @param map is of type HashMap
	 * @throws com.integrosys.base.uiinfra.exception.CommandProcessingException
	 *         on errors
	 * @throws com.integrosys.base.uiinfra.exception.CommandValidationException
	 *         on errors
	 * @return HashMap with the Result
	 */
	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
		String indicator = (String) map.get("indicator");
		HashMap result = new HashMap();
		HashMap exceptionMap = new HashMap();
		HashMap temp = new HashMap();
//		OBFilter filter = (OBFilter) map.get("reportFormObj");
//		if(filter.getReportId()!=null)
//			result.put("reportFormObj", filter);
		OBValuationAmountAndRating filter = (OBValuationAmountAndRating) map.get("valuationAmountAndRatingObj");
		result.put("valuationAmountAndRatingObj", filter);
		
		String fromPage = (String) map.get("fromPage");
		String event = (String) map.get("event");
		result.put("event", event);
		
		String startIdx = (String) map.get("startIndex");
		if(startIdx==null){
			startIdx="0";
		}
		CustomerSearchCriteria globalCriteria = (CustomerSearchCriteria)map.get(IGlobalConstant.GLOBAL_CUSTOMERSEARCHCRITERIA_OBJ);
		
		String searchText=(String) map.get("searchCustomerName");
		if(searchText==null && "pagination".equals(fromPage)){
			searchText=globalCriteria.getCustomerName();
		}else{
			String errorCode="";
			if(ASSTValidator.isValidGenericASST(searchText)){
				exceptionMap.put("customerNameError", new ActionMessage("error.string.invalidCharacter"));
			}else if (!(errorCode=Validator.checkString(searchText, true, 3, 50)).equals(Validator.ERROR_NONE)) {
				exceptionMap.put("customerNameError",  new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode),3, 50 + ""));
			}
		}
		
		
		CustomerSearchCriteria searchCriteria= new CustomerSearchCriteria();
		try{
				searchCriteria.setCustomerName(searchText);
				searchCriteria.setStartIndex(Integer.parseInt(startIdx));
				searchCriteria.setNItems(10000);
//				if("RPT0032".equals(filter.getReportId())){
//					searchCriteria.setCustomerStatus(ICMSConstant.CUSTOMER_STATUS_INACTIVE);
//				}else if("RPT0002".equals(filter.getReportId())){
//					searchCriteria.setCustomerStatus(ICMSConstant.CUSTOMER_STATUS_ALL);
//				}
				
				List customerNameAndIdList = new ArrayList();
				List customerNameAndIdListHbm = new ArrayList();
//				customerNameAndIdList = (ArrayList) getCustomerSearchList(searchText);
				
				ICustomerProxy custproxy = CustomerProxyFactory.getProxy();
				SearchResult sr = custproxy.searchCustomer(searchCriteria);
				if(sr != null) {
				customerNameAndIdListHbm=(List) sr.getResultList();
				System.out.println(customerNameAndIdListHbm.get(0));
				OBCustomerSearchResult customerNameAndIdListHbm1 = (OBCustomerSearchResult) customerNameAndIdListHbm.get(0);
				String custName = "";
				String custId = "";
				if(!customerNameAndIdListHbm.isEmpty()) {
					for(int i=0;i<customerNameAndIdListHbm.size();i++) {
						customerNameAndIdListHbm1 = (OBCustomerSearchResult) customerNameAndIdListHbm.get(i);
						custName = (String) customerNameAndIdListHbm1.getCustomerName();
						custId = (String) customerNameAndIdListHbm1.getLegalReference();
						
						List customerdataList = new ArrayList();
						customerdataList.add(custName);
						customerdataList.add(custId);
						if(!customerdataList.isEmpty()) {
							customerNameAndIdList.add(customerdataList);
						}
					}
				}
				}
				
				result.put("customerList", sr);
				result.put("customerNameAndIdList", customerNameAndIdList);
				result.put(IGlobalConstant.GLOBAL_CUSTOMERSEARCHCRITERIA_OBJ, searchCriteria);
				result.put("customerSearchCriteria1", searchCriteria);
			}
			catch (Exception e) {
				CommandProcessingException cpe = new CommandProcessingException("failed to search customer using search criteria '" + searchCriteria + "'");
				cpe.initCause(e);
				throw cpe;
			}


		temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		return temp;
	}
	
	private List getCustomerSearchList(String searchtext) {
		List customerNameAndIdList = new ArrayList();
		String data="";
		String data2="";
		String sql="SELECT LMP_SHORT_NAME,LMP_LE_ID FROM SCI_LE_MAIN_PROFILE WHERE upper(LMP_SHORT_NAME) LIKE '"+searchtext.toUpperCase()+"%' ORDER BY LMP_SHORT_NAME";
		System.out.println("getCustomerSearchList Query =>"+sql);
		DBUtil dbUtil=null;
		try {
			 dbUtil=new DBUtil();
			dbUtil.setSQL(sql);
			ResultSet rs = dbUtil.executeQuery();
			if(null!=rs){
				while(rs.next()){
					List customerdataList = new ArrayList();
					data=rs.getString("LMP_SHORT_NAME");
					data2=rs.getString("LMP_LE_ID");
					customerdataList.add(data);
					customerdataList.add(data2);
					if(!customerdataList.isEmpty()) {
						customerNameAndIdList.add(customerdataList);
					}
				}
			}
			rs.close();
		} catch (DBConnectionException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally{ 
			try {
				dbUtil.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return customerNameAndIdList;
	}

}
