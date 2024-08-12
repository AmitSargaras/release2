package com.integrosys.cms.ui.otherbankbranch;

import java.util.HashMap;

import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.otherbank.bus.OtherBankException;
import com.integrosys.cms.app.otherbranch.bus.OBOtherBranch;
import com.integrosys.cms.app.otherbranch.proxy.IOtherBranchProxyManager;
import com.integrosys.cms.app.otherbranch.trx.IOtherBankBranchTrxValue;
import com.integrosys.cms.app.otherbranch.trx.OBOtherBankBranchTrxValue;
import com.integrosys.cms.app.transaction.OBTrxContext;

/**
 * Purpose : This command delete the Other Bank branch selected for deletion 
 *
 * @author $Author: Dattatray Thorat $
 * @version $Revision: 1.0 $
 * @since $Date: 2011-02-18 15:13:16 +0800 (Fri, 18 Feb 2011) $
 * Tag : $Name$
 */

public class DeleteOtherBranchCommand extends AbstractCommand implements ICommonEventConstant {

	/**
	 * Default Constructor
	 */
	private IOtherBranchProxyManager otherBranchProxyManager;

	public IOtherBranchProxyManager getOtherBranchProxyManager() {
		return otherBranchProxyManager;
	}

	public void setOtherBranchProxyManager(
			IOtherBranchProxyManager otherBranchProxyManager) {
		this.otherBranchProxyManager = otherBranchProxyManager;
	}

	public DeleteOtherBranchCommand() {
	}
	
public String[][] getParameterDescriptor() {
		
		return (new String[][]{
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
				IOtherBankBranchTrxValue trxValueIn = (OBOtherBankBranchTrxValue) map.get("IOtherBankBranchTrxValue");

				IOtherBankBranchTrxValue trxValueOut = new OBOtherBankBranchTrxValue();

					if (event.equals("maker_submit_remove_branch")) {
						trxValueOut = getOtherBranchProxyManager().makerDeleteOtherBranch(ctx, trxValueIn, otherBranch);
					} else {
						// event is  maker_confirm_resubmit_edit
						String remarks = (String) map.get("remarks");
						ctx.setRemarks(remarks);
						trxValueOut = getOtherBranchProxyManager().makerEditRejectedOtherBranch(ctx, trxValueIn, otherBranch);
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



