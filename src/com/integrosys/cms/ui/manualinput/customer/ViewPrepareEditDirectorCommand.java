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
import com.integrosys.cms.app.customer.bus.OBCMSCustomer;
import com.integrosys.cms.app.customer.bus.OBDirector;
import com.integrosys.cms.app.directorMaster.bus.DirectorMasterException;
import com.integrosys.cms.app.directorMaster.proxy.IDirectorMasterProxyManager;
import com.integrosys.cms.app.geography.city.bus.ICity;
import com.integrosys.cms.app.geography.city.proxy.ICityProxyManager;
import com.integrosys.cms.app.geography.country.bus.ICountry;
import com.integrosys.cms.app.geography.region.bus.IRegion;
import com.integrosys.cms.app.geography.state.bus.IState;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;
import com.integrosys.cms.ui.manualinput.CommonUtil;

/**
 * Class created by
 * 
 * @author sandiip.shinde
 * @since 17-03-2011
 * 
 */

public class ViewPrepareEditDirectorCommand extends AbstractCommand {

	
	private ICityProxyManager cityProxy;
	
	private IDirectorMasterProxyManager directorMasterProxy;
	
	
	
	public IDirectorMasterProxyManager getDirectorMasterProxy() {
		return directorMasterProxy;
	}

	public void setDirectorMasterProxy(
			IDirectorMasterProxyManager directorMasterProxy) {
		this.directorMasterProxy = directorMasterProxy;
	}

	public ICityProxyManager getCityProxy() {
		return cityProxy;
	}

