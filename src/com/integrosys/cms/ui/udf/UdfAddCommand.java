package com.integrosys.cms.ui.udf;

import java.util.HashMap;
import java.util.List;

import org.apache.struts.action.ActionMessage;

import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.AccessDeniedException;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.udf.proxy.IUdfProxyManager;
import com.integrosys.cms.app.udf.bus.OBUdf;
import com.integrosys.cms.app.udf.bus.UDFConstants;
import com.integrosys.cms.app.udf.bus.UdfException;

public class UdfAddCommand extends AbstractCommand implements ICommonEventConstant {
	IUdfProxyManager udfProxyManager;
	
	public IUdfProxyManager getUdfProxyManager() {
		return udfProxyManager;
	}

	public void setUdfProxyManager(IUdfProxyManager udfProxyManager) {
		this.udfProxyManager = udfProxyManager;
	}
	public HashMap doExecute(HashMap map) throws CommandValidationException, CommandProcessingException, AccessDeniedException {
		HashMap resultMap = new HashMap();
		HashMap exceptionMap = new HashMap();
		try {
			OBUdf udf = (OBUdf)map.get("UdfObj");
			List seqList = getUdfProxyManager().getUdfSequencesByModuleId(Long.toString((udf.getModuleId())));
			
			int maxUdfLimit= UDFConstants.UDF_MAXLIMIT;
			if(3 == udf.getModuleId() ) {
				 maxUdfLimit= UDFConstants.UDF_MAXLIMIT_LIMITMODULE;
			}
			System.out.println("maxUdfLimit:: "+maxUdfLimit + " seqList.size():: "+seqList.size());
		  	if (seqList != null && seqList.size() == maxUdfLimit) {
				exceptionMap.put("udfError", new ActionMessage("udf.maxLimitExceeded"));
			}
			else if (seqList != null && seqList.contains(new Integer(udf.getSequence()))) {
				exceptionMap.put("udfError", new ActionMessage("udf.duplicateUdfNo"));
			}
			else {
				udfProxyManager.insertUdf(udf);
				resultMap.put("status", "added");
			}
		}
		catch (UdfException udfEx) {
			CommandProcessingException cpe = new CommandProcessingException(udfEx.getMessage());
			cpe.initCause(udfEx);
			throw cpe;
		}
		catch (Exception ex) {
			CommandProcessingException cpe = new CommandProcessingException(ex.getMessage());
			cpe.initCause(ex);
			throw cpe;
		}
		HashMap returnMap = new HashMap();
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		return returnMap;
	}

	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ "theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE }, 
				{ "UdfObj", "com.integrosys.cms.app.udf.bus.OBUdf", FORM_SCOPE  }
				}
		);
	}

	public String[][] getResultDescriptor() {
		return (new String[][] {
			{ "status", "java.lang.String", REQUEST_SCOPE }
			}
	);}

}
