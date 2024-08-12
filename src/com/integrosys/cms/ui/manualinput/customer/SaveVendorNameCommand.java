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
import com.integrosys.cms.app.partygroup.proxy.IPartyGroupProxyManager;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;

public class SaveVendorNameCommand extends AbstractCommand{
	
private IPartyGroupProxyManager partyGroupProxy;
	
	public IPartyGroupProxyManager getPartyGroupProxy() {
		return partyGroupProxy;
	}

	public void setPartyGroupProxy(IPartyGroupProxyManager partyGroupProxy) {
		this.partyGroupProxy = partyGroupProxy;
	}
	public String[][] getParameterDescriptor() {
		return (new String[][] { 
				{ "trxID", "java.lang.String", REQUEST_SCOPE },				
				{ "theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE },
				{ "OBCMSCustomer", "com.integrosys.cms.app.customer.bus.ICMSCustomer", FORM_SCOPE },
				{ "vendorList", "java.util.List", SERVICE_SCOPE },
				{ "vendorId", "java.lang.String", REQUEST_SCOPE },
				{ "event", "java.lang.String", SERVICE_SCOPE }, 
				{ "event", "java.lang.String", REQUEST_SCOPE }, 
				{ "vendorName", "java.lang.String", REQUEST_SCOPE },
				{ "vendorName", "java.lang.String", SERVICE_SCOPE },
				{ "systemCustomerId", "java.lang.String", REQUEST_SCOPE },
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
				{ "index", "java.lang.String", REQUEST_SCOPE },
				{ "vendorList", "java.util.List", SERVICE_SCOPE },
				{ "vendorId", "java.lang.String", REQUEST_SCOPE },
				{ "event", "java.lang.String", SERVICE_SCOPE }, 
				{ "event", "java.lang.String", REQUEST_SCOPE }, 
				{ "vendorName", "java.lang.String", REQUEST_SCOPE },
				{ "vendorName", "java.lang.String", SERVICE_SCOPE },
				{ "systemCustomerId", "java.lang.String", REQUEST_SCOPE },
				{ "OBCMSCustomer", "com.integrosys.cms.app.customer.bus.ICMSCustomer", FORM_SCOPE },
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
		String index = (String) map.get("index");
		
		DefaultLogger.debug(this, "Inside doExecute() SaveVendorNameCommand "+event);
		DefaultLogger.debug(this, "Inside doExecute() SaveVendorNameCommand "+index);

		List vendor = null;
		ICMSCustomer obCustomer = (OBCMSCustomer)map.get("OBCMSCustomer");
		List list = (List)map.get("vendorList");
		 HashMap exceptionMap = new HashMap();
		String vendorName = (String)map.get("vendorName");
		String VenCustomerId = obCustomer.getCifId();
		String profileId = Long.toString(obCustomer.getCustomerID());
		DefaultLogger.debug(this, "CustomerId from Map for event cif id "+VenCustomerId);
		DefaultLogger.debug(this, "CustomerId from Map for event customer name "+obCustomer.getCustomerName());
		DefaultLogger.debug(this, "profileId from LEID for event edit "+profileId);
		DefaultLogger.debug(this, "Size of list for event edit "+list.size());

		if(list==null)
		{
		list = new ArrayList();
		}
		boolean flag = false;
		ICustomerDAO customerDAO = CustomerDAOFactory.getDAO();
	    List vNames = customerDAO.getvendorList(VenCustomerId);

		if(list!=null && list.size()!=0)
		{	
			for(int i = 0;i<list.size();i++)
			{
			OBVendor ven = (OBVendor)list.get(i);
				if((ven.getVendorName().equals(vendorName)) && ((Integer.parseInt(index)-1)==i )){
				flag = true;
				}
			}
		}
		
		if(!flag)
		{
			if(vNames!=null && vNames.size()!=0)
			{	
				for(int i = 0;i<vNames.size();i++)
				{
				OBVendor ven = (OBVendor)vNames.get(i);
						if(ven.getVendorName().equals(vendorName))
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
				OBVendor ven = (OBVendor)list.get(i);
				if(ven.getVendorName().equals(vendorName))
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
		
		OBVendor value = new OBVendor();
		if(event.equals("save_edited_vendor_in_edit") || event.equals("save_edited_vendor_in_resubmit") ){	
			value =(OBVendor) list.get((Integer.parseInt(index))-1);
			value.setVendorName(vendorName);
		}
		else{	
			value.setVendorName(vendorName);
		}
		list.remove((Integer.parseInt(index))-1);
		list.add((Integer.parseInt(index))-1, value);
		resultMap.put("index",index);
		resultMap.put("event",event);
		resultMap.put("vendorList", list);
		resultMap.put("OBCMSCustomer", obCustomer);
		
		
		DefaultLogger.debug(this, " -------- Create Successfull -----------");
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		return returnMap;		
	}
}
