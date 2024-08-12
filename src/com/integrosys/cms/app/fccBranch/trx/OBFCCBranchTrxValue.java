package com.integrosys.cms.app.fccBranch.trx;

import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.fileInsertMapper.bus.IFileMapperId;
import com.integrosys.cms.app.fccBranch.bus.IFCCBranch;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.OBCMSTrxValue;

/****
 * 
 * @author komal.agicha
 *
 */
public class OBFCCBranchTrxValue extends OBCMSTrxValue implements IFCCBranchTrxValue{

    public  OBFCCBranchTrxValue(){}

    IFCCBranch fccBranch ;
    IFCCBranch stagingFCCBranch ;
    
    IFileMapperId FileMapperID;
    IFileMapperId stagingFileMapperID;
    
    public OBFCCBranchTrxValue(ICMSTrxValue anICMSTrxValue)
    {
        AccessorUtil.copyValue (anICMSTrxValue, this);
    }

	public IFCCBranch getFCCBranch() {
		return fccBranch;
	}

	public void setFCCBranch(IFCCBranch fccBranch) {
		this.fccBranch = fccBranch;
	}

	public IFCCBranch getStagingFCCBranch() {
		return stagingFCCBranch;
	}

	public void setStagingFCCBranch(IFCCBranch stagingFCCBranch) {
		this.stagingFCCBranch = stagingFCCBranch;
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
