package com.integrosys.cms.app.custexposure.bus.group;

import com.integrosys.cms.app.custexposure.bus.ICustExposure;


/**
 * Group Exposure contains all group attributes and members' exposure 
 * @author Siew Kheat
 */
public interface IGroupExposure extends java.io.Serializable {

	public void setGroupExpCustGrp(IGroupExpCustGrp groupExpCustGrp);
	public IGroupExpCustGrp getGroupExpCustGrp();
	
	// public void addGroupMemberExposure(ICustExposure custExposure);
	public ICustExposure[] getGroupMemberExposure();
	public void setGroupMemberExposure(ICustExposure[] custExposures);
	
	public IGroupExpBankEntity[] getGroupExpBankEntity();
	public void setGroupExpBankEntity(IGroupExpBankEntity[] groupExpBankEntities);

}
