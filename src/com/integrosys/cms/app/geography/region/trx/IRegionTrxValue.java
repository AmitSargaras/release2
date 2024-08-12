package com.integrosys.cms.app.geography.region.trx;

import com.integrosys.cms.app.fileInsertMapper.bus.IFileMapperId;
import com.integrosys.cms.app.geography.region.bus.IRegion;
import com.integrosys.cms.app.transaction.ICMSTrxValue;


/**
 * 
 * @author sandiip.shinde
 * @since 14-04-2011
 */
public interface IRegionTrxValue extends ICMSTrxValue{
	
	/**
	 * Get the region busines entity
	 * 
	 * @return ICMSCustomer
	 */
	public IRegion getActualRegion();

	/**
	 * Get the staging region business entity
	 * 
	 * @return ICMSRegion
	 */
	public IRegion getStagingRegion();

	/**
	 * Set the region busines entity
	 * 
	 * @param value is of type ICMSRegion
	 */
	public void setActualRegion(IRegion value);

	/**
	 * Set the staging region business entity
	 * 
	 * @param value is of type ICMSRegion
	 */
	public void setStagingRegion(IRegion value);
	
	public IFileMapperId getStagingFileMapperID();

	public IFileMapperId getFileMapperID();

	public void setStagingFileMapperID(IFileMapperId value);

	public void setFileMapperID(IFileMapperId value);

}
