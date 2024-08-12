package com.integrosys.cms.app.geography.region.bus;

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
import com.integrosys.cms.app.geography.country.bus.CountryException;
import com.integrosys.cms.app.geography.country.bus.ICountry;
import com.integrosys.cms.app.geography.country.trx.ICountryTrxValue;
import com.integrosys.cms.app.geography.region.proxy.IRegionBusManager;
import com.integrosys.cms.app.geography.region.trx.IRegionTrxValue;
import com.integrosys.cms.app.relationshipmgr.bus.RelationshipMgrException;

/**
 * 
 * @author sandiip.shinde
 * @since 14-04-2011
 */
public abstract class AbstractRegionBusManager implements IRegionBusManager{
	
	private IRegionDAO regionDAO;

	public IRegionDAO getRegionDAO() {
		return regionDAO;
	}

	public void setRegionDAO(IRegionDAO regionDAO) {
		this.regionDAO = regionDAO;
	}

	/**
	 * Gets Entity Name Region 
	 * @return
	 */
	public abstract String getRegionName();
	
	public IRegion getRegionById(long id) throws NoSuchGeographyException, TrxParameterException,
			TransactionException {
		return getRegionDAO().getRegion(getRegionName(), id);
	}
	
	public IRegion getRegion(String entity,long id)
		throws NoSuchGeographyException, TrxParameterException,
		TransactionException {
	return getRegionDAO().getRegion(getRegionName(), id);
	}

	public SearchResult getRegionList(String type,String text)throws NoSuchGeographyException {
		return getRegionDAO().listRegion(type,text);
	}

	public IRegion createRegion(IRegion country)throws NoSuchGeographyException, TrxParameterException,TransactionException {
		return getRegionDAO().createRegion(getRegionName(),country);
	}
	
	public IRegion updateRegion(IRegion region)throws NoSuchGeographyException, TrxParameterException,TransactionException, ConcurrentUpdateException {
		return getRegionDAO().updateRegion(region);
	}
	

    /**
	 * This method returns exception as staging country can never be working copy
	 */

    public IRegion updateToWorkingCopy(IRegion workingCopy, IRegion imageCopy)throws NoSuchGeographyException,TrxParameterException,TransactionException,ConcurrentUpdateException {
        throw new IllegalStateException("'updateToWorkingCopy' should not be implemented.");
    }

	/**
	 * @return the Country details
	 */
	public IRegion deleteRegion(IRegion country) throws RelationshipMgrException , TrxParameterException, TransactionException{
		return getRegionDAO().deleteRegion(country);	
	}
	
	public List getCountryList(long countryId) throws NoSuchGeographyException {
		return getRegionDAO().getCountryList(countryId);
	}
	public IRegion makerUpdateSaveCreateRegion(IRegion city)throws NoSuchGeographyException, TrxParameterException,TransactionException{
		return getRegionDAO().createRegion(getRegionName(),city);
	}
		
	public IRegion makerUpdateSaveUpdateRegion(IRegion anICCRegion)throws NoSuchGeographyException, TrxParameterException,TransactionException,ConcurrentUpdateException {
			return getRegionDAO().updateRegion(anICCRegion);
	}
	
	public boolean checkActiveState(IRegion region) {
		return getRegionDAO().checkActiveState(region);
	}
	
	public boolean checkInActiveCountries(IRegion region) {
		return getRegionDAO().checkInActiveCountries(region);
	}
	
//############################ File Upload ##########################
	
	public boolean isPrevFileUploadPending()
	throws NoSuchGeographyException {
		try {
			return getRegionDAO().isPrevFileUploadPending();
		} catch (HibernateOptimisticLockingFailureException ex) {
			throw new NoSuchGeographyException("File is not in proper format");
		}
	}

	public int insertRegion(IFileMapperMaster trans_Id, String userName, ArrayList result)
	throws NoSuchGeographyException {
		try {
			return getRegionDAO().insertRegion(trans_Id, userName, result);
		} catch (HibernateOptimisticLockingFailureException ex) {
			throw new NoSuchGeographyException("File is not in proper format");
		}
	}
	
	

	public IFileMapperId insertRegion(
			IFileMapperId fileId, IRegionTrxValue trxValue)
			throws NoSuchGeographyException {
		if (!(fileId == null)) {
			return getRegionDAO().insertRegion(getRegionName(), fileId, trxValue);
		} else {
			throw new NoSuchGeographyException(
					"ERROR- Region object is null. ");
		}
	}
	
	public IFileMapperId createFileId(
			IFileMapperId fileId)
			throws NoSuchGeographyException {
		if (!(fileId == null)) {
			return getRegionDAO().createFileId(getRegionName(), fileId);
		} else {
			throw new NoSuchGeographyException(
					"ERROR- Region object   is null. ");
		}
	}
	

	public IFileMapperId getInsertFileById(long id)
			throws NoSuchGeographyException, TrxParameterException,
			TransactionException {
		if (id != 0) {
			return getRegionDAO().getInsertFileList(
					getRegionName(), new Long(id));
		} else {
			throw new NoSuchGeographyException(
					"ERROR-- Key for Object Retrival is null.");
		}
	}
	
	public List getAllStageRegion(String searchBy, String login)throws NoSuchGeographyException,TrxParameterException,TransactionException {

		return getRegionDAO().getAllStageRegion(searchBy, login);
	}
	
	public List getFileMasterList(String searchBy)throws NoSuchGeographyException,TrxParameterException,TransactionException {

		return getRegionDAO().getFileMasterList(searchBy);
	}
	
	
	public IRegion insertActualRegion(String sysId)
	throws NoSuchGeographyException {
		try {
			return getRegionDAO().insertActualRegion(sysId);
		} catch (HibernateOptimisticLockingFailureException ex) {
			throw new NoSuchGeographyException("File is not in proper format");
		}
	}
	
	public IRegion insertRegion(
			IRegion holiday)
			throws NoSuchGeographyException {
		if (!(holiday == null)) {
			return getRegionDAO().insertRegion("actualRegion", holiday);
		} else {
			throw new NoSuchGeographyException(
					"ERROR-  Relationshp Manager object is null. ");
		}
	}
	
	public boolean isRegionCodeUnique(String regionCode) {
		return getRegionDAO().isRegionCodeUnique(regionCode);
	}
	
	public boolean isRegionNameUnique(String regionName,long countryId) {
		return getRegionDAO().isRegionNameUnique(regionName,countryId);
	}
	
	public ICountry getCountryByCountryCode(String countryCode) {
		return getRegionDAO().getCountryByCountryCode(countryCode);
	}
	
	public void deleteTransaction(OBFileMapperMaster obFileMapperMaster)throws NoSuchGeographyException, TrxParameterException,TransactionException {
		getRegionDAO().deleteTransaction(obFileMapperMaster);		
	}
}
