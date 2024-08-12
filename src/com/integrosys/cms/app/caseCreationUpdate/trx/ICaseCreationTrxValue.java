package com.integrosys.cms.app.caseCreationUpdate.trx;

import com.integrosys.cms.app.fileInsertMapper.bus.IFileMapperId;
import com.integrosys.cms.app.caseCreationUpdate.bus.ICaseCreation;
import com.integrosys.cms.app.transaction.ICMSTrxValue;

/**
 * @author abhijit.rudrakshawar
 */
 
public interface ICaseCreationTrxValue  extends ICMSTrxValue {

    public ICaseCreation getCaseCreation();

    public ICaseCreation getStagingCaseCreation();
    
    public IFileMapperId getStagingFileMapperID();
    
    public IFileMapperId getFileMapperID();

    public void setCaseCreation(ICaseCreation value);

    public void setStagingCaseCreation(ICaseCreation value);
    
    public void setStagingFileMapperID(IFileMapperId value);
    
    public void setFileMapperID(IFileMapperId value);
}
