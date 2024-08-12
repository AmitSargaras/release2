/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/diary/bus/IDiaryItemDAO.java,v 1.4 2004/06/29 10:03:55 jtan Exp $
 */
package com.integrosys.cms.app.systemBankBranch.bus;

import java.util.List;

import com.integrosys.base.businfra.search.SearchResult;

/**
 * @author  Abhijit R. 
 */
public interface ISystemBankBranchJdbc {
	SearchResult getAllSystemBankBranch (String searchBy,String searchText)throws SystemBankBranchException;
	SearchResult getAllSystemBankBranch()throws SystemBankBranchException;
	List getAllHUBBranchId()throws SystemBankBranchException;
	List getAllHUBBranchValue()throws SystemBankBranchException;
	SearchResult getAllSystemBankBranchSearch(String login)throws SystemBankBranchException;
	ISystemBankBranch listSystemBankBranch(long branchCode)throws SystemBankBranchException;
}
