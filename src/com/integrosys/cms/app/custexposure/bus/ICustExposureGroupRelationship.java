package com.integrosys.cms.app.custexposure.bus;

import com.integrosys.cms.app.custgrpi.bus.IGroupMember;

/**
 * Group relationship interface 
 * @author skchai
 */
public interface ICustExposureGroupRelationship extends java.io.Serializable {

    public String getGroupName();
    public void setGroupName(String groupName) ;
    
    public IGroupMember getGroupMember();
    public void setGroupMember(IGroupMember groupMember);
}
