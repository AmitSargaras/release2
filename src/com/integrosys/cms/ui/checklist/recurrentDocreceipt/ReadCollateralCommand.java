/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/collateral/ReadCollateralCommand.java,v 1.41 2006/11/11 15:22:30 jzhai Exp $
 */

package com.integrosys.cms.ui.checklist.recurrentDocreceipt;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Iterator;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.util.LabelValueBean;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.AccessDeniedException;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.checklist.bus.CheckListDAOFactory;
import com.integrosys.cms.app.checklist.bus.ICheckListDAO;
import com.integrosys.cms.app.collateral.bus.CollateralDAOFactory;
import com.integrosys.cms.app.collateral.bus.CollateralException;
import com.integrosys.cms.app.collateral.bus.IBhavCopy;
import com.integrosys.cms.app.collateral.bus.ICollateral;
import com.integrosys.cms.app.collateral.bus.ILimitCharge;
import com.integrosys.cms.app.collateral.bus.IValuation;
import com.integrosys.cms.app.collateral.bus.OBBhavCopy;
import com.integrosys.cms.app.collateral.bus.OBValuation;
import com.integrosys.cms.app.collateral.bus.type.guarantee.subtype.banksameccy.IBankSameCurrency;
import com.integrosys.cms.app.collateral.bus.type.guarantee.subtype.banksameccy.OBBankSameCurrency;
import com.integrosys.cms.app.collateral.bus.type.guarantee.subtype.corpthirdparty.ICorporateThirdParty;
import com.integrosys.cms.app.collateral.bus.type.guarantee.subtype.corpthirdparty.OBCorporateThirdParty;
import com.integrosys.cms.app.collateral.bus.type.guarantee.subtype.government.IGovernment;
import com.integrosys.cms.app.collateral.bus.type.guarantee.subtype.government.OBGovernment;
import com.integrosys.cms.app.collateral.bus.type.guarantee.subtype.personal.IPersonal;
import com.integrosys.cms.app.collateral.bus.type.guarantee.subtype.personal.OBPersonal;
import com.integrosys.cms.app.collateral.bus.type.guarantee.subtype.sblcsameccy.ISBLCSameCurrency;
import com.integrosys.cms.app.collateral.bus.type.guarantee.subtype.sblcsameccy.OBSBLCSameCurrency;
import com.integrosys.cms.app.collateral.bus.type.insurance.subtype.keymaninsurance.IKeymanInsurance;
import com.integrosys.cms.app.collateral.bus.type.marketable.IMarketableEquity;
import com.integrosys.cms.app.collateral.bus.type.marketable.subtype.bondslocal.IBondsLocal;
import com.integrosys.cms.app.collateral.bus.type.marketable.subtype.mainindexlocal.IMainIndexLocal;
import com.integrosys.cms.app.collateral.bus.type.marketable.subtype.mainindexlocal.OBMainIndexLocal;
import com.integrosys.cms.app.collateral.bus.type.marketable.subtype.otherlistedlocal.IOtherListedLocal;
import com.integrosys.cms.app.collateral.bus.type.property.IPropertyCollateral;
import com.integrosys.cms.app.collateral.bus.type.property.OBPropertyCollateral;
import com.integrosys.cms.app.collateral.proxy.CollateralProxyFactory;
import com.integrosys.cms.app.collateral.proxy.IBhavCopyProxy;
import com.integrosys.cms.app.collateral.trx.ICollateralTrxValue;
import com.integrosys.cms.app.collateral.trx.OBCollateralTrxValue;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.customer.bus.CustomerDAOFactory;
import com.integrosys.cms.app.customer.bus.ICMSCustomer;
import com.integrosys.cms.app.customer.bus.ICustomerDAO;
import com.integrosys.cms.app.customer.proxy.CustomerProxyFactory;
import com.integrosys.cms.app.customer.proxy.ICustomerProxy;
import com.integrosys.cms.app.feed.bus.bond.IBondFeedEntry;
import com.integrosys.cms.app.feed.bus.forex.ForexFeedGroupException;
import com.integrosys.cms.app.feed.bus.forex.IForexFeedEntry;
import com.integrosys.cms.app.feed.bus.mutualfunds.IMutualFundsFeedEntry;
import com.integrosys.cms.app.feed.bus.stock.IStockFeedEntry;
import com.integrosys.cms.app.feed.proxy.bond.IBondFeedProxy;
import com.integrosys.cms.app.feed.proxy.forex.IForexFeedProxy;
import com.integrosys.cms.app.feed.proxy.mutualfunds.IMutualFundsFeedProxy;
import com.integrosys.cms.app.feed.proxy.stock.IStockFeedProxy;
import com.integrosys.cms.app.feed.trx.stock.IStockFeedGroupTrxValue;
import com.integrosys.cms.app.geography.city.bus.ICity;
import com.integrosys.cms.app.geography.country.bus.ICountry;
import com.integrosys.cms.app.geography.region.bus.IRegion;
import com.integrosys.cms.app.geography.state.bus.IState;
import com.integrosys.cms.app.insurancecoverage.proxy.IInsuranceCoverageProxyManager;
import com.integrosys.cms.app.limit.bus.ILimitProfile;
import com.integrosys.cms.app.otherbank.proxy.IOtherBankProxyManager;
import com.integrosys.cms.app.systemBank.bus.ISystemBank;
import com.integrosys.cms.app.systemBank.proxy.ISystemBankProxyManager;
import com.integrosys.cms.app.systemBankBranch.bus.ISystemBankBranch;
import com.integrosys.cms.app.systemBankBranch.proxy.ISystemBankBranchProxyManager;
import com.integrosys.cms.app.transaction.OBTrxContext;
import com.integrosys.cms.app.valuationAgency.bus.IValuationAgency;
import com.integrosys.cms.app.valuationAgency.proxy.IValuationAgencyProxyManager;
import com.integrosys.cms.host.eai.security.bus.SecurityDaoImpl;
import com.integrosys.cms.host.stp.common.IStpErrorMessageFetcher;
import com.integrosys.cms.ui.collateral.BhavCopyException;
import com.integrosys.cms.ui.collateral.CategoryCodeConstant;
import com.integrosys.cms.ui.collateral.CollateralAction;
import com.integrosys.cms.ui.collateral.CollateralStpValidateUtils;
import com.integrosys.cms.ui.collateral.CollateralStpValidator;
import com.integrosys.cms.ui.collateral.assetbased.assetgencharge.OBInsuranceGC;
import com.integrosys.cms.ui.common.UIUtil;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;
import com.integrosys.cms.ui.insurancecoverage.IInsuranceCoverage;
import com.integrosys.cms.ui.manualinput.CommonUtil;
import com.integrosys.cms.ui.manualinput.security.MISecurityUIHelper;
import com.integrosys.cms.ui.otherbankbranch.IOtherBank;
import com.integrosys.component.commondata.app.CommonDataSingleton;
import com.integrosys.cms.app.collateral.proxy.ICollateralProxy;
import com.integrosys.cms.app.collateral.bus.type.asset.subtype.gcharge.IGeneralCharge;
import com.integrosys.cms.ui.collateral.assetbased.assetgencharge.AssetGenChargeHelper;
import com.integrosys.cms.app.collateral.bus.type.asset.subtype.gcharge.IGeneralChargeDetails;
import com.integrosys.cms.app.collateral.bus.type.asset.subtype.gcharge.IGeneralChargeStockDetails;

