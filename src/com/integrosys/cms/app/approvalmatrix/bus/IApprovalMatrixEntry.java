/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/feed/bus/bond/IBondFeedEntry.java,v 1.6 2004/06/04 04:55:19 hltan Exp $
 */
package com.integrosys.cms.app.approvalmatrix.bus;

import java.util.Date;

import com.integrosys.base.techinfra.ejbsupport.IValueObject;

/**
 * 
 * @author $Author: hltan $
 * @version $Revision: 1.6 $
 * @since $Date: 2004/06/04 04:55:19 $ Tag: $Name: $
 * 
 */
public interface IApprovalMatrixEntry extends java.io.Serializable, IValueObject {
	
	/**
	 * @return the lastUpdatedDate
	 */
	public Date getLastUpdatedDate() ;

	/**
	 * @param lastUpdatedDate the lastUpdatedDate to set
	 */
	public void setLastUpdatedDate(Date lastUpdatedDate) ;
	
	/**
	 * @return the riskGrade
	 */
	public int getRiskGrade() ;

	/**
	 * @param riskGrade the riskGrade to set
	 */
	public void setRiskGrade(int riskGrade) ;

	/**
	 * @return the level1
	 */
	public String getLevel1() ;

	/**
	 * @param level1 the level1 to set
	 */
	public void setLevel1(String level1) ;
	
	/**
	 * @return the level2
	 */
	public String getLevel2() ;

	/**
	 * @param level2 the level2 to set
	 */
	public void setLevel2(String level2) ;

	/**
	 * @return the level3
	 */
	public String getLevel3() ;

	/**
	 * @param level3 the level3 to set
	 */
	public void setLevel3(String level3) ;
	
	/**
	 * @return the level4
	 */
	public String getLevel4() ;

	/**
	 * @param level4 the level4 to set
	 */
	public void setLevel4(String level4) ;

	/**
	 * @return the versionTime
	 */
	public long getVersionTime() ;

	/**
	 * @param versionTime the versionTime to set
	 */
	public void setVersionTime(long versionTime) ;
	
	/**
	 * @return the approvalMatrixEntryID
	 */
	public long getApprovalMatrixEntryID() ;

	/**
	 * @param approvalMatrixEntryID the approvalMatrixEntryID to set
	 */
	public void setApprovalMatrixEntryID(long approvalMatrixEntryID) ;

	/**
	 * @return the approvalMatrixEntryRef
	 */
	public long getApprovalMatrixEntryRef() ;

	/**
	 * @param approvalMatrixEntryRef the approvalMatrixEntryRef to set
	 */
	public void setApprovalMatrixEntryRef(long approvalMatrixEntryRef) ;
}
