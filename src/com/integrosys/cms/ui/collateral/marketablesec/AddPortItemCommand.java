/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/collateral/marketablesec/AddPortItemCommand.java,v 1.7 2005/08/09 07:48:12 lyng Exp $
 */

package com.integrosys.cms.ui.collateral.marketablesec;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.struts.action.ActionMessage;

import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.collateral.bus.CollateralValuator;
import com.integrosys.cms.app.collateral.bus.type.marketable.IMarketableCollateral;
import com.integrosys.cms.app.collateral.bus.type.marketable.IMarketableEquity;
import com.integrosys.cms.app.collateral.bus.type.marketable.linedetail.IMarketableEquityLineDetail;
import com.integrosys.cms.app.collateral.bus.type.marketable.subtype.bondslocal.IBondsLocal;
import com.integrosys.cms.app.collateral.bus.type.marketable.subtype.mainindexlocal.IMainIndexLocal;
import com.integrosys.cms.app.collateral.bus.type.marketable.subtype.otherlistedlocal.IOtherListedLocal;
import com.integrosys.cms.app.collateral.trx.ICollateralTrxValue;
import com.integrosys.cms.app.feed.bus.bond.IBondFeedEntry;
import com.integrosys.cms.app.feed.bus.mutualfunds.IMutualFundsFeedEntry;
import com.integrosys.cms.app.feed.bus.stock.IStockFeedEntry;
import com.integrosys.cms.app.feed.proxy.bond.IBondFeedProxy;
import com.integrosys.cms.app.feed.proxy.mutualfunds.IMutualFundsFeedProxy;
import com.integrosys.cms.app.feed.proxy.stock.IStockFeedProxy;
import com.integrosys.cms.ui.collateral.marketablesec.linedetail.IMarketableEquityLineDetailConstants;

/**
 * @author $Author: lyng $<br>
 * @version $Revision: 1.7 $
 * @since $Date: 2005/08/09 07:48:12 $
 * Tag: $Name:  $
 */

/**
 * Created by IntelliJ IDEA. User: ssathish Date: Jul 2, 2003 Time: 1:26:03 PM
 * To change this template use Options | File Templates.
 */
