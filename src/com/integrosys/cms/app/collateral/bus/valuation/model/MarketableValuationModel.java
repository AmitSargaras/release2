/*
 * Created on May 11, 2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.integrosys.cms.app.collateral.bus.valuation.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.integrosys.cms.app.collateral.bus.ICollateral;
import com.integrosys.cms.app.collateral.bus.type.marketable.IMarketableCollateral;
import com.integrosys.cms.app.collateral.bus.type.marketable.IMarketableEquity;

/**
 * @author Administrator
 * 
 *         TODO To change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Style - Code Templates
 */
public class MarketableValuationModel extends GenericValuationModel {
	private List portfolioItems;

	private Map feeds;

	public MarketableValuationModel() {
		super();
		portfolioItems = new ArrayList();
		feeds = new HashMap();
	}

	/**
	 * @return Returns the feeds.
	 */
	public Map getFeeds() {
		return feeds;
	}

	/**
	 * @param feeds The feeds to set.
	 */
	public void setFeeds(Map feeds) {
		this.feeds = feeds;
	}

	public void addFeedInfo(String isin, FeedInfoModel model) {
		this.feeds.put(isin, model);
	}

	/**
	 * @return Returns the portfolioItems.
	 */
	public List getPortfolioItems() {
		return portfolioItems;
	}

	/**
	 * @param portfolioItems The portfolioItems to set.
	 */
	public void setPortfolioItems(List portfolioItems) {
		this.portfolioItems = portfolioItems;
	}

	public void addPortfolioItem(IMarketableEquity equity) {
		portfolioItems.add(equity);
	}

	public void setDetailFromCollateral(ICollateral col) {
		super.setDetailFromCollateral(col);
		if (col instanceof IMarketableCollateral) {
			IMarketableCollateral marketableCol = (IMarketableCollateral) col;
			IMarketableEquity[] equityList = marketableCol.getEquityList();
			if (equityList != null) {
				for (int i = 0; i < equityList.length; i++) {
					addPortfolioItem(equityList[i]);
				}
			}
		}
	}
}
