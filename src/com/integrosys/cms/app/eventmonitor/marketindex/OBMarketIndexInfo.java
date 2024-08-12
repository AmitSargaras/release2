/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/eventmonitor/marketindex/OBMarketIndexInfo.java,v 1.2 2003/09/08 19:15:40 phtan Exp $
 */

package com.integrosys.cms.app.eventmonitor.marketindex;

import com.integrosys.cms.app.eventmonitor.OBEventInfo;

/**
 * JavaBean to hold the event information use for display
 */
public class OBMarketIndexInfo extends OBEventInfo {

	private String stockExchange;

	private double currentIndex;

	private double oldIndex;

	private String percentChange;

	private boolean isUp;

	public boolean isUp() {
		return isUp;
	}

	public void setUp(boolean up) {
		isUp = up;
	}

	public String getStockExchange() {
		return stockExchange;
	}

	public void setStockExchange(String stockExchange) {
		this.stockExchange = stockExchange;
	}

	public double getCurrentIndex() {
		return currentIndex;
	}

	public void setCurrentIndex(double currentIndex) {
		this.currentIndex = currentIndex;
	}

	public double getOldIndex() {
		return oldIndex;
	}

	public void setOldIndex(double oldIndex) {
		this.oldIndex = oldIndex;
	}

	public String getPercentChange() {
		return percentChange;
	}

	public void setPercentChange(String percentChange) {
		this.percentChange = percentChange;
	}

	public String toString() {
		StringBuffer buf = new StringBuffer();
		buf.append(super.toString()).append("; ");
		buf.append("stockExchange").append(" [").append(stockExchange).append("]; ");
		buf.append("currentIndex").append(" [").append(currentIndex).append("]; ");
		buf.append("oldIndex").append(" [").append(oldIndex).append("]");

		return buf.toString();
	}
}
