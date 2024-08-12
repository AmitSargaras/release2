package com.integrosys.cms.ui.manualinput.customer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.struts.util.LabelValueBean;

import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.customer.bus.ICMSCustomer;
import com.integrosys.cms.app.customer.bus.IContact;
import com.integrosys.cms.app.customer.bus.OBBankingMethod;
import com.integrosys.cms.app.customer.bus.OBCMSCustomer;
import com.integrosys.cms.app.customer.bus.OBContact;
import com.integrosys.cms.app.customer.bus.OBDirector;
import com.integrosys.cms.app.customer.bus.OBSubline;
import com.integrosys.cms.app.geography.city.bus.ICity;
import com.integrosys.cms.app.geography.city.proxy.ICityProxyManager;
import com.integrosys.cms.app.geography.country.bus.ICountry;
import com.integrosys.cms.app.geography.region.bus.IRegion;
import com.integrosys.cms.app.geography.state.bus.IState;
import com.integrosys.cms.app.partygroup.bus.IPartyGroup;
import com.integrosys.cms.app.partygroup.bus.PartyGroupException;
import com.integrosys.cms.app.partygroup.proxy.IPartyGroupProxyManager;
import com.integrosys.cms.app.systemBank.bus.OBSystemBank;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;
import com.integrosys.cms.ui.manualinput.CommonUtil;

/**
 * Class created by
 * 
 * @author sandiip.shinde
 * @since 17-03-2011
 * 
 */

public class SaveDirectorCommand extends AbstractCommand {

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
				{ "directorList", "java.util.List", SERVICE_SCOPE },
				{ "theOBTrxContext",
						"com.integrosys.cms.app.transaction.OBTrxContext",
						FORM_SCOPE },
				{ "OBCMSCustomer",
						"com.integrosys.cms.app.customer.bus.ICMSCustomer",
						FORM_SCOPE },
				{ "event", "java.lang.String", REQUEST_SCOPE },
				{ "directorName", "java.lang.String", REQUEST_SCOPE },
				{ "dinNo", "java.lang.String", REQUEST_SCOPE },
				{ "relatedDUNSNo", "java.lang.String", REQUEST_SCOPE },
				{ "directorPan", "java.lang.String", REQUEST_SCOPE },
				{ "directorAadhar", "java.lang.String", REQUEST_SCOPE },
				{ "businessEntityName", "java.lang.String", REQUEST_SCOPE },
				
				{ "dirStdCodeTelNo", "java.lang.String", REQUEST_SCOPE },
				{ "dirStdCodeTelex", "java.lang.String", REQUEST_SCOPE },
				
				{ "namePrefix", "java.lang.String", REQUEST_SCOPE },
				{ "fullName", "java.lang.String", REQUEST_SCOPE },
				{ "percentageOfControl", "java.lang.String", REQUEST_SCOPE },
				{ "directorAddress1", "java.lang.String", REQUEST_SCOPE },
				{ "directorAddress2", "java.lang.String", REQUEST_SCOPE },
				{ "directorAddress3", "java.lang.String", REQUEST_SCOPE },
				{ "directorPostCode", "java.lang.String", REQUEST_SCOPE },
				{ "directorRegion", "java.lang.String", REQUEST_SCOPE },
				{ "directorState", "java.lang.String", REQUEST_SCOPE },
				{ "directorCountry", "java.lang.String", REQUEST_SCOPE },
				{ "directorCity", "java.lang.String", REQUEST_SCOPE },
				{ "directorTelNo", "java.lang.String", REQUEST_SCOPE },
				{ "directorFax", "java.lang.String", REQUEST_SCOPE },
				{ "directorEmail", "java.lang.String", REQUEST_SCOPE },
				{ "relationship", "java.lang.String", REQUEST_SCOPE },
				{ "relatedType", "java.lang.String", REQUEST_SCOPE },
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
				/*
				 * { "customerOb",
				 * "com.integrosys.cms.app.customer.bus.OBCMSCustomer",
				 * REQUEST_SCOPE },
				 */

