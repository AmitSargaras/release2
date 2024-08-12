package com.integrosys.cms.app.tatduration.bus;

import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * User: Cynthia
 * Date: Aug 27, 2008
 * Time: 3:18:36 PM
 * To change this template use File | Settings | File Templates.
 */

public class OBTatParam implements ITatParam 
{

    private long tatParamId;
    private String applicationType;
    private Set tatParamItemList;

    //standard fields
    private long versionTime;

    public long getTatParamId() 
    {
		return tatParamId;
	}

	public void setTatParamId(long tatParamId) 
	{
		this.tatParamId = tatParamId;
	}

	public String getApplicationType() 
	{
		return applicationType;
	}

	public void setApplicationType(String applicationType) 
	{
		this.applicationType = applicationType;
	}
	
    public long getVersionTime() 
    {
        return versionTime;
    }

    public void setVersionTime(long versionTime) 
    {
        this.versionTime = versionTime;
    }
    
    public Set getTatParamItemList() 
    {
		return tatParamItemList;
	}

	public void setTatParamItemList(Set tatParamItemList) 
	{
		this.tatParamItemList = tatParamItemList;
	}
}
