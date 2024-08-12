/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/customer/ListCustomerCommand.java,v 1.17 2005/05/12 12:58:51 jtan Exp $
 */

package com.integrosys.cms.ui.diaryitem;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import org.apache.struts.util.LabelValueBean;
import org.springframework.jdbc.core.RowMapper;

import com.ibm.db2.jcc.t4.ob;
import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.collateral.bus.CollateralDAOFactory;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.customer.bus.CustomerException;
import com.integrosys.cms.app.customer.bus.CustomerSearchCriteria;
import com.integrosys.cms.app.customer.bus.ICMSCustomer;
import com.integrosys.cms.app.customer.bus.IContact;
import com.integrosys.cms.app.customer.bus.OBCustomerSearchResult;
import com.integrosys.cms.app.customer.proxy.CustomerProxyFactory;
import com.integrosys.cms.app.customer.proxy.ICustomerProxy;
import com.integrosys.cms.app.diary.bus.IDiaryDao;
import com.integrosys.cms.app.diary.bus.IDiaryItem;
import com.integrosys.cms.app.diary.bus.IDiaryItemJdbc;
import com.integrosys.cms.app.diary.bus.OBDiaryItem;
import com.integrosys.cms.app.diary.proxy.IDiaryItemProxyManager;
import com.integrosys.cms.app.transaction.OBTrxContext;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;
import com.integrosys.cms.ui.manualinput.CommonUtil;
import com.integrosys.cms.ui.manualinput.security.MISecurityUIHelper;
import com.integrosys.component.bizstructure.app.bus.ITeam;
import com.integrosys.component.user.app.bus.OBSearchCommonUser;
import com.integrosys.cms.app.customer.bus.OBCMSCustomer;
import com.integrosys.cms.app.customer.bus.OBContact;
import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.techinfra.dbsupport.JdbcTemplateAdapter;
/**
 * This class is used to list the customer details based on some search
 * contsraints
 * 
 * @author $Author: jtan $<br>
 * @version $Revision: 1.17 $
 * @since $Date: 2005/05/12 12:58:51 $ Tag: $Name: $
 */
public class SubmitCustomerCommand extends AbstractCommand {
	/**
	 * Default Constructor
	 */
	public SubmitCustomerCommand() {

	}
	
	private IDiaryItemProxyManager diaryItemProxyManager;

