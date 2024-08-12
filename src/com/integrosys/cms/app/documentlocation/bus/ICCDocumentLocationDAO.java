/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/documentlocation/bus/ICCDocumentLocationDAO.java,v 1.3 2004/04/06 09:22:38 hltan Exp $
 */
package com.integrosys.cms.app.documentlocation.bus;

import com.integrosys.base.businfra.search.SearchDAOException;

/**
 * This interface defines the constant specific to the document location table
 * and the methods required by the cc document location
 * 
 * @author $Author: hltan $<br>
 * @version $Revision: 1.3 $
 * @since $Date: 2004/04/06 09:22:38 $ Tag: $Name: $
 */
public interface ICCDocumentLocationDAO extends ICCDocumentLocationTableConstants {
	/**
	 * To get the number of cc document location that satisfy the criteria
	 * @param aCriteria of CCDocumentLocationSearchCriteria type
	 * @return int - the number of CC document location that satisfy the
	 *         criteria
	 * @throws SearchDAOException on errors
	 */
	public int getNoOfCCDocumentLocation(CCDocumentLocationSearchCriteria aCriteria) throws SearchDAOException;

	/**
	 * Get the list of cc document location based on the criteria
	 * @param anOwnerType of String type
	 * @param aLimitProfileID of long type
	 * @param anOwnerID of long type
	 * @return ICCDocumentLocation[] - the list of cc document location
	 * @throws SearchDAOException on error
	 */
	public ICCDocumentLocation[] getCCDocumentLocation(String anOwnerType, long aLimitProfileID, long anOwnerID)
			throws SearchDAOException;
}
