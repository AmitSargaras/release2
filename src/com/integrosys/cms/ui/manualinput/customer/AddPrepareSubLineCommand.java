package com.integrosys.cms.ui.manualinput.customer;

import java.util.HashMap;
import java.util.List;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.customer.bus.ICMSCustomer;
import com.integrosys.cms.app.customer.bus.OBCMSCustomer;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;


	/**
	 * Class created by
	 * @author sandiip.shinde
	 * @since 17-03-2011
	 *
	 */

public class AddPrepareSubLineCommand extends AbstractCommand{

	/**
	 * Defines an two dimensional array with the parameter list to be passed to
	 * the doExecute method by a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */

	public String[][] getParameterDescriptor() {
		return (new String[][] { 
				{ "trxID", "java.lang.String", REQUEST_SCOPE },	
				{ "event", "java.lang.String", REQUEST_SCOPE },
				//{ "systemList", "java.util.List", SERVICE_SCOPE },
				{ "theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE },
				{ "OBCMSCustomer", "com.integrosys.cms.app.customer.bus.ICMSCustomer", FORM_SCOPE },
				{ "event", "java.lang.String", REQUEST_SCOPE }, 
				
				{ IGlobalConstant.GLOBAL_CUSTOMER_OBJ, "com.integrosys.cms.app.customer.bus.ICMSCustomer", GLOBAL_SCOPE }
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

		return (new String[][] {	
				/*{ "customerOb", "com.integrosys.cms.app.customer.bus.OBCMSCustomer", REQUEST_SCOPE },*/
				{ "OBCMSCustomer", "com.integrosys.cms.app.customer.bus.ICMSCustomer", FORM_SCOPE },
				{ "event", "java.lang.String", REQUEST_SCOPE },
				{ "event", "java.lang.String", SERVICE_SCOPE },
				//{ "systemList", "java.util.List", SERVICE_SCOPE },
				{ "request.ITrxValue", "com.integrosys.cms.app.transaction.ICMSTrxValue", REQUEST_SCOPE},
				{com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY, "java.util.Locale", GLOBAL_SCOPE}
			});
	}

	/**
	 * This method does the Business operations with the HashMap and put the
	 * results back into the HashMap.Here get data from database for Interest
	 * Rate is done.
	 * 
	 * @param map is of type HashMap
	 * @return HashMap with the Result
	 */
	
	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
		HashMap resultMap = new HashMap();
		HashMap returnMap = new HashMap();
		String event = (String) map.get("event");
		
		DefaultLogger.debug(this, "Inside doExecute() ManualInputCreateCustomerCommand "+event);
	//	List list = (List)map.get("systemList");
		
		ICMSCustomer obCustomer = (OBCMSCustomer)map.get("OBCMSCustomer");
		resultMap.put("OBCMSCustomer", obCustomer);
		resultMap.put("event", event);
		
	//	resultMap.put("systemList", list);
		/*String source = (String) map.get("legalSource");
		ICMSCustomer obCustomer = (OBCMSCustomer)map.get("OBCMSCustomer");
		OBTrxContext ctx = (OBTrxContext) map.get("theOBTrxContext");
		ICMSCustomerTrxValue trxValue = new OBCMSCustomerTrxValue();		
		List system = obCustomer.getCMSLegalEntity().getOtherSystem();
		
		
		trxValue.setTrxContext(ctx);		
		trxValue.setStagingCustomer(obCustomer);
		trxValue.setLegalName(obCustomer.getLegalEntity().getLegalName());
		trxValue.setCustomerName(obCustomer.getCustomerName());
		trxValue.setLimitProfileReferenceNumber(obCustomer.getCifId());
		DefaultLogger.debug(this, " -------- Before Create : "+obCustomer);
		try {
			ICMSCustomerTrxValue trx = CustomerProxyFactory.getProxy().createCustomer(trxValue);
			resultMap.put("customerOb", trx);
			resultMap.put("request.ITrxValue", trx);
		} catch (CustomerException e) {
			CommandProcessingException cpe = new CommandProcessingException(e.getMessage());
			cpe.initCause(e);
			throw cpe;
		} catch (Exception e) {
			CommandProcessingException cpe = new CommandProcessingException("Internal Error While Processing ");
			cpe.initCause(e);
			throw cpe;
		}*/
		DefaultLogger.debug(this, " -------- Create Successfull -----------");
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		return returnMap;		
	}
}
