package com.integrosys.cms.app.otherbank.trx;

import com.integrosys.cms.app.fileInsertMapper.bus.IFileMapperId;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.ui.otherbankbranch.IOtherBank;

/**
 * @author dattatray.thorat
 */
public interface IOtherBankTrxValue extends ICMSTrxValue{
	
	public IOtherBank getOtherBank();
	
	public IOtherBank getStagingOtherBank();

	public void setOtherBank(IOtherBank value);
	
	public void setStagingOtherBank(IOtherBank value);
	
	
	//*********************UPLOAD*************************
	

	   public void setStagingFileMapperID(IFileMapperId value);
	   
	   public IFileMapperId getFileMapperID();
	   
	   public IFileMapperId getStagingFileMapperID();
	   
	   public void setFileMapperID(IFileMapperId value);
	   
	   
	    
	
}
