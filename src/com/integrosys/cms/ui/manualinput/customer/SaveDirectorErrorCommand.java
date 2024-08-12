package com.integrosys.cms.ui.manualinput.customer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.struts.util.LabelValueBean;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.customer.bus.ICMSCustomer;
import com.integrosys.cms.app.customer.bus.IContact;
import com.integrosys.cms.app.customer.bus.IDirector;
import com.integrosys.cms.app.customer.bus.OBCMSCustomer;
import com.integrosys.cms.app.customer.bus.OBContact;
import com.integrosys.cms.app.customer.bus.OBDirector;
import com.integrosys.cms.app.geography.city.bus.ICity;
import com.integrosys.cms.app.geography.city.proxy.ICityProxyManager;
import com.integrosys.cms.app.geography.region.bus.IRegion;
import com.integrosys.cms.app.geography.state.bus.IState;
import com.integrosys.cms.app.rbicategory.bus.OBIndustryCodeCategory;
import com.integrosys.cms.app.rbicategory.bus.OBRbiCategory;
import com.integrosys.cms.app.rbicategory.proxy.IRbiCategoryProxyManager;
import com.integrosys.cms.app.relationshipmgr.proxy.IRelationshipMgrProxyManager;
import com.integrosys.cms.ui.collateral.CategoryCodeConstant;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;
import com.integrosys.cms.ui.manualinput.CommonUtil;
import com.integrosys.cms.ui.relationshipmgr.IRelationshipMgr;
import com.integrosys.component.commondata.app.CommonDataSingleton;

/**
 * Class created by
 * 
 * @author sandiip.shinde
 * @since 17-03-2011
 * 
 */

public class SaveDirectorErrorCommand extends AbstractCommand {

	/**
	 * Defines an two dimensional array with the parameter list to be passed to
	 * the doExecute method by a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */

	private ICityProxyManager cityProxy;

	private IRbiCategoryProxyManager rbiCategoryProxy;
	
	public IRbiCategoryProxyManager getRbiCategoryProxy() {
		return rbiCategoryProxy;
	}

	public void setRbiCategoryProxy(IRbiCategoryProxyManager rbiCategoryProxy) {
		this.rbiCategoryProxy = rbiCategoryProxy;
	}
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
				{ "trxID", "java.lang.String", REQUEST_SCOPE },

				{ "theOBTrxContext",
						"com.integrosys.cms.app.transaction.OBTrxContext",
						FORM_SCOPE },
						{"directorCountryList", "java.util.List", SERVICE_SCOPE },
						{"directorStateList", "java.util.List", SERVICE_SCOPE },
							{"directorCityList", "java.util.List", SERVICE_SCOPE },
								{"directorRegionList", "java.util.List", SERVICE_SCOPE },
				{ "OBCMSCustomer",
						"com.integrosys.cms.app.customer.bus.ICMSCustomer",
						FORM_SCOPE },
						
				{ "event", "java.lang.String", REQUEST_SCOPE },
				{ "index", "java.lang.String", REQUEST_SCOPE },

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
				{"directorCountryList", "java.util.List", SERVICE_SCOPE },
					{"directorStateList", "java.util.List", SERVICE_SCOPE },
						{"directorCityList", "java.util.List", SERVICE_SCOPE },
							{"directorRegionList", "java.util.List", SERVICE_SCOPE },
				{ "regionList", "java.util.List", SERVICE_SCOPE },
				{ "relationshipMgrList", "java.util.List", SERVICE_SCOPE },
				{ "rbiIndustryCodeList", "java.util.ArrayList", SERVICE_SCOPE },
				{ "partyGroupList", "java.util.ArrayList", SERVICE_SCOPE },
				{ "cityList", "java.util.List", SERVICE_SCOPE },
				{ "stateList", "java.util.List", SERVICE_SCOPE },
				{ "dirList", "java.util.List", SERVICE_SCOPE },
				{ "index", "java.lang.String", REQUEST_SCOPE },
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
		String index = (String) map.get("index");
		List directorCountryList = (List) map.get("directorCountryList");
		List directorStateList = (List) map.get("directorStateList");
		List directorCityList = (List) map.get("directorCityList");
		List directorRegionList = (List) map.get("directorRegionList");
		DefaultLogger.debug(this,
				"Inside doExecute() ManualInputCreateCustomerCommand " + event);

