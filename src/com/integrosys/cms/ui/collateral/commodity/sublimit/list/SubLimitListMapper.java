package com.integrosys.cms.ui.collateral.commodity.sublimit.list;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.common.Constants;
import com.integrosys.base.uiinfra.exception.MapperException;
import com.integrosys.base.uiinfra.mapper.MapperUtil;
import com.integrosys.cms.app.collateral.bus.ICollateralLimitMap;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.ui.collateral.CollateralHelper;
import com.integrosys.cms.ui.collateral.commodity.sublimit.SLUIConstants;
import com.integrosys.cms.ui.collateral.commodity.sublimit.SubLimitUtils;

public class SubLimitListMapper extends AbstractCommonMapper {

	public CommonForm mapOBToForm(CommonForm aForm, Object obj, HashMap map) throws MapperException {
		DefaultLogger.debug(this, " mapOBToForm - Begin.");
		SubLimitListForm sllForm = (SubLimitListForm) aForm;
		HashMap limitMap = (HashMap) obj;
		Locale locale = (Locale) map.get(Constants.GLOBAL_LOCALE_KEY);
		setSubLimitDetail2Form(sllForm, limitMap, locale);
		DefaultLogger.debug(this, " mapOBToForm - End.");
		return sllForm;
	}

	public Object mapFormToOB(CommonForm aForm, HashMap map) throws MapperException {
		DefaultLogger.debug(this, " mapFormToOB - Begin.");
		SubLimitListForm sllForm = (SubLimitListForm) aForm;
		HashMap limitMap = (HashMap) map.get(SLUIConstants.AN_CMDT_LIMIT_MAP);
		Locale locale = (Locale) map.get(Constants.GLOBAL_LOCALE_KEY);
		setForm2Object(sllForm, limitMap, locale);
		DefaultLogger.debug(this, " mapFormToOB - End.");
		return limitMap;
	}

	/*
	 * @see com.integrosys.base.uiinfra.common.ICommand#getParameterDescriptor()
	 */
	public String[][] getParameterDescriptor() {
		return (new String[][] { { Constants.GLOBAL_LOCALE_KEY, SLUIConstants.CN_LOCALE, GLOBAL_SCOPE },
				{ SLUIConstants.AN_CMDT_LIMIT_MAP, SLUIConstants.CN_HASHMAP, SERVICE_SCOPE } });
	}

	private void setSubLimitDetail2Form(SubLimitListForm sllForm, HashMap limitMap, Locale locale) {
		List collaterPoolChkList = new ArrayList();
		List specificTrxChkList = new ArrayList();
		List cashReqQtyChkList = new ArrayList();
		List cashReqQtyList = new ArrayList();
		if (limitMap != null) {
			Iterator itr = SubLimitUtils.getSortedLimitDetails(limitMap).iterator();
			while (itr.hasNext()) {
				ICollateralLimitMap colLimitMap = (ICollateralLimitMap) itr.next();
				String key = CollateralHelper.getColLimitMapLimitID(colLimitMap);
				if (colLimitMap.getIsCollateralPool()) {
					collaterPoolChkList.add(key);
				}
				if (colLimitMap.getIsSpecificTrx()) {
					specificTrxChkList.add(key);
				}
				if (colLimitMap.getCashReqPct() != ICMSConstant.DOUBLE_INVALID_VALUE) {
					cashReqQtyChkList.add(key);
					String qty = MapperUtil.mapDoubleToString(colLimitMap.getCashReqPct(), 0, locale);
					cashReqQtyList.add(qty);
				}
				else {
					cashReqQtyChkList.add("");
					cashReqQtyList.add("");
				}
			}
		}
		// DefaultLogger.debug(this, "Num of CollaterPool : "
		// + collaterPoolChkList.size());
		// DefaultLogger.debug(this, "Num of SpecificTrx : "
		// + specificTrxChkList.size());
		// DefaultLogger.debug(this, "Num of CashReq : " +
		// cashReqQtyList.size());
		sllForm.setCollaterPoolChk((String[]) collaterPoolChkList.toArray(new String[collaterPoolChkList.size()]));
		sllForm.setSpecificTrxChk((String[]) specificTrxChkList.toArray(new String[specificTrxChkList.size()]));
		sllForm.setCashReqQtyChk((String[]) cashReqQtyChkList.toArray(new String[cashReqQtyChkList.size()]));
		sllForm.setCashReqQty((String[]) cashReqQtyList.toArray(new String[cashReqQtyList.size()]));
	}

	private void setForm2Object(SubLimitListForm sllForm, HashMap limitMap, Locale locale) {
		List collaterPoolChkList = new ArrayList();
		List specificTrxChkList = new ArrayList();
		if (sllForm.getCollaterPoolChk() != null) {
			collaterPoolChkList = Arrays.asList(sllForm.getCollaterPoolChk());
		}
		if (sllForm.getSpecificTrxChk() != null) {
			specificTrxChkList = Arrays.asList(sllForm.getSpecificTrxChk());
		}
		String[] cashReqQty = sllForm.getCashReqQty();
		List limitIDList = new ArrayList();
		if (sllForm.getLimitIDArray() != null) {
			limitIDList = Arrays.asList(sllForm.getLimitIDArray());
		}
		if ((limitMap != null) && !limitMap.isEmpty()) {
			for (Iterator iterator = SubLimitUtils.getSortedLimitDetails(limitMap).iterator(); iterator.hasNext();) {
				ICollateralLimitMap cLimitMap = (ICollateralLimitMap) iterator.next();
				String limitID = CollateralHelper.getColLimitMapLimitID(cLimitMap);
				if (collaterPoolChkList.contains(limitID)) {
					cLimitMap.setIsCollateralPool(true);
				}
				else {
					cLimitMap.setIsCollateralPool(false);
				}
				if (specificTrxChkList.contains(limitID)) {
					cLimitMap.setIsSpecificTrx(true);
				}
				else {
					cLimitMap.setIsSpecificTrx(false);
				}
				int index = limitIDList.indexOf(limitID);
				DefaultLogger.debug(this, "Index : " + index);
				if ((index >= 0) && (cashReqQty[index] != null) && !cashReqQty[index].trim().equals("")) {
					try {
						cLimitMap.setCashReqPct(MapperUtil.mapStringToDouble(cashReqQty[index], locale));
					}
					catch (Exception e) {
						e.printStackTrace();
					}
				}
				else {
					cLimitMap.setCashReqPct(ICMSConstant.DOUBLE_INVALID_VALUE);
				}
			}
		}
	}
}
