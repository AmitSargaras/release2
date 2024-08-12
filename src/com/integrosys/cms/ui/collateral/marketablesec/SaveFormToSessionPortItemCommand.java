package com.integrosys.cms.ui.collateral.marketablesec;

import java.util.HashMap;
import java.util.List;

import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.collateral.bus.type.marketable.IMarketableEquity;
import com.integrosys.cms.app.feed.bus.mutualfunds.IMutualFundsFeedEntry;
import com.integrosys.cms.app.feed.bus.stock.StockFeedEntryException;
import com.integrosys.cms.app.feed.proxy.mutualfunds.IMutualFundsFeedProxy;
import com.integrosys.cms.ui.collateral.marketablesec.linedetail.IMarketableEquityLineDetailConstants;

public class SaveFormToSessionPortItemCommand extends AbstractCommand {
	private IMutualFundsFeedProxy mutualFundsFeedProxy = (IMutualFundsFeedProxy)BeanHouse.get("mutualFundsFeedProxy");

	public String[][] getParameterDescriptor() {
		return (new String[][] { { "form.PortItemObject", "java.lang.Object", FORM_SCOPE },
				{ "bondCode", "java.lang.String", REQUEST_SCOPE },
				{ "schemeCode", "java.lang.String", REQUEST_SCOPE },
				{ "stockExchange", "java.lang.String", REQUEST_SCOPE },
				{ "scriptCode", "java.lang.String", REQUEST_SCOPE },
				{ "fundsFeedEntry", "com.integrosys.cms.app.feed.bus.mutualfunds.OBMutualFundsFeedEntry", REQUEST_SCOPE },
				{ "stockFeedEntry", "com.integrosys.cms.app.feed.bus.stock.IStockFeedEntry", REQUEST_SCOPE },
				{IMarketableEquityLineDetailConstants.SESSION_LINE_DETAIL_LIST, List.class.getName(), SERVICE_SCOPE},
				{ "subtype", "java.lang.String", REQUEST_SCOPE } });
	}
	
	public String[][] getResultDescriptor() {
		return (new String[][] {
				{ "bondCode", "java.lang.String", REQUEST_SCOPE },
				{ "schemeCode", "java.lang.String", REQUEST_SCOPE },
				{ "stockExchange", "java.lang.String", REQUEST_SCOPE },
				{ "scriptCode", "java.lang.String", REQUEST_SCOPE },
				{ "fundsFeedEntry", "com.integrosys.cms.app.feed.bus.mutualfunds.OBMutualFundsFeedEntry", REQUEST_SCOPE },
				{ "stockFeedEntry", "com.integrosys.cms.app.feed.bus.stock.IStockFeedEntry", REQUEST_SCOPE },
				{ "subtype", "java.lang.String", REQUEST_SCOPE },
				{ "session.PortItemObject", "java.lang.Object", SERVICE_SCOPE }
				});
	}
	
	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {

		HashMap resultMap = new HashMap();
		HashMap returnMap = new HashMap();

		IMarketableEquity iMarketEquity = (IMarketableEquity) map.get("form.PortItemObject");
		String strSchemeCode= (String) map.get("schemeCode");

		IMutualFundsFeedEntry mutualFundsFeedEntry = null;	

		resultMap.put("bondCode", map.get("bondCode"));
		resultMap.put("schemeCode", map.get("schemeCode"));
		resultMap.put("stockExchange", map.get("stockExchange"));
		resultMap.put("scriptCode", map.get("scriptCode"));
		resultMap.put("stockFeedEntry", map.get("stockFeedEntry"));
		
		if(strSchemeCode !=null)
		{
			mutualFundsFeedEntry = mutualFundsFeedProxy.getIMutualFundsFeed(strSchemeCode);					
			
			if(mutualFundsFeedEntry != null)
			{
				resultMap.put("fundsFeedEntry", mutualFundsFeedEntry);
			}
		}
		
		System.out.println("fundsFeedEntry in SaveFormTosessionPortItem is "+mutualFundsFeedEntry);

		
		resultMap.put("subtype", map.get("subtype"));
		resultMap.put("session.PortItemObject", iMarketEquity);
				
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		return returnMap;
	}

}
