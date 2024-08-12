
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
import com.integrosys.cms.app.creditApproval.proxy.ICreditApprovalProxy;
import com.integrosys.cms.app.creditApproval.trx.ICreditApprovalTrxValue;
import com.integrosys.cms.app.transaction.ITrxContext;

/**
 * @author $Govind: Sahu $
 * @version $Revision: 1.7 $
 * @since $Date: 2011/04/05 04:51:48 $ Tag: $Name: $
 */
public class RejectCreditApprovalCommand extends AbstractCommand implements ICommonEventConstant {
	private ICreditApprovalProxy creditApprovalProxy;

	public String[][] getParameterDescriptor() {
		return new String[][] {
				{ "creditApprovalTrxValue", "com.integrosys.cms.app.creditApproval.trx.ICreditApprovalTrxValue", SERVICE_SCOPE},
				{ "theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE },
				{ "remarks", "java.lang.String",REQUEST_SCOPE },
				{ "TrxId", "java.lang.String", FORM_SCOPE}
				
		};
	}

	public String[][] getResultDescriptor() {
		return (new String[][] { {"request.ITrxValue", "com.integrosys.cms.app.transaction.ICMSTrxValue", REQUEST_SCOPE},
				 {"TrxId", "java.lang.String", REQUEST_SCOPE}
		});
	}

	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
		HashMap resultMap = new HashMap();
		HashMap returnMap = new HashMap();
		HashMap exceptionMap = new HashMap();
		
		String remarks = (String) map.get("remarks");
		String trxId = (String) (map.get("TrxId"));

		try {
			if(remarks == null || remarks.equals("")){
            	exceptionMap.put("remarksError", new ActionMessage("error.reject.remark"));
            	ICreditApprovalTrxValue trxValue = null;
            	resultMap.put("TrxId", trxId);
            	resultMap.put("creditApprovalTrxValue", trxValue);
            	resultMap.put("request.ITrxValue", trxValue);
				returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
				returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
				return returnMap;
            }
			else{

			ICreditApprovalTrxValue trxValue = (ICreditApprovalTrxValue) map.get("creditApprovalTrxValue");
			ITrxContext trxContext = (ITrxContext) map.get("theOBTrxContext");
			trxValue = getCreditApprovalProxy().checkerRejectCreditApproval(trxContext, trxValue);
			resultMap.put("request.ITrxValue", trxValue);
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

}
