/*
 * Created on Sep 26, 2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.integrosys.cms.ui.collateral.commodity.secapportion;

import java.util.HashMap;
import java.util.List;

import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.collateral.bus.ICollateral;
import com.integrosys.cms.app.collateral.bus.OBSecApportionLmtDtl;
import com.integrosys.cms.app.collateral.bus.OBSecApportionmentDtl;
import com.integrosys.cms.app.collateral.trx.ICollateralTrxValue;
import com.integrosys.cms.ui.collateral.secapportion.SecApportionmentViewHelper;

/**
 * @author Administrator
 * 
 *         TODO To change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Style - Code Templates
 */
public class UpdateApportionmentCommand extends AbstractCommand {
	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ "form.secApportionObject", "com.integrosys.cms.app.collateral.bus.OBSecApportionmentDtl", FORM_SCOPE },
				{ "commodityMainTrxValue", "java.util.HashMap", SERVICE_SCOPE },
				{ "limitDtlList", "java.util.List", SERVICE_SCOPE }, { "indexID", "java.lang.String", REQUEST_SCOPE },
				{ "apportionIndexID", "java.lang.String", REQUEST_SCOPE }, });
	}

	public String[][] getResultDescriptor() {
		return (new String[][] { { "commodityMainTrxValue", "java.util.HashMap", SERVICE_SCOPE }, });
	}

	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
		HashMap result = new HashMap();
		HashMap exceptionMap = new HashMap();
		HashMap temp = new HashMap();
		try {
			HashMap mapInMap = (HashMap) (map.get("commodityMainTrxValue"));
			String indexID = (String) (map.get("indexID"));
			ICollateralTrxValue[] trxValList = (ICollateralTrxValue[]) (mapInMap.get("trxValue"));
			ICollateralTrxValue itrxValue = trxValList[Integer.parseInt(indexID)];
			ICollateral iCol = (ICollateral) itrxValue.getStagingCollateral();
			List limitDtl = (List) (map.get("limitDtlList"));
			if (limitDtl == null) {
				throw new Exception("limit detail is null!");
			}
			String indexId = (String) (map.get("apportionIndexID"));

			boolean hasError = false;
			/*
			 * if
			 * (!SecApportionmentValidator.validateRankInOrder((String)(map.get
			 * ("priorityRank")), itrxValue)) { exceptionMap.put("priorityRank",
			 * new ActionMessage("error.apportionment.priorityRank")); hasError
			 * = true; }
			 */

			if (!hasError) {
				OBSecApportionmentDtl apportionDtl = (OBSecApportionmentDtl) (map.get("form.secApportionObject"));
				OBSecApportionmentDtl prevDetail = (OBSecApportionmentDtl) (iCol.getSecApportionment().get(Integer
						.parseInt(indexId)));
				SecApportionmentViewHelper helper = new SecApportionmentViewHelper(limitDtl);
				OBSecApportionLmtDtl apportionLmtDtl = helper.getLimitDetailByLimitCharge(""
						+ apportionDtl.getLimitID(), "" + apportionDtl.getChargeDetailId());
				prevDetail.setPriorityRankingAmount(apportionDtl.getPriorityRankingAmount());
				prevDetail.setChargeRank(String.valueOf(apportionLmtDtl.getChargeRank()));
				prevDetail.setPercAmtInd(apportionDtl.getPercAmtInd());
				prevDetail.setByAbsoluteAmt(apportionDtl.getByAbsoluteAmt());
				prevDetail.setByPercentage(apportionDtl.getByPercentage());
				prevDetail.setMinPercAmtInd(apportionDtl.getMinPercAmtInd());
				prevDetail.setMinAbsoluteAmt(apportionDtl.getMinAbsoluteAmt());
				prevDetail.setMinPercentage(apportionDtl.getMinPercentage());
				prevDetail.setMaxPercAmtInd(apportionDtl.getMaxPercAmtInd());
				prevDetail.setMaxAbsoluteAmt(apportionDtl.getMaxAbsoluteAmt());
				prevDetail.setMaxPercentage(apportionDtl.getMaxPercentage());
			}

			result.put("commodityMainTrxValue", mapInMap);
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
