package com.integrosys.cms.ui.manualinput.customer;

import java.rmi.RemoteException;
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
import com.integrosys.cms.app.customer.bus.OBCMSCustomer;
import com.integrosys.cms.app.customer.bus.OBContact;
import com.integrosys.cms.app.geography.city.bus.ICity;
import com.integrosys.cms.app.geography.city.proxy.ICityProxyManager;
import com.integrosys.cms.app.geography.region.bus.IRegion;
import com.integrosys.cms.app.geography.state.bus.IState;
import com.integrosys.cms.app.limit.bus.LimitException;
import com.integrosys.cms.app.limit.bus.LimitListSummaryItemBase;
import com.integrosys.cms.app.manualinput.limit.proxy.SBMILmtProxy;
import com.integrosys.cms.app.rbicategory.bus.OBIndustryCodeCategory;
import com.integrosys.cms.app.rbicategory.bus.OBRbiCategory;
import com.integrosys.cms.app.rbicategory.proxy.IRbiCategoryProxyManager;
import com.integrosys.cms.app.relationshipmgr.proxy.IRelationshipMgrProxyManager;
import com.integrosys.cms.ui.collateral.CategoryCodeConstant;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;
import com.integrosys.cms.ui.manualinput.CommonUtil;
import com.integrosys.cms.ui.manualinput.limit.MILimitUIHelper;
import com.integrosys.cms.ui.relationshipmgr.IRelationshipMgr;
import com.integrosys.component.commondata.app.CommonDataSingleton;

/**
 * Class created by
 * 
 * @author sandiip.shinde
 * @since 17-03-2011
 * 
 */

public class SaveCriErrorCommand extends AbstractCommand {

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
				{ "facilityFor", "java.lang.String", REQUEST_SCOPE },
				{ "facilityName", "java.lang.String", REQUEST_SCOPE },
				{ "referenceId", "java.lang.String", REQUEST_SCOPE },
				{ "serialNoList", "java.util.List", SERVICE_SCOPE },
				{ "theOBTrxContext",
						"com.integrosys.cms.app.transaction.OBTrxContext",
						FORM_SCOPE },
						{"directorCountryList", "java.util.List", SERVICE_SCOPE },
						{"facilityList", "java.util.List", SERVICE_SCOPE },
							{"directorCityList", "java.util.List", SERVICE_SCOPE },
								{"directorRegionList", "java.util.List", SERVICE_SCOPE },
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
				{"facilityList", "java.util.List", SERVICE_SCOPE },
				{ "serialNoList", "java.util.List", SERVICE_SCOPE },
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
		
		String facilityFor = (String) (map.get("facilityFor"));
		String facilityName = (String) (map.get("facilityName"));
		String referenceId = (String) (map.get("referenceId"));
		
		String []str = facilityName.split("-");
		facilityName = str[0].trim();
		
		List facilityList = new ArrayList();
		
		List lbValList = new ArrayList();
		MILimitUIHelper helper = new MILimitUIHelper();
		SBMILmtProxy proxy = helper.getSBMILmtProxy();
		try {
			if(facilityFor.equals("Capital Market Exposure") || facilityFor.equals("Priority/Non priority Sector") 
					|| facilityFor.equals("Real Estate Exposure")){
				List lmtList = proxy.getLimitListByFacilityFor(referenceId, facilityFor);
				if(lmtList!=null && lmtList.size()>0){
					String label;
					String value;
					for (int i = 0; i < lmtList.size(); i++) {
						LimitListSummaryItemBase limitSummaryItem=(LimitListSummaryItemBase) lmtList.get(i);
						label=limitSummaryItem.getLimitId() + " - " + limitSummaryItem.getProdTypeCode();
						value= limitSummaryItem.getCmsLimitId();
						LabelValueBean lvBean = new LabelValueBean(label,value);
						lbValList.add(lvBean);
					}
				}
			}else{
			
			List lmtList = proxy.getLimitSummaryListByCustID(referenceId);
			if(lmtList!=null && lmtList.size()>0){
				String label;
				String value;
				for (int i = 0; i < lmtList.size(); i++) {
					LimitListSummaryItemBase limitSummaryItem=(LimitListSummaryItemBase) lmtList.get(i);
					label=limitSummaryItem.getLimitId() + " - " + limitSummaryItem.getProdTypeCode();
					value= limitSummaryItem.getCmsLimitId();
					LabelValueBean lvBean = new LabelValueBean(label,value);
					lbValList.add(lvBean);
				}
			}
		}
		} catch (LimitException e) {
			e.printStackTrace();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		
		String strFacilityAmount ="";
		String strLineNo ="";
		List serialNoList = new ArrayList();
		
		List lbValList1 = new ArrayList();
		
		try {
			List lmtList = proxy.getLimitSummaryListByCustID(referenceId, facilityName);
			if(lmtList!=null && lmtList.size()>0){
				String label;
				String value;
				for (int i = 0; i < lmtList.size(); i++) {
					LimitListSummaryItemBase limitSummaryItem=(LimitListSummaryItemBase) lmtList.get(i);
					strFacilityAmount = limitSummaryItem.getActualSecCoverage();
					strLineNo = limitSummaryItem.getLineNo();
				}
			}
			
			//---------------------
			List tranch = proxy.getLimitTranchListByFacilityFor(facilityName, facilityFor );
			if(tranch!=null && tranch.size()>0){
				String label;
				String value;
				for (int i = 0; i < tranch.size(); i++) {
					label = (String)tranch.get(i);
					value = (String)tranch.get(i);
					LabelValueBean lvBean = new LabelValueBean(label,value);
					lbValList1.add(lvBean);
				}
			}
			
		} catch (LimitException e) {
			e.printStackTrace();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		
		serialNoList = CommonUtil.sortDropdown(lbValList1);
	
		facilityList = CommonUtil.sortDropdown(lbValList);
		
		DefaultLogger.debug(this,
				"Inside doExecute() ManualInputCreateCustomerCommand " + event);

		ICMSCustomer obCustomer = (OBCMSCustomer) map.get("OBCMSCustomer");
		
		resultMap.put("serialNoList", serialNoList);
		resultMap.put("OBCMSCustomer", obCustomer);
		resultMap.put("facilityList", facilityList);
		
		DefaultLogger.debug(this, " -------- Create Successfull -----------");
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		return returnMap;
	}

	
}
