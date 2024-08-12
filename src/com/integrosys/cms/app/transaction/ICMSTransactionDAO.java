/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/transaction/ICMSTransactionDAO.java,v 1.9 2004/07/04 10:24:20 jhe Exp $
 */
package com.integrosys.cms.app.transaction;

import java.util.Collection;
import java.util.List;

import com.integrosys.base.businfra.search.SearchDAOException;
import com.integrosys.base.businfra.search.SearchResult;

/**
 * DAO for collateral.
 * 
 * @author $Author: jhe $<br>
 * @version $Revision: 1.9 $
 * @since $Date: 2004/07/04 10:24:20 $ Tag: $Name: $
 */
public interface ICMSTransactionDAO {
	/**
	 * Get all transactions for the given criteria
	 * 
	 * @return search resule
	 * @throws SearchDAOException on error searching
	 * 
	 */
	/*
	 * searchNextRouteList added since 20/05/2004
	 */
	public Collection searchNextRouteList(CMSTrxSearchCriteria criteria) throws SearchDAOException;

	public Collection getTransactionLogs(String transactionID) throws SearchDAOException;

	public Collection getTransactionLogs(long transactionID) throws SearchDAOException;

	public SearchResult searchTransactions(CMSTrxSearchCriteria criteria) throws SearchDAOException;

	public SearchResult searchWorkflowTransactions(CMSTrxSearchCriteria criteria) throws SearchDAOException;

	public SearchResult searchPendingCases(CMSTrxSearchCriteria criteria) throws SearchDAOException;

	public int getTransactionCount(CMSTrxSearchCriteria criteria) throws SearchDAOException;

	public int getAllTransactionCount(CMSTrxSearchCriteria criteria) throws SearchDAOException;

	public int getWorkflowTrxCount(CMSTrxSearchCriteria criteria) throws SearchDAOException;

	public Collection getLPTodoList() throws SearchDAOException;

	public String getTrxSubTypeByTrxID(long transactionID) throws SearchDAOException;
	
	public List getTransactionTypeList(String teamID) throws SearchDAOException;

	/**
	 * Retrieve list of Transaction Ids by list of reference ids and it's
	 * transaction type
	 * @param referenceIdList list of reference ids
	 * @param transactionType transaction type for the reference id
	 * @return list of transaction ids (in <tt>String</tt> type)
	 */
	public List retrieveListOfTransactionIdsByReferenceIdsAndType(List referenceIdList, String transactionType);
}
