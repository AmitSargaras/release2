package com.integrosys.cms.ui.collateral.marketablesec.linedetail;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.collateral.bus.type.marketable.IMarketableCollateral;
import com.integrosys.cms.app.collateral.bus.type.marketable.IMarketableEquity;
import com.integrosys.cms.app.collateral.bus.type.marketable.linedetail.IMarketableEquityLineDetail;
import com.integrosys.cms.app.collateral.trx.ICollateralTrxValue;

public class DeleteLineDetailCommand extends AbstractCommand implements IMarketableEquityLineDetailConstants {
	
	public HashMap doExecute(HashMap map)
			throws CommandValidationException, CommandProcessingException {
		HashMap resultMap = new HashMap();
		HashMap exceptionMap = new HashMap();
		HashMap returnMap = new HashMap();
		
		String from_event = (String) map.get("from_event");
		String subtype = (String) map.get("subtype");
		String selectedItem = (String) map.get("selectedItem");
		
		List<IMarketableEquityLineDetail> lineDetailList = (List<IMarketableEquityLineDetail>) map.get(IMarketableEquityLineDetailConstants.SESSION_LINE_DETAIL_LIST);
		List<IMarketableEquityLineDetail> newLineDetailList = null;
		
		if(StringUtils.isNotBlank(selectedItem)) {
			
			if(lineDetailList != null) {
				newLineDetailList = new ArrayList<IMarketableEquityLineDetail>(lineDetailList);
				newLineDetailList.remove(Integer.parseInt(selectedItem));
			}
		}
		
		resultMap.put("from_event", from_event);
		resultMap.put("subtype", subtype);
		resultMap.put("indexID", map.get("indexID"));
		resultMap.put("itemType", map.get("itemType"));
		
		resultMap.put("bondCode", map.get("bondCode"));
		resultMap.put("schemeCode", map.get("schemeCode"));
		resultMap.put("stockExchange", map.get("stockExchange"));
		resultMap.put("scriptCode", map.get("scriptCode"));
		resultMap.put("stockFeedEntry", map.get("stockFeedEntry"));
		resultMap.put("fundsFeedEntry", map.get("fundsFeedEntry")); 
		resultMap.put(IMarketableEquityLineDetailConstants.SESSION_LINE_DETAIL_LIST, newLineDetailList);
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		System.out.println("fundsFeedEntry in DeleteLineDetail is "+map.get("fundsFeedEntry"));

		return returnMap;
	}
	
	public String[][] getParameterDescriptor() {
		return new String[][] {
			{ "from_event", String.class.getName(), REQUEST_SCOPE},
			{ "subtype", String.class.getName(), REQUEST_SCOPE},
			{ "indexID", "java.lang.String", REQUEST_SCOPE },
			{ "itemType", String.class.getName(), REQUEST_SCOPE},
			{ "bondCode", "java.lang.String", REQUEST_SCOPE },
			{ "schemeCode", "java.lang.String", REQUEST_SCOPE },
			{ "stockExchange", "java.lang.String", REQUEST_SCOPE },
			{ "scriptCode", "java.lang.String", REQUEST_SCOPE },
			{ "stockFeedEntry", "com.integrosys.cms.app.feed.bus.stock.IStockFeedEntry", REQUEST_SCOPE },
			{ "fundsFeedEntry", "com.integrosys.cms.app.feed.bus.mutualfunds.OBMutualFundsFeedEntry", REQUEST_SCOPE },
			{IMarketableEquityLineDetailConstants.SESSION_LINE_DETAIL_LIST, List.class.getName(), SERVICE_SCOPE},
			{"selectedItem", String.class.getName(), REQUEST_SCOPE}
		};
	}
	
	public String[][] getResultDescriptor() {
		return new String[][] {
			{ "from_event", String.class.getName(), REQUEST_SCOPE},
			{ "subtype", String.class.getName(), REQUEST_SCOPE},
			{ "indexID", "java.lang.String", REQUEST_SCOPE },
			{ "itemType", String.class.getName(), REQUEST_SCOPE},
			{ "bondCode", "java.lang.String", REQUEST_SCOPE },
			{ "schemeCode", "java.lang.String", REQUEST_SCOPE },
			{ "stockExchange", "java.lang.String", REQUEST_SCOPE },
			{ "scriptCode", "java.lang.String", REQUEST_SCOPE },
			{ "fundsFeedEntry", "com.integrosys.cms.app.feed.bus.mutualfunds.OBMutualFundsFeedEntry", REQUEST_SCOPE },
			{ "stockFeedEntry", "com.integrosys.cms.app.feed.bus.stock.IStockFeedEntry", REQUEST_SCOPE },
			{IMarketableEquityLineDetailConstants.SESSION_LINE_DETAIL_LIST, List.class.getName(), SERVICE_SCOPE}
		};
	}

}