public class AddPortItemCommand extends AbstractCommand {

	
	private IBondFeedProxy bondFeedProxy = (IBondFeedProxy)BeanHouse.get("bondFeedProxy");
	private IStockFeedProxy stockFeedProxyNew = (IStockFeedProxy)BeanHouse.get("stockFeedProxy");
	private IMutualFundsFeedProxy mutualFundsFeedProxy = (IMutualFundsFeedProxy)BeanHouse.get("mutualFundsFeedProxy");
	public String[][] getParameterDescriptor() {
		return (new String[][] { { "form.PortItemObject", "java.lang.Object", FORM_SCOPE },
				{ "serviceColObj", "com.integrosys.cms.app.collateral.trx.ICollateralTrxValue", SERVICE_SCOPE },
				{ "curr", "java.lang.String", SERVICE_SCOPE },
				{ "branchName", "java.lang.String", SERVICE_SCOPE },
				{ "secPriority", "java.lang.String", SERVICE_SCOPE },
				//{ "event", "java.lang.String", REQUEST_SCOPE },
				{ "BondList", "java.util.List", SERVICE_SCOPE },
				{ "StockList", "java.util.List", SERVICE_SCOPE },
				{ "FundList", "java.util.List", SERVICE_SCOPE },
				{IMarketableEquityLineDetailConstants.SESSION_LINE_DETAIL_LIST, List.class.getName(), SERVICE_SCOPE},
				{IMarketableEquityLineDetailConstants.SESSION_EQUITY_EVENT, String.class.getName(), SERVICE_SCOPE },
				{ "subtype", "java.lang.String", REQUEST_SCOPE } });
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
				{ "BondList", "java.util.List", SERVICE_SCOPE },
				{ "StockList", "java.util.List", SERVICE_SCOPE },
				{ "FundList", "java.util.List", SERVICE_SCOPE },
				{IMarketableEquityLineDetailConstants.SESSION_LINE_DETAIL_LIST, List.class.getName(), SERVICE_SCOPE},
				{IMarketableEquityLineDetailConstants.SESSION_EQUITY_EVENT, String.class.getName(), SERVICE_SCOPE },
				{ "session.PortItemObject", "java.lang.Object", SERVICE_SCOPE },
				{ "subtype", "java.lang.String", REQUEST_SCOPE }, });
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

		IMarketableEquity iMarketEquity = (IMarketableEquity) map.get("form.PortItemObject");

		ICollateralTrxValue itrxValue = (ICollateralTrxValue) map.get("serviceColObj");
		//String event = (String) map.get("event");
		if(itrxValue.getCollateral() instanceof  IBondsLocal)
		{
		
		IBondFeedEntry bondFeedEntry = null;
		
		String bondCode = null;
		List BondList = new ArrayList() ;
		BondList = (List) map.get("BondList");
			bondCode = iMarketEquity.getStockCode();					
			
			if(bondCode !=null)
			{
				bondFeedEntry = bondFeedProxy.getBondFeedEntry(bondCode);
				if(bondFeedEntry != null)
				{
				BondList.add(bondFeedEntry);
				}
				else
				{
					//result.put("event",event);
					//resultMap.put("lienList", (List)map.get("lienList"));
					//resultMap.put("indexID", indexID);
			        exceptionMap.put("stockCode",  new ActionMessage("label.bond.invalid.code","Stock Code Invalid"));
					//returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
			        temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
					temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
					//returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
					return temp;
					
				}
			}
				
						
		if(BondList!= null)
		{
			result.put("BondList", BondList);
		}
	}

		if(itrxValue.getCollateral() instanceof  IOtherListedLocal)
		{			
		
		IStockFeedEntry stockFeedEntry = null;
		
		String strStockExchange = null;
		String strScriptCode = null;
		
		List StockList = new ArrayList() ;
		StockList = (List) map.get("StockList");
			strScriptCode = iMarketEquity.getIsinCode();
			strStockExchange = iMarketEquity.getStockExchange();
			
			
			if(strStockExchange!=null && strScriptCode!=null)
			{
				stockFeedEntry = stockFeedProxyNew.getStockFeedEntryStockExc(strStockExchange,strScriptCode);
				if(stockFeedEntry != null)
				{
				StockList.add(stockFeedEntry);
				}
				else
				{
					//result.put("event",event);
					//resultMap.put("lienList", (List)map.get("lienList"));
					//resultMap.put("indexID", indexID);
			        exceptionMap.put("isinCode",  new ActionMessage("label.script.invalid.code","Stock Code Invalid"));
					//returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
			        temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
					temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
					//returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
					return temp;
					
				}
			}
							
				
						
		if(StockList!= null)
		{
			result.put("StockList", StockList);
		}
	}
		if(itrxValue.getCollateral() instanceof  IMainIndexLocal)
		{			
			
		IMutualFundsFeedEntry mutualFundsFeedEntry = null;
		
		String mutualCode = null;
		List FundList = new ArrayList() ;
		FundList = (List) map.get("FundList");
			mutualCode = iMarketEquity.getStockCode();					
			
			if(mutualCode !=null)
			{
				mutualFundsFeedEntry = mutualFundsFeedProxy.getIMutualFundsFeed(mutualCode);					
				
				if(mutualFundsFeedEntry != null)
				{
					FundList.add(mutualFundsFeedEntry);
				}
				else
				{
					//result.put("event",event);
					//resultMap.put("lienList", (List)map.get("lienList"));
					//resultMap.put("indexID", indexID);
			        exceptionMap.put("stockCode",  new ActionMessage("label.scheme.invalid.code","Stock Code Invalid"));
					//returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
			        temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
					temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
					//returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
					return temp;
					
				}
			}
				
						
		if(FundList!= null)
		{
			result.put("FundList", FundList);
		}
	}
		
		IMarketableCollateral iMarket = (IMarketableCollateral) itrxValue.getStagingCollateral();
		String curr = (String) map.get("curr");
		String branchName = (String) map.get("branchName");
		String secPriority = (String) map.get("secPriority");
		iMarket.setCurrencyCode(curr);
		iMarket.setSecurityOrganization(branchName);
		iMarket.setSecPriority(secPriority);
		
		List<IMarketableEquityLineDetail> lineDetailList = (List<IMarketableEquityLineDetail>) map.get(IMarketableEquityLineDetailConstants.SESSION_LINE_DETAIL_LIST);
		if(lineDetailList != null) {
			iMarketEquity.setLineDetails((IMarketableEquityLineDetail[]) lineDetailList.toArray(new IMarketableEquityLineDetail[0]));
		}
		
		addPortItem(iMarket, iMarketEquity);

		try {
			CollateralValuator valuator = new CollateralValuator();
            //valuator.setMarketableCMVFSV(iMarket);
            valuator.setCollateralCMVFSV(iMarket);

        }
		catch (Exception e) {
			// do nothing.
		}

		itrxValue.setStagingCollateral(iMarket);

		result.put("serviceColObj", itrxValue);
		DefaultLogger.debug(this, "After Addition:" + itrxValue + ":");
		result.put("subtype", map.get("subtype"));
		result.put(IMarketableEquityLineDetailConstants.SESSION_LINE_DETAIL_LIST, null);
		result.put(IMarketableEquityLineDetailConstants.SESSION_EQUITY_EVENT, null);
		result.put("session.PortItemObject",null);

		temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		return temp;
	}

	public static void addPortItem(IMarketableCollateral iMarket, IMarketableEquity iMarketEquity) {

		IMarketableEquity[] existingArray = iMarket.getEquityList();
		int arrayLength = 0;
		if (existingArray != null) {
			arrayLength = existingArray.length;
		}
		DefaultLogger.debug("AddPortItemCommand...", "existingArray length: " + arrayLength);
		IMarketableEquity[] newArray = new IMarketableEquity[arrayLength + 1];
		if (existingArray != null) {
			System.arraycopy(existingArray, 0, newArray, 0, arrayLength);
		}
		newArray[arrayLength] = iMarketEquity;

		iMarket.setEquityList(newArray);
	}
}
