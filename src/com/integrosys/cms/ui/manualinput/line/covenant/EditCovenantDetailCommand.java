package com.integrosys.cms.ui.manualinput.line.covenant;

import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.AccessDeniedException;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.collateral.trx.ICollateralTrxValue;
import com.integrosys.cms.app.customer.bus.ILineCovenant;
import com.integrosys.cms.app.limit.bus.ILimit;
import com.integrosys.cms.app.limit.bus.ILimitCovenant;
import com.integrosys.cms.app.limit.bus.ILimitSysXRef;
import com.integrosys.cms.app.limit.trx.ILimitTrxValue;
import com.integrosys.cms.ui.manualinput.limit.covenant.ILmtCovenantConstants;

public class EditCovenantDetailCommand extends AbstractCommand implements ILmtCovenantConstants{
	
	public HashMap doExecute(HashMap map) throws CommandValidationException, CommandProcessingException, AccessDeniedException {
		HashMap resultMap = new HashMap();
		HashMap exceptionMap = new HashMap();
		HashMap returnMap = new HashMap();
		
		ILimitTrxValue itrxValue = (ILimitTrxValue) map.get("lmtTrxObj");
		ILineCovenant covenantDetail = (ILineCovenant) map.get(COVENANT_LINE_DETAIL_FORM);
		String index = (String) map.get("index");
			ILimit stageLimit = (ILimit) itrxValue.getStagingLimit();
			ILimitSysXRef refArr = stageLimit.getLimitSysXRefs()[(Integer.parseInt(index))-1];
			ILineCovenant covenantDetails[] =refArr.getCustomerSysXRef().getLineCovenant();

			int idx = 0;
			DefaultLogger.info(this, "Updating Line Detail into session : "+covenantDetails);
			covenantDetails[0] = covenantDetail;
		    resultMap.put("lmtTrxObj", itrxValue);
		
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		
		return returnMap;
	}
	
	public String[][] getParameterDescriptor() {
		return new String[][] {
			{ "lmtTrxObj", "com.integrosys.cms.app.limit.trx.ILimitTrxValue", SERVICE_SCOPE },
			{COVENANT_LINE_DETAIL_FORM, ILineCovenant.class.getName(), FORM_SCOPE},
			{ "index", "java.lang.String", REQUEST_SCOPE },
		};
	}
	
	public String[][] getResultDescriptor() {
		return new String[][] {
			{ "lmtTrxObj", "com.integrosys.cms.app.limit.trx.ILimitTrxValue", SERVICE_SCOPE },
			{ "index", "java.lang.String", REQUEST_SCOPE },
		};
	}
}
