package com.integrosys.cms.app.geography.country.trx;

import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.fileInsertMapper.bus.IFileMapperId;
import com.integrosys.cms.app.geography.country.bus.ICountry;
import com.integrosys.cms.app.transaction.OBCMSTrxValue;

public class OBCountryTrxValue extends OBCMSTrxValue implements ICountryTrxValue{

	//private static final long serialVersionUID = -8925595229657400462L;
	
	private ICountry actualCountry;
	
	private ICountry stagingCountry;
	
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

	/**
	 * Default Constructor
	 */
	public OBCountryTrxValue() {
		super.setTransactionType(ICMSConstant.INSTANCE_COUNTRY);
	}

	/**
	 * Construct OB from interface
	 * 
	 * @param value is of type ITrxValue
	 */
	public OBCountryTrxValue(ITrxValue value) {
		this();
		AccessorUtil.copyValue(value, this);
	}

	public ICountry getActualCountry() {
		return actualCountry;
	}

	public void setActualCountry(ICountry actualCountry) {
		this.actualCountry = actualCountry;
	}

	public ICountry getStagingCountry() {
		return stagingCountry;
	}

	public void setStagingCountry(ICountry stagingCountry) {
		this.stagingCountry = stagingCountry;
	}
}
