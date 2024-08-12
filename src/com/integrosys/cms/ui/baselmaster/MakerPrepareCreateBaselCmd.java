package com.integrosys.cms.ui.baselmaster;

import java.util.HashMap;

import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.baselmaster.proxy.BaselProxyManagerImpl;
import com.integrosys.cms.app.baselmaster.proxy.IBaselProxyManager;
import com.integrosys.cms.app.baselmaster.trx.OBBaselMasterTrxValue;
import com.integrosys.cms.app.component.trx.OBComponentTrxValue;

public class MakerPrepareCreateBaselCmd extends AbstractCommand implements ICommonEventConstant{
	private IBaselProxyManager baselProxy;
	
	
	
	public IBaselProxyManager getBaselProxy() {
		return baselProxy;
	}

	public void setBaselProxy(IBaselProxyManager baselProxy) {
		this.baselProxy = baselProxy;
	}

	public MakerPrepareCreateBaselCmd(){
		
	}
	
	public String[][] getParameterDescriptor() {
		return (new String[][] { 
				  {"theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE},
				  {"session.searchbaselName", "java.lang.String", SERVICE_SCOPE},
		            {"session.startIndex", "java.lang.String", SERVICE_SCOPE}
				  
					 
		});
	}
	
	public String[][] getResultDescriptor() {
		return (new String[][] { 
				{"IComponentTrxValue", "com.integrosys.cms.app.component.trx.OBComponentTrxValue", SERVICE_SCOPE},
				{"session.searchbaselName", "java.lang.String", SERVICE_SCOPE},
	            {"session.startIndex", "java.lang.String", SERVICE_SCOPE}
				 });
	}
	
	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
		
		HashMap returnMap = new HashMap();
		HashMap resultMap = new HashMap();
		
		OBBaselMasterTrxValue baselTrxValue = new OBBaselMasterTrxValue();
		resultMap.put("session.startIndex", map.get("session.startIndex"));
		resultMap.put("session.searchbaselName",map.get("session.searchbaselName"));
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		return returnMap;
	}


}
