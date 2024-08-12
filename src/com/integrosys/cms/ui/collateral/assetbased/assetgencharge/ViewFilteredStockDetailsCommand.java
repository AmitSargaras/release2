/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/collateral/assetbased/assetgencharge/ReadAssetGenChargeCommand.java,v 1.2 2005/04/19 12:09:45 hshii Exp $
 */
package com.integrosys.cms.ui.collateral.assetbased.assetgencharge;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import org.apache.struts.action.ActionMessage;

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
import com.integrosys.cms.app.collateral.bus.CollateralException;
import com.integrosys.cms.app.collateral.bus.type.asset.subtype.gcharge.IGeneralCharge;
import com.integrosys.cms.app.collateral.bus.type.asset.subtype.gcharge.IGeneralChargeDetails;
import com.integrosys.cms.app.collateral.bus.type.asset.subtype.gcharge.IGeneralChargeStockDetails;
import com.integrosys.cms.app.collateral.proxy.CollateralProxyFactory;
import com.integrosys.cms.app.collateral.proxy.ICollateralProxy;
import com.integrosys.cms.app.collateral.trx.ICollateralTrxValue;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.customer.bus.ICMSCustomer;
import com.integrosys.cms.app.customer.proxy.CustomerProxyFactory;
import com.integrosys.cms.app.customer.proxy.ICustomerProxy;
import com.integrosys.cms.app.geography.bus.NoSuchGeographyException;
import com.integrosys.cms.app.geography.city.proxy.ICityProxyManager;
import com.integrosys.cms.app.geography.city.trx.ICityTrxValue;
import com.integrosys.cms.app.limit.bus.ILimit;
import com.integrosys.cms.app.limit.bus.OBLimit;
import com.integrosys.cms.app.transaction.ITrxContext;
import com.integrosys.cms.ui.collateral.CollateralConstant;
import com.integrosys.cms.ui.common.UIUtil;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;

/**
 * Description
 * 
 * @author $Author: hshii $<br>
 * @version $Revision: 1.2 $
 * @since $Date: 2005/04/19 12:09:45 $ Tag: $Name: $
 */
