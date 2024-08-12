/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/limit/bus/BFLTATParameterSummary.java,v 1.2 2005/09/19 08:16:21 czhou Exp $
 */
package com.integrosys.cms.app.limit.bus;

import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * This class contains the attribute required for the c/c checklist summary
 * 
 * @author $Author: czhou $<br>
 * @version $Revision: 1.2 $
 * @since $Date: 2005/09/19 08:16:21 $ Tag: $Name: $
 */
public class BFLTATParameterSummary implements IBFLTATParameterSummary {
	private String segment = null;

	private String bflType = null;

	private String bcaType = null;

	private int localDays = ICMSConstant.INT_INVALID_VALUE;

	private int overseasDays = ICMSConstant.INT_INVALID_VALUE;

	/**
	 * Get BFL TAT Segment
	 * 
	 * @return String
	 */
	public String getSegment() {
		return this.segment;
	}

	/**
	 * Set BFL TAT Segment
	 * 
	 * @param segment of type String
	 */
	public void setSegment(String segment) {
		this.segment = segment;
	}

	/**
	 * Get BFL TAT type
	 * 
	 * @return String
	 */
	public String getBflType() {
		return this.bflType;
	}

	/**
	 * Set BFL TAT type
	 * 
	 * @param bflType of type String
	 */
	public void setBflType(String bflType) {
		this.bflType = bflType;
	}

	/**
	 * Get bca type
	 * 
	 * @return String
	 */
	public String getBcaType() {
		return this.bcaType;
	}

	/**
	 * Set BFL TAT bca type
	 * 
	 * @param bcaType of type String
	 */
	public void setBcaType(String bcaType) {
		this.bcaType = bcaType;
	}

	/**
	 * Get BFL TAT local parameter in days
	 * 
	 * @return int
	 */
	public int getLocalDays() {
		return this.localDays;
	}

	/**
	 * Set BFL TAT local parameter in days
	 * 
	 * @param localDays of type int
	 */
	public void setLocalDays(int localDays) {
		this.localDays = localDays;
	}

	/**
	 * Get BFL TAT overseas parameters in days
	 * 
	 * @return int
	 */
	public int getOverseasDays() {
		return this.overseasDays;
	}

	/**
	 * Set BFL TAT overseas parameters in days
	 * 
	 * @param overseasDays of type int
	 */
	public void setOverseasDays(int overseasDays) {
		this.overseasDays = overseasDays;
	}
}