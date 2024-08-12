package com.integrosys.cms.app.caseBranch.trx;

import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.fileInsertMapper.bus.IFileMapperId;
import com.integrosys.cms.app.caseBranch.bus.ICaseBranch;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.OBCMSTrxValue;

/**
 * @author abhijit.rudrakshawar
 */
public class OBCaseBranchTrxValue extends OBCMSTrxValue implements ICaseBranchTrxValue{

    public  OBCaseBranchTrxValue(){}

    ICaseBranch caseBranch ;
    ICaseBranch stagingCaseBranch ;
    
    IFileMapperId FileMapperID;
    IFileMapperId stagingFileMapperID;
    
    public OBCaseBranchTrxValue(ICMSTrxValue anICMSTrxValue)
    {
        AccessorUtil.copyValue (anICMSTrxValue, this);
    }

	public ICaseBranch getCaseBranch() {
		return caseBranch;
	}

	public void setCaseBranch(ICaseBranch caseBranch) {
		this.caseBranch = caseBranch;
	}

	public ICaseBranch getStagingCaseBranch() {
		return stagingCaseBranch;
	}

	public void setStagingCaseBranch(ICaseBranch stagingCaseBranch) {
		this.stagingCaseBranch = stagingCaseBranch;
	}

	public IFileMapperId getFileMapperID() {
		return FileMapperID;
	}

	public void setFileMapperID(IFileMapperId fileMapperID) {
		FileMapperID = fileMapperID;
	}

	public IFileMapperId getStagingFileMapperID() {
		return stagingFileMapperID;
	}

	public void setStagingFileMapperID(IFileMapperId stagingFileMapperID) {
		this.stagingFileMapperID = stagingFileMapperID;
	}
    
   

}
