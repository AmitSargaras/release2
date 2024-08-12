package com.integrosys.cms.ui.manualinput.line.covenant;


import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.apache.commons.lang.ArrayUtils;
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
import com.integrosys.cms.app.customer.bus.ICustomerSysXRef;
import com.integrosys.cms.app.customer.bus.ILineCovenant;
import com.integrosys.cms.app.customer.bus.OBCustomerSysXRef;
import com.integrosys.cms.app.customer.bus.OBLineCovenant;
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
import com.integrosys.cms.app.limit.bus.LimitException;
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
import com.integrosys.cms.ui.manualinput.limit.covenant.ILmtCovenantConstants;
import com.integrosys.cms.ui.manualinput.security.MISecurityUIHelper;
import com.integrosys.component.bizstructure.app.bus.ITeam;

public class PrepareCovenantDetailCommand extends AbstractCommand implements ILmtCovenantConstants,ILineCovenantConstants {
	
	public String[][] getParameterDescriptor() {
		return (new String[][] { { "lmtTrxObj", "com.integrosys.cms.app.limit.trx.ILimitTrxValue", SERVICE_SCOPE },
			    { "limitId", "java.lang.String", REQUEST_SCOPE },
			    { COVENANT_LINE_DETAIL_FORM, "java.lang.Object", FORM_SCOPE },
			    { "lineDetailId", "java.lang.String", REQUEST_SCOPE },
			    { "limitRef", "java.lang.String", REQUEST_SCOPE },
			    { "fromEventForLineCov", "java.lang.String", REQUEST_SCOPE },
			    { SESSION_COUNTRY_LIST_LINE, List.class.getName() , SERVICE_SCOPE },
				{ SESSION_CURRENCY_LIST_LINE, List.class.getName(), SERVICE_SCOPE },
				{ SESSION_BANK_RESTRICTION_LIST_LINE, List.class.getName(), SERVICE_SCOPE },
				{ SESSION_DRAWER_LIST_LINE, List.class.getName(), SERVICE_SCOPE },
				{ SESSION_DRAWEE_LIST_LINE, List.class.getName(), SERVICE_SCOPE },
				{ SESSION_BENE_LIST_LINE, List.class.getName(), SERVICE_SCOPE },
				{ SESSION_COVENANT_GOODS_RESTRICTION_LIST_LINE, List.class.getName() , SERVICE_SCOPE },
				{ SESSION_DROPDOWN_COUNTRY_LIST, List.class.getName(), SERVICE_SCOPE },
				{ SESSION_DROPDOWN_CURRENCY_LIST, List.class.getName(), SERVICE_SCOPE },
				{ SESSION_DROPDOWN_BANK_RESTRICTION_LIST, List.class.getName(), SERVICE_SCOPE },
				{ SESSION_DROPDOWN_GOODS_PARENT_LIST, List.class.getName(), SERVICE_SCOPE },
				{"eventForEdit", "java.lang.String", REQUEST_SCOPE },
				{ "limitProfileID", "java.lang.String", REQUEST_SCOPE },
				{ "xReferenceId", "java.lang.String", REQUEST_SCOPE },
				{ "customerID", "java.lang.String", REQUEST_SCOPE }, 
				{ "index", "java.lang.String", REQUEST_SCOPE },
				{ "fromEvent", "java.lang.String", REQUEST_SCOPE },
				{ "event", "java.lang.String", REQUEST_SCOPE },
				{ IGlobalConstant.USER_TEAM, "com.integrosys.component.bizstructure.app.bus.ITeam", GLOBAL_SCOPE },
				{ "trxID", "java.lang.String", REQUEST_SCOPE },
				{ "theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE },
				{ "sessionCriteria", "java.lang.String", REQUEST_SCOPE },
				{ "indexID", "java.lang.String", REQUEST_SCOPE },
				{ "sourceRefNo", "java.lang.String", REQUEST_SCOPE },
				{ "isCreate", "java.lang.String", REQUEST_SCOPE },
				{ "facCat", "java.lang.String", REQUEST_SCOPE },
				{ "fundedAmount", "java.lang.String", REQUEST_SCOPE },
				{ "nonFundedAmount", "java.lang.String", REQUEST_SCOPE  },
				{ "memoExposer", "java.lang.String", REQUEST_SCOPE },
				{ "sanctionedLimit", "java.lang.String", REQUEST_SCOPE },
				{ "xRefForLineCov", ILimitSysXRef.class.getName() , SERVICE_SCOPE },
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
				{"isLineCreate", "java.lang.String", REQUEST_SCOPE},
				{ "lineCovenantObj",ILineCovenant.class.getName(), SERVICE_SCOPE },
				{ "adhocFacility","java.lang.String", REQUEST_SCOPE },
		});
	}

	public String[][] getResultDescriptor() {
		return (new String[][] { { "lmtTrxObj", "com.integrosys.cms.app.limit.trx.ILimitTrxValue", SERVICE_SCOPE },
				{ COVENANT_LINE_DETAIL_FORM, "java.lang.Object", FORM_SCOPE },
				{ "wip", "java.lang.String", REQUEST_SCOPE },
				{ "event", "java.lang.String", REQUEST_SCOPE },
				{ "fromEventForLineCov", "java.lang.String", REQUEST_SCOPE },
				{ "trxID", "java.lang.String", SERVICE_SCOPE },
				{ SESSION_COUNTRY_LIST_LINE, List.class.getName() , SERVICE_SCOPE },
				{ SESSION_CURRENCY_LIST_LINE, List.class.getName(), SERVICE_SCOPE },
				{ SESSION_BANK_RESTRICTION_LIST_LINE, List.class.getName(), SERVICE_SCOPE },
				{ SESSION_DRAWER_LIST_LINE, List.class.getName(), SERVICE_SCOPE },
				{ SESSION_DRAWEE_LIST_LINE, List.class.getName(), SERVICE_SCOPE },
				{ SESSION_BENE_LIST_LINE, List.class.getName(), SERVICE_SCOPE },
				{ SESSION_COVENANT_GOODS_RESTRICTION_LIST_LINE, List.class.getName() , SERVICE_SCOPE },
				{ SESSION_DROPDOWN_COUNTRY_LIST, List.class.getName(), SERVICE_SCOPE },
				{ SESSION_DROPDOWN_CURRENCY_LIST, List.class.getName(), SERVICE_SCOPE },
				{ SESSION_DROPDOWN_BANK_RESTRICTION_LIST, List.class.getName(), SERVICE_SCOPE },
				{ SESSION_DROPDOWN_GOODS_PARENT_LIST, List.class.getName(), SERVICE_SCOPE },
				{"isLineCreate", "java.lang.String", REQUEST_SCOPE},
				{"eventForEdit", "java.lang.String", REQUEST_SCOPE },
				{ "countryList", "java.util.List", SERVICE_SCOPE },
				{ "goodsList", "java.util.List", SERVICE_SCOPE },
				{ "bankList", "java.util.List", SERVICE_SCOPE },
				{ "currList", "java.util.List", SERVICE_SCOPE },
				{ "fromEvent", "java.lang.String", REQUEST_SCOPE },
				{ "limitId", "java.lang.String", REQUEST_SCOPE },
				{ "limitRef", "java.lang.String", REQUEST_SCOPE },
				{ "customerID", "java.lang.String", REQUEST_SCOPE }, 
				{ "limitProfileID", "java.lang.String", REQUEST_SCOPE },
				{ "xReferenceId", "java.lang.String", REQUEST_SCOPE },
				{ "lmtId", "java.lang.String", SERVICE_SCOPE },
			    { "lineDetailId", "java.lang.String", REQUEST_SCOPE },
				{ "status", "java.lang.String", REQUEST_SCOPE },
				{ "indexID", "java.lang.String", REQUEST_SCOPE },
				{ "index", "java.lang.String", REQUEST_SCOPE },
				{ "sourceRefNo", "java.lang.String", REQUEST_SCOPE },
				{ "xRefsId", Long.class.getName(), SERVICE_SCOPE },
				{ "covenantMap", Map.class.getName(), SERVICE_SCOPE },
				{ "covenantRequired", String.class.getName(), REQUEST_SCOPE },
				{"isLineCreate", "java.lang.String", REQUEST_SCOPE},
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
				{ "xRefForLineCov", ILimitSysXRef.class.getName() , SERVICE_SCOPE },
				{ "adhocFacility","java.lang.String", REQUEST_SCOPE },
				});
				
	}

	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException,
			AccessDeniedException {
		HashMap result = new HashMap();
		HashMap exceptionMap = new HashMap();
		HashMap temp = new HashMap();
		HashMap<String,String> covenantMap = new HashMap<String,String>();
		String lmtIdValue = "";
		DefaultLogger.debug(this, "in PrepareCovenantDetailCommand.java ==108==>> (map.get(limitId))");
		String lmtID = (String) (map.get("limitId")); //CMS_LSP_APPR_LMTS_ID
		DefaultLogger.debug(this, "in PrepareCovenantDetailCommand.java ==108==>> (map.get(limitId))"+lmtID);
		boolean flag = false;
		ILimitProfileTrxValue limitProfileTrxVal = null;
		List<String> updateEvents = Arrays.asList("add_country_rest","add_currency_rest","add_bank_rest","add_drawer_rest","add_drawee_rest",
				"add_bene_rest","edit_bene_rest","edit_drawer_rest","edit_drawee_rest","remove_country_rest","remove_currency_rest","remove_bank_rest",
				"remove_drawer_rest","remove_drawee_rest","remove_bene_rest","add_goods_restriction","delete_goods_restriction");
		
		try {
			OBTrxContext ctx = (OBTrxContext) map.get("theOBTrxContext");
			String event = (String) (map.get("event"));
			String from_event = (String) (map.get("fromEvent"));
			String fromEventForLineCov = (String) (map.get("fromEventForLineCov"));
			String limitProfileID = (String) (map.get("limitProfileID"));	
			String limitRef = (String) (map.get("limitRef"));
			String lineDetailId = (String) map.get("lineDetailId");
			String xReferenceId = (String) map.get("xReferenceId");
			String isCreate =  (String) map.get("isCreate");
			String isLineCreate=(String) map.get("isLineCreate");
			String adhocFacility=(String) map.get("adhocFacility");
			System.out.println("PrepareCovenantDetailCommand.java=>adhocFacility=>"+adhocFacility);
			result.put("adhocFacility", adhocFacility);
			result.put("isLineCreate", isLineCreate);
			List<OBLineCovenant> restCountryListForLine =null;
			List<OBLineCovenant> restCurrencyListForLine = null;
			List<OBLineCovenant> restBankListForLine = null;
			List<OBLineCovenant> restDrawerListForLine = null;
			List<OBLineCovenant> restDraweeListForLine = null;
			List<OBLineCovenant> restBeneListForLine = null;
			List<OBLineCovenant> restGoodsRestrictionListForLine = null;
			ILimitSysXRef xRefForLineCov = null;
			
			if("Yes".equals(isLineCreate) && "prepare_create_ubs".equals(fromEventForLineCov)
					&& !"add_country_rest".equals(event) && !"add_currency_rest".equals(event)
					&& !"add_bank_rest".equals(event) && !"add_drawer_rest".equals(event)
					&& !"add_drawee_rest".equals(event) && !"add_bene_rest".equals(event)
					&& !"remove_country_rest".equals(event) && !"remove_currency_rest".equals(event)
					&& !"remove_bank_rest".equals(event) && !"remove_drawer_rest".equals(event)
					&& !"remove_drawee_rest".equals(event) && !"remove_bene_rest".equals(event)
					&& !"refresh_goods_master".equals(event) && !"add_goods_restriction".equals(event)
					&& !"delete_goods_restriction".equals(event)
					&& !"create_covenant_detail_error".equals(event)
					) {
				restCountryListForLine = new ArrayList();
				restCurrencyListForLine = new ArrayList();
				restBankListForLine = new ArrayList();
				restDrawerListForLine = new ArrayList();
				restDraweeListForLine = new ArrayList();
				restBeneListForLine = new ArrayList();
				restGoodsRestrictionListForLine = new ArrayList();
				result.put("xRefForLineCov", xRefForLineCov);
			}else {
				xRefForLineCov = "prepare_create_ubs".equals(fromEventForLineCov)?null:(ILimitSysXRef) map.get("xRefForLineCov");
				restCountryListForLine = getEmptyOrDefault(map, SESSION_COUNTRY_LIST_LINE);
				restCurrencyListForLine = getEmptyOrDefault(map, SESSION_CURRENCY_LIST_LINE);
				restBankListForLine = getEmptyOrDefault(map, SESSION_BANK_RESTRICTION_LIST_LINE);
				restDrawerListForLine = getEmptyOrDefault(map, SESSION_DRAWER_LIST_LINE);
				restDraweeListForLine = getEmptyOrDefault(map, SESSION_DRAWEE_LIST_LINE);
				restBeneListForLine = getEmptyOrDefault(map, SESSION_BENE_LIST_LINE);
				restGoodsRestrictionListForLine = getEmptyOrDefault(map, SESSION_COVENANT_GOODS_RESTRICTION_LIST_LINE);
			}
			
			DefaultLogger.debug(this, "in PrepareCovenantDetailCommand.java ==124==>> limitProfileID"+ limitProfileID);
			DefaultLogger.debug(this, "in PrepareCovenantDetailCommand.java ==125==>> limitRef"+ limitRef);
			DefaultLogger.debug(this, "in PrepareCovenantDetailCommand.java ==126==>> lineDetailId"+ lineDetailId);
			DefaultLogger.debug(this, "in PrepareCovenantDetailCommand.java ==130==>> xReferenceId"+ xReferenceId);
			

			
			
			MILimitUIHelper helper = new MILimitUIHelper();
			CovenantMappingHelper covHelper= new CovenantMappingHelper();
			
			ILimitTrxValue lmtTrxObj = (ILimitTrxValue) map.get("lmtTrxObj");
			DefaultLogger.debug(this, "****************** INSIDE PrepareCovenantDetailCommand EVENT IS: " + event);
			String trxID = (String) (map.get("trxID"));
			DefaultLogger.debug(this, "********** Before PrepareCovenantDetailCommand function   lmtID" + lmtID);
			String stgReferenceId = lmtTrxObj.getStagingReferenceID();
			String index =(String) map.get("indexID");
			String sourceRefNo=(String) map.get("sourceRefNo");
			ILineCovenant lineCov = (ILineCovenant) map.get(COVENANT_LINE_DETAIL_FORM);
			DefaultLogger.debug(this, "****************** INSIDE ReadLmtDetailCmd EVENT IS: " + event);
			DefaultLogger.debug(this, "******************sourceRefNo:"+sourceRefNo);
			DefaultLogger.debug(this, "in PrepareCovenantDetailCommand.java ==152==>> trxID"+ trxID);
			DefaultLogger.debug(this, "in PrepareCovenantDetailCommand.java ==153==>> trxID"+ stgReferenceId);
			DefaultLogger.debug(this, "in PrepareCovenantDetailCommand.java ==154==>> trxID"+ lmtTrxObj.getReferenceID());
			
			ILimitDAO lmtDao = LimitDAOFactory.getDAO();
			ILineCovenant curLmt = null;
			String main = "mainTable";
			String stg = "stgTable";
			Locale locale = (Locale) map.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);
			
			String stgXrefId =null;
			String actualXrefId=null;
			if(xRefForLineCov!=null) {
				stgXrefId=String.valueOf(xRefForLineCov.getCustomerSysXRef().getXRefID()); 
				actualXrefId= lmtDao.getActualLineXrefIdNew(xRefForLineCov.getSID());
			}
			ILineCovenant[] covStg = xRefForLineCov!=null?xRefForLineCov.getCustomerSysXRef().getLineCovenant(): (ILineCovenant[]) lmtDao.getLineCovenantData(stgXrefId,stg);
			if(covStg == null || covStg.length==0) {
				//To get stage facility covenant details
				covStg = (ILineCovenant[]) getLimitCovenantDetails(lmtTrxObj,stg);
			}
			ILineCovenant[] covMain = (ILineCovenant[]) lmtDao.getLineCovenantData(actualXrefId,main);
			if(covStg == null || covMain.length==0) {
				//To get main facility covenant details
				covMain = (ILineCovenant[]) getLimitCovenantDetails(lmtTrxObj,main);
			}
			
			//check based on event not fromEvent
			
			// if cov from facility is blank, no need to copy
			if (EventConstant.EVENT_PREPARE_CREATE.equals(fromEventForLineCov) || EventConstant.EVENT_PREPARE_CREATE_UBS.equals(fromEventForLineCov)  
					|| EventConstant.EVENT_CREATE_RELEASED_LINE_DETAILS.equals(fromEventForLineCov)) {
					if(updateEvents.contains(event)) {
						curLmt = lineCov;
					}else {
						if(covStg!=null && covStg.length!=0) {
							for(ILineCovenant cov : covStg) {
								if(ICMSConstant.YES.equals(cov.getSingleCovenantInd())) {
									curLmt = cov;
								}
								else if(ICMSConstant.YES.equals(cov.getBeneInd())) {
									restBeneListForLine.add((OBLineCovenant) cov);
								}
								else if(ICMSConstant.YES.equals(cov.getDraweeInd())) {
									restDraweeListForLine.add((OBLineCovenant) cov);
								}
								else if(ICMSConstant.YES.equals(cov.getDrawerInd())) {
									restDrawerListForLine.add((OBLineCovenant) cov);
								}
								else if(ICMSConstant.YES.equals(cov.getGoodsRestrictionInd())) {
									restGoodsRestrictionListForLine.add((OBLineCovenant) cov);
								}
								else if(ICMSConstant.YES.equals(cov.getRestrictedBankInd())) {
									restBankListForLine.add((OBLineCovenant) cov);
								}
								else if(ICMSConstant.YES.equals(cov.getRestrictedCountryInd())) {
									restCountryListForLine.add((OBLineCovenant) cov);
								}
								else if(ICMSConstant.YES.equals(cov.getRestrictedCurrencyInd())) {
									restCurrencyListForLine.add((OBLineCovenant) cov);
								}
							}
						}
					}
				result.put(SESSION_COUNTRY_LIST_LINE,restCountryListForLine);
				result.put(SESSION_CURRENCY_LIST_LINE,restCurrencyListForLine);
				result.put(SESSION_BANK_RESTRICTION_LIST_LINE,restBankListForLine);
				result.put(SESSION_DRAWER_LIST_LINE,restDrawerListForLine);
				result.put(SESSION_DRAWEE_LIST_LINE,restDraweeListForLine);
				result.put(SESSION_BENE_LIST_LINE,restBeneListForLine);
				result.put(SESSION_COVENANT_GOODS_RESTRICTION_LIST_LINE,restGoodsRestrictionListForLine);
				result.put("lmtTrxObj", lmtTrxObj);
				
			}
			else if(EventConstant.EVENT_PREPARE_UPDATE_STATUS_UBS.equals(fromEventForLineCov) && "updateStatus".equals(from_event)) {
				ILimitSysXRef xref=xRefForLineCov; 
				if(xref != null && xref.getCustomerSysXRef().getLineCovenant()!=null && xref.getCustomerSysXRef().getLineCovenant().length!=0) {
					
					restCountryListForLine = new ArrayList();
					restCurrencyListForLine = new ArrayList();
					restBankListForLine = new ArrayList();
					restDrawerListForLine = new ArrayList();
					restDraweeListForLine = new ArrayList();
					restBeneListForLine = new ArrayList();
					
					restGoodsRestrictionListForLine = new ArrayList();
					for(ILineCovenant cov : covMain) {
						if(ICMSConstant.YES.equals(cov.getSingleCovenantInd())) {
							curLmt = cov;
						}
						else if(ICMSConstant.YES.equals(cov.getBeneInd())) {
							restBeneListForLine.add((OBLineCovenant) cov);
						}
						else if(ICMSConstant.YES.equals(cov.getDraweeInd())) {
							restDraweeListForLine.add((OBLineCovenant) cov);
						}
						else if(ICMSConstant.YES.equals(cov.getDrawerInd())) {
							restDrawerListForLine.add((OBLineCovenant) cov);
						}
						else if(ICMSConstant.YES.equals(cov.getGoodsRestrictionInd())) {
							restGoodsRestrictionListForLine.add((OBLineCovenant) cov);
						}
						else if(ICMSConstant.YES.equals(cov.getRestrictedBankInd())) {
							restBankListForLine.add((OBLineCovenant) cov);
						}
						else if(ICMSConstant.YES.equals(cov.getRestrictedCountryInd())) {
							restCountryListForLine.add((OBLineCovenant) cov);
						}
						else if(ICMSConstant.YES.equals(cov.getRestrictedCurrencyInd())) {
							restCurrencyListForLine.add((OBLineCovenant) cov);
						}
					}
					result.put("xRefsId",xref.getSID());
				
				result.put(SESSION_COUNTRY_LIST_LINE,restCountryListForLine);
				result.put(SESSION_CURRENCY_LIST_LINE,restCurrencyListForLine);
				result.put(SESSION_BANK_RESTRICTION_LIST_LINE,restBankListForLine);
				result.put(SESSION_DRAWER_LIST_LINE,restDrawerListForLine);
				result.put(SESSION_DRAWEE_LIST_LINE,restDraweeListForLine);
				result.put(SESSION_BENE_LIST_LINE,restBeneListForLine);
				result.put(SESSION_COVENANT_GOODS_RESTRICTION_LIST_LINE,restGoodsRestrictionListForLine);
				result.put("lmtTrxObj", lmtTrxObj);
				}
			}else if("return_from_line_covenant".equals(fromEventForLineCov)
					&& covStg!=null && covStg.length!=0) {
				
				if("prepare_edit_covenant_detail".equals(event)) {
					restCountryListForLine = new ArrayList();
					restCurrencyListForLine = new ArrayList();
					restBankListForLine = new ArrayList();
					restDrawerListForLine = new ArrayList();
					restDraweeListForLine = new ArrayList();
					restBeneListForLine = new ArrayList();
					for(ILineCovenant cov : covStg) {
						if(ICMSConstant.YES.equals(cov.getSingleCovenantInd())) {
							curLmt = cov;
						}
						else if(ICMSConstant.YES.equals(cov.getBeneInd())) {
							restBeneListForLine.add((OBLineCovenant) cov);
						}
						else if(ICMSConstant.YES.equals(cov.getDraweeInd())) {
							restDraweeListForLine.add((OBLineCovenant) cov);
						}
						else if(ICMSConstant.YES.equals(cov.getDrawerInd())) {
							restDrawerListForLine.add((OBLineCovenant) cov);
						}
						else if(ICMSConstant.YES.equals(cov.getGoodsRestrictionInd())) {
							restGoodsRestrictionListForLine.add((OBLineCovenant) cov);
						}
						else if(ICMSConstant.YES.equals(cov.getRestrictedBankInd())) {
							restBankListForLine.add((OBLineCovenant) cov);
						}
						else if(ICMSConstant.YES.equals(cov.getRestrictedCountryInd())) {
							restCountryListForLine.add((OBLineCovenant) cov);
						}
						else if(ICMSConstant.YES.equals(cov.getRestrictedCurrencyInd())) {
							restCurrencyListForLine.add((OBLineCovenant) cov);
						}
					}
					result.put(SESSION_COUNTRY_LIST_LINE,restCountryListForLine);
					result.put(SESSION_CURRENCY_LIST_LINE,restCurrencyListForLine);
					result.put(SESSION_BANK_RESTRICTION_LIST_LINE,restBankListForLine);
					result.put(SESSION_DRAWER_LIST_LINE,restDrawerListForLine);
					result.put(SESSION_DRAWEE_LIST_LINE,restDraweeListForLine);
					result.put(SESSION_BENE_LIST_LINE,restBeneListForLine);
					result.put(SESSION_COVENANT_GOODS_RESTRICTION_LIST_LINE,restGoodsRestrictionListForLine);
					result.put("lmtTrxObj", lmtTrxObj);
				}
				
				
			}
			else if (EventConstant.EVENT_PREPARE_UPDATE.equals(fromEventForLineCov) || EventConstant.EVENT_PREPARE_UPDATE_UBS.equals(fromEventForLineCov) || EventConstant.EVENT_PREPARE_UPDATE_TS.equals(fromEventForLineCov)
					|| EventConstant.EVENT_PREPARE_UPDATE_REJECTED.equals(fromEventForLineCov) || EventConstant.EVENT_PREPARE_UPDATE_UBS_REJECTED.equals(fromEventForLineCov)
					|| EventConstant.EVENT_EDIT_RELEASED_LINE_DETAILS.equals(fromEventForLineCov) ||EventConstant.EVENT_EDIT_UDF.equals(fromEventForLineCov)
					|| EventConstant.EVENT_EDIT_RELEASED_LINE_DETAILS_REJECTED.equals(fromEventForLineCov) ||EventConstant.EVENT_EDIT_UDF_REJECTED.equals(fromEventForLineCov)
					|| EventConstant.EVENT_PREPARE_UPDATE_STATUS_UBS.equals(fromEventForLineCov) || EventConstant.EVENT_UPDATE_STATUS_UDF.equals(fromEventForLineCov) || EventConstant.EVENT_UPDATE_STATUS_RELEASED_LINE_DETAILS.equals(fromEventForLineCov)
					|| EventConstant.EVENT_UPDATE_STATUS_UBS_ERROR.equals(fromEventForLineCov) || EventConstant.EVENT_PREPARE_UPDATE_STATUS_TS.equals(fromEventForLineCov)
					) {

				ILimitSysXRef xref=xRefForLineCov; 
				
				if(xref != null && xref.getCustomerSysXRef().getLineCovenant()!=null && xref.getCustomerSysXRef().getLineCovenant().length!=0) {
					 if(EventConstant.EVENT_PREPARE_UPDATE_UBS.equals(fromEventForLineCov) && "edit_covenant_detail".equals(event)
							&& !"add_country_rest".equals(event) && !"add_currency_rest".equals(event)
							&& !"add_bank_rest".equals(event) && !"add_drawer_rest".equals(event)
							&& !"add_drawee_rest".equals(event) && !"add_bene_rest".equals(event)
							&& !"remove_country_rest".equals(event) && !"remove_currency_rest".equals(event)
							&& !"remove_bank_rest".equals(event) && !"remove_drawer_rest".equals(event)
							&& !"remove_drawee_rest".equals(event) && !"remove_bene_rest".equals(event)
							&& !"refresh_goods_master".equals(event) && !"add_goods_restriction".equals(event)
							&& !"delete_goods_restriction".equals(event)
							) {
								restCountryListForLine = new ArrayList();
								restCurrencyListForLine = new ArrayList();
								restBankListForLine = new ArrayList();
								restDrawerListForLine = new ArrayList();
								restDraweeListForLine = new ArrayList();
								restBeneListForLine = new ArrayList();
								restGoodsRestrictionListForLine = new ArrayList();
					
								//To map facility covenant with line covenant
								xref=getStageLimitCovDetailsToUpdateLineCov(lmtTrxObj,xRefForLineCov);
								
								covMain=xref.getCustomerSysXRef().getLineCovenant();
								
								for(ILineCovenant cov : covMain) {
									if(ICMSConstant.YES.equals(cov.getSingleCovenantInd())) {
										curLmt = cov;
									}
									else if(ICMSConstant.YES.equals(cov.getBeneInd())) {
										restBeneListForLine.add((OBLineCovenant) cov);
									}
									else if(ICMSConstant.YES.equals(cov.getDraweeInd())) {
										restDraweeListForLine.add((OBLineCovenant) cov);
									}
									else if(ICMSConstant.YES.equals(cov.getDrawerInd())) {
										restDrawerListForLine.add((OBLineCovenant) cov);
									}
									else if(ICMSConstant.YES.equals(cov.getGoodsRestrictionInd())) {
										restGoodsRestrictionListForLine.add((OBLineCovenant) cov);
									}
									else if(ICMSConstant.YES.equals(cov.getRestrictedBankInd())) {
										restBankListForLine.add((OBLineCovenant) cov);
									}
									else if(ICMSConstant.YES.equals(cov.getRestrictedCountryInd())) {
										restCountryListForLine.add((OBLineCovenant) cov);
									}
									else if(ICMSConstant.YES.equals(cov.getRestrictedCurrencyInd())) {
										restCurrencyListForLine.add((OBLineCovenant) cov);
									}
								}
								result.put("xRefsId",xref.getSID());
							}else if(EventConstant.EVENT_PREPARE_UPDATE_UBS.equals(fromEventForLineCov) && "prepare_edit_covenant_detail".equals(event)) {
								covMain=xref.getCustomerSysXRef().getLineCovenant();
								for(ILineCovenant cov : covMain) {
									if(ICMSConstant.YES.equals(cov.getSingleCovenantInd())) {
										curLmt = cov;
									}
									else if(ICMSConstant.YES.equals(cov.getBeneInd())) {
										restBeneListForLine.add((OBLineCovenant) cov);
									}
									else if(ICMSConstant.YES.equals(cov.getDraweeInd())) {
										restDraweeListForLine.add((OBLineCovenant) cov);
									}
									else if(ICMSConstant.YES.equals(cov.getDrawerInd())) {
										restDrawerListForLine.add((OBLineCovenant) cov);
									}
									else if(ICMSConstant.YES.equals(cov.getGoodsRestrictionInd())) {
										restGoodsRestrictionListForLine.add((OBLineCovenant) cov);
									}
									else if(ICMSConstant.YES.equals(cov.getRestrictedBankInd())) {
										restBankListForLine.add((OBLineCovenant) cov);
									}
									else if(ICMSConstant.YES.equals(cov.getRestrictedCountryInd())) {
										restCountryListForLine.add((OBLineCovenant) cov);
									}
									else if(ICMSConstant.YES.equals(cov.getRestrictedCurrencyInd())) {
										restCurrencyListForLine.add((OBLineCovenant) cov);
									}
								}
								result.put("xRefsId",xref.getSID());
							}
				}
				//for add-reject-resubmit
				else if(EventConstant.EVENT_PREPARE_UPDATE_UBS.equals(fromEventForLineCov) 
						&& !"add_country_rest".equals(event) && !"add_currency_rest".equals(event)
						&& !"add_bank_rest".equals(event) && !"add_drawer_rest".equals(event)
						&& !"add_drawee_rest".equals(event) && !"add_bene_rest".equals(event)
						&& !"remove_country_rest".equals(event) && !"remove_currency_rest".equals(event)
						&& !"remove_bank_rest".equals(event) && !"remove_drawer_rest".equals(event)
						&& !"remove_drawee_rest".equals(event) && !"remove_bene_rest".equals(event)
						&& !"refresh_goods_master".equals(event) && !"add_goods_restriction".equals(event)
						&& !"delete_goods_restriction".equals(event)
						&& covStg!=null && covStg.length!=0) {
					
					restCountryListForLine = new ArrayList();
					restCurrencyListForLine = new ArrayList();
					restBankListForLine = new ArrayList();
					restDrawerListForLine = new ArrayList();
					restDraweeListForLine = new ArrayList();
					restBeneListForLine = new ArrayList();
					restGoodsRestrictionListForLine = new ArrayList();
					result.put("xRefsId",xref.getSID());
					
					for(ILineCovenant cov : covStg) {
						if(ICMSConstant.YES.equals(cov.getSingleCovenantInd())) {
							curLmt = cov;
						}
						else if(ICMSConstant.YES.equals(cov.getBeneInd())) {
							restBeneListForLine.add((OBLineCovenant) cov);
						}
						else if(ICMSConstant.YES.equals(cov.getDraweeInd())) {
							restDraweeListForLine.add((OBLineCovenant) cov);
						}
						else if(ICMSConstant.YES.equals(cov.getDrawerInd())) {
							restDrawerListForLine.add((OBLineCovenant) cov);
						}
						else if(ICMSConstant.YES.equals(cov.getGoodsRestrictionInd())) {
							restGoodsRestrictionListForLine.add((OBLineCovenant) cov);
						}
						else if(ICMSConstant.YES.equals(cov.getRestrictedBankInd())) {
							restBankListForLine.add((OBLineCovenant) cov);
						}
						else if(ICMSConstant.YES.equals(cov.getRestrictedCountryInd())) {
							restCountryListForLine.add((OBLineCovenant) cov);
						}
						else if(ICMSConstant.YES.equals(cov.getRestrictedCurrencyInd())) {
							restCurrencyListForLine.add((OBLineCovenant) cov);
						}
					}
				}else {
					curLmt = lineCov;
					result.put("xRefsId",xref.getSID());
				}
				
				result.put(SESSION_COUNTRY_LIST_LINE,restCountryListForLine);
				result.put(SESSION_CURRENCY_LIST_LINE,restCurrencyListForLine);
				result.put(SESSION_BANK_RESTRICTION_LIST_LINE,restBankListForLine);
				result.put(SESSION_DRAWER_LIST_LINE,restDrawerListForLine);
				result.put(SESSION_DRAWEE_LIST_LINE,restDraweeListForLine);
				result.put(SESSION_BENE_LIST_LINE,restBeneListForLine);
				result.put(SESSION_COVENANT_GOODS_RESTRICTION_LIST_LINE,restGoodsRestrictionListForLine);
				result.put("lmtTrxObj", lmtTrxObj);
				
			}
			else if(EventConstant.EVENT_CLOSE_UDF.equals(fromEventForLineCov) || EventConstant.EVENT_CLOSE_RELEASED_LINE_DETAILS.equals(fromEventForLineCov)
					|| EventConstant.EVENT_REOPEN_UDF.equals(fromEventForLineCov) || EventConstant.EVENT_REOPEN_RELEASED_LINE_DETAILS.equals(fromEventForLineCov)
					||EventConstant.EVENT_CLOSE_UDF_REJECTED.equals(fromEventForLineCov) || EventConstant.EVENT_CLOSE_RELEASED_LINE_DETAILS_REJECTED.equals(fromEventForLineCov)
					|| EventConstant.EVENT_REOPEN_UDF_REJECTED.equals(fromEventForLineCov) || 
					EventConstant.EVENT_REOPEN_RELEASED_LINE_DETAILS_REJECTED.equals(fromEventForLineCov)){
					ILimitSysXRef xref=xRefForLineCov; 
					
					if(xref.getCustomerSysXRef().getLineCovenant()!=null && xref.getCustomerSysXRef().getLineCovenant().length!=0) {
						for(ILineCovenant cov : covMain) {
							if(ICMSConstant.YES.equals(cov.getSingleCovenantInd())) {
								curLmt = cov;
							}
							else if(ICMSConstant.YES.equals(cov.getBeneInd())) {
								restBeneListForLine.add((OBLineCovenant) cov);
							}
							else if(ICMSConstant.YES.equals(cov.getDraweeInd())) {
								restDraweeListForLine.add((OBLineCovenant) cov);
							}
							else if(ICMSConstant.YES.equals(cov.getDrawerInd())) {
								restDrawerListForLine.add((OBLineCovenant) cov);
							}
							else if(ICMSConstant.YES.equals(cov.getGoodsRestrictionInd())) {
								restGoodsRestrictionListForLine.add((OBLineCovenant) cov);
							}
							else if(ICMSConstant.YES.equals(cov.getRestrictedBankInd())) {
								restBankListForLine.add((OBLineCovenant) cov);
							}
							else if(ICMSConstant.YES.equals(cov.getRestrictedCountryInd())) {
								restCountryListForLine.add((OBLineCovenant) cov);
							}
							else if(ICMSConstant.YES.equals(cov.getRestrictedCurrencyInd())) {
								restCurrencyListForLine.add((OBLineCovenant) cov);
							}
						}
					}
					result.put(SESSION_COUNTRY_LIST_LINE,restCountryListForLine);
					result.put(SESSION_CURRENCY_LIST_LINE,restCurrencyListForLine);
					result.put(SESSION_BANK_RESTRICTION_LIST_LINE,restBankListForLine);
					result.put(SESSION_DRAWER_LIST_LINE,restDrawerListForLine);
					result.put(SESSION_DRAWEE_LIST_LINE,restDraweeListForLine);
					result.put(SESSION_BENE_LIST_LINE,restBeneListForLine);
					result.put(SESSION_COVENANT_GOODS_RESTRICTION_LIST_LINE,restGoodsRestrictionListForLine);
					result.put("lmtTrxObj", lmtTrxObj);
					
				} 
				else if("read_ubs".equals(fromEventForLineCov)&&"view".equals(from_event)){
					ILimitSysXRef xref=xRefForLineCov; 
					restCountryListForLine = new ArrayList();
					restCurrencyListForLine = new ArrayList();
					restBankListForLine = new ArrayList();
					restDrawerListForLine = new ArrayList();
					restDraweeListForLine = new ArrayList();
					restBeneListForLine = new ArrayList();
					restGoodsRestrictionListForLine = new ArrayList();
					
					if(xref.getCustomerSysXRef().getLineCovenant()!=null && xref.getCustomerSysXRef().getLineCovenant().length!=0) {
						covMain = xRefForLineCov.getCustomerSysXRef().getLineCovenant();
						for(ILineCovenant cov : covMain) {
							if(ICMSConstant.YES.equals(cov.getSingleCovenantInd())) {
								curLmt = cov;
							}
							else if(ICMSConstant.YES.equals(cov.getBeneInd())) {
								restBeneListForLine.add((OBLineCovenant) cov);
							}
							else if(ICMSConstant.YES.equals(cov.getDraweeInd())) {
								restDraweeListForLine.add((OBLineCovenant) cov);
							}
							else if(ICMSConstant.YES.equals(cov.getDrawerInd())) {
								restDrawerListForLine.add((OBLineCovenant) cov);
							}
							else if(ICMSConstant.YES.equals(cov.getGoodsRestrictionInd())) {
								restGoodsRestrictionListForLine.add((OBLineCovenant) cov);
							}
							else if(ICMSConstant.YES.equals(cov.getRestrictedBankInd())) {
								restBankListForLine.add((OBLineCovenant) cov);
							}
							else if(ICMSConstant.YES.equals(cov.getRestrictedCountryInd())) {
								restCountryListForLine.add((OBLineCovenant) cov);
							}
							else if(ICMSConstant.YES.equals(cov.getRestrictedCurrencyInd())) {
								restCurrencyListForLine.add((OBLineCovenant) cov);
							}
						}
					}
					result.put(SESSION_COUNTRY_LIST_LINE,restCountryListForLine);
					result.put(SESSION_CURRENCY_LIST_LINE,restCurrencyListForLine);
					result.put(SESSION_BANK_RESTRICTION_LIST_LINE,restBankListForLine);
					result.put(SESSION_DRAWER_LIST_LINE,restDrawerListForLine);
					result.put(SESSION_DRAWEE_LIST_LINE,restDraweeListForLine);
					result.put(SESSION_BENE_LIST_LINE,restBeneListForLine);
					result.put(SESSION_COVENANT_GOODS_RESTRICTION_LIST_LINE,restGoodsRestrictionListForLine);
					result.put("lmtTrxObj", lmtTrxObj);
				}
				//added below code to read the stage line details and display to the checker
				else if (("process".equals(from_event) || "prepare_close".equals(from_event) || "view".equals(from_event))
						&& "prepare_edit_covenant_detail".equals(event)) {
				
					if(covStg!=null && covStg.length!=0) {
						
						for(ILineCovenant cov : covStg) {
							if(ICMSConstant.YES.equals(cov.getSingleCovenantInd())) {
								curLmt = cov;
							}
							else if(ICMSConstant.YES.equals(cov.getBeneInd())) {
								restBeneListForLine.add((OBLineCovenant) cov);
							}
							else if(ICMSConstant.YES.equals(cov.getDraweeInd())) {
								restDraweeListForLine.add((OBLineCovenant) cov);
							}
							else if(ICMSConstant.YES.equals(cov.getDrawerInd())) {
								restDrawerListForLine.add((OBLineCovenant) cov);
							}
							else if(ICMSConstant.YES.equals(cov.getGoodsRestrictionInd())) {
								restGoodsRestrictionListForLine.add((OBLineCovenant) cov);
							}
							else if(ICMSConstant.YES.equals(cov.getRestrictedBankInd())) {
								restBankListForLine.add((OBLineCovenant) cov);
							}
							else if(ICMSConstant.YES.equals(cov.getRestrictedCountryInd())) {
								restCountryListForLine.add((OBLineCovenant) cov);
							}
							else if(ICMSConstant.YES.equals(cov.getRestrictedCurrencyInd())) {
								restCurrencyListForLine.add((OBLineCovenant) cov);
							}
						}
					}
					result.put(SESSION_COUNTRY_LIST_LINE,restCountryListForLine);
					result.put(SESSION_CURRENCY_LIST_LINE,restCurrencyListForLine);
					result.put(SESSION_BANK_RESTRICTION_LIST_LINE,restBankListForLine);
					result.put(SESSION_DRAWER_LIST_LINE,restDrawerListForLine);
					result.put(SESSION_DRAWEE_LIST_LINE,restDraweeListForLine);
					result.put(SESSION_BENE_LIST_LINE,restBeneListForLine);
					result.put(SESSION_COVENANT_GOODS_RESTRICTION_LIST_LINE,restGoodsRestrictionListForLine);
					result.put("lmtTrxObj", lmtTrxObj);
					
				}
				else if (("read".equals(from_event)||"updateStatus".equals(from_event)||"close".equals(from_event)
						||"reopen".equals(from_event) || "prepare_delete".equals(from_event) || "update".equals(from_event)
						)&&!"return_from_line_covenant".equals(fromEventForLineCov)) {
					if(covMain!=null && covMain.length!=0) {
						for(ILineCovenant cov : covMain) {
							if(ICMSConstant.YES.equals(cov.getSingleCovenantInd())) {
								curLmt = cov;
							}
							else if(ICMSConstant.YES.equals(cov.getBeneInd())) {
								restBeneListForLine.add((OBLineCovenant) cov);
							}
							else if(ICMSConstant.YES.equals(cov.getDraweeInd())) {
								restDraweeListForLine.add((OBLineCovenant) cov);
							}
							else if(ICMSConstant.YES.equals(cov.getDrawerInd())) {
								restDrawerListForLine.add((OBLineCovenant) cov);
							}
							else if(ICMSConstant.YES.equals(cov.getGoodsRestrictionInd())) {
								restGoodsRestrictionListForLine.add((OBLineCovenant) cov);
							}
							else if(ICMSConstant.YES.equals(cov.getRestrictedBankInd())) {
								restBankListForLine.add((OBLineCovenant) cov);
							}
							else if(ICMSConstant.YES.equals(cov.getRestrictedCountryInd())) {
								restCountryListForLine.add((OBLineCovenant) cov);
							}
							else if(ICMSConstant.YES.equals(cov.getRestrictedCurrencyInd())) {
								restCurrencyListForLine.add((OBLineCovenant) cov);
							}
						}
					}
					result.put(SESSION_COUNTRY_LIST_LINE,restCountryListForLine);
					result.put(SESSION_CURRENCY_LIST_LINE,restCurrencyListForLine);
					result.put(SESSION_BANK_RESTRICTION_LIST_LINE,restBankListForLine);
					result.put(SESSION_DRAWER_LIST_LINE,restDrawerListForLine);
					result.put(SESSION_DRAWEE_LIST_LINE,restDraweeListForLine);
					result.put(SESSION_BENE_LIST_LINE,restBeneListForLine);
					result.put(SESSION_COVENANT_GOODS_RESTRICTION_LIST_LINE,restGoodsRestrictionListForLine);
					result.put("lmtTrxObj", lmtTrxObj);
				}	
				else if("return_from_line_covenant".equals(fromEventForLineCov)) {
					
					if("add_country_rest".equals(event) || "add_currency_rest".equals(event)
							|| "add_bank_rest".equals(event) || "add_drawer_rest".equals(event)
							|| "add_drawee_rest".equals(event) || "add_bene_rest".equals(event)
							|| "remove_country_rest".equals(event) || "remove_currency_rest".equals(event)
							|| "remove_bank_rest".equals(event) || "remove_drawer_rest".equals(event)
							|| "remove_drawee_rest".equals(event) || "remove_bene_rest".equals(event)
							|| "refresh_goods_master".equals(event) || "add_goods_restriction".equals(event)
							|| "delete_goods_restriction".equals(event)
							) {
						curLmt=lineCov;
					}else {
						ILineCovenant lineCovenantObj = (ILineCovenant) map.get("lineCovenantObj");
						curLmt = lineCovenantObj;
					}
					
					result.put(SESSION_COUNTRY_LIST_LINE,restCountryListForLine);
					result.put(SESSION_CURRENCY_LIST_LINE,restCurrencyListForLine);
					result.put(SESSION_BANK_RESTRICTION_LIST_LINE,restBankListForLine);
					result.put(SESSION_DRAWER_LIST_LINE,restDrawerListForLine);
					result.put(SESSION_DRAWEE_LIST_LINE,restDraweeListForLine);
					result.put(SESSION_BENE_LIST_LINE,restBeneListForLine);
					result.put(SESSION_COVENANT_GOODS_RESTRICTION_LIST_LINE,restGoodsRestrictionListForLine);
					result.put("lmtTrxObj", lmtTrxObj);
				}
			
				if("prepare_edit_bene_rest".equals(event)){
					ILineCovenant newLmt = new OBLineCovenant();
					AccessorUtil.copyValue(lineCov, newLmt);
					
					String beneficiaryName = (String)map.get("beneficiaryName");
					String beneficiaryAmount = (String)map.get("beneficiaryAmount");
					String beneficiaryCustId = (String)map.get("beneficiaryCustId");
					String beneficiaryCustName = (String)map.get("beneficiaryCustName");
					newLmt.setBeneInd(ICMSConstant.YES);
					newLmt.setBeneName(beneficiaryName);
					newLmt.setBeneCustId(beneficiaryCustId);
					newLmt.setBeneAmount(beneficiaryAmount);
					newLmt.setBeneCustName(beneficiaryCustName);
					result.put(SESSION_COUNTRY_LIST_LINE,restCountryListForLine);
					result.put(SESSION_CURRENCY_LIST_LINE,restCurrencyListForLine);
					result.put(SESSION_BANK_RESTRICTION_LIST_LINE,restBankListForLine);
					result.put(SESSION_DRAWER_LIST_LINE,restDrawerListForLine);
					result.put(SESSION_DRAWEE_LIST_LINE,restDraweeListForLine);
					result.put(SESSION_BENE_LIST_LINE,restBeneListForLine);
					result.put(SESSION_COVENANT_GOODS_RESTRICTION_LIST_LINE,restGoodsRestrictionListForLine);
					result.put("lmtTrxObj", lmtTrxObj);
					result.put("eventForEdit", map.get("event"));

					curLmt = newLmt;
				}else if("prepare_edit_drawer_rest".equals(event)){
					ILineCovenant newLmt = new OBLineCovenant();
					AccessorUtil.copyValue(lineCov, newLmt);
					
					String drawerName = (String)map.get("drawerName");
					String drawerAmount = (String)map.get("drawerAmount");
					String drawerCustId = (String)map.get("drawerCustId");
					String drawerCustName = (String)map.get("drawerCustName");
					newLmt.setDrawerInd(ICMSConstant.YES);
					newLmt.setDrawerName(drawerName);
					newLmt.setDrawerCustId(drawerCustId);
					newLmt.setDrawerAmount(drawerAmount);
					newLmt.setDrawerCustName(drawerCustName);
					result.put(SESSION_COUNTRY_LIST_LINE,restCountryListForLine);
					result.put(SESSION_CURRENCY_LIST_LINE,restCurrencyListForLine);
					result.put(SESSION_BANK_RESTRICTION_LIST_LINE,restBankListForLine);
					result.put(SESSION_DRAWER_LIST_LINE,restDrawerListForLine);
					result.put(SESSION_DRAWEE_LIST_LINE,restDraweeListForLine);
					result.put(SESSION_BENE_LIST_LINE,restBeneListForLine);
					result.put(SESSION_COVENANT_GOODS_RESTRICTION_LIST_LINE,restGoodsRestrictionListForLine);
					result.put("lmtTrxObj", lmtTrxObj);
					result.put("eventForEdit", map.get("event"));

					curLmt = newLmt;
				}else if("prepare_edit_drawee_rest".equals(event)){
					ILineCovenant newLmt = new OBLineCovenant();
					AccessorUtil.copyValue(lineCov, newLmt);
					
					String draweeName = (String)map.get("draweeName");
					String draweeAmount = (String)map.get("draweeAmount");
					String draweeCustId = (String)map.get("draweeCustId");
					String draweeCustName = (String)map.get("draweeCustName");
					newLmt.setDraweeInd(ICMSConstant.YES);
					newLmt.setDraweeName(draweeName);
					newLmt.setDraweeCustId(draweeCustId);
					newLmt.setDraweeAmount(draweeAmount);
					newLmt.setDraweeCustName(draweeCustName);
					result.put(SESSION_COUNTRY_LIST_LINE,restCountryListForLine);
					result.put(SESSION_CURRENCY_LIST_LINE,restCurrencyListForLine);
					result.put(SESSION_BANK_RESTRICTION_LIST_LINE,restBankListForLine);
					result.put(SESSION_DRAWER_LIST_LINE,restDrawerListForLine);
					result.put(SESSION_DRAWEE_LIST_LINE,restDraweeListForLine);
					result.put(SESSION_BENE_LIST_LINE,restBeneListForLine);
					result.put(SESSION_COVENANT_GOODS_RESTRICTION_LIST_LINE,restGoodsRestrictionListForLine);
					result.put("eventForEdit", map.get("event"));
					result.put("lmtTrxObj", lmtTrxObj);
					curLmt = newLmt;
				}
			
			DefaultLogger.debug(this, "in PrepareCovenantDetailCommand.java ==130==>> lmtTrxObj.getReferenceID()"+ lmtTrxObj.getReferenceID());

			if(ILmtCovenantConstants.EVENT_PREPARE_EDIT_COVENANT_DETAIL.equals(event)) {
				covenantMap.put("facCat", (String)map.get("facCat"));
				covenantMap.put("fundedAmount", (String)map.get("fundedAmount"));
				covenantMap.put("nonFundedAmount", (String)map.get("nonFundedAmount"));
				covenantMap.put("memoExposer", (String)map.get("memoExposer"));
				covenantMap.put("sanctionedLimit", (String)map.get("sanctionedLimit"));
				covenantMap.put("isCreate", (String)map.get("isCreate"));
				result.put("covenantMap", covenantMap);
			}
			
			result.put("lmtTrxObj", lmtTrxObj);
			result.put("limitProfileID",map.get("limitProfileID"));
			result.put("customerID",map.get("customerID"));
			result.put("limitId", lmtID); //Shiv
			result.put("lmtId", lmtID); //Shiv
			result.put("limitRef", limitRef);
			result.put("lineDetailId", lineDetailId);
			result.put("xReferenceId", xReferenceId);
			result.put(SESSION_DROPDOWN_COUNTRY_LIST, getDropdownList(SESSION_DROPDOWN_COUNTRY_LIST,map));
			result.put(SESSION_DROPDOWN_CURRENCY_LIST, getDropdownList(SESSION_DROPDOWN_CURRENCY_LIST,map));
			result.put(SESSION_DROPDOWN_BANK_RESTRICTION_LIST, getDropdownList(SESSION_DROPDOWN_BANK_RESTRICTION_LIST,map));
			result.put(SESSION_DROPDOWN_GOODS_PARENT_LIST, getDropdownList(SESSION_DROPDOWN_GOODS_PARENT_LIST,map));
			//result.put("goodsList", getGoodsList(lmtDao.getGoodsList()));
			result.put(COVENANT_LINE_DETAIL_FORM, curLmt);
			result.put("sessionCriteria",map.get("sessionCriteria"));
			result.put("event", event);
			result.put("fromEvent", from_event);
			result.put("fromEventForLineCov", fromEventForLineCov);
			result.put("index",map.get("index"));
			result.put("indexID", index);
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
	
	
	private List getEmptyOrDefault(HashMap map, String sessionListForLine){
		return map.get(sessionListForLine) != null ? (List) map.get(sessionListForLine) : new ArrayList();
	}
	
	private List getDropdownList(String dropdownListName, HashMap map) throws LimitException, RemoteException {
		List returnList = Collections.emptyList();
		
		if(SESSION_DROPDOWN_COUNTRY_LIST.equals(dropdownListName)) {
			returnList = map.get(SESSION_DROPDOWN_COUNTRY_LIST) != null? (List) map.get(SESSION_DROPDOWN_COUNTRY_LIST): getCountryList(LimitDAOFactory.getDAO().getCountryList()); 
		}
		else if(SESSION_DROPDOWN_CURRENCY_LIST.equals(dropdownListName)) {
			returnList = map.get(SESSION_DROPDOWN_CURRENCY_LIST) != null? (List) map.get(SESSION_DROPDOWN_CURRENCY_LIST): getCurrList(LimitDAOFactory.getDAO().getCurrList()); 
		}
		else if(SESSION_DROPDOWN_BANK_RESTRICTION_LIST.equals(dropdownListName)) {
			returnList = map.get(SESSION_DROPDOWN_BANK_RESTRICTION_LIST) != null? (List) map.get(SESSION_DROPDOWN_BANK_RESTRICTION_LIST): getBankList(LimitDAOFactory.getDAO().getBankList()); 
		}
		else if(SESSION_DROPDOWN_GOODS_PARENT_LIST.equals(dropdownListName)) {
			if(map.get(SESSION_DROPDOWN_GOODS_PARENT_LIST) != null) {
				returnList = (List) map.get(SESSION_DROPDOWN_GOODS_PARENT_LIST);
			}
			else {
				IGoodsMasterJdbc goodsMasterJdbc = (IGoodsMasterJdbc)BeanHouse.get("goodsMasterJdbc");
				List<String> parentGoodsCodeList = goodsMasterJdbc.getParentGoodsCodeList();
				returnList = getGoodsParentList(parentGoodsCodeList);
			}
		}
		return returnList;
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
	
	private ILineCovenant[] getLimitCovenantDetails(ILimitTrxValue lmtTrxObj,String tableType) {
		ILimitCovenant[] lmtCovAct = null;
		ILineCovenant[] lineCovStg=new ILineCovenant[0];
		
		if(tableType.equalsIgnoreCase("mainTable") && lmtTrxObj!=null && lmtTrxObj.getLimit()!=null
				&& lmtTrxObj.getLimit().getLimitCovenant() != null && lmtTrxObj.getLimit().getLimitCovenant().length>0) {
			Map covMap = CovenantMappingHelper.compareLmtCovenant(null, lmtTrxObj.getLimit().getLimitCovenant(), false);
			lineCovStg=CovenantMappingHelper.copyLimitCovenantToLineCovenant(covMap, lineCovStg);
			
		}else if(tableType.equalsIgnoreCase("stgTable") && lmtTrxObj!=null && lmtTrxObj.getStagingLimit()!=null
				&& lmtTrxObj.getStagingLimit().getLimitCovenant() != null && lmtTrxObj.getStagingLimit().getLimitCovenant().length>0){
			Map covMap = CovenantMappingHelper.compareLmtCovenant(null, lmtTrxObj.getStagingLimit().getLimitCovenant(), false);
			lineCovStg = CovenantMappingHelper.copyLimitCovenantToLineCovenant(covMap, lineCovStg);
		}
		return lineCovStg;
	}
	
	private ILimitSysXRef getStageLimitCovDetailsToUpdateLineCov(ILimitTrxValue lmtTrxObj, ILimitSysXRef xRefForLineCov) {
		CovenantMappingHelper covHelper= new CovenantMappingHelper();
		ILimit lmtAct = lmtTrxObj.getLimit();
		ILimit stgLmt = lmtTrxObj.getStagingLimit();
		
		if (lmtAct != null && stgLmt != null) {
			ILimitCovenant[] lmtCovStg = null;
			ILimitCovenant[] lmtCovAct = null;
			
			lmtCovAct = lmtAct.getLimitCovenant();
			lmtCovStg = stgLmt.getLimitCovenant();
			
			if(lmtCovStg!=null && !ArrayUtils.isEmpty(lmtCovStg)) {
				
				if (lmtCovAct != null && !ArrayUtils.isEmpty(lmtCovAct)) {
					// compare actual limit covenant with stage limit covenant
					Map covMap = covHelper.compareLmtCovenant(lmtCovAct, lmtCovStg, true);
					xRefForLineCov=covHelper.setLimitCovenantDataForSpecificLine(covMap, xRefForLineCov);

				} else {
					// copy all the stage limit covenants to line covenants as actual limit covenants are null
					Map covMap = covHelper.compareLmtCovenant(lmtCovAct, lmtCovStg, false);
					xRefForLineCov=covHelper.setLimitCovenantDataForSpecificLine(covMap, xRefForLineCov);
					
				}
			}
		}
		return xRefForLineCov;
	}
}
