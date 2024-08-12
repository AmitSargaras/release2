package com.integrosys.cms.app.geography.proxy;

import java.util.List;

import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.cms.app.geography.bus.IGeography;
import com.integrosys.cms.app.geography.bus.NoSuchGeographyException;
import com.integrosys.cms.app.geography.country.bus.ICountry;
import com.integrosys.cms.app.geography.country.trx.ICountryTrxValue;
import com.integrosys.cms.app.transaction.ITrxContext;

public interface IGeographyBusManager {

	public List listContinent();

	public List listCountry(String type, String text);

	public List listRegion();

	public List listState(String type, String text);

	public List listCity(String type, String text);

	
	public void deleteCountry(long id);

	public void deleteRegion(long id);

	public void deleteState(long id);

	public void deleteCity(long id);	
}
