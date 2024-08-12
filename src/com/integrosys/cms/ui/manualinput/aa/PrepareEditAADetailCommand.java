/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:$
 */
package com.integrosys.cms.ui.manualinput.aa;

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
import com.integrosys.base.uiinfra.exception.AccessDeniedException;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.collateral.bus.CollateralDAOFactory;
import com.integrosys.cms.app.common.bus.IBookingLocation;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.geography.bus.NoSuchGeographyException;
import com.integrosys.cms.app.geography.region.bus.IRegionDAO;
import com.integrosys.cms.app.limit.bus.ILimitProfile;
import com.integrosys.cms.app.limit.bus.LimitDAO;
import com.integrosys.cms.app.limit.bus.LimitException;
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
import com.integrosys.component.bizstructure.app.bus.ITeam;
import com.integrosys.component.commondata.app.CommonDataSingleton;

/**
 * Describe this class. Purpose: to prepare the AA value to be edit Description:
 * command that get the value from database to let the user to edit
 * 
 * @author $Author: Jerlin$<br>
 * @version $Revision:$
 * @since $Date:$ Tag: $Name$
 */

public class PrepareEditAADetailCommand extends AbstractCommand implements ICommonEventConstant {

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
				{ IGlobalConstant.USER_TEAM, "com.integrosys.component.bizstructure.app.bus.ITeam", GLOBAL_SCOPE },
				{ "InitialLimitProfile", "com.integrosys.cms.app.limit.bus.OBLimitProfile", FORM_SCOPE },
				{ "event", "java.lang.String", REQUEST_SCOPE }, { "indexChange", "java.lang.String", REQUEST_SCOPE },
				{ "preEvent", "java.lang.String", REQUEST_SCOPE },
				{ "limitObj", "com.integrosys.cms.app.limit.bus.OBLimitProfile", SERVICE_SCOPE },
				{ "session.ind", "java.lang.String", SERVICE_SCOPE },
				{ "session.InitialLimitProfile", "java.lang.Object", SERVICE_SCOPE },
				{ "session.obLimitProfile", "com.integrosys.cms.app.limit.bus.ILimitProfile", SERVICE_SCOPE }});
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
				{ "InitialLimitProfile", "java.lang.Object", FORM_SCOPE },
				{ "migratedFlag", "java.lang.String", SERVICE_SCOPE },
				{ "creditAprrovalList", "java.util.List", REQUEST_SCOPE },
				{ "riskGradeList", "java.util.List", REQUEST_SCOPE },
				{ "relationShipMgrName", "java.lang.String", REQUEST_SCOPE },
				{ "event", "java.lang.String", REQUEST_SCOPE },
				{ "session.ind", "java.lang.String", SERVICE_SCOPE },
				{ "regionName", "java.lang.String", REQUEST_SCOPE },
				{ "branchName", "java.lang.String", SERVICE_SCOPE },
				
		});
	}

	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException,
			AccessDeniedException {
		HashMap returnMap = new HashMap();
		HashMap resultMap = new HashMap();
		OBTrxContext trxContext = (OBTrxContext) map.get("theOBTrxContext");
		ILimitProfile obLimitProfile = (ILimitProfile) map.get("InitialLimitProfile");
		if(obLimitProfile==null){
			obLimitProfile=(ILimitProfile) map.get("session.InitialLimitProfile");
		}
		ILimitProfileTrxValue limitProfileTrxVal = null;
		
		String countryCode = null;
		AAUIHelper helper = new AAUIHelper();
		ITeam team = (ITeam) (map.get(IGlobalConstant.USER_TEAM));
		String event = (String) map.get("event");
		String ind = (String) map.get("indexChange");
		if(ind==null||"null".equals(ind)||"".equals(ind)){
			ind = (String) map.get("session.ind");
		}
		
		int indexChange = Integer.parseInt(ind);
		// DefaultLogger.debug(this, "Inside doExecute()  event= " + event +
		// ", LimitProfileID="+ obLimitProfile.getLimitProfileID());
		IBookingLocation bookingLoc = null;
		try {

			if (indexChange == 0) {
				ILimitProxy proxy = LimitProxyFactory.getProxy();
				limitProfileTrxVal = proxy.getTrxLimitProfile(obLimitProfile.getLimitProfileID());
				// if current status is other than ACTIVE & REJECTED, then show
				// workInProgress.
				// i.e. allow edit only if status is either ACTIVE or REJECTED
				if (!((limitProfileTrxVal.getStatus().equals(ICMSConstant.STATE_ND)) || (limitProfileTrxVal.getStatus()
						.equals(ICMSConstant.STATE_ACTIVE)))) {
					resultMap.put("wip", "wip");
					resultMap.put("InitialLimitProfile", limitProfileTrxVal.getStagingLimitProfile());

				}
				else {
					resultMap.put("limitProfileTrxVal", limitProfileTrxVal);
				}

				resultMap.put("InitialLimitProfile", limitProfileTrxVal.getLimitProfile());

				bookingLoc = limitProfileTrxVal.getLimitProfile().getOriginatingLocation();
				if (bookingLoc != null) {
					countryCode = bookingLoc.getCountryCode();
				}

			}
			else {
				limitProfileTrxVal = (ILimitProfileTrxValue) map.get("limitProfileTrxVal");

				if ("refresh_maker_edit_aadetail".equals(event) || "refresh_maker_edit_aadetail_reject".equals(event)) {
					bookingLoc = obLimitProfile.getOriginatingLocation();
					if (bookingLoc != null) {
						countryCode = bookingLoc.getCountryCode();
					}

					limitProfileTrxVal.setStagingLimitProfile(obLimitProfile);

					String preEvent = (String) map.get("preEvent");
					resultMap.put("preEvent", preEvent);

				}
				else {
					bookingLoc = limitProfileTrxVal.getStagingLimitProfile().getOriginatingLocation();
					if (bookingLoc != null) {
						countryCode = bookingLoc.getCountryCode();
					}

				}
				
				resultMap.put("limitProfileTrxVal", limitProfileTrxVal);
				resultMap.put("InitialLimitProfile", limitProfileTrxVal.getStagingLimitProfile());
				
			}
			SBMIAAProxy proxy = helper.getSBMIAAProxy();
			resultMap.put("creditAprrovalList", getCreditApproverList(proxy.getCreditApproverList())); //shiv
			
			
			/*helper.canAccess(team, bookingLoc);
			resultMap.put("countryList", helper.getCountryList(team));
			resultMap.put("orgList", helper.getOrgList(countryCode, team));*/
			/*
			 * if(countryCode != null){ SourceSystemList sourceSystem =
			 * SourceSystemList.getInstance (countryCode); Collection
			 * sourceSystemListID = sourceSystem.getSourceSystemListID();
			 * Collection sourceSystemListValue =
			 * sourceSystem.getSourceSystemListValue ();
			 * resultMap.put("sourceSystemListID", sourceSystemListID);
			 * resultMap.put("sourceSystemListValue", sourceSystemListValue);
			 * 
			 * }
			 */
			LimitDAO limitDao = new LimitDAO();
			try {
			String migratedFlag = "N";	
			boolean status = false;	
			 status = limitDao.getCAMMigreted("SCI_LSP_LMT_PROFILE",limitProfileTrxVal.getLimitProfile().getLimitProfileID(),"CMS_LSP_LMT_PROFILE_ID");
			
			if(status)
			{
				migratedFlag= "Y";
			}
			resultMap.put("migratedFlag", migratedFlag);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			resultMap.put("relationShipMgrName", getRelationshipMgr(limitProfileTrxVal.getLimitProfile().getRelationshipManager())); //shiv
			resultMap.put("regionName",getRegionIdName(limitProfileTrxVal.getLimitProfile().getControllingBranch()));//shiv
			resultMap.put("branchName",limitProfileTrxVal.getLimitProfile().getControllingBranch());//Govind S
			resultMap.put("event",event);
			resultMap.put("session.ind",ind);
			resultMap.put("riskGradeList",getGradeList());//Shiv
			//resultMap.put("sourceSystemList", helper.getSourceSystemList(countryCode, team));
			if("return_maker_edit_aadetail".equals(event)){
				ILimitProfile limitObj = (ILimitProfile) map.get("limitObj");
				resultMap.put("InitialLimitProfile", limitObj);
				}

			resultMap.put("timefrequency.labels", CommonDataSingleton.getCodeCategoryLabels(ICMSUIConstant.TIME_FREQ));
			resultMap.put("timefrequency.values", CommonDataSingleton.getCodeCategoryValues(ICMSUIConstant.TIME_FREQ));

		}
		catch (LimitException e) {
			DefaultLogger.debug(this, "got exception in doExecute" + e);
			e.printStackTrace();
			throw (new CommandProcessingException(e.getMessage()));
		} catch (SearchDAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			throw (new CommandProcessingException(e.getMessage()));
		}

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
	

	//Shiv 200911
	//Start:Code added to display Relationship Mgr Name instead of Relationship Mgr Code
	public String getRelationshipMgr(String relMgrId) {
		IRelationshipMgrDAO relationshipmgr = (IRelationshipMgrDAO)BeanHouse.get("relationshipMgrDAO");
		String	strRelationshipmgr = "";
		if(relMgrId != null){
			strRelationshipmgr = relationshipmgr.getRelationshipMgrById(Long.parseLong(relMgrId)).getRelationshipMgrName();
		}
		return strRelationshipmgr;
	}
	
	//Shiv 200911
	//Start:Code added to display Relationship Mgr Name instead of Relationship Mgr Code
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
