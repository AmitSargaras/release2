/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/feed/bus/stock/IStockFeedGroup.java,v 1.2 2003/08/07 08:34:09 btchng Exp $
 */
package com.integrosys.cms.app.feed.bus.stock;

import java.util.Set;

import com.integrosys.base.techinfra.ejbsupport.IValueObject;

/**
 * 
 * @author $Author: btchng $
 * @version $Revision: 1.2 $
 * @since $Date: 2003/08/07 08:34:09 $ Tag: $Name: $
 * 
 */
public interface IStockFeedGroup extends java.io.Serializable, IValueObject {

	IStockFeedEntry[] getFeedEntries();

	Set getFeedEntriesSet();

	long getStockFeedGroupID();

	String getStockType();

	String getSubType();

	String getType();

	long getVersionTime();

	void setFeedEntries(IStockFeedEntry[] param);

	void setFeedEntriesSet(Set feedEntriesSet);

	void setStockFeedGroupID(long param);

	void setStockType(String param);

	void setSubType(String param);

	void setType(String param);

	void setVersionTime(long versionTime);
}
