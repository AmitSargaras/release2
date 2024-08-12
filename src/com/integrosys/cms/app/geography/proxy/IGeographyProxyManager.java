package com.integrosys.cms.app.geography.proxy;

import java.util.List;

import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.cms.app.geography.bus.NoSuchGeographyException;
import com.integrosys.cms.app.geography.country.bus.ICountry;
import com.integrosys.cms.app.geography.country.trx.ICountryTrxValue;
import com.integrosys.cms.app.transaction.ITrxContext;

public interface IGeographyProxyManager {

	public List listContinent() throws NoSuchGeographyException;

	public List listCountry(String type, String text) throws NoSuchGeographyException;

	public List listRegion() throws NoSuchGeographyException;

	public List listState(String type, String text) throws NoSuchGeographyException;

	public List listCity(String type, String text) throws NoSuchGeographyException;

	
	public void deleteCountry(long id) throws NoSuchGeographyException;

	public void deleteRegion(long id) throws NoSuchGeographyException;

	public void deleteState(long id) throws NoSuchGeographyException;

	public void deleteCity(long id) throws NoSuchGeographyException;
	
}
