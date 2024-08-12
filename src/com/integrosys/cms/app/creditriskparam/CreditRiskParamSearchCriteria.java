/**
 *
 * Copyright Integro Technologies Pte Ltd
 * $Header$
 *
 * CreditRiskParamSearchCriteria
 *
 * Created on 1:56:17 PM
 *
 * Purpose:
 * Description:
 *
 * @author $Author$<br>
 * @version $Revision$
 * @since $Date$
 * Tag: $Name$
 */
package com.integrosys.cms.app.creditriskparam;

import com.integrosys.base.businfra.search.SearchCriteria;

/**
 * Created by IntelliJ IDEA. User: Eric Date: Feb 16, 2007 Time: 1:56:17 PM
 */
public class CreditRiskParamSearchCriteria extends SearchCriteria {

	private String groupSubType;

	private String groupStockType;

	private int currentIndex;

	public String getGroupSubType() {
		return groupSubType;
	}

	public void setGroupSubType(String groupSubType) {
		this.groupSubType = groupSubType;
	}

	public String getGroupStockType() {
		return groupStockType;
	}

	public void setGroupStockType(String groupStockType) {
		this.groupStockType = groupStockType;
	}

	public int getCurrentIndex() {
		return currentIndex;
	}

	public void setCurrentIndex(int currentIndex) {
		this.currentIndex = currentIndex;
	}

}
