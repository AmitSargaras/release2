package com.integrosys.cms.app.workspace;

import java.rmi.RemoteException;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.businfra.workflow.ITaskListSearchCriteria;

public interface SBWorkspaceManager extends javax.ejb.EJBObject {

	public SearchResult getToDoList(ITaskListSearchCriteria criteria, String todoType) throws WorkspaceException,
			RemoteException;

	public SearchResult getToTrackList(ITaskListSearchCriteria criteria, String trackType) throws WorkspaceException,
			RemoteException;

}