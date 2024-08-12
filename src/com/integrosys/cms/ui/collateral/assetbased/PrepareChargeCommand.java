/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/collateral/assetbased/PrepareChargeCommand.java,v 1.19 2006/09/15 08:32:03 hshii Exp $
 */

package com.integrosys.cms.ui.collateral.assetbased;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.collateral.bus.ICollateralLimitMap;
import com.integrosys.cms.app.collateral.bus.ILimitCharge;
import com.integrosys.cms.app.collateral.trx.ICollateralTrxValue;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.ui.collateral.CategoryCodeConstant;
import com.integrosys.cms.ui.collateral.LEList;
import com.integrosys.cms.ui.common.CommonCodeList;

/**
 * @author $Author: hshii $<br>
 * @version $Revision: 1.19 $
 * @since $Date: 2006/09/15 08:32:03 $
 * Tag: $Name:  $
 */

/**
 * Created by IntelliJ IDEA. User: ssathish Date: Jul 2, 2003 Time: 1:26:03 PM
 * To change this template use Options | File Templates.
 */
public class PrepareChargeCommand extends AbstractCommand {

	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ "serviceColObj", "com.integrosys.cms.app.collateral.trx.ICollateralTrxValue", SERVICE_SCOPE },
				{ "from_event", "java.lang.String", REQUEST_SCOPE }, });

	}

	/**
	 * Defines an two dimensional array with the result list to be expected as a
	 * result from the doExecute method using a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getResultDescriptor() {
		return (new String[][] { { "natureOfChargeID", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },
				{ "natureOfChargeValue", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },
				{ "natureOfChargeMap", "java.util.HashMap", ICommonEventConstant.REQUEST_SCOPE },
				{ "rankID", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },
				{ "rankValue", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },
				{ "LEID", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },
				{ "LEValue", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },
				{ "colLimitMap", "java.lang.String[]", ICommonEventConstant.REQUEST_SCOPE },
				{ "colLimitMapValue", "java.util.HashMap", ICommonEventConstant.REQUEST_SCOPE },
				{ "colLimitStatusMap", "java.util.HashMap", ICommonEventConstant.REQUEST_SCOPE },
				{ "colChargeLimitIDMap", "java.util.HashMap", ICommonEventConstant.REQUEST_SCOPE },
				{ "securityId", "java.lang.String", ICommonEventConstant.REQUEST_SCOPE },
				{ "chargeID", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },
				{ "chargeValue", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE }, });

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

		LEList list1 = LEList.getInstance();
		result.put("LEID", list1.getLEID());
		result.put("LEValue", list1.getLEValue());

		RankList rank = RankList.getInstance();
		Collection list = rank.getRankListID();
		result.put("rankID", list);
		list = rank.getRankListValue();
		result.put("rankValue", list);
		ICollateralTrxValue itrxValue = ((ICollateralTrxValue) map.get("serviceColObj"));

		String subtypecode = "";

		if (itrxValue.getCollateral() != null) {
			subtypecode = itrxValue.getCollateral().getCollateralSubType().getSubTypeCode();
		}
		else {
			subtypecode = itrxValue.getStagingCollateral().getCollateralSubType().getSubTypeCode();
		}

		Collection chargeListID = new ArrayList();
		Collection chargeListValue = new ArrayList();
		HashMap chargeMap = new HashMap();
		if (subtypecode.equals(ICMSConstant.COLTYPE_ASSET_SPEC_CHARGE_AIRCRAFT)) {
			com.integrosys.cms.ui.collateral.assetbased.assetaircraft.NatureOfChargeList nature = com.integrosys.cms.ui.collateral.assetbased.assetaircraft.NatureOfChargeList
					.getInstance();
			chargeListID = nature.getNatureOfChargeListID();
			chargeListValue = nature.getNatureOfChargeListValue();
			chargeMap = nature.getNatureOfChargeMap();
		}
		else if (subtypecode.equals(ICMSConstant.COLTYPE_ASSET_SPEC_CHARGE_OTHERS)) {
			com.integrosys.cms.ui.collateral.assetbased.assetspecother.NatureOfChargeList nature = com.integrosys.cms.ui.collateral.assetbased.assetspecother.NatureOfChargeList
					.getInstance();
			chargeListID = nature.getNatureOfChargeListID();
			chargeListValue = nature.getNatureOfChargeListValue();
			chargeMap = nature.getNatureOfChargeMap();
		}
		else if (subtypecode.equals(ICMSConstant.COLTYPE_ASSET_SPEC_CHARGE_PLANT)) {
			com.integrosys.cms.ui.collateral.assetbased.assetspecplant.NatureOfChargeList nature = com.integrosys.cms.ui.collateral.assetbased.assetspecplant.NatureOfChargeList
					.getInstance();
			chargeListID = nature.getNatureOfChargeListID();
			chargeListValue = nature.getNatureOfChargeListValue();
			chargeMap = nature.getNatureOfChargeMap();
		}
		else if (subtypecode.equals(ICMSConstant.COLTYPE_ASSET_SPEC_CHARGE_VEH)) {
			com.integrosys.cms.ui.collateral.assetbased.assetspecvehicles.NatureOfChargeList nature = com.integrosys.cms.ui.collateral.assetbased.assetspecvehicles.NatureOfChargeList
					.getInstance();
			chargeListID = nature.getNatureOfChargeListID();
			chargeListValue = nature.getNatureOfChargeListValue();
			chargeMap = nature.getNatureOfChargeMap();
		}
		else if (subtypecode.equals(ICMSConstant.COLTYPE_ASSET_VESSEL)) {
			com.integrosys.cms.ui.collateral.assetbased.assetvessel.NatureOfChargeList nature = com.integrosys.cms.ui.collateral.assetbased.assetvessel.NatureOfChargeList
					.getInstance();
			chargeListID = nature.getNatureOfChargeListID();
			chargeListValue = nature.getNatureOfChargeListValue();
			chargeMap = nature.getNatureOfChargeMap();
		}
		else if (subtypecode.equals(ICMSConstant.COLTYPE_ASSET_SPEC_CHARGE_GOLD)) {
			CommonCodeList commonCodeList = CommonCodeList.getInstance(CategoryCodeConstant.NAT_OF_CHARGE_ASSETGOLD);
			chargeListID = commonCodeList.getCommonCodeValues();
			chargeListValue = commonCodeList.getCommonCodeLabels();
			chargeMap = new LinkedHashMap(commonCodeList.getLabelValueMap());
		}
		else { // Asset based general charge
			com.integrosys.cms.ui.collateral.assetbased.assetgencharge.NatureOfChargeList nature = com.integrosys.cms.ui.collateral.assetbased.assetgencharge.NatureOfChargeList
					.getInstance();
			chargeListID = nature.getNatureOfChargeListID();
			chargeListValue = nature.getNatureOfChargeListValue();
			chargeMap = nature.getNatureOfChargeMap();
		}
		result.put("natureOfChargeID", chargeListID);
		result.put("natureOfChargeValue", chargeListValue);
		result.put("natureOfChargeMap", chargeMap);

		ChargeTypeList chargeList = ChargeTypeList.getInstance();
		result.put("chargeID", chargeList.getChargeTypeID());
		result.put("chargeValue", chargeList.getChargeTypeValue());

		String event = (String) map.get("from_event");
		ICollateralLimitMap[] collateralLimitMaps = new ICollateralLimitMap[0];
		ILimitCharge[] limitCharge;
		String securityID = (itrxValue.getCollateral() != null) ? String.valueOf(itrxValue.getCollateral()
				.getCollateralID()) : "";
		Collection colLimitID = new ArrayList();
		if ((event != null) && event.equals("read")) {
			collateralLimitMaps = itrxValue.getCollateral().getCollateralLimits();
			limitCharge = itrxValue.getCollateral().getLimitCharges();
			// securityID = itrxValue.getCollateral().getSCISecurityID();

		}
		else {
			collateralLimitMaps = itrxValue.getStagingCollateral().getCollateralLimits();
			limitCharge = itrxValue.getStagingCollateral().getLimitCharges();
			/*
			 * if (itrxValue.getStagingCollateral().getSCISecurityID() != null)
			 * securityID =
			 * String.valueOf(itrxValue.getStagingCollateral().getCollateralID
			 * ());
			 */
		}
		HashMap limitMap = new HashMap();
		HashMap limitStatusMap = new HashMap();
		HashMap chargeLimitIDMap = new HashMap();

		int collateralLimitMapsLength = (collateralLimitMaps == null) ? 0 : collateralLimitMaps.length;
		for (int i = 0; i < collateralLimitMapsLength; i++) {
			String prodDesc = null;
			if (collateralLimitMaps[i].getLimitType() != null) {
				prodDesc = CommonCodeList.getInstance(ICMSConstant.COMMON_CODE_PRODUCT_TYPE).getCommonCodeLabel(
						collateralLimitMaps[i].getLimitType());
			}
			String status = "";
			if ((collateralLimitMaps[i].getSCIStatus() != null)
					&& collateralLimitMaps[i].getSCIStatus().equals(ICMSConstant.HOST_STATUS_DELETE)) {
				status = "DELETED";
			}
			else {
				colLimitID.add(String.valueOf(collateralLimitMaps[i].getChargeID()));
			}
			String[] idArr = new String[2];
			idArr[0] = collateralLimitMaps[i].getCustomerCategory()
					.equals(ICMSConstant.CUSTOMER_CATEGORY_MAIN_BORROWER) ? String.valueOf(collateralLimitMaps[i]
					.getSCILimitID()) : String.valueOf(collateralLimitMaps[i].getSCICoBorrowerLimitID());
			idArr[1] = String.valueOf(collateralLimitMaps[i].getSCISysGenID());
			chargeLimitIDMap.put(String.valueOf(collateralLimitMaps[i].getChargeID()), idArr);
			limitStatusMap.put(String.valueOf(collateralLimitMaps[i].getChargeID()), status);
			limitMap.put(String.valueOf(collateralLimitMaps[i].getChargeID()), prodDesc);
		}
		if (limitCharge != null) {
			for (int i = 0; i < limitCharge.length; i++) {
				if (limitCharge[i] != null) {
					collateralLimitMaps = limitCharge[i].getLimitMaps();
					for (int j = 0; (collateralLimitMaps != null) && (j < collateralLimitMaps.length); j++) {
						if (!chargeLimitIDMap.containsKey(String.valueOf(collateralLimitMaps[j].getChargeID()))) {
							String prodDesc = null;
							if (collateralLimitMaps[j].getLimitType() != null) {
								prodDesc = CommonCodeList.getInstance(ICMSConstant.COMMON_CODE_PRODUCT_TYPE)
										.getCommonCodeLabel(collateralLimitMaps[i].getLimitType());
							}
							String status = "";
							if ((collateralLimitMaps[j].getSCIStatus() != null)
									&& collateralLimitMaps[j].getSCIStatus().equals(ICMSConstant.HOST_STATUS_DELETE)) {
								status = "DELETED";
							}
							else {
								colLimitID.add(String.valueOf(collateralLimitMaps[j].getChargeID()));
							}
							String[] idArr = new String[2];
							idArr[0] = collateralLimitMaps[i].getCustomerCategory().equals(
									ICMSConstant.CUSTOMER_CATEGORY_MAIN_BORROWER) ? String
									.valueOf(collateralLimitMaps[j].getSCILimitID()) : String
									.valueOf(collateralLimitMaps[j].getSCICoBorrowerLimitID());
							idArr[1] = String.valueOf(collateralLimitMaps[j].getSCISysGenID());
							chargeLimitIDMap.put(String.valueOf(collateralLimitMaps[j].getChargeID()), idArr);
							limitStatusMap.put(String.valueOf(collateralLimitMaps[j].getChargeID()), status);
							limitMap.put(String.valueOf(collateralLimitMaps[j].getChargeID()), prodDesc);
						}
					}
				}
			}
		}
		if ((event != null) && event.equals("process")) {
			if (itrxValue.getCollateral() != null) {
				limitCharge = itrxValue.getCollateral().getLimitCharges();
				if (limitCharge != null) {
					for (int i = 0; i < limitCharge.length; i++) {
						if (limitCharge[i] != null) {
							collateralLimitMaps = limitCharge[i].getLimitMaps();
							for (int j = 0; (collateralLimitMaps != null) && (j < collateralLimitMaps.length); j++) {
								if (!chargeLimitIDMap.containsKey(String.valueOf(collateralLimitMaps[j].getChargeID()))) {
									String prodDesc = null;
									if (collateralLimitMaps[j].getLimitType() != null) {
										prodDesc = CommonCodeList.getInstance(ICMSConstant.COMMON_CODE_PRODUCT_TYPE)
												.getCommonCodeLabel(collateralLimitMaps[i].getLimitType());
									}
									String status = "";
									if ((collateralLimitMaps[j].getSCIStatus() != null)
											&& collateralLimitMaps[j].getSCIStatus().equals(
													ICMSConstant.HOST_STATUS_DELETE)) {
										status = "DELETED";
									}
									else {
										colLimitID.add(String.valueOf(collateralLimitMaps[j].getChargeID()));
									}
									String[] idArr = new String[2];
									idArr[0] = collateralLimitMaps[j].getCustomerCategory().equals(
											ICMSConstant.CUSTOMER_CATEGORY_MAIN_BORROWER) ? String
											.valueOf(collateralLimitMaps[j].getSCILimitID()) : String
											.valueOf(collateralLimitMaps[j].getSCICoBorrowerLimitID());
									idArr[1] = String.valueOf(collateralLimitMaps[j].getSCISysGenID());
									chargeLimitIDMap.put(String.valueOf(collateralLimitMaps[j].getChargeID()), idArr);
									limitStatusMap.put(String.valueOf(collateralLimitMaps[j].getChargeID()), status);
									limitMap.put(String.valueOf(collateralLimitMaps[j].getChargeID()), prodDesc);
								}
							}
						}
					}
				}
			}
		}

		DefaultLogger.debug(this, "----- limitMap size: " + limitMap.size());
		result.put("colLimitMap", (String[]) colLimitID.toArray(new String[0]));
		result.put("colLimitMapValue", limitMap);
		result.put("colLimitStatusMap", limitStatusMap);
		result.put("colChargeLimitIDMap", chargeLimitIDMap);

		result.put("securityId", securityID);

		temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		return temp;
	}
}
