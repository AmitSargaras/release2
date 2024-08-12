/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/feed/bus/stockindex/IStockIndexFeedGroup.java,v 1.1 2003/08/18 09:56:42 btchng Exp $
 */
package com.integrosys.cms.app.feed.bus.stockindex;

import java.util.Set;

import com.integrosys.base.techinfra.ejbsupport.IValueObject;

/**
 * 
 * @author $Author: btchng $
 * @version $Revision: 1.1 $
 * @since $Date: 2003/08/18 09:56:42 $ Tag: $Name: $
 * 
 */
public interface IStockIndexFeedGroup extends java.io.Serializable, IValueObject {

	IStockIndexFeedEntry[] getFeedEntries();

	String getType();

	String getSubType();

	long getStockIndexFeedGroupID();

	void setFeedEntries(IStockIndexFeedEntry[] param);

	Set getFeedEntriesSet();

	void setType(String param);

	void setSubType(String param);

	void setStockIndexFeedGroupID(long param);

	void setFeedEntriesSet(Set feedEntriesSet);

	long getVersionTime();

	void setVersionTime(long versionTime);
}
