/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/collateral/commodity/hedgedcontract/HedgedConMapper.java,v 1.6 2004/08/04 05:48:28 hshii Exp $
 */
package com.integrosys.cms.ui.collateral.commodity.hedgedcontract;

import java.util.HashMap;
import java.util.Locale;

import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.exception.MapperException;
import com.integrosys.cms.app.collateral.bus.type.commodity.ICommodityCollateral;
import com.integrosys.cms.app.collateral.bus.type.commodity.IHedgingContractInfo;
import com.integrosys.cms.app.collateral.bus.type.commodity.OBHedgingContractInfo;
import com.integrosys.cms.ui.collateral.CollateralMapper;
import com.integrosys.cms.ui.common.UIUtil;

/**
 * Description
 * 
 * @author $Author: hshii $<br>
 * @version $Revision: 1.6 $
 * @since $Date: 2004/08/04 05:48:28 $ Tag: $Name: $
 */

public class HedgedConMapper extends AbstractCommonMapper {
	public Object mapFormToOB(CommonForm cForm, HashMap inputs) throws MapperException {
		HedgedConForm aForm = (HedgedConForm) cForm;
		Locale locale = (Locale) inputs.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);

		int indexID = Integer.parseInt((String) inputs.get("indexID"));
		OBHedgingContractInfo obToChange = null;

		if (indexID == -1) {
			obToChange = new OBHedgingContractInfo();
		}
		else {
			try {
				int secIndexID = Integer.parseInt((String) inputs.get("secIndexID"));
				HashMap trxValueMap = (HashMap) inputs.get("commodityMainTrxValue");
				ICommodityCollateral[] col = (ICommodityCollateral[]) trxValueMap.get("staging");
				obToChange = (OBHedgingContractInfo) AccessorUtil
						.deepClone(col[secIndexID].getHedgingContractInfos()[indexID]);
			}
			catch (Exception e) {
				e.printStackTrace();
				throw new MapperException(e.getMessage());
			}
		}

		obToChange.setGlobalTreasuryReference(aForm.getGlobalTreasuryRef());
		obToChange.setDateOfDeal(CollateralMapper.compareDate(locale, obToChange.getDateOfDeal(), aForm.getDealDate()));
		if (isEmptyOrNull(aForm.getCounterParty())) {
			obToChange.setNameOfTheCounterParty(null);
		}
		else {
			obToChange.setNameOfTheCounterParty(aForm.getCounterParty());
		}
		obToChange.setDealAmount(UIUtil.convertToAmount(locale, aForm.getDealAmtCcy(), aForm.getDealAmt()));
		/*
		 * obToChange.setDealAmountCurrency(aForm.getDealAmtCcy()); if
		 * (isEmptyOrNull(aForm.getDealAmt())) {
		 * obToChange.setDealAmount(ICMSConstant.DOUBLE_INVALID_VALUE); } else {
		 * try {
		 * obToChange.setDealAmount(MapperUtil.mapStringToDouble(aForm.getDealAmt
		 * (), locale)); } catch (Exception e) { e.printStackTrace(); throw new
		 * MapperException(e.getMessage()); } }
		 */
		obToChange.setHedgingAgreement(aForm.getHedgedAgreeRef());
		obToChange.setHedgingAgreementDate(CollateralMapper.compareDate(locale, obToChange.getHedgingAgreementDate(),
				aForm.getHedgedAgreeDate()));
		if (isEmptyOrNull(aForm.getMargin())) {
			obToChange.setMargin(0);
		}
		else {
			obToChange.setMargin(Integer.parseInt(aForm.getMargin()));
		}

		return obToChange;
	}

	public CommonForm mapOBToForm(CommonForm cForm, Object obj, HashMap inputs) throws MapperException {
		HedgedConForm aForm = (HedgedConForm) cForm;
		HashMap hedgedContractMap = (HashMap) obj;
		Locale locale = (Locale) inputs.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);
		IHedgingContractInfo hedgingCon = (IHedgingContractInfo) hedgedContractMap.get("obj");

		aForm.setSecurityID((String) hedgedContractMap.get("securityID"));
		aForm.setGlobalTreasuryRef(hedgingCon.getGlobalTreasuryReference());
		aForm.setDealDate(DateUtil.formatDate(locale, hedgingCon.getDateOfDeal()));
		aForm.setCounterParty(hedgingCon.getNameOfTheCounterParty());
		if (hedgingCon.getDealAmount() != null) {
			aForm.setDealAmtCcy(hedgingCon.getDealAmount().getCurrencyCode());
			if (hedgingCon.getDealAmount().getAmount() >= 0) {
				try {
					aForm
							.setDealAmt(UIUtil.formatNumber(hedgingCon.getDealAmount().getAmountAsBigDecimal(), 0,
									locale));
				}
				catch (Exception e) {
					throw new MapperException(e.getMessage());
				}
			}
		}
		else {
			aForm.setDealAmtCcy("");
			aForm.setDealAmt("");
		}
		/*
		 * aForm.setDealAmtCcy(hedgingCon.getDealAmountCurrency()); if
		 * (hedgingCon.getDealAmount() > ICMSConstant.DOUBLE_INVALID_VALUE) {
		 * aForm
		 * .setDealAmt(MapperUtil.mapDoubleToString(hedgingCon.getDealAmount(),
		 * 3,locale)); }
		 */
		aForm.setHedgedAgreeRef(hedgingCon.getHedgingAgreement());
		aForm.setHedgedAgreeDate(DateUtil.formatDate(locale, hedgingCon.getHedgingAgreementDate()));
		aForm.setMargin(String.valueOf(hedgingCon.getMargin()));

		return aForm;
	}

	public String[][] getParameterDescriptor() {
		return (new String[][] { { "indexID", "java.lang.String", REQUEST_SCOPE },
				{ "secIndexID", "java.lang.String", REQUEST_SCOPE },
				// {"serviceHedgeCon",
				// "com.integrosys.cms.app.collateral.bus.type.commodity.IHedgingContractInfo"
				// , SERVICE_SCOPE},
				{ "commodityMainTrxValue", "java.util.HashMap", SERVICE_SCOPE },
				{ com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY, "java.util.Locale", GLOBAL_SCOPE }, });
	}
}
