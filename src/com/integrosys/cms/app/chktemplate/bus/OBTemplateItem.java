/*
 * Copyright Integro Technologies Pte Ltd
 */
package com.integrosys.cms.app.chktemplate.bus;

import java.util.Date;

/**
 * This is the implementation class for the ITemplateItem
 * 
 * @author $Author: hltan $<br>
 * @version $Revision: 1.10 $
 * @since $Date: 2003/08/23 06:59:16 $ Tag: $Name: $
 */
public class OBTemplateItem implements ITemplateItem, Comparable {
	private static final long serialVersionUID = -8208830412843156532L;

	private long templateItemID = com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;

	private boolean isMandatoryInd = false;
	
	private boolean isMandatoryDisplayInd = false;

	private boolean isMandatoryForBorrowerInd = false;

	private boolean isMandatoryForPledgorInd = false;

	private boolean isInVaultInd = false;

	private boolean isExtCustInd = false;

	private boolean isAuditInd = false;

	private boolean isDeletedInd = false;
	
	private boolean withTitle ;
	
	private boolean withoutTitle ;
	
	private boolean underConstruction;
	
	private boolean propertyCompleted;
	
	private boolean newWithFBR;
	
	private boolean newWithoutFBR;
	
	private boolean usedWithoutFBR;
	
	private boolean usedWithFBR;
	
	private int tenureCount=0;
	
	private String tenureType="";
	
	private String statementType="";
	
	private String isRecurrent="";
	
	private String rating="";
	
	private String segment="";
	
	private String totalSancAmt="";
	
	private String classification="";
	
	private String guarantor="";
	//private String deferCount="";

	private IItem item = null;

	/**
     * Added by @author anil.pandey on @createdOn Sep 29, 2014 4:22:43 PM for handling audit.
     */
    private Date creationDate;
	private Date lastUpdateDate;
	private String facilityCategory;
	
	private String facilityType;
	
	private String system;
	
	public String getIsRecurrent() {
		return isRecurrent;
	}

	public void setIsRecurrent(String isRecurrent) {
		this.isRecurrent = isRecurrent;
	}

	public String getRating() {
		return rating;
	}

	public void setRating(String rating) {
		this.rating = rating;
	}

	public String getSegment() {
		return segment;
	}

	public void setSegment(String segment) {
		this.segment = segment;
	}

	public String getTotalSancAmt() {
		return totalSancAmt;
	}

	public void setTotalSancAmt(String totalSancAmt) {
		this.totalSancAmt = totalSancAmt;
	}

	public String getClassification() {
		return classification;
	}

	public void setClassification(String classification) {
		this.classification = classification;
	}

	public String getGuarantor() {
		return guarantor;
	}

	public void setGuarantor(String guarantor) {
		this.guarantor = guarantor;
	}

	public String getStatementType() {
		return statementType;
	}

	public void setStatementType(String statementType) {
		this.statementType = statementType;
	}

	private long parentItemID = com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;

	/**
	 * Get the template Item ID
	 * @return long - the template item ID
	 */
	public long getTemplateItemID() {
		return this.templateItemID;
	}

	/**
	 * Get the mandatory indicator
	 * @return boolean - true if it is mandatory and false otherwise
	 */
	public boolean getIsMandatoryInd() {
		return this.isMandatoryInd;
	}

	public boolean getIsMandatoryDisplayInd() {
		return isMandatoryDisplayInd;
	}

	public void setIsMandatoryDisplayInd(boolean isMandatoryDisplayInd) {
		this.isMandatoryDisplayInd = isMandatoryDisplayInd;
	}

	/**
	 * Get the borrower mandatory indicator CC items will be using either
	 * IsMandatoryForBorrower or IsMandatoryForPledgor
	 * @return boolean - true if it is mandatory and false otherwise
	 */
	public boolean getIsMandatoryForBorrowerInd() {
		return isMandatoryForBorrowerInd;
	}

	/**
	 * Get the pledgor/guarantor/chargor mandatory indicator CC items will be
	 * using either IsMandatoryForBorrower or IsMandatoryForPledgor
	 * @return boolean - true if it is mandatory and false otherwise
	 */
	public boolean getIsMandatoryForPledgorInd() {
		return isMandatoryForPledgorInd;
	}

	/**
	 * Get the in vault indicator
	 * @return boolean - true if it is to be vaulted and false otherwise
	 */
	public boolean getIsInVaultInd() {
		return this.isInVaultInd;
	}

