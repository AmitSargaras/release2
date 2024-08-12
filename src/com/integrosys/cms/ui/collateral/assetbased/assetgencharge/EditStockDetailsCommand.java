/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/collateral/assetbased/assetpostdatedchqs/AddChequeCommand.java,v 1.2 2005/08/26 10:12:37 hshii Exp $
 */

package com.integrosys.cms.ui.collateral.assetbased.assetgencharge;

import static com.integrosys.cms.ui.collateral.CollateralConstant.SELECTED_STOCK_LOCATIONS;
import static com.integrosys.cms.ui.collateral.CollateralConstant.SESSION_DUE_DATE_AND_STOCK_DETAILS;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionMessage;


import java.util.Collections;
import com.integrosys.cms.app.collateral.bus.type.asset.subtype.gcharge.GCStockDetComparator;

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
import com.integrosys.cms.app.collateral.bus.type.asset.subtype.gcharge.GCStockDetComparator;
import com.integrosys.cms.app.collateral.bus.type.asset.subtype.gcharge.IGeneralCharge;
import com.integrosys.cms.app.collateral.bus.type.asset.subtype.gcharge.IGeneralChargeDetails;
import com.integrosys.cms.app.collateral.bus.type.asset.subtype.gcharge.IGeneralChargeStockDetails;
import com.integrosys.cms.app.collateral.proxy.CollateralProxyFactory;
import com.integrosys.cms.app.collateral.proxy.ICollateralProxy;
import com.integrosys.cms.app.collateral.trx.ICollateralTrxValue;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.customer.bus.ICMSCustomer;
import com.integrosys.cms.app.geography.bus.NoSuchGeographyException;
import com.integrosys.cms.app.geography.city.proxy.ICityProxyManager;
import com.integrosys.cms.app.geography.city.trx.ICityTrxValue;
import com.integrosys.cms.ui.common.UIUtil;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;

/**
 * @author $Author: hshii $<br>
 * @version $Revision: 1.2 $
 * @since $Date: 2005/08/26 10:12:37 $ Tag: $Name: $
 */

public class EditStockDetailsCommand extends AbstractCommand {

	public String[][] getParameterDescriptor() {
		return (new String[][] { { "form.stockDetailsObject", "java.lang.Object", FORM_SCOPE },
				{ "serviceColObj", "com.integrosys.cms.app.collateral.trx.ICollateralTrxValue", SERVICE_SCOPE },
				{ "serviceStockDetailsList",  "java.util.List", SERVICE_SCOPE },
				{ com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY, "java.util.Locale", GLOBAL_SCOPE },
				{ "subtype", "java.lang.String", REQUEST_SCOPE },
				{ "dpCalculateManually", "java.lang.String", REQUEST_SCOPE },
				{ "calculatedDP","java.lang.String",REQUEST_SCOPE},
				{ "dpShare", "java.lang.String", SERVICE_SCOPE },
				{ "remarkByMaker", "java.lang.String", REQUEST_SCOPE },
				{ "totalLonable", "java.lang.String", REQUEST_SCOPE },
				{ "migrationFlag", "java.lang.String", REQUEST_SCOPE },
				
				{ SESSION_DUE_DATE_AND_STOCK_DETAILS, IGeneralChargeDetails.class.getName(), SERVICE_SCOPE },
				{ SELECTED_STOCK_LOCATIONS, String.class.getName(), SERVICE_SCOPE}
				});
	}

