/*
 * Created on Jan 11, 2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.integrosys.cms.app.recurrent.bus;

//java
import java.io.Serializable;
import java.util.Date;

/**
 * @author user
 * 
 *         To change the template for this generated type comment go to
 *         Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public interface IConvenantSubItem extends Serializable {
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

	public Date getCheckedDate();

	public Date getDeferredDate();

	public Date getWaivedDate();

	public long getDeferredCount();

	public long getDaysOverDue();

	public boolean getIsPrintReminderInd();

	public boolean getIsDeletedInd();

	public boolean getIsVerifiedInd();

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

	public void setCheckedDate(Date aCheckedDate);

	public void setDeferredDate(Date aDeferredDate);

	public void setWaivedDate(Date waivedDate);

	public void setDeferredCount(long deferredCount);

	public void setIsPrintReminderInd(boolean anIsPrintReminderInd);

	public void setIsDeletedInd(boolean anIsDeletedInd);

	public void setIsVerifiedInd(boolean anIsVerifiedInd);

}
