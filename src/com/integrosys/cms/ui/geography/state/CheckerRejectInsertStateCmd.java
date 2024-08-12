/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:
 */

package com.integrosys.cms.ui.geography.state;

import java.util.HashMap;

import org.apache.struts.action.ActionMessage;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.geography.bus.NoSuchGeographyException;
import com.integrosys.cms.app.geography.state.proxy.IStateProxyManager;
import com.integrosys.cms.app.geography.state.trx.IStateTrxValue;
import com.integrosys.cms.app.geography.state.trx.OBStateTrxValue;
import com.integrosys.cms.app.transaction.OBTrxContext;

/**
 * 
 * @author $Author: Dattatray Thorat $<br>
 * Command for checker to reject update by maker.
 * 
 */
public class CheckerRejectInsertStateCmd extends AbstractCommand implements ICommonEventConstant {
	
	private IStateProxyManager stateProxy;
	
	/**
	 * @return the stateProxy
	 */
	public IStateProxyManager getStateProxy() {
		return stateProxy;
	}

	/**
	 * @param stateProxy the stateProxy to set
	 */
	public void setStateProxy(IStateProxyManager stateProxy) {
		this.stateProxy = stateProxy;
	}

	/**
	 * Default Constructor
	 */
	public CheckerRejectInsertStateCmd() {
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
				{"IStateTrxValue", "com.integrosys.cms.app.geography.state.trx.IStateTrxValue", SERVICE_SCOPE},
				{ "theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE },
				 {"remarks", "java.lang.String", REQUEST_SCOPE},
				 {"event", "java.lang.String", REQUEST_SCOPE},
				 { "TrxId", "java.lang.String", REQUEST_SCOPE }
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
				{ "request.ITrxValue", "com.integrosys.component.common.transaction.ICompTrxResult",
				REQUEST_SCOPE },
				{ "TrxId", "java.lang.String", REQUEST_SCOPE }
				
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
	        HashMap exceptionMap = new HashMap();
	        try {
	            OBTrxContext ctx = (OBTrxContext) map.get("theOBTrxContext");
	            IStateTrxValue trxValueIn = (OBStateTrxValue) map.get("IStateTrxValue");
	            String event = (String) map.get("event");
	            String remarks = (String) map.get("remarks");
	            String transId=(String) (map.get("TrxId"));
	            if(remarks == null || remarks.equals("")){
	            	exceptionMap.put("remarksError", new ActionMessage("error.reject.remark"));
	            	IStateTrxValue trxValue = null;
	            	resultMap.put("TrxId", transId);
	            	resultMap.put("IStateTrxValue", trxValueIn);
	            	resultMap.put("request.ITrxValue", trxValue);
					returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
					returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
					return returnMap;
	            }else{
		            ctx.setRemarks(remarks);
		            IStateTrxValue trxValueOut = getStateProxy().checkerRejectInsertState(ctx, trxValueIn);
		            resultMap.put("request.ITrxValue", trxValueOut);
	            }
	            
	        }catch (NoSuchGeographyException ex) {
	        	 DefaultLogger.debug(this, "got exception in doExecute" + ex);
		            ex.printStackTrace();
		            throw (new CommandProcessingException(ex.getMessage()));
			}
	        catch (Exception e) {
	            DefaultLogger.debug(this, "got exception in doExecute" + e);
	            e.printStackTrace();
	            throw (new CommandProcessingException(e.getMessage()));
	        }

	        returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
	        return returnMap;
	}

}
