/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:$
 */
package com.integrosys.cms.ui.manualinput.aa;
//import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.TreeSet;

import org.apache.struts.util.LabelValueBean;

import com.integrosys.base.businfra.search.SearchDAOException;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.common.bus.IBookingLocation;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.customer.bus.ICMSCustomer;
import com.integrosys.cms.app.customer.proxy.CustomerProxyFactory;
import com.integrosys.cms.app.geography.bus.NoSuchGeographyException;
import com.integrosys.cms.app.geography.region.bus.IRegionDAO;
import com.integrosys.cms.app.limit.bus.ILimitProfile;
import com.integrosys.cms.app.limit.bus.LimitException;
import com.integrosys.cms.app.limit.bus.OBOtherCovenant;
import com.integrosys.cms.app.limit.proxy.ILimitProxy;
import com.integrosys.cms.app.limit.proxy.LimitProxyFactory;
import com.integrosys.cms.app.limit.trx.ILimitProfileTrxValue;
import com.integrosys.cms.app.manualinput.aa.proxy.SBMIAAProxy;
import com.integrosys.cms.app.relationshipmgr.bus.IRelationshipMgrDAO;
import com.integrosys.cms.app.transaction.OBTrxContext;
import com.integrosys.cms.ui.collateral.CategoryCodeConstant;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;
import com.integrosys.cms.ui.manualinput.CommonUtil;
import com.integrosys.cms.ui.manualinput.limit.othercovenantsdetails.IOtherCovenantDetailsDAO;
import com.integrosys.component.bizstructure.app.bus.ITeam;
import com.integrosys.component.commondata.app.CommonDataSingleton;

/**
 * Describe this class. Purpose: for Maker to reedit the AA value after rejected
 * by checker Description: command that get the value that being rejected by
 * checker from database to let the user to reedit the value
 * 
 * @author $Author: Jerlin$<br>
 * @version $Revision:$
 * @since $Date: $ Tag: $Name$
 */

public class MakerReadRejectedAADetailCommand extends AbstractCommand implements ICommonEventConstant {

	/**
	 * Defines an two dimensional array with the parameter list to be passed to
	 * the doExecute method by a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */

