package com.integrosys.cms.app.otherbank.bus;

import java.io.Serializable;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.cms.app.fileInsertMapper.bus.IFileMapperId;
import com.integrosys.cms.app.otherbank.trx.IOtherBankTrxValue;
import com.integrosys.cms.app.otherbranch.bus.OtherBranchException;
import com.integrosys.cms.app.otherbranch.trx.IOtherBankBranchTrxValue;
import com.integrosys.cms.ui.otherbankbranch.IOtherBranch;

/** 
 * Defines methods for operation on other bank
 * @author $Author: Dattatray Thorat $
 * @version $Revision: 1.0 $
 * @since $Date: 2011-02-18 17:27:01 +0800 (Fri, 18 Feb 2011) $
 * Tag : $Name$
 */

public interface IOtherBranchDAO {
	
	public final static String ACTUAL_ENTITY_NAME = "actualOtherBranch";
	
	public final static String STAGING_ENTITY_NAME = "stagingOtherBranch";
	
	public SearchResult getOtherBranch() throws OtherBranchException;
	
	public SearchResult getOtherBranchList(String searchType,String searchVal) throws OtherBranchException;
	
	public IOtherBranch getOtherBranchById(long id) throws OtherBranchException ;
	 
	public IOtherBranch updateOtherBranch(IOtherBranch OtherBranch) throws OtherBranchException;
	
	public IOtherBranch deleteOtherBranch(IOtherBranch OtherBranch) throws OtherBranchException;
	
	public IOtherBranch createOtherBranch(IOtherBranch OtherBranch) throws OtherBranchException;
	
	IOtherBranch getOtherBranch(String entityName, Serializable key)
		throws OtherBranchException;

	IOtherBranch updateOtherBranch(String entityName, IOtherBranch item)
		throws OtherBranchException;

	IOtherBranch createOtherBranch(String entityName, IOtherBranch systemBank)
		throws OtherBranchException;


}
