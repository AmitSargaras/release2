/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/collateral/commodity/sublimit/item/UpdateSubLimitItemCommand.java,v 1.3 2005/10/18 11:33:25 hmbao Exp $
 */
package com.integrosys.cms.ui.collateral.commodity.sublimit.item;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.struts.action.ActionMessage;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.base.businfra.currency.CurrencyCode;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.cms.app.collateral.bus.ICollateralLimitMap;
import com.integrosys.cms.app.collateral.bus.type.commodity.sublimit.ISubLimit;
import com.integrosys.cms.app.limit.bus.ILimit;
import com.integrosys.cms.ui.collateral.commodity.sublimit.SLUIConstants;
import com.integrosys.cms.ui.collateral.commodity.sublimit.SubLimitCommand;
import com.integrosys.cms.ui.collateral.commodity.sublimit.SubLimitUtils;
import com.integrosys.cms.ui.common.ForexHelper;

/**
 * Describe this class. Purpose: Description:
 * 
 * @author BaoHongMan
 * @version R1.4
 * @since 2005-9-26
 * @Tag com.integrosys.cms.ui.collateral.commodity.sublimit.
 *      UpdateSubLimitItemCommand.java
 */
public class UpdateSubLimitItemCommand extends SubLimitCommand {
	/*
	 * @see com.integrosys.base.uiinfra.common.ICommand#getParameterDescriptor()
	 */
	public String[][] getParameterDescriptor() {
		return new String[][] { { SLUIConstants.FN_IDX_ID, SLUIConstants.CN_STRING, REQUEST_SCOPE },
				{ SLUIConstants.FN_LIMIT_ID, SLUIConstants.CN_STRING, REQUEST_SCOPE },
				{ SLUIConstants.AN_OB_SL, SLUIConstants.CN_I_SL, FORM_SCOPE },
				{ SLUIConstants.AN_CMDT_LIMIT_MAP, SLUIConstants.CN_HASHMAP, SERVICE_SCOPE },
				{ SLUIConstants.AN_COMM_MAIN_TRX_VALUE, SLUIConstants.CN_HASHMAP, SERVICE_SCOPE } };
	}

	/*
	 * @see com.integrosys.base.uiinfra.common.ICommand#getResultDescriptor()
	 */
	public String[][] getResultDescriptor() {
		return new String[][] { { SLUIConstants.AN_SL_AMOUNT_CHK_MSG_LIST, SLUIConstants.CN_LIST, REQUEST_SCOPE },
				{ SLUIConstants.AN_CMDT_LIMIT_MAP, SLUIConstants.CN_HASHMAP, SERVICE_SCOPE } };
	}

	/*
	 * @see
	 * com.integrosys.cms.ui.collateral.commodity.sublimit.SubLimitCommand#execute
	 * (java.util.HashMap, java.util.HashMap, java.util.HashMap)
	 */
	protected void execute(HashMap paramMap, HashMap resultMap, HashMap exceptionMap) throws CommandProcessingException {
		ISubLimit sl = (ISubLimit) paramMap.get(SLUIConstants.AN_OB_SL);
		if (sl == null) {
			throw new CommandProcessingException("sub limit is null");
		}
		HashMap limitMap = (HashMap) paramMap.get(SLUIConstants.AN_CMDT_LIMIT_MAP);
		String limitId = (String) paramMap.get(SLUIConstants.FN_LIMIT_ID);
		ICollateralLimitMap cLimitMap = (ICollateralLimitMap) limitMap.get(limitId);
		ISubLimit[] slArray = cLimitMap.getSubLimit();
		int index = Integer.parseInt((String) paramMap.get(SLUIConstants.FN_IDX_ID));
		DefaultLogger.debug(this, "Index : " + index);
		if (isDuplicate(sl, slArray, index)) {
			DefaultLogger.debug(this, "Found duplicate sub limit .");
			exceptionMap.put(SLUIConstants.ERR_DUPLICATE_SL, new ActionMessage(SLUIConstants.ERR_DEPLICATE_SL_INFO));
			return;
		}
		if (index == -1) {
			int arrayLength = (slArray == null ? 0 : slArray.length);
			ISubLimit[] newArray = new ISubLimit[arrayLength + 1];
			System.arraycopy(slArray, 0, newArray, 0, arrayLength);
			newArray[arrayLength] = sl;
			slArray = newArray;
			DefaultLogger.debug(this, "Add new Sub Limit.");
		}
		else {
			slArray[index] = sl;
		}
		doAmountCheck(sl, slArray, paramMap, resultMap);
		DefaultLogger.debug(this, "Num of Sub Limit : " + slArray.length);
		cLimitMap.setSubLimit(slArray);
		limitMap.put(limitId, cLimitMap);
		resultMap.put(SLUIConstants.AN_CMDT_LIMIT_MAP, limitMap);
	}