	public void setCityProxy(ICityProxyManager cityProxy) {
		this.cityProxy = cityProxy;
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
				{ "viewDirectorList", "java.util.List", SERVICE_SCOPE },
				{ "trxID", "java.lang.String", REQUEST_SCOPE },
				{ "theOBTrxContext",
						"com.integrosys.cms.app.transaction.OBTrxContext",
						FORM_SCOPE },
				{ "OBCMSCustomer",
						"com.integrosys.cms.app.customer.bus.ICMSCustomer",
						FORM_SCOPE },
				{ "event", "java.lang.String", REQUEST_SCOPE },
				
				{ "index", "java.lang.String", REQUEST_SCOPE },
				{ "event", "java.lang.String", REQUEST_SCOPE },
				{ "legalSource", "java.lang.String", REQUEST_SCOPE },
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
				{ "directorCountryList", "java.util.List", SERVICE_SCOPE },
				{ "directorRegionList", "java.util.List", SERVICE_SCOPE },
				{ "directorMasterList", "com.integrosys.base.businfra.search.SearchResult", SERVICE_SCOPE },
				{ "directorCityList", "java.util.List", SERVICE_SCOPE },
				{ "directorName", "java.lang.String", REQUEST_SCOPE },
				{ "dinNo", "java.lang.String", REQUEST_SCOPE },
				{ "relatedDUNSNo", "java.lang.String", REQUEST_SCOPE },
				{ "directorPan", "java.lang.String", REQUEST_SCOPE },
				{ "directorAadhar", "java.lang.String", REQUEST_SCOPE },
				{ "businessEntityName", "java.lang.String", REQUEST_SCOPE },
				{ "namePrefix", "java.lang.String", REQUEST_SCOPE },
				{ "fullName", "java.lang.String", REQUEST_SCOPE },
				{ "percentageOfControl", "java.lang.String", REQUEST_SCOPE },		
				{ "dirStdCodeTelNo", "java.lang.String", REQUEST_SCOPE },
				{ "dirStdCodeTelex", "java.lang.String", REQUEST_SCOPE },
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
				{ "event", "java.lang.String", SERVICE_SCOPE },
				{ "event", "java.lang.String", REQUEST_SCOPE },
				{ "directorStateList", "java.util.List", SERVICE_SCOPE },
				{ "dirList", "java.util.List", REQUEST_SCOPE },
				{ "index", "java.lang.String", REQUEST_SCOPE },
				{ "OBCMSCustomer",
						"com.integrosys.cms.app.customer.bus.ICMSCustomer",
						FORM_SCOPE },
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
		String system = (String) map.get("system");
		String index = (String) map.get("index");
		int ind = Integer.parseInt(index) - 1;
		String systemCustomerId = (String) map.get("systemCustomerId");
		DefaultLogger.debug(this,
				"Inside doExecute() ManualInputCreateCustomerCommand " + event);

		String indexId = (String) map.get("index");
		ICMSCustomer obCustomer = (OBCMSCustomer) map.get("OBCMSCustomer");
        List dir = new ArrayList();
		List list = (List) map.get("viewDirectorList"); 
		OBDirector director = (OBDirector)list.get(ind);
		dir.add(director);
		/*String directorName = director.getDirectorName();
		String dinNo = director.getDinNo();
		String relatedDUNSNo = director.getRelatedDUNSNo();
		String DirectorPan = director.getDirectorPan();
		String BusinessEntityName = director.getBusinessEntityName();
		String NamePrefix = director.getNamePrefix();
		String FullName = director.getFullName();
		String PercentageOfControl = director.getPercentageOfControl();
		String DirectorAddress1 = director.getDirectorAddress1();
		String DirectorAddress2 = director.getDirectorAddress2();
		String DirectorAddress3 = director.getDirectorAddress3();
		String DirectorPostCode = director.getDirectorPostCode();
		
		String DirectorTelNo = director.getDirectorTelNo();
		String DirectorFax = director.getDirectorFax();
		String DirectorEmail = director.getDirectorEmail();
		String Relationship = director.getRelationship();
		String RelatedType = director.getRelatedType();*/
		String DirectorRegion = director.getDirectorRegion();
		String DirectorCity = director.getDirectorCity();
		String DirectorState = director.getDirectorState();
		String DirectorCountry = director.getDirectorCountry();
		int country =0;

		if (!(DirectorState == null) && !(DirectorState.equals(""))) {
			resultMap.put("directorCityList", getCityList(Long.parseLong(DirectorState)));
		} else {
			resultMap.put("directorCityList", getList(country));
		}

		if (!(DirectorRegion == null) && !(DirectorRegion.equals(""))) {
			resultMap.put("directorStateList", getStateList(Long.parseLong(DirectorRegion)));
		} else {
			resultMap.put("directorStateList", getList(country));
		}

		if (!(DirectorCountry == null) && !(DirectorCountry.equals(""))) {
			resultMap.put("directorRegionList", getRegionList(Long.parseLong(DirectorCountry
					.trim())));
		} else {
			resultMap.put("directorRegionList", getList(country));
		}
		try {
			resultMap.put("directorMasterList", getDirectorMasterProxy().getAllDirectorMaster());
		} catch (DirectorMasterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TrxParameterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransactionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		resultMap.put("directorCountryList", getCountryList(country));
		
		resultMap.put("index", index);
		resultMap.put("event", event);
		/*resultMap.put("directorName", directorName);
		resultMap.put("dinNo", dinNo);
		resultMap.put("relatedDUNSNo", relatedDUNSNo);
		resultMap.put("directorPan", DirectorPan);
		resultMap.put("businessEntityName", BusinessEntityName);
		resultMap.put("namePrefix", NamePrefix);
		resultMap.put("fullName", FullName);
		resultMap.put("percentageOfControl", PercentageOfControl);
		resultMap.put("directorAddress1", DirectorAddress1);
		resultMap.put("directorAddress2", DirectorAddress2);
		resultMap.put("directorAddress3", DirectorAddress3);
		resultMap.put("directorPostCode", DirectorPostCode);
		resultMap.put("directorRegion", DirectorRegion);
		resultMap.put("directorCity", DirectorCity);
		resultMap.put("directorState", DirectorState);
		resultMap.put("directorCountry", DirectorCountry);
		resultMap.put("directorTelNo", DirectorTelNo);
		resultMap.put("directorFax", DirectorFax);
		resultMap.put("directorEmail", DirectorEmail);
		resultMap.put("relationship", Relationship);
		resultMap.put("relatedType", RelatedType);*/
		resultMap.put("dirList", dir);
		resultMap.put("OBCMSCustomer", obCustomer);

		DefaultLogger.debug(this, " -------- Create Successfull -----------");
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		return returnMap;
	}

	private List getCountryList(long countryId) {
		List lbValList = new ArrayList();
		try {
			List idList = (List) getCityProxy().getCountryList(countryId);
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

	private List getCityList(long stateId) {
		List lbValList = new ArrayList();
		try {
			List idList = (List) getCityProxy().getCityList(stateId);

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
