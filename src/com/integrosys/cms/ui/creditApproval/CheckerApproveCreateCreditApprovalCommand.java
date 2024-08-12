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
 * Title: CLIMS
 * Description: for Checker to approve the new created Credit
 * Author: Govind.Sahu
 * Date: Apr 6, 2011
 */

public class CheckerApproveCreateCreditApprovalCommand extends AbstractCommand implements ICommonEventConstant {
	
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
    public CheckerApproveCreateCreditApprovalCommand() {
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
        		{"creditApprovalTrxValue", "com.integrosys.cms.app.creditApproval.trx.ICreditApprovalTrxValue", SERVICE_SCOPE},
                {"theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE},
                {"remarks", "java.lang.String", REQUEST_SCOPE},
                {"event", "java.lang.String", REQUEST_SCOPE}
        }
        );
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
                {"request.ITrxValue", "com.integrosys.cms.app.transaction.ICMSTrxValue", REQUEST_SCOPE}
        }
        );
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
        HashMap exceptionMap = new HashMap();
        HashMap resultMap = new HashMap();
        
       String event = (String) map.get("event");
       DefaultLogger.debug(this, "Inside doExecute()");
        try {
            OBTrxContext ctx = (OBTrxContext) map.get("theOBTrxContext");
            ICreditApprovalTrxValue trxValueIn = (OBCreditApprovalTrxValue) map.get("creditApprovalTrxValue");
          
            String remarks = (String) map.get("remarks");
            ctx.setRemarks(remarks);
            ICreditApprovalTrxValue trxValueOut= null;
            
            if(null != trxValueIn.getOperationDescField() && !trxValueIn.getOperationDescField().equals("DELETE_CREDIT_APPROVAL") && !trxValueIn.getOperationDescField().equals("EDIT_REJECTED_DELETE_CREDIT_APPROVAL")){
            
            	if(event.equals(CreditApprovalAction.CHECKER_CONFIRM_APPROVE_UPDATE)){
            	ICreditApprovalTrxValue trxValue=null;
                trxValue = (ICreditApprovalTrxValue) getCreditApprovalProxy().getCreditApprovalTrxValue(Long.parseLong(trxValueIn.getReferenceID()));
            	String oldEmployeeId = trxValue.getCreditApproval().getEmployeeId();
				String newEmployeeId = trxValueIn.getStagingCreditApproval().getEmployeeId();
				//System.out.println("oldEmployeeId:"+oldEmployeeId+" newEmployeeId:"+newEmployeeId);
				
	            if(null!=oldEmployeeId && (!oldEmployeeId.equalsIgnoreCase(newEmployeeId))){
           
			boolean validateEmpId = getCreditApprovalProxy().isCreditEmployeeIdUnique(newEmployeeId);

				if(validateEmpId){
    			ICreditApprovalTrxValue creditApprovalTrxValue = null;
    			resultMap.put("creditApprovalTrxValue", creditApprovalTrxValue);
		        exceptionMap.put("employeeIdDuplicateError",  new ActionMessage("error.string.exist","Employee Id"));
				returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
				returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
				return returnMap;
				}
				
            	}
            		}
            }
	        if(event.equals("checker_confirm_approve_create"))
	        {
	        	String creditApprovalName = trxValueIn.getStagingCreditApproval().getApprovalName();
				boolean validateApprovalName = getCreditApprovalProxy().isCreditApprovalNameUnique(creditApprovalName);
	    		if(validateApprovalName){
	    			ICreditApprovalTrxValue creditApprovalTrxValue = null;
	    			resultMap.put("creditApprovalTrxValue", creditApprovalTrxValue);
			        exceptionMap.put("approvalName",  new ActionMessage("error.string.exist","CreditApprover Name"));
					returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
					returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
					return returnMap;
	    		}
	    		
	    		String employeeId = trxValueIn.getStagingCreditApproval().getEmployeeId();
	    		boolean validateEmpId = getCreditApprovalProxy().isCreditEmployeeIdUnique(employeeId);

				if(validateEmpId){
    			ICreditApprovalTrxValue creditApprovalTrxValue = null;
    			resultMap.put("creditApprovalTrxValue", creditApprovalTrxValue);
		        exceptionMap.put("employeeIdDuplicateError",  new ActionMessage("error.string.exist","Employee Id"));
				returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
				returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
				return returnMap;
				}
	    		
	            trxValueOut = getCreditApprovalProxy().checkerApproveCreateCreditApproval(ctx, trxValueIn);
	        }
	        else if(event.equals(CreditApprovalAction.CHECKER_CONFIRM_APPROVE_UPDATE))
	        {
	        	  trxValueOut = getCreditApprovalProxy().checkerApproveUpdateCreditApproval(ctx, trxValueIn);
	        }
        
	        resultMap.put("request.ITrxValue", trxValueOut);
	        DefaultLogger.debug(this, "ITrxValue after approve " + trxValueOut);
        }
		catch (CreditApprovalException ex) {
       	 DefaultLogger.debug(this, "got exception in doExecute" + ex);
         ex.printStackTrace();
         throw (new CommandProcessingException(ex.getMessage()));
		}
	    catch (TransactionException e) {
            DefaultLogger.debug(this, "got exception in doExecute" + e);
            e.printStackTrace();
            throw (new CommandProcessingException(e.getMessage()));
	    }
	    catch (Exception e) {
            DefaultLogger.debug(this, "got exception in doExecute" + e);
            e.printStackTrace();
            throw (new CommandProcessingException(e.getMessage()));
        }
        DefaultLogger.debug(this, "Going out of doExecute()");
        returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);

        return returnMap;
    }	
}