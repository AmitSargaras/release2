package com.integrosys.cms.app.custexposure.bus;

import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.custgrpi.bus.IGroupMember;

/**
 * Customer Exposure - Group Relationship This class is only used for customer
 * relationship and only meant to contain minimal data
 * 
 * @author skchai
 */
public class OBCustExposureGroupRelationship implements
		ICustExposureGroupRelationship {

	private static final long serialVersionUID = 1L;
	private String groupName;
	private IGroupMember groupMember;

	/**
	 * @return the groupMember
	 */
	public IGroupMember getGroupMember() {
		return groupMember;
	}

	/**
	 * @param groupMember
	 *            the groupMember to set
	 */
	public void setGroupMember(IGroupMember groupMember) {
		this.groupMember = groupMember;
	}

	/**
	 * Get group name
	 * 
	 * @return group name
	 */
	public String getGroupName() {
		return groupName;
	}

	/**
	 * Set group name
	 * 
	 * @param group
	 *            Name
	 */
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

    /**
     * Return a String representation of the object
     *
     * @return String
     */
    public String toString() {
        return AccessorUtil.printMethodValue(this);
    }
}
