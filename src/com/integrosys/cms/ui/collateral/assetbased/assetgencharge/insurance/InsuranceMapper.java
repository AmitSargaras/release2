/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/collateral/assetbased/assetgencharge/insurance/InsuranceMapper.java,v 1.11 2006/04/11 09:01:13 pratheepa Exp $
 */
package com.integrosys.cms.ui.collateral.assetbased.assetgencharge.insurance;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.base.businfra.currency.CurrencyCode;
import com.integrosys.base.businfra.currency.CurrencyManager;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.exception.MapperException;
import com.integrosys.cms.app.collateral.bus.IInsurancePolicy;
import com.integrosys.cms.app.collateral.bus.OBInsurancePolicy;
import com.integrosys.cms.app.collateral.bus.type.asset.subtype.gcharge.IGenChargeMapEntry;
import com.integrosys.cms.app.collateral.bus.type.asset.subtype.gcharge.IGeneralCharge;
import com.integrosys.cms.app.collateral.bus.type.asset.subtype.gcharge.OBGenChargeMapEntry;
import com.integrosys.cms.ui.collateral.CollateralConstant;
import com.integrosys.cms.ui.collateral.assetbased.assetgencharge.AssetGenChargeUtil;
import com.integrosys.cms.ui.common.ForexHelper;
import com.integrosys.cms.ui.common.UIUtil;

/**
 * Description
 * 
 * @author $Author: pratheepa $<br>
 * @version $Revision: 1.11 $
 * @since $Date: 2006/04/11 09:01:13 $ Tag: $Name: $
 */

public class InsuranceMapper extends AbstractCommonMapper {
	public Object mapFormToOB(CommonForm cForm, HashMap inputs) throws MapperException {
		InsuranceForm aForm = (InsuranceForm) cForm;

		IGeneralCharge iCol = (IGeneralCharge) inputs.get("col");

		Locale locale = (Locale) inputs.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);
		String strKey = (String) inputs.get("indexID");
		/*
		 * if (!isEmptyOrNull((String)inputs.get("strKey"))) { strKey =
		 * (String)inputs.get("strKey"); }
		 */

		String tab = (String) inputs.get("tab");

