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
public interface IAnnexureSubItem extends Serializable {
	public long getSubItemID();

	public long getSubItemRef();

	public String getStatus();

	public Date getDocEndDate();

	public Date getDueDate();

	public Date getDeferredDate();

	public Date getWaivedDate();

	public void setSubItemID(long aSubItemID);

	public void setSubItemRef(long aSubItemRef);

	public void setStatus(String aStatus);

	public void setDocEndDate(Date aDocEndDate);

	public void setDueDate(Date aDueDate);

	public void setDeferredDate(Date aDeferredDate);

	public void setWaivedDate(Date waivedDate);

}
