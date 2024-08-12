package com.integrosys.cms.ui.npaTraqCodeMaster;

import java.util.HashMap;

import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.npaTraqCodeMaster.bus.NpaTraqCodeMasterException;
import com.integrosys.cms.app.npaTraqCodeMaster.proxy.INpaTraqCodeMasterProxyManager;
import com.integrosys.cms.app.npaTraqCodeMaster.trx.INpaTraqCodeMasterTrxValue;
import com.integrosys.cms.app.npaTraqCodeMaster.trx.OBNpaTraqCodeMasterTrxValue;
import com.integrosys.cms.app.transaction.OBTrxContext;

public class MakerCloseNpaTraqCodeMasterCmd extends AbstractCommand implements ICommonEventConstant{

	private INpaTraqCodeMasterProxyManager npaTraqCodeMasterProxy;
	
	public INpaTraqCodeMasterProxyManager getNpaTraqCodeMasterProxy() {
		return npaTraqCodeMasterProxy;
	}

	public void setNpaTraqCodeMasterProxy(INpaTraqCodeMasterProxyManager npaTraqCodeMasterProxy) {
		this.npaTraqCodeMasterProxy = npaTraqCodeMasterProxy;
	}
	
	/**
	 * Default Constructor
	 */
	public MakerCloseNpaTraqCodeMasterCmd() {
	}
	
	/**
	 * Defines an two dimensional array with the parameter list to be passed to
	 * the doExecute method by a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ "INpaTraqCodeMasterTrxValue", "com.integrosys.cms.app.npaTraqCodeMaster.trx.INpaTraqCodeMasterTrxValue",
						SERVICE_SCOPE },
				{ "theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE },
				{"event", "java.lang.String", REQUEST_SCOPE},
		});
	}
	
	public String[][] getResultDescriptor() {
		return (new String[][] {
				{ "request.ITrxValue", "com.integrosys.cms.app.transaction.ICMSTrxValue", REQUEST_SCOPE } });
	}

	/**
	 * This method does the Business operations with the HashMap and put the
	 * results back into the HashMap.
	 *
	 * @param map
	 *            is of type HashMap
	 * @return HashMap with the Result
	 */
	
	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
		HashMap returnMap = new HashMap();
		HashMap resultMap = new HashMap();
		HashMap exceptionMap = new HashMap();
		try {
			String event = (String) map.get("event");
			OBTrxContext ctx = (OBTrxContext) map.get("theOBTrxContext");
			INpaTraqCodeMasterTrxValue trxValueIn = (OBNpaTraqCodeMasterTrxValue) map.get("INpaTraqCodeMasterTrxValue");
			INpaTraqCodeMasterTrxValue trxValueOut = new OBNpaTraqCodeMasterTrxValue();
			
			if( event.equals("maker_confirm_close") ){
				trxValueOut = getNpaTraqCodeMasterProxy().makerCloseRejectedNpaTraqCodeMaster(ctx, trxValueIn);
				resultMap.put("request.ITrxValue", trxValueOut);
			}
			else if(event.equals("maker_confirm_draft_close")){
            	 trxValueOut = getNpaTraqCodeMasterProxy().makerCloseDraftNpaTraqCodeMaster(ctx, trxValueIn);
            	 resultMap.put("request.ITrxValue", trxValueOut);
            }
				
		} catch (NpaTraqCodeMasterException ex) {
			DefaultLogger.debug(this, "got exception in doExecute" + ex);
			ex.printStackTrace();
			throw (new CommandProcessingException(ex.getMessage()));
		} catch (TransactionException e) {
			DefaultLogger.debug(this, "got exception in doExecute" + e);
			throw (new CommandProcessingException(e.getMessage()));
		} catch (Exception e) {
			DefaultLogger.debug(this, "got exception in doExecute" + e);
			e.printStackTrace();
			throw (new CommandProcessingException(e.getMessage()));
		}
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		return returnMap;
	}
}
