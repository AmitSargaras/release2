package com.integrosys.cms.ui.collateral.marketablesec.linedetail;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.util.LabelValueBean;

import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.AccessDeniedException;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.collateral.bus.type.marketable.IMarketableCollateral;
import com.integrosys.cms.app.collateral.bus.type.marketable.IMarketableEquity;
import com.integrosys.cms.app.collateral.bus.type.marketable.linedetail.IMarketableEquityLineDetail;
import com.integrosys.cms.app.collateral.bus.type.marketable.linedetail.OBMarketableEquityLineDetail;
import com.integrosys.cms.app.collateral.trx.ICollateralTrxValue;
import com.integrosys.cms.app.feed.bus.mutualfunds.IMutualFundsFeedEntry;
import com.integrosys.cms.app.feed.proxy.mutualfunds.IMutualFundsFeedProxy;
import com.integrosys.cms.app.limit.bus.IFacilityJdbc;
import com.integrosys.cms.app.limit.bus.ILimit;

public class PrepareLineDetailCommand extends AbstractCommand implements IMarketableEquityLineDetailConstants {

	private IMutualFundsFeedProxy mutualFundsFeedProxy = (IMutualFundsFeedProxy)BeanHouse.get("mutualFundsFeedProxy");

	public String[][] getParameterDescriptor() {
		return  new String[][] {
			{ "serviceColObj", "com.integrosys.cms.app.collateral.trx.ICollateralTrxValue", SERVICE_SCOPE },
			{ "indexID", "java.lang.String", REQUEST_SCOPE },
			{ "from_event", String.class.getName(), REQUEST_SCOPE},
			{ "subtype", String.class.getName(), REQUEST_SCOPE},
			{ "event", String.class.getName(), REQUEST_SCOPE},
			{ "selectedItem", String.class.getName(), REQUEST_SCOPE},
			{ "itemType", String.class.getName(), REQUEST_SCOPE},
			{ "refId", String.class.getName(), REQUEST_SCOPE},
			{ "bondCode", "java.lang.String", REQUEST_SCOPE },
			{ "schemeCode", "java.lang.String", REQUEST_SCOPE },
			{ "stockExchange", "java.lang.String", REQUEST_SCOPE },
			{ "scriptCode", "java.lang.String", REQUEST_SCOPE },
			{ "stockFeedEntry", "com.integrosys.cms.app.feed.bus.stock.IStockFeedEntry", REQUEST_SCOPE },
			{ "fundsFeedEntry", "com.integrosys.cms.app.feed.bus.mutualfunds.OBMutualFundsFeedEntry", REQUEST_SCOPE },
			{ SESSION_FAC_DETAIL_LIST, "java.util.List", SERVICE_SCOPE },
			{ SESSION_FAC_NAME_LIST, "java.util.List", SERVICE_SCOPE },
			{ SESSION_FAC_LINE_NO_LIST, "java.util.List", SERVICE_SCOPE },
			{ SESSION_FAC_ID_LIST, "java.util.List", SERVICE_SCOPE },
			{IMarketableEquityLineDetailConstants.SESSION_LINE_DETAIL_LIST, List.class.getName(), SERVICE_SCOPE}
		};
	}

