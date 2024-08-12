package com.integrosys.cms.app.holiday.trx;

import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.fileInsertMapper.bus.IFileMapperId;
import com.integrosys.cms.app.holiday.bus.IHoliday;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.OBCMSTrxValue;

/**
 * @author abhijit.rudrakshawar
 */
public class OBHolidayTrxValue extends OBCMSTrxValue implements IHolidayTrxValue{

    public  OBHolidayTrxValue(){}

    IHoliday holiday ;
    IHoliday stagingHoliday ;
    
    IFileMapperId FileMapperID;
    IFileMapperId stagingFileMapperID;
    
    public OBHolidayTrxValue(ICMSTrxValue anICMSTrxValue)
    {
        AccessorUtil.copyValue (anICMSTrxValue, this);
    }

	public IHoliday getHoliday() {
		return holiday;
	}

	public void setHoliday(IHoliday holiday) {
		this.holiday = holiday;
	}

	public IHoliday getStagingHoliday() {
		return stagingHoliday;
	}

	public void setStagingHoliday(IHoliday stagingHoliday) {
		this.stagingHoliday = stagingHoliday;
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
