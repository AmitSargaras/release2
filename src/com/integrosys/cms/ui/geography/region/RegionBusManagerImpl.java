package com.integrosys.cms.ui.geography.region;

import java.util.List;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;
import com.integrosys.cms.app.fileInsertMapper.bus.OBFileMapperMaster;
import com.integrosys.cms.app.geography.bus.NoSuchGeographyException;
import com.integrosys.cms.app.geography.country.bus.ICountry;
import com.integrosys.cms.app.geography.region.bus.AbstractRegionBusManager;
import com.integrosys.cms.app.geography.region.bus.IRegion;
import com.integrosys.cms.app.geography.region.bus.IRegionDAO;
import com.integrosys.cms.app.geography.region.proxy.IRegionBusManager;
import com.integrosys.cms.app.relationshipmgr.bus.RelationshipMgrException;

public class RegionBusManagerImpl extends AbstractRegionBusManager implements IRegionBusManager {

	private IRegionDAO regionDAO;

	public IRegionDAO getRegionDAO() {
		return regionDAO;
	}

	public void setRegionDAO(IRegionDAO regionDAO) {
		this.regionDAO = regionDAO;
	}
	
	public String getRegionName(){
		return IRegionDAO.ACTUAL_ENTITY_NAME_REGION;
	}

	public IRegion createRegion(IRegion country)
			throws NoSuchGeographyException, TrxParameterException,
			TransactionException {
		return getRegionDAO().createRegion(country);
	}

	public IRegion deleteRegion(IRegion country)
			throws NoSuchGeographyException, TrxParameterException,
			TransactionException {
		return getRegionDAO().deleteRegion(country);
	}

	public IRegion getRegionById(long id) throws NoSuchGeographyException,
			TrxParameterException, TransactionException {
		return getRegionDAO().getRegionById(id);
	}

	public SearchResult getRegionList(String type, String text)
			throws NoSuchGeographyException {
		return getRegionDAO().listRegion(type, text);
	}

	public IRegion updateRegion(IRegion region)
			throws NoSuchGeographyException, TrxParameterException,
			TransactionException, ConcurrentUpdateException {
		return getRegionDAO().updateRegion(region);
	}

	public IRegion updateToWorkingCopy(IRegion workingCopy, IRegion imageCopy)
			throws NoSuchGeographyException, TrxParameterException,
			TransactionException, ConcurrentUpdateException {
		IRegion updated;
		try {
			workingCopy.setRegionName(imageCopy.getRegionName());
			workingCopy.setRegionCode(imageCopy.getRegionCode());
			workingCopy.setCountryId(imageCopy.getCountryId());

			updated = updateRegion(workingCopy);
		} catch (RelationshipMgrException e) {
			throw new RelationshipMgrException(
					"Error while Copying copy to main file");
		}
		return updateRegion(updated);
	}

	public List getCountryList(long countryId) throws NoSuchGeographyException {
		return getRegionDAO().getCountryList(countryId);
	}

	public IRegion makerUpdateSaveCreateRegion(IRegion region)
			throws NoSuchGeographyException, TrxParameterException,
			TransactionException {
		return getRegionDAO().createRegion(region);
	}

	public IRegion makerUpdateSaveUpdateRegion(IRegion region)
			throws NoSuchGeographyException, TrxParameterException,
			TransactionException, ConcurrentUpdateException {
		return getRegionDAO().updateRegion(region);
	}

	public boolean checkActiveState(IRegion region) {
		return getRegionDAO().checkActiveState(region);
	}

	public boolean checkInActiveCountries(IRegion region) {
		return getRegionDAO().checkInActiveCountries(region);
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
