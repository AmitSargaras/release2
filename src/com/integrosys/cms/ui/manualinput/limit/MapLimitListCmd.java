package com.integrosys.cms.ui.manualinput.limit;

import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.AccessDeniedException;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.limit.bus.ILimit;
import com.integrosys.cms.app.limit.trx.ILimitTrxValue;

public class MapLimitListCmd extends AbstractCommand {
	public String[][] getParameterDescriptor() {
		return (new String[][] { { "event", "java.lang.String", REQUEST_SCOPE },
				{ "lmtTrxObj", "com.integrosys.cms.app.limit.trx.ILimitTrxValue", SERVICE_SCOPE }, });

	}

	public String[][] getResultDescriptor() {
		return (new String[][] { { "lmtList", "java.lang.Object", FORM_SCOPE }, });
	}

	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException,
			AccessDeniedException {
		HashMap result = new HashMap();
		HashMap exceptionMap = new HashMap();
		HashMap temp = new HashMap();
		DefaultLogger.debug(this, "Inside MapLimitListCmd.java  line 30.");
		try {
			ILimitTrxValue lmtTrxValue = (ILimitTrxValue) (map.get("lmtTrxObj"));
			ILimit curLmt = lmtTrxValue.getStagingLimit();
			result.put("lmtList", curLmt);
		}
		catch (Exception ex) {
			throw (new CommandProcessingException(ex.getMessage()));
		}
		temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		DefaultLogger.debug(this, "Out MapLimitListCmd.java  line 41 Done.");
		return temp;
	}
}