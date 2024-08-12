package com.integrosys.cms.ui.collateral;

import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.collateral.bus.ICollateral;
import com.integrosys.cms.app.collateral.trx.ICollateralTrxValue;

public class SaveCersaiDetailsToSessionCommand extends AbstractCommand{
	
	public String[][] getParameterDescriptor() {
		return (new String[][] {
			 {"serviceColObj", "com.integrosys.cms.app.collateral.trx.ICollateralTrxValue", SERVICE_SCOPE},
             {"form.collateralObject", "com.integrosys.cms.app.collateral.bus.ICollateral", FORM_SCOPE}, 
				});
	}

	public String[][] getResultDescriptor() {
		return (new String[][] { 
			{"serviceColObj", "com.integrosys.cms.app.collateral.trx.ICollateralTrxValue", SERVICE_SCOPE},
			});
	}

	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
		HashMap result = new HashMap();
		HashMap exceptionMap = new HashMap();
		HashMap returnMap = new HashMap();
		try {
			ICollateralTrxValue itrxValue = (ICollateralTrxValue) map.get("serviceColObj");	
			ICollateral stageCollateral = (ICollateral) itrxValue.getStagingCollateral();
			
			ICollateral collateralFormObj = (ICollateral) map.get("form.collateralObject");
			
			itrxValue.setStagingCollateral(collateralFormObj);
			result.put("serviceColObj", itrxValue);
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