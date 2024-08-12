package com.integrosys.cms.app.geography.city.trx;

import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.fileInsertMapper.bus.IFileMapperId;
import com.integrosys.cms.app.geography.city.bus.ICity;
import com.integrosys.cms.app.geography.region.bus.IRegion;
import com.integrosys.cms.app.transaction.OBCMSTrxValue;

/**
 * 
 * @author sandiip.shinde
 * @since 14-04-2011
 */

public class OBCityTrxValue extends OBCMSTrxValue implements ICityTrxValue{

	//private static final long serialVersionUID = -8925595229657400462L;
	
	private ICity actualCity;
	
	private ICity stagingCity;
	
	IFileMapperId FileMapperID;
    IFileMapperId stagingFileMapperID;
    

	/**
	 * @return the fileMapperID
	 */
	public IFileMapperId getFileMapperID() {
		return FileMapperID;
	}

	/**
	 * @param fileMapperID the fileMapperID to set
	 */
	public void setFileMapperID(IFileMapperId fileMapperID) {
		FileMapperID = fileMapperID;
	}

	/**
	 * @return the stagingFileMapperID
	 */
	public IFileMapperId getStagingFileMapperID() {
		return stagingFileMapperID;
	}

	/**
	 * @param stagingFileMapperID the stagingFileMapperID to set
	 */
	public void setStagingFileMapperID(IFileMapperId stagingFileMapperID) {
		this.stagingFileMapperID = stagingFileMapperID;
	}

	public ICity getActualCity() {
		return actualCity;
	}

	public void setActualCity(ICity actualCity) {
		this.actualCity = actualCity;
	}

	public ICity getStagingCity() {
		return stagingCity;
	}

	public void setStagingCity(ICity stagingCity) {
		this.stagingCity = stagingCity;
	}

	/**
	 * Default Constructor
	 */
	public OBCityTrxValue() {
		super.setTransactionType(ICMSConstant.INSTANCE_CITY);
	}

	/**
	 * Construct OB from interface
	 * 
	 * @param value is of type ITrxValue
	 */
	public OBCityTrxValue(ITrxValue value) {
		this();
		AccessorUtil.copyValue(value, this);
	}

}
