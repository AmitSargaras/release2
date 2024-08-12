/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/transaction/CMSTransactionDAOFactory.java,v 1.4 2004/07/06 07:43:20 jhe Exp $
 */
package com.integrosys.cms.app.transaction;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.integrosys.base.techinfra.context.BeanHouse;

/**
 * This factory class loading ICMSTransactionDAO implementations.
 * 
 * @author jhe
 * @author Chong Jun Yong
 * @since 2004/07/06
 */
public final class CMSTransactionDAOFactory {

	private static Map daoMap;

	static {
		daoMap = Collections.synchronizedMap(new HashMap());

		daoMap.put("CMSTransactionDAOXA", new CMSTransactionDAOXA());
		daoMap.put("CMSTransactionExtDAO", new CMSTransactionExtDAO());
	}

	/**
	 * Get a Singleton instance of CMS Transaction DAO implementation. The CMS
	 * Transaction DAO binding object will always get cleared before return it
	 * to caller.
	 * 
	 * @return a singleton instance of CMSTransactionDAO.
	 */
	public static ICMSTransactionDAO getDAO() {
		return (ICMSTransactionDAO) BeanHouse.get("transactionJdbcDao");
	}

	/**
	 * Added for DAOXA load
	 * @since 20/06/2004
	 * @param criteria
	 * @return
	 */
	public static ICMSTransactionDAO getDAO(CMSTrxSearchCriteria criteria) {
		if (CMSTransactionExtDAO.authLevel(criteria) > 0) {
			return getDAOXA();
		}

		return getDAO();
	}

	public static ICMSTransactionDAO getDAOXA() {
		CMSTransactionDAOXA daoXa = (CMSTransactionDAOXA) daoMap.get("CMSTransactionDAOXA");
		daoXa.setPassDAO(getDAO());
		daoXa.setExtDAO((CMSTransactionExtDAO) daoMap.get("CMSTransactionExtDAO"));
		return daoXa;
	}
}