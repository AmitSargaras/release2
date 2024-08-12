package com.integrosys.cms.app.geography.region.trx;

import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.fileInsertMapper.bus.IFileMapperId;
import com.integrosys.cms.app.geography.region.bus.IRegion;
import com.integrosys.cms.app.transaction.OBCMSTrxValue;

/**
 * 
 * @author sandiip.shinde
 * @since 14-04-2011
 */

public class OBRegionTrxValue extends OBCMSTrxValue implements IRegionTrxValue{

	//private static final long serialVersionUID = -8925595229657400462L;
	
	private IRegion actualRegion;
	
	private IRegion stagingRegion;
	
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

	public IRegion getActualRegion() {
		return actualRegion;
	}

	public void setActualRegion(IRegion actualRegion) {
		this.actualRegion = actualRegion;
	}

	public IRegion getStagingRegion() {
		return stagingRegion;
	}

	public void setStagingRegion(IRegion stagingRegion) {
		this.stagingRegion = stagingRegion;
	}

	/**
	 * Default Constructor
	 */
	public OBRegionTrxValue() {
		super.setTransactionType(ICMSConstant.INSTANCE_REGION);
	}

	/**
	 * Construct OB from interface
	 * 
	 * @param value is of type ITrxValue
	 */
	public OBRegionTrxValue(ITrxValue value) {
		this();
		AccessorUtil.copyValue(value, this);
	}

}
