package com.integrosys.cms.ui.manualinput.customer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.struts.action.ActionMessage;
import org.apache.struts.util.LabelValueBean;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.customer.bus.CustomerDAOFactory;
import com.integrosys.cms.app.customer.bus.CustomerException;
import com.integrosys.cms.app.customer.bus.ICMSCustomer;
import com.integrosys.cms.app.customer.bus.IContact;
import com.integrosys.cms.app.customer.bus.ICustomerDAO;
import com.integrosys.cms.app.customer.bus.ISystem;
import com.integrosys.cms.app.customer.bus.OBCMSCustomer;
import com.integrosys.cms.app.customer.bus.OBContact;
import com.integrosys.cms.app.customer.bus.OBSystem;
import com.integrosys.cms.app.customer.proxy.CustomerProxyFactory;
import com.integrosys.cms.app.customer.trx.ICMSCustomerTrxValue;
import com.integrosys.cms.app.customer.trx.OBCMSCustomerTrxValue;
import com.integrosys.cms.app.geography.city.bus.ICity;
import com.integrosys.cms.app.geography.city.proxy.ICityProxyManager;
import com.integrosys.cms.app.geography.region.bus.IRegion;
import com.integrosys.cms.app.geography.state.bus.IState;
import com.integrosys.cms.app.relationshipmgr.proxy.IRelationshipMgrProxyManager;
import com.integrosys.cms.app.transaction.OBTrxContext;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;
import com.integrosys.cms.ui.manualinput.CommonUtil;
import com.integrosys.cms.ui.relationshipmgr.IRelationshipMgr;


	/**
	 * Class created by
	 * @author sandiip.shinde
	 * @since 17-03-2011
	 *
	 */

public class SaveEditedSystemCommand extends AbstractCommand{

	/**
	 * Defines an two dimensional array with the parameter list to be passed to
	 * the doExecute method by a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	private ICityProxyManager cityProxy;

	private IRelationshipMgrProxyManager relationshipMgrProxyManager;
	
	
	
	public ICityProxyManager getCityProxy() {
		return cityProxy;
	}

	public void setCityProxy(ICityProxyManager cityProxy) {
		this.cityProxy = cityProxy;
	}

	public IRelationshipMgrProxyManager getRelationshipMgrProxyManager() {
		return relationshipMgrProxyManager;
	}

	public void setRelationshipMgrProxyManager(
			IRelationshipMgrProxyManager relationshipMgrProxyManager) {
		this.relationshipMgrProxyManager = relationshipMgrProxyManager;
	}

	public String[][] getParameterDescriptor() {
		return (new String[][] { 
				{ "systemList", "java.util.List", SERVICE_SCOPE },
				{ "trxID", "java.lang.String", REQUEST_SCOPE },				
				{ "theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE },
				{ "OBCMSCustomer", "com.integrosys.cms.app.customer.bus.ICMSCustomer", FORM_SCOPE },
				{ "event", "java.lang.String", SERVICE_SCOPE }, 
				{ "legalId", "java.lang.String", REQUEST_SCOPE },
				{ "system", "java.lang.String", REQUEST_SCOPE },
				{ "index", "java.lang.String", REQUEST_SCOPE },
				{ "systemCustomerId", "java.lang.String", REQUEST_SCOPE },
				{ "legalSource", "java.lang.String", REQUEST_SCOPE },
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
				{ "countryList", "java.util.List", SERVICE_SCOPE },
				{ "regionList", "java.util.List", SERVICE_SCOPE },
				{ "relationshipMgrList", "java.util.List", SERVICE_SCOPE },
				{ "cityList", "java.util.List", SERVICE_SCOPE },
				{ "stateList", "java.util.List", SERVICE_SCOPE },
				{ "event", "java.lang.String", REQUEST_SCOPE },
				{ "index", "java.lang.String", REQUEST_SCOPE },
				{ "systemList", "java.util.List", SERVICE_SCOPE },
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
		
		DefaultLogger.debug(this, "Inside doExecute() ManualInputCreateCustomerCommand "+event);

		List system = null;
		ICMSCustomer obCustomer = (OBCMSCustomer)map.get("OBCMSCustomer");
		/*IContact[] contact = obCustomer.getCMSLegalEntity()
		.getRegisteredAddress();
OBContact address = (OBContact) contact[0];
String cityId = address.getCity();
String countryId = address.getCountryCode();
String stateId = address.getState();
String regionId = obCustomer.getCMSLegalEntity().getRegion();
String rmRegion = obCustomer.getCMSLegalEntity().getRmRegion();

if (!(cityId.equals("") || cityId == null)) {
	resultMap.put("cityList", getCityList(Long.parseLong(stateId)));
}
if (!(stateId.equals("") || stateId == null)) {
	resultMap.put("stateList", getStateList(Long.parseLong(regionId)));
}
if (!(regionId.equals("") || regionId == null)) {
	resultMap
			.put("regionList", getRegionList(Long.parseLong(countryId)));
}if (!(rmRegion.equals("") || rmRegion == null)) {
	resultMap
	.put("relationshipMgrList", getRelationshipMgrList(rmRegion));
}*/
		List list = (List)map.get("systemList");
		 HashMap exceptionMap = new HashMap();
		String systemName = (String)map.get("system");
		String systemCustomerId = (String)map.get("systemCustomerId");
		if(list==null)
		{
		list = new ArrayList();
		}
		boolean flag = false;
		ICustomerDAO customerDAO = CustomerDAOFactory.getDAO();
	    List cust = customerDAO.getAllSystemAndSystemId();
		
