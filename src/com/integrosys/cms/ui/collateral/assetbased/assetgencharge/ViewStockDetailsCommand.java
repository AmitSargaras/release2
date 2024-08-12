/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/collateral/assetbased/assetpostdatedchqs/AddChequeCommand.java,v 1.2 2005/08/26 10:12:37 hshii Exp $
 */

package com.integrosys.cms.ui.collateral.assetbased.assetgencharge;

import static com.integrosys.cms.ui.collateral.CollateralConstant.SESSION_DUE_DATE_AND_STOCK_DETAILS;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Collections;
import com.integrosys.cms.app.collateral.bus.type.asset.subtype.gcharge.GCStockDetComparator;


import org.apache.commons.lang.StringUtils;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.collateral.bus.type.asset.subtype.gcharge.IGeneralCharge;
import com.integrosys.cms.app.collateral.bus.type.asset.subtype.gcharge.IGeneralChargeDetails;
import com.integrosys.cms.app.collateral.bus.type.asset.subtype.gcharge.IGeneralChargeStockDetails;
import com.integrosys.cms.app.collateral.bus.type.asset.subtype.gcharge.OBGeneralChargeDetails;
import com.integrosys.cms.app.collateral.trx.ICollateralTrxValue;

/**
 * @author $Author: hshii $<br>
 * @version $Revision: 1.2 $
 * @since $Date: 2005/08/26 10:12:37 $ Tag: $Name: $
 */

public class ViewStockDetailsCommand extends AbstractCommand {

	public String[][] getParameterDescriptor() {
		return (new String[][] { 
				{ "serviceColObj", "com.integrosys.cms.app.collateral.trx.ICollateralTrxValue", SERVICE_SCOPE },
				{ "serviceStockDetailsList",  "java.util.List", SERVICE_SCOPE },
				{ "event", String.class.getName(), REQUEST_SCOPE },
				{ com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY, "java.util.Locale", GLOBAL_SCOPE },
				{ "subtype", "java.lang.String", REQUEST_SCOPE },
				{ "locationId", "java.lang.String", REQUEST_SCOPE },
				{ "trxID", "java.lang.String", REQUEST_SCOPE },
				{ "dueDate", "java.lang.String", REQUEST_SCOPE },
				{ "stockdocMonth", "java.lang.String", REQUEST_SCOPE },
				{ "stockdocYear", "java.lang.String", REQUEST_SCOPE },
				{ "parentPageFrom", "java.lang.String", REQUEST_SCOPE },
				{ "totalLonable", "java.lang.String", REQUEST_SCOPE },
				{ "remarkByMaker", "java.lang.String", REQUEST_SCOPE },
				{ "dpCalculateManually", "java.lang.String", REQUEST_SCOPE },
				
				{ "migrationFlag", "java.lang.String", REQUEST_SCOPE },


				{ com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY, "java.util.Locale", GLOBAL_SCOPE },		
				{ SESSION_DUE_DATE_AND_STOCK_DETAILS, IGeneralChargeDetails.class.getName(), SERVICE_SCOPE },
		});
	}

	public String[][] getResultDescriptor() {
		return (new String[][] {
				{ "serviceColObj", "com.integrosys.cms.app.collateral.trx.ICollateralTrxValue", SERVICE_SCOPE },
				{ "displayList",  "java.util.List", SERVICE_SCOPE },
				{ "subtype", "java.lang.String", REQUEST_SCOPE }, 
				{ "dueDate", "java.lang.String", SERVICE_SCOPE },
				{ "insEditable", "java.lang.String", SERVICE_SCOPE },
				{ "locationId", "java.lang.String", SERVICE_SCOPE },
				{ "trxID", "java.lang.String", REQUEST_SCOPE },
				{ "serviceStockDetailsList",  "java.util.List", SERVICE_SCOPE },
				{ "locationList", "java.util.List", ICommonEventConstant.REQUEST_SCOPE },
				{ "assetsMarginType", "java.lang.String", REQUEST_SCOPE }, 
				{ "assetsFixedMarginValue", "java.lang.String", REQUEST_SCOPE }, 
				
				{ "assetsMarginTypeNew", "java.lang.String", REQUEST_SCOPE }, 
				{ "assetsFixedMarginValueNew", "java.lang.String", REQUEST_SCOPE }, 
				
				{ "liabilityMarginTypeAdv", "java.lang.String", REQUEST_SCOPE }, 
				{ "liabilityFixedMarginValueAdv", "java.lang.String", REQUEST_SCOPE },
				
				{ "liabilityMarginType", "java.lang.String", REQUEST_SCOPE }, 
				{ "liabilityFixedMarginValue", "java.lang.String", REQUEST_SCOPE },
				{ "form.stockDetailsObject", "java.lang.Object", FORM_SCOPE },
				{ "parentPageFrom", "java.lang.String", SERVICE_SCOPE },
				{ "stockdocMonth", "java.lang.String", SERVICE_SCOPE },
				{ "stockdocYear", "java.lang.String", SERVICE_SCOPE },

				{ "remarkByMaker", "java.lang.String", SERVICE_SCOPE },
				{ "totalLonable", "java.lang.String", SERVICE_SCOPE },
				{ "dpCalculateManually", "java.lang.String", SERVICE_SCOPE },
				{ "dpShare", "java.lang.String", SERVICE_SCOPE },
				{ "migrationFlag", "java.lang.String", SERVICE_SCOPE },
				

		});
	}

	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {

		HashMap result = new HashMap();
		HashMap exceptionMap = new HashMap();
		HashMap temp = new HashMap();
		Locale locale = (Locale) map.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);
		String locationID = (String)map.get("locationId");
		String insEditable="N";
		IGeneralChargeDetails stockDetailsObject = (IGeneralChargeDetails)map.get("form.stockDetailsObject");
		IGeneralChargeDetails sessionDueDateAndStocks = (IGeneralChargeDetails)map.get(SESSION_DUE_DATE_AND_STOCK_DETAILS);
		String event = (String) map.get("event");
		
