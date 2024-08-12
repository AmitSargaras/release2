package com.integrosys.cms.ui.udf;

import java.util.HashMap;

import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;


public class UdfPrepareAddCommand extends AbstractCommand implements ICommonEventConstant {
	
	public String[][] getParameterDescriptor() {
		return (new String[][] {});
	}

	public String[][] getResultDescriptor() {
		return (new String[][]{
		});
	}

	public HashMap doExecute(HashMap map) throws CommandValidationException, CommandProcessingException {
		map.put(ICommonEventConstant.COMMAND_RESULT_MAP, map);
		return map;
	}
}