		if(list!=null && list.size()!=0)
		{	
		for(int i = 0;i<list.size();i++)
		{
			OBSystem sys = (OBSystem)list.get(i);
			if((sys.getSystem().equals(systemName)) && (sys.getSystemCustomerId().equals(systemCustomerId)) && ((Integer.parseInt(index)-1)==i ))
					{
				flag = true;
				/*exceptionMap.put("bankingMethodEmptyError", new ActionMessage("error.string.duplicatesystem.id"));
				ICMSCustomerTrxValue partyGroupTrxValue = null;
				resultMap.put("request.ITrxValue", partyGroupTrxValue);
				//resultMap.put("event",event);
				resultMap.put("index",index);
				returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
				returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
				return returnMap;*/
					}
			
		}
		}
		
		if(!flag)
		{
			if(cust!=null && cust.size()!=0)
			{	
			for(int i = 0;i<cust.size();i++)
			{
				OBSystem sys = (OBSystem)cust.get(i);
				if(sys.getSystem().equals(systemName) && sys.getSystemCustomerId().equals(systemCustomerId))
						{
					exceptionMap.put("bankingMethodEmptyError", new ActionMessage("error.string.duplicatesystem.id"));
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
				OBSystem sys = (OBSystem)list.get(i);
				if(sys.getSystem().equals(systemName) && sys.getSystemCustomerId().equals(systemCustomerId))
						{
					exceptionMap.put("bankingMethodEmptyError", new ActionMessage("error.string.duplicatesystem.id"));
					ICMSCustomerTrxValue partyGroupTrxValue = null;
					resultMap.put("request.ITrxValue", partyGroupTrxValue);
					returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
					returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
					return returnMap;
						}
			}
			
		}
		
		OBSystem value = new OBSystem();
		if(event.equals("save_edited_system_in_edit") || event.equals("save_edited_system_in_resubmit") )
		{	
			value =(OBSystem) list.get((Integer.parseInt(index))-1);
			value.setSystem(systemName);
			value.setSystemCustomerId(systemCustomerId);
		}
		else
		{	
			value.setSystem(systemName);
			value.setSystemCustomerId(systemCustomerId);
		}
		list.remove((Integer.parseInt(index))-1);
		list.add((Integer.parseInt(index))-1, value);
		resultMap.put("index",index);
		resultMap.put("event",event);
		resultMap.put("systemList", list);
		resultMap.put("OBCMSCustomer", obCustomer);
		
		
		DefaultLogger.debug(this, " -------- Create Successfull -----------");
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		return returnMap;		
	}
	
	/*private List getStateList(long stateId) {
		List lbValList = new ArrayList();
		try {
			List idList = (List) getCityProxy().getStateList(stateId);
			for (int i = 0; i < idList.size(); i++) {
				IState state = (IState) idList.get(i);
				if (state.getStatus().equals("ACTIVE")) {
					String id = Long.toString(state.getIdState());
					String val = state.getStateName();
					LabelValueBean lvBean = new LabelValueBean(val, id);
					lbValList.add(lvBean);
				}
			}
		} catch (Exception ex) {
		}
		return CommonUtil.sortDropdown(lbValList);
	}

	private List getRegionList(long regionId) {
		List lbValList = new ArrayList();
		try {
			List idList = (List) getCityProxy().getRegionList(regionId);

			for (int i = 0; i < idList.size(); i++) {
				IRegion region = (IRegion) idList.get(i);
				if (region.getStatus().equals("ACTIVE")) {
					String id = Long.toString(region.getIdRegion());
					String val = region.getRegionName();
					LabelValueBean lvBean = new LabelValueBean(val, id);
					lbValList.add(lvBean);
				}
			}
		} catch (Exception ex) {
		}
		return CommonUtil.sortDropdown(lbValList);
	}

	private List getCityList(long cityId) {
		List lbValList = new ArrayList();
		try {
			List idList = (List) getCityProxy().getCityList(cityId);

			for (int i = 0; i < idList.size(); i++) {
				ICity city = (ICity) idList.get(i);
				if (city.getStatus().equals("ACTIVE")) {
					String id = Long.toString(city.getIdCity());
					String val = city.getCityName();
					LabelValueBean lvBean = new LabelValueBean(val, id);
					lbValList.add(lvBean);
				}
			}
		} catch (Exception ex) {
		}
		return CommonUtil.sortDropdown(lbValList);
	}

	private List getRelationshipMgrList(String rmRegion) {
		List lbValList = new ArrayList();
		List idList = new ArrayList();
		try {
			SearchResult idListsr = (SearchResult) getRelationshipMgrProxyManager().getRelationshipMgrList(rmRegion);

			if (idListsr != null) {
				  idList = new ArrayList(idListsr.getResultList());
			}
			
			for (int i = 0; i < idList.size(); i++) {
				IRelationshipMgr mgr = (IRelationshipMgr) idList.get(i);
				if (mgr.getStatus().equals("ACTIVE")) {
					String id = Long.toString(mgr.getId());
					String val = mgr.getRelationshipMgrName();
					LabelValueBean lvBean = new LabelValueBean(val, id);
					lbValList.add(lvBean);
				}
			}
		} catch (Exception ex) {
		}
		return CommonUtil.sortDropdown(lbValList);
	}*/
}
