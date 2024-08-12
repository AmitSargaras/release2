package com.integrosys.cms.app.geography.city.trx;

import com.integrosys.cms.app.fileInsertMapper.bus.IFileMapperId;
import com.integrosys.cms.app.geography.city.bus.ICity;
import com.integrosys.cms.app.transaction.ICMSTrxValue;


/**
 * 
 * @author sandiip.shinde
 * @since 14-04-2011
 */

public interface ICityTrxValue extends ICMSTrxValue{

	/**
	 * Get the city business entity
	 * 
	 * @return ICMSCustomer
	 */
	public ICity getActualCity();

	/**
	 * Get the staging city business entity
	 * 
	 * @return ICMSCity
	 */
	public ICity getStagingCity();

	/**
	 * Set the city business entity
	 * 
	 * @param value is of type ICMSCity
	 */
	public void setActualCity(ICity value);

	/**
	 * Set the staging city business entity
	 * 
	 * @param value is of type ICMSCity
	 */
	public void setStagingCity(ICity value);
	
	
	public IFileMapperId getStagingFileMapperID();

	public IFileMapperId getFileMapperID();

	public void setStagingFileMapperID(IFileMapperId value);

	public void setFileMapperID(IFileMapperId value);
}
