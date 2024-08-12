package com.integrosys.cms.app.insurancecoverage.bus;

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
import com.integrosys.cms.app.insurancecoverage.trx.IInsuranceCoverageTrxValue;
import com.integrosys.cms.app.relationshipmgr.bus.RelationshipMgrException;
import com.integrosys.cms.app.relationshipmgr.trx.IRelationshipMgrTrxValue;
import com.integrosys.cms.ui.insurancecoverage.IInsuranceCoverage;
import com.integrosys.cms.ui.relationshipmgr.IRelationshipMgr;


/**
 *@author $Author: Dattatray Thorat $
 * Abstract Insurance Coverage Bus manager 
 */
public abstract class AbstractInsuranceCoverageBusManager implements IInsuranceCoverageBusManager {
	
	IInsuranceCoverageDAO insuranceCoverageDAO;
	
	/**
	 * @return the insuranceCoverageDAO
	 */
	public IInsuranceCoverageDAO getInsuranceCoverageDAO() {
		return insuranceCoverageDAO;
	}

	/**
	 * @param insuranceCoverageDAO the insuranceCoverageDAO to set
	 */
	public void setInsuranceCoverageDAO(IInsuranceCoverageDAO insuranceCoverageDAO) {
		this.insuranceCoverageDAO = insuranceCoverageDAO;
	}

	
	public abstract String getInsuranceCoverageName();
	
	
	/**
	  * @return Particular System Bank  according 
	  * to the id passed as parameter.  
	  * @param Bank Code 
	  */

	

	public IInsuranceCoverage getInsuranceCoverageById(long id) throws InsuranceCoverageException,TrxParameterException,TransactionException  {
		if(id!=0){
		return  getInsuranceCoverageDAO().getInsuranceCoverage(getInsuranceCoverageName(),new Long(id));
		}else{
			throw new InsuranceCoverageException("ERROR-- Key for Object Retrival is null.");
		}
	}

	/**
	  * @return Particular Other Bank  according 
	  * to the id passed as parameter.  
	  * @param Bank Code 
	  */

	

	public IInsuranceCoverage getInsuranceCoverage(long id) throws InsuranceCoverageException,TrxParameterException,TransactionException  {
		if(id!=0){
			return  getInsuranceCoverageDAO().getInsuranceCoverage(getInsuranceCoverageName(),new Long(id));
		}else{
			throw new InsuranceCoverageException("ERROR-- Key for Object Retrival is null.");
		}
	}
	
	/**
	 @return InsuranceCoverage Object after create
	 * 
	 */
	
	public IInsuranceCoverage createInsuranceCoverage(IInsuranceCoverage systemBank)throws InsuranceCoverageException {
		if(!(systemBank==null)){
		return getInsuranceCoverageDAO().createInsuranceCoverage(getInsuranceCoverageName(), systemBank);
		}else{
			throw new InsuranceCoverageException("ERROR- Insurance Coverage object   is null. ");
		}
	}
	/**
	 @return InsuranceCoverage Object after update
	 * 
	 */
	public IInsuranceCoverage updateInsuranceCoverage(IInsuranceCoverage item) throws InsuranceCoverageException,TrxParameterException,TransactionException,ConcurrentUpdateException {
		try {
			return getInsuranceCoverageDAO().updateInsuranceCoverage(getInsuranceCoverageName(), item);
		}
		catch (HibernateOptimisticLockingFailureException ex) {
			throw new InsuranceCoverageException("Current InsuranceCoverage [" + item + "] was updated before by ["
					+ item.getInsuranceCoverageCode() + "] at [" + item.getCompanyName() + "]");
		}
		
	}
	
	/**
	 * @return the SearchResult
	 */
	public SearchResult getInsuranceCoverageList(String icCode,String companyName) throws InsuranceCoverageException{
		return (SearchResult)getInsuranceCoverageDAO().getInsuranceCoverageList(icCode,companyName);
	}
	
