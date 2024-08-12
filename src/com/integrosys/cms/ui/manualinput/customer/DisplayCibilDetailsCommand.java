package com.integrosys.cms.ui.manualinput.customer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.struts.util.LabelValueBean;

import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.customer.bus.CustomerDAO;
import com.integrosys.cms.app.customer.bus.ICMSCustomer;
import com.integrosys.cms.app.customer.bus.IContact;
import com.integrosys.cms.app.customer.bus.OBCMSCustomer;
import com.integrosys.cms.app.customer.bus.OBContact;
import com.integrosys.cms.app.geography.city.bus.ICity;
import com.integrosys.cms.app.geography.city.proxy.ICityProxyManager;
import com.integrosys.cms.app.geography.country.bus.ICountry;
import com.integrosys.cms.app.geography.region.bus.IRegion;
import com.integrosys.cms.app.geography.state.bus.IState;
import com.integrosys.cms.app.rbicategory.bus.OBIndustryCodeCategory;
import com.integrosys.cms.app.rbicategory.bus.OBRbiCategory;
import com.integrosys.cms.app.rbicategory.proxy.IRbiCategoryProxyManager;
import com.integrosys.cms.app.systemBankBranch.bus.ISystemBankBranch;
import com.integrosys.cms.app.systemBankBranch.bus.SystemBankBranchException;
import com.integrosys.cms.app.systemBankBranch.proxy.ISystemBankBranchProxyManager;
import com.integrosys.cms.ui.collateral.CategoryCodeConstant;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;
import com.integrosys.cms.ui.manualinput.CommonUtil;
import com.integrosys.component.commondata.app.CommonDataSingleton;
import com.integrosys.component.user.app.bus.OBCommonUser;

/**
 * Class created by
 * 
 * @author sandiip.shinde
 * @since 17-03-2011
 * 
 */

public class DisplayCibilDetailsCommand extends AbstractCommand {

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

	public ICityProxyManager getCityProxy() {
		return cityProxy;
	}

	public void setCityProxy(ICityProxyManager cityProxy) {
		this.cityProxy = cityProxy;
	}

	
	

