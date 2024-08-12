//GENERATED FILE... ANYMODIFICATION WILL BE LOST. ASK SATHISH FOR ANY CLARIFICATION
package com.integrosys.cms.ui.collateral;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.util.LabelValueBean;

import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.collateral.bus.CollateralDAOFactory;
import com.integrosys.cms.app.collateral.bus.CollateralValuator;
import com.integrosys.cms.app.collateral.bus.ICollateral;
import com.integrosys.cms.app.collateral.bus.ILimitCharge;
import com.integrosys.cms.app.collateral.bus.type.cash.ICashCollateral;
import com.integrosys.cms.app.collateral.bus.type.cash.ICashDeposit;
import com.integrosys.cms.app.collateral.bus.type.marketable.IMarketableCollateral;
import com.integrosys.cms.app.collateral.bus.type.marketable.IMarketableEquity;
import com.integrosys.cms.app.collateral.bus.type.marketable.subtype.bondslocal.IBondsLocal;
import com.integrosys.cms.app.collateral.bus.type.marketable.subtype.mainindexlocal.IMainIndexLocal;
import com.integrosys.cms.app.collateral.bus.type.marketable.subtype.otherlistedlocal.IOtherListedLocal;
import com.integrosys.cms.app.collateral.bus.type.others.IOthersCollateral;
import com.integrosys.cms.app.collateral.bus.type.property.IPropertyCollateral;
import com.integrosys.cms.app.collateral.trx.ICollateralTrxValue;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.feed.proxy.stock.IStockFeedProxy;
import com.integrosys.cms.app.otherbank.proxy.IOtherBankProxyManager;
import com.integrosys.cms.app.valuationAgency.bus.IValuationAgency;
import com.integrosys.cms.app.valuationAgency.proxy.IValuationAgencyProxyManager;
import com.integrosys.cms.app.feed.bus.bond.BondFeedEntryException;
import com.integrosys.cms.app.feed.bus.bond.IBondFeedEntry;
import com.integrosys.cms.app.feed.bus.forex.ForexFeedGroupException;
import com.integrosys.cms.app.feed.bus.mutualfunds.IMutualFundsFeedEntry;
import com.integrosys.cms.app.feed.bus.mutualfunds.OBMutualFundsFeedEntry;
import com.integrosys.cms.app.feed.bus.stock.IStockFeedEntry;
import com.integrosys.cms.app.feed.bus.stock.StockFeedEntryException;
import com.integrosys.cms.app.feed.proxy.bond.IBondFeedProxy;
import com.integrosys.cms.app.feed.proxy.mutualfunds.IMutualFundsFeedProxy;
import com.integrosys.cms.app.feed.proxy.stock.IStockFeedProxy;
import com.integrosys.cms.ui.collateral.marketablesec.BaselCompliantList;
import com.integrosys.cms.ui.collateral.marketablesec.NomineeNameList;
import com.integrosys.cms.ui.collateral.marketablesec.SettleOrgList;
import com.integrosys.cms.ui.common.CommonCodeList;
import com.integrosys.cms.ui.common.CountryList;
import com.integrosys.cms.ui.common.CurrencyList;
import com.integrosys.cms.ui.manualinput.CommonUtil;
import com.integrosys.cms.ui.manualinput.security.MISecurityUIHelper;


/**
 * @author $Author: hshii $<br>
 * @version $Revision: 1.4 $
 * @since $Date: 2005/08/12 02:42:59 $ Tag: $Name: $
 */

