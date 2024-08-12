/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/customer/bus/OBGroupSearchResult.java,v 1.2 2003/09/04 09:22:35 lakshman Exp $
 */
package com.integrosys.cms.app.customer.bus;

/**
 * This interface represents a customer search result data.
 * 
 * @author $Author: lakshman $
 * @version $Revision: 1.2 $
 * @since $Date: 2003/09/04 09:22:35 $ Tag: $Name: $
 */
public class OBGroupSearchResult implements IGroupSearchResult {
	private String groupType;

	private String groupID;

	private String groupName;

	private String parent;

	private String parentGroupID;

	private String parentGroupName;

	private String legalID;

	private String legalName;

	private String legalParent;

	private String legalParentID;

	private String legalParentName;

	private String ownershipPercent;

	private String relationshipStatus;

	public String getGroupType() {
		return groupType;
	}

	public void setGroupType(String groupType) {
		this.groupType = groupType;
	}

	public String getGroupID() {
		return groupID;
	}

	public void setGroupID(String groupID) {
		this.groupID = groupID;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getParent() {
		return parent;
	}

	public void setParent(String parent) {
		this.parent = parent;
	}

	public String getParentGroupID() {
		return parentGroupID;
	}

	public void setParentGroupID(String parentGroupID) {
		this.parentGroupID = parentGroupID;
	}

	public String getParentGroupName() {
		return parentGroupName;
	}

	public void setParentGroupName(String parentGroupName) {
		this.parentGroupName = parentGroupName;
	}

	public String getLegalID() {
		return legalID;
	}

	public void setLegalID(String legalID) {
		this.legalID = legalID;
	}

	public String getLegalName() {
		return legalName;
	}

	public void setLegalName(String legalName) {
		this.legalName = legalName;
	}

	public String getLegalParent() {
		return legalParent;
	}

	public void setLegalParent(String legalParent) {
		this.legalParent = legalParent;
	}

	public String getLegalParentID() {
		return legalParentID;
	}

	public void setLegalParentID(String legalParentID) {
		this.legalParentID = legalParentID;
	}

	public String getLegalParentName() {
		return legalParentName;
	}

	public void setLegalParentName(String legalParentName) {
		this.legalParentName = legalParentName;
	}

	public String getOwnershipPercent() {
		return ownershipPercent;
	}

	public void setOwnershipPercent(String ownershipPercent) {
		this.ownershipPercent = ownershipPercent;
	}

	public String getRelationshipStatus() {
		return relationshipStatus;
	}

	public void setRelationshipStatus(String relationshipStatus) {
		this.relationshipStatus = relationshipStatus;
	}
}