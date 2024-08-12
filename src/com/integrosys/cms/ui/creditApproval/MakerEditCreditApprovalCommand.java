/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:
 */

package com.integrosys.cms.ui.creditApproval;

import java.util.HashMap;

import org.apache.struts.action.ActionMessage;

import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.creditApproval.bus.CreditApprovalException;
import com.integrosys.cms.app.creditApproval.bus.OBCreditApproval;
import com.integrosys.cms.app.creditApproval.proxy.ICreditApprovalProxy;
import com.integrosys.cms.app.creditApproval.trx.ICreditApprovalTrxValue;
import com.integrosys.cms.app.creditApproval.trx.OBCreditApprovalTrxValue;
import com.integrosys.cms.app.transaction.OBTrxContext;

/**
 *@author govind.sahu $
 *Command for maker to edit Credit Approval trx value
 */
public class MakerEditCreditApprovalCommand extends AbstractCommand implements ICommonEventConstant {
	private ICreditApprovalProxy creditApprovalProxy;

	/**
	 * @return the creditApprovalProxy
	 */
	public ICreditApprovalProxy getCreditApprovalProxy() {
		return creditApprovalProxy;
	}

	/**
	 * @param creditApprovalProxy the creditApprovalProxy to set
	 */
	public void setCreditApprovalProxy(
			ICreditApprovalProxy creditApprovalProxy) {
		this.creditApprovalProxy = creditApprovalProxy;
	}