public class EditProcessingCollateralCommand extends AbstractCommand {
	
	
	private IBondFeedProxy bondFeedProxy = (IBondFeedProxy) BeanHouse
	.get("bondFeedProxy");
private IStockFeedProxy stockFeedProxyNew = (IStockFeedProxy) BeanHouse
	.get("stockFeedProxy");
private IMutualFundsFeedProxy mutualFundsFeedProxy = (IMutualFundsFeedProxy) BeanHouse
	.get("mutualFundsFeedProxy");

public String[][] getParameterDescriptor() {
return (new String[][] {
		//{ "form.PortItemObject", "java.lang.Object", FORM_SCOPE },
		{ "event", "java.lang.String", REQUEST_SCOPE },
		{ "curr", "java.lang.String", SERVICE_SCOPE },
		{ "branchName", "java.lang.String", SERVICE_SCOPE },
		{ "secPriority", "java.lang.String", SERVICE_SCOPE },
		{ "BondList", "java.util.List", SERVICE_SCOPE },
		{ "StockList", "java.util.List", SERVICE_SCOPE },
		{ "FundList", "java.util.List", SERVICE_SCOPE },
		{ "indexID", "java.lang.String", REQUEST_SCOPE },
		{ "subtype", "java.lang.String", REQUEST_SCOPE },
		{ "fundsFeedEntry", "com.integrosys.cms.app.feed.bus.mutualfunds.OBMutualFundsFeedEntry", REQUEST_SCOPE },

		{
				"serviceColObj",
				"com.integrosys.cms.app.collateral.trx.ICollateralTrxValue",
				SERVICE_SCOPE },
		{ "schemeCode", "java.lang.String", REQUEST_SCOPE },
		{ "schemeName", "java.lang.String", REQUEST_SCOPE },
		{ "typeSheme", "java.lang.String", REQUEST_SCOPE },
		{ "startDate", "java.lang.String", REQUEST_SCOPE },
		{ "endDate", "java.lang.String", REQUEST_SCOPE },
		{ "stockExchange", "java.lang.String", REQUEST_SCOPE },
		{ "scriptCode", "java.lang.String", REQUEST_SCOPE }	,
		{ "bondCode", "java.lang.String", REQUEST_SCOPE },
			
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
		{
				"serviceColObj",
				"com.integrosys.cms.app.collateral.trx.ICollateralTrxValue",
				SERVICE_SCOPE },
		{ "subtype", "java.lang.String", REQUEST_SCOPE },
		{ "BondList", "java.util.List", SERVICE_SCOPE },
		{ "StockList", "java.util.List", SERVICE_SCOPE },
		{ "FundList", "java.util.List", SERVICE_SCOPE },
		{ "stockExchange", "java.lang.String", REQUEST_SCOPE },
		{ "scriptCode", "java.lang.String", REQUEST_SCOPE }	,
		{ "bondCode", "java.lang.String", REQUEST_SCOPE },
		{ "schemeName", "java.lang.String", REQUEST_SCOPE },
		{ "typeSheme", "java.lang.String", REQUEST_SCOPE },
		{ "startDate", "java.lang.String", REQUEST_SCOPE },
		{ "endDate", "java.lang.String", REQUEST_SCOPE },
		{ "fundsFeedEntry", "com.integrosys.cms.app.feed.bus.mutualfunds.OBMutualFundsFeedEntry", REQUEST_SCOPE },
		{ "schemeCode", "java.lang.String", REQUEST_SCOPE } });
}

/**
* This method does the Business operations with the HashMap and put the
* results back into the HashMap.Here reading for Company Borrower is done.
* 
* @param map
*            is of type HashMap
* @throws com.integrosys.base.uiinfra.exception.CommandProcessingException
*             on errors
* @throws com.integrosys.base.uiinfra.exception.CommandValidationException
*             on errors
* @return HashMap with the Result
*/
public HashMap doExecute(HashMap map) throws CommandProcessingException,
	CommandValidationException {

HashMap result = new HashMap();
HashMap exceptionMap = new HashMap();
HashMap temp = new HashMap();
/*IMarketableEquity iMarketEquity = (IMarketableEquity) map
		.get("form.PortItemObject");*/
String event=(String)map.get("event");
String schemeCode=(String)map.get("schemeCode");// mutual fund
String schemeName = (String) map.get("schemeName");
String typeSheme = (String) map.get("typeSheme");
String startDate = (String) map.get("startDate");
String endDate = (String) map.get("endDate");
String bondCode=(String)map.get("bondCode");
int index = Integer.parseInt((String) map.get("indexID"));
DefaultLogger.debug(this, "SubType:" + map.get("subtype"));
DefaultLogger.debug(this, "Index is:" + index);
ICollateralTrxValue itrxValue = (ICollateralTrxValue) map
		.get("serviceColObj");
result.put("subtype", map.get("subtype"));

result.put("bondCode", map.get("bondCode"));
result.put("schemeCode", map.get("schemeCode"));
result.put("schemeName", map.get("schemeName"));
result.put("typeSheme", map.get("typeSheme"));
result.put("endDate", map.get("endDate"));
result.put("startDate", map.get("startDate"));
result.put("stockExchange", map.get("stockExchange"));
result.put("scriptCode", map.get("scriptCode"));
if (itrxValue.getCollateral() instanceof IBondsLocal) {
	// IBondsLocal iCol = (IBondsLocal) itrxValue.getCollateral();

	IBondFeedEntry bondFeedEntry = null;

	List BondList = new ArrayList();
	BondList = (List) map.get("BondList");
	//bondCode = iMarketEquity.getStockCode();
	if("".equals(bondCode))
		bondCode=null;
	
	if (bondCode != null) {
		bondFeedEntry = bondFeedProxy.getBondFeedEntry(bondCode);
		if (bondFeedEntry != null) {
			BondList.add(bondFeedEntry);
		} else {
			// result.put("event",event);
			// resultMap.put("lienList", (List)map.get("lienList"));
			// resultMap.put("indexID", indexID);
			exceptionMap.put("stockCode", new ActionMessage(
					"label.bond.invalid.code"));
			// returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP,
			// resultMap);
			temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
			temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP,
					exceptionMap);
			// returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP,
			// exceptionMap);
			return temp;

		}
	}

	if (BondList != null) {
		result.put("BondList", BondList);
	}
} else if (itrxValue.getCollateral() instanceof IOtherListedLocal) {

	IStockFeedEntry stockFeedEntry = null;

	String strStockExchange = null;
	String strScriptCode = null;

	List StockList = new ArrayList();
	StockList = (List) map.get("StockList");
	 strScriptCode=(String)map.get("scriptCode");
	 strStockExchange=(String)map.get("stockExchange");
	/*strScriptCode = iMarketEquity.getIsinCode();
	strStockExchange = iMarketEquity.getStockExchange();*/
	if("".equals(strScriptCode))
		strScriptCode=null;
	if("".equals(strStockExchange))
		strStockExchange=null;

	if (strStockExchange != null && strScriptCode != null) {
		stockFeedEntry = stockFeedProxyNew.getStockFeedEntryStockExc(
				strStockExchange, strScriptCode);
		if (stockFeedEntry != null) {
			StockList.add(stockFeedEntry);
		} else {
			// result.put("event",event);
			// resultMap.put("lienList", (List)map.get("lienList"));
			// resultMap.put("indexID", indexID);
			exceptionMap.put("isinCode", new ActionMessage(
					"label.script.invalid.code", "Stock Code Invalid"));
			// returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP,
			// resultMap);
			temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
			temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP,
					exceptionMap);
			// returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP,
			// exceptionMap);
			return temp;

		}
	}