/**

 * @author $Author: jzhai $<br>
 * @version $Revision: 1.41 $
 * @since $Date: 2006/11/11 15:22:30 $
 * Tag: $Name:  $
 */

/**
 * Created by IntelliJ IDEA. User: ssathish Date: Jul 2, 2003 Time: 12:13:00 PM
 * To change this template use Options | File Templates.
 */
public class ReadCollateralCommand extends AbstractCommand {
	


	private IBondFeedProxy bondFeedProxy = (IBondFeedProxy)BeanHouse.get("bondFeedProxy");
	
	
	
	private IStockFeedProxy stockFeedProxyNew = (IStockFeedProxy)BeanHouse.get("stockFeedProxy");
	
	private IMutualFundsFeedProxy mutualFundsFeedProxy = (IMutualFundsFeedProxy)BeanHouse.get("mutualFundsFeedProxy");
	
	private ISystemBankBranchProxyManager systemBankBranchProxy;
		 
	public ISystemBankBranchProxyManager getSystemBankBranchProxy() {
		return systemBankBranchProxy;
	}
	
	public void setSystemBankBranchProxy(
			ISystemBankBranchProxyManager systemBankBranchProxy) {
		this.systemBankBranchProxy = systemBankBranchProxy;
	}

	
	IStockFeedProxy stockFeedProxy;
	
	IBhavCopyProxy bhavCopyProxy;
	
	private IForexFeedProxy forexFeedProxy;
	
	private IOtherBankProxyManager otherBankProxyManager ;
	
	

	/**
	 * @return the otherBankProxyManager
	 */
	public IOtherBankProxyManager getOtherBankProxyManager() {
		return (IOtherBankProxyManager)BeanHouse.get("otherBankProxyManager");
	}

	/**
	 * @param otherBankProxyManager the otherBankProxyManager to set
	 */
	public void setOtherBankProxyManager(
			IOtherBankProxyManager otherBankProxyManager) {
		this.otherBankProxyManager = otherBankProxyManager;
	}

	/**
	 * @return the stockFeedProxy
	 */
	public IStockFeedProxy getStockFeedProxy() {
		return stockFeedProxy;
	}

	/**
	 * @param stockFeedProxy the stockFeedProxy to set
	 */
	public void setStockFeedProxy(IStockFeedProxy stockFeedProxy) {
		this.stockFeedProxy = stockFeedProxy;
	}

	/**
	 * @return the bhavCopyProxy
	 */
	public IBhavCopyProxy getBhavCopyProxy() {
		return bhavCopyProxy;
	}

