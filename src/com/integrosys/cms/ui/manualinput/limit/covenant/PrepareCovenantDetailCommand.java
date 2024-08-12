package com.integrosys.cms.ui.manualinput.limit.covenant;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
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
import com.integrosys.base.techinfra.util.AccessorUtil;
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
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.customer.bus.CustomerDAOFactory;
import com.integrosys.cms.app.customer.bus.ICMSCustomer;
import com.integrosys.cms.app.customer.bus.ICustomerDAO;
import com.integrosys.cms.app.customer.proxy.CustomerProxyFactory;
import com.integrosys.cms.app.customer.proxy.ICustomerProxy;
import com.integrosys.cms.app.feed.bus.forex.IForexFeedEntry;
import com.integrosys.cms.app.feed.proxy.forex.IForexFeedProxy;
import com.integrosys.cms.app.goodsMaster.bus.IGoodsMasterJdbc;
import com.integrosys.cms.app.limit.bus.ICollateralAllocation;
import com.integrosys.cms.app.limit.bus.ILimit;
import com.integrosys.cms.app.limit.bus.ILimitCovenant;
import com.integrosys.cms.app.limit.bus.ILimitDAO;
import com.integrosys.cms.app.limit.bus.ILimitProfile;
import com.integrosys.cms.app.limit.bus.ILimitSysXRef;
import com.integrosys.cms.app.limit.bus.LimitDAO;
import com.integrosys.cms.app.limit.bus.LimitDAOFactory;
import com.integrosys.cms.app.limit.bus.OBLimitCovenant;
import com.integrosys.cms.app.limit.proxy.ILimitProxy;
import com.integrosys.cms.app.limit.proxy.LimitProxyFactory;
import com.integrosys.cms.app.limit.trx.ILimitProfileTrxValue;
import com.integrosys.cms.app.limit.trx.ILimitTrxValue;
import com.integrosys.cms.app.manualinput.limit.proxy.SBMILmtProxy;
import com.integrosys.cms.app.relationshipmgr.bus.IRelationshipMgrDAO;
import com.integrosys.cms.app.transaction.OBTrxContext;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;
import com.integrosys.cms.ui.manualinput.CommonUtil;
import com.integrosys.cms.ui.manualinput.aa.AAUIHelper;
import com.integrosys.cms.ui.manualinput.limit.EventConstant;
import com.integrosys.cms.ui.manualinput.limit.LimitListSummaryItem;
import com.integrosys.cms.ui.manualinput.limit.MILimitUIHelper;
import com.integrosys.cms.ui.manualinput.security.MISecurityUIHelper;
import com.integrosys.component.bizstructure.app.bus.ITeam;

