package com.integrosys.cms.ui.udf;

import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.systemBank.bus.SystemBankException;
import com.integrosys.cms.app.udf.bus.IUdf;
import com.integrosys.cms.app.udf.bus.OBUdf;
import com.integrosys.cms.app.udf.bus.UdfException;
import com.integrosys.cms.app.udf.proxy.IUdfProxyManager;
import com.integrosys.cms.app.udf.trx.IUdfTrxValue;
import com.integrosys.cms.app.udf.trx.OBUdfTrxValue;

public class MakerReadUdfCmd extends AbstractCommand implements ICommonEventConstant{

	private IUdfProxyManager udfProxyManager;

	public IUdfProxyManager getUdfProxyManager() {
		return udfProxyManager;
	}

	public void setUdfProxyManager(IUdfProxyManager udfProxyManager) {
		this.udfProxyManager = udfProxyManager;
	}
	/**
	 * Default Constructor
	 */
	public MakerReadUdfCmd() {
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
			// {"productCode", "java.lang.String", REQUEST_SCOPE},
			 { "startIndex", "java.lang.String", REQUEST_SCOPE },
			 { "trxId", "java.lang.String", REQUEST_SCOPE },
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
				{ "UdfObj", "com.integrosys.cms.app.udf.bus.OBUdf", FORM_SCOPE },
				{"IUdfTrxValue", "com.integrosys.cms.app.udf.trx.IUdfTrxValue", SERVICE_SCOPE},
				{"event", "java.lang.String", REQUEST_SCOPE}
				
		});
	}
	
	/**
	 * This method does the Business operations with the HashMap and put the
	 * results back into the HashMap.Here creation for Company Borrower is done.
	 * 
	 * @param map is of type HashMap
	 * @return HashMap with the Result
	 */
	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException,SystemBankException {
		HashMap returnMap = new HashMap();
		HashMap resultMap = new HashMap();
		try {
			IUdf udf;
			IUdfTrxValue trxValue=null;
			String trxId=(String) (map.get("trxId"));
			String startIdx = (String) map.get("startIndex");
			String event = (String) map.get("event");
			trxValue = (OBUdfTrxValue) getUdfProxyManager().getUdfTrxValue(Long.parseLong(trxId));
			udf = (OBUdf) trxValue.getUdf();
			DefaultLogger.debug(this, "startIdx: " + startIdx);
			if((trxValue.getStatus().equals("PENDING_CREATE"))||(trxValue.getStatus().equals("PENDING_UPDATE"))||(trxValue.getStatus().equals("PENDING_DELETE"))
					||trxValue.getStatus().equals("REJECTED")||trxValue.getStatus().equals("DRAFT") || trxValue.getStatus().equals("PENDING_ENABLE"))
			{
				resultMap.put("wip", "wip");
			}
			resultMap.put("IUdfTrxValue", trxValue);
			resultMap.put("UdfObj", udf);
			resultMap.put("event", event);
			resultMap.put("startIndex",startIdx);
		} catch (UdfException e) {
		
			DefaultLogger.debug(this, "got exception in doExecute" + e);
			e.printStackTrace();
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
