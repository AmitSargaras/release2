/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/customer/bus/IGroupSearchResult.java,v 1.2 2003/09/04 09:22:35 lakshman Exp $
 */
package com.integrosys.cms.app.customer.bus;

/**
 * This interface represents a customer search result data.
 * 
 * @author $Author: lakshman $
 * @version $Revision: 1.2 $
 * @since $Date: 2003/09/04 09:22:35 $ Tag: $Name: $
 */
public interface IGroupSearchResult extends java.io.Serializable {

	public String getGroupType();

	public void setGroupType(String groupType);

	public String getGroupID();

	public void setGroupID(String groupID);

	public String getGroupName();

	public void setGroupName(String groupName);

	public String getParent();

	public void setParent(String parent);

	public String getParentGroupID();

	public void setParentGroupID(String parentGroupID);

	public String getParentGroupName();

	public void setParentGroupName(String parentGroupName);

	public String getLegalID();

	public void setLegalID(String legalID);

	public String getLegalName();

	public void setLegalName(String legalName);

	public String getLegalParent();

	public void setLegalParent(String legalParent);

	public String getLegalParentID();

	public void setLegalParentID(String legalParentID);

	public String getLegalParentName();

	public void setLegalParentName(String legalParentName);

	public String getOwnershipPercent();

	public void setOwnershipPercent(String ownershipPercent);

	public String getRelationshipStatus();

	public void setRelationshipStatus(String relationshipStatus);

}