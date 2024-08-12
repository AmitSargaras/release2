/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/collateral/ProcessCollateralCommand.java,v 1.5 2005/10/05 04:07:16 hshii Exp $
 */

package com.integrosys.cms.ui.collateral;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.util.LabelValueBean;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.checklist.bus.CheckListDAOFactory;
import com.integrosys.cms.app.checklist.bus.ICheckListDAO;
import com.integrosys.cms.app.collateral.bus.CollateralDAOFactory;
import com.integrosys.cms.app.collateral.bus.CollateralException;
import com.integrosys.cms.app.collateral.bus.ICollateral;
import com.integrosys.cms.app.collateral.bus.type.asset.subtype.gcharge.OBGeneralCharge;
import com.integrosys.cms.app.collateral.bus.type.asset.subtype.schargeaircraft.OBSpecificChargeAircraft;
import com.integrosys.cms.app.collateral.bus.type.asset.subtype.schargeplant.OBSpecificChargePlant;
import com.integrosys.cms.app.collateral.bus.type.guarantee.IGuaranteeCollateral;
import com.integrosys.cms.app.collateral.bus.type.guarantee.linedetail.ILineDetail;
import com.integrosys.cms.app.collateral.bus.type.guarantee.subtype.banksameccy.IBankSameCurrency;
import com.integrosys.cms.app.collateral.bus.type.guarantee.subtype.banksameccy.OBBankSameCurrency;
import com.integrosys.cms.app.collateral.bus.type.guarantee.subtype.corpthirdparty.ICorporateThirdParty;
import com.integrosys.cms.app.collateral.bus.type.guarantee.subtype.government.IGovernment;
import com.integrosys.cms.app.collateral.bus.type.guarantee.subtype.personal.IPersonal;
import com.integrosys.cms.app.collateral.bus.type.guarantee.subtype.sblcsameccy.ISBLCSameCurrency;
import com.integrosys.cms.app.collateral.bus.type.insurance.subtype.keymaninsurance.IKeymanInsurance;
import com.integrosys.cms.app.collateral.bus.type.marketable.IMarketableEquity;
import com.integrosys.cms.app.collateral.bus.type.marketable.subtype.bondslocal.IBondsLocal;
import com.integrosys.cms.app.collateral.bus.type.marketable.subtype.mainindexlocal.IMainIndexLocal;
import com.integrosys.cms.app.collateral.bus.type.marketable.subtype.otherlistedlocal.IOtherListedLocal;
import com.integrosys.cms.app.collateral.bus.type.property.IPropertyCollateral;
import com.integrosys.cms.app.collateral.bus.type.property.subtype.comgeneral.OBCommercialGeneral;
import com.integrosys.cms.app.collateral.proxy.CollateralProxyFactory;
import com.integrosys.cms.app.collateral.trx.ICollateralTrxValue;
import com.integrosys.cms.app.collateral.trx.OBCollateralTrxValue;
import com.integrosys.cms.app.collateralNewMaster.bus.CollateralNewMasterDAOFactory;
import com.integrosys.cms.app.collateralNewMaster.bus.ICollateralNewMasterDao;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.customer.bus.CustomerDAOFactory;
import com.integrosys.cms.app.customer.bus.ICustomerDAO;
import com.integrosys.cms.app.feed.bus.bond.IBondFeedEntry;
import com.integrosys.cms.app.feed.bus.forex.ForexFeedGroupException;
import com.integrosys.cms.app.feed.bus.forex.IForexFeedEntry;
import com.integrosys.cms.app.feed.bus.mutualfunds.IMutualFundsFeedEntry;
import com.integrosys.cms.app.feed.bus.stock.IStockFeedEntry;
import com.integrosys.cms.app.feed.proxy.bond.IBondFeedProxy;
import com.integrosys.cms.app.feed.proxy.forex.IForexFeedProxy;
import com.integrosys.cms.app.feed.proxy.mutualfunds.IMutualFundsFeedProxy;
import com.integrosys.cms.app.feed.proxy.stock.IStockFeedProxy;
import com.integrosys.cms.app.geography.country.bus.ICountry;
import com.integrosys.cms.app.insurancecoverage.proxy.IInsuranceCoverageProxyManager;
import com.integrosys.cms.app.otherbank.proxy.IOtherBankProxyManager;
import com.integrosys.cms.app.systemBank.bus.ISystemBank;
import com.integrosys.cms.app.systemBank.proxy.ISystemBankProxyManager;
import com.integrosys.cms.app.systemBankBranch.bus.ISystemBankBranch;
import com.integrosys.cms.app.transaction.OBTrxContext;
import com.integrosys.cms.app.valuationAgency.proxy.IValuationAgencyProxyManager;
import com.integrosys.cms.host.stp.common.IStpErrorMessageFetcher;
import com.integrosys.cms.ui.collateral.guarantees.linedetail.ILineDetailConstants;
import com.integrosys.cms.ui.common.UIUtil;
import com.integrosys.cms.ui.insurancecoverage.IInsuranceCoverage;
import com.integrosys.cms.ui.manualinput.CommonUtil;
import com.integrosys.cms.ui.manualinput.security.MISecurityUIHelper;
import com.integrosys.cms.ui.otherbankbranch.IOtherBank;

/**

 * @author $Author: hshii $<br>
 * @version $Revision: 1.5 $
 * @since $Date: 2005/10/05 04:07:16 $
 * Tag: $Name:  $
 */

/**
 * Created by IntelliJ IDEA. User: ssathish Date: Jul 2, 2003 Time: 12:13:00 PM
 * To change this template use Options | File Templates.
 */
public class ProcessCollateralCommand extends AbstractCommand {
	private IForexFeedProxy forexFeedProxy;
	
	private IBondFeedProxy bondFeedProxy = (IBondFeedProxy)BeanHouse.get("bondFeedProxy");
	
