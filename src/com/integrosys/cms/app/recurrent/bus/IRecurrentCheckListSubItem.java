/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/checklist/bus/IRecurrentCheckListSubItem.java,v 1.6 2006/08/18 10:52:58 jychong Exp $
 */
package com.integrosys.cms.app.recurrent.bus;

//java
import java.io.Serializable;
import java.util.Date;

/**
 * This interface defines the list of attributes that will be available to a
 * recurrent checklist sub item
 * 
 * @author $Author: jychong $<br>
 * @version $Revision: 1.6 $
 * @since $Date: 2006/08/18 10:52:58 $ Tag: $Name: $
 */
public interface IRecurrentCheckListSubItem extends Serializable {
	public long getSubItemID();

	public long getSubItemRef();

	public String getStatus();

	public String getRemarks();
	
	public String getActionParty();

	public int getFrequency();

	public String getFrequencyUnit();

	public int getGracePeriod();

	public String getGracePeriodUnit();

	public Date getDocEndDate();

	public Date getDueDate();

	public Date getReceivedDate();

	public Date getDeferredDate();

	public Date getWaivedDate();

	public long getDeferredCount();

	public long getDaysOverDue();

	public boolean getIsPrintReminderInd();

	public boolean getIsDeletedInd();

	public void setSubItemID(long aSubItemID);

	public void setSubItemRef(long aSubItemRef);

	public void setStatus(String aStatus);

	public void setRemarks(String remarks);
	
	public void setActionParty(String actionParty);

	public void setFrequency(int aFrequency);

	public void setFrequencyUnit(String aFrequencyUnit);

	public void setGracePeriod(int aGracePeriod);

	public void setGracePeriodUnit(String aGracePeriodUnit);

	public void setDocEndDate(Date aDocEndDate);

	public void setDueDate(Date aDueDate);

	public void setReceivedDate(Date aReceivedDate);

	public void setDeferredDate(Date aDeferredDate);

	public void setWaivedDate(Date waivedDate);

	public void setDeferredCount(long deferredCount);

	public void setIsPrintReminderInd(boolean anIsPrintReminderInd);

	public void setIsDeletedInd(boolean anIsDeletedInd);
}
