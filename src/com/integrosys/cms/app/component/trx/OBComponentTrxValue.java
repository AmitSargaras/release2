package com.integrosys.cms.app.component.trx;

import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.component.bus.IComponent;
import com.integrosys.cms.app.fileInsertMapper.bus.IFileMapperId;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.OBCMSTrxValue;

public class OBComponentTrxValue extends OBCMSTrxValue implements IComponentTrxValue{
	
	public OBComponentTrxValue(){
		
	}
	
	 public OBComponentTrxValue(ICMSTrxValue anICMSTrxValue)
	    {
	        AccessorUtil.copyValue (anICMSTrxValue, this);
	    }

	
	IComponent component ;
	IComponent stagingComponent ;
    
    IFileMapperId FileMapperID;
    IFileMapperId stagingFileMapperID;
    
	public IComponent getComponent() {
		return component;
	}
	public void setComponent(IComponent component) {
		this.component = component;
	}
	public IComponent getStagingComponent() {
		return stagingComponent;
	}
	public void setStagingComponent(IComponent stagingComponent) {
		this.stagingComponent = stagingComponent;
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
