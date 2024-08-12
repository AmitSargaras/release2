package com.integrosys.cms.app.otherbank.trx;

import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.fileInsertMapper.bus.IFileMapperId;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.OBCMSTrxValue;
import com.integrosys.cms.ui.otherbankbranch.IOtherBank;

/**
 * @author dattatray.thorat
 */
public class OBOtherBankTrxValue extends OBCMSTrxValue implements IOtherBankTrxValue{

    public  OBOtherBankTrxValue(){}

    IOtherBank otherBank ;
    IOtherBank stagingOtherBank ;
    
    IFileMapperId FileMapperID;
    IFileMapperId stagingFileMapperID;

    public OBOtherBankTrxValue(ICMSTrxValue anICMSTrxValue)
    {
        AccessorUtil.copyValue (anICMSTrxValue, this);
    }

	/**
	 * @return the otherBank
	 */
	public IOtherBank getOtherBank() {
		return otherBank;
	}

	/**
	 * @param otherBank the otherBank to set
	 */
	public void setOtherBank(IOtherBank otherBank) {
		this.otherBank = otherBank;
	}

	/**
	 * @return the stagingOtherBank
	 */
	public IOtherBank getStagingOtherBank() {
		return stagingOtherBank;
	}

	/**
	 * @param stagingOtherBank the stagingOtherBank to set
	 */
	public void setStagingOtherBank(IOtherBank stagingOtherBank) {
		this.stagingOtherBank = stagingOtherBank;
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
