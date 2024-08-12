/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/collateral/assetbased/assetpostdatedchqs/AddChequeCommand.java,v 1.2 2005/08/26 10:12:37 hshii Exp $
 */

package com.integrosys.cms.ui.collateral.assetbased.assetgencharge;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import org.apache.struts.util.LabelValueBean;

import com.integrosys.base.businfra.search.SearchDAOException;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.collateral.bus.CollateralDAOFactory;
import com.integrosys.cms.app.collateral.bus.CollateralException;
import com.integrosys.cms.app.collateral.bus.type.asset.subtype.gcharge.IGeneralCharge;
import com.integrosys.cms.app.collateral.bus.type.asset.subtype.gcharge.IGeneralChargeDetails;
import com.integrosys.cms.app.collateral.bus.type.asset.subtype.gcharge.IGeneralChargeStockDetails;
import com.integrosys.cms.app.collateral.proxy.CollateralProxyFactory;
import com.integrosys.cms.app.collateral.proxy.ICollateralProxy;
import com.integrosys.cms.app.collateral.trx.ICollateralTrxValue;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.feed.bus.forex.ForexFeedGroupException;
import com.integrosys.cms.app.feed.proxy.forex.IForexFeedProxy;
import com.integrosys.cms.app.geography.bus.NoSuchGeographyException;
import com.integrosys.cms.app.geography.city.proxy.ICityProxyManager;
import com.integrosys.cms.app.geography.city.trx.ICityTrxValue;
import com.integrosys.cms.app.geography.country.bus.ICountry;
import com.integrosys.cms.app.systemBankBranch.bus.ISystemBankBranch;
import com.integrosys.cms.ui.common.UIUtil;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;
import com.integrosys.cms.ui.manualinput.CommonUtil;
import com.integrosys.cms.ui.manualinput.security.MISecurityUIHelper;

/**
 * @author $Author: hshii $<br>
 * @version $Revision: 1.2 $
 * @since $Date: 2005/08/26 10:12:37 $ Tag: $Name: $
 */

public class RefreshFilterLocationsCommand extends AbstractCommand {
	
	private IForexFeedProxy forexFeedProxy;
	/**
	 * @return the forexFeedProxy
	 */
	public IForexFeedProxy getForexFeedProxy() {
		return (IForexFeedProxy)BeanHouse.get("forexFeedProxy");
	}

	/**
	 * @param forexFeedProxy the forexFeedProxy to set
	 */
	public void setForexFeedProxy(IForexFeedProxy forexFeedProxy) {
		this.forexFeedProxy = forexFeedProxy;
	}
	
