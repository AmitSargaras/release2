/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/collateral/assetbased/assetpostdatedchqs/AddChequeCommand.java,v 1.2 2005/08/26 10:12:37 hshii Exp $
 */

package com.integrosys.cms.ui.checklist.recurrentDocreceipt;

import java.math.BigDecimal;
import java.util.ArrayList;
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
				 { "no_template_gc", "java.lang.String", SERVICE_SCOPE },
				{ "dueDate", "java.lang.String", REQUEST_SCOPE },
				{ "parentPageFrom", "java.lang.String", REQUEST_SCOPE },
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
//				{ "serviceColObj", "com.integrosys.cms.app.collateral.trx.ICollateralTrxValue", SERVICE_SCOPE },
				{ "filterLocationList",  "java.util.List", REQUEST_SCOPE },
				{ "subtype", "java.lang.String", REQUEST_SCOPE },
				{ "calculatedDP", "java.lang.String", REQUEST_SCOPE },
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
		if (map.get("no_template_gc")!=null &&((String) map.get("no_template_gc")).equals("true")) {
			result.put( "filterLocationList",  "");
			result.put( "subtype", "");
			result.put( "calculatedDP","");
//			result.put( "fundedShare", "");
			
			result.put( "dpShare", "");
			result.put( "statementName", "");
			result.put( "isEditable", "");
			result.put( "serviceStockDetailsList","");
			result.put( "displayList",  "");
			result.put( "parentPageFrom", "");
			result.put( "dueDate","");
			result.put( "collateralList", "");
			result.put( "event", "");
			result.put( "collateralList", "");
			result.put( "systemBankBranch", "");
			result.put( "countryNme", "");

			DefaultLogger.debug(this, "inside addChequeCommand");
			temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
			temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
			return temp;
		}else{
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
					if(iGeneralChargeDetails!=null && ("APPROVED").equalsIgnoreCase(iGeneralChargeDetails.getStatus())){
					existingDueDateList.add(iGeneralChargeDetails.getDueDate());
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
					if(existingChargeDetails!=null && existingChargeDetails.getDocCode().equals(docCode) /*&& existingChargeDetails.getDueDate().equals(dueDateToFilter)*/){
						existingGeneralChargeStockDetails = existingChargeDetails.getGeneralChargeStockDetails();
						for (int j = 0; j < existingGeneralChargeStockDetails.length; j++) {
							IGeneralChargeStockDetails existingStockDetails = existingGeneralChargeStockDetails[j];
							if(!distinctLocation.containsKey(Long.toString(existingStockDetails.getLocationId()))){
								distinctLocation.put(Long.toString(existingStockDetails.getLocationId()),Long.toString(existingStockDetails.getLocationId()));
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
//		result.put("serviceColObj", itrxValue);
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