public class ViewFilteredStockDetailsCommand extends AbstractCommand {
	public String[][] getParameterDescriptor() {
		return (new String[][] { { "from_page", "java.lang.String", SERVICE_SCOPE },
				{ "event", "java.lang.String", REQUEST_SCOPE },
				{ "dueDate", "java.lang.String", REQUEST_SCOPE },
				{ "trxID", "java.lang.String", REQUEST_SCOPE },
				{ "totalLonable", "java.lang.String", REQUEST_SCOPE },
				{ "stockLocation", "java.lang.String", REQUEST_SCOPE },
				{ "form.collateralObject", "com.integrosys.cms.app.collateral.bus.ICollateral", FORM_SCOPE },
				
				{ IGlobalConstant.USER, "com.integrosys.component.user.app.bus.ICommonUser", GLOBAL_SCOPE },
				{ IGlobalConstant.USER_TEAM, "com.integrosys.component.bizstructure.app.bus.ITeam", GLOBAL_SCOPE },
				{ com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY, "java.util.Locale", GLOBAL_SCOPE },
				{ "serviceColObj", "com.integrosys.cms.app.collateral.trx.ICollateralTrxValue", SERVICE_SCOPE }, });
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
				{ "from_page", "java.lang.String", SERVICE_SCOPE },
				{ "tab", "java.lang.String", SERVICE_SCOPE },
//				{ "fundedShare", "java.lang.String", SERVICE_SCOPE },
				
				{ "dpShare", "java.lang.String", SERVICE_SCOPE },
//				{ "isEditable", "java.lang.String", SERVICE_SCOPE },
				{ "calculatedDP", "java.lang.String", REQUEST_SCOPE },
				{ "remarks", "java.lang.String", REQUEST_SCOPE },
				{ "alertRequired",  "java.util.List", SERVICE_SCOPE },
				{ "displayList",  "java.util.List", SERVICE_SCOPE },
				{ "trxID", "java.lang.String", REQUEST_SCOPE },
				{ "serviceColObj", "com.integrosys.cms.app.collateral.trx.ICollateralTrxValue", SERVICE_SCOPE }, 
				{ "dueDateList",  "java.util.List", SERVICE_SCOPE},
				{ "filterLocationList",  "java.util.List", SERVICE_SCOPE },
				{ "loanable", "java.lang.String", REQUEST_SCOPE },
				{ "totalLonable", "java.lang.String", REQUEST_SCOPE },
				
				{ "migrationFlag", "java.lang.String", REQUEST_SCOPE },
				{ "remarkByMaker", "java.lang.String", REQUEST_SCOPE },
				
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
		HashMap result = new HashMap();
		HashMap exceptionMap = new HashMap();
		HashMap temp = new HashMap();

		String migrationFlag =(String) map.get("migrationFlag");
		String remarkByMaker =(String) map.get("remarkByMaker");
		
		String totalLonable =(String) map.get("totalLonable");
		if(null==totalLonable ||  "".equals(totalLonable)) {
			totalLonable="0";
		}
		System.out.println("totalLonable==="+totalLonable);

		String event = (String) map.get("event");
		String trxID = (String) map.get("trxID");
		ICollateralTrxValue itrxValue = (ICollateralTrxValue) map.get("serviceColObj");

		if ( event!=null && !event.endsWith("return")) {
			result.put("from_page", (String) map.get("event"));
		}
		else {
			DefaultLogger.debug(this, "<<<<<<<<<<< readAssetGenChargeCommand - event: " + event);
			ITrxContext ctx = itrxValue.getTrxContext();
            //Andy Wong, 11 Feb 2009: check for ctx not null to fix nullpointer
            if(ctx!=null) {
			    DefaultLogger.debug(this, "<<<<<<<<<<< readAssetGenChargeCommand - remarks: " + ctx.getRemarks());
			    result.put("remarks", ctx.getRemarks());
            }
		}


		//Added by Anil =========Start===============
		String calculatedDPVal= (String) map.get("calculatedDP");
		
//		String fundedShareVal= (String) map.get("fundedShare");
		
		String fundedShareVal= (String) map.get("dpShare");
		BigDecimal fundedShare = new BigDecimal("0");
		BigDecimal calculatedDP = new BigDecimal("0");
		
		BigDecimal loanable = new BigDecimal("0");
		
		String fundedSharePerc= "";
		ICollateralProxy collateralProxy = CollateralProxyFactory.getProxy();
		long customerID=0;
		try {
			String customerIDstr = collateralProxy.getCustomerIDByCollateralID(itrxValue.getCollateral().getCollateralID());
			if(customerIDstr!=null&&!"".equals(customerIDstr.trim())){
				customerID=Long.parseLong(customerIDstr);
			}
		} catch (CollateralException e) {
			e.printStackTrace();
		}
		
		if(customerID>0){
		ICustomerProxy custProxy = CustomerProxyFactory.getProxy();
		ICMSCustomer cust = custProxy.getCustomer(customerID);
//		fundedSharePerc = cust.getFundedSharePercent();
		
		fundedSharePerc = cust.getDpSharePercent();
		}
		
		
		Locale locale = (Locale) map.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);
		Date dueDate=null;
		
		String dueDateStr = (String) map.get("dueDate");
		String docCode = "";
		String docDueDate = "";
		if(dueDateStr!=null&&!"".equals(dueDateStr.trim())){
			String[] split = dueDateStr.split(",");
			dueDate = DateUtil.convertDate(locale, split[0]);
			docDueDate = split[0];
			docCode=split[1];
		}else{
			exceptionMap.put("dueDateError", new ActionMessage("label.please.select.option"));
		}
		if(dueDateStr!=null&&!"".equals(dueDateStr.trim())){
		if(fundedShareVal!=null && !"".equals(fundedShareVal.trim()) && !"0".equals(fundedShareVal.trim())){
//			result.put("fundedShare", fundedShareVal);
			
			result.put("dpShare", fundedShareVal);
			//fundedShareVal="100";
			//fundedShare= new BigDecimal("100");
		}
		else{
			if(fundedSharePerc!=null && !"".equals(fundedSharePerc.trim()) && !"0".equals(fundedSharePerc.trim())){
//				result.put("fundedShare", fundedSharePerc);
				
				result.put("dpShare", fundedSharePerc);
				fundedShareVal = fundedSharePerc;
				//fundedShareVal="100";
				//fundedShare= new BigDecimal("100");
			}else{
//				result.put("fundedShare", "100");
				result.put("dpShare", "100");
				fundedShareVal = "100";
			}
			/*if(customerID==0){
				exceptionMap.put("fundedShareError", new ActionMessage("collatral.mapping.mandatory"));
			}else{
				exceptionMap.put("fundedShareError", new ActionMessage("customer.financialdetail.mandatory"));
		  }*/
		}
		}
		
