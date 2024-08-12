/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/collateral/commodity/loanagency/PrepareLoanAgencyCommand.java,v 1.9 2004/08/04 05:48:28 hshii Exp $
 */
package com.integrosys.cms.ui.collateral.commodity.loanagency;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.collateral.bus.type.commodity.ICommodityCollateral;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.ui.collateral.TimeFreqList;
import com.integrosys.cms.ui.collateral.commodity.CommodityMainConstant;
import com.integrosys.cms.ui.common.CurrencyList;
import com.integrosys.component.commondata.app.CommonDataSingleton;

/**
 * Description
 * 
 * @author $Author: hshii $<br>
 * @version $Revision: 1.9 $
 * @since $Date: 2004/08/04 05:48:28 $ Tag: $Name: $
 */
public class PrepareLoanAgencyCommand extends AbstractCommand {
	public String[][] getParameterDescriptor() {
		return (new String[][] { { "commodityMainTrxValue", "java.util.HashMap", SERVICE_SCOPE },
				{ "from_page", "java.lang.String", REQUEST_SCOPE },
				{ "secIndexID", "java.lang.String", REQUEST_SCOPE }, });
	}

	/**
	 * Defines an two dimensional array with the result list to be expected as a
	 * result from the doExecute method using a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getResultDescriptor() {
		return (new String[][] { { "currencyCode", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },
				{ "facilityTypeID", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },
				{ "facilityTypeValue", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },
				{ "transTypeID", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },
				{ "transTypeValue", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },
				{ "timeFreqID", "java.util.Collection", REQUEST_SCOPE },
				{ "timeFreqValue", "java.util.Collection", REQUEST_SCOPE },
				{ "paymentFreqID", "java.util.Collection", REQUEST_SCOPE },
				{ "paymentFreqValue", "java.util.Collection", REQUEST_SCOPE },
				{ "calculationBaseID", "java.util.Collection", REQUEST_SCOPE },
				{ "calculationBaseValue", "java.util.Collection", REQUEST_SCOPE },
				{ "secID", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },
				{ "secValue", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE }, });
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

		String from_event = (String) map.get("from_page");
		if ((from_event != null) && from_event.equals(LoanAgencyAction.EVENT_PREPARE_UPDATE)) {
			HashMap trxValueMap = (HashMap) map.get("commodityMainTrxValue");
			ICommodityCollateral[] col = (ICommodityCollateral[]) trxValueMap.get("staging");
			int secInd = -1;
			String secIndex = (String) map.get("secIndexID");

			if (secIndex != null) {
				secInd = Integer.parseInt(secIndex);
			}

			Collection secID = new ArrayList();
			if (col != null) {
				for (int i = 0; i < col.length; i++) {
					if (((secInd != -1) && (i == secInd))
							|| (!col[i].getStatus().equals(ICMSConstant.STATE_DELETED) && !col[i].getStatus().equals(
									ICMSConstant.STATE_PENDING_DELETE))) {
						secID.add(String.valueOf(col[i].getSCISecurityID()));
					}
				}
			}

			result.put("secID", secID);
			result.put("secValue", secID);

			CurrencyList currList = CurrencyList.getInstance();
			result.put("currencyCode", currList.getCountryValues());

			FacilityTypeList facilityList = FacilityTypeList.getInstance();
			result.put("facilityTypeID", facilityList.getFacilityTypeID());
			result.put("facilityTypeValue", facilityList.getFacilityTypeValue());

			TrxTypeList trxTypeList = TrxTypeList.getInstance();
			result.put("transTypeID", trxTypeList.getTrxTypeID());
			result.put("transTypeValue", trxTypeList.getTrxTypeValue());

			PaymentFreqList paymentFreqList = PaymentFreqList.getInstance();
			result.put("paymentFreqID", paymentFreqList.getPaymentFreqID());
			result.put("paymentFreqValue", paymentFreqList.getPaymentFreqValue());

			Collection calculationBase = CommonDataSingleton
					.getCodeCategoryLabels(CommodityMainConstant.CALCULATION_BASE);
			result.put("calculationBaseID", calculationBase);
			result.put("calculationBaseValue", calculationBase);

			TimeFreqList timeFreqList = TimeFreqList.getInstance();
			result.put("timeFreqID", timeFreqList.getTimeFreqID());
			result.put("timeFreqValue", timeFreqList.getTimeFreqValue());
		}
		temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		return temp;
	}
}
