package com.integrosys.cms.app.caseCreationUpdate.trx;

import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.fileInsertMapper.bus.IFileMapperId;
import com.integrosys.cms.app.caseCreationUpdate.bus.ICaseCreation;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.OBCMSTrxValue;

/**
 * @author abhijit.rudrakshawar
 */
public class OBCaseCreationTrxValue extends OBCMSTrxValue implements ICaseCreationTrxValue{

    public  OBCaseCreationTrxValue(){}

    ICaseCreation caseCreationUpdate ;
    ICaseCreation stagingCaseCreation ;
    
    IFileMapperId FileMapperID;
    IFileMapperId stagingFileMapperID;
    
    public OBCaseCreationTrxValue(ICMSTrxValue anICMSTrxValue)
    {
        AccessorUtil.copyValue (anICMSTrxValue, this);
    }

	public ICaseCreation getCaseCreation() {
		return caseCreationUpdate;
	}

	public void setCaseCreation(ICaseCreation caseCreationUpdate) {
		this.caseCreationUpdate = caseCreationUpdate;
	}

	public ICaseCreation getStagingCaseCreation() {
		return stagingCaseCreation;
	}

	public void setStagingCaseCreation(ICaseCreation stagingCaseCreation) {
		this.stagingCaseCreation = stagingCaseCreation;
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
