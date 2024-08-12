/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:
 */

package com.integrosys.cms.ui.collateralNewMaster;

import java.util.HashMap;

import org.apache.struts.action.ActionMessage;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.collateralNewMaster.bus.CollateralNewMasterException;
import com.integrosys.cms.app.collateralNewMaster.proxy.ICollateralNewMasterProxyManager;
import com.integrosys.cms.app.collateralNewMaster.trx.ICollateralNewMasterTrxValue;
import com.integrosys.cms.app.collateralNewMaster.trx.OBCollateralNewMasterTrxValue;
import com.integrosys.cms.app.systemBankBranch.trx.ISystemBankBranchTrxValue;
import com.integrosys.cms.app.transaction.OBTrxContext;

/**
 * 
 * @author $Author: Abhijit R $<br>
 * Command for checker to reject update by maker.
 * 
 */
public class CheckerRejectEditCollateralNewMasterCmd extends AbstractCommand implements ICommonEventConstant {
	
	
	private ICollateralNewMasterProxyManager collateralNewMasterProxy;

	
	
	public ICollateralNewMasterProxyManager getCollateralNewMasterProxy() {
		return collateralNewMasterProxy;
	}

	public void setCollateralNewMasterProxy(
			ICollateralNewMasterProxyManager collateralNewMasterProxy) {
		this.collateralNewMasterProxy = collateralNewMasterProxy;
	}

	/**
	 * Default Constructor
	 */
	public CheckerRejectEditCollateralNewMasterCmd() {
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
				{ "ICollateralNewMasterTrxValue", "com.integrosys.cms.app.collateralNewMaster.trx.ICollateralNewMasterTrxValue", SERVICE_SCOPE },
				{ "theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE },
				 {"remarks", "java.lang.String", REQUEST_SCOPE},
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
				{ "request.ITrxValue", "com.integrosys.component.common.transaction.ICompTrxResult",
				REQUEST_SCOPE }
				
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
	            OBTrxContext ctx = (OBTrxContext) map.get("theOBTrxContext");
	            ICollateralNewMasterTrxValue trxValueIn = (OBCollateralNewMasterTrxValue) map.get("ICollateralNewMasterTrxValue");
	            String event = (String) map.get("event");
	            String remarks = (String) map.get("remarks");
	            ctx.setRemarks(remarks);
	            if(remarks == null||remarks.trim().equals("")){
					exceptionMap.put("collateralRemarksError", new ActionMessage("error.reject.remark"));
					ISystemBankBranchTrxValue trxValueInValidate = null;
					
					resultMap.put("trxId", trxValueIn.getTransactionID());
					returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
					returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
					return returnMap;
				}else{
					try{
				
	            ICollateralNewMasterTrxValue trxValueOut = getCollateralNewMasterProxy().checkerRejectCollateralNewMaster(ctx, trxValueIn);
	            resultMap.put("request.ITrxValue", trxValueOut);
	            
	        }catch (CollateralNewMasterException ex) {
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
}