		IInsurancePolicy insurance = null;
		if ("-1".equals(strKey)) {
			insurance = AssetGenChargeUtil.getInsuranceInfo(iCol, aForm.getRefID());
			if (insurance == null) {
				insurance = new OBInsurancePolicy();
			}
			if (CollateralConstant.TAB_STOCK.equals(tab)) {
				insurance.setCategory(IInsurancePolicy.STOCK);
			}
			else {
				insurance.setCategory(IInsurancePolicy.FAO);
			}
		}
		else {
			insurance = AssetGenChargeUtil.getInsuranceInfo(iCol, strKey);
		}
		DefaultLogger.debug(this, "refID: " + aForm.getRefID());
		Date stageDate = null;
		try {
			insurance.setRefID(aForm.getRefID());
			insurance.setPolicyNo(aForm.getInsPolicyNum().trim());
			insurance.setInsurerName(aForm.getInsurerName());
			insurance.setInsuranceType(aForm.getInsuranceType());
			if (!isEmptyOrNull(aForm.getDocumentNo())) {
				insurance.setDocumentNo(aForm.getDocumentNo());
			}
			else {
				insurance.setDocumentNo(null);
			}

			if (!isEmptyOrNull(aForm.getLmtProfileId())) {
				insurance.setLmtProfileId(Long.valueOf(aForm.getLmtProfileId()));
			}
			else {
				insurance.setLmtProfileId(null);
			}
			if (!isEmptyOrNull(aForm.getExpiryDateIns())) {
				stageDate = UIUtil.compareDate(locale, insurance.getExpiryDate(), aForm.getExpiryDateIns());
				insurance.setExpiryDate(stageDate);
			}
			else {
				insurance.setExpiryDate(null);
			}

			insurance.setCurrencyCode(aForm.getInsPolicyCurrency());

			if (isEmptyOrNull(aForm.getInsurableAmt())) {
				insurance.setInsurableAmount(null);
			}
			else {
				insurance.setInsurableAmount(CurrencyManager.convertToAmount(locale, insurance.getCurrencyCode(), aForm
						.getInsurableAmt()));
			}

			if (isEmptyOrNull(aForm.getInsuredAmt())) {
				insurance.setInsuredAmount(null);
			}
			else {
				insurance.setInsuredAmount(CurrencyManager.convertToAmount(locale, insurance.getCurrencyCode(), aForm
						.getInsuredAmt()));
			}

			if (!isEmptyOrNull(aForm.getEffectiveDateIns())) {
				stageDate = UIUtil.compareDate(locale, insurance.getEffectiveDate(), aForm.getEffectiveDateIns());
				insurance.setEffectiveDate(stageDate);
			}
			else {
				insurance.setEffectiveDate(null);
			}

			insurance.setInsuredAgainst(aForm.getInsuredAgainst());

			HashMap insuranceMap = (HashMap) iCol.getInsurance();
			insuranceMap.put(insurance.getRefID(), insurance);

			// set the linkage between insurance and stock/fao
			HashMap objInsuranceMap = new HashMap();
			HashMap insuranceObjMap = new HashMap();
			if (CollateralConstant.TAB_STOCK.equals(tab)) {
				objInsuranceMap = (HashMap) iCol.get_Stock_Insurance_Map();
				insuranceObjMap = (HashMap) iCol.get_Insurance_Stock_Map();
			}
			else {
				objInsuranceMap = (HashMap) iCol.get_FixedAssetOthers_Insurance_Map();
				insuranceObjMap = (HashMap) iCol.get_Insurance_FixedAssetOthers_Map();
			}
			DefaultLogger.debug(this, "<<<< objInsuranceMap size: "
					+ (objInsuranceMap == null ? "null" : String.valueOf(objInsuranceMap.size())));
			DefaultLogger.debug(this, "<<<< insuranceObjMap size: "
					+ (insuranceObjMap == null ? "null" : String.valueOf(insuranceObjMap.size())));
			if (objInsuranceMap == null) {
				objInsuranceMap = new HashMap();
			}
			if (insuranceObjMap == null) {
				insuranceObjMap = new HashMap();
			}

			if (aForm.getInsuranceLinks() != null) {
				ArrayList objList = new ArrayList();
				if (insuranceObjMap.containsKey(insurance.getRefID())) {
					objList = (ArrayList) insuranceObjMap.get(insurance.getRefID());
				}
				if (objList == null) {
					objList = new ArrayList();
				}
				ArrayList deleteList = getDeleteList(objList, aForm.getInsuranceLinks());
				if ((deleteList != null) && (deleteList.size() > 0)) {
					objInsuranceMap = deleteObjInsuranceLinkage(deleteList, objInsuranceMap);
					objList = deleteInsuranceObjLinkage(deleteList, objList);
				}

				for (int i = 0; i < aForm.getInsuranceLinks().length; i++) {
					String objKey = aForm.getInsuranceLinks()[i];
					DefaultLogger.debug(this, "<<<<<<<<< objKey: " + objKey);
					ArrayList insuranceList = new ArrayList();
					if (objInsuranceMap.containsKey(objKey)) {
						insuranceList = (ArrayList) objInsuranceMap.get(objKey);
					}

					if (!hasObjInInsuranceLink(objList, objKey)) {
						OBGenChargeMapEntry entry = new OBGenChargeMapEntry();
						entry.setInsuranceID(insurance.getRefID());
						entry.setEntryValueID(objKey);
						objList.add(entry);

						// By right if obj not found in insuranceMap, then
						// insurance will also not found in objMap (stock / fao)
						if (insuranceList == null) {
							insuranceList = new ArrayList();
						}
						insuranceList.add(entry);
					}
					objInsuranceMap.put(objKey, insuranceList);
				}
				insuranceObjMap.put(insurance.getRefID(), objList);
			}
			else {
				if (insuranceObjMap.containsKey(insurance.getRefID())) {
					ArrayList objList = (ArrayList) insuranceObjMap.get(insurance.getRefID());
					objInsuranceMap = deleteObjInsuranceLinkage(objList, objInsuranceMap);
					insuranceObjMap.remove(insurance.getRefID());
				}
			}

			if (CollateralConstant.TAB_STOCK.equals(tab)) {
				iCol.set_Stock_Insurance_Map(objInsuranceMap);
				iCol.set_Insurance_Stock_Map(insuranceObjMap);
			}
			else {
				iCol.set_FixedAssetOthers_Insurance_Map(objInsuranceMap);
				iCol.set_Insurance_FixedAssetOthers_Map(insuranceObjMap);
			}
		}
		catch (Exception e) {
			DefaultLogger.debug(this + " InsuranceMapper", "error is :" + e.toString());
			e.printStackTrace();
			throw new MapperException(e.getMessage());
		}
		HashMap returnMap = new HashMap();
		returnMap.put("col", iCol);
		returnMap.put("strKey", insurance.getRefID());
		return returnMap;
	}

	public CommonForm mapOBToForm(CommonForm cForm, Object obj, HashMap inputs) throws MapperException {
		InsuranceForm aForm = (InsuranceForm) cForm;

		Locale locale = (Locale) inputs.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);
		IInsurancePolicy insurance = null;

		String event = (String) inputs.get("event");
		CurrencyCode cmsCcy = null;

		HashMap insuranceMap = (HashMap) obj;
		insurance = (IInsurancePolicy) insuranceMap.get("obj");
		String cmsSecurityCcy = (String) insuranceMap.get("ccy");
		cmsCcy = new CurrencyCode(cmsSecurityCcy);

		aForm.setInsuranceLinks((String[]) insuranceMap.get("links"));
		aForm.setCmsSecurityCurrency(cmsSecurityCcy);

		Amount amt = null;
		double value = 0;
		ForexHelper fr = new ForexHelper();

		if (insurance != null) { // Edit Insurance
			DefaultLogger.debug(this, "refID: " + insurance.getRefID());
			aForm.setRefID(insurance.getRefID());
			aForm.setInsPolicyNum(insurance.getPolicyNo());
			aForm.setInsurerName(insurance.getInsurerName());
			aForm.setInsuranceType(insurance.getInsuranceType());
			aForm.setExpiryDateIns(DateUtil.formatDate(locale, insurance.getExpiryDate()));
			if (insurance.getCurrencyCode() != null) {
				aForm.setInsPolicyCurrency(insurance.getCurrencyCode());
			}
			else {
				aForm.setInsPolicyCurrency(cmsSecurityCcy);
			}
			if ((insurance.getDocumentNo() != null) && (insurance.getDocumentNo().trim().length() > 0)) {
				aForm.setDocumentNo(insurance.getDocumentNo());
			}

			if (insurance.getLmtProfileId() != null) {
				aForm.setLmtProfileId(String.valueOf(insurance.getLmtProfileId()));
			}

			amt = insurance.getInsurableAmount();
			if ((amt != null) && (amt.getCurrencyCode() != null)) {
				try {
					aForm.setInsurableAmt(CurrencyManager.convertToString(locale, amt));
				}
				catch (Exception e) {
					DefaultLogger.error(this, "exception thrown...");
					throw new MapperException(e.getMessage());
				}
			}

			amt = insurance.getInsuredAmount();
			if ((amt != null) && (amt.getCurrencyCode() != null)) {
				try {
					aForm.setInsuredAmt(CurrencyManager.convertToString(locale, amt));
				}
				catch (Exception e) {
					DefaultLogger.error(this, "exception thrown...");
					throw new MapperException(e.getMessage());
				}
				try {
					value = fr.convertAmount(amt, cmsCcy);
					aForm.setInsuredAmtCMS(CurrencyManager.convertToString(locale, new Amount(value, cmsCcy)));
				}
				catch (Exception e) {
					DefaultLogger.error(this, "Caught Forex Exception!", e);
					aForm.setInsuredAmtCMS("Forex Error");
				}
			}

			aForm.setEffectiveDateIns(DateUtil.formatDate(locale, insurance.getEffectiveDate()));
			aForm.setInsuredAgainst(insurance.getInsuredAgainst());
		}
		else {
			aForm.setInsPolicyCurrency(cmsSecurityCcy);
		}

		return aForm;
	}

	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ "indexID", "java.lang.String", REQUEST_SCOPE },
				// {"strKey", "java.lang.String", REQUEST_SCOPE},
				{ com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY, "java.util.Locale", GLOBAL_SCOPE },
				{ "tab", "java.lang.String", SERVICE_SCOPE },
				{ "serviceColObj", "com.integrosys.cms.app.collateral.trx.ICollateralTrxValue", SERVICE_SCOPE },
				{ "event", "java.lang.String", REQUEST_SCOPE },
				{ "col", "com.integrosys.cms.app.collateral.bus.type.asset.subtype.gcharge.IGeneralCharge",
						SERVICE_SCOPE }, });
	}

	private boolean hasObjInInsuranceLink(ArrayList objList, String objKey) {
		if ((objList == null) || objList.isEmpty() || (objKey == null)) {
			return false;
		}
		Iterator itr = objList.iterator();
		while (itr.hasNext()) {
			IGenChargeMapEntry entry = (IGenChargeMapEntry) itr.next();
			if (entry.getEntryValueID().equals(objKey)) {
				return true;
			}
		}
		return false;
	}

	private ArrayList getDeleteList(ArrayList objList, String[] insuranceLinks) {
		ArrayList deleteList = new ArrayList();
		if (objList != null) {
			Iterator itr = objList.iterator();
			while (itr.hasNext()) {
				IGenChargeMapEntry entry = (IGenChargeMapEntry) itr.next();
				boolean found = false;
				for (int i = 0; i < insuranceLinks.length; i++) {
					if (entry.getEntryValueID().equals(insuranceLinks[i])) {
						found = true;
						break;
					}
				}

				if (!found) {
					deleteList.add(entry);
				}
			}
		}
		return deleteList;
	}

	private HashMap deleteObjInsuranceLinkage(ArrayList deleteList, HashMap objInsuranceMap) {
		if (deleteList != null) {
			Iterator itr = deleteList.iterator();
			while (itr.hasNext()) {
				IGenChargeMapEntry entry = (IGenChargeMapEntry) itr.next();
				ArrayList insuranceList = (ArrayList) objInsuranceMap.get(entry.getEntryValueID());
				int index = -1;
				Iterator entryItr = insuranceList.iterator();
				boolean found = false;
				while (entryItr.hasNext()) {
					index++;
					IGenChargeMapEntry tmp = (IGenChargeMapEntry) entryItr.next();
					if (tmp.getInsuranceID().equals(entry.getInsuranceID())) {
						found = true;
						break;
					}
				}
				if (found) {
					insuranceList.remove(index);
				}
				objInsuranceMap.put(entry.getEntryValueID(), insuranceList);
			}
		}

		return objInsuranceMap;
	}

	private ArrayList deleteInsuranceObjLinkage(ArrayList deleteList, ArrayList objList) {
		if (deleteList != null) {
			Iterator itr = deleteList.iterator();
			while (itr.hasNext()) {
				IGenChargeMapEntry entry = (IGenChargeMapEntry) itr.next();
				int index = objList.indexOf(entry);
				objList.remove(index);
			}
		}
		return objList;
	}
}
