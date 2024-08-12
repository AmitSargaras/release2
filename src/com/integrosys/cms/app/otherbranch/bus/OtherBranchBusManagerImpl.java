package com.integrosys.cms.app.otherbranch.bus;

import java.util.ArrayList;
import java.util.List;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;
import com.integrosys.cms.app.fileInsertMapper.bus.IFileMapperId;
import com.integrosys.cms.app.fileInsertMapper.bus.IFileMapperMaster;
import com.integrosys.cms.app.fileInsertMapper.bus.OBFileMapperMaster;
import com.integrosys.cms.app.geography.bus.NoSuchGeographyException;
import com.integrosys.cms.app.otherbank.bus.AbstractOtherBankBusManager;
import com.integrosys.cms.app.otherbranch.trx.IOtherBankBranchTrxValue;
import com.integrosys.cms.app.systemBank.bus.SystemBankException;
import com.integrosys.cms.ui.otherbankbranch.IOtherBranch;

/**
 * Purpose : This OtherBranchBusManagerImpl implements the methods that will be available to the
 * operating on a other bank branch
 * 
 * @author $Author: Dattatray Thorat $
 * @version $Revision: 1.0 $
 * @since $Date: 2011-02-18 17:27:01 +0800 (Fri, 18 Feb 2011) $
 */

public class OtherBranchBusManagerImpl extends AbstractOtherBankBranchBusManager implements IOtherBranchBusManager  {
	
	IOtherBranchDAO otherBranchDAO;

	/**
	 * @return the otherBranchDAO
	 */
	public IOtherBranchDAO getOtherBranchDAO() {
		return otherBranchDAO;
	}

	/**
	 * @param otherBranchDAO the otherBranchDAO to set
	 */
	public void setOtherBranchDAO(IOtherBranchDAO otherBranchDAO) {
		this.otherBranchDAO = otherBranchDAO;
	}

	/**
	 * @return the SearchResult  
	 */
	
	public SearchResult getOtherBranch() throws OtherBranchException{
		return (SearchResult)getOtherBranchDAO().getOtherBranch();
	}

	/**
	 * @return the OtherBranch Details  
	 */
	public IOtherBranch getOtherBranchById(long id) throws OtherBranchException {
		return (IOtherBranch)getOtherBranchDAO().getOtherBranchById(id);
	}

	/**
	 * @return the SearchResult  
	 */
	public SearchResult getOtherBranchList(String searchType,String searchVal) throws OtherBranchException{
		return (SearchResult)getOtherBranchDAO().getOtherBranchList(searchType,searchVal);
	}

	/**
	 * @return the OtherBranch Details  
	 */
	public IOtherBranch updateOtherBranch(IOtherBranch OtherBranch) throws OtherBranchException {
		return (IOtherBranch)getOtherBranchDAO().updateOtherBranch(OtherBranch);
	}
	
	/**
	 * @return the OtherBranch Details  
	 */
	public IOtherBranch deleteOtherBranch(IOtherBranch OtherBranch) throws OtherBranchException {
		return (IOtherBranch)getOtherBranchDAO().deleteOtherBranch(OtherBranch);
	}

	/**
	 * @return the OtherBranch Details  
	 */
	public IOtherBranch createOtherBranch(IOtherBranch OtherBranch) throws OtherBranchException {
		return (IOtherBranch)getOtherBranchDAO().createOtherBranch(OtherBranch);
	}
	
	/**
	 * This method returns exception as staging
	 *  system bank can never be working copy
	 */

	public IOtherBranch updateToWorkingCopy(IOtherBranch workingCopy,IOtherBranch imageCopy) throws SystemBankException,TrxParameterException, 
	TransactionException,ConcurrentUpdateException {
		IOtherBranch updated;
		try {
			workingCopy.setOtherBranchName(imageCopy.getOtherBranchName());
			workingCopy.setAddress(imageCopy.getAddress());
			workingCopy.setCity(imageCopy.getCity());
			workingCopy.setContactMailId(imageCopy.getContactMailId());
			workingCopy.setContactNo(imageCopy.getContactNo());
			workingCopy.setFaxNo(imageCopy.getFaxNo());
			workingCopy.setStatus(imageCopy.getStatus());
			// AccessorUtil.copyValue(imageCopy, workingCopy, new String[] {
			// "Id" });
			updated = updateOtherBranch(workingCopy);
		} catch (OtherBranchException e) {
			throw new SystemBankException(
					"Error while Copying copy to main file");
		}

		return updateOtherBranch(updated);
	}
	
	public List getAllOtherBranch() {

		return null;
	}
	
	public boolean isOBCodeUnique(String obCode){
		 return getOtherBranchDAO().isUniqueCodeBankBranch(obCode);
	}


	public IFileMapperId createFileIdBankBranch(IFileMapperId obFileMapperID)
			throws OtherBranchException {
		// TODO Auto-generated method stub
		return null;
	}


	
	public String getOtherBranchName() {
		// TODO Auto-generated method stub
		return null;
	}


	public void deleteTransaction(OBFileMapperMaster obFileMapperMaster) throws OtherBranchException, TrxParameterException,TransactionException {
		getOtherBranchDAO().deleteTransaction(obFileMapperMaster);					
	}

	public String isCodeExisting(String countryCode, String regionCode,String stateCode, String cityCode) throws OtherBranchException,TrxParameterException, TransactionException {
		return getOtherBranchDAO().isCodeExisting(countryCode, regionCode, stateCode, cityCode);
	}
	
}
