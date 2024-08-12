/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/diary/bus/IDiaryItemDAO.java,v 1.4 2004/06/29 10:03:55 jtan Exp $
 */
package com.integrosys.cms.app.caseBranch.bus;

import java.util.List;

import com.integrosys.base.businfra.search.SearchResult;

/**
 * @author  Abhijit R. 
 */
public interface ICaseBranchJdbc {
	SearchResult getAllCaseBranch (String searchBy,String searchText)throws CaseBranchException;
	SearchResult getAllCaseBranch()throws CaseBranchException;
	SearchResult getAllFilteredCaseBranch(String code,String name)throws CaseBranchException;
	List getAllCaseBranchSearch(String login)throws CaseBranchException;
	ICaseBranch listCaseBranch(long branchCode)throws CaseBranchException;
	public List getCaseBranchListForYear(long year);	
	public List getRecurrentCaseBranchListForYear(long year);	
}