	/**
	 * Default Constructor
	 */
	public MakerEditCreditApprovalCommand() {
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
				  {"creditApprovalTrxValue", "com.integrosys.cms.app.creditApproval.trx.ICreditApprovalTrxValue", SERVICE_SCOPE},
				  {"theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE},
				  {"remarks", "java.lang.String", REQUEST_SCOPE},
				  {"event", "java.lang.String", REQUEST_SCOPE},
				  {"oBCreditApproval", "com.integrosys.cms.app.creditApproval.bus.OBCreditApproval", FORM_SCOPE },
				  {"TrxId", "java.lang.String", REQUEST_SCOPE}		 
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
				{"request.ITrxValue", "com.integrosys.cms.app.transaction.ICMSTrxValue", REQUEST_SCOPE},
				  { CreditApprovalForm.MAPPER, "com.integrosys.cms.app.creditApproval.bus.ICreditApproval", FORM_SCOPE }
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
		HashMap exceptionMap = new HashMap();
		HashMap resultMap = new HashMap();
		try {
			OBCreditApproval creditApproval = (OBCreditApproval) map.get("oBCreditApproval");
			String event = (String) map.get("event");
			OBTrxContext ctx = (OBTrxContext) map.get("theOBTrxContext");
			ICreditApprovalTrxValue trxValueIn = (OBCreditApprovalTrxValue) map.get("creditApprovalTrxValue");

			ICreditApprovalTrxValue trxValueOut = new OBCreditApprovalTrxValue();
			String creditApprovalName = "";
			boolean validateApprovalName = false;

			if(event.equals("maker_save_create")){
				/*boolean valiDateAppCode = (boolean)getCreditApprovalProxy().getCheckCreditApprovalUniquecode(creditApproval.getApprovalCode());
	    		if(valiDateAppCode)
	    		{
	    			ICreditApprovalTrxValue creditApprovalTrxValue = null;
	    			resultMap.put("creditApprovalTrxValue", creditApprovalTrxValue);
			        exceptionMap.put("approvalCode", new ActionMessage("error.string.appcode.exist"));
					returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
					returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
					return returnMap;
	    		}*/
	    		creditApprovalName = creditApproval.getApprovalName();
				validateApprovalName = getCreditApprovalProxy().isCreditApprovalNameUnique(creditApprovalName);
	    		if( validateApprovalName ){
	    			ICreditApprovalTrxValue creditApprovalTrxValue = null;
	    			resultMap.put("creditApprovalTrxValue", creditApprovalTrxValue);
			        exceptionMap.put("approvalName",  new ActionMessage("error.string.exist","CreditApprover Name"));
					returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
					returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
					return returnMap;
	    		}
	    		
	    		String employeeId = creditApproval.getEmployeeId();
				boolean validateEmpId = getCreditApprovalProxy().isCreditEmployeeIdUnique(employeeId);
				creditApproval.setCpsId(creditApproval.getEmployeeId());
					if(validateEmpId){
	    			ICreditApprovalTrxValue creditApprovalTrxValue = null;
	    			resultMap.put("creditApprovalTrxValue", creditApprovalTrxValue);
			        exceptionMap.put("employeeIdDuplicateError",  new ActionMessage("error.string.exist","Employee Id"));
					returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
					returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
					return returnMap;
	    		}
				trxValueOut = getCreditApprovalProxy().makerUpdateSaveCreateCreditApproval(ctx, trxValueIn, creditApproval);
			}
			else if (event.equals("maker_confirm_resubmit_edit")||event.equals("maker_save_update")) {
				
				String newEmployeeId = creditApproval.getEmployeeId();
				String oldEmployeeId = trxValueIn.getStagingCreditApproval().getEmployeeId();
				boolean validateEmpId = false;
				creditApproval.setCpsId(creditApproval.getEmployeeId());
				//		if(null != oldEmployeeId && !oldEmployeeId.equals(newEmployeeId))
				System.out.println("oldEmployeeId : "+oldEmployeeId+", newEmployeeId : "+newEmployeeId);
				if(null !=  oldEmployeeId && (!oldEmployeeId.equalsIgnoreCase(newEmployeeId)))
				
				validateEmpId = getCreditApprovalProxy().isCreditEmployeeIdUnique(newEmployeeId);
				
				else if(null == oldEmployeeId && null != newEmployeeId)
					
					validateEmpId = getCreditApprovalProxy().isCreditEmployeeIdUnique(newEmployeeId);

					if(validateEmpId){
	    			ICreditApprovalTrxValue creditApprovalTrxValue = null;
	    			resultMap.put("creditApprovalTrxValue", creditApprovalTrxValue);
			        exceptionMap.put("employeeIdDuplicateError",  new ActionMessage("error.string.exist","Employee Id"));
			        returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
					returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
					return returnMap;
	    		}
					trxValueOut = getCreditApprovalProxy().makerUpdateCreditApproval(ctx, trxValueIn, creditApproval);
			}
			else if(event.equals(CreditApprovalAction.CONFIRM_MAKER_SUBMIT_REMOVE)) {
				    creditApproval.setDeprecated("Y");
					creditApproval.setStatus("INACTIVE");
					trxValueOut = getCreditApprovalProxy().makerDeleteCreditApproval(ctx, trxValueIn, creditApproval);
			}
			else {
					// event is  maker_confirm_resubmit_edit
				if (event.equals("maker_confirm_resubmit_edit_rejected") && trxValueIn.getStatus().equalsIgnoreCase("REJECTED") && trxValueIn.getFromState().equalsIgnoreCase("PENDING_CREATE")) {
					creditApprovalName = creditApproval.getApprovalName();
					validateApprovalName = getCreditApprovalProxy().isCreditApprovalNameUnique(creditApprovalName);
		    		if( validateApprovalName ){
		    			ICreditApprovalTrxValue creditApprovalTrxValue = null;
		    			resultMap.put("creditApprovalTrxValue", creditApprovalTrxValue);
				        exceptionMap.put("approvalName",  new ActionMessage("error.string.exist","CreditApprover Name"));
						returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
						returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
						return returnMap;
		    		}
		    		
		    		String employeeId = creditApproval.getEmployeeId();
					boolean validateEmpId = getCreditApprovalProxy().isCreditEmployeeIdUnique(employeeId);
					creditApproval.setCpsId(creditApproval.getEmployeeId());
						if(validateEmpId){
		    			ICreditApprovalTrxValue creditApprovalTrxValue = null;
		    			resultMap.put("creditApprovalTrxValue", creditApprovalTrxValue);
				        exceptionMap.put("employeeIdDuplicateError",  new ActionMessage("error.string.exist","Employee Id"));
						returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
						returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
						return returnMap;
		    		}
				}
				String remarks = (String) map.get("remarks");
				ctx.setRemarks(remarks);
				trxValueOut = getCreditApprovalProxy().makerEditRejectedCreditApproval(ctx, trxValueIn, creditApproval);
			}
				resultMap.put("request.ITrxValue", trxValueOut);

		}
		catch (CreditApprovalException ex) {
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
