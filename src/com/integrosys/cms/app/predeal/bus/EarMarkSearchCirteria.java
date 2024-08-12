/**
 *
 * Copyright Integro Technologies Pte Ltd
 * $Header$
 *
 * EarMarkSearchCirteria
 *
 * Created on 5:04:00 PM
 *
 * Purpose:
 * Description:
 *
 * @author $Author$<br>
 * @version $Revision$
 * @since $Date$
 * Tag: $Name$
 */
package com.integrosys.cms.app.predeal.bus;

import com.integrosys.base.businfra.search.SearchCriteria;

/**
 * Created by IntelliJ IDEA. User: Eric Date: Mar 23, 2007 Time: 5:04:00 PM
 */
public class EarMarkSearchCirteria extends SearchCriteria {
	private String feedId;

	private String earMarkId;

	public String getFeedId() {
		return feedId;
	}

	public void setFeedId(String feedId) {
		this.feedId = feedId;
	}

	public String getEarMarkId() {
		return earMarkId;
	}

	public void setEarMarkId(String earMarkId) {
		this.earMarkId = earMarkId;
	}

}
