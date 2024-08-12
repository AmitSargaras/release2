/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/limit/bus/IBFLTATParameterSummary.java,v 1.1 2005/09/12 02:51:18 hshii Exp $
 */
package com.integrosys.cms.app.limit.bus;

/**
 * This class contains the attribute required for the c/c checklist summary
 * 
 * @author $Author: hshii $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2005/09/12 02:51:18 $ Tag: $Name: $
 */
public interface IBFLTATParameterSummary extends java.io.Serializable {

	/**
	 * Get BFL TAT Segment
	 * 
	 * @return String
	 */
	public String getSegment();

	/**
	 * Set BFL TAT Segment
	 * 
	 * @param segment of type String
	 */
	public void setSegment(String segment);

	/**
	 * Get BFL TAT type
	 * 
	 * @return String
	 */
	public String getBflType();

	/**
	 * Set BFL TAT type
	 * 
	 * @param bflType of type String
	 */
	public void setBflType(String bflType);

	/**
	 * Get bca type
	 * 
	 * @return String
	 */
	public String getBcaType();

	/**
	 * Set BFL TAT bca type
	 * 
	 * @param bflType of type String
	 */
	public void setBcaType(String bcaType);

	/**
	 * Get BFL TAT local parameter in days
	 * 
	 * @return int
	 */
	public int getLocalDays();

	/**
	 * Set BFL TAT local parameter in days
	 * 
	 * @param localDays of type int
	 */
	public void setLocalDays(int localDays);

	/**
	 * Get BFL TAT overseas parameters in days
	 * 
	 * @return int
	 */
	public int getOverseasDays();

	/**
	 * Set BFL TAT overseas parameters in days
	 * 
	 * @param segment of type int
	 */
	public void setOverseasDays(int overseasDays);
}