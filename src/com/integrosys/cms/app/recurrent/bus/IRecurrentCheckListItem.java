/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/checklist/bus/IRecurrentCheckListItem.java,v 1.15 2006/08/16 09:21:57 jychong Exp $
 */
package com.integrosys.cms.app.recurrent.bus;

//java
import com.integrosys.cms.app.chktemplate.bus.IItem;

import java.io.Serializable;
import java.util.Date;

/**
 * This interface defines the list of attributes that will be available to a
 * recurrent checklist item
 * 
 * @author $Author: jychong $<br>
 * @version $Revision: 1.15 $
 * @since $Date: 2006/08/16 09:21:57 $ Tag: $Name: $
 */
public interface IRecurrentCheckListItem extends Serializable {
	public long getCheckListItemID();

	public long getCheckListItemRef();

	public IItem getItem();

	public String getItemDesc();

	public int getFrequency();

	public String getFrequencyUnit();

	public int getGracePeriod();

	public String getGracePeriodUnit();

	public boolean getChaseReminderInd();

	public String getRemarks();

	public boolean getIsDeletedInd();

	public Date getInitialDocEndDate();

	public Date getInitialDueDate();

	public boolean getIsOneOffInd();

	public Date getLastDocEntryDate();

	public int getEndDateChangedCount();

	public IRecurrentCheckListSubItem[] getRecurrentCheckListSubItemList();

	public IRecurrentCheckListSubItem[] getSubItemsByCondition(String cond);

	// public IRecurrentCheckListSubItem getNextPendingSubItemAfter(int
	// offSetIdx);

	public void setCheckListItemID(long aCheckListItemID);

	public void setCheckListItemRef(long aCheckListItemRef);

	public void setItem(IItem anIItem);

	public void setFrequency(int aFrequency);

	public void setFrequencyUnit(String aFrequencyUnit);

	public void setGracePeriod(int aGracePeriod);

	public void setGracePeriodUnit(String aGracePeriodUnit);

	public void setChaseReminderInd(boolean aChaseReminderInd);

	public void setRemarks(String aRemarks);

	public void setIsDeletedInd(boolean anIsDeletedInd);

	public void setInitialDocEndDate(Date anInitialDocEndDate);

	public void setInitialDueDate(Date aInitialDueDate);

	public void setIsOneOffInd(boolean isOneOffInd);

	public void setLastDocEntryDate(Date lastDocEntryDate);

	public void setEndDateChangedCount(int count);

	public void setRecurrentCheckListSubItemList(IRecurrentCheckListSubItem[] aSubItemList);

	public void updateSubItem(int anItemIndex, IRecurrentCheckListSubItem anISubtem);
	
	public abstract String getDocType();
	
	public abstract void setDocType(String docType);
	
}
