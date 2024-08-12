/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/diary/bus/IDiaryItemDAO.java,v 1.4 2004/06/29 10:03:55 jtan Exp $
 */
package com.integrosys.cms.app.relationshipmgr.bus;

import java.util.List;
import java.util.Map;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.cms.ui.relationshipmgr.IRelationshipMgr;

/**
 * @author  Abhijit R. 
 */
public interface IRelationshipMgrJdbc {
	SearchResult getAllRelationshipMgr (String searchBy,String searchText)throws RelationshipMgrException;
	SearchResult getAllRelationshipMgr()throws RelationshipMgrException;
	List getAllRelationshipMgrSearch(String login)throws RelationshipMgrException;
	IRelationshipMgr listRelationshipMgr(long branchCode)throws RelationshipMgrException;
	
	boolean isPrevFileUploadPending() throws RelationshipMgrException;
	List getAllStageRelationshipMgr (String searchBy, String login)throws RelationshipMgrException;
	List getFileMasterList(String searchBy)throws RelationshipMgrException;
	
	public Map<String, String> fetchRMData(String rmEmpCode);
	
	public boolean isValidRMEmpCode(String rmEmpCode);
}
