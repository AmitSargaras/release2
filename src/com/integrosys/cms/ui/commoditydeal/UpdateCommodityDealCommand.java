/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/commoditydeal/UpdateCommodityDealCommand.java,v 1.16 2006/11/20 09:05:42 jzhan Exp $
 */
package com.integrosys.cms.ui.commoditydeal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

import org.apache.struts.action.ActionMessage;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.collateral.bus.ICollateralLimitMap;
import com.integrosys.cms.app.collateral.bus.ICollateralParameter;
import com.integrosys.cms.app.collateral.bus.type.commodity.ICommodityCollateral;
import com.integrosys.cms.app.collateral.bus.type.commodity.IHedgingContractInfo;
import com.integrosys.cms.app.collateral.proxy.CollateralProxyFactory;
import com.integrosys.cms.app.collateral.proxy.ICollateralProxy;
import com.integrosys.cms.app.commodity.common.AmountConversion;
import com.integrosys.cms.app.commodity.common.AmountConversionException;
import com.integrosys.cms.app.commodity.common.UOMWrapper;
import com.integrosys.cms.app.commodity.common.UOMWrapperFactory;
import com.integrosys.cms.app.commodity.deal.bus.ICommodityDeal;
import com.integrosys.cms.app.commodity.deal.bus.doc.ICommodityTitleDocument;
import com.integrosys.cms.app.commodity.deal.proxy.CommodityDealProxyFactory;
import com.integrosys.cms.app.commodity.deal.proxy.ICommodityDealProxy;
import com.integrosys.cms.app.commodity.deal.trx.ICommodityDealTrxValue;
import com.integrosys.cms.app.commodity.main.bus.profile.IProfile;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.transaction.OBTrxContext;

/**
 * Description
 * 
 * @author $Author: jzhan $<br>
 * @version $Revision: 1.16 $
 * @since $Date: 2006/11/20 09:05:42 $ Tag: $Name: $
 */

