/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:$
 */
package com.integrosys.cms.ui.manualinput.aa;

import java.math.BigDecimal;
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
import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.collateral.bus.CollateralDAOFactory;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.customer.bus.CustomerDAO;
import com.integrosys.cms.app.customer.bus.CustomerDAOFactory;
import com.integrosys.cms.app.customer.bus.ICMSCustomer;
import com.integrosys.cms.app.customer.bus.ICustomerDAO;
import com.integrosys.cms.app.customer.proxy.CustomerProxyFactory;
import com.integrosys.cms.app.feed.proxy.forex.IForexFeedProxy;
import com.integrosys.cms.app.geography.bus.NoSuchGeographyException;
import com.integrosys.cms.app.geography.region.bus.IRegionDAO;
import com.integrosys.cms.app.limit.bus.ILimitProfile;
import com.integrosys.cms.app.limit.bus.LimitDAO;
import com.integrosys.cms.app.limit.bus.LimitException;
import com.integrosys.cms.app.limit.bus.LimitListSummaryItemBase;
import com.integrosys.cms.app.limit.proxy.ILimitProxy;
import com.integrosys.cms.app.limit.proxy.LimitProxyFactory;
import com.integrosys.cms.app.limit.trx.ILimitProfileTrxValue;
import com.integrosys.cms.app.manualinput.aa.proxy.SBMIAAProxy;
import com.integrosys.cms.app.manualinput.limit.proxy.SBMILmtProxy;
import com.integrosys.cms.app.relationshipmgr.bus.IRelationshipMgrDAO;
import com.integrosys.cms.app.systemBankBranch.bus.ISystemBankBranch;
import com.integrosys.cms.app.transaction.OBTrxContext;
import com.integrosys.cms.ui.collateral.CategoryCodeConstant;
import com.integrosys.cms.ui.common.constant.ICMSUIConstant;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;
import com.integrosys.cms.ui.manualinput.CommonUtil;
import com.integrosys.cms.ui.manualinput.limit.MILimitUIHelper;
import com.integrosys.component.commondata.app.CommonDataSingleton;

/**
 * Describe this class. Purpose: to prepare the AA value to be view Description:
 * command that get the value from database to let the user to view
 * 
 * @author $Author: Jerlin$<br>
 * @version $Revision:$
 * @since $Date:$ Tag: $Name$
 */

public class PrepareViewAADetailCommand extends AbstractCommand implements ICommonEventConstant {

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
				
