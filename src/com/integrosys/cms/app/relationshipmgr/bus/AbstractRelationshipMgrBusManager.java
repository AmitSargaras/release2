package com.integrosys.cms.app.relationshipmgr.bus;

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
import com.integrosys.cms.app.relationshipmgr.trx.IRelationshipMgrTrxValue;
import com.integrosys.cms.ui.relationshipmgr.IRelationshipMgr;


/**
 *@author $Author: Dattatray Thorat $
 * Abstract Relationship Manager Bus manager 
 */
public abstract class AbstractRelationshipMgrBusManager implements IRelationshipMgrBusManager {
	
	IRelationshipMgrDAO relationshipMgrDAO;
	
	/**
	 * @return the relationshipMgrDAO
	 */
	public IRelationshipMgrDAO getRelationshipMgrDAO() {
		return relationshipMgrDAO;
	}

	/**
	 * @param relationshipMgrDAO the relationshipMgrDAO to set
	 */
	public void setRelationshipMgrDAO(IRelationshipMgrDAO relationshipMgrDAO) {
		this.relationshipMgrDAO = relationshipMgrDAO;
	}

	
	public abstract String getRelationshipMgrName();
	
	
	/**
	  * @return Particular System Bank  according 
	  * to the id passed as parameter.  
	  * @param Bank Code 
	  */

	

	public IRelationshipMgr getRelationshipMgrById(long id) throws RelationshipMgrException,TrxParameterException,TransactionException  {
		if(id!=0){
		return  getRelationshipMgrDAO().getRelationshipMgr(getRelationshipMgrName(),new Long(id));
		}else{
			throw new RelationshipMgrException("ERROR-- Key for Object Retrival is null.");
		}
	}

	/**
	  * @return Particular Other Bank  according 
	  * to the id passed as parameter.  
	  * @param Bank Code 
	  */

	

	public IRelationshipMgr getRelationshipMgr(long id) throws RelationshipMgrException,TrxParameterException,TransactionException  {
		if(id!=0){
			return  getRelationshipMgrDAO().getRelationshipMgr(getRelationshipMgrName(),new Long(id));
		}else{
			throw new RelationshipMgrException("ERROR-- Key for Object Retrival is null.");
		}
	}
	
	/**
	 @return RelationshipMgr Object after create
	 * 
	 */
	
	public IRelationshipMgr createRelationshipMgr(IRelationshipMgr relationshipMgr)throws RelationshipMgrException {
		if(!(relationshipMgr==null)){
		return getRelationshipMgrDAO().createRelationshipMgr(getRelationshipMgrName(), relationshipMgr);
		}else{
			throw new RelationshipMgrException("ERROR- Other Bank object   is null. ");
		}
	}
	/**
	 @return RelationshipMgr Object after update
	 * 
	 */
	public IRelationshipMgr updateRelationshipMgr(IRelationshipMgr item) throws RelationshipMgrException,TrxParameterException,TransactionException,ConcurrentUpdateException {
		try {
			return getRelationshipMgrDAO().updateRelationshipMgr(getRelationshipMgrName(), item);
		}
		catch (HibernateOptimisticLockingFailureException ex) {
			throw new RelationshipMgrException("Current RelationshipMgr [" + item + "] was updated before by ["
					+ item.getRelationshipMgrCode() + "] at [" + item.getRelationshipMgrName() + "]");
		}
		
	}
	
	/**
	 * @return the SearchResult
	 */
	public SearchResult getRelationshipMgrList(String rmCode,String rmName) throws RelationshipMgrException{
		return (SearchResult)getRelationshipMgrDAO().getRelationshipMgrList(rmCode,rmName);
	}
	
	public SearchResult getRelationshipMgrList(String regionId) throws RelationshipMgrException{
		return (SearchResult)getRelationshipMgrDAO().getRelationshipMgrList(regionId);
	}
	
	public boolean isRMCodeUnique(String rmCode){
		 return getRelationshipMgrDAO().isRMCodeUnique(rmCode);
	 }
	
	//############################ File Upload ##########################
	