				{ "directorList", "java.util.List", SERVICE_SCOPE },
				{ "OBCMSCustomer",
						"com.integrosys.cms.app.customer.bus.ICMSCustomer",
						FORM_SCOPE },
				{ "request.ITrxValue",
						"com.integrosys.cms.app.transaction.ICMSTrxValue",
						REQUEST_SCOPE },
						{ "countryRegList", "java.util.List", SERVICE_SCOPE },
						{ "cityRegList", "java.util.List", SERVICE_SCOPE },
						{ "stateRegList", "java.util.List", SERVICE_SCOPE },
						{ "regionRegList", "java.util.List", SERVICE_SCOPE },
				{com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY,
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
		int country = 0;
		List system = null;
		ICMSCustomer obCustomer = (OBCMSCustomer) map.get("OBCMSCustomer");
		IContact[] contact = obCustomer.getCMSLegalEntity()
		.getRegisteredAddress();
		OBContact address = new OBContact();
		OBContact addressReg = new OBContact();
		for( int i=0;contact.length>i;i++)
		{
			if(contact[i].getContactType().equals("CORPORATE"))
			{
				 address = (OBContact) contact[i];
			}
			if(contact[i].getContactType().equals("REGISTERED"))
			{
				 addressReg = (OBContact) contact[i];
			}
		}
		String cityRegId = addressReg.getCity();
		String countryRegId = addressReg.getCountryCode();
		String stateRegId = addressReg.getState();
		String regionRegId = addressReg.getRegion();
		if (!( stateRegId == null  ||  stateRegId.equals("") )) {
			resultMap.put("cityRegList", getCityList(Long.parseLong(stateRegId)));
		}
		else 
		{
			resultMap.put("cityRegList", getList(country));
		}
		if (!( regionRegId == null || regionRegId.equals(""))) {
			resultMap.put("stateRegList", getStateList(Long.parseLong(regionRegId)));
		}
		else 
		{
			resultMap.put("stateRegList", getList(country));
		}
		if (!( countryRegId == null  ||  countryRegId.equals("") )) {
			resultMap.put("regionRegList", getRegionList(Long.parseLong(countryRegId.trim())));
		}
		else 
		{
			resultMap.put("regionRegList", getList(country));
		}
		resultMap.put("countryRegList", getCountryList(country));
		String directorName = (String) map.get("directorName");
		String dinNo = (String) map.get("dinNo");
		String relatedDUNSNo = (String) map.get("relatedDUNSNo");
		String directorPan = (String) map.get("directorPan");
		String directorAadhar = (String) map.get("directorAadhar");
		String businessEntityName = (String) map.get("businessEntityName");
		String namePrefix = (String) map.get("namePrefix");
		String fullName = (String) map.get("fullName");
		String percentageOfControl = (String) map.get("percentageOfControl");
		String directorAddress1 = (String) map.get("directorAddress1");
		String directorAddress2 = (String) map.get("directorAddress2");
		String directorAddress3 = (String) map.get("directorAddress3");
		String directorPostCode = (String) map.get("directorPostCode");
		String directorRegion = (String) map.get("directorRegion");
		String directorCity = (String) map.get("directorCity");
		String directorState = (String) map.get("directorState");
		String directorCountry = (String) map.get("directorCountry");
		String directorTelNo = (String) map.get("directorTelNo");
		String directorFax = (String) map.get("directorFax");
		String directorEmail = (String) map.get("directorEmail");
		String relationship = (String) map.get("relationship");
		String relatedType = (String) map.get("relatedType");
		
		String dirStdCodeTelNo = (String) map.get("dirStdCodeTelNo");
		String dirStdCodeTelex = (String) map.get("dirStdCodeTelex");

		List directorList = (List) map.get("directorList");

		OBDirector director = null;
		try {

			if (directorList == null) {
				directorList = new ArrayList();
			}
			director = new OBDirector();
			director.setDirectorName(directorName);
			director.setDinNo(dinNo); 
			director.setRelatedDUNSNo(relatedDUNSNo);  
			director.setDirectorPan(directorPan); 
			director.setDirectorAadhar(directorAadhar); 
			director.setBusinessEntityName(businessEntityName);  
			director.setNamePrefix(namePrefix);  
			director.setFullName(fullName);  
			director.setPercentageOfControl(percentageOfControl);  
			director.setDirectorAddress1(directorAddress1);  
			director.setDirectorAddress2(directorAddress2);  
			director.setDirectorAddress3(directorAddress3); 
			director.setDirectorPostCode(directorPostCode);  
			director.setDirectorRegion(directorRegion); 
			director.setDirectorCity(directorCity);  
			director.setDirectorState(directorState); 
			director.setDirectorCountry(directorCountry);  
			director.setDirectorTelNo(directorTelNo);  
			director.setDirectorFax(directorFax);  
			director.setDirectorEmail(directorEmail);  
			director.setRelationship(relationship);  
			director.setRelatedType(relatedType);  
			director.setDirStdCodeTelNo(dirStdCodeTelNo);
			director.setDirStdCodeTelex(dirStdCodeTelex);
			directorList.add(director);
		} catch (PartyGroupException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		resultMap.put("OBCMSCustomer", obCustomer);
		resultMap.put("directorList", directorList);

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
	private List getCountryList(long countryId) {
		List lbValList = new ArrayList();
		try {
			List idList = (List)getCityProxy().getCountryList(countryId);
			for (int i = 0; i < idList.size(); i++) {
				ICountry country = (ICountry) idList.get(i);
				if (country.getStatus().equals("ACTIVE")) {
					String id = Long.toString(country.getIdCountry());
					String val = country.getCountryName();
					LabelValueBean lvBean = new LabelValueBean(val, id);
					lbValList.add(lvBean);
				}
			}
		} catch (Exception ex) {
		}
		return CommonUtil.sortDropdown(lbValList);
	}
}
