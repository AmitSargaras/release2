package com.integrosys.cms.ui.otherbankbranch;

import java.util.HashMap;

import org.apache.struts.action.ActionMessage;

import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.otherbank.bus.OBOtherBank;
import com.integrosys.cms.app.otherbank.bus.OtherBankException;
import com.integrosys.cms.app.otherbank.proxy.IOtherBankProxyManager;
import com.integrosys.cms.app.otherbank.trx.IOtherBankTrxValue;
import com.integrosys.cms.app.otherbank.trx.OBOtherBankTrxValue;
import com.integrosys.cms.app.otherbranch.bus.OBOtherBranch;
import com.integrosys.cms.app.otherbranch.trx.IOtherBankBranchTrxValue;
import com.integrosys.cms.app.transaction.OBTrxContext;

/**
 * This command Edits the Other Bank selected for edition 
 * 
 * $Author: Dattatray Thorat
 * 
 * @version $Revision: 1.2 $
 * @since $Date: 2011/03/25 11:32:23 $ Tag: $Name: $
 */
public class SubmitCreateOtherBankCommand extends AbstractCommand implements ICommonEventConstant {

	/**
	 * Default Constructor
	 */
	private IOtherBankProxyManager otherBankProxyManager;

	
	/**
	 * @return the otherBankProxyManager
	 */
	public IOtherBankProxyManager getOtherBankProxyManager() {
		return otherBankProxyManager;
	}

	/**
	 * @param otherBankProxyManager the otherBankProxyManager to set
	 */
	public void setOtherBankProxyManager(
			IOtherBankProxyManager otherBankProxyManager) {
		this.otherBankProxyManager = otherBankProxyManager;
	}

	public SubmitCreateOtherBankCommand() {
	}
	
	public String[][] getParameterDescriptor() {
		
		return (new String[][]{
				{"IOtherBankTrxValue", "com.integrosys.cms.app.otherbank.trx.IOtherBankTrxValue", SERVICE_SCOPE},
	            {"OtherBankObj","com.integrosys.cms.app.otherbank.bus.OBOtherBank",FORM_SCOPE},
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
				String event = (String) map.get("event");
				OBTrxContext ctx = (OBTrxContext) map.get("theOBTrxContext");
				IOtherBankTrxValue trxValueIn = (OBOtherBankTrxValue) map.get("IOtherBankTrxValue");
				
				boolean isOtherBankCodeUnique = false;
				OBOtherBank otherBank = (OBOtherBank) map.get("OtherBankObj");
				
				if(otherBank.getOtherBankCode()!=null)
	        		isOtherBankCodeUnique = getOtherBankProxyManager().isUniqueCode(otherBank.getOtherBankCode().trim());
	        	
	        	boolean isOtherBankNameUnique = getOtherBankProxyManager().isUniqueName(otherBank.getOtherBankName());
	        	
				if( isOtherBankCodeUnique ){
					exceptionMap.put("otherBankCodeError", new ActionMessage("error.string.exist","Other Bank Code"));
				}
				if( isOtherBankNameUnique ){
					exceptionMap.put("otherBankNameError", new ActionMessage("error.string.exist","Other Bank Name"));
				}
				
				if( isOtherBankCodeUnique || isOtherBankNameUnique ){
					IOtherBankTrxValue otherBankTrxValue = null;
					resultMap.put("request.ITrxValue", otherBankTrxValue);
					returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
					returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
					return returnMap;
				}
				
				IOtherBankTrxValue trxValueOut = new OBOtherBankTrxValue();

					if (event.equals("maker_submit_create_other_bank")) {
						trxValueOut = getOtherBankProxyManager().makerCreateOtherBank(ctx, trxValueOut, otherBank);
					} else {
						// event is  maker_confirm_resubmit_edit
						String remarks = (String) map.get("remarks");
						ctx.setRemarks(remarks);
						trxValueOut = getOtherBankProxyManager().makerEditRejectedOtherBank(ctx, trxValueIn, otherBank);
					} 

					resultMap.put("request.ITrxValue", trxValueOut);
	        } catch (OtherBankException obe) {
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