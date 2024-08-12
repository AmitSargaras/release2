package com.integrosys.cms.ui.cersaiMapping;

import java.util.HashMap;

import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.cersaiMapping.bus.CersaiMappingException;
import com.integrosys.cms.app.cersaiMapping.bus.CersaiMappingJdbcImpl;
import com.integrosys.cms.app.cersaiMapping.bus.ICersaiMapping;
import com.integrosys.cms.app.cersaiMapping.proxy.ICersaiMappingProxyManager;
import com.integrosys.cms.app.cersaiMapping.trx.ICersaiMappingTrxValue;
import com.integrosys.cms.app.cersaiMapping.trx.OBCersaiMappingTrxValue;
import com.integrosys.cms.app.transaction.OBTrxContext;

public class MakerCloseCersaiMappingCmd extends AbstractCommand implements ICommonEventConstant{

	private ICersaiMappingProxyManager cersaiMappingProxy;
	
	public ICersaiMappingProxyManager getCersaiMappingProxy() {
		return cersaiMappingProxy;
	}

	public void setCersaiMappingProxy(ICersaiMappingProxyManager cersaiMappingProxy) {
		this.cersaiMappingProxy = cersaiMappingProxy;
	}
	
	/**
	 * Default Constructor
	 */
	public MakerCloseCersaiMappingCmd() {
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
				{ "ICersaiMappingTrxValue", "com.integrosys.cms.app.cersaiMapping.trx.ICersaiMappingTrxValue",
						SERVICE_SCOPE },
				{ "theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE },
				{"event", "java.lang.String", REQUEST_SCOPE},
		});
	}
	
	public String[][] getResultDescriptor() {
		return (new String[][] {
				{ "request.ITrxValue", "com.integrosys.cms.app.transaction.ICMSTrxValue", REQUEST_SCOPE },
				{ "masterValueList", "com.integrosys.cms.app.cersaiMapping.bus.ICersaiMapping", SERVICE_SCOPE },
				{ "masterValueList", "com.integrosys.cms.app.cersaiMapping.bus.ICersaiMapping", REQUEST_SCOPE }
		});
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
			ICersaiMappingTrxValue trxValueIn = (OBCersaiMappingTrxValue) map.get("ICersaiMappingTrxValue");
			
			CersaiMappingJdbcImpl cersaiMappingJdbc=(CersaiMappingJdbcImpl)BeanHouse.get("cersaiMappingJdbc");
			ICersaiMappingTrxValue trxValueOut = new OBCersaiMappingTrxValue();
			
			String stagingRefId=trxValueIn.getStagingReferenceID();
			ICersaiMapping[] masterValueList =cersaiMappingJdbc.fetchValueList(stagingRefId);
			
			if( event.equals("maker_confirm_close") ){
				trxValueOut = getCersaiMappingProxy().makerCloseRejectedCersaiMapping(ctx, trxValueIn);
				resultMap.put("masterValueList", masterValueList);
				resultMap.put("request.ITrxValue", trxValueOut);
			}
				
		} catch (CersaiMappingException ex) {
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

