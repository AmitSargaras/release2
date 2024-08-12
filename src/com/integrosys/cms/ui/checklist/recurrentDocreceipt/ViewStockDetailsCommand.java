/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/collateral/assetbased/assetpostdatedchqs/AddChequeCommand.java,v 1.2 2005/08/26 10:12:37 hshii Exp $
 */

package com.integrosys.cms.ui.checklist.recurrentDocreceipt;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

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
import com.integrosys.cms.ui.collateral.assetbased.assetgencharge.AssetGenChargeHelper;

/**
 * @author $Author: hshii $<br>
 * @version $Revision: 1.2 $
 * @since $Date: 2005/08/26 10:12:37 $ Tag: $Name: $
 */

public class ViewStockDetailsCommand extends AbstractCommand {

	public String[][] getParameterDescriptor() {
		return (new String[][] { { "form.stockDetailsObject", "java.lang.Object", FORM_SCOPE },
				{ "serviceColObj", "com.integrosys.cms.app.collateral.trx.ICollateralTrxValue", SERVICE_SCOPE },
				{ "serviceStockDetailsList",  "java.util.List", SERVICE_SCOPE },
				{ com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY, "java.util.Locale", GLOBAL_SCOPE },
				{ "subtype", "java.lang.String", REQUEST_SCOPE },
				{ "locationId", "java.lang.String", REQUEST_SCOPE },
				{ "trxID", "java.lang.String", REQUEST_SCOPE },
				{ "dueDate", "java.lang.String", REQUEST_SCOPE },
				{ "parentPageFrom", "java.lang.String", REQUEST_SCOPE },
				{ com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY, "java.util.Locale", GLOBAL_SCOPE },		
		
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
				{ "liabilityMarginType", "java.lang.String", REQUEST_SCOPE }, 
				{ "liabilityFixedMarginValue", "java.lang.String", REQUEST_SCOPE },
				{ "form.stockDetailsObject", "java.lang.Object", FORM_SCOPE },
				{ "session.stockDetailsObject", "java.lang.Object", SERVICE_SCOPE },
				{ "parentPageFrom", "java.lang.String", SERVICE_SCOPE },

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
		Locale locale = (Locale) map.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);
		String locationID = (String)map.get("locationId");
		String insEditable="N";
		IGeneralChargeDetails stockDetailsObject = (IGeneralChargeDetails)map.get("form.stockDetailsObject");
		
		
		
		Date dueDate =null;
		String docCode = "";
		String dueDateStr=(String)map.get("dueDate");
		String trxID=(String)map.get("trxID");
		if(dueDateStr!=null && !"".equals(dueDateStr)){
			String[] split = dueDateStr.split(",");
			dueDate = DateUtil.convertDate(locale, split[0]);
			docCode=split[1];
		}
		

		ICollateralTrxValue itrxValue = (ICollateralTrxValue) map.get("serviceColObj");
		IGeneralCharge iAsset = (IGeneralCharge) itrxValue.getStagingCollateral();
		List stockDetailsList= new ArrayList();
		IGeneralChargeDetails[] existingGeneralChargeDetails = iAsset.getGeneralChargeDetails();
		IGeneralChargeDetails existingChargeDetails;
		IGeneralChargeDetails editOb= new OBGeneralChargeDetails();
		
		IGeneralChargeStockDetails[] existingGeneralChargeStockDetails;
		String locationDet="";
		if(existingGeneralChargeDetails!=null){
			for (int i = 0; i < existingGeneralChargeDetails.length; i++) {
				 existingChargeDetails = existingGeneralChargeDetails[i];
				if(existingChargeDetails!=null && null != existingChargeDetails.getDocCode()  && existingChargeDetails.getDocCode().equals(docCode)){
					existingGeneralChargeStockDetails = existingChargeDetails.getGeneralChargeStockDetails();
					for (int j = 0; j < existingGeneralChargeStockDetails.length; j++) {
						IGeneralChargeStockDetails existingStockDetails = existingGeneralChargeStockDetails[j];
						if(existingStockDetails.getLocationId()==Long.parseLong(locationID)){
							stockDetailsList.add(existingStockDetails);
							locationDet=existingStockDetails.getLocationDetail();
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
					editOb.setCalculatedDP(existingChargeDetails.getCalculatedDP());
					//editOb.setFundedShare(existingChargeDetails.getFundedShare());
					
					//Uma Khot:Cam upload and Dp field calculation CR
					editOb.setDpShare(existingChargeDetails.getDpShare());
					editOb.setLastApprovedBy(existingChargeDetails.getLastApprovedBy());
					editOb.setLastApprovedOn(existingChargeDetails.getLastApprovedOn());
					editOb.setLastUpdatedBy(existingChargeDetails.getLastUpdatedBy());
					editOb.setLastUpdatedOn(existingChargeDetails.getLastUpdatedOn());
				}
			}
		} 
		
		itrxValue.setStagingCollateral(iAsset);
		result.put("serviceColObj", itrxValue);
		result.put("subtype", map.get("subtype"));
		result.put("trxID", map.get("trxID"));
		//preparing for view
		result.put("insEditable", insEditable);
		result.put("dueDate", map.get("dueDate"));
		result.put("locationId", map.get("locationId"));
		result.put("locationList", AssetGenChargeHelper.getLocationList());
		result.put("serviceStockDetailsList", stockDetailsList);
		result.put("form.stockDetailsObject", editOb);
		result.put("session.stockDetailsObject", editOb);
		result.put("parentPageFrom", map.get("parentPageFrom"));
		
		DefaultLogger.debug(this, "inside ViewStockDetailsCommand");
		temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		return temp;
	}
}
