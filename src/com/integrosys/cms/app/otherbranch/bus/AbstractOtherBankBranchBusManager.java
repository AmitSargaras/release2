package com.integrosys.cms.app.otherbranch.bus;

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

import com.integrosys.cms.app.otherbank.bus.OtherBankException;
import com.integrosys.cms.app.otherbank.trx.IOtherBankTrxValue;
import com.integrosys.cms.app.otherbranch.trx.IOtherBankBranchTrxValue;
import com.integrosys.cms.ui.otherbankbranch.IOtherBank;
import com.integrosys.cms.ui.otherbankbranch.IOtherBranch;


/**
 *@author $Author: Dattatray Thorat $
 * Abstract Other Bank Branch Bus manager 
 */
public abstract class AbstractOtherBankBranchBusManager implements IOtherBranchBusManager {

	private IOtherBranchDAO otherBranchDAO;

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


	public abstract String getOtherBranchName();
	
	
	/**
	  * @return Particular System Bank  according 
	  * to the id passed as parameter.  
	  * @param Bank Code 
	  */

	

	public IOtherBranch getOtherBranchById(long id) throws OtherBranchException,TrxParameterException,TransactionException  {
		if(id!=0){
		return  getOtherBranchDAO().getOtherBranch(getOtherBranchName(),new Long(id));
		}else{
			throw new OtherBranchException("ERROR-- Key for Object Retrival is null.");
		}
	}

	/**
	  * @return Particular Other Bank  according 
	  * to the id passed as parameter.  
	  * @param Bank Code 
	  */

	

	public IOtherBranch getOtherBranch(long id) throws OtherBranchException,TrxParameterException,TransactionException  {
		if(id!=0){
			return  getOtherBranchDAO().getOtherBranchById(id);
		}else{
			throw new OtherBranchException("ERROR-- Key for Object Retrival is null.");
		}
	}
	
	/**
	 @return OtherBranch Object after create
	 * 
	 */
	
	public IOtherBranch createOtherBranch(IOtherBranch systemBank)throws OtherBranchException {
		if(!(systemBank==null)){
		return getOtherBranchDAO().createOtherBranch(getOtherBranchName(), systemBank);
		}else{
			throw new OtherBranchException("ERROR- Other Bank object   is null. ");
		}
	}
	/**
	 @return OtherBranch Object after update
	 * 
	 */
	public IOtherBranch updateOtherBranch(IOtherBranch item) throws OtherBranchException,TrxParameterException,TransactionException,ConcurrentUpdateException {
		try {
			return getOtherBranchDAO().updateOtherBranch(getOtherBranchName(), item);
		}
		catch (HibernateOptimisticLockingFailureException ex) {
			throw new OtherBranchException("Current OtherBranch [" + item + "] was updated before by ["
					+ item.getOtherBranchCode() + "] at [" + item.getOtherBranchName() + "]");
		}
		
	}
	
	public SearchResult getOtherBranchList(String searchType,String searchVal) throws OtherBranchException{
		return getOtherBranchDAO().getOtherBranchList(searchType, searchVal);
	}
	
	public SearchResult getOtherBranch() throws OtherBranchException{
		return getOtherBranchDAO().getOtherBranch();
	}
	
	public boolean isOBCodeUnique(String obCode){
		 return getOtherBranchDAO().isOBCodeUnique(obCode);
	 }
	
//**********************UPLOAD********************************
		
	
	public boolean isPrevFileUploadPendingBankBranch()
	throws OtherBranchException {
		try {
			return getOtherBranchDAO().isPrevFileUploadPendingBankBranch();  
		} catch (HibernateOptimisticLockingFailureException ex) {
			throw new OtherBranchException("File is not in proper format");
		}
	}
	
	public int insertOtherBankBranch(IFileMapperMaster trans_Id, String userName, ArrayList result)
	throws OtherBranchException {
		try {
			return getOtherBranchDAO().insertOtherBankBranch(trans_Id, userName, result);    
		} catch (HibernateOptimisticLockingFailureException ex) {
			throw new OtherBranchException("File is not in proper format");
		}
	}
	
