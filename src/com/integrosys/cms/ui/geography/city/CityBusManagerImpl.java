package com.integrosys.cms.ui.geography.city;

import java.util.List;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;
import com.integrosys.cms.app.geography.bus.NoSuchGeographyException;
import com.integrosys.cms.app.geography.city.bus.AbstractCityBusManager;
import com.integrosys.cms.app.geography.city.bus.ICity;
import com.integrosys.cms.app.geography.city.bus.ICityDAO;
import com.integrosys.cms.app.geography.city.proxy.ICityBusManager;

/**
 * @author $Author: Sandeep Shinde
 * @version 2.0
 * @since $Date: 14/04/2011 02:12:00 $ Tag: $Name: $
 */

public class CityBusManagerImpl extends AbstractCityBusManager implements ICityBusManager {

	private ICityDAO cityDAO;

	public ICityDAO getCityDAO() {
		return cityDAO;
	}

	public void setCityDAO(ICityDAO cityDAO) {
		this.cityDAO = cityDAO;
	}
	
	public String getCityName(){
		return ICityDAO.ACTUAL_ENTITY_NAME_CITY;
	}
	
	public ICity createCity(ICity city) throws NoSuchGeographyException,
			TrxParameterException, TransactionException {
		return getCityDAO().createCity(city);
	}

	public ICity deleteCity(ICity city) throws NoSuchGeographyException,
			TrxParameterException, TransactionException {
		return getCityDAO().deleteCity(city);
	}

	public ICity getCityById(long id) throws NoSuchGeographyException,
			TrxParameterException, TransactionException {
		return getCityDAO().getCityById(id);
	}

	public SearchResult listCity(String type, String text)
			throws NoSuchGeographyException {
		return getCityDAO().listCity(type, text);
	}

	public ICity updateCity(ICity city) throws NoSuchGeographyException,
			TrxParameterException, TransactionException,
			ConcurrentUpdateException {
		return getCityDAO().updateCity(city);
	}

	public ICity updateToWorkingCopy(ICity workingCopy, ICity imageCopy)
			throws NoSuchGeographyException, TrxParameterException,
			TransactionException, ConcurrentUpdateException {
		ICity updated;
		try {
			workingCopy.setCityName(imageCopy.getCityName());
			workingCopy.setCityCode(imageCopy.getCityCode());
			workingCopy.setStateId(imageCopy.getStateId());
			workingCopy.setEcbfCityId(imageCopy.getEcbfCityId());
			updated = updateCity(workingCopy);
		} catch (NoSuchGeographyException e) {
			throw new NoSuchGeographyException(
					"Error while Copying copy to main file");
		}
		return updateCity(updated);
	}

	public List getCountryList(long countryId) throws NoSuchGeographyException {
		return getCityDAO().getCountryList(countryId);
	}

	public List getRegionList(long stateId) throws NoSuchGeographyException {
		return getCityDAO().getRegionList(stateId);
	}

	public List getStateList(long stateId) throws NoSuchGeographyException {
		return getCityDAO().getStateList(stateId);
	}
	
	public List getCityList(long stateId) throws NoSuchGeographyException {
		return getCityDAO().getCityList(stateId);
	}

	public ICity makerUpdateSaveCreateCity(ICity anICCCity)
			throws NoSuchGeographyException, TrxParameterException,
			TransactionException {
		return getCityDAO().createCity(anICCCity);
	}

	public ICity makerUpdateSaveUpdateCity(ICity anICCCity)
			throws NoSuchGeographyException, TrxParameterException,
			TransactionException {
		try {
			return getCityDAO().updateCity(anICCCity);
		} catch (ConcurrentUpdateException e) {
			e.printStackTrace();
		}
		return null;
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
}
