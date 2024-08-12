package com.integrosys.cms.ui.collateral.guarantees.gtebanksame;

import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.collateral.bus.type.guarantee.IGuaranteeCollateral;
import com.integrosys.cms.app.collateral.trx.ICollateralTrxValue;

public class SaveBankGuaranteeToSessionCommand extends AbstractCommand{
	public String[][] getParameterDescriptor() {
		return (new String[][] {
			    {"serviceColObj", ICollateralTrxValue.class.getName(), SERVICE_SCOPE},
				{ "form.collateralObject", "com.integrosys.cms.app.collateral.bus.type.guarantee.IGuaranteeCollateral", FORM_SCOPE }, 
				});
	}

	public String[][] getResultDescriptor() {
		return (new String[][] { 
			{"serviceColObj", ICollateralTrxValue.class.getName(), SERVICE_SCOPE},
			});
	}

	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
		HashMap result = new HashMap();
		HashMap exceptionMap = new HashMap();
		HashMap returnMap = new HashMap();
		try {
			ICollateralTrxValue itrxValue = (ICollateralTrxValue) map.get("serviceColObj");	
			IGuaranteeCollateral stageCollateral = (IGuaranteeCollateral) itrxValue.getStagingCollateral();
			
			IGuaranteeCollateral guaranteeFormObj = (IGuaranteeCollateral) map.get("form.collateralObject");
			System.out.println("guaranteeFormObj "+guaranteeFormObj.getAddressLine1()+"-----"+guaranteeFormObj.getGuarantersNamePrefix());
			/*stageCollateral.setAddressLine1(guaranteeFormObj.getAddressLine1());
			stageCollateral.setGuarantersNamePrefix(guaranteeFormObj.getGuarantersNamePrefix());*/
			itrxValue.setStagingCollateral(guaranteeFormObj);
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