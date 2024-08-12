package com.integrosys.cms.ui.manualinput.customer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.struts.action.ActionMessage;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.customer.bus.CustomerDAOFactory;
import com.integrosys.cms.app.customer.bus.ICMSCustomer;
import com.integrosys.cms.app.customer.bus.IContact;
import com.integrosys.cms.app.customer.bus.ICustomerDAO;
import com.integrosys.cms.app.customer.bus.IVendor;
import com.integrosys.cms.app.customer.bus.OBCMSCustomer;
import com.integrosys.cms.app.customer.bus.OBContact;
import com.integrosys.cms.app.customer.bus.OBDirector;
import com.integrosys.cms.app.customer.bus.OBSystem;
import com.integrosys.cms.app.customer.bus.OBVendor;
import com.integrosys.cms.app.customer.trx.ICMSCustomerTrxValue;
import com.integrosys.cms.app.partygroup.bus.PartyGroupException;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;

public class AddPrepareVendorCommand extends AbstractCommand{

	public String[][] getParameterDescriptor() {
		return (new String[][] { 
				{ "vendorList", "java.util.List", SERVICE_SCOPE },
				{ "vendorId", "java.lang.String", REQUEST_SCOPE },
				{ "vendorName", "java.lang.String", REQUEST_SCOPE },
				{ "vendorName", "java.lang.String", SERVICE_SCOPE },
				{ "trxID", "java.lang.String", REQUEST_SCOPE },				
				{ "theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE },
				{ "OBCMSCustomer", "com.integrosys.cms.app.customer.bus.ICMSCustomer", FORM_SCOPE },
				{ "event", "java.lang.String", REQUEST_SCOPE },
				{ "index", "java.lang.String", REQUEST_SCOPE },
				{"cifId","java.lang.String", REQUEST_SCOPE},
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
				{ "customerOb", "com.integrosys.cms.app.customer.bus.OBCMSCustomer", REQUEST_SCOPE },
				{ "vendorList", "java.util.List", SERVICE_SCOPE },
				{ "vendorId", "java.lang.String", REQUEST_SCOPE },
				{ "vendorName", "java.lang.String", SERVICE_SCOPE },
				{ "vendorName", "java.lang.String", REQUEST_SCOPE },
				{ "event", "java.lang.String", SERVICE_SCOPE },
				{ "OBCMSCustomer", "com.integrosys.cms.app.customer.bus.ICMSCustomer", FORM_SCOPE },
				{ "request.ITrxValue", "com.integrosys.cms.app.transaction.ICMSTrxValue", REQUEST_SCOPE},
				{com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY, "java.util.Locale", GLOBAL_SCOPE},
				{"cifId","java.lang.String", SERVICE_SCOPE},
				{ "index", "java.lang.String", REQUEST_SCOPE }
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
		String index = (String) map.get("index");
		String cifId=(String) map.get("cifId");
		DefaultLogger.debug(this, "Inside doExecute() AddpreparevendorCommand "+event);
		DefaultLogger.debug(this, "Inside doExecute() AddpreparevendorCommand "+index);

		List list = (List)map.get("vendorList");
		
		ICMSCustomer obCustomer = (OBCMSCustomer)map.get("OBCMSCustomer");
		resultMap.put("OBCMSCustomer", obCustomer);
		resultMap.put("cifId", cifId);
		resultMap.put("event",event);
		resultMap.put("index", index);
		resultMap.put("vendorList", list);
		DefaultLogger.debug(this, " -------- Create Successfull -----------");
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		return returnMap;		
	}
	
	
}