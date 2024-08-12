/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/sccertificate/bus/EBSCCertificateItemLocal.java,v 1.1 2003/08/06 05:28:54 hltan Exp $
 */
package com.integrosys.cms.app.sccertificate.bus;

import javax.ejb.EJBLocalObject;

/**
 * This is the local interface to the EBSCCertificateItem entity bean.
 * 
 * @author $Author: hltan $
 * @version $Revision: 1.1 $
 * @since $Date: 2003/08/06 05:28:54 $ Tag: $Name: $
 */
public interface EBSCCertificateItemLocal extends EJBLocalObject {
	/**
	 * Return the sc certificate ID
	 * @return Long - the sc certificate ID
	 */
	public Long getCMPSCCertID();

	/**
	 * Return the scc item ID of the scc item
	 * @return long - the scc item ID
	 */
	public long getSCCertItemID();

	/**
	 * Return the scc item reference of the scc item
	 * @return long - the scc item reference
	 */
	public long getSCCertItemRef();

	/**
	 * Return an object representation of the scc item information.
	 * 
	 * @return ISCCertificateItem
	 */
	public ISCCertificateItem getValue();

	/**
	 * Persist a scc item information
	 * 
	 * @param value is of type ISCCertificateItem
	 */
	public void setValue(ISCCertificateItem value);

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
