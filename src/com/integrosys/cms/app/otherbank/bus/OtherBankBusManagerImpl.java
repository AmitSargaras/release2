package com.integrosys.cms.app.otherbank.bus;

import java.util.List;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;
import com.integrosys.cms.app.fileInsertMapper.bus.OBFileMapperMaster;
import com.integrosys.cms.app.geography.bus.NoSuchGeographyException;
import com.integrosys.cms.app.otherbranch.bus.OtherBranchException;
import com.integrosys.cms.app.systemBank.bus.SystemBankException;
import com.integrosys.cms.ui.otherbankbranch.IOtherBank;

/**
 * This OtherBankBusManagerImpl implements all the methods of  IOtherBankBusManager 
 * $Author: Dattatray Thorat
 * @version $Revision: 1.2 $
 * @since $Date: 2011/02/18 11:32:23 $ Tag: $Name: $
 */

public class OtherBankBusManagerImpl  extends AbstractOtherBankBusManager implements IOtherBankBusManager  {    
	
	IOtherBankDAO otherBankDao;

	/**
	 * @return the otherBankDAO
	 */
	public IOtherBankDAO getOtherBankDao() {
		return otherBankDao;
	}

	/**
	 * @param otherBankDao the otherBankDao to set
	 */
	public void setOtherBankDao(IOtherBankDAO otherBankDao) {
		this.otherBankDao = otherBankDao;
	}

	/**
	 * @return the SearchResult
	 */
	public SearchResult getOtherBank() throws OtherBankException{
		return (SearchResult)getOtherBankDao().getOtherBank();
	}

	/**
	 * @return the boolean
	 */
	public boolean checkOtherBranchById(long id) throws OtherBankException {
		return (boolean)getOtherBankDao().checkOtherBranchById(id);
	}
	
	/**
	 * @return the OtherBank Details
	 */
	public IOtherBank getOtherBankById(long id) throws OtherBankException {
		return (IOtherBank)getOtherBankDao().getOtherBankById(id);
	}

	
	/**
	 * @return the SearchResult
	 */
	public SearchResult getOtherBankList(String bankCode,String bankName) throws OtherBankException{
		return (SearchResult)getOtherBankDao().getOtherBankList(bankCode,bankName);
	}

	public SearchResult getInsurerList() throws OtherBankException{
		return (SearchResult)getOtherBankDao().getInsurerList();
	}

	/**
	 * @return the OtherBank Details
	 */
	public IOtherBank updateOtherBank(IOtherBank OtherBank) throws OtherBankException {
		return (IOtherBank)getOtherBankDao().updateOtherBank(OtherBank);
	}
	
	/**
	 * @return the OtherBank Details
	 */
	public IOtherBank deleteOtherBank(IOtherBank OtherBank) throws OtherBankException {
		return (IOtherBank)getOtherBankDao().deleteOtherBank(OtherBank);
	}
	
	/**
	 * @return the OtherBank Details
	 */
	public IOtherBank createOtherBank(IOtherBank OtherBank) throws OtherBankException {
		return (IOtherBank)getOtherBankDao().createOtherBank(OtherBank);
	}
	
	/**
	 * This method returns exception as staging
	 *  system bank can never be working copy
	 */

	public IOtherBank updateToWorkingCopy(IOtherBank workingCopy,IOtherBank imageCopy) throws SystemBankException,TrxParameterException, 
	TransactionException,ConcurrentUpdateException {
		IOtherBank updated;
		try {
			workingCopy.setOtherBankName(imageCopy.getOtherBankName());
			workingCopy.setAddress(imageCopy.getAddress());
			workingCopy.setCity(imageCopy.getCity());
			workingCopy.setContactMailId(imageCopy.getContactMailId());
			workingCopy.setContactNo(imageCopy.getContactNo());
			workingCopy.setFaxNo(imageCopy.getFaxNo());
			workingCopy.setStatus(imageCopy.getStatus());
			workingCopy.setCountry(imageCopy.getCountry());
			workingCopy.setRegion(imageCopy.getRegion());
			workingCopy.setState(imageCopy.getState());
			// AccessorUtil.copyValue(imageCopy, workingCopy, new String[] {
			// "Id" });
			updated = updateOtherBank(workingCopy);
		} catch (OtherBankException e) {
			throw new SystemBankException(
					"Error while Copying copy to main file");
		}

		return updateOtherBank(updated);
	}
    
    public List getAllOtherBank() {

		return null;
	}
    
    public SearchResult getOtherBranchList(String branchCode,String branchName,String state,String city,long id) throws OtherBranchException{
		return getOtherBankDao().getOtherBranchList(branchCode, branchName,state,city,id);
	}
    
    /**
	 * @return the ArrayList
	 */
	public List getCountryList() throws OtherBankException{
		return (List)getOtherBankDao().getCountryList();
	}
	
	/**
	 * @return the ArrayList
	 */
	public List getRegionList(String countryId) throws OtherBankException{
		return (List)getOtherBankDao().getRegionList(countryId);
	}
	
	public SearchResult getInsurerNameFromCode(String insurerName)throws OtherBankException{
		return (SearchResult)getOtherBankDao().getInsurerNameFromCode( insurerName);
	}
	
	/**
	 * @return the ArrayList
	 */
	public List getStateList(String regionId) throws OtherBankException{
		return (List)getOtherBankDao().getStateList(regionId);
	}
	
	/**
	 * @return the ArrayList
	 */
	public List getCityList(String stateId) throws OtherBankException{
		return (List)getOtherBankDao().getCityList(stateId);
	}

	
	public String getOtherBankName() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public void deleteTransaction(OBFileMapperMaster obFileMapperMaster) throws OtherBankException, TrxParameterException,TransactionException {
		getOtherBankDao().deleteTransaction(obFileMapperMaster);					
	}

	public String isCodeExisting(String countryCode, String regionCode,String stateCode, String cityCode) throws OtherBankException,TrxParameterException, TransactionException {
		return getOtherBankDao().isCodeExisting(countryCode, regionCode, stateCode, cityCode);
	}
}
