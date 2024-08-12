/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/feed/bus/bond/IBondFeedEntry.java,v 1.6 2004/06/04 04:55:19 hltan Exp $
 */
package com.integrosys.cms.app.feed.bus.bond;

import java.util.Date;

import org.apache.struts.upload.FormFile;

import com.integrosys.base.techinfra.ejbsupport.IValueObject;

/**
 * 
 * @author $Author: hltan $
 * @version $Revision: 1.6 $
 * @since $Date: 2004/06/04 04:55:19 $ Tag: $Name: $
 * 
 */
public interface IBondFeedEntry extends java.io.Serializable, IValueObject {
	
	/**
	 * @return the bondCode
	 */
	public String getBondCode() ;

	/**
	 * @param bondCode the bondCode to set
	 */
	public void setBondCode(String bondCode) ;

	/**
	 * @return the issueDate
	 */
	public Date getIssueDate() ;

	/**
	 * @param issueDate the issueDate to set
	 */
	public void setIssueDate(Date issueDate) ;

	/**
	 * @return the maturityDate
	 */
	public Date getMaturityDate() ;

	/**
	 * @param maturityDate the maturityDate to set
	 */
	public void setMaturityDate(Date maturityDate) ;

	/**
	 * @return the name
	 */
	public String getName() ;

	/**
	 * @param name the name to set
	 */
	public void setName(String name) ;

	/**
	 * @return the isinCode
	 */
	public String getIsinCode() ;

	/**
	 * @param isinCode the isinCode to set
	 */
	public void setIsinCode(String isinCode) ;

	/**
	 * @return the rating
	 */
	public String getRating() ;

	/**
	 * @param rating the rating to set
	 */
	public void setRating(String rating) ;

	/**
	 * @return the couponRate
	 */
	public double getCouponRate() ;

	/**
	 * @param couponRate the couponRate to set
	 */
	public void setCouponRate(double couponRate) ;

	/**
	 * @return the unitPrice
	 */
	public double getUnitPrice();

	/**
	 * @param unitPrice the unitPrice to set
	 */
	public void setUnitPrice(double unitPrice) ;

	/**
	 * @return the versionTime
	 */
	public long getVersionTime() ;

	/**
	 * @param versionTime the versionTime to set
	 */
	public void setVersionTime(long versionTime) ;

	/**
	 * @param bondFeedEntryID the bondFeedEntryID to set
	 */
	public void setBondFeedEntryID(long bondFeedEntryID);

	/**
	 * @param bondFeedEntryRef the bondFeedEntryRef to set
	 */
	public void setBondFeedEntryRef(long bondFeedEntryRef) ;


	public long getBondFeedEntryID() ;

	public long getBondFeedEntryRef() ;
	
	public FormFile getFileUpload();
	
	public void setFileUpload(FormFile fileUpload);
	
	public Date getLastUpdateDate() ;

	public void setLastUpdateDate(Date lastUpdateDate) ;
}
