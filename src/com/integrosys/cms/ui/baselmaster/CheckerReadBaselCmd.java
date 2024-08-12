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

public class CheckerReadBaselCmd extends AbstractCommand implements	ICommonEventConstant{
	
	private IBaselProxyManager baselProxy;

	public IBaselProxyManager getBaselProxy() {
		return baselProxy;
	}

	public void setBaselProxy(IBaselProxyManager baselProxy) {
		this.baselProxy = baselProxy;
	}
	
	public CheckerReadBaselCmd() {
	}

	
	public String[][] getParameterDescriptor() {
		return (new String[][] { 
				{ "TrxId", "java.lang.String", REQUEST_SCOPE },
				{"event", "java.lang.String", REQUEST_SCOPE},
				
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
				{ "baselObj", "com.integrosys.cms.app.baselmaster.bus.OBBaselMaster", FORM_SCOPE },
				{"IBaselMasterTrxValue", "com.integrosys.cms.app.baselmaster.trx.IBaselMasterTrxValue", SERVICE_SCOPE},
				{"event", "java.lang.String", REQUEST_SCOPE},
				
		});
	}

	/**
	 * This method does the Business operations with the HashMap and put the
	 * results back into the HashMap.Here creation for Company Borrower is done.
	 * 
	 * @param map is of type HashMap
	 * @return HashMap with the Result
	 */
	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException,ComponentException {
		HashMap returnMap = new HashMap();
		HashMap resultMap = new HashMap();
		try {
			IBaselMaster basel;
			IBaselMasterTrxValue trxValue=null;
			String baselCode=(String) (map.get("TrxId"));
			String event = (String) map.get("event");
			// function to get Component Trx value
			trxValue = (OBBaselMasterTrxValue) getBaselProxy().getBaselByTrxID(baselCode);
			
			basel = (OBBaselMaster) trxValue.getStagingBaselMaster();
			
			resultMap.put("IBaselMasterTrxValue", trxValue);
			resultMap.put("baselObj", basel);
			resultMap.put("event", event);
		} catch (ComponentException e) {
		
			DefaultLogger.debug(this, "got exception in doExecute" + e);
			e.printStackTrace();
			throw (new CommandProcessingException(e.getMessage()));
		} catch (TransactionException e) {
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
