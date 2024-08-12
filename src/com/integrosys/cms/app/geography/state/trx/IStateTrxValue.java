package com.integrosys.cms.app.geography.state.trx;

import com.integrosys.cms.app.fileInsertMapper.bus.IFileMapperId;
import com.integrosys.cms.app.geography.state.bus.IState;
import com.integrosys.cms.app.transaction.ICMSTrxValue;

public interface IStateTrxValue extends ICMSTrxValue{

	/**
	 * Get the state busines entity
	 * 
	 * @return ICMSCustomer
	 */
	public IState getActualState();

	/**
	 * Get the staging state business entity
	 * 
	 * @return ICMSState
	 */
	public IState getStagingState();

	/**
	 * Set the state busines entity
	 * 
	 * @param value is of type ICMSState
	 */
	public void setActualState(IState value);

	/**
	 * Set the staging state business entity
	 * 
	 * @param value is of type ICMSState
	 */
	public void setStagingState(IState value);
	
	public IFileMapperId getStagingFileMapperID();

	public IFileMapperId getFileMapperID();

	public void setStagingFileMapperID(IFileMapperId value);

	public void setFileMapperID(IFileMapperId value);

}
