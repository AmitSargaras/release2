/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/collateral/marketablesec/PreparePortItemCommand.java,v 1.18 2004/06/04 05:19:57 hltan Exp $
 */

package com.integrosys.cms.ui.collateral.marketablesec;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.collateral.bus.ICollateral;
import com.integrosys.cms.app.collateral.bus.type.marketable.IMarketableEquity;
import com.integrosys.cms.app.collateral.trx.ICollateralTrxValue;
import com.integrosys.cms.app.common.constant.ICMSConstant;

import com.integrosys.cms.app.feed.bus.bond.BondFeedEntryException;
import com.integrosys.cms.app.feed.bus.bond.IBondFeedEntry;
import com.integrosys.cms.app.feed.proxy.bond.IBondFeedProxy;

import com.integrosys.cms.app.feed.bus.mutualfunds.IMutualFundsFeedEntry;
import com.integrosys.cms.app.feed.bus.mutualfunds.OBMutualFundsFeedEntry;
import com.integrosys.cms.app.feed.bus.stock.IStockFeedEntry;
import com.integrosys.cms.app.feed.bus.stock.StockFeedEntryException;
import com.integrosys.cms.app.feed.proxy.mutualfunds.IMutualFundsFeedProxy;
import com.integrosys.cms.app.feed.proxy.stock.IStockFeedProxy;

import com.integrosys.cms.ui.collateral.ExchangeControlList;
import com.integrosys.cms.ui.collateral.marketablesec.linedetail.IMarketableEquityLineDetailConstants;
import com.integrosys.cms.ui.common.CommonCodeList;
import com.integrosys.cms.ui.common.CountryList;
import com.integrosys.cms.ui.common.CurrencyList;

/**
 * @author $Author: hltan $<br>
 * @version $Revision: 1.18 $
 * @since $Date: 2004/06/04 05:19:57 $
 * Tag: $Name:  $
 */

/**
 * Created by IntelliJ IDEA. User: ssathish Date: Jul 2, 2003 Time: 1:26:03 PM
 * To change this template use Options | File Templates.
 */
public class PreparePortItemCommand extends AbstractCommand {

	
	private IBondFeedProxy bondFeedProxy = (IBondFeedProxy)BeanHouse.get("bondFeedProxy");
	
	private IMutualFundsFeedProxy mutualFundsFeedProxy = (IMutualFundsFeedProxy)BeanHouse.get("mutualFundsFeedProxy");

	
	
	private IStockFeedProxy stockFeedProxy = (IStockFeedProxy)BeanHouse.get("stockFeedProxy");
	
	

	
	//private IBondFeedProxy bondFeedProxy = (IBondFeedProxy)BeanHouse.get("bondFeedProxy");

