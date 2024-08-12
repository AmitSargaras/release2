package com.integrosys.cms.ui.manualinput.customer;

import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.customer.bus.CustomerException;
import com.integrosys.cms.app.customer.proxy.CustomerProxyFactory;
import com.integrosys.cms.app.customer.trx.ICMSCustomerTrxValue;
import com.integrosys.cms.app.customer.trx.OBCMSCustomerTrxValue;
import com.integrosys.cms.app.transaction.OBTrxContext;

	/**
	 * Class created by
	 * @author sandiip.shinde
	 * @since 05-04-2011
	 *
	 */

public class CheckerRejectDeleteCustomerCommand extends AbstractCommand{
	
	/**
	 * Default Constructor
	 */
	public CheckerRejectDeleteCustomerCommand() {
	}

	/**
	 * Defines an two dimensional array with the parameter list to be passed to
	 * the doExecute method by a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ "ICMSCustomerTrxValue", "com.integrosys.cms.app.customer.trx.ICMSCustomerTrxValue", SERVICE_SCOPE },
				{ "theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE },
				 {"remarks", "java.lang.String", REQUEST_SCOPE},		
		
		});
	}

	/**
	 * Defines an two dimensional array with the result list to be expected as a
	 * result from the doExecute method using a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getResultDescriptor() {
		return (new String[][] { { "request.ITrxResult", "com.integrosys.component.common.transaction.ICompTrxResult",
				REQUEST_SCOPE } });
	}

	/**
	 * This method does the Business operations with the HashMap and put the
	 * results back into the HashMap.Here creation for Company Borrower is done.
	 * 
	 * @param map is of type HashMap
	 * @return HashMap with the Result
	 */
	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
		HashMap returnMap = new HashMap();
        HashMap resultMap = new HashMap();
        try {
            OBTrxContext ctx = (OBTrxContext) map.get("theOBTrxContext");
            ICMSCustomerTrxValue trxValueIn = (OBCMSCustomerTrxValue) map.get("ICMSCustomerTrxValue");

            String remarks = (String) map.get("remarks");
            ctx.setRemarks(remarks);

            ICMSCustomerTrxValue trxValueOut = CustomerProxyFactory.getProxy().checkerRejectDeleteCustomer(ctx, trxValueIn);
            resultMap.put("request.ITrxValue", trxValueOut);
        }catch (CustomerException ex) {
        	 DefaultLogger.debug(this, "got exception in doExecute" + ex);
	            ex.printStackTrace();
	            throw (new CommandProcessingException(ex.getMessage()));
		}catch (Exception e) {
			CommandProcessingException cpe = new CommandProcessingException("Internal Error While Processing ");
			cpe.initCause(e);
			throw cpe;
		}

        returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
        return returnMap;
	}
}