	public IFileMapperId createFileIdBankBranch(
			IFileMapperId fileId)
			throws OtherBranchException {
		if (!(fileId == null)) {
			return getOtherBranchDAO().createFileIdBankBranch(getOtherBranchName(), fileId);
		} else {
			throw new OtherBranchException(
					"ERROR-  object   is null. ");
		}
	}
	
	
	public IOtherBranch insertOtherBankBranch(
			IOtherBranch otherBankBranch)
			throws OtherBranchException {
		if (!(otherBankBranch == null)) {
			return getOtherBranchDAO().insertOtherBankBranch(IOtherBranchDAO.ACTUAL_ENTITY_NAME, otherBankBranch);
		} else {
			throw new OtherBranchException(
					"ERROR-  object is null. ");
		}
	}
	
	public IFileMapperId insertOtherBankBranch(
			IFileMapperId fileId, IOtherBankBranchTrxValue trxValue)
			throws OtherBranchException {
		if (!(fileId == null)) {
			return getOtherBranchDAO().insertOtherBankBranch(getOtherBranchName(), fileId, trxValue);
		} else {
			throw new OtherBranchException(
					"ERROR- OtherBank object is null. ");
		}
	}
	
	

	
	public IFileMapperId getInsertFileByIdBankBranch(long id)
	throws OtherBranchException, TrxParameterException,
	TransactionException {
if (id != 0) {
	return getOtherBranchDAO().getInsertFileListBankBranch(
			getOtherBranchName(), new Long(id));
} else {
	throw new OtherBranchException(
			"ERROR-- Key for Object Retrival is null.");
}
}
	
	
	public List getAllStageOtherBankBranch(String searchBy, String login)throws OtherBranchException,TrxParameterException,TransactionException {

		return getOtherBranchDAO().getAllStageOtherBankBranch(searchBy, login);    
	}
	
	public List getFileMasterListBankBranch(String searchBy)throws OtherBranchException,TrxParameterException,TransactionException {

		return  getOtherBranchDAO().getFileMasterListBankBranch(searchBy); 
	}
	
	
	
	
	public IOtherBranch insertActualOtherBankBranch(String sysId)
	throws OtherBranchException {
		try {
			return  getOtherBranchDAO().insertActualOtherBankBranch(sysId);
		} catch (HibernateOptimisticLockingFailureException ex) {
			throw new OtherBranchException("File is not in proper format");
		}
	}
	
	
	public boolean isUniqueCodeBankBranch(
			String branchCode)
			throws OtherBranchException {
		if (!(branchCode == null)) {
			return getOtherBranchDAO().isUniqueCodeBankBranch(branchCode);  
		} else {
			throw new OtherBranchException(
					"ERROR-  object   is null. ");
		}
	}
	
	public boolean isUniqueBranchName(String branchName,String bankCode)
			throws OtherBranchException {
		if (!(branchName == null)) {
			return getOtherBranchDAO().isUniqueBranchName(branchName,bankCode);  
		} else {
			throw new OtherBranchException(
					"ERROR-  object   is null. ");
		}
	}
	
	public boolean isUniqueRbiCode(
			String rbiCode)
			throws OtherBranchException {
		if (!(rbiCode == null)) {
			return getOtherBranchDAO().isUniqueRbiCode(rbiCode);  
		} else {
			throw new OtherBranchException(
					"ERROR-  object   is null. ");
		}
	}

	public void deleteTransaction(OBFileMapperMaster obFileMapperMaster) throws OtherBranchException, TrxParameterException,TransactionException {
		getOtherBranchDAO().deleteTransaction(obFileMapperMaster);					
	}
	
	public String isCodeExisting(String countryCode, String regionCode,String stateCode, String cityCode) throws OtherBranchException,TrxParameterException, TransactionException {
		return getOtherBranchDAO().isCodeExisting(countryCode, regionCode, stateCode, cityCode);
	}

}