		String stockLocation=(String) map.get("stockLocation");
		boolean stockLocationFilter=false;
		 if(stockLocation!=null && !"".equals(stockLocation.trim())){
			 stockLocationFilter=true;
		 }
		
		 
		 
		
		 IGeneralCharge newCollateral = (IGeneralCharge) itrxValue.getStagingCollateral();
		    IGeneralCharge actualCollateral = (IGeneralCharge) itrxValue.getCollateral();
		    Date latestDueDate = null;
		    
		 List existingDueDateList= new ArrayList();
			IGeneralChargeDetails[] actualgeneralChargeDetails = actualCollateral.getGeneralChargeDetails();
			if(actualgeneralChargeDetails!=null ){
				for (int i = 0; i < actualgeneralChargeDetails.length; i++) {
					IGeneralChargeDetails iGeneralChargeDetails = actualgeneralChargeDetails[i];
					if(iGeneralChargeDetails!=null && ("APPROVED").equalsIgnoreCase(iGeneralChargeDetails.getStatus())
							&& null != iGeneralChargeDetails.getDueDate()){
					existingDueDateList.add(iGeneralChargeDetails.getDueDate());
					}
				}
			}
			if(existingDueDateList.size()>=1){
				Collections.sort(existingDueDateList,Collections.reverseOrder());
				latestDueDate = (Date) existingDueDateList.get(0);
			}
		
			 BigDecimal releasedAmount=new BigDecimal(0);
				ILimit obLimit=new OBLimit();
				try {
				if(!docDueDate.equals("")){
					if(latestDueDate == null || (latestDueDate!=null && !latestDueDate.after(DateUtil.convertDate(locale,docDueDate)))){
					obLimit= collateralProxy.getReleasableAmountByCollateralID(itrxValue.getCollateral().getCollateralID());
					if(obLimit!=null){
						if(obLimit.getTotalReleasedAmount()!=null){
							releasedAmount =new BigDecimal(obLimit.getTotalReleasedAmount());		
						}
						result.put("alertRequired", obLimit.getIsDP());
					}
					}}
					else{
						result.put("alertRequired", "N");
					}
				} catch (SearchDAOException e) {
					e.printStackTrace();
				} catch (CollateralException e) {
					e.printStackTrace();
				}
			
			IGeneralChargeDetails[] actualGeneralChargeDetails = newCollateral.getGeneralChargeDetails();
			IGeneralChargeDetails actualChargeDetails;
			IGeneralChargeStockDetails[] actualGeneralChargeStockDetails;
			BigDecimal totalLonableForDP1= new BigDecimal(0);
			BigDecimal totalLonableAsset1= new BigDecimal(0);
			boolean appFlag= false;
			BigDecimal totalLonableLiability1= new BigDecimal(0);
			
			if(!existingDueDateList.contains(dueDate)){
				result.put("calculatedDP", calculatedDPVal);
			//	result.put("fundedShare", fundedShareVal);
				result.put("totalLonable", totalLonable);
				
				result.put("dpShare", fundedShareVal);
				}
		/*	if(!existingDueDateList.contains(dueDate)){
			if(actualGeneralChargeDetails!=null){
				for (int i = 0; i < actualGeneralChargeDetails.length; i++) {
					actualChargeDetails = actualGeneralChargeDetails[i];
					 
						 appFlag = true;
					 if(actualChargeDetails!=null && actualChargeDetails.getDueDate().equals(latestDueDate)){
						 actualGeneralChargeStockDetails = actualChargeDetails.getGeneralChargeStockDetails();
							//
						 for (int j = 0; j < actualGeneralChargeStockDetails.length; j++) {
								IGeneralChargeStockDetails existingStockDetails = actualGeneralChargeStockDetails[j];
								if("CurrentAsset".equals(existingStockDetails.getStockType())){
									totalLonableAsset1=totalLonableAsset1.add(new BigDecimal(existingStockDetails.getLonable()));
								}else if("CurrentLiabilities".equals(existingStockDetails.getStockType())){
									totalLonableLiability1=totalLonableLiability1.add(new BigDecimal(existingStockDetails.getLonable()));
								}
							}
						}
					 
					
				}
			}
			if(appFlag){
			totalLonableForDP1=totalLonableForDP1.add((totalLonableAsset1.subtract(totalLonableLiability1)));	
			calculatedDP=totalLonableForDP1.multiply(new BigDecimal(fundedShareVal));
			calculatedDP=calculatedDP.divide(new BigDecimal(100),BigDecimal.ROUND_FLOOR);
			}
			}
			else{
				if(actualGeneralChargeDetails!=null){
					for (int i = 0; i < actualGeneralChargeDetails.length; i++) {
						actualChargeDetails = actualGeneralChargeDetails[i];
						 
						 if(actualChargeDetails!=null && actualChargeDetails.getDueDate().equals(latestDueDate)){
							 actualGeneralChargeStockDetails = actualChargeDetails.getGeneralChargeStockDetails();
								//
							 for (int j = 0; j < actualGeneralChargeStockDetails.length; j++) {
									IGeneralChargeStockDetails existingStockDetails = actualGeneralChargeStockDetails[j];
								if(actualChargeDetails.getCalculatedDP()!=null || "".equals(actualChargeDetails.getCalculatedDP())){
									calculatedDP = new BigDecimal(actualChargeDetails.getCalculatedDP());
								result.put("calculatedDP", actualChargeDetails.getCalculatedDP());
								}
								if(actualChargeDetails.getFundedShare()!=null || "".equals(actualChargeDetails.getFundedShare())){
									fundedShareVal = actualChargeDetails.getFundedShare();
								result.put("fundedShare", actualChargeDetails.getFundedShare());
								}
							 }
						}
					}
				}
				
			}*/
			
				
			 