	private IStockFeedProxy stockFeedProxyNew = (IStockFeedProxy)BeanHouse.get("stockFeedProxy");
	
	private IMutualFundsFeedProxy mutualFundsFeedProxy = (IMutualFundsFeedProxy)BeanHouse.get("mutualFundsFeedProxy");
	
	private IStockFeedProxy stockFeedProxy;
	
	public IStockFeedProxy getStockFeedProxy() {
		return (IStockFeedProxy)BeanHouse.get("stockFeedProxy");
	}
	
	public void setStockFeedProxy(IStockFeedProxy stockFeedProxy) {
		this.stockFeedProxy = stockFeedProxy;
	}
	
	private IOtherBankProxyManager otherBankProxyManager = (IOtherBankProxyManager)BeanHouse.get("otherBankProxyManager");

	public String[][] getParameterDescriptor() {
		return (new String[][] { { "trxID", "java.lang.String", REQUEST_SCOPE },
				{ "theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE },
				{ "event", "java.lang.String", REQUEST_SCOPE },
				{ "exchangeRate", "java.lang.String", SERVICE_SCOPE }});
	}

	/**
	 * Defines an two dimensional array with the result list to be expected as a
	 * result from the doExecute method using a HashMap syntax for the array is
	 * (HashMapkey,classDocComfort_process.jspname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getResultDescriptor() {
		return (new String[][] {
				{ "serviceColObj", "com.integrosys.cms.app.collateral.trx.ICollateralTrxValue", SERVICE_SCOPE },
				{ "form.collateralObject", "com.integrosys.cms.app.collateral.bus.ICollateral", FORM_SCOPE },
                { "errorMsg", "java.lang.String", REQUEST_SCOPE },
                { "exchangeRate", "java.lang.String", SERVICE_SCOPE },  
                { "cityName", "java.lang.String", SERVICE_SCOPE },
	            { "stateName", "java.lang.String", SERVICE_SCOPE },
	            { "regionName", "java.lang.String", SERVICE_SCOPE },
	            { "isEntryFmToDo", "java.lang.String", SERVICE_SCOPE },
	            { "countryName", "java.lang.String", SERVICE_SCOPE },
	            { "collateralList", "java.util.List", REQUEST_SCOPE },
	            { "systemBankBranch", "com.integrosys.cms.app.systemBankBranch.bus.ISystemBankBranch", REQUEST_SCOPE },
	            { "form.collateralObject", "com.integrosys.cms.app.collateral.bus.ICollateral", SERVICE_SCOPE },
	            { "countryNme", "java.lang.String", REQUEST_SCOPE },
	            { "valuationAgencyName", "java.lang.String", REQUEST_SCOPE },
	            { "InsurerName", "java.lang.String", REQUEST_SCOPE },
	            { "bankListMap", "java.util.HashMap", SERVICE_SCOPE },
	            { "insuranceCoverageMap","java.util.HashMap",SERVICE_SCOPE},
	            { "orgMap","java.util.HashMap",SERVICE_SCOPE},
	            { "orgList", "java.util.List", SERVICE_SCOPE },	            
	            { "BondList", "java.util.List", SERVICE_SCOPE },
	            { "StockList", "java.util.List", SERVICE_SCOPE },
	            { "trxID", "java.lang.String", REQUEST_SCOPE },
	            { "FundList", "java.util.List", SERVICE_SCOPE },
	            { "transactionHistoryList", "java.util.List", SERVICE_SCOPE },
	            { "currencyList", "java.util.List", SERVICE_SCOPE },
	            { "insuranceCheck", "java.lang.String", SERVICE_SCOPE },
	            { "checklistIsActive", "java.lang.Boolean", SERVICE_SCOPE },
	            { "thirdPartyState", "java.lang.String", SERVICE_SCOPE },
	            { "thirdPartyCity", "java.lang.String", SERVICE_SCOPE },
	            { "collateralCategory", "java.lang.String", SERVICE_SCOPE },
	            { "cersaiApplicableInd", "java.lang.String", SERVICE_SCOPE },
	            { "mandatoryThirdPartyEntitiesStr", "java.lang.String", SERVICE_SCOPE },

	            { "valuationAgencyName_v1", "java.lang.String", REQUEST_SCOPE },
	            { "cityName_v1", "java.lang.String", SERVICE_SCOPE },
	          	{ "stateName_v1", "java.lang.String", SERVICE_SCOPE },
	          	{ "regionName_v1", "java.lang.String", SERVICE_SCOPE },
	          	{ "countryName_v1", "java.lang.String", SERVICE_SCOPE },
	          	
	          	{ "valuationAgencyName_v2", "java.lang.String", REQUEST_SCOPE },
	            { "cityName_v2", "java.lang.String", SERVICE_SCOPE },
	          	{ "stateName_v2", "java.lang.String", SERVICE_SCOPE },
	          	{ "regionName_v2", "java.lang.String", SERVICE_SCOPE },
	          	{ "countryName_v2", "java.lang.String", SERVICE_SCOPE },
	          	
	          	{ "valuationAgencyName_v3", "java.lang.String", REQUEST_SCOPE },
	            { "cityName_v3", "java.lang.String", SERVICE_SCOPE },
	          	{ "stateName_v3", "java.lang.String", SERVICE_SCOPE },
	          	{ "regionName_v3", "java.lang.String", SERVICE_SCOPE },
	          	{ "countryName_v3", "java.lang.String", SERVICE_SCOPE },
	            {ILineDetailConstants.SESSION_LINE_DETAIL_LIST, List.class.getName(), SERVICE_SCOPE},
	            { "otherChecklistCountPartyName", "java.lang.String", SERVICE_SCOPE },
		});
	}

	/**
	 * This method does the Business operations with the HashMap and put the
	 * results back into the HashMap.Here reading for Company Borrower is done.
	 * 
	 * @param map is of type HashMap
	 * @throws com.integrosys.base.uiinfra.exception.CommandProcessingException
	 *         on errors
	 * @throws com.integrosys.base.uiinfra.exception.CommandValidationException
	 *         on errors
	 * @return HashMap with the Result
	 */
	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {

		HashMap result = new HashMap();
		HashMap exceptionMap = new HashMap();
		HashMap temp = new HashMap();
		BigDecimal exchangeRate = null;
		List collateralList = new ArrayList();
		 ISystemBankBranch systemBankBranch = null;
		String countryNme = "";
		String valuationAgencyName = "";
		
		String valuationAgencyName_v1 = "";
		String valuationAgencyName_v2 = "";
		String valuationAgencyName_v3 = "";
		
		IValuationAgencyProxyManager valuationProxy = (IValuationAgencyProxyManager)BeanHouse.get("valuationAgencyProxy");

		String idStr = (String) map.get("trxID");
		DefaultLogger.debug(this, "============= collateral id ============> " + idStr);

		OBTrxContext ctx = (OBTrxContext) map.get("theOBTrxContext");
		ICollateralTrxValue itrxValue = new OBCollateralTrxValue();
		String event = (String) map.get("event");
		try {
			itrxValue = CollateralProxyFactory.getProxy().getCollateralTrxValue(ctx, idStr);
			
			String cityName=null;
			String stateName=null;
			String regionName=null;
			String countryName=null;
			String insurerNamevl=null;
			
			String cityName_v1=null;
			String stateName_v1=null;
			String regionName_v1=null;
			String countryName_v1=null;
			
			String cityName_v2=null;
			String stateName_v2=null;
			String regionName_v2=null;
			String countryName_v2=null;
			
			String cityName_v3=null;
			String stateName_v3=null;
			String regionName_v3=null;
			String countryName_v3=null;
			
			String thirdPartyCity = null;
			String thirdPartyState = null;
			
			IBankSameCurrency iCol1 = new OBBankSameCurrency();
			
			if(itrxValue.getCollateral() instanceof  IBankSameCurrency){
				iCol1 = (IBankSameCurrency) itrxValue.getStagingCollateral();
				
				if(iCol1.getCity()!=null && !iCol1.getCity().equals(""))
		    		cityName = otherBankProxyManager.getCityName(iCol1.getCity());
		    	
		    	if(iCol1.getState()!=null && !iCol1.getState().equals(""))
		    		stateName = otherBankProxyManager.getStateName(iCol1.getState());
		    	
		    	if(iCol1.getRegion()!=null && !iCol1.getRegion().equals(""))
		    		regionName = otherBankProxyManager.getRegionName(iCol1.getRegion());
		    	
		    	if(iCol1.getCountry()!=null && !iCol1.getCountry().equals(""))
		    		countryName = otherBankProxyManager.getCountryName(iCol1.getCountry());
		    	
			}
			else if(itrxValue.getCollateral() instanceof  ICorporateThirdParty){
				ICorporateThirdParty iCol2 = (ICorporateThirdParty) itrxValue.getStagingCollateral();
				
				if(iCol2.getCity()!=null && !iCol2.getCity().equals(""))
		    		cityName = otherBankProxyManager.getCityName(iCol2.getCity());
		    	
		    	if(iCol2.getState()!=null && !iCol2.getState().equals(""))
		    		stateName = otherBankProxyManager.getStateName(iCol2.getState());
		    	
		    	if(iCol2.getRegion()!=null && !iCol2.getRegion().equals(""))
		    		regionName = otherBankProxyManager.getRegionName(iCol2.getRegion());
		    	
		    	if(iCol2.getCountry()!=null && !iCol2.getCountry().equals(""))
		    		countryName = otherBankProxyManager.getCountryName(iCol2.getCountry());
		    	
			}
			else if(itrxValue.getCollateral() instanceof  IGovernment){
				IGovernment iCol3 = (IGovernment) itrxValue.getStagingCollateral();
				
				if(iCol3.getCity()!=null && !iCol3.getCity().equals(""))
		    		cityName = otherBankProxyManager.getCityName(iCol3.getCity());
		    	
		    	if(iCol3.getState()!=null && !iCol3.getState().equals(""))
		    		stateName = otherBankProxyManager.getStateName(iCol3.getState());
		    	
		    	if(iCol3.getRegion()!=null && !iCol3.getRegion().equals(""))
		    		regionName = otherBankProxyManager.getRegionName(iCol3.getRegion());
		    	
		    	if(iCol3.getCountry()!=null && !iCol3.getCountry().equals(""))
		    		countryName = otherBankProxyManager.getCountryName(iCol3.getCountry());
		    	
			}
			else if(itrxValue.getCollateral() instanceof  ISBLCSameCurrency){
				ISBLCSameCurrency iCol4 = (ISBLCSameCurrency) itrxValue.getStagingCollateral();
				
				if(iCol4.getCity()!=null && !iCol4.getCity().equals(""))
		    		cityName = otherBankProxyManager.getCityName(iCol4.getCity());
		    	
		    	if(iCol4.getState()!=null && !iCol4.getState().equals(""))
		    		stateName = otherBankProxyManager.getStateName(iCol4.getState());
		    	
		    	if(iCol4.getRegion()!=null && !iCol4.getRegion().equals(""))
		    		regionName = otherBankProxyManager.getRegionName(iCol4.getRegion());
		    	
		    	if(iCol4.getCountry()!=null && !iCol4.getCountry().equals(""))
		    		countryName = otherBankProxyManager.getCountryName(iCol4.getCountry());
		    	
			}
			
			else if(itrxValue.getCollateral() instanceof  IPersonal){
				IPersonal iCol5 = (IPersonal) itrxValue.getStagingCollateral();
				
				if(iCol5.getCity()!=null && !iCol5.getCity().equals(""))
		    		cityName = otherBankProxyManager.getCityName(iCol5.getCity());
		    	
		    	if(iCol5.getState()!=null && !iCol5.getState().equals(""))
		    		stateName = otherBankProxyManager.getStateName(iCol5.getState());
		    	
		    	if(iCol5.getRegion()!=null && !iCol5.getRegion().equals(""))
		    		regionName = otherBankProxyManager.getRegionName(iCol5.getRegion());
		    	
		    	if(iCol5.getCountry()!=null && !iCol5.getCountry().equals(""))
		    		countryName = otherBankProxyManager.getCountryName(iCol5.getCountry());
			}
			else if(itrxValue.getCollateral() instanceof  IPropertyCollateral){
				IPropertyCollateral iCol5 = (IPropertyCollateral) itrxValue.getStagingCollateral();
			
		    	if(iCol5.getNearestCity_v1()!=null && !iCol5.getNearestCity_v1().equals(""))
		    		cityName_v1 = otherBankProxyManager.getCityName(iCol5.getNearestCity_v1());
		    	
		    	if(iCol5.getLocationState_v1()!=null && !iCol5.getLocationState_v1().equals(""))
		    		stateName_v1 = otherBankProxyManager.getStateName(iCol5.getLocationState_v1());
		    	
		    	if(iCol5.getRegion_v1()!=null && !iCol5.getRegion_v1().equals(""))
		    		regionName_v1 = otherBankProxyManager.getRegionName(iCol5.getRegion_v1());
		    	
		    	if(iCol5.getCountry_v1()!=null && !iCol5.getCountry_v1().equals(""))
		    		countryName_v1 = otherBankProxyManager.getCountryName(iCol5.getCountry_v1());
		    	
		    	valuationAgencyName_v1 = valuationProxy.getValuationAgencyName(iCol5.getValuatorCompany_v1());
		    	
		    	if(iCol5.getNearestCity_v2()!=null && !iCol5.getNearestCity_v2().equals(""))
		    		cityName_v2 = otherBankProxyManager.getCityName(iCol5.getNearestCity_v2());
		    	
		    	if(iCol5.getLocationState_v2()!=null && !iCol5.getLocationState_v2().equals(""))
		    		stateName_v2 = otherBankProxyManager.getStateName(iCol5.getLocationState_v2());
		    	
		    	if(iCol5.getRegion_v2()!=null && !iCol5.getRegion_v2().equals(""))
		    		regionName_v2 = otherBankProxyManager.getRegionName(iCol5.getRegion_v2());
		    	
		    	if(iCol5.getCountry_v2()!=null && !iCol5.getCountry_v2().equals(""))
		    		countryName_v2 = otherBankProxyManager.getCountryName(iCol5.getCountry_v2());
		    	
		    	valuationAgencyName_v2 = valuationProxy.getValuationAgencyName(iCol5.getValuatorCompany_v2());
		    	
		    	if(iCol5.getNearestCity_v3()!=null && !iCol5.getNearestCity_v3().equals(""))
		    		cityName_v3 = otherBankProxyManager.getCityName(iCol5.getNearestCity_v3());
		    	
		    	if(iCol5.getLocationState_v3()!=null && !iCol5.getLocationState_v3().equals(""))
		    		stateName_v3 = otherBankProxyManager.getStateName(iCol5.getLocationState_v3());
		    	
		    	if(iCol5.getRegion_v3()!=null && !iCol5.getRegion_v3().equals(""))
		    		regionName_v3 = otherBankProxyManager.getRegionName(iCol5.getRegion_v3());
		    	
		    	if(iCol5.getCountry_v3()!=null && !iCol5.getCountry_v3().equals(""))
		    		countryName_v3 = otherBankProxyManager.getCountryName(iCol5.getCountry_v3());
		    	
		    	valuationAgencyName_v3 = valuationProxy.getValuationAgencyName(iCol5.getValuatorCompany_v3());
		    	
		    	result.put("bankListMap", getBankListHashMap());
			
			}
			else if(itrxValue.getCollateral() instanceof  IKeymanInsurance){
				IKeymanInsurance iCol6 = (IKeymanInsurance) itrxValue.getStagingCollateral();
				List lbValList = new ArrayList();
				if(iCol6.getInsurerName()!=null && !"".equals(iCol6.getInsurerName().trim())){
					try {
					 SearchResult searchResult = otherBankProxyManager.getInsurerNameFromCode(iCol6.getInsurerName());
					 List insurerNamValue = (List) searchResult.getResultList();
					 
					 for (int i = 0; i < insurerNamValue.size(); i++) {
						 IInsuranceCoverage insurernam = (IInsuranceCoverage) insurerNamValue.get(i);
							String id = insurernam.getCompanyName();
							String val =null;
							LabelValueBean lvBean = new LabelValueBean(val, id);
							lbValList.add(lvBean);
							insurerNamevl=id;
						}
					
				} catch (Exception ex) {
				}
					result.put("InsurerName",insurerNamevl);
					
				}
			}
			if(itrxValue.getCollateral() instanceof  IBondsLocal)
			{
			//IBondsLocal iCol = (IBondsLocal) itrxValue.getCollateral();		
			
			IBondsLocal iCol;
			IBondsLocal iColTemp;
			if ( event.equals("process")) {
				
				 iCol = (IBondsLocal) itrxValue.getStagingCollateral();		

			}
			else{
			 iCol = (IBondsLocal) itrxValue.getCollateral();				
			}
			//In case stock code not available. This fix was done to handle delete record as checker
			iColTemp = (IBondsLocal) itrxValue.getCollateral();
			IBondFeedEntry bondFeedEntry = null;
			IMarketableEquity[] equity = iCol.getEquityList();
			IMarketableEquity[] equityTemp = iColTemp.getEquityList();

			String bondCode = null;
			List BondList = new ArrayList() ;
			for(int i=0;i<equityTemp.length;i++){
				bondCode = equityTemp[i].getStockCode();
				if(bondCode !=null)
				{
					DefaultLogger.debug(this, "=============Inside if as bond  Code is============> " + bondCode);
					bondFeedEntry = bondFeedProxy.getBondFeedEntry(bondCode);
					if(bondFeedEntry != null)
					{
					BondList.add(bondFeedEntry);
					}
				}
				}
			for(int i=0;i<equity.length;i++){
				bondCode = equity[i].getStockCode();					
				
				if(bondCode !=null)
				{
					bondFeedEntry = bondFeedProxy.getBondFeedEntry(bondCode);
					if(bondFeedEntry != null)
					{
					BondList.add(bondFeedEntry);
					}
				}
			}		
							
			if(BondList!= null)
			{
				result.put("BondList", BondList);
			}
		}
			
			if(itrxValue.getCollateral() instanceof  IOtherListedLocal)
			{
				//IOtherListedLocal iCol = (IOtherListedLocal) itrxValue.getCollateral();	
				DefaultLogger.debug(this, "=============Inside instance of Stocks============> ");
				IOtherListedLocal iCol;
				IOtherListedLocal iColtemp;
				if ( event.equals("process")) {
				DefaultLogger.debug(this, "=============Inside process of Stocks============> ");

					 iCol = (IOtherListedLocal) itrxValue.getStagingCollateral();		

				}
				else{
				 iCol = (IOtherListedLocal) itrxValue.getCollateral();				
				}
				//In case stock code not available. This fix was done to handle delete record as checker
				iColtemp = (IOtherListedLocal) itrxValue.getCollateral();		
				
			IStockFeedEntry stockFeedEntry = null;
			IMarketableEquity[] equity = iCol.getEquityList();
			IMarketableEquity[] equityTemp = iColtemp.getEquityList();

			String strStockExchange = null;
			String strScriptCode = null;
			List StockList = new ArrayList() ;
			
			for(int i=0;i<equityTemp.length;i++){
				strScriptCode = equityTemp[i].getIsinCode();
				strStockExchange = equityTemp[i].getStockExchange();
				DefaultLogger.debug(this, "=============TEmp strScriptCode of Stocks============> "+strScriptCode);
				DefaultLogger.debug(this, "=============TEmp strStockExchange of Stocks============> "+strStockExchange);
				if(strStockExchange!=null && strScriptCode!=null)
				{
					stockFeedEntry = stockFeedProxyNew.getStockFeedEntryStockExc(strStockExchange,strScriptCode);
					if(stockFeedEntry != null)
					{
					StockList.add(stockFeedEntry);
					}
				}
			}
			for(int i=0;i<equity.length;i++){
				strScriptCode = equity[i].getIsinCode();
				strStockExchange = equity[i].getStockExchange();
				DefaultLogger.debug(this, "=============strScriptCode of Stocks============> "+strScriptCode);
				DefaultLogger.debug(this, "=============strStockExchange of Stocks============> "+strStockExchange);

				if(strStockExchange!=null && strScriptCode!=null)
				{
					stockFeedEntry = stockFeedProxyNew.getStockFeedEntryStockExc(strStockExchange,strScriptCode);
					if(stockFeedEntry != null)
					{
					StockList.add(stockFeedEntry);
					}
				}
								
			}		
							
			if(StockList!= null)
			{
				result.put("StockList", StockList);
			}
		}
			
			if(itrxValue.getCollateral() instanceof  IMainIndexLocal)
			{
				//IMainIndexLocal iCol = (IMainIndexLocal) itrxValue.getCollateral();	
				IMainIndexLocal iCol;
				IMainIndexLocal iColTemp;
				if ( event.equals("process")) {
					
					 iCol = (IMainIndexLocal) itrxValue.getStagingCollateral();		

				}
				else{
				 iCol = (IMainIndexLocal) itrxValue.getCollateral();				
				}
				//In case stock code not available. This fix was done to handle delete record as checker
				iColTemp = (IMainIndexLocal) itrxValue.getCollateral();
			IMutualFundsFeedEntry mutualFundsFeedEntry = null;
			IMarketableEquity[] equity = iCol.getEquityList();
			IMarketableEquity[] equityTemp = iColTemp.getEquityList();

			String mutualCode = null;
			List FundList = new ArrayList() ;
			for(int i=0;i<equityTemp.length;i++){
				mutualCode = equityTemp[i].getStockCode();					
				
				if(mutualCode !=null)
				{
					mutualFundsFeedEntry = mutualFundsFeedProxy.getIMutualFundsFeed(mutualCode);					
					
					if(mutualFundsFeedEntry != null)
					{
						FundList.add(mutualFundsFeedEntry);
					}
				}
			}		
			for(int i=0;i<equity.length;i++){
				mutualCode = equity[i].getStockCode();					
				
				if(mutualCode !=null)
				{
					mutualFundsFeedEntry = mutualFundsFeedProxy.getIMutualFundsFeed(mutualCode);					
					
					if(mutualFundsFeedEntry != null)
					{
						FundList.add(mutualFundsFeedEntry);
					}
				}
			}		
							
			if(FundList!= null)
			{
				result.put("FundList", FundList);
			}
		}
			
			 //Uma Khot::Insurance Deferral maintainance
			if(itrxValue.getCollateral() instanceof  OBGeneralCharge || itrxValue.getCollateral() instanceof OBSpecificChargeAircraft
					|| itrxValue.getCollateral() instanceof OBSpecificChargePlant){
				boolean checklistIsActive=true;
				String otherChecklistPartyName = "";
				String otherChecklistCountPartyName = "";
				ICheckListDAO dao = CheckListDAOFactory.getCheckListDAO();
				String checklistId = dao.getChecklistIdByCustomerId("O",itrxValue.getCustomerID());
				if(null!=checklistId){
				int otherChecklistCount = dao.getOtherChecklistCount("CHECKLIST",checklistId);
				if(otherChecklistCount>0){
					otherChecklistPartyName = dao.getOtherChecklistCountPartyName("CHECKLIST",checklistId);
					otherChecklistCountPartyName = otherChecklistCountPartyName +","+otherChecklistPartyName ;
					checklistIsActive=false;
				}
				}
				
				result.put("checklistIsActive", checklistIsActive);
				result.put("otherChecklistCountPartyName", otherChecklistCountPartyName);
			}		
				
			if(itrxValue.getCollateral() instanceof OBCommercialGeneral){
				boolean checklistIsActive=true;
				String otherChecklistPartyName = "";
				String otherChecklistCountPartyName = "";
				ICustomerDAO customerDao =  CustomerDAOFactory.getDAO();
				List limitIdList = customerDao.getCollateralMappedCustomerLimitIdList(itrxValue.getCollateral().getCollateralID());
				
				ICheckListDAO dao = CheckListDAOFactory.getCheckListDAO();
				
				for(int i=0;i< limitIdList.size();i++){
					String limitId = (String) limitIdList.get(i);
					String checklistId = dao.getChecklistIdByLimitId("O",Long.parseLong(limitId));
					if(null!=checklistId){
					int otherChecklistCount = dao.getOtherChecklistCount("CHECKLIST",checklistId);
					if(otherChecklistCount>0){
						otherChecklistPartyName = dao.getOtherChecklistCountPartyName("CHECKLIST",checklistId);
						otherChecklistCountPartyName = otherChecklistCountPartyName +","+otherChecklistPartyName ;
						checklistIsActive=false;
//						break;
					}
					}
				}
				
				result.put("checklistIsActive", checklistIsActive);
				result.put("otherChecklistCountPartyName", otherChecklistCountPartyName);
			}
			
			//Uma Khot:start:Added to update wrong data of security 10000 records
//				if(itrxValue.getCurrentTrxHistoryID().equals("20150713002865576"))
		/*		if(itrxValue.getCurrentTrxHistoryID().equals("20150910002679982"))
			//	if(itrxValue.getCurrentTrxHistoryID().equals("20150727000029815"))
				{
					try {
						CustomerDAOFactory.getDAO().updateGcInsurance();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} */
				//Uma Khot:end:Added to update wrong data of security 10000 records
			
			
			String collateralCode = itrxValue.getCollateral().getCollateralCode();
			ICollateralNewMasterDao collateralDao = CollateralNewMasterDAOFactory.getCollateralNewMasterDAO();
			String insuranceCheck = collateralDao.getInsuranceByCode(collateralCode);
			
			//CERSAI CR
			Map collateralMaster = null;
			String collateralCategory = null;	
			String cersaiApplicableInd = null;
			if(null != itrxValue && null != itrxValue.getCollateral()) {
				collateralMaster = CollateralDAOFactory.getDAO().getCollateralCategoryAndCersaiInd(itrxValue.getCollateral().getCollateralID());
				if(null != collateralMaster ) {
					collateralCategory = (String) collateralMaster.get("COLLATERAL_CATEGORY"); 
					cersaiApplicableInd = (String) collateralMaster.get("CERSAI_IND");
				}
			}
			
			List mandatoryThirdPartyEntitiesList = CollateralHelper.getMandatoryEntitiesForCinForThirdParty();
			String mandatoryThirdPartyEntitiesStr = UIUtil.getDelimitedStringFromList(mandatoryThirdPartyEntitiesList, ",");
			
			if(null != itrxValue && null != itrxValue.getStagingCollateral()) {
				if(StringUtils.isNotBlank(itrxValue.getStagingCollateral().getThirdPartyState())) {
					thirdPartyState = otherBankProxyManager.getStateName(itrxValue.getStagingCollateral().getThirdPartyState());
				}
				if(StringUtils.isNotBlank(itrxValue.getStagingCollateral().getThirdPartyCity())) {
					thirdPartyCity = otherBankProxyManager.getCityName(itrxValue.getStagingCollateral().getThirdPartyCity());
				}
			}
	    		
			result.put("valuationAgencyName_v1",valuationAgencyName_v1);
	    	result.put("cityName_v1",cityName_v1);
	    	result.put("stateName_v1",stateName_v1);
	    	result.put("regionName_v1",regionName_v1);
	    	result.put("countryName_v1",countryName_v1);
	    	
			result.put("valuationAgencyName_v2",valuationAgencyName_v2);
	    	result.put("cityName_v2",cityName_v2);
	    	result.put("stateName_v2",stateName_v2);
	    	result.put("regionName_v2",regionName_v2);
	    	result.put("countryName_v2",countryName_v2);
	    	
	    	result.put("valuationAgencyName_v3",valuationAgencyName_v3);
	    	result.put("cityName_v3",cityName_v3);
	    	result.put("stateName_v3",stateName_v3);
	    	result.put("regionName_v3",regionName_v3);
	    	result.put("countryName_v3",countryName_v3);
	    	
			result.put("insuranceCheck", insuranceCheck);
			result.put("valuationAgencyName",valuationAgencyName);
	    	result.put("cityName",cityName);
	    	result.put("stateName",stateName);
	    	result.put("regionName",regionName);
	    	result.put("countryName",countryName);
	    	result.put("thirdPartyState", thirdPartyState);
	    	result.put("thirdPartyCity", thirdPartyCity);
	    	result.put("collateralCategory",collateralCategory);
	    	result.put("cersaiApplicableInd",cersaiApplicableInd);
	    	result.put("mandatoryThirdPartyEntitiesStr",mandatoryThirdPartyEntitiesStr);
	    	
		}
		catch (CollateralException e) {
			exceptionMap.put("collateral.errror", new ActionMessage("collateral.error"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (itrxValue != null) {

			result.put("serviceColObj", itrxValue);
			
			String from_event = (String) map.get("from_event");
            if ((from_event != null) && from_event.equals("read")) {
                result.put("form.collateralObject", itrxValue.getCollateral());
            } else {
            	ICollateral col = itrxValue.getStagingCollateral();
            	if(itrxValue.getCollateral()!= null)
            	{
            //	col.setCurrencyCode(itrxValue.getCollateral().getCurrencyCode());
            	col.setCollateralLocation(itrxValue.getCollateral().getCollateralLocation());
            	//col.setSecurityOrganization(itrxValue.getCollateral().getSecurityOrganization());
            	//col.setSecPriority(itrxValue.getCollateral().getSecPriority());
            	itrxValue.setStagingCollateral(col); 
            	}
                result.put("form.collateralObject", itrxValue.getStagingCollateral());
            }

			//result.put("form.collateralObject", itrxValue.getStagingCollateral());

            String error = null;
            if (ICMSConstant.STATE_PENDING_RETRY.equals(itrxValue.getStatus())) {
                IStpErrorMessageFetcher iStpErrorMessageFetcher = (IStpErrorMessageFetcher) BeanHouse
                        .get("stpErrorMessageFetcher");
                ArrayList aList = (ArrayList) iStpErrorMessageFetcher.getErrorMessage(itrxValue.getTransactionID());
                if (aList != null && aList.size() > 0) {
                    error = (String) aList.get(0);
                }
            }
            result.put("errorMsg", error);
            DefaultLogger.debug(this, "============= error ============> " + error);

            // check duplicated collateral name to enable / disable Approve button
//            long cmsCollateralId = ICMSConstant.LONG_INVALID_VALUE;
//            String isDuplicatedCollateralName = ICMSConstant.FALSE_VALUE;
//            if (itrxValue.getCollateral() != null && itrxValue.getCollateral().getCollateralID() != ICMSConstant.LONG_INVALID_VALUE) {
//                cmsCollateralId = itrxValue.getCollateral().getCollateralID();
//            }
//            if (AbstractCollateralStpValidator.isCollateralNameDuplicated(
//                    itrxValue.getStagingCollateral().getSCIReferenceNote(), cmsCollateralId)) {
//                isDuplicatedCollateralName = ICMSConstant.TRUE_VALUE;
//            }
//            result.put("isDuplicatedColName", isDuplicatedCollateralName);
        }
		try {
			if(itrxValue!=null){
		    exchangeRate = getForexFeedProxy().getExchangeRateWithINR(itrxValue.getCollateral().getCurrencyCode());
		    //Add By Govind S:05/09/2011
		    collateralList = getCollateralCodeList(itrxValue.getStagingCollateral().getCollateralSubType().getSubTypeCode());
		    String countryCode = itrxValue.getStagingCollateral().getCollateralLocation();
		    String branchCode = itrxValue.getStagingCollateral().getSecurityOrganization();
		   
		    systemBankBranch = getSysBankBranchByCuntryAndBranchCode(countryCode, branchCode);
		    
		    countryNme = getCountryNamebyCode(countryCode);
			}
		} catch (ForexFeedGroupException e) {
			DefaultLogger.debug(this, "got exception in doExecute" + e);
			e.printStackTrace();
		} catch (Exception ex) {
			DefaultLogger.debug(this, "got exception in doExecute" + ex);
			ex.printStackTrace();
		}
		if(exchangeRate!= null)
		{
		result.put("exchangeRate", exchangeRate.toString());
		}
		else
		{
			result.put("exchangeRate", exchangeRate);
		}
		
		IInsuranceCoverageProxyManager insuranceCoverageProxyManager = (IInsuranceCoverageProxyManager) BeanHouse.get("insuranceCoverageProxyManager");
		
		SearchResult sr = insuranceCoverageProxyManager.getInsuranceCoverageList(null,null);
		HashMap insuranceCoverageMap = new HashMap();
		ArrayList resultList = (ArrayList)sr.getResultList();
		for (int i = 0; i < resultList.size(); i++) {
			IInsuranceCoverage insuranceCoverage = (IInsuranceCoverage) resultList.get(i);
			String id = Long.toString(insuranceCoverage.getId());
			String val = insuranceCoverage.getCompanyName();
			insuranceCoverageMap.put(id, val);
		}
		result.put("insuranceCoverageMap", insuranceCoverageMap);
		
		HashMap orgMap = new HashMap();
		try {
			MISecurityUIHelper helper = new MISecurityUIHelper();
			ISystemBankBranch[] branch = CollateralDAOFactory.getDAO().getListAllSystemBankBranch(itrxValue.getStagingCollateral().getCollateralLocation());
			
			if (branch != null) {
				for (int i = 0; i < branch.length; i++) {
					ISystemBankBranch lst = branch[i];
					String id = lst.getSystemBankBranchCode();
					String value = lst.getSystemBankBranchName();
					orgMap.put(id,value);
					result.put("orgMap", orgMap);
				}
			}
		}
		catch (Exception ex) {
		}
		
		ICustomerDAO customerDAO = CustomerDAOFactory.getDAO();
	    List transactionHistoryList = customerDAO.getTransactionHistoryList(itrxValue.getTransactionID());
	    
		List<ILineDetail> lineDetailList = null;
		ICollateral col = itrxValue.getStagingCollateral();
		if(col instanceof IGuaranteeCollateral) {
			IGuaranteeCollateral guaranteeCol = (IGuaranteeCollateral) col;
			if(guaranteeCol.getLineDetails() != null && guaranteeCol.getLineDetails().length > 0) {
				lineDetailList = new ArrayList<ILineDetail>(Arrays.asList(guaranteeCol.getLineDetails()));
			}
		}

	    result.put("transactionHistoryList", transactionHistoryList);
	    result.put("theOBTrxContext", map.get("theOBTrxContext"));
		result.put("orgList", getListAllSystemBankBranch(itrxValue.getStagingCollateral().getCollateralLocation()));
		result.put("collateralList", collateralList);
		result.put("systemBankBranch", systemBankBranch);
		result.put("countryNme", countryNme);
		result.put("isEntryFmToDo", "Y");
		result.put("trxID", idStr);
		result.put("currencyList", getCurrencyList());
		result.put(ILineDetailConstants.SESSION_LINE_DETAIL_LIST, lineDetailList);
		temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		return temp;
	}
	
	/**
	 * @return the forexFeedProxy
	 */
	public IForexFeedProxy getForexFeedProxy() {
		return (IForexFeedProxy)BeanHouse.get("forexFeedProxy");
	}

	/**
	 * @param forexFeedProxy the forexFeedProxy to set
	 */
	public void setForexFeedProxy(IForexFeedProxy forexFeedProxy) {
		this.forexFeedProxy = forexFeedProxy;
	}
	
	private List getListAllSystemBankBranch(String country) {
		List lbValList = new ArrayList();
		try {
				MISecurityUIHelper helper = new MISecurityUIHelper();
				ISystemBankBranch[] branch = CollateralDAOFactory.getDAO().getListAllSystemBankBranch(country);
				
				if (branch != null) {
					for (int i = 0; i < branch.length; i++) {
						ISystemBankBranch lst = branch[i];
						String id = lst.getSystemBankBranchCode();
						String value = lst.getSystemBankBranchName();
						LabelValueBean lvBean = new LabelValueBean(value, id);
						lbValList.add(lvBean);
					}
				}
		}
		catch (Exception ex) {
		}
		return CommonUtil.sortDropdown(lbValList);
	}
	
	//Add By Govind S:Get collateral code with desc,05/09/2011
	private List getCollateralCodeList(String subTypeValue) {
		List lbValList = new ArrayList();
		try {
			if (subTypeValue != null) {
				MISecurityUIHelper helper = new MISecurityUIHelper();
				List colCodeLst = helper.getSBMISecProxy().getCollateralCodeBySubTypes(subTypeValue);
				if (colCodeLst != null) {
					
					for (int i = 0; i < colCodeLst.size(); i++) {
						String[] codeLst = (String[]) colCodeLst.get(i);
						String code = codeLst[0];
						String name = codeLst[1];
						LabelValueBean lvBean = new LabelValueBean(UIUtil.replaceSpecialCharForXml(name), UIUtil
								.replaceSpecialCharForXml(code));
						lbValList.add(lvBean);
					}
				}
			}
		}
		catch (Exception ex) {
		}
		return CommonUtil.sortDropdown(lbValList);
	}
	
	private ISystemBankBranch getSysBankBranchByCuntryAndBranchCode(String country , String branchCode) {
		ISystemBankBranch branch = null;
		try {
				MISecurityUIHelper helper = new MISecurityUIHelper();
				 branch = CollateralDAOFactory.getDAO().getSysBankBranchByCuntryAndBranchCode(country , branchCode);
		}
		catch (Exception ex) {
		}
		return branch;
	}
	
	private String getCountryNamebyCode(String countryCode) {
		List lbValList = new ArrayList();
		String value = null;
		try {
				MISecurityUIHelper helper = new MISecurityUIHelper();
				ICountry[] country = CollateralDAOFactory.getDAO().getCountryNamebyCode(countryCode);
				
				
				if (country != null) {
					for (int i = 0; i < country.length; i++) {
						ICountry lst = country[i];
						String id = lst.getCountryCode();
						value = lst.getCountryName();
						LabelValueBean lvBean = new LabelValueBean(value, id);
						
						lbValList.add(lvBean);
					}
				}
		}
		catch (Exception ex) {
		}
		return value;
	}
	
	private HashMap getBankListHashMap() {
		HashMap bankListMap = new HashMap();
		try {
			
			ISystemBankProxyManager systemBankProxy = (ISystemBankProxyManager) BeanHouse.get("systemBankProxy");
			List systemBankList = systemBankProxy.getAllActual();
			
			for (int i = 0; i < systemBankList.size(); i++) {
				ISystemBank systemBank = (ISystemBank) systemBankList.get(i);
				String id = Long.toString(systemBank.getId());
				String val = systemBank.getSystemBankName();
				bankListMap.put(id, val);
			}
			
			IOtherBankProxyManager otherBankProxyManager = (IOtherBankProxyManager) BeanHouse.get("otherBankProxyManager");
			SearchResult sr = otherBankProxyManager.getOtherBankList("", "");
			List otherBankList = (List) sr.getResultList();
			for (int i = 0; i < otherBankList.size(); i++) {
				IOtherBank otherBank = (IOtherBank) otherBankList.get(i);
				String id = Long.toString(otherBank.getId());
				String val = otherBank.getOtherBankName();
				bankListMap.put(id, val);
			}
		} catch (Exception ex) {
		}
		return bankListMap;
	}
	private List getCurrencyList() {
		List lbValList = new ArrayList();
		try {
				MISecurityUIHelper helper = new MISecurityUIHelper();
				IForexFeedEntry[] currency = CollateralDAOFactory.getDAO().getCurrencyList();
				
				if (currency != null) {
					for (int i = 0; i < currency.length; i++) {
						IForexFeedEntry lst = currency[i];
						String id = lst.getBuyCurrency().trim();
						String value = lst.getCurrencyIsoCode().trim();
						LabelValueBean lvBean = new LabelValueBean(value, id);
						lbValList.add(lvBean);
					}
				}
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
		return CommonUtil.sortDropdown(lbValList);
	}
}