public class PrepareCovenantDetailCommand extends AbstractCommand implements ILmtCovenantConstants {
	public String[][] getParameterDescriptor() {
		return (new String[][] { { "lmtTrxObj", "com.integrosys.cms.app.limit.trx.ILimitTrxValue", SERVICE_SCOPE },
			    { "limitId", "java.lang.String", REQUEST_SCOPE },
			    { COVENANT_DETAIL_FORM, "java.lang.Object", FORM_SCOPE },
			    { "limitRef", "java.lang.String", REQUEST_SCOPE },
				{ "limitProfileID", "java.lang.String", REQUEST_SCOPE },
				{ "customerID", "java.lang.String", REQUEST_SCOPE }, 
				{ "fromEvent", "java.lang.String", REQUEST_SCOPE },
				{ "event", "java.lang.String", REQUEST_SCOPE },
				{ "isCreate", "java.lang.String", REQUEST_SCOPE },
				{ IGlobalConstant.USER_TEAM, "com.integrosys.component.bizstructure.app.bus.ITeam", GLOBAL_SCOPE },
				{ "trxID", "java.lang.String", REQUEST_SCOPE }, { "event", "java.lang.String", REQUEST_SCOPE },
				{ "theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE },
				{ "restCountryList", "java.util.List", SERVICE_SCOPE },
				{ "restCurrencyList", "java.util.List", SERVICE_SCOPE },
				{ "restBankList", "java.util.List", SERVICE_SCOPE },
				{ "restDrawerList", "java.util.List", SERVICE_SCOPE },
				{ "restDraweeList", "java.util.List", SERVICE_SCOPE },
				{ "restBeneList", "java.util.List", SERVICE_SCOPE },
				{ SESSION_COVENANT_GOODS_RESTRICTION_LIST, List.class.getName() , SERVICE_SCOPE },
				{ "sessionCriteria", "java.lang.String", REQUEST_SCOPE },
				{"eventForEdit", "java.lang.String", REQUEST_SCOPE },
				{ "index", "java.lang.String", REQUEST_SCOPE },
				{ "beneficiaryName", "java.lang.String", REQUEST_SCOPE },
				{ "beneficiaryAmount", "java.lang.String", REQUEST_SCOPE },
				{ "beneficiaryCustId", "java.lang.String", REQUEST_SCOPE },
				{ "beneficiaryCustName", "java.lang.String", REQUEST_SCOPE },
				{ "drawerName", "java.lang.String", REQUEST_SCOPE },
				{ "drawerAmount", "java.lang.String", REQUEST_SCOPE },
				{ "drawerCustId", "java.lang.String", REQUEST_SCOPE },
				{ "drawerCustName", "java.lang.String", REQUEST_SCOPE },
				{ "draweeName", "java.lang.String", REQUEST_SCOPE },
				{ "draweeAmount", "java.lang.String", REQUEST_SCOPE },
				{ "draweeCustId", "java.lang.String", REQUEST_SCOPE },
				{ "draweeCustName", "java.lang.String", REQUEST_SCOPE },
				{"exceptionMap","java.util.HashMap",REQUEST_SCOPE},
		});
	}

	public String[][] getResultDescriptor() {
		return (new String[][] { { "lmtTrxObj", "com.integrosys.cms.app.limit.trx.ILimitTrxValue", SERVICE_SCOPE },
				{ COVENANT_DETAIL_FORM, "java.lang.Object", FORM_SCOPE },
				{ "wip", "java.lang.String", REQUEST_SCOPE },
				{ "event", "java.lang.String", REQUEST_SCOPE },
				{ "trxID", "java.lang.String", SERVICE_SCOPE },
				{ "index", "java.lang.String", REQUEST_SCOPE },
				{ "beneficiaryName", "java.lang.String", REQUEST_SCOPE },
				{ "beneficiaryAmount", "java.lang.String", REQUEST_SCOPE },
				{ "beneficiaryCustId", "java.lang.String", REQUEST_SCOPE },
				{ "beneficiaryCustName", "java.lang.String", REQUEST_SCOPE },
				{ "drawerName", "java.lang.String", REQUEST_SCOPE },
				{ "drawerAmount", "java.lang.String", REQUEST_SCOPE },
				{ "drawerCustId", "java.lang.String", REQUEST_SCOPE },
				{ "drawerCustName", "java.lang.String", REQUEST_SCOPE },
				{ "draweeName", "java.lang.String", REQUEST_SCOPE },
				{ "draweeAmount", "java.lang.String", REQUEST_SCOPE },
				{ "draweeCustId", "java.lang.String", REQUEST_SCOPE },
				{ "draweeCustName", "java.lang.String", REQUEST_SCOPE },
				{ "restCountryList", "java.util.List", SERVICE_SCOPE },
				{ "restCurrencyList", "java.util.List", SERVICE_SCOPE },
				{ "restBankList", "java.util.List", SERVICE_SCOPE },
				{ "restDrawerList", "java.util.List", SERVICE_SCOPE },
				{ "restDraweeList", "java.util.List", SERVICE_SCOPE },
				{ "restBeneList", "java.util.List", SERVICE_SCOPE },
				{ "countryList", "java.util.List", SERVICE_SCOPE },
				{ "goodsList", "java.util.List", SERVICE_SCOPE },
				{ "bankList", "java.util.List", SERVICE_SCOPE },
				{ "incoTermList", "java.util.List", SERVICE_SCOPE },
				{ "currList", "java.util.List", SERVICE_SCOPE },
				{ "event", "java.lang.String", REQUEST_SCOPE },
				{ "isCreate", "java.lang.String", REQUEST_SCOPE },
				{ "fromEvent", "java.lang.String", REQUEST_SCOPE },
				{ "limitId", "java.lang.String", REQUEST_SCOPE },
				{ "limitRef", "java.lang.String", REQUEST_SCOPE },
				{ "customerID", "java.lang.String", REQUEST_SCOPE }, 
				{ "limitProfileID", "java.lang.String", REQUEST_SCOPE },
				{ "lmtId", "java.lang.String", SERVICE_SCOPE },
				{ "status", "java.lang.String", REQUEST_SCOPE },
				{"eventForEdit", "java.lang.String", REQUEST_SCOPE },
				{ SESSION_DROPDOWN_GOODS_PARENT_LIST, List.class.getName(), SERVICE_SCOPE },
				{ SESSION_COVENANT_GOODS_RESTRICTION_LIST, List.class.getName() , SERVICE_SCOPE },
				{"exceptionMap","java.util.HashMap",REQUEST_SCOPE},				
				{ "covenantRequired", String.class.getName(), REQUEST_SCOPE }
				});
				
	}

	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException,
			AccessDeniedException {
		HashMap result = new HashMap();
		HashMap exceptionMap = new HashMap();
		HashMap temp = new HashMap();
		String lmtIdValue = "";
		DefaultLogger.debug(this, "in PrepareCovenantDetailCommand.java ==108==>> (map.get(limitId))");
		String lmtID = (String) (map.get("limitId")); //CMS_LSP_APPR_LMTS_ID
		DefaultLogger.debug(this, "in PrepareCovenantDetailCommand.java ==108==>> (map.get(limitId))"+lmtID);
		boolean flag = false;
		ILimitProfileTrxValue limitProfileTrxVal = null;
		try {
			OBTrxContext ctx = (OBTrxContext) map.get("theOBTrxContext");
			String event = (String) (map.get("event"));
			String from_event = (String) (map.get("fromEvent"));
			String limitProfileID = (String) (map.get("limitProfileID"));	
			String limitRef = (String) (map.get("limitRef"));
			String isCreate = (String) (map.get("isCreate"));
			
			List<OBLimitCovenant> restCountryList = new ArrayList();
			List<OBLimitCovenant>  restCurrencyList = new ArrayList();
			List<OBLimitCovenant>  restBankList = new ArrayList();
			List<OBLimitCovenant>  restDrawerList = new ArrayList();
			List<OBLimitCovenant>  restDraweeList = new ArrayList();
			List<OBLimitCovenant>  restBeneList = new ArrayList();
			List<OBLimitCovenant>  restGoodsRestrictionList = new ArrayList();

			DefaultLogger.debug(this, "in PrepareCovenantDetailCommand.java ==114==>> limitProfileID"+ limitProfileID);
			DefaultLogger.debug(this, "in PrepareCovenantDetailCommand.java ==132==>> limitRef"+ limitRef);
			long lmtProfId = Long.parseLong(limitProfileID);
			ILimitProxy limitProxy = LimitProxyFactory.getProxy();
			ILimitProfile profile = limitProxy.getLimitProfile(lmtProfId); 
			ILimitCovenant lmt = (ILimitCovenant) map.get(COVENANT_DETAIL_FORM);

			
			DefaultLogger.debug(this, "in PrepareCovenantDetailCommand.java ==119==>> profile.getCustomerID()"+ profile.getCustomerID());
			ICustomerProxy custProxy = CustomerProxyFactory.getProxy();
			ICMSCustomer cust = custProxy.getCustomer(profile.getCustomerID());
			DefaultLogger.debug(this, "in PrepareCovenantDetailCommand.java ==129==>> profile.getCustomerID()"+ profile.getCustomerID());

			
			
			String customerID = (String) (map.get("customerID"));
			MILimitUIHelper helper = new MILimitUIHelper();
			ITeam team = (ITeam) (map.get(IGlobalConstant.USER_TEAM));

			SBMILmtProxy proxy = helper.getSBMILmtProxy();
			ILimitTrxValue lmtTrxObj = (ILimitTrxValue) map.get("lmtTrxObj");
			DefaultLogger.debug(this, "****************** INSIDE PrepareCovenantDetailCommand EVENT IS: " + event +" and from_event:"+from_event);
			String trxID = (String) (map.get("trxID"));
			DefaultLogger.debug(this, "********** Before PrepareCovenantDetailCommand function   lmtID" + lmtID);
			String stgReferenceId = lmtTrxObj.getStagingReferenceID();
			
			
			DefaultLogger.debug(this, "****************** INSIDE ReadLmtDetailCmd EVENT IS: " + event);
			DefaultLogger.debug(this, "in PrepareCovenantDetailCommand.java ==114==>> trxID"+ trxID);
			
			
			ILimitDAO lmtDao = LimitDAOFactory.getDAO();
			ILimitCovenant curLmt = null;
			String main = "mainTable";
			String stg = "stgTable";
			Locale locale = (Locale) map.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);
			ILimit  lmtCol = null;
			List<String> updateEvents = Arrays.asList("add_country_rest","add_currency_rest","add_bank_rest","add_drawer_rest","add_drawee_rest",
					"add_bene_rest","edit_bene_rest","edit_drawer_rest","edit_drawee_rest","remove_country_rest","remove_currency_rest","remove_bank_rest",
					"remove_drawer_rest","remove_drawee_rest","remove_bene_rest","add_goods_restriction","delete_goods_restriction"); 
			if(trxID == null || "null".equals(trxID)) 
				trxID = lmtTrxObj.getTransactionID();
			
			if (EventConstant.EVENT_PREPARE_CLOSE.equals(from_event) || (EventConstant.EVENT_PROCESS_UPDATE.equals(from_event) && !updateEvents.contains(event) )
					|| EventConstant.EVENT_TRACK.equals(from_event) || EventConstant.EVENT_PROCESS.equals(from_event)
					|| EventConstant.EVENT_PROCESS_DELETE.equals(from_event)) {
				DefaultLogger.debug(this, "********** Trasanction ID IS: " + trxID);
				if(lmtTrxObj.getStagingLimit().getLimitCovenant() == null || lmtTrxObj.getStagingLimit().getLimitCovenant().length==0) {
				lmtTrxObj = proxy.searchLimitByTrxId(trxID);
				ILimitCovenant[] covStg = lmtDao.getCovenantData(stgReferenceId,stg);
				if(covStg!=null && covStg.length!=0) {
				lmtTrxObj.getStagingLimit().setLimitCovenant(covStg);
				}
				lmtCol = (ILimit) lmtTrxObj.getStagingLimit();
				if(lmtCol.getLimitCovenant()!=null) {
					for(ILimitCovenant limitCovenant : lmtCol.getLimitCovenant() ) {
						if(ICMSConstant.YES.equals(limitCovenant.getSingleCovenantInd())) {
							curLmt = limitCovenant;
						}
						else if(ICMSConstant.YES.equals(limitCovenant.getBeneInd())) {
							restBeneList.add((OBLimitCovenant) limitCovenant);
						}
						else if(ICMSConstant.YES.equals(limitCovenant.getDraweeInd())) {
							restDraweeList.add((OBLimitCovenant) limitCovenant);
						}
						else if(ICMSConstant.YES.equals(limitCovenant.getDrawerInd())) {
							restDrawerList.add((OBLimitCovenant) limitCovenant);
						}
						else if(ICMSConstant.YES.equals(limitCovenant.getGoodsRestrictionInd())) {
							restGoodsRestrictionList.add((OBLimitCovenant) limitCovenant);
						}
						else if(ICMSConstant.YES.equals(limitCovenant.getRestrictedBankInd())) {
							restBankList.add((OBLimitCovenant) limitCovenant);
						}
						else if(ICMSConstant.YES.equals(limitCovenant.getRestrictedCountryInd())) {
							restCountryList.add((OBLimitCovenant) limitCovenant);
						}
						else if(ICMSConstant.YES.equals(limitCovenant.getRestrictedCurrencyInd())) {
							restCurrencyList.add((OBLimitCovenant) limitCovenant);
						}
					}
//					int arrLength = lmtCol.getLimitCovenant().length;					
//					for(int i=0;i<arrLength;i++) {
//					curLmt = lmtCol.getLimitCovenant()[i];
//					if(!"".equals(curLmt.getRestrictedCountryname())&&curLmt.getRestrictedCountryname()!=null) {
//						OBLimitCovenant ob = new OBLimitCovenant();
//						ob.setRestrictedCountryname(curLmt.getRestrictedCountryname());
//						ob.setRestrictedAmount(curLmt.getRestrictedAmount());
//						restCountryList.add(ob);
//					}
//					if(!"".equals(curLmt.getRestrictedCurrency())&&curLmt.getRestrictedCurrency()!=null) {
//						OBLimitCovenant ob = new OBLimitCovenant();
//						ob.setRestrictedCurrency(curLmt.getRestrictedCurrency());
//						ob.setRestrictedCurrencyAmount(curLmt.getRestrictedCurrencyAmount());
//						restCurrencyList.add(ob);
//					}
//					if(!"".equals(curLmt.getRestrictedBank())&&curLmt.getRestrictedBank()!=null) {
//						OBLimitCovenant ob = new OBLimitCovenant();
//						ob.setRestrictedBank(curLmt.getRestrictedBank());
//						ob.setRestrictedBankAmount(curLmt.getRestrictedBankAmount());
//						restBankList.add(ob);
//					}
//					if(!"".equals(curLmt.getDrawerName())&&curLmt.getDrawerName()!=null) {
//						OBLimitCovenant ob = new OBLimitCovenant();
//						ob.setDrawerName(curLmt.getDrawerName());
//						ob.setDrawerAmount(curLmt.getDrawerAmount());
//						ob.setDrawerCustId(curLmt.getDrawerCustId());
//						ob.setDrawerCustName(curLmt.getDrawerCustName());
//						restDrawerList.add(ob);
//					}
//					if(!"".equals(curLmt.getDrawerName())&&curLmt.getDrawerName()!=null) {
//						OBLimitCovenant ob = new OBLimitCovenant();
//						ob.setDraweeName(curLmt.getDraweeName());
//						ob.setDraweeAmount(curLmt.getDraweeAmount());
//						ob.setDraweeCustId(curLmt.getDraweeCustId());
//						ob.setDraweeCustName(curLmt.getDraweeCustName());
//						restDraweeList.add(ob);
//					}
//					if(!"".equals(curLmt.getBeneName())&&curLmt.getBeneName()!=null) {
//						OBLimitCovenant ob = new OBLimitCovenant();
//						ob.setBeneName(curLmt.getBeneName());
//						ob.setBeneAmount(curLmt.getBeneAmount());
//						ob.setBeneCustId(curLmt.getBeneCustId());
//						ob.setBeneCustName(curLmt.getBeneCustName());
//						restBeneList.add(ob);
//					}
//					}
					
				}
				
				ILimitCovenant[] covMain = lmtDao.getCovenantData(lmtTrxObj.getReferenceID(),main);
				if(covMain!=null && covMain.length!=0) {
				lmtTrxObj.getLimit().setLimitCovenant(covMain);
				}
				result.put("lmtTrxObj", lmtTrxObj);
				result.put("restCountryList",restCountryList);
				result.put("restCurrencyList",restCurrencyList);
				result.put("restBankList",restBankList);
				result.put("restDrawerList",restDrawerList);
				result.put("restDraweeList",restDraweeList);
				result.put("restBeneList",restBeneList);
				Collections.sort(restGoodsRestrictionList, OBLimitCovenant.Comparators.GOODS_RESTRICTION_COMPARATOR);
				result.put(SESSION_COVENANT_GOODS_RESTRICTION_LIST,restGoodsRestrictionList);
				}else {
					lmtCol = (ILimit) lmtTrxObj.getStagingLimit();
					if(lmtCol.getLimitCovenant()!=null) {
						//int arrLength = lmtCol.getLimitCovenant().length;
						for(ILimitCovenant limitCovenant : lmtCol.getLimitCovenant() ) {
							if(ICMSConstant.YES.equals(limitCovenant.getSingleCovenantInd())) {
								curLmt = limitCovenant;
							}
							else if(ICMSConstant.YES.equals(limitCovenant.getBeneInd())) {
								restBeneList.add((OBLimitCovenant) limitCovenant);
							}
							else if(ICMSConstant.YES.equals(limitCovenant.getDraweeInd())) {
								restDraweeList.add((OBLimitCovenant) limitCovenant);
							}
							else if(ICMSConstant.YES.equals(limitCovenant.getDrawerInd())) {
								restDrawerList.add((OBLimitCovenant) limitCovenant);
							}
							else if(ICMSConstant.YES.equals(limitCovenant.getGoodsRestrictionInd())) {
								restGoodsRestrictionList.add((OBLimitCovenant) limitCovenant);
							}
							else if(ICMSConstant.YES.equals(limitCovenant.getRestrictedBankInd())) {
								restBankList.add((OBLimitCovenant) limitCovenant);
							}
							else if(ICMSConstant.YES.equals(limitCovenant.getRestrictedCountryInd())) {
								restCountryList.add((OBLimitCovenant) limitCovenant);
							}
							else if(ICMSConstant.YES.equals(limitCovenant.getRestrictedCurrencyInd())) {
								restCurrencyList.add((OBLimitCovenant) limitCovenant);
							}
						}
//						for(int i=0;i<arrLength;i++) {
//							curLmt = lmtCol.getLimitCovenant()[i];
//							if(!"".equals(curLmt.getRestrictedCountryname())&&curLmt.getRestrictedCountryname()!=null) {
//								OBLimitCovenant ob = new OBLimitCovenant();
//								ob.setRestrictedCountryname(curLmt.getRestrictedCountryname());
//								ob.setRestrictedAmount(curLmt.getRestrictedAmount());
//								restCountryList.add(ob);
//							}
//							if(!"".equals(curLmt.getRestrictedCurrency())&&curLmt.getRestrictedCurrency()!=null) {
//								OBLimitCovenant ob = new OBLimitCovenant();
//								ob.setRestrictedCurrency(curLmt.getRestrictedCurrency());
//								ob.setRestrictedCurrencyAmount(curLmt.getRestrictedCurrencyAmount());
//								restCurrencyList.add(ob);
//							}
//							if(!"".equals(curLmt.getRestrictedBank())&&curLmt.getRestrictedBank()!=null) {
//								OBLimitCovenant ob = new OBLimitCovenant();
//								ob.setRestrictedBank(curLmt.getRestrictedBank());
//								ob.setRestrictedBankAmount(curLmt.getRestrictedBankAmount());
//								restBankList.add(ob);
//							}
//							if(!"".equals(curLmt.getDrawerName())&&curLmt.getDrawerName()!=null) {
//								OBLimitCovenant ob = new OBLimitCovenant();
//								ob.setDrawerName(curLmt.getDrawerName());
//								ob.setDrawerAmount(curLmt.getDrawerAmount());
//								ob.setDrawerCustId(curLmt.getDrawerCustId());
//								ob.setDrawerCustName(curLmt.getDrawerCustName());
//								restDrawerList.add(ob);
//							}
//							if(!"".equals(curLmt.getDrawerName())&&curLmt.getDrawerName()!=null) {
//								OBLimitCovenant ob = new OBLimitCovenant();
//								ob.setDraweeName(curLmt.getDraweeName());
//								ob.setDraweeAmount(curLmt.getDraweeAmount());
//								ob.setDraweeCustId(curLmt.getDraweeCustId());
//								ob.setDraweeCustName(curLmt.getDraweeCustName());
//								restDraweeList.add(ob);
//							}
//							if(!"".equals(curLmt.getBeneName())&&curLmt.getBeneName()!=null) {
//								OBLimitCovenant ob = new OBLimitCovenant();
//								ob.setBeneName(curLmt.getBeneName());
//								ob.setBeneAmount(curLmt.getBeneAmount());
//								ob.setBeneCustId(curLmt.getBeneCustId());
//								ob.setBeneCustName(curLmt.getBeneCustName());
//								restBeneList.add(ob);
//							}
//							}
						result.put("restCountryList",restCountryList);
						result.put("restCurrencyList",restCurrencyList);
						result.put("restBankList",restBankList);
						result.put("restDrawerList",restDrawerList);
						result.put("restDraweeList",restDraweeList);
						result.put("restBeneList",restBeneList);
						Collections.sort(restGoodsRestrictionList, OBLimitCovenant.Comparators.GOODS_RESTRICTION_COMPARATOR);
						result.put(SESSION_COVENANT_GOODS_RESTRICTION_LIST,restGoodsRestrictionList);
						result.put("lmtTrxObj", lmtTrxObj);
				}
				}
				
			}else if(
					("prepare_create".equals(from_event)||"update_return".equals(from_event)||"return".contains(from_event)||
					"add_country_rest".equals(event)||"add_currency_rest".equals(event)||"add_bank_rest".equals(event)
					||"remove_country_rest".equals(event)||"remove_currency_rest".equals(event)||"remove_bank_rest".equals(event)
					||"add_drawer_rest".equals(event)||"add_drawee_rest".equals(event)||"add_bene_rest".equals(event)
					||"remove_drawer_rest".equals(event)||"remove_drawee_rest".equals(event)||"remove_bene_rest".equals(event)
					||"edit_bene_rest".equals(event)||"edit_drawer_rest".equals(event)||"edit_drawee_rest".equals(event) || "add_goods_restriction".equals(event)) 
					&&
					!("prepare_edit_bene_rest".equals(event)||"prepare_edit_drawer_rest".equals(event)||"prepare_edit_drawee_rest".equals(event))
					) {
				if(("prepare_create".equals(from_event)||"update_return".equals(from_event)||"return".contains(from_event) || "process_update".equals(from_event)) &&
						("add_country_rest".equals(event)||"add_currency_rest".equals(event)||"add_bank_rest".equals(event)
						||"remove_country_rest".equals(event)||"remove_currency_rest".equals(event)||"remove_bank_rest".equals(event)
						||"add_drawer_rest".equals(event)||"add_drawee_rest".equals(event)||"add_bene_rest".equals(event) || "add_goods_restriction".equals(event)
						||"remove_drawer_rest".equals(event)||"remove_drawee_rest".equals(event)||"remove_bene_rest".equals(event)
						||"edit_bene_rest".equals(event)||"edit_drawer_rest".equals(event)||"edit_drawee_rest".equals(event)
						)) {
					result.put("eventForEdit", null);
					curLmt = lmt;
					result.put("lmtTrxObj", lmtTrxObj);
					result.put("restCountryList",map.get("restCountryList"));
					result.put("restCurrencyList",map.get("restCurrencyList"));
					result.put("restBankList",map.get("restBankList"));
					result.put("restDrawerList",map.get("restDrawerList"));
					result.put("restDraweeList",map.get("restDraweeList"));
					result.put("restBeneList",map.get("restBeneList"));
				}else if(("prepare_create".equals(from_event)||"update_return".equals(from_event)||"return".contains(from_event))&&
						!("add_country_rest".equals(event)||"add_currency_rest".equals(event)||"add_bank_rest".equals(event)
								||"remove_country_rest".equals(event)||"remove_currency_rest".equals(event)||"remove_bank_rest".equals(event)
								||"add_drawer_rest".equals(event)||"add_drawee_rest".equals(event)||"add_bene_rest".equals(event) || "add_goods_restriction".equals(event)
								||"remove_drawer_rest".equals(event)||"remove_drawee_rest".equals(event)||"remove_bene_rest".equals(event)
								||"edit_bene_rest".equals(event)||"edit_drawer_rest".equals(event)||"edit_drawee_rest".equals(event)
								)) {
					if(lmtTrxObj.getStagingLimit().getLimitCovenant()!=null && lmtTrxObj.getStagingLimit().getLimitCovenant().length!=0) {
						for(ILimitCovenant limitCov : lmtTrxObj.getStagingLimit().getLimitCovenant()) {
							if(ICMSConstant.YES.equals(limitCov.getSingleCovenantInd()))
								curLmt = limitCov;
						}
						
						}if("prepare_create".equals(from_event) && "prepare_edit_covenant_detail".equals(event))
						{
							result.put("lmtTrxObj", lmtTrxObj);
							result.put("restCountryList",restCountryList);
							result.put("restCurrencyList",restCurrencyList);
							result.put("restBankList",restBankList);
							result.put("restDrawerList",restDrawerList);
							result.put("restDraweeList",restDraweeList);
							result.put("restBeneList",restBeneList);
						}
						else
						{
						result.put("lmtTrxObj", lmtTrxObj);
						result.put("restCountryList",map.get("restCountryList"));
						result.put("restCurrencyList",map.get("restCurrencyList"));
						result.put("restBankList",map.get("restBankList"));
						result.put("restDrawerList",map.get("restDrawerList"));
						result.put("restDraweeList",map.get("restDraweeList"));
						result.put("restBeneList",map.get("restBeneList"));
						}		
				}
				/*else if(("prepare_update".equals(from_event)) &&
						("add_country_rest".equals(event)||"add_currency_rest".equals(event)||"add_bank_rest".equals(event)
						||"remove_country_rest".equals(event)||"remove_currency_rest".equals(event)||"remove_bank_rest".equals(event)
						||"add_drawer_rest".equals(event)||"add_drawee_rest".equals(event)||"add_bene_rest".equals(event) || "add_goods_restriction".equals(event)
						||"remove_drawer_rest".equals(event)||"remove_drawee_rest".equals(event)||"remove_bene_rest".equals(event)
						||"edit_bene_rest".equals(event)||"edit_drawer_rest".equals(event)||"edit_drawee_rest".equals(event)
						)) {
					result.put("eventForEdit", null);
					curLmt = lmt;
					result.put("lmtTrxObj", lmtTrxObj);
					result.put("restCountryList",map.get("restCountryList"));
					result.put("restCurrencyList",map.get("restCurrencyList"));
					result.put("restBankList",map.get("restBankList"));
					result.put("restDrawerList",map.get("restDrawerList"));
					result.put("restDraweeList",map.get("restDraweeList"));
					result.put("restBeneList",map.get("restBeneList"));
				}*/
			}else if("prepare_edit_bene_rest".equals(event)){
				ILimitCovenant newLmt = new OBLimitCovenant();
				AccessorUtil.copyValue(lmt, newLmt);
				
				String beneficiaryName = (String)map.get("beneficiaryName");
				String beneficiaryAmount = (String)map.get("beneficiaryAmount");
				String beneficiaryCustId = (String)map.get("beneficiaryCustId");
				String beneficiaryCustName = (String)map.get("beneficiaryCustName");
				newLmt.setBeneInd(ICMSConstant.YES);
				newLmt.setBeneName(beneficiaryName);
				newLmt.setBeneCustId(beneficiaryCustId);
				newLmt.setBeneAmount(beneficiaryAmount);
				newLmt.setBeneCustName(beneficiaryCustName);
				result.put("restCountryList",map.get("restCountryList"));
				result.put("restCurrencyList",map.get("restCurrencyList"));
				result.put("restBankList",map.get("restBankList"));
				result.put("restDrawerList",map.get("restDrawerList"));
				result.put("restDraweeList",map.get("restDraweeList"));
				result.put("restBeneList",map.get("restBeneList"));
				result.put("lmtTrxObj", lmtTrxObj);
				result.put("eventForEdit", map.get("event"));

				curLmt = newLmt;
			}else if("prepare_edit_drawer_rest".equals(event)){
				ILimitCovenant newLmt = new OBLimitCovenant();
				AccessorUtil.copyValue(lmt, newLmt);
				
				String drawerName = (String)map.get("drawerName");
				String drawerAmount = (String)map.get("drawerAmount");
				String drawerCustId = (String)map.get("drawerCustId");
				String drawerCustName = (String)map.get("drawerCustName");
				newLmt.setDrawerInd(ICMSConstant.YES);
				newLmt.setDrawerName(drawerName);
				newLmt.setDrawerCustId(drawerCustId);
				newLmt.setDrawerAmount(drawerAmount);
				newLmt.setDrawerCustName(drawerCustName);
				result.put("restCountryList",map.get("restCountryList"));
				result.put("restCurrencyList",map.get("restCurrencyList"));
				result.put("restBankList",map.get("restBankList"));
				result.put("restDrawerList",map.get("restDrawerList"));
				result.put("restDraweeList",map.get("restDraweeList"));
				result.put("restBeneList",map.get("restBeneList"));
				result.put("lmtTrxObj", lmtTrxObj);
				result.put("eventForEdit", map.get("event"));

				curLmt = newLmt;
			}else if("prepare_edit_drawee_rest".equals(event)){
				ILimitCovenant newLmt = new OBLimitCovenant();
				AccessorUtil.copyValue(lmt, newLmt);
				
				String draweeName = (String)map.get("draweeName");
				String draweeAmount = (String)map.get("draweeAmount");
				String draweeCustId = (String)map.get("draweeCustId");
				String draweeCustName = (String)map.get("draweeCustName");
				newLmt.setDraweeInd(ICMSConstant.YES);
				newLmt.setDraweeName(draweeName);
				newLmt.setDraweeCustId(draweeCustId);
				newLmt.setDraweeAmount(draweeAmount);
				newLmt.setDraweeCustName(draweeCustName);
				result.put("restCountryList",map.get("restCountryList"));
				result.put("restCurrencyList",map.get("restCurrencyList"));
				result.put("restBankList",map.get("restBankList"));
				result.put("restDrawerList",map.get("restDrawerList"));
				result.put("restDraweeList",map.get("restDraweeList"));
				result.put("restBeneList",map.get("restBeneList"));
				result.put("eventForEdit", map.get("event"));
				result.put("lmtTrxObj", lmtTrxObj);
				curLmt = newLmt;	
			}else if("prepare_edit_covenant_detail".equals(event) && "prepare_update".equals(from_event)
					||"edit_covenant_detail".equals(event) && "prepare_update".equals(from_event)){
				//lmtCol = (ILimit) lmtTrxObj.getLimit();
				
				if(lmtTrxObj.getStagingLimit().getLimitCovenant() == null || lmtTrxObj.getStagingLimit().getLimitCovenant().length==0)
				{
					lmtCol = (ILimit) lmtTrxObj.getLimit();
				}
				else
				{
					lmtCol = (ILimit) lmtTrxObj.getStagingLimit();
				}
							
				ILimitCovenant[] limitcovenant = lmtCol.getLimitCovenant();
				
				if(limitcovenant != null)
				{		
				if(limitcovenant.length == 0)
				{
					lmtTrxObj = proxy.searchLimitByLmtId(lmtID);
					ILimitCovenant[] covMain = lmtDao.getCovenantData(lmtID,main);
					
					ILimitCovenant[] covStg = lmtDao.getCovenantData(stgReferenceId,stg);
					if(covStg!=null && covStg.length!=0) {
						lmtTrxObj.getStagingLimit().setLimitCovenant(covStg);
					}
					
					if (covMain != null && covMain.length != 0) {
						lmtTrxObj.getLimit().setLimitCovenant(covMain);
						lmtCol = (ILimit) lmtTrxObj.getLimit();
				}
				}
			}
				if(lmtCol.getLimitCovenant()!=null) {
					for(ILimitCovenant limitCovenant : lmtCol.getLimitCovenant() ) {
						if(ICMSConstant.YES.equals(limitCovenant.getSingleCovenantInd())) {
							curLmt = limitCovenant;
						}
						else if(ICMSConstant.YES.equals(limitCovenant.getBeneInd())) {
							restBeneList.add((OBLimitCovenant) limitCovenant);
						}
						else if(ICMSConstant.YES.equals(limitCovenant.getDraweeInd())) {
							restDraweeList.add((OBLimitCovenant) limitCovenant);
						}
						else if(ICMSConstant.YES.equals(limitCovenant.getDrawerInd())) {
							restDrawerList.add((OBLimitCovenant) limitCovenant);
						}
						else if(ICMSConstant.YES.equals(limitCovenant.getGoodsRestrictionInd())) {
							restGoodsRestrictionList.add((OBLimitCovenant) limitCovenant);
						}
						else if(ICMSConstant.YES.equals(limitCovenant.getRestrictedBankInd())) {
							restBankList.add((OBLimitCovenant) limitCovenant);
						}
						else if(ICMSConstant.YES.equals(limitCovenant.getRestrictedCountryInd())) {
							restCountryList.add((OBLimitCovenant) limitCovenant);
						}
						else if(ICMSConstant.YES.equals(limitCovenant.getRestrictedCurrencyInd())) {
							restCurrencyList.add((OBLimitCovenant) limitCovenant);
						}
					}
					result.put("restCountryList",restCountryList);
					result.put("restCurrencyList",restCurrencyList);
					result.put("restBankList",restBankList);
					result.put("restDrawerList",restDrawerList);
					result.put("restDraweeList",restDraweeList);
					result.put("restBeneList",restBeneList);
					result.put("eventForEdit", map.get("event"));
					Collections.sort(restGoodsRestrictionList, OBLimitCovenant.Comparators.GOODS_RESTRICTION_COMPARATOR);
					result.put(SESSION_COVENANT_GOODS_RESTRICTION_LIST,restGoodsRestrictionList);
					result.put("lmtTrxObj", lmtTrxObj);
				}
			}else {
				flag = true;
				DefaultLogger.debug(this, "********** Before searchLimitByLmtId function   lmtID" + lmtID);
				if(lmtTrxObj.getLimit().getLimitCovenant() == null || lmtTrxObj.getLimit().getLimitCovenant().length==0) {
				lmtTrxObj = proxy.searchLimitByLmtId(lmtID);
				DefaultLogger.debug(this, "********** after searchLimitByLmtId function  lmtTrxObj" + lmtTrxObj);
				ILimitCovenant[] covMain = lmtDao.getCovenantData(lmtID,main);
				if(covMain!=null && covMain.length!=0) {
				lmtTrxObj.getLimit().setLimitCovenant(covMain);
				}
				lmtCol = (ILimit) lmtTrxObj.getLimit() ;
				if(lmtCol.getLimitCovenant()!=null) {
					for(ILimitCovenant limitCovenant : lmtCol.getLimitCovenant() ) {
						if(ICMSConstant.YES.equals(limitCovenant.getSingleCovenantInd())) {
							curLmt = limitCovenant;
						}
						else if(ICMSConstant.YES.equals(limitCovenant.getBeneInd())) {
							restBeneList.add((OBLimitCovenant) limitCovenant);
						}
						else if(ICMSConstant.YES.equals(limitCovenant.getDraweeInd())) {
							restDraweeList.add((OBLimitCovenant) limitCovenant);
						}
						else if(ICMSConstant.YES.equals(limitCovenant.getDrawerInd())) {
							restDrawerList.add((OBLimitCovenant) limitCovenant);
						}
						else if(ICMSConstant.YES.equals(limitCovenant.getGoodsRestrictionInd())) {
							restGoodsRestrictionList.add((OBLimitCovenant) limitCovenant);
						}
						else if(ICMSConstant.YES.equals(limitCovenant.getRestrictedBankInd())) {
							restBankList.add((OBLimitCovenant) limitCovenant);
						}
						else if(ICMSConstant.YES.equals(limitCovenant.getRestrictedCountryInd())) {
							restCountryList.add((OBLimitCovenant) limitCovenant);
						}
						else if(ICMSConstant.YES.equals(limitCovenant.getRestrictedCurrencyInd())) {
							restCurrencyList.add((OBLimitCovenant) limitCovenant);
						}
					}
//					int arrLength = lmtCol.getLimitCovenant().length;
//					for(int i=0;i<arrLength;i++) {
//						curLmt = lmtCol.getLimitCovenant()[i];
//						if(!"".equals(curLmt.getRestrictedCountryname())&&curLmt.getRestrictedCountryname()!=null) {
//							OBLimitCovenant ob = new OBLimitCovenant();
//							ob.setRestrictedCountryname(curLmt.getRestrictedCountryname());
//							ob.setRestrictedAmount(curLmt.getRestrictedAmount());
//							restCountryList.add(ob);
//						}
//						if(!"".equals(curLmt.getRestrictedCurrency())&&curLmt.getRestrictedCurrency()!=null) {
//							OBLimitCovenant ob = new OBLimitCovenant();
//							ob.setRestrictedCurrency(curLmt.getRestrictedCurrency());
//							ob.setRestrictedCurrencyAmount(curLmt.getRestrictedCurrencyAmount());
//							restCurrencyList.add(ob);
//						}
//						if(!"".equals(curLmt.getRestrictedBank())&&curLmt.getRestrictedBank()!=null) {
//							OBLimitCovenant ob = new OBLimitCovenant();
//							ob.setRestrictedBank(curLmt.getRestrictedBank());
//							ob.setRestrictedBankAmount(curLmt.getRestrictedBankAmount());
//							restBankList.add(ob);
//						}
//						if(!"".equals(curLmt.getDrawerName())&&curLmt.getDrawerName()!=null) {
//							OBLimitCovenant ob = new OBLimitCovenant();
//							ob.setDrawerName(curLmt.getDrawerName());
//							ob.setDrawerAmount(curLmt.getDrawerAmount());
//							ob.setDrawerCustId(curLmt.getDrawerCustId());
//							ob.setDrawerCustName(curLmt.getDrawerCustName());
//							restDrawerList.add(ob);
//						}
//						if(!"".equals(curLmt.getDrawerName())&&curLmt.getDrawerName()!=null) {
//							OBLimitCovenant ob = new OBLimitCovenant();
//							ob.setDraweeName(curLmt.getDraweeName());
//							ob.setDraweeAmount(curLmt.getDraweeAmount());
//							ob.setDraweeCustId(curLmt.getDraweeCustId());
//							ob.setDraweeCustName(curLmt.getDraweeCustName());
//							restDraweeList.add(ob);
//						}
//						if(!"".equals(curLmt.getBeneName())&&curLmt.getBeneName()!=null) {
//							OBLimitCovenant ob = new OBLimitCovenant();
//							ob.setBeneName(curLmt.getBeneName());
//							ob.setBeneAmount(curLmt.getBeneAmount());
//							ob.setBeneCustId(curLmt.getBeneCustId());
//							ob.setBeneCustName(curLmt.getBeneCustName());
//							restBeneList.add(ob);
//						}
//						}
					result.put("restCountryList",restCountryList);
					result.put("restCurrencyList",restCurrencyList);
					result.put("restBankList",restBankList);
					result.put("restDrawerList",restDrawerList);
					result.put("restDraweeList",restDraweeList);
					result.put("restBeneList",restBeneList);
					result.put("eventForEdit", map.get("event"));
					Collections.sort(restGoodsRestrictionList, OBLimitCovenant.Comparators.GOODS_RESTRICTION_COMPARATOR);
					result.put(SESSION_COVENANT_GOODS_RESTRICTION_LIST,restGoodsRestrictionList);
			}
				ILimitCovenant[] covStg = lmtDao.getCovenantData(stgReferenceId,stg);
				if(covStg!=null && covStg.length!=0) {
					lmtTrxObj.getStagingLimit().setLimitCovenant(covStg);
				}
				result.put("lmtTrxObj", lmtTrxObj);
			}else {
				lmtCol = (ILimit) lmtTrxObj.getStagingLimit();
				if(lmtCol.getLimitCovenant()!=null) {
					
					for(ILimitCovenant limitCovenant : lmtCol.getLimitCovenant() ) {
						if(ICMSConstant.YES.equals(limitCovenant.getSingleCovenantInd())) {
							curLmt = limitCovenant;
						}
						else if(ICMSConstant.YES.equals(limitCovenant.getBeneInd())) {
							restBeneList.add((OBLimitCovenant) limitCovenant);
						}
						else if(ICMSConstant.YES.equals(limitCovenant.getDraweeInd())) {
							restDraweeList.add((OBLimitCovenant) limitCovenant);
						}
						else if(ICMSConstant.YES.equals(limitCovenant.getDrawerInd())) {
							restDrawerList.add((OBLimitCovenant) limitCovenant);
						}
						else if(ICMSConstant.YES.equals(limitCovenant.getGoodsRestrictionInd())) {
							restGoodsRestrictionList.add((OBLimitCovenant) limitCovenant);
						}
						else if(ICMSConstant.YES.equals(limitCovenant.getRestrictedBankInd())) {
							restBankList.add((OBLimitCovenant) limitCovenant);
						}
						else if(ICMSConstant.YES.equals(limitCovenant.getRestrictedCountryInd())) {
							restCountryList.add((OBLimitCovenant) limitCovenant);
						}
						else if(ICMSConstant.YES.equals(limitCovenant.getRestrictedCurrencyInd())) {
							restCurrencyList.add((OBLimitCovenant) limitCovenant);
						}
					}
					
					//int arrLength = lmtCol.getLimitCovenant().length;
//					for(int i=0;i<arrLength;i++) {
//						curLmt = lmtCol.getLimitCovenant()[i];
//						if(!"".equals(curLmt.getRestrictedCountryname())&&curLmt.getRestrictedCountryname()!=null) {
//							OBLimitCovenant ob = new OBLimitCovenant();
//							ob.setRestrictedCountryname(curLmt.getRestrictedCountryname());
//							ob.setRestrictedAmount(curLmt.getRestrictedAmount());
//							restCountryList.add(ob);
//						}
//						if(!"".equals(curLmt.getRestrictedCurrency())&&curLmt.getRestrictedCurrency()!=null) {
//							OBLimitCovenant ob = new OBLimitCovenant();
//							ob.setRestrictedCurrency(curLmt.getRestrictedCurrency());
//							ob.setRestrictedCurrencyAmount(curLmt.getRestrictedCurrencyAmount());
//							restCurrencyList.add(ob);
//						}
//						if(!"".equals(curLmt.getRestrictedBank())&&curLmt.getRestrictedBank()!=null) {
//							OBLimitCovenant ob = new OBLimitCovenant();
//							ob.setRestrictedBank(curLmt.getRestrictedBank());
//							ob.setRestrictedBankAmount(curLmt.getRestrictedBankAmount());
//							restBankList.add(ob);
//						}
//						if(!"".equals(curLmt.getDrawerName())&&curLmt.getDrawerName()!=null) {
//							OBLimitCovenant ob = new OBLimitCovenant();
//							ob.setDrawerName(curLmt.getDrawerName());
//							ob.setDrawerAmount(curLmt.getDrawerAmount());
//							ob.setDrawerCustId(curLmt.getDrawerCustId());
//							ob.setDrawerCustName(curLmt.getDrawerCustName());
//							restDrawerList.add(ob);
//						}
//						if(!"".equals(curLmt.getDrawerName())&&curLmt.getDrawerName()!=null) {
//							OBLimitCovenant ob = new OBLimitCovenant();
//							ob.setDraweeName(curLmt.getDraweeName());
//							ob.setDraweeAmount(curLmt.getDraweeAmount());
//							ob.setDraweeCustId(curLmt.getDraweeCustId());
//							ob.setDraweeCustName(curLmt.getDraweeCustName());
//							restDraweeList.add(ob);
//						}
//						if(!"".equals(curLmt.getBeneName())&&curLmt.getBeneName()!=null) {
//							OBLimitCovenant ob = new OBLimitCovenant();
//							ob.setBeneName(curLmt.getBeneName());
//							ob.setBeneAmount(curLmt.getBeneAmount());
//							ob.setBeneCustId(curLmt.getBeneCustId());
//							ob.setBeneCustName(curLmt.getBeneCustName());
//							restBeneList.add(ob);
//						}
//						
//						}
					result.put("restCountryList",restCountryList);
					result.put("restCurrencyList",restCurrencyList);
					result.put("restBankList",restBankList);
					result.put("restDrawerList",restDrawerList);
					result.put("restDraweeList",restDraweeList);
					result.put("restBeneList",restBeneList);
					result.put("eventForEdit", map.get("event"));
					Collections.sort(restGoodsRestrictionList, OBLimitCovenant.Comparators.GOODS_RESTRICTION_COMPARATOR);
					result.put(SESSION_COVENANT_GOODS_RESTRICTION_LIST,restGoodsRestrictionList);
					result.put("lmtTrxObj", lmtTrxObj);
			}
			}
			}	
			DefaultLogger.debug(this, "in PrepareCovenantDetailCommand.java ==130==>> lmtTrxObj.getReferenceID()"+ lmtTrxObj.getReferenceID());

			
			IGoodsMasterJdbc goodsMasterJdbc = (IGoodsMasterJdbc)BeanHouse.get("goodsMasterJdbc");
			List<String> parentGoodsCodeList = goodsMasterJdbc.getParentGoodsCodeList();
			
			result.put("limitProfileID",map.get("limitProfileID"));
			result.put("customerID",map.get("customerID"));
			result.put("limitId", lmtID); //Shiv
			result.put("lmtId", lmtID); //Shiv
			result.put("limitRef", limitRef);
			
			result.put("isCreate", isCreate);
			result.put("countryList", getCountryList(lmtDao.getCountryList()));
			result.put("currList", getCurrList(lmtDao.getCurrList()));
			result.put("bankList", getBankList(lmtDao.getBankList()));
			result.put("goodsList", getGoodsList(lmtDao.getGoodsList()));
			result.put(SESSION_DROPDOWN_GOODS_PARENT_LIST, getGoodsParentList(parentGoodsCodeList));
			result.put("incoTermList", getIncoTermList(lmtDao.getIncoTermList()));
			result.put(COVENANT_DETAIL_FORM, curLmt);
			result.put("sessionCriteria",map.get("sessionCriteria"));
			result.put("event", event);
			result.put("fromEvent", from_event);
			//result.put("eventForEdit", map.get("event"));
			result.put("index",map.get("index"));
			result.put("exceptionMap",map.get("exceptionMap"));			
			result.put("covenantRequired", curLmt != null?curLmt.getCovenantReqd():null);
			
		}
		catch (AccessDeniedException adex) {
			throw adex;
		}
		catch (Exception ex) {
			ex.printStackTrace();
			throw (new CommandProcessingException(ex.getMessage()));
		}
		temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		return temp;
	}
	
