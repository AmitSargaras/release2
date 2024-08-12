package com.integrosys.cms.ui.baselmaster;

import java.util.HashMap;

import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.baselmaster.bus.IBaselMaster;
import com.integrosys.cms.app.baselmaster.bus.OBBaselMaster;
import com.integrosys.cms.app.baselmaster.proxy.IBaselProxyManager;
import com.integrosys.cms.app.baselmaster.trx.IBaselMasterTrxValue;
import com.integrosys.cms.app.baselmaster.trx.OBBaselMasterTrxValue;
import com.integrosys.cms.app.component.bus.ComponentException;
import com.integrosys.cms.app.component.bus.IComponent;
import com.integrosys.cms.app.component.bus.OBComponent;
import com.integrosys.cms.app.component.trx.IComponentTrxValue;
import com.integrosys.cms.app.component.trx.OBComponentTrxValue;

public class MakerReadBaselCmd extends AbstractCommand implements ICommonEventConstant {
	
	private IBaselProxyManager baselProxy;

	public IBaselProxyManager getBaselProxy() {
		return baselProxy;
	}

	public void setBaselProxy(IBaselProxyManager baselProxy) {
		this.baselProxy = baselProxy;
	}

	public MakerReadBaselCmd() {
	}
	
	public String[][] getParameterDescriptor() {
		return (new String[][] { 
				 {"baselCode", "java.lang.String", REQUEST_SCOPE},
				 {"session.searchcomponentName", "java.lang.String", SERVICE_SCOPE},
		            {"session.startIndex", "java.lang.String", SERVICE_SCOPE},
		            {"startIndex", "java.lang.String", REQUEST_SCOPE},
				 {"event", "java.lang.String", REQUEST_SCOPE}		 
		});
	}

	/**
	 * Defines an two dimensional array with the result list to be expected as a
	 * result from the doExecute method using a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getResultDescriptor() {
		return (new String[][] { 
				{ "baselObj", "com.integrosys.cms.app.baselmaster.bus.OBBaselMaster",SERVICE_SCOPE },
				{ "baselObj", "com.integrosys.cms.app.baselmaster.bus.OBBaselMaster", FORM_SCOPE},
				{"event", "java.lang.String", REQUEST_SCOPE},
				{"session.searchcomponentName", "java.lang.String", SERVICE_SCOPE},
	            {"session.startIndex", "java.lang.String", SERVICE_SCOPE},
				{ "hubValueList", "java.util.List", REQUEST_SCOPE },
				{ "migratedFlag", "java.lang.String", SERVICE_SCOPE },
				{"IBaselMasterTrxValue", "com.integrosys.cms.app.baselmaster.trx.IBaselMasterTrxValue", SERVICE_SCOPE}
				 });
	}

	/**
	 * This method does the Business operations with the HashMap and put the
	 * results back into the HashMap.Here creation for Company Borrower is done.
	 * 
	 * @param map is of type HashMap
	 * @return HashMap with the Result
	 */
	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
		
		HashMap returnMap = new HashMap();
		HashMap resultMap = new HashMap();
		try {
			IBaselMaster basel;
			IBaselMasterTrxValue trxValue=null;
			String startIdx ="";
			String event = (String) map.get("event");
			String baselCode=(String) (map.get("baselCode"));
			startIdx = (String) map.get("startIndex");
			if(startIdx==null){
				startIdx = (String) map.get("session.startIndex");
			}			
			DefaultLogger.debug(this, "startIdx: " + startIdx);
			
			trxValue = (OBBaselMasterTrxValue) getBaselProxy().getBaselTrxValue(Long.parseLong(baselCode));
			basel = (OBBaselMaster) trxValue.getBaselMaster();
			
			
			
			if(!(trxValue.getStatus().equals("ACTIVE")))
			{
				resultMap.put("wip", "wip");
			}
			
			resultMap.put("event", event);
			resultMap.put("session.startIndex",startIdx);	
			resultMap.put("session.searchcomponentName",map.get("session.searchcomponentName"));
			resultMap.put("IBaselMasterTrxValue", trxValue);
			resultMap.put("baselObj", basel);
		}catch (ComponentException ex) {
			DefaultLogger.debug(this, "got exception in doExecute" + ex);
			ex.printStackTrace();
			throw (new CommandProcessingException(ex.getMessage()));
		}
		catch (TransactionException e) {
			DefaultLogger.debug(this, "got exception in doExecute" + e);
			e.printStackTrace();
			throw (new CommandProcessingException(e.getMessage()));
		}catch (Exception e) {
            DefaultLogger.debug(this, "got exception in doExecute" + e);
            e.printStackTrace();
            throw (new CommandProcessingException(e.getMessage()));
        }
		

		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		return returnMap;
	}

}