	public String[][] getResultDescriptor() {
		return (new String[][] {
				{ "serviceColObj", "com.integrosys.cms.app.collateral.trx.ICollateralTrxValue", SERVICE_SCOPE },
				{ "displayList",  "java.util.List", SERVICE_SCOPE },
				{ "subtype", "java.lang.String", REQUEST_SCOPE },
				{ "calculatedDP", "java.lang.String", REQUEST_SCOPE },
				{ "isEditable", "java.lang.String", SERVICE_SCOPE },
				{ "statementName",  "java.util.List", SERVICE_SCOPE },
				{ "isStockDetailsAdded",  "java.util.List", SERVICE_SCOPE },
				{ "dpShare", "java.lang.String", SERVICE_SCOPE },
				{ "remarkByMaker", "java.lang.String", SERVICE_SCOPE },
				{ "loanable", "java.lang.String", REQUEST_SCOPE },
				{ "dpCalculateManually", "java.lang.String", REQUEST_SCOPE },
				{ "totalLonable", "java.lang.String", REQUEST_SCOPE },
				{ "migrationFlag", "java.lang.String", REQUEST_SCOPE },
				
				{ SESSION_DUE_DATE_AND_STOCK_DETAILS, IGeneralChargeDetails.class.getName(), SERVICE_SCOPE },
				});
	}

	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {

		HashMap<String, Object> result = new HashMap<String, Object>();
		HashMap<String, Object> exceptionMap = new HashMap<String, Object>();
		HashMap<String, Map<String, ?>> temp = new HashMap<String, Map<String, ?>>();
		
		IGeneralChargeDetails existingChargeDetails = (IGeneralChargeDetails) map.get(SESSION_DUE_DATE_AND_STOCK_DETAILS);
		String fundedSharePercent = "";
		String calculatedDPValue = "";
		BigDecimal totalLonableForDP= new BigDecimal(0);
		String fundedShare= "";
		String remarkByMaker= "";
		
		if(map.get("dpShare")!=null && !"".equals(map.get("dpShare"))){
			if(map.get("dpShare") instanceof BigDecimal){
		fundedShare =map.get("dpShare").toString();
			}
		}
		if(map.get("dpShare")!=null && !"".equals(map.get("dpShare"))){
			if(map.get("dpShare") instanceof String){
			fundedShare =(String)map.get("dpShare");
			}
			}
		
		if(fundedShare==null && "".equals(fundedShare)){
			 fundedShare= "0";
		}
		if(null != map.get("remarkByMaker")) {
		remarkByMaker =map.get("remarkByMaker").toString();
		}
		String dpCalculateManually =(String) map.get("dpCalculateManually");
		
		String migrationFlag =(String) map.get("migrationFlag");
		
		String totalLonable =(String) map.get("totalLonable");
		if(null==totalLonable ||  "".equals(totalLonable)) {
			totalLonable="0";
		}
		System.out.println("totalLonable==="+totalLonable);

		List stockDetailsList= (ArrayList) map.get("serviceStockDetailsList");
		Collections.sort(stockDetailsList, new GCStockDetComparator("K"));
		ICollateralTrxValue itrxValue = (ICollateralTrxValue) map.get("serviceColObj");
		IGeneralCharge iAsset = (IGeneralCharge) itrxValue.getStagingCollateral();
		List displayList= new ArrayList();
		Locale locale = (Locale) map.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);
		//retriving Existing StockDetailsList
		if(stockDetailsList!=null && stockDetailsList.size()==0){
			exceptionMap.put("stockDetailsError", new ActionMessage("error.assets.liabilities.mandatory"));
		}
		
		IGeneralChargeDetails newChargeDetails = (IGeneralChargeDetails) map.get("form.stockDetailsObject");
		String chargeDetailLocation = newChargeDetails.getLocation();
		
