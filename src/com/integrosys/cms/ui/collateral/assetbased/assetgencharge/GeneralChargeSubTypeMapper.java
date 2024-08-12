/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/collateral/assetbased/assetgencharge/GeneralChargeSubTypeMapper.java,v 1.10 2005/04/15 06:40:18 hshii Exp $
 */
package com.integrosys.cms.ui.collateral.assetbased.assetgencharge;

import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.base.businfra.currency.CurrencyCode;
import com.integrosys.base.businfra.currency.CurrencyManager;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.common.IMapper;
import com.integrosys.base.uiinfra.exception.MapperException;
import com.integrosys.cms.app.collateral.bus.ICollateralParameter;
import com.integrosys.cms.app.collateral.bus.type.asset.subtype.gcharge.IGeneralCharge;
import com.integrosys.cms.app.collateral.bus.type.asset.subtype.gcharge.IGeneralChargeSubType;
import com.integrosys.cms.app.collateral.bus.type.asset.subtype.gcharge.OBGeneralChargeSubType;
import com.integrosys.cms.app.collateral.proxy.CollateralProxyFactory;
import com.integrosys.cms.app.collateral.trx.ICollateralTrxValue;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.ui.collateral.TimeFreqList;
import com.integrosys.cms.ui.common.ForexHelper;
import com.integrosys.cms.ui.common.UIUtil;

/**
 * Mapper for GeneralChargeSubType
 * 
 * @author $Author: hshii $<br>
 * @version $Revision: 1.10 $
 * @since $Date: 2005/04/15 06:40:18 $ Tag: $Name: $
 */
public abstract class GeneralChargeSubTypeMapper implements IMapper, ICommonEventConstant {
	protected Locale locale;

	protected String currCode;

	public Object mapFormToOB(CommonForm cForm, HashMap inputs, Object obj) throws MapperException {
		GeneralChargeSubTypeForm aForm = (GeneralChargeSubTypeForm) cForm;
		IGeneralChargeSubType genChrgSubType = (IGeneralChargeSubType) obj;

		locale = (Locale) inputs.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);
		IGeneralCharge iCol = (IGeneralCharge) (((ICollateralTrxValue) inputs.get("serviceColObj"))
				.getStagingCollateral());

		currCode = aForm.getValCurrency();

