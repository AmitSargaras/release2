package com.integrosys.cms.ui.geography.country;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;
import com.integrosys.cms.app.fileInsertMapper.bus.OBFileMapperMaster;
import com.integrosys.cms.app.geography.bus.NoSuchGeographyException;
import com.integrosys.cms.app.geography.country.bus.AbstractCountryBusManager;
import com.integrosys.cms.app.geography.country.bus.ICountry;
import com.integrosys.cms.app.geography.country.bus.ICountryDAO;
import com.integrosys.cms.app.geography.country.proxy.ICountryBusManager;
import com.integrosys.cms.app.relationshipmgr.bus.RelationshipMgrException;

public class CountryBusManagerImpl extends AbstractCountryBusManager implements ICountryBusManager {

	private ICountryDAO countryDAO;

	public ICountryDAO getCountryDAO() {
		return countryDAO;
	}

	public void setCountryDAO(ICountryDAO countryDAO) {
		this.countryDAO = countryDAO;
	}
	
	public String getCountryName(){
		return ICountryDAO.ACTUAL_ENTITY_NAME_COUNTRY;
	}

	public ICountry createCountry(ICountry country)
			throws NoSuchGeographyException, TrxParameterException,
			TransactionException {
		return getCountryDAO().createCountry(country);
	}

	public ICountry deleteCountry(ICountry country)
			throws NoSuchGeographyException, TrxParameterException,
			TransactionException {
		return getCountryDAO().deleteCountry(country);
	}

	public ICountry getCountryById(long id) throws NoSuchGeographyException,
			TrxParameterException, TransactionException {
		return getCountryDAO().getCountryById(id);
	}

	public SearchResult getCountryList(String type, String text)
			throws NoSuchGeographyException {
		return getCountryDAO().listCountry(type, text);
	}

	public ICountry updateCountry(ICountry country)
			throws NoSuchGeographyException, TrxParameterException,
			TransactionException, ConcurrentUpdateException {
		return getCountryDAO().updateCountry(country);
	}

	public ICountry updateToWorkingCopy(ICountry workingCopy, ICountry imageCopy)
			throws NoSuchGeographyException, TrxParameterException,
			TransactionException, ConcurrentUpdateException {
		ICountry updated;
		try {
			workingCopy.setCountryName(imageCopy.getCountryName());
			workingCopy.setCountryCode(imageCopy.getCountryCode());

			updated = updateCountry(workingCopy);
		} catch (RelationshipMgrException e) {
			throw new RelationshipMgrException(
					"Error while Copying copy to main file");
		}
		return updateCountry(updated);
	}

	public ICountry makerUpdateSaveCreateCountry(ICountry country)
			throws NoSuchGeographyException, TrxParameterException,
			TransactionException {
		return getCountryDAO().createCountry(country);
	}

	public ICountry makerUpdateSaveUpdateCountry(ICountry country)
			throws NoSuchGeographyException, TrxParameterException,
			TransactionException, ConcurrentUpdateException {
		return getCountryDAO().updateCountry(country);
	}

	public boolean checkActiveRegion(ICountry country) {
		return getCountryDAO().checkActiveRegion(country);
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
