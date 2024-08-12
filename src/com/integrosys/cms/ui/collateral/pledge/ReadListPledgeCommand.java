package com.integrosys.cms.ui.collateral.pledge;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.collateral.bus.ICollateral;
import com.integrosys.cms.app.collateral.bus.ICollateralLimitMap;
import com.integrosys.cms.app.collateral.trx.ICollateralTrxValue;
import com.integrosys.cms.app.limit.bus.ILimit;
import com.integrosys.cms.app.limit.bus.ILimitProfile;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;

public class ReadListPledgeCommand extends AbstractCommand {
	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ IGlobalConstant.GLOBAL_LIMITPROFILE_OBJ, "com.integrosys.cms.app.limit.bus.ILimitProfile",
						GLOBAL_SCOPE },
				{ "serviceColObj", "com.integrosys.cms.app.collateral.trx.ICollateralTrxValue", SERVICE_SCOPE }, });
	}

	public String[][] getResultDescriptor() {
		return (new String[][] { { "limitList", "java.util.List", SERVICE_SCOPE },
				{ "collateralLimitMapList", "java.util.List", SERVICE_SCOPE }, });
	}

	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
		HashMap result = new HashMap();
		HashMap exceptionMap = new HashMap();
		HashMap returnMap = new HashMap();
		try {
			ILimitProfile limitProfile = (ILimitProfile) map.get(IGlobalConstant.GLOBAL_LIMITPROFILE_OBJ);
			ILimit[] limits = limitProfile.getLimits();

			ICollateralTrxValue trxValue = (ICollateralTrxValue) map.get("serviceColObj");
			ICollateral iCol = trxValue.getStagingCollateral();
			ICollateralLimitMap[] collateralLimitMap = iCol.getCollateralLimits();

			List limitList = new ArrayList();
			List collateralLimitMapList = new ArrayList();
			for (int i = 0; i < collateralLimitMap.length; i++) {
				for (int j = 0; j < limits.length; j++) {
					if (collateralLimitMap[i].getLimitID() == limits[j].getLimitID()) {
						limitList.add(limits[j]);
						collateralLimitMapList.add(collateralLimitMap[i]);
						break;
					}
				}
			}
			result.put("limitList", limitList);
			result.put("collateralLimitMapList", collateralLimitMapList);
		}
		catch (Exception e) {
			DefaultLogger.error(this, "got exception in doExecute", e);
			throw (new CommandProcessingException(e.getMessage()));
		}
		DefaultLogger.debug(this, "Going out of doExecute()");

		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		return returnMap;
	}
}