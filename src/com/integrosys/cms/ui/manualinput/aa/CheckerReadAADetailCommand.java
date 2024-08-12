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
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.customer.bus.ICMSCustomer;
import com.integrosys.cms.app.customer.proxy.CustomerProxyFactory;
import com.integrosys.cms.app.geography.bus.NoSuchGeographyException;
import com.integrosys.cms.app.geography.region.bus.IRegionDAO;
import com.integrosys.cms.app.limit.bus.LimitException;
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
import com.integrosys.component.commondata.app.CommonDataSingleton;

/**
 * Describe this class. Purpose: for checker to read the transaction
 * Description: command that let the checker to read the transaction that being
 * make by the maker
 * 
 * @author $Author: Jerlin$<br>
 * @version $Revision:$
 * @since $Date:$ Tag: $Name$
 */

public class CheckerReadAADetailCommand extends AbstractCommand implements ICommonEventConstant {

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
				 { "mainEventIdentifier", "java.lang.String", REQUEST_SCOPE },
				{ "mainEventIdentifier", "java.lang.String", SERVICE_SCOPE },
				{ "event", "java.lang.String", REQUEST_SCOPE } });
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
				{ "timefrequencies.map", "java.util.HashMap", SERVICE_SCOPE },
				{ "InitialLimitProfile", "java.lang.Object", FORM_SCOPE },
				{ "event", "java.lang.String", REQUEST_SCOPE }, { "TrxId", "java.lang.String", REQUEST_SCOPE },
				{ "creditAprrovalList", "java.util.List", SERVICE_SCOPE },
				{ "mainEventIdentifier", "java.lang.String", REQUEST_SCOPE },
				{ "mainEventIdentifier", "java.lang.String", SERVICE_SCOPE },
				{ "riskGradeList", "java.util.List", SERVICE_SCOPE },
				 { "session.TrxId", "java.lang.String", SERVICE_SCOPE },
				{ "relationShipMgrName", "java.lang.String", SERVICE_SCOPE },
				{ "regionName", "java.lang.String", SERVICE_SCOPE },
				{ "branchName", "java.lang.String", SERVICE_SCOPE },
				{ "otherCovenantDetailsList", "java.util.List", SERVICE_SCOPE }
				
				
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
		HashMap returnMap = new HashMap();
		HashMap resultMap = new HashMap();
		List otherCovenantDetailsList = null;
		String stagingrefid = null;
		String event = (String) map.get("event");
		String trxId = (String) map.get("TrxId");
		if(trxId==null||"".equals(trxId)){
			trxId=(String) map.get("session.TrxId");
		}
		trxId = trxId.trim();
		DefaultLogger.debug(this, "Inside doExecute()  = " + trxId);
		String mainEventIdentifier = event;
		resultMap.put("mainEventIdentifier", mainEventIdentifier);
		OBTrxContext trxContext = (OBTrxContext) map.get("theOBTrxContext");

		try {
			ILimitProxy proxy = LimitProxyFactory.getProxy();

			ILimitProfileTrxValue limitProfileTrxVal = proxy.getTrxLimitProfile(trxId);
			if(limitProfileTrxVal !=null){
				stagingrefid =limitProfileTrxVal.getStagingReferenceID();
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
			/*if(limitProfileTrxVal.getLimitProfile()!=null){
			resultMap.put("relationShipMgrName", getRelationshipMgr(limitProfileTrxVal.getLimitProfile().getRelationshipManager())); //shiv
			}
			if(limitProfileTrxVal.getStagingLimitProfile()!=null){
			resultMap.put("regionName",getRegionIdName(limitProfileTrxVal.getStagingLimitProfile().getControllingBranch()));
			}*/
			
			resultMap.put("limitProfileTrxVal", limitProfileTrxVal);
			resultMap.put("InitialLimitProfile", limitProfileTrxVal.getStagingLimitProfile());
			}
			// set FrequencyUnit List
			resultMap.put("timefrequencies.map", AADetailHelper.buildTimeFrequencyMap());
			resultMap.put("session.TrxId", trxId);
			resultMap.put("event", event);
			
			AAUIHelper helper = new AAUIHelper();
			SBMIAAProxy proxy1 = helper.getSBMIAAProxy();
			//BigDecimal sanctLmt = new BigDecimal(limitProfileTrxVal.getLimitProfile().getTotalSactionedAmount());
			resultMap.put("creditAprrovalList", getCreditApproverList(proxy1.getCreditApproverList())); //shiv
			//resultMap.put("creditAprrovalList", getCreditApproverList(proxy1.getCreditApproverListWithLimit(sanctLmt))); 

		}
		catch (LimitException e) {
			DefaultLogger.debug(this, "got exception in doExecute" + e);
			DefaultLogger.error(this, e);
			throw (new CommandProcessingException(e.getMessage()));
		} catch (SearchDAOException e) {
			e.printStackTrace();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		
		IOtherCovenantDetailsDAO othercovenantdetailsdaoimpl = (IOtherCovenantDetailsDAO)BeanHouse.get("otherCoveantDeatilsDAO");
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
		
		if(otherCovenantDetailsList != null)
		{
			resultMap.put("otherCovenantDetailsList",otherCovenantDetailsList);
		}
		DefaultLogger.debug(this, "Going out of doExecute()");
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
