/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/collateral/property/AddChargeCommand.java,v 1.20 2006/09/15 08:33:04 hshii Exp $
 */

package com.integrosys.cms.ui.collateral.property;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;

import org.apache.commons.lang.StringUtils;
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
import com.integrosys.cms.app.collateral.bus.type.property.IPropertyCollateral;
import com.integrosys.cms.app.collateral.trx.ICollateralTrxValue;
import com.integrosys.cms.ui.collateral.CollateralHelper;
import com.integrosys.cms.ui.collateral.SecuritySubTypeUtil;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;

/**
 * @author $Author: hshii $<br>
 * @version $Revision: 1.20 $
 * @since $Date: 2006/09/15 08:33:04 $ Tag: $Name: $
 */

public class AddChargeCommand extends AbstractCommand {

	public String[][] getParameterDescriptor() {
		return (new String[][] { { "form.chargeObject", "java.lang.Object", FORM_SCOPE },
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

		HashMap result = new HashMap();
		HashMap exceptionMap = new HashMap();
		HashMap temp = new HashMap();

		ILimitCharge iPropLimit = (ILimitCharge) map.get("form.chargeObject");

		ICollateralTrxValue itrxValue = (ICollateralTrxValue) map.get("serviceColObj");
		try {
			SecuritySubTypeUtil.validateChargeType(iPropLimit.getChargeType(), iPropLimit.getLimitMaps(), "chargeType",
					exceptionMap);
		}
		catch (Exception e) {
			DefaultLogger.error(this, "<<<<<<<<<<< exception at SecuritySubTypeUtil.validateChargeType: "
					+ e.toString());
			throw new CommandProcessingException(e.getMessage());
		}
		IPropertyCollateral iProp = (IPropertyCollateral) itrxValue.getStagingCollateral();

		if (iPropLimit != null) {
			if (iPropLimit.getSecurityRank() == 0) {
				exceptionMap.put("rank", new ActionMessage("error.collateral.charge.rank"));
			}
			else if (iPropLimit.getSecurityRank() == 1) {
				if ((iPropLimit.getPriorChargeAmount() != null) && (iPropLimit.getPriorChargeAmount().getAmount() >= 0)) {
					exceptionMap.put("priorChargeAmount", new ActionMessage(
							"error.collateral.1st.charge.priorchargeamt"));
				}

				if ((iPropLimit.getPriorChargeChargee() != null) && (iPropLimit.getPriorChargeChargee().length() > 0)) {
					exceptionMap.put("chargeePriorCharge", new ActionMessage("error.collateral.1st.charge.chargee"));
				}
			}
			else {
				if ((iPropLimit.getPriorChargeAmount() == null) || (iPropLimit.getPriorChargeAmount().getAmount() < 0)) {
					exceptionMap.put("priorChargeAmount", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.AMOUNT,
							Validator.ERROR_LESS_THAN), "0", String.valueOf(IGlobalConstant.MAXIMIUM_ALLOWED_AMOUNT)));
				}

				if (StringUtils.isEmpty(iPropLimit.getPriorChargeChargee())) {
					exceptionMap.put("chargeePriorCharge", new ActionMessage("error.string.mandatory"));
				}

				if (StringUtils.isEmpty(iPropLimit.getPriorChargeType())) {
					exceptionMap.put("priorChargeType", new ActionMessage("error.string.mandatory"));
				}
			}

			if ((exceptionMap.get("rank") == null) && (iPropLimit.getSecurityRank() != 0)) {
				ILimitCharge[] charge = iProp.getLimitCharges();
				boolean isSameRank = false;
				for (int i = 0; (charge != null) && (i < charge.length) && !isSameRank; i++) {
					if (charge[i] != null) {
						if (charge[i].getSecurityRank() == iPropLimit.getSecurityRank()) {
							isSameRank = true;
						}
					}
				}

				if (isSameRank) {
					exceptionMap.put("rank", new ActionMessage("error.collateral.charge.rank.same"));
				}
			}

			boolean isSameLE = false;
			if (exceptionMap.get("limitID") == null) {
				ICollateralLimitMap[] limitSecMap = iPropLimit.getLimitMaps();
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
				ICollateralLimitMap[] limitSecMap = iPropLimit.getLimitMaps();
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
			addCharge(iProp, iPropLimit);

			itrxValue.setStagingCollateral(iProp);
			result.put("serviceColObj", itrxValue);

			result.put("subtype", map.get("subtype"));

			DefaultLogger.debug(this, "iPropLimit.getLimitMaps().length: " + iPropLimit.getLimitMaps().length);
		}

		temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		return temp;
	}

	public static void addCharge(IPropertyCollateral iProp, ILimitCharge iPropLimit) {
		ILimitCharge[] existingArray = iProp.getLimitCharges();
		int arrayLength = 0;
		if (existingArray != null) {
			arrayLength = existingArray.length;
		}

		ILimitCharge[] newArray = new ILimitCharge[arrayLength + 1];
		if (existingArray != null) {
			System.arraycopy(existingArray, 0, newArray, 0, arrayLength);
		}
		newArray[arrayLength] = iPropLimit;

		if (newArray != null) {
			Arrays.sort(newArray, new Comparator() {
				public int compare(Object o1, Object o2) {
					int int1 = ((ILimitCharge) o1).getSecurityRank();
					int int2 = ((ILimitCharge) o2).getSecurityRank();
					DefaultLogger.debug(this, "int1: " + int1 + "\tint2: " + int2 + "\tdifference: " + (int1 - int2));
					return int1 - int2;
				}
			});
		}

		iProp.setLimitCharges(newArray);
	}
}
