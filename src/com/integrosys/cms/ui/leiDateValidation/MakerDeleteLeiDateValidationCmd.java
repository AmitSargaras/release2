package com.integrosys.cms.ui.leiDateValidation;

import java.util.HashMap;

import org.apache.struts.action.ActionMessage;

import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.leiDateValidation.bus.OBLeiDateValidation;
import com.integrosys.cms.app.leiDateValidation.bus.LeiDateValidationException;
import com.integrosys.cms.app.leiDateValidation.proxy.ILeiDateValidationProxyManager;
import com.integrosys.cms.app.leiDateValidation.trx.ILeiDateValidationTrxValue;
import com.integrosys.cms.app.leiDateValidation.trx.OBLeiDateValidationTrxValue;
import com.integrosys.cms.app.transaction.OBTrxContext;

public class MakerDeleteLeiDateValidationCmd extends AbstractCommand implements ICommonEventConstant {

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
	public MakerDeleteLeiDateValidationCmd() {
	}
	
	/**
	 * Defines an two dimensional array with the result list to be
	 * expected as a result from the doExecute method using a HashMap
	 * syntax for the array is (HashMapkey,classname,scope)
	 * The scope may be request,form or service
	 *
	 * @return the two dimensional String array
	 */
	public String[][] getParameterDescriptor() {
		return (new String[][]{
				{"ILeiDateValidationTrxValue", "com.integrosys.cms.app.leiDateValidation.trx.ILeiDateValidationTrxValue", SERVICE_SCOPE},
				{"theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE},
				{"event", "java.lang.String", REQUEST_SCOPE},
				{ "leiDateValidationObj", "com.integrosys.cms.app.leiDateValidation.bus.OBLeiDateValidation", FORM_SCOPE },
				{"remarks", "java.lang.String", REQUEST_SCOPE}
		});
	}
	
	/**
	 * Defines an two dimensional array with the result list to be
	 * expected as a result from the doExecute method using a HashMap
	 * syntax for the array is (HashMapkey,classname,scope)
	 * The scope may be request,form or service
	 *
	 * @return the two dimensional String array
	 */
	public String[][] getResultDescriptor() {
		return (new String[][]{
				{"request.ITrxValue", "com.integrosys.cms.app.transaction.ICMSTrxValue", REQUEST_SCOPE},
		});
	}
	
	/**
	 * This method does the Business operations  with the HashMap and put the results back into
	 * the HashMap.
	 *
	 * @param map is of type HashMap
	 * @return HashMap with the Result
	 */
	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
		HashMap returnMap = new HashMap();
		HashMap resultMap = new HashMap();
		HashMap exceptionMap = new HashMap();
		try{
		OBTrxContext ctx = (OBTrxContext) map.get("theOBTrxContext");
		ILeiDateValidationTrxValue trxValueIn = (OBLeiDateValidationTrxValue) map.get("ILeiDateValidationTrxValue");
		OBLeiDateValidation leiDateValidation = (OBLeiDateValidation) map.get("leiDateValidationObj");
		String event = (String) map.get("event");
		String remarks = (String) map.get("remarks");
		ILeiDateValidationTrxValue trxValueOut = new OBLeiDateValidationTrxValue();

		boolean isPartyIDUnique = false;
		
		if( event.equals("maker_confirm_resubmit_delete") ){
			String partyID=leiDateValidation.getPartyID();
			String partyIDActual=trxValueIn.getStagingLeiDateValidation().getPartyID();
			if(! partyID.equals(partyIDActual)) {
				isPartyIDUnique = getLeiDateValidationProxy().isPartyIDUnique(partyID);				
				if(isPartyIDUnique != false){
					exceptionMap.put("partyIDError", new ActionMessage("error.string.exist","This Party ID "));
					returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
					returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
					return returnMap;
				}
			}
			ctx.setRemarks(remarks);
			trxValueOut = getLeiDateValidationProxy().makerEditRejectedLeiDateValidation(ctx, trxValueIn,leiDateValidation);
		}
		
		resultMap.put("request.ITrxValue", trxValueOut);
			
	} catch (LeiDateValidationException ex) {
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
