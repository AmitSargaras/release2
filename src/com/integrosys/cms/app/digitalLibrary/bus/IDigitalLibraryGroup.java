/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/feed/bus/bond/IBondFeedGroup.java,v 1.2 2003/08/06 08:10:08 btchng Exp $
 */
package com.integrosys.cms.app.digitalLibrary.bus;

import java.util.Set;

import com.integrosys.base.techinfra.ejbsupport.IValueObject;

/**
 * 
 * @author btchng
 * @author Chong Jun Yong
 * @since 2003/08/06
 * 
 */
public interface IDigitalLibraryGroup extends java.io.Serializable, IValueObject {

	long getDigitalLibraryGroupID();

	IDigitalLibraryEntry[] getFeedEntries();

	Set getFeedEntriesSet();

	String getStockType();

	String getSubType();

	String getType();

	long getVersionTime();

	void setDigitalLibraryGroupID(long param);

	void setFeedEntries(IDigitalLibraryEntry[] param);

	void setFeedEntriesSet(Set feedEntriesSet);

	void setStockType(String stockType);

	void setSubType(String param);

	void setType(String param);

	void setVersionTime(long versionTime);
}
