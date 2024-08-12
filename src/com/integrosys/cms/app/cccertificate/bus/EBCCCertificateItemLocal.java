/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/cccertificate/bus/EBCCCertificateItemLocal.java,v 1.1 2004/01/13 06:21:45 hltan Exp $
 */
package com.integrosys.cms.app.cccertificate.bus;

import javax.ejb.EJBLocalObject;

/**
 * This is the local interface to the EBCCCertificateItem entity bean.
 * 
 * @author $Author: hltan $
 * @version $Revision: 1.1 $
 * @since $Date: 2004/01/13 06:21:45 $ Tag: $Name: $
 */
public interface EBCCCertificateItemLocal extends EJBLocalObject {
	/**
	 * Return the sc certificate ID
	 * @return Long - the sc certificate ID
	 */
	public Long getCMPCCCertID();

	/**
	 * Return the ccc item ID of the ccc item
	 * @return long - the ccc item ID
	 */
	public long getCCCertItemID();

	/**
	 * Return the ccc item reference of the ccc item
	 * @return long - the ccc item reference
	 */
	public long getCCCertItemRef();

	/**
	 * Return an object representation of the ccc item information.
	 * 
	 * @return ICCCertificateItem
	 */
	public ICCCertificateItem getValue();

	/**
	 * Persist a ccc item information
	 * 
	 * @param value is of type ICCCertificateItem
	 */
	public void setValue(ICCCertificateItem value);

	/**
	 * Set the item as deleted
	 * @param anIsDeletedInd of boolean type
	 */
	public void setIsDeletedInd(boolean anIsDeletedInd);

	/**
	 * Get the item as deleted
	 * @return boolean - the delete indicator
	 */
	public boolean getIsDeletedInd();
}
