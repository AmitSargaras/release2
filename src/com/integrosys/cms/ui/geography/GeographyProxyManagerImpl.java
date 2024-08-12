package com.integrosys.cms.ui.geography;

import java.util.List;

import org.apache.commons.lang.Validate;

import com.integrosys.base.businfra.transaction.ITrxController;
import com.integrosys.base.businfra.transaction.ITrxControllerFactory;
import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.geography.bus.IGeography;
import com.integrosys.cms.app.geography.bus.NoSuchGeographyException;
import com.integrosys.cms.app.geography.country.bus.ICountry;
import com.integrosys.cms.app.geography.country.trx.ICountryTrxValue;
import com.integrosys.cms.app.geography.country.trx.OBCountryTrxValue;
import com.integrosys.cms.app.geography.proxy.IGeographyBusManager;
import com.integrosys.cms.app.geography.proxy.IGeographyProxyManager;
import com.integrosys.cms.app.transaction.ICMSTrxResult;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.ITrxContext;
import com.integrosys.cms.app.transaction.OBCMSTrxParameter;

public class GeographyProxyManagerImpl implements IGeographyProxyManager {

	private IGeographyBusManager geographyBusManager;
	
	private IGeographyBusManager stagingCountryBusManager;
	
    private ITrxControllerFactory trxControllerFactory;


	public IGeographyBusManager getGeographyBusManager() {
		return geographyBusManager;
	}

	public void setGeographyBusManager(IGeographyBusManager geographyBusManager) {
		this.geographyBusManager = geographyBusManager;
	}

	public IGeographyBusManager getStagingCountryBusManager() {
		return stagingCountryBusManager;
	}

	public void setStagingCountryBusManager(
			IGeographyBusManager stagingCountryBusManager) {
		this.stagingCountryBusManager = stagingCountryBusManager;
	}

	public ITrxControllerFactory getTrxControllerFactory() {
		return trxControllerFactory;
	}

	public void setTrxControllerFactory(ITrxControllerFactory trxControllerFactory) {
		this.trxControllerFactory = trxControllerFactory;
	}

	public List listContinent() throws NoSuchGeographyException {
		try {
			return getGeographyBusManager().listContinent();
		} catch (Exception e) {
			
			e.printStackTrace();
			throw new NoSuchGeographyException("Error While Listing Continent");  
		}
	}

	public List listCountry(String type, String text) throws NoSuchGeographyException{
		try {
			return getGeographyBusManager().listCountry(type, text);
		} catch (Exception e) {			
			e.printStackTrace();
			throw new NoSuchGeographyException("Error While Listing Country");
		}
	}

	public List listRegion() throws NoSuchGeographyException{
		try {
			return getGeographyBusManager().listRegion();
		} catch (Exception e) {			
			e.printStackTrace();
			throw new NoSuchGeographyException("Error While Listing Region");
		}
	}

	public List listState(String type, String text) throws NoSuchGeographyException{
		try {
			return getGeographyBusManager().listState(type, text);
		} catch (Exception e) {			
			e.printStackTrace();
			throw new NoSuchGeographyException("Error While Listing State");
		}
	}

	public List listCity(String type, String text) throws NoSuchGeographyException{
		try {
			return getGeographyBusManager().listCity(type, text);
		} catch (Exception e) {			
			e.printStackTrace();
			throw new NoSuchGeographyException("Error While Listing City");
		}
	}

	public void deleteCity(long id) throws NoSuchGeographyException{
		try {
			getGeographyBusManager().deleteCity(id);
		} catch (Exception e) {			
			e.printStackTrace();
			throw new NoSuchGeographyException("Error While Deleting City");
		}
	}

	public void deleteCountry(long id) throws NoSuchGeographyException{
		try {
			getGeographyBusManager().deleteCountry(id);
		} catch (Exception e) {			
			e.printStackTrace();
			throw new NoSuchGeographyException("Error While Deleting Country");
		}
	}

	public void deleteRegion(long id) throws NoSuchGeographyException{
		try {
			getGeographyBusManager().deleteRegion(id);
		} catch (Exception e) {			
			e.printStackTrace();
			throw new NoSuchGeographyException("Error While Deleting Region");
		}

	}

	public void deleteState(long id) throws NoSuchGeographyException{
		try {
			getGeographyBusManager().deleteState(id);
		} catch (Exception e) {
			e.printStackTrace();
			throw new NoSuchGeographyException("Error While Deleting State");
		}

	}
}
