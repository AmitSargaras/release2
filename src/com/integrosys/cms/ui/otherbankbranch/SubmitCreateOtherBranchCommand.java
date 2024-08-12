package com.integrosys.cms.ui.otherbankbranch;

import java.util.HashMap;

import org.apache.struts.action.ActionMessage;

import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.otherbank.trx.IOtherBankTrxValue;
import com.integrosys.cms.app.otherbranch.bus.OBOtherBranch;
import com.integrosys.cms.app.otherbranch.bus.OtherBranchException;
import com.integrosys.cms.app.otherbranch.proxy.IOtherBranchProxyManager;
import com.integrosys.cms.app.otherbranch.trx.IOtherBankBranchTrxValue;
import com.integrosys.cms.app.otherbranch.trx.OBOtherBankBranchTrxValue;
import com.integrosys.cms.app.transaction.OBTrxContext;

/**
 * This command edits other bank branch selected for editing
 *  $Author: Dattatray Thorat 
 * @version $Revision: 1.2 $
 */
public class SubmitCreateOtherBranchCommand extends AbstractCommand implements ICommonEventConstant {

	/**
	 * Default Constructor
	 */
	public SubmitCreateOtherBranchCommand() {
	}
	
	private IOtherBranchProxyManager otherBranchProxyManager;

	/**
	 * @return the otherBranchProxyManager
	 */
	public IOtherBranchProxyManager getOtherBranchProxyManager() {
		return otherBranchProxyManager;
	}

	/**
	 * @param otherBranchProxyManager the otherBranchProxyManager to set
	 */
	public void setOtherBranchProxyManager(IOtherBranchProxyManager otherBranchProxyManager) {
		this.otherBranchProxyManager = otherBranchProxyManager;
	}
	
	public String[][] getParameterDescriptor() {
		
		return (new String[][]{
				{"otherBankId","java.lang.String",REQUEST_SCOPE},
				{"otherBankCode","java.lang.String",REQUEST_SCOPE},
				{"otherBankName","java.lang.String",REQUEST_SCOPE},
				{"IOtherBankBranchTrxValue", "com.integrosys.cms.app.otherbranch.trx.IOtherBankBranchTrxValue", SERVICE_SCOPE},
				{"OtherBranchObj", "com.integrosys.cms.app.otherbranch.bus.OBOtherBranch", FORM_SCOPE},
	            {"theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE},
	            {"event", "java.lang.String", REQUEST_SCOPE},
                {"remarks", "java.lang.String", REQUEST_SCOPE}
			});
	    }
	
	public String[][] getResultDescriptor() {
	
		return (new String[][]{
	            {com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY, "java.util.Locale", GLOBAL_SCOPE},
	            {"request.ITrxValue", "com.integrosys.cms.app.transaction.ICMSTrxValue", REQUEST_SCOPE},
	            {"otherBankId","java.lang.String",REQUEST_SCOPE},
				{"otherBankCode","java.lang.String",REQUEST_SCOPE},
				{"otherBankName","java.lang.String",REQUEST_SCOPE}
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
	        try {
	        	
	        	String bankId = (String) map.get("otherBankId");
	        	String bankCode = (String) map.get("otherBankCode");
	        	String bankName = (String) map.get("otherBankName");
	        	
	        	OBOtherBranch otherBranch = (OBOtherBranch) map.get("OtherBranchObj");
				
	        	boolean isOtherBankNameUnique = false;
	        	boolean isRbiCodeUnique = false;
	        	
	        	String branchName = otherBranch.getOtherBranchName();
	        	String rbiCode = String.valueOf(otherBranch.getRbiCode());
	        	
	        	if(  rbiCode != null && ! rbiCode.equals("0") )
	        		isRbiCodeUnique = getOtherBranchProxyManager().isUniqueRbiCode(rbiCode);

				if( isRbiCodeUnique ){
					exceptionMap.put("branchRbiCodeError", new ActionMessage("error.string.exist","RBI Code"));
				}
				
	        	isOtherBankNameUnique = getOtherBranchProxyManager().isUniqueBranchName(branchName,bankCode);
	        	
				if( isOtherBankNameUnique ){
					exceptionMap.put("otherBrachNameError", new ActionMessage("error.string.exist","Other Bank Branch Name"));
				}
				
				if( isRbiCodeUnique || isOtherBankNameUnique ){
					IOtherBankTrxValue otherBankTrxValue = null;
					resultMap.put("request.ITrxValue", otherBankTrxValue);
					returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
					returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
					return returnMap;
				}
				
				String event = (String) map.get("event");
				OBTrxContext ctx = (OBTrxContext) map.get("theOBTrxContext");
				IOtherBankBranchTrxValue trxValueIn = (OBOtherBankBranchTrxValue) map.get("IOtherBankBranchTrxValue");

				IOtherBankBranchTrxValue trxValueOut = new OBOtherBankBranchTrxValue();

					if (event.equals("maker_submit_create_other_bank_branch")) {
						trxValueOut = getOtherBranchProxyManager().makerCreateOtherBranch(ctx, trxValueOut, otherBranch);
					} else {
						// event is  maker_confirm_resubmit_edit
						String remarks = (String) map.get("remarks");
						ctx.setRemarks(remarks);
						trxValueOut = getOtherBranchProxyManager().makerEditRejectedOtherBranch(ctx, trxValueIn, otherBranch);
					} 
					resultMap.put("otherBankId", bankId);
		        	resultMap.put("otherBankCode", bankCode);
		        	resultMap.put("otherBankName", bankName);
					resultMap.put("request.ITrxValue", trxValueOut);
	        } catch (OtherBranchException obe) {
	        	CommandProcessingException cpe = new CommandProcessingException(obe.getMessage());
				cpe.initCause(obe);
				throw cpe;
			} catch (Exception e) {
				CommandProcessingException cpe = new CommandProcessingException("Internal Error While Processing ");
				cpe.initCause(e);
				throw cpe;
			}

	        returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
	        return returnMap;
	    }
	}