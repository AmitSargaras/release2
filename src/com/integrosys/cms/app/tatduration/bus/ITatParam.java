package com.integrosys.cms.app.tatduration.bus;

import com.integrosys.base.techinfra.ejbsupport.IValueObject;

import java.util.Set;
import java.io.Serializable;

/**
 * Created by IntelliJ IDEA.
 * User: Cynthia
 * Date: Aug 27, 2008
 * Time: 3:43:02 PM
 * To change this template use File | Settings | File Templates.
 */
public interface ITatParam extends IValueObject, Serializable 
{

	public long getTatParamId();
	public void setTatParamId(long tatParamId);

	public String getApplicationType();
	public void setApplicationType(String applicationType);
	
	public Set getTatParamItemList();
	public void setTatParamItemList(Set tatParamItemList);
	
	public long getVersionTime();
    public void setVersionTime(long versionTime);

}
