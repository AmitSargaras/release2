package com.integrosys.cms.ui.collateral.marketablesec.linedetail;

import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.util.LabelValueBean;

import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.AccessDeniedException;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.collateral.bus.type.marketable.IMarketableEquity;
import com.integrosys.cms.app.feed.bus.mutualfunds.IMutualFundsFeedEntry;
import com.integrosys.cms.app.feed.bus.mutualfunds.OBMutualFundsFeedEntry;
import com.integrosys.cms.app.limit.bus.ILimit;

public class CancelLineItemCommand extends AbstractCommand implements IMarketableEquityLineDetailConstants{

	public String[][] getParameterDescriptor() {
		return new String[][] {
			{ "from_event", String.class.getName(), REQUEST_SCOPE},
			{ "subtype", String.class.getName(), REQUEST_SCOPE},
			{ "bondCode", "java.lang.String", REQUEST_SCOPE },
			{ "schemeCode", "java.lang.String", REQUEST_SCOPE },
			{ "stockExchange", "java.lang.String", REQUEST_SCOPE },
			{ "scriptCode", "java.lang.String", REQUEST_SCOPE },
			{ "fundsFeedEntry", "com.integrosys.cms.app.feed.bus.mutualfunds.OBMutualFundsFeedEntry", REQUEST_SCOPE },
			{ "stockFeedEntry", "com.integrosys.cms.app.feed.bus.stock.IStockFeedEntry", REQUEST_SCOPE },
			{ "indexID", "java.lang.String", REQUEST_SCOPE },
			{ "itemType", String.class.getName(), REQUEST_SCOPE}
		};
	}

	public String[][] getResultDescriptor() {
		return new String[][] {
			{ SESSION_FAC_DETAIL_LIST, "java.util.List", SERVICE_SCOPE },
			{ SESSION_FAC_LINE_NO_LIST, "java.util.List", SERVICE_SCOPE },
			{ SESSION_FAC_ID_LIST, "java.util.List", SERVICE_SCOPE },
			{ SESSION_FAC_NAME_LIST, "java.util.List", SERVICE_SCOPE },
			{ "bondCode", "java.lang.String", REQUEST_SCOPE },
			{ "schemeCode", "java.lang.String", REQUEST_SCOPE },
			{ "stockExchange", "java.lang.String", REQUEST_SCOPE },
			{ "scriptCode", "java.lang.String", REQUEST_SCOPE },
			{ "fundsFeedEntry", "com.integrosys.cms.app.feed.bus.mutualfunds.OBMutualFundsFeedEntry", REQUEST_SCOPE },
			{ "stockFeedEntry", "com.integrosys.cms.app.feed.bus.stock.IStockFeedEntry", REQUEST_SCOPE },
			{ "from_event", String.class.getName(), REQUEST_SCOPE},
			{ "subtype", String.class.getName(), REQUEST_SCOPE},
			{ "indexID", "java.lang.String", REQUEST_SCOPE },
			{ "itemType", String.class.getName(), REQUEST_SCOPE}
		};
	}
	
	public HashMap doExecute(HashMap map)
			throws CommandValidationException, CommandProcessingException, AccessDeniedException {
		HashMap resultMap = new HashMap();
		HashMap returnMap = new HashMap();
		
		String from_event = (String) map.get("from_event");
		String subtype = (String) map.get("subtype");
		System.out.println("fundsFeedEntry in CancelLineDetail is "+map.get("fundsFeedEntry"));

		
		resultMap.put(SESSION_FAC_NAME_LIST, null);
		resultMap.put(SESSION_FAC_ID_LIST, null);
		resultMap.put(SESSION_FAC_LINE_NO_LIST, null);
		resultMap.put(SESSION_FAC_DETAIL_LIST, null);
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

		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		return returnMap;
	}
}
