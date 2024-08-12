/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/checklist/bus/IDocumentHeldItem.java,v 1.4 2006/07/31 01:57:25 czhou Exp $
 */
package com.integrosys.cms.app.chktemplate.bus;

//java
import java.io.Serializable;
import java.util.Date;

/**
 * This interface defines the list of attributes that is required for listing
 * held documents.
 * 
 * @author $Author: czhou $<br>
 * @version $Revision: 1.4 $
 * @since $Date: 2006/07/31 01:57:25 $ Tag: $Name: $
 */
public interface IDocumentHeldItem extends Serializable {
	public long getDocNo();

	public String getDocCode();

	public String getDocDesc();

	public String getNarration();

	public long getParentDocItemRefID();

	public String getParentDocItemDocCode();

	public Boolean isInVault();

	public Boolean isExtCust();

	public String getDocStatus();

	public String getCustStatus();

	public Date getDocDate();

	public Date getDocExpiry();

	public boolean isShared();

	public String getShareDetailsSummary();

	public void setDocNo(long docNo);

	public void setDocCode(String docCode);

	public void setDocDesc(String docDesc);

	public void setNarration(String narration);

	public void setParentDocItemRefID(long parentItemRefID);

	public void setParentDocItemDocCode(String parentDocCode);

	public void setInVault(Boolean isInVault);

	public void setExtCust(Boolean isExtCust);

	public void setDocStatus(String docStatus);

	public void setCustStatus(String custStatus);

	public void setDocDate(Date docDate);

	public void setDocExpiry(Date docExpiry);

	public void setIsShared(boolean shared);

	public void setShareDetailsSummary(String shareDetailsSummary);

}
