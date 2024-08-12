/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/diary/bus/IDiaryItemDAO.java,v 1.4 2004/06/29 10:03:55 jtan Exp $
 */
package com.integrosys.cms.app.caseCreationUpdate.bus;

import java.util.List;

import com.integrosys.base.businfra.search.SearchResult;

/**
 * @author  Abhijit R. 
 */
public interface ICaseCreationJdbc {
	SearchResult getAllCaseCreation (String searchBy,String searchText)throws CaseCreationException;
	SearchResult getAllCaseCreation(long id)throws CaseCreationException;
	SearchResult getAllCaseCreation()throws CaseCreationException;
	SearchResult getAllCaseCreationBranchMenu(String branchCode)throws CaseCreationException;
	SearchResult getAllCaseCreationBranchMenuSearch(String partyName , String caseId, String region , String segment, String status, String branchCode)throws CaseCreationException;
	SearchResult getAllCaseCreationCPUTMenuSearch(String partyName , String caseId, String region , String segment, String status)throws CaseCreationException;
	List getAllCaseCreationSearch(String login)throws CaseCreationException;
	ICaseCreation listCaseCreation(long branchCode)throws CaseCreationException;
	public List getCaseCreationListForYear(long year);	
	public List getRecurrentCaseCreationListForYear(long year);	
}
