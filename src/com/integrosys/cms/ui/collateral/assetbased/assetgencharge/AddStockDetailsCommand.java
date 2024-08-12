/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/collateral/assetbased/assetpostdatedchqs/AddChequeCommand.java,v 1.2 2005/08/26 10:12:37 hshii Exp $
 */

package com.integrosys.cms.ui.collateral.assetbased.assetgencharge;

import static com.integrosys.cms.ui.collateral.CollateralConstant.SELECTED_STOCK_LOCATIONS;
import static com.integrosys.cms.ui.collateral.CollateralConstant.SESSION_DUE_DATE_AND_STOCK_DETAILS;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

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
import com.integrosys.cms.app.collateral.bus.type.asset.subtype.gcharge.GCStockDetComparator;
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
import com.integrosys.cms.app.limit.bus.ILimitProfile;
import com.integrosys.cms.asst.validator.ASSTValidator;
import com.integrosys.cms.ui.common.UIUtil;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;

/**
 * @author $Author: hshii $<br>
 * @version $Revision: 1.2 $
 * @since $Date: 2005/08/26 10:12:37 $ Tag: $Name: $
 */

public class AddStockDetailsCommand extends AbstractCommand {

	public String[][] getParameterDescriptor() {
		return (new String[][] { { "form.stockDetailsObject", "java.lang.Object", FORM_SCOPE },
				{ "serviceColObj", "com.integrosys.cms.app.collateral.trx.ICollateralTrxValue", SERVICE_SCOPE },
				{ "serviceStockDetailsList",  "java.util.List", SERVICE_SCOPE },
				{ "dpShare", "java.lang.String", SERVICE_SCOPE },
				{ "dpCalculateManually", "java.lang.String", SERVICE_SCOPE },
				{ "subtype", "java.lang.String", REQUEST_SCOPE }, 
				{ "remarkByMaker", "java.lang.String", SERVICE_SCOPE },
				{ "totalLonable", "java.lang.String", REQUEST_SCOPE },
				{ "migrationFlag", "java.lang.String", REQUEST_SCOPE },
				{ com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY, "java.util.Locale", GLOBAL_SCOPE },
				{ IGlobalConstant.GLOBAL_LIMITPROFILE_OBJ, "com.integrosys.cms.app.limit.bus.ILimitProfile",GLOBAL_SCOPE },
				{ IGlobalConstant.GLOBAL_CUSTOMER_OBJ, "com.integrosys.cms.app.customer.bus.ICMSCustomer", GLOBAL_SCOPE },
				{ SESSION_DUE_DATE_AND_STOCK_DETAILS, IGeneralChargeDetails.class.getName(), SERVICE_SCOPE },
				{ "dueDate", "java.lang.String", SERVICE_SCOPE },
				{ SELECTED_STOCK_LOCATIONS, String.class.getName(), SERVICE_SCOPE}
				});
	}

	public String[][] getResultDescriptor() {
		return (new String[][] {
				{ "serviceColObj", "com.integrosys.cms.app.collateral.trx.ICollateralTrxValue", SERVICE_SCOPE },
				{ "displayList",  "java.util.List", SERVICE_SCOPE },
				{ "subtype", "java.lang.String", REQUEST_SCOPE },
				{ "calculatedDP", "java.lang.String", SERVICE_SCOPE },
				{ "isEditable", "java.lang.String", SERVICE_SCOPE },
				{ "statementName",  "java.util.List", SERVICE_SCOPE },
				{ "isStockDetailsAdded",  "java.util.List", SERVICE_SCOPE },
				{ "dpShare", "java.lang.String", SERVICE_SCOPE },
				{ "loanable", "java.lang.String", REQUEST_SCOPE },
				{ "dpCalculateManually", "java.lang.String", SERVICE_SCOPE },
				{ "remarkByMaker", "java.lang.String", SERVICE_SCOPE },
				{ SESSION_DUE_DATE_AND_STOCK_DETAILS, IGeneralChargeDetails.class.getName(), SERVICE_SCOPE },
				
				{ "totalLonable", "java.lang.String", SERVICE_SCOPE },
				{ "dueDate", "java.lang.String", SERVICE_SCOPE },
				{ "migrationFlag", "java.lang.String", SERVICE_SCOPE },
			
				});
	}

	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
		HashMap<String, Object> result = new HashMap<String, Object>();
		HashMap<String, Object> exceptionMap = new HashMap<String, Object>();
		HashMap<String, Map<String, ?>> temp = new HashMap<String, Map<String, ?>>();
		
