package com.integrosys.cms.app.geography.state.trx;

import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.fileInsertMapper.bus.IFileMapperId;
import com.integrosys.cms.app.geography.state.bus.IState;
import com.integrosys.cms.app.transaction.OBCMSTrxValue;

/**
 * 
 * @author sandiip.shinde
 * @since 14-04-2011
 */

public class OBStateTrxValue extends OBCMSTrxValue implements IStateTrxValue{

	//private static final long serialVersionUID = -8925595229657400462L;
	
	private IState actualState;
	
	private IState stagingState;
	
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

	public IState getActualState() {
		return actualState;
	}

	public void setActualState(IState actualState) {
		this.actualState = actualState;
	}

	public IState getStagingState() {
		return stagingState;
	}

	public void setStagingState(IState stagingState) {
		this.stagingState = stagingState;
	}

	/**
	 * Default Constructor
	 */
	public OBStateTrxValue() {
		super.setTransactionType(ICMSConstant.INSTANCE_STATE);
	}

	/**
	 * Construct OB from interface
	 * 
	 * @param value is of type ITrxValue
	 */
	public OBStateTrxValue(ITrxValue value) {
		this();
		AccessorUtil.copyValue(value, this);
	}

}