		try {
			genChrgSubType.setAddress(aForm.getAddress());
			genChrgSubType.setValuationCurrency(aForm.getValCurrency());
			genChrgSubType.setValuerName(aForm.getValuer());

			if (AbstractCommonMapper.isEmptyOrNull(aForm.getNonStdRevalFreqNum())
					&& AbstractCommonMapper.isEmptyOrNull(aForm.getNonStdRevalFreqUnit())) {
				genChrgSubType.setRevalFreq(ICMSConstant.INT_INVALID_VALUE);
				genChrgSubType.setRevalFreqUnit(null);
			}
			else {
				genChrgSubType.setRevalFreq(convertToInt(aForm.getNonStdRevalFreqNum()));
				genChrgSubType.setRevalFreqUnit(aForm.getNonStdRevalFreqUnit());
			}

			if (!AbstractCommonMapper.isEmptyOrNull(aForm.getValuationDate())) {
				Date stageDate = UIUtil
						.compareDate(locale, genChrgSubType.getValuationDate(), aForm.getValuationDate());
				genChrgSubType.setValuationDate(stageDate);
			}
			else {
				genChrgSubType.setValuationDate(null);
			}

			// genChrgSubType.setGrossValue(convertToAmount(aForm.
			// getGrossValueValCurr()));
		}
		catch (Exception e) {
			throw new MapperException("mapFormToOB " + e.getMessage());
		}
		return genChrgSubType;
	}

	public CommonForm mapOBToForm(CommonForm cForm, Object obj, HashMap inputs) throws MapperException {
		GeneralChargeSubTypeForm aForm = (GeneralChargeSubTypeForm) cForm;

		locale = (Locale) inputs.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);

		ForexHelper fr = new ForexHelper();

		HashMap stockMap = (HashMap) obj;
		IGeneralChargeSubType genChrgSubType = (OBGeneralChargeSubType) stockMap.get("obj");
		String cmsSecurityCcy = (String) stockMap.get("ccy");
		CurrencyCode cmsCcy = new CurrencyCode(cmsSecurityCcy);
		String securityLoc = (String) stockMap.get("securityLocation");
		try {
			aForm.setCmsSecCurrency(cmsSecurityCcy);
			aForm.setAddress(genChrgSubType.getAddress());
			if (genChrgSubType.getValuationCurrency() != null) {
				aForm.setValCurrency(genChrgSubType.getValuationCurrency());
			}
			else {
				aForm.setValCurrency(cmsSecurityCcy);
			}
			aForm.setValuationDate(DateUtil.formatDate(locale, genChrgSubType.getValuationDate()));
			aForm.setValuer(genChrgSubType.getValuerName());

			int freq = 0;
			int freqCode = 0;
			if (!AbstractCommonMapper.isEmptyOrNull(securityLoc)) {
				ICollateralParameter parameter = null;
				try {
					parameter = CollateralProxyFactory.getProxy().getCollateralParameter(securityLoc,
							ICMSConstant.COLTYPE_ASSET_GENERAL_CHARGE);
				}
				catch (Exception e) {
					DefaultLogger.debug(this + " GeneralChargeSubTypeMapper",
							"There is no parameter found in the database");
				}
				String stdRevalFreq = "";
				if (parameter != null) {
					if (parameter.getValuationFrequencyUnit() != null) {
						freq = parameter.getValuationFrequency();
						freqCode = UIUtil.getFreqCode(parameter.getValuationFrequencyUnit());
						stdRevalFreq = String.valueOf(parameter.getValuationFrequency());
						stdRevalFreq += " "
								+ TimeFreqList.getInstance().getTimeFreqItem(parameter.getValuationFrequencyUnit());
					}
				}
				aForm.setStdRevalFreq(stdRevalFreq);
			}

			if (genChrgSubType.getRevalFreq() != ICMSConstant.INT_INVALID_VALUE) {
				aForm.setNonStdRevalFreqNum(String.valueOf(genChrgSubType.getRevalFreq()));
				freq = genChrgSubType.getRevalFreq();
			}
			else {
				aForm.setNonStdRevalFreqNum("");
			}

			aForm.setNonStdRevalFreqUnit(genChrgSubType.getRevalFreqUnit());
			if (!AbstractCommonMapper.isEmptyOrNull(genChrgSubType.getRevalFreqUnit())) {
				freqCode = UIUtil.getFreqCode(genChrgSubType.getRevalFreqUnit());
			}

			Date revaluationDate = UIUtil.calculateDate(freq, freqCode, genChrgSubType.getValuationDate());
			aForm.setRevaluationDate(DateUtil.formatDate(locale, revaluationDate));
		}
		catch (Exception e) {
			throw new MapperException("mapOBtoForm" + e.getMessage());
		}
		return aForm;
	}

	protected Amount convertToAmount(String strAmount) throws Exception {
		Amount amt = null;
		if (!AbstractCommonMapper.isEmptyOrNull(strAmount)) {
			amt = CurrencyManager.convertToAmount(locale, currCode, strAmount);
		}
		return amt;
	}

	protected String convertAmtToString(Amount amt) throws Exception {
		String strAmount = null;
		if ((amt != null) && (amt.getCurrencyCode() != null)) {
			strAmount = CurrencyManager.convertToString(locale, amt);
		}
		return strAmount;
	}

	protected double convertToDouble(String strAmount) throws Exception {
		double dAmount = 0;
		if (!AbstractCommonMapper.isEmptyOrNull(strAmount)) {
			dAmount = Double.parseDouble(strAmount);
		}
		return dAmount;
	}

	protected int convertToInt(String strAmount) throws Exception {
		int iAmount = ICMSConstant.INT_INVALID_VALUE;
		if (!AbstractCommonMapper.isEmptyOrNull(strAmount)) {
			iAmount = Integer.parseInt(strAmount);
		}
		return iAmount;
	}

	public String[][] getParameterDescriptor() {
		return (new String[][] { { "indexID", "java.lang.String", REQUEST_SCOPE },
				{ com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY, "java.util.Locale", GLOBAL_SCOPE },
				{ "serviceColObj", "com.integrosys.cms.app.collateral.trx.ICollateralTrxValue", SERVICE_SCOPE }, });
	}
}
