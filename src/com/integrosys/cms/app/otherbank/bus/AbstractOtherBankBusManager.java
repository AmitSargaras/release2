package com.integrosys.cms.app.otherbank.bus;

import java.util.ArrayList;
import java.util.List;

import org.springframework.orm.hibernate4.HibernateOptimisticLockingFailureException;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;
import com.integrosys.cms.app.fileInsertMapper.bus.IFileMapperId;
import com.integrosys.cms.app.fileInsertMapper.bus.IFileMapperMaster;
import com.integrosys.cms.app.fileInsertMapper.bus.OBFileMapperMaster;
import com.integrosys.cms.app.geography.bus.NoSuchGeographyException;
import com.integrosys.cms.app.otherbank.trx.IOtherBankTrxValue;
import com.integrosys.cms.app.otherbranch.bus.OtherBranchException;
import com.integrosys.cms.ui.otherbankbranch.IOtherBank;


/**
 *@author $Author: Dattatray Thorat $
 * Abstract Other Bank Bus manager 
 */
public abstract class AbstractOtherBankBusManager implements IOtherBankBusManager {

	private IOtherBankDAO otherBankDao;

	
	/**
	 * @return the otherBankDao
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


	public abstract String getOtherBankName();
	
	
	/**
	  * @return Particular System Bank  according 
	  * to the id passed as parameter.  
	  * @param Bank Code 
	  */

	

	public IOtherBank getOtherBankById(long id) throws OtherBankException,TrxParameterException,TransactionException  {
		if(id!=0){
		return  getOtherBankDao().getOtherBank(getOtherBankName(),new Long(id));
		}else{
			throw new OtherBankException("ERROR-- Key for Object Retrival is null.");
		}
	}

	/**
	  * @return Particular Other Bank  according 
	  * to the id passed as parameter.  
	  * @param Bank Code 
	  */

	

	public IOtherBank getOtherBank(long id) throws OtherBankException,TrxParameterException,TransactionException  {
		if(id!=0){
			return  getOtherBankDao().getOtherBank(getOtherBankName(),new Long(id));
		}else{
			throw new OtherBankException("ERROR-- Key for Object Retrival is null.");
		}
	}
	
	/**
	 @return OtherBank Object after create
	 * 
	 */
	
	public IOtherBank createOtherBank(IOtherBank systemBank)throws OtherBankException {
		if(!(systemBank==null)){
		return getOtherBankDao().createOtherBank(getOtherBankName(), systemBank);
		}else{
			throw new OtherBankException("ERROR- Other Bank object   is null. ");
		}
	}
	/**
	 @return OtherBank Object after update
	 * 
	 */
	public IOtherBank updateOtherBank(IOtherBank item) throws OtherBankException,TrxParameterException,TransactionException,ConcurrentUpdateException {
		try {
			return getOtherBankDao().updateOtherBank(getOtherBankName(), item);
		}
		catch (HibernateOptimisticLockingFailureException ex) {
			throw new OtherBankException("Current OtherBank [" + item + "] was updated before by ["
					+ item.getOtherBankCode() + "] at [" + item.getOtherBankName() + "]");
		}
		
	}
	
	/**
	 * @return the SearchResult
	 */
	public SearchResult getOtherBankList(String bankCode,String bankName) throws OtherBankException{
		return (SearchResult)getOtherBankDao().getOtherBankList(bankCode,bankName);
	}
	
	
	public  List<OBOtherBank> getOtherBankList(String bankCode,String bankName,String branchName,String branchCode) throws OtherBankException{
		return (List<OBOtherBank>)getOtherBankDao().getOtherBankList(bankCode,bankName,  branchName,  branchCode);
	}
	
	/**
	 * @return the boolean
	 */
	public boolean checkOtherBranchById(long id) throws OtherBankException {
		return (boolean)getOtherBankDao().checkOtherBranchById(id);
	}
	
