package com.integrosys.cms.app.fccBranch.trx;

import com.integrosys.cms.app.fccBranch.bus.IFCCBranch;
import com.integrosys.cms.app.fileInsertMapper.bus.IFileMapperId;
import com.integrosys.cms.app.transaction.ICMSTrxValue;

/****
 * 
 * @author komal.agicha
 *
 */
 
public interface IFCCBranchTrxValue  extends ICMSTrxValue {

    public IFCCBranch getFCCBranch();

    public IFCCBranch getStagingFCCBranch();
    
    public IFileMapperId getStagingFileMapperID();
    
    public IFileMapperId getFileMapperID();

    public void setFCCBranch(IFCCBranch value);

    public void setStagingFCCBranch(IFCCBranch value);
    
    public void setStagingFileMapperID(IFileMapperId value);
    
    public void setFileMapperID(IFileMapperId value);
}
