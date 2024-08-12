package com.integrosys.cms.app.otherbranch.trx;

import com.integrosys.cms.app.fileInsertMapper.bus.IFileMapperId;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.ui.otherbankbranch.IOtherBranch;

/**
 * @author dattatray.thorat
 */
public interface IOtherBankBranchTrxValue extends ICMSTrxValue{
	
	public IOtherBranch getOtherBranch();
	
	public IOtherBranch getStagingOtherBranch();

	public void setOtherBranch(IOtherBranch value);
	
	public void setStagingOtherBranch(IOtherBranch value);
	
	
	//*********************UPLOAD*************************
	

	   public void setStagingFileMapperID(IFileMapperId value);
	   
	   public IFileMapperId getFileMapperID();
	   
	   public IFileMapperId getStagingFileMapperID();
	   
	   public void setFileMapperID(IFileMapperId value);
	   
}
