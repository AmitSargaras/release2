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
import com.integrosys.cms.app.customer.bus.CustomerException;
import com.integrosys.cms.app.customer.bus.ICMSCustomer;
import com.integrosys.cms.app.customer.bus.IContact;
import com.integrosys.cms.app.customer.bus.ISystem;
import com.integrosys.cms.app.customer.bus.OBCMSCustomer;
import com.integrosys.cms.app.customer.bus.OBContact;
import com.integrosys.cms.app.customer.bus.OBDirector;
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

public class SaveEditedDirectorCommand extends AbstractCommand{

	/**
	 * Defines an two dimensional array with the parameter list to be passed to
	 * the doExecute method by a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */

	public String[][] getParameterDescriptor() {
		return (new String[][] { 
				{ "directorList", "java.util.List", SERVICE_SCOPE },
				{ "dirList", "java.util.List", REQUEST_SCOPE },
				{ "trxID", "java.lang.String", REQUEST_SCOPE },				
				{ "theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE },
				{ "OBCMSCustomer", "com.integrosys.cms.app.customer.bus.ICMSCustomer", FORM_SCOPE },
				{ "event", "java.lang.String", REQUEST_SCOPE }, 
				{ "directorName", "java.lang.String", REQUEST_SCOPE },
				{ "dinNo", "java.lang.String", REQUEST_SCOPE },
				{ "relatedDUNSNo", "java.lang.String", REQUEST_SCOPE },
				{ "directorPan", "java.lang.String", REQUEST_SCOPE },
				{ "directorAadhar", "java.lang.String", REQUEST_SCOPE },
				{ "businessEntityName", "java.lang.String", REQUEST_SCOPE },
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
				{ "dirStdCodeTelNo", "java.lang.String", REQUEST_SCOPE },
				{ "dirStdCodeTelex", "java.lang.String", REQUEST_SCOPE },
				{ "directorEmail", "java.lang.String", REQUEST_SCOPE },
				{ "relationship", "java.lang.String", REQUEST_SCOPE },
				{ "relatedType", "java.lang.String", REQUEST_SCOPE },
				{ "event", "java.lang.String", REQUEST_SCOPE },
				{ "directorStateList", "java.util.List", SERVICE_SCOPE },
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
			
				{ "index", "java.lang.String", REQUEST_SCOPE },
				{ "directorList", "java.util.List", SERVICE_SCOPE },
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
		
		List list = (List)map.get("directorList");
		
		if(list==null)
		{
		list = new ArrayList();
		}
		OBDirector value = new OBDirector();
		if(event.equals("save_edited_director_in_edit") || event.equals("save_edited_director_in_resubmit") )
		{	
				value =(OBDirector) list.get((Integer.parseInt(index))-1);
			
				value.setDirectorName((String)map.get("directorName"));
				value.setDinNo((String)map.get("dinNo"));
				value.setRelatedDUNSNo((String)map.get("relatedDUNSNo"));
				value.setDirectorPan((String)map.get("directorPan"));
				value.setDirectorAadhar((String)map.get("directorAadhar"));
				value.setBusinessEntityName((String)map.get("businessEntityName"));
				value.setNamePrefix((String)map.get("namePrefix"));
				value.setFullName((String)map.get("fullName"));
				value.setPercentageOfControl((String)map.get("percentageOfControl"));
				value.setDirectorAddress1((String)map.get("directorAddress1"));
				value.setDirectorAddress2((String)map.get("directorAddress2"));
				value.setDirectorAddress3((String)map.get("directorAddress3"));
				value.setDirectorPostCode((String)map.get("directorPostCode"));
				value.setDirectorTelNo((String)map.get("directorTelNo"));
				value.setDirectorFax((String)map.get("directorFax"));
				value.setDirectorEmail((String)map.get("directorEmail"));
				value.setRelationship((String)map.get("relationship"));
				value.setRelatedType((String)map.get("relatedType"));
				value.setDirStdCodeTelNo((String)map.get("dirStdCodeTelNo"));
				value.setDirStdCodeTelex((String)map.get("dirStdCodeTelex"));
				value.setDirectorRegion((String)map.get("directorRegion"));
				value.setDirectorCity((String)map.get("directorCity"));
				value.setDirectorState((String)map.get("directorState"));
				value.setDirectorCountry((String)map.get("directorCountry"));
			
		}
		else
		{	
			value.setDirectorName((String)map.get("directorName"));
	     	value.setDinNo((String)map.get("dinNo"));
			value.setRelatedDUNSNo((String)map.get("relatedDUNSNo"));
			value.setDirectorPan((String)map.get("directorPan"));
			value.setDirectorAadhar((String)map.get("directorAadhar"));
			value.setBusinessEntityName((String)map.get("businessEntityName"));
			value.setNamePrefix((String)map.get("namePrefix"));
		    value.setFullName((String)map.get("fullName"));
			value.setPercentageOfControl((String)map.get("percentageOfControl"));
			value.setDirectorAddress1((String)map.get("directorAddress1"));
			value.setDirectorAddress2((String)map.get("directorAddress2"));
			value.setDirectorAddress3((String)map.get("directorAddress3"));
			value.setDirectorPostCode((String)map.get("directorPostCode"));
			value.setDirectorTelNo((String)map.get("directorTelNo"));
			value.setDirectorFax((String)map.get("directorFax"));
		    value.setDirectorEmail((String)map.get("directorEmail"));
		    value.setRelationship((String)map.get("relationship"));
			value.setRelatedType((String)map.get("relatedType"));
			value.setDirStdCodeTelNo((String)map.get("dirStdCodeTelNo"));
			value.setDirStdCodeTelex((String)map.get("dirStdCodeTelex"));
			value.setDirectorRegion((String)map.get("directorRegion"));
			value.setDirectorCity((String)map.get("directorCity"));
			value.setDirectorState((String)map.get("directorState"));
			value.setDirectorCountry((String)map.get("directorCountry"));
		}
	
		list.remove((Integer.parseInt(index))-1);
		
		list.add((Integer.parseInt(index))-1, value);
		resultMap.put("index",index);
		resultMap.put("event",event);
		resultMap.put("directorList", list);
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
