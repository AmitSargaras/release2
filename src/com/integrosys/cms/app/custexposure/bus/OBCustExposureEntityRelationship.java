package com.integrosys.cms.app.custexposure.bus;

import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.custrelationship.bus.OBCustRelationship;

/**
 * Business object of entity relationship
 * 
 * @author skchai
 */
public class OBCustExposureEntityRelationship extends OBCustRelationship
		implements ICustExposureEntityRelationship {

	private static final long serialVersionUID = 1L;
	private String groupName;
	private long grpID;
	private String relationName;
	private String relatedEntiyName;
	private long relatedEntiySubProfileId;
	private String relationType;

	public String getRelatedEntiyName() {
		return relatedEntiyName;
	}

	public void setRelatedEntiyName(String relatedEntiyName) {
		this.relatedEntiyName = relatedEntiyName;
	}

	public long getRelatedEntiySubProfileId() {
		return relatedEntiySubProfileId;
	}

	public void setRelatedEntiySubProfileId(long relatedEntiySubProfileId) {
		this.relatedEntiySubProfileId = relatedEntiySubProfileId;
	}

	public long getGrpID() {
		return grpID;
	}

	public void setGrpID(long grpID) {
		this.grpID = grpID;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getRelationName() {
		return relationName;
	}

	public void setRelationName(String relationName) {
		this.relationName = relationName;
	}

	/**
	 * @return the relationType
	 */
	public String getRelationType() {
		return relationType;
	}

	/**
	 * @param relationType the relationType to set
	 */
	public void setRelationType(String relationType) {
		this.relationType = relationType;
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
