/* Copyright Integro Technologies Pte Ltd
 * com.integrosys.cms.app.transaction.CMSTransactionDAOXA.java Created on Jun 17, 2004 6:17:29 PM
 *
 */

package com.integrosys.cms.app.transaction;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.integrosys.base.businfra.search.SearchDAOException;
import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.common.util.dataaccess.DAOContext;
import com.integrosys.cms.app.common.util.dataaccess.DataAccessException;
import com.integrosys.cms.app.common.util.dataaccess.SearchingParameters;

/**
 * @since Jun 17, 2004
 * @author heju
 * @version 1.0.0
 */
public class CMSTransactionDAOXA implements ICMSTransactionDAO {
	private ICMSTransactionDAO passDAO;

	private CMSTransactionExtDAO extDAO;

	public ICMSTransactionDAO getPassDAO() {
		return passDAO;
	}

	public void setPassDAO(ICMSTransactionDAO passDAO) {
		this.passDAO = passDAO;
	}

	public CMSTransactionExtDAO getExtDAO() {
		return extDAO;
	}

	public void setExtDAO(CMSTransactionExtDAO extDAO) {
		this.extDAO = extDAO;
	}

	public SearchResult searchPendingCases(CMSTrxSearchCriteria criteria) throws SearchDAOException {
		return new SearchResult(0, 0, 0, new ArrayList());
	}

	public Collection searchNextRouteList(CMSTrxSearchCriteria criteria) throws SearchDAOException {
		return passDAO.searchNextRouteList(criteria);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.integrosys.cms.app.transaction.ICMSTransactionDAO#searchTransactions
	 * (com.integrosys.cms.app.transaction.CMSTrxSearchCriteria)
	 */
	public SearchResult searchTransactions(CMSTrxSearchCriteria criteria) throws SearchDAOException {
		DAOContext daoContext = null;
		try {
			CMSTrxSearchCriteriaParameterizer parizer = new CMSTrxSearchCriteriaParameterizer();
			SearchingParameters searching = parizer.map(criteria);
			DefaultLogger.debug(this, searching);
			daoContext = new DAOContext();
			Collection retcol = extDAO.searchToDoList(daoContext, searching);
			return new SearchResult(criteria.getStartIndex(), 0, retcol.size(), retcol);
		}
		catch (DataAccessException e) {
			throw new SearchDAOException("Failed on Searching Transaction", e.fillInStackTrace());
		}
		finally {
			if (daoContext != null) {
				daoContext.close();
			}
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.integrosys.cms.app.transaction.ICMSTransactionDAO#getTransactionLogs
	 * (java.lang.String)
	 */
	public Collection getTransactionLogs(String transactionID) throws SearchDAOException {
		return passDAO.getTransactionLogs(transactionID);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.integrosys.cms.app.transaction.ICMSTransactionDAO#getTransactionLogs
	 * (long)
	 */
	public Collection getTransactionLogs(long transactionID) throws SearchDAOException {
		// TODO Auto-generated method stub
		return passDAO.getTransactionLogs(transactionID);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seecom.integrosys.cms.app.transaction.ICMSTransactionDAO#
	 * searchWorkflowTransactions
	 * (com.integrosys.cms.app.transaction.CMSTrxSearchCriteria)
	 */
	public SearchResult searchWorkflowTransactions(CMSTrxSearchCriteria criteria) throws SearchDAOException {
		return passDAO.searchWorkflowTransactions(criteria);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.integrosys.cms.app.transaction.ICMSTransactionDAO#getTransactionCount
	 * (com.integrosys.cms.app.transaction.CMSTrxSearchCriteria)
	 */
	public int getTransactionCount(CMSTrxSearchCriteria criteria) throws SearchDAOException {
		// return passDAO.getTransactionCount(criteria);
		DAOContext daoContext = null;
		try {
			CMSTrxSearchCriteriaParameterizer parizer = new CMSTrxSearchCriteriaParameterizer();
			SearchingParameters searching = parizer.map(criteria);
			DefaultLogger.debug(this, searching);
			daoContext = new DAOContext();
			int count = extDAO.getTransactionCount(daoContext, searching);
			return count;
		}
		catch (DataAccessException e) {
			throw new SearchDAOException("Failed on Searching Transaction", e.fillInStackTrace());
		}
		finally {
			if (daoContext != null) {
				daoContext.close();
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.integrosys.cms.app.transaction.ICMSTransactionDAO#getAllTransactionCount
	 * (com.integrosys.cms.app.transaction.CMSTrxSearchCriteria)
	 */
	public int getAllTransactionCount(CMSTrxSearchCriteria criteria) throws SearchDAOException {

		return passDAO.getAllTransactionCount(criteria);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.integrosys.cms.app.transaction.ICMSTransactionDAO#getWorkflowTrxCount
	 * (com.integrosys.cms.app.transaction.CMSTrxSearchCriteria)
	 */
	public int getWorkflowTrxCount(CMSTrxSearchCriteria criteria) throws SearchDAOException {
		return passDAO.getWorkflowTrxCount(criteria);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.integrosys.cms.app.transaction.ICMSTransactionDAO#getLPTodoList()
	 */
	public Collection getLPTodoList() throws SearchDAOException {
		return passDAO.getLPTodoList();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.integrosys.cms.app.transaction.ICMSTransactionDAO#getLPTodoList()
	 */
	public String getTrxSubTypeByTrxID(long transactionID) throws SearchDAOException {
		return passDAO.getTrxSubTypeByTrxID(transactionID);
	}

	/*
	 * Test ONLY
	 * 
	 * @author heju
	 */
	public static void main(String[] argv) throws Exception {
		CMSTrxSearchCriteria criteria = new CMSTrxSearchCriteria();
		criteria.setSearchIndicator(com.integrosys.cms.app.common.constant.ICMSConstant.TODO_ACTION);
		criteria.setOnlyMembershipRecords(true);
		criteria.setTeamTypeMembershipID(5);
		criteria.setAllowedCountries(new String[] { "SG", "HK" });
		criteria.setAllowedOrganisations(new String[] { "SCBL", "SCMY" });
		criteria.setAllowedSegments(new String[] { "30", "40" });

		criteria.setCurrentState(true);
		criteria.setCurrentState("New");

		// criteria.setTransactionTypes(new String[]{"LIMIT","DEAL"});
		criteria.setLimitProfileID(new Long(20031127001463L));

		criteria.setStartIndex(5);
		criteria.setNItems(50);

		CMSTransactionDAO dao = new CMSTransactionDAO();
		// dao.searchTransactions(criteria);
		criteria.setSearchIndicator(com.integrosys.cms.app.common.constant.ICMSConstant.TOTRACK_ACTION);
		dao.searchTransactions(criteria);
	}

	public List retrieveListOfTransactionIdsByReferenceIdsAndType(List referenceIdList, String transactionType) {
		return passDAO.retrieveListOfTransactionIdsByReferenceIdsAndType(referenceIdList, transactionType);
	}

	public List getTransactionTypeList(String teamID) throws SearchDAOException {
		// TODO Auto-generated method stub
		return null;
	}

}
