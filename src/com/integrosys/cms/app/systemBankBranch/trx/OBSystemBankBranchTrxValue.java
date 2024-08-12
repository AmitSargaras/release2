package com.integrosys.cms.app.systemBankBranch.trx;

import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.fileInsertMapper.bus.IFileMapperId;
import com.integrosys.cms.app.systemBankBranch.bus.ISystemBankBranch;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.OBCMSTrxValue;

/**
 * @author abhijit.rudrakshawar
 */
public class OBSystemBankBranchTrxValue extends OBCMSTrxValue implements ISystemBankBranchTrxValue{

    public  OBSystemBankBranchTrxValue(){}

    ISystemBankBranch systemBankBranch ;
    ISystemBankBranch stagingSystemBankBranch ;
    IFileMapperId FileMapperID;
    IFileMapperId stagingFileMapperID;

    public OBSystemBankBranchTrxValue(ICMSTrxValue anICMSTrxValue)
    {
        AccessorUtil.copyValue (anICMSTrxValue, this);
    }

	public ISystemBankBranch getSystemBankBranch() {
		return systemBankBranch;
	}

	public void setSystemBankBranch(ISystemBankBranch systemBankBranch) {
		this.systemBankBranch = systemBankBranch;
	}

	public ISystemBankBranch getStagingSystemBankBranch() {
		return stagingSystemBankBranch;
	}

	public void setStagingSystemBankBranch(ISystemBankBranch stagingSystemBankBranch) {
		this.stagingSystemBankBranch = stagingSystemBankBranch;
	}

	public IFileMapperId getStagingFileMapperID() {
		return stagingFileMapperID;
	}

	public void setStagingFileMapperID(IFileMapperId stagingFileMapperID) {
		this.stagingFileMapperID = stagingFileMapperID;
	}

	public IFileMapperId getFileMapperID() {
		return FileMapperID;
	}

	public void setFileMapperID(IFileMapperId fileMapperID) {
		FileMapperID = fileMapperID;
	}

	

}
