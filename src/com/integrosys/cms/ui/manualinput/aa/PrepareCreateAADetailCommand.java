/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:$
 */
package com.integrosys.cms.ui.manualinput.aa;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.TreeMap;
import java.util.TreeSet;

import org.apache.struts.util.LabelValueBean;

import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.collateral.bus.CollateralDAOFactory;
import com.integrosys.cms.app.common.bus.IBookingLocation;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.customer.bus.ICMSCustomer;
import com.integrosys.cms.app.customer.proxy.CustomerProxyFactory;
import com.integrosys.cms.app.geography.bus.NoSuchGeographyException;
import com.integrosys.cms.app.geography.region.bus.IRegionDAO;
import com.integrosys.cms.app.limit.bus.ILimitProfile;
import com.integrosys.cms.app.limit.proxy.ILimitProxy;
import com.integrosys.cms.app.limit.proxy.LimitProxyFactory;
import com.integrosys.cms.app.limit.trx.ILimitProfileTrxValue;
import com.integrosys.cms.app.manualinput.aa.proxy.SBMIAAProxy;
import com.integrosys.cms.app.relationshipmgr.bus.IRelationshipMgrDAO;
import com.integrosys.cms.app.systemBankBranch.bus.ISystemBankBranch;
import com.integrosys.cms.app.transaction.OBTrxContext;
import com.integrosys.cms.ui.collateral.CategoryCodeConstant;
import com.integrosys.cms.ui.common.constant.ICMSUIConstant;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;
import com.integrosys.cms.ui.manualinput.CommonUtil;
import com.integrosys.cms.ui.manualinput.limit.othercovenantsdetails.IOtherCovenantDetailsDAO;
import com.integrosys.component.bizstructure.app.bus.ITeam;
import com.integrosys.component.commondata.app.CommonDataSingleton;

/**
 * Describe this class. Purpose: to prepare the AA value to be create
 * Description: command that get the value from database to let the user to
 * create the value
 * 
 * @author $Author: Jerlin$<br>
 * @version $Revision:$
 * @since $Date:$ Tag: $Name$
 */

public class PrepareCreateAADetailCommand extends AbstractCommand implements ICommonEventConstant {