	/**
	 * @param bhavCopyProxy the bhavCopyProxy to set
	 */
	public void setBhavCopyProxy(IBhavCopyProxy bhavCopyProxy) {
		this.bhavCopyProxy = bhavCopyProxy;
	}

	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ "collateralID", "java.lang.String", REQUEST_SCOPE },
				{ "collateralID", "java.lang.String", SERVICE_SCOPE },
				{ "theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE },
				{ "trxID", "java.lang.String", REQUEST_SCOPE },
				{ "userName", "java.lang.String", REQUEST_SCOPE },
				
				{ "event", "java.lang.String", REQUEST_SCOPE },
				{ "flag", "java.lang.String", REQUEST_SCOPE },
				{ "flag1", "java.lang.String", SERVICE_SCOPE },
				{ IGlobalConstant.USER_TEAM, "com.integrosys.component.bizstructure.app.bus.ITeam", GLOBAL_SCOPE },
				{ IGlobalConstant.GLOBAL_LIMITPROFILE_OBJ, "com.integrosys.cms.app.limit.bus.ILimitProfile",
						GLOBAL_SCOPE },
				{ "dueDate", "java.lang.String", REQUEST_SCOPE },
				
				
				
				{ "frameRequested", "java.lang.String", REQUEST_SCOPE },
				{ "frame", "java.lang.String", SERVICE_SCOPE },
				{ "from", "java.lang.String", REQUEST_SCOPE },
				{ "fromLimitProfileId", "java.lang.String", REQUEST_SCOPE },
				{ "form.collateralObject", "com.integrosys.cms.app.collateral.bus.ICollateral", SERVICE_SCOPE },
				{ IGlobalConstant.GLOBAL_CUSTOMER_OBJ, "com.integrosys.cms.app.customer.bus.ICMSCustomer", GLOBAL_SCOPE },
				{com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY, "java.util.Locale", GLOBAL_SCOPE},
				{"countryId","java.lang.String",REQUEST_SCOPE},
				{"regionId","java.lang.String",REQUEST_SCOPE},
				{"stateId","java.lang.String",REQUEST_SCOPE},
				{ "insuranceList",  "java.util.List", SERVICE_SCOPE },
				{ "actualMap",  "java.util.HashMap", SERVICE_SCOPE },
				 { "componentList",  "java.util.List", SERVICE_SCOPE },
				 { "insuranceCoverageList",  "java.util.List", SERVICE_SCOPE },
	            
	            { "LegalID", "java.lang.String", REQUEST_SCOPE },
								
				 /*{ "collateralList", "java.util.List", SERVICE_SCOPE },
		            { "systemBankBranch", "com.integrosys.cms.app.systemBankBranch.bus.ISystemBankBranch", SERVICE_SCOPE },
		            { "countryNme", "java.lang.String", SERVICE_SCOPE },*/
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
				{ "serviceColObj", "com.integrosys.cms.app.collateral.trx.ICollateralTrxValue", SERVICE_SCOPE },
				{ "form.collateralObject", "com.integrosys.cms.app.collateral.bus.ICollateral", FORM_SCOPE },
				{ "collateralID", "java.lang.String", REQUEST_SCOPE },
				{ "collateralID", "java.lang.String", SERVICE_SCOPE },
				{ "wip", "java.lang.String", REQUEST_SCOPE },
				{ "customerOb", "com.integrosys.cms.app.customer.bus.OBCMSCustomer", REQUEST_SCOPE },
				{ "limitprofileOb", "com.integrosys.cms.app.limit.bus.OBLimitProfile", REQUEST_SCOPE },
				{ "flag1", "java.lang.String", SERVICE_SCOPE },
				{ "trxID", "java.lang.String", REQUEST_SCOPE },
				{ "userName", "java.lang.String", REQUEST_SCOPE },
				{ "migratedFlag", "java.lang.String", SERVICE_SCOPE },
				// { "oldValuer", "java.lang.String", SERVICE_SCOPE },
				{ "from_page", "java.lang.String", SERVICE_SCOPE },
				{ "bcaLocationList", "java.util.ArrayList", SERVICE_SCOPE },
				{ "onlyBcaLocList", "java.util.ArrayList", SERVICE_SCOPE },
				{ "liquidationIsNPL", "java.lang.String", SERVICE_SCOPE },
				{ "showMFChecklist", "java.lang.String", SERVICE_SCOPE },
				{ "frame", "java.lang.String", SERVICE_SCOPE }, { "from", "java.lang.String", SERVICE_SCOPE },
				{ "errorMsg", "java.lang.String", REQUEST_SCOPE },
				{ "form.collateralObject", "com.integrosys.cms.app.collateral.bus.ICollateral", SERVICE_SCOPE },
				{ "branchList", "java.util.List", ICommonEventConstant.SERVICE_SCOPE },
				{ "exchangeRate", "java.lang.String", SERVICE_SCOPE },
				{ "countryList","java.util.List",SERVICE_SCOPE},
	            { "regionList","java.util.List",SERVICE_SCOPE},
	            { "stateList","java.util.List",SERVICE_SCOPE},
	            { "cityList","java.util.List",SERVICE_SCOPE},
	            { "cityName", "java.lang.String", SERVICE_SCOPE },
	            { "stateName", "java.lang.String", SERVICE_SCOPE },
	            { "regionName", "java.lang.String", SERVICE_SCOPE },
	            { "countryName", "java.lang.String", SERVICE_SCOPE },
	            { "collateralList", "java.util.List", REQUEST_SCOPE },
	            { "systemBankBranch", "com.integrosys.cms.app.systemBankBranch.bus.ISystemBankBranch", REQUEST_SCOPE },
	            { "countryNme", "java.lang.String", REQUEST_SCOPE },
	            { "valuationAgencyList","java.util.List",SERVICE_SCOPE},
	            { "valuationAgencyName", "java.lang.String", REQUEST_SCOPE },	            
	            
	            { "collateralList", "java.util.List", SERVICE_SCOPE },
	            { "systemBankBranch", "com.integrosys.cms.app.systemBankBranch.bus.ISystemBankBranch", SERVICE_SCOPE },
	            { "countryNme", "java.lang.String", SERVICE_SCOPE },
	            { "no_template_gc", "java.lang.String", SERVICE_SCOPE },
	            { "insuranceCoverageMap","java.util.HashMap",SERVICE_SCOPE},	            
	            { "InsurerName", "java.lang.String", REQUEST_SCOPE },
	            { "bankList", "java.util.List", SERVICE_SCOPE },
	            { "bankListMap", "java.util.HashMap", SERVICE_SCOPE },
	            { "docCodeWithStocks", "java.util.HashMap", SERVICE_SCOPE },
	            { "orgMap","java.util.HashMap",SERVICE_SCOPE},
	            { "orgList", "java.util.List", SERVICE_SCOPE },
	          //  { "bondFeedEntry", "com.integrosys.cms.app.feed.bus.bond.IBondFeedEntry", REQUEST_SCOPE },
	            { "BondList", "java.util.List", SERVICE_SCOPE },
	            { "StockList", "java.util.List", SERVICE_SCOPE },
	            { "FundList", "java.util.List", SERVICE_SCOPE },
	            { "currencyList", "java.util.List", SERVICE_SCOPE },
	            { "transactionHistoryList", "java.util.List", SERVICE_SCOPE },
	            { "insuranceList",  "java.util.List", SERVICE_SCOPE },
	            { "componentList",  "java.util.List", SERVICE_SCOPE },
	            { "insuranceCoverageList",  "java.util.List", SERVICE_SCOPE },
	             { IGlobalConstant.GLOBAL_CUSTOMER_OBJ,
					"com.integrosys.cms.app.customer.bus.ICMSCustomer", GLOBAL_SCOPE },
				
		});

	}

	/**
	 * This method does the Business operations with the HashMap and put the
	 * results back into the HashMap.Here reading for Company Borrower is done.
	 * 
	 * @param map is of type HashMap
	 * @return HashMap with the Result
	 * @throws com.integrosys.base.uiinfra.exception.CommandProcessingException
	 *         on errors
	 * @throws com.integrosys.base.uiinfra.exception.CommandValidationException
	 *         on errors
	 */
	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException,
			AccessDeniedException {

		HashMap result = new HashMap();
		HashMap exceptionMap = new HashMap();
		HashMap temp = new HashMap();
		List insuranceList = (List) map.get("insuranceList");
		if(insuranceList ==null){
			insuranceList=new ArrayList();
		}
		
		List componentList = (List) map.get("componentList");
		if(componentList ==null){
			componentList=new ArrayList();
		}
		List insuranceCoverageList = (List) map.get("insuranceCoverageList");
		if(insuranceCoverageList ==null){
			insuranceCoverageList=new ArrayList();
		}
		
		
		OBTrxContext ctx = (OBTrxContext) map.get("theOBTrxContext");
		ICollateralTrxValue itrxValue = new OBCollateralTrxValue();
		List collateralList = new ArrayList();
		ISystemBankBranch systemBankBranch = null;
		String countryNme = "";
		String valuationAgencyName = "";
		HashMap docCodeWithStocks = new HashMap();		
		String event = (String) map.get("event");
		String flag = (String) map.get("flag");
		String flag1 = (String) map.get("flag1");
		BigDecimal exchangeRate = null;
		String trxID = (String) map.get("trxID");
        String userName=(String)map.get("userName");
        String idStrVal = (String) map.get("collateralID");
        String countryId = (String) map.get("countryId");
    	String regionId = (String) map.get("regionId");
    	String stateId = (String) map.get("stateId");
    	Locale locale = (Locale) map.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);
    	String LegalID = (String) map.get("LegalID");
     	IValuationAgencyProxyManager valuationProxy = (IValuationAgencyProxyManager)BeanHouse.get("valuationAgencyProxy");
        
		DefaultLogger.debug(this, "event is : " + event);
		try {
			// result.put("flag", flag);
			if (flag1 != null) {
				result.put("flag1", flag1);
			}
			else {
				result.put("flag1", flag);
			}

			ICMSCustomer custOB = (ICMSCustomer) map.get(IGlobalConstant.GLOBAL_CUSTOMER_OBJ);
			if (null != custOB) {
				result.put("customerOb", custOB);
			}

			ILimitProfile limitProfileOB = (ILimitProfile) map.get(IGlobalConstant.GLOBAL_LIMITPROFILE_OBJ);
			String fromLimitProfileIdStr = (String) map.get("fromLimitProfileId");
			if (null != limitProfileOB) {
				result.put("limitprofileOb", limitProfileOB);

				if (fromLimitProfileIdStr != null) {
					long fromLimitProfileIdValue = Long.parseLong(fromLimitProfileIdStr);
					if (fromLimitProfileIdValue != limitProfileOB.getLimitProfileID()) {
						throw new AccessDeniedException("Limit Profile Id [" + fromLimitProfileIdValue
								+ "] from the listing page different from the one in Session ["
								+ limitProfileOB.getLimitProfileID() + "]");
					}

				}

			}

			if (event.equals("process_update") || event.equals("prepare_close") || event.equals("track")) {
				
				itrxValue = CollateralProxyFactory.getProxy().getCollateralTrxValue(ctx, trxID.trim());

			}
			else {
				String idStr = (String) map.get("collateralID");
				ICheckListDAO checkListDAO=CheckListDAOFactory.getCheckListDAO();
				String dueDateStr2=(String) map.get("dueDate");
				String[] split2 = dueDateStr2.split(",");
				long id = checkListDAO.getCollateralIdMap(custOB.getCMSLegalEntity().getLEReference(), split2[1]);
				if(id==0){
					result.put("no_template_gc", "true");
					temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
					return temp;
				}else{
					result.put("no_template_gc", "false");
				}
				itrxValue = CollateralProxyFactory.getProxy().getCollateralTrxValue(ctx, id);
				if(custOB != null)
				{
				itrxValue.setCustomerID(custOB.getCustomerID());
				itrxValue.setLegalName(custOB.getCustomerName());
				}
				
			}
	//======================PRAMOD(Start)=====================
			ICollateralProxy collateralProxy = CollateralProxyFactory.getProxy();
			//ICollateralTrxValue itrxValue1 = (ICollateralTrxValue) map.get("serviceColObj");;
			long customerID=0;
			try {
				String customerIDstr = collateralProxy.getCustomerIDByCollateralID(itrxValue.getCollateral().getCollateralID());
				if(customerIDstr!=null&&!"".equals(customerIDstr.trim())){
					customerID=Long.parseLong(customerIDstr);
				}
			} catch (CollateralException e) {
				e.printStackTrace();
			}
			
			 List retrivedDueDateList=(List)collateralProxy.getRecurrentDueDateListByCustomerAndCollatralID(customerID,0);
			 List dateList=new ArrayList();
			 List docList=new ArrayList();
			 String dueDate=null;
			 
			
			 
			 
			 String docCode = "";
			  
			    
				if(retrivedDueDateList!=null){
					
					Iterator it=retrivedDueDateList.iterator();
					while(it.hasNext())
			        {
						String dueDateStr=(String) it.next();
			         
			          if(dueDateStr!=null&&!"".equals(dueDateStr.trim())){
			  			String[] split = dueDateStr.split(",");
			  			 dueDate =split[0];
			  			
			  		    dateList.add(dueDate);
			  			docCode=split[1];
						docList.add(docCode);
			  			
			  		}
			          		          
			        }
				}
			    

				if(itrxValue.getCollateral() instanceof  IGeneralCharge)
				{
			    IGeneralCharge newCollateral = (IGeneralCharge) itrxValue.getStagingCollateral();
			    if(dateList!=null){
			    	for(int k=0; k<dateList.size(); k++){
					IGeneralChargeDetails[] existingGeneralChargeDetails = newCollateral.getGeneralChargeDetails();					
					IGeneralChargeDetails existingChargeDetails;					
					if(existingGeneralChargeDetails!=null){
						for (int i = 0; i < existingGeneralChargeDetails.length; i++) {
							 existingChargeDetails = existingGeneralChargeDetails[i];
							 
							if(existingChargeDetails!=null && null != existingChargeDetails.getDocCode()  && existingChargeDetails.getDocCode().equals(docList.get(k))){								
								docCodeWithStocks.put(docList.get(k), docList.get(k));

								
							}
						}
					}
			    }
			    }
			    
				}
			    //=======================PRAMOD(End)====================
			
			if(itrxValue.getCollateral() instanceof  IBondsLocal)
			{
				IBondsLocal iCol;
				if ( event.equals("track")||event.equals("prepare_close") ||event.equals("process_update")) {
					
					 iCol = (IBondsLocal) itrxValue.getStagingCollateral();		

				}
				else{
				 iCol = (IBondsLocal) itrxValue.getCollateral();				
				}
			IBondFeedEntry bondFeedEntry = null;
			IMarketableEquity[] equity = iCol.getEquityList();
			String bondCode = null;
			List BondList = new ArrayList() ;
			for(int i=0;i<equity.length;i++){
				bondCode = equity[i].getStockCode();					
				IBondFeedEntry obMutualFundsEntry = null;
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
				
				IOtherListedLocal iCol;
				if ( event.equals("track")||event.equals("prepare_close") ||event.equals("process_update")) {
					
					 iCol = (IOtherListedLocal) itrxValue.getStagingCollateral();		

				}
				else{
				 iCol = (IOtherListedLocal) itrxValue.getCollateral();				
				}
						
							
			
			IStockFeedEntry stockFeedEntry = null;
			IMarketableEquity[] equity = iCol.getEquityList();
			String strStockExchange = null;
			String strScriptCode = null;
			List StockList = new ArrayList() ;
			for(int i=0;i<equity.length;i++){
				strScriptCode = equity[i].getIsinCode();
				strStockExchange = equity[i].getStockExchange();
				
				
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
				if ( event.equals("track")||event.equals("prepare_close") ||event.equals("process_update")) {
					
					 iCol = (IMainIndexLocal) itrxValue.getStagingCollateral();		

				}
				else{
				 iCol = (IMainIndexLocal) itrxValue.getCollateral();				
				}
				
			IMutualFundsFeedEntry mutualFundsFeedEntry = null;
			IMarketableEquity[] equity = iCol.getEquityList();
			String mutualCode = null;
			List FundList = new ArrayList() ;
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
			
			if (ICMSConstant.STATE_PENDING_PERFECTION.equals(itrxValue.getStatus())
					&& (ICMSConstant.STATE_PENDING_CREATE.equals(itrxValue.getFromState()) || ICMSConstant.PENDING_UPDATE
							.equals(itrxValue.getFromState()))) {
				Map context = new HashMap();
				// Andy Wong: set CMV to staging if actual got value but staging
				// blank, used for pre Stp valuation validation
				if (itrxValue.getCollateral() != null
						&& itrxValue.getCollateral().getCMV() != null
						&& (itrxValue.getStagingCollateral().getCMV() == null || itrxValue.getStagingCollateral()
								.getCMV().getAmount() <= 0)) {
					itrxValue.getStagingCollateral().setCMV(itrxValue.getCollateral().getCMV());
				}
				context.put(CollateralStpValidator.COL_OB, itrxValue.getStagingCollateral());
				context.put(CollateralStpValidator.TRX_STATUS, itrxValue.getStatus());
				context.put(CollateralStpValidator.COL_TRX_VALUE, itrxValue);
				ActionErrors errors = CollateralStpValidateUtils.validateAndAccumulate(context);
				if (!errors.isEmpty()) {
					temp.put(ICommonEventConstant.MESSAGE_LIST, errors);
				}
			}

			DefaultLogger.debug(this, "============= transaction id ============> " + itrxValue.getTransactionID());
			String error = null;
			IStpErrorMessageFetcher iStpErrorMessageFetcher = (IStpErrorMessageFetcher) BeanHouse
					.get("stpErrorMessageFetcher");
			ArrayList aList = (ArrayList) iStpErrorMessageFetcher.getErrorMessage(itrxValue.getTransactionID());
			if (aList != null && aList.size() > 0) {
				error = (String) aList.get(0);
			}
			result.put("errorMsg", error);

			// Added for Liquidation Button
			if (itrxValue.getCollateral() != null) {
				long collateralId = itrxValue.getCollateral().getCollateralID();
				String liquidationIsNPL = CollateralProxyFactory.getProxy().getLiquidationIsNPL(collateralId);
				result.put("liquidationIsNPL", liquidationIsNPL);
//				result.put("liquidationIsNPL",  ICMSConstant.TRUE_VALUE);   // need to check with later..
			}

			if ((event.equals("prepare_update") || event.equals("prepare_delete"))
					&& (itrxValue.getStatus().equals(ICMSConstant.STATE_REJECTED)
							|| itrxValue.getStatus().equals(ICMSConstant.STATE_PENDING_UPDATE)
							|| itrxValue.getStatus().equals(ICMSConstant.STATE_DRAFT) || itrxValue.getStatus().equals(
							ICMSConstant.STATE_PENDING_DELETE))) {
				result.put("wip", "wip");
			}

			if ((event.equals("prepare_update") || event.equals("prepare_delete"))
					&& itrxValue.getStatus().equals(ICMSConstant.STATE_ACTIVE)) {
				ICollateral staging = CollateralProxyFactory.getProxy().getCollateral(
						Long.parseLong((String) map.get("collateralID")), true);
				itrxValue.setStagingCollateral(staging);
			}

			if (event.equals("prepare_update")
					&& (itrxValue.getStatus().equals(ICMSConstant.STATE_DELETED) || itrxValue.getStatus().equals(
							ICMSConstant.STATE_PENDING_DELETE))) {
				if (itrxValue.getStagingCollateral() == null) {
					ICollateral staging = CollateralProxyFactory.getProxy().getCollateral(
							Long.parseLong((String) map.get("collateralID")), true);
					itrxValue.setStagingCollateral(staging);
				}
			}

			if (itrxValue != null) {
				if (itrxValue.getCollateral() != null) {
					DefaultLogger.debug(this, "Margin" + itrxValue.getCollateral().getMargin());
					if (itrxValue.getCollateral().getCollateralSubType() != null) {
						if (itrxValue.getCollateral().getCollateralSubType().getSubTypeCode() != null) {
							if (CommonDataSingleton.getCodeCategoryLabelByValue(CategoryCodeConstant.SEC_SUBTYP,
									itrxValue.getCollateral().getCollateralSubType().getSubTypeCode()) != null) {
								itrxValue.getCollateral().getCollateralSubType().setSubTypeCode(
										CommonDataSingleton.getCodeCategoryLabelByValue(
												CategoryCodeConstant.SEC_SUBTYP, itrxValue.getCollateral()
														.getCollateralSubType().getSubTypeCode()));
							}
						}
					}
					ILimitCharge[] chargeList = itrxValue.getCollateral().getLimitCharges();
					if (chargeList != null) {
						Arrays.sort(chargeList, new Comparator() {
							public int compare(Object o1, Object o2) {
								int int1 = ((ILimitCharge) o1).getSecurityRank();
								int int2 = ((ILimitCharge) o2).getSecurityRank();
								return int1 - int2;
							}
						});
					}
					itrxValue.getCollateral().setLimitCharges(chargeList);
				}
				if (itrxValue.getStagingCollateral() != null) {
					DefaultLogger.debug(this, "stageMargin" + itrxValue.getStagingCollateral().getMargin());
					if (itrxValue.getStagingCollateral().getCollateralSubType() != null) {
						if (itrxValue.getStagingCollateral().getCollateralSubType().getSubTypeCode() != null) {
							if (CommonDataSingleton.getCodeCategoryLabelByValue(CategoryCodeConstant.SEC_SUBTYP,
									itrxValue.getStagingCollateral().getCollateralSubType().getSubTypeCode()) != null) {
								itrxValue.getStagingCollateral().getCollateralSubType().setSubTypeCode(
										CommonDataSingleton.getCodeCategoryLabelByValue(
												CategoryCodeConstant.SEC_SUBTYP, itrxValue.getStagingCollateral()
														.getCollateralSubType().getSubTypeCode()));
							}
						}
					}
					ILimitCharge[] chargeList = itrxValue.getStagingCollateral().getLimitCharges();
					if (chargeList != null) {
						Arrays.sort(chargeList, new Comparator() {
							public int compare(Object o1, Object o2) {
								int int1 = ((ILimitCharge) o1).getSecurityRank();
								int int2 = ((ILimitCharge) o2).getSecurityRank();
								return int1 - int2;
							}
						});
					}
					itrxValue.getStagingCollateral().setLimitCharges(chargeList);
				}
			}
			
			if(itrxValue.getLimitProfileReferenceNumber()== null){
				String cmsCollateralId = itrxValue.getReferenceID();
				String camId = CollateralDAOFactory.getDAO().getCamIdByCollateralID(cmsCollateralId);
				itrxValue.setLimitProfileReferenceNumber(camId);
			}	
			
			if (itrxValue != null) {
				result.put("serviceColObj", itrxValue);
				//******************Add By sachin patil**********************//
				String from_event = (String) map.get("from_event");
	            if ((from_event != null) && (from_event.equals("process_update")||from_event.equals("prepare_close"))) {
	                result.put("form.collateralObject", itrxValue.getCollateral());
	            } else {
	            	ICollateral col = itrxValue.getStagingCollateral();
	            //	col.setCurrencyCode(itrxValue.getCollateral().getCurrencyCode());
	            	col.setCollateralLocation(itrxValue.getCollateral().getCollateralLocation());
	            	//col.setSecurityOrganization(itrxValue.getStagingCollateral().getSecurityOrganization());
	            //	col.setSecPriority(itrxValue.getCollateral().getSecPriority());
	            	itrxValue.setStagingCollateral(col); 
	                result.put("form.collateralObject", itrxValue.getStagingCollateral());
	            }
				
				
				
				//************ Start of Lines added by Dattatray Thorat *****************
				
				if((itrxValue.getCollateral()) instanceof OBMainIndexLocal){
					
			       // Locale locale = (Locale) map.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);
			        IStockFeedGroupTrxValue trxValue = null;
					IMainIndexLocal iCol = (IMainIndexLocal) itrxValue.getCollateral();
					IValuation valuation;
					if(iCol.getSourceValuation()==null)
						valuation = new OBValuation();
					else
						valuation = iCol.getSourceValuation();
					
					IMarketableEquity[] equity = iCol.getEquityList();
					IBhavCopy bhavCopy = new OBBhavCopy();
					//Start- Added by Uma Khot- CAM QUARTER ACTIVITY CR: allow decimal in NoofUnits for Security Sub-type ‘Mutual Funds’.
					double noOfUnits = 0;
					//End- Added by Uma Khot- CAM QUARTER ACTIVITY CR: allow decimal in NoofUnits for Security Sub-type ‘Mutual Funds’.
					if(equity.length>0){
						String scCode = "";
						for(int i=0;i<equity.length;i++){
							scCode = equity[i].getClientCode();
							noOfUnits = equity[i].getNoOfUnits();
							
						}
						if(scCode !="" && scCode !=null){
							
							DefaultLogger.debug(this, "getting by subtype.");
							
							trxValue = getStockFeedProxy().getStockFeedGroup("KLS", "003");
							IStockFeedEntry[] stockFeedEntries = trxValue.getStagingStockFeedGroup().getFeedEntries();
							
							// bhavCopy = getBhavCopyProxy().getBhavCopyDetails(Long.parseLong(scCode));
							
							double closeValue = stockFeedEntries[0].getUnitPrice(); 
								//bhavCopy.getCloseValue().doubleValue();
							
							double resultVal = noOfUnits * closeValue;
							
							if(valuation == null){
								valuation.setCurrencyCode("MYR");
								valuation.setFSV(UIUtil.convertToAmount(locale, "MYR", Double.toString(resultVal)));
								valuation.setFSVEvaluationDate(new Date());
							}else{	
								valuation.setFSV(UIUtil.convertToAmount(locale, "MYR", Double.toString(resultVal)));
								valuation.setFSVEvaluationDate(new Date());
							}
							iCol.setSourceValuation(valuation);
						}	
					}	
				}	

				//************ End of Lines added by Dattatray Thorat *****************				
				
				if (event.equals("read")) {
					result.put("form.collateralObject", itrxValue.getCollateral());
					DefaultLogger.debug(this, "-----------------------ACTUAL subtypeCode: "
							+ itrxValue.getCollateral().getCollateralSubType().getSubTypeCode());
				}
				else {
					result.put("form.collateralObject", itrxValue.getStagingCollateral());
					DefaultLogger.debug(this, "-------------------------STAGING subtypeCode: "
							+ itrxValue.getStagingCollateral().getCollateralSubType().getSubTypeCode());
				}

				if (fromLimitProfileIdStr != null && itrxValue.getLimitProfileID() == ICMSConstant.LONG_INVALID_VALUE) {
					itrxValue.setLimitProfileID(Long.parseLong(fromLimitProfileIdStr));
				}
			}

		}
		//*********** Start of Exception code added by Dattatray Thorat ************  
		catch (BhavCopyException bce) {
        	CommandProcessingException cpe = new CommandProcessingException(bce.getMessage());
			cpe.initCause(bce);
			throw cpe;
		}
		//*********** End of Exception code added by Dattatray Thorat ************
		catch (CollateralException e) {
			throw new CommandProcessingException("failed to read collateral", e);
		}

		String frame = (String) map.get("frameRequested");
		if (frame == null) {
			frame = (String) map.get("frame");
		}

		if (CollateralAction.EVENT_PROCESS_UPDATE.equals(event)
				&& ICMSConstant.STATE_PENDING_DELETE.equals(itrxValue.getFromState())
				&& ICMSConstant.STATE_REJECTED.equals(itrxValue.getStatus())) {
			result.put("from_page", "prepare_delete");
		}
		else {
			result.put("from_page", event);
		}

	
		try { //Add By Govind S:05/09/2011
			if(itrxValue!=null){
		    exchangeRate = getForexFeedProxy().getExchangeRateWithINR(itrxValue.getCollateral().getCurrencyCode());
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
		List regionList = new ArrayList();
		List stateList = new ArrayList();
		List cityList = new ArrayList();
		
		String cityName=null;
		String stateName=null;
		String regionName=null;
		String countryName=null;
		
		IBankSameCurrency iCol1 = new OBBankSameCurrency();
		if(itrxValue.getCollateral() instanceof  OBBankSameCurrency){
			iCol1 = (IBankSameCurrency) itrxValue.getStagingCollateral();
	    	if(iCol1.getCountry()!=null){
	    		regionList = getRegionList(iCol1.getCountry());
	    		stateList = getStateList(iCol1.getRegion());
	    		cityList = getCityList(iCol1.getState());
	    	}
	    	if(iCol1.getCity()!=null && !iCol1.getCity().equals(""))
	    		cityName = getOtherBankProxyManager().getCityName(iCol1.getCity());
	    	
	    	if(iCol1.getState()!=null && !iCol1.getState().equals(""))
	    		stateName = getOtherBankProxyManager().getStateName(iCol1.getState());
	    	
	    	if(iCol1.getRegion()!=null && !iCol1.getRegion().equals(""))
	    		regionName = getOtherBankProxyManager().getRegionName(iCol1.getRegion());
	    	
	    	if(iCol1.getCountry()!=null && !iCol1.getCountry().equals(""))
	    		countryName = getOtherBankProxyManager().getCountryName(iCol1.getCountry());
		}
		else if(itrxValue.getCollateral() instanceof  OBCorporateThirdParty)
		{
			ICorporateThirdParty iCol2 = (ICorporateThirdParty) itrxValue.getStagingCollateral();
	    	if(iCol2.getCountry()!=null){
	    		regionList = getRegionList(iCol2.getCountry());
	    		stateList = getStateList(iCol2.getRegion());
	    		cityList = getCityList(iCol2.getState());
	    	}
	    	if(iCol2.getCity()!=null && !iCol2.getCity().equals(""))
	    		cityName = getOtherBankProxyManager().getCityName(iCol2.getCity());
	    	
	    	if(iCol2.getState()!=null && !iCol2.getState().equals(""))
	    		stateName = getOtherBankProxyManager().getStateName(iCol2.getState());
	    	
	    	if(iCol2.getRegion()!=null && !iCol2.getRegion().equals(""))
	    		regionName = getOtherBankProxyManager().getRegionName(iCol2.getRegion());
	    	
	    	if(iCol2.getCountry()!=null && !iCol2.getCountry().equals(""))
	    		countryName = getOtherBankProxyManager().getCountryName(iCol2.getCountry());
		}
		
		else if(itrxValue.getCollateral() instanceof  OBGovernment){
			IGovernment iCol3 = (IGovernment)itrxValue.getStagingCollateral();
	    	
	    	if(iCol3.getCountry()!=null){
	    		regionList = getRegionList(iCol3.getCountry());
	    		stateList = getStateList(iCol3.getRegion());
	    		cityList = getCityList(iCol3.getState());
	    	}
	    	if(iCol3.getCity()!=null && !iCol3.getCity().equals(""))
	    		cityName = getOtherBankProxyManager().getCityName(iCol3.getCity());
	    	
	    	if(iCol3.getState()!=null && !iCol3.getState().equals(""))
	    		stateName = getOtherBankProxyManager().getStateName(iCol3.getState());
	    	
	    	if(iCol3.getRegion()!=null && !iCol3.getRegion().equals(""))
	    		regionName = getOtherBankProxyManager().getRegionName(iCol3.getRegion());
	    	
	    	if(iCol3.getCountry()!=null && !iCol3.getCountry().equals(""))
	    		countryName = getOtherBankProxyManager().getCountryName(iCol3.getCountry());
		}
		else if(itrxValue.getCollateral() instanceof  OBSBLCSameCurrency){
			ISBLCSameCurrency iCol4 = (ISBLCSameCurrency) itrxValue.getStagingCollateral();
			
			if(iCol4.getCountry()!=null){
	    		regionList = getRegionList(iCol4.getCountry());
	    		stateList = getStateList(iCol4.getRegion());
	    		cityList = getCityList(iCol4.getState());
	    	}
	    	if(iCol4.getCity()!=null && !iCol4.getCity().equals(""))
	    		cityName = getOtherBankProxyManager().getCityName(iCol4.getCity());
	    	
	    	if(iCol4.getState()!=null && !iCol4.getState().equals(""))
	    		stateName = getOtherBankProxyManager().getStateName(iCol4.getState());
	    	
	    	if(iCol4.getRegion()!=null && !iCol4.getRegion().equals(""))
	    		regionName = getOtherBankProxyManager().getRegionName(iCol4.getRegion());
	    	
	    	if(iCol4.getCountry()!=null && !iCol4.getCountry().equals(""))
	    		countryName = getOtherBankProxyManager().getCountryName(iCol4.getCountry());
		
		
		
		}
		
		else if(itrxValue.getCollateral() instanceof  OBPersonal){
			IPersonal iCol5 = (IPersonal) itrxValue.getStagingCollateral();
		
			if(iCol5.getCountry()!=null){
	    		regionList = getRegionList(iCol5.getCountry());
	    		stateList = getStateList(iCol5.getRegion());
	    		cityList = getCityList(iCol5.getState());
	    	}
	    	if(iCol5.getCity()!=null && !iCol5.getCity().equals(""))
	    		cityName = getOtherBankProxyManager().getCityName(iCol5.getCity());
	    	
	    	if(iCol5.getState()!=null && !iCol5.getState().equals(""))
	    		stateName = getOtherBankProxyManager().getStateName(iCol5.getState());
	    	
	    	if(iCol5.getRegion()!=null && !iCol5.getRegion().equals(""))
	    		regionName = getOtherBankProxyManager().getRegionName(iCol5.getRegion());
	    	
	    	if(iCol5.getCountry()!=null && !iCol5.getCountry().equals(""))
	    		countryName = getOtherBankProxyManager().getCountryName(iCol5.getCountry());
		
		
		}
		else if(itrxValue.getCollateral() instanceof  OBPropertyCollateral){
			IPropertyCollateral iCol5 = (IPropertyCollateral) itrxValue.getStagingCollateral();
		
			if(iCol5.getCountry()!=null){
	    		regionList = getRegionList(iCol5.getCountry());
	    		stateList = getStateList(iCol5.getRegion());
	    		cityList = getCityList(iCol5.getLocationState());
	    	}
	    	if(iCol5.getNearestCity()!=null && !iCol5.getNearestCity().equals(""))
	    		cityName = getOtherBankProxyManager().getCityName(iCol5.getNearestCity());
	    	
	    	if(iCol5.getLocationState()!=null && !iCol5.getLocationState().equals(""))
	    		stateName = getOtherBankProxyManager().getStateName(iCol5.getLocationState());
	    	
	    	if(iCol5.getRegion()!=null && !iCol5.getRegion().equals(""))
	    		regionName = getOtherBankProxyManager().getRegionName(iCol5.getRegion());
	    	
	    	if(iCol5.getCountry()!=null && !iCol5.getCountry().equals(""))
	    		countryName = getOtherBankProxyManager().getCountryName(iCol5.getCountry());
	    	
	    	valuationAgencyName = valuationProxy.getValuationAgencyName(iCol5.getValuatorCompany());
		
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
		
		if(itrxValue.getCollateral() instanceof  IKeymanInsurance){
			IKeymanInsurance iCol6 = (IKeymanInsurance) itrxValue.getStagingCollateral();
		
			if(iCol6.getInsurerName()!=null && !"".equals(iCol6.getInsurerName().trim())){
				
				result.put("InsurerName",getInsurerNameFromCode(iCol6.getInsurerName()));
			}
		
		}
		
		HashMap orgMap = new HashMap();
		try {
			MISecurityUIHelper helper = new MISecurityUIHelper();
			ISystemBankBranch[] branch = CollateralDAOFactory.getDAO().getListAllSystemBankBranch(itrxValue.getCollateral().getCollateralLocation());
			
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

	    result.put("transactionHistoryList", transactionHistoryList);
	    
		result.put("orgList", getListAllSystemBankBranch(itrxValue.getStagingCollateral().getCollateralLocation()));
		
		result.put("valuationAgencyList",getValuationAgencyList(valuationProxy));
		
		result.put("valuationAgencyName", valuationAgencyName);
		result.put("cityName",cityName);
    	result.put("stateName",stateName);
    	result.put("regionName",regionName);
    	result.put("countryName",countryName);
    	
    	result.put("countryList",getCountryList());
    	result.put("regionList",regionList);
    	result.put("stateList",stateList);
    	result.put("cityList",cityList);
    	result.put("currencyList", getCurrencyList());
	
		if(exchangeRate!= null)
		{
		result.put("exchangeRate", exchangeRate.toString());
		}
		else
		{
			result.put("exchangeRate", exchangeRate);
		}
		
		SecurityDaoImpl securityDaoImpl = new SecurityDaoImpl();
		try {
		String migratedFlag = "N";	
		boolean status = false;	
		 status = securityDaoImpl.getSecurityMigreted("CMS_SECURITY", itrxValue.getCollateral().getCollateralID());
		
		if(status)
		{
			migratedFlag= "Y";
		}
		result.put("migratedFlag", migratedFlag);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		result.put("frame", frame);
		result.put("docCodeWithStocks",docCodeWithStocks);
		result.put("trxID", trxID);
		result.put("userName", userName);
		result.put("collateralID", idStrVal);
		result.put("from", map.get("from"));
		result.put("branchList", getBranchList());
		result.put("collateralList", collateralList);
		result.put("systemBankBranch", systemBankBranch);
		result.put("countryNme", countryNme);
		result.put("bankList",getBankList());
		result.put("bankListMap", getBankListHashMap());
		result.put("insuranceList", insuranceList);
		result.put("componentList", componentList);
		result.put("insuranceCoverageList", insuranceCoverageList);
	
		ICMSCustomer custOBNew = (ICMSCustomer) map.get(IGlobalConstant.GLOBAL_CUSTOMER_OBJ);
		if (custOBNew == null)
		{
		ICMSCustomer custOB = null;
		ICustomerDAO customerDAO1 = CustomerDAOFactory.getDAO();
		if(null != LegalID && LegalID != "")
		{
		List customerList = customerDAO1.searchCustomerByCIFNumber(LegalID);
		 custOB = (ICMSCustomer)  customerList.get(0);
		}
	        if (custOB != null) {
                
            	result.put(IGlobalConstant.GLOBAL_CUSTOMER_OBJ, custOB);
            }
		}
		
		temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		return temp;
	}
	private ICMSCustomer getCustomer(String sub_profile_id) {
        ICMSCustomer custOB = null;
        ICustomerProxy custproxy = CustomerProxyFactory.getProxy();
        try {
            custOB = custproxy.getCustomer(Long.parseLong(sub_profile_id));
            if (custOB != null) {
                return custOB;
            }
        } catch (Exception e) {

        }
        return custOB;

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
	
	private List getBankList() {
		List lbValList = new ArrayList();
		try {
			
			ISystemBankProxyManager systemBankProxy = (ISystemBankProxyManager) BeanHouse.get("systemBankProxy");
			List systemBankList = systemBankProxy.getAllActual();
			
			for (int i = 0; i < systemBankList.size(); i++) {
				ISystemBank systemBank = (ISystemBank) systemBankList.get(i);
				String id = Long.toString(systemBank.getId());
				String val = systemBank.getSystemBankName();
				LabelValueBean lvBean = new LabelValueBean(val, id);
				lbValList.add(lvBean);
			}
			
			IOtherBankProxyManager otherBankProxyManager = (IOtherBankProxyManager) BeanHouse.get("otherBankProxyManager");
			SearchResult sr = otherBankProxyManager.getOtherBankList("", "");
			List otherBankList = (List) sr.getResultList();
			for (int i = 0; i < otherBankList.size(); i++) {
				IOtherBank otherBank = (IOtherBank) otherBankList.get(i);
				String id = Long.toString(otherBank.getId());
				String val = otherBank.getOtherBankName();
				LabelValueBean lvBean = new LabelValueBean(val, id);
				lbValList.add(lvBean);
			}
		} catch (Exception ex) {
		}
		return (List) CommonUtil.sortDropdown(lbValList);
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
	
	private List getBranchList() {
		List lbValList = new ArrayList();
		try {
			
			 SearchResult searchResult = getSystemBankBranchProxy().getAllActualBranch();
			 List idList = (List) searchResult.getResultList();
			
			for (int i = 0; i < idList.size(); i++) {
				ISystemBankBranch bankBranch = (ISystemBankBranch) idList.get(i);
				String id = Long.toString(bankBranch.getId());
				String val = bankBranch.getSystemBankBranchName();
				LabelValueBean lvBean = new LabelValueBean(val, id);
				lbValList.add(lvBean);
			}
		} catch (Exception ex) {
		}
		return (List) CommonUtil.sortDropdown(lbValList);
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

private List getCountryList() {
		List lbValList = new ArrayList();
		try {
			getStockFeedProxy();
			getBhavCopyProxy();
			List idList = (List) getOtherBankProxyManager().getCountryList();
			
		
			for (int i = 0; i < idList.size(); i++) {
				ICountry country = (ICountry)idList.get(i);
				if( country.getStatus().equals("ACTIVE")) {
					String id = Long.toString(country.getIdCountry());
					String val = country.getCountryName();
					LabelValueBean lvBean = new LabelValueBean(val, id);
					lbValList.add(lvBean);
				}
			}
		} catch (Exception ex) {
		}
		return CommonUtil.sortDropdown(lbValList);
	}
    
    private List getRegionList(String countryId) {
		List lbValList = new ArrayList();
		try {
			List idList = (List) getOtherBankProxyManager().getRegionList(countryId);				
		
			for (int i = 0; i < idList.size(); i++) {
				IRegion region = (IRegion)idList.get(i);
				if( region.getStatus().equals("ACTIVE")) {
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
	
	private List getStateList(String regionId) {
		List lbValList = new ArrayList();
		try {
			List idList = (List) getOtherBankProxyManager().getStateList(regionId);				
		
			for (int i = 0; i < idList.size(); i++) {
				IState state = (IState)idList.get(i);
				if( state.getStatus().equals("ACTIVE")) {
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
	
	private List getCityList(String stateId) {
		List lbValList = new ArrayList();
		try {
			List idList = (List) getOtherBankProxyManager().getCityList(stateId);				
		
			for (int i = 0; i < idList.size(); i++) {
				ICity city = (ICity)idList.get(i);
				if( city.getStatus().equals("ACTIVE")) {
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
	
	
	private List getValuationAgencyList(IValuationAgencyProxyManager valuationProxy) {
		List lbValList = new ArrayList();
		try {
			
			ArrayList valuationAgencyList = new ArrayList();
			valuationAgencyList = (ArrayList) valuationProxy.getAllActual();
			
			for (int i = 0; i < valuationAgencyList.size(); i++) {
				IValuationAgency valuationAgency = (IValuationAgency) valuationAgencyList.get(i);
				String id = Long.toString(valuationAgency.getId());
				String val = valuationAgency.getValuationAgencyName();
				LabelValueBean lvBean = new LabelValueBean(val, id);
				lbValList.add(lvBean);
			}
		} catch (Exception ex) {
		}
		return (List) CommonUtil.sortDropdown(lbValList);
	}
	
	
	
	
			
			private String getInsurerNameFromCode(String insurerName) {
				List lbValList = new ArrayList();
				String insurerNam= new String();
				try {
					
				
					
				
					 SearchResult searchResult =  getOtherBankProxyManager().getInsurerNameFromCode(insurerName);
					 List insurerNamValue = (List) searchResult.getResultList();
					 
					 for (int i = 0; i < insurerNamValue.size(); i++) {
						 IInsuranceCoverage insurernam = (IInsuranceCoverage) insurerNamValue.get(i);
							String id = insurernam.getCompanyName();
							String val =null;
							LabelValueBean lvBean = new LabelValueBean(val, id);
							lbValList.add(lvBean);
							insurerNam=id;
						}
					
				} catch (Exception ex) {
				}
				
				return insurerNam;
				
				
			
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
