/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/checklist/bus/IAnnexure.java,v 1.9 2006/08/16 09:21:57 jychong Exp $
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
public interface IAnnexure extends Serializable {

	/**
	 * Get the convenant ID
	 * @return long - the convenant ID
	 */
	public long getAnnexureID();

	/**
	 * Get the Annexure reference
	 * @return long - the convenant reference
	 */
	public long getAnnexureRef();

	/**
	 * Get the convenant status if it is verified
	 * @return String - the convenant status
	 */
	public String getAnnexureStatus();

	public IAnnexureSubItem[] getAnnexureSubItemList();

	public String getDescription();

	public Date getInitialDocEndDate();

	public Date getInitialDueDate();

	public String getSourceId();

	public IAnnexureSubItem[] getSubItemsByCondition(String cond);

	/**
	 * Get the convenant ID
	 * @param aAnnexureID of long type
	 */
	public void setAnnexureID(long aAnnexureID);

	/**
	 * Set the convenant reference
	 * @param aAnnexureRef of long type
	 */
	public void setAnnexureRef(long aAnnexureRef);

	/**
	 * Set the convenant status
	 * @param aAnnexureStatus of String type
	 */
	public void setAnnexureStatus(String aAnnexureStatus);

	public void setAnnexureSubItemList(IAnnexureSubItem[] aSubItemList);

	/**
	 * Set the description
	 * @param aDescription of String type
	 */
	public void setDescription(String aDescription);

	public void setInitialDocEndDate(Date anInitialDocEndDate);

	public void setInitialDueDate(Date aInitialDueDate);

	public void setSourceId(String sourceId);

	public void updateSubItem(int anItemIndex, IAnnexureSubItem anISubtem);

}
