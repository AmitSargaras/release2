package com.integrosys.cms.host.eai.security.inquiry;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import com.integrosys.cms.host.eai.security.SecurityMessageBody;
import com.integrosys.cms.host.eai.security.bus.marketable.MarketableSecurity;
import com.integrosys.cms.host.eai.security.bus.marketable.PortfolioItem;

public class MarketableSecurityDetailsPopulator extends AbstractSecuritySubtypeDetailsPopulator {

	protected void doPopulateMessageBody(SecurityMessageBody securityMsgBody, Object securitySubtypeObject) {
		MarketableSecurity marketable = (MarketableSecurity) securitySubtypeObject;
		marketable.setLOSSecurityId(securityMsgBody.getSecurityDetail().getLOSSecurityId());
		securityMsgBody.setMarketableSecDetail(marketable);

		Map parameters = new HashMap();
		parameters.put("collateralId", new Long(securityMsgBody.getSecurityDetail().getCMSSecurityId()));

		List portfolioItemList = getSecurityDao().retrieveObjectsListByParameters(parameters, PortfolioItem.class);
		if (portfolioItemList != null && !portfolioItemList.isEmpty()) {

			for (Iterator itr = portfolioItemList.iterator(); itr.hasNext();) {
				PortfolioItem portfolio = (PortfolioItem) itr.next();
				portfolio.setSecurityId(marketable.getLOSSecurityId());
				portfolio.setCMSPortfolioItemId(portfolio.getItemId());
			}

			Vector portfolioItemDetails = new Vector(portfolioItemList);
			securityMsgBody.setPortfolioItems(portfolioItemDetails);
		}

		populateValuationDetails(securityMsgBody);
	}

	protected Class getSecuritySubtypeClass() {
		return MarketableSecurity.class;
	}

}
