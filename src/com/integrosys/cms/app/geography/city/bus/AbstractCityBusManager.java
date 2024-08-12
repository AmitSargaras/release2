package com.integrosys.cms.app.geography.city.bus;

import java.util.ArrayList;
import java.util.List;

import org.springframework.orm.hibernate4.HibernateOptimisticLockingFailureException;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;
import com.integrosys.cms.app.fileInsertMapper.bus.IFileMapperId;
import com.integrosys.cms.app.fileInsertMapper.bus.IFileMapperMaster;
import com.integrosys.cms.app.fileInsertMapper.bus.OBFileMapperID;
import com.integrosys.cms.app.fileInsertMapper.bus.OBFileMapperMaster;
import com.integrosys.cms.app.geography.bus.NoSuchGeographyException;
import com.integrosys.cms.app.geography.city.proxy.ICityBusManager;
import com.integrosys.cms.app.geography.city.trx.ICityTrxValue;

/**
 * 
 * @author sandiip.shinde
 * @since 14-04-2011
 */
public abstract class AbstractCityBusManager implements ICityBusManager{
	
	private ICityDAO cityDAO;

	public ICityDAO getCityDAO() {
		return cityDAO;
	}

	public void setCityDAO(ICityDAO cityDAO) {
		this.cityDAO = cityDAO;
	}

	/**
	 * Gets Entity Name Region 
	 * @return
	 */
	public abstract String getCityName();
	
	public ICity getCityById(long id) throws NoSuchGeographyException, TrxParameterException,
			TransactionException {
		return getCityDAO().getCity(getCityName(), id);
	}
	
	public ICity getCity(String entity,long id)
		throws NoSuchGeographyException, TrxParameterException,
		TransactionException {
	return getCityDAO().getCity(getCityName(), id);
	}

	public ICity createCity(ICity city)throws NoSuchGeographyException, TrxParameterException,TransactionException {
		return getCityDAO().createCity(getCityName(),city);
	}
	
	public ICity updateCity(ICity city)
		throws NoSuchGeographyException, TrxParameterException,
		TransactionException, ConcurrentUpdateException {
	return getCityDAO().updateCity(city);
	}
	
	public List getCountryList(long countryId) throws NoSuchGeographyException {
		return getCityDAO().getCountryList(countryId);
	}
	
	public List getStateList(long stateId) throws NoSuchGeographyException {
		return getCityDAO().getStateList(stateId);
	}
	
	
	public List getCityList(long stateId) throws NoSuchGeographyException {
		return getCityDAO().getCityList(stateId);
	}
	
	public List getRegionList(long stateId) throws NoSuchGeographyException {
		return getCityDAO().getRegionList(stateId);
	}
	
	public SearchResult listCity(String type, String text)throws NoSuchGeographyException {
		return getCityDAO().listCity(type, text);
	}
	
	public ICity makerUpdateSaveCreateCity(ICity city)throws NoSuchGeographyException, TrxParameterException,TransactionException{
		return getCityDAO().createCity(getCityName(),city);
	}
		
	public ICity makerUpdateSaveUpdateCity(ICity anICCCity)throws NoSuchGeographyException, TrxParameterException,TransactionException,ConcurrentUpdateException {
		return getCityDAO().updateCity(anICCCity);
	}
	
	   /**
	 * This method returns exception as staging city can never be working copy
	 */

    public ICity updateToWorkingCopy(ICity workingCopy, ICity imageCopy)throws NoSuchGeographyException,TrxParameterException,TransactionException,ConcurrentUpdateException {
        throw new IllegalStateException("'updateToWorkingCopy' should not be implemented.");
    }

	/**
	 * @return the Country details
	 */
	public ICity deleteCity(ICity city) throws NoSuchGeographyException , TrxParameterException, TransactionException{
		return getCityDAO().deleteCity(city);	
	}
	
	public boolean checkInActiveStates(ICity city) {
		return getCityDAO().checkInActiveStates(city);
	}
	
	public boolean isCityCodeUnique(String cityCode) {
		return getCityDAO().isCityCodeUnique(cityCode);
	}
	
	public boolean isCityNameUnique(String cityName,long stateId) {
		return getCityDAO().isCityNameUnique(cityName,stateId);
	}
	
	public List getCityByCountryCode(String countryCode)
			throws NoSuchGeographyException {
		return getCityDAO().getCityByCountryCode(countryCode);
	}
//############################ File Upload ##########################
	
	public boolean isPrevFileUploadPending()
	throws NoSuchGeographyException {
		try {
			return getCityDAO().isPrevFileUploadPending();
		} catch (HibernateOptimisticLockingFailureException ex) {
			throw new NoSuchGeographyException("File is not in proper format");
		}
	}

	public int insertCity(IFileMapperMaster trans_Id, String userName, ArrayList result)
	throws NoSuchGeographyException {
		try {
			return getCityDAO().insertCity(trans_Id, userName, result);
		} catch (HibernateOptimisticLockingFailureException ex) {
			throw new NoSuchGeographyException("File is not in proper format");
		}
	}
	
	

	public IFileMapperId insertCity(
			IFileMapperId fileId, ICityTrxValue trxValue)
			throws NoSuchGeographyException {
		if (!(fileId == null)) {
			return getCityDAO().insertCity(getCityName(), fileId, trxValue);
		} else {
			throw new NoSuchGeographyException(
					"ERROR- City object is null. ");
		}
	}
	
	public IFileMapperId createFileId(
			IFileMapperId fileId)
			throws NoSuchGeographyException {
		if (!(fileId == null)) {
			return getCityDAO().createFileId(getCityName(), fileId);
		} else {
			throw new NoSuchGeographyException(
					"ERROR- City object   is null. ");
		}
	}
	

	public IFileMapperId getInsertFileById(long id)
			throws NoSuchGeographyException, TrxParameterException,
			TransactionException {
		if (id != 0) {
			return getCityDAO().getInsertFileList(
					getCityName(), new Long(id));
		} else {
			throw new NoSuchGeographyException(
					"ERROR-- Key for Object Retrival is null.");
		}
	}
	
	public List getAllStageCity(String searchBy, String login)throws NoSuchGeographyException,TrxParameterException,TransactionException {

		return getCityDAO().getAllStageCity(searchBy, login);
	}
	
	public List getFileMasterList(String searchBy)throws NoSuchGeographyException,TrxParameterException,TransactionException {

		return getCityDAO().getFileMasterList(searchBy);
	}
	
	
	public ICity insertActualCity(String sysId)
	throws NoSuchGeographyException {
		try {
			return getCityDAO().insertActualCity(sysId);
		} catch (HibernateOptimisticLockingFailureException ex) {
			throw new NoSuchGeographyException("File is not in proper format");
		}
	}
	
	public ICity insertCity(
			ICity holiday)
			throws NoSuchGeographyException {
		if (!(holiday == null)) {
			return getCityDAO().insertCity("actualCity", holiday);
		} else {
			throw new NoSuchGeographyException(
					"ERROR-  Relationshp Manager object is null. ");
		}
	}
	
	public void deleteTransaction(OBFileMapperMaster obFileMapperMaster)    //A shiv 170811
	throws NoSuchGeographyException {
		try {
			getCityDAO().deleteTransaction(obFileMapperMaster);
		} catch (HibernateOptimisticLockingFailureException ex) {
			throw new NoSuchGeographyException("File is not in proper format");
		}
	}
}