	/**
	 * Defines an two dimensional array with the parameter list to be passed to
	 * the doExecute method by a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */

	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ "limitProfileTrxVal", "com.integrosys.cms.app.limit.trx.OBLimitProfileTrxValue", SERVICE_SCOPE },
				{ "theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE },
				{ IGlobalConstant.GLOBAL_CUSTOMER_OBJ, "com.integrosys.cms.app.customer.bus.ICMSCustomer", GLOBAL_SCOPE },
				{ IGlobalConstant.USER_TEAM, "com.integrosys.component.bizstructure.app.bus.ITeam", GLOBAL_SCOPE },
				{ "InitialLimitProfile", "com.integrosys.cms.app.limit.bus.OBLimitProfile", FORM_SCOPE },
				{ "event", "java.lang.String", REQUEST_SCOPE }, 
				{ "mainEventIdentifier", "java.lang.String", REQUEST_SCOPE }, 
				{ "mainEventIdentifier", "java.lang.String", SERVICE_SCOPE },
				
				{ "indexChange", "java.lang.String", REQUEST_SCOPE },
				{ "preEvent", "java.lang.String", REQUEST_SCOPE }, 
				{ "customerID", "java.lang.String", REQUEST_SCOPE }
				
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
				{ "limitProfileTrxVal", "com.integrosys.cms.app.limit.trx.OBLimitProfileTrxValue", SERVICE_SCOPE },
				{ IGlobalConstant.GLOBAL_CUSTOMER_OBJ, "com.integrosys.cms.app.customer.bus.ICMSCustomer", GLOBAL_SCOPE },
				{ "timefrequency.labels", "java.util.Collection", REQUEST_SCOPE },
				{ "timefrequency.values", "java.util.Collection", REQUEST_SCOPE },
				{ "preEvent", "java.lang.String", REQUEST_SCOPE }, { "countryList", "java.util.List", REQUEST_SCOPE },
				{ "orgList", "java.util.List", REQUEST_SCOPE },
				{ "sourceSystemList", "java.util.List", REQUEST_SCOPE },
				{ "sourceSystemListID", "java.util.Collection", REQUEST_SCOPE },
				{ "sourceSystemListValue", "java.util.Collection", REQUEST_SCOPE },
				{ "InitialLimitProfile", "java.lang.Object", FORM_SCOPE },
				{ "creditAprrovalList", "java.util.List", SERVICE_SCOPE },
				{ "riskGradeList", "java.util.List", SERVICE_SCOPE },
				{ "relationShipMgrName", "java.lang.String", SERVICE_SCOPE },
				{ "regionName", "java.lang.String", SERVICE_SCOPE },
				{ "branchName", "java.lang.String", SERVICE_SCOPE },
				{ "customerID", "java.lang.String", REQUEST_SCOPE }
		});
	}

	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
		HashMap returnMap = new HashMap();
		HashMap resultMap = new HashMap();

		String event = (String) map.get("event");
		String mainEventIdentifier= event;
		String ind = (String) map.get("indexChange");
		String customerID = (String) map.get("customerID");
		int indexChange = Integer.parseInt(ind);
		String countryCode = null;
		AAUIHelper helper = new AAUIHelper();
		ILimitProfileTrxValue limitProfileTrxVal = null;
		
		
		
		ILimitProxy proxyLimit = null;
		try {
			ITeam team = (ITeam) (map.get(IGlobalConstant.USER_TEAM));

			ICMSCustomer customerOB =(ICMSCustomer)map.get(IGlobalConstant.GLOBAL_CUSTOMER_OBJ);
			if ((customerOB != null) && (ICMSConstant.LONG_INVALID_VALUE != customerOB.getLegalEntity().getLEID())) {
				// Customer is found
				resultMap.put("relationShipMgrName", getRelationshipMgr(customerOB.getRelationshipMgr()));
				resultMap.put("regionName",getRegionIdName(customerOB.getRmRegion()));
				resultMap.put("branchName",customerOB.getMainBranch());
			}
			if (!(event.equals("maker_add_agreement_confirm_error") || event
					.equals("maker_update_agreement_confirm_error"))) {

				//if (indexChange == 0) {
				
					proxyLimit= LimitProxyFactory.getProxy();
					//limitProfileTrxVal = proxy.getTrxLimitProfile(obLimitProfile.getLimitProfileID());
					SBMIAAProxy proxy = helper.getSBMIAAProxy();
					
					//BigDecimal sanctLmt = new BigDecimal(customerOB.getTotalSanctionedLimit());					
					resultMap.put("creditAprrovalList", getCreditApproverList(proxy.getCreditApproverList())); //shiv
					//resultMap.put("creditAprrovalList", getCreditApproverList(proxy.getCreditApproverListWithLimit(sanctLmt))); 
//					ILimitProfileTrxValue limitProfileTrxVal = null;
//					resultMap.put("limitProfileTrxVal", limitProfileTrxVal);

				//}
				//else {
					OBTrxContext trxContext = (OBTrxContext) map.get("theOBTrxContext");
					ILimitProfileTrxValue limitProfileTrxValNew = (ILimitProfileTrxValue) map.get("limitProfileTrxVal");
					ILimitProfile obLimitProfile = null;
					if (limitProfileTrxValNew != null) {
						//resultMap.put("relationShipMgrName", getRelationshipMgr(limitProfileTrxVal.getLimitProfile().getRelationshipManager()));
					//	resultMap.put("regionName",getRegionIdName(limitProfileTrxVal.getStagingLimitProfile().getControllingBranch()));
						
						obLimitProfile = limitProfileTrxValNew.getStagingLimitProfile();
					}
					else {
						obLimitProfile = (ILimitProfile) map.get("InitialLimitProfile");
					}
					
					boolean flag = false;
					flag = proxyLimit.getTrxCam(customerID);
					
					if (flag) {
						resultMap.put("wip", "wip");
					}

					resultMap.put("limitProfileTrxVal", limitProfileTrxValNew);
					resultMap.put("InitialLimitProfile", obLimitProfile);

					if (obLimitProfile != null) {
						IBookingLocation bookingLoc = obLimitProfile.getOriginatingLocation();
						if (bookingLoc != null) {
							countryCode = bookingLoc.getCountryCode();
						}
					}

					String preEvent = (String) map.get("preEvent");
					resultMap.put("preEvent", preEvent);

				//}

				
				/*resultMap.put("countryList", helper.getCountryList(team));
				resultMap.put("orgList", helper.getOrgList(countryCode, team));
				resultMap.put("sourceSystemList", helper.getSourceSystemList(countryCode, team));*/
			}

			resultMap.put("riskGradeList",getGradeList());//Shiv
			resultMap.put("timefrequency.labels", CommonDataSingleton.getCodeCategoryLabels(ICMSUIConstant.TIME_FREQ));
			resultMap.put("timefrequency.values", CommonDataSingleton.getCodeCategoryValues(ICMSUIConstant.TIME_FREQ));

		}
		catch (Exception e) {
			DefaultLogger.debug(this, "got exception in doExecute" + e);
			e.printStackTrace();
			throw (new CommandProcessingException(e.getMessage()));
		}
		resultMap.put("event", event);
		resultMap.put("mainEventIdentifier", mainEventIdentifier);
		DefaultLogger.debug(this, "Going out of doExecute()");
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		return returnMap;
	}
	
	//Shiv
	private List getCreditApproverList(List lst) {
		List lbValList = new ArrayList();
	try {
			
		for (int i = 0; i < lst.size(); i++) {
			String [] creditApprover = (String[])lst.get(i);
				String val = creditApprover[0];
				String name = creditApprover[1];
				if(creditApprover[2].equals("Y")){
					name = name+ " (Senior)";
				}
				LabelValueBean lvBean = new LabelValueBean(name,val );
				lbValList.add(lvBean);
		}
	} catch (Exception ex) {
	}
	return CommonUtil.sortDropdown(lbValList);
}
	//Shiv
	private List getGradeList() {
		List lbValList = new ArrayList();
		List values = new ArrayList();
		TreeSet ts = new TreeSet();
		try {
		values.addAll(CommonDataSingleton.getCodeCategoryLabels(CategoryCodeConstant.CommonCode_RISK_GRADE));
		
		for (int i = 0; i < values.size(); i++) {
			ts.add(new Integer(values.get(i).toString()));
		}
		Iterator itr = ts.iterator();
		
		while (itr.hasNext()) {
				String val = itr.next().toString();
				LabelValueBean lvBean = new LabelValueBean(val,val );
				lbValList.add(lvBean);
		}
	} catch (Exception ex) {
	}
	return lbValList;
}
	public String getRelationshipMgr(String relMgrId) {
		IRelationshipMgrDAO relationshipmgr = (IRelationshipMgrDAO)BeanHouse.get("relationshipMgrDAO");
		String	strRelationshipmgr = "";
		if(relMgrId != null){
			strRelationshipmgr = relationshipmgr.getRelationshipMgrById(Long.parseLong(relMgrId)).getRelationshipMgrName();
		}
		return strRelationshipmgr;
	}
	public String getRegionIdName(String regionId) {
		IRegionDAO regionDao = (IRegionDAO) BeanHouse.get("regionDAO");
		String regionName="";
		try {
			if(regionId != null){
				regionName = regionDao.getRegionById(Long.parseLong(regionId)).getRegionName();
			}
		} catch (NoSuchGeographyException e) {
			e.printStackTrace();
		} catch (TrxParameterException e) {
			e.printStackTrace();
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (TransactionException e) {
			e.printStackTrace();
		}
		return regionName;
}
}
