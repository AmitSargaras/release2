//GENERATED FILE... ANYMODIFICATION WILL BE LOST. ASK SATHISH FOR ANY CLARIFICATION
package com.integrosys.cms.ui.collateral;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.util.LabelValueBean;

import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.collateral.bus.CollateralDAOFactory;
import com.integrosys.cms.app.collateral.bus.ICollateral;
import com.integrosys.cms.app.collateral.bus.ILimitCharge;
import com.integrosys.cms.app.collateral.bus.type.cash.ICashCollateral;
import com.integrosys.cms.app.collateral.bus.type.cash.ICashDeposit;
import com.integrosys.cms.app.collateral.bus.type.cash.subtype.cashfd.OBCashFd;
import com.integrosys.cms.app.collateral.bus.type.marketable.IMarketableCollateral;
import com.integrosys.cms.app.collateral.bus.type.marketable.IMarketableEquity;
import com.integrosys.cms.app.collateral.bus.type.others.IOthersCollateral;
import com.integrosys.cms.app.collateral.bus.type.property.IPropertyCollateral;
import com.integrosys.cms.app.collateral.trx.ICollateralTrxValue;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.feed.proxy.stock.IStockFeedProxy;
import com.integrosys.cms.app.otherbank.proxy.IOtherBankProxyManager;
import com.integrosys.cms.app.valuationAgency.bus.IValuationAgency;
import com.integrosys.cms.app.valuationAgency.proxy.IValuationAgencyProxyManager;
import com.integrosys.cms.app.feed.bus.forex.ForexFeedGroupException;
import com.integrosys.cms.app.feed.bus.mutualfunds.OBMutualFundsFeedEntry;
import com.integrosys.cms.app.feed.bus.stock.IStockFeedEntry;
import com.integrosys.cms.app.feed.bus.stock.StockFeedEntryException;
import com.integrosys.cms.app.feed.proxy.mutualfunds.IMutualFundsFeedProxy;
import com.integrosys.cms.app.feed.proxy.stock.IStockFeedProxy;
import com.integrosys.cms.ui.manualinput.CommonUtil;
import com.integrosys.cms.ui.manualinput.security.MISecurityUIHelper;


/**
 * @author $Author: hshii $<br>
 * @version $Revision: 1.4 $
 * @since $Date: 2005/08/12 02:42:59 $ Tag: $Name: $
 */

public class ProcessingCollateralCommand extends AbstractCommand {
	
	
	
	private IStockFeedProxy stockFeedProxy;
	
	public IStockFeedProxy getStockFeedProxy() {
		return (IStockFeedProxy)BeanHouse.get("stockFeedProxy");
	}
	
	public void setStockFeedProxy(IStockFeedProxy stockFeedProxy) {
		this.stockFeedProxy = stockFeedProxy;
	}

	private IOtherBankProxyManager otherBankProxyManager;
	
	public IOtherBankProxyManager getOtherBankProxyManager() {
		return otherBankProxyManager;
	}