	public String[][] getParameterDescriptor() {
		return (new String[][] { { "form.stockDetailsObject", "java.lang.Object", FORM_SCOPE },
				{ "serviceColObj", "com.integrosys.cms.app.collateral.trx.ICollateralTrxValue", SERVICE_SCOPE },
				{ "serviceStockDetailsList",  "java.util.List", SERVICE_SCOPE },
				{ com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY, "java.util.Locale", GLOBAL_SCOPE },
				{ "subtype", "java.lang.String", REQUEST_SCOPE },
				{ "event", "java.lang.String", REQUEST_SCOPE },
				{ "dueDate", "java.lang.String", REQUEST_SCOPE },
				{ "parentPageFrom", "java.lang.String", REQUEST_SCOPE },
				{ "hdnPreviousDueDate", "java.lang.String", REQUEST_SCOPE },

				{ IGlobalConstant.GLOBAL_CUSTOMER_OBJ, "com.integrosys.cms.app.customer.bus.ICMSCustomer", GLOBAL_SCOPE },
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
		return (new String[][] {
				{ "serviceColObj", "com.integrosys.cms.app.collateral.trx.ICollateralTrxValue", SERVICE_SCOPE },
				{ "filterLocationList",  "java.util.List", REQUEST_SCOPE },
				{ "subtype", "java.lang.String", REQUEST_SCOPE },
				{ "calculatedDP", "java.lang.String", REQUEST_SCOPE },
				
				{ "totalLonable", "java.lang.String", REQUEST_SCOPE },
				{ "migrationFlag", "java.lang.String", REQUEST_SCOPE },
				{ "hdnPreviousDueDate", "java.lang.String", REQUEST_SCOPE },

				//{ "fundedShare", "java.lang.String", SERVICE_SCOPE },
//				{ "fundedShare", "java.lang.String", SERVICE_SCOPE },
				{ "statementName", "java.lang.String", SERVICE_SCOPE },
				{ "isEditable", "java.lang.String", SERVICE_SCOPE },
				{ "serviceStockDetailsList",  "java.util.List", SERVICE_SCOPE },
				{ "displayList",  "java.util.List", SERVICE_SCOPE },
				{ "parentPageFrom", "java.lang.String", SERVICE_SCOPE },
				{ "dueDate", "java.lang.String", SERVICE_SCOPE },
				{ "collateralList", "java.util.List", REQUEST_SCOPE },
				{ "event", "java.lang.String", REQUEST_SCOPE },
				{ "collateralList", "java.util.List", SERVICE_SCOPE },
	            { "systemBankBranch", "com.integrosys.cms.app.systemBankBranch.bus.ISystemBankBranch", SERVICE_SCOPE },
	            { "countryNme", "java.lang.String", SERVICE_SCOPE },
	            { "dpShare", "java.lang.String", SERVICE_SCOPE },
	            {"dpCalculateManually", "java.lang.String", SERVICE_SCOPE},
	            { "finalYearList", "java.util.List", SERVICE_SCOPE },
	            { "finalMonthList", "java.util.List", SERVICE_SCOPE },
	            { "finalYearList", "java.util.List", REQUEST_SCOPE },
	            { "finalMonthList", "java.util.List", REQUEST_SCOPE }
				});
	}

	/**
	 * This method does the Business operations with the HashMap and put the
	 * results back into the HashMap.Here reading for Company Borrower is done.
	 * 
	 * @param map is of type HashMap
	 * @throws com.integrosys.base.uiinfra.exception.CommandProcessingException
	 *         on errors
	 * @throws com.integrosys.base.uiinfra.exception.CommandValidationException
	 *         on errors
	 * @return HashMap with the Result
	 */
	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
//copy the logic for distinct location from ReadAssetGenChargeCommand
//add entry in get nextpage and struts config
//create list.jsp for dropdown
//add the due date list in edit command		
		
		HashMap result = new HashMap();
		HashMap exceptionMap = new HashMap();
		HashMap temp = new HashMap();
		Locale locale = (Locale) map.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);
		ICollateralTrxValue itrxValue = (ICollateralTrxValue) map.get("serviceColObj");
		IGeneralCharge iAsset = (IGeneralCharge) itrxValue.getStagingCollateral();
		List filterLocationList= new ArrayList();
		String dueDateStr = (String) map.get("dueDate");
		//String event=(String)map.get("event");
		Date dueDateToFilter = null;
		Date latestDueDate = null;
		String docCode="";
		if(dueDateStr!=null&&!"".equals(dueDateStr.trim())){
			dueDateToFilter = DateUtil.convertDate(locale, dueDateStr.split(",")[0]);
			docCode=dueDateStr.split(",")[1];
		}
		

		String migrationFlag =(String) map.get("migrationFlag");
		String remarkByMaker =(String) map.get("remarkByMaker");
		String hdnPreviousDueDate = (String) map.get("hdnPreviousDueDate");
		result.put("hdnPreviousDueDate",hdnPreviousDueDate );

		
		String totalLonable =(String) map.get("totalLonable");
		if(null==totalLonable ||  "".equals(totalLonable)) {
			totalLonable="0";
		}
		
		String statementName ="";
		String isEditable="Y";
		if(docCode!=null&& !"".equals(docCode.trim())){
			ICollateralProxy colProxy = CollateralProxyFactory.getProxy();
			try {
				statementName = colProxy.getStatementNameByDocCode(docCode);
			} catch (SearchDAOException e1) {
				e1.printStackTrace();
			} catch (CollateralException e1) {
				e1.printStackTrace();
			}

			 IGeneralCharge actualCollateral = (IGeneralCharge) itrxValue.getCollateral();
			List existingDueDateList= new ArrayList();
			IGeneralChargeDetails[] actualGeneralChargeDetails = actualCollateral.getGeneralChargeDetails();
			if(actualGeneralChargeDetails!=null ){
				for (int i = 0; i < actualGeneralChargeDetails.length; i++) {
					IGeneralChargeDetails iGeneralChargeDetails = actualGeneralChargeDetails[i];
					if(iGeneralChargeDetails!=null && ("APPROVED").equalsIgnoreCase(iGeneralChargeDetails.getStatus()) 
							&& null != iGeneralChargeDetails.getDueDate()){
					
						if (iGeneralChargeDetails.getDueDate() != null) {
							existingDueDateList.add(iGeneralChargeDetails.getDueDate());
						}
					}
				}
			}
			if(existingDueDateList.size()>=1){
				Collections.sort(existingDueDateList,Collections.reverseOrder());
				latestDueDate = (Date) existingDueDateList.get(0);
			}
			HashMap distinctLocation=new HashMap();
			IGeneralChargeDetails[] existingGeneralChargeDetails = actualCollateral.getGeneralChargeDetails();
			IGeneralChargeDetails existingChargeDetails;
			IGeneralChargeDetails actualChargeDetails;
			IGeneralChargeStockDetails[] existingGeneralChargeStockDetails;
			if(actualGeneralChargeDetails!=null){
			  for (int i = 0; i < actualGeneralChargeDetails.length; i++) {
			 actualChargeDetails = actualGeneralChargeDetails[i];
			// ====================================================added by bharat
			 if(actualChargeDetails!=null && docCode.equals(actualChargeDetails.getDocCode())){
					existingGeneralChargeStockDetails = actualChargeDetails.getGeneralChargeStockDetails();
					if(actualChargeDetails.getCalculatedDP()!=null || "".equals(actualChargeDetails.getCalculatedDP())){
					result.put("calculatedDP", actualChargeDetails.getCalculatedDP());
					}
//					if(actualChargeDetails.getFundedShare()!=null || "".equals(actualChargeDetails.getFundedShare())){
//					result.put("fundedShare", actualChargeDetails.getFundedShare());
//					}
					
					if(actualChargeDetails.getDpShare()!=null || "".equals(actualChargeDetails.getDpShare())){
						result.put("dpShare", actualChargeDetails.getDpShare());
						}
					
					if(null != actualChargeDetails.getRemarkByMaker() || "".equals(actualChargeDetails.getRemarkByMaker())){
						result.put("remarkByMaker", actualChargeDetails.getRemarkByMaker());
						}
					System.out.println("totalLonable================"+totalLonable);
					if(null != migrationFlag &&  !"Y".equalsIgnoreCase(migrationFlag.trim()) ){
					if(null != actualChargeDetails.getTotalLoanable() || "".equals(actualChargeDetails.getTotalLoanable())){
						result.put("totalLonable", actualChargeDetails.getTotalLoanable());
						}
					}
					if(null != actualChargeDetails.getMigrationFlag_DP_CR() || "".equals(actualChargeDetails.getMigrationFlag_DP_CR())){
						result.put("migrationFlag", actualChargeDetails.getMigrationFlag_DP_CR());
						}
					
					//dpCalculateManually
					if(actualChargeDetails.getDpCalculateManually()!=null || "".equals(actualChargeDetails.getDpCalculateManually())){
						result.put("dpCalculateManually", actualChargeDetails.getDpCalculateManually());
						}
				}
		}
			}
			 /* for (int i = 0; i < actualGeneralChargeDetails.length; i++) {
				 actualChargeDetails = actualGeneralChargeDetails[i];
				// ====================================================added by bharat
				 if(actualChargeDetails!=null && actualChargeDetails.getDueDate().equals(latestDueDate) && !(latestDueDate.compareTo(dueDateToFilter)==0)){
						existingGeneralChargeStockDetails = actualChargeDetails.getGeneralChargeStockDetails();
						if(actualChargeDetails.getCalculatedDP()!=null || "".equals(actualChargeDetails.getCalculatedDP())){
						result.put("calculatedDP", actualChargeDetails.getCalculatedDP());
						}
						if(actualChargeDetails.getFundedShare()!=null || "".equals(actualChargeDetails.getFundedShare())){
						result.put("fundedShare", actualChargeDetails.getFundedShare());
						}
					}
			}*/
			
			
			if(existingGeneralChargeDetails!=null){
				for (int i = 0; i < existingGeneralChargeDetails.length; i++) {
					 existingChargeDetails = existingGeneralChargeDetails[i];
					// ====================================================added by bharat
					/* if(existingChargeDetails!=null && existingChargeDetails.getDueDate().equals(latestDueDate)){
							existingGeneralChargeStockDetails = existingChargeDetails.getGeneralChargeStockDetails();
							
							result.put("calculatedDP", existingChargeDetails.getCalculatedDP());
							result.put("fundedShare", existingChargeDetails.getFundedShare());
							break;
						}*/
					 //========================================================
//					if(existingChargeDetails!=null && existingChargeDetails.getDueDate().equals(dueDateToFilter)){
					if(existingChargeDetails!=null && null != existingChargeDetails.getDocCode()  && docCode.equals(existingChargeDetails.getDocCode()) /*&& existingChargeDetails.getDueDate().equals(dueDateToFilter)*/){
						existingGeneralChargeStockDetails = existingChargeDetails.getGeneralChargeStockDetails();
						if(existingGeneralChargeStockDetails!=null) {
							for (int j = 0; j < existingGeneralChargeStockDetails.length; j++) {
								IGeneralChargeStockDetails existingStockDetails = existingGeneralChargeStockDetails[j];
								if(!distinctLocation.containsKey(Long.toString(existingStockDetails.getLocationId()))){
									distinctLocation.put(Long.toString(existingStockDetails.getLocationId()),Long.toString(existingStockDetails.getLocationId()));
								}
							}
						}
						
						break;
					}
				}
			}
			
					
			Collection values = distinctLocation.values();
			List addedLocations= new ArrayList();
			addedLocations.addAll(values);
			
			ICityProxyManager proxy= (ICityProxyManager)BeanHouse.get("cityProxy");
			String label;
			String value;

			for (Iterator iterator = addedLocations.iterator(); iterator.hasNext();) {
				long locationID = Long.parseLong((String)iterator.next());
				
				String cityName="";
				if(ICMSConstant.CITY_VALUE_ALL.equals(String.valueOf(locationID))){
					cityName=ICMSConstant.CITY_LABEL_ALL;
				}else{
					try {
							ICityTrxValue cityById = proxy.getCityById(locationID);
							cityName = cityById.getActualCity().getCityName();
					} catch (NoSuchGeographyException e) {
						e.printStackTrace();
					} catch (TrxParameterException e) {
						e.printStackTrace();
					} catch (TransactionException e) {
						e.printStackTrace();
					}
				}
				label=cityName;
				value=Long.toString(locationID);
				LabelValueBean lvBean = new LabelValueBean(label,value);
				filterLocationList.add(lvBean);
			}
		}
		
		String event=(String)map.get("event");
		result.put("isEditable", isEditable);
		result.put("serviceColObj", itrxValue);
		result.put("filterLocationList", filterLocationList);
		result.put("dueDate", dueDateStr);
		
		String countryNme = "";
		ISystemBankBranch systemBankBranch = null;
		BigDecimal exchangeRate = null;
		List collateralList = new ArrayList();
		try { 
			if(itrxValue!=null){
				
				
				
		    exchangeRate = getForexFeedProxy().getExchangeRateWithINR(itrxValue.getCollateral().getCurrencyCode());
		    collateralList = getCollateralCodeList(itrxValue.getStagingCollateral().getCollateralSubType().getSubTypeCode());
		    
		    String branchCode = itrxValue.getStagingCollateral().getSecurityOrganization();
		    String countryCode = itrxValue.getStagingCollateral().getCollateralLocation();
		    
		    systemBankBranch = getSysBankBranchByCuntryAndBranchCode(countryCode, branchCode);
		    
		    countryNme = getCountryNamebyCode(countryCode);
		              
			}
		} catch (ForexFeedGroupException e) {
			DefaultLogger.debug(this, "got exception in doExecute" + e);
			e.printStackTrace();
		} catch (Exception ex) {
			DefaultLogger.debug(this, "got exception in doExecute" + ex);
			ex.printStackTrace();
		}
		
		//For Stock doc Month and Stock Doc year start
		String[] splittArray = null;
		String[] splittString = null;
		String selectedDueDate = "";
		 String selectedDocCode = "";
		 String selectedMonth = "";
		 String selectedYear = "";
		 ArrayList finalMonthList = new ArrayList();
		 ArrayList finalYearList = new ArrayList();
		 
		 boolean flag = false;
		 boolean flagCondition = false;
		 
		if ( null != dueDateStr  && !"".equalsIgnoreCase(dueDateStr) && !"null".equalsIgnoreCase(dueDateStr)){
	         splittArray = dueDateStr.split(",");
	          selectedDueDate = (String) splittArray[0];
	          selectedDocCode = (String) splittArray[1];
	    }
		
		if ( null != selectedDueDate  && !"".equalsIgnoreCase(selectedDueDate) && !"null".equalsIgnoreCase(selectedDueDate)){
			splittString = selectedDueDate.split("/");
			if(splittString.length == 3) {
				selectedMonth = (String) splittString[1];
				selectedYear = (String) splittString[2];
				flagCondition = true;
			}
	    }
		//Arrays.asList
		List<String> months = Arrays.asList("Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec");
		
		if(flagCondition == true) {
		
		int index = months.indexOf(selectedMonth);
		System.out.println("Selected months index=>"+index);
		int checkIndex = index + 6;
		int cnt = 0;
		if(checkIndex > 11) {
			cnt = checkIndex - 11;
			cnt = cnt - 1;
		}else {
			cnt = checkIndex;
		}
		for(int i=0;i<6;i++) {
			
			finalMonthList.add(months.get(cnt));
			cnt++;
			if(cnt == 12) {
				cnt = 0;
				flag = true;
			}
		}
		
		int year = Integer.parseInt(selectedYear);
		if(index == 0) {
			year = year - 1;
			finalYearList.add(year);
		}else if(flag == true){
			finalYearList.add(year);
			year = year - 1;
			finalYearList.add(year);
		}else {
			finalYearList.add(year);
		}
		}
		
		result.put("finalYearList",finalYearList);
		result.put("finalMonthList", finalMonthList);
		//For Stock doc Month and Stock Doc year End
		
		if("filter_locations".equals(event))
			result.put("displayList", new ArrayList());
		
		result.put("subtype", map.get("subtype"));
		result.put("statementName",statementName);
		result.put("serviceStockDetailsList",new ArrayList());
		result.put("parentPageFrom", map.get("parentPageFrom"));
		
		result.put("collateralList", collateralList);
		result.put("systemBankBranch", systemBankBranch);
		result.put("countryNme", countryNme);
		result.put("event", event);

		DefaultLogger.debug(this, "inside addChequeCommand");
		temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		return temp;
	}
	
	
	//Add By Govind S:Get collateral code with desc,05/09/2011
		private List getCollateralCodeList(String subTypeValue) {
			List lbValList = new ArrayList();
			try {
				if (subTypeValue != null) {
					MISecurityUIHelper helper = new MISecurityUIHelper();
					List colCodeLst = helper.getSBMISecProxy().getCollateralCodeBySubTypes(subTypeValue);
					if (colCodeLst != null) {
						
						for (int i = 0; i < colCodeLst.size(); i++) {
							String[] codeLst = (String[]) colCodeLst.get(i);
							String code = codeLst[0];
							String name = codeLst[1];
							LabelValueBean lvBean = new LabelValueBean(UIUtil.replaceSpecialCharForXml(name), UIUtil
									.replaceSpecialCharForXml(code));
							lbValList.add(lvBean);
						}
					}
				}
			}
			catch (Exception ex) {
			}
			return CommonUtil.sortDropdown(lbValList);
		}
		
		private ISystemBankBranch getSysBankBranchByCuntryAndBranchCode(String country , String branchCode) {
			ISystemBankBranch branch = null;
			try {
					MISecurityUIHelper helper = new MISecurityUIHelper();
					 branch = CollateralDAOFactory.getDAO().getSysBankBranchByCuntryAndBranchCode(country , branchCode);
			}
			catch (Exception ex) {
			}
			return branch;
		}
		
		private String getCountryNamebyCode(String countryCode) {
			List lbValList = new ArrayList();
			String value = null;
			try {
					MISecurityUIHelper helper = new MISecurityUIHelper();
					ICountry[] country = CollateralDAOFactory.getDAO().getCountryNamebyCode(countryCode);
					
					
					if (country != null) {
						for (int i = 0; i < country.length; i++) {
							ICountry lst = country[i];
							String id = lst.getCountryCode();
							value = lst.getCountryName();
							LabelValueBean lvBean = new LabelValueBean(value, id);
							
							lbValList.add(lvBean);
						}
					}
			}
			catch (Exception ex) {
			}
			return value;
		}		
}
