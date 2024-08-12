package com.integrosys.cms.ui.otherbankbranch;

import java.util.HashMap;

import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.insurancecoverage.bus.InsuranceCoverageException;
import com.integrosys.cms.app.otherbank.bus.OBOtherBank;
import com.integrosys.cms.app.otherbank.proxy.IOtherBankProxyManager;
import com.integrosys.cms.app.otherbank.trx.IOtherBankTrxValue;
import com.integrosys.cms.app.otherbank.trx.OBOtherBankTrxValue;
import com.integrosys.cms.app.transaction.OBTrxContext;

/**
 * This command Edits the Other Bank Details selected for edition 
 * 
 * $Author: Dattatray Thorat
 * 
 * @version $Revision: 1.2 $
 */
public class MakerSubmitDraftOtherBankCommand extends AbstractCommand implements ICommonEventConstant {


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

	/**
	 * Default Constructor
	 */

	public MakerSubmitDraftOtherBankCommand() {
	}
	
	public String[][] getParameterDescriptor() {
		
		return (new String[][]{
				{ "OtherBankObj", "com.integrosys.cms.app.otherbank.bus.OBOtherBank", FORM_SCOPE },
				{"IOtherBankTrxValue", "com.integrosys.cms.app.otherbank.trx.IOtherBankTrxValue", SERVICE_SCOPE},
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
	        	OBOtherBank otherBank = (OBOtherBank) map.get("OtherBankObj");
				String event = (String) map.get("event");
				OBTrxContext ctx = (OBTrxContext) map.get("theOBTrxContext");
				IOtherBankTrxValue trxValueIn = (OBOtherBankTrxValue) map.get("IOtherBankTrxValue");

				IOtherBankTrxValue trxValueOut = new OBOtherBankTrxValue();

					if(event.equals("maker_confirm_resubmit_draft")){
						trxValueOut = getOtherBankProxyManager().makerUpdateCreateOtherBank(ctx, trxValueIn, otherBank);
					}else {
						// event is  maker_confirm_resubmit_edit
						String remarks = (String) map.get("remarks");
						ctx.setRemarks(remarks);
						trxValueOut = getOtherBankProxyManager().makerEditRejectedOtherBank(ctx, trxValueIn, otherBank);
					} 

					resultMap.put("request.ITrxValue", trxValueOut);
	        	
	        } catch (InsuranceCoverageException obe) {
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