		ICMSCustomer obCustomer = (OBCMSCustomer) map.get("OBCMSCustomer");
       
		IContact[] contact = obCustomer.getCMSLegalEntity()
				.getRegisteredAddress();
		
		OBContact address = new OBContact();
		for( int i=0;contact.length>i;i++)
		{
			if(contact[i].getContactType().equals("CORPORATE"))
			{
				 address = (OBContact) contact[i];
			}
		}
		
		String cityId = address.getCity();
		String countryId = address.getCountryCode();
		String stateId = address.getState();
		String regionId = address.getRegion();
		String partyId = obCustomer.getPartyGroupName();
		String rmRegion = obCustomer.getCMSLegalEntity().getRmRegion();
		String industryName = obCustomer.getIndustryName();
		IDirector[] dir = obCustomer.getCMSLegalEntity().getDirector();
		IDirector director =dir[0]; 
		String dirCityId = "";
		String dirCountryId = "";
		String dirStateId = "";
		String dirRegionId = "";
		
		if(director!=null){
		 dirCityId = director.getDirectorCity();
		 dirCountryId = director.getDirectorCountry();
		 dirStateId = director.getDirectorState();
		 dirRegionId = director.getDirectorRegion();
		}
		
		List dirList = new ArrayList<OBDirector>();
		dirList.add((OBDirector)director);
		
		if (!( dirStateId == null  ||  dirStateId.equals("") )) {
			resultMap.put("directorCityList", getCityList(Long.parseLong(dirStateId)));
		}
		if (!( dirRegionId == null || dirRegionId.equals(""))) {
			resultMap.put("directorStateList", getStateList(Long.parseLong(dirRegionId)));
		}
		if (!( dirCountryId == null  ||  dirCountryId.equals("") )) {
			resultMap
					.put("directorRegionList", getRegionList(Long.parseLong(dirCountryId)));
		}
		
		if (!( stateId == null  ||  stateId.equals("") )) {
			resultMap.put("cityList", getCityList(Long.parseLong(stateId)));
		}
		if (!( regionId == null || regionId.equals(""))) {
			resultMap.put("stateList", getStateList(Long.parseLong(regionId)));
		}
		if (!( countryId == null  ||  countryId.equals("") )) {
			resultMap
					.put("regionList", getRegionList(Long.parseLong(countryId)));
		}if (!( rmRegion == null ||  rmRegion.equals("")) ) {
			resultMap
			.put("relationshipMgrList", getRelationshipMgrList(rmRegion));
             }
		if (!(industryName == null) && !(industryName.equals(""))) {
			resultMap.put("rbiIndustryCodeList", getRbiIndustryCodeList(industryName));
		} 
		resultMap.put("OBCMSCustomer", obCustomer);
		resultMap.put("dirList",dirList);
		resultMap.put("index", index);

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
	}
	private List getRbiIndustryCodeList(String indName) {
		List lbValList = new ArrayList();
		List rbiIndustryCodeList =new ArrayList();
		 HashMap rBICateCodeHashMap = CommonDataSingleton.getCodeCategoryValueLabelMap(CategoryCodeConstant.HDFC_RBI_CODE);
		OBRbiCategory oBRbiCategory = new OBRbiCategory();
		try {
			List idList = (List) getRbiCategoryProxy().getRbiIndCodeByNameList(indName);
			oBRbiCategory = (OBRbiCategory)idList.get(0);
			Set rbiC = oBRbiCategory.getStageIndustryNameSet();
			Iterator itSet = rbiC.iterator();
			OBIndustryCodeCategory oBIndustryCodeCategory = new OBIndustryCodeCategory();
			int count = 0;
			while( itSet.hasNext() ){
				oBIndustryCodeCategory = (OBIndustryCodeCategory)itSet.next();
				
				//Uma :Rbi Category Master issue- Null Pointer Exception
				if(null != rBICateCodeHashMap.get(oBIndustryCodeCategory.getRbiCodeCategoryId())){
				String id = oBIndustryCodeCategory.getRbiCodeCategoryId();
				String value =oBIndustryCodeCategory.getRbiCodeCategoryId()+"-"+rBICateCodeHashMap.get(oBIndustryCodeCategory.getRbiCodeCategoryId()).toString();
				LabelValueBean lvBean = new LabelValueBean(value, id);
				lbValList.add(lvBean);
				}
			} 
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return CommonUtil.sortDropdown(lbValList);		
	}
}