	public IDiaryItemProxyManager getDiaryItemProxyManager() {
		return diaryItemProxyManager;
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
				{ IGlobalConstant.USER_TEAM, "com.integrosys.component.bizstructure.app.bus.ITeam", GLOBAL_SCOPE },
				{ "customerSearchCriteria", "com.integrosys.cms.app.customer.bus.CustomerSearchCriteria", FORM_SCOPE },
				{ "customerSearchCriteria1", "com.integrosys.cms.app.customer.bus.CustomerSearchCriteria",SERVICE_SCOPE },
				{ "theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE },
				{ "event", "java.lang.String", REQUEST_SCOPE },
				{ "1", "java.lang.String", REQUEST_SCOPE },
				{ "customerID", "java.lang.String", REQUEST_SCOPE },
				{ "hiddenItemID", "java.lang.String", REQUEST_SCOPE },
				{ "customerList", "com.integrosys.base.businfra.search.SearchResult", SERVICE_SCOPE },
				{ "indicator", "java.lang.String", REQUEST_SCOPE },
				{ "diaryItemObj","com.integrosys.cms.app.diary.bus.OBDiaryItem", SERVICE_SCOPE },
				{ "facilityBoardCategorylist", "java.util.List", SERVICE_SCOPE },
				{ "facilityLineNoList", "java.util.List", SERVICE_SCOPE },
				{ "facilitySerialNoList", "java.util.List", SERVICE_SCOPE },
				{ "activityList", "java.util.List", SERVICE_SCOPE },
				
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
//				{ "customerList", "com.integrosys.base.businfra.search.SearchResult", FORM_SCOPE },
				{ IGlobalConstant.GLOBAL_CUSTOMERSEARCHCRITERIA_OBJ,"com.integrosys.cms.app.customer.bus.CustomerSearchCriteria", GLOBAL_SCOPE },
				{ "customerSearchCriteria1", "com.integrosys.cms.app.customer.bus.CustomerSearchCriteria",SERVICE_SCOPE },
				{ "customerObject", "com.integrosys.cms.app.customer.bus.OBCustomerSearchResult",SERVICE_SCOPE },
				{ "diaryItemObj","com.integrosys.cms.app.diary.bus.OBDiaryItem", FORM_SCOPE },
				{ "facilityBoardCategorylist", "java.util.List", SERVICE_SCOPE },
				{ "facilityLineNoList", "java.util.List", SERVICE_SCOPE },
				{ "facilitySerialNoList", "java.util.List", SERVICE_SCOPE },
				{ "activityList", "java.util.List", SERVICE_SCOPE },
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
		String id=(String)map.get("1");
		String customerID=(String)map.get("customerID");
		String thiddenItemID=(String)map.get("hiddenItemID");
		int hiddenItemID=Integer.parseInt(thiddenItemID);
		int selectId=hiddenItemID-1;
		OBTrxContext theOBTrxContext = (OBTrxContext) map.get("theOBTrxContext");
		SearchResult sr=(SearchResult)map.get("customerList");
		ArrayList resultList = new ArrayList();
		resultList= (ArrayList) sr.getResultList();
		OBCustomerSearchResult searchResult =(OBCustomerSearchResult)resultList.get(selectId);
		IDiaryItemJdbc diaryItemJdbc=(IDiaryItemJdbc)BeanHouse.get("diaryItemJdbc");
		List regionAndSegment = diaryItemJdbc.getRegionAndSegment(searchResult.getLegalReference());
		OBDiaryItem obdiaryItem = (OBDiaryItem)map.get("diaryItemObj");
		obdiaryItem.setCustomerReference(searchResult.getLegalReference());
		List facilityLineNoList = new ArrayList<String>();
		List facilitySerialNoList = new ArrayList<String>();
		if (regionAndSegment != null) {
		for (int i = 0; i < regionAndSegment.size(); i++) {
			String[] codeLst = (String[]) regionAndSegment.get(i);
			obdiaryItem.setCustomerSegment(codeLst[0]);
			obdiaryItem.setCustomerSegmentName(codeLst[1]);
		}
		}
		
		ICMSCustomer customerOB = CustomerProxyFactory.getProxy().getCustomerByCIFSourceFromDB(searchResult.getLegalReference(), null);

		IContact[] contact = customerOB.getCMSLegalEntity()
				.getRegisteredAddress();
		OBContact address = new OBContact();
		if(contact!= null && !contact.equals("")){
			for (int i = 0; contact.length > i; i++) {
				if (contact[i].getContactType().equals("CORPORATE")) {
					address = (OBContact) contact[i];
				}
				
			}
		}
		String regionName = null;
		String regionId = address.getRegion();
		if(regionId!=null && !regionId.equals(""))
			 regionName = diaryItemJdbc.getRegionName(regionId);
		
		
		
		obdiaryItem.setRegion(regionName);
		
		try {
			//ICustomerProxy custproxy = CustomerProxyFactory.getProxy();
			//SearchResult sr = custproxy.searchCustomer(searchCriteria);

			result.put("customerList", sr);
			result.put("customerObject", searchResult);
			result.put("facilityBoardCategorylist", getListOfFacilityBoardCategory(diaryItemJdbc,searchResult.getLegalReference()));
			result.put("facilityLineNoList",facilityLineNoList);
			result.put("facilitySerialNoList",facilitySerialNoList);
			//result.put(IGlobalConstant.GLOBAL_CUSTOMERSEARCHCRITERIA_OBJ, searchCriteria);
			//result.put("customerSearchCriteria1", searchCriteria);
		}
		catch (Exception e) {
			CommandProcessingException cpe = new CommandProcessingException("failed to search customer using search criteria");
			cpe.initCause(e);
			throw cpe;
		}
		
		result.put("diaryItemObj", map.get("diaryItemObj"));
		
		temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		return temp;
	}

	

	private boolean isEmptySearchCriteria(CustomerSearchCriteria criteria) {
		if (isNotEmptyStr(criteria.getCustomerName())) {
			return false;
		}
		if (isNotEmptyStr(criteria.getLegalID())) {
			return false;
		}
		// if (isNotEmptyStr(criteria.getLeIDType())) {
		// return false;
		// }
		if (isNotEmptyStr(criteria.getIdNO())) {
			return false;
		}
		if (isNotEmptyStr(criteria.getAaNumber())) {
			return false;
		}
		if (isNotEmptyStr(criteria.getDocBarCode())) {
			return false;
		}
		return true;
	}

	private boolean isNotEmptyStr(String str) {
		if (str == null) {
			return false;
		}
		if (str.trim().equals("")) {
			return false;
		}
		return true;
	}
	
	private List getListOfFacilityBoardCategory(IDiaryItemJdbc diaryItemJdbc,String customerName) {
		List lbValList = new ArrayList();
		try {
				//MISecurityUIHelper helper = new MISecurityUIHelper();
				List facilityBoardCategory = diaryItemJdbc.getListOfFacilityBoardCategory(customerName);
				
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
	
	

}
