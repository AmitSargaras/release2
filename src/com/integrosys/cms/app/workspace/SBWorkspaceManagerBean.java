package com.integrosys.cms.app.workspace;

import javax.ejb.SessionContext;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.businfra.workflow.ITaskListSearchCriteria;
import com.integrosys.base.techinfra.logger.DefaultLogger;

public class SBWorkspaceManagerBean implements javax.ejb.SessionBean {

	private SessionContext m_sc;

	/**
	 * Default Constructor
	 */
	public SBWorkspaceManagerBean() {

	}

	public SearchResult getToDoList(ITaskListSearchCriteria criteria, String todoType) throws WorkspaceException {
		if (criteria == null) {
			throw new WorkspaceException("invalid parameter");
		}
		// return WorkspaceDAOFactory.getDAO().searchTransaction(criteria);
		// dao shld be selected based on todo-type
		CustodianTaskListDAO dao = new CustodianTaskListDAO();
		return dao.getTaskList(criteria);
	}

	public SearchResult getToTrackList(ITaskListSearchCriteria criteria, String trackType) throws WorkspaceException {
		if (criteria == null) {
			throw new WorkspaceException("invalid parameter");
		}
		DefaultLogger.info(this, "Not implemented yet!!");
		return null;
	}

	public void ejbCreate() {

	}

	public void ejbRemove() {

	}

	public void ejbActivate() {

	}

	public void ejbPassivate() {

	}

	public void setSessionContext(SessionContext sc) {
		this.m_sc = sc;
	}

}