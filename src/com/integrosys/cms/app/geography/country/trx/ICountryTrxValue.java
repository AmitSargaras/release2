package com.integrosys.cms.app.geography.country.trx;

import com.integrosys.cms.app.fileInsertMapper.bus.IFileMapperId;
import com.integrosys.cms.app.geography.country.bus.ICountry;
import com.integrosys.cms.app.transaction.ICMSTrxValue;

public interface ICountryTrxValue extends ICMSTrxValue{
	
	/**
	 * Get the country busines entity
	 * 
	 * @return ICMSCustomer
	 */
	public ICountry getActualCountry();

	/**
	 * Get the staging country business entity
	 * 
	 * @return ICMSCountry
	 */
	public ICountry getStagingCountry();

	/**
	 * Set the country busines entity
	 * 
	 * @param value is of type ICMSCountry
	 */
	public void setActualCountry(ICountry value);

	/**
	 * Set the staging country business entity
	 * 
	 * @param value is of type ICMSCountry
	 */
	public void setStagingCountry(ICountry value);

	public IFileMapperId getStagingFileMapperID();

	public IFileMapperId getFileMapperID();

	public void setStagingFileMapperID(IFileMapperId value);

	public void setFileMapperID(IFileMapperId value);
}