	public boolean isPrevFileUploadPending()
	throws RelationshipMgrException {
		try {
			return getRelationshipMgrDAO().isPrevFileUploadPending();
		} catch (HibernateOptimisticLockingFailureException ex) {
			throw new RelationshipMgrException("File is not in proper format");
		}
	}

	public int insertRelationshipMgr(IFileMapperMaster trans_Id, String userName, ArrayList result)
	throws RelationshipMgrException {
		try {
			return getRelationshipMgrDAO().insertRelationshipMgr(trans_Id, userName, result);
		} catch (HibernateOptimisticLockingFailureException ex) {
			throw new RelationshipMgrException("File is not in proper format");
		}
	}
	
	

	public IFileMapperId insertRelationshipMgr(
			IFileMapperId fileId, IRelationshipMgrTrxValue trxValue)
			throws RelationshipMgrException {
		if (!(fileId == null)) {
			return getRelationshipMgrDAO().insertRelationshipMgr(getRelationshipMgrName(), fileId, trxValue);
		} else {
			throw new RelationshipMgrException(
					"ERROR- RelationshipMgr object is null. ");
		}
	}
	
	public IFileMapperId createFileId(
			IFileMapperId fileId)
			throws RelationshipMgrException {
		if (!(fileId == null)) {
			return getRelationshipMgrDAO().createFileId(getRelationshipMgrName(), fileId);
		} else {
			throw new RelationshipMgrException(
					"ERROR- RelationshipMgr object   is null. ");
		}
	}
	

	public IFileMapperId getInsertFileById(long id)
			throws RelationshipMgrException, TrxParameterException,
			TransactionException {
		if (id != 0) {
			return getRelationshipMgrDAO().getInsertFileList(
					getRelationshipMgrName(), new Long(id));
		} else {
			throw new RelationshipMgrException(
					"ERROR-- Key for Object Retrival is null.");
		}
	}
	
	public List getAllStageRelationshipMgr(String searchBy, String login)throws RelationshipMgrException,TrxParameterException,TransactionException {

		return getRelationshipMgrDAO().getAllStageRelationshipMgr(searchBy, login);
	}
	
	public List getFileMasterList(String searchBy)throws RelationshipMgrException,TrxParameterException,TransactionException {

		return getRelationshipMgrDAO().getFileMasterList(searchBy);
	}
	
	
	public IRelationshipMgr insertActualRelationshipMgr(String sysId)
	throws RelationshipMgrException {
		try {
			return getRelationshipMgrDAO().insertActualRelationshipMgr(sysId);
		} catch (HibernateOptimisticLockingFailureException ex) {
			throw new RelationshipMgrException("File is not in proper format");
		}
	}
	
	public IRelationshipMgr insertRelationshipMgr(
			IRelationshipMgr holiday)
			throws RelationshipMgrException {
		if (!(holiday == null)) {
			return getRelationshipMgrDAO().insertRelationshipMgr("actualRelationshipMgr", holiday);
		} else {
			throw new RelationshipMgrException(
					"ERROR-  Relationshp Manager object is null. ");
		}
	}
	
	/**
	 * @return the ArrayList
	 */
	public List getRegionList(String countryId) throws OtherBankException{
		return (List)getRelationshipMgrDAO().getRegionList(countryId);
	}
	
	public boolean isValidRegionCode(String regionCode) {
		return getRelationshipMgrDAO().isValidRegionCode(regionCode);
	}
	
	public boolean isRelationshipMgrNameUnique(String relationshipMgrName) {
		return getRelationshipMgrDAO().isRelationshipMgrNameUnique(relationshipMgrName);
	}
	
	
	public boolean isEmployeeIdUnique(String employeeId) {
		return getRelationshipMgrDAO().isEmployeeIdUnique(employeeId);
	}
	
	public void deleteTransaction(OBFileMapperMaster obFileMapperMaster) throws NoSuchGeographyException, TrxParameterException,TransactionException {
		getRelationshipMgrDAO().deleteTransaction(obFileMapperMaster);					
	}
}