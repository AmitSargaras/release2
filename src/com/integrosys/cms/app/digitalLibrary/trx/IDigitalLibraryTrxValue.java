/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/feed/trx/bond/IDigitalLibraryGroupTrxValue.java,v 1.1 2003/08/06 08:10:08 btchng Exp $
 */
package com.integrosys.cms.app.digitalLibrary.trx;

import com.integrosys.cms.app.digitalLibrary.bus.IDigitalLibraryGroup;
import com.integrosys.cms.app.transaction.ICMSTrxValue;

/**
 * This interface represents a CheckList trx value.
 * 
 * @author $Author: btchng $
 * @version $Revision: 1.1 $
 * @since $Date: 2003/08/06 08:10:08 $ Tag: $Name: $
 */
public interface IDigitalLibraryTrxValue extends ICMSTrxValue {

	/**
	 * Get the IDigitalLibraryGroup busines entity
	 * 
	 * @return IDigitalLibraryGroup
	 */
	public IDigitalLibraryGroup getDigitalLibraryGroup();

	/**
	 * Get the staging IDigitalLibraryGroup business entity
	 * 
	 * @return ICheckList
	 */
	public IDigitalLibraryGroup getStagingDigitalLibraryGroup();

	/**
	 * Set the IDigitalLibraryGroup busines entity
	 * 
	 * @param value is of type IDigitalLibraryGroup
	 */
	public void setDigitalLibraryGroup(IDigitalLibraryGroup value);

	/**
	 * Set the staging IDigitalLibraryGroup business entity
	 * 
	 * @param value is of type IDigitalLibraryGroup
	 */
	public void setStagingDigitalLibraryGroup(IDigitalLibraryGroup value);
}