	public String[][] getResultDescriptor() {
		return new String[][] { 
			{ "indexID", "java.lang.String", REQUEST_SCOPE },
			{ "from_event", String.class.getName(), REQUEST_SCOPE},
			{ "subtype", String.class.getName(), REQUEST_SCOPE},
			{ "selectedItem", String.class.getName(), REQUEST_SCOPE},
			{ "itemType", String.class.getName(), REQUEST_SCOPE},
			{ "bondCode", "java.lang.String", REQUEST_SCOPE },
			{ "schemeCode", "java.lang.String", REQUEST_SCOPE },
			{ "stockExchange", "java.lang.String", REQUEST_SCOPE },
			{ "scriptCode", "java.lang.String", REQUEST_SCOPE },
			{ "stockFeedEntry", "com.integrosys.cms.app.feed.bus.stock.IStockFeedEntry", REQUEST_SCOPE },
			{ "fundsFeedEntry", "com.integrosys.cms.app.feed.bus.mutualfunds.OBMutualFundsFeedEntry", REQUEST_SCOPE },
			{ SESSION_FAC_DETAIL_LIST, "java.util.List", SERVICE_SCOPE },
			{ SESSION_FAC_NAME_LIST, "java.util.List", SERVICE_SCOPE },
			{ SESSION_FAC_LINE_NO_LIST, "java.util.List", SERVICE_SCOPE },
			{ SESSION_FAC_ID_LIST, "java.util.List", SERVICE_SCOPE },
			{ IMarketableEquityLineDetailConstants.REQUEST_MARKETABLE_EQUITY_LINE_DETAIL_LIST, IMarketableEquityLineDetail.class.getName(), REQUEST_SCOPE},
			{ IMarketableEquityLineDetailConstants.REQUEST_STAGING_MARKETABLE_EQUITY_LINE_DETAIL_LIST, IMarketableEquityLineDetail.class.getName(), REQUEST_SCOPE},
			{ MARKETABLE_EQUITY_LINE_DETAIL_FORM, "java.lang.Object", FORM_SCOPE }
		};
	}
	
