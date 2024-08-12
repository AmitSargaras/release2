package com.integrosys.cms.ui.collateral.guarantees.feedetails;

import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.collateral.bus.type.guarantee.subtype.gtegovtlink.IFeeDetails;
import com.integrosys.cms.app.collateral.bus.type.guarantee.subtype.gtegovtlink.IGteGovtLink;
import com.integrosys.cms.app.collateral.trx.ICollateralTrxValue;
import com.integrosys.cms.ui.collateral.CategoryCodeConstant;
import com.integrosys.cms.ui.common.CommonCodeList;

/**
 * Created by IntelliJ IDEA. User: jitendra Date: May 7, 2007 Time: 7:04:03 PM
 * To change this template use File | Settings | File Templates.
 */
public class ReadFeeDetailsCommand extends AbstractCommand {

	public String[][] getParameterDescriptor() {
		return (new String[][] { { "indexID", "java.lang.String", REQUEST_SCOPE },
				{ "subtype", "java.lang.String", REQUEST_SCOPE },
				{ "serviceColObj", "com.integrosys.cms.app.collateral.trx.ICollateralTrxValue", SERVICE_SCOPE },
				{ "from_event", "java.lang.String", REQUEST_SCOPE },
				{ "formActionName", "java.lang.String", REQUEST_SCOPE }, });
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
				{ "form.feeDetailsObject", "java.lang.Object", FORM_SCOPE },
				{ "actualFeeDetails",
						"com.integrosys.cms.app.collateral.bus.type.guarantee.subtype.gtegovtlink.IFeeDetails",
						REQUEST_SCOPE },
				{ "stageFeeDetails",
						"com.integrosys.cms.app.collateral.bus.type.guarantee.subtype.gtegovtlink.IFeeDetails",
						REQUEST_SCOPE }, { "indexID", "java.lang.String", REQUEST_SCOPE },
				{ "subtype", "java.lang.String", REQUEST_SCOPE }, { "from_event", "java.lang.String", REQUEST_SCOPE },
				{ "tenorUnitID", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },
				{ "tenorUnitValue", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE }, });
	}

	/**
	 * This method does the Business operations with the HashMap and put the
	 * results back into the HashMap.Here reading for Company Borrower is done.
	 * 
	 * @param map is of type HashMap
	 * @return HashMap with the Result
	 * @throws com.integrosys.base.uiinfra.exception.CommandProcessingException
	 *         on errors
	 * @throws com.integrosys.base.uiinfra.exception.CommandValidationException
	 *         on errors
	 */
	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {

		HashMap result = new HashMap();
		HashMap exceptionMap = new HashMap();
		HashMap temp = new HashMap();

		IGteGovtLink iGteGovtLink;

		long index = Long.parseLong((String) map.get("indexID"));

		// System.out.println("index = " + index);

		String from_event = (String) map.get("from_event");
		String formActionName = (String) map.get("formActionName");

		ICollateralTrxValue itrxValue = (ICollateralTrxValue) map.get("serviceColObj");

		if ((from_event != null) && from_event.equals("read")) {
			iGteGovtLink = (IGteGovtLink) itrxValue.getCollateral();
		}
		else {
			iGteGovtLink = (IGteGovtLink) itrxValue.getStagingCollateral();
			if ((from_event != null) && from_event.equals("process")) {
				IGteGovtLink iCol1 = (IGteGovtLink) itrxValue.getCollateral();
				IGteGovtLink iCol2 = (IGteGovtLink) itrxValue.getStagingCollateral();
				IFeeDetails actualObj = null;
				if (iCol1 != null)
					actualObj = getItem(iCol1.getFeeDetails(), index);
				IFeeDetails stageObj = getItem(iCol2.getFeeDetails(), index);
				DefaultLogger.debug(this, "actual Fee Details: " + actualObj + "\tstage Fee Details: " + stageObj);
				result.put("actualFeeDetails", actualObj);
				result.put("stageFeeDetails", stageObj);
			}
		}

		IFeeDetails feeDetailsObject;
		if (from_event != null) {
			feeDetailsObject = getItem(iGteGovtLink.getFeeDetails(), index);
			if ((feeDetailsObject == null) && from_event.equals("process")) {
				feeDetailsObject = getItem(((IGteGovtLink) itrxValue.getCollateral()).getFeeDetails(), index);
			}
		}
		else {
			feeDetailsObject = iGteGovtLink.getFeeDetails()[(int) index];
		}

		CommonCodeList commonCode = CommonCodeList.getInstance(CategoryCodeConstant.TIME_FREQ);

		result.put("tenorUnitID", commonCode.getCommonCodeValues());
		result.put("tenorUnitValue", commonCode.getCommonCodeLabels());

		result.put("form.feeDetailsObject", feeDetailsObject);

		result.put("indexID", map.get("indexID"));
		result.put("subtype", map.get("subtype"));
		result.put("from_event", from_event);
		result.put("formActionName", formActionName);
		temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		return temp;
	}

	private IFeeDetails getItem(IFeeDetails temp[], long itemRef) {
		IFeeDetails item = null;
		if (temp == null) {
			return item;
		}
		for (int i = 0; i < temp.length; i++) {
			if (temp[i].getRefID() == itemRef) {
				item = temp[i];
			}
			else {
				continue;
			}
		}
		return item;
	}

}
