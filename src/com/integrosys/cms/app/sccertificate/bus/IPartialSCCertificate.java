/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/sccertificate/bus/IPartialSCCertificate.java,v 1.2 2006/05/30 10:19:38 czhou Exp $
 */
package com.integrosys.cms.app.sccertificate.bus;

/**
 * This interface defines the list of attributes that is required for partial
 * SCC
 * 
 * @author $Author: czhou $<br>
 * @version $Revision: 1.2 $
 * @since $Date: 2006/05/30 10:19:38 $ Tag: $Name: $
 */
public interface IPartialSCCertificate extends ISCCertificate {
	/**
	 * Set the list of Partial SCC items
	 * @return IPartialSCCertificateItem[] - the list of partial SCC items
	 */
	public IPartialSCCertificateItem[] getPartialSCCItemList();

	/**
	 * Get the list of Clean Partial SCC Items
	 * @return Clean Partial SCC Items
	 */
	public IPartialSCCertificateItem[] getCleanPSCCItemList();

	/**
	 * Get the list of Not Clean Partial SCC Items
	 * @return Not Clean Partial SCC Items
	 */
	public IPartialSCCertificateItem[] getNotCleanPSCCItemList();

}
