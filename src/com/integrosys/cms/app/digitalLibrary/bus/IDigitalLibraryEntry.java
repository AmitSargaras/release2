/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/feed/bus/bond/IBondFeedEntry.java,v 1.6 2004/06/04 04:55:19 hltan Exp $
 */
package com.integrosys.cms.app.digitalLibrary.bus;

import java.util.Date;

import com.integrosys.base.techinfra.ejbsupport.IValueObject;

/**
 * 
 * @author $Author: hltan $
 * @version $Revision: 1.6 $
 * @since $Date: 2004/06/04 04:55:19 $ Tag: $Name: $
 * 
 */
public interface IDigitalLibraryEntry extends java.io.Serializable, IValueObject {
	
	/**
	 * @return the lastUpdatedDate
	 */
	public Date getLastUpdatedDate() ;

	/**
	 * @param lastUpdatedDate the lastUpdatedDate to set
	 */
	public void setLastUpdatedDate(Date lastUpdatedDate) ;
	
	public String getClimsDocCategory();

	public void setClimsDocCategory(String climsDocCategory) ;

	public String getDigiLibDocCategory() ;

	public void setDigiLibDocCategory(String digiLibDocCategory) ;
	
	public long getVersionTime() ;

	/**
	 * @param versionTime the versionTime to set
	 */
	public void setVersionTime(long versionTime) ;
	
	/**
	 * @return the approvalMatrixEntryID
	 */
	public long getDigitalLibraryEntryID() ;

	/**
	 * @param approvalMatrixEntryID the approvalMatrixEntryID to set
	 */
	public void setDigitalLibraryEntryID(long approvalMatrixEntryID) ;

	/**
	 * @return the approvalMatrixEntryRef
	 */
	public long getDigitalLibraryEntryRef() ;

	/**
	 * @param approvalMatrixEntryRef the approvalMatrixEntryRef to set
	 */
	public void setDigitalLibraryEntryRef(long approvalMatrixEntryRef) ;
	
	public String getClimsDocCategoryCode();
	
	public void setClimsDocCategoryCode(String climsDocCategoryCode);
	
	
	public String getClimsDocCategorySeq();
	
	public void setClimsDocCategorySeq(String climsDocCategorySeq);
	
	
	
	
	
	

	
}
