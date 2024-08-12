package com.integrosys.cms.ui.creditApproval;

import java.util.HashMap;

import org.apache.struts.action.ActionMessage;

import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.creditApproval.bus.CreditApprovalException;
import com.integrosys.cms.app.creditApproval.bus.ICreditApproval;
import com.integrosys.cms.app.creditApproval.trx.ICreditApprovalTrxValue;
import com.integrosys.cms.app.transaction.OBTrxContext;

/**
 * This class implements command
 */
public class MakerSubmitCreditApprovalCommand extends CreditApprovalCommand {

	public String[][] getParameterDescriptor() {
		return new String[][] {
				{ CreditApprovalForm.MAPPER, "com.integrosys.cms.app.creditApproval.bus.ICreditApproval", FORM_SCOPE },
				{ "creditApprovalTrxValue", "com.integrosys.cms.app.creditApproval.trx.ICreditApprovalTrxValue", SERVICE_SCOPE },
				{ "theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE }
				
		};
	}

	
	public String[][] getResultDescriptor() {
		return (new String[][] { {"request.ITrxValue", "com.integrosys.cms.app.transaction.ICMSTrxValue", REQUEST_SCOPE} });
	}
	/**
	 * This method does the Business operations with the HashMap and put the
	 * results back into the HashMap.Here reading for Company Borrower is done.
	 * 
	 * @param map is of type HashMap
	 * @throws com.integrosys.base.uiinfra.exception.CommandProcessingException
	 *         on errors
	 * @throws com.integrosys.base.uiinfra.exception.CommandValidationException
	 *         on errors
	 * @return HashMap with the Result
	 */
	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
		DefaultLogger.debug(this, "Map is " + map);

		HashMap resultMap = new HashMap();
		HashMap exceptionMap = new HashMap();
		HashMap returnMap = new HashMap();

		try {
			ICreditApprovalTrxValue value = null;
			OBTrxContext ctx = (OBTrxContext) map.get("theOBTrxContext");
			ICreditApproval creditApproval = (ICreditApproval) map.get(CreditApprovalForm.MAPPER);
			
			String creditApprovalName = creditApproval.getApprovalName();
			boolean validateApprovalName = getCreditApprovalProxy().isCreditApprovalNameUnique(creditApprovalName);
    		if(validateApprovalName)		    		{
    			ICreditApprovalTrxValue creditApprovalTrxValue = null;
    			resultMap.put("creditApprovalTrxValue", creditApprovalTrxValue);
		        exceptionMap.put("approvalName",  new ActionMessage("error.string.exist","CreditApprover Name"));
				returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
				returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
				return returnMap;
    		}
    		
    		String employeeId = creditApproval.getEmployeeId();
    		creditApproval.setCpsId(employeeId);
			boolean validateEmpId = getCreditApprovalProxy().isCreditEmployeeIdUnique(employeeId);

				if(validateEmpId){
    			ICreditApprovalTrxValue creditApprovalTrxValue = null;
    			resultMap.put("creditApprovalTrxValue", creditApprovalTrxValue);
		        exceptionMap.put("employeeIdDuplicateError",  new ActionMessage("error.string.exist","Employee Id"));
				returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
				returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
				return returnMap;
    		}
			value = getCreditApprovalProxy().makerSubmitCreditApproval(ctx,value,creditApproval);
			resultMap.put("creditApprovalTrxValue", value);
			resultMap.put("request.ITrxValue", value);
		}catch (CreditApprovalException ex) {
			DefaultLogger.debug(this, "got exception in doExecute" + ex);
			ex.printStackTrace();
			throw (new CommandProcessingException(ex.getMessage()));
		}
		catch (TransactionException e) {
			e.printStackTrace();
			DefaultLogger.debug(this, "got exception in doExecute" + e);
			throw (new CommandProcessingException(e.getMessage()));
		}catch (Exception e) {
            DefaultLogger.debug(this, "got exception in doExecute" + e);
            e.printStackTrace();
            throw (new CommandProcessingException(e.getMessage()));
        }
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		
		return returnMap;
	}
}