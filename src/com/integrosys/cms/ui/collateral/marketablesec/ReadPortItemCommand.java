/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/collateral/marketablesec/ReadPortItemCommand.java,v 1.6 2003/09/09 04:19:26 hshii Exp $
 */

package com.integrosys.cms.ui.collateral.marketablesec;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.collateral.bus.type.marketable.IMarketableCollateral;
import com.integrosys.cms.app.collateral.bus.type.marketable.IMarketableEquity;
import com.integrosys.cms.app.collateral.bus.type.marketable.IMarketableEquityDetail;
import com.integrosys.cms.app.collateral.bus.type.marketable.linedetail.IMarketableEquityLineDetail;
import com.integrosys.cms.app.collateral.trx.ICollateralTrxValue;

import com.integrosys.cms.app.feed.bus.bond.BondFeedEntryException;
import com.integrosys.cms.app.feed.bus.bond.IBondFeedEntry;
import com.integrosys.cms.app.feed.bus.forex.ForexFeedGroupException;
import com.integrosys.cms.app.feed.bus.mutualfunds.OBMutualFundsFeedEntry;
import com.integrosys.cms.app.feed.proxy.bond.IBondFeedProxy;
import com.integrosys.cms.app.feed.proxy.mutualfunds.IMutualFundsFeedProxy;

import com.integrosys.cms.app.feed.bus.stock.IStockFeedEntry;
import com.integrosys.cms.app.feed.bus.stock.StockFeedEntryException;
import com.integrosys.cms.app.feed.proxy.stock.IStockFeedProxy;
import com.integrosys.cms.ui.collateral.marketablesec.linedetail.IMarketableEquityLineDetailConstants;
import com.integrosys.cms.ui.collateral.marketablesec.linedetail.MarketableEquityLineDetailAction;


/**
 * @author $Author: hshii $<br>
 * @version $Revision: 1.6 $
 * @since $Date: 2003/09/09 04:19:26 $
 * Tag: $Name:  $
 */

/**
 * Created by IntelliJ IDEA. User: ssathish Date: Jul 2, 2003 Time: 1:26:03 PM
 * To change this template use Options | File Templates.
 */
public class ReadPortItemCommand extends AbstractCommand {

	
	private IBondFeedProxy bondFeedProxy = (IBondFeedProxy)BeanHouse.get("bondFeedProxy");

	
	//Add by govind for equity-security
private IStockFeedProxy stockFeedProxy;
	
	public IStockFeedProxy getStockFeedProxy() {
		return (IStockFeedProxy)BeanHouse.get("stockFeedProxy");
	}
	
	public void setStockFeedProxy(IStockFeedProxy stockFeedProxy) {
		this.stockFeedProxy = stockFeedProxy;
	}


