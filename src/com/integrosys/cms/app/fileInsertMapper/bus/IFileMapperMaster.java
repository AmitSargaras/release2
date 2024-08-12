package com.integrosys.cms.app.fileInsertMapper.bus;

import java.io.Serializable;
import java.util.Date;

import org.apache.struts.upload.FormFile;

import com.integrosys.base.techinfra.ejbsupport.IValueObject;
import com.integrosys.cms.app.systemBank.bus.ISystemBank;

/**
 * @author  Abhijit R. 
 */
public interface IFileMapperMaster extends Serializable, IValueObject {

    public long getId();
    public void setId(long id);	

    public long getFileId();
    public void setFileId(long id);
    
    public String getTransId();
    public void setTransId(String id);
    
    public long getSysId();
    public void setSysId(long id);
}