public class UpdateCommodityDealCommand extends AbstractCommand {
	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY, "java.util.Locale", GLOBAL_SCOPE },
				{ "commodityDealTrxValue", "com.integrosys.cms.app.commodity.deal.trx.ICommodityDealTrxValue",
						SERVICE_SCOPE },
				{ "generalInfoObj", "com.integrosys.cms.app.commodity.deal.bus.ICommodityDeal", FORM_SCOPE },
				{ "theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE },
				{ "TitleDocWR", "com.integrosys.cms.app.commodity.deal.bus.doc.ICommodityTitleDocument", SERVICE_SCOPE },
				// Added by Pratheepa for CR129
				{ "TitleDocWRNeg", "com.integrosys.cms.app.commodity.deal.bus.doc.ICommodityTitleDocument",
						SERVICE_SCOPE },
				{ "dealCollateral", "com.integrosys.cms.app.collateral.bus.type.commodity.ICommodityCollateral",
						SERVICE_SCOPE },
				{ "profileService", "com.integrosys.cms.app.commodity.main.bus.profile.IProfile", SERVICE_SCOPE },
				{ "checkDealAmtAgainstCMVFlag", "java.lang.String", REQUEST_SCOPE },
				{ "currentEventNum", "java.lang.String", REQUEST_SCOPE }, });
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
				{ "request.ITrxValue", "com.integrosys.cms.app.commodity.deal.trx.ICommodityDealTrxValue",
						REQUEST_SCOPE }, { "checkDealAmtAgainstCMVFlag", "java.lang.String", REQUEST_SCOPE },
				{ "checkDealAmtAgainstCMVMsg", "java.lang.String", REQUEST_SCOPE },
				{ "currentEventNum", "java.lang.String", REQUEST_SCOPE }, });
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

		ICommodityDeal commDealObj = (ICommodityDeal) map.get("generalInfoObj");
		ICommodityDealTrxValue trxValue = (ICommodityDealTrxValue) map.get("commodityDealTrxValue");
		Locale locale = (Locale) map.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);
		ICommodityCollateral dealCollateral = (ICommodityCollateral) map.get("dealCollateral");
		ICollateralLimitMap[] colLimitMapList = dealCollateral.getCollateralLimits();
		IProfile profile = (IProfile) map.get("profileService");

		if (((commDealObj.getCashMarginPct() < 0) || (commDealObj.getCashReqPct() < 0)) && (colLimitMapList != null)) {
			boolean found = false;
			for (int i = 0; !found && (i < colLimitMapList.length); i++) {
				if (commDealObj.getLimitID() == colLimitMapList[i].getLimitID()) {
					found = true;
					if (colLimitMapList[i].getCashReqPct() >= 0) {
						if (commDealObj.getCashMarginPct() < 0) {
							exceptionMap.put("marginCash", new ActionMessage("error.integer.mandatory"));
						}
						if (commDealObj.getCashReqPct() < 0) {
							exceptionMap.put("cashRequirement", new ActionMessage("error.integer.mandatory"));
						}
					}
				}
			}
		}

		// Meant for CR144
		String currentEventNum = (String) map.get("currentEventNum");
		result.put("currentEventNum", currentEventNum);

		String checkDealAmtAgainstCMVFlag = (String) map.get("checkDealAmtAgainstCMVFlag");
		if (checkDealAmtAgainstCMVFlag.equals(CommodityDealConstant.CHECK_AMT_AGAINST_CMV_REQUIRED)) {
			if (exceptionMap.isEmpty()) { // no error with amount, proceed with
											// this validation
				Amount dealAmount = commDealObj.getDealAmt();
				Amount cmvAmount = dealCollateral.getCMV();
				double cmvValue = cmvAmount.getAmount();

				try { // use for determining if deal amount if greater than cmv
						// of collateral
					dealAmount = AmountConversion.getConversionAmount(dealAmount, cmvAmount.getCurrencyCode());
					double dealValue = dealAmount.getAmount();

					if (dealValue > cmvValue) {
						result.put("checkDealAmtAgainstCMVFlag", CommodityDealConstant.CHECK_AMT_GREATER_THAN_CMV);
						result.put("checkDealAmtAgainstCMVMsg", CommodityDealConstant.DEAL_AMT_GREATER_THAN_CMV);
						exceptionMap.put("originalFaceAmt_notForDisplay", new ActionMessage(
								"error.commodity.deal.genearlinfo.dealAmtGreaterThanCMV", "Deal Amount"));
					}

				}
				catch (AmountConversionException e) {
					result.put("checkDealAmtAgainstCMVFlag", CommodityDealConstant.CHECK_AMT_AMOUNT_CONVERSION_ERROR);
					result.put("checkDealAmtAgainstCMVMsg", CommodityDealConstant.AMOUNT_CONVERSION_ERROR);
					exceptionMap.put("originalFaceAmt_notForDisplay", new ActionMessage(
							"error.commodity.deal.genearlinfo.dealAmtConversionError", "Deal Amount"));
				}
			}
		}

		/*
		 * if (exceptionMap.isEmpty()) { exceptionMap =
		 * CommodityDealFieldValidator.validateInput(commDealObj, locale); }
		 */
		if (exceptionMap.isEmpty()) {
			OBTrxContext ctx = (OBTrxContext) map.get("theOBTrxContext");
			ctx.setTrxCountryOrigin(ctx.getLimitProfile().getOriginatingLocation().getCountryCode());
			ICommodityDealProxy proxy = CommodityDealProxyFactory.getProxy();
			if ((trxValue.getStatus() != null) && !ICMSConstant.STATE_ACTIVE.equals(trxValue.getStatus())) {
				commDealObj.setTitleDocsLatest(commDealObj.getTitleDocsAll());
			}
			else {
				commDealObj.setTitleDocsLatest(combineUpdatedLatestHistDoc(commDealObj.getTitleDocsLatest(),
						commDealObj.getTitleDocsHistory()));
			}

			ICommodityTitleDocument wrTitleDoc = (ICommodityTitleDocument) map.get("TitleDocWR");
			if (wrTitleDoc != null) {
				ICommodityTitleDocument[] titleDocLatest = commDealObj.getTitleDocsLatest();
				titleDocLatest = addTitleDoc(titleDocLatest, wrTitleDoc);
				commDealObj.setTitleDocsLatest(titleDocLatest);
			}

			// Start of code by Pratheepa for CR129
			ICommodityTitleDocument wrTitleDocNeg = (ICommodityTitleDocument) map.get("TitleDocWRNeg");
			if (wrTitleDocNeg != null) {
				ICommodityTitleDocument[] titleDocLatest = commDealObj.getTitleDocsLatest();
				titleDocLatest = addTitleDoc(titleDocLatest, wrTitleDocNeg);
				commDealObj.setTitleDocsLatest(titleDocLatest);
			}
			// End of code by Pratheepa for CR129

			UOMWrapper marketUOM = null;
			if (commDealObj.getContractMarketUOMConversionRate() != null) {
				marketUOM = UOMWrapperFactory.getInstance().valueOf(
						commDealObj.getContractMarketUOMConversionRate().getKey().getToUnit());
			}
			UOMWrapper contractUOM = null;
			if (commDealObj.getContractQuantity() != null) {
				contractUOM = commDealObj.getContractQuantity().getUnitofMeasure();
			}
			commDealObj = SetCommodityDealData.setCommodityUOM(commDealObj, marketUOM, contractUOM);

			if (commDealObj.getOrigFaceValue() != null) {
				commDealObj = SetCommodityDealData.setCommodityDealCcy(commDealObj, commDealObj.getOrigFaceValue()
						.getCurrencyCode());
			}
			commDealObj = SetCommodityDealData.setProfileInfo(commDealObj, profile);
			Amount cmvAmt = commDealObj.getCalculatedCMVAmt();
			commDealObj.setCMV(cmvAmt);

			ICollateralProxy colProxy = CollateralProxyFactory.getProxy();
			if (dealCollateral != null) {
				ICollateralParameter colParam = null;
				try {
					colParam = colProxy.getCollateralParameter(dealCollateral.getCollateralLocation(), dealCollateral
							.getCollateralSubType().getSubTypeCode());
				}
				catch (Exception e) {
					// ignore when no CRP.
				}
				double hedgingMargin = 0;
				if (commDealObj.getHedgeContractID() > 0) {
					IHedgingContractInfo hedgingContract = getHedgingContractByID(dealCollateral, commDealObj
							.getHedgeContractID());
					if ((hedgingContract != null) && (hedgingContract.getMargin() > 0)) {
						hedgingMargin = hedgingContract.getMargin();
					}
				}
				double crp = 0;
				if (colParam != null) {
					crp = colParam.getThresholdPercent();
				}
				Amount fsvAmt = commDealObj.getCalculatedFSVAmt(crp, hedgingMargin);
				commDealObj.setFSV(fsvAmt);
			}
			trxValue.setStagingCommodityDeal(commDealObj);

			try {
				trxValue = proxy.makerSaveCommodityDeal(ctx, trxValue, commDealObj);
				result.put("request.ITrxValue", trxValue);
			}
			catch (Exception e) {
				e.printStackTrace();
				throw new CommandProcessingException(e.getMessage());
			}
		}

		temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		return temp;
	}

	public static ICommodityTitleDocument[] addTitleDoc(ICommodityTitleDocument[] existingArray,
			ICommodityTitleDocument obj) {
		int arrayLength = 0;
		if (existingArray != null) {
			arrayLength = existingArray.length;
		}

		ICommodityTitleDocument[] newArray = new ICommodityTitleDocument[arrayLength + 1];
		if (existingArray != null) {
			System.arraycopy(existingArray, 0, newArray, 0, arrayLength);
		}
		newArray[arrayLength] = obj;

		return newArray;
	}

	private static IHedgingContractInfo getHedgingContractByID(ICommodityCollateral col, long hedgingContractID) {
		if (col != null) {
			IHedgingContractInfo[] contractList = col.getHedgingContractInfos();
			if (contractList != null) {
				for (int i = 0; i < contractList.length; i++) {
					if (contractList[i].getHedgingContractInfoID() == hedgingContractID) {
						return contractList[i];
					}
				}
			}
			contractList = col.getDeletedHedgeContractInfos();
			if (contractList != null) {
				for (int i = 0; i < contractList.length; i++) {
					if (contractList[i].getHedgingContractInfoID() == hedgingContractID) {
						return contractList[i];
					}
				}
			}
		}
		return null;
	}

	private ICommodityTitleDocument[] combineUpdatedLatestHistDoc(ICommodityTitleDocument[] latest,
			ICommodityTitleDocument[] history) {
		ArrayList tempArr = new ArrayList();
		if (latest != null) {
			for (int i = 0; i < latest.length; i++) {
				tempArr.add(latest[i]);
			}
		}
		if (history != null) {
			for (int i = 0; i < history.length; i++) {
				tempArr.add(history[i]);
			}
		}
		ICommodityTitleDocument[] res = new ICommodityTitleDocument[tempArr.size()];
		for (int j = 0; j < tempArr.size(); j++) {
			res[j] = (ICommodityTitleDocument) (tempArr.get(j));
		}
		return res;
	}
}