	public String[][] getParameterDescriptor() {
		return (new String[][] { { "indexID", "java.lang.String", REQUEST_SCOPE },
				{ "subtype", "java.lang.String", REQUEST_SCOPE },
				{ "serviceColObj", "com.integrosys.cms.app.collateral.trx.ICollateralTrxValue", SERVICE_SCOPE },
				{ "from_event", "java.lang.String", REQUEST_SCOPE }, 
				{ "bondCode", "java.lang.String", REQUEST_SCOPE },
				{ "schemeCode", "java.lang.String", REQUEST_SCOPE },
				{ "fundsFeedEntry", "com.integrosys.cms.app.feed.bus.mutualfunds.OBMutualFundsFeedEntry", REQUEST_SCOPE },
				{ "stockExchange", "java.lang.String", REQUEST_SCOPE },
				{ "scriptCode", "java.lang.String", REQUEST_SCOPE },
				{IMarketableEquityLineDetailConstants.SESSION_LINE_DETAIL_LIST, List.class.getName(), SERVICE_SCOPE},
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
		return (new String[][] {
				{ "form.PortItemObject", "java.lang.Object", FORM_SCOPE },
				{ "indexID", "java.lang.String", REQUEST_SCOPE },
				{ "subtype", "java.lang.String", REQUEST_SCOPE },
				{ "from_event", "java.lang.String", REQUEST_SCOPE },
				{ "actualEquity", "com.integrosys.cms.app.collateral.bus.type.marketable.IMarketableEquity",
						REQUEST_SCOPE },
				{ "stageEquity", "com.integrosys.cms.app.collateral.bus.type.marketable.IMarketableEquity",
						REQUEST_SCOPE },
				{ "equityDetail", "Lcom.integrosys.cms.app.collateral.bus.type.marketable.IMarketableEquityDetail",

						REQUEST_SCOPE },
				{ "fundsFeedEntry", "com.integrosys.cms.app.feed.bus.mutualfunds.OBMutualFundsFeedEntry", REQUEST_SCOPE },
						{ "bondFeedEntry", "com.integrosys.cms.app.feed.bus.bond.IBondFeedEntry", REQUEST_SCOPE },
						{ "schemeCode", "java.lang.String", REQUEST_SCOPE },
						{ "stockExchange", "java.lang.String", REQUEST_SCOPE },
						{ "scriptCode", "java.lang.String", REQUEST_SCOPE },
											
				{ "stockFeedEntry", "com.integrosys.cms.app.feed.bus.stock.IStockFeedEntry", REQUEST_SCOPE },
				{IMarketableEquityLineDetailConstants.SESSION_LINE_DETAIL_LIST, List.class.getName(), SERVICE_SCOPE},
				{ "session.PortItemObject", "java.lang.Object", SERVICE_SCOPE },
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

		
		IBondFeedEntry bondFeedEntry = null;
		String bondCode = (String) map.get("bondCode");

		String strScriptCode= (String) map.get("scriptCode");
		IStockFeedEntry stockFeedEntry = null;
		
		IMarketableEquity equity = (IMarketableEquity) map.get("session.PortItemObject");
		long index = Long.parseLong((String) map.get("indexID"));
		String from_event = (String) map.get("from_event");
		
		if(equity == null) {
			
			IMarketableCollateral iMarket;
			ICollateralTrxValue itrxValue = (ICollateralTrxValue) map.get("serviceColObj");
			if ((from_event != null) && from_event.equals("read")) {
				iMarket = (IMarketableCollateral) itrxValue.getCollateral();
			}
			else {
				iMarket = (IMarketableCollateral) itrxValue.getStagingCollateral();
				
				if ((from_event != null) && from_event.equals("process")) {
					IMarketableEquity actualEquity = null;
					if((IMarketableCollateral) itrxValue.getCollateral()!=null){
						actualEquity = getItem(((IMarketableCollateral) itrxValue.getCollateral())
							.getEquityList(), index);
					}				
					IMarketableEquity stageEquity = getItem(iMarket.getEquityList(), index);
					result.put("actualEquity", actualEquity);
					result.put("stageEquity", stageEquity);
				}
			}

			
			if (from_event != null) {
				equity = getItem(iMarket.getEquityList(), index);
				if ((equity == null) && from_event.equals("process")) {
					equity = getItem(((IMarketableCollateral) itrxValue.getCollateral()).getEquityList(), index);
				}
				else if(from_event.endsWith(PortItemAction.EVENT_PREPARE_UPDATE_SUB) || from_event.endsWith(PortItemAction.EVENT_PREPARE) || from_event.endsWith(MarketableEquityLineDetailAction.EVENT_PREPARE_CREATE_LINE_DETAIL)) {
					equity = iMarket.getEquityList()[(int) index];
				}
			}
			else {
				equity = iMarket.getEquityList()[(int) index];
			}
		}

		IMarketableEquityDetail[] equityDetails = equity.getEquityDetailArray();
		
		//*** added by sachin
		OBMutualFundsFeedEntry FundsFeedEntry = null;
		//equity.getStockCode();
		try {
			if(equity.getStockCode()!=null){
				//FundsFeedEntry = getMutualFundsFeedProxy().getIMutualFundsFeed(itrxValue.getCollateral().getSchemeCode());
				FundsFeedEntry = (OBMutualFundsFeedEntry)mutualFundsFeedProxy.getIMutualFundsFeed(equity.getStockCode());
			}
		} catch (ForexFeedGroupException e) {
			DefaultLogger.debug(this, "got exception in doExecute" + e);
			e.printStackTrace();
		} catch (Exception ex) {
			DefaultLogger.debug(this, "got exception in doExecute" + ex);
			ex.printStackTrace();
		}
		
			result.put("fundsFeedEntry", FundsFeedEntry);
			System.out.println("fundsFeedEntry in readPortItem is "+FundsFeedEntry);

			result.put("schemeCode", equity.getStockCode());
			
						
		//*************end by sachin

			//************* Code added for Bond Marketable Security by Sachin Patil ******
			try {
				
				if(equity.getStockCode()!=null)
				{
					bondFeedEntry = bondFeedProxy.getBondFeedEntry(equity.getStockCode());
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
			result.put("stockExchange", map.get("stockExchange"));
			result.put("scriptCode", strScriptCode);
		result.put("form.PortItemObject", equity);
		result.put("indexID", map.get("indexID"));
		result.put("subtype", map.get("subtype"));
		result.put("from_event", from_event);
		result.put("equityDetail", equityDetails);
		
		/*********************Add by govind For Equity-Security***********************/
		try {
			if(equity.getStockExchange()!=null && equity.getIsinCode()!=null)
			{
				stockFeedEntry = getStockFeedProxy().getStockFeedEntryStockExc(equity.getStockExchange(),equity.getIsinCode());
				result.put("scriptCode", equity.getIsinCode());
				result.put("stockExchange", equity.getStockExchange());
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
		/**********************Govind S: Line end here*****************/
		
		List<IMarketableEquityLineDetail> lineDetailList = (List<IMarketableEquityLineDetail>) map.get(IMarketableEquityLineDetailConstants.SESSION_LINE_DETAIL_LIST);
		if(lineDetailList == null && equity!=null && equity.getLineDetails()!= null && equity.getLineDetails().length>0)
			lineDetailList = new ArrayList<IMarketableEquityLineDetail>(Arrays.asList(equity.getLineDetails()));
		
		result.put(IMarketableEquityLineDetailConstants.SESSION_LINE_DETAIL_LIST, lineDetailList);
		
		temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		return temp;
	}

	private IMarketableEquity getItem(IMarketableEquity temp[], long itemRef) {
		IMarketableEquity item = null;
		if (temp == null) {
			return item;
		}
		for (int i = 0; i < temp.length; i++) {
			if (temp[i].getRefID() == itemRef) {
				item = temp[i];
			}
			else {
				continue;
			}
		}
		return item;
	}
	
	private IMutualFundsFeedProxy mutualFundsFeedProxy = (IMutualFundsFeedProxy)BeanHouse.get("mutualFundsFeedProxy");

}