		if(StringUtils.isNotBlank(chargeDetailLocation)){
		IGeneralChargeStockDetails[] newStockDetails = getGeneralChargeStockDetailsArray(newChargeDetails, stockDetailsList);
		
		String selectedLocations = (String)map.get(SELECTED_STOCK_LOCATIONS);
		Map<String,String> selectedLocationMap = UIUtil.getMapFromDelimitedString(selectedLocations, ",");
		IGeneralChargeStockDetails[] newGeneralChargeStockDetails;
		IGeneralChargeStockDetails[] existingGeneralChargeStockDetails;
		List<IGeneralChargeStockDetails> otherThanEditedLocation= new ArrayList<IGeneralChargeStockDetails>();
		
		if(existingChargeDetails!=null && existingChargeDetails.getGeneralChargeStockDetails() != null){
			existingGeneralChargeStockDetails = existingChargeDetails.getGeneralChargeStockDetails();					
			
			for (int j = 0; j < existingGeneralChargeStockDetails.length; j++) {
				IGeneralChargeStockDetails existingStockDetails = existingGeneralChargeStockDetails[j];
				if(!chargeDetailLocation.equals(Long.toString(existingStockDetails.getLocationId()))){
					otherThanEditedLocation.add(existingStockDetails);
				}
			}
			newGeneralChargeStockDetails= mergeArray((IGeneralChargeStockDetails[]) otherThanEditedLocation.toArray(new IGeneralChargeStockDetails[otherThanEditedLocation.size()]),newStockDetails);
			existingChargeDetails.setGeneralChargeStockDetails(newGeneralChargeStockDetails);
		}
		
		Collection<String> values = selectedLocationMap.values();

		List<String> addedLocations= new ArrayList<String>();
		addedLocations.addAll(values);
	
		//if Exception exist then skip displayList creation 
		if ((exceptionMap == null) || (exceptionMap.size() == 0)) {
		//Preparing Display OB
		AssetGenChargeStockDetailsHelperForm helperFrom;
		ICityProxyManager proxy= (ICityProxyManager)BeanHouse.get("cityProxy");
		IGeneralChargeDetails[] newGCDetails = iAsset.getGeneralChargeDetails();
		
		
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
			helperFrom.setDueDate(DateUtil.formatDate(locale, newChargeDetails.getDueDate())+","+newChargeDetails.getDocCode());
			
			if(existingChargeDetails!=null && existingChargeDetails.getGeneralChargeStockDetails()!=null){
			//	BigDecimal totalLonable= new BigDecimal(0);
				BigDecimal totalLonableAsset= new BigDecimal(0);
				BigDecimal totalLonableLiability= new BigDecimal(0);
				existingGeneralChargeStockDetails = existingChargeDetails.getGeneralChargeStockDetails();
				for (int j = 0; j < existingGeneralChargeStockDetails.length; j++) {
					IGeneralChargeStockDetails existingStockDetails = existingGeneralChargeStockDetails[j];
					if(locationID==(existingStockDetails.getLocationId())){
						if(("CurrentAsset".equals(existingStockDetails.getStockType())  || "ValueDebtors".equals(existingStockDetails.getStockType())  ) && ("YES".equals(existingStockDetails.getApplicableForDp()))){
									totalLonableAsset=totalLonableAsset.add(new BigDecimal(existingStockDetails.getLonable()));
								}else if(( "CurrentLiabilities".equals(existingStockDetails.getStockType()) || "LessAdvances".equals(existingStockDetails.getStockType()) ) && ("YES".equals(existingStockDetails.getApplicableForDp()))){
									totalLonableLiability=totalLonableLiability.add(new BigDecimal(existingStockDetails.getLonable()));
								}

					//	helperFrom.setTotalLonable((totalLonableAsset.subtract(totalLonableLiability)).toString());
						if(null != migrationFlag &&  "Y".equalsIgnoreCase(migrationFlag.trim()) ){
							helperFrom.setTotalLonable((totalLonableAsset.subtract(totalLonableLiability)).toString());
							totalLonable=(totalLonableAsset.subtract(totalLonableLiability)).toString();
						}else {
							helperFrom.setTotalLonable(totalLonable);
						}
					//	helperFrom.setTotalLonable(totalLonable);
						totalLonableForDP=totalLonableForDP.add(UIUtil.mapStringToBigDecimal(totalLonable));
						//fundedSharePercent = existingChargeDetails.getFundedShare();
						
						fundedSharePercent = existingChargeDetails.getDpShare();
						
						//IRB Santosh
						//String dpCalculateManually = (String) map.get("dpCalculateManually");
						
						if(null!=fundedSharePercent && !"null".equals(fundedSharePercent)  && !"".equals(fundedSharePercent) && dpCalculateManually!=null && dpCalculateManually.equals("NO")){

							calculatedDPValue = totalLonableForDP.multiply(new BigDecimal(fundedSharePercent)).toString();
							calculatedDPValue = new BigDecimal(calculatedDPValue).divide(new BigDecimal(100),BigDecimal.ROUND_FLOOR).toString();
						} 
						else if(dpCalculateManually!=null && dpCalculateManually.equals("NO")) {
							calculatedDPValue = totalLonableForDP.multiply(new BigDecimal(0)).toString();
						}
						else calculatedDPValue=(String) map.get("calculatedDP");
					}
				}
				helperFrom.setTotalLonable((totalLonableAsset.subtract(totalLonableLiability)).toString());
				totalLonableForDP=totalLonableForDP.add(new BigDecimal(helperFrom.getTotalLonable()));
				
				fundedSharePercent = existingChargeDetails.getDpShare();
				
				//String dpCalculateManually = (String) map.get("dpCalculateManually");
				
				if(StringUtils.isNotBlank(fundedSharePercent) && !fundedSharePercent.equals("null") && dpCalculateManually!=null && dpCalculateManually.equals("NO")){
					calculatedDPValue = totalLonableForDP.multiply(new BigDecimal(fundedSharePercent)).toString();
					calculatedDPValue = new BigDecimal(calculatedDPValue).divide(new BigDecimal(100),BigDecimal.ROUND_FLOOR).toString();
				} 
				else if(dpCalculateManually!=null && dpCalculateManually.equals("NO")) {
					calculatedDPValue = totalLonableForDP.multiply(new BigDecimal(0)).toString();
				}
				else calculatedDPValue=(String) map.get("calculatedDP");
			}

			displayList.add(helperFrom);
		}
		//No error hence setting flag as "Y"
		result.put("isStockDetailsAdded", "Y");
		}
		if(displayList!=null)
			DefaultLogger.debug(this, "displayList.size()"+displayList.size());	
		