		Date dueDate =null;
	//	String docCode = "";
		String dueDateStr=(String)map.get("dueDate");
		
		String trxID=(String)map.get("trxID");
		if(dueDateStr!=null && !"".equals(dueDateStr)){
			String[] split = dueDateStr.split(",");
			dueDate = DateUtil.convertDate(locale, split[0]);
	//		docCode=split[1];
		}
	String stockdocMonth = (String) map.get("stockdocMonth");
		String stockdocYear = (String) map.get("stockdocYear");
		
		String migrationFlag =(String) map.get("migrationFlag");
		
		String totalLonable =(String) map.get("totalLonable");
		if(null==totalLonable ||  "".equals(totalLonable)) {
			totalLonable="";
		}
		System.out.println("totalLonable in ViewStockDetailsCommand==="+totalLonable);
		
		System.out.println("ViewStockDetailsCommand.java stockdocMonth=>"+stockdocMonth+" stockdocYear=>"+stockdocYear);
			
		ICollateralTrxValue itrxValue = (ICollateralTrxValue) map.get("serviceColObj");
		List stockDetailsList= new ArrayList();
		IGeneralChargeDetails existingChargeDetails;
		IGeneralChargeDetails editOb= new OBGeneralChargeDetails();
		
		IGeneralChargeStockDetails[] existingGeneralChargeStockDetails;
		String locationDet="";

		existingChargeDetails = sessionDueDateAndStocks;
		if(existingChargeDetails!=null && existingChargeDetails.getGeneralChargeStockDetails() != null){
			existingGeneralChargeStockDetails = existingChargeDetails.getGeneralChargeStockDetails();
			for (int j = 0; j < existingGeneralChargeStockDetails.length; j++) {
				IGeneralChargeStockDetails existingStockDetails = existingGeneralChargeStockDetails[j];
				if(StringUtils.isNotBlank(locationID) && existingStockDetails.getLocationId()==Long.parseLong(locationID)){
					stockDetailsList.add(existingStockDetails);
					locationDet=existingStockDetails.getLocationDetail();
					editOb.setTotalLoanable(existingChargeDetails.getTotalLoanable());
					editOb.setRemarkByMaker(existingChargeDetails.getRemarkByMaker());
					editOb.setMigrationFlag_DP_CR(existingChargeDetails.getMigrationFlag_DP_CR());

					
				}
			}
			if(IGeneralChargeDetails.STATUS_INSURANCE_UPDATED.equals( existingChargeDetails.getStatus())){
				insEditable="Y";
			}else{
				insEditable="N";
			}
			editOb=existingChargeDetails;
			editOb.setLocation(locationID);
			editOb.setLocationDetail(locationDet);
		}
		
		boolean isErrorEvent = AssetGenChargeStockDetailsAction.EVENT_EDIT_STOCK_DETAILS_ERROR.equals(event) || AssetGenChargeStockDetailsAction.EVENT_EDIT_PREPARE_CURRENT_ERROR.equals(event);
	
		result.put("serviceColObj", itrxValue);
		result.put("subtype", map.get("subtype"));
		result.put("trxID", map.get("trxID"));
		//preparing for view
		result.put("insEditable", insEditable);
		result.put("dueDate", dueDateStr);
		result.put("locationId", map.get("locationId"));
		result.put("locationList", AssetGenChargeHelper.getLocationList());
		
		
		Collections.sort(stockDetailsList, new GCStockDetComparator("K"));
		result.put("serviceStockDetailsList", stockDetailsList);
		result.put("form.stockDetailsObject", isErrorEvent?null:editOb);
		result.put("parentPageFrom", map.get("parentPageFrom"));
		result.put("stockdocMonth", stockdocMonth);
		result.put("stockdocYear", stockdocYear);
		result.put("remarkByMaker", map.get("remarkByMaker"));
		result.put("dpCalculateManually", map.get("dpCalculateManually")); 
		
		result.put("dpShare", map.get("dpShare"));
		result.put("totalLonable", totalLonable);
		
		result.put("migrationFlag", migrationFlag);
		DefaultLogger.debug(this, "inside ViewStockDetailsCommand");
		temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		return temp;
	}
}