	private boolean isDuplicate(ISubLimit sl, ISubLimit[] subLimitArray, int selectedIndex) {
		if ((subLimitArray == null) || (subLimitArray.length == 0)) {
			return false;
		}
		for (int index = 0; index < subLimitArray.length; index++) {
			if ((index != selectedIndex) && sl.getSubLimitType().equals(subLimitArray[index].getSubLimitType())) {
				// Note : here subLimitType means subLimitTypeID.
				DefaultLogger.debug(this, "Duplicate SL - Index : " + index);
				return true;
			}
		}
		return false;
	}

	private void doAmountCheck(ISubLimit sl, ISubLimit[] slArray, HashMap paramMap, HashMap resultMap)
			throws CommandProcessingException {
		try {
			ILimit currLimit = getCurrentLimit(paramMap);
			double appLmtAmt = currLimit.getApprovedLimitAmount().getAmountAsDouble();
			CurrencyCode appCCY = currLimit.getApprovedLimitAmount().getCurrencyCodeAsObject();
			boolean isInnerLmt = SubLimitUtils.isInnerLimit(currLimit);

			double slAmt = Double.parseDouble(sl.getSubLimitAmount());
			Amount slAmount = new Amount(slAmt, sl.getSubLimitCCY());

			slAmt = ForexHelper.getInstance().convertAmount(slAmount, appCCY);

			DefaultLogger.debug(this, " - do amount check..");
			List msgList = new ArrayList();
			if (isInnerLmt || !sl.isInnerLimit()) {
				if (appLmtAmt < slAmt) {
					msgList.add(SLUIConstants.MSG_SLAMOUNT_HIGHER_APP_AMOUNT);
				}
			}
			if (appLmtAmt < getTotalSLAmount(slArray, isInnerLmt, appCCY)) {
				msgList.add(SLUIConstants.MSG_TSLAMOUNT_HIGHER_TAPP_AMOUNT);
			}
			resultMap.put(SLUIConstants.AN_SL_AMOUNT_CHK_MSG_LIST, msgList);
			DefaultLogger.debug(this, "Num of MSG : " + msgList.size());
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	private ILimit getCurrentLimit(HashMap paramMap) throws CommandProcessingException {
		String limitID = (String) paramMap.get(SLUIConstants.FN_LIMIT_ID);
		HashMap trxValueMap = (HashMap) paramMap.get(SLUIConstants.AN_COMM_MAIN_TRX_VALUE);
		HashMap limitList = (HashMap) trxValueMap.get("limitList");
		if (limitList != null) {
			return (ILimit) limitList.get(limitID);
		}
		throw new CommandProcessingException("No Limit found - ID:" + limitID);
	}

	private double getTotalSLAmount(ISubLimit[] slArray, boolean isInnerIncluded, CurrencyCode ccy) {
		ForexHelper foreHelper = ForexHelper.getInstance();
		double sumAmount = 0;
		try {
			if ((slArray != null) && (slArray.length > 0)) {
				for (int index = 0; index < slArray.length; index++) {
					if (slArray[index].isInnerLimit() && !isInnerIncluded) {
						continue;
					}
					double tempSLAmt = Double.parseDouble(slArray[index].getSubLimitAmount());
					DefaultLogger.debug(this, " - Amount : " + tempSLAmt);
					DefaultLogger.debug(this, " - CCY : " + slArray[index].getSubLimitCCY());
					Amount tempAmount = new Amount(tempSLAmt, slArray[index].getSubLimitCCY());
					sumAmount += foreHelper.convertAmount(tempAmount, ccy);
				}
			}
		}
		catch (Exception e) {
			sumAmount = 0;
			e.printStackTrace();
		}
		DefaultLogger.debug(this, "Amount : " + sumAmount);
		return sumAmount;
	}
}