	if (StockList != null) {
		result.put("StockList", StockList);
	}
} else

if (itrxValue.getCollateral() instanceof IMainIndexLocal) { 

	IMutualFundsFeedEntry mutualFundsFeedEntry = null;

	String mutualCode = null;
	List FundList = new ArrayList();
	FundList = (List) map.get("FundList");
	//mutualCode = iMarketEquity.getStockCode();
	mutualCode = schemeCode;
	if("".equals(mutualCode))
		mutualCode=null;
	if (mutualCode != null) {
		mutualFundsFeedEntry = mutualFundsFeedProxy
				.getIMutualFundsFeed(mutualCode);

		if (mutualFundsFeedEntry != null) {
			FundList.add(mutualFundsFeedEntry);
		} else {
			// result.put("event",event);
			// resultMap.put("lienList", (List)map.get("lienList"));
			// resultMap.put("indexID", indexID);
			exceptionMap.put("stockCode", new ActionMessage(
					"label.scheme.invalid.code", "Stock Code Invalid"));
			// returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP,
			// resultMap);
			temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
			temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP,
					exceptionMap);
			// returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP,
			// exceptionMap);
			return temp;

		}
	}

	if (FundList != null) {
		result.put("FundList", FundList);
	}
	result.put("fundsFeedEntry", mutualFundsFeedEntry);

}


IMarketableCollateral iMarket = (IMarketableCollateral) itrxValue
		.getStagingCollateral();

String curr = (String) map.get("curr");
String branchName = (String) map.get("branchName");
String secPriority = (String) map.get("secPriority");
iMarket.setCurrencyCode(curr);
iMarket.setSecurityOrganization(branchName);
iMarket.setSecPriority(secPriority);

IMarketableEquity equityArray[] = iMarket.getEquityList();
//equityArray[index] = iMarketEquity;
// equityArray = updateItem(equityArray, index, iMarketEquity);

iMarket.setEquityList(equityArray);
itrxValue.setStagingCollateral(iMarket);



try {
	CollateralValuator valuator = new CollateralValuator();
	// valuator.setMarketableCMVFSV(iMarket);
	valuator.setCollateralCMVFSV(iMarket);
} catch (Exception e) {
	// do nothing.
}

result.put("serviceColObj", itrxValue);
result.put("schemeCode", map.get("schemeCode"));
result.put("schemeName", schemeName);
result.put("typeSheme", typeSheme);
result.put("startDate", startDate);
result.put("endDate", endDate);
DefaultLogger.debug(this, "After Addition1:" + itrxValue);

temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
return temp;
}

private IMarketableEquity[] updateItem(IMarketableEquity temp[],
	long itemRef, IMarketableEquity charge) {

for (int i = 0; i < temp.length; i++) {
	DefaultLogger.debug(this, "itemRef: " + itemRef + "\tid: "
			+ temp[i].getRefID() + "\tequity id:" + charge.getRefID());
	if (temp[i].getRefID() == itemRef) {
		temp[i] = charge;
		break;
	} else {
		continue;
	}
}
return temp;
}

}
