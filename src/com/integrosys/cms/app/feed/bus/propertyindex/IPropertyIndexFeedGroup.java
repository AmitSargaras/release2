/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/feed/bus/propertyindex/IPropertyIndexFeedGroup.java,v 1.1 2003/08/20 10:59:05 btchng Exp $
 */
package com.integrosys.cms.app.feed.bus.propertyindex;

import com.integrosys.base.techinfra.ejbsupport.IValueObject;

/**
 * 
 * @author $Author: btchng $
 * @version $Revision: 1.1 $
 * @since $Date: 2003/08/20 10:59:05 $ Tag: $Name: $
 * 
 */
public interface IPropertyIndexFeedGroup extends java.io.Serializable, IValueObject {

	IPropertyIndexFeedEntry[] getFeedEntries();

	String getType();

	String getSubType();

	long getPropertyIndexFeedGroupID();

	void setFeedEntries(IPropertyIndexFeedEntry[] param);

	void setType(String param);

	void setSubType(String param);

	void setPropertyIndexFeedGroupID(long param);

	long getVersionTime();

	void setVersionTime(long versionTime);
}
