package com.integrosys.cms.ui.otherbankbranch;

import java.util.HashMap;

import org.apache.struts.action.ActionMessage;

import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.otherbank.trx.IOtherBankTrxValue;
import com.integrosys.cms.app.otherbranch.bus.OBOtherBranch;
import com.integrosys.cms.app.otherbranch.bus.OtherBranchException;
import com.integrosys.cms.app.otherbranch.proxy.IOtherBranchProxyManager;
import com.integrosys.cms.app.otherbranch.trx.IOtherBankBranchTrxValue;
import com.integrosys.cms.app.otherbranch.trx.OBOtherBankBranchTrxValue;
import com.integrosys.cms.app.transaction.OBTrxContext;

/**
 * This command edits other bank selected for editing
 *  $Author: Dattatray Thorat 
 * @version $Revision: 1.2 $
 * @since $Date: 2005/09/30 11:32:23 $ Tag: $Name: $
 */
public class EditOtherBranchCommand extends AbstractCommand implements ICommonEventConstant {

	/**
	 * Default Constructor
	 */
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

	public EditOtherBranchCommand() {
	}
	
public String[][] getParameterDescriptor() {
		
		return (new String[][]{
				{"BranchId", "java.lang.String", REQUEST_SCOPE},
				{"IOtherBankBranchTrxValue", "com.integrosys.cms.app.otherbranch.trx.IOtherBankBranchTrxValue", SERVICE_SCOPE},
	            {"OtherBranchObj","com.integrosys.cms.app.otherbranch.bus.OBOtherBranch",FORM_SCOPE},
	            {"theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE},
	            {"event", "java.lang.String", REQUEST_SCOPE},
                {"remarks", "java.lang.String", REQUEST_SCOPE}
		});
	}
	
	public String[][] getResultDescriptor() {
	
		return (new String[][]{
	            {com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY, "java.util.Locale", GLOBAL_SCOPE},
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
	        HashMap exceptionMap = new HashMap();
	        try {
	        	OBOtherBranch otherBranch = (OBOtherBranch) map.get("OtherBranchObj");
				String event = (String) map.get("event");
				OBTrxContext ctx = (OBTrxContext) map.get("theOBTrxContext");
				String id = (String) map.get("BranchId");
        		IOtherBankBranchTrxValue trxValueIn = null;
        		trxValueIn = getOtherBranchProxyManager().getOtherBranchTrxValue(Long.parseLong(id));
				//IOtherBankBranchTrxValue trxValueIn = (OBOtherBankBranchTrxValue) map.get("IOtherBankBranchTrxValue");
				IOtherBankBranchTrxValue trxValueOut = new OBOtherBankBranchTrxValue();
				
				boolean isOtherBankBrachNameUnique = false;
				boolean isRbiCodeUnique = false;
				String bankCode = otherBranch.getOtherBankCode().getOtherBankCode();
				
				String newOtherBankBrachName = otherBranch.getOtherBranchName();
				String oldOtherBankBrachName = "";
				
				String newRbiCode = String.valueOf(otherBranch.getRbiCode());
				String oldRbiCode = "";
				
				if(trxValueIn.getFromState().equals(ICMSConstant.STATE_PENDING_PERFECTION)){
					
					oldOtherBankBrachName = trxValueIn.getStagingOtherBranch().getOtherBranchName();
					
					if( ! newOtherBankBrachName.equalsIgnoreCase(oldOtherBankBrachName) )
		        		isOtherBankBrachNameUnique = getOtherBranchProxyManager().isUniqueBranchName(newOtherBankBrachName,bankCode);
		        							
					if( isOtherBankBrachNameUnique ){
						exceptionMap.put("otherBrachNameError", new ActionMessage("error.string.exist","Other Bank Brach Name"));
						IOtherBankTrxValue otherBankTrxValue = null;
						resultMap.put("request.ITrxValue", otherBankTrxValue);
						returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
						returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
						return returnMap;
					}
					
					trxValueOut = getOtherBranchProxyManager().makerUpdateCreateOtherBankBranch(ctx, trxValueIn, otherBranch);
				}else{
					if (event.equals("maker_submit_edit_branch") || event.equals("maker_confirm_resubmit_update")) {
						
						if( event.equals("maker_submit_edit_branch") )
							oldOtherBankBrachName = trxValueIn.getOtherBranch().getOtherBranchName();
						else
							oldOtherBankBrachName = trxValueIn.getStagingOtherBranch().getOtherBranchName();
						
						if( ! newOtherBankBrachName.equalsIgnoreCase(oldOtherBankBrachName) )
			        		isOtherBankBrachNameUnique = getOtherBranchProxyManager().isUniqueBranchName(newOtherBankBrachName,bankCode);
			        							
						if( isOtherBankBrachNameUnique ){
							exceptionMap.put("otherBrachNameError", new ActionMessage("error.string.exist","Other Bank Brach Name"));
							IOtherBankTrxValue otherBankTrxValue = null;
							resultMap.put("request.ITrxValue", otherBankTrxValue);
							returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
							returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
							return returnMap;
						}
						
						trxValueOut = getOtherBranchProxyManager().makerUpdateOtherBranch(ctx, trxValueIn, otherBranch);
					} 
					else if (event.equals("maker_save_update_other_bank_branch")){
						
						oldOtherBankBrachName = trxValueIn.getOtherBranch().getOtherBranchName();
						
						if( ! newOtherBankBrachName.equalsIgnoreCase(oldOtherBankBrachName) )
			        		isOtherBankBrachNameUnique = getOtherBranchProxyManager().isUniqueBranchName(newOtherBankBrachName,bankCode);
			        							
						if( isOtherBankBrachNameUnique ){
							exceptionMap.put("otherBrachNameError", new ActionMessage("error.string.exist","Other Bank Brach Name"));
							IOtherBankTrxValue otherBankTrxValue = null;
							resultMap.put("request.ITrxValue", otherBankTrxValue);
							returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
							returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
							return returnMap;
						}
						
						trxValueOut = getOtherBranchProxyManager().makerUpdateSaveOtherBankBranch(ctx, trxValueIn, otherBranch);
					} else {
						// event is  maker_confirm_resubmit_edit

						if( trxValueIn.getFromState().equalsIgnoreCase("PENDING_CREATE") ){
							oldOtherBankBrachName = trxValueIn.getStagingOtherBranch().getOtherBranchName();
							oldRbiCode = String.valueOf(trxValueIn.getStagingOtherBranch().getRbiCode());
						}
						else{
							oldOtherBankBrachName = trxValueIn.getOtherBranch().getOtherBranchName();
							oldRbiCode = String.valueOf(trxValueIn.getOtherBranch().getRbiCode());
						}
						
						if( ! newOtherBankBrachName.equals(oldOtherBankBrachName) )
							isOtherBankBrachNameUnique = getOtherBranchProxyManager().isUniqueBranchName(newOtherBankBrachName.trim(),bankCode);

						if(  newRbiCode != null && ! newRbiCode.equals("0") ){
							if( ! newRbiCode.equals(oldRbiCode) )
							isRbiCodeUnique = getOtherBranchProxyManager().isUniqueRbiCode(newRbiCode);
						}
						
						if( isOtherBankBrachNameUnique ){
							exceptionMap.put("otherBrachNameError", new ActionMessage("error.string.exist","Other Bank Branch Name"));
						}
						
						if( isRbiCodeUnique ){
							exceptionMap.put("branchRbiCodeError", new ActionMessage("error.string.exist","RBI Code"));
						}
						
						if( isRbiCodeUnique || isOtherBankBrachNameUnique ){							
							returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
							returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
							return returnMap;
						}
						
						String remarks = (String) map.get("remarks");
						ctx.setRemarks(remarks);
						trxValueOut = getOtherBranchProxyManager().makerEditRejectedOtherBranch(ctx, trxValueIn, otherBranch);
					} 
				}
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