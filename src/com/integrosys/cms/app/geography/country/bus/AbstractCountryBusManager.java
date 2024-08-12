package com.integrosys.cms.app.geography.country.bus;

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
import com.integrosys.cms.app.geography.country.proxy.ICountryBusManager;
import com.integrosys.cms.app.geography.country.trx.ICountryTrxValue;

public abstract class AbstractCountryBusManager implements ICountryBusManager{
	
	private ICountryDAO countryDAO;
	
	public ICountryDAO getCountryDAO() {
		return countryDAO;
	}
	
	public void setCountryDAO(ICountryDAO countryDAO) {
		this.countryDAO = countryDAO;
	}

	/**
	 * Gets Entity Name Country 
	 * @return
	 */
	public abstract String getCountryName();
	
	public ICountry getCountryById(long id)
			throws NoSuchGeographyException, TrxParameterException,
			TransactionException {
		return getCountryDAO().getCountry(getCountryName(), id);
	}
	
	public ICountry getCountry(String entity,long id)
		throws NoSuchGeographyException, TrxParameterException,
		TransactionException {
	return getCountryDAO().getCountry(getCountryName(), id);
	}

	public ICountryTrxValue getCountryTrxValue(long aCountryId)
			throws NoSuchGeographyException, TrxParameterException,
			TransactionException {

		return null;
	}

	public SearchResult getCountryList(String type, String text)
			throws NoSuchGeographyException {
		return getCountryDAO().listCountry(type, text);
	}

	public ICountry createCountry(ICountry country)	throws NoSuchGeographyException, TrxParameterException,
			TransactionException {
		return getCountryDAO().createCountry(getCountryName(),country);
	}		
	public ICountry updateCountry(ICountry country)
		throws NoSuchGeographyException, TrxParameterException,
		TransactionException, ConcurrentUpdateException {
		return null;
	}
	
	public ICountry makerUpdateSaveCreateCountry(ICountry city)throws NoSuchGeographyException, TrxParameterException,TransactionException{
		return getCountryDAO().createCountry(getCountryName(),city);
	}
		
	public ICountry makerUpdateSaveUpdateCountry(ICountry anICCCountry)throws NoSuchGeographyException, TrxParameterException,TransactionException,ConcurrentUpdateException {
			return getCountryDAO().updateCountry(anICCCountry);
	}
	
	public boolean checkActiveRegion(ICountry country) {
		return getCountryDAO().checkActiveRegion(country);
	}
	
    /**
	 * This method returns exception as staging country can never be working copy
	 */

    public ICountry updateToWorkingCopy(ICountry workingCopy, ICountry imageCopy)throws NoSuchGeographyException,TrxParameterException,TransactionException,ConcurrentUpdateException {
        throw new IllegalStateException("'updateToWorkingCopy' should not be implemented.");
    }

	/**
	 * @return the Country details
	 */
	public ICountry deleteCountry(ICountry country) throws NoSuchGeographyException , TrxParameterException, TransactionException{
		return getCountryDAO().deleteCountry(country);	
	}
	
	
//############################ File Upload ##########################
	
	public boolean isPrevFileUploadPending()
	throws CountryException {
		try {
			return getCountryDAO().isPrevFileUploadPending();
		} catch (HibernateOptimisticLockingFailureException ex) {
			throw new CountryException("File is not in proper format");
		}
	}

	public int insertCountry(IFileMapperMaster trans_Id, String userName, ArrayList result)
	throws CountryException {
		try {
			return getCountryDAO().insertCountry(trans_Id, userName, result);
		} catch (HibernateOptimisticLockingFailureException ex) {
			throw new CountryException("File is not in proper format");
		}
	}
	
	

	public IFileMapperId insertCountry(
			IFileMapperId fileId, ICountryTrxValue trxValue)
			throws CountryException {
		if (!(fileId == null)) {
			return getCountryDAO().insertCountry(getCountryName(), fileId, trxValue);
		} else {
			throw new CountryException(
					"ERROR- Country object is null. ");
		}
	}
	
	public IFileMapperId createFileId(
			IFileMapperId fileId)
			throws CountryException {
		if (!(fileId == null)) {
			return getCountryDAO().createFileId(getCountryName(), fileId);
		} else {
			throw new CountryException(
					"ERROR- Country object   is null. ");
		}
	}
	

	public IFileMapperId getInsertFileById(long id)
			throws CountryException, TrxParameterException,
			TransactionException {
		if (id != 0) {
			return getCountryDAO().getInsertFileList(
					getCountryName(), new Long(id));
		} else {
			throw new CountryException(
					"ERROR-- Key for Object Retrival is null.");
		}
	}
	
	public List getAllStageCountry(String searchBy, String login)throws CountryException,TrxParameterException,TransactionException {

		return getCountryDAO().getAllStageCountry(searchBy, login);
	}
	
	public List getFileMasterList(String searchBy)throws CountryException,TrxParameterException,TransactionException {

		return getCountryDAO().getFileMasterList(searchBy);
	}
	
	
	public ICountry insertActualCountry(String sysId)
	throws CountryException {
		try {
			return getCountryDAO().insertActualCountry(sysId);
		} catch (HibernateOptimisticLockingFailureException ex) {
			throw new CountryException("File is not in proper format");
		}
	}
	
	public ICountry insertCountry(
			ICountry holiday)
			throws CountryException {
		if (!(holiday == null)) {
			return getCountryDAO().insertCountry("actualCountry", holiday);
		} else {
			throw new CountryException(
					"ERROR-  Relationshp Manager object is null. ");
		}
	}

	public boolean isCountryCodeUnique(String countryCode) {
		return getCountryDAO().isCountryCodeUnique(countryCode);
	}
	
	public boolean isCountryNameUnique(String countryName) {
		return getCountryDAO().isCountryNameUnique(countryName);
	}
	
	public void deleteTransaction(OBFileMapperMaster obFileMapperMaster)throws NoSuchGeographyException, TrxParameterException,TransactionException {
		getCountryDAO().deleteTransaction(obFileMapperMaster);		
	}
}
