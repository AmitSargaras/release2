package com.integrosys.cms.app.systemBankBranch.trx;

import com.integrosys.cms.app.fileInsertMapper.bus.IFileMapperId;
import com.integrosys.cms.app.systemBankBranch.bus.ISystemBankBranch;
import com.integrosys.cms.app.transaction.ICMSTrxValue;

/**
 * @author abhijit.rudrakshawar
 */
 
public interface ISystemBankBranchTrxValue  extends ICMSTrxValue {

    public ISystemBankBranch getSystemBankBranch();

    public ISystemBankBranch getStagingSystemBankBranch();
    
    public IFileMapperId getStagingFileMapperID();
    
    public IFileMapperId getFileMapperID();

    public void setSystemBankBranch(ISystemBankBranch value);

    public void setStagingSystemBankBranch(ISystemBankBranch value);
    
    public void setStagingFileMapperID(IFileMapperId value);
    
    public void setFileMapperID(IFileMapperId value);
}
