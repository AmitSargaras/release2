/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/feed/bus/bond/IBondFeedGroup.java,v 1.2 2003/08/06 08:10:08 btchng Exp $
 */
package com.integrosys.cms.app.feed.bus.mutualfunds;

import java.util.Set;

import com.integrosys.base.techinfra.ejbsupport.IValueObject;

/**
 * 
* @author $Author: Dattatray Thorat $
 * @version $Revision: 1.4 $
 * @since $Date: 2011/05/10 10:45:20 $ Tag: $Name%
 */
public interface IMutualFundsFeedGroup extends java.io.Serializable, IValueObject {

	long getMutualFundsFeedGroupID();

	IMutualFundsFeedEntry[] getFeedEntries();

	Set getFeedEntriesSet();

	long getVersionTime();

	void setMutualFundsFeedGroupID(long param);

	void setFeedEntries(IMutualFundsFeedEntry[] param);

	void setFeedEntriesSet(Set feedEntriesSet);

	void setVersionTime(long versionTime);
	
	String getStockType();

	String getSubType();

	String getType();
	
	void setStockType(String stockType);

	void setSubType(String param);

	void setType(String param);
}
