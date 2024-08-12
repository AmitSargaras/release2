/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/commodity/main/bus/CommodityMainInfoSearchResult.java,v 1.2 2004/06/04 04:52:40 hltan Exp $
 */
package com.integrosys.cms.app.commodity.main.bus;

import java.util.Collection;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.techinfra.util.AccessorUtil;

/**
 * Created by IntelliJ IDEA. User: Administrator Date: Apr 5, 2004 Time:
 * 10:26:35 AM To change this template use File | Settings | File Templates.
 */
public class CommodityMainInfoSearchResult extends SearchResult {

	public CommodityMainInfoSearchResult() {
		super();
	}

	public void setResultList(Collection collection) {
		this.m_ResultList = collection;
	}

	public void setStartIndex(int i) {
		this.m_startIndex = i;
	}

	public void setCurrentIndex(int i) {
		this.m_currentIndex = i;
	}

	public void setNItems(int i) {
		this.m_nItems = i;
	}

	public boolean equals(Object o) {
		return this.toString().equals(o.toString());
	}

	public String toString() {
		return AccessorUtil.printMethodValue(this);
	}

}
