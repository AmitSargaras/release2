package com.integrosys.cms.ui.manualinput.customer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.struts.util.LabelValueBean;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.customer.bus.ICMSCustomer;
import com.integrosys.cms.app.customer.bus.IContact;
import com.integrosys.cms.app.customer.bus.OBCMSCustomer;
import com.integrosys.cms.app.customer.bus.OBContact;
import com.integrosys.cms.app.geography.city.bus.ICity;
import com.integrosys.cms.app.geography.city.proxy.ICityProxyManager;
import com.integrosys.cms.app.geography.region.bus.IRegion;
import com.integrosys.cms.app.geography.state.bus.IState;
import com.integrosys.cms.app.relationshipmgr.proxy.IRelationshipMgrProxyManager;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;
import com.integrosys.cms.ui.manualinput.CommonUtil;
import com.integrosys.cms.ui.relationshipmgr.IRelationshipMgr;

/**
 * Class created by
 * 
 * @author sandiip.shinde
 * @since 17-03-2011
 * 
 */

public class CopyOfficeToRegisterCommand extends AbstractCommand {

	/**
	 * Defines an two dimensional array with the parameter list to be passed to
	 * the doExecute method by a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */

	private ICityProxyManager cityProxy;

	public ICityProxyManager getCityProxy() {
		return cityProxy;
	}

	public void setCityProxy(ICityProxyManager cityProxy) {
		this.cityProxy = cityProxy;
	}

	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ "trxID", "java.lang.String", REQUEST_SCOPE },

				{ "theOBTrxContext",
						"com.integrosys.cms.app.transaction.OBTrxContext",
						FORM_SCOPE },
				{ "OBCMSCustomer",
						"com.integrosys.cms.app.customer.bus.ICMSCustomer",
						FORM_SCOPE },
				{ "event", "java.lang.String", REQUEST_SCOPE },

				{ IGlobalConstant.GLOBAL_CUSTOMER_OBJ,
						"com.integrosys.cms.app.customer.bus.ICMSCustomer",
						GLOBAL_SCOPE } });
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
				{ "customerOb",
						"com.integrosys.cms.app.customer.bus.OBCMSCustomer",
						REQUEST_SCOPE },
				{ "OBCMSCustomer",
						"com.integrosys.cms.app.customer.bus.ICMSCustomer",
						FORM_SCOPE },
				{ "countryList", "java.util.List", SERVICE_SCOPE },
				{ "regionList", "java.util.List", SERVICE_SCOPE },
				{ "countryRegList", "java.util.List", SERVICE_SCOPE },
				{ "regionRegList", "java.util.List", SERVICE_SCOPE },
				{ "cityRegList", "java.util.List", SERVICE_SCOPE },
				{ "stateRegList", "java.util.List", SERVICE_SCOPE },
				{ "cityList", "java.util.List", SERVICE_SCOPE },
				{ "stateList", "java.util.List", SERVICE_SCOPE },
				{ "request.ITrxValue",
						"com.integrosys.cms.app.transaction.ICMSTrxValue",
						REQUEST_SCOPE },
				{
						com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY,
						"java.util.Locale", GLOBAL_SCOPE } });
	}

	/**
	 * This method does the Business operations with the HashMap and put the
	 * results back into the HashMap.Here get data from database for Interest
	 * Rate is done.
	 * 
	 * @param map
	 *            is of type HashMap
	 * @return HashMap with the Result
	 */

	public HashMap doExecute(HashMap map) throws CommandProcessingException,
			CommandValidationException {
		HashMap resultMap = new HashMap();
		HashMap returnMap = new HashMap();
		String event = (String) map.get("event");

		DefaultLogger.debug(this,
				"Inside doExecute() ManualInputCreateCustomerCommand " + event);

		ICMSCustomer obCustomer = (OBCMSCustomer) map.get("OBCMSCustomer");

		IContact[] contact = obCustomer.getCMSLegalEntity()
				.getRegisteredAddress();

		OBContact address = new OBContact();
		OBContact addressReg = new OBContact();
		OBContact addressCopy = new OBContact();
		for (int i = 0; contact.length > i; i++) {
			if (contact[i].getContactType().equals("CORPORATE")) {
				address = (OBContact) contact[i];
			}

		}

		String cityId = address.getCity();
		String countryId = address.getCountryCode();
		String stateId = address.getState();
		String regionId = address.getRegion();
		obCustomer.setRegOfficeDUNSNo(obCustomer.getBorrowerDUNSNo());
		
		int country = 0;

		if (!(stateId.equals("") || stateId == null)) {

			resultMap.put("cityList", getCityList(Long.parseLong(stateId)));
			resultMap.put("cityRegList", getCityList(Long.parseLong(stateId)));
		}
		if (!(regionId.equals("") || regionId == null)) {

			resultMap.put("stateList", getStateList(Long.parseLong(regionId)));
			resultMap.put("stateRegList",
					getStateList(Long.parseLong(regionId)));
		}
		if (!(countryId.equals("") || countryId == null)) {

			resultMap.put("regionList",
					getRegionList(Long.parseLong(countryId)));
			resultMap.put("regionRegList", getRegionList(Long
					.parseLong(countryId)));
		}
		for (int i = 0; contact.length > i; i++) {

			if (contact[i].getContactType().equals("REGISTERED")) {
				contact[i].setCity(cityId);
				contact[i].setCountryCode(countryId);
				contact[i].setState(stateId);
				contact[i].setRegion(regionId);
				contact[i].setAddressLine1(address.getAddressLine1());
				contact[i].setAddressLine2(address.getAddressLine2());
				contact[i].setAddressLine3(address.getAddressLine3());
				contact[i].setPostalCode(address.getPostalCode());
				contact[i].setTelephoneNumer(address.getTelephoneNumer());
				contact[i].setTelex(address.getTelex());
				contact[i].setStdCodeTelNo(address.getStdCodeTelNo());
				contact[i].setStdCodeTelex(address.getStdCodeTelex());
				contact[i].setEmailAddress(address.getEmailAddress());
			}
		}

		obCustomer.getCMSLegalEntity().setRegisteredAddress(contact);
		resultMap.put("OBCMSCustomer", obCustomer);

		DefaultLogger.debug(this, " -------- Create Successfull -----------");
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		return returnMap;
	}

	private List getStateList(long stateId) {
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

	private List getList(long stateId) {
		List lbValList = new ArrayList();
		try {
			List idList = new ArrayList();
			for (int i = 0; i < idList.size(); i++) {
				IRegion region = (IRegion) idList.get(i);
				String id = "";
				String val = "";
				LabelValueBean lvBean = new LabelValueBean(val, id);
				lbValList.add(lvBean);
			}
		} catch (Exception ex) {
		}
		return CommonUtil.sortDropdown(lbValList);
	}

}