	public HashMap doExecute(HashMap map)
			throws CommandValidationException, CommandProcessingException, AccessDeniedException {
		HashMap resultMap = new HashMap();
		HashMap returnMap = new HashMap();
		
		ICollateralTrxValue itrxValue = (ICollateralTrxValue) map.get("serviceColObj");
		IMarketableCollateral actualColl = (IMarketableCollateral) itrxValue.getCollateral();
		String from_event = (String) map.get("from_event");
		String subtype = (String) map.get("subtype"); 
		String event = (String) map.get("event");
		String refId = (String) map.get("refId");
		
		List<ILimit> facDetailList = (List<ILimit>) map.get(SESSION_FAC_DETAIL_LIST);
		List<LabelValueBean> facNameList = (List<LabelValueBean>) map.get(SESSION_FAC_NAME_LIST);
		List<LabelValueBean> facIdList = (List<LabelValueBean>) map.get(SESSION_FAC_ID_LIST);
		List<LabelValueBean> facLineNoList = (List<LabelValueBean>) map.get(SESSION_FAC_LINE_NO_LIST);
		
		IFacilityJdbc facilityJdbc = (IFacilityJdbc) BeanHouse.get("facilityJdbc");
		
		if(facDetailList == null || facDetailList.size() == 0) {
			facDetailList = facilityJdbc.getFacDetailBySecurityId(itrxValue.getCollateral().getCollateralID());
			 facIdList = new ArrayList<LabelValueBean>();
			 facLineNoList =  new ArrayList<LabelValueBean>();
			 facNameList = new ArrayList<LabelValueBean>();
			 RefreshFacilityDropdownCommand.prepareFacilityDropdownList(facDetailList, DROPDOWN_FACILITY_NAME, null, facNameList);
			 RefreshFacilityDropdownCommand.prepareFacilityDropdownList(facDetailList, DROPDOWN_LINE_NO, null, facLineNoList); 
		}
		
		if(MarketableEquityLineDetailAction.EVENT_ERROR_EDIT_LINE_DETAIL.equals(event)) {
			resultMap.put("selectedItem", map.get("selectedItem"));
		}
		else if(MarketableEquityLineDetailAction.EVENT_PREPARE_EDIT_LINE_DETAIL.equals(event) || MarketableEquityLineDetailAction.EVENT_VIEW_LINE_DETAIL.equals(event)) {
			String selectedItem = (String) map.get("selectedItem");
			IMarketableEquityLineDetail lineDetail = null;
			if(StringUtils.isNotBlank(selectedItem)) {
				List<IMarketableEquityLineDetail> lineDetailList = (List<IMarketableEquityLineDetail>) map.get(IMarketableEquityLineDetailConstants.SESSION_LINE_DETAIL_LIST);
				
				if(lineDetailList!= null && lineDetailList.size()>0) {
					lineDetail = lineDetailList.get(Integer.valueOf(selectedItem));
					
					if(StringUtils.isNotBlank(lineDetail.getFacilityName())) {
						RefreshFacilityDropdownCommand.prepareFacilityDropdownList(facDetailList, DROPDOWN_FACILITY_ID, lineDetail.getFacilityName(), facIdList);
					}
					
				}
			}
			else if(MarketableEquityLineDetailAction.EVENT_VIEW_LINE_DETAIL.equals(event) && 
					("read".equals(from_event) || "close".equals(from_event) || "process".equals(from_event)) && StringUtils.isNotBlank(refId) ) {
				List<IMarketableEquityLineDetail> lineDetailList = (List<IMarketableEquityLineDetail>) map.get(IMarketableEquityLineDetailConstants.SESSION_LINE_DETAIL_LIST);
				IMarketableEquityLineDetail actualLineDetail = null;
				if(lineDetailList != null && lineDetailList.size()>0) {
					for(IMarketableEquityLineDetail sessionLineDetaill : lineDetailList) {
						if(sessionLineDetaill.getRefID() ==  Long.valueOf(refId)) {
							lineDetail = sessionLineDetaill;
							break;
						}
					}
				}
				
				for(IMarketableEquity equity : actualColl.getEquityList()) {
					IMarketableEquityLineDetail[] lineDetails = equity.getLineDetails();
					if(lineDetails != null) {
						for(IMarketableEquityLineDetail equityLineDetail : lineDetails) {
							if(lineDetail.getRefID() == equityLineDetail.getRefID()) {
								actualLineDetail = equityLineDetail;
							}
						}
					}
				}
				
				resultMap.put(IMarketableEquityLineDetailConstants.REQUEST_STAGING_MARKETABLE_EQUITY_LINE_DETAIL_LIST, lineDetail);
				resultMap.put(IMarketableEquityLineDetailConstants.REQUEST_MARKETABLE_EQUITY_LINE_DETAIL_LIST, actualLineDetail);
			}
			resultMap.put("selectedItem", selectedItem);
			resultMap.put(MARKETABLE_EQUITY_LINE_DETAIL_FORM, lineDetail);
		}
		else {
			resultMap.put(MARKETABLE_EQUITY_LINE_DETAIL_FORM, new OBMarketableEquityLineDetail());
		}
		
		resultMap.put("from_event", from_event);
		resultMap.put("subtype", subtype);
		
		resultMap.put("bondCode", map.get("bondCode"));
		resultMap.put("schemeCode", map.get("schemeCode"));
		resultMap.put("stockExchange", map.get("stockExchange"));
		resultMap.put("scriptCode", map.get("scriptCode"));
		resultMap.put("stockFeedEntry", map.get("stockFeedEntry"));
		
		IMutualFundsFeedEntry mutualFundsFeedEntry = null;	
		String strSchemeCode= (String) map.get("schemeCode"); 
		if(strSchemeCode !=null)
		{
			mutualFundsFeedEntry = mutualFundsFeedProxy.getIMutualFundsFeed(strSchemeCode);					
			
			if(mutualFundsFeedEntry != null)
			{
				resultMap.put("fundsFeedEntry", mutualFundsFeedEntry);
			}
		}
		System.out.println("fundsFeedEntry in PrepareLineDetail is "+mutualFundsFeedEntry);
		resultMap.put(SESSION_FAC_NAME_LIST, facNameList);
		resultMap.put(SESSION_FAC_DETAIL_LIST, facDetailList);
		resultMap.put(SESSION_FAC_ID_LIST, facIdList);
		resultMap.put(SESSION_FAC_LINE_NO_LIST, facLineNoList);
		resultMap.put("indexID", map.get("indexID"));
		resultMap.put("itemType", map.get("itemType"));
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		
		return returnMap;
	}

}
