package com.integrosys.cms.app.otherbranch.trx;

import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.fileInsertMapper.bus.IFileMapperId;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.OBCMSTrxValue;
import com.integrosys.cms.ui.otherbankbranch.IOtherBranch;

/**
 * @author dattatray.thorat
 */
public class OBOtherBankBranchTrxValue extends OBCMSTrxValue implements IOtherBankBranchTrxValue{

   
	public  OBOtherBankBranchTrxValue(){}

    IOtherBranch otherBranch ;
    IOtherBranch stagingOtherBranch ;
    
    
    IFileMapperId FileMapperID;
    IFileMapperId stagingFileMapperID;

    public OBOtherBankBranchTrxValue(ICMSTrxValue anICMSTrxValue)
    {
        AccessorUtil.copyValue (anICMSTrxValue, this);
    }

	/**
	 * @return the otherBranch
	 */
	public IOtherBranch getOtherBranch() {
		return otherBranch;
	}

	/**
	 * @param otherBranch the otherBranch to set
	 */
	public void setOtherBranch(IOtherBranch otherBranch) {
		this.otherBranch = otherBranch;
	}

	/**
	 * @return the stagingOtherBranch
	 */
	public IOtherBranch getStagingOtherBranch() {
		return stagingOtherBranch;
	}

	/**
	 * @param stagingOtherBranch the stagingOtherBranch to set
	 */
	public void setStagingOtherBranch(IOtherBranch stagingOtherBranch) {
		this.stagingOtherBranch = stagingOtherBranch;
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
