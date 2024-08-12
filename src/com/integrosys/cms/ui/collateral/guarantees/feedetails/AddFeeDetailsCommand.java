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

/**
 * Created by IntelliJ IDEA. User: jitendra Date: May 8, 2007 Time: 7:23:48 PM
 * To change this template use File | Settings | File Templates.
 */
public class AddFeeDetailsCommand extends AbstractCommand {

	public String[][] getParameterDescriptor() {
		return (new String[][] { { "form.feeDetailsObject", "java.lang.Object", FORM_SCOPE },
				{ "indexID", "java.lang.String", REQUEST_SCOPE },
				{ "serviceColObj", "com.integrosys.cms.app.collateral.trx.ICollateralTrxValue", SERVICE_SCOPE },
				{ "subtype", "java.lang.String", REQUEST_SCOPE }, });
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
				{ "serviceColObj", "com.integrosys.cms.app.collateral.trx.ICollateralTrxValue", SERVICE_SCOPE },
				{ "subtype", "java.lang.String", REQUEST_SCOPE }, });
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

		DefaultLogger.debug(this, "inside doExecute ");
		HashMap result = new HashMap();
		HashMap exceptionMap = new HashMap();
		HashMap temp = new HashMap();

		IFeeDetails iFeeDetails = (IFeeDetails) map.get("form.feeDetailsObject");
		ICollateralTrxValue itrxValue = (ICollateralTrxValue) map.get("serviceColObj");

		IGteGovtLink iGteGovtLink = (IGteGovtLink) itrxValue.getStagingCollateral();

		this.addFeeDetails(iGteGovtLink, iFeeDetails);

		itrxValue.setStagingCollateral(iGteGovtLink);

		result.put("serviceColObj", itrxValue);
		result.put("subtype", map.get("subtype"));

		DefaultLogger.debug(this, "Existing doExecute ");

		temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		return temp;
	}

	private void addFeeDetails(IGteGovtLink iGteGovtLink, IFeeDetails iFeeDetails) {
		IFeeDetails[] existingArray = iGteGovtLink.getFeeDetails();
		int arrayLength = 0;
		if (existingArray != null) {
			arrayLength = existingArray.length;
		}

		IFeeDetails[] newArray = new IFeeDetails[arrayLength + 1];
		if (existingArray != null) {
			System.arraycopy(existingArray, 0, newArray, 0, arrayLength);
		}
		newArray[arrayLength] = iFeeDetails;
		iGteGovtLink.setFeeDetails(newArray);
	}
}
