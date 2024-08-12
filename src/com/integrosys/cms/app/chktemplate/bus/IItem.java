/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/checklist/bus/IItem.java,v 1.3 2003/10/28 08:56:24 hltan Exp $
 */
package com.integrosys.cms.app.chktemplate.bus;

//java
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * This interface defines the list of attributes that will be available to a
 * checklist item
 * 
 * @author $Author: hltan $<br>
 * @version $Revision: 1.3 $
 * @since $Date: 2003/10/28 08:56:24 $ Tag: $Name: $
 */
public interface IItem extends Serializable {
	/**
	 * Get the item ID
	 * @return long - the item ID
	 */
	public long getItemID();

	/**
	 * Get the item code
	 * @return String - the item code
	 */
	public String getItemCode();

	/**
	 * Get the item description
	 * @return String - the item description
	 */
	public String getItemDesc();

	/**
	 * Get the item type
	 * @return String - the item type
	 */
	public String getItemType();

	/**
	 * Get the expiry date
	 * @return Date - the expiry date
	 */
	public Date getExpiryDate();

	/**
	 * Get the monitor type
	 * @return String - the monitor type
	 */
	public String getMonitorType();

    /**
     * Get the user-input document version.
     * @return String - document version
     */
    public String getDocumentVersion();

    /**
     * Get the flag to determine if item is applicable for borrower
     * @return boolean - flag for borrower
     */
    public boolean getIsForBorrower();

    /**
     * Get the flag to determine if item is applicable for pledgor
     * @return boolean - flag for pledgor
     */
    public boolean getIsForPledgor();

    /**
     * Get the flag to determine if item is pre-approve (by LOS)
     * @return boolean - pre-approval indicator
     */
    public boolean getIsPreApprove();

    /**
     * Get the applicable loan application type.
     * E.g. of loan application type = Hire Purchase, Credit Card, Corporate, All
     * @return String - loan application type
     */
   // public String getLoanApplicationType();

    /**
     * Get the list of dynamic properties
     * @return list of dynamic properties
     */
    public IDynamicProperty[] getPropertyList();


    /**
	 * Set the item ID
	 * @param anItemID - long
	 */
	public void setItemID(long anItemID);

	/**
	 * Set the item code
	 * @param anItemCode - String
	 */
	public void setItemCode(String anItemCode);

	/**
	 * Set the item description
	 * @param anItemDesc - String
	 */
	public void setItemDesc(String anItemDesc);

	/**
	 * Set the item type
	 * @param anItemType - String
	 */
	public void setItemType(String anItemType);

	/**
	 * Set the expiry date
	 * @param anExpiryDate - Date
	 */
	public void setExpiryDate(Date anExpiryDate);

	/**
	 * Set the monitor type
	 * @param aMonitorType of String type
	 */
	public void setMonitorType(String aMonitorType);


    /**
     * Set the user-input document version
     * @param docVersion - Document Version
     */
    public void setDocumentVersion(String docVersion);

    /**
     * Set the flag to determine if item is applicable to borrower.
     * @param flag - borrower indicator
     */
    public void setIsForBorrower(boolean flag);

    /**
     * Set the flag to determine if item is applicable to pledgor.
     * @param flag - pledgor indicator
     */
    public void setIsForPledgor(boolean flag);

    /**
     * Set the flag to determine if item is pre-approve (in LOS).
     * @param flag - pre-approval indicator
     */
    public void setIsPreApprove(boolean flag);

    /**
     * Set the loan application type that is applicable
     * E.g. of loan application type = Hire Purchase, Credit Card, Corporate, All
     * @param type - loan application type
     */
  //  public void setLoanApplicationType(String type);

    /**
     * Set the list of dynamic properties
     * @param propertyList - list of dyanmic properties
     */
    public void setPropertyList(IDynamicProperty[] propertyList);
    
	public Collection getCMRDocAppItemList();

	public void setCMRDocAppItemList(Collection docAppItemList);
    
	


	public String getDeprecated();
	
	public void setDeprecated(String deprecated);
	
	public int getTenureCount();
	
	public String getTenureType();
	
    public void setTenureCount(int tenureCount);
    
    public void setTenureType(String tenureType);
    
 //   public String getDeferCount();
	
    
  //  public void setDeferCount(String deferCount);
    
	public String getStatus();
	
    public void setStatus(String aStatus);
    
    public String getSkipImgTag();
	
    public void setSkipImgTag(String skipImgTag);
    
    public String getStatementType();

	public void setStatementType(String statementType);
	
	
	public String getIsRecurrent();

	public void setIsRecurrent(String isRecurrent);
	
	public String getRating();
	
	public void setRating(String rating) ;

	public String getSegment();

	public void setSegment(String segment);

	public String getTotalSancAmt();

	public void setTotalSancAmt(String totalSancAmt);

	public String getClassification() ;

	public void setClassification(String classification);

	public String getGuarantor() ;

	public void setGuarantor(String guarantor) ;

	public String getOldItemCode();
	
	public void setOldItemCode(String oldItemCode);

	public String getIsApplicableForCersaiInd();

	public void setIsApplicableForCersaiInd(String isApplicableForCersaiInd);

}
