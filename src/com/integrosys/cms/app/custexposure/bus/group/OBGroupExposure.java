package com.integrosys.cms.app.custexposure.bus.group;

import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.custexposure.bus.ICustExposure;

/**
 * OB that wrap around group exposure
 * @author skchai
 *
 */
public class OBGroupExposure implements IGroupExposure {

	private static final long serialVersionUID = 1L;
	private ICustExposure[] groupMemberExposure;
	private IGroupExpBankEntity[] groupExpBankEntity;
	private IGroupExpCustGrp groupExpCustGrp;

	/**
	 * @return the groupExpCustGrp
	 */
	public IGroupExpCustGrp getGroupExpCustGrp() {
		return groupExpCustGrp;
	}

	/**
	 * @param groupExpCustGrp the groupExpCustGrp to set
	 */
	public void setGroupExpCustGrp(IGroupExpCustGrp groupExpCustGrp) {
		this.groupExpCustGrp = groupExpCustGrp;
	}

	public void addGroupMemberExposure(ICustExposure custExposure) {
		
	}
	
    /**
	 * @return the groupMember
	 */
	public ICustExposure[] getGroupMemberExposure() {
		return groupMemberExposure;
	}

	/**
	 * @param groupMember the groupMember to set
	 */
	public void setGroupMemberExposure(ICustExposure[] groupMemberExposure) {
		this.groupMemberExposure = groupMemberExposure;
	}
	

	/**
     * Return a String representation of this object.
     *
     * @return String
     */
    public String toString() {
        return AccessorUtil.printMethodValue (this);
    }

	/**
	 * @return the groupExpBankEntity
	 */
	public IGroupExpBankEntity[] getGroupExpBankEntity() {
		return groupExpBankEntity;
	}

	/**
	 * @param groupExpBankEntity the groupExpBankEntity to set
	 */
	public void setGroupExpBankEntity(IGroupExpBankEntity[] groupExpBankEntity) {
		this.groupExpBankEntity = groupExpBankEntity;
	}

}
