/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/collateral/others/UpdateChargeCommand.java,v 1.6 2006/09/15 08:32:34 hshii Exp $
 */

package com.integrosys.cms.ui.collateral.others;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;

import org.apache.struts.action.ActionMessage;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.validation.Validator;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ErrorKeyMapper;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.collateral.bus.ICollateralLimitMap;
import com.integrosys.cms.app.collateral.bus.ILimitCharge;
import com.integrosys.cms.app.collateral.bus.type.others.IOthersCollateral;
import com.integrosys.cms.app.collateral.trx.ICollateralTrxValue;
import com.integrosys.cms.ui.collateral.CollateralHelper;
import com.integrosys.cms.ui.collateral.SecuritySubTypeUtil;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;

/**
 * @author $Author: hshii $<br>
 * @version $Revision: 1.6 $
 * @since $Date: 2006/09/15 08:32:34 $
 * Tag: $Name:  $
 */

/**
 * Created by IntelliJ IDEA. User: ssathish Date: Jul 2, 2003 Time: 1:26:03 PM
 * To change this template use Options | File Templates.
 */
public class UpdateChargeCommand extends AbstractCommand {

	public String[][] getParameterDescriptor() {
		return (new String[][] { { "form.chargeObject", "java.lang.Object", FORM_SCOPE },
				{ "indexID", "java.lang.String", REQUEST_SCOPE }, { "subtype", "java.lang.String", REQUEST_SCOPE },
				{ "serviceColObj", "com.integrosys.cms.app.collateral.trx.ICollateralTrxValue", SERVICE_SCOPE } });
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

		HashMap result = new HashMap();
		HashMap exceptionMap = new HashMap();
		HashMap temp = new HashMap();

		ILimitCharge iLimit = (ILimitCharge) map.get("form.chargeObject");

		SecuritySubTypeUtil.validateChargeType(iLimit.getChargeType(), iLimit.getLimitMaps(), "chargeType",
				exceptionMap);

		ICollateralTrxValue itrxValue = (ICollateralTrxValue) map.get("serviceColObj");

		IOthersCollateral iOthersCollateral = (IOthersCollateral) itrxValue.getStagingCollateral();

		if (iLimit != null) {

			if (iLimit.getSecurityRank() == 0) {
				exceptionMap.put("rank", new ActionMessage("error.collateral.charge.rank"));
			}
			else if (iLimit.getSecurityRank() == 1) {
				if ((iLimit.getPriorChargeAmount() != null) && (iLimit.getPriorChargeAmount().getAmount() >= 0)) {
					exceptionMap
							.put("priorChargeAmount", new ActionMessage("error.collateral.1st.charge.priorchargeamt"));
				}
				if ((iLimit.getPriorChargeChargee() != null) && (iLimit.getPriorChargeChargee().length() > 0)) {
					exceptionMap.put("chargeePriorCharge", new ActionMessage("error.collateral.1st.charge.chargee"));
				}
			}
			else {
				if ((iLimit.getPriorChargeAmount() == null) || (iLimit.getPriorChargeAmount().getAmount() < 0)) {
					exceptionMap.put("priorChargeAmount", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.AMOUNT,
							Validator.ERROR_LESS_THAN), "0", String.valueOf(IGlobalConstant.MAXIMIUM_ALLOWED_AMOUNT)));
				}
				if ((iLimit.getPriorChargeChargee() == null) || iLimit.getPriorChargeChargee().equals("")) {
					exceptionMap.put("chargeePriorCharge", new ActionMessage("error.string.mandatory"));
				}
				if (iLimit.getLegalChargeDate() == null) {
					exceptionMap.put("dateLegalCharge", new ActionMessage("error.date.mandatory"));
				}
			}
			if ((exceptionMap.get("rank") == null) && (iLimit.getSecurityRank() != 0)) {
				ILimitCharge[] charge = iOthersCollateral.getLimitCharges();
				boolean isSameRank = false;
				for (int i = 0; (i < charge.length) && !isSameRank; i++) {
					if (charge[i] != null) {
						DefaultLogger.debug(this, "Charge" + i + " rank is :" + charge[i].getSecurityRank()
								+ "\trank is: " + iLimit.getSecurityRank());
						if (charge[i].getSecurityRank() == iLimit.getSecurityRank()) {
							if (charge[i].getRefID() != iLimit.getRefID()) {
								isSameRank = true;
							}
						}
					}
				}
				if (isSameRank) {
					exceptionMap.put("rank", new ActionMessage("error.collateral.charge.rank.same"));
				}
			}
			boolean isSameLE = false;
			if (exceptionMap.get("limitID") == null) {
				ICollateralLimitMap[] limitSecMap = iLimit.getLimitMaps();
				String sciLEID = limitSecMap[0].getSCILegalEntityID();
				for (int i = 1; (i < limitSecMap.length) && !isSameLE; i++) {
					if (limitSecMap[i].getSCILegalEntityID() != null &&
							!limitSecMap[i].getSCILegalEntityID().equals(sciLEID)) {
						isSameLE = true;
						exceptionMap.put("limitID", new ActionMessage("error.collateral.limit.diffLEID.limitid"));
					}
				}
			}
			if (exceptionMap.get("limitID") == null) {
				ArrayList tempList = new ArrayList();
				ICollateralLimitMap[] limitSecMap = iLimit.getLimitMaps();
				boolean sameLimit = false;
				for (int i = 0; (i < limitSecMap.length) && !sameLimit; i++) {
					String strLmtID = CollateralHelper.getColLimitMapLimitID(limitSecMap[i]);
					if (tempList.contains(strLmtID)) {
						sameLimit = true;
						exceptionMap.put("limitID", new ActionMessage("error.collateral.limit.sameLimit"));
					}
					else {
						tempList.add(strLmtID);
					}
				}
			}

		}

		if (exceptionMap.size() == 0) {
			int index = Integer.parseInt((String) map.get("indexID"));
			DefaultLogger.debug(this, "SubType:" + map.get("subtype"));
			DefaultLogger.debug(this, "Index is:" + index);

			ILimitCharge limitArray[] = iOthersCollateral.getLimitCharges();
			limitArray[index] = iLimit;

			if (limitArray != null) {
				Arrays.sort(limitArray, new Comparator() {
					public int compare(Object o1, Object o2) {
						int int1 = ((ILimitCharge) o1).getSecurityRank();
						int int2 = ((ILimitCharge) o2).getSecurityRank();
						DefaultLogger.debug(this, "int1: " + int1 + "\tint2: " + int2 + "\tdifference: "
								+ (int1 - int2));
						return int1 - int2;
					}
				});
			}

			iOthersCollateral.setLimitCharges(limitArray);
			itrxValue.setStagingCollateral(iOthersCollateral);

			result.put("serviceColObj", itrxValue);
			result.put("subtype", map.get("subtype"));
			DefaultLogger.debug(this, "After Addition1:" + itrxValue);
		}
		temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		return temp;
	}
}
