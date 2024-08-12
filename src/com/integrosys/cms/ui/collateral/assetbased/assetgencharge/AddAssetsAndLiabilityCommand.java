/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/collateral/assetbased/assetpostdatedchqs/AddChequeCommand.java,v 1.2 2005/08/26 10:12:37 hshii Exp $
 */

package com.integrosys.cms.ui.collateral.assetbased.assetgencharge;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.struts.action.ActionMessage;

import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.collateral.bus.type.asset.subtype.gcharge.IGeneralCharge;
import com.integrosys.cms.app.collateral.bus.type.asset.subtype.gcharge.IGeneralChargeDetails;
import com.integrosys.cms.app.collateral.bus.type.asset.subtype.gcharge.IGeneralChargeStockDetails;
import com.integrosys.cms.app.collateral.bus.type.asset.subtype.gcharge.GCStockDetComparator;
import com.integrosys.cms.app.collateral.trx.ICollateralTrxValue;
import com.integrosys.cms.app.component.bus.IComponentDao;

/**
 * @author $Author: hshii $<br>
 * @version $Revision: 1.2 $
 * @since $Date: 2005/08/26 10:12:37 $ Tag: $Name: $
 */

public class AddAssetsAndLiabilityCommand extends AbstractCommand {

	public String[][] getParameterDescriptor() {
		return (new String[][] { 
				{ "form.stockDetailsObject", "java.lang.Object", FORM_SCOPE },
				{ "serviceColObj", "com.integrosys.cms.app.collateral.trx.ICollateralTrxValue", SERVICE_SCOPE },
				{ "serviceStockDetailsList",  "java.util.List", SERVICE_SCOPE },
				{ "subtype", "java.lang.String", REQUEST_SCOPE }, 
				{ "assetsMarginType", "java.lang.String", REQUEST_SCOPE }, 
				{ "assetsFixedMarginValue", "java.lang.String", REQUEST_SCOPE },
				{"stockLocation","java.lang.String", REQUEST_SCOPE},
				
				{ "assetsMarginTypeNew", "java.lang.String", REQUEST_SCOPE }, 
				{ "assetsFixedMarginValueNew", "java.lang.String", REQUEST_SCOPE },
				
				{ "liabilityMarginType", "java.lang.String", REQUEST_SCOPE }, 
				{ "liabilityFixedMarginValue", "java.lang.String", REQUEST_SCOPE },
				
				{ "liabilityMarginTypeAdv", "java.lang.String", REQUEST_SCOPE }, 
				{ "liabilityFixedMarginValueAdv", "java.lang.String", REQUEST_SCOPE },
				
				{ "from_page", "java.lang.String", REQUEST_SCOPE },
				{"itemType","java.lang.String", REQUEST_SCOPE },
				{ "migrationFlag", "java.lang.String", REQUEST_SCOPE },
				{ "totalLonable", "java.lang.String", REQUEST_SCOPE },

		
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
				{ "serviceStockDetailsList",  "java.util.List", SERVICE_SCOPE },
				{ "locationList", "java.util.List", ICommonEventConstant.REQUEST_SCOPE },
				{ "subtype", "java.lang.String", REQUEST_SCOPE }, 
				{ "assetsMarginType", "java.lang.String", REQUEST_SCOPE }, 
				{ "assetsFixedMarginValue", "java.lang.String", REQUEST_SCOPE }, 
				
				{ "assetsMarginTypeNew", "java.lang.String", REQUEST_SCOPE }, 
				{ "assetsFixedMarginValueNew", "java.lang.String", REQUEST_SCOPE }, 
				
				{ "liabilityMarginType", "java.lang.String", REQUEST_SCOPE }, 
				{ "liabilityFixedMarginValue", "java.lang.String", REQUEST_SCOPE },
				
				{ "liabilityMarginTypeAdv", "java.lang.String", REQUEST_SCOPE }, 
				{ "liabilityFixedMarginValueAdv", "java.lang.String", REQUEST_SCOPE },
				{"stockLocation","java.lang.String", REQUEST_SCOPE},
				{"stockLocation","java.lang.String", SERVICE_SCOPE},
				
				
				
				{ "form.stockDetailsObject", "java.lang.Object", FORM_SCOPE },
				{ "from_page", "java.lang.String", REQUEST_SCOPE },
				{"applicableForDpList", " java.util.HashMap", REQUEST_SCOPE},
				{"applicableForDpList", " java.util.HashMap", SERVICE_SCOPE},
				
				{"compoList", " java.util.HashMap", REQUEST_SCOPE},
				{"compoList", " java.util.HashMap", SERVICE_SCOPE},
				{"itemType","java.lang.String", REQUEST_SCOPE },
				{ "migrationFlag", "java.lang.String", REQUEST_SCOPE },
				{ "totalLonable", "java.lang.String", SERVICE_SCOPE },
				
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

		IGeneralChargeDetails chargeDetails = (IGeneralChargeDetails) map.get("form.stockDetailsObject");
		IGeneralChargeStockDetails newStockDetails = chargeDetails.getGeneralChargeStockDetails()[0];
		List stockDetailsList= new ArrayList();
		if(map.get("serviceStockDetailsList")!=null){
			stockDetailsList= (ArrayList) map.get("serviceStockDetailsList");
		}
		String migrationFlag=  (String)map.get("migrationFlag");
		System.out.println("migrationFlag in AddSset=="+migrationFlag);
		
		String totalLonable=  (String)map.get("totalLonable");
		System.out.println("totalLonable in AddSsetcmd====="+totalLonable);
		
		
	//	Collections.sort(stockDetailsList, new GCStockDetComparator());
		/*boolean isComponentExist=false;
		for (Iterator iterator = stockDetailsList.iterator(); iterator
				.hasNext();) {
			IGeneralChargeStockDetails stockDetails = (IGeneralChargeStockDetails) iterator.next();
			if(stockDetails.getComponent().equals(newStockDetails.getComponent())){
				isComponentExist=true;
			}
			
		}
		if(!isComponentExist){
			stockDetailsList.add(chargeDetails.getGeneralChargeStockDetails()[0]);
		}else{
			exceptionMap.put("componentsError", new ActionMessage("error.component.already.exist"));
		}*/
		
		ICollateralTrxValue itrxValue = (ICollateralTrxValue) map.get("serviceColObj");

		IGeneralCharge iAsset = (IGeneralCharge) itrxValue.getStagingCollateral();
		//addStockDetails1(chargeDetails.getGeneralChargeStockDetails()[0]);
		itrxValue.setStagingCollateral(iAsset);
//-----------------
		String assetsMarginType = (String) map.get("assetsMarginType");
		String assetsFixedMarginValue = (String) map.get("assetsFixedMarginValue");
		
		String assetsMarginTypeNew = (String) map.get("assetsMarginTypeNew");
		String assetsFixedMarginValueNew = (String) map.get("assetsFixedMarginValueNew");
		
		String liabilityMarginType = (String) map.get("liabilityMarginType");
		String liabilityFixedMarginValue = (String) map.get("liabilityFixedMarginValue");
		
		String liabilityMarginTypeAdv = (String) map.get("liabilityMarginTypeAdv");
		String liabilityFixedMarginValueAdv = (String) map.get("liabilityFixedMarginValueAdv");
		
		
		String itemType=(String)map.get("itemType");
		String stockLocation = (String) map.get("stockLocation");
		
		result.put("stockLocation", stockLocation);

		result.put("assetsMarginType", assetsMarginType);
		result.put("assetsFixedMarginValue", assetsFixedMarginValue);
		
		result.put("assetsMarginTypeNew", assetsMarginTypeNew);
		result.put("assetsFixedMarginValueNew", assetsFixedMarginValueNew);
		
		result.put("liabilityMarginType", liabilityMarginType);
		result.put("liabilityFixedMarginValue", liabilityFixedMarginValue);
		
		result.put("liabilityMarginTypeAdv", liabilityMarginTypeAdv);
		result.put("liabilityFixedMarginValueAdv", liabilityFixedMarginValueAdv);
		
		result.put("form.stockDetailsObject", chargeDetails);
//-----------------
		result.put("locationList", AssetGenChargeHelper.getLocationList());
		result.put("serviceColObj", itrxValue);
		
		//Collections.sort(stockDetailsList, new GCStockDetComparator());

		result.put("serviceStockDetailsList", stockDetailsList);
		result.put("from_page", map.get("from_page"));
		result.put("subtype", map.get("subtype"));
		
		//Start santosh
		IComponentDao componentDAO=(IComponentDao)BeanHouse.get("componentDao");
		List<String> applicableForDpList=new ArrayList<String>();
		applicableForDpList=componentDAO.getApplicableForDpList();
		result.put("applicableForDpList", applicableForDpList);
		//end santosh
		
		String category="";
		
		if(null != itemType && !"".equals(itemType)) {
			
			if("CURRENTASSET".equals(itemType)) {
				 category="Stock";
			}else if("CURRENTLIABILITIES".equals(itemType)) {
				 category="Creditors";
			}else if("VALUEDEBTORS".equals(itemType)) {
				 category="Debtors";
			}else if("LESSVALUEADVANCES".equals(itemType)) {
				 category="Advances";
			}
			
		}
		List<String> compoList=new ArrayList<String>();
		AssetGenChargeHelper helper= new AssetGenChargeHelper();
		compoList=helper.getCompoList(category);
		result.put("compoList", compoList);
		
		/*for(int i=0; i< stockDetailsList.size();i++) {
		IGeneralChargeStockDetails stockDetails = (IGeneralChargeStockDetails) stockDetailsList.get(i);
		
		stockDetails.g
		
		}*/
	
		if(null != migrationFlag &&  "Y".equalsIgnoreCase(migrationFlag.trim()) ){
		//IGeneralChargeStockDetails[] serviceStockDetailsList = (IGeneralChargeStockDetails[]) map.get("serviceStockDetailsList");
		BigDecimal totalLonableAsset= new BigDecimal(0);
		BigDecimal totalLonableLiability= new BigDecimal(0);
		for (int j = 0; j <  stockDetailsList.size(); j++) {
			
			
			IGeneralChargeStockDetails serviceStockDetailsList = (IGeneralChargeStockDetails) stockDetailsList.get(j);
				//IRB Santosh
				if(("CurrentAsset".equals(serviceStockDetailsList.getStockType() ) || "ValueDebtors".equals(serviceStockDetailsList.getStockType()) ) && ("YES".equals(serviceStockDetailsList.getApplicableForDp()))){
					totalLonableAsset=totalLonableAsset.add(new BigDecimal(serviceStockDetailsList.getLonable()));
				}else if (  ("CurrentLiabilities".equals(serviceStockDetailsList.getStockType()) || "LessAdvances".equals(serviceStockDetailsList.getStockType()) ) && ("YES".equals(serviceStockDetailsList.getApplicableForDp()))){
					totalLonableLiability=totalLonableLiability.add(new BigDecimal(serviceStockDetailsList.getLonable()));
				}
			
		}
		//helperFrom.setTotalLonable(totalLonable);

		
		//	helperFrom.setTotalLonable((totalLonableAsset.subtract(totalLonableLiability)).toString());
			 totalLonable=(totalLonableAsset.subtract(totalLonableLiability)).toString();
			result.put("totalLonable", totalLonable);
		}else {
			result.put("totalLonable", totalLonable);
		}
		
		
		
		
		DefaultLogger.debug(this, "inside AddAssetsAndLiabilityCommand");
		temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		return temp;
	}
	
	


	/*public static void addStockDetails(IGeneralCharge iAsset, IGeneralChargeDetails chargeDetails) {
		
		IGeneralChargeStockDetails generalChargeStockDetails = chargeDetails.getGeneralChargeStockDetails()[0];

		IGeneralChargeDetails[] existingArray = iAsset.getGeneralChargeDetails();
		int arrayLength = 0;
		if (existingArray != null) {
			arrayLength = existingArray.length;
		}

		IGeneralChargeDetails[] newArray = new IGeneralChargeDetails[arrayLength + 1];
		if (existingArray != null) {
			System.arraycopy(existingArray, 0, newArray, 0, arrayLength);
		}
		newArray[arrayLength] = chargeDetails;

		iAsset.setGeneralChargeDetails(newArray);
	}
	public static void addStockDetails1(IGeneralChargeStockDetails generalChargeStockDetails) {
		IGeneralChargeDetails chargeDetails= new OBGeneralChargeDetails();;
		if(iAsset.getGeneralChargeDetails()!=null){
			chargeDetails=(IGeneralChargeDetails)iAsset.getGeneralChargeDetails()[0];
		}else{
			chargeDetails= new OBGeneralChargeDetails();
		}
		
		//IGeneralChargeStockDetails generalChargeStockDetails = chargeDetails.getGeneralChargeStockDetails()[0];
		
		IGeneralChargeStockDetails[] existingArray = chargeDetails.getGeneralChargeStockDetails();
		int arrayLength = 0;
		if (existingArray != null) {
			arrayLength = existingArray.length;
		}
		
		IGeneralChargeStockDetails[] newArray = new IGeneralChargeStockDetails[arrayLength + 1];
		if (existingArray != null) {
			System.arraycopy(existingArray, 0, newArray, 0, arrayLength);
		}
		newArray[arrayLength] = generalChargeStockDetails;
		
		chargeDetails.setGeneralChargeStockDetails(newArray);
		IGeneralChargeDetails[] generalChargeDetails = new IGeneralChargeDetails[1];
		generalChargeDetails[0]=chargeDetails;
		iAsset.setGeneralChargeDetails(generalChargeDetails);
	}*/
}