	public String[][] getParameterDescriptor() {
		return (new String[][] { { "subtype", "java.lang.String", ICommonEventConstant.REQUEST_SCOPE },
				{ "curr", "java.lang.String", SERVICE_SCOPE },
				{ "branchName", "java.lang.String", SERVICE_SCOPE },
				{ "secPriority", "java.lang.String", SERVICE_SCOPE },
				{ "serviceColObj", "com.integrosys.cms.app.collateral.trx.ICollateralTrxValue", SERVICE_SCOPE },
				{ "bondCode", "java.lang.String", REQUEST_SCOPE },
				{ "serviceColObj", "com.integrosys.cms.app.collateral.trx.ICollateralTrxValue", SERVICE_SCOPE },
				{ "stockFeedEntry", "com.integrosys.cms.app.feed.bus.stock.IStockFeedEntry", REQUEST_SCOPE },
				{ "fundsFeedEntry", "com.integrosys.cms.app.feed.bus.mutualfunds.OBMutualFundsFeedEntry", REQUEST_SCOPE },
				{ "stockExchange", "java.lang.String", REQUEST_SCOPE },
				{ "scriptCode", "java.lang.String", REQUEST_SCOPE }	,
				{ "schemeCode", "java.lang.String", REQUEST_SCOPE },
				{ "BondList", "java.util.List", SERVICE_SCOPE },
				{ "StockList", "java.util.List", SERVICE_SCOPE },
				{ "FundList", "java.util.List", SERVICE_SCOPE },
				{ "event", "java.lang.String", REQUEST_SCOPE },
				{ IMarketableEquityLineDetailConstants.SESSION_EQUITY_EVENT, String.class.getName(), SERVICE_SCOPE },
				{ "session.PortItemObject", "java.lang.Object", SERVICE_SCOPE }
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
		return (new String[][] { { "countryLabels", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },
				{ "countryValues", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },
				{ "curr", "java.lang.String", SERVICE_SCOPE },
				{ "branchName", "java.lang.String", SERVICE_SCOPE },
				{ "secPriority", "java.lang.String", SERVICE_SCOPE },
				{ "equityTypeID", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },
				{ "equityTypeValue", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },
				{ "settleOrgID", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },
				{ "settleOrgValue", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },
				{ "bondTypeID", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },
				{ "bondTypeValue", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },
				{ "nomineeNameID", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },
				{ "nomineeNameValue", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },
				{ "ExchangeControlID", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },
				{ "ExchangeControlValue", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },
				{ "currencyCode", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },
				{ "baselCompliantID", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },
				{ "baselCompliantValue", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },
				{ "secCustodianList", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },
				{ "secCustodianID", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },

				{ "serviceColObj", "com.integrosys.cms.app.collateral.trx.ICollateralTrxValue", SERVICE_SCOPE }, 
				{ "bondFeedEntry", "com.integrosys.cms.app.feed.bus.bond.IBondFeedEntry", REQUEST_SCOPE },
				{ "fundsFeedEntry", "com.integrosys.cms.app.feed.bus.mutualfunds.OBMutualFundsFeedEntry", REQUEST_SCOPE },

				{ "serviceColObj", "com.integrosys.cms.app.collateral.trx.ICollateralTrxValue", SERVICE_SCOPE },
				{ "stockFeedEntry", "com.integrosys.cms.app.feed.bus.stock.IStockFeedEntry", REQUEST_SCOPE },
				{ "stockExchange", "java.lang.String", REQUEST_SCOPE },
				{ "scriptCode", "java.lang.String", REQUEST_SCOPE },
				{ "schemeCode", "java.lang.String", REQUEST_SCOPE },
				{ "BondList", "java.util.List", SERVICE_SCOPE },
				{ "StockList", "java.util.List", SERVICE_SCOPE },
				{ "FundList", "java.util.List", SERVICE_SCOPE },
				{ "event", "java.lang.String", REQUEST_SCOPE },
				{ IMarketableEquityLineDetailConstants.SESSION_EQUITY_EVENT, String.class.getName(), SERVICE_SCOPE },
				{ "session.PortItemObject", "java.lang.Object", SERVICE_SCOPE },
				{ "form.PortItemObject", "java.lang.Object", FORM_SCOPE },
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
		IStockFeedEntry stockFeedEntry = null;
		String strStockExchange= (String)map.get("stockExchange");
		String strScriptCode= (String) map.get("scriptCode");
		OBMutualFundsFeedEntry FundsFeedEntry = null;
		IBondFeedEntry bondFeedEntry = null;
		String bondCode = (String) map.get("bondCode");
		String strSchemeCode= (String) map.get("schemeCode");
		
		CountryList list = CountryList.getInstance();
		Collection listID = list.getCountryLabels();
		Collection listValues = list.getCountryValues();
		result.put("countryLabels", listID);
		result.put("countryValues", listValues);
		
		IMarketableEquity marketableEquity = (IMarketableEquity) map.get("session.PortItemObject");

		String subtype = (String) map.get("subtype");

		if (subtype.equals("MarksecBill")) {
			com.integrosys.cms.ui.collateral.marketablesec.marksecbill.EquityTypeList equity = com.integrosys.cms.ui.collateral.marketablesec.marksecbill.EquityTypeList
					.getInstance();
			listID = equity.getEquityTypeID();
			listValues = equity.getEquityTypeValue();
		}
		else if (subtype.equals("MarksecBondForeign")) {
			listID = null;
			listValues = null;
		}
		else if (subtype.equals("MarksecBondLocal")) {
			listID = null;
			listValues = null;
		}
		else if (subtype.equals("MarksecCustSec")) {
			com.integrosys.cms.ui.collateral.marketablesec.markseccustsec.EquityTypeList equity = com.integrosys.cms.ui.collateral.marketablesec.markseccustsec.EquityTypeList
					.getInstance();
			listID = equity.getEquityTypeID();
			listValues = equity.getEquityTypeValue();
		}
		else if (subtype.equals("MarksecGovtForeignDiff")) {
			com.integrosys.cms.ui.collateral.marketablesec.marksecgovtforeigndiff.EquityTypeList equity = com.integrosys.cms.ui.collateral.marketablesec.marksecgovtforeigndiff.EquityTypeList
					.getInstance();
			listID = equity.getEquityTypeID();
			listValues = equity.getEquityTypeValue();
		}
		else if (subtype.equals("MarksecGovtForeignSame")) {
			com.integrosys.cms.ui.collateral.marketablesec.marksecgovtforeignsame.EquityTypeList equity = com.integrosys.cms.ui.collateral.marketablesec.marksecgovtforeignsame.EquityTypeList
					.getInstance();
			listID = equity.getEquityTypeID();
			listValues = equity.getEquityTypeValue();
		}
		else if (subtype.equals("MarksecMainForeign")) {
			com.integrosys.cms.ui.collateral.marketablesec.marksecmainforeign.EquityTypeList equity = com.integrosys.cms.ui.collateral.marketablesec.marksecmainforeign.EquityTypeList
					.getInstance();
			listID = equity.getEquityTypeID();
			listValues = equity.getEquityTypeValue();
		}
		else if (subtype.equals("MarksecMainLocal")) {
			com.integrosys.cms.ui.collateral.marketablesec.marksecmainlocal.EquityTypeList equity = com.integrosys.cms.ui.collateral.marketablesec.marksecmainlocal.EquityTypeList
					.getInstance();
			listID = equity.getEquityTypeID();
			listValues = equity.getEquityTypeValue();
		}
		else if (subtype.equals("MarksecNonListedLocal")) {
			com.integrosys.cms.ui.collateral.marketablesec.marksecnonlistedlocal.EquityTypeList equity = com.integrosys.cms.ui.collateral.marketablesec.marksecnonlistedlocal.EquityTypeList
					.getInstance();
			listID = equity.getEquityTypeID();
			listValues = equity.getEquityTypeValue();
		}
		else if (subtype.equals("MarksecOtherListedForeign")) {
			com.integrosys.cms.ui.collateral.marketablesec.marksecotherlistedforeign.EquityTypeList equity = com.integrosys.cms.ui.collateral.marketablesec.marksecotherlistedforeign.EquityTypeList
					.getInstance();
			listID = equity.getEquityTypeID();
			listValues = equity.getEquityTypeValue();
		}
		else if (subtype.equals("MarksecOtherListedLocal")) {
			com.integrosys.cms.ui.collateral.marketablesec.marksecotherlistedlocal.EquityTypeList equity = com.integrosys.cms.ui.collateral.marketablesec.marksecotherlistedlocal.EquityTypeList
					.getInstance();
			listID = equity.getEquityTypeID();
			listValues = equity.getEquityTypeValue();
		}
		else if (subtype.equals("MarksecSCBSec")) {
			com.integrosys.cms.ui.collateral.marketablesec.marksecscbsec.EquityTypeList equity = com.integrosys.cms.ui.collateral.marketablesec.marksecscbsec.EquityTypeList
					.getInstance();
			listID = equity.getEquityTypeID();
			listValues = equity.getEquityTypeValue();
		}

		result.put("equityTypeID", listID);
		result.put("equityTypeValue", listValues);

		if (subtype.equals("MarksecBondForeign") || subtype.equals("MarksecBondLocal")) {
			SettleOrgList settleList = SettleOrgList.getInstance();
			result.put("settleOrgID", settleList.getSettleOrgID());
			result.put("settleOrgValue", settleList.getSettleOrgValue());

			if (subtype.equals("MarksecBondForeign")) {
				com.integrosys.cms.ui.collateral.marketablesec.marksecbondforeign.BondTypeList bondTypeList = com.integrosys.cms.ui.collateral.marketablesec.marksecbondforeign.BondTypeList
						.getInstance();
				result.put("bondTypeID", bondTypeList.getBondTypeID());
				result.put("bondTypeValue", bondTypeList.getBondTypeValue());
			}
			else {
				com.integrosys.cms.ui.collateral.marketablesec.marksecbondlocal.BondTypeList bondTypeList = com.integrosys.cms.ui.collateral.marketablesec.marksecbondlocal.BondTypeList
						.getInstance();
				result.put("bondTypeID", bondTypeList.getBondTypeID());
				result.put("bondTypeValue", bondTypeList.getBondTypeValue());
			}
		}
		//************* Code added for Bond Marketable Security by Sachin Patil ******
		try {
			List BondList = new ArrayList() ;
			BondList = (List) map.get("BondList");
			if(bondCode !=null)
			{
				bondFeedEntry = bondFeedProxy.getBondFeedEntry(bondCode);
				if(bondFeedEntry != null)
				{
				BondList.add(bondFeedEntry);
				}
			}
				DefaultLogger.debug(this, "In doExecute :" + bondFeedEntry);
		} catch (BondFeedEntryException e) {
			DefaultLogger.debug(this, "got exception in doExecute" + e);
			e.printStackTrace();
		} catch (Exception ex) {
			DefaultLogger.debug(this, "got exception in doExecute" + ex);
			ex.printStackTrace();
		}
		if(bondFeedEntry!= null)
		{
			result.put("bondFeedEntry", bondFeedEntry);
		}
		
		
		NomineeNameList nameList = NomineeNameList.getInstance();
		result.put("nomineeNameID", nameList.getNomineeNameID());
		result.put("nomineeNameValue", nameList.getNomineeNameValue());

		ExchangeControlList list1 = ExchangeControlList.getInstance();
		result.put("ExchangeControlID", list1.getExchangeControlID());
		result.put("ExchangeControlValue", list1.getExchangecontrolValue());

		BaselCompliantList basel = BaselCompliantList.getInstance();
		result.put("baselCompliantID", basel.getBaselCompliantID());
		result.put("baselCompliantValue", basel.getBaselCompliantValue());

		Collection currency = CurrencyList.getInstance().getCountryValues();
		result.put("currencyCode", currency);

		// get the custodian list
		// CommonCodeList commonCode =
		// CommonCodeList.getInstance(CategoryCodeConstant.SEC_CUSTODIAN);
		// result.put ( "secCustodianList", commonCode.getCommonCodeLabels() );
		// result.put ( "secCustodianID", commonCode.getCommonCodeValues() );

		ICollateralTrxValue colTrxValue = (ICollateralTrxValue) map.get("serviceColObj");
		ICollateral col = (colTrxValue.getStagingCollateral() == null) ? colTrxValue.getCollateral() : colTrxValue
				.getStagingCollateral();
		String curr="",
		       branchName="",
		       secPriority="";
        if(null!=col){
        	 curr=col.getCurrencyCode();
        	 if("".equals(curr))
        		 curr=(String)map.get("curr");
        	 branchName=col.getSecurityOrganization();
        	 if("".equals(branchName))
        		 branchName=(String)map.get("branchName");
        	 secPriority=col.getSecPriority();
        	 if("".equals(secPriority))
        		 secPriority=(String)map.get("secPriority");
        	result.put("secPriority",secPriority);	
        	result.put("branchName",branchName);	
        	result.put("curr",curr);	
        }
		CommonCodeList commonCode = CommonCodeList.getInstance(col.getCollateralLocation(),
				ICMSConstant.CATEGORY_CODE_BKGLOC, true);
		result.put("secCustodianID", commonCode.getCommonCodeValues());
		result.put("secCustodianList", commonCode.getCommonCodeLabels());

		/*********************************************/
		try {
			List StockList = new ArrayList() ;
			StockList = (List) map.get("StockList");
			if(strStockExchange!=null && strScriptCode!=null)
			{
				stockFeedEntry = stockFeedProxy.getStockFeedEntryStockExc(strStockExchange,strScriptCode);
				if(stockFeedEntry != null)
				{
				StockList.add(stockFeedEntry);
				}
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
		IMutualFundsFeedEntry mutualFundsFeedEntry = null;	
		// added by sachin
		try {
			List FundList = new ArrayList() ;
			FundList = (List) map.get("FundList");
				
				if(strSchemeCode !=null)
				{
					mutualFundsFeedEntry = mutualFundsFeedProxy.getIMutualFundsFeed(strSchemeCode);					
					
					if(mutualFundsFeedEntry != null)
					{
						FundList.add(mutualFundsFeedEntry);
					}
				}
			
				DefaultLogger.debug(this, "In doExecute :" + mutualFundsFeedEntry);
		} catch (StockFeedEntryException e) {
			DefaultLogger.debug(this, "got exception in doExecute" + e);
			e.printStackTrace();
		} catch (Exception ex) {
			DefaultLogger.debug(this, "got exception in doExecute" + ex);
			ex.printStackTrace();
		}
		if(mutualFundsFeedEntry!= null)
		{
			result.put("fundsFeedEntry", mutualFundsFeedEntry);
		
		}else {
			result.put("fundsFeedEntry", map.get("fundsFeedEntry"));

		}
		System.out.println("fundsFeedEntry in PreparePortItem is "+map.get("fundsFeedEntry"));

		
		
		
		
		
		
		String event = (String) map.get("event");
		String sessionEquityEvent = (String) map.get(IMarketableEquityLineDetailConstants.SESSION_EQUITY_EVENT);
		result.put("event", event);
		if(StringUtils.isBlank(sessionEquityEvent)) {
			result.put(IMarketableEquityLineDetailConstants.SESSION_EQUITY_EVENT, event);
		}
		result.put("form.PortItemObject", marketableEquity);
		
		result.put("stockExchange", strStockExchange);
		result.put("scriptCode", strScriptCode);
		result.put("schemeCode", strSchemeCode);
		result.put("subtype", subtype);
		temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		return temp;
	}
	
}