		existingChargeDetails.setLocation(newChargeDetails.getLocation());
		existingChargeDetails.setLocationDetail(newChargeDetails.getLocationDetail());
		existingChargeDetails.setCoverageAmount(newChargeDetails.getCoverageAmount());
		existingChargeDetails.setAdHocCoverageAmount(newChargeDetails.getAdHocCoverageAmount());
		existingChargeDetails.setCoveragePercentage(newChargeDetails.getCoveragePercentage());
		result.put(SESSION_DUE_DATE_AND_STOCK_DETAILS, existingChargeDetails);
		
		}else{
			exceptionMap.put("locationError", new ActionMessage("label.please.select.option"));
		}		
		
		//BigDecimal calculatedDP= new BigDecimal(0);
		//calculatedDP=(totalLonableForDP.multiply(fundedShare)).divide(new BigDecimal(100));
		//calculatedDP=totalLonableForDP.multiply(fundedShare);
		//calculatedDP=calculatedDP.divide(new BigDecimal(100),BigDecimal.ROUND_FLOOR);
		
		String statementName ="";
		ICollateralProxy collateralProxy = CollateralProxyFactory.getProxy();
		try {
			statementName = collateralProxy.getStatementNameByDocCode(newChargeDetails.getDocCode());
		} catch (SearchDAOException e1) {
			e1.printStackTrace();
		} catch (CollateralException e1) {
			e1.printStackTrace();
		}
		itrxValue.setStagingCollateral(iAsset);
		result.put("statementName", statementName);
		result.put("isEditable", "Y");//User is able to add hence its editable.
		result.put("serviceColObj", itrxValue);
		result.put("displayList", displayList);
		
		result.put("dpShare", fundedSharePercent);
		result.put("subtype", map.get("subtype"));
		result.put("calculatedDP", calculatedDPValue);
		
		result.put("remarkByMaker", remarkByMaker);
		
		result.put("loanable", totalLonableForDP);
		result.put("totalLonable", totalLonable);
		result.put("migrationFlag", migrationFlag);
		result.put("dpCalculateManually", dpCalculateManually);

		DefaultLogger.debug(this, "inside addChequeCommand");
		temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		return temp;
	}

	private IGeneralChargeStockDetails[] mergeArray(
			IGeneralChargeStockDetails[] existingGeneralChargeStockDetails,
			IGeneralChargeStockDetails[] newStockDetails) {
		
		IGeneralChargeStockDetails[] newGeneralChargeStockDetails = 
				new IGeneralChargeStockDetails[existingGeneralChargeStockDetails.length+newStockDetails.length];
		for (int i = 0; i < existingGeneralChargeStockDetails.length; i++) {
			newGeneralChargeStockDetails[i]= existingGeneralChargeStockDetails[i];
		}
		for (int i = 0; i < newStockDetails.length; i++) {
			newGeneralChargeStockDetails[i+existingGeneralChargeStockDetails.length]= newStockDetails[i];
		}
		
		return newGeneralChargeStockDetails;
	}

	private IGeneralChargeStockDetails[] getGeneralChargeStockDetailsArray(
			IGeneralChargeDetails newChargeDetails, List stockDetailsList) {
		IGeneralChargeStockDetails[] newStockDetails= new IGeneralChargeStockDetails[stockDetailsList.size()];
		
		for (int i = 0; i < stockDetailsList.size(); i++) {
			IGeneralChargeStockDetails iGeneralChargeStockDetails = (IGeneralChargeStockDetails) stockDetailsList.get(i);
			iGeneralChargeStockDetails.setLocationId(Long.parseLong(newChargeDetails.getLocation()));
			iGeneralChargeStockDetails.setLocationDetail(newChargeDetails.getLocationDetail());
			newStockDetails[i]=iGeneralChargeStockDetails;
		}
		return newStockDetails;
	}
	

	public static void addGeneralChargeDetails(IGeneralCharge iAsset, IGeneralChargeDetails chargeDetails) {
		
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
	
}
