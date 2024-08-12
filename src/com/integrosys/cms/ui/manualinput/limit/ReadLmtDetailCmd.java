/*
 * Created on Feb 11, 2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.integrosys.cms.ui.manualinput.limit;

import static com.integrosys.cms.ui.manualinput.IManualInputConstants.CO_BORROWER_LIST;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.TreeMap;

import org.apache.struts.util.LabelValueBean;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.AccessDeniedException;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.checklist.bus.CheckListDAOFactory;
import com.integrosys.cms.app.checklist.bus.ICheckListDAO;
import com.integrosys.cms.app.collateral.bus.CollateralDAOFactory;
import com.integrosys.cms.app.collateral.bus.ICollateral;
import com.integrosys.cms.app.collateral.bus.type.property.subtype.comgeneral.OBCommercialGeneral;
import com.integrosys.cms.app.collateralNewMaster.bus.ICollateralNewMaster;
import com.integrosys.cms.app.collateralNewMaster.bus.ICollateralNewMasterJdbc;
import com.integrosys.cms.app.common.bus.IBookingLocation;
import com.integrosys.cms.app.customer.bus.CustomerDAOFactory;
import com.integrosys.cms.app.customer.bus.ICMSCustomer;
import com.integrosys.cms.app.customer.bus.ICoBorrowerDetails;
import com.integrosys.cms.app.customer.bus.ICustomerDAO;
import com.integrosys.cms.app.customer.bus.ILimitXRefCoBorrower;
import com.integrosys.cms.app.customer.bus.OBCoBorrowerDetails;
import com.integrosys.cms.app.customer.proxy.CustomerProxyFactory;
import com.integrosys.cms.app.customer.proxy.ICustomerProxy;
import com.integrosys.cms.app.facilityNewMaster.bus.IFacilityNewMaster;
import com.integrosys.cms.app.facilityNewMaster.bus.IFacilityNewMasterJdbc;
import com.integrosys.cms.app.feed.bus.forex.IForexFeedEntry;
import com.integrosys.cms.app.feed.proxy.forex.IForexFeedProxy;
import com.integrosys.cms.app.limit.bus.ICollateralAllocation;
import com.integrosys.cms.app.limit.bus.IFacilityCoBorrowerDetails;
import com.integrosys.cms.app.limit.bus.ILimit;
import com.integrosys.cms.app.limit.bus.ILimitDAO;
import com.integrosys.cms.app.limit.bus.ILimitProfile;
import com.integrosys.cms.app.limit.bus.ILimitSysXRef;
import com.integrosys.cms.app.limit.bus.LimitDAO;
import com.integrosys.cms.app.limit.bus.LimitDAOFactory;
import com.integrosys.cms.app.limit.bus.OBFacilityCoBorrowerDetails;
import com.integrosys.cms.app.limit.bus.OBLimitSysXRef;
import com.integrosys.cms.app.limit.proxy.ILimitProxy;
import com.integrosys.cms.app.limit.proxy.LimitProxyFactory;
import com.integrosys.cms.app.limit.trx.ILimitProfileTrxValue;
import com.integrosys.cms.app.limit.trx.ILimitTrxValue;
import com.integrosys.cms.app.manualinput.limit.proxy.SBMILmtProxy;
import com.integrosys.cms.app.relationshipmgr.bus.IRelationshipMgrDAO;
import com.integrosys.cms.app.riskType.bus.IRiskType;
import com.integrosys.cms.app.riskType.proxy.IRiskTypeProxyManager;
import com.integrosys.cms.app.transaction.OBTrxContext;
import com.integrosys.cms.ui.common.UIUtil;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;
import com.integrosys.cms.ui.manualinput.CommonUtil;
import com.integrosys.cms.ui.manualinput.aa.AAUIHelper;
import com.integrosys.cms.ui.manualinput.security.MISecurityUIHelper;
import com.integrosys.component.bizstructure.app.bus.ITeam;

/**
 * @author Administrator
 * 
 *         TODO To change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Style - Code Templates
 */
