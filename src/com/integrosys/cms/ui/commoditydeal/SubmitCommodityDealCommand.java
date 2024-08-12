/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/commoditydeal/SubmitCommodityDealCommand.java,v 1.24 2006/11/19 14:55:09 jzhan Exp $
 */
package com.integrosys.cms.ui.commoditydeal;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;

import org.apache.struts.action.ActionMessage;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.base.techinfra.logger.DefaultLogger;
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
import com.integrosys.cms.app.limit.bus.ILimit;
import com.integrosys.cms.app.transaction.OBTrxContext;

/**
 * Description
 * 
 * @author $Author: jzhan $<br>
 * @version $Revision: 1.24 $
 * @since $Date: 2006/11/19 14:55:09 $ Tag: $Name: $
 */

public class SubmitCommodityDealCommand extends AbstractCommand {
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
						SERVICE_SCOPE }, { "commodityLimitMap", "java.util.HashMap", SERVICE_SCOPE },
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
		ICommodityDealProxy proxy = CommodityDealProxyFactory.getProxy();
		// HashMap commodityLimitMap = (HashMap)map.get("commodityLimitMap");

		ICommodityCollateral dealCollateral = (ICommodityCollateral) map.get("dealCollateral");
		ICollateralLimitMap[] colLimitMapList = dealCollateral.getCollateralLimits();
		IProfile profile = (IProfile) map.get("profileService");

		if (((commDealObj.getCashMarginPct() < 0) || (commDealObj.getCashReqPct() < 0)) && (colLimitMapList != null)) {
			boolean found = false;
			for (int i = 0; !found && (i < colLimitMapList.length); i++) {
				if ((commDealObj.getCustomerCategory().equals(ICMSConstant.CUSTOMER_CATEGORY_MAIN_BORROWER) && (commDealObj
						.getLimitID() == colLimitMapList[i].getLimitID()))
						|| (commDealObj.getCustomerCategory().equals(ICMSConstant.CUSTOMER_CATEGORY_CO_BORROWER) && (commDealObj
								.getCoBorrowerLimitID() == colLimitMapList[i].getCoBorrowerLimitID()))) {
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

		/*
		 * if (exceptionMap.isEmpty()) { ILimit limit =
		 * getLimitByCollateralID(commodityLimitMap,
		 * commDealObj.getCollateralID(), commDealObj.getLimitID()); try {
		 * boolean isValidOpLimit = proxy.isValidOperationalLimit(limit,
		 * trxValue.getCommodityDeal(), commDealObj); if (!isValidOpLimit) {
		 * exceptionMap.put("validateOpLimit", new
		 * ActionMessage("error.commodity.deal.invalid.oplimit")); } } catch
		 * (Exception e) { throw new CommandProcessingException(e.getMessage());
		 * } }
		 */
		if (exceptionMap.isEmpty()) {
			exceptionMap = CommodityDealFieldValidator.validateInput(commDealObj, locale);
		}
		if (exceptionMap.isEmpty()) {
			OBTrxContext ctx = (OBTrxContext) map.get("theOBTrxContext");
			ctx.setTrxCountryOrigin(ctx.getLimitProfile().getOriginatingLocation().getCountryCode());
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
					// ignore if no CRP.
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

			verifyDealAmount(map, commDealObj, result, exceptionMap);

			if (exceptionMap.isEmpty()) {
				trxValue.setStagingCommodityDeal(commDealObj);

				try {
					trxValue = proxy.makerUpdateCommodityDeal(ctx, trxValue, commDealObj);
					result.put("request.ITrxValue", trxValue);
				}
				catch (Exception e) {
					e.printStackTrace();
					throw new CommandProcessingException(e.getMessage());
				}
			}
		}

		temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		return temp;
	}

	/**
	 * Verify if deal amount is less than (Deal CMV + Cash for Requirement +
	 * Cash for Set-off). Set the flag to display a prompt on screen if it is
	 * not less than the above condition.
	 * 
	 * Deal CMV, Cash for Requirement, Cash for Set-off would be converted to
	 * Deal Amount's Currency and their sum would be rounded using
	 * "BigDecimal.ROUND_HALF_UP" rounding before comparing with the Deal
	 * Amount. Reason for performing the rounding is such that it tallies with
	 * what is display on the screen. E.g. if screen showed value of "USD 892"
	 * (converted from HKD), the actual value after conversion could be USD
	 * 891.5869542 and without rounding, the value with decimals will be used
	 * for comparison which does not tally with what user see on screen (which
	 * they perceived to be right).
	 * 
	 * 
	 * @param map Map containing values from request/session etc.
	 * @param commDealObj Commodity Deal Object
	 * @param result Result Map
	 * @param exceptionMap Exception Map
	 * @throws CommandProcessingException Exception occurs.
	 */
	protected void verifyDealAmount(HashMap map, ICommodityDeal commDealObj, HashMap result, HashMap exceptionMap)
			throws CommandProcessingException {
		// Meant for CR144
		String currentEventNum = (String) map.get("currentEventNum");
		result.put("currentEventNum", currentEventNum);

		String checkDealAmtAgainstCMVFlag = (String) map.get("checkDealAmtAgainstCMVFlag");
		if (checkDealAmtAgainstCMVFlag.equals(CommodityDealConstant.CHECK_AMT_AGAINST_CMV_REQUIRED)) {
			if (exceptionMap.isEmpty()) { // no error with amount, proceed with
											// this validation
				try {
					Amount dealAmount = commDealObj.getDealAmt();
					// Amount cmvAmount = dealCollateral.getCMV();
					// UAT1.4 - JIRA CMS-2699, Defect #164

					// CMV Amount is null if Valuation has not been done
					// If its null, set it to 0 at dealAmount's currency code
					// Else, retrieve CMV and convert CMV to deal Amount's
					// currency if its not the same currency
					// (although CMV should always be in deal amount's
					// currency).
					Amount cmvAmount = (commDealObj.getCMV() == null) ? new Amount(0.0, dealAmount.getCurrencyCode())
							: ((commDealObj.getCMV().getCurrencyCode().equals(dealAmount.getCurrencyCode())) ? commDealObj
									.getCMV()
									: AmountConversion.getConversionAmount(commDealObj.getCMV(), dealAmount
											.getCurrencyCode()));

					DefaultLogger.debug(this, "##################>>>>>>>>> deal Amount: " + dealAmount);
					DefaultLogger.debug(this, "##################>>>>>>>>> CMV Amount: " + cmvAmount);

					/*
					 * //Add the cash deposits (Only Cash for Requirement and
					 * Cash for Comfort) to the Deal CMV amount.
					 * IDealCashDeposit[] cashDeposits =
					 * commDealObj.getCashDeposit(); if(cashDeposits != null) {
					 * for(int i=0; i<cashDeposits.length; i++) {
					 * DefaultLogger.debug(this,
					 * "##################>>>>>>>>> Cash Deposit [" + i +
					 * "] Deposit Type: " + cashDeposits[i].getDepositType() +
					 * " | Cash Deposit Amount: " +
					 * cashDeposits[i].getAmount());
					 * if(cashDeposits[i].getDepositType().equals("R") ||
					 * cashDeposits[i].getDepositType().equals("S")) {
					 * cmvAmount.
					 * addToThis(AmountConversion.getConversionAmount(cashDeposits
					 * [i].getAmount(), cmvAmount.getCurrencyCode())); } } }
					 * DefaultLogger.debug(this,
					 * "##################>>>>>>>>> CMV Amount After Adding Cash Deposits: "
					 * + cmvAmount);
					 */

					// still required to round up since CMV still contains
					// decimals, although on-screen it is not shown!!
					// Rounding up the CMV Amount for Comparison, such that it
					// tallies with the value displayed on screen
					cmvAmount.setAmountAsBigDecimal(cmvAmount.getAmountAsBigDecimal().setScale(0, BigDecimal.ROUND_HALF_UP));
					DefaultLogger.debug(this, "##################>>>>>>>>> CMV Amount After Rounding: " + cmvAmount);

					// -- no longer required to convert deal amount. all others
					// have been converted to deal amount's currency
					// dealAmount =
					// AmountConversion.getConversionAmount(dealAmount,
					// cmvAmount.getCurrencyCode());
					// DefaultLogger.debug(this,
					// "##################>>>>>>>>> (Converted) Deal Amount: " +
					// dealAmount);

					if (dealAmount.getAmount() > cmvAmount.getAmount()) {
						result.put("checkDealAmtAgainstCMVFlag", CommodityDealConstant.CHECK_AMT_GREATER_THAN_CMV);
						result.put("checkDealAmtAgainstCMVMsg", CommodityDealConstant.DEAL_AMT_GREATER_THAN_CMV);
						exceptionMap.put("originalFaceAmt_notForDisplay", new ActionMessage(
								"error.commodity.deal.genearlinfo.dealAmtGreaterThanCMV", "Deal Amount"));
					}

				}
				catch (AmountConversionException e) {
					DefaultLogger.info(this, "Unable to convert Cash Deposits Amount to CMV Amount");
					result.put("checkDealAmtAgainstCMVFlag", CommodityDealConstant.CHECK_AMT_AMOUNT_CONVERSION_ERROR);
					result.put("checkDealAmtAgainstCMVMsg", CommodityDealConstant.AMOUNT_CONVERSION_ERROR);
					exceptionMap.put("originalFaceAmt_notForDisplay", new ActionMessage(
							"error.commodity.deal.genearlinfo.dealAmtConversionError", "Deal Amount"));

				}/*
				 * catch (ChainedException e) { DefaultLogger.info(this,
				 * "ChainedException: Unable to add Cash Deposits Amount to CMV Amount!!! This should never have been thrown!!"
				 * ); result.put("checkDealAmtAgainstCMVFlag",
				 * CommodityDealConstant.CHECK_AMT_AMOUNT_CONVERSION_ERROR);
				 * result.put("checkDealAmtAgainstCMVMsg",
				 * CommodityDealConstant.AMOUNT_CONVERSION_ERROR);
				 * exceptionMap.put("originalFaceAmt_notForDisplay", new
				 * ActionMessage
				 * ("error.commodity.deal.genearlinfo.dealAmtConversionError",
				 * "Deal Amount")); }
				 */
			}
		}
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

	private ILimit getLimitByCollateralID(HashMap colLimitMap, long collateralID, long limitID) {
		ILimit limit = null;
		Collection colSet = colLimitMap.keySet();
		if (colSet != null) {
			Iterator itr = colSet.iterator();
			boolean found = false;
			while (!found && itr.hasNext()) {
				ICommodityCollateral col = (ICommodityCollateral) itr.next();
				if (col.getCollateralID() == collateralID) {
					found = true;
					Collection limitList = (Collection) colLimitMap.get(col);
					if (limitList != null) {
						Iterator itr1 = limitList.iterator();
						boolean foundLimit = false;
						while (!foundLimit && itr1.hasNext()) {
							ILimit tmp = (ILimit) itr1.next();
							if (tmp.getLimitID() == limitID) {
								foundLimit = true;
								limit = tmp;
							}
						}
					}
				}
			}
		}
		return limit;
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