	/**
	 * Get the external custodian indicator
	 * @return boolean - true if it is external custodian and false otherwise
	 */
	public boolean getIsExtCustInd() {
		return this.isExtCustInd;
	}

	/**
	 * Get the audit indicator
	 * @return boolean - true if it is to be audited and false otherwise
	 */
	public boolean getIsAuditInd() {
		return this.isAuditInd;
	}

	/**
	 * Get the delete indicator
	 * @return boolean - true if it is deleted and false otherwise
	 */
	public boolean getIsDeletedInd() {
		return this.isDeletedInd;
	}

	/**
	 * Get the item code. This can be the item code from the item or it can be
	 * specific to the template item only
	 * @return String - the item code
	 */
	public String getItemCode() {
		if (this.getItem() != null) {
			return this.getItem().getItemCode();
		}
		return null;
	}

	/**
	 * Get the item description
	 * @return String - the item description
	 */
	public String getItemDesc() {
		if (this.getItem() != null) {
			return this.getItem().getItemDesc();
		}
		return null;
	}

	/**
	 * Get the template item
	 * @return IItem - the template item
	 */
	public IItem getItem() {
		return this.item;
	}

	/**
	 * Get the parent item ID that it inherited from
	 * @return long - the parent item ID
	 */
	public long getParentItemID() {
		return this.parentItemID;
	}

	/**
	 * Helper method to check if the item is being inherited or not. If there is
	 * a parent item id then it is inherited
	 * @return boolean - true if it is inherited and false otherwise
	 */
	public boolean isInherited() {
		if (getParentItemID() == com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE) {
			return false;
		}
		return true;
	}

