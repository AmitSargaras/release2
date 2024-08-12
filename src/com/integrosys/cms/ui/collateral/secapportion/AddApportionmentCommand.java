/*
 * Created on Jul 18, 2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.integrosys.cms.ui.collateral.secapportion;

import java.util.HashMap;
import java.util.List;

import org.apache.struts.action.ActionMessage;

import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.collateral.bus.ICollateral;
import com.integrosys.cms.app.collateral.bus.OBSecApportionLmtDtl;
import com.integrosys.cms.app.collateral.bus.OBSecApportionmentDtl;
import com.integrosys.cms.app.collateral.trx.ICollateralTrxValue;

/**
 * @author Administrator
 * 
 *         TODO To change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Style - Code Templates
 */
public class AddApportionmentCommand extends AbstractCommand {
	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ "priorityRank", "java.lang.String", REQUEST_SCOPE },
				{ "limitId", "java.lang.String", REQUEST_SCOPE },
				{ "chargeDtlId", "java.lang.String", REQUEST_SCOPE },
				{ "form.secApportionObject", "com.integrosys.cms.app.collateral.bus.OBSecApportionmentDtl", FORM_SCOPE },
				{ "limitDtlList", "java.util.List", SERVICE_SCOPE },
				{ "serviceColObj", "com.integrosys.cms.app.collateral.trx.ICollateralTrxValue", SERVICE_SCOPE },
				{ "subtype", "java.lang.String", REQUEST_SCOPE }, });
	}

	public String[][] getResultDescriptor() {
		return (new String[][] {
				{ "serviceColObj", "com.integrosys.cms.app.collateral.trx.ICollateralTrxValue", SERVICE_SCOPE },
				{ "subtype", "java.lang.String", REQUEST_SCOPE }, });
	}

	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
		HashMap result = new HashMap();
		HashMap exceptionMap = new HashMap();
		HashMap temp = new HashMap();

		try {
			ICollateralTrxValue itrxValue = (ICollateralTrxValue) map.get("serviceColObj");
			ICollateral iCol = (ICollateral) itrxValue.getStagingCollateral();
			List limitDtl = (List) (map.get("limitDtlList"));
			if (limitDtl == null) {
				throw new Exception("limit detail is null!");
			}

			boolean hasError = false;

			if (!SecApportionmentValidator.validateRankDup((String) (map.get("priorityRank")), itrxValue)) {
				exceptionMap.put("priorityRank", new ActionMessage("error.apportionment.priorityRank"));
				hasError = true;
			}

			if (!SecApportionmentValidator.validateDuplicate((String) (map.get("chargeDtlId")), (String) (map
					.get("limitId")), itrxValue)) {
				exceptionMap.put("limitId", new ActionMessage("error.apportionment.limitId"));
				hasError = true;
			}

			if (!hasError) {
				OBSecApportionmentDtl apportionDtl = (OBSecApportionmentDtl) (map.get("form.secApportionObject"));
				SecApportionmentViewHelper helper = new SecApportionmentViewHelper(limitDtl);
				apportionDtl.setLeName(helper.getLeNameByLimitId(apportionDtl.getLeId()));
				OBSecApportionLmtDtl apportionLmtDtl = helper.getLimitDetailByLimitCharge(""
						+ apportionDtl.getLimitID(), "" + apportionDtl.getChargeDetailId());
				apportionDtl.setLimitIdDisp("" + apportionLmtDtl.getLimitId());
				apportionDtl.setProductDesc(apportionLmtDtl.getProductDesc());
				apportionDtl.setActivatedLimitAmt(apportionLmtDtl.getActivatedLimitAmt());
				apportionDtl.setActivatedLimitCcy(apportionLmtDtl.getActivatedLimitCcy());
				apportionDtl.setApprovedLimitAmt(apportionLmtDtl.getApprovedLimitAmt());
				apportionDtl.setApprovedLimitCcy(apportionLmtDtl.getApprovedLimitCcy());
				apportionDtl.setChargeRank(String.valueOf(apportionLmtDtl.getChargeRank()));
				apportionDtl.setChargeAmount(apportionLmtDtl.getChargeAmount());
				iCol.getSecApportionment().add(apportionDtl);
			}

			result.put("serviceColObj", itrxValue);
			result.put("subtype", map.get("subtype"));
			temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
			temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
			return temp;
		}
		catch (Exception ex) {
			ex.printStackTrace();
			throw new CommandProcessingException("Error when processing AddApportionmentCommand");
		}
	}
}