	public String[][] getParameterDescriptor() {
		return (new String[][] {
				
				{ "trxID", "java.lang.String", REQUEST_SCOPE },
				{IGlobalConstant.GLOBAL_LOS_USER, "com.integrosys.component.user.app.bus.ICommonUser",GLOBAL_SCOPE},
				{ "theOBTrxContext",
						"com.integrosys.cms.app.transaction.OBTrxContext",
						FORM_SCOPE },
				{ "OBCMSCustomer",
						"com.integrosys.cms.app.customer.bus.ICMSCustomer",
						FORM_SCOPE },
				{ "event", "java.lang.String", REQUEST_SCOPE },

				{ IGlobalConstant.GLOBAL_CUSTOMER_OBJ,
						"com.integrosys.cms.app.customer.bus.ICMSCustomer",
						GLOBAL_SCOPE } ,
				{"cifId","java.lang.String", REQUEST_SCOPE},
				{"pan","java.lang.String", REQUEST_SCOPE}});
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
				//{ "currencyList", "java.util.List", SERVICE_SCOPE },
				{ "regionList", "java.util.List", SERVICE_SCOPE },
				{ "countryRegList", "java.util.List", SERVICE_SCOPE },
				
				{ "relationshipMgrList", "java.util.List", SERVICE_SCOPE },
				{ "trxID", "java.lang.String", REQUEST_SCOPE },
				{ "partyGroupList", "java.util.ArrayList", SERVICE_SCOPE },
				{ "countryList", "java.util.List", SERVICE_SCOPE },
				
				{ "branchObj", "com.integrosys.cms.app.systemBankBranch.bus.OBSystemBankBranch",SERVICE_SCOPE },
				{ "cityList", "java.util.List", SERVICE_SCOPE },
				{ "stateList", "java.util.List", SERVICE_SCOPE },
				{ "countryRegList", "java.util.List", SERVICE_SCOPE },
				{ "cityRegList", "java.util.List", SERVICE_SCOPE },
				{ "stateRegList", "java.util.List", SERVICE_SCOPE },
				{ "regionRegList", "java.util.List", SERVICE_SCOPE },
				{ "rbiIndustryCodeList", "java.util.List", SERVICE_SCOPE },
				
				{ "request.ITrxValue",
						"com.integrosys.cms.app.transaction.ICMSTrxValue",
						REQUEST_SCOPE },
				{"cifId","java.lang.String", SERVICE_SCOPE},
				{
						com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY,
						"java.util.Locale", GLOBAL_SCOPE },
						{"duplicatePanPartyDetails","java.util.String",SERVICE_SCOPE}	});
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
		String trxId=(String) map.get("trxId");
		
		//For getting Pan details
		String cifId=(String) map.get("cifId");
		String pan=(String) map.get("pan");
		//For getting Pan details
		DefaultLogger.debug(this,
				"Inside doExecute() ManualInputCreateCustomerCommand " + event);

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
		
		String cityId = address.getCity();
		String countryId = address.getCountryCode();
		String stateId = address.getState();
		String regionId = address.getRegion();
		if(obCustomer.getRegOffice().equals("Y")){
			obCustomer.setRegOfficeDUNSNo(obCustomer.getBorrowerDUNSNo());
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
		}
		
		 contact = obCustomer.getCMSLegalEntity()
			.getRegisteredAddress();
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
		
		
		String partyId = obCustomer.getPartyGroupName();
		String rmRegion = obCustomer.getCMSLegalEntity().getRmRegion();
		String industryName = obCustomer.getIndustryName();
		
		
		String branchId = obCustomer.getMainBranch();
		String id = "";
		if(branchId!= null && !"".equals(branchId))
		{
		String[] array = branchId.split("-");
		id = array[0];
		}
		
		int country = 0;

		String cityRegId = addressReg.getCity();
		String countryRegId = addressReg.getCountryCode();
		String stateRegId = addressReg.getState();
		String regionRegId = addressReg.getRegion();
		  OBCommonUser globalUser = (OBCommonUser) map.get(IGlobalConstant.GLOBAL_LOS_USER);
	        String branchCode= globalUser.getEjbBranchCode();
	        
	    	ISystemBankBranchProxyManager systemBankBranchProxyManager=(ISystemBankBranchProxyManager) BeanHouse.get("systemBankBranchProxy");
	    	 ISystemBankBranch branch = null;
	    	 
						if(id !=null && !"".equals(id))
						{
							
						
							try {
								//branch = systemBankBranchProxyManager.getSystemBankBranchById(Long.parseLong(id));
								obCustomer.setBranchCode(id);	
							} catch (SystemBankBranchException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
	    	/* List branchList= new ArrayList();
			 List idList = (List) searchResult.getResultList();
			for (int i = 0; i < idList.size(); i++) {
				ISystemBankBranch bankBranch = (ISystemBankBranch) idList.get(i);
				if(branchCode.equals(bankBranch.getSystemBankBranchCode())){
					
					map.put("branchObj", bankBranch);
					break;
				}
			}*/
						
					
		if (!( stateId == null  ||  stateId.equals("") )) {
			resultMap.put("cityList", getCityList(Long.parseLong(stateId)));
		}
		if (!(industryName == null) && !(industryName.equals(""))) {
			resultMap.put("rbiIndustryCodeList", getRbiIndustryCodeList(industryName));
		}
		if (!( regionId == null || regionId.equals(""))) {
			resultMap.put("stateList", getStateList(Long.parseLong(regionId)));
		}
		if (!( countryId == null  ||  countryId.equals("") )) {
			resultMap
					.put("regionList", getRegionList(Long.parseLong(countryId.trim())));
		}
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
		resultMap.put("countryList", getCountryList(country));
		//resultMap.put("currencyList", getCurrencyList());
		resultMap.put("countryRegList", getCountryList(country));
		resultMap.put("trxId", trxId);
		resultMap.put("OBCMSCustomer", obCustomer);
		
		//For getting Pan details
		CustomerDAO customerDao = new CustomerDAO();
		List<String> duplicatePartyList=new ArrayList<String>();
		if(null!=cifId && !"".equals(cifId) && null!=pan && !"".equals(pan))
		 duplicatePartyList=customerDao.getPanDetails(cifId, pan);
		String duplicatePanPartyDetails="";
		if(null!=duplicatePartyList && duplicatePartyList.size() >0){
		for(int i=0; i<duplicatePartyList.size() ; i++){
			if(i != (duplicatePartyList.size()-1 )){
				duplicatePanPartyDetails=duplicatePanPartyDetails+duplicatePartyList.get(i)+ ",";
			}
			else{
				duplicatePanPartyDetails=duplicatePanPartyDetails+duplicatePartyList.get(i);	
			}
			}
		}
		//DefaultLogger.debug(this,"duplicatePanPartyDetails:"+ duplicatePanPartyDetails);
		resultMap.put("duplicatePanPartyDetails", duplicatePanPartyDetails);
		
		
		//For getting Pan details
		resultMap.put("cifId", cifId);
		DefaultLogger.debug(this, " -------- Create Successfull -----------");
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		return returnMap;
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

	private List getRbiIndustryCodeList(String indName) {
		List lbValList = new ArrayList();
		List rbiIndustryCodeList =new ArrayList();
		 HashMap rBICateCodeHashMap = CommonDataSingleton.getCodeCategoryValueLabelMap(CategoryCodeConstant.HDFC_RBI_CODE);
		OBRbiCategory oBRbiCategory = new OBRbiCategory();
		try {
			List idList = (List) getRbiCategoryProxy().getRbiIndCodeByNameList(indName);
			if(idList!=null && idList.size()>0){
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
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return CommonUtil.sortDropdown(lbValList);		
	}	
	/*private List getCurrencyList() {
		List lbValList = new ArrayList();
		try {
				MISecurityUIHelper helper = new MISecurityUIHelper();
				IForexFeedEntry[] currency = helper.getSBMISecProxy().getCurrencyList();
				
				if (currency != null) {
					for (int i = 0; i < currency.length; i++) {
						IForexFeedEntry lst = currency[i];
						String id = lst.getBuyCurrency();
						String value = lst.getBuyCurrency();
						LabelValueBean lvBean = new LabelValueBean(value, id);
						lbValList.add(lvBean);
					}
				}
		}
		catch (Exception ex) {
		}
		return CommonUtil.sortDropdown(lbValList);
	}*/

}