	public String[][] getParameterDescriptor() {
		return (new String[][] { { "TrxId", "java.lang.String", REQUEST_SCOPE },
				{ "session.TrxId", "java.lang.String", SERVICE_SCOPE },
				{ "limitObj", "com.integrosys.cms.app.limit.bus.OBLimitProfile", SERVICE_SCOPE },
				{ "event", "java.lang.String", REQUEST_SCOPE },
				{ IGlobalConstant.USER_TEAM, "com.integrosys.component.bizstructure.app.bus.ITeam", GLOBAL_SCOPE },
				{"otherCovenantDetailsList", "java.util.List", SERVICE_SCOPE},
				{ "theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE },
				{ "InitialLimitProfile", "com.integrosys.cms.app.limit.bus.OBLimitProfile", FORM_SCOPE },
				{ "indexChange", "java.lang.String", REQUEST_SCOPE },
				{ "limitProfileTrxVal", "com.integrosys.cms.app.limit.trx.OBLimitProfileTrxValue", SERVICE_SCOPE }, });
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
				{ "session.TrxId", "java.lang.String", SERVICE_SCOPE },
				{ "timefrequency.values", "java.util.Collection", REQUEST_SCOPE },
				{ "timefrequencies.map", "java.util.HashMap", SERVICE_SCOPE },
				{"otherCovenantDetailsList", "java.util.List", SERVICE_SCOPE},
				{ "InitialLimitProfile", "java.lang.Object", FORM_SCOPE },
				{ "TrxId", "java.lang.String", REQUEST_SCOPE }, { "countryList", "java.util.List", REQUEST_SCOPE },
				{ "orgList", "java.util.List", REQUEST_SCOPE },
				{ "event", "java.lang.String", REQUEST_SCOPE },
				{ "sourceSystemList", "java.util.List", REQUEST_SCOPE },
				{ "indexChange", "java.lang.String", REQUEST_SCOPE },
				{ "creditAprrovalList", "java.util.List", SERVICE_SCOPE },
				{ "riskGradeList", "java.util.List", SERVICE_SCOPE },
				{ "relationShipMgrName", "java.lang.String", SERVICE_SCOPE },
				{ "regionName", "java.lang.String", SERVICE_SCOPE },
				{ "branchName", "java.lang.String", SERVICE_SCOPE },
		});
	}

	/**
	 * This method does the Business operations with the HashMap and put the
	 * results back into the HashMap.Here get the value where checker rejected
	 * from database for Interest Rate is done.
	 * 
	 * @param map is of type HashMap
	 * @return HashMap with the Result
	 */

	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
		HashMap returnMap = new HashMap();
		HashMap resultMap = new HashMap();
		String stagingrefid;
		String countryCode = null;
		OBOtherCovenant obothercovenant;
		String trxId = (String) map.get("TrxId");
		if(trxId==null||"".equals(trxId)){
			trxId = (String) map.get("session.TrxId");
		}
		trxId = trxId.trim();
		int ind = 0;
		String indexChange = null;
		indexChange = (String) map.get("indexChange");
		// System.out.println("indexChange : " + indexChange);
		// System.out.println("trxId : " + trxId);
		if (indexChange != null) {
			ind = Integer.parseInt(indexChange);
		}

		String event = (String) map.get("event");

		OBTrxContext trxContext = (OBTrxContext) map.get("theOBTrxContext");
		ILimitProfile obLimitProfile = (ILimitProfile) map.get("InitialLimitProfile");
		ILimitProfileTrxValue limitProfileTrxVal = null;
		AAUIHelper helper = new AAUIHelper();
		ITeam team = (ITeam) (map.get(IGlobalConstant.USER_TEAM));

		
		//ICMSCustomer customerOB = null;
		try {
			if (ind > 0) {

				limitProfileTrxVal = (ILimitProfileTrxValue) map.get("limitProfileTrxVal");
				if (limitProfileTrxVal != null) {
					resultMap.put("relationShipMgrName", getRelationshipMgr(limitProfileTrxVal.getLimitProfile().getRelationshipManager()));
					resultMap.put("regionName",getRegionIdName(limitProfileTrxVal.getStagingLimitProfile().getControllingBranch()));
					resultMap.put("branchName",getRegionIdName(limitProfileTrxVal.getStagingLimitProfile().getControllingBranch()));
					obLimitProfile = limitProfileTrxVal.getStagingLimitProfile();
				}
				if ("refresh_maker_edit_aadetail_reject".equals(event)) {
					IBookingLocation bookingLoc = obLimitProfile.getOriginatingLocation();
					if (bookingLoc != null) {
						countryCode = bookingLoc.getCountryCode();
					}

					limitProfileTrxVal.setStagingLimitProfile(obLimitProfile);

					String preEvent = (String) map.get("preEvent");
					resultMap.put("preEvent", preEvent);

				}
				else {
					IBookingLocation bookingLoc = limitProfileTrxVal.getStagingLimitProfile().getOriginatingLocation();
					if (bookingLoc != null) {
						countryCode = bookingLoc.getCountryCode();
					}

				}

				resultMap.put("limitProfileTrxVal", limitProfileTrxVal);
				resultMap.put("InitialLimitProfile", limitProfileTrxVal.getStagingLimitProfile());

			}
			else {
				ILimitProxy proxy = LimitProxyFactory.getProxy();
				// Get Trx By TrxID
				limitProfileTrxVal = proxy.getTrxLimitProfile(trxId);
				if (limitProfileTrxVal != null) {
					//resultMap.put("relationShipMgrName", getRelationshipMgr(limitProfileTrxVal.getLimitProfile().getRelationshipManager()));
					//resultMap.put("regionName",getRegionIdName(limitProfileTrxVal.getStagingLimitProfile().getControllingBranch()));
					
					if(limitProfileTrxVal.getLimitProfile()!=null){
						ICMSCustomer customerOB = null;
						customerOB = CustomerProxyFactory.getProxy().getCustomerByCIFSource(limitProfileTrxVal.getLimitProfile().getLEReference(), null);
						if ((customerOB != null) && (ICMSConstant.LONG_INVALID_VALUE != customerOB.getLegalEntity().getLEID())) {
							// Customer is found
							resultMap.put("relationShipMgrName", getRelationshipMgr(customerOB.getRelationshipMgr()));
							resultMap.put("regionName",getRegionIdName(customerOB.getRmRegion()));
							resultMap.put("branchName",customerOB.getMainBranch());
						}	
						}else if(limitProfileTrxVal.getStagingLimitProfile()!=null){
							ICMSCustomer customerOB = null;
							customerOB = CustomerProxyFactory.getProxy().getCustomerByCIFSource(limitProfileTrxVal.getStagingLimitProfile().getLEReference(), null);
							if ((customerOB != null) && (ICMSConstant.LONG_INVALID_VALUE != customerOB.getLegalEntity().getLEID())) {
								// Customer is found
								resultMap.put("relationShipMgrName", getRelationshipMgr(customerOB.getRelationshipMgr()));
								resultMap.put("regionName",getRegionIdName(customerOB.getRmRegion()));
								resultMap.put("branchName",customerOB.getMainBranch());
							}	
						}
					obLimitProfile = limitProfileTrxVal.getStagingLimitProfile();
				}
				// if current status is other than ACTIVE & REJECTED, then show
				// workInProgress.
				// i.e. allow edit only if status is either ACTIVE or REJECTED
				if ((!limitProfileTrxVal.getStatus().equals(ICMSConstant.STATE_ACTIVE))
						&& (!limitProfileTrxVal.getStatus().equals(ICMSConstant.STATE_REJECTED_CREATE))
						&& (!limitProfileTrxVal.getStatus().equals(ICMSConstant.STATE_REJECTED))
						&& (!limitProfileTrxVal.getStatus().equals(ICMSConstant.STATE_REJECTED_DELETE))

				) {
					resultMap.put("wip", "wip");
					resultMap.put("InitialLimitProfile", limitProfileTrxVal.getStagingLimitProfile());
				}
				else {
					resultMap.put("limitProfileTrxVal", limitProfileTrxVal);

				}

				if (limitProfileTrxVal.getStagingLimitProfile() != null) {
					IBookingLocation bookingLoc = limitProfileTrxVal.getStagingLimitProfile().getOriginatingLocation();
					if (bookingLoc != null) {
						countryCode = bookingLoc.getCountryCode();
					}
				}
			}
			
			SBMIAAProxy proxy = helper.getSBMIAAProxy();
			//BigDecimal sanctLmt = new BigDecimal(customerOB.getTotalSanctionedLimit());
			resultMap.put("creditAprrovalList", getCreditApproverList(proxy.getCreditApproverList())); //shiv
			//resultMap.put("creditAprrovalList", getCreditApproverList(proxy.getCreditApproverListWithLimit(sanctLmt))); 
			
			resultMap.put("countryList", helper.getCountryList(team));
			resultMap.put("orgList", helper.getOrgList(countryCode, team));
			resultMap.put("sourceSystemList", helper.getSourceSystemList(countryCode, team));
			resultMap.put("event", event);
			resultMap.put("session.TrxId", trxId);
			resultMap.put("timefrequency.labels", CommonDataSingleton
					.getCodeCategoryLabels(AADetailHelper.TIME_FREQUENCY_CODE));
			resultMap.put("timefrequency.values", CommonDataSingleton
					.getCodeCategoryValues(AADetailHelper.TIME_FREQUENCY_CODE));
			resultMap.put("InitialLimitProfile", limitProfileTrxVal.getStagingLimitProfile());

			// set FrequencyUnit List
			resultMap.put("timefrequencies.map", AADetailHelper.buildTimeFrequencyMap());
			resultMap.put("indexChange", String.valueOf(ind));
			if("return_maker_edit_aadetail_reject".equals(event)){
				ILimitProfile limitObj = (ILimitProfile) map.get("limitObj");
				resultMap.put("InitialLimitProfile", limitObj);
				}
		}
		catch (LimitException e) {
			DefaultLogger.debug(this, "got exception in doExecute" + e);
			e.printStackTrace();
			throw (new CommandProcessingException(e.getMessage()));
		} catch (SearchDAOException e) {
			e.printStackTrace();
		} catch (RemoteException e) {
			e.printStackTrace();
			throw (new CommandProcessingException(e.getMessage()));
		}
		DefaultLogger.debug(this, "Going out of doExecute()");
		stagingrefid = limitProfileTrxVal.getStagingReferenceID();
		IOtherCovenantDetailsDAO othercovenantdetailsdaoimpl = (IOtherCovenantDetailsDAO)BeanHouse.get("otherCoveantDeatilsDAO");
		List otherCovenantDetailsList = null;
		if(stagingrefid != null)
		{
			try {
				otherCovenantDetailsList=othercovenantdetailsdaoimpl.getOtherCovenantDetailsStaging(stagingrefid);
				System.out.println("otherCovenantDetailsList----------------------->>>>>>>"+otherCovenantDetailsList);
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		if (otherCovenantDetailsList != null) {
			if (!otherCovenantDetailsList.isEmpty()) {
				for (int i = 0; i < otherCovenantDetailsList.size(); i++) {
					obothercovenant = (OBOtherCovenant) otherCovenantDetailsList
							.get(i);
					String othercovenantid = obothercovenant
							.getOtherCovenantId() + "";
					if (obothercovenant.getStatus() != null) {
						if (obothercovenant.getStatus().equalsIgnoreCase("ACTIVE") && (obothercovenant.getMonitoringResponsibilityList1() == null || obothercovenant.getMonitoringResponsibilityList1().isEmpty())) {
							String finalmonitoringresp = othercovenantdetailsdaoimpl
									.getOtherCovenantDetailsValuesStagingInString(
											othercovenantid);
							obothercovenant
									.setMonitoringResponsibilityList1(
											finalmonitoringresp);
						}
					}
					if (obothercovenant.getStatus() != null) {
						if (obothercovenant.getStatus().equalsIgnoreCase("ACTIVE") && obothercovenant.getFinalfaciltyName() == null) {
							String finalFacilityName = othercovenantdetailsdaoimpl
									.getOtherCovenantDetailsFacilityValuesStaging(
											othercovenantid);
							obothercovenant
									.setFinalfaciltyName(finalFacilityName);
						}
					}
				}
			}
			resultMap.put("otherCovenantDetailsList",otherCovenantDetailsList);
		}
		
		
		resultMap.put("riskGradeList",getGradeList());//Shiv
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