	public void setOtherBankProxyManager(
			IOtherBankProxyManager otherBankProxyManager) {
		this.otherBankProxyManager = otherBankProxyManager;
	}
	
	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ "indexID", "java.lang.String", REQUEST_SCOPE },
				{ "serviceColObj", "com.integrosys.cms.app.collateral.trx.ICollateralTrxValue", SERVICE_SCOPE },
				{ "form.collateralObject", "com.integrosys.cms.app.collateral.bus.ICollateral", FORM_SCOPE },
				{ "trxID", "java.lang.String", REQUEST_SCOPE },
				{ "pdcEvent", "java.lang.String", REQUEST_SCOPE },
				{ "userName", "java.lang.String", REQUEST_SCOPE },
				{ "collateralID", "java.lang.String", REQUEST_SCOPE },
				{ "itemType", "java.lang.String", REQUEST_SCOPE },
				{ "schemeCode", "java.lang.String", REQUEST_SCOPE },
				{ "schemeName", "java.lang.String", REQUEST_SCOPE },
				{ "typeSheme", "java.lang.String", REQUEST_SCOPE },
				{ "startDate", "java.lang.String", REQUEST_SCOPE },
				{ "endDate", "java.lang.String", REQUEST_SCOPE },
				{ "fundsFeedEntry", "com.integrosys.cms.app.feed.bus.mutualfunds.OBMutualFundsFeedEntry", REQUEST_SCOPE },
				{ "stockFeedEntry", "com.integrosys.cms.app.feed.bus.stock.IStockFeedEntry", REQUEST_SCOPE },
				{ "stockExchange", "java.lang.String", REQUEST_SCOPE },
				{ "scriptCode", "java.lang.String", REQUEST_SCOPE },
				{ "event", "java.lang.String", REQUEST_SCOPE },
				{ "cashDepositID", "java.lang.String", REQUEST_SCOPE },
				{ "recpNo", "java.lang.String", REQUEST_SCOPE },				
				{ "indexID", "java.lang.String", REQUEST_SCOPE },
				{ "custname", "java.lang.String", REQUEST_SCOPE },
				 { "custname", "java.lang.String", SERVICE_SCOPE },
				 { "system", "java.util.List", SERVICE_SCOPE },
				 {"systemName", "java.lang.String", REQUEST_SCOPE},
				 {"systemId", "java.lang.String", REQUEST_SCOPE},
				// { "systemIdList", "java.util.List", SERVICE_SCOPE },
		});


	}

	public String[][] getResultDescriptor() {
		return (new String[][] { { "serviceColObj", "com.integrosys.cms.app.collateral.trx.ICollateralTrxValue",
				SERVICE_SCOPE },
				{"trxID", "java.lang.String", REQUEST_SCOPE },
				{ "userName", "java.lang.String", REQUEST_SCOPE },
				{ "collateralID", "java.lang.String", REQUEST_SCOPE },
				{ "pdcEvent", "java.lang.String", REQUEST_SCOPE },
				{ "schemeCode", "java.lang.String", REQUEST_SCOPE },
				{ "schemeName", "java.lang.String", REQUEST_SCOPE },
				{ "typeSheme", "java.lang.String", REQUEST_SCOPE },
				{ "startDate", "java.lang.String", REQUEST_SCOPE },
				{ "endDate", "java.lang.String", REQUEST_SCOPE },
				{ "fundsFeedEntry", "com.integrosys.cms.app.feed.bus.mutualfunds.OBMutualFundsFeedEntry", REQUEST_SCOPE },
				{ "stockFeedEntry", "com.integrosys.cms.app.feed.bus.stock.IStockFeedEntry", REQUEST_SCOPE },
				{ "stockExchange", "java.lang.String", REQUEST_SCOPE },
				{ "scriptCode", "java.lang.String", REQUEST_SCOPE },
				{ "event", "java.lang.String", REQUEST_SCOPE },
				{ "custname", "java.lang.String", REQUEST_SCOPE },
				{ "cashDepositID", "java.lang.String", REQUEST_SCOPE },
				 { "custname", "java.lang.String", SERVICE_SCOPE },
				 { "itemType", "java.lang.String", REQUEST_SCOPE },
				 { "system", "java.util.List", SERVICE_SCOPE },
				 { "systemIdList", "java.util.List", SERVICE_SCOPE },
				 { "systemIdList", "java.util.List", REQUEST_SCOPE },
				 { "indexID", "java.lang.String", REQUEST_SCOPE },
				 {"systemId", "java.lang.String", REQUEST_SCOPE},
				 { "systemIdList", "java.util.List", SERVICE_SCOPE },
				 { "line", "java.util.List", SERVICE_SCOPE },
				 { "facilityNameList", "java.util.List", SERVICE_SCOPE },
//				 { "facilityIdList", "java.util.List", SERVICE_SCOPE },
//				 { "facilityIdList", "java.util.List", REQUEST_SCOPE },			 
				// { "utilizedAmunt", "java.lang.String", REQUEST_SCOPE }
				 { "utilizedAmunt", "java.lang.String", SERVICE_SCOPE },
				 {"fdWebServiceFlag" ,"java.lang.String",REQUEST_SCOPE}				 
				});

	}

	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
		HashMap result = new HashMap();
		HashMap exceptionMap = new HashMap();
		HashMap temp = new HashMap();
		OBMutualFundsFeedEntry FundsFeedEntry = null;
		
		ICollateral iColObj = (ICollateral) map.get("form.collateralObject");
		IStockFeedEntry stockFeedEntry = null;
		String schemeCode = (String) map.get("schemeCode");
		String schemeName = (String) map.get("schemeName");
		String typeSheme = (String) map.get("typeSheme");
		String startDate = (String) map.get("startDate");
		String endDate = (String) map.get("endDate");
		ICollateralTrxValue itrxValue = (ICollateralTrxValue) map.get("serviceColObj");
		String strStockExchange= (String)map.get("stockExchange");
		String strScriptCode= (String) map.get("scriptCode");
		String event= (String) map.get("event");
		String cashDepositID=(String) map.get("cashDepositID");
		String recpNo=(String) map.get("recpNo");		
		result.put("cashDepositID",cashDepositID);
		String trxIDStr = (String) map.get("trxID");
		String pdcEvent = (String) map.get("pdcEvent");
		long index = 0L;
		if(map.get("indexID") != null && !map.get("indexID").equals(""))
		{
		 index = Long.parseLong((String) map.get("indexID"));
		}
		String subtypecode = iColObj.getCollateralSubType().getSubTypeCode();
		 String userName=(String)map.get("userName");
		 String idStrVal = (String) map.get("collateralID");
		if (subtypecode.equals(ICMSConstant.COLTYPE_CASH_HKDUSD)
				|| subtypecode.equals(ICMSConstant.COLTYPE_CASH_SAMECCY)) {
			DefaultLogger.debug(this, "entering cash deposit: cms security currency: " + iColObj.getCurrencyCode());
			ICashDeposit[] deposit = ((ICashCollateral) iColObj).getDepositInfo();
			if (deposit != null) {
				for (int i = 0; i < deposit.length; i++) {
					deposit[i].setDepositCcyCode(iColObj.getCurrencyCode());
					if (deposit[i].getDepositAmount() != null) {
						deposit[i].getDepositAmount().setCurrencyCode(iColObj.getCurrencyCode());
					}
				}
			}
			((ICashCollateral) iColObj).setDepositInfo(deposit);
		}
		else if (iColObj instanceof IMarketableCollateral) {
			IMarketableEquity[] equity = ((IMarketableCollateral) iColObj).getEquityList();
			if (equity != null) {
				for (int i = 0; i < equity.length; i++) {
					equity[i].setItemCurrencyCode(iColObj.getCurrencyCode());
				}
			}
			((IMarketableCollateral) iColObj).setEquityList(equity);
			/*
			 * } else if
			 * (subtypecode.equals(ICMSConstant.COLTYPE_ASSET_PDT_CHEQUE)) {
			 * IPostDatedCheque[] cheque = ((IAssetPostDatedCheque)
			 * iColObj).getPostDatedCheques(); if (cheque != null) { for (int i
			 * = 0; i < cheque.length; i++) {
			 * cheque[i].setChequeCcyCode(iColObj.getCurrencyCode()); } }
			 * ((IAssetPostDatedCheque) iColObj).setPostDatedCheques(cheque);
			 */
		}
		else if ((iColObj instanceof IPropertyCollateral) || (iColObj instanceof IOthersCollateral)
				|| subtypecode.equals(ICMSConstant.COLTYPE_ASSET_GENERAL_CHARGE)
				|| subtypecode.equals(ICMSConstant.COLTYPE_ASSET_SPEC_CHARGE_AIRCRAFT)
				|| subtypecode.equals(ICMSConstant.COLTYPE_ASSET_SPEC_CHARGE_OTHERS)
				|| subtypecode.equals(ICMSConstant.COLTYPE_ASSET_SPEC_CHARGE_PLANT)
				|| subtypecode.equals(ICMSConstant.COLTYPE_ASSET_SPEC_CHARGE_VEH)) {
			ILimitCharge[] charge = iColObj.getLimitCharges();
			if (charge != null) {
				for (int i = 0; i < charge.length; i++) {
					charge[i].setChargeCcyCode(iColObj.getCurrencyCode());
					charge[i].setPriorChargeCcyCode(iColObj.getCurrencyCode());
				}
			}
			iColObj.setLimitCharges(charge);
		}

		if (ICMSConstant.STATE_PENDING_PERFECTION.equals(itrxValue.getStatus())) {
            Map context = new HashMap();
            //Andy Wong: set CMV to staging if actual got value but staging blank, used for pre Stp valuation validation
                if(itrxValue.getCollateral()!=null && itrxValue.getCollateral().getCMV()!=null
                        && (itrxValue.getStagingCollateral().getCMV()==null || itrxValue.getStagingCollateral().getCMV().getAmount() <= 0)){
                itrxValue.getStagingCollateral().setCMV(itrxValue.getCollateral().getCMV());
            }
            context.put(CollateralStpValidator.COL_OB, itrxValue.getStagingCollateral());
            context.put(CollateralStpValidator.TRX_STATUS, itrxValue.getStatus());
            context.put(CollateralStpValidator.COL_TRX_VALUE, itrxValue);
            ActionErrors errors = CollateralStpValidateUtils.validateAndAccumulate(context);
			if (!errors.isEmpty()) {
				temp.put(MESSAGE_LIST, errors);
			}
		}

		String itemType = (String) map.get("itemType");
		DefaultLogger.debug(this, "<<<<<<<<<<<<< itemType: " + itemType);
		if (itemType != null) {
			result.put("itemType", itemType);
		}

		//*** added by sachin
		try {
			if(schemeCode!=null){
				//FundsFeedEntry = getMutualFundsFeedProxy().getIMutualFundsFeed(itrxValue.getCollateral().getSchemeCode());
				FundsFeedEntry = (OBMutualFundsFeedEntry)getMutualFundsFeedProxy().getIMutualFundsFeed(schemeCode);
			}
		} catch (ForexFeedGroupException e) {
			DefaultLogger.debug(this, "got exception in doExecute" + e);
			e.printStackTrace();
		} catch (Exception ex) {
			DefaultLogger.debug(this, "got exception in doExecute" + ex);
			ex.printStackTrace();
		}
		
			result.put("fundsFeedEntry", FundsFeedEntry);
			System.out.println("fundsFeedEntry in ProcessingCollateralCommand is "+FundsFeedEntry);

			
	
		//*************end by sachin
		
		/*********************************************/
		try {
			if(strStockExchange!=null && strScriptCode!=null)
			{
				stockFeedEntry = getStockFeedProxy().getStockFeedEntryStockExc(strStockExchange,strScriptCode);
			}
				DefaultLogger.debug(this, "In doExecute :" + stockFeedEntry);
		} catch (StockFeedEntryException e) {
			DefaultLogger.debug(this, "got exception in doExecute" + e);
			e.printStackTrace();
		} catch (Exception ex) {
			DefaultLogger.debug(this, "got exception in doExecute" + ex);
			ex.printStackTrace();
		}
		if(stockFeedEntry!= null)
		{
		result.put("stockFeedEntry", stockFeedEntry);
		}
		
		if(itrxValue != null)
		{
			
			long partyid =   itrxValue.getCollateral().getCollateralID() ;
			  String  partyName = getPartyNamebyCode(partyid);
			  result.put("custname", partyName);
		}
		
		if(itrxValue != null)
		{
			
			long partyid =   itrxValue.getCollateral().getCollateralID() ;
			  List  system = getSystemDetail(partyid);
			  result.put("system", getValuationAgencyList(system));
			  
			  List facilityNameList = getFacilityNameList(partyid);
			  result.put("facilityNameList", getValuationFacilityNameList(facilityNameList));
			//  result.put("facilityIdList", "");
			//  String indexID =(String) map.get("indexID");
			//  result.put("systemIdList", getValuationAgencyListByIndex(system,indexID));
			 /* long partyid1 =   itrxValue.getCollateral().getCollateralID() ;
			  List  system1 = getSystemDetail(partyid1);
			  
			//  result.put("system", getValuationAgencyList(system));
			  result.put("systemIdList", getValuationAgencyList(system));*/
		}
		
		if(itrxValue != null)
		{
			
			long partyid =   itrxValue.getCollateral().getCollateralID() ;
			  List  line = getLineDetail(partyid);
			  result.put("line", getLineList(line));
			  
			  
			  
			//  String indexID =(String) map.get("indexID");
			//  result.put("systemIdList", getValuationAgencyListByIndex(system,indexID));
			 /* long partyid1 =   itrxValue.getCollateral().getCollateralID() ;
			  List  system1 = getSystemDetail(partyid1);
			  
			//  result.put("system", getValuationAgencyList(system));
			  result.put("systemIdList", getValuationAgencyList(system));*/
		}
		String id =(String) map.get("systemName");
		if((id != null) && (id.equals("")))
		{
			result.put("systemIdList", getSystemNullList());
			
		}
		else
		{
			long partyid =   itrxValue.getCollateral().getCollateralID() ;
			  List  system = getSystemDetail(partyid);
			result.put("systemIdList", getSystemListByName(system,id));
			
		}
		if(iColObj instanceof OBCashFd)
		{
			
		//	DefaultLogger.debug(this, "In cashDepositID :" + cashDepositID);
		//	String receiptNo = CollateralDAOFactory.getDAO().getReceiptNoByDepositID(cashDepositID);
			String receiptNo =  recpNo;
			DefaultLogger.debug(this, "In receiptNo :" + receiptNo);
			double totalLienAmount = 0.0;
	        if(null != receiptNo && !("".equals(receiptNo))){
	         totalLienAmount = CollateralDAOFactory.getDAO().getAllTotalLienAmount(receiptNo);
	         DefaultLogger.debug(this, "In totalLienAmount :" + totalLienAmount);
	        }
	          result.put("utilizedAmunt", String.valueOf(totalLienAmount));
	          
	          //check whether Webservice is on in case of FD
	          String fdWebServiceFlag=CollateralDAOFactory.getDAO().getFdEnableFlag();
	          result.put("fdWebServiceFlag", fdWebServiceFlag);
		}
		result.put("stockExchange", strStockExchange);
		result.put("scriptCode", strScriptCode);
	//	result.put("custname", (String) map.get("custname"));
		result.put("serviceColObj", itrxValue);
		result.put("event", event);
		result.put("schemeCode", schemeCode);
		result.put("schemeName", schemeName);
		result.put("typeSheme", typeSheme);
		result.put("startDate", startDate);
		result.put("endDate", endDate);
		result.put("trxID", trxIDStr);
		result.put("pdcEvent", pdcEvent);
		result.put("userName", userName);
		result.put("systemId", (String) map.get("systemId"));
		result.put("collateralID", idStrVal);
		result.put("indexID", String.valueOf(index));
		temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);

		return temp;
	}
	
	private String getPartyNamebyCode(long partyCode) {
		String partyName = null;
		try {
				MISecurityUIHelper helper = new MISecurityUIHelper();
				partyName = CollateralDAOFactory.getDAO().getPartyNamebyCode(partyCode);
		}
		catch (Exception ex) {
		}
		return partyName;
	}
	
	private List getSystemDetail(long partyCode) {
		List system = new ArrayList();
		try {
				MISecurityUIHelper helper = new MISecurityUIHelper();
				system = CollateralDAOFactory.getDAO().getSystemList(partyCode);
		}
		catch (Exception ex) {
		}
		return system;
	}
	
	private List getFacilityNameList(long partyCode) {
		List system = new ArrayList();
		try {
				MISecurityUIHelper helper = new MISecurityUIHelper();
				system = CollateralDAOFactory.getDAO().getFacilityNameList(partyCode);
		}
		catch (Exception ex) {
		}
		return system;
	}
	
	private List getLineDetail(long partyCode) {
		List Line = new ArrayList();
		try {
				MISecurityUIHelper helper = new MISecurityUIHelper();
				Line = CollateralDAOFactory.getDAO().getLineList(partyCode);
		}
		catch (Exception ex) {
		}
		return Line;
	}
	
	private IMutualFundsFeedProxy mutualFundsFeedProxy;

	public IMutualFundsFeedProxy getMutualFundsFeedProxy() {
		return (IMutualFundsFeedProxy)BeanHouse.get("mutualFundsFeedProxy");
	}

	public void setMutualFundsFeedProxy(IMutualFundsFeedProxy mutualFundsFeedProxy) {
		this.mutualFundsFeedProxy = mutualFundsFeedProxy;
	}
	
	private List getValuationAgencyList(List valuationProxy) {
		List lbValList = new ArrayList();
		try {
			
			//ArrayList valuationAgencyList = new ArrayList();
			//valuationAgencyList = (ArrayList) valuationProxy.();
			String[] stringArray = new String[2];
			for (int i = 0; i < valuationProxy.size(); i++) {
				
				
				stringArray = (String[])valuationProxy.get(i);
				String id = stringArray[0] ;
				String val =  stringArray[1] ;
				LabelValueBean lvBean = new LabelValueBean(val, id);
				lbValList.add(lvBean);
			}
		} catch (Exception ex) {
		}
		return (List) CommonUtil.sortDropdown(lbValList);
	}
	
	private List getValuationFacilityNameList(List valuationProxy) {
		List lbValList = new ArrayList();
		try {
			
			//ArrayList valuationAgencyList = new ArrayList();
			//valuationAgencyList = (ArrayList) valuationProxy.();
			String[] stringArray = new String[2];
			for (int i = 0; i < valuationProxy.size(); i++) {
				
				
				stringArray = (String[])valuationProxy.get(i);
				String id = stringArray[0] ;
				String val =  stringArray[0] ;
				LabelValueBean lvBean = new LabelValueBean(val, id);
				lbValList.add(lvBean);
			}
		} catch (Exception ex) {
		}
		return (List) CommonUtil.sortDropdown(lbValList);
	}
	
	private List getLineList(List lineProxy) {
		List lbValList = new ArrayList();
		try {
			
			//ArrayList valuationAgencyList = new ArrayList();
			//valuationAgencyList = (ArrayList) valuationProxy.();
			String[] stringArray = new String[2];
			for (int i = 0; i < lineProxy.size(); i++) {
				
				
				stringArray = (String[])lineProxy.get(i);
				String id = stringArray[0] ;
				String val =  stringArray[1] ;
				LabelValueBean lvBean = new LabelValueBean(id, val);
				lbValList.add(lvBean);
			}
		} catch (Exception ex) {
		}
		return (List) CommonUtil.sortDropdown(lbValList);
	}
	
	private List getValuationAgencyListByIndex(List valuationProxy ,String index) {
		List lbValList = new ArrayList();
		try {
			
			//ArrayList valuationAgencyList = new ArrayList();
			//valuationAgencyList = (ArrayList) valuationProxy.();
			String[] stringArray = new String[2];
			for (int i = 0; i < valuationProxy.size(); i++) {
				
				if(i == Integer.parseInt(index))
				{
				stringArray = (String[])valuationProxy.get(i);
				String id = stringArray[0] ;
				String val =  stringArray[1] ;
				LabelValueBean lvBean = new LabelValueBean(val, id);
				lbValList.add(lvBean);
				}
			}
		} catch (Exception ex) {
		}
		return (List) CommonUtil.sortDropdown(lbValList);
	}
	
	
	private List getSystemNullList() {
		List lbValList = new ArrayList();
		try {			
				LabelValueBean lvBean = new LabelValueBean("","");
				lbValList.add(lvBean);
				lbValList.remove(0);
			
		} catch (Exception ex) {
		}
		return (List) CommonUtil.sortDropdown(lbValList);
	}
	
	private List getSystemListByName(List valuationProxy,String idName) {
		List lbValList = new ArrayList();
		try {
			
			//ArrayList valuationAgencyList = new ArrayList();
			//valuationAgencyList = (ArrayList) valuationProxy.();
			String[] stringArray = new String[2];
			for (int i = 0; i < valuationProxy.size(); i++) {
				
				
				stringArray = (String[])valuationProxy.get(i);
				if((idName != null) && !(idName.equals("") ))
				{
					if(idName.equals(stringArray[0]))
					{
						String id = stringArray[0] ;
						String val =  stringArray[1] ;
						LabelValueBean lvBean = new LabelValueBean(val, id);
						lbValList.add(lvBean);
					}
				}
			}
		} catch (Exception ex) {
		}
		return (List) CommonUtil.sortDropdown(lbValList);
	}
}