public class ReadLmtDetailCmd extends AbstractCommand {
	public String[][] getParameterDescriptor() {
		return (new String[][] {{ "lmtTrxObj", "com.integrosys.cms.app.limit.trx.ILimitTrxValue", SERVICE_SCOPE },
				{ "limitId", "java.lang.String", REQUEST_SCOPE },
				{ "limitProfileID", "java.lang.String", REQUEST_SCOPE },
				{ "customerID", "java.lang.String", REQUEST_SCOPE }, 
				{ IGlobalConstant.USER_TEAM, "com.integrosys.component.bizstructure.app.bus.ITeam", GLOBAL_SCOPE },
				{ "trxID", "java.lang.String", REQUEST_SCOPE }, { "event", "java.lang.String", REQUEST_SCOPE },
				{ "theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE },
				{ "sessionCriteria", "java.lang.String", REQUEST_SCOPE },
				{ "facCoBorrowerList", "java.util.List", SERVICE_SCOPE },
								
		});
	}

	public String[][] getResultDescriptor() {
		return (new String[][] { { "lmtTrxObj", "com.integrosys.cms.app.limit.trx.ILimitTrxValue", SERVICE_SCOPE },
				{ "lmtDetailForm", "java.lang.Object", FORM_SCOPE },
				{ "wip", "java.lang.String", REQUEST_SCOPE },
				{ "event", "java.lang.String", REQUEST_SCOPE },
				{ "trxID", "java.lang.String", SERVICE_SCOPE },
				{ "relationShipMgrName", "java.lang.String", REQUEST_SCOPE },
				{ "event", "java.lang.String", REQUEST_SCOPE },
				{ "limitId", "java.lang.String", REQUEST_SCOPE },
				{ "transactionHistoryList", "java.util.List", SERVICE_SCOPE },
				{ "collateralMap", "java.util.HashMap", SERVICE_SCOPE },
				{ "fundedAmount", "java.lang.String", REQUEST_SCOPE },
				{ "nonFundedAmount", "java.lang.String", REQUEST_SCOPE  },
				{ "memoExposer", "java.lang.String", REQUEST_SCOPE },
				{ "sanctionedLimit", "java.lang.String", REQUEST_SCOPE },
				{ "inrValue", "java.lang.String", SERVICE_SCOPE },
				{ "customerID", "java.lang.String", REQUEST_SCOPE }, 
				{ "limitProfileID", "java.lang.String", REQUEST_SCOPE },
				{ "migratedFlag", "java.lang.String", SERVICE_SCOPE },
				{ "lmtId", "java.lang.String", SERVICE_SCOPE },
				{ "subPartyNameList", "java.util.List", SERVICE_SCOPE },
				{ "currencyList", "java.util.List", REQUEST_SCOPE },
				{ "riskTypeList", "java.util.List", REQUEST_SCOPE },
				{"allowToDeleteFacility","java.lang.Boolean",REQUEST_SCOPE}, // Uma Khot:Don't Delete the facility if facility doc pending in case creation.
				{"facilityChklistDocPending","java.lang.Boolean",REQUEST_SCOPE},
		        {"isCollDeletedFlag","java.lang.Boolean",REQUEST_SCOPE},
				{"checkFacilityDocumentsIsPendingReceived","java.lang.Boolean",REQUEST_SCOPE},
				{"checkFacilityDocumentsIsReceived","java.lang.Boolean",REQUEST_SCOPE},
				{"collateralSet","java.util.HashSet",SERVICE_SCOPE},
				{"pendingPropertySecCount","java.lang.Boolean",REQUEST_SCOPE},
				{ "checklistIsActive", "java.lang.Boolean", SERVICE_SCOPE },
				{ "sessionCriteria", "java.lang.String", REQUEST_SCOPE },
				{ "status", "java.lang.String", REQUEST_SCOPE },
				{ CO_BORROWER_LIST, List.class.getName(), SERVICE_SCOPE },
				{ "facCoBorrowerList", "java.util.List", SERVICE_SCOPE },
				{ "facCoBorrowerLiabIds", "java.lang.String", SERVICE_SCOPE },
				{ "LineCoBorrowIds", "java.util.List", SERVICE_SCOPE },
				{ "availAndOptionApplicable", "java.lang.String", REQUEST_SCOPE },

				{ "fromEvent", "java.lang.String", REQUEST_SCOPE },
				});
				
	}

	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException,
			AccessDeniedException {
		HashMap result = new HashMap();
		HashMap exceptionMap = new HashMap();
		HashMap temp = new HashMap();
		String lmtIdValue = "";
		DefaultLogger.debug(this, "in ReadLmtDetailCmd.java ==108==>> (map.get(limitId))");
		String lmtID = (String) (map.get("limitId")); //CMS_LSP_APPR_LMTS_ID
		DefaultLogger.debug(this, "in ReadLmtDetailCmd.java ==108==>> (map.get(limitId))"+lmtID);
		boolean flag = false;
		ILimitProfileTrxValue limitProfileTrxVal = null;
		try {
			OBTrxContext ctx = (OBTrxContext) map.get("theOBTrxContext");
			String event = (String) (map.get("event"));
			String limitProfileID = (String) (map.get("limitProfileID"));			
			DefaultLogger.debug(this, "in ReadLmtDetailCmd.java ==114==>> limitProfileID"+ limitProfileID);
			//long lmtProfId = Long.parseLong(limitProfileID);
			//ILimitProxy limitProxy = LimitProxyFactory.getProxy();
			//ILimitProfile profile = limitProxy.getLimitProfile(lmtProfId);  
			//String customerID = (String) (map.get("customerID"));
			long customerID =0;
			if(null==map.get("customerID") || "".equals(map.get("customerID"))) {
				   LimitDAO limitDao = new LimitDAO();
				   customerID =  Long.parseLong((String)(limitDao.getLimitCustomerId(limitProfileID)));
			}
			else {
			 customerID = Long.parseLong((String) (map.get("customerID")));
			}
			DefaultLogger.debug(this, "in ReadLmtDetailCmd.java ==119==>> profile.getCustomerID()"+ customerID);
			ICustomerProxy custProxy = CustomerProxyFactory.getProxy();
			ICMSCustomer cust = custProxy.getCustomer(customerID);
			
			MILimitUIHelper helper = new MILimitUIHelper();
			ITeam team = (ITeam) (map.get(IGlobalConstant.USER_TEAM));

			SBMILmtProxy proxy = helper.getSBMILmtProxy();
			ILimitTrxValue lmtTrxObj = null;
			DefaultLogger.debug(this, "****************** INSIDE ReadLmtDetailCmd EVENT IS: " + event);
			String trxID = (String) (map.get("trxID"));
			DefaultLogger.debug(this, "********** Trasanction ID IS 151: " + trxID);
			if (EventConstant.EVENT_PREPARE_CLOSE.equals(event) || EventConstant.EVENT_PROCESS_UPDATE.equals(event)
					|| EventConstant.EVENT_TRACK.equals(event) || EventConstant.EVENT_PROCESS.equals(event)
					|| EventConstant.EVENT_PROCESS_DELETE.equals(event)) {
				DefaultLogger.debug(this, "**********Start Trasanction ID IS: 155" + trxID);
				lmtTrxObj = proxy.searchLimitByTrxId(trxID);
				DefaultLogger.debug(this, "**********End Trasanction ID IS: 157" + trxID);
			}
			else {
				flag = true;
				DefaultLogger.debug(this, "********** Before searchLimitByLmtId function 160  lmtID" + lmtID);
				lmtTrxObj = proxy.searchLimitByLmtId(lmtID);
				DefaultLogger.debug(this, "********** after searchLimitByLmtId function 162 lmtTrxObj" + lmtTrxObj);
			}
			ILimitSysXRef[] limitSysXRefs =null;
			if(lmtTrxObj.getLimit() != null && lmtTrxObj.getLimit().getLimitSysXRefs()!=null) {
				limitSysXRefs = lmtTrxObj.getLimit().getLimitSysXRefs();
				TreeMap sortedMap= new TreeMap();
				for (int i = 0; i < limitSysXRefs.length; i++) {
					ILimitSysXRef iLimitSysXRef = limitSysXRefs[i];					String serialNo=iLimitSysXRef.getCustomerSysXRef().getSerialNo();
					if(null==serialNo || serialNo.equals("")) {
						serialNo = iLimitSysXRef.getCustomerSysXRef().getHiddenSerialNo();
					}
					if(!"HIDE".equalsIgnoreCase(iLimitSysXRef.getCustomerSysXRef().getStatus()))
					sortedMap.put(iLimitSysXRef.getCustomerSysXRef().getFacilitySystemID()+serialNo, iLimitSysXRef);
					Collection values = sortedMap.values();
					lmtTrxObj.getLimit().setLimitSysXRefs((ILimitSysXRef[])values.toArray(new ILimitSysXRef[values.size()]));
				}
			}
			String lmtId ="";
			String trxStatusOfFacUpdateUpload = "";
			if(null != lmtTrxObj) {
				if(null != lmtTrxObj.getLimit()) {
			lmtId = (String)lmtTrxObj.getLimit().getLimitRef();
			LimitDAO limitDao = new LimitDAO();
			trxStatusOfFacUpdateUpload = limitDao.getFacilityUpdateUploadTransactionStatus(lmtId);
			}
			}
			ICustomerDAO customerDAO = CustomerDAOFactory.getDAO();
			List transactionHistoryList = customerDAO.getTransactionHistoryList(lmtTrxObj.getTransactionID());
			result.put("transactionHistoryList", transactionHistoryList);
			result.put("lmtTrxObj", lmtTrxObj);
			if (CommonUtil.checkWip(event, lmtTrxObj)) {
				result.put("wip", "wip");
			}
			if (CommonUtil.checkDeleteWip(event, lmtTrxObj)) {
				result.put("wip", "wip");
			}
			if(!"process".equals(event) && !"track".equals(event) && !"prepare_close".equals(event)  && !"process_update".equals(event) &&!"process_delete".equals(event) ){
				if ( !lmtTrxObj.getStatus().equals("ACTIVE")) {
					result.put("wip", "wip");
				}
				System.out.println("ReadLmtdetailcmd.java=>trxStatusOfFacUpdateUpload=>"+trxStatusOfFacUpdateUpload);
				if (trxStatusOfFacUpdateUpload.startsWith("PENDING_")) {
					result.put("wip", "wip");
				}
			}
			// for read event render form from original object
			// otherwise reder form from staging object
			// for maker edit limit detail, we need to copy original object to
			// staging
			/*if (EventConstant.EVENT_PREPARE_UPDATE.equals(event)) {
				OBLimit stgLmt = new OBLimit(lmtTrxObj.getLimit());
				lmtTrxObj.setStagingLimit(stgLmt);
			}*/
			ILimit curLmt = null;
			// if (EventConstant.EVENT_READ.equals(event) || EventConstant.EVENT_CUST_READ.equals(event)) {
			//if (EventConstant.EVENT_CUST_READ.equals(event)) {
			if( !("track".equals(event) || "process".equals(event) || "process_update".equals(event) || "prepare_close".equals(event) ) ){


				curLmt = lmtTrxObj.getLimit();
				
				if(lmtTrxObj.getLimit() != null && lmtTrxObj.getLimit().getLimitSysXRefs()!=null) {
					TreeMap sortedMap= new TreeMap();
					
					limitSysXRefs = lmtTrxObj.getLimit().getLimitSysXRefs();
				sortedMap= new TreeMap();
				for (int i = 0; i < limitSysXRefs.length; i++) {
					ILimitSysXRef iLimitSysXRef = limitSysXRefs[i];
					String serialNo=iLimitSysXRef.getCustomerSysXRef().getSerialNo();
					if(null==serialNo || serialNo.equals("")) {
						serialNo = iLimitSysXRef.getCustomerSysXRef().getHiddenSerialNo();
					}
					if(!"HIDE".equalsIgnoreCase(iLimitSysXRef.getCustomerSysXRef().getStatus()))
					sortedMap.put(iLimitSysXRef.getCustomerSysXRef().getFacilitySystemID()+serialNo, iLimitSysXRef);
				}
				Collection values = sortedMap.values();
				lmtTrxObj.getLimit().setLimitSysXRefs((ILimitSysXRef[])values.toArray(new ILimitSysXRef[values.size()]));
			}
				curLmt = lmtTrxObj.getLimit();
				lmtIdValue = curLmt.getLimitRef();
			}else {
				
				if(lmtTrxObj.getStagingLimit() != null && lmtTrxObj.getStagingLimit().getLimitSysXRefs()!=null) {
					TreeMap sortedMap= new TreeMap();
					
					limitSysXRefs = lmtTrxObj.getStagingLimit().getLimitSysXRefs();
				sortedMap= new TreeMap();
				for (int i = 0; i < limitSysXRefs.length; i++) {
					ILimitSysXRef iLimitSysXRef = limitSysXRefs[i];
					String serialNo=iLimitSysXRef.getCustomerSysXRef().getSerialNo();
					if(null==serialNo || serialNo.equals("")) {
						serialNo = iLimitSysXRef.getCustomerSysXRef().getHiddenSerialNo();
					}
					if(!"HIDE".equalsIgnoreCase(iLimitSysXRef.getCustomerSysXRef().getStatus()))
					sortedMap.put(iLimitSysXRef.getCustomerSysXRef().getFacilitySystemID()+serialNo, iLimitSysXRef);
				}
				Collection values = sortedMap.values();
				lmtTrxObj.getStagingLimit().setLimitSysXRefs((ILimitSysXRef[])values.toArray(new ILimitSysXRef[values.size()]));
					if(flag){
						lmtTrxObj.getStagingLimit().setLimitSysXRefs(lmtTrxObj.getLimit().getLimitSysXRefs());
					}
			}
				curLmt = lmtTrxObj.getStagingLimit();
				lmtIdValue = curLmt.getLimitRef();
			}	
			
			Locale locale = (Locale) map.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);
			List lmtList = proxy.getLimitSummaryListByAA(limitProfileID);
			List lmtListFormated = helper.formatLimitListView(lmtList, locale);
			
			String facCode = curLmt.getFacilityCode();
			String availAndOptionApplicable = null;
			if(facCode != null)
			{
				IFacilityNewMasterJdbc facilityNewMasterJdbc = (IFacilityNewMasterJdbc) BeanHouse.get("facilityNewMasterJdbc");
				IFacilityNewMaster facMaster = facilityNewMasterJdbc.getFacilityMasterByFacCode(facCode);
				availAndOptionApplicable = facMaster != null ?facMaster.getAvailAndOptionApplicable():null;
			}
			result.put("availAndOptionApplicable", availAndOptionApplicable);
			BigDecimal funded = new BigDecimal("0");
			BigDecimal nonFunded = new BigDecimal("0");
			BigDecimal memoExposure = new BigDecimal("0");
			BigDecimal convertedAmount = new BigDecimal("0");
			BigDecimal exchangeRate = new BigDecimal("1");
			DefaultLogger.debug(this, "in ReadLmtDetailCmd.java ==229==>> lmtListFormated.size()"+lmtListFormated.size());
			for(int i = 0; i < lmtListFormated.size(); i++){
				LimitListSummaryItem lstSummaryItem = (LimitListSummaryItem) lmtListFormated.get(i);
				DefaultLogger.debug(this, "in ReadLmtDetailCmd.java ==231==>> lstSummaryItem.getFacilityTypeCode()"+lstSummaryItem.getFacilityTypeCode());
 				if(!(lstSummaryItem.getLimitId()).subSequence(0, 17).equals(lmtIdValue)){
					if(!AbstractCommonMapper.isEmptyOrNull(lstSummaryItem.getCurrencyCode())){
						 IForexFeedProxy frxPxy = (IForexFeedProxy)BeanHouse.get("forexFeedProxy");
						 exchangeRate = frxPxy.getExchangeRateWithINR(lstSummaryItem.getCurrencyCode().trim());
					}
					
//					DefaultLogger.debug(this, "in ReadLmtDetailCmd.java ==239==>> exchangeRate/lstSummaryItem.getCurrencyCode().trim()"+exchangeRate+"/"+lstSummaryItem.getCurrencyCode().trim());
//					DefaultLogger.debug(this, "in ReadLmtDetailCmd.java ==240==>> lstSummaryItem.getActualSecCoverage()=="+lstSummaryItem.getActualSecCoverage());
					if(lstSummaryItem.getFacilityTypeCode().equalsIgnoreCase("FUNDED") && "No".equalsIgnoreCase(lstSummaryItem.getIsSubLimit()) &&
					 ( (lstSummaryItem.getIsAdhoc().equals("Y") && lstSummaryItem.getIsAdhocToSum().equals("Y")) ||
					  (lstSummaryItem.getIsAdhoc().equals("N") && lstSummaryItem.getIsAdhocToSum().equals("N")) )
					){
						convertedAmount = exchangeRate.multiply(new BigDecimal(lstSummaryItem.getActualSecCoverage()));
						funded = funded.add(convertedAmount);
						DefaultLogger.debug(this, "in ReadLmtDetailCmd.java ==244==>> convertedAmount/funded"+convertedAmount+"/"+funded);
					}
					
					
					
					if(lstSummaryItem.getFacilityTypeCode().equalsIgnoreCase("NON_FUNDED") && "No".equalsIgnoreCase(lstSummaryItem.getIsSubLimit()) && 
							 ( (lstSummaryItem.getIsAdhoc().equals("Y") && lstSummaryItem.getIsAdhocToSum().equals("Y")) ||
									  (lstSummaryItem.getIsAdhoc().equals("N") && lstSummaryItem.getIsAdhocToSum().equals("N")) )
				){
						convertedAmount = exchangeRate.multiply(new BigDecimal(lstSummaryItem.getActualSecCoverage()));
						nonFunded = nonFunded.add(convertedAmount);
						DefaultLogger.debug(this, "in ReadLmtDetailCmd.java ==252==>> convertedAmount/nonFunded"+convertedAmount+"/"+nonFunded);
					}
					
					
					if(lstSummaryItem.getFacilityTypeCode().equalsIgnoreCase("MEMO_EXPOSURE") && "No".equalsIgnoreCase(lstSummaryItem.getIsSubLimit()) && 
							 ( (lstSummaryItem.getIsAdhoc().equals("Y") && lstSummaryItem.getIsAdhocToSum().equals("Y")) ||
									  (lstSummaryItem.getIsAdhoc().equals("N") && lstSummaryItem.getIsAdhocToSum().equals("N")) )
					){
						convertedAmount = exchangeRate.multiply(new BigDecimal(lstSummaryItem.getActualSecCoverage()));
						memoExposure = memoExposure.add(convertedAmount);
						DefaultLogger.debug(this, "in ReadLmtDetailCmd.java ==259==>> convertedAmount/memoExposure"+convertedAmount+"/"+memoExposure);
					}
					
				}
			}
			BigDecimal totFunded = new BigDecimal("0");
			BigDecimal totNonFunded = new BigDecimal("0");
			BigDecimal totMemoExposure = new BigDecimal("0");
			
			if(cust.getTotalFundedLimit()!= null)
			{
				 totFunded = new BigDecimal(cust.getTotalFundedLimit()).subtract(funded);
			}
			if(cust.getTotalNonFundedLimit()!= null)
			{
			 totNonFunded = new BigDecimal(cust.getTotalNonFundedLimit()).subtract(nonFunded);
			}
			if(cust.getMemoExposure()!= null)
			{
			 totMemoExposure = new BigDecimal(cust.getMemoExposure()).subtract(memoExposure);
			}
			DefaultLogger.debug(this, "in ReadLmtDetailCmd.java ==277==>>totFunded/totNonFunded/totMemoExposure/cust.getTotalSanctionedLimit()=>"+totFunded+"/"+totNonFunded+"/"+totMemoExposure+"/"+cust.getTotalSanctionedLimit());
			DefaultLogger.debug(this, "in ReadLmtDetailCmd.java ==278==>>cust.getTotalFundedLimit()=>"+cust.getTotalFundedLimit());
			DefaultLogger.debug(this, "in ReadLmtDetailCmd.java ==279==>>cust.getTotalNonFundedLimit()=>"+cust.getTotalNonFundedLimit());
			DefaultLogger.debug(this, "in ReadLmtDetailCmd.java ==280==>>cust.getMemoExposure()=>"+cust.getMemoExposure());
			
			result.put("fundedAmount", totFunded.toString());
			result.put("nonFundedAmount", totNonFunded.toString());
			result.put("memoExposer", totMemoExposure.toString());
			result.put("sanctionedLimit", cust.getTotalSanctionedLimit());
				
				IBookingLocation bookingLoc = curLmt.getBookingLocation();
				AAUIHelper aaHelper = new AAUIHelper();
				//aaHelper.canAccess(team, bookingLoc);
				
				BigDecimal sancAmountVal = new BigDecimal("0");
						BigDecimal viewExchangeRate = new BigDecimal("1");
						if(!AbstractCommonMapper.isEmptyOrNull(curLmt.getCurrencyCode())){
								 IForexFeedProxy frxPxy = (IForexFeedProxy)BeanHouse.get("forexFeedProxy");
								 viewExchangeRate = frxPxy.getExchangeRateWithINR(curLmt.getCurrencyCode().trim());
						 }
						sancAmountVal = viewExchangeRate.multiply(new BigDecimal(curLmt.getRequiredSecurityCoverage()));
						
//			if(profile != null){
				result.put("subPartyNameList", getSubPartyNameList(proxy.getSubPartyNameList(String.valueOf(customerID))));
//			}
			
			//Start:Uma Khot:Don't Delete the facility if facility doc pending in case creation.
			boolean allowToDeleteFacility =false;
			boolean facilityChklistDocPending =false;
			boolean checkFacilityDocumentsIsPendingReceived = false;
			boolean checkFacilityDocumentsIsReceived = false;
			if(EventConstant.EVENT_PREPARE_DELETE.equals(event) || EventConstant.EVENT_PROCESS_DELETE.equals(event) || (EventConstant.EVENT_PROCESS.equals(event) && "PENDING_DELETE".equals(curLmt.getLimitStatus()))){
			
				String apprLimitId=lmtID;
				if(null==apprLimitId){
					if(null!=lmtTrxObj){
						if(null!=lmtTrxObj.getLimit()){
						long limitID = lmtTrxObj.getLimit().getLimitID();
						apprLimitId=String.valueOf(limitID);
						}
					}
				}
				if(null!=apprLimitId){
					boolean stageFlag =false;
					//first we will search the doc in actual table if not found then only we will search the same in staging. As if case creation doc 
					//are pending in either actual or staging we should not allow the user to delete facility until documents are closed.
					
					ILimitDAO dao = LimitDAOFactory.getDAO();
					
					//	boolean actualFlag = proxy.getActualCaseCreationDetails(apprLimitId);
					boolean actualFlag = dao.getActualCaseCreationDetails(apprLimitId);
					if(!actualFlag){
						// stageFlag =proxy.getStageCaseCreationDetails(apprLimitId);
						 stageFlag =dao.getStageCaseCreationDetails(apprLimitId);
					}
					if(!(stageFlag || actualFlag)){
						allowToDeleteFacility=true;
					}
					//Don't Delete the facility if facility doc pending in facility checklist.
					facilityChklistDocPending =dao.getPendingFacilityDocCount(apprLimitId);
					
					if(!facilityChklistDocPending) {
						facilityChklistDocPending =dao.getNewPendingFacilityDocCount(apprLimitId);
					}
					checkFacilityDocumentsIsPendingReceived = dao.checkFacilityDocumentsIsPendingReceived(apprLimitId);
					checkFacilityDocumentsIsReceived = dao.checkFacilityDocumentsIsReceived(apprLimitId);
					
					//Check Facility 
					
				}else{
					allowToDeleteFacility=true;
				}
				
			}
			
			//Insurance Deferral CR
			int colCountStage=0;
			int colCountActual=0;
			Set collateralSet=new HashSet();
			Set collateralStage=new HashSet();
			Set collateralActual=new HashSet();
			List collaterlIdListFinal= new ArrayList();
			
			boolean checklistIsActive=true;
			allowToDeleteFacility=true;
			 boolean isCollDeletedFlag = true;
			boolean pendingPropertySecCount=false;
			 if(null!=lmtTrxObj.getLimit() && null!=lmtTrxObj.getLimit().getCollateralAllocations()){
				  ICollateralAllocation[] collAllocations=lmtTrxObj.getLimit().getCollateralAllocations();
				  colCountActual=collAllocations==null ?0:collAllocations.length;
			for (int i = 0; i < colCountActual; i++) {
				String isCollateralDeleted = collAllocations[i].getHostStatus();
				System.out.println("is Collateral Deleted(Collateral Limit Linkage status) : "+isCollateralDeleted);
				if(isCollateralDeleted.equalsIgnoreCase("I")){
					isCollDeletedFlag = false;
					break;
				}
	 	}
			if(isCollDeletedFlag==false) {
				allowToDeleteFacility=false;
			}else {	
			allowToDeleteFacility=true;
			}
			}
			System.out.println("allow To Delete Facility inside ReadLmtDetailCmd is : "+allowToDeleteFacility);

			if(EventConstant.EVENT_PROCESS.equals(event) && "ACTIVE".equals(curLmt.getLimitStatus())){	
			
				allowToDeleteFacility=true;
				//Insurance Deferral CR
				
				 ICollateralAllocation[] collAlocationsStage  = lmtTrxObj.getStagingLimit().getCollateralAllocations();
				
				 colCountStage = collAlocationsStage == null ? 0 : collAlocationsStage.length;
						for (int j = 0; j < colCountStage; j++) {
							ICollateral colStage = collAlocationsStage[j].getCollateral();
							if (colStage != null && colStage instanceof OBCommercialGeneral) {
								collateralStage.add(colStage.getCollateralID());
							}
						}
					
						if(null!=lmtTrxObj.getLimit() && null!=lmtTrxObj.getLimit().getCollateralAllocations()){
									  ICollateralAllocation[] collAllocations=lmtTrxObj.getLimit().getCollateralAllocations();
									  colCountActual=collAllocations==null ?0:collAllocations.length;
										for (int i = 0; i < colCountActual; i++) {
											ICollateral col = collAllocations[i].getCollateral();
											if (col != null && col instanceof OBCommercialGeneral) {
												collateralActual.add(col.getCollateralID());
											}
										}
								 	}
				Iterator it = collateralStage.iterator();
				while(it.hasNext()){
					long collId = (Long)it.next();
					
					if(!(collateralActual.contains(collId))){
						collateralSet.add(collId);
					}
				}
				ILimitDAO dao = LimitDAOFactory.getDAO(); 
				List collaterlIdList= new ArrayList();
				collaterlIdList.addAll(collateralSet);
				
				long limitID = curLmt.getLimitID();
				long limitProfileID2 = curLmt.getLimitProfileID();
				boolean limitSecurityCount=false;
				
				for(int k=0;k<collaterlIdList.size();k++){
				 limitSecurityCount = dao.getLimitSecurityCount(limitProfileID2,String.valueOf(collaterlIdList.get(k)),limitID);
				 
				 if(!limitSecurityCount){
					 //given peroperty sec is not attached to any limit for the given customer.
					 collaterlIdListFinal.add(collaterlIdList.get(k));
					
				}
				}
				
				//check if given checklist for the given customer is active
				if(collaterlIdListFinal.size()>0){
					
					ICheckListDAO checklistDao = CheckListDAOFactory.getCheckListDAO();
					String checklistId = checklistDao.getChecklistIdByLimitId("O",limitProfileID2);
					if(null!=checklistId){
					int otherChecklistCount = checklistDao.getOtherChecklistCount("CHECKLIST",checklistId);
					if(otherChecklistCount>0){
						checklistIsActive=false;
					}
					}
					 pendingPropertySecCount = dao.getPendingPropertySecCount(collaterlIdListFinal);
				}
				
			}
			
			result.put("checklistIsActive", checklistIsActive);
			
			Set collateralSetFinal=new HashSet();
			collateralSetFinal.addAll(collaterlIdListFinal);
			result.put("collateralSet", collateralSetFinal);
			result.put("pendingPropertySecCount", pendingPropertySecCount);
	
			result.put("facilityChklistDocPending",facilityChklistDocPending);
			result.put("allowToDeleteFacility",allowToDeleteFacility);
			result.put("isCollDeletedFlag",isCollDeletedFlag);
			result.put("checkFacilityDocumentsIsPendingReceived",checkFacilityDocumentsIsPendingReceived);
			result.put("checkFacilityDocumentsIsReceived",checkFacilityDocumentsIsReceived);
			
			//End:Uma Khot:Don't Delete the facility if facility doc pending in case creation.
				
			result.put("limitProfileID",map.get("limitProfileID"));
			result.put("customerID",String.valueOf(customerID));
			result.put("inrValue", sancAmountVal.toString());
			result.put("relationShipMgrName", getRelationshipMgr(lmtTrxObj.getStagingLimit().getRelationShipManager())); //shiv
			result.put("fromEvent", map.get("event"));
			result.put("trxID", trxID); //Shiv
			result.put("event", event); //Shiv
			result.put("limitId", lmtID); //Shiv
			result.put("lmtId", lmtID); //Shiv
			result.put("lmtDetailForm", curLmt);
			result.put("sessionCriteria",map.get("sessionCriteria"));
			result.put("status", lmtTrxObj.getStatus());
			
			ILimitDAO limit = LimitDAOFactory.getDAO();
			List<ICoBorrowerDetails> coBorrowerList = limit.getPartyCoBorrowerDetails(customerID);
			result.put(CO_BORROWER_LIST, coBorrowerList);
			

			List facCoBorrowerList =curLmt.getCoBorrowerDetails();
			result.put("facCoBorrowerList", facCoBorrowerList);
			/*List<IFacilityCoBorrowerDetails> facCoBorrowerListNew = new ArrayList<IFacilityCoBorrowerDetails>();

			 for(int j=0; j<coBorrowerList.size(); j++) {
				 
				 IFacilityCoBorrowerDetails facCoBorrower = new OBFacilityCoBorrowerDetails();
				 ICoBorrowerDetails partyCoBorrower = new OBCoBorrowerDetails();
					
				partyCoBorrower= (ICoBorrowerDetails) coBorrowerList.get(j);
				String liabId= partyCoBorrower.getCoBorrowerLiabId();
				String borroName= partyCoBorrower.getCoBorrowerName();
				String[] borroNm= borroName.split("-");
				borroName=borroNm[1];
					
				facCoBorrower.setCoBorrowerLiabId(liabId);
				facCoBorrower.setCoBorrowerName(borroName);
				facCoBorrowerListNew.add(facCoBorrower);
			}*/
					 
		//	System.out.println("facCoBorrowerList@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@in JAVA CMD=="+facCoBorrowerList);

		//	result.put("facCoBorrowerList", facCoBorrowerListNew);

			
			ILimitSysXRef[] limitSysXRefs1 =null;
			Set <String> cbHashset=new HashSet<String>();
			String liabId="";
			if(lmtTrxObj.getStagingLimit() != null && lmtTrxObj.getStagingLimit().getLimitSysXRefs()!=null) {
				limitSysXRefs1 = lmtTrxObj.getStagingLimit().getLimitSysXRefs();
			
				for (int i = 0; i < limitSysXRefs1.length; i++) {
				//	ILimitSysXRef iLimitSysXRef1 = limitSysXRefs1[i];	
				
				ILimitXRefCoBorrower[] coborroObj=	limitSysXRefs1[i].getCustomerSysXRef().getXRefCoBorrowerData();
						if(null != coborroObj ) {
							for (int j = 0; j < coborroObj.length; j++) {
								liabId =coborroObj[j].getCoBorrowerId();
								cbHashset.add(liabId);
							}	
						}
				}
			}
			List<String> LineCoBorrowerIdList = new ArrayList<String>();

			if (cbHashset != null && !cbHashset.isEmpty()) {
                for (String borrowId : cbHashset) {
                	LineCoBorrowerIdList.add(borrowId);
                }
           }
			String lineCBId = UIUtil.getJSONStringFromList(LineCoBorrowerIdList, ",");

			lineCBId = lineCBId==null ? "" : lineCBId ;
		//	System.out.println("in ReadLmtDetailCmd ssssssssssssssssssss lineCBId==============="+lineCBId);
			result.put("LineCoBorrowIds", lineCBId);

		//	lmtTrxObj.getStagingLimit().getLimitSysXR
			/*List facCoBorrowerList = new ArrayList();
			  if( null !=curLmt.getCoBorrowerDetails() && curLmt.getCoBorrowerDetails().size() >0 )	
			  {
				for(int i=0;i<curLmt.getCoBorrowerDetails().size();i++) {
	       	 		String  id= curLmt.getCoBorrowerDetails().get(i).getCoBorrowerLiabId();
	       	    //	String  name= lmt.getCoBorrowerDetails().get(i).getCoBorrowerName();
	       	 		
	       	 			LabelValueBean lvBean1 = new LabelValueBean(id, id);
	       	 		facCoBorrowerList.add(lvBean1);
	       	 		}
				}
				result.put("facCoBorrowerList", facCoBorrowerList);*/
			
		}
		catch (AccessDeniedException adex) {
			adex.printStackTrace();
			throw adex;
		}
		catch (Exception ex) {
			ex.printStackTrace();
			throw (new CommandProcessingException(ex.getMessage()));
		}
		
		LimitDAO limitDao = new LimitDAO();
		try {
		String migratedFlag = "N";	
		boolean status = false;	
		if(lmtID!=null) {
			 status = limitDao.getCAMMigreted("SCI_LSP_APPR_LMTS",Long.parseLong(lmtID),"CMS_LSP_APPR_LMTS_ID");
		}
		
		if(status)
		{
			migratedFlag= "Y";
		}
		result.put("migratedFlag", migratedFlag);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		result.put("currencyList", getCurrencyList());
		try {
			result.put("riskTypeList", getRiskTypeList());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		result.put("collateralMap",  getCollateralInfo());
		temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		return temp;
	}
	
	//Shiv 200911
	//Start:Code added to display Relationship Mgr Name instead of Relationship Mgr Code
	public String getRelationshipMgr(String relMgrId) {
		IRelationshipMgrDAO relationshipmgr = (IRelationshipMgrDAO)BeanHouse.get("relationshipMgrDAO");
		String	strRelationshipmgr = "";
		if(relMgrId != null){
			strRelationshipmgr = relationshipmgr.getRelationshipMgrByCode(relMgrId).getRelationshipMgrName();
		}
		return strRelationshipmgr;
	}
	public HashMap getCollateralInfo() {
		HashMap map= new HashMap();
		ICollateralNewMasterJdbc collateralNewMasterJdbc = (ICollateralNewMasterJdbc)BeanHouse.get("collateralNewMasterJdbc");
		SearchResult result= collateralNewMasterJdbc.getAllCollateralNewMaster();
		ArrayList list=(ArrayList)result.getResultList();
		for(int ab=0;ab<list.size();ab++){
			ICollateralNewMaster newMaster=(ICollateralNewMaster)list.get(ab);
			map.put(newMaster.getNewCollateralCode(), newMaster.getNewCollateralDescription());
			
		}
		return map;
	}
	
	private List getSubPartyNameList(List lst) {
		List lbValList = new ArrayList();
	try {
			
		for (int i = 0; i < lst.size(); i++) {
			String [] mgnrLst = (String[])lst.get(i);
				LabelValueBean lvBean = new LabelValueBean(mgnrLst[1],mgnrLst[2] );
				lbValList.add(lvBean);
		}
	} catch (Exception ex) {
	}
	return CommonUtil.sortDropdown(lbValList);
}
	
	private List getCurrencyList() {
		List lbValList = new ArrayList();
		try {
				MISecurityUIHelper helper = new MISecurityUIHelper();
				IForexFeedEntry[] currency = CollateralDAOFactory.getDAO().getCurrencyList();
				
				if (currency != null) {
					for (int i = 0; i < currency.length; i++) {
						IForexFeedEntry lst = currency[i];
//						String id = lst.getCurrencyIsoCode().trim();
						String id = lst.getBuyCurrency().trim();
						String value = lst.getCurrencyIsoCode().trim();
						LabelValueBean lvBean = new LabelValueBean(value, id);
						lbValList.add(lvBean);
					}
				}
		}
		catch (Exception ex) {
		}
		return CommonUtil.sortDropdown(lbValList);
	}
	private List getRiskTypeList() throws Exception {
		
		List<LabelValueBean> lbValList = new ArrayList<LabelValueBean>();
		IRiskTypeProxyManager riskTypeProxy = (IRiskTypeProxyManager)BeanHouse.get("riskTypeProxy");
		SearchResult riskTypeList= (SearchResult) riskTypeProxy.getAllActualRiskType();
		Iterator itr = riskTypeList.getResultList().iterator();
		
		while(itr.hasNext()) {
			IRiskType riskType = (IRiskType) itr.next();
			LabelValueBean lvBean = new LabelValueBean(riskType.getRiskTypeName(),riskType.getRiskTypeCode());
			lbValList.add(lvBean);
		}
		
		return CommonUtil.sortDropdown(lbValList);
	}
}
