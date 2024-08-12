package com.integrosys.cms.ui.udf;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.AccessDeniedException;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.udf.bus.IUdf;
import com.integrosys.cms.app.udf.bus.UdfException;
import com.integrosys.cms.app.udf.proxy.IUdfProxyManager;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;

public class UdfListCommand extends AbstractCommand implements ICommonEventConstant {

	IUdfProxyManager udfProxyManager;
	
	public IUdfProxyManager getUdfProxyManager() {
		return udfProxyManager;
	}

	public void setUdfProxyManager(IUdfProxyManager udfProxyManager) {
		this.udfProxyManager = udfProxyManager;
	}

	public String[][] getParameterDescriptor() {
		return (new String[][] { 
			{ IGlobalConstant.GLOBAL_CUSTOMER_OBJ, "com.integrosys.cms.app.customer.bus.OBCMSCustomer", GLOBAL_SCOPE },
			{ IGlobalConstant.GLOBAL_LIMITPROFILE_OBJ, "com.integrosys.cms.app.limit.bus.ILimitProfile", GLOBAL_SCOPE},
			{ "event", "java.lang.String", REQUEST_SCOPE }
			});
	}

	public String[][] getResultDescriptor() {
		return (new String[][]{
	            {"UdfMap", "java.util.Map", SERVICE_SCOPE},
	            {"udfKeyList", "java.util.List", SERVICE_SCOPE},
	            {com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY, "java.util.Locale", GLOBAL_SCOPE},
	            {"event", "java.lang.String", SERVICE_SCOPE}
		});
	}

	public HashMap doExecute(HashMap map) throws CommandValidationException, CommandProcessingException, AccessDeniedException {
		List udfList;
		List udfKeyList = new ArrayList();
		Map udfMap = new HashMap();
		try {
			//udfList = getUdfProxyManager().findAllUdfs();
			
			SearchResult udfListSerachRes;
			udfListSerachRes = getUdfProxyManager().getAllActualUdf();
			
			if(udfListSerachRes!=null){
				udfList = new ArrayList(udfListSerachRes.getResultList());
				}else {
					udfList = new ArrayList();
				}
			Iterator iter = udfList.iterator();
			IUdf udf;
			while (iter.hasNext()) {
				udf = (IUdf) iter.next();
				if (udfMap.get(udf.getModuleName()) == null) {
					udfMap.put(udf.getModuleName(), new ArrayList());
					udfKeyList.add(udf.getModuleName());
				}
				((List)udfMap.get(udf.getModuleName())).add(udf);
			}
		}
		catch (UdfException udfException) {
			CommandProcessingException cpe = new CommandProcessingException("UdfListCommand: "+ udfException.getMessage());
			cpe.initCause(udfException);
			throw cpe;			
		}
		catch (Exception exception) {
			CommandProcessingException cpe = new CommandProcessingException("UdfListCommand: Error Processing Command. ");
			cpe.initCause(exception);
			throw cpe;			
		}
		HashMap resultMap = new HashMap();
		resultMap.put("UdfMap", udfMap);
		resultMap.put("udfKeyList", udfKeyList);
		HashMap returnMap = new HashMap();
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		return returnMap;
	}
	
}
