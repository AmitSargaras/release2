package com.integrosys.cms.app.caseBranch.trx;

import com.integrosys.cms.app.fileInsertMapper.bus.IFileMapperId;
import com.integrosys.cms.app.caseBranch.bus.ICaseBranch;
import com.integrosys.cms.app.transaction.ICMSTrxValue;

/**
 * @author abhijit.rudrakshawar
 */
 
public interface ICaseBranchTrxValue  extends ICMSTrxValue {

    public ICaseBranch getCaseBranch();

    public ICaseBranch getStagingCaseBranch();
    
    public IFileMapperId getStagingFileMapperID();
    
    public IFileMapperId getFileMapperID();

    public void setCaseBranch(ICaseBranch value);

    public void setStagingCaseBranch(ICaseBranch value);
    
    public void setStagingFileMapperID(IFileMapperId value);
    
    public void setFileMapperID(IFileMapperId value);
}
