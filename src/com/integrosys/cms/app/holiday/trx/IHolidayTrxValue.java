package com.integrosys.cms.app.holiday.trx;

import com.integrosys.cms.app.fileInsertMapper.bus.IFileMapperId;
import com.integrosys.cms.app.holiday.bus.IHoliday;
import com.integrosys.cms.app.transaction.ICMSTrxValue;

/**
 * @author abhijit.rudrakshawar
 */
 
public interface IHolidayTrxValue  extends ICMSTrxValue {

    public IHoliday getHoliday();

    public IHoliday getStagingHoliday();
    
    public IFileMapperId getStagingFileMapperID();
    
    public IFileMapperId getFileMapperID();

    public void setHoliday(IHoliday value);

    public void setStagingHoliday(IHoliday value);
    
    public void setStagingFileMapperID(IFileMapperId value);
    
    public void setFileMapperID(IFileMapperId value);
}
