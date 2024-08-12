/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/bus/IPledgorCreditGrade.java,v 1.1 2003/09/03 09:25:26 elango Exp $
 */
package com.integrosys.cms.app.collateral.bus;

import java.io.Serializable;
import java.util.Date;

/**
 * This interface represents pledgor credit grade information.
 * 
 * @author $Author: elango $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2003/09/03 09:25:26 $ Tag: $Name: $
 */
public interface IPledgorCreditGrade extends Serializable {
	/**
	 * Get pledgor credit grade id.
	 * 
	 * @return long
	 */
	public long getCreditGradeID();

	/**
	 * Set pledgor credit grade id.
	 * 
	 * @param creditGradeID of type long
	 */
	public void setCreditGradeID(long creditGradeID);

	/**
	 * Get pledgor ID, the primary key in CMS.
	 * 
	 * @return long
	 */
	public long getPledgorID();

	/**
	 * Set pledgor ID, the primary key in CMS.
	 * 
	 * @param pledgorID is of type long
	 */
	public void setPledgorID(long pledgorID);

	/**
	 * Get pledgor credit grade id SCI reference.
	 * 
	 * @return long
	 */
	public long getCreditGradeIDRef();

	/**
	 * Set pledgor credit grade id SCI reference.
	 * 
	 * @param creditGradeIDRef of type long
	 */
	public void setCreditGradeIDRef(long creditGradeIDRef);

	/**
	 * Get pledgor id SCI reference.
	 * 
	 * @return long
	 */
	public long getPledgorIDRef();

	/**
	 * Set pledgor id SCI reference.
	 * 
	 * @param pledgorIDRef of type long
	 */
	public void setPledgorIDRef(long pledgorIDRef);

	/**
	 * Get pledgor credit grade type value.
	 * 
	 * @return String
	 */
	public String getCreditGradeType();

	/**
	 * Set pledgor credit grade type value.
	 * 
	 * @param creditGradeType of type String
	 */
	public void setCreditGradeType(String creditGradeType);

	/**
	 * Get pledgor credit grade code value.
	 * 
	 * @return String
	 */
	public String getCreditGradeCode();

	/**
	 * Set pledgor credit grade code value.
	 * 
	 * @param creditGradeCode of type String
	 */
	public void setCreditGradeCode(String creditGradeCode);

	/**
	 * Get pledgor credit grade start date.
	 * 
	 * @return Date
	 */
	public Date getCreditGradeStartDate();

	/**
	 * Set pledgor credit grade start date.
	 * 
	 * @param creditGradeStartDate of type Date
	 */
	public void setCreditGradeStartDate(Date creditGradeStartDate);

	/**
	 * Get pledgor credit grade description.
	 * 
	 * @return String
	 */
	public String getCreditGradeDesc();

	/**
	 * Set pledgor credit grade description.
	 * 
	 * @param creditGradeDesc of type String
	 */
	public void setCreditGradeDesc(String creditGradeDesc);
}
