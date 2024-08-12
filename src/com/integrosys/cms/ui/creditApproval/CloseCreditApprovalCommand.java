
package com.integrosys.cms.ui.creditApproval;

import java.util.HashMap;

import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.creditApproval.bus.CreditApprovalException;
import com.integrosys.cms.app.creditApproval.proxy.ICreditApprovalProxy;
import com.integrosys.cms.app.creditApproval.trx.ICreditApprovalTrxValue;
import com.integrosys.cms.app.creditApproval.trx.OBCreditApprovalTrxValue;
import com.integrosys.cms.app.transaction.ITrxContext;

/**
 * @author $govind: sahu $
 * @version $Revision: 1.8 $
 * @since $Date: 2011/04/08 06:39:12 $ Tag: $Name: $
 */
public class CloseCreditApprovalCommand extends AbstractCommand implements ICommonEventConstant {
	
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

	public String[][] getParameterDescriptor() {
		return new String[][] {
        		{"creditApprovalTrxValue", "com.integrosys.cms.app.creditApproval.trx.ICreditApprovalTrxValue", SERVICE_SCOPE},
                {"theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE},
                {"event", "java.lang.String", REQUEST_SCOPE}
		};
	}

	public String[][] getResultDescriptor() {
		return new String[][] { {"request.ITrxValue", "com.integrosys.cms.app.transaction.ICMSTrxValue", REQUEST_SCOPE}
		};
	}

	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {


		HashMap resultMap = new HashMap();
		HashMap returnMap = new HashMap();

		try {

			ICreditApprovalTrxValue trxValueIn = (OBCreditApprovalTrxValue) map.get("creditApprovalTrxValue");

			ITrxContext trxContext = (ITrxContext) map.get("theOBTrxContext");
			String event = (String) map.get("event");

			 if(event.equals("maker_confirm_draft_close")){
				 ICreditApprovalTrxValue trxValueOut = getCreditApprovalProxy().makerCloseDraftCreditApproval(trxContext, trxValueIn);
	            	 resultMap.put("request.ITrxValue", trxValueOut);
	          }
			 else
			 {
			    trxValueIn = getCreditApprovalProxy().makerCloseRejectedCreditApproval(trxContext, trxValueIn);
			    resultMap.put("request.ITrxValue", trxValueIn);
			 }

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
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);

		return returnMap;
	}
}