	/**
	 * @return the SearchResult
	 */
	public SearchResult getInsuranceCoverageDtlsList(long id) throws InsuranceCoverageException{
		return (SearchResult)getInsuranceCoverageDAO().getInsuranceCoverageDtlsList(id);
	}
	
	public boolean isICCodeUnique(String rmCode){
		 return getInsuranceCoverageDAO().isICCodeUnique(rmCode);
	 }
	
//############################ File Upload ##########################
	
	public boolean isPrevFileUploadPending()
	throws InsuranceCoverageException {
		try {
			return getInsuranceCoverageDAO().isPrevFileUploadPending();
		} catch (HibernateOptimisticLockingFailureException ex) {
			throw new InsuranceCoverageException("File is not in proper format");
		}
	}

	public int insertInsuranceCoverage(IFileMapperMaster trans_Id, String userName, ArrayList result)
	throws InsuranceCoverageException {
		try {
			return getInsuranceCoverageDAO().insertInsuranceCoverage(trans_Id, userName, result);
		} catch (HibernateOptimisticLockingFailureException ex) {
			throw new InsuranceCoverageException("File is not in proper format");
		}
	}
	
	

	public IFileMapperId insertInsuranceCoverage(
			IFileMapperId fileId, IInsuranceCoverageTrxValue trxValue)
			throws InsuranceCoverageException {
		if (!(fileId == null)) {
			return getInsuranceCoverageDAO().insertInsuranceCoverage(getInsuranceCoverageName(), fileId, trxValue);
		} else {
			throw new InsuranceCoverageException(
					"ERROR- InsuranceCoverage object is null. ");
		}
	}
	
	public IFileMapperId createFileId(
			IFileMapperId fileId)
			throws InsuranceCoverageException {
		if (!(fileId == null)) {
			return getInsuranceCoverageDAO().createFileId(getInsuranceCoverageName(), fileId);
		} else {
			throw new InsuranceCoverageException(
					"ERROR- InsuranceCoverage object   is null. ");
		}
	}
	

	public IFileMapperId getInsertFileById(long id)
			throws InsuranceCoverageException, TrxParameterException,
			TransactionException {
		if (id != 0) {
			return getInsuranceCoverageDAO().getInsertFileList(
					getInsuranceCoverageName(), new Long(id));
		} else {
			throw new InsuranceCoverageException(
					"ERROR-- Key for Object Retrival is null.");
		}
	}
	
	public List getAllStageInsuranceCoverage(String searchBy, String login)throws InsuranceCoverageException,TrxParameterException,TransactionException {

		return getInsuranceCoverageDAO().getAllStageInsuranceCoverage(searchBy, login);
	}
	
	public List getFileMasterList(String searchBy)throws InsuranceCoverageException,TrxParameterException,TransactionException {

		return getInsuranceCoverageDAO().getFileMasterList(searchBy);
	}
	
	
	public IInsuranceCoverage insertActualInsuranceCoverage(String sysId)
	throws InsuranceCoverageException {
		try {
			return getInsuranceCoverageDAO().insertActualInsuranceCoverage(sysId);
		} catch (HibernateOptimisticLockingFailureException ex) {
			throw new InsuranceCoverageException("File is not in proper format");
		}
	}
	
	public IInsuranceCoverage insertInsuranceCoverage(
			IInsuranceCoverage holiday)
			throws InsuranceCoverageException {
		if (!(holiday == null)) {
			return getInsuranceCoverageDAO().insertInsuranceCoverage("actualInsuranceCoverage", holiday);
		} else {
			throw new InsuranceCoverageException(
					"ERROR-  Insurance Coverage object is null. ");
		}
	}
	
	public boolean isCompanyNameUnique(String companyName) {
		return getInsuranceCoverageDAO().isCompanyNameUnique(companyName);
	}
	
	public void deleteTransaction(OBFileMapperMaster obFileMapperMaster) throws NoSuchGeographyException, TrxParameterException,TransactionException {
		getInsuranceCoverageDAO().deleteTransaction(obFileMapperMaster);					
	}
}