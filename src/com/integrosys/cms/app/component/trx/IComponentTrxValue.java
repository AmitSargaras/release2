package com.integrosys.cms.app.component.trx;

import com.integrosys.cms.app.fileInsertMapper.bus.IFileMapperId;
import com.integrosys.cms.app.component.bus.IComponent;
import com.integrosys.cms.app.transaction.ICMSTrxValue;

public interface IComponentTrxValue extends ICMSTrxValue{
	
	public IComponent getComponent();

    public IComponent getStagingComponent();
    
    public IFileMapperId getStagingFileMapperID();
    
    public IFileMapperId getFileMapperID();

    public void setComponent(IComponent value);

    public void setStagingComponent(IComponent value);
    
    public void setStagingFileMapperID(IFileMapperId value);
    
    public void setFileMapperID(IFileMapperId value);

}
