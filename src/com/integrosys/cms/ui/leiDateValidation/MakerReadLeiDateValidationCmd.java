package com.integrosys.cms.ui.leiDateValidation;

import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.leiDateValidation.bus.ILeiDateValidation;
import com.integrosys.cms.app.leiDateValidation.bus.OBLeiDateValidation;
import com.integrosys.cms.app.leiDateValidation.bus.LeiDateValidationException;
import com.integrosys.cms.app.leiDateValidation.proxy.ILeiDateValidationProxyManager;
import com.integrosys.cms.app.leiDateValidation.trx.ILeiDateValidationTrxValue;
import com.integrosys.cms.app.leiDateValidation.trx.OBLeiDateValidationTrxValue;
import com.integrosys.cms.app.systemBank.bus.SystemBankException;

public class MakerReadLeiDateValidationCmd extends AbstractCommand implements ICommonEventConstant{

	private ILeiDateValidationProxyManager leiDateValidationProxy;

	public ILeiDateValidationProxyManager getLeiDateValidationProxy() {
		return leiDateValidationProxy;
	}

	public void setLeiDateValidationProxy(ILeiDateValidationProxyManager leiDateValidationProxy) {
		this.leiDateValidationProxy = leiDateValidationProxy;
	}
	/**
	 * Default Constructor
	 */
	public MakerReadLeiDateValidationCmd() {
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
			 {"id", "java.lang.String", REQUEST_SCOPE},
			 { "startIndex", "java.lang.String", REQUEST_SCOPE },
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
				{ "leiDateValidationObj", "com.integrosys.cms.app.leiDateValidation.bus.OBLeiDateValidation", FORM_SCOPE },
				{"ILeiDateValidationTrxValue", "com.integrosys.cms.app.leiDateValidation.trx.ILeiDateValidationTrxValue", SERVICE_SCOPE},
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
			ILeiDateValidation leiDateValidation;
			ILeiDateValidationTrxValue trxValue=null;
			String id=(String) (map.get("id"));
			String startIdx = (String) map.get("startIndex");
			String event = (String) map.get("event");
			trxValue = (OBLeiDateValidationTrxValue) getLeiDateValidationProxy().getLeiDateValidationTrxValue(Long.parseLong(id));
			leiDateValidation = (OBLeiDateValidation) trxValue.getLeiDateValidation();
			DefaultLogger.debug(this, "startIdx: " + startIdx);
			if((trxValue.getStatus().equals("PENDING_CREATE"))||(trxValue.getStatus().equals("PENDING_UPDATE"))||(trxValue.getStatus().equals("PENDING_DELETE"))||trxValue.getStatus().equals("REJECTED")||trxValue.getStatus().equals("DRAFT"))
			{
				resultMap.put("wip", "wip");
			}
			resultMap.put("ILeiDateValidationTrxValue", trxValue);
			resultMap.put("leiDateValidationObj", leiDateValidation);
			resultMap.put("event", event);
			resultMap.put("startIndex",startIdx);
		} catch (LeiDateValidationException e) {
		
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
