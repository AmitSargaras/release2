/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/diary/bus/IDiaryItemDAO.java,v 1.4 2004/06/29 10:03:55 jtan Exp $
 */
package com.integrosys.cms.app.discrepency.bus;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.integrosys.base.businfra.search.SearchDAOException;
import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.cms.app.transaction.CMSTrxSearchCriteria;

/**
 * @author  Abhijit R. 
 */
public interface IDiscrepancyJdbc {
	
	public SearchResult searchTransactions(CMSTrxSearchCriteria criteria) throws SearchDAOException, RemoteException;
	public SearchResult searchTransactionsByCriteria(CMSTrxSearchCriteria criteria) throws SearchDAOException, RemoteException;
	public HashMap searchBulkTransactions(CMSTrxSearchCriteria criteria,long customerId) throws SearchDAOException, RemoteException;
	public SearchResult sortTransactions(CMSTrxSearchCriteria criteria) throws SearchDAOException, RemoteException;
	public ArrayList listDiscrepancy(long customerId) throws SearchDAOException;
	public List<Long> getDeferralIdsForValuation2(long custId);
}
