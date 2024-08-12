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
import com.integrosys.cms.app.customer.bus.ICustomerDAO;
import com.integrosys.cms.app.customer.bus.OBCMSCustomer;
import com.integrosys.cms.app.customer.bus.OBVendor;
import com.integrosys.cms.app.customer.trx.ICMSCustomerTrxValue;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;

public class AddNewVendorCommand extends AbstractCommand{

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
				{ "index", "java.lang.String", REQUEST_SCOPE },
				{ "OBCMSCustomer", "com.integrosys.cms.app.customer.bus.ICMSCustomer", FORM_SCOPE },
				{ "request.ITrxValue", "com.integrosys.cms.app.transaction.ICMSTrxValue", REQUEST_SCOPE},
				{com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY, "java.util.Locale", GLOBAL_SCOPE}
			});
	}
	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
		HashMap resultMap = new HashMap();
		HashMap returnMap = new HashMap();
		String event = (String) map.get("event");
		String index = (String) map.get("index");
		DefaultLogger.debug(this, "Inside doExecute() AddNewVendorCommand "+event);
		DefaultLogger.debug(this, "Inside doExecute() AddNewVendorCommand "+index);
		List vendor = null;
		ICMSCustomer obCustomer = (OBCMSCustomer)map.get("OBCMSCustomer");
		List list = (List)map.get("vendorList");
		 HashMap exceptionMap = new HashMap();
		String vendorName = (String)map.get("vendorName");
		String CustomerId = (String)map.get("systemCustomerId");
		String profileId = Long.toString(obCustomer.getCustomerID());
		
		DefaultLogger.debug(this, "CustomerId from Map"+CustomerId);
		DefaultLogger.debug(this, "profileId from LEID "+profileId);
		if(list==null)
		{
		list = new ArrayList();
		}
		ICustomerDAO customerDAO = CustomerDAOFactory.getDAO();
	    List vNames = customerDAO.getvendorList(profileId);
		if(vNames!=null && vNames.size()!=0)
		{	
		for(int i = 0;i<vNames.size();i++)
		{
			OBVendor sys = (OBVendor)vNames.get(i);
			if(sys.getVendorName().equals(vendorName))
					{
				exceptionMap.put("bankingMethodEmptyError", new ActionMessage("error.string.duplicatesvendor.name"));
				ICMSCustomerTrxValue partyGroupTrxValue = null;
				resultMap.put("request.ITrxValue", partyGroupTrxValue);
				returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
				returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
				return returnMap;
					}
		}
		}
		for(int i = 0;i<list.size();i++)
		{
			OBVendor sys = (OBVendor)list.get(i);
			if(sys.getVendorName().equals(vendorName))
					{
				exceptionMap.put("bankingMethodEmptyError", new ActionMessage("error.string.duplicatesvendor.name"));
				ICMSCustomerTrxValue partyGroupTrxValue = null;
				resultMap.put("request.ITrxValue", partyGroupTrxValue);
				returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
				returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
				return returnMap;
					}
		}
		
		
		OBVendor value = new OBVendor();
		value.setVendorName(vendorName);
		
		list.add(value);
		resultMap.put("vendorList", list);
		resultMap.put("event", event);
		resultMap.put("index",index);
		resultMap.put("OBCMSCustomer", obCustomer);
		
		
		DefaultLogger.debug(this, " -------- Create Successfull -----------");
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		return returnMap;		
	}
}
