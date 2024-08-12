package com.integrosys.cms.app.valuationAgency.bus;

import java.util.ArrayList;
import java.util.List;

import org.springframework.orm.hibernate4.HibernateOptimisticLockingFailureException;

import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;
import com.integrosys.cms.app.fileInsertMapper.bus.IFileMapperId;
import com.integrosys.cms.app.fileInsertMapper.bus.IFileMapperMaster;
import com.integrosys.cms.app.fileInsertMapper.bus.OBFileMapperMaster;
import com.integrosys.cms.app.valuationAgency.trx.IValuationAgencyTrxValue;

public abstract class AbstractValuationAgencyBusManager implements
		IValuationAgencyBusManager {
	private IValuationAgencyDao valuationDao;
	private IValuationAgencyJdbc valuationJdbc;
	
	public IValuationAgencyJdbc getValuationJdbc() {
		return valuationJdbc;
	}

	public void setValuationJdbc(IValuationAgencyJdbc valuationJdbc) {
		this.valuationJdbc = valuationJdbc;
	}


	public IValuationAgencyDao getValuationDao() {
		return valuationDao;
	}

	public void setValuationDao(IValuationAgencyDao valuationDao) {
		this.valuationDao = valuationDao;
	}



	public abstract String getValuationAgencyName();

	/**
	 * @return Particular Valuation Agency according to the id passed as parameter.
	 * @param Bank
	 *            Code
	 */

	public IValuationAgency getValuationAgencyById(long id)
			throws ValuationAgencyException, TrxParameterException,
			TransactionException {
		if (id != 0) {
			return getValuationDao().getValuationAgency(
					getValuationAgencyName(), new Long(id));
		} else {
			throw new ValuationAgencyException(
					"ERROR-- Key for Object Retrival is null.");
		}
	}

	/**
	 * @return ValuationAgency Object after create
	 * 
	 */

	public IValuationAgency createValuationAgency(
			IValuationAgency valuationAgency) throws ValuationAgencyException {
		if (!(valuationAgency == null)) {
			return getValuationDao().createValuationAgency(
					getValuationAgencyName(), valuationAgency);
		} else {
			throw new ValuationAgencyException(
					"ERROR- Valuation Agency object   is null. ");
		}
	}

	/**
	 * @return ValuationAgency Object after update
	 * 
	 */
	public IValuationAgency updateValuationAgency(IValuationAgency item)
			throws ValuationAgencyException, TrxParameterException,
			TransactionException, ConcurrentUpdateException {
		try {
			return getValuationDao().updateValuationAgency(
					getValuationAgencyName(), item);
		} catch (HibernateOptimisticLockingFailureException ex) {
			throw new ValuationAgencyException("Current ValuationAgency ["
					+ item + "] was updated before by ["
					+ item.getValuationAgencyCode() + "] at ["
					+ item.getValuationAgencyName() + "]");
		}

	}

	public IValuationAgency disableValuationAgency(IValuationAgency item)
			throws ValuationAgencyException, TrxParameterException,
			TransactionException {
		try {
			return getValuationDao().disableValuationAgency(
					getValuationAgencyName(), item);
		} catch (HibernateOptimisticLockingFailureException ex) {
			throw new ValuationAgencyException("current ValuationAgency ["
					+ item + "]");
		}
	}

	/**
	 * @return ValuationAgency Object after delete
	 * 
	 */
	public IValuationAgency enableValuationAgency(IValuationAgency item)
			throws ValuationAgencyException, TrxParameterException,
			TransactionException {
		try {
			return getValuationDao().enableValuationAgency(
					getValuationAgencyName(), item);
		} catch (HibernateOptimisticLockingFailureException ex) {
			throw new ValuationAgencyException("current ValuationAgency ["
					+ item + "]");
		}
	}
	
	public boolean isVACodeUnique(String rmCode){
		 return getValuationDao().isVACodeUnique(rmCode);
	 }
	
	public List getCountryList(long countryId) throws ValuationAgencyException {
		return getValuationDao().getCountryList(countryId);
	}
	
	public List getCityList(long stateId) throws ValuationAgencyException {
		return getValuationDao().getCityList(stateId);
	}
	
	//**********************UPLOAD********************************
		
	
	public boolean isPrevFileUploadPending()
	throws ValuationAgencyException {
		try {
			return getValuationDao().isPrevFileUploadPending();
		} catch (HibernateOptimisticLockingFailureException ex) {
			throw new ValuationAgencyException("File is not in proper format");
		}
	}
	
	public int insertValuationAgency(IFileMapperMaster trans_Id, String userName, ArrayList result)
	throws ValuationAgencyException {
		try {
			return getValuationDao().insertValuationAgency(trans_Id, userName, result);
		} catch (HibernateOptimisticLockingFailureException ex) {
			throw new ValuationAgencyException("File is not in proper format");
		}
	}
	
	public IFileMapperId createFileId(
			IFileMapperId fileId)
			throws ValuationAgencyException {
		if (!(fileId == null)) {
			return getValuationDao().createFileId(getValuationAgencyName(), fileId);
		} else {
			throw new ValuationAgencyException(
					"ERROR-  object   is null. ");
		}
	}
	
	
	public IValuationAgency insertValuationAgency(
			IValuationAgency valuationAgency)
			throws ValuationAgencyException {
		if (!(valuationAgency == null)) {
			return getValuationDao().insertValuationAgency("actualOBValuationAgency", valuationAgency);
		} else {
			throw new ValuationAgencyException(
					"ERROR-  object is null. ");
		}
	}
	
	public IFileMapperId insertValuationAgency(
			IFileMapperId fileId, IValuationAgencyTrxValue trxValue)
			throws ValuationAgencyException {
		if (!(fileId == null)) {
			return getValuationDao().insertValuationAgency(getValuationAgencyName(), fileId, trxValue);
		} else {
			throw new ValuationAgencyException(
					"ERROR- ValuationAgency object is null. ");
		}
	}
	
	public IFileMapperId getInsertFileById(long id)
	throws ValuationAgencyException, TrxParameterException,
	TransactionException {
if (id != 0) {
	return getValuationDao().getInsertFileList(
			getValuationAgencyName(), new Long(id));
} else {
	throw new ValuationAgencyException(
			"ERROR-- Key for Object Retrival is null.");
}
}
	
	
	public List getAllStageValuationAgency(String searchBy, String login)throws ValuationAgencyException,TrxParameterException,TransactionException {

		return getValuationDao().getAllStageValuationAgency(searchBy, login);    
	}
	
	public List getFileMasterList(String searchBy)throws ValuationAgencyException,TrxParameterException,TransactionException {

		return  getValuationDao().getFileMasterList(searchBy); 
	}
	
	
	
	
	public IValuationAgency insertActualValuationAgency(String sysId)
	throws ValuationAgencyException {
		try {
			return  getValuationDao().insertActualValuationAgency(sysId);
		} catch (HibernateOptimisticLockingFailureException ex) {
			throw new ValuationAgencyException("File is not in proper format");
		}
	}
	
	
	public boolean isUniqueCode(
			String branchCode)
			throws ValuationAgencyException {
		if (!(branchCode == null)) {
			return getValuationDao().isUniqueCode(branchCode);  
		} else {
			throw new ValuationAgencyException(
					"ERROR-  object   is null. ");
		}
	}

	public void deleteTransaction(OBFileMapperMaster obFileMapperMaster) throws ValuationAgencyException, TrxParameterException,TransactionException {
		getValuationDao().deleteTransaction(obFileMapperMaster);			
	}
	
	public boolean isValuationNameUnique(String valuationName) throws ValuationAgencyException, TrxParameterException,TransactionException {
		return getValuationDao().isValuationNameUnique(valuationName);
	}
	
	public String isCodeExisting(String countryCode, String regionCode,String stateCode, String cityCode) throws ValuationAgencyException,TrxParameterException, TransactionException {
		return getValuationDao().isCodeExisting(countryCode, regionCode, stateCode, cityCode);
	}
	
	public String getValuationAgencyName(String companyId) {
		return getValuationDao().getValuationAgencyName(companyId);
	}
}
