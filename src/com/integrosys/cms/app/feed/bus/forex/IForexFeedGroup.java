/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/feed/bus/forex/IForexFeedGroup.java,v 1.1 2003/07/23 11:11:44 phtan Exp $
 */
package com.integrosys.cms.app.feed.bus.forex;

import java.util.Set;

import com.integrosys.base.techinfra.ejbsupport.IValueObject;

/**
 * 
 * @author $Author: phtan $
 * @version $Revision: 1.1 $
 * @since $Date: 2003/07/23 11:11:44 $ Tag: $Name: $
 * 
 */
public interface IForexFeedGroup extends java.io.Serializable, IValueObject {
	IForexFeedEntry[] getFeedEntries();

	Set getFeedEntriesSet();

	long getForexFeedGroupID();

	String getSubType();

	String getType();

	long getVersionTime();

	void setFeedEntries(IForexFeedEntry[] param);

	/**
	 * <p>
	 * Persistence field to deal with feed entries array. <b>Not</b> supposed to
	 * be called in application.
	 * 
	 * <p>
	 * To be called in two places at most.
	 * <ol>
	 * <li>when doing replication to create staging copy
	 * <li>retrieving data from persistence storage.
	 * </ol>
	 * 
	 * @param feedEntriesSet feed entries set
	 */
	void setFeedEntriesSet(Set feedEntriesSet);

	void setForexFeedGroupID(long param);

	void setSubType(String param);

	void setType(String param);

	void setVersionTime(long versionTime);
	
	void setParentGroupCodeList(Set parentGroupCodeList);
}
