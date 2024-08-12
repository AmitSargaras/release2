/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/diary/bus/IDiaryItemDAO.java,v 1.4 2004/06/29 10:03:55 jtan Exp $
 */
package com.integrosys.cms.app.fccBranch.bus;

import com.integrosys.base.businfra.search.SearchResult;

/****
 * 
 * @author komal.agicha
 *
 */
public interface IFCCBranchJdbc {
	SearchResult getAllFCCBranch (String searchBy,String searchText)throws FCCBranchException;
	SearchResult getAllFCCBranch()throws FCCBranchException;
	IFCCBranch findBranchByBranchCode(String branchCode) throws Exception;
}