	private List getCountryList(List lst) {
		List lbValList = new ArrayList();
	try {
			
		for (int i = 0; i < lst.size(); i++) {
			String [] mgnrLst = (String[])lst.get(i);
				LabelValueBean lvBean = new LabelValueBean(mgnrLst[1],mgnrLst[0] );
				lbValList.add(lvBean);
		}
	} catch (Exception ex) {
	}
	return CommonUtil.sortDropdown(lbValList);
}
	private List getBankList(List lst) {
		List lbValList = new ArrayList();
	try {
			
		for (int i = 0; i < lst.size(); i++) {
			String [] mgnrLst = (String[])lst.get(i);
				LabelValueBean lvBean = new LabelValueBean(mgnrLst[1],mgnrLst[0] );
				lbValList.add(lvBean);
		}
	} catch (Exception ex) {
	}
	return CommonUtil.sortDropdown(lbValList);
}
	private List getCurrList(List lst) {
		List lbValList = new ArrayList();
	try {
			
		for (int i = 0; i < lst.size(); i++) {
			String [] mgnrLst = (String[])lst.get(i);
				LabelValueBean lvBean = new LabelValueBean(mgnrLst[1],mgnrLst[0] );
				lbValList.add(lvBean);
		}
	} catch (Exception ex) {
	}
	return CommonUtil.sortDropdown(lbValList);
}
	private List getGoodsList(List lst) {
		List lbValList = new ArrayList();
	try {
			
		for (int i = 0; i < lst.size(); i++) {
			String [] mgnrLst = (String[])lst.get(i);
				LabelValueBean lvBean = new LabelValueBean(mgnrLst[1],mgnrLst[0] );
				lbValList.add(lvBean);
		}
	} catch (Exception ex) {
	}
	return CommonUtil.sortDropdown(lbValList);
}
	
	private List getIncoTermList(List lst) {
		List lbValList = new ArrayList();
	try {
			
		for (int i = 0; i < lst.size(); i++) {
			String [] mgnrLst = (String[])lst.get(i);
				LabelValueBean lvBean = new LabelValueBean(mgnrLst[0],mgnrLst[0] );
				lbValList.add(lvBean);
		}
	} catch (Exception ex) {
	}
	return CommonUtil.sortDropdown(lbValList);
}
	
	private List getGoodsParentList(List<String> list) {
		if(list != null && !list.isEmpty()) {
			List lbValList = new ArrayList();
			try {
				for(String goodsItem : list) {
					LabelValueBean lvBean = new LabelValueBean(goodsItem,goodsItem);
					lbValList.add(lvBean);
				}
				
			} catch (Exception ex) {
				DefaultLogger.error(this, "Exception caught in getting list :: ", ex);
			}
			return CommonUtil.sortDropdown(lbValList);
		}
		return list;
	}
}