	/**
	 * Helper method to check if an item is selected from the global list of
	 * items
	 * @return boolean - true if it is from global list and false otherwise
	 */
	public boolean isFromGlobal() {
		if (getItem() != null) {
			if (getItem().getItemID() != com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Set the template item ID
	 * @param aTemplateItemID - long
	 */
	public void setTemplateItemID(long aTemplateItemID) {
		this.templateItemID = aTemplateItemID;
	}

	/**
	 * Set the mandatory indicator
	 * @param anIsMandatoryInd - boolean
	 */
	public void setIsMandatoryInd(boolean anIsMandatoryInd) {
		this.isMandatoryInd = anIsMandatoryInd;
	}

	/**
	 * Set the borrower mandatory indicator CC items will be using either
	 * IsMandatoryForBorrower or IsMandatoryForPledgor
	 * @param flag - boolean
	 */
	public void setIsMandatoryForBorrowerInd(boolean flag) {
		this.isMandatoryForBorrowerInd = flag;
	}

	/**
	 * Set the pledgor/guarantor/chargor mandatory indicator CC items will be
	 * using either IsMandatoryForBorrower or IsMandatoryForPledgor
	 * @param flag - boolean
	 */
	public void setIsMandatoryForPledgorInd(boolean flag) {
		this.isMandatoryForPledgorInd = flag;
	}

	/**
	 * Set the in vault indicator
	 * @param anIsInVaultInd - boolean
	 */
	public void setIsInVaultInd(boolean anIsInVaultInd) {
		this.isInVaultInd = anIsInVaultInd;
	}

	/**
	 * Set the external custodian indicator
	 * @param anIsExtCustInd - boolean
	 */
	public void setIsExtCustInd(boolean anIsExtCustInd) {
		this.isExtCustInd = anIsExtCustInd;
	}

	/**
	 * Set the audit indicator
	 * @param anIsAuditInd - boolean
	 */
	public void setIsAuditInd(boolean anIsAuditInd) {
		this.isAuditInd = anIsAuditInd;
	}

	/**
	 * Set the delete indication
	 * @param anIsDeletedInd - boolean
	 */
	public void setIsDeletedInd(boolean anIsDeletedInd) {
		this.isDeletedInd = anIsDeletedInd;
	}

	/**
	 * Set the template item. The template item must be one of the items created
	 * through the global template.
	 * @param anIItem - IItem
	 */
	public void setItem(IItem anIItem) {
		this.item = anIItem;
	}

	/**
	 * Set the parent Item ID.
	 * @param aParentItemID - long
	 */
	public void setParentItemID(long aParentItemID) {
		this.parentItemID = aParentItemID;
	}
	
	public boolean getWithTitle() {
		// TODO Auto-generated method stub
		return withTitle;
	}

	public void setWithTitle(boolean l) {
		this.withTitle = l;
		
	}

	public boolean getWithoutTitle() {
		return withoutTitle;
	}

	public void setWithoutTitle(boolean withoutTitle) {
		this.withoutTitle = withoutTitle;
	}

	public boolean getUnderConstruction() {
		return underConstruction;
	}

	public void setUnderConstruction(boolean underConstruction) {
		this.underConstruction = underConstruction;
	}

	public boolean getPropertyCompleted() {
		return propertyCompleted;
	}

	public void setPropertyCompleted(boolean propertyCompleted) {
		this.propertyCompleted = propertyCompleted;
	}

	public boolean getNewWithFBR() {
		return newWithFBR;
	}

	public void setNewWithFBR(boolean newWithFBR) {
		this.newWithFBR = newWithFBR;
	}

	public boolean getNewWithoutFBR() {
		return newWithoutFBR;
	}

	public void setNewWithoutFBR(boolean newWithoutFBR) {
		this.newWithoutFBR = newWithoutFBR;
	}

	public boolean getUsedWithoutFBR() {
		return usedWithoutFBR;
	}

	public void setUsedWithoutFBR(boolean usedWithoutFBR) {
		this.usedWithoutFBR = usedWithoutFBR;
	}

	public boolean getUsedWithFBR() {
		return usedWithFBR;
	}

	public void setUsedWithFBR(boolean usedWithFBR) {
		this.usedWithFBR = usedWithFBR;
	}

	public String toString() {
		StringBuffer buf = new StringBuffer("OBTemplateItem [");
		buf.append(", itemCode=");
		buf.append(getItemCode());
		buf.append(", itemDesc=");
		buf.append(getItemDesc());
		buf.append(", parentItemID=");
		buf.append(parentItemID);
		buf.append(", isAuditInd=");
		buf.append(isAuditInd);
		buf.append(", isDeletedInd=");
		buf.append(isDeletedInd);
		buf.append(", isExtCustInd=");
		buf.append(isExtCustInd);
		buf.append(", isInVaultInd=");
		buf.append(isInVaultInd);
		buf.append(", isMandatoryForBorrowerInd=");
		buf.append(isMandatoryForBorrowerInd);
		buf.append(", isMandatoryForPledgorInd=");
		buf.append(isMandatoryForPledgorInd);
		buf.append(", isMandatoryInd=");
		buf.append(isMandatoryInd);
		buf.append(", isMandatoryDisplayInd=");
		buf.append(isMandatoryDisplayInd);
		buf.append(", templateItemID=");
		buf.append(templateItemID);
		buf.append(", isFromGlobal=");
		buf.append(isFromGlobal());
		buf.append(", isInherited=");
		buf.append(isInherited());
		buf.append("]");
		return buf.toString();
	}

	public int compareTo(Object other) {
		String otherItemCode = (other == null) ? null : ((ITemplateItem) other).getItemCode();

		if (this.getItemCode() == null) {
			return (otherItemCode == null) ? 0 : -1;
		}

		return (otherItemCode == null) ? 1 : this.getItemCode().compareTo(otherItemCode);
	}

	public int getTenureCount() {
		return tenureCount;
	}

	public void setTenureCount(int tenureCount) {
		this.tenureCount = tenureCount;
	}

	public String getTenureType() {
		return tenureType;
	}

	public void setTenureType(String tenureType) {
		this.tenureType = tenureType;
	}
	
	
	/**
	 * @return the creationDate
	 */
	public Date getCreationDate() {
		return creationDate;
	}

	/**
	 * @param creationDate the creationDate to set
	 */
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	/**
	 * @return the lastUpdateDate
	 */
	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}

	/**
	 * @param lastUpdateDate the lastUpdateDate to set
	 */
	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	public String getFacilityCategory() {
		return facilityCategory;
	}

	public void setFacilityCategory(String facilityCategory) {
		this.facilityCategory = facilityCategory;
	}

	public String getFacilityType() {
		return facilityType;
	}

	public void setFacilityType(String facilityType) {
		this.facilityType = facilityType;
	}

	public String getSystem() {
		return system;
	}

	public void setSystem(String system) {
		this.system = system;
	}

//	public String getDeferCount() {
//		return deferCount;
//	}
//
//	public void setDeferCount(String deferCount) {
//		this.deferCount = deferCount;
//	}
	
	

}