		BigDecimal totalLonableForDP= new BigDecimal(0);
		BigDecimal fundedShare= new BigDecimal(0);
		IGeneralChargeDetails existingChargeDetails = (IGeneralChargeDetails) map.get(SESSION_DUE_DATE_AND_STOCK_DETAILS);
		String dueDateStr = "";
	
		String totalLonable =(String) map.get("totalLonable");
		if(null==totalLonable ||  "".equals(totalLonable)) {
			totalLonable="0";
		}
		System.out.println("totalLonable==="+totalLonable);

		String migrationFlag =(String) map.get("migrationFlag");
		ICollateralTrxValue itrxValue = (ICollateralTrxValue) map.get("serviceColObj");
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
		
		if(null!=cust.getDpSharePercent()){
		fundedShare= new BigDecimal(cust.getDpSharePercent());
		}
		}


		BigDecimal calculatedDP= new BigDecimal(0);
		List stockDetailsList= (ArrayList) map.get("serviceStockDetailsList");
		Collections.sort(stockDetailsList, new GCStockDetComparator());
		//ICollateralTrxValue itrxValue = (ICollateralTrxValue) map.get("serviceColObj");
		IGeneralCharge iAsset = (IGeneralCharge) itrxValue.getStagingCollateral();
		List displayList= new ArrayList();
		Locale locale = (Locale) map.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);
		//retriving Existing StockDetailsList
		if(stockDetailsList!=null && stockDetailsList.size()==0){
			exceptionMap.put("stockDetailsError", new ActionMessage("error.assets.liabilities.mandatory"));
		}
		
		IGeneralChargeDetails newChargeDetails = (IGeneralChargeDetails) map.get("form.stockDetailsObject");
		String chargeDetailLocation = newChargeDetails.getLocation();
		
		if(newChargeDetails.getLocationDetail()!=null && ! "".equals(newChargeDetails.getLocationDetail().trim())){
			if(ASSTValidator.isValidAlphaNumStringWithSpace(newChargeDetails.getLocationDetail())){
				exceptionMap.put("locationDetailError",new ActionMessage("error.string.invalidCharacter"));
			}
		}
		if(chargeDetailLocation==null || (chargeDetailLocation!=null && "".equals(chargeDetailLocation.trim()))){
			exceptionMap.put("locationError", new ActionMessage("label.please.select.option"));
		}
		
		IGeneralChargeStockDetails[] newStockDetails = null;
		if (!((exceptionMap != null) && (exceptionMap.size() > 0))) {
		if(chargeDetailLocation!=null && !"".equals(chargeDetailLocation.trim())){
			newStockDetails = getGeneralChargeStockDetailsArray(newChargeDetails, stockDetailsList);
		
		
		String selectedLocations = (String)map.get(SELECTED_STOCK_LOCATIONS);
		Map<String,String> selectedLocationMap = UIUtil.getMapFromDelimitedString(selectedLocations, ",");
		IGeneralChargeStockDetails[] newGeneralChargeStockDetails;
		IGeneralChargeStockDetails[] existingGeneralChargeStockDetails;
		boolean flagAdd=true;
		
		if(existingChargeDetails!=null ){
			existingGeneralChargeStockDetails = existingChargeDetails.getGeneralChargeStockDetails();
			if(existingGeneralChargeStockDetails!=null) {
								
				if(selectedLocationMap.containsKey(chargeDetailLocation)){
					exceptionMap.put("locationError", new ActionMessage("error.location.exist"));
				}else{
					selectedLocationMap.put(chargeDetailLocation, chargeDetailLocation);
					newGeneralChargeStockDetails= mergeArray(existingGeneralChargeStockDetails,newStockDetails);
					existingChargeDetails.setGeneralChargeStockDetails(newGeneralChargeStockDetails);
				}
			}else {
				selectedLocationMap.put(chargeDetailLocation, chargeDetailLocation);
				existingChargeDetails.setGeneralChargeStockDetails(newStockDetails);
			}
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
			helperFrom.setDueDate(DateUtil.formatDate(locale, existingChargeDetails.getDueDate())+","+existingChargeDetails.getDocCode());			
			 dueDateStr=helperFrom.getDueDate();
			System.out.println("***********************inside saveduedatecmd dueDate : "+dueDateStr);
			
			if(existingChargeDetails!=null){
   			//	BigDecimal totalLonable= new BigDecimal(0);
				BigDecimal totalLonableAsset= new BigDecimal(0);
				BigDecimal totalLonableLiability= new BigDecimal(0);
				existingGeneralChargeStockDetails = existingChargeDetails.getGeneralChargeStockDetails();
				if(existingGeneralChargeStockDetails!=null) {
					for (int j = 0; j < existingGeneralChargeStockDetails.length; j++) {
						IGeneralChargeStockDetails existingStockDetails = existingGeneralChargeStockDetails[j];
						if(locationID==(existingStockDetails.getLocationId())){
							//IRB Santosh
								if(("CurrentAsset".equals(existingStockDetails.getStockType()) || "ValueDebtors".equals(existingStockDetails.getStockType()) ) && ("YES".equals(existingStockDetails.getApplicableForDp()))){
									totalLonableAsset=totalLonableAsset.add(new BigDecimal(existingStockDetails.getLonable()));
								}else if (  ("CurrentLiabilities".equals(existingStockDetails.getStockType()) || "LessAdvances".equals(existingStockDetails.getStockType()) ) && ("YES".equals(existingStockDetails.getApplicableForDp()))){
									totalLonableLiability=totalLonableLiability.add(new BigDecimal(existingStockDetails.getLonable()));
								}

						}

						//helperFrom.setTotalLonable(totalLonable);

						if(null != migrationFlag &&  "Y".equalsIgnoreCase(migrationFlag.trim()) ){
							helperFrom.setTotalLonable((totalLonableAsset.subtract(totalLonableLiability)).toString());
							totalLonable=(totalLonableAsset.subtract(totalLonableLiability)).toString();
						}else {
							helperFrom.setTotalLonable(totalLonable);
						}
					//	totalLonableForDP=totalLonableForDP.add(new BigDecimal(helperFrom.getTotalLonable()));
						totalLonableForDP=totalLonableForDP.add(UIUtil.mapStringToBigDecimal(totalLonable) );
						calculatedDP=totalLonableForDP.multiply(fundedShare);
						calculatedDP=calculatedDP.divide(new BigDecimal(100),BigDecimal.ROUND_FLOOR);
						helperFrom.setCalculatedDP(calculatedDP.toString());
					}
				}
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
		
		}
		if(newChargeDetails.getLocationDetail()!=null && ! "".equals(newChargeDetails.getLocationDetail().trim())){
			if(ASSTValidator.isValidAlphaNumStringWithSpace(newChargeDetails.getLocationDetail())){
				exceptionMap.put("locationDetailError",new ActionMessage("error.string.invalidCharacter"));
			}
		}
	
				
		String statementName ="";
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
		result.put("subtype", map.get("subtype"));
		//added by bharat  19/04/2013 friday ----- start -----
//		result.put("fundedShare", fundedShare.toString());
		//added by bharat  19/04/2013 friday ------ end -------
		
		result.put("dpShare", fundedShare.toString());
		result.put("calculatedDP", calculatedDP.toString());
		result.put("loanable", totalLonableForDP);
		result.put("dpCalculateManually", map.get("dpCalculateManually"));
		result.put("remarkByMaker", map.get("remarkByMaker"));
		result.put("totalLonable", totalLonable);
		result.put("migrationFlag", migrationFlag);
		result.put("dueDate", dueDateStr);
		
	
		
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

	private static boolean addStockDetailsToChargeDetails(IGeneralChargeDetails chargeDetails,  IGeneralChargeStockDetails stocks) {
		if (chargeDetails == null || stocks == null)
			return false;
		
		IGeneralChargeStockDetails[] stocksArr = chargeDetails.getGeneralChargeStockDetails();
		if (stocksArr == null)
			stocksArr = new IGeneralChargeStockDetails[0];
		
		
		List<IGeneralChargeStockDetails> stocksList = new ArrayList<IGeneralChargeStockDetails>(
				Arrays.asList(stocksArr));
		stocksList.add(stocks);
		stocksArr = stocksList.toArray(new IGeneralChargeStockDetails[stocksList.size()]);
		chargeDetails.setGeneralChargeStockDetails(stocksArr);
		
		return true;
	}
	
	private IGeneralChargeStockDetails[] getGeneralChargeStockDetailsArray(
			IGeneralChargeDetails newChargeDetails, List stockDetailsList) {
		IGeneralChargeStockDetails[] newStockDetails= new IGeneralChargeStockDetails[stockDetailsList.size()];
		
		 Collections.sort(stockDetailsList, new GCStockDetComparator("K"));
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
