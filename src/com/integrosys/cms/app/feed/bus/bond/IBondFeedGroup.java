/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/feed/bus/bond/IBondFeedGroup.java,v 1.2 2003/08/06 08:10:08 btchng Exp $
 */
package com.integrosys.cms.app.feed.bus.bond;

import java.util.Set;

import com.integrosys.base.techinfra.ejbsupport.IValueObject;

/**
 * 
 * @author btchng
 * @author Chong Jun Yong
 * @since 2003/08/06
 * 
 */
public interface IBondFeedGroup extends java.io.Serializable, IValueObject {

	long getBondFeedGroupID();

	IBondFeedEntry[] getFeedEntries();

	Set getFeedEntriesSet();

	String getStockType();

	String getSubType();

	String getType();

	long getVersionTime();

	void setBondFeedGroupID(long param);

	void setFeedEntries(IBondFeedEntry[] param);

	void setFeedEntriesSet(Set feedEntriesSet);

	void setStockType(String stockType);

	void setSubType(String param);

	void setType(String param);

	void setVersionTime(long versionTime);
}
