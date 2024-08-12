/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/transaction/CMSTransactionUtil.java,v 1.1 2005/10/25 12:05:25 wltan Exp $
 */
package com.integrosys.cms.app.transaction;

/**
 * DAO for collateral.
 * 
 * @author $Author: wltan $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2005/10/25 12:05:25 $
 */
public abstract class CMSTransactionUtil {

	/**
	 * Helper method to check if to be sorted by legal name.
	 * 
	 * @param criteria - CMSTrxSearchCrtieria
	 * @return boolean
	 */
	public static boolean isSortedByLegalName(CMSTrxSearchCriteria criteria) {
		return ((criteria.getLegalID() != null) ||
		// (criteria.getLegalID() != null &&
		// criteria.getLegalName().trim().length() != 0) ||
				((criteria.getLegalName() != null) && (criteria.getLegalName().trim().length() != 0)) || ((criteria
				.getCustomerName() != null) && (criteria.getCustomerName().trim().length() != 0)));
	}
}