		//retrieving due dates
	    List retrivedDueDateList=(List)collateralProxy.getRecurrentDueDateListByCustomerAndCollatralID(customerID,0);
	    List dueDateList= new ArrayList();
	    if(retrivedDueDateList!=null && retrivedDueDateList.size()>0){
//	    	Collections.sort(retrivedDueDateList,Collections.reverseOrder());
	    	dueDateList=AssetGenChargeHelper.getDisplayDueDateList(retrivedDueDateList);
	    }
		
		//IGeneralCharge newCollateral = (IGeneralCharge) itrxValue.getStagingCollateral();
		
		List displayList= new ArrayList();
		String isEditable="N";
		BigDecimal totalLonableForDP= new BigDecimal(0);
		BigDecimal totalLonableAsset= new BigDecimal(0);
		BigDecimal totalLonableLiability= new BigDecimal(0);
			if(dueDate!=null){
			HashMap distinctLocation=new HashMap();
			IGeneralChargeDetails[] existingGeneralChargeDetails = newCollateral.getGeneralChargeDetails();
			IGeneralChargeDetails existingChargeDetails;
			IGeneralChargeStockDetails[] existingGeneralChargeStockDetails;
			if(existingGeneralChargeDetails!=null){
				for (int i = 0; i < existingGeneralChargeDetails.length; i++) {
					 existingChargeDetails = existingGeneralChargeDetails[i];
					if(existingChargeDetails!=null && docCode.equals(existingChargeDetails.getDocCode())&& dueDate.equals(existingChargeDetails.getDueDate())
							&& existingChargeDetails.getGeneralChargeStockDetails() != null){
						existingGeneralChargeStockDetails = existingChargeDetails.getGeneralChargeStockDetails();
						for (int j = 0; j < existingGeneralChargeStockDetails.length; j++) {
							IGeneralChargeStockDetails existingStockDetails = existingGeneralChargeStockDetails[j];
							if(!distinctLocation.containsKey(Long.toString(existingStockDetails.getLocationId()))){
								distinctLocation.put(Long.toString(existingStockDetails.getLocationId()),Long.toString(existingStockDetails.getLocationId()));
							}
							//IRB Santosh
							if(("CurrentAsset".equals(existingStockDetails.getStockType()) || "ValueDebtors".equals(existingStockDetails.getStockType()) )  && ("YES".equals(existingStockDetails.getApplicableForDp()))){
								totalLonableAsset=totalLonableAsset.add(new BigDecimal(existingStockDetails.getLonable()));
							}else if( ( "CurrentLiabilities".equals(existingStockDetails.getStockType()) || "LessAdvances".equals(existingStockDetails.getStockType()) )  && ("YES".equals(existingStockDetails.getApplicableForDp()))){
								totalLonableLiability=totalLonableLiability.add(new BigDecimal(existingStockDetails.getLonable()));
							}
						}	
					}
				}
			}
			totalLonableForDP=totalLonableForDP.add((totalLonableAsset.subtract(totalLonableLiability)));	
			//calculatedDP=totalLonableForDP.multiply(new BigDecimal(fundedShareVal));
			//calculatedDP=calculatedDP.divide(new BigDecimal(100),BigDecimal.ROUND_FLOOR);
			
			Collection values = distinctLocation.values();
			
			List addedLocations= new ArrayList();
			if(!stockLocationFilter){
			addedLocations.addAll(values);
			}else{
				addedLocations.add(stockLocation);
			}
			
			//Preparing Display OB
			
			AssetGenChargeStockDetailsHelperForm helperFrom;
			ICityProxyManager proxy= (ICityProxyManager)BeanHouse.get("cityProxy");
			IGeneralChargeDetails[] newGCDetails = newCollateral.getGeneralChargeDetails();
			for (Iterator iterator = addedLocations.iterator(); iterator.hasNext();) {
				long locationID = Long.parseLong((String)iterator.next());
				helperFrom= new AssetGenChargeStockDetailsHelperForm();
				helperFrom.setLocationID(Long.toString(locationID));
				String cityName="";
				try {
					if(ICMSConstant.CITY_VALUE_ALL.equals(String.valueOf(locationID))){
						cityName=ICMSConstant.CITY_LABEL_ALL;
					}else{
						ICityTrxValue cityById = proxy.getCityById(locationID);
						cityName = cityById.getActualCity().getCityName();
					}
				} catch (NoSuchGeographyException e) {
					e.printStackTrace();
				} catch (TrxParameterException e) {
					e.printStackTrace();
				} catch (TransactionException e) {
					e.printStackTrace();
				}
				helperFrom.setLocationName(cityName);
				helperFrom.setDueDate(dueDateStr);
				
				if(newGCDetails!=null){
					for (int i = 0; i < newGCDetails.length; i++) {
						existingChargeDetails = newGCDetails[i];
						if(existingChargeDetails!=null && docCode.equals(existingChargeDetails.getDocCode()) && dueDate.equals(existingChargeDetails.getDueDate())){
	//						BigDecimal totalLonable= new BigDecimal(0);
							BigDecimal totalLonableAset= new BigDecimal(0);
							BigDecimal totalLonableLiabilit= new BigDecimal(0);
							existingGeneralChargeStockDetails = existingChargeDetails.getGeneralChargeStockDetails();
							for (int j = 0; j < existingGeneralChargeStockDetails.length; j++) {
								IGeneralChargeStockDetails existingStockDetails = existingGeneralChargeStockDetails[j];
								if(locationID==(existingStockDetails.getLocationId())){
									if("CurrentAsset".equals(existingStockDetails.getStockType()) && ("YES".equals(existingStockDetails.getApplicableForDp()))){
										totalLonableAset=totalLonableAset.add(new BigDecimal(existingStockDetails.getLonable()));
									}else if("CurrentLiabilities".equals(existingStockDetails.getStockType()) && ("YES".equals(existingStockDetails.getApplicableForDp()))){
										totalLonableLiabilit=totalLonableLiabilit.add(new BigDecimal(existingStockDetails.getLonable()));
									}
								}
							}
					//		helperFrom.setTotalLonable(totalLonable);
							
							if(null != migrationFlag &&  "Y".equalsIgnoreCase(migrationFlag.trim()) ){
								helperFrom.setTotalLonable((totalLonableAsset.subtract(totalLonableLiability)).toString());
								totalLonable=(totalLonableAsset.subtract(totalLonableLiability)).toString();
							}else {
								helperFrom.setTotalLonable(totalLonable);
							}
							
							helperFrom.setLastApprovedBy(existingChargeDetails.getLastUpdatedBy());
							helperFrom.setLastUpdatedOn(existingChargeDetails.getLastUpdatedOn());
							helperFrom.setLastApprovedBy(existingChargeDetails.getLastApprovedBy());
							helperFrom.setLastApprovedOn(existingChargeDetails.getLastApprovedOn());
							
							loanable=UIUtil.mapStringToBigDecimal(totalLonable);
//							totalLonableForDP=totalLonableForDP.add(new BigDecimal(helperFrom.getTotalLonable()));
							break;
						}
					}
				}
				displayList.add(helperFrom);
			}
			
			
//			calculatedDP=totalLonableForDP.multiply(fundedShare);
//			calculatedDP=calculatedDP.divide(new BigDecimal(100),BigDecimal.ROUND_FLOOR);
		}
		//Added by Anil =========End=================
		
//		result.put("isEditable", isEditable);
		result.put("forwardPage", event);
		result.put("tab", CollateralConstant.TAB_GENERAL);
		result.put("displayList", displayList);
		result.put("dueDateList", dueDateList);
//		result.put("fundedShare", fundedShareVal);
		
		result.put("dpShare", fundedShareVal);
		result.put("trxID", trxID);
		result.put("serviceColObj", itrxValue);
		result.put("calculatedDP", calculatedDP.toString());
		result.put("filterLocationList", new ArrayList());
		result.put("totalLonable", totalLonable);
		
		result.put("migrationFlag", migrationFlag);
		result.put("remarkByMaker", remarkByMaker);
		
		result.put("loanable",loanable);
		temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		return temp;
	}

}
