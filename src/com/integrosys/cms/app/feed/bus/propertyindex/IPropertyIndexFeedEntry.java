/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/feed/bus/propertyindex/IPropertyIndexFeedEntry.java,v 1.3 2003/08/20 12:38:21 btchng Exp $
 */
package com.integrosys.cms.app.feed.bus.propertyindex;

import java.util.Date;

import com.integrosys.base.techinfra.ejbsupport.IValueObject;

/**
 * 
 * @author $Author: btchng $
 * @version $Revision: 1.3 $
 * @since $Date: 2003/08/20 12:38:21 $ Tag: $Name: $
 * 
 */
public interface IPropertyIndexFeedEntry extends java.io.Serializable, IValueObject {

	String getType();

	String getRegion();

	String getCountryCode();

	double getUnitPrice();

	Date getLastUpdatedDate();

	long getPropertyIndexFeedEntryID();

	long getPropertyIndexFeedEntryRef();

	long getVersionTime();

	boolean isDeletedInd();

	void setType(String type);

	void setRegion(String region);

	void setCountryCode(String countryCode);

	void setUnitPrice(double unitPrice);

	void setLastUpdatedDate(Date lastUpdatedDate);

	void setPropertyIndexFeedEntryID(long param);

	void setPropertyIndexFeedEntryRef(long param);

	void setVersionTime(long versionTime);

	void setDeletedInd(boolean deletedInd);
}