				{ "InitialLimitProfile", "com.integrosys.cms.app.limit.bus.OBLimitProfile", FORM_SCOPE },
				{ "aaID", "java.lang.String", REQUEST_SCOPE }, { "event", "java.lang.String", REQUEST_SCOPE },
				{ "mainEventIdentifier", "java.lang.String", REQUEST_SCOPE }, 
				{ "mainEventIdentifier", "java.lang.String", SERVICE_SCOPE },
				{ "session.aaID", "java.lang.String", SERVICE_SCOPE }});
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
				{ "InitialLimitProfile", "java.lang.Object", FORM_SCOPE },
				{ "session.InitialLimitProfile", "java.lang.Object", SERVICE_SCOPE },
				{ "transactionHistoryList", "java.util.List", SERVICE_SCOPE },
				{ "migratedFlag", "java.lang.String", SERVICE_SCOPE },
				{ "creditAprrovalList", "java.util.List", SERVICE_SCOPE },
				{ "riskGradeList", "java.util.List", SERVICE_SCOPE },
				{ "event", "java.lang.String", REQUEST_SCOPE },
				{ "relationShipMgrName", "java.lang.String", SERVICE_SCOPE },
				{ "totalSanctionedAmtFac", "java.lang.String", SERVICE_SCOPE },
				{ "regionName", "java.lang.String", SERVICE_SCOPE },
				{ "branchName", "java.lang.String", SERVICE_SCOPE },
				{ "customerId", "java.lang.String", SERVICE_SCOPE },
				{ "customerId", "java.lang.String", SERVICE_SCOPE },
				{ "session.obLimitProfile", "com.integrosys.cms.app.limit.bus.ILimitProfile", SERVICE_SCOPE },
				{ "session.aaID", "java.lang.String", SERVICE_SCOPE }
				
				
		});
		}

	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
		HashMap returnMap = new HashMap();
		HashMap resultMap = new HashMap();
		OBTrxContext trxContext = (OBTrxContext) map.get("theOBTrxContext");
		ILimitProfile obLimitProfile = (ILimitProfile) map.get("InitialLimitProfile");
		ILimitProfileTrxValue limitProfileTrxVal = null;
		String event = (String) map.get("event");
		String aaID = (String) map.get("aaID");
		if(aaID==null || aaID.equals("")){
			aaID=(String) map.get("session.aaID");
		}
		String mainEventIdentifier= event;
		resultMap.put("mainEventIdentifier", mainEventIdentifier);
		DefaultLogger.debug(this, "Inside doExecute()  event= " + event + ", LimitProfileID=" + aaID);
		try {
			

			if (!(event.equals("maker_add_aadetail_confirm_error") || event.equals("maker_edit_aadetail_confirm_error"))) {
				ILimitProxy proxy = LimitProxyFactory.getProxy();

				 limitProfileTrxVal = proxy.getTrxLimitProfile(Long.parseLong(aaID));

				if ("maker_delete_aadetail".equals(event)) {
					if (!((limitProfileTrxVal.getStatus().equals(ICMSConstant.STATE_ND)) || (limitProfileTrxVal
							.getStatus().equals(ICMSConstant.STATE_ACTIVE)))) {
						resultMap.put("wip", "wip");
						resultMap.put("InitialLimitProfile", limitProfileTrxVal.getLimitProfile());

					}
					else {
						resultMap.put("limitProfileTrxVal", limitProfileTrxVal);
					}
					resultMap.put("InitialLimitProfile", limitProfileTrxVal.getLimitProfile());
					

				}
				else {
					MILimitUIHelper helper = new MILimitUIHelper();
					LimitListSummaryItemBase objlmtListSummary=null;
					ICMSCustomer customerOB = null;
					BigDecimal exchangeRate = new BigDecimal("1");
					BigDecimal  sancAmountVal = new BigDecimal("0");
					customerOB = CustomerProxyFactory.getProxy().getCustomerByCIFSource(limitProfileTrxVal.getLimitProfile().getLEReference(), null);
					SBMILmtProxy sbiproxy = helper.getSBMILmtProxy();
					List lmtList = sbiproxy.getLimitSummaryListByAA(aaID);
					for(int i=0;i<lmtList.size();i++)
					{
						objlmtListSummary=new LimitListSummaryItemBase();
						objlmtListSummary=(LimitListSummaryItemBase)lmtList.get(i);
						
						if( !AbstractCommonMapper.isEmptyOrNull(objlmtListSummary.getCurrencyCode())){
							IForexFeedProxy frxPxy = (IForexFeedProxy)BeanHouse.get("forexFeedProxy");
							 exchangeRate = frxPxy.getExchangeRateWithINR(objlmtListSummary.getCurrencyCode().trim());
						}
						
						if(objlmtListSummary.getActualSecCoverage()!=null){
							 sancAmountVal = exchangeRate.multiply(new BigDecimal(objlmtListSummary.getActualSecCoverage().replaceAll(",", ""))).add(sancAmountVal);
						}
						
						if(objlmtListSummary.getIsAdhoc() !=null && objlmtListSummary.getIsAdhoc().equals("Y") && objlmtListSummary.getAdhocAmount() != null){
							 sancAmountVal = exchangeRate.multiply(new BigDecimal(objlmtListSummary.getAdhocAmount().replaceAll(",", ""))).add(sancAmountVal);
						}
					}
					
					if ((customerOB != null) && (ICMSConstant.LONG_INVALID_VALUE != customerOB.getLegalEntity().getLEID())) {
						// Customer is found
						resultMap.put("relationShipMgrName", getRelationshipMgr(customerOB.getRelationshipMgr()));
						resultMap.put("regionName",getRegionIdName(customerOB.getRmRegion()));
						resultMap.put("branchName",customerOB.getMainBranch());
						resultMap.put("customerId",String.valueOf(customerOB.getCustomerID()));
						
						
					}
					ICustomerDAO customerDAO = CustomerDAOFactory.getDAO();
				//	List transactionHistoryList = customerDAO.getTransactionHistoryList(limitProfileTrxVal.getTransactionID());
					List transactionHistoryList = customerDAO.getTransactionCAMHistoryList(limitProfileTrxVal.getTransactionID(),customerOB.getCMSLegalEntity().getLEReference());
					limitProfileTrxVal.getLimitProfile().setTotalSanctionedAmountFacLevel(sancAmountVal.toString());
					//resultMap.put("relationShipMgrName", getRelationshipMgr(limitProfileTrxVal.getLimitProfile().getRelationshipManager())); //shiv
					//resultMap.put("regionName",getRegionIdName(limitProfileTrxVal.getStagingLimitProfile().getControllingBranch()));
					resultMap.put("totalSanctionedAmtFac",sancAmountVal.toString());
					resultMap.put("transactionHistoryList", transactionHistoryList);
					resultMap.put("limitProfileTrxVal", limitProfileTrxVal);
					resultMap.put("session.aaID", aaID);
					resultMap.put("session.obLimitProfile",obLimitProfile);
					resultMap.put("event", event);
					resultMap.put("InitialLimitProfile", limitProfileTrxVal.getLimitProfile());
				}

			}
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
			AAUIHelper helper = new AAUIHelper();
			SBMIAAProxy proxy = helper.getSBMIAAProxy();
			resultMap.put("creditAprrovalList", getCreditApproverList(proxy.getCreditApproverList())); //shiv
			resultMap.put("session.InitialLimitProfile", limitProfileTrxVal.getLimitProfile());
			resultMap.put("riskGradeList",getGradeList());//Shiv
			resultMap.put("timefrequency.labels", CommonDataSingleton.getCodeCategoryLabels(ICMSUIConstant.TIME_FREQ));
			resultMap.put("timefrequency.values", CommonDataSingleton.getCodeCategoryValues(ICMSUIConstant.TIME_FREQ));

		}
		catch (LimitException e) {
			DefaultLogger.debug(this, "got exception in doExecute" + e);
			e.printStackTrace();
			throw (new CommandProcessingException(e.getMessage()));
		} catch (SearchDAOException e) {
			e.printStackTrace();
		} catch (RemoteException e) {
			e.printStackTrace();
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
