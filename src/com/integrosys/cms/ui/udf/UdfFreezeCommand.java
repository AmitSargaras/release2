package com.integrosys.cms.ui.udf;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.AccessDeniedException;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.udf.bus.IUdf;
import com.integrosys.cms.app.udf.bus.UdfException;
import com.integrosys.cms.app.udf.proxy.IUdfProxyManager;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;

public class UdfFreezeCommand extends AbstractCommand implements ICommonEventConstant {

	IUdfProxyManager udfProxyManager;
	
	public IUdfProxyManager getUdfProxyManager() {
		return udfProxyManager;
	}

	public void setUdfProxyManager(IUdfProxyManager udfProxyManager) {
		this.udfProxyManager = udfProxyManager;
	}

	
	public HashMap doExecute(HashMap map) throws CommandValidationException, CommandProcessingException, AccessDeniedException {
		String idStr = (String) map.get("id");
		long id = Long.parseLong(idStr);
		IUdf udf =udfProxyManager.findUdfById("com.integrosys.cms.app.udf.bus.OBUdf", id);
		udfProxyManager.freezeUdf(udf);
		HashMap resultMap = new HashMap();
		resultMap.put("status", "freezed");
		HashMap returnMap = new HashMap();
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		return returnMap;
	}

	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ "id", "java.lang.String", REQUEST_SCOPE }, 
			});
	}

	public String[][] getResultDescriptor() {
		return (new String[][] {
			{ "status", "java.lang.String", REQUEST_SCOPE }
		});
	}
	
}
