/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/checklist/bus/ITemplateItem.java,v 1.9 2003/08/23 06:59:16 hltan Exp $
 */
package com.integrosys.cms.app.chktemplate.bus;

//java
import com.integrosys.cms.app.chktemplate.bus.IItem;

import java.io.Serializable;
import java.util.Date;

/**
 * This interface defines the list of attributes that will be available to a
 * template item
 * 
 * @author $Author: hltan $<br>
 * @version $Revision: 1.9 $
 * @since $Date: 2003/08/23 06:59:16 $ Tag: $Name: $
 */
public interface ITemplateItem extends Serializable {
	/**
	 * Get the template item ID
	 * @return long - the template item ID
	 */
	public long getTemplateItemID();

	/**
	 * Get the mandatory indicator.
     * This has been changed to be used for collateral items only.
     * CC items will be using IsMandatoryForBorrower & IsMandatoryForPledgor
	 * @return boolean - true if it is mandatory and false otherwise
	 */
	public boolean getIsMandatoryInd();
	
	public boolean getIsMandatoryDisplayInd();

    /**
     * Get the borrower mandatory indicator
     * CC items will be using either IsMandatoryForBorrower or IsMandatoryForPledgor
     * @return boolean - true if it is mandatory and false otherwise
     */
    public boolean getIsMandatoryForBorrowerInd();

    /**
     * Get the pledgor/guarantor/chargor mandatory indicator
     * CC items will be using either IsMandatoryForBorrower or IsMandatoryForPledgor
     * @return boolean - true if it is mandatory and false otherwise
     */
    public boolean getIsMandatoryForPledgorInd();


    /**
	 * Get the in vault indicator
	 * @return boolean - true if it is to be vaulted and false otherwise
	 */
	public boolean getIsInVaultInd();

	/**
	 * Get the external custodian indicator
	 * @return boolean - true if it is external custodian and false otherwise
	 */
	public boolean getIsExtCustInd();

	/**
	 * Get the audit indicator
	 * @return boolean - true if it is to be audited and false otherwise
	 */
	public boolean getIsAuditInd();

	/**
	 * Get teh delete indicator
	 * @return boolean - true if it is deleted and false otherwise
	 */
	public boolean getIsDeletedInd();

	/**
	 * Get the item code. This can be the item code from the item or it can be
	 * specific to the template item only
	 * @return String - the item code
	 */
	public String getItemCode();

	/**
	 * Get the item description
	 * @return String - the item description
	 */
	public String getItemDesc();

	/**
	 * Get the parent template ID
	 * @return long - the parent template ID
	 */
	public long getParentItemID();

	/**
	 * Get the template item
	 * @return IItem - the template item
	 */
	public IItem getItem();

	/**
	 * Helper method to check if the item is being inherited or not. If there is
	 * a parent item id then it is inherited
	 * @return boolean - true if it is inherited and false otherwise
	 */
	public boolean isInherited();

	/**
	 * Helper method to check if an item is selected from the global list of
	 * items
	 * @return boolean - true if it is from global list and false otherwise
	 */
	public boolean isFromGlobal();

	/**
	 * Set the template item ID
	 * @param aTemplateID - long
	 */
	public void setTemplateItemID(long aTemplateID);

	/**
	 * Set the mandatory indicator
     * This has been changed to be used for collateral items only.
     * CC items will be using IsMandatoryForBorrower & IsMandatoryForPledgor
	 * @param anIsMandatoryInd - boolean
	 */
	public void setIsMandatoryInd(boolean anIsMandatoryInd);
	
	
	public void setIsMandatoryDisplayInd(boolean anIsMandatoryDisplayInd);

    /**
     * Set the borrower mandatory indicator
     * CC items will be using either IsMandatoryForBorrower or IsMandatoryForPledgor
     * @param flag - boolean
     */
    public void setIsMandatoryForBorrowerInd(boolean flag);
    
    /**
     * Set the pledgor/guarantor/chargor mandatory indicator
     * CC items will be using either IsMandatoryForBorrower or IsMandatoryForPledgor
     * @param flag - boolean
     */
    public void setIsMandatoryForPledgorInd(boolean flag);

    /**
	 * Set the in vault indicator
	 * @param anIsInVaultInd - boolean
	 */
	public void setIsInVaultInd(boolean anIsInVaultInd);

	/**
	 * Set the external custodian indicator
	 * @param anIsExtCustInd - boolean
	 */
	public void setIsExtCustInd(boolean anIsExtCustInd);

	/**
	 * Set the audit indicator
	 * @param anIsAuditInd - boolean
	 */
	public void setIsAuditInd(boolean anIsAuditInd);

	/**
	 * Set the delete indicator
	 * @param anIsDeletedInd - boolean
	 */
	public void setIsDeletedInd(boolean anIsDeletedInd);

	/**
	 * Set the parent item ID
	 * @param aParentItemID - long
	 */
	public void setParentItemID(long aParentItemID);

	/**
	 * Set the template item
	 * @param anIItem - IItem
	 */
	public void setItem(IItem anIItem);
	
	public void setWithTitle(boolean withTitle);
	
	public boolean getWithTitle();
	
	public boolean getWithoutTitle();

	public void setWithoutTitle(boolean withoutTitle);

	public boolean getUnderConstruction();

	public void setUnderConstruction(boolean underConstruction);
	public boolean getPropertyCompleted();

	public void setPropertyCompleted(boolean propertyCompleted);

	public boolean getNewWithFBR();

	public void setNewWithFBR(boolean newWithFBR);

	public boolean getNewWithoutFBR();

	public void setNewWithoutFBR(boolean newWithoutFBR);
	public boolean getUsedWithoutFBR();

	public void setUsedWithoutFBR(boolean usedWithoutFBR);

	public boolean getUsedWithFBR();

	public void setUsedWithFBR(boolean usedWithFBR);
	
	public int getTenureCount();
	
	public String getTenureType();
	
    public void setTenureCount(int tenureCount);
    
    public void setTenureType(String tenureType);
    
    public String getStatementType();

	public void setStatementType(String statementType);
	
	public abstract String getIsRecurrent();

	public abstract void setIsRecurrent(String isRecurrent);
	
	public abstract String getRating();
	
	public abstract void setRating(String rating) ;

	public abstract String getSegment();

	public abstract void setSegment(String segment);

	public abstract String getTotalSancAmt();

	public abstract void setTotalSancAmt(String totalSancAmt);

	public abstract String getClassification() ;

	public abstract void setClassification(String classification);

	public abstract String getGuarantor() ;

	public abstract void setGuarantor(String guarantor) ;

	public abstract Date getCreationDate();
	
	public abstract void setCreationDate(Date creationDate) ;
	
	public abstract Date getLastUpdateDate() ;
	
	public abstract void setLastUpdateDate(Date lastUpdateDate) ;
	
//    public String getDeferCount();
//	
//    
//    public void setDeferCount(String deferCount);
	
	public String getFacilityCategory();

	public void setFacilityCategory(String facilityCategory);

	public String getFacilityType();

	public void setFacilityType(String facilityType);
	public String getSystem();

	public void setSystem(String system) ;
}