	public SearchResult getOtherBranchList(String branchCode,String branchName,String state,String city,long id) throws OtherBranchException{
		return getOtherBankDao().getOtherBranchList(branchCode,branchName,state,city,id);
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
	
//**********************UPLOAD********************************
		
	
	public boolean isPrevFileUploadPending()
	throws OtherBankException {
		try {
			return getOtherBankDao().isPrevFileUploadPending();  
		} catch (HibernateOptimisticLockingFailureException ex) {
			throw new OtherBankException("File is not in proper format");
		}
	}
	
	public int insertOtherBank(IFileMapperMaster trans_Id, String userName, ArrayList result)
	throws OtherBankException {
		try {
			return getOtherBankDao().insertOtherBank(trans_Id, userName, result);    
		} catch (HibernateOptimisticLockingFailureException ex) {
			throw new OtherBankException("File is not in proper format");
		}
	}
	
	public IFileMapperId createFileId(
			IFileMapperId fileId)
			throws OtherBankException {
		if (!(fileId == null)) {
			return getOtherBankDao().createFileId(getOtherBankName(), fileId);
		} else {
			throw new OtherBankException(
					"ERROR-  object   is null. ");
		}
	}
	
	
	public IOtherBank insertOtherBank(
			IOtherBank otherBank)
			throws OtherBankException {
		if (!(otherBank == null)) {
			return getOtherBankDao().insertOtherBank("actualOtherBank", otherBank);
		} else {
			throw new OtherBankException(
					"ERROR-  object is null. ");
		}
	}
	
	public IFileMapperId insertOtherBank(
			IFileMapperId fileId, IOtherBankTrxValue trxValue)
			throws OtherBankException {
		if (!(fileId == null)) {
			return getOtherBankDao().insertOtherBank(getOtherBankName(), fileId, trxValue);
		} else {
			throw new OtherBankException(
					"ERROR- OtherBank object is null. ");
		}
	}
	
	public IFileMapperId getInsertFileById(long id)
	throws OtherBankException, TrxParameterException,
	TransactionException {
if (id != 0) {
	return getOtherBankDao().getInsertFileList(
			getOtherBankName(), new Long(id));
} else {
	throw new OtherBankException(
			"ERROR-- Key for Object Retrival is null.");
}
}
	
	
	public List getAllStageOtherBank(String searchBy, String login)throws OtherBankException,TrxParameterException,TransactionException {

		return getOtherBankDao().getAllStageOtherBank(searchBy, login);    
	}
	
	public List getFileMasterList(String searchBy)throws OtherBankException,TrxParameterException,TransactionException {

		return  getOtherBankDao().getFileMasterList(searchBy); 
	}
	
	
	
	
	public IOtherBank insertActualOtherBank(String sysId)
	throws OtherBankException {
		try {
			return  getOtherBankDao().insertActualOtherBank(sysId);
		} catch (HibernateOptimisticLockingFailureException ex) {
			throw new OtherBankException("File is not in proper format");
		}
	}
	
	
	public boolean isUniqueCode(
			String branchCode)
			throws OtherBankException {
		if (!(branchCode == null)) {
			return getOtherBankDao().isUniqueCode(branchCode);  
		} else {
			throw new OtherBankException(
					"ERROR-  object   is null. ");
		}
	}
	
	public boolean isUniqueName(
			String branchName)
			throws OtherBankException {
		if (!(branchName == null)) {
			return getOtherBankDao().isUniqueName(branchName);  
		} else {
			throw new OtherBankException(
					"ERROR-  object   is null. ");
		}
	}
	
	//*******************************Guarantee Security methods starts*********************************************
    
	public String getCityName(String cityId) throws OtherBankException{
		return (String)getOtherBankDao().getCityName(cityId);
	}
	
	public String getStateName(String stateId) throws OtherBankException{
		return (String)getOtherBankDao().getStateName(stateId);
	}
	
	public String getRegionName(String regionId) throws OtherBankException{
		return (String)getOtherBankDao().getRegionName(regionId);
	}
	
	public String getCountryName(String countryId) throws OtherBankException{
		return (String)getOtherBankDao().getCountryName(countryId);
	}
						 
    //*******************************Guarantee Security methods end************************************************
	
	public String isCodeExisting(String countryCode, String regionCode,String stateCode, String cityCode) throws OtherBankException,TrxParameterException, TransactionException {
		return getOtherBankDao().isCodeExisting(countryCode, regionCode, stateCode, cityCode);
	}
	
	public void deleteTransaction(OBFileMapperMaster obFileMapperMaster) throws OtherBankException, TrxParameterException,TransactionException {
		getOtherBankDao().deleteTransaction(obFileMapperMaster);					
	}
		
}