/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/feed/bus/unittrust/IUnitTrustFeedGroup.java,v 1.2 2003/08/08 04:26:15 btchng Exp $
 */
package com.integrosys.cms.app.feed.bus.unittrust;

import java.util.Set;

import com.integrosys.base.techinfra.ejbsupport.IValueObject;

/**
 * 
 * @author $Author: btchng $
 * @version $Revision: 1.2 $
 * @since $Date: 2003/08/08 04:26:15 $ Tag: $Name: $
 * 
 */
public interface IUnitTrustFeedGroup extends java.io.Serializable, IValueObject {

	IUnitTrustFeedEntry[] getFeedEntries();

	Set getFeedEntriesSet();

	String getType();

	String getSubType();

	String getStockType();

	void setStockType(String stockType);

	long getUnitTrustFeedGroupID();

	void setFeedEntries(IUnitTrustFeedEntry[] param);

	void setFeedEntriesSet(Set feedEntriesSet);

	void setType(String param);

	void setSubType(String param);

	void setUnitTrustFeedGroupID(long param);

	long getVersionTime();

	void setVersionTime(long versionTime);
}
