/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/checklist/bus/IConvenant.java,v 1.9 2006/08/16 09:21:57 jychong Exp $
 */
package com.integrosys.cms.app.recurrent.bus;

//java
import java.io.Serializable;
import java.util.Date;

/**
 * This interface defines the list of attributes that will be available to a
 * convenant
 * 
 * @author $Author: jychong $<br>
 * @version $Revision: 1.9 $
 * @since $Date: 2006/08/16 09:21:57 $ Tag: $Name: $
 */
public interface IConvenant extends Serializable {
	public boolean getChaseReminderInd();

	/**
	 * Get the convenant ID
	 * @return long - the convenant ID
	 */
	public long getConvenantID();

	/**
	 * Get the convenant reference
	 * @return long - the convenant reference
	 */
	public long getConvenantRef();

	/**
	 * Get the convenant status if it is verified
	 * @return String - the convenant status
	 */
	public String getConvenantStatus();

	public IConvenantSubItem[] getConvenantSubItemList();

	/**
	 * Get the date checked
	 * @return Date - the date checked
	 */
	public Date getDateChecked();

	/**
	 * Get the description
	 * @return String - the description
	 */
	public String getDescription();

	public int getEndDateChangedCount();

	public boolean getFee();

	// cr 26
	public int getFrequency();

	public String getFrequencyUnit();

	public int getGracePeriod();

	public String getGracePeriodUnit();

	public Date getInitialDocEndDate();

	public Date getInitialDueDate();

	/**
	 * Get the delete indicator
	 * @return boolean - true if it is deleted and false otherwise
	 */
	public boolean getIsDeletedInd();

	public boolean getIsOneOffInd();

	public boolean getIsParameterizedDesc();

	/**
	 * Get the verified indicator
	 * @return boolean - true if it is verified and false otherwise
	 */
	public boolean getIsVerifiedInd();

	public Date getLastDocEntryDate();

	/**
	 * Get the remarks
	 * @return String - the remarks
	 */
	public String getRemarks();

	public boolean getRiskTrigger();

	public String getSourceId();

	public IConvenantSubItem[] getSubItemsByCondition(String cond);

	/**
	 * Helper method to check if processing is allowed or not
	 * @return boolean - true if processing is allowed and false otherwise
	 */
	public boolean isProcessingAllowed();

	public void setChaseReminderInd(boolean aChaseReminderInd);

	/**
	 * Get the convenant ID
	 * @param aConvenantID of long type
	 */
	public void setConvenantID(long aConvenantID);

	/**
	 * Set the convenant reference
	 * @param aConvenantRef of long type
	 */
	public void setConvenantRef(long aConvenantRef);

	/**
	 * Set the convenant status
	 * @param aConvenantStatus of String type
	 */
	public void setConvenantStatus(String aConvenantStatus);

	public void setConvenantSubItemList(IConvenantSubItem[] aSubItemList);

	/**
	 * Set the date checked
	 * @param aDateChecked of Date type
	 */
	public void setDateChecked(Date aDateChecked);

	/**
	 * Set the description
	 * @param aDescription of String type
	 */
	public void setDescription(String aDescription);

	public void setEndDateChangedCount(int count);

	public void setFee(boolean fee);

	public void setFrequency(int aFrequency);

	public void setFrequencyUnit(String aFrequencyUnit);

	public void setGracePeriod(int aGracePeriod);

	public void setGracePeriodUnit(String aGracePeriodUnit);

	public void setInitialDocEndDate(Date anInitialDocEndDate);

	public void setInitialDueDate(Date aInitialDueDate);

	/**
	 * Set the delete indicator
	 * @param anIsDeletedInd of boolean type
	 */
	public void setIsDeletedInd(boolean anIsDeletedInd);

	public void setIsOneOffInd(boolean isOneOffInd);

	public void setIsParameterizedDesc(boolean isParameterizedDesc);

	/**
	 * Set the verified indicator
	 * @param anIsVerifiedInd of boolean type
	 */
	public void setIsVerifiedInd(boolean anIsVerifiedInd);

	public void setLastDocEntryDate(Date lastDocEntryDate);

	/**
	 * Set the remarks
	 * @param aRemarks of String type
	 */
	public void setRemarks(String aRemarks);

	public void setRiskTrigger(boolean riskTrigger);

	public void setSourceId(String sourceId);

	public void updateSubItem(int anItemIndex, IConvenantSubItem anISubtem);

}
