
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
import com.integrosys.cms.app.creditApproval.bus.ICreditApproval;
import com.integrosys.cms.app.creditApproval.proxy.ICreditApprovalProxy;
import com.integrosys.cms.app.creditApproval.trx.ICreditApprovalTrxValue;
import com.integrosys.cms.app.creditApproval.trx.OBCreditApprovalTrxValue;
import com.integrosys.cms.app.transaction.OBTrxContext;

/**
@author $Govind: Sahu R$
* Command for Credit Approval
 */
public class MakerSaveCreditApprovalCommand extends AbstractCommand implements ICommonEventConstant {
	
	
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
	public void setCreditApprovalProxy(ICreditApprovalProxy creditApprovalProxy) {
		this.creditApprovalProxy = creditApprovalProxy;
	}

	/**
	 * Default Constructor
	 */
	
	
	public MakerSaveCreditApprovalCommand() {
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
	        		{ "creditApprovalTrxValue", "com.integrosys.cms.app.creditApproval.trx.ICreditApprovalTrxValue", SERVICE_SCOPE },
	                {"theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE},
	                {"remarks", "java.lang.String", REQUEST_SCOPE},
	                {"event", "java.lang.String", REQUEST_SCOPE},
	                { "oBCreditApproval", "com.integrosys.cms.app.creditApproval.bus.OBCreditApproval", FORM_SCOPE }
	               
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
			HashMap exceptionMap = new HashMap();
			HashMap resultMap = new HashMap();
			try {
				String event = (String) map.get("event");
				ICreditApproval creditApproval = (ICreditApproval) map.get("oBCreditApproval");
				OBTrxContext ctx = (OBTrxContext) map.get("theOBTrxContext");
				ICreditApprovalTrxValue trxValueOut= new OBCreditApprovalTrxValue()  ;
				String creditApprovalName = "";	
				String employeeId = "";

    			if(event.equals("maker_update_draft_creditApproval")){
					ICreditApprovalTrxValue trxValueIn = (OBCreditApprovalTrxValue) map.get("creditApprovalTrxValue");
					trxValueOut = getCreditApprovalProxy().makerUpdateSaveUpdateCreditApproval(ctx, trxValueIn, creditApproval);				
					resultMap.put("request.ITrxValue", trxValueOut);
				}else{	// maker add - save
					creditApprovalName = creditApproval.getApprovalName();
					boolean validateApprovalName = getCreditApprovalProxy().isCreditApprovalNameUnique(creditApprovalName);
					employeeId = creditApproval.getEmployeeId();
					boolean validateEmpId = getCreditApprovalProxy().isCreditEmployeeIdUnique(employeeId);
		    		if(validateApprovalName){
		    			ICreditApprovalTrxValue creditApprovalTrxValue = null;
		    			resultMap.put("creditApprovalTrxValue", creditApprovalTrxValue);
				        exceptionMap.put("approvalName",  new ActionMessage("error.string.exist","CreditApprover Name"));
						returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
						returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
						return returnMap;
		    		}
		    		 if(validateEmpId){
		    			ICreditApprovalTrxValue creditApprovalTrxValue = null;
		    			resultMap.put("creditApprovalTrxValue", creditApprovalTrxValue);
				        exceptionMap.put("employeeIdDuplicateError",  new ActionMessage("error.string.exist","Employee Id"));
				        returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
						returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
						
						return returnMap;
		    		}
				trxValueOut = getCreditApprovalProxy().makerSaveCreditApproval(ctx,creditApproval);				
					resultMap.put("request.ITrxValue", trxValueOut);
				}	    			
	    	
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
			return returnMap;
	    }
}
