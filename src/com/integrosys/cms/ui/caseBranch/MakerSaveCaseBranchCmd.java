/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:
 */

package com.integrosys.cms.ui.caseBranch;

import java.util.HashMap;

import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.caseBranch.bus.OBCaseBranch;
import com.integrosys.cms.app.caseBranch.bus.CaseBranchException;
import com.integrosys.cms.app.caseBranch.proxy.ICaseBranchProxyManager;
import com.integrosys.cms.app.caseBranch.trx.ICaseBranchTrxValue;
import com.integrosys.cms.app.caseBranch.trx.OBCaseBranchTrxValue;
import com.integrosys.cms.app.transaction.OBTrxContext;

/**
@author $Author: Abhijit R$
* Command for Save CaseBranch
 */
public class MakerSaveCaseBranchCmd extends AbstractCommand implements ICommonEventConstant {
	
	
	private ICaseBranchProxyManager caseBranchProxy;

	public ICaseBranchProxyManager getCaseBranchProxy() {
		return caseBranchProxy;
	}

	public void setCaseBranchProxy(ICaseBranchProxyManager caseBranchProxy) {
		this.caseBranchProxy = caseBranchProxy;
	}
	
	
	/**
	 * Default Constructor
	 */
	
	
	public MakerSaveCaseBranchCmd() {
	}

	/**
	 * Defines an two dimensional array with the parameter list to be passed to
	 * the doExecute method by a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	 public String[][] getParameterDescriptor() {
	        return (new String[][]{
	        		{"ICaseBranchTrxValue", "com.integrosys.cms.app.caseBranch.trx.ICaseBranchTrxValue", SERVICE_SCOPE},
	                {"theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE},
	                {"remarks", "java.lang.String", REQUEST_SCOPE},
	                {"event", "java.lang.String", REQUEST_SCOPE},
	        		{ "caseBranchObj", "com.integrosys.cms.app.caseBranch.bus.OBCaseBranch", FORM_SCOPE }
	               
	        }
	        );
	    }

	 public String[][] getResultDescriptor() {
			return (new String[][] { 
					{"request.ITrxValue", "com.integrosys.cms.app.transaction.ICMSTrxValue", REQUEST_SCOPE}
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
			try {
				String event = (String) map.get("event");
				OBCaseBranch caseBranch = (OBCaseBranch) map.get("caseBranchObj");
				OBTrxContext ctx = (OBTrxContext) map.get("theOBTrxContext");
				ICaseBranchTrxValue trxValueOut = new OBCaseBranchTrxValue();
				if(event.equals("maker_update_draft_caseBranch")){
					ICaseBranchTrxValue trxValueIn = (OBCaseBranchTrxValue) map.get("ICaseBranchTrxValue");
					trxValueOut = getCaseBranchProxy().makerUpdateSaveUpdateCaseBranch(ctx, trxValueIn, caseBranch);
					
					resultMap.put("request.ITrxValue", trxValueOut);
				}else{
				
				trxValueOut = getCaseBranchProxy().makerSaveCaseBranch(ctx,caseBranch);
					
					resultMap.put("request.ITrxValue", trxValueOut);
				}
			}catch (CaseBranchException ex) {
				DefaultLogger.debug(this, "got exception in doExecute" + ex);
				ex.printStackTrace();
				throw (new CommandProcessingException(ex.getMessage()));
			}
			catch (TransactionException e) {
				DefaultLogger.debug(this, "got exception in doExecute" + e);